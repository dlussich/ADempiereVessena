/**
 * 
 */
package org.openup.process;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.logging.Level;

import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

/**
 * @author Nicolas
 *
 */
public class RTRExpirationTruck extends SvrProcess {
	
	private int truck_ID = 0;
	private int user_ID = 0;
	private Timestamp fechaDesde = null;
	private Timestamp fechaHasta = null;
	
	private static final String TABLA_MOLDE = "UY_Molde_ExpirationTruck";

	/**
	 * 
	 */
	public RTRExpirationTruck() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		
		// Obtengo parametros y los recorro
				ProcessInfoParameter[] para = getParameter();
				for (int i = 0; i < para.length; i++) {
					String name = para[i].getParameterName().trim();
					if (name != null) {

						if (name.equalsIgnoreCase("UY_TR_Truck_ID")) {
							if (para[i].getParameter() != null) {
								this.truck_ID = ((BigDecimal) para[i].getParameter())
										.intValueExact();
							}
						}
						if (name.equalsIgnoreCase("AD_User_ID")) {
							if (para[i].getParameter() != null) {
								this.user_ID = ((BigDecimal) para[i].getParameter())
										.intValueExact();
							}
						}
						if (name.equalsIgnoreCase("DueDate")) {
						
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

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		deleteInstanciasViejasReporte();
		loadModelTable();
		return "ok";
	}
	
	private void deleteInstanciasViejasReporte() {

		String sql = "";
		try {

			sql = "DELETE FROM " + TABLA_MOLDE + " WHERE ad_user_id = "
					+ this.user_ID;

			DB.executeUpdate(sql, null);
		} catch (Exception e) {
			log.log(Level.SEVERE, sql, e);
		}
	}
	
	private String loadModelTable() {
		
		String where = "";
		
		try {
			
			where = " between '" + this.fechaDesde + "' and '" + this.fechaHasta + "'";
			
			if(this.truck_ID > 0) where += " and t.uy_tr_truck_id = " + this.truck_ID;			
			
			this.loadSuctaDate(where);
			this.loadMTOPDate(where);
			this.loadSeguroDate(where);			
			
		} catch (Exception e) {
			return e.getMessage();
		}
		return "OK";

	}
	
	private void loadSuctaDate(String where) {

		String insert = "", sql = "";
		
		try {
			
			insert = "INSERT INTO " + TABLA_MOLDE + " (ad_client_id,ad_org_id,ad_user_id,uy_tr_truck_id,concepto,duedate) ";
			
			sql = "select t.ad_client_id, t.ad_org_id," + this.user_ID + ", t.uy_tr_truck_id, 'SUCTA', t.suctadate" +
                  " from uy_tr_truck t" +
                  " where t.suctadate"  + where;
			
			log.log(Level.INFO, insert + sql);
			DB.executeUpdate(insert + sql, null);
			
			
		} catch (Exception e) {
			log.log(Level.SEVERE, insert + sql, e);
			
		}		

	}
	
	private void loadMTOPDate(String where) {

		String insert = "", sql = "";
		
		try {
			
			insert = "INSERT INTO " + TABLA_MOLDE + " (ad_client_id,ad_org_id,ad_user_id,uy_tr_truck_id,concepto,duedate) ";
			
			sql = "select t.ad_client_id, t.ad_org_id," + this.user_ID + ", t.uy_tr_truck_id, 'MTOP', t.mtopdate" +
                  " from uy_tr_truck t" +
                  " where t.mtopdate"  + where;
			
			log.log(Level.INFO, insert + sql);
			DB.executeUpdate(insert + sql, null);
			
			
		} catch (Exception e) {
			log.log(Level.SEVERE, insert + sql, e);
			
		}		

	}
	
	private void loadSeguroDate(String where) {

		String insert = "", sql = "";
		
		try {
			
			insert = "INSERT INTO " + TABLA_MOLDE + " (ad_client_id,ad_org_id,ad_user_id,uy_tr_truck_id,concepto,duedate) ";
			
			sql = "select t.ad_client_id, t.ad_org_id," + this.user_ID + ", t.uy_tr_truck_id, 'SEGURO', i.duedate" +
				  " from uy_tr_truck t" +
				  " inner join uy_tr_truckinsurance i on t.uy_tr_truck_id = i.uy_tr_truck_id" +		
                  " where i.duedate"  + where;
			
			log.log(Level.INFO, insert + sql);
			DB.executeUpdate(insert + sql, null);
			
			
		} catch (Exception e) {
			log.log(Level.SEVERE, insert + sql, e);
			
		}		

	}

}
