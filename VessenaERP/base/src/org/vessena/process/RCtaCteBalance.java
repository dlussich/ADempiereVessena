/**
 * 
 */
package org.openup.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import org.compiere.apps.Waiting;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MBPartner;
import org.compiere.model.MClient;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.openup.beans.ReportBalanceCtaCte;
import org.openup.beans.ReportOpenAmt;

/**
 * @author OpenUp. Gabriel Vila. 03/11/2011. Issue #902.
 * Logica del reporte balance cuenta corriente.
 */
public class RCtaCteBalance {

	private ReportBalanceCtaCte balanceFilters;
	private String idReporte = "";
	private static final String TABLA_MOLDE = "uy_molde_balance_ctacte";
	private Waiting waiting = null;

	/**
	 * Constructor. Recibo filtros seleccionados para este reporte.
	 * @param openAmtFilters
	 */
	public RCtaCteBalance(ReportBalanceCtaCte balanceFilters) {
		this.balanceFilters = balanceFilters;
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

		// Delete de reportes anteriores de este usuario para ir limpiando la tabla molde
		this.deleteInstanciasViejasReporte();
		
		this.getData();
		
		this.updateData();
	
		this.deleteSaldosCero();
		
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
				  " WHERE ad_user_id =" + this.balanceFilters.adUserID;
			
			DB.executeUpdateEx(sql,null);
		}
		catch (Exception e)
		{
			throw e;
		}
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
					" AND saldo_abierta_mn = 0 " +
					" AND saldo_abierta_me = 0 " +
					" AND saldo_doc_mn = 0 " +
					" AND saldo_doc_me = 0 ";
			
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
			this.idReporte = UtilReportes.getReportID(Long.valueOf(this.balanceFilters.adUserID));
			//OpenUp. Nicolas Sarlabos. 16/07/2013. #1143
			// Armo where de filtros
			if (this.balanceFilters.customerID > 0) {

				MBPartner partner = new MBPartner(Env.getCtx(),this.balanceFilters.customerID,null);//instancio cliente

				//si es un cliente padre
				if(partner.isParent()){

					whereFiltros += " AND st.c_bpartner_id IN (select c_bpartner_id from c_bpartner where bpartner_parent_id = " + this.balanceFilters.customerID + " OR c_bpartner_id = " + this.balanceFilters.customerID + ")";

				} else whereFiltros += " AND st.c_bpartner_id = " + this.balanceFilters.customerID;

			} 			
			//Fin OpenUp. #1143
			if (this.balanceFilters.vendorID > 0)
				whereFiltros += " AND st.c_bpartner_id = " + this.balanceFilters.vendorID;
	
			insert = "INSERT INTO " + TABLA_MOLDE + " (idreporte, fecreporte, ad_user_id, ad_client_id, ad_org_id," +
					" dateto, c_bpartner_id, bpvalue, bpname, c_currency_id, saldo_abierta_mn, saldo_abierta_me," +
					" saldo_doc_mn, saldo_doc_me)";

			StringBuilder strb = new StringBuilder("");

			Timestamp dateTo = TimeUtil.trunc(this.balanceFilters.dateTo, TimeUtil.TRUNC_DAY);
			
			String whereOrg = "";
			if (this.balanceFilters.adOrgID > 0) 
				whereOrg = " AND st.ad_org_id =" + this.balanceFilters.adOrgID;

			
			this.showHelp("Obteniendo datos...");
			
			// Invoices
			strb.append(" SELECT DISTINCT '" + this.idReporte + "',current_date," + this.balanceFilters.adUserID + "," + 
						this.balanceFilters.adClientID + "," + this.balanceFilters.adOrgID + ",'" + dateTo + "'::timestamp without time zone, " +
						"st.c_bpartner_id, bp.value as bpvalue, (bp.name || ' - ' ||coalesce(bp.name2,'')) as bpname, " +
						this.balanceFilters.cCurrencyID + ",0,0,0,0 " +
						" from c_invoice st " +
						" inner join c_doctype doc ON st.c_doctype_id = doc.c_doctype_id" +
					    " inner join c_bpartner bp on st.c_bpartner_id = bp.c_bpartner_id " +
					    " WHERE st.ad_client_id =" + this.balanceFilters.adClientID +
					    whereOrg +
					    " AND st.dateinvoiced <= '" + dateTo + "' " + 
					    " AND st.docstatus = 'CO' " + 
						" AND st.paymentruletype !='CO' " +
					    " AND doc.allocationbehaviour IN ('INV','PAY') " + whereFiltros);

			if (this.balanceFilters.partnerType != null){

				if (this.balanceFilters.partnerType.equalsIgnoreCase(ReportBalanceCtaCte.PARTNER_CLIENTE))
					strb.append(" AND st.issotrx='Y' ");

				if (this.balanceFilters.partnerType.equalsIgnoreCase(ReportBalanceCtaCte.PARTNER_PROVEEDOR))
					strb.append(" AND st.issotrx='N' ");
				
			}
			else{
				if (this.balanceFilters.cBPGroupID == ReportOpenAmt.GRUPO_CLIENTE)
					strb.append(" AND st.issotrx='Y' ");

				if (this.balanceFilters.cBPGroupID == ReportOpenAmt.GRUPO_PROVEEDOR)
					strb.append(" AND st.issotrx='N' ");
				
			}
			


			// Payments
			strb.append(" UNION SELECT DISTINCT '" + this.idReporte + "',current_date," + this.balanceFilters.adUserID + "," +
						this.balanceFilters.adClientID + "," + this.balanceFilters.adOrgID + ",'" + dateTo + "'::timestamp without time zone, " +
						"st.c_bpartner_id, bp.value as bpvalue, (bp.name || ' - ' ||coalesce(bp.name2,'')) as bpname, " +
						this.balanceFilters.cCurrencyID + ",0,0,0,0 " +
						" from c_payment st " +
						" inner join c_doctype doc ON st.c_doctype_id = doc.c_doctype_id" +
					    " inner join c_bpartner bp on st.c_bpartner_id = bp.c_bpartner_id " +
					    " WHERE st.ad_client_id =" + this.balanceFilters.adClientID +
					    whereOrg +
					    " AND st.datetrx <= '" + dateTo + "' " + 
					    " AND st.docstatus = 'CO' " + 
						" AND st.isreconciled='N' " +
					    " AND doc.allocationbehaviour='PAY' " + whereFiltros);

			if (this.balanceFilters.partnerType != null){

				if (this.balanceFilters.partnerType.equalsIgnoreCase(ReportBalanceCtaCte.PARTNER_CLIENTE))
					strb.append(" AND st.isreceipt='Y' ");

				if (this.balanceFilters.partnerType.equalsIgnoreCase(ReportBalanceCtaCte.PARTNER_PROVEEDOR))
					strb.append(" AND st.isreceipt='N' ");
				
			}
			else{
				if (this.balanceFilters.cBPGroupID == ReportOpenAmt.GRUPO_CLIENTE)
					strb.append(" AND st.isreceipt='Y' ");

				if (this.balanceFilters.cBPGroupID == ReportOpenAmt.GRUPO_PROVEEDOR)
					strb.append(" AND st.isreceipt='N' ");
			}
			

			
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
			
			MClient client = new MClient(Env.getCtx(), this.balanceFilters.adClientID, null);
			MAcctSchema schema = client.getAcctSchema();

			
			this.showHelp("Calculo de saldos...");
			
			sql = " SELECT * " +
				  " FROM " + TABLA_MOLDE +
				  " WHERE idreporte=?";
			
			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, null);
			pstmt.setString(1, this.idReporte);
			
			rs = pstmt.executeQuery ();
			
			rs.last();
			int totalRowCount = rs.getRow(), rowCount = 0;
			rs.beforeFirst();
			
			String action = "";
			int cBPartnerID = 0;
			int cCurrencyMN = schema.getC_Currency_ID();
			BigDecimal saldoAbiertoMN = Env.ZERO, saldoAbiertoME = Env.ZERO, saldoDocMN = Env.ZERO, saldoDocME = Env.ZERO;
			
			// Corte de control por socio de negocio
			while (rs.next()){

				this.showHelp("Procesando linea " + rowCount++ + " de " + totalRowCount);
				
				cBPartnerID = rs.getInt("c_bpartner_id");

				saldoAbiertoMN = getSaldoAbierta(cBPartnerID, cCurrencyMN);
				saldoAbiertoME = getSaldoAbierta(cBPartnerID, this.balanceFilters.cCurrencyID);				
				saldoDocMN = getSaldoDocumentada(cBPartnerID, cCurrencyMN);
				saldoDocME = getSaldoDocumentada(cBPartnerID, this.balanceFilters.cCurrencyID);
				
				action = " UPDATE " + TABLA_MOLDE + 
		 		 		 " SET saldo_abierta_mn =" + saldoAbiertoMN + "," +
						 " saldo_abierta_me =" + saldoAbiertoME + "," +
						 " saldo_doc_mn =" + saldoDocMN + "," +
						 " saldo_doc_me =" + saldoDocME + 
		 		 		 " WHERE idreporte ='" + this.idReporte + "'" +
		 		 		 " AND c_bpartner_id =" + cBPartnerID;
				
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

	/**
	 * OpenUp. Gabriel Vila. 08/11/2011. Issue #902	
	 * Obtiene saldo cuenta abierta para un determinado socio de negocio - moneda.
	 * @return
	 * @throws Exception 
	 */
	private BigDecimal getSaldoAbierta(int cBPartnerID, int cCurrencyID) throws Exception{
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		BigDecimal value = Env.ZERO, saldoFacturas = Env.ZERO, saldoNotasCredito = Env.ZERO, saldoRecibos = Env.ZERO; 
		
		Timestamp dateTo = TimeUtil.trunc(this.balanceFilters.dateTo, TimeUtil.TRUNC_DAY);
		
		try{

			String whereOrg = "";
			if (this.balanceFilters.adOrgID > 0) 
				whereOrg = " AND st.ad_org_id =" + this.balanceFilters.adOrgID;
			
			// Saldo Facturas
			sql = "SELECT COALESCE(SUM(st.grandtotal),0) as saldo " +
				  " FROM C_Invoice st INNER JOIN C_DocType doc ON st.c_doctype_id = doc.c_doctype_id " +
				  " WHERE st.ad_client_id =" + this.balanceFilters.adClientID +
				  whereOrg +
				  " AND st.dateinvoiced <= '" + dateTo + "' " + 
				  " AND st.docstatus = 'CO' " +
				  " AND st.paymentruletype !='CO' " +
				  " AND st.c_bpartner_id = " + cBPartnerID +
				  " AND st.c_currency_id = " + cCurrencyID +
				  " AND doc.allocationbehaviour ='INV'" +
				  " AND doc.value NOT IN('devcontado') ";

			if (this.balanceFilters.partnerType != null){
				if (this.balanceFilters.partnerType.equalsIgnoreCase(ReportBalanceCtaCte.PARTNER_CLIENTE))
					sql += " AND st.issotrx='Y'";
				if (this.balanceFilters.partnerType.equalsIgnoreCase(ReportBalanceCtaCte.PARTNER_PROVEEDOR))
					sql += " AND st.issotrx='N'";				
			}
			else{
				if (this.balanceFilters.cBPGroupID == ReportOpenAmt.GRUPO_CLIENTE)
					sql += " AND st.issotrx='Y'";
				if (this.balanceFilters.cBPGroupID == ReportOpenAmt.GRUPO_PROVEEDOR)
					sql += " AND st.issotrx='N'";				
			}
			

			
			pstmt = DB.prepareStatement(sql, null);
			rs = pstmt.executeQuery();
			if (rs.next()) saldoFacturas = rs.getBigDecimal("saldo");
			
			//OpenUp. Nicolas Sarlabos. 25/11/2013. #1651.
			BigDecimal allocInv = getTotalAfectado("alloc_invoiceamtopenxdate",cBPartnerID,cCurrencyID);
			saldoFacturas = saldoFacturas.subtract(allocInv); 
			//Fin OpenUp.
			
			DB.close(rs, pstmt);
			rs = null; pstmt = null;

			// Saldo Notas de Credito
			sql = "SELECT COALESCE(SUM(st.grandtotal),0) as saldo " +
				  " FROM C_Invoice st INNER JOIN C_DocType doc ON st.c_doctype_id = doc.c_doctype_id " +
				  " WHERE st.ad_client_id =" + this.balanceFilters.adClientID +
				  whereOrg +
				  " AND st.dateinvoiced <= '" + dateTo + "' " + 
				  " AND st.docstatus = 'CO' " +
				  " AND st.paymentruletype !='CO' " +
				  " AND st.c_bpartner_id = " + cBPartnerID +
				  " AND st.c_currency_id = " + cCurrencyID +
				  " AND doc.allocationbehaviour ='PAY'" +
				  " AND doc.value NOT IN('devcontado') "; 

			if (this.balanceFilters.partnerType != null){
				if (this.balanceFilters.partnerType.equalsIgnoreCase(ReportBalanceCtaCte.PARTNER_CLIENTE))
					sql += " AND st.issotrx='Y'";
				if (this.balanceFilters.partnerType.equalsIgnoreCase(ReportBalanceCtaCte.PARTNER_PROVEEDOR))
					sql += " AND st.issotrx='N'";
				
			}
			else{
				if (this.balanceFilters.cBPGroupID == ReportOpenAmt.GRUPO_CLIENTE)
					sql += " AND st.issotrx='Y'";
				if (this.balanceFilters.cBPGroupID == ReportOpenAmt.GRUPO_PROVEEDOR)
					sql += " AND st.issotrx='N'";
				
			}

			pstmt = DB.prepareStatement(sql, null);
			rs = pstmt.executeQuery();
			if (rs.next()) saldoNotasCredito = rs.getBigDecimal("saldo");
			
			//OpenUp. Nicolas Sarlabos. 25/11/2013. #1651.
			BigDecimal allocNc = getTotalAfectado("alloc_creditnoteamtopenxdate",cBPartnerID,cCurrencyID);
			saldoNotasCredito = saldoNotasCredito.subtract(allocNc);
			//Fin OpenUp.
			
			DB.close(rs, pstmt);
			rs = null; pstmt = null;

			// Saldo Recibos
			sql = "SELECT COALESCE(SUM(st.payamt),0) as saldo " +
				  " FROM c_payment st INNER JOIN C_DocType doc ON st.c_doctype_id = doc.c_doctype_id " +
				  " WHERE st.ad_client_id =" + this.balanceFilters.adClientID +
				  whereOrg +
				  " AND st.datetrx <= '" + dateTo + "' " + 
				  " AND st.docstatus = 'CO' " +
				  " AND st.isreconciled='N' " +
				  " AND st.c_bpartner_id = " + cBPartnerID +
				  " AND st.c_currency_id = " + cCurrencyID +
				  " AND doc.allocationbehaviour ='PAY'"; 

			if (this.balanceFilters.partnerType != null){
				if (this.balanceFilters.partnerType.equalsIgnoreCase(ReportBalanceCtaCte.PARTNER_CLIENTE))
					sql += " AND st.isreceipt='Y' ";

				if (this.balanceFilters.partnerType.equalsIgnoreCase(ReportBalanceCtaCte.PARTNER_PROVEEDOR))
					sql += " AND st.isreceipt='N' ";
				
			}
			else{
				if (this.balanceFilters.cBPGroupID == ReportOpenAmt.GRUPO_CLIENTE)
					sql += " AND st.isreceipt='Y' ";

				if (this.balanceFilters.cBPGroupID == ReportOpenAmt.GRUPO_PROVEEDOR)
					sql += " AND st.isreceipt='N' ";
				
			}
			
			pstmt = DB.prepareStatement(sql, null);
			rs = pstmt.executeQuery();
			if (rs.next()) saldoRecibos = rs.getBigDecimal("saldo");
			
			//OpenUp. Nicolas Sarlabos. 25/11/2013. #1651.
			BigDecimal allocPay = getTotalAfectado("alloc_paymentamtopenxdate",cBPartnerID,cCurrencyID);
			saldoRecibos = saldoRecibos.subtract(allocPay);
			//Fin OpenUp.

			if (this.balanceFilters.partnerType != null){
				// Deudores Saldo cuenta abierta = facturas - notas de credito - recibos
				if (this.balanceFilters.partnerType.equalsIgnoreCase(ReportBalanceCtaCte.PARTNER_CLIENTE))
					value = (saldoFacturas.subtract(saldoNotasCredito)).subtract(saldoRecibos);
				// Proveedores Saldo cuenta abierta = recibos + notas de credito - facturas
				if (this.balanceFilters.partnerType.equalsIgnoreCase(ReportBalanceCtaCte.PARTNER_PROVEEDOR))	
					value = (saldoRecibos.add(saldoNotasCredito)).subtract(saldoFacturas);
				
			}
			else{
				// Deudores Saldo cuenta abierta = facturas - notas de credito - recibos
				if (this.balanceFilters.cBPGroupID == ReportOpenAmt.GRUPO_CLIENTE)
					value = (saldoFacturas.subtract(saldoNotasCredito)).subtract(saldoRecibos);
				// Proveedores Saldo cuenta abierta = recibos + notas de credito - facturas
				if (this.balanceFilters.cBPGroupID == ReportOpenAmt.GRUPO_PROVEEDOR)	
					value = (saldoRecibos.add(saldoNotasCredito)).subtract(saldoFacturas);
				
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
	 * OpenUp. Gabriel Vila. 08/11/2011. Issue #902	
	 * Obtiene saldo cuenta abierta para un determinado socio de negocio - moneda.
	 * @return
	 * @throws Exception 
	 */
	private BigDecimal getSaldoDocumentada(int cBPartnerID, int cCurrencyID) throws Exception{
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		BigDecimal value = Env.ZERO; 
		
		Timestamp dateTo = TimeUtil.trunc(this.balanceFilters.dateTo, TimeUtil.TRUNC_DAY);
		
		try{

			String whereOrg = "";
			if (this.balanceFilters.adOrgID > 0) 
				whereOrg = " AND mp.ad_org_id =" + this.balanceFilters.adOrgID;
			
			sql = "SELECT COALESCE(SUM(mp.payamt),0) as saldo " +
				  " FROM uy_mediospago mp " +
				  " INNER JOIN uy_linepayment lp ON mp.uy_mediospago_id = lp.uy_mediospago_id " +
				  " INNER JOIN c_payment payment ON lp.c_payment_id = payment.c_payment_id " +
				  " WHERE mp.ad_client_id =" + this.balanceFilters.adClientID +
				  whereOrg +
				  " AND mp.duedate >= '" + dateTo + "' " +
				  " AND mp.estado!='REC' "  +
				  " AND mp.docstatus = 'CO' " +				  
				  " AND mp.c_bpartner_id = " + cBPartnerID +
				  " AND mp.c_currency_id = " + cCurrencyID +
				  " AND payment.docstatus='CO'";

			if (this.balanceFilters.partnerType != null){
				if (this.balanceFilters.partnerType.equalsIgnoreCase(ReportBalanceCtaCte.PARTNER_CLIENTE))
					sql += " AND mp.tipomp='TER'";
				if (this.balanceFilters.partnerType.equalsIgnoreCase(ReportBalanceCtaCte.PARTNER_PROVEEDOR))
					sql += " AND mp.tipomp='PRO'";
				
			}
			else{
				if (this.balanceFilters.cBPGroupID == ReportOpenAmt.GRUPO_CLIENTE)
					sql += " AND mp.tipomp='TER'";
				if (this.balanceFilters.cBPGroupID == ReportOpenAmt.GRUPO_PROVEEDOR)
					sql += " AND mp.tipomp='PRO'";
				
			}
			
			
			pstmt = DB.prepareStatement(sql, null);
			rs = pstmt.executeQuery();

			if (rs.next()) value = rs.getBigDecimal("saldo");

			// Si es proveedor doy vuelta el signo
			if (this.balanceFilters.partnerType != null){
				if (this.balanceFilters.partnerType.equalsIgnoreCase(ReportBalanceCtaCte.PARTNER_PROVEEDOR)){
					value = value.negate();
				}
			}
			else{
				if (this.balanceFilters.cBPGroupID == ReportOpenAmt.GRUPO_PROVEEDOR){
					value = value.negate();
				}
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
	 * 
	 * OpenUp. #1651	
	 * Descripcion : Metodo que devuelve el importe total afectado
	 * @return
	 * @author  Nicolas Sarlabos
	 * Fecha : 22/11/2013
	 */

	public BigDecimal getTotalAfectado(String nombre_tabla,Integer partnerID, Integer currencyID) {
		
		BigDecimal totalAfectado=Env.ZERO;
		
		if (nombre_tabla != "" & partnerID > 0) {
			String sql = "SELECT coalesce(SUM(amtallocated),0) FROM " + nombre_tabla + " WHERE c_bpartner_id = " + partnerID + " AND dateallocated <='"
					+ this.balanceFilters.dateTo + "'" + " AND c_currency_id = " + currencyID;

			totalAfectado = DB.getSQLValueBD(null, sql);
		}
	
		return totalAfectado;
		

	}
	
	private void showHelp(String text){
		if (this.getWaiting() != null){
			this.getWaiting().setText(text);
		}			
	}
	
}
