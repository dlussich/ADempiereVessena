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
import java.util.List;
import java.util.logging.Level;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.apps.ProcessCtl;
import org.compiere.model.MPInstance;
import org.compiere.model.MPInstancePara;
import org.compiere.model.MPeriod;
import org.compiere.model.MProcess;
import org.compiere.process.ProcessInfo;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.openup.model.MFduCod;
import org.openup.model.MFduConnectionData;
import org.openup.model.MFduEvolutionConceptsNav;
import org.openup.model.MFduLogisticMonth;
import org.openup.model.MFduLogisticMonthDates;


/**OpenUp. v2.5.1. 19/19/2012
 * @author gbrust
 *
 */
public class ROp_EvolutionConcepts extends SvrProcess {
	
	private static String TABLA_MOLDE = "UY_Molde_Fdu_EvolutionConcepts";
	
	private int fduEvoConceptNavID = 0;
	
	private String reportType = "";	
	private Timestamp fecha = null;
	private int periodID = 0;
	private int logisticMonthID = 0;
	String codigosTotalizadores = "";
	String codigosAjustes = "";
	
	
	private boolean conReporteGrafico = false;
	private boolean anioAnterior = false;
	
	private int adClientID = 0;
	private int adOrgID = 0;
	private int adUserID = 0;	
	private String idReporte = "";
	
	
	public ROp_EvolutionConcepts() {
		
	}

	
	@Override
	protected void prepare() {
		
		ProcessInfoParameter[] para = getParameter();

		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName().trim();
			if (name!= null){
				if (name.equalsIgnoreCase("UY_Fdu_EvolutionConceptsNav_ID"))
					this.fduEvoConceptNavID = ((BigDecimal)para[i].getParameter()).intValueExact();
			}
		}
		
		MFduEvolutionConceptsNav concepts = new MFduEvolutionConceptsNav(getCtx(), this.fduEvoConceptNavID, this.get_TrxName());			
		
		this.reportType = concepts.getReportType();
		this.fecha = new MFduLogisticMonthDates(getCtx(), concepts.getUY_Fdu_LogisticMonthDates_ID(), this.get_TrxName()).getDateTrx();
		this.periodID = concepts.getC_Period_ID();
		this.logisticMonthID = concepts.getUY_Fdu_LogisticMonth_ID();
		this.codigosTotalizadores = concepts.getConsumptionsCodes();
		this.codigosAjustes = concepts.getAdjustmentsCodes();
		
		this.conReporteGrafico = concepts.isGraphicReport();
		this.anioAnterior = concepts.isLastYear();
				
		this.adUserID = concepts.getAD_User_ID();
		this.adClientID = concepts.getAD_Client_ID();
		this.adOrgID = concepts.getAD_Org_ID();
		this.idReporte = UtilReportes.getReportID(new Long(this.adUserID));
		
	}
	
	
	private Connection getConnection() throws Exception {
		
		Connection retorno = null;

		String connectString = "";//"jdbc:sqlserver://192.168.150.175\\FP01;databaseName=FDU;user=adempiere;password=adempiere1144";
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
						
		//Delete de reportes anteriores de este usuario para ir limpiando la tabla molde
		this.deleteInstanciasViejasReporte();
								
		//cargo en tabla molde, los movimientos segun filtro indicado por el usuario, para codigos de totalizadores y ajustes.	(siempre y cuando no venga la sql vacia)
		if(!this.codigosTotalizadores.equals("") && !this.getSqlForTotalizadores().equals("")) this.loadMovimientos(this.getSqlForTotalizadores(), "Totalizadores");
		if(!this.codigosAjustes.equals("") && !this.getSqlForAjustes().equals("")) this.loadMovimientos(this.getSqlForAjustes(), "Ajustes");
				
		//Se lanza el reporte común
		this.lanzarReporte("EvolutionsConceptsRV", "UY_ROp_EvolutionsConceptsRV");
		
		//Si se eligió con reporte grafico se lanza.
		if(this.conReporteGrafico){
			this.lanzarReporte("EvConcPesosJR", "UY_ROp_EvoConceptJR");			
		}
				
		return "OK";
	}
	
	private void lanzarReporte(String nombreReporte, String process){
		
		this.showHelp("Abriendo Reporte");
		
		// Disparo proceso de Reporte de nombre igual al parametro pasado
		// Le paso los parametros que necesita via código
		// ID del proceso de Reporte de nombre igual al pasado por parametro
		int adProcessID = MProcess.getProcess_ID(process, null); 

		MPInstance instance = new MPInstance(Env.getCtx(), adProcessID, 0);
		instance.saveEx();
		
		ProcessInfo pi = new ProcessInfo (nombreReporte, adProcessID);
		pi.setAD_PInstance_ID (instance.getAD_PInstance_ID());	
		pi.setAD_Client_ID(this.adClientID);
		
		MPInstancePara  para = new MPInstancePara(instance, 10);
		para.setParameter("AD_Client_ID", new BigDecimal(this.adClientID));
		para.saveEx();

		para = new MPInstancePara(instance, 20);
		para.setParameter("AD_Org_ID", new BigDecimal(this.adOrgID));
		para.saveEx();
		
		para = new MPInstancePara(instance, 30);
		para.setParameter("AD_User_ID", new BigDecimal(this.adUserID));
		para.saveEx();
		
		para = new MPInstancePara(instance, 40);
		para.setParameter("idReporte", this.idReporte);
		para.saveEx();		
		
		ProcessCtl worker = new ProcessCtl(null, 0, pi, null);
		worker.start();		
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
	 * Fecha : 29/08/2012
	 */
	private void loadMovimientos(String sql, String tabla){
		
		Connection con = null;
		ResultSet rs = null;			
		
		try {
			
			con = this.getConnection();
			Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			
			rs = stmt.executeQuery(sql);
			
			this.showHelp("Leyendo Datos de " + tabla + "...");
			
			/*
			rs.last();
			int totalRowCount = rs.getRow(), rowCount = 0;
			rs.beforeFirst();
			*/
			
			while (rs.next()) {
				
				//this.showHelp("Linea " + rowCount++ + " de " + totalRowCount);
				
				MFduCod cod = MFduCod.getMFduCodForValue(getCtx(), get_TrxName(), rs.getString("codigo"));
												
				String values = "'" + rs.getString("codigo") + "','" + cod.getName() + "','" + rs.getString("cierreAnterior") + "'," + rs.getString("montoPesosAnterior") + "," +
				                rs.getString("montoDolaresAnterior") + ",'" + rs.getString("cierre") + "'," + rs.getString("montoPesos") + "," + rs.getString("montoDolares") + "," + 
						        this.adUserID + "," + this.adOrgID + "," + this.adClientID + ",'" + this.idReporte + "'," + 
				                rs.getBigDecimal("DifPesos") + "," + rs.getBigDecimal("DifDolares") + "," + rs.getInt("CantCuentasAnterior") + "," + rs.getInt("CantCtasActual") + "," + rs.getInt("DifCant");
								
				
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
	
	private String getSqlForTotalizadores(){
		
		String sql = "";
		SimpleDateFormat df = new SimpleDateFormat("yyyy-dd-MM 00:00:00");
				
		if(this.reportType.equals("UC")){ //unico cierre
			
			String fecha = (this.anioAnterior ? "(SELECT DateAdd(Year, -1, '" + df.format(this.fecha) + "'))" : "'" + df.format(this.fecha) + "'") ;
						
			sql = "WITH cierreAnterior AS(" +
					 " SELECT ConDet AS codigo, Gru as grupoCierre, FchCie AS cierre, SUM(ImpPes) AS montoPesos, SUM(ImpDol) AS montoDolares, (SELECT COUNT(aux.NroCue)" +  
																																			  " FROM FDu.dbo.T_FDU_CC108D_DETALLE_T aux" +
																																			  " WHERE t.FchCie = aux.FchCie" +
																																			  " AND t.ConDet = aux.ConDet) as CantCuentas" +
					 " FROM FDu.dbo.T_FDU_CC108D_DETALLE_T t" +
					 " WHERE FchCie = (SELECT MAX(FchCie)" +
					 " 					FROM FDu.dbo.T_FDU_CC108D_DETALLE_T" + 
					 " 					WHERE FchCie < " + fecha +
					 " 									AND GRU = (SELECT DISTINCT Gru" + 
					 "			   									FROM FDu.dbo.T_FDU_CC108D_DETALLE_T" + 
					 "			   									WHERE FchCie = " + fecha + "))" +
					 (!this.codigosTotalizadores.equals("") ? " AND ConDet in (" + this.codigosTotalizadores + ")" : "" ) +					 
					 " GROUP BY ConDet, Gru, FchCie" +					 
					 " )" +
					 " SELECT ConDet AS codigo, CASE WHEN a.cierre is null THEN 'No Tiene' ELSE CONVERT(varchar, a.cierre, 103) END AS cierreAnterior," + 
					 " CASE WHEN a.montoPesos is null THEN 00.00 ELSE a.montoPesos END AS montoPesosAnterior," + 
					 " CASE WHEN a.montoDolares is null THEN 00.00 ELSE a.montoDolares END AS montoDolaresAnterior," + 
					 " CONVERT(varchar, FchCie, 103) AS cierre, SUM(c.ImpPes) AS montoPesos, SUM(c.ImpDol) AS montoDolares, (isnull(a.montoPesos, 0.0) - SUM(isnull(c.ImpPes, 0.0))) AS DifPesos, (isnull(a.montoDolares, 0.0) - SUM(isnull(c.ImpDol, 0.0))) AS DifDolares," + 
					 " isnull(a.CantCuentas, 0) as CantCuentasAnterior, isnull((SELECT COUNT(aux.NroCue) FROM FDu.dbo.T_FDU_CC108D_DETALLE_T aux WHERE aux.FchCie = c.FchCie AND ConDet = c.ConDet),0) as CantCtasActual," + 
					 " ((isnull(a.CantCuentas, 0)) - (isnull((SELECT COUNT(aux.NroCue) FROM FDu.dbo.T_FDU_CC108D_DETALLE_T aux WHERE aux.FchCie = c.FchCie AND ConDet = c.ConDet),0))) as DifCant" +
					 " FROM FDu.dbo.T_FDU_CC108D_DETALLE_T c" +
					 " LEFT JOIN cierreAnterior a ON c.ConDet = a.codigo" +
					 " WHERE FchCie = " + fecha + 			
					 (!this.codigosTotalizadores.equals("") ? " AND ConDet in (" + this.codigosTotalizadores + ")" : "" ) +			
					 " GROUP BY c.ConDet, a.cierre, a.montoPesos, a.montoDolares, c.FchCie, c.Gru, a.CantCuentas" +
					 " ORDER BY c.ConDet";
			
		}else if(this.reportType.equals("MC")){ // mes calendario
			
			MPeriod period = null;
			MPeriod periodAnterior = null;
			
			if(this.periodID > 0) {
				if(this.anioAnterior){
					period = new MPeriod(getCtx(), (this.periodID), get_TrxName());
					periodAnterior = new MPeriod(getCtx(), (this.periodID -12), get_TrxName());
				}else{
					period = new MPeriod(getCtx(), this.periodID, get_TrxName());
					periodAnterior = new MPeriod(getCtx(), (this.periodID -1), get_TrxName());
				}
				
			}else{
				throw new AdempiereException("Debe elegir un mes calendario");
			}
			
						
			sql = "WITH cierreAnterior AS(" + 
				  " SELECT t.ConDet as codigo, '" + periodAnterior.getName() + "' AS mesCalendario, SUM(t.ImpPes) AS montoPesos, SUM(t.ImpDol) AS montoDolares, (SELECT COUNT(aux.NroCue)" +
				  "																								                                           		FROM FDU.dbo.T_FDU_CC108D_DETALLE_T aux" +
				  "																																		  		WHERE aux.FchCie between '" + df.format(periodAnterior.getStartDate()) +  "' AND '" + df.format(periodAnterior.getEndDate()) + "'" +
				  "																									                                       		AND t.ConDet = aux.ConDet) as CantCuentas" +
				  "	FROM FDU.dbo.T_FDU_CC108D_DETALLE_T	t" +
				  "	WHERE t.FchCie between '" + df.format(periodAnterior.getStartDate()) +  "' AND '" + df.format(periodAnterior.getEndDate()) + "'" + 
				  "	GROUP BY t.ConDet" +
				  "	)" +
				  "	SELECT c.ConDet AS codigo, a.mesCalendario AS cierreAnterior," + 
				  " CASE WHEN a.montoPesos is null THEN 00.00 ELSE a.montoPesos END AS montoPesosAnterior," + 					 
				  " CASE WHEN a.montoDolares is null THEN 00.00 ELSE a.montoDolares END AS montoDolaresAnterior," +  
				           "'" + period.getName() + "' AS cierre, SUM(c.ImpPes) AS montoPesos, SUM(c.ImpDol) AS montoDolares," +									  
				  " (isnull(a.montoPesos, 0.0) - SUM(isnull(c.ImpPes, 0.0))) AS DifPesos," + 
				  " (isnull(a.montoDolares, 0.0) - SUM(isnull(c.ImpDol, 0.0))) AS DifDolares," +
				  " isnull(a.CantCuentas, 0) as CantCuentasAnterior, isnull((SELECT COUNT(aux.NroCue)" +
				  "															 FROM FDu.dbo.T_FDU_CC108D_DETALLE_T aux" +
				  "															 WHERE aux.FchCie between '" + df.format(period.getStartDate()) +  "' AND '" + df.format(period.getEndDate()) + "'" +
				  "															 AND aux.ConDet = c.ConDet),0) as CantCtasActual," + 
				  "	((isnull(a.CantCuentas, 0)) - (isnull((SELECT COUNT(aux.NroCue)" +
				  " 									   FROM FDu.dbo.T_FDU_CC108D_DETALLE_T aux " +
				  "										   WHERE aux.FchCie between '" + df.format(period.getStartDate()) +  "' AND '" + df.format(period.getEndDate()) + "'" +
				  "                                        AND aux.ConDet = c.ConDet),0))) as DifCant" +                           	
				  "	FROM FDU.dbo.T_FDU_CC108D_DETALLE_T	c" +
				  "	INNER JOIN cierreAnterior a ON c.ConDet = a.codigo" +
				  " WHERE c.FchCie between '" + df.format(period.getStartDate()) +  "' AND '" + df.format(period.getEndDate()) + "'" +
				  (!this.codigosTotalizadores.equals("") ? " AND ConDet in (" + this.codigosTotalizadores + ")" : "" ) +	 
				  "	GROUP BY c.ConDet, a.mesCalendario, a.montoPesos, a.montoDolares, a.CantCuentas" +
				  "	ORDER BY c.ConDet";
		
		}else if(this.reportType.equals("ML")){ // mes logistico
			
			MFduLogisticMonth month = null;
			MFduLogisticMonth monthAnterior = null;
			
			if(this.logisticMonthID > 0){
				if(this.anioAnterior){
					month = new MFduLogisticMonth(getCtx(), this.logisticMonthID -12, get_TrxName());
					monthAnterior = new MFduLogisticMonth(getCtx(), (this.logisticMonthID -13), get_TrxName());
				}else{
					month = new MFduLogisticMonth(getCtx(), this.logisticMonthID, get_TrxName());
					monthAnterior = new MFduLogisticMonth(getCtx(), (this.logisticMonthID -1), get_TrxName());
				}				
			}else{
				throw new AdempiereException("Debe elegir un mes logistico");
			}
			
			if(month.get_ID() > 0 && monthAnterior.get_ID() > 0){
				
			}else{
				throw new AdempiereException("Algún mes logístico involucrado no se encuentra cargado");
			}
			
			sql = "WITH cierreAnterior AS(" + 
				  " SELECT t.ConDet AS codigo, '" +  monthAnterior.getDescription() + "' AS mesLogistico, SUM(t.ImpPes) AS montoPesos, SUM(t.ImpDol) AS montoDolares, (SELECT COUNT(aux.NroCue)" + 
																																									  " FROM FDu.dbo.T_FDU_CC108D_DETALLE_T aux " +
																																									  (!this.getWhereFechas(monthAnterior.get_ID()).equals("") ? " WHERE aux.FchCie in (" + getWhereFechas(monthAnterior.get_ID()) + ")" : "" ) +
																																									  " AND t.ConDet = aux.ConDet) as CantCuentas" + 
				  " FROM FDU.dbo.T_FDU_CC108D_DETALLE_T t" + 
				  (!this.getWhereFechas(monthAnterior.get_ID()).equals("") ? " WHERE t.FchCie in (" + getWhereFechas(monthAnterior.get_ID()) + ")" : "" ) +
				  " GROUP BY ConDet" +
				  " )" + 
				  " SELECT c.ConDet AS codigo, a.mesLogistico AS cierreAnterior, " +
				  " CASE WHEN a.montoPesos is null THEN 00.00 ELSE a.montoPesos END AS montoPesosAnterior," + 					 
				  " CASE WHEN a.montoDolares is null THEN 00.00 ELSE a.montoDolares END AS montoDolaresAnterior," + 
				  "'" +  month.getDescription() + "' AS cierre, SUM(c.ImpPes) AS montoPesos, SUM(c.ImpDol) AS montoDolares," +				  
				  " (isnull(a.montoPesos, 0.0) - SUM(isnull(c.ImpPes, 0.0))) AS DifPesos, (isnull(a.montoDolares, 0) - SUM(isnull(c.ImpDol, 0))) AS DifDolares," +
				  " isnull(a.CantCuentas, 0) as CantCuentasAnterior, isnull((SELECT COUNT(aux.NroCue) FROM FDu.dbo.T_FDU_CC108D_DETALLE_T aux " + (!this.getWhereFechas(month.get_ID()).equals("") ? " WHERE aux.FchCie in (" + getWhereFechas(month.get_ID()) + ")" : "" ) + " AND aux.ConDet = c.ConDet),0) as CantCtasActual," + 
				  " ((isnull(a.CantCuentas, 0)) - (isnull((SELECT COUNT(aux.NroCue) FROM FDu.dbo.T_FDU_CC108D_DETALLE_T aux " + (!this.getWhereFechas(month.get_ID()).equals("") ? " WHERE aux.FchCie in (" + getWhereFechas(month.get_ID()) + ")" : "" ) + " AND aux.ConDet = c.ConDet),0))) as DifCant" +
				  " FROM FDU.dbo.T_FDU_CC108D_DETALLE_T c" +
				  " INNER JOIN cierreAnterior a ON c.ConDet = a.codigo" +
				  (!this.getWhereFechas(month.get_ID()).equals("") ? " WHERE c.FchCie in (" + getWhereFechas(month.get_ID()) + ")" : "" ) +
				  (!this.codigosTotalizadores.equals("") ? " AND ConDet in (" + this.codigosTotalizadores + ")" : "" ) +
				  " GROUP BY c.ConDet, a.mesLogistico, a.montoPesos, a.montoDolares, CantCuentas" +
				  " ORDER BY c.ConDet";			
		}
			
		return sql;		
		
	}
	
	private String getSqlForAjustes(){	
		
		String sql = "";
		SimpleDateFormat df = new SimpleDateFormat("yyyy-dd-MM 00:00:00");
				
		if(this.reportType.equals("UC")){ //unico cierre
			
			String fecha = (this.anioAnterior ? "(SELECT DateAdd(Year, -1, '" + df.format(this.fecha) + "'))" : "'" + df.format(this.fecha) + "'") ;
						
			sql = "WITH cierreAnterior AS(" +
					 " SELECT CanPesConAju AS codigo, Gru as grupoCierre, FchCie AS cierre, SUM(ImpPes) AS montoPesos, " +					 
					 " (SELECT SUM(ImpPes)" +
					 " FROM FDu.dbo.T_FDU_CC108D_DETALLE_A a" +
					 " WHERE FchCie = (SELECT MAX(FchCie)" + 					
					 "				   FROM FDu.dbo.T_FDU_CC108D_DETALLE_A" + 					
					 " 				   WHERE FchCie < " + fecha +				
				     "                 AND GRU = (SELECT DISTINCT Gru" +			   					
				     " 							  FROM FDu.dbo.T_FDU_CC108D_DETALLE_A" +			   					
					 "  						  WHERE FchCie = " + fecha + "))" +					     
					 (!this.codigosAjustes.equals("") ? " AND a.CanPesConAju in (" + this.codigosAjustes + ")" : "" ) + 
					 " AND CodMonAju = '840'" +
					 " GROUP BY CanPesConAju, Gru, FchCie) AS montoDolares," +
					 
					 " (SELECT COUNT(aux.NroCue)" +  
					 " FROM FDu.dbo.T_FDU_CC108D_DETALLE_A aux" +
					 " WHERE t.FchCie = aux.FchCie" +
					 " AND t.CanPesConAju = aux.CanPesConAju) as CantCuentas" +
					 " FROM FDu.dbo.T_FDU_CC108D_DETALLE_A t" +
					 " WHERE FchCie = (SELECT MAX(FchCie)" +
					 " 					FROM FDu.dbo.T_FDU_CC108D_DETALLE_A" + 
					 " 					WHERE FchCie < " + fecha +
					 " 					AND GRU = (SELECT DISTINCT Gru" + 
					 "			   					FROM FDu.dbo.T_FDU_CC108D_DETALLE_A" + 
					 "			   					WHERE FchCie = " + fecha + "))" +
					 (!this.codigosAjustes.equals("") ? " AND t.CanPesConAju in (" + this.codigosAjustes + ")" : "" ) + 
					 " AND CodMonAju = '858'" +
					 " GROUP BY CanPesConAju, Gru, FchCie" +					 
					 " )" +
					 " SELECT CanPesConAju AS codigo, CASE WHEN a.cierre is null THEN 'No Tiene' ELSE CONVERT(varchar, a.cierre, 103) END AS cierreAnterior," + 
					 " CASE WHEN a.montoPesos is null THEN 00.00 ELSE a.montoPesos END AS montoPesosAnterior," + 					 
					 " CASE WHEN a.montoDolares is null THEN 00.00 ELSE a.montoDolares END AS montoDolaresAnterior," + 
					 " CONVERT(varchar, FchCie, 103) AS cierre, SUM(c.ImpPes) AS montoPesos, " +				 
					 
					 " CASE WHEN (SELECT SUM(ImpPes)" +
					 " FROM FDu.dbo.T_FDU_CC108D_DETALLE_A" +
					 " WHERE FchCie = " + fecha +
					 (!this.codigosAjustes.equals("") ? " AND CanPesConAju in (" + this.codigosAjustes + ")" : "" ) + 
					 " AND CodMonAju = '840'" +
					 " GROUP BY CanPesConAju, Gru, FchCie) is null THEN 00.00 ELSE (SELECT SUM(ImpPes)" +
					 " 																FROM FDu.dbo.T_FDU_CC108D_DETALLE_A" +
					 " 																WHERE FchCie = " + fecha +
					 																(!this.codigosAjustes.equals("") ? " AND CanPesConAju in (" + this.codigosAjustes + ")" : "" ) +																					 
					 " 																AND CodMonAju = '840'" +
					 " 																GROUP BY CanPesConAju, Gru, FchCie) END AS montoDolares," + 					 
					 
					 "(isnull(a.montoPesos, 0.0) - SUM(isnull(c.ImpPes, 0.0))) AS DifPesos, " +
					 
  					 " (isnull(a.montoDolares, 0) - CASE WHEN (SELECT SUM(isnull(ImpPes, 0)) " +
  					 " 					            		  FROM FDu.dbo.T_FDU_CC108D_DETALLE_A" +				  
  					 " 										  WHERE FchCie =  " + fecha +
  					 										  (!this.codigosAjustes.equals("") ? " AND CanPesConAju in (" + this.codigosAjustes + ")" : "" ) + 
					 " 										  AND CodMonAju = '840'" +
					 " 										  GROUP BY CanPesConAju, Gru, FchCie) is null THEN 00.00 ELSE (SELECT SUM(isnull(ImpPes, 0)) " +
					 " 					            		  																FROM FDu.dbo.T_FDU_CC108D_DETALLE_A" +				  
					 " 										  																WHERE FchCie =  " + fecha +
	  				 " 																										AND CodMonAju = '840'" +
					 " 																										GROUP BY CanPesConAju, Gru, FchCie) END) AS DifDolares," +
										 
					 " isnull(a.CantCuentas, 0) as CantCuentasAnterior, " +
					 " isnull((SELECT COUNT(aux.NroCue) " +
					 "		   FROM FDu.dbo.T_FDU_CC108D_DETALLE_A aux " +
					 "		   WHERE aux.FchCie = c.FchCie " +
					 "		   AND CanPesConAju = c.CanPesConAju),0) as CantCtasActual," + 
					 " ((isnull(a.CantCuentas, 0)) - (isnull((SELECT COUNT(aux.NroCue)" +
					 " 										  FROM FDu.dbo.T_FDU_CC108D_DETALLE_A aux " +
					 "										  WHERE aux.FchCie = c.FchCie " +
					 "										  AND CanPesConAju = c.CanPesConAju),0))) as DifCant" +
					 " FROM FDu.dbo.T_FDU_CC108D_DETALLE_A c" +
					 " LEFT JOIN cierreAnterior a ON c.CanPesConAju = a.codigo" +
					 " WHERE FchCie = " + fecha + 			
					 (!this.codigosAjustes.equals("") ? " AND c.CanPesConAju in (" + this.codigosAjustes + ")" : "" ) + 		
					 " GROUP BY c.CanPesConAju, a.cierre, a.montoPesos, a.montoDolares, c.FchCie, c.Gru, a.CantCuentas" +
					 " ORDER BY c.CanPesConAju";
			
		}else if(this.reportType.equals("MC")){ // mes calendario
			
			MPeriod period = null;
			MPeriod periodAnterior = null;
			
			if(this.periodID > 0) {
				if(this.anioAnterior){
					period = new MPeriod(getCtx(), (this.periodID), get_TrxName());
					periodAnterior = new MPeriod(getCtx(), (this.periodID -12), get_TrxName());
				}else{
					period = new MPeriod(getCtx(), this.periodID, get_TrxName());
					periodAnterior = new MPeriod(getCtx(), (this.periodID -1), get_TrxName());
				}
				
			}else{
				throw new AdempiereException("Debe elegir un mes calendario");
			}
			
						
			sql = "WITH cierreAnterior AS(" + 
				  " SELECT t.CanPesConAju as codigo, '" + periodAnterior.getName() + "' AS mesCalendario, SUM(t.ImpPes) AS montoPesos, " +
				  	
				  " (SELECT SUM(ImpPes)" +
				  " FROM FDu.dbo.T_FDU_CC108D_DETALLE_A" +
				  "  WHERE FchCie between '" + df.format(periodAnterior.getStartDate()) +  "' AND '" + df.format(periodAnterior.getEndDate()) + "'" +
				  (!this.codigosAjustes.equals("") ? " AND CanPesConAju in (" + this.codigosAjustes + ")" : "" ) + 
				  " AND CodMonAju = '840'" +
				  " GROUP BY CanPesConAju, Gru, FchCie) AS montoDolares," + 
				  	
				  " (SELECT COUNT(aux.NroCue)" +
				  "  FROM FDU.dbo.T_FDU_CC108D_DETALLE_A aux" +
				  "  WHERE aux.FchCie between '" + df.format(periodAnterior.getStartDate()) +  "' AND '" + df.format(periodAnterior.getEndDate()) + "'" +
				  "	 AND t.CanPesConAju = aux.CanPesConAju) as CantCuentas" +
				  
	  			  "	FROM FDU.dbo.T_FDU_CC108D_DETALLE_A	t" +
				  "	WHERE t.FchCie between '" + df.format(periodAnterior.getStartDate()) +  "' AND '" + df.format(periodAnterior.getEndDate()) + "'" + 
				  "	GROUP BY t.CanPesConAju" +
				  "	)" +
				  
				  "	SELECT c.CanPesConAju AS codigo, a.mesCalendario AS cierreAnterior, " +
				  " CASE WHEN a.montoPesos is null THEN 00.00 ELSE a.montoPesos END AS montoPesosAnterior," + 					 
				  " CASE WHEN a.montoDolares is null THEN 00.00 ELSE a.montoDolares END AS montoDolaresAnterior," +  
				  "'" + period.getName() + "' AS cierre, SUM(c.ImpPes) AS montoPesos, " +
				  
				 " CASE WHEN (SELECT SUM(ImpPes)" +
				 " FROM FDu.dbo.T_FDU_CC108D_DETALLE_A" +
				 "  WHERE FchCie between '" + df.format(period.getStartDate()) +  "' AND '" + df.format(period.getEndDate()) + "'" +
				 (!this.codigosAjustes.equals("") ? " AND CanPesConAju in (" + this.codigosAjustes + ")" : "" ) + 
				 " AND CodMonAju = '840'" +
				 " GROUP BY CanPesConAju, Gru, FchCie) is null THEN 00.00 ELSE (SELECT SUM(ImpPes)" +
				 " 																FROM FDu.dbo.T_FDU_CC108D_DETALLE_A" +
				 "  															WHERE FchCie between '" + df.format(period.getStartDate()) +  "' AND '" + df.format(period.getEndDate()) + "'" +
																				(!this.codigosAjustes.equals("") ? " AND CanPesConAju in (" + this.codigosAjustes + ")" : "" ) + 
				 " 																AND CodMonAju = '840'" +
				 " 																GROUP BY CanPesConAju, Gru, FchCie) END AS montoDolares," + 
												  
				  " (isnull(a.montoPesos, 0.0) - SUM(isnull(c.ImpPes, 0.0))) AS DifPesos," + 
				  
				 " (isnull(a.montoDolares, 0) - CASE WHEN (SELECT SUM(isnull(ImpPes, 0)) " +
				 " 					            		  FROM FDu.dbo.T_FDU_CC108D_DETALLE_A" +				  
				 "  									  WHERE FchCie between '" + df.format(period.getStartDate()) +  "' AND '" + df.format(period.getEndDate()) + "'" +
													      (!this.codigosAjustes.equals("") ? " AND CanPesConAju in (" + this.codigosAjustes + ")" : "" ) + 
                 " 										  AND CodMonAju = '840'" +
				 " 										  GROUP BY CanPesConAju, Gru, FchCie) is null THEN 00.00 ELSE (SELECT SUM(isnull(ImpPes, 0)) " +
				 " 					            		  															   FROM FDu.dbo.T_FDU_CC108D_DETALLE_A" +				  
				 "																									  WHERE FchCie between '" + df.format(period.getStartDate()) +  "' AND '" + df.format(period.getEndDate()) + "'" +
																													  (!this.codigosAjustes.equals("") ? " AND CanPesConAju in (" + this.codigosAjustes + ")" : "" ) + 
				 " 																									  AND CodMonAju = '840'" +
				 " 																									  GROUP BY CanPesConAju, Gru, FchCie) END) AS DifDolares," +
							  
				  " isnull(a.CantCuentas, 0) as CantCuentasAnterior, " +				  
				  
				  " isnull((SELECT COUNT(aux.NroCue)" +
				  " FROM FDU.dbo.T_FDU_CC108D_DETALLE_A aux" +
                  "	WHERE aux.FchCie between '" + df.format(period.getStartDate()) +  "' AND '" + df.format(period.getEndDate()) + "'" +
                  "	AND aux.CanPesConAju = c.CanPesConAju),0) as CantCtasActual," + 
				  
				  "	((isnull(a.CantCuentas, 0)) - (isnull((SELECT COUNT(aux.NroCue)" +
				  " 									   FROM FDU.dbo.T_FDU_CC108D_DETALLE_A aux " +
				  "										   WHERE aux.FchCie between '" + df.format(period.getStartDate()) +  "' AND '" + df.format(period.getEndDate()) + "'" +
				  "                                        AND aux.CanPesConAju = c.CanPesConAju),0))) as DifCant" +                           	
				
				  "	FROM FDU.dbo.T_FDU_CC108D_DETALLE_A	c" +
				  "	INNER JOIN cierreAnterior a ON c.CanPesConAju = a.codigo" +
				  " WHERE c.FchCie between '" + df.format(period.getStartDate()) +  "' AND '" + df.format(period.getEndDate()) + "'" +
				  (!this.codigosAjustes.equals("") ? " AND c.CanPesConAju in (" + this.codigosAjustes + ")" : "" ) +  
				  "	GROUP BY c.CanPesConAju, a.mesCalendario, a.montoPesos, a.montoDolares, a.CantCuentas" +
				  "	ORDER BY c.CanPesConAju";
		
		}else if(this.reportType.equals("ML")){ // mes logistico
			
			MFduLogisticMonth month = null;
			MFduLogisticMonth monthAnterior = null;
			
			if(this.logisticMonthID > 0){
				if(this.anioAnterior){
					month = new MFduLogisticMonth(getCtx(), this.logisticMonthID -12, get_TrxName());
					monthAnterior = new MFduLogisticMonth(getCtx(), (this.logisticMonthID -13), get_TrxName());
				}else{
					month = new MFduLogisticMonth(getCtx(), this.logisticMonthID, get_TrxName());
					monthAnterior = new MFduLogisticMonth(getCtx(), (this.logisticMonthID -1), get_TrxName());
				}				
			}else{
				throw new AdempiereException("Debe elegir un mes logistico");
			}
			
			if(month.get_ID() > 0 && monthAnterior.get_ID() > 0){
				
			}else{
				throw new AdempiereException("Algún mes logístico involucrado no se encuentra cargado");
			}
			
			sql = "WITH cierreAnterior AS(" + 
				  " SELECT t.CanPesConAju AS codigo, '" +  monthAnterior.getDescription() + "' AS mesLogistico, SUM(t.ImpPes) AS montoPesos, " +
				  		
				  " (SELECT SUM(ImpPes)" +
				  " FROM FDu.dbo.T_FDU_CC108D_DETALLE_A" +
				  (!this.getWhereFechas(monthAnterior.get_ID()).equals("") ? " WHERE FchCie in (" + getWhereFechas(monthAnterior.get_ID()) + ")" : "" ) +
				  (!this.codigosAjustes.equals("") ? " AND CanPesConAju in (" + this.codigosAjustes + ")" : "" ) + 
				  " AND CodMonAju = '840'" +
				  " GROUP BY CanPesConAju, Gru, FchCie) AS montoDolares," + 
				  		
				  "(SELECT COUNT(aux.NroCue)" + 
				  " FROM FDU.dbo.T_FDU_CC108D_DETALLE_A aux" +
				  (!this.getWhereFechas(monthAnterior.get_ID()).equals("") ? " WHERE aux.FchCie in (" + getWhereFechas(monthAnterior.get_ID()) + ")" : "" ) +
				  " AND t.CanPesConAju = aux.CanPesConAju) as CantCuentas" + 
				  
				  " FROM FDU.dbo.T_FDU_CC108D_DETALLE_A t" + 
				  (!this.getWhereFechas(monthAnterior.get_ID()).equals("") ? " WHERE t.FchCie in (" + getWhereFechas(monthAnterior.get_ID()) + ")" : "" ) +
				  " GROUP BY t.CanPesConAju" +
				  " )" + 
				  
				  " SELECT c.CanPesConAju AS codigo, a.mesLogistico AS cierreAnterior," +
				  " CASE WHEN a.montoPesos is null THEN 00.00 ELSE a.montoPesos END AS montoPesosAnterior," + 					 
				  " CASE WHEN a.montoDolares is null THEN 00.00 ELSE a.montoDolares END AS montoDolaresAnterior," +  
				  "'" +  month.getDescription() + "' AS cierre, SUM(c.ImpPes) AS montoPesos, " +
				  
				  " CASE WHEN (SELECT SUM(ImpPes)" +
				  " FROM FDu.dbo.T_FDU_CC108D_DETALLE_A" +
				  (!this.getWhereFechas(month.get_ID()).equals("") ? " WHERE FchCie in (" + getWhereFechas(month.get_ID()) + ")" : "" ) +
				  (!this.codigosAjustes.equals("") ? " AND CanPesConAju in (" + this.codigosAjustes + ")" : "" ) + 
			      " AND CodMonAju = '840'" +
				  " GROUP BY CanPesConAju, Gru, FchCie) is null THEN 00.00 ELSE (SELECT SUM(ImpPes)" +
				  " 															FROM FDu.dbo.T_FDU_CC108D_DETALLE_A" +
																				(!this.getWhereFechas(month.get_ID()).equals("") ? " WHERE FchCie in (" + getWhereFechas(month.get_ID()) + ")" : "" ) +
																				(!this.codigosAjustes.equals("") ? " AND CanPesConAju in (" + this.codigosAjustes + ")" : "" ) + 
				  " 															AND CodMonAju = '840'" +
				  " 															GROUP BY CanPesConAju, Gru, FchCie) END AS montoDolares," + 
						  
				  " (isnull(a.montoPesos, 0.0) - SUM(isnull(c.ImpPes, 0.0))) AS DifPesos, " +
				  
				  " (isnull(a.montoDolares, 0) - CASE WHEN (SELECT SUM(isnull(ImpPes, 0)) " +
				  " 					            		  FROM FDu.dbo.T_FDU_CC108D_DETALLE_A" +				  
							  								  (!this.getWhereFechas(month.get_ID()).equals("") ? " WHERE FchCie in (" + getWhereFechas(month.get_ID()) + ")" : "" ) +
															  (!this.codigosAjustes.equals("") ? " AND CanPesConAju in (" + this.codigosAjustes + ")" : "" ) + 
														      " AND CodMonAju = '840'" +
															  " GROUP BY CanPesConAju, Gru, FchCie) is null THEN 00.00 ELSE (SELECT SUM(isnull(ImpPes, 0)) " +
																				  " 					            		  FROM FDu.dbo.T_FDU_CC108D_DETALLE_A" +				  
																							  								  (!this.getWhereFechas(month.get_ID()).equals("") ? " WHERE FchCie in (" + getWhereFechas(month.get_ID()) + ")" : "" ) +
																															  (!this.codigosAjustes.equals("") ? " AND CanPesConAju in (" + this.codigosAjustes + ")" : "" ) + 
																														      " AND CodMonAju = '840'" +
																															  " GROUP BY CanPesConAju, Gru, FchCie) END) AS DifDolares," +	
				  
				  " isnull(a.CantCuentas, 0) as CantCuentasAnterior," +
				  
				  " isnull((SELECT COUNT(aux.NroCue)" + 
				  " 	   FROM FDU.dbo.T_FDU_CC108D_DETALLE_A aux " + 
				  			(!this.getWhereFechas(month.get_ID()).equals("") ? " WHERE aux.FchCie in (" + getWhereFechas(month.get_ID()) + ")" : "" ) + 
				  		   " AND aux.CanPesConAju = c.CanPesConAju),0) as CantCtasActual," + 
				  		   
				  " ((isnull(a.CantCuentas, 0)) - (isnull((SELECT COUNT(aux.NroCue)" +
				  "										   FROM FDU.dbo.T_FDU_CC108D_DETALLE_A aux " + 
				  											(!this.getWhereFechas(month.get_ID()).equals("") ? " WHERE aux.FchCie in (" + getWhereFechas(month.get_ID()) + ")" : "" ) +
				  " 									   AND aux.CanPesConAju = c.CanPesConAju),0))) as DifCant" +
				  										   
				  " FROM FDU.dbo.T_FDU_CC108D_DETALLE_A c" +
				  " INNER JOIN cierreAnterior a ON c.CanPesConAju = a.codigo" +
				  (!this.getWhereFechas(month.get_ID()).equals("") ? " WHERE c.FchCie in (" + getWhereFechas(month.get_ID()) + ")" : "" ) +
				   (!this.codigosAjustes.equals("") ? " AND c.CanPesConAju in (" + this.codigosAjustes + ")" : "" ) + 
				  " GROUP BY c.CanPesConAju, a.mesLogistico, a.montoPesos, a.montoDolares, CantCuentas" +
				  " ORDER BY c.CanPesConAju";			
		}
			
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
	
	private String getWhereFechas(int month_id){
				
		String whereFechas = "";
		SimpleDateFormat df = new SimpleDateFormat("yyyy-dd-MM 00:00:00");
		
		List<MFduLogisticMonthDates> dates = MFduLogisticMonthDates.getFechasForMonth(getCtx(), month_id);
		if (dates.size() > 0){
			
			for (MFduLogisticMonthDates fdudate: dates){
				whereFechas += "'" + df.format(fdudate.getDateTrx()) + "',";
			}
			whereFechas = whereFechas.substring(0, (whereFechas.length())-1);
				
		}
		
		return whereFechas;	
	}
	
	private void showHelp(String text){
		if (this.getProcessInfo().getWaiting() != null){
			this.getProcessInfo().getWaiting().setText(text);
		}			
	}
	

	

}
