package org.openup.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.logging.Level;

import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;

public class RProdPPOrderFulfillment extends SvrProcess {
	
	private int c_doctype_id = 0;
	private int m_product_id = 0;
	private Timestamp fechaDesde=null;
	private Timestamp fechaHasta=null;
	private int pp_order_id = 0;
	private int ad_user_id = 0;
	private static final String TABLA_MOLDE = "UY_Molde_PPOrderFulfillment";

	@Override
	protected String doIt() throws Exception {
		deleteInstanciasViejasReporte();
		loadModelTable();
		return "ok";
	}

	/**
	 * 
	 * OpenUp.	#145
	 * Descripcion : Elimino registros de instancias anteriores del reporte para el usuario actual.
	 * @return
	 * @author  Nicolas Sarlabos
	 * Fecha : 20/10/2011
	 */
	private void deleteInstanciasViejasReporte() {

		String sql = "";
		try {

			sql = "DELETE FROM " + TABLA_MOLDE + " WHERE ad_user_id ="
					+ this.ad_user_id;

			DB.executeUpdate(sql, null);
		} catch (Exception e) {
			log.log(Level.SEVERE, sql, e);
		}
	}
	
	@Override
	protected void prepare() {
		// Obtengo parametros y los recorro
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++) {
			String name = para[i].getParameterName().trim();
			if (name != null) {

				if (name.equalsIgnoreCase("PP_Order_ID")) {
					if (para[i].getParameter() != null) {
						this.pp_order_id = ((BigDecimal) para[i].getParameter())
								.intValueExact();
					}
				}
				if (name.equalsIgnoreCase("AD_User_ID")) {
					if (para[i].getParameter() != null) {
						this.ad_user_id = ((BigDecimal) para[i].getParameter())
								.intValueExact();
					}
				}
				if (name.equalsIgnoreCase("C_DocType_ID")) {
					if (para[i].getParameter() != null) {
						this.c_doctype_id = ((BigDecimal) para[i].getParameter())
								.intValueExact();
					}
				}
				if (name.equalsIgnoreCase("M_Product_ID")) {
					if (para[i].getParameter() != null) {
						this.m_product_id = ((BigDecimal) para[i].getParameter())
								.intValueExact();
					}
				}
				if (name.equalsIgnoreCase("dateordered")) {
				
					if (para[i].getParameter()!=null){
						this.fechaDesde =  (Timestamp) para[i].getParameter();
					}
						
					if (para[i].getParameter_To()!=null){
						this.fechaHasta =  (Timestamp) para[i].getParameter_To();
					}
				}
			}

		}
		
	}
	
	/**
	 * 
	 * OpenUp.	#145
	 * Descripcion : Obtengo y cargo datos en la tabla molde
	 * @return
	 * @author  Nicolas Sarlabos
	 * Fecha : 20/10/2011
	 */
	private String loadModelTable() {

		String insert = "", sql = "";
		
		try {
			insert = "INSERT INTO " + TABLA_MOLDE + " (ad_user_id,ad_client_id,ad_org_id,pp_order_id,dateordered,datepromised,docstatus," +
			         "c_doctype_id,m_product_id,name,qtyordered,qtydelivered,c_uom_id,uy_cerroconfirmacion,datetrx,dias) ";

			sql = "SELECT *,(date_part('day',datetrx - datepromised)) as desvio FROM" +
				  " (SELECT "  + this.ad_user_id + ",pp.ad_client_id,pp.ad_org_id,pp.pp_order_id,pp.dateordered,pp.datepromised," +
				  " pp.docstatus,pp.c_doctypetarget_id,pp.m_product_id,prod.name,pp.qtyordered,pp.qtydelivered,pp.c_uom_id,pp.uy_cerroconfirmacion," +
				  " (SELECT MAX(datetrx) FROM uy_confirmorderhdr WHERE pp_order_id=pp.pp_order_id AND docstatus='CO' )as datetrx" +
                  " FROM pp_order pp" +
                  " INNER JOIN m_product prod ON pp.m_product_id=prod.m_product_id" +
                  " WHERE pp.docstatus='CL' and pp.pp_order_id in(SELECT pp_order_id FROM uy_confirmorderhdr)" + filtros() + ") AS p";

			log.log(Level.INFO, insert + sql);
			DB.executeUpdate(insert + sql, null);
			updateTable(); 

		} catch (Exception e) {
			log.log(Level.SEVERE, insert + sql, e);
			return e.getMessage();
		}
		return "OK";

	}

	/**
	 * 
	 * OpenUp.	#145
	 * Descripcion : Se crea el string de filtros
	 * @return
	 * @author  Nicolas Sarlabos 
	 * Fecha : 20/10/2011
	 */

	private String filtros() {
		String salida="";
		// Si tengo parametro para orden
		if(this.pp_order_id>0){
			salida +=" AND pp.pp_order_id="+this.pp_order_id;
		}
		// Si tengo parametros para producto 
		if(this.m_product_id>0){
			salida +=" AND pp.m_product_id="+this.m_product_id;
		}
		// Si tengo parametros para tipo de documento 
		if(this.c_doctype_id>0){
			salida +=" AND pp.c_doctypetarget_id="+this.c_doctype_id;
		}
		// Si tengo parametro para fecha "desde"
		if(fechaDesde !=null){
			salida +=" AND pp.dateordered>='"+ this.fechaDesde+"'";
		}
		// Si tengo parametro para fecha "hasta"
		if(fechaHasta !=null){
			salida +=" AND pp.dateordered<='"+ this.fechaHasta+"'";
		}
		return salida;
	}
	
	/**
	 * 
	 * OpenUp. 	#145
	 * Descripcion : se realizan los calculos y se actualiza la tabla molde
	 * @author  Nicolas Sarlabos 
	 * Fecha : 20/10/2011
	 */
	private void updateTable() {
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
				
		try{
						
			sql = "SELECT ad_user_id,pp_order_id,qtyordered,qtydelivered,datepromised,datetrx" +
				  " FROM " + TABLA_MOLDE +
				  " WHERE ad_user_id =" + this.ad_user_id;
					
			pstmt = DB.prepareStatement (sql, get_TrxName());
			rs = pstmt.executeQuery ();

			while (rs.next()){
				String sql2="";
								
				//calculos para porcentaje completo de la orden			
				BigDecimal qtyordered = rs.getBigDecimal("qtyordered");
				BigDecimal qtydelivered = rs.getBigDecimal("qtydelivered");
				BigDecimal hundred = new BigDecimal(100);
				BigDecimal percentage = Env.ZERO;
				//defensivo para evitar division por cero
				if(qtyordered.compareTo(Env.ZERO)>0) percentage = ((qtydelivered.multiply(hundred)).divide(qtyordered,0, BigDecimal.ROUND_HALF_UP));
				//sql para cargar porcentaje completo de la orden
				sql2+="UPDATE "+TABLA_MOLDE +" SET porcentaje=" + percentage +
                      " WHERE pp_order_id=" + rs.getInt("pp_order_id") + " AND ad_user_id=" + rs.getInt("ad_user_id") + ";";
						
						
				DB.executeUpdate(sql2, null);
				
				}
			
									
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
	}
	
		
}
