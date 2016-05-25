/**
 * 
 */
package org.openup.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import org.compiere.apps.Waiting;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.openup.beans.ReportBankAccountStatus;

/**
 * @author OpenUp. Gabriel Vila. 28/11/2011. Issue #902.
 * Logica del reporte estado de cuenta bancario.
 */
public class RBankAccountStatus {

	private ReportBankAccountStatus bankAcctStatusFilters;
	private String idReporte = "";
	private static final String TABLA_MOLDE = "uy_molde_bankaccountstatus";
	private Waiting waiting = null;

	/**
	 * Constructor. Recibo filtros seleccionados para este reporte.
	 * @param openAmtFilters
	 */
	public RBankAccountStatus(ReportBankAccountStatus bankAcctStatusFilters) {
		this.bankAcctStatusFilters = bankAcctStatusFilters;
	}

	public Waiting getWaiting() {
		return this.waiting;
	}

	public void setWaiting(Waiting value) {
		this.waiting = value;
	}

	/**
	 * OpenUp. Gabriel Vila. 28/11/2011.
	 * Carga datos para el reporte.
	 */
	public void execute() throws Exception {

		this.deleteInstanciasViejasReporte();
		
		this.getData();
		
		this.updateData();
		
		this.showHelp("Iniciando Vista Previa...");
	}



	/**
	 * Elimina corridas anteriores de este reporte para este usuario.
	 * @throws Exception 
	 */
	private void deleteInstanciasViejasReporte() throws Exception{
		
		String sql = "";
		try{
			
			sql = " DELETE FROM " + TABLA_MOLDE +
				  " WHERE ad_user_id =" + this.bankAcctStatusFilters.adUserID;
			
			DB.executeUpdateEx(sql,null);
		}
		catch (Exception e)
		{
			throw e;
		}
	}

	/**
	 * Carga datos en tabla molde considerando filtros.
	 * @throws Exception 
	 */
	private void getData() throws Exception{
		
		String insert ="", sql = "", whereFiltros = "";

		try
		{
			// Obtengo id para este reporte
			this.idReporte = UtilReportes.getReportID(Long.valueOf(this.bankAcctStatusFilters.adUserID));
			
			// Armo where de filtros
			if (this.bankAcctStatusFilters.cBankAccountID > 0)
				whereFiltros += " AND v.c_bankaccount_id = " + this.bankAcctStatusFilters.cBankAccountID;
			
			if (this.bankAcctStatusFilters.cdocTypeID > 0)
				whereFiltros += " AND v.c_doctype_id = " + this.bankAcctStatusFilters.cdocTypeID;
		
			//OpenUp. Nicolas Sarlabos. 19/02/2016. #5264. Agrego nuevos campos.
			insert = "INSERT INTO " + TABLA_MOLDE + " (idreporte, fecreporte, ad_user_id, ad_client_id, ad_org_id," +
					" c_bankaccount_id, record_id, documentno, c_doctype_id," +					
					" docname, startdate, datetrx, amtdocument, saldoinicial, debe, haber, saldoacumulado, checkno," +
					" c_bpartner_id, description, created, uy_sum_accountstatus_id, qty, uy_paymentrule_id)";
			//Fin OpenUp.
			
			StringBuilder strb = new StringBuilder("");
			
			Timestamp dateFrom = TimeUtil.trunc(this.bankAcctStatusFilters.dateFrom, TimeUtil.TRUNC_DAY);
			Timestamp dateTo = TimeUtil.trunc(this.bankAcctStatusFilters.dateTo, TimeUtil.TRUNC_DAY);
			
			this.showHelp("Obteniendo datos...");
			
			String whereOrg = "";
			/*
			if (this.bankAcctStatusFilters.adOrgID > 0){
				whereOrg = " AND v.ad_org_id =" + this.bankAcctStatusFilters.adOrgID;
			}
			*/
			
			//OpenUp. Nicolas Sarlabos. 19/02/2016. #5264. Agrego nuevos campos.
			strb.append(" SELECT '" + this.idReporte + "',current_date," + this.bankAcctStatusFilters.adUserID + "," +
						this.bankAcctStatusFilters.adClientID + "," + this.bankAcctStatusFilters.adOrgID + "," +
					    " v.c_bankaccount_id, v.record_id, v.documentno, v.c_doctype_id, doc.printname, " +
					    " v.duedate, v.datetrx, 0, 0, coalesce(v.amtsourcedr,0) as debe, coalesce(v.amtsourcecr,0) as haber, 0," +
					    " coalesce(v.checkno,'') as checkno, v.c_bpartner_id, v.description, v.created, v.uy_sum_accountstatus_id, v.qty, v.uy_paymentrule_id " +
						" from uy_sum_accountstatus v " +
					    " inner join c_doctype doc on v.c_doctype_id = doc.c_doctype_id " +
					    " WHERE v.ad_client_id =" + this.bankAcctStatusFilters.adClientID +
					    whereOrg +
					    //" AND v.datetrx between '" + dateFrom + "' AND '" + dateTo + "' " +
					    " AND v.duedate >='" + dateFrom + "' AND v.duedate <='" + dateTo + "' " +
					    " AND v.IsBeforeBalance ='" + (this.bankAcctStatusFilters.beforeBalance ? "Y" : "N") + "' "   + whereFiltros +  
					    " ORDER BY v.duedate, v.created, v.uy_sum_accountstatus_id");
			//Fin OpenUp.
			
			sql = strb.toString();
			
			DB.executeUpdateEx(insert + sql, null);
						
 		}
		catch (Exception e)
		{
			throw e;
		}
	}

	/**
	 * Calcula campos de saldos. 
	 * @throws Exception
	 */
	private void updateData() throws Exception{

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try{
			
			this.showHelp("Calculo de saldos...");
			
			sql = " SELECT * " +
				  " FROM " + TABLA_MOLDE +
				  " WHERE idreporte=?" +
				  " ORDER BY startdate, created, uy_sum_accountstatus_id";
			
			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, null);
			pstmt.setString(1, this.idReporte);
			
			rs = pstmt.executeQuery ();
			
			rs.last();
			int totalRowCount = rs.getRow(), rowCount = 0;
			rs.beforeFirst();
			
			//OpenUp. Nicolas Sarlabos. 11/09/2013. #1302
			if (totalRowCount <= 0){
				this.updateSoloConSaldoInicial();
				return;
			}
			//Fin OpenUp.
			
			String action = ""; 
			BigDecimal saldoAcumulado = Env.ZERO, saldoInicial = Env.ZERO, debe = Env.ZERO, haber = Env.ZERO;
			int cBankAccountID = 0, cBankAccountIDAux = 0, cDocTypeID = 0, recordID = 0;
			
			// Corte de control por socio de negocio
			while (rs.next()){

				this.showHelp("Procesando linea " + rowCount++ + " de " + totalRowCount);
				
				cDocTypeID = rs.getInt("c_doctype_id");
				recordID = rs.getInt("record_id");
				cBankAccountIDAux = rs.getInt("c_bankaccount_id");

				// Si hay cambio de cuenta bancaria
				if (cBankAccountIDAux != cBankAccountID){
					cBankAccountID = cBankAccountIDAux;
					saldoInicial = getSaldoInicial(cBankAccountID);
					saldoAcumulado = saldoInicial;
				}
				
				debe = rs.getBigDecimal("debe");
				haber = rs.getBigDecimal("haber");
				
				if (debe.compareTo(Env.ZERO) != 0) saldoAcumulado = saldoAcumulado.subtract(debe);
				if (haber.compareTo(Env.ZERO) != 0) saldoAcumulado = saldoAcumulado.add(haber);						
				
				String whereCheckNo = "";
				if (rs.getString("checkno") != null){
					if (!rs.getString("checkno").equalsIgnoreCase("")){
						whereCheckNo = " AND checkno ='" + rs.getString("checkno") + "' ";	
					}
					
				}
				
				action = " UPDATE " + TABLA_MOLDE + 
		 		 		 " SET saldoacumulado =" + saldoAcumulado + "," +
  	 		 		     " saldoinicial = " + saldoInicial + 
		 		 		 " WHERE idreporte ='" + this.idReporte + "'" +
		 		 		 " AND startdate ='" + rs.getTimestamp("startdate") + "'" +
		 		 		 " AND c_doctype_id =" + cDocTypeID +
		 		 		 " AND record_id = " + recordID + whereCheckNo;
				DB.executeUpdateEx(action, null);
			}
			
			this.deleteSaldosCero();
			
		}
		catch (Exception e)
		{
			throw e;
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
	}

	/**
	 * OpenUp. Gabriel Vila. 28/11/2011. Issue #902.
	 * Obtiene y retorna saldo inicial de una cuenta bancaria.
	 * @param cBankAccountID
	 * @return
	 * @throws Exception 
	 */
	private BigDecimal getSaldoInicial(int cBankAccountID) throws Exception {
		return this.getSaldoInicialHaber(cBankAccountID).subtract(this.getSaldoInicialDebe(cBankAccountID));
	}

	/**
	 * Obtiene saldo al debe de una determinada cuenta bancaria.
	 * @param cBPartnerID
	 * @return
	 * @throws Exception
	 */
	private BigDecimal getSaldoInicialDebe(int cBankAccountID) throws Exception {
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		BigDecimal value = new BigDecimal(0);
		
		try{
			sql =" SELECT COALESCE(SUM(v.amtsourcedr),0) as saldo " + 
				 " FROM uy_sum_accountstatus v " +
				 " WHERE v.AD_Client_ID =" + this.bankAcctStatusFilters.adClientID + 
				 //" AND v.AD_Org_ID =" + this.bankAcctStatusFilters.adOrgID +
				 " AND v.duedate <? " +
				 " AND v.c_bankaccount_id=" + cBankAccountID +
				 " AND v.IsBeforeBalance ='" + (this.bankAcctStatusFilters.beforeBalance ? "Y" : "N") + "' ";				 
			
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setTimestamp(1, this.bankAcctStatusFilters.dateFrom);			
			rs = pstmt.executeQuery();
		
			if (rs.next()){
				value = rs.getBigDecimal("saldo");
			}
		}
		catch (Exception e)
		{
			throw e;
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		return value;

	}
	
	/**
	 * Obtiene saldo al haber de una determinada cuenta bancaria.
	 * @param cBPartnerID
	 * @return
	 * @throws Exception
	 */
	private BigDecimal getSaldoInicialHaber(int cBankAccountID) throws Exception {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		BigDecimal value = new BigDecimal(0);
		
		try{
			sql ="SELECT COALESCE(SUM(v.amtsourcecr),0) as saldo " + 
		 		  	" FROM uy_sum_accountstatus v " +
		 			 " WHERE v.AD_Client_ID =" + this.bankAcctStatusFilters.adClientID + 
					 //" AND v.AD_Org_ID =" + this.bankAcctStatusFilters.adOrgID +
					 " AND v.duedate <? " +
					 " AND v.c_bankaccount_id=" + cBankAccountID +
					 " AND v.IsBeforeBalance ='" + (this.bankAcctStatusFilters.beforeBalance ? "Y" : "N") + "' ";					 
			
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setTimestamp(1, this.bankAcctStatusFilters.dateFrom);			
			rs = pstmt.executeQuery();
		
			if (rs.next()){
				value = rs.getBigDecimal("saldo");
			}
		}
		catch (Exception e)
		{
			throw e;
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
		return value;
		
	}

	/**
	 * Elimina registros de temporal con saldos en cero.
	 * @throws Exception 
	 */
	private void deleteSaldosCero() throws Exception{
		
		String sql = "";
		try{
			
			sql = " DELETE FROM " + TABLA_MOLDE +
					" WHERE idreporte ='" + this.idReporte + "'" +
					" AND debe = 0 " +
					" AND haber = 0";
			
			DB.executeUpdateEx(sql,null);
		}
		catch (Exception e)
		{
			throw e;
		}
	}
	
	/**
	 * OpenUp. Nicolas Sarlabos. 11/09/2013. Issue #1302
	 * Inserta solo saldos iniciales cuando no se obtuvieron datos 
	 * @throws Exception 
	 */
	private void updateSoloConSaldoInicial() throws Exception{

		String insert = "", select = "";
		int accountID = 0;
		BigDecimal saldoInicial = Env.ZERO;

		accountID = this.bankAcctStatusFilters.cBankAccountID;
		this.bankAcctStatusFilters.beforeBalance = false;
		saldoInicial = getSaldoInicial(accountID);
		
		

		if(saldoInicial.compareTo(Env.ZERO) != 0) {

			insert = "INSERT INTO " + TABLA_MOLDE + " (idreporte, fecreporte, ad_user_id, ad_client_id, ad_org_id," +
					" c_bankaccount_id, record_id, documentno, c_doctype_id," +					
					" docname, startdate, datetrx, amtdocument, saldoinicial, debe, haber, saldoacumulado, checkno, c_bpartner_id, description)";

			select = " SELECT '" + this.idReporte + "',current_date," + this.bankAcctStatusFilters.adUserID + "," + this.bankAcctStatusFilters.adClientID + "," + 
					this.bankAcctStatusFilters.adOrgID + "," + accountID + ",null,null,null,null,null,null,null," + saldoInicial + ",null,null," + saldoInicial + ",null,null,null";

			DB.executeUpdateEx(insert + select, null);	

		}
	}

	private void showHelp(String text){
		if (this.getWaiting() != null){
			this.getWaiting().setText(text);
		}			
	}
}
