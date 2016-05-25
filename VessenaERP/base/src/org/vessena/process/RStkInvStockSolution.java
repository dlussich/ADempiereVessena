/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 19/02/2013
 */
package org.openup.process;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MProduct;
import org.compiere.model.MUOM;
import org.compiere.model.MUOMConversion;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * org.openup.process - RStkInvStockSolution
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Hp - 19/02/2013
 * @see
 */
public class RStkInvStockSolution extends SvrProcess {

	// Variables para recibir filtros del reporte
	private Timestamp fechaHasta;
	private int adClientID = 0, adUserID = 0;
	private int mWarehouseID = 0, mLocatorID = 0;
	private int uyLineaNegocioID = 0, mProductCategoryID = 0; 
	private int mProductID = 0;
	private int uyFamiliaID = 0, uySubFamiliaID = 0;
	private boolean hideQtyZero = false;
	
	private String idReporte = ""; 
	private static final String TABLA_MOLDE = "UY_Molde_StkInvSolution";

	/**
	 * Costructor.
	 */
	public RStkInvStockSolution() {
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
					if(para[i].getParameter()!=null){
						this.fechaHasta = (Timestamp)para[i].getParameter();
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
				if (name.equalsIgnoreCase("M_Product_Category_ID")){
					if(para[i].getParameter()!=null){
						this.mProductCategoryID = ((BigDecimal)para[i].getParameter()).intValueExact();
					}
				}
				if (name.equalsIgnoreCase("UY_Linea_Negocio_ID")){
					if(para[i].getParameter()!=null){
						this.uyLineaNegocioID = ((BigDecimal)para[i].getParameter()).intValueExact();
					}
				}
				if (name.equalsIgnoreCase("UY_Familia_ID")){
					if(para[i].getParameter()!=null){
						this.uyFamiliaID = ((BigDecimal)para[i].getParameter()).intValueExact();
					}
				}
				if (name.equalsIgnoreCase("UY_SubFamilia_ID")){
					if(para[i].getParameter()!=null){
						this.uySubFamiliaID = ((BigDecimal)para[i].getParameter()).intValueExact();
					}
				}
				if (name.equalsIgnoreCase("M_Product_ID")){
					if(para[i].getParameter()!=null){
						this.mProductID = ((BigDecimal)para[i].getParameter()).intValueExact();
					}
				}
				if (name.equalsIgnoreCase("iszero")){
					if(para[i].getParameter()!=null){
						this.hideQtyZero = (((String)para[i].getParameter()).equalsIgnoreCase("Y")) ? true : false;
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
		
		// Actualizo valores necesarios en columnas de la tabla molde. 
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
				  " WHERE ad_user_id =" + this.adUserID;
			DB.executeUpdate(sql,null);
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}
	}

	/**
	 * OpenUp. Gabriel Vila. 27/06/2011. Issue #743.
	 * @throws Exception 
	 */
	private void getData() throws Exception{
		
		String insert = "", sql = "";

		try
		{
			insert = "INSERT INTO " + TABLA_MOLDE + " (ad_client_id, ad_org_id, ad_user_id, idreporte," +
					" fecreporte, iszero, m_warehouse_id, m_locator_id, m_product_id, name, c_uom_id, m_product_category_id, " +
					" uy_linea_negocio_id, uy_familia_id, uy_subfamilia_id, c_period_id, movementdate," +
					" qty_approved, qty_blocked, qty_quarantine, qty_physical, qty_intransit, qty_reserved, qty_pending, qty_available) ";
			
			// Filtros seleccionados por el usuario
			String filtros = " WHERE stk.ad_client_id =" + this.adClientID;
			if (this.mWarehouseID > 0) filtros += " AND stk.m_warehouse_id =" + this.mWarehouseID;
			if (this.mLocatorID > 0) filtros += " AND stk.m_locator_id =" + this.mLocatorID;
			if (this.mProductCategoryID > 0) filtros += " AND prod.m_product_category_id =" + this.mProductCategoryID;
			if (this.uyLineaNegocioID > 0) filtros += " AND prod.uy_linea_negocio_id =" + this.uyLineaNegocioID;
			if (this.uyFamiliaID > 0) filtros += " AND prod.uy_familia_id =" + this.uyFamiliaID;
			if (this.uySubFamiliaID > 0) filtros += " AND prod.uy_subfamilia_id =" + this.uySubFamiliaID;
			if (this.mProductID > 0) filtros += " AND stk.m_product_id =" + this.mProductID;
			
			sql = " SELECT stk.ad_client_id,0," + this.adUserID + ",'" + this.idReporte + "',current_date,'N'," +
				  " stk.m_warehouse_id,stk.m_locator_id,stk.m_product_id,prod.name,prod.c_uom_id,prod.m_product_category_id," +
				  " prod.uy_linea_negocio_id,prod.uy_familia_id,prod.uy_subfamilia_id,0,'" + this.fechaHasta + "'," + 
				  " stk_approved(stk.m_product_id, stk.m_warehouse_id, stk.m_locator_id, null, null, '" + this.fechaHasta + "') as approved," +
				  " stk_locked(stk.m_product_id, stk.m_warehouse_id, stk.m_locator_id, null, null, '" + this.fechaHasta + "') as locked," +
				  " stk_quarantine(stk.m_product_id, stk.m_warehouse_id, stk.m_locator_id, null, null, '" + this.fechaHasta + "') as quarantine," +
				  " stk_physical(stk.m_product_id, stk.m_warehouse_id, stk.m_locator_id, null, null, '" + this.fechaHasta + "') as physical," +
				  " stk_ordered(stk.m_product_id, stk.m_warehouse_id, stk.m_locator_id, null, null, '" + this.fechaHasta + "') as ordered," +
				  " stk_reserved(stk.m_product_id, stk.m_warehouse_id, stk.m_locator_id, null, null, '" + this.fechaHasta + "') as reserved," +
				  " stk_pending(stk.m_product_id, stk.m_warehouse_id, stk.m_locator_id, null, null, '" + this.fechaHasta + "') as pendiente," +
				  " stk_available(stk.m_product_id, stk.m_warehouse_id, stk.m_locator_id, null, null, '" + this.fechaHasta + "') as disponible " +
				  " FROM stk_distinct_prodxwarxloc stk " +
				  " INNER JOIN m_product prod on stk.m_product_id = prod.m_product_id " + filtros;
			
			log.log(Level.INFO, insert + sql);
			DB.executeUpdate(insert + sql, null);
			
 		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, insert + sql, e);
			throw e;
		}
	}
	
	/**
	 * Actualizo valores necesarios en columnas de la tabla molde.
	 */
	private void updateData(){
		String sql = "";
		try{
			
			// Si el usuario indica ocultar registros con cantidad fisica cero
			if (this.hideQtyZero){
				sql = " UPDATE " + TABLA_MOLDE + 
					  " SET iszero = 'Y' " +
					  " WHERE idreporte ='" + this.idReporte + "'" + 
					  " AND qty_physical <> 0";
				DB.executeUpdate(sql,null);
			}
			
			this.updateCajas();
			
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}		
	}

	private void updateCajas() {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try{
			sql = " SELECT * " +
				  " FROM " + TABLA_MOLDE + 
				  " WHERE idreporte ='" + this.idReporte + "'" + 
				  " ORDER BY m_product_id ";
			
			pstmt = DB.prepareStatement (sql, null);
			rs = pstmt.executeQuery ();

			// Recorro registros y voy actualizando
			while (rs.next()){
				MUOMConversion[] convs = MUOMConversion.getProductConversions(getCtx(), rs.getInt("m_product_id"));
				if (convs.length > 0){
					for (int i = 0; i < convs.length; i++){
						MUOM uom = (MUOM)convs[i].getC_UOM_To();
						if (uom.getContainerType() != null){
							if (uom.getContainerType().equalsIgnoreCase("CAJ")){
								
								BigDecimal divideRate = convs[i].getDivideRate();
								
								if (divideRate != null){
									if (divideRate.compareTo(Env.ZERO) > 0){

										BigDecimal caj_qty_approved = Env.ZERO, caj_qty_blocked = Env.ZERO, caj_qty_quarantine = Env.ZERO;
										BigDecimal caj_qty_physical = Env.ZERO, caj_qty_intransit = Env.ZERO, caj_qty_reserved = Env.ZERO; 
										BigDecimal caj_qty_pending = Env.ZERO, caj_qty_available = Env.ZERO;
										
										caj_qty_approved = rs.getBigDecimal("qty_approved").divide(divideRate, 2, RoundingMode.HALF_UP);
										caj_qty_blocked = rs.getBigDecimal("qty_blocked").divide(divideRate, 2, RoundingMode.HALF_UP);
										caj_qty_quarantine = rs.getBigDecimal("qty_quarantine").divide(divideRate, 2, RoundingMode.HALF_UP);
										caj_qty_physical = rs.getBigDecimal("qty_physical").divide(divideRate, 2, RoundingMode.HALF_UP);
										caj_qty_intransit = rs.getBigDecimal("qty_intransit").divide(divideRate, 2, RoundingMode.HALF_UP);
										caj_qty_reserved = rs.getBigDecimal("qty_reserved").divide(divideRate, 2, RoundingMode.HALF_UP);
										caj_qty_pending = rs.getBigDecimal("qty_pending").divide(divideRate, 2, RoundingMode.HALF_UP);
										caj_qty_available = rs.getBigDecimal("qty_available").divide(divideRate, 2, RoundingMode.HALF_UP);
										
										String action = "update " + TABLA_MOLDE +
												" set caj_qty_approved =" + caj_qty_approved + "," +
												" caj_qty_blocked =" + caj_qty_blocked + "," +
												" caj_qty_quarantine =" + caj_qty_quarantine + "," +
												" caj_qty_physical =" + caj_qty_physical + "," +
												" caj_qty_intransit =" + caj_qty_intransit + "," +
												" caj_qty_reserved =" + caj_qty_reserved + "," +
												" caj_qty_pending =" + caj_qty_pending + "," +
												" caj_qty_available =" + caj_qty_available + "," +
												" caj_uom =" + "'" + uom.getName() + "'" + //OpenUp. Nicolas Sarlabos. 27/02/2013. Se carga el nombre de la UM. 
												" WHERE idreporte ='" + this.idReporte + "' " +
												" and m_product_id =" + rs.getInt("m_product_id");
										DB.executeUpdateEx(action, null);
									}
								}
								
								
								
								
							}							
						}
					}
					
				}
		
				// Quintales
				BigDecimal qui_qty_physical = Env.ZERO, qui_qty_reserved = Env.ZERO, qui_qty_available = Env.ZERO;

				MProduct prod = new MProduct(getCtx(), rs.getInt("m_product_id"), null);
				if (prod.get_ID() > 0){
					if (prod.get_Value("uy_factor") != null){
						BigDecimal factorQuintal = (BigDecimal)prod.get_Value("uy_factor");
						if (factorQuintal != null){
							if (factorQuintal.compareTo(Env.ZERO) > 0){
								
								qui_qty_physical = rs.getBigDecimal("qty_physical").multiply(factorQuintal).setScale(5, RoundingMode.HALF_UP);
								qui_qty_reserved = rs.getBigDecimal("qty_reserved").multiply(factorQuintal).setScale(5, RoundingMode.HALF_UP);
								qui_qty_available = rs.getBigDecimal("qty_available").multiply(factorQuintal).setScale(5, RoundingMode.HALF_UP);
								
								String action = "update " + TABLA_MOLDE +
										" set qui_qty_physical =" + qui_qty_physical + "," +
										" qui_qty_reserved =" + qui_qty_reserved + "," +
										" qui_qty_available =" + qui_qty_available + 
										" WHERE idreporte ='" + this.idReporte + "' " +
										" and m_product_id =" + rs.getInt("m_product_id");
								DB.executeUpdateEx(action, null);
							}
						}
					}
				}
				
			}
			
		}
		catch (Exception e)
		{
			throw new AdempiereException(e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}

	}

}
