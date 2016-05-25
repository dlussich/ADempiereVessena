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
import org.openup.model.MReservaPedidoHdr;


public class RLogReservesNotAsigned extends SvrProcess {
	private int salesRep_ID = 0;
	private String zonaFiltro = "";
	private Timestamp fechaDesde=null;
	private Timestamp fechaHasta=null;
	private int ad_user_id = 0;
	private static final String TABLA_MOLDE = "UY_Molde_Reservasinasignar";

	@Override
	protected String doIt() throws Exception {
		deleteInstanciasViejasReporte();
		loadModelTable();
		updateParametrosIDZonas();
		return "ok";
	}

	/**
	 * 
	 * OpenUp.	
	 * Descripcion : Obtengo y cargo en tabla molde, las reservas sin asignar segun filtros indicados por el usuario.
	 * @return
	 * @author  Nicolas Sarlabos
	 * Fecha : 13/06/2011
	 */
	private String loadModelTable() {

		String insert = "", sql = "";
		
		try {
			insert = "INSERT INTO " + TABLA_MOLDE + " (ad_user_id,ad_client_id,ad_org_id,uy_reservapedidohdr_id,c_bpartner_id,c_bpartner_location_id,direccion,uy_zonareparto_id,bultos,dateordered,datereserved,salesrep_id,uy_zonareparto_id1," +
					"uy_zonareparto_id2,uy_zonareparto_id3,uy_zonareparto_id4,uy_zonareparto_id5, c_order_id) ";

			sql = "select "+this.ad_user_id+", uy_reservapedidohdr.ad_client_id, uy_reservapedidohdr.ad_org_id, uy_reservapedidohdr.uy_reservapedidohdr_id, uy_reservapedidohdr.c_bpartner_id, c_order.c_bpartner_location_id,"
					+ " (coalesce(c_location.city,'') || ' - ' || coalesce(c_location.address1,'') || ' ' || coalesce(c_location.address2,'') ) as direccion,"
					+ " c_bpartner_location.uy_zonareparto_id, "
					+ " (select coalesce(sum(qtyentered),0) from uy_reservapedidoline"
					+ " where uy_reservapedidoline.uy_reservapedidohdr_id = uy_reservapedidohdr.uy_reservapedidohdr_id"
					+ " and c_uom_id <>100) as bultos,"
					+ " c_order.dateordered, uy_reservapedidohdr.datereserved,c_order.salesrep_id,0,0,0,0,0, c_order.c_order_id "
					+ " from uy_reservapedidohdr "
					+ " inner join c_order on uy_reservapedidohdr.c_order_id = c_order.c_order_id"
					+ " inner join c_bpartner_location on c_order.c_bpartner_location_id = c_bpartner_location.c_bpartner_location_id"
					+ " inner join c_location on c_bpartner_location.c_location_id = c_location.c_location_id"
					+ " where uy_reservapedidohdr.docstatus='CO' "
					+ " and not exists (select * from uy_asignatransporteline where uy_reservapedidohdr_id = uy_reservapedidohdr.uy_reservapedidohdr_id) " 
					+ filtros();

			log.log(Level.INFO, insert + sql);
			DB.executeUpdate(insert + sql, null);
			updateTable(); //OpenUp Nicolas Sarlabos issue #891 19/10/2011

		} catch (Exception e) {
			log.log(Level.SEVERE, insert + sql, e);
			return e.getMessage();
		}
		return "OK";

	}
	
	/**
	 * 
	 * OpenUp.	
	 * Descripcion : Se crea el string de filtros
	 * @return
	 * @author  Nsicolas Sarlabos 
	 * Fecha : 13/06/2011
	 */

	private String filtros() {
		String salida="";
		// Si tengo parametro para vendedor
		if(this.salesRep_ID>0){
			salida +=" AND c_order.salesrep_id="+this.salesRep_ID;
		}
		// Si tengo parametros para zonas 
		if(!this.zonaFiltro.equals("")){
			salida +=" AND c_bpartner_location.uy_zonareparto_id in ("+this.zonaFiltro+")";
		}
		// Si tengo parametro para fecha "desde"
		if(fechaDesde !=null){
			salida +=" AND uy_reservapedidohdr.datereserved>='"+ this.fechaDesde+"'";
		}
		// Si tengo parametro para fecha "hasta"
		if(fechaHasta !=null){
			salida +=" AND uy_reservapedidohdr.datereserved<='"+ this.fechaHasta+"'";
		}
		return salida;
	}
	
	/**
	 * 
	 * OpenUp.  issue #891	
	 * Descripcion : Método que realiza el calculo de importe para cada reserva y lo inserta en la tabla molde
	 * @author  Nicolas Sarlabos 
	 * Fecha : 19/10/2011
	 */
	private void updateTable() {
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
				
		try{
						
			sql = "SELECT uy_reservapedidohdr_id" +
				  " FROM " + TABLA_MOLDE +
				  " WHERE ad_user_id=" + this.ad_user_id;
					
			pstmt = DB.prepareStatement (sql, get_TrxName());
			rs = pstmt.executeQuery ();

			while (rs.next()){
				String sql2="";
								
				MReservaPedidoHdr mreserva = new MReservaPedidoHdr(Env.getCtx(), rs.getInt("uy_reservapedidohdr_id"), null);
				
				BigDecimal total = mreserva.getTotalLines();
								
				sql2+="UPDATE "+TABLA_MOLDE +" SET amount=" + total + 
				" WHERE uy_reservapedidohdr_id=" + rs.getInt("uy_reservapedidohdr_id") + " AND ad_user_id=" + this.ad_user_id;
							
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

	@Override
	protected void prepare() {

		// Obtengo parametros y los recorro
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++) {
			String name = para[i].getParameterName().trim();
			if (name != null) {

				if (name.equalsIgnoreCase("SalesRep_ID")) {
					if (para[i].getParameter() != null) {
						this.salesRep_ID = ((BigDecimal) para[i].getParameter())
								.intValueExact();
					}
				}
				if (name.equalsIgnoreCase("AD_User_ID")) {
					if (para[i].getParameter() != null) {
						this.ad_user_id = ((BigDecimal) para[i].getParameter())
								.intValueExact();
					}
				}
				if (name.equalsIgnoreCase("uy_zonareparto_id1")) {
					if (para[i].getParameter() != null) {
						if (!zonaFiltro.equals("")) {
							zonaFiltro += ",";
						}
						this.zonaFiltro += ((BigDecimal) para[i].getParameter())
								.intValueExact();
					}
				}
				if (name.equalsIgnoreCase("uy_zonareparto_id2")) {
					if (para[i].getParameter() != null) {
						if (!zonaFiltro.equals("")) {
							zonaFiltro += ",";
						}
						this.zonaFiltro += ((BigDecimal) para[i].getParameter())
								.intValueExact();
					}
				}
				if (name.equalsIgnoreCase("uy_zonareparto_id3")) {
					if (para[i].getParameter() != null) {
						if (!zonaFiltro.equals("")) {
							zonaFiltro += ",";
						}
						this.zonaFiltro += ((BigDecimal) para[i].getParameter())
								.intValueExact();
					}
				}
				if (name.equalsIgnoreCase("uy_zonareparto_id4")) {
					if (para[i].getParameter() != null) {
						if (!zonaFiltro.equals("")) {
							zonaFiltro += ",";
						}
						this.zonaFiltro += ((BigDecimal) para[i].getParameter())
								.intValueExact();
					}
				}
				if (name.equalsIgnoreCase("uy_zonareparto_id5")) {
					if (para[i].getParameter() != null) {
						if (!zonaFiltro.equals("")) {
							zonaFiltro += ",";
						}
						this.zonaFiltro += ((BigDecimal) para[i].getParameter())
								.intValueExact();
					}
				}
				if (name.equalsIgnoreCase("datereserved")) {
				
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

	/* Elimino registros de instancias anteriores del reporte para el usuario actual.*/
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
	
	
	/**
	 * 
	 * OpenUp.	
	 * Descripcion : Vuelve a dejar en "0" los ID de las zonas de reparto
	 * @author  Nicolas Sarlabos
	 * Fecha : 13/06/2011
	 */
private void updateParametrosIDZonas() {
		
		String action = "UPDATE AD_PInstance_Para SET p_number = 0" +
						" WHERE AD_PInstance_ID = " + this.getAD_PInstance_ID() +
						" AND lower(ParameterName) IN ('uy_zonareparto_id1','uy_zonareparto_id2','uy_zonareparto_id3','uy_zonareparto_id4','uy_zonareparto_id5')";
		
		DB.executeUpdate(action,null);
	}
}