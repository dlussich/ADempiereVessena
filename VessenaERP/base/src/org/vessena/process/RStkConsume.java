/**
 * 
 */
package org.openup.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MDocType;
import org.compiere.model.MSequence;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.openup.model.X_UY_ProductConsume;

/**
 * @author Nicolas
 *
 */
public class RStkConsume extends SvrProcess {
	
	// Variables para recibir filtros del reporte
	private Timestamp fechaDesde = null;
	private Timestamp fechaHasta = null;
	private int truckID = 0, prodID = 0;
	private int adClientID = 0, adUserID = 0;
	private int mWarehouseID = 0, mLocatorID = 0;
	 
	private String idReporte = ""; 
	private static final String TABLA_MOLDE = "UY_Molde_StkMovement";

	/**
	 * 
	 */
	public RStkConsume() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		
		// Parametro para id de reporte
		ProcessInfoParameter paramIDReporte = null;
		
		// Obtengo parametros y los recorro
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName().trim();
			if (name!= null){
				if (name.equalsIgnoreCase("idReporte")){
					paramIDReporte = para[i]; 
				}
				if (name.equalsIgnoreCase("MovementDate")){
					this.fechaDesde = (Timestamp)para[i].getParameter();
					this.fechaHasta = (Timestamp)para[i].getParameter_To();
				}
				if (name.equalsIgnoreCase("UY_TR_Truck_ID")){
					if(para[i].getParameter()!=null){
						this.truckID = ((BigDecimal)para[i].getParameter()).intValueExact();
					}
				}
				if (name.equalsIgnoreCase("M_Warehouse_ID")){
					if(para[i].getParameter()!=null){
						this.mWarehouseID = ((BigDecimal)para[i].getParameter()).intValueExact();
					}
				}
				if (name.equalsIgnoreCase("M_Locator_ID")){
					if(para[i].getParameter()!=null){
						this.mLocatorID = ((BigDecimal)para[i].getParameter()).intValueExact();
					}
				}
				if (name.equalsIgnoreCase("M_Product_ID")){
					if(para[i].getParameter()!=null){
						this.prodID = ((BigDecimal)para[i].getParameter()).intValueExact();
					}
				}
	
			}
		}

		// Empresa - Usuario
		this.adClientID = getAD_Client_ID();
		this.adUserID = getAD_User_ID();
		
		// Obtengo id para este reporte
		this.idReporte = UtilReportes.getReportID(new Long(this.adUserID));
		
		// Si tengo parametro para idreporte
		if (paramIDReporte!=null) paramIDReporte.setParameter(this.idReporte);

	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
	
		// Delete de reportes anteriores de este usuario para ir limpiando la tabla molde
		this.deleteInstanciasViejasReporte();

		// Cargo informacion a reportear en tabla molde
		this.getData();
		
		// Actualizo data en columnas de tabla molde
		this.updateData();
		
		// Retorno con exito.
		return "ok";
	}
	
	/**
	 * Delete de registros de instancias viejas de este reporte para el usuario conectado
	 */
	private void deleteInstanciasViejasReporte(){
		String sql = "";
		try{
			sql = " DELETE FROM " + TABLA_MOLDE + 
				  " WHERE idusuario =" + this.adUserID;
			DB.executeUpdate(sql,null);
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}
	}
	
	private void getData() throws Exception{
		
		String insert = "", sql = "";

		try
		{
			insert = "INSERT INTO " + TABLA_MOLDE + " (ad_client_id, ad_org_id, idusuario, idreporte,fecreporte," +
					" m_product_id, prodname, c_uom_id, m_product_category_id, uy_linea_negocio_id, uy_familia_id, uy_subfamilia_id, " +
					" m_warehouse_id, m_locator_id, movementdate, uy_stockstatustype_id, movementtype, uy_stockstatus_id, " +
					" c_doctype_id, documentno, c_invoice_id, c_bpartner_id, bpname, ad_user_id, datetrx," +
				    " saldoinicial, saldoacumulado, entrada, salida, record_id, uy_tr_truck_id) ";
			
			//obtengo tipo de documento "Consumo de Stock"
			MDocType doc = MDocType.forValue(getCtx(), "prodconsume", get_TrxName());

			if(doc!=null && doc.get_ID()>0){
				
				// Filtros seleccionados por el usuario
				String filtros = " where stk.ad_client_id = " + this.adClientID + " and stk.c_doctype_id = " + doc.get_ID() + 
						" and stk.ad_table_id = " + X_UY_ProductConsume.Table_ID + 
						" and stk.movementdate between '" + this.fechaDesde + "' and '" + this.fechaHasta + "'" +
						" and pc.docstatus = 'CO'";

				if (this.mWarehouseID > 0) filtros += " AND stk.m_warehouse_id =" + this.mWarehouseID;
				if (this.mLocatorID > 0) filtros += " AND stk.m_locator_id =" + this.mLocatorID;
				if (this.truckID > 0) filtros += " AND pc.uy_tr_truck_id =" + this.truckID;
				if (this.prodID > 0) filtros += " AND stk.m_product_id =" + this.prodID;

				// Funcion para calculo de saldo inicial
				String funcionSaldoInicial = "stk_bystatus(stk.m_product_id, stk.m_warehouse_id, stk.m_locator_id,null,null,'" + 
			  			  new Timestamp(this.fechaDesde.getTime() - 86400000) + "',stk.uy_stockstatus_id)";

				sql = " SELECT stk.ad_client_id,0," + this.adUserID + ",'" + this.idReporte + "',current_date," + 
						" stk.m_product_id, prod.name as prodname, " +
						" prod.c_uom_id,prod.m_product_category_id, prod.uy_linea_negocio_id,prod.uy_familia_id,prod.uy_subfamilia_id," +
						" stk.m_warehouse_id, stk.m_locator_id, stk.movementdate, sts.uy_stockstatustype_id, stsdoc.movementtype," +
						" stk.uy_stockstatus_id, stk.c_doctype_id, stk.documentno," +
						" vuy_inout_invoices.c_invoice_id," +
						" stk.c_bpartner_id, bp.name as bpname, stk.ad_user_id, stk.updated," +
						funcionSaldoInicial + " as saldoinicial, 0 as saldoacumulado," +
						" case when sign = 1 then stk.movementqty else 0 end as entrada," +
						" case when sign = -1 then stk.movementqty else 0 end as salida, " + 
						" nextid(" + MSequence.get(Env.getCtx(), TABLA_MOLDE).get_ID() + ",'N'), pc.uy_tr_truck_id " +
						" from uy_stocktransaction stk " +
						" inner join m_product prod on stk.m_product_id = prod.m_product_id " +
						" left outer join c_bpartner bp on stk.c_bpartner_id = bp.c_bpartner_id " +
						" inner join uy_stockstatus sts on stk.uy_stockstatus_id = sts.uy_stockstatus_id " +
						" inner join uy_stockstatusbydochdr stsdoc on stk.c_doctype_id = stsdoc.c_doctype_id " + 
						" inner join uy_productconsume pc on stk.record_id = pc.uy_productconsume_id " +
						" left outer join vuy_inout_invoices on (stk.record_id = vuy_inout_invoices.m_inout_id AND stk.ad_table_id = 319) " + filtros;

				log.log(Level.INFO, insert + sql);
				DB.executeUpdate(insert + sql, null);

			} else throw new AdempiereException("No se pudo obtener tipo de documento 'Consumo de Stock'");
			
 		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, insert + sql, e);
			throw e;
		}
	}

	/**
	 * Actualizo valores necesarios en columnas de la tabla molde.
	 * @throws Exception 
	 */
	private void updateData() throws Exception{

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try{
			// Update de Saldo Acumulado
			// Obtengo registros de tabla temporal debidamiente ordenados
			sql = " SELECT * FROM " + TABLA_MOLDE +
				  " WHERE idreporte ='" + this.idReporte + "'" +
				  " AND idusuario =" + this.adUserID +
				  " ORDER BY m_product_id, m_warehouse_id, m_locator_id, movementdate, record_id";
			
			pstmt = DB.prepareStatement (sql, null);
			rs = pstmt.executeQuery();
			
			// Corte de control
			int mProductAuxID = 0, mWarehouseAuxID = 0, mLocatorAuxID = 0;
			int mProductID = 0, mWarehouseID = 0, mLocatorID = 0;
			BigDecimal acumulado = Env.ZERO;
			
			while (rs.next()){
				mProductAuxID = rs.getInt("m_product_id");
				mWarehouseAuxID = rs.getInt("m_warehouse_id");
				mLocatorAuxID = rs.getInt("m_locator_id");

				// Corte por producto-almacen-ubicacion
				if ((mProductAuxID != mProductID) || (mWarehouseAuxID != mWarehouseID) || (mLocatorAuxID != mLocatorID)){
					acumulado = rs.getBigDecimal("saldoinicial");
					mProductID = mProductAuxID; mWarehouseID = mWarehouseAuxID; mLocatorID = mLocatorAuxID;
				}
				acumulado = acumulado.add(rs.getBigDecimal("entrada")).subtract(rs.getBigDecimal("salida"));
				DB.executeUpdate(" UPDATE " + TABLA_MOLDE + " SET saldoacumulado =" + acumulado + 
							     " WHERE record_id =" + rs.getInt("record_id"), null);
			}
		}
		catch (Exception e)
		{
			throw e;
		}	
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
	}

}
