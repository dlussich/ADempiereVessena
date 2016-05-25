package org.openup.process;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;

public class RProdPPOrderEfficiency extends SvrProcess{
	
	private int c_doctype_id = 0;
	private int m_product_id = 0;
	private Timestamp fechaDesde=null;
	private Timestamp fechaHasta=null;
	private Timestamp fechaConfDesde=null;
	private Timestamp fechaConfHasta=null;
	private int pp_order_id = 0;
	private int ad_user_id = 0;
	private static final String TABLA_MOLDE = "UY_Molde_PPOrderEfficiency";

	@Override
	protected String doIt() throws Exception {
		deleteInstanciasViejasReporte();
		loadModelTable();
		return "ok";
	}
	
	/**
	 * 
	 * OpenUp.	#121
	 * Descripcion : Elimino registros de instancias anteriores del reporte para el usuario actual.
	 * @return
	 * @author  Nicolas Sarlabos
	 * Fecha : 24/10/2011
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
				//OpenUp. Nicolas Sarlabos. 10/12/2012. Agrego filtros por fecha de confirmacion.
				if (name.equalsIgnoreCase("datetrx")) {
					
					if (para[i].getParameter()!=null){
						this.fechaConfDesde =  (Timestamp) para[i].getParameter();
					}
						
					if (para[i].getParameter_To()!=null){
						this.fechaConfHasta =  (Timestamp) para[i].getParameter_To();
					}
				}
				//Fin OpenUp.
			}

		}
		
	}
	
	/**
	 * 
	 * OpenUp.	#121
	 * Descripcion : Obtengo y cargo datos en la tabla molde
	 * @return
	 * @author  Nicolas Sarlabos
	 * Fecha : 24/10/2011
	 */
	private String loadModelTable() {

		String insert = "", sql = "";
		
		try {
			
			//OpenUp. Nicolas Sarlabos. 10/12/2012.Modifico para cargar fecha de confirmacion y numero de turno
			insert = "INSERT INTO " + TABLA_MOLDE + " (ad_user_id,ad_client_id,ad_org_id,pp_order_id,dateordered,datestart,datefinish," +
			         "c_doctype_id,m_product_id,name,c_uom_id,uy_confirmorderhdr_id,uy_cantidad_operarios_dir,uy_unidades_hora,qtydelivered,turno,datetrx,interval_confirm,horas_confirm) ";

			sql = "SELECT *,coalesce(EXTRACT(epoch FROM interval_confirm)/3600,0) as horas_confirm " +
				  " FROM (SELECT *,(datefinish - datestart) as interval_confirm" + 
				  " FROM (SELECT " + this.ad_user_id + ",pp.ad_client_id,pp.ad_org_id,pp.pp_order_id,pp.dateordered,con.datestart,con.datefinish,pp.c_doctypetarget_id," +
			      " pp.m_product_id,prod.name,pp.c_uom_id,con.uy_confirmorderhdr_id,std.uy_cantidad_operarios_dir,std.uy_unidades_hora,con.qtydelivered,con.turno,con.datetrx" +
                  " FROM pp_order pp" +
                  " INNER JOIN m_product prod ON pp.m_product_id=prod.m_product_id" +
                  " INNER JOIN uy_confirmorderhdr con ON pp.pp_order_id=con.pp_order_id" +
                  " INNER JOIN UY_Estandar_Producciones std ON prod.m_product_id=std.m_product_id" +
                  " WHERE pp.docstatus='CL' AND con.docstatus='CO'" + filtros() + ") AS p) AS q";
			//Fin OpenUp.
			
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
	 * OpenUp.	#121
	 * Descripcion : Se crea el string de filtros
	 * @return
	 * @author  Nicolas Sarlabos 
	 * Fecha : 24/10/2011
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
		//OpenUp. Nicolas Sarlabos. 10/12/2012. Agrego filtro de fecha de confirmacion
		// Si tengo parametro para fecha confirmacion "desde"
		if(fechaConfDesde !=null){
			salida +=" AND con.datetrx>='"+ this.fechaConfDesde+"'";
		}
		// Si tengo parametro para fecha confirmacion "hasta"
		if(fechaHasta !=null){
			salida +=" AND con.datetrx<='"+ this.fechaConfHasta+"'";
		}
		//Fin OpenUp.
		return salida;
	}
	
	/**
	 * 
	 * OpenUp. 	#121
	 * Descripcion : se realizan calculos y se actualiza la tabla molde
	 * @author  Nicolas Sarlabos 
	 * Fecha : 24/10/2011
	 */
	
	private void updateTable() {
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;		
		try{
						
			sql = "SELECT ad_user_id,pp_order_id,uy_confirmorderhdr_id,qtydelivered," +
			      " uy_cantidad_operarios_dir,horas_confirm,uy_unidades_hora" +
				  " FROM " + TABLA_MOLDE + 
				  " WHERE ad_user_id =" + this.ad_user_id;
					
			pstmt = DB.prepareStatement (sql, null);
			rs = pstmt.executeQuery ();

			while (rs.next()){
				
				String sql2="";
				BigDecimal real = Env.ZERO;
				BigDecimal teorico = Env.ZERO;
				BigDecimal operarios = Env.ZERO;
				BigDecimal cantxHora = Env.ZERO;
				BigDecimal desvio = Env.ZERO;
				BigDecimal horas_conf = rs.getBigDecimal("horas_confirm");
				
				if(horas_conf.compareTo(Env.ZERO)>0){
					//calculo nº de operarios por linea en la confirmacion de orden
					operarios = (operariosEnConfirmacion(rs.getInt("uy_confirmorderhdr_id"),rs.getInt("pp_order_id"))).divide(horas_conf,2,RoundingMode.HALF_UP);
					//calculo cantidad entregada x hora en la confirmacion de orden
					cantxHora = rs.getBigDecimal("qtydelivered").divide(horas_conf,2,RoundingMode.HALF_UP);
					
					sql2+= " ,operarios=" + operarios + ",cant_hora=" + cantxHora  ;
                    
									
				}
				//calculo rendimiento real
				if(operarios.compareTo(Env.ZERO)>0){ 
					
					real = cantxHora.divide(operarios,2,RoundingMode.HALF_UP);
					
					sql2+=", rend_real=" + real ; 
                   			
				}
				//calculo rendimiento teorico
				BigDecimal std_operarios = rs.getBigDecimal("uy_cantidad_operarios_dir");
				if(std_operarios.compareTo(Env.ZERO)>0){
					
					teorico = rs.getBigDecimal("uy_unidades_hora").divide(std_operarios,2,RoundingMode.HALF_UP);
					
					sql2+=", rend_teorico=" + teorico; 
				}
				//calculo desvio
				if(teorico.compareTo(Env.ZERO)>0){
					
					desvio = (real.divide(teorico,2,RoundingMode.HALF_UP)).multiply(Env.ONEHUNDRED);
					
					sql2+=", desvio=" + desvio ;
				}
						
				if(!sql2.equalsIgnoreCase(""))
					DB.executeUpdate("UPDATE "+TABLA_MOLDE +" SET "+ sql2.substring(2)+" WHERE pp_order_id=" + rs.getInt("pp_order_id") + " AND uy_confirmorderhdr_id=" + rs.getInt("uy_confirmorderhdr_id") + 
                    " AND ad_user_id=" + rs.getInt("ad_user_id"), null);
				
			}

									
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
			throw new AdempiereException(e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
	}
		
	/**
	 * 
	 * OpenUp.	
	 * Descripcion : Calcula cantidad de horas trabajadas de operarios para una confirmacion  
	 * @return
	 * @author  Nicolas Sarlabos 
	 * Fecha : 26/10/2011
	 */
	
	
	public BigDecimal operariosEnConfirmacion(int confID,int ppoID){
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		BigDecimal horas = Env.ZERO;
		
		
		try{
						
			sql = "SELECT datestart,datefinish" +
                  " FROM uy_operarios" +
                  " WHERE uy_confirmorderhdr_id=" + confID + " AND pp_order_id=" + ppoID;
					
			pstmt = DB.prepareStatement (sql, null);
			rs = pstmt.executeQuery ();

			while (rs.next()){
				
				String sql2 = "SELECT workers_hours(?,?) ";
				Timestamp dateFinish = rs.getTimestamp("datefinish");
				Timestamp dateStart = rs.getTimestamp("datestart");
				BigDecimal res = DB.getSQLValueBD(get_TrxName(), sql2, 
								new Object[]{dateFinish,dateStart});
						
				horas = horas.add(res);  //acumulo las horas del operario
							
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
		return horas;
		
		
	}
	
	
	

}
