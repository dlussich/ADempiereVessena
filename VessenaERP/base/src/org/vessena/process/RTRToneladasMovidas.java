/**
 * 
 */
package org.openup.process;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.openup.model.MCiudad;
import org.openup.model.MTRBorder;
import org.openup.model.MTRLoadMonitor;
import org.openup.model.MTRLoadMonitorLine;
import org.openup.model.MTRTrip;

/**
 * @author Nicolás
 *
 */
public class RTRToneladasMovidas extends SvrProcess {
	
	private Timestamp fechaDesde = null;
	private Timestamp fechaHasta = null;
	private String vehiculoPropio = "";
	private String tipo = "";
	private String tramo = "";
	
	private int adUserID = 0;
	private int adClientID = 0;
	private int adOrgID = 0;
	
	private String idReporte = "";
	private static final String TABLA_MOLDE = "UY_Molde_Trafico";
	
	private static final String IMPORTACION = "IMP";
	private static final String EXPORTACION = "EXP";
	
	private static final String VEHICULO_PROPIO = "Y";
	private static final String VEHICULO_CONTRATADO = "N";
	
	private static final String TERRITORIO_NACIONAL = "TN";
	private static final String TERRITORIO_EXTRANJERO = "TE";

	/**
	 * 
	 */
	public RTRToneladasMovidas() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		
		// Obtengo parametros y los recorro
				ProcessInfoParameter[] para = getParameter();
				for (int i = 0; i < para.length; i++)
				{
					String name = para[i].getParameterName().trim();
					if (name!= null){

						if (name.equalsIgnoreCase("DateTrx")){
							this.fechaDesde = (Timestamp)para[i].getParameter();
							this.fechaHasta = (Timestamp)para[i].getParameter_To();
						}

						if (name.equalsIgnoreCase("tipo")){
							if (para[i].getParameter()!=null)
								this.tipo = (String)para[i].getParameter();
						}
						
						if (name.equalsIgnoreCase("tramo")){
							if (para[i].getParameter()!=null)
								this.tramo = (String)para[i].getParameter();
						}
						
						if (name.equalsIgnoreCase("IsOwn")){
							if (para[i].getParameter()!=null)
								this.vehiculoPropio = (String)para[i].getParameter();
						}

						if (name.equalsIgnoreCase("AD_User_ID")){
							this.adUserID = ((BigDecimal)para[i].getParameter()).intValueExact();
						}

						if (name.equalsIgnoreCase("AD_Client_ID")){
							this.adClientID = ((BigDecimal)para[i].getParameter()).intValueExact();
						}

						if (name.equalsIgnoreCase("AD_Org_ID")){
							this.adOrgID = ((BigDecimal)para[i].getParameter()).intValueExact();
						}				
					}
				}

				// Obtengo id para este reporte
				this.idReporte = UtilReportes.getReportID(new Long(this.adUserID));

	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {

		// Delete de reportes anteriores de este usuario para ir limpiando la tabla molde
		this.deleteInstanciasViejasReporte();
		
		// Obtengo y cargo en tabla molde segun filtros
		this.loadData();
		
		//Actualizo datos
		this.updateData();
		
		return "ok";
	}
	
	/* Elimino registros de instancias anteriores del reporte para el usuario actual.*/
	private void deleteInstanciasViejasReporte(){
		
		String sql = "";
		try{
			
			sql = "DELETE FROM " + TABLA_MOLDE +
				  " WHERE ad_user_id = " + this.adUserID;
			
			DB.executeUpdateEx(sql,null);
		}
		catch (Exception e)
		{
			throw new AdempiereException(e);
		}
	}
	
	private void loadData(){

		String insert = "", sql = "";
		String whereFiltros = " where m.ad_client_id = " + this.adClientID + " and m.ad_org_id = " + this.adOrgID + 
				" and s.value = 'cargado' and m.docstatus = 'CO'";
		String orderBy = " order by m.datetrx desc";
		
		try {
			
			if(this.fechaDesde!=null) whereFiltros += " and m.datetrx >= '" + this.fechaDesde + "'";
			if(this.fechaHasta!=null) whereFiltros += " and m.datetrx <= '" + this.fechaHasta + "'";
			
			if(this.tipo.equalsIgnoreCase(IMPORTACION)) {

				whereFiltros += " and tp.triptype = 'IMPORTACION' ";
 
			} else if(this.tipo.equalsIgnoreCase(EXPORTACION)) whereFiltros += " and tp.triptype = 'EXPORTACION' ";

			if(this.vehiculoPropio.equalsIgnoreCase(VEHICULO_PROPIO)) {

				whereFiltros += " and t.isown = 'Y' ";

			} else if(this.vehiculoPropio.equalsIgnoreCase(VEHICULO_CONTRATADO)) whereFiltros += " and t.isown = 'N' ";
				
			insert = "INSERT INTO " + TABLA_MOLDE + " (ad_org_id, ad_client_id, ad_user_id, idreporte, fecreporte, uy_tr_truck_id, truckno, uy_tr_trip_id, " + 
					" datetrx, uy_tr_loadmonitor_id, uy_tr_loadmonitorline_id, uy_tr_transorder_id, c_bpartner_id, qtypackage, volume, m_product_id, uy_ciudad_id, uy_ciudad_id_1, isown, triptype)";
			
			sql = "select m.ad_org_id, m.ad_client_id," + this.adUserID + ",'" + this.idReporte + "', current_date, t.uy_tr_truck_id, t.truckno," +
					" l.uy_tr_trip_id, m.datetrx, m.uy_tr_loadmonitor_id, uy_tr_loadmonitorline_id, m.uy_tr_transorder_id, t.c_bpartner_id_p::numeric, l.qtypackage," +
					" l.volume, tp.m_product_id, m.uy_ciudad_id, m.uy_ciudad_id_1, t.isown, tp.triptype" +
					" from uy_tr_loadmonitor m" +
					" inner join uy_tr_loadmonitorline l on m.uy_tr_loadmonitor_id = l.uy_tr_loadmonitor_id" +
					" inner join uy_tr_truck t on m.tractor_id = t.uy_tr_truck_id" +
					" inner join uy_tr_loadstatus s on m.uy_tr_loadstatus_id = s.uy_tr_loadstatus_id" +
					" inner join uy_tr_trip tp on l.uy_tr_trip_id = tp.uy_tr_trip_id" + whereFiltros + orderBy;
			
			DB.executeUpdateEx(insert + sql, null);			

		} catch (Exception e) {
			
			throw new AdempiereException(e.getMessage());
		}
	}
	
	private void updateData(){

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;	
		BigDecimal divisor = new BigDecimal(1000);

		try{

			sql = "select * from " + TABLA_MOLDE + " where idreporte = '" + this.idReporte + "'";

			pstmt = DB.prepareStatement (sql, null);
			rs = pstmt.executeQuery ();

			while (rs.next()){

				MCiudad origen = new MCiudad(getCtx(), rs.getInt("uy_ciudad_id"), get_TrxName());
				MCiudad destino = new MCiudad(getCtx(), rs.getInt("uy_ciudad_id_1"), get_TrxName());
				MTRTrip trip = new MTRTrip(getCtx(), rs.getInt("uy_tr_trip_id"), get_TrxName());
				MTRLoadMonitor monitor = new MTRLoadMonitor(getCtx(), rs.getInt("uy_tr_loadmonitor_id"), get_TrxName());
				MTRLoadMonitorLine line = new MTRLoadMonitorLine(getCtx(), rs.getInt("uy_tr_loadmonitorline_id"), get_TrxName());

				MTRBorder border = null;
				boolean delete = false;

				if(this.tramo.equalsIgnoreCase(TERRITORIO_NACIONAL)){

					if(this.tipo.equalsIgnoreCase(IMPORTACION)) {	

						border = MTRBorder.forName(getCtx(), destino.getName(), get_TrxName());

						if(border!=null && origen.getC_Country_ID()!=336){

							delete = true;

						} else {							
							
							DB.executeUpdateEx("update " + TABLA_MOLDE + " set weight = " + line.getWeight2().divide(divisor, RoundingMode.HALF_UP) + 
									" where idreporte = '" + this.idReporte + "' and uy_tr_trip_id = " + trip.get_ID() +
									" and uy_tr_loadmonitor_id = " + monitor.get_ID() + " and uy_ciudad_id = " + origen.get_ID() + 
									" and uy_ciudad_id_1 = " + destino.get_ID(), get_TrxName());						
							
						}

					} else if(this.tipo.equalsIgnoreCase(EXPORTACION)){

						border = MTRBorder.forName(getCtx(), origen.getName(), get_TrxName());

						if(border!=null && destino.getC_Country_ID()!=336){

							delete = true;

						} else {
							
							DB.executeUpdateEx("update " + TABLA_MOLDE + " set weight = " + line.getWeight2().divide(divisor, RoundingMode.HALF_UP) + 
									" where idreporte = '" + this.idReporte + "' and uy_tr_trip_id = " + trip.get_ID() +
									" and uy_tr_loadmonitor_id = " + monitor.get_ID() + " and uy_ciudad_id = " + origen.get_ID() + 
									" and uy_ciudad_id_1 = " + destino.get_ID(), get_TrxName());							
							
						}				
					}


				} else if (this.tramo.equalsIgnoreCase(TERRITORIO_EXTRANJERO)){

					if(this.tipo.equalsIgnoreCase(IMPORTACION)) {

						border = MTRBorder.forName(getCtx(), origen.getName(), get_TrxName());

						if(border!=null && destino.getC_Country_ID()==336){

							delete = true;

						} else {
							
							DB.executeUpdateEx("update " + TABLA_MOLDE + " set weight2 = " + line.getWeight2().divide(divisor, RoundingMode.HALF_UP) + 
									" where idreporte = '" + this.idReporte + "' and uy_tr_trip_id = " + trip.get_ID() +
									" and uy_tr_loadmonitor_id = " + monitor.get_ID() + " and uy_ciudad_id = " + origen.get_ID() + 
									" and uy_ciudad_id_1 = " + destino.get_ID(), get_TrxName());						
							
						}
						

					} else if(this.tipo.equalsIgnoreCase(EXPORTACION)){

						border = MTRBorder.forName(getCtx(), destino.getName(), get_TrxName());

						if(border!=null && origen.getC_Country_ID()==336){

							delete = true;

						} else {
							
							DB.executeUpdateEx("update " + TABLA_MOLDE + " set weight2 = " + line.getWeight2().divide(divisor, RoundingMode.HALF_UP) + 
									" where idreporte = '" + this.idReporte + "' and uy_tr_trip_id = " + trip.get_ID() +
									" and uy_tr_loadmonitor_id = " + monitor.get_ID() + " and uy_ciudad_id = " + origen.get_ID() + 
									" and uy_ciudad_id_1 = " + destino.get_ID(), get_TrxName());							
							
						}

					}			

				}
				
				if(delete){
					
					DB.executeUpdateEx("delete from " + TABLA_MOLDE + " where idreporte = '" + this.idReporte + "' and uy_tr_trip_id = " + trip.get_ID() +
							" and uy_tr_loadmonitor_id = " + monitor.get_ID() + " and uy_ciudad_id = " + origen.get_ID() + 
							" and uy_ciudad_id_1 = " + destino.get_ID() + " and uy_tr_loadmonitorline_id = " + line.get_ID(), get_TrxName());
					
				} 

			}		

		} catch (Exception e) {

			throw new AdempiereException(e.getMessage());

		} finally {
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}		

	}

}
