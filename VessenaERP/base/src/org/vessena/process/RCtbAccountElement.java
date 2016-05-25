/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 01/12/2012
 */
package org.openup.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.compiere.apps.Waiting;
import org.compiere.model.MConversionRate;
import org.compiere.model.MElementValue;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.openup.beans.ReportAccountElement;


/**
 * org.openup.process - RCtbAccountElement
 * OpenUp Ltda. Issue #124 
 * Description: Consulta de información para Balance Contable.
 * @author Gabriel Vila - 01/12/2012
 * @see
 */
public class RCtbAccountElement {

	private ReportAccountElement reportFilters;
	private String idReporte = "";
	private static final String TABLA_MOLDE = "uy_molde_accountelement";
	private Waiting waiting = null;
	
	private int sequenceNumber = 0;
	
	
	/**
	 * Constructor. Recibo filtros seleccionados para este reporte.
	 */
	public RCtbAccountElement(ReportAccountElement filters) {
		this.reportFilters = filters; 
	}

	public Waiting getWaiting() {
		return this.waiting;
	}

	public void setWaiting(Waiting value) {
		this.waiting = value;
	}

	
	/**
	 * OpenUp. Gabriel Vila. 03/11/2011.
	 * Carga datos para el reporte.
	 */
	public void execute() throws Exception {

		// Si no tengo fecha hasta, aviso y salgo
		if (this.reportFilters.dateTo == null){
			throw new Exception("Debe indicar Fecha Hasta.");
		}
		
		// Obtengo id para este reporte
		this.idReporte = UtilReportes.getReportID(Long.valueOf(this.reportFilters.adUserID));
		
		this.deleteOldData();
		
		this.getData();
		
		this.updateData();
		
		this.deleteSaldoCero();
		
		this.deleteCuentasNivelFiltro();
		
		this.showHelp("Iniciando Vista Previa...");
	}



	/**
	 * Elimina corridas anteriores de este reporte para este usuario.
	 * @throws Exception 
	 */
	private void deleteOldData() throws Exception{
		
		String sql = "";
		try{
			
			sql = " DELETE FROM " + TABLA_MOLDE +
				  " WHERE ad_user_id =" + this.reportFilters.adUserID;
			
			DB.executeUpdateEx(sql,null);
		}
		catch (Exception e)
		{
			throw e;
		}
	}
		
	private void showHelp(String text){
	
		if (this.getWaiting() != null){
			this.getWaiting().setText(text);
		}
		
	}
	
	/***
	 * Obtiene y guarda en temporal, informacion de asientos segun filtros seleccionados.
	 * OpenUp Ltda. Issue #124 
	 * @author Gabriel Vila - 01/12/2012
	 * @see
	 * @throws Exception
	 */
	private void getData() throws Exception{
		
		String insert ="", sql = "", whereFiltros = "";

		try
		{
			// Armo where de filtro

			if (this.reportFilters.reportType.equalsIgnoreCase("RESULTADO")){
				whereFiltros += " and ev.AccountType IN ('R','E') ";
			}
			else if (this.reportFilters.reportType.equalsIgnoreCase("SITUACION")){
				whereFiltros += " and ev.AccountType IN ('A','L','O') ";
			}
			
			insert = " INSERT INTO " + TABLA_MOLDE + " (idreporte, fecreporte, ad_user_id, ad_client_id, ad_org_id," +
					 " c_acctschema_id, accounttype, parent_id, seqtree, account_id, account_value, account_name, " +
					 " account_saldo, account_saldo_factacct, account_saldome, account_saldome_factacct, " +
					 " nivel, seqno, ad_tree_id, issummary, datetrx) ";
			
			this.showHelp("Obteniendo cuentas...");
			
			// Arbol de cuentas
			sql = " SELECT '" + this.idReporte + "',current_date," + this.reportFilters.adUserID + "," + 
						this.reportFilters.adClientID + "," + this.reportFilters.adOrgID + "," +
						" scele.c_acctschema_id, ev.accounttype, tr.parent_id, tr.seqno, tr.node_id , " +
						" ev.value, ev.name, " +
						" 0, 0, 0, 0, 0, 0, tr.ad_tree_id, ev.issummary,'" + this.reportFilters.dateTo + "' " +
						" from ad_treenode tr " +
						" inner join c_element el on tr.ad_tree_id = el.ad_tree_id " +
						" inner join c_acctschema_element scele on (el.c_element_id = scele.c_element_id " +
						" and scele.ad_client_id =" + this.reportFilters.adClientID + 
						" and scele.c_acctschema_id=" + this.reportFilters.cAcctSchemaID + 
						" and scele.elementtype='AC') " +
						" inner join c_elementvalue ev on tr.node_id = ev.c_elementvalue_id" +
					    " WHERE tr.ad_client_id =" + this.reportFilters.adClientID + whereFiltros +
						" order by ev.value ";
			
			DB.executeUpdateEx(insert + sql, null);
			
 		}
		catch (Exception e)
		{
			throw e;
		}
	}

	/***
	 * Actualiza informacion asociada a las cuentas del arbol.
	 * OpenUp Ltda. Issue #124 
	 * @author Gabriel Vila - 01/12/2012
	 * @see
	 * @throws Exception
	 */
	private void updateData() throws Exception{

		this.updateCuentasNotSummary();
		this.updateCuentasSummary();
		
	}
	
	
	/***
	 * Actualiza saldo de cuentas que no son capitulos y que por lo tanto
	 * generan asientos contables.
	 * OpenUp Ltda. Issue #124 
	 * @author Gabriel Vila - 01/12/2012
	 * @see
	 * @throws Exception
	 */
	private void updateCuentasNotSummary() throws Exception{

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try{
			
			this.showHelp("Saldo Asientos ...");
			
			sql = " SELECT account_id " +
				  " FROM " + TABLA_MOLDE + 
				  " WHERE idreporte=? " +
				  " AND issummary =? ";
			
			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, null);
			pstmt.setString(1, this.idReporte);
			pstmt.setString(2, "N");
			
			rs = pstmt.executeQuery ();
			
			rs.last();
			int totalRowCount = rs.getRow(), rowCount = 0;
			rs.beforeFirst();
			
			String action = ""; 
			int accountID = 0;
			BigDecimal saldoCuentaMN = Env.ZERO, saldoCuentaME = Env.ZERO;
			
			// Tasa de cambio con fecha posterior a fecha hasta
			BigDecimal rate = Env.ONE;
			if (this.reportFilters.showOtherCurrency){
				Timestamp fechaTC = TimeUtil.trunc(TimeUtil.addDays(this.reportFilters.dateTo, 1), TimeUtil.TRUNC_DAY); 
				rate = MConversionRate.getRate(this.reportFilters.cCurrencyID, 142, fechaTC, 0, this.reportFilters.adClientID, 0);
			}
			
			while (rs.next()){
				
				this.showHelp("Saldos linea " + rowCount++ + " de " + totalRowCount);
				
				accountID = rs.getInt("account_id");

				BigDecimal saldoTR = Env.ZERO;
				
				// Si la moneda de la cuenta es pesos
				MElementValue ev = new MElementValue(Env.getCtx(), accountID, null);
				if ((ev.getC_Currency_ID() == 142) || ((ev.getC_Currency_ID() == 0))){
					saldoCuentaMN = this.getSaldoCuentaMN(accountID);
					saldoTR = saldoCuentaMN;
				}
				else{
					saldoCuentaMN = Env.ZERO; 
				}
				
				if (this.reportFilters.showOtherCurrency){
					if ((ev.getC_Currency_ID() != 142) && (ev.getC_Currency_ID() != 0)){
						saldoCuentaME = this.getSaldoCuentaME(accountID, this.reportFilters.cCurrencyID);
						saldoTR = this.getSaldoCuentaMN(accountID);
					}
					else{
						saldoCuentaME = Env.ZERO;
					}				
				}
									
				// Actualizo saldo
				action = " UPDATE " + TABLA_MOLDE + 
		 		 		 " SET account_saldo =" + saldoCuentaMN + "," + 
						 " account_saldo_factacct =" + saldoCuentaMN + "," +
						 " account_saldome =" + saldoCuentaME + "," + 
						 " account_saldome_factacct =" + saldoCuentaME + "," +
						 " account_saldotr =" + saldoTR +
		 		 		 " WHERE idreporte ='" + this.idReporte + "'" +
		 		 		 " AND account_id =" + accountID;
				DB.executeUpdateEx(action, null);
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
	}

	/***
	 * Obtiene saldo de cuenta recibida en moneda nacional.
	 * OpenUp Ltda. Issue #124 
	 * @author Gabriel Vila - 01/12/2012
	 * @see
	 * @param accountID
	 * @return
	 * @throws Exception
	 */
	private BigDecimal getSaldoCuentaMN(int accountID) throws Exception {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		BigDecimal value = Env.ZERO; 
		
		try{
			
			String whereFechas ="";
			
			if (this.reportFilters.dateFrom != null){
				whereFechas = " and dateacct >='" + this.reportFilters.dateFrom + "'";
			}
			if (this.reportFilters.dateTo != null){
				whereFechas += " and dateacct <='" + this.reportFilters.dateTo + "'";
			}
			
			String whereAsientoApertura = "";
			if (!this.reportFilters.includeInitialLode){
				whereAsientoApertura = " and record_id not in " +
									   "(select gl_journal_id from gl_journal where fact_acct.record_id = gl_journal.gl_journal_id " +
									   " and gl_journal.isinitialload='Y' and fact_acct.ad_table_id=224)";
			}
			
			sql = " select coalesce((sum(coalesce(amtacctdr,0)) - sum(coalesce(amtacctcr,0))),0) as saldo " +
				  " from fact_acct " +
				  " where c_acctschema_id =? " +
				  " and account_id =?" +
				  whereFechas + whereAsientoApertura;
			
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, this.reportFilters.cAcctSchemaID);
			pstmt.setInt(2, accountID);
			
			rs = pstmt.executeQuery ();

			if (rs.next()) value = rs.getBigDecimal(1);
			
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

	
	/***
	 * Obtiene saldo de cuenta recibida en moneda extranjera recibida.
	 * OpenUp Ltda. Issue #124 
	 * @author Gabriel Vila - 01/12/2012
	 * @see
	 * @param accountID
	 * @return
	 * @throws Exception
	 */
	private BigDecimal getSaldoCuentaME(int accountID, int cCurrencyID) throws Exception {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		BigDecimal value = Env.ZERO; 
		
		try{
			
			String whereFechas ="";
			
			if (this.reportFilters.dateFrom != null){
				whereFechas = " and dateacct >='" + this.reportFilters.dateFrom + "'";
			}
			if (this.reportFilters.dateTo != null){
				whereFechas += " and dateacct <='" + this.reportFilters.dateTo + "'";
			}
			
			String whereAsientoApertura = "";
			if (!this.reportFilters.includeInitialLode){
				whereAsientoApertura = " and record_id not in " +
									   "(select gl_journal_id from gl_journal where fact_acct.record_id = gl_journal.gl_journal_id " +
									   " and gl_journal.isinitialload='Y' and fact_acct.ad_table_id=224)";
			}
			
			sql = " select " +
				  " case when c_currency_id =" + cCurrencyID +
				  " then coalesce((sum(coalesce(amtsourcedr,0)) - sum(coalesce(amtsourcecr,0))),0) " +
				  " else 0 " +
				  " end as saldo " +
				  " from fact_acct " +
				  " where c_acctschema_id =? " +
				  " and account_id =?" +
				  " and c_currency_id =?" +
				  whereFechas + whereAsientoApertura +
				  " group by c_currency_id ";
			
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, this.reportFilters.cAcctSchemaID);
			pstmt.setInt(2, accountID);
			pstmt.setInt(3, cCurrencyID);
			
			rs = pstmt.executeQuery ();

			if (rs.next()) value = rs.getBigDecimal(1);
			
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

	
	/***
	 * Actualiza saldos de capitulos y orden de todas las cuentas
	 * OpenUp Ltda. Issue #124 
	 * @author Gabriel Vila - 01/12/2012
	 * @see
	 * @throws Exception
	 */
	private void updateCuentasSummary() throws Exception{

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try{
			
			this.showHelp("Calculando totales...");
			
			sql = " SELECT account_id " +
				  " FROM " + TABLA_MOLDE +
				  " WHERE idreporte=?" +
				  " AND parent_id =0" +
				  " ORDER BY seqtree ";
			
			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, null);
			pstmt.setString(1, this.idReporte);
			
			rs = pstmt.executeQuery ();
			
			rs.last();
			int totalRowCount = rs.getRow(), rowCount = 0;
			rs.beforeFirst();
			
			while (rs.next()){
				
				this.showHelp("Totales linea " + rowCount++ + " de " + totalRowCount);

				// Actualiza saldos de esta cuenta de manera recursiva
				this.sequenceNumber++;
				this.updateCuentasSummaryRecursive(rs.getInt("account_id"), 1);
				
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
	}

	/***
	 * Metodo recursivo que actualiza saldos, secuencia y nivel de cuentas
	 * OpenUp Ltda. Issue #124 
	 * @author Gabriel Vila - 01/12/2012
	 * @see
	 * @param int1
	 * @param i
	 * @throws Exception 
	 */
	private void updateCuentasSummaryRecursive(int parentID, int nivel) throws Exception {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try{
			
			// Antes de obtener hijos de esta cuenta, actualizo secuencia y nivel de cuenta padre
			String action = " update " + TABLA_MOLDE +
						    " set seqno =" + this.sequenceNumber + "," +
						    " nivel = " + nivel +
						    " where idreporte ='" + this.idReporte + "'" +
						    " and account_id =" + parentID;
			DB.executeUpdateEx(action, null);
			
			nivel++;
			
			// Obtengo hijas
			sql = " select account_id, issummary " +
				  " from " + TABLA_MOLDE +
				  " where idreporte =? " +
				  " and parent_id =?" + 
				  " order by seqtree ";
			
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setString(1, this.idReporte);
			pstmt.setInt(2, parentID);
			
			rs = pstmt.executeQuery ();

			// Actualizo hijas
			while (rs.next()){
				
				// Si cuenta hija es capitulo, la actualizo de manera secuencial
				if (rs.getString("issummary").equalsIgnoreCase("Y")){
					this.sequenceNumber++;
					this.updateCuentasSummaryRecursive(rs.getInt("account_id"), nivel);
				}
				else{
					
					// Actualizo secuencia y nivel de esta cuenta
					this.sequenceNumber++;
					action = " update " + TABLA_MOLDE +
						     " set seqno =" + this.sequenceNumber + "," +
						     " nivel = " + nivel +
						     " where idreporte ='" + this.idReporte + "'" +
						     " and account_id =" + rs.getInt("account_id");
					DB.executeUpdateEx(action, null);					
				}
			}
			
			// Actualizo saldos de cuenta padre
			ArrayList<Object> params = new ArrayList<Object>();
			params.add(this.idReporte);
			params.add(parentID);

			// Moneda Nacional
			String sqlhijas = " select coalesce(sum(account_saldo),0) as saldo " +
					          " from " + TABLA_MOLDE +
					          " where idreporte =? " +
					          " and parent_id =?";
			
			BigDecimal sumSaldoMN = DB.getSQLValueBDEx(null, sqlhijas, params);
			
			action = " update " + TABLA_MOLDE +
			 	     " set account_saldo =" + sumSaldoMN + 
				     " where idreporte ='" + this.idReporte + "'" +
				     " and account_id =" + parentID;
			DB.executeUpdateEx(action, null);

			// Moneda Extranjera
			if (this.reportFilters.showOtherCurrency){

				sqlhijas = " select coalesce(sum(account_saldome),0) as saldo " +
				           " from " + TABLA_MOLDE +
				           " where idreporte =? " +
				           " and parent_id =?";
		
				BigDecimal sumSaldoME = DB.getSQLValueBDEx(null, sqlhijas, params);
		
				action = " update " + TABLA_MOLDE +
				 	     " set account_saldome =" + sumSaldoME + 
					     " where idreporte ='" + this.idReporte + "'" +
					     " and account_id =" + parentID;
				DB.executeUpdateEx(action, null);				
			}
			
			// Traduccion
			sqlhijas = " select coalesce(sum(account_saldotr),0) as saldo " +
			           " from " + TABLA_MOLDE +
			           " where idreporte =? " +
			           " and parent_id =?";
	
			BigDecimal sumSaldoTR = DB.getSQLValueBDEx(null, sqlhijas, params);
	
			action = " update " + TABLA_MOLDE +
			 	     " set account_saldotr =" + sumSaldoTR + 
				     " where idreporte ='" + this.idReporte + "'" +
				     " and account_id =" + parentID;
			DB.executeUpdateEx(action, null);				
			
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

	/***
	 * Elimina cuentas sin saldo.	
	 * OpenUp Ltda. Issue #124 
	 * @author Gabriel Vila - 01/12/2012
	 * @see
	 * @throws Exception
	 */
	private void deleteSaldoCero() throws Exception{
		
		if (this.reportFilters.showAmtZeroAccounts) return;
		
		String sql = "";
		try{
			
			sql = " DELETE FROM " + TABLA_MOLDE +
				  " WHERE idreporte ='" + this.idReporte + "'" +
				  " AND account_saldo = 0 " +
				  " AND account_saldome = 0 ";
			
			this.showHelp("Eliminando sin saldo...");
			
			DB.executeUpdateEx(sql,null);
			
			// Si no muestra saldo en otra moneda, dejo saldome en null para que no salga en el RV
			if (!this.reportFilters.showOtherCurrency){
				sql = " update " + TABLA_MOLDE +
					  " set account_saldome = null " +
					  " where idreporte ='" + this.idReporte + "'";				
				DB.executeUpdateEx(sql,null);
			}
			
		}
		catch (Exception e)
		{
			throw e;
		}
	}
	
	/***
	 * Elimina cuentas con mayor nivel que el seleccionado en los filtros
	 * OpenUp Ltda. Issue #124 
	 * @author Hp - 01/12/2012
	 * @see
	 * @throws Exception
	 */
	private void deleteCuentasNivelFiltro() throws Exception{
		
		String sql = "";
		try{
			
			sql = " DELETE FROM " + TABLA_MOLDE +
				  " WHERE idreporte ='" + this.idReporte + "'" +
				  " AND nivel >" + this.reportFilters.nivel;	

			DB.executeUpdateEx(sql,null);
		}
		catch (Exception e)
		{
			throw e;
		}
	}
}
