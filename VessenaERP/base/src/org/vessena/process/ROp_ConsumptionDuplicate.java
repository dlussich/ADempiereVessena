/**
 * 
 */
package org.openup.process;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.openup.model.MFduConnectionData;

/**
 * OpenUp Ltda. v2.5.1. 08/01/2013
 * @author gbrust
 * 
 */
public class ROp_ConsumptionDuplicate extends SvrProcess {

	private static String TABLA_MOLDE = "UY_Molde_Fdu_ConsumptionDuplicate";

	private Timestamp fechaDesde;
	private Timestamp fechaHasta;
	
	private int adClientID = 0;
	private int adOrgID = 0;
	private int adUserID = 0;
	private String idReporte = "";

	/**
	 * 
	 */
	public ROp_ConsumptionDuplicate() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		// Obtengo parametros y los recorro
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++) {
			String name = para[i].getParameterName().trim();
			if (name != null) {
				if (name.equalsIgnoreCase("DateTrx")){
					this.fechaDesde = (Timestamp)para[i].getParameter();	
					this.fechaHasta = (Timestamp)para[i].getParameter_To();	
				}				
				if (name.equalsIgnoreCase("AD_User_ID")) {
					this.adUserID = ((BigDecimal) para[i].getParameter())
							.intValueExact();
				}
				if (name.equalsIgnoreCase("AD_Client_ID")) {
					this.adClientID = ((BigDecimal) para[i].getParameter())
							.intValueExact();
				}
				if (name.equalsIgnoreCase("AD_Org_ID")) {
					this.adOrgID = ((BigDecimal) para[i].getParameter())
							.intValueExact();
				}
			}
		}
		// Obtengo id para este reporte
		this.idReporte = UtilReportes.getReportID(new Long(this.adUserID));

	}

	private Connection getConnection() throws Exception {

		Connection retorno = null;

		String connectString = ""; 

		try {

			MFduConnectionData conn = new MFduConnectionData(getCtx(), 1000010, get_TrxName());

			if (conn != null) {

				connectString = "jdbc:sqlserver://" + conn.getserver_ip()
						+ "\\" + conn.getServer() + ";databaseName="
						+ conn.getdatabase_name() + ";user="
						+ conn.getuser_db() + ";password="
						+ conn.getpassword_db();

				retorno = DriverManager.getConnection(connectString,
						conn.getuser_db(), conn.getpassword_db());
			}

		} catch (Exception e) {
			throw e;
		}

		return retorno;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		
		//Delete de reportes anteriores de este usuario para ir limpiando la tabla molde
		this.deleteInstanciasViejasReporte();
								
		//Obtengo y cargo en tabla molde, los movimientos segun filtro indicado por el usuario.		
		this.loadMovimientos();	
						
		return "ok";
	}
	
	/* Elimino registros de instancias anteriores del reporte para el usuario actual.*/
	private void deleteInstanciasViejasReporte(){
		
		String sql = "";
		try{
			
			sql = "DELETE FROM " + TABLA_MOLDE +
				  " WHERE AD_User_ID =" + this.adUserID;
			
			DB.executeUpdateEx(sql,null);
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
			throw new AdempiereException(e);
		}
	}
	
	
	/**
	 * OpenUp.	
	 * Descripcion : Carga movimientos de cuentas en la tabla molde.
	 * @author  Guillermo Brust
	 * Fecha : 08/01/2013
	 */
	private void loadMovimientos(){
		
		Connection con = null;
		ResultSet rs = null;
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-dd-MM 00:00:00");
		
		try {
			
			con = this.getConnection();
			Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			
			String sql = "SELECT DISTINCT MDFecOpe AS Fecha_Operacion, MDFecCta AS Fecha_Cierre, MDNroCta AS Cuenta, MDNroCom AS Comercio, MDNroCup AS Cupon, MDCuoVig AS Cuota_Vigente, MDImpTot AS Importe, COUNT(MDFecCta) AS Veces_repetidas" +
						 " FROM FinancialPro.dbo.MOVCTAD" +
						 " WHERE MDFecCta BETWEEN '" + df.format(this.fechaDesde) + "' AND '" + df.format(this.fechaHasta) + "'" + 
						 " AND MDDebCre = '1'" +
						 " GROUP BY MDFecOpe, MDFecCta, MDNroCta, MDNroCom, MDNroCup, MDCuoVig, MDImpTot" + 
						 " HAVING COUNT(MDNroCta) > 1";			
			
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
											
				String values = "'" + rs.getTimestamp("Fecha_Cierre") + "','" + rs.getString("Cuenta") + "','" + rs.getString("Comercio") + "','" + rs.getString("Cupon") + "','" +
								rs.getString("Cuota_Vigente") + "'," + rs.getBigDecimal("Importe")  + "," + rs.getInt("Veces_repetidas") +  "," + this.adUserID + "," + this.adOrgID + "," + this.adClientID + ",'" + this.idReporte + "'";
								
				
				this.recordData(values);			
			}
			rs.close();
			con.close();	
			
		} catch (Exception e) {
			throw new AdempiereException(e);
		} 
		finally {
			
			if (con != null){
				try {
					if (rs != null){
						if (!rs.isClosed()) rs.close();
					}
					if (!con.isClosed()) con.close();
				} catch (SQLException e) {
					throw new AdempiereException(e);
				}
			}
		}
	}
	
	private void recordData(String values){

		String insert = "";		
		
		try{			
			insert = "INSERT INTO " + TABLA_MOLDE + " VALUES(" + values + ")";			
			
			log.log(Level.INFO, insert);
			DB.executeUpdateEx(insert, null);			
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, insert, e);
			throw new AdempiereException(e);
		}
	}

}
