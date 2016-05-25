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
import org.openup.model.MFduLogisticMonth;


/**
 * @author gbrust
 *
 */
public class ROpDPMO extends SvrProcess {
	
	private static String TABLA_MOLDE = "UY_Molde_Fdu_Dpmo";
		
	private int ToleranciaPagosRedes = 0;
	private int ToleranciaPagosSuc = 0;
	private int ToleranciaAjustes = 0;
	private String ReportType = "";	
	private Timestamp FechaInicio = null;
	private Timestamp FechaFin = null;
	private int logisticMonthID = 0;	
		
	private int adClientID = 0;
	private int adOrgID = 0;
	private int adUserID = 0;	
	private String idReporte = "";

	/**
	 * 
	 */
	public ROpDPMO() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		
		ProcessInfoParameter[] para = getParameter();

		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName().trim();
			if (name!= null){
					
				if (name.equalsIgnoreCase("ToleranciaPagosRedes")){
					if (para[i].getParameter()!=null){
						this.ToleranciaPagosRedes = ((BigDecimal)para[i].getParameter()).intValueExact();
					}
				}	
				if (name.equalsIgnoreCase("ToleranciaPagosSuc")){
					if (para[i].getParameter()!=null){
						this.ToleranciaPagosSuc = ((BigDecimal)para[i].getParameter()).intValueExact();
					}
				}		
				if (name.equalsIgnoreCase("ToleranciaAjustes")){
					if (para[i].getParameter()!=null){
						this.ToleranciaAjustes = ((BigDecimal)para[i].getParameter()).intValueExact();
					}
				} 				
				if (name.equalsIgnoreCase("ReportType")){
					if (para[i].getParameter()!=null){
						this.ReportType = (String)para[i].getParameter();
					}
				}		
				if (name.equalsIgnoreCase("datetrx")){
					if (para[i].getParameter()!=null){
						this.FechaInicio = (Timestamp)para[i].getParameter();
						this.FechaFin = (Timestamp)para[i].getParameter_To();
					}
				}		
				if (name.equalsIgnoreCase("UY_Fdu_LogisticMonth_ID")){
					if (para[i].getParameter()!=null){
						this.logisticMonthID = ((BigDecimal)para[i].getParameter()).intValueExact();
					}
				}
				if (name.equalsIgnoreCase("AD_User_ID")){
					if (para[i].getParameter()!=null){
						this.adUserID = ((BigDecimal)para[i].getParameter()).intValueExact();
					}					
				}
				if (name.equalsIgnoreCase("AD_Client_ID")){
					if (para[i].getParameter()!=null){
						this.adClientID = ((BigDecimal)para[i].getParameter()).intValueExact();
					}					
				}
				if (name.equalsIgnoreCase("AD_Org_ID")){
					if (para[i].getParameter()!=null){
						this.adOrgID = ((BigDecimal)para[i].getParameter()).intValueExact();
					}					
				}					
			}
		}
		
		// Obtengo id para este reporte
		this.idReporte = UtilReportes.getReportID(new Long(this.adUserID));

	}
	
	private Connection getConnection() throws Exception {
		
		Connection retorno = null;

		String connectString = ""; //"jdbc:sqlserver://192.168.150.175\\FP01;databaseName=FDU;user=adempiere;password=adempiere1144";
		//String user = "adempiere";
		//String password = "adempiere1144";

		try {

			MFduConnectionData conn = MFduConnectionData.forFduFileID(getCtx(), 1000008, null);
			
			if(conn != null){
				
				connectString = "jdbc:sqlserver://" + conn.getserver_ip() + "\\" + conn.getServer() + 
								";databaseName=" + conn.getdatabase_name() + ";user=" + conn.getuser_db() + 
								";password=" + conn.getpassword_db() ;
				
				retorno = DriverManager.getConnection(connectString, conn.getuser_db(), conn.getpassword_db());
			}	
			
		} catch (Exception e) {
			throw e;
		}
		
		return retorno;
	}

	@Override
	protected String doIt() throws Exception {
				
		// Delete de reportes anteriores de este usuario para ir limpiando la tabla molde
		this.deleteInstanciasViejasReporte();
				
		// Obtengo y cargo en tabla molde, los movimientos segun filtro indicado por el usuario.
		if(!this.getSql().equals("")) this.loadMovimientos(this.getSql());
				
		
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
	
	private void loadMovimientos(String sql){
		
		Connection con = null;
		ResultSet rs = null;			
		
		try {
			
			con = this.getConnection();
			Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			
			rs = stmt.executeQuery(sql);
			
			
			
			while (rs.next()) {
																		
				String values = this.adClientID + "," + this.adOrgID + "," + this.adUserID + ",'" + this.idReporte + "'," + 
								"'" + this.FechaInicio + "','" + this.FechaFin + "','" + rs.getString("Accion") + "'," + rs.getInt("CantidadErroneos") + "," + rs.getBigDecimal("DPMO") + "," + rs.getBigDecimal("CantidadTotal");						
						
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
	
	private String getSql(){
		
		String sql = "/*Se toman en cuenta los pagos y ajustes enviados y confirmados que sobre pasan los dias de la tolerancia parametrizada" +
					 " segun sea si agencia de cobranza o sucursal mas los no enviados*/" +
					 " with temp as(" + 
					 " SELECT 'Pagos' as Accion, (COUNT(PgoNro) + (SELECT COUNT(PgoNro)" +
					 "	  										   FROM FinancialPro.dbo.PGOTARJ" + 
					 "											   WHERE PgoGen = '1' /*Enviado*/" +
					 "											   AND PgoConf = 'S'  /*Confirmado*/" + 
					 "											   AND PgoOrigen in ('0') /*Sucursal*/" +
					 "											   AND (DATEPART(DAY, PgoFecConf) - DATEPART(DAY, PgoFecha)) > " + this.ToleranciaPagosSuc +
					 											   this.getWhereFechas("Pagos") + ") + (SELECT COUNT(PgoNro)" +
					 " 																					FROM FinancialPro.dbo.PGOTARJ" + 
					 " 																					WHERE PgoGen = '0' /*No Enviado*/" + 
					 " 																					AND PgoConf <> 'A'  /*Anulado*/	" +	
					 																					this.getWhereFechas("Pagos") + ")) AS CantidadErroneos," + 
					 
					 " (SELECT COUNT(PgoNro)" +
					 " FROM FinancialPro.dbo.PGOTARJ" + 
					 " WHERE PgoConf <> 'A'  /*Anulado*/	" +	
					 this.getWhereFechas("Pagos") + ") AS CantidadTotal" +
					 
					 " FROM FinancialPro.dbo.PGOTARJ" + 
					 " WHERE PgoGen = '1' /*Enviado*/" + 
					 " AND PgoConf = 'S'  /*Confirmado*/" + 
					 " AND PgoOrigen in ('1','2') /*Agencias de cobranzas*/" + 
					 " AND (DATEPART(DAY, PgoFecConf) - DATEPART(DAY, PgoFecha)) > " + this.ToleranciaPagosRedes +
				     this.getWhereFechas("Pagos") +
				     
				     " UNION" +
				     
					 " SELECT 'Ajustes' as Accion, (COUNT(AjuNro) + (SELECT COUNT(AjuNro)" +
					 " 										  		 FROM FinancialPro.dbo.AJUCTACT" + 
					 "										  		 WHERE AjuGen = '0' /*No Enviado*/" + 
					 " 												 AND AjuConf <> 'A'" +
					 												 this.getWhereFechas("Ajustes") + ")) as CantidadErroneos," + 
					 " (SELECT COUNT(AjuNro)" +
					 " FROM FinancialPro.dbo.AJUCTACT" + 
					 " WHERE AjuConf <> 'A'" +
					 this.getWhereFechas("Ajustes") + ") AS CantidadTotal" + 

					 " FROM FinancialPro.dbo.AJUCTACT" + 
					 " WHERE AjuGen = '1' /*Enviado*/" + 
					 " AND AjuConf = 'S'  /*Confirmado*/" + 
					 " and (DATEPART(DAY, AjuFecConf) - DATEPART(DAY, AjuFecha)) > " + this.ToleranciaAjustes +
				     this.getWhereFechas("Ajustes") +
				     ")" +

					 " SELECT t.Accion, t.CantidadErroneos, t.CantidadTotal, (cast(t.CantidadErroneos as float) / cast(t.CantidadTotal as float) * 1000000) as DPMO" +
					 " FROM temp t";		
			
		return sql;		
		
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
	
	private String getWhereFechas(String tabla){
		
		String whereFechas = "";
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-dd-MM 00:00:00");
		
		if(this.ReportType.equals("RF")){ //rango de fechas
			
			if(tabla.equals("Ajustes")){
					whereFechas = " AND AjuFecha between '" + df.format(this.FechaInicio) + "' AND '" + df.format(this.FechaFin) + "'";							
			}else whereFechas = " AND PgoFecha between '" + df.format(this.FechaInicio) + "' AND '" + df.format(this.FechaFin) + "'";	
			
			
		}else if(this.ReportType.equals("ML")){ // mes ligistico
			
			MFduLogisticMonth month = null;
						
			if(this.logisticMonthID > 0){
				month = new MFduLogisticMonth(getCtx(), this.logisticMonthID, get_TrxName());
				this.FechaInicio = month.getFirsDate();
				this.FechaFin = month.getLastDate();
				
			}else{
				throw new AdempiereException("Debe elegir un mes logistico");
			}
						
			if(tabla.equals("Ajustes")){
				whereFechas = " AND AjuFecha between '" + df.format(this.FechaInicio) + "' AND '" + df.format(this.FechaFin) + "'";			
										
			}else whereFechas =  " AND PgoFecha between '" + df.format(this.FechaInicio) + "' AND '" + df.format(this.FechaFin) + "'";	
		
		}			
		
		return whereFechas;	
	}	
	


}
