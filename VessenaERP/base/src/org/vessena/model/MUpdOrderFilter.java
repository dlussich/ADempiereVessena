/**
 * MUpdOrderFilter.java
 * 03/03/2011
 */
package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;

import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MSequence;
import org.compiere.model.MSysConfig;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Trx;

/**
 * OpenUp.
 * MUpdOrderFilter
 * Descripcion :
 * @author Gabriel Vila
 * Fecha : 03/03/2011
 */
public class MUpdOrderFilter extends X_UY_UpdOrder_Filter implements DocAction {

	private String processMsg = null;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 986866045115613632L;

	/**
	 * Constructor
	 * @param ctx
	 * @param UY_UpdOrder_Filter_ID
	 * @param trxName
	 */
	public MUpdOrderFilter(Properties ctx, int UY_UpdOrder_Filter_ID,
			String trxName) {
		super(ctx, UY_UpdOrder_Filter_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUpdOrderFilter(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#approveIt()
	 */
	@Override
	public boolean approveIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#closeIt()
	 */
	@Override
	public boolean closeIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#completeIt()
	 */
	@Override
	public String completeIt() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#createPDF()
	 */
	@Override
	public File createPDF() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getApprovalAmt()
	 */
	@Override
	public BigDecimal getApprovalAmt() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getC_Currency_ID()
	 */
	@Override
	public int getC_Currency_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getDoc_User_ID()
	 */
	@Override
	public int getDoc_User_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getDocumentInfo()
	 */
	@Override
	public String getDocumentInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getDocumentNo()
	 */
	@Override
	public String getDocumentNo() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getProcessMsg()
	 */
	@Override
	public String getProcessMsg() {
		return this.processMsg;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getSummary()
	 */
	@Override
	public String getSummary() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#invalidateIt()
	 */
	@Override
	public boolean invalidateIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#prepareIt()
	 */
	@Override
	public String prepareIt() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#processIt(java.lang.String)
	 */
	@Override
	public boolean processIt(String action) throws Exception {
		// Proceso segun accion
		if (action.equalsIgnoreCase(DocumentEngine.ACTION_Prepare))
			return this.loadOrders();
		else if (action.equalsIgnoreCase(DocumentEngine.ACTION_Complete))
			return this.updateOrders();
		else
			return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#reActivateIt()
	 */
	@Override
	public boolean reActivateIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#rejectIt()
	 */
	@Override
	public boolean rejectIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#reverseAccrualIt()
	 */
	@Override
	public boolean reverseAccrualIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#reverseCorrectIt()
	 */
	@Override
	public boolean reverseCorrectIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#unlockIt()
	 */
	@Override
	public boolean unlockIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#voidIt()
	 */
	@Override
	public boolean voidIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * OpenUp.	
	 * Descripcion : Obtiene y carga en tabla del proceso, los pedidos de venta a considerar.
	 * @author  Gabriel Vila 
	 * Fecha : 04/03/2011
	 */
	private boolean loadOrders()throws Exception{
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		boolean result = true;
		
		//Controlo que si pone el check de solo lineas, que el campo de producto no este vacio.
		if (this.getuy_docaction_update().equalsIgnoreCase(DocAction.ACTION_Void)&& this.isChecked()){//Solo lineas
			if(this.getM_Product_ID()==0){
				throw new Exception("Por favor seleccione un producto");
			}
		}
		
		try
		{
			log.info("Iniciando carga de pedidos de venta no para proceso de actualizacion.");
			
			// Obtengo numero de transaccion
			int idDocNroTrans = Integer.parseInt(MSysConfig.getValue("UY_DOCID_NROTRANSACCION", Env.getAD_Client_ID(Env.getCtx())));
			int nroTrans = Integer.parseInt(MSequence.getDocumentNo(idDocNroTrans, null, false, null));

			// Eliminar posibles filtros de pedidos anteriores, para no duplicar.
			DB.executeUpdate("DELETE FROM UY_UpdOrder_Order WHERE UY_UpdOrder_Filter_ID =" + this.getUY_UpdOrder_Filter_ID(), get_TrxName());
			
			// Condicion de Filtros
			String whereFiltros = "", whereZonas = "", whereProducto = "", whereDocStatus="";

			if (this.getuy_dateordered_from()!=null) whereFiltros += " AND hdr.dateordered >='" + this.getuy_dateordered_from() + "'";
			if (this.getuy_dateordered_to()!=null) whereFiltros += " AND hdr.dateordered <='" + this.getuy_dateordered_to() + "'";
			if (this.getM_Warehouse_ID()>0) whereFiltros += " AND hdr.m_warehouse_id =" + this.getM_Warehouse_ID();
			if (this.getC_BPartner_ID()>0) whereFiltros += " AND hdr.c_bpartner_id =" + this.getC_BPartner_ID();
			if (this.getUY_CanalVentas_ID()>0) whereFiltros += " AND cli.uy_canalventas_id =" + this.getUY_CanalVentas_ID();
			if (this.getC_SalesRegion_ID()>0) whereFiltros += " AND cliloc.c_salesregion_id =" + this.getC_SalesRegion_ID();
			if (this.getuy_datepromised_from()!=null) whereFiltros += " AND hdr.datepromised >='" + this.getuy_datepromised_from() + "'";
			if (this.getuy_datepromised_to()!=null) whereFiltros += " AND hdr.datepromised <='" + this.getuy_datepromised_to() + "'";
			if (this.getC_Order_ID()>0) whereFiltros += " AND hdr.c_order_id =" + this.getC_Order_ID();
			//Nicolas Garcia 17-3-2011 Se agregan Filtros
			if (this.getC_BPartner_Location_ID()>0) whereFiltros += " AND cliloc.c_bpartner_location_ID =" + this.getC_BPartner_Location_ID();
			if (this.getSalesRep_ID()>0) whereFiltros += " AND hdr.salesrep_id =" + this.getSalesRep_ID();
			// Fin N.G
			// OpenUp. Nicolas Garcia. 21/09/2011. Issue #682.
			if (this.getM_Product_ID()>0) whereProducto = " AND EXISTS (" +
				" SELECT c_orderline_id FROM c_orderline WHERE m_product_id =" + this.getM_Product_ID()
						+ " AND c_orderline.c_order_id = hdr.c_order_id AND C_Orderline_ID IN (select c_orderline_id from vuy_pedidos_pendientes_lineas))";

			//Fin Issue #682

			
			// Zonas de Entrega
			if (this.getuy_zonareparto_filtro1()>0) {
				whereZonas = " AND cliloc.uy_zonareparto_id IN (" + getuy_zonareparto_filtro1() + ")";
			}
			
			if (this.getuy_zonareparto_filtro2()>0) {
				if (!whereZonas.equalsIgnoreCase(""))
					whereZonas = " AND cliloc.uy_zonareparto_id IN (" + getuy_zonareparto_filtro1() + 
								 "," + getuy_zonareparto_filtro2() + ")";
				else
					whereZonas = " AND cliloc.uy_zonareparto_id IN (" + getuy_zonareparto_filtro2() + ")";
			}

			if (this.getuy_zonareparto_filtro3()>0) {
				if (whereZonas.equalsIgnoreCase(""))
					whereZonas = " AND cliloc.uy_zonareparto_id IN (" + getuy_zonareparto_filtro3() + ")";
				else{
					if ( (this.getuy_zonareparto_filtro1() > 0) && (this.getuy_zonareparto_filtro2() > 0)){
						whereZonas = " AND cliloc.uy_zonareparto_id IN (" + getuy_zonareparto_filtro1() + 
						 "," + getuy_zonareparto_filtro2() + 
						 "," + getuy_zonareparto_filtro3() + ")";
					}
					else if (this.getuy_zonareparto_filtro1() > 0){
						whereZonas = " AND cliloc.uy_zonareparto_id IN (" + getuy_zonareparto_filtro1() + 
						 "," + getuy_zonareparto_filtro3() + ")";

					}
					else if (this.getuy_zonareparto_filtro2() > 0){
						whereZonas = " AND cliloc.uy_zonareparto_id IN (" + getuy_zonareparto_filtro2() + 
						 "," + getuy_zonareparto_filtro3() + ")";
					}
				}
			}
			
			// Filtro de docstatus depende de tipo de accion seleccionada por el usuario
			if (this.getuy_docaction_update().equalsIgnoreCase(DocumentEngine.ACTION_Void))
				whereDocStatus = " AND hdr.docstatus='CO' ";
			else if (this.getuy_docaction_update().equalsIgnoreCase(DocumentEngine.ACTION_Complete))
				whereDocStatus = " AND hdr.docstatus IN('DR','IN','IP') ";
			else if (this.getuy_docaction_update().equalsIgnoreCase("FE"))
				whereDocStatus = " AND hdr.docstatus='CO' ";

			
			sql = " SELECT hdr.c_bpartner_id, hdr.c_order_id, hdr.dateordered, hdr.c_bpartner_location_id," +
				  " (coalesce(loca.address1,'') || coalesce(loca.address2,'')) as direccion, cliloc.uy_zonareparto_id " +  
				  " FROM c_order hdr " +
				  " INNER JOIN c_bpartner cli ON hdr.c_bpartner_id = cli.c_bpartner_id " +
				  " INNER JOIN c_bpartner_location cliloc ON hdr.c_bpartner_location_id = cliloc.c_bpartner_location_id " +				  
				  " LEFT OUTER JOIN c_location loca ON cliloc.c_location_id = loca.c_location_id " +
				  " WHERE hdr.ad_client_id = ?" +
				  " AND hdr.isactive='Y'" +
				  " AND hdr.issotrx='Y'" +
				  whereDocStatus + whereFiltros + whereZonas + whereProducto;

			log.info(sql);
			
			pstmt = DB.prepareStatement(sql.toString(), null);
			pstmt.setInt(1, this.getAD_Client_ID());
			rs = pstmt.executeQuery();
			
			// Recorro los pedidos ordenados por articulo.
			// Voy asignando por articulo hasta agotar disponible
			while (rs.next())
			{			
				MUpdOrderOrder rOrd = new MUpdOrderOrder(getCtx(), 0, get_TrxName());
				rOrd.setUY_UpdOrder_Filter_ID(this.getUY_UpdOrder_Filter_ID());
				rOrd.setC_Order_ID(rs.getInt("c_order_id"));
				rOrd.setC_BPartner_ID(rs.getInt("c_bpartner_id"));
				rOrd.setC_BPartner_Location_ID(rs.getInt("c_bpartner_location_id"));
				rOrd.setDateOrdered(rs.getTimestamp("DateOrdered"));
				rOrd.setUY_ZonaReparto_ID(rs.getInt("uy_zonareparto_id"));
				rOrd.setuy_procesar(true);
				
				MOrder ordHdr = new MOrder(getCtx(), rs.getInt("c_order_id"), null);
				
				if(this.isChecked()){
					// OpenUp. Nicolas Garcia. 27/10/2011. Issue #821.
					//Selecciono las lineas de esa orden que sean del producto seleccionado.
					MOrderLine[] lines = ordHdr.getLines(" AND m_product_id=" + this.getM_Product_ID() + " AND uy_esbonificcruzada='N'", "");
					//Fin Issue #821
					for(int i=0;lines.length>i;i++){
						
						if( (lines[i].getQtyOrdered().subtract(lines[i].getQtyReserved())).subtract(lines[i].getQtyInvoiced()).compareTo(Env.ZERO )>0 ){
							
							i=lines.length;//Condicion para salir.
							//Si encuentro por lo menos una linea que tenga pendiente entonces la agrego.
							rOrd.saveEx();
						}
						
					}
					
					
				}else{//Si no se hace por total de producto
					BigDecimal netoPendiente = ordHdr.getUY_TotalPendiente();
					netoPendiente = netoPendiente.divide(Env.ONE, 2, RoundingMode.HALF_UP);
					
					rOrd.setneto_pendiente(netoPendiente);					
					
					// Solo guardo los que tienen saldo pendiente
					if (ordHdr.getUY_TotalPendiente().compareTo(Env.ZERO)>0) rOrd.saveEx();
				}
			}
			rs.close();
			pstmt.close();

			// Actualizo atributos de filtros
			this.setDocStatus(DocumentEngine.STATUS_WaitingConfirmation);
			this.setDocAction(DocumentEngine.ACTION_Complete);
			this.set_ValueOfColumn("uy_nrotrx", nroTrans);
			this.saveEx();
			
			log.info("Fin carga Pedidos de Venta para Actualizacion.");
			
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, sql.toString(), e);
			this.processMsg = e.getMessage();
			result = false;
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		return result;
	}

	
	/**
	 * OpenUp.	
	 * Descripcion : Actualiza pedidos de venta seleccionados por el usuario.
	 * @return
	 * @author  Gabriel Vila 
	 * Fecha : 04/03/2011
	 */
	private boolean updateOrders() {
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		String trxAux=Trx.createTrxName();
		Trx trans = Trx.get(trxAux, true);
		boolean result = true;

		try{
			sql = "SELECT ord.* " +
				  " FROM UY_UpdOrder_Order ord " +
				  " WHERE UY_UpdOrder_Filter_ID =? " +
				  " AND ord.uy_procesar = 'Y'";
			
			pstmt = DB.prepareStatement (sql, trxAux);
			pstmt.setInt(1, this.getUY_UpdOrder_Filter_ID());
			rs = pstmt.executeQuery ();
			/**
			 * Nicolas Garcia OpenUp
			 * 17/3/2011, 16/06/2011
			 * 17/3/2011 Se modifica proceso para discriminar por "FE" (Nueva Fecha)
			 * 17/3/2011 Con esto el usuario podra hacer una modificacion masiva a la fecha de entrega de las ordenes
			 * 16/06/2011 Se reacomoda para Issue 682 Borrar linea que contenga un producto X.
			 */
			
			
			
			
			while (rs.next()){
				int cOrderID = rs.getInt("C_Order_ID");

				MOrder orderHdr = new MOrder(getCtx(), cOrderID, trxAux);
			
				
				String actionDescription = "COMPLETADO Automaticamente por Proceso de Actualizacion Masiva de Pedidos.";
				
				//Si se desea Cancelar el pedido o una linea lineas
				if (this.getuy_docaction_update().equalsIgnoreCase(DocAction.ACTION_Void)){
					
					//Pregunto si solo desea borrar las lineas que contega el producto.
					if (this.isChecked()){//Solo lineas
						// OpenUp. Nicolas Garcia. 27/10/2011. Issue #821.
						//Traigo las lineas que tienen ese producto y que no son bonificacion crusadas, teoricamente tendria que ser una.
						MOrderLine[] lines = orderHdr.getLines(" AND m_product_id=" + this.getM_Product_ID() + " AND uy_esbonificcruzada='N'", "");
						//Fin Issue #821		
						if(lines.length>0){
							lines[0].cancelarLinea();
							actionDescription = "**** Linea (producto:"+lines[0].getM_Product().getValue()+") ANULADA Automaticamente por Proceso de Actualizacion Masiva de Pedidos. *****";
						}
						
					}else{//Este caso es cuando se cancela el pedido
						// Actualizo pedido ejecutando DocAction indicada por el usuario.
						if (orderHdr.processIt(this.getuy_docaction_update())){
							actionDescription = "**** ANULADO Automaticamente por Proceso de Actualizacion Masiva de Pedidos. *****";
						
						}
						else{
							result = false;
							throw new Exception("No se pudo Actualizar Pedido : " + orderHdr.getDocumentNo() + ", " + orderHdr.getProcessMsg());
						}						
					}					
				}
				
				//Caso en que se seleccione cambio de Fecha.
				if (this.getuy_docaction_update().equalsIgnoreCase("FE")){

					actionDescription="Fecha Entrega modificada por Proceso de Actualizacion Masiva de Pedidos.";
					orderHdr.setDatePromised(this.getUY_NuevaFechaEntrega());
					
					// Reactivo para que quede pendiente de verificacion crediticia
					if (!orderHdr.reActivateIt()) {
						result = false;
						throw new Exception("No se pudo Reactivar Pedido : " + orderHdr.getDocumentNo() + ", " + orderHdr.getProcessMsg());
					}
					
					// Completo pedido
					if (!orderHdr.processIt(DocumentEngine.ACTION_Complete)) {
						orderHdr.setDocStatus(DOCSTATUS_Invalid);
						orderHdr.setDocAction(DocumentEngine.ACTION_Complete);
						orderHdr.setProcessed(false);
					}
					
				}
				

				//Cualquiera sea el resultado, guardo.
				orderHdr.setDescriptionAdd(actionDescription);//Acumulo Mensajes.
				orderHdr.saveEx(trxAux);
				
			}//Fin While rs
			
			// Marco proceso como completado;			
			this.setDocAction(DocumentEngine.ACTION_Close);
			this.setDocStatus(DocumentEngine.STATUS_Completed);
			this.setProcessed(true);
			this.saveEx(trxAux);
			
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
			this.processMsg = e.getMessage();
			result = false;
		}
		finally
		{
			trans.close();
			rs = null; pstmt = null;
		}

		return result;
	}

	@Override
	public boolean applyIt() {
		// TODO Auto-generated method stub
		return false;
	}

}
