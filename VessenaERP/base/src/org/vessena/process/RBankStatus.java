package org.openup.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.compiere.apps.Waiting;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.openup.beans.ReportBankStatus;

public class RBankStatus extends SvrProcess {

	private static final String TABLA_MOLDE = "UY_Molde_BankStatus";
	private static Waiting waiting = null;
	private ReportBankStatus bankStatusFilters = new ReportBankStatus();
	
	private String idReporte;
	
	public RBankStatus(ReportBankStatus bankStatusFilters) {
		this.bankStatusFilters = bankStatusFilters;
		
	}

	@Override
	protected void prepare() {
		// TODO Auto-generated method stub

	}

	/**
	 * OpenUp. Santiago Evans. 3/2/2016.
	 * Carga datos para el reporte.
	 */
	public void execute() throws Exception {

		this.deleteInstanciasViejasReporte();
		
		this.getDataDebit();
		
		this.getDataCredit();
		
		this.getDataBouncedChecks();
		
		this.getDataCurrentBalance();
		
		this.getDataDifference();
		
	}	

	/**
	 * Elimina corridas anteriores de este reporte para este usuario.
	 * @throws Exception 
	 */
	private void deleteInstanciasViejasReporte() throws Exception{
		
		String sql = "";
		try{
			
			sql = " DELETE FROM " + TABLA_MOLDE +
				  " WHERE ad_user_id =" + this.bankStatusFilters.adUserID;
			
			DB.executeUpdateEx(sql,null);
		}
		catch (Exception e)
		{
			throw e;
		}
	}	

	/**
	 * Carga datos de los débitos en tabla molde considerando filtros.
	 * @throws Exception 
	 */
	private void getDataDebit() throws Exception{
		
		String insert = "", sql = "", whereFiltros = ""; String dateFrom = "";

		try
		{
			// Obtengo id para este reporte
			this.idReporte = UtilReportes.getReportID(Long.valueOf(this.bankStatusFilters.adUserID));
			
			// Armo where de filtros
			if (this.bankStatusFilters.cBankAccountID > 0)
				whereFiltros += " AND c_bankaccount_id = " + this.bankStatusFilters.cBankAccountID;
						
			
			insert = "INSERT INTO " + TABLA_MOLDE + " (ad_user_id, ad_client_id, ad_org_id, c_bankaccount_id,amtsourcedr, amtsourcecr, payamt, totalamt, differenceamt, datetrx)";

			StringBuilder strb = new StringBuilder("");
	        
			Calendar cal = new GregorianCalendar();
	        cal.setTimeInMillis(this.bankStatusFilters.date.getTime());
	        cal.add(cal.DAY_OF_YEAR, -1);
			
			DateFormat format = new SimpleDateFormat( "yyyy-MM-dd 00:00:00" );
			dateFrom = format.format(this.bankStatusFilters.date);
			String dateAcc = format.format(new Timestamp(cal.getTimeInMillis()));
			
			this.showHelp("Obteniendo datos...");			

			strb.append("(select " + this.bankStatusFilters.adUserID + ", ad_client_id, ad_org_id,c_bankaccount_id, CAST(sum(payamt) as numeric(10,2)) as debitos, 0 ,0 ,0, 0, '"+ dateFrom +"' from uy_mediospago " +  
						"where tipomp='PRO'"+
						"and estado = 'ENT'"+ 
						"and duedate <= '" + dateAcc + "' " + whereFiltros +
						" group by c_bankaccount_id, ad_client_id, ad_org_id);"); 
			
			sql = strb.toString();
			
			DB.executeUpdateEx(insert + sql, get_TrxName());
						
 		}
		catch (Exception e)
		{
			throw e;
		}
	}	
	
	/**
	 * Carga datos de los créditos en tabla molde considerando filtros.
	 * @throws Exception 
	 */
	private void getDataCredit() throws Exception{
		
		String insert = "", sql = "", sqlManage = "", sqlAccounts = "", whereFiltros = ""; String dateFrom = "";
		PreparedStatement pstmt = null;  
		ResultSet docs, accounts = null;	
		
		try
		{
			// Obtengo id para este reporte
			this.idReporte = UtilReportes.getReportID(Long.valueOf(this.bankStatusFilters.adUserID));
			
			// Armo where de filtros
			if (this.bankStatusFilters.cBankAccountID > 0)
				whereFiltros += " AND c_bankaccount_id = " + this.bankStatusFilters.cBankAccountID;
			
			insert = "INSERT INTO " + TABLA_MOLDE + " (ad_user_id, ad_client_id, ad_org_id, c_bankaccount_id,amtsourcedr, amtsourcecr, payamt, totalamt, differenceamt, datetrx)";

			StringBuilder strb = new StringBuilder("");

			Calendar cal = new GregorianCalendar();
	        cal.setTimeInMillis(this.bankStatusFilters.date.getTime());
	        cal.add(cal.DAY_OF_YEAR, -1);			
			
			DateFormat format = new SimpleDateFormat( "yyyy-MM-dd 00:00:00" );
			dateFrom = format.format(this.bankStatusFilters.date);
			String dateAcc = format.format(new Timestamp(cal.getTimeInMillis()));


			
			this.showHelp("Obteniendo datos...");			

			strb.append("(select " + this.bankStatusFilters.adUserID + ", ad_client_id, ad_org_id,c_bankaccount_id, 0, CAST(sum(payamt) as numeric(10,2)) as creditos, 0 ,0, 0 from uy_mediospago "  
						+"where tipomp='TER'"
						+" and estado = 'DEP'" 
						+" and duedate <= '" + dateAcc + "' " + whereFiltros 
						+" group by c_bankaccount_id, ad_client_id, ad_org_id" 
						+" union " 
						+"select " + this.bankStatusFilters.adUserID + ", ad_client_id, ad_org_id,c_bankaccount_id, 0, CAST(sum(uy_totalme) as numeric(10,2)) as creditos, 0 ,0, 0 from uy_movbancarioshdr "   
						+"where docstatus='CO'"
						+" and c_doctype_id = (select c_doctype_id from c_doctype where value='depefectivo')" 
						+" and IsConciliated='N'"
						+" group by c_bankaccount_id, ad_client_id, ad_org_id)"); 
			
			sql = strb.toString();
			pstmt = DB.prepareStatement(sql, get_TrxName());
			docs  = pstmt.executeQuery();					
			
			while (docs.next()){
				sqlAccounts = "select c_bankaccount_id from "+ TABLA_MOLDE +" where c_bankaccount_id = "+ docs.getInt("c_bankaccount_id");
				pstmt = DB.prepareStatement(sqlAccounts, get_TrxName());
				accounts = pstmt.executeQuery();
				if (accounts.next()){
					sqlManage = " UPDATE " + TABLA_MOLDE + " SET amtsourcecr = amtsourcecr + " + docs.getInt("creditos") + " where c_bankaccount_id = " + docs.getInt("c_bankaccount_id"); 
				}else{
					sqlManage = insert + " VALUES (" + this.bankStatusFilters.adUserID + "," + docs.getInt("ad_client_id") +"," + docs.getInt("ad_org_id") + ", "
								+  docs.getInt("c_bankaccount_id") +", 0, "+ docs.getInt("creditos") +", 0, 0, 0, '"+ dateFrom +"');";
				}
				DB.executeUpdateEx(sqlManage, get_TrxName());
				pstmt.close();				
				accounts.close();
			}
			
			
			

			docs.close();
						
 		}
		catch (Exception e)
		{
			throw e;
		}
	}		

	/**
	 * Carga datos de los cheques "rebotados" en tabla molde considerando filtros.
	 * @throws Exception 
	 */
	private void getDataBouncedChecks() throws Exception{
		
		String insert = "", sql = "", sqlManage = "", sqlAccounts = "", whereFiltros = ""; String dateFrom = "";
		PreparedStatement pstmt = null;  
		ResultSet docs, accounts = null;	
		
		try
		{
			// Obtengo id para este reporte
			this.idReporte = UtilReportes.getReportID(Long.valueOf(this.bankStatusFilters.adUserID));
			
			// Armo where de filtros
			if (this.bankStatusFilters.cBankAccountID > 0)
				whereFiltros += " AND c_bankaccount_id = " + this.bankStatusFilters.cBankAccountID;
			
			insert = "INSERT INTO " + TABLA_MOLDE + " (ad_user_id, ad_client_id, ad_org_id, c_bankaccount_id,amtsourcedr, amtsourcecr, payamt, totalamt, differenceamt, datetrx)";

			StringBuilder strb = new StringBuilder("");
			
			DateFormat format = new SimpleDateFormat( "yyyy-MM-dd 00:00:00" );
			dateFrom = format.format(this.bankStatusFilters.date);
			
			
			this.showHelp("Obteniendo datos...");			

			strb.append("select  ad_client_id, ad_org_id,c_bankaccount_id, 0, 0, CAST(sum(PAYAMT) as numeric(10,2)) as creditos, 0, 0 from uy_mediospago "  
						+"where tipomp='PRO'"
						+" and estado = 'REC'" 
						+" and duedate <= '" + dateFrom + "' " + whereFiltros 
						+" group by c_bankaccount_id, ad_client_id, ad_org_id;"); 

			
			sql = strb.toString();
			pstmt = DB.prepareStatement(sql, get_TrxName());
			docs  = pstmt.executeQuery();			
			
			while (docs.next()){
				sqlAccounts = "select c_bankaccount_id from "+ TABLA_MOLDE +" where c_bankaccount_id = "+ docs.getInt("c_bankaccount_id");
				pstmt = DB.prepareStatement(sqlAccounts, get_TrxName());
				accounts = pstmt.executeQuery();
				if (accounts.next()){
					sqlManage = " UPDATE " + TABLA_MOLDE + " SET payamt = payamt + " + docs.getInt("creditos") + " where c_bankaccount_id = " + docs.getInt("c_bankaccount_id"); 
				}else{
					sqlManage = insert + " VALUES (" + this.bankStatusFilters.adUserID + "," + docs.getInt("ad_client_id") +"," + docs.getInt("ad_org_id") + ", "
								+  docs.getInt("c_bankaccount_id") +", 0, 0, "+ docs.getInt("creditos") +", 0, 0, '"+ dateFrom +"');";
				}
				DB.executeUpdateEx(sqlManage, get_TrxName());
				pstmt.close();				
				accounts.close();
			}
			
			docs.close();
						
 		}
		catch (Exception e)
		{
			throw e;
		}
	}	
	
	/**
	 * Carga datos de los saldos reales de las cuentas bancarias
	 * @throws Exception 
	 */
	private void getDataCurrentBalance() throws Exception{
		
		String insert = "", sql = "", sqlManage = "", sqlAccounts = "", whereFiltros = ""; String dateFrom = "";
		PreparedStatement pstmt = null;  
		ResultSet docs, accounts = null;	
		
		try
		{
			// Obtengo id para este reporte
			this.idReporte = UtilReportes.getReportID(Long.valueOf(this.bankStatusFilters.adUserID));
			
			// Armo where de filtros
			if (this.bankStatusFilters.cBankAccountID > 0)
				whereFiltros += " AND c_bankaccount_id = " + this.bankStatusFilters.cBankAccountID;
			
			insert = "INSERT INTO " + TABLA_MOLDE + " (ad_user_id, ad_client_id, ad_org_id, c_bankaccount_id,amtsourcedr, amtsourcecr, payamt, totalamt, differenceamt, datetrx)";

			StringBuilder strb = new StringBuilder("");
			
	        Calendar cal = new GregorianCalendar();
	        cal.setTimeInMillis(this.bankStatusFilters.date.getTime());
	        cal.add(cal.MINUTE, -1);
			
			DateFormat format = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
			String dateAcc = format.format(new Timestamp(cal.getTimeInMillis()));
			
			format = new SimpleDateFormat( "yyyy-MM-dd 00:00:00" );
			dateFrom = format.format(this.bankStatusFilters.date);
			
			this.showHelp("Obteniendo datos...");			

			strb.append("select  ad_client_id, ad_org_id,c_bankaccount_id, 0, 0, 0,CAST(sum(amtsourcecr-amtsourcedr) as numeric(10,2)) as creditos, 0 from uy_sum_accountstatus "  
						+" where duedate <= '" + dateAcc + "' " + whereFiltros 
						+" group by c_bankaccount_id, ad_client_id, ad_org_id;"); 

			
			sql = strb.toString();
			pstmt = DB.prepareStatement(sql, get_TrxName());
			docs  = pstmt.executeQuery();			
			
			while (docs.next()){
				sqlAccounts = "select c_bankaccount_id from "+ TABLA_MOLDE +" where c_bankaccount_id = "+ docs.getInt("c_bankaccount_id");
				pstmt = DB.prepareStatement(sqlAccounts, get_TrxName());
				accounts = pstmt.executeQuery();
				if (accounts.next()){
					sqlManage = " UPDATE " + TABLA_MOLDE + " SET totalamt = "+ docs.getInt("creditos") +"  where c_bankaccount_id = " + docs.getInt("c_bankaccount_id"); 
				}else{
					sqlManage = insert + " VALUES (" + this.bankStatusFilters.adUserID + "," + docs.getInt("ad_client_id") +"," + docs.getInt("ad_org_id") + ", "
								+  docs.getInt("c_bankaccount_id") +", 0, 0, 0, "+ docs.getInt("creditos") +", 0, '"+ dateFrom +"');";
				}
				DB.executeUpdateEx(sqlManage, get_TrxName());
				pstmt.close();
				accounts.close();
			}
			
			
			docs.close();
						
 		}
		catch (Exception e)
		{
			throw e;
		}
	}	

	/**
	 * Carga datos de los saldos reales de las cuentas bancarias
	 * @throws Exception 
	 */
	private void getDataDifference() throws Exception{
		
		String sql = "";
		
		try
		{
			// Obtengo id para este reporte
			this.idReporte = UtilReportes.getReportID(Long.valueOf(this.bankStatusFilters.adUserID));
			
			sql = " UPDATE " + TABLA_MOLDE + " SET differenceamt = totalamt - amtsourcedr + amtsourcecr;";

			DB.executeUpdateEx(sql, get_TrxName());
			
						
 		}
		catch (Exception e)
		{
			throw e;
		}
	}		
	
	@Override
	protected String doIt() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public Waiting getWaiting() {
		return this.waiting;
	}
	
	public void setWaiting(Waiting value) {
		this.waiting = value;
	}
	
	private void showHelp(String text){
		if (this.getWaiting() != null){
			this.getWaiting().setText(text);
		}			
	}	
	

}
