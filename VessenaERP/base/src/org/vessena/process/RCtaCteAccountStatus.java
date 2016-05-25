/**
 * 
 */
package org.openup.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import org.compiere.apps.Waiting;
import org.compiere.model.MBPartner;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.openup.beans.ReportAccountStatus;
import org.openup.beans.ReportOpenAmt;

/**
 * @author OpenUp. Gabriel Vila. 03/11/2011. Issue #902.
 * Logica del reporte estado de cuenta corriente.
 */
public class RCtaCteAccountStatus {

	private ReportAccountStatus acctStatusFilters;
	private String idReporte = "";
	private static final String TABLA_MOLDE = "uy_molde_accountstatus";
	private Waiting waiting = null;
	
	/**
	 * Constructor. Recibo filtros seleccionados para este reporte.
	 * @param openAmtFilters
	 */
	public RCtaCteAccountStatus(ReportAccountStatus acctStatusFilters) {
		this.acctStatusFilters = acctStatusFilters;
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
				  " WHERE ad_user_id =" + this.acctStatusFilters.adUserID;
			
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
			this.idReporte = UtilReportes.getReportID(Long.valueOf(this.acctStatusFilters.adUserID));
			//OpenUp. Nicolas Sarlabos. 16/07/2013. #1143
			// Armo where de filtros
			if (this.acctStatusFilters.customerID > 0) {

				MBPartner partner = new MBPartner(Env.getCtx(),this.acctStatusFilters.customerID,null);//instancio cliente

				//si es un cliente padre
				if(partner.isParent()){

					whereFiltros += " AND st.c_bpartner_id IN (select c_bpartner_id from c_bpartner where bpartner_parent_id = " + this.acctStatusFilters.customerID + " OR c_bpartner_id = " + this.acctStatusFilters.customerID + ")";

				} else whereFiltros += " AND st.c_bpartner_id = " + this.acctStatusFilters.customerID;

			}		
			//Fin OpenUp. #1143
			if (this.acctStatusFilters.vendorID > 0)
				whereFiltros += " AND st.c_bpartner_id = " + this.acctStatusFilters.vendorID;
		
			whereFiltros += " AND st.c_currency_id = " + this.acctStatusFilters.cCurrencyID;
		
			insert = "INSERT INTO " + TABLA_MOLDE + " (idreporte, fecreporte, ad_user_id, ad_client_id, ad_org_id," +
					" isinvoice, c_bpartner_id, bpvalue, bpname, isreceipt, record_id, documentno, c_currency_id, currencyname, c_doctype_id," +					
					" docname, startdate, datetrx, duedate, amtdocument, serie,saldoinicial, debe, haber, saldoacumulado, "
					+ "poreference)";

			StringBuilder strb = new StringBuilder("");
			
			Timestamp dateFrom = TimeUtil.trunc(this.acctStatusFilters.dateFrom, TimeUtil.TRUNC_DAY);
			Timestamp dateTo = TimeUtil.trunc(this.acctStatusFilters.dateTo, TimeUtil.TRUNC_DAY);
			
			this.showHelp("Obteniendo datos...");
			
			String whereOrg = "";
			if (this.acctStatusFilters.adOrgID > 0) 
				whereOrg = " AND st.ad_org_id =" + this.acctStatusFilters.adOrgID;
			
			// Facturas y Notas de Credito
			strb.append(" SELECT '" + this.idReporte + "',current_date," + this.acctStatusFilters.adUserID + "," +
						this.acctStatusFilters.adClientID + "," + this.acctStatusFilters.adOrgID + "," +
						" case when doc.allocationbehaviour ='INV' then 'Y' else 'N' end as isinvoice, st.c_bpartner_id," + 
						" bp.value as bpvalue, (bp.name || ' - ' ||coalesce(bp.name2,'')) as bpname, st.issotrx, " +
						" st.c_invoice_id, st.documentno, st.c_currency_id, cur.description as curname, " +
						" st.c_doctype_id, doc.printname, st.dateinvoiced as startdate, st.dateinvoiced, " +
						" coalesce(st.duedate, paymenttermduedate(st.c_paymentterm_id, st.dateinvoiced)) as duedate, st.grandtotal, st.serie,"
						+ "0,0,0,0, coalesce(st.POReference,'') as poreference " +
						" from c_invoice st " +
						" inner join c_doctype doc ON st.c_doctype_id = doc.c_doctype_id" +
					    " inner join c_currency cur on st.c_currency_id = cur.c_currency_id " +
					    " inner join c_bpartner bp on st.c_bpartner_id = bp.c_bpartner_id " +
					    " WHERE doc.allocationbehaviour is not null and st.ad_client_id =" + this.acctStatusFilters.adClientID +
					    whereOrg +
					    " AND st.paymentruletype != 'CO' " +
					    // Si no tiene movimientos en este periodo igual debo traer registro para mandarlo a obtener saldo inicial
					    " AND st.dateinvoiced between '" + dateFrom + "' AND '" + dateTo + "' " +
					    //" AND st.dateinvoiced <='" + dateTo + "' " +
					     
					    " AND st.docstatus = 'CO' " +
					    " AND st.ispaid='N' " + whereFiltros); //OpenUp. Nicolas Sarlabos. 16/07/2013. #1141. Se modifica WHERE para contemplar todos los tipos de documentos

			if (this.acctStatusFilters.partnerType != null){
				if (this.acctStatusFilters.partnerType.equalsIgnoreCase(ReportAccountStatus.PARTNER_CLIENTE))
					strb.append(" AND st.issotrx='Y' ");

				if (this.acctStatusFilters.partnerType.equalsIgnoreCase(ReportAccountStatus.PARTNER_PROVEEDOR))
					strb.append(" AND st.issotrx='N' ");
			}
			else{
				if (this.acctStatusFilters.cBPGroupID == ReportOpenAmt.GRUPO_CLIENTE)
					strb.append(" AND st.issotrx='Y' ");

				if (this.acctStatusFilters.cBPGroupID == ReportOpenAmt.GRUPO_PROVEEDOR)
					strb.append(" AND st.issotrx='N' ");
			}
			
			// Payments
			strb.append(" UNION SELECT '" + this.idReporte + "',current_date," + this.acctStatusFilters.adUserID + "," +
						this.acctStatusFilters.adClientID + "," + this.acctStatusFilters.adOrgID + "," +
						" 'N' as isinvoice,st.c_bpartner_id," + 
						" bp.value as bpvalue, (bp.name || ' - ' ||coalesce(bp.name2,'')) as bpname, st.isreceipt, " +
						" st.c_payment_id, st.documentno, st.c_currency_id, cur.description as curname, " +
						" st.c_doctype_id, doc.printname, st.datetrx as startdate, st.datetrx, st.datetrx as duedate, st.payamt, st.serie,"
						+ " 0,0,0,0, coalesce(st.description,'') as poreference " + //Openup. Nicolas Sarlabos. 01/04/2016. #5681.
						" from c_payment st " +
						" inner join c_doctype doc ON st.c_doctype_id = doc.c_doctype_id" +
					    " inner join c_currency cur on st.c_currency_id = cur.c_currency_id " +
					    " inner join c_bpartner bp on st.c_bpartner_id = bp.c_bpartner_id " +
					    " WHERE doc.allocationbehaviour is not null and st.ad_client_id =" + this.acctStatusFilters.adClientID +
					    whereOrg +
					    // Si no tiene movimientos en este periodo igual debo traer registro para mandarlo a obtener saldo inicial
					     " AND st.datetrx between '" + dateFrom + "' AND '" + dateTo + "' " + 
					    //" AND st.datetrx <='" + dateTo + "' " +
					    " AND st.isreconciled ='N' " +					    
					    " AND st.docstatus = 'CO' " + whereFiltros);

			if (this.acctStatusFilters.partnerType != null){
				if (this.acctStatusFilters.partnerType.equalsIgnoreCase(ReportAccountStatus.PARTNER_CLIENTE))
					strb.append(" AND st.isreceipt='Y' ");

				if (this.acctStatusFilters.partnerType.equalsIgnoreCase(ReportAccountStatus.PARTNER_PROVEEDOR))
					strb.append(" AND st.isreceipt='N' ");
			}
			else{
				if (this.acctStatusFilters.cBPGroupID == ReportOpenAmt.GRUPO_CLIENTE)
					strb.append(" AND st.isreceipt='Y' ");

				if (this.acctStatusFilters.cBPGroupID == ReportOpenAmt.GRUPO_PROVEEDOR)
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
			
			this.showHelp("Calculo de saldos...");
			
			sql = " SELECT * " +
				  " FROM " + TABLA_MOLDE +
				  " WHERE idreporte=?" +
				  " ORDER BY bpvalue, datetrx, c_doctype_id, record_id ASC";
			
			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, null);
			pstmt.setString(1, this.idReporte);
			
			rs = pstmt.executeQuery ();
			
			rs.last();
			int totalRowCount = rs.getRow(), rowCount = 0;
			rs.beforeFirst();
			
			//OpenUp. Nicolas Sarlabos. 16/08/2013. #1228
			if (totalRowCount <= 0){
				this.updateSoloConSaldoInicial();
				return;
			}
			//Fin OpenUp.
			
			String action = ""; 
			BigDecimal saldoAcumulado = Env.ZERO, saldoInicial = Env.ZERO, totalDocumento = Env.ZERO;
			int cBPartnerID = 0, cBPartnerIDAux = 0, cDocTypeID = 0, recordID = 0;
			boolean isDebe = false;
			
			// Corte de control por socio de negocio
			while (rs.next()){

				this.showHelp("Procesando linea " + rowCount++ + " de " + totalRowCount);
				
				cDocTypeID = rs.getInt("c_doctype_id");
				recordID = rs.getInt("record_id");
				cBPartnerIDAux = rs.getInt("c_bpartner_id");

				// Si hay cambio de socio de negocio
				if (cBPartnerIDAux != cBPartnerID){
					cBPartnerID = cBPartnerIDAux;
					saldoInicial = getSaldoInicial(cBPartnerID);
					saldoAcumulado = saldoInicial;
				}				
				
				totalDocumento = rs.getBigDecimal("amtdocument");
				
				// Si fecha menor a rango considero monto cero
				if (rs.getTimestamp("datetrx").compareTo(this.acctStatusFilters.dateFrom) < 0){
					totalDocumento = Env.ZERO;
				}
					
				
				if (this.acctStatusFilters.partnerType != null){

					if (this.acctStatusFilters.partnerType.equalsIgnoreCase(ReportAccountStatus.PARTNER_CLIENTE)){
						if (rs.getString("isinvoice").equalsIgnoreCase("Y")){
							saldoAcumulado = saldoAcumulado.add(totalDocumento);
							isDebe = true;
						}
						else{
							saldoAcumulado = saldoAcumulado.subtract(totalDocumento);						
							isDebe = false;
						}
							
					}
					else{
						if (rs.getString("isinvoice").equalsIgnoreCase("Y")){
							saldoAcumulado = saldoAcumulado.subtract(totalDocumento);
							isDebe = false;
						}	
						else{
							saldoAcumulado = saldoAcumulado.add(totalDocumento);
							isDebe = true;
						}
					}
					
				}
				else{
					if (this.acctStatusFilters.cBPGroupID == ReportOpenAmt.GRUPO_CLIENTE){
						if (rs.getString("isinvoice").equalsIgnoreCase("Y")){
							saldoAcumulado = saldoAcumulado.add(totalDocumento);
							isDebe = true;
						}
						else{
							saldoAcumulado = saldoAcumulado.subtract(totalDocumento);						
							isDebe = false;
						}
							
					}
					else{
						if (rs.getString("isinvoice").equalsIgnoreCase("Y")){
							saldoAcumulado = saldoAcumulado.subtract(totalDocumento);
							isDebe = false;
						}	
						else{
							saldoAcumulado = saldoAcumulado.add(totalDocumento);
							isDebe = true;
						}
					}
					
				}
				
				
				action = " UPDATE " + TABLA_MOLDE + 
		 		 		 " SET saldoacumulado =" + saldoAcumulado + "," +
  	 		 		     " saldoinicial = " + saldoInicial + "," +
		 		 		 ((isDebe) ? " debe = " : " haber = ") + totalDocumento +
		 		 		 " WHERE idreporte ='" + this.idReporte + "'" +
		 		 		 " AND c_bpartner_id =" + cBPartnerID +
		 		 		 " AND c_doctype_id =" + cDocTypeID +
		 		 		 " AND record_id = " + recordID;
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
	 * OpenUp. Nicolas Sarlabos. 16/08/2013. Issue #1228
	 * Inserta solo saldos iniciales cuando no se obtuvieron datos 
	 * @throws Exception 
	 */
	private void updateSoloConSaldoInicial() throws Exception{
		
		String sql = "", where = "", insert = "", select = "",name = "";;
		int partnerID = 0;
		BigDecimal saldoInicial = Env.ZERO;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		if (this.acctStatusFilters.partnerType != null){

			if (this.acctStatusFilters.partnerType.equalsIgnoreCase(ReportAccountStatus.PARTNER_CLIENTE)){
				
				if (this.acctStatusFilters.customerID > 0) {
					
					where = " and bp.c_bpartner_id = " + this.acctStatusFilters.customerID; 
				
				} else where = " and bp.iscustomer='Y'";			
			}
			else {
				if (this.acctStatusFilters.vendorID > 0) {
					
					where = " and bp.c_bpartner_id = " + this.acctStatusFilters.vendorID; 
				
				} else where = " and bp.isvendor='Y'";
				
			}
			
		}
		else {
			
			if(this.acctStatusFilters.cBPGroupID == ReportAccountStatus.GRUPO_CLIENTE) {
				
				if (this.acctStatusFilters.customerID > 0) {
					
					where = " and bp.c_bpartner_id = " + this.acctStatusFilters.customerID; 
				
				} else where = " and bp.iscustomer='Y'";			
			}
			
			else if(this.acctStatusFilters.cBPGroupID == ReportAccountStatus.GRUPO_PROVEEDOR) {
			
				if (this.acctStatusFilters.vendorID > 0) {
					
					where = " and bp.c_bpartner_id = " + this.acctStatusFilters.vendorID; 
				
				} else where = " and bp.isvendor='Y'";
				
			}
			
		}
		
		
		sql = "select c_bpartner_id,value,name from c_bpartner bp where bp.isactive='Y'" + where + " order by bp.value";
		
		pstmt = DB.prepareStatement(sql, null);
		rs = pstmt.executeQuery ();
		
		while (rs.next()){

			partnerID = rs.getInt("c_bpartner_id");
			name = rs.getString("name").replace("'", "").trim();
			saldoInicial = getSaldoInicial(partnerID);

			if(saldoInicial.compareTo(Env.ZERO) != 0) {

				insert = "INSERT INTO " + TABLA_MOLDE + " (idreporte, fecreporte, ad_user_id, ad_client_id, ad_org_id," +
						" isinvoice, c_bpartner_id, bpvalue, bpname, isreceipt, record_id, documentno, c_currency_id, currencyname, c_doctype_id," +					
						" docname, startdate, datetrx, duedate, amtdocument, serie,saldoinicial, debe, haber, saldoacumulado)";

				select = " SELECT '" + this.idReporte + "',current_date," + this.acctStatusFilters.adUserID + "," +
						this.acctStatusFilters.adClientID + "," + this.acctStatusFilters.adOrgID + ",'N'," + partnerID + ",'" + rs.getString("value") + "','" +
						name + "','N',null,null," + this.acctStatusFilters.cCurrencyID + ",null,null,null,'" + this.acctStatusFilters.dateFrom + "','" + this.acctStatusFilters.dateFrom + "',null,null,null," + saldoInicial + ",null,null," + saldoInicial;

				DB.executeUpdateEx(insert + select, null);	

			}

		}		

	}

	/**
	 * OpenUp. Gabriel Vila. 08/11/2011. Issue #902.
	 * Obtiene y retorna saldo inicial de un cliente.
	 * @param cBPartnerID
	 * @return
	 * @throws Exception 
	 */
	private BigDecimal getSaldoInicial(int cBPartnerID) throws Exception {
		return this.getSaldoInicialDebe(cBPartnerID).subtract(this.getSaldoInicialHaber(cBPartnerID));
	}

	/**
	 * OpenUp.	
	 * Descripcion : Obtiene saldo inicial al DEBE de un determinado socio de negocio (deudor o proveedor).
	 * @param idSocioNegocio. int :  ID del socio de negocio a considerar.
	 * @return
	 * @author  Gabriel Vila 
	 * Fecha : 28/09/2010
	 * @throws Exception 
	 */
	private BigDecimal getSaldoInicialDebe(int cBPartnerID) throws Exception {
		
		BigDecimal value = new BigDecimal(0);
		
		if (this.acctStatusFilters.partnerType != null){

			if (this.acctStatusFilters.partnerType.equalsIgnoreCase(ReportAccountStatus.PARTNER_CLIENTE)){
				value = this.getSaldoInicialDebeDeudInvoice(cBPartnerID);
			}
			else{
				value = this.getSaldoInicialDebeProvInvoice(cBPartnerID).add(this.getSaldoInicialDebeProvPayment(cBPartnerID));
			}

		}
		else{

			if (this.acctStatusFilters.cBPGroupID == ReportOpenAmt.GRUPO_CLIENTE){
				value = this.getSaldoInicialDebeDeudInvoice(cBPartnerID);
			}
			else{
				value = this.getSaldoInicialDebeProvInvoice(cBPartnerID).add(this.getSaldoInicialDebeProvPayment(cBPartnerID));
			}
			
		}
		
		return value;
	}
	
	/**
	 * OpenUp.	
	 * Descripcion : Obtiene saldo inicial al HABER de un determinado socio de negocio (deudor o proveedor).
	 * @param idSocioNegocio. int :  ID del socio de negocio a considerar.
	 * @return
	 * @author  Gabriel Vila 
	 * Fecha : 28/09/2010
	 * @throws Exception 
	 */
	private BigDecimal getSaldoInicialHaber(int cBPartnerID) throws Exception {

		BigDecimal value = new BigDecimal(0);
		
		if (this.acctStatusFilters.partnerType != null){
			if (this.acctStatusFilters.partnerType.equalsIgnoreCase(ReportAccountStatus.PARTNER_CLIENTE)){
				value = this.getSaldoInicialHaberDeudInvoice(cBPartnerID).add(this.getSaldoInicialHaberDeudPayment(cBPartnerID));
			}
			else{
				value = this.getSaldoInicialHaberProvInvoice(cBPartnerID);
			}
		}
		else{
			if (this.acctStatusFilters.cBPGroupID == ReportOpenAmt.GRUPO_CLIENTE){
				value = this.getSaldoInicialHaberDeudInvoice(cBPartnerID).add(this.getSaldoInicialHaberDeudPayment(cBPartnerID));
			}
			else{
				value = this.getSaldoInicialHaberProvInvoice(cBPartnerID);
			}
		}

		
		
		return value;
	}

	/**
	 * OpenUp.	
	 * Descripcion : Obtiene saldo inicial de facturas de venta para un determinado cliente.
	 * @param idSocioNegocio. int : ID del cliente a considerar.
	 * @return
	 * @author  Gabriel Vila 
	 * Fecha : 28/09/2010
	 * @throws Exception 
	 */
	private BigDecimal getSaldoInicialDebeDeudInvoice(int cBPartnerID) throws Exception{
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		BigDecimal value = new BigDecimal(0);
		
		try{
		
			String whereMoneda = "";
			
			// Condicion de moneda
			if (this.acctStatusFilters.cCurrencyID > 0) whereMoneda = " AND a.C_Currency_ID = " + this.acctStatusFilters.cCurrencyID;

			String whereOrg = "";
			if (this.acctStatusFilters.adOrgID > 0) 
				whereOrg = " AND a.ad_org_id =" + this.acctStatusFilters.adOrgID;

			
			sql = "SELECT COALESCE(SUM(a.grandtotal),0) as saldo " +
				  " FROM C_Invoice a INNER JOIN C_DocType b ON a.c_doctype_id = b.c_doctype_id " +
				  " WHERE a.ad_client_id =" + this.acctStatusFilters.adClientID +
				  whereOrg +
				  " AND a.dateinvoiced <? " + 
				  " AND a.docstatus = 'CO' " +
				  " AND a.ispaid = 'N' " +
				  " AND a.C_BPartner_ID =" + cBPartnerID + whereMoneda +
				  " AND b.issotrx='Y' " +				  
				  " AND b.allocationbehaviour ='INV'";

			pstmt = DB.prepareStatement(sql, null);
			pstmt.setTimestamp(1, this.acctStatusFilters.dateFrom);			
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
	 * OpenUp.	
	 * Descripcion : Obtiene saldo inicial de notas de credito de venta para un determinado cliente.
	 * @param idSocioNegocio. int : ID del cliente a considerar.
	 * @return
	 * @author  Gabriel Vila 
	 * Fecha : 28/09/2010
	 * @throws Exception 
	 */
	private BigDecimal getSaldoInicialHaberDeudInvoice(int cBPartnerID) throws Exception{
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		BigDecimal value = new BigDecimal(0);
		
		try{
		
			String whereMoneda = "";
			// Condicion de moneda
			if (this.acctStatusFilters.cCurrencyID > 0) whereMoneda = " AND a.C_Currency_ID = " + this.acctStatusFilters.cCurrencyID;

			String whereOrg = "";
			if (this.acctStatusFilters.adOrgID > 0) 
				whereOrg = " AND a.ad_org_id =" + this.acctStatusFilters.adOrgID;

			
			sql = "SELECT COALESCE(SUM(a.grandtotal),0) as saldo " +
				  " FROM C_Invoice a INNER JOIN C_DocType b ON a.c_doctype_id = b.c_doctype_id " +
				  " WHERE a.ad_client_id =" + this.acctStatusFilters.adClientID +
				  whereOrg +
				  " AND a.dateinvoiced <? " + 
				  " AND a.docstatus = 'CO' " +
				  " AND a.ispaid = 'N' " +
				  " AND a.C_BPartner_ID =" + cBPartnerID + whereMoneda +
				  " AND b.issotrx='Y'" +
				  " AND b.allocationbehaviour ='PAY'";

			pstmt = DB.prepareStatement(sql, null);
			pstmt.setTimestamp(1, this.acctStatusFilters.dateFrom);			
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
	 * OpenUp.	
	 * Descripcion : Obtiene saldo inicial de cobranzas de ventas para un determinado cliente.
	 * @param idSocioNegocio. int : ID del cliente a considerar.
	 * @return
	 * @author  Gabriel Vila 
	 * Fecha : 28/09/2010
	 * @throws Exception 
	 */
	private BigDecimal getSaldoInicialHaberDeudPayment(int cBPartnerID) throws Exception{
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		BigDecimal value = new BigDecimal(0);
		
		try{
		
			String whereMoneda = "";
			// Condicion de moneda
			if (this.acctStatusFilters.cCurrencyID > 0) whereMoneda = " AND a.C_Currency_ID = " + this.acctStatusFilters.cCurrencyID;

			String whereOrg = "";
			if (this.acctStatusFilters.adOrgID > 0) 
				whereOrg = " AND a.ad_org_id =" + this.acctStatusFilters.adOrgID;

			
			sql = "SELECT COALESCE(SUM(a.payamt),0) as saldo " +
				  " FROM C_Payment a INNER JOIN C_DocType b ON a.c_doctype_id = b.c_doctype_id " +
				  " WHERE a.ad_client_id =" + this.acctStatusFilters.adClientID +
				  whereOrg +
				  " AND a.datetrx <? " + 
				  " AND a.docstatus = 'CO' " +
				  " AND a.C_BPartner_ID =" + cBPartnerID + whereMoneda +
				  " AND b.issotrx='Y'" +
				  " AND b.docbasetype='ARR'";

			pstmt = DB.prepareStatement(sql, null);
			pstmt.setTimestamp(1, this.acctStatusFilters.dateFrom);			
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
	 * OpenUp.	
	 * Descripcion : Obtiene saldo inicial de notas de credito de compra para un determinado proveedor.
	 * @param idSocioNegocio. int : ID del proveedor a considerar.
	 * @return
	 * @author  Gabriel Vila 
	 * Fecha : 28/09/2010
	 * @throws Exception 
	 */
	private BigDecimal getSaldoInicialDebeProvInvoice(int cBPartnerID) throws Exception{
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		BigDecimal value = new BigDecimal(0);
		
		try{
		
			String whereMoneda = "";
			// Condicion de moneda
			if (this.acctStatusFilters.cCurrencyID > 0) whereMoneda = " AND a.C_Currency_ID = " + this.acctStatusFilters.cCurrencyID;

			String whereOrg = "";
			if (this.acctStatusFilters.adOrgID > 0) 
				whereOrg = " AND a.ad_org_id =" + this.acctStatusFilters.adOrgID;
			
			sql = "SELECT COALESCE(SUM(a.grandtotal),0) as saldo " +
				  " FROM C_Invoice a INNER JOIN C_DocType b ON a.c_doctype_id = b.c_doctype_id " +
				  " WHERE a.ad_client_id =" + this.acctStatusFilters.adClientID +
				  whereOrg +
				  " AND a.dateinvoiced <? " + 
				  " AND a.docstatus = 'CO' " +
				  " AND a.ispaid = 'N' " +				  
				  " AND a.C_BPartner_ID =" + cBPartnerID + whereMoneda +
				  " AND b.issotrx='N'" +
				  " AND b.allocationbehaviour ='PAY'";

			pstmt = DB.prepareStatement(sql, null);
			pstmt.setTimestamp(1, this.acctStatusFilters.dateFrom);			
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
	 * OpenUp.	
	 * Descripcion : Obtiene saldo inicial de pagos de compras para un determinado proveedor.
	 * @param idSocioNegocio. int : ID del proveedor a considerar.
	 * @return
	 * @author  Gabriel Vila 
	 * Fecha : 28/09/2010
	 * @throws Exception 
	 */
	private BigDecimal getSaldoInicialDebeProvPayment(int cBPartnerID) throws Exception{
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		BigDecimal value = new BigDecimal(0);
		
		try{
		
			String whereMoneda = "";
			// Condicion de moneda
			if (this.acctStatusFilters.cCurrencyID > 0) whereMoneda = " AND a.C_Currency_ID = " + this.acctStatusFilters.cCurrencyID;

			String whereOrg = "";
			if (this.acctStatusFilters.adOrgID > 0) 
				whereOrg = " AND a.ad_org_id =" + this.acctStatusFilters.adOrgID;
			
			
			sql = "SELECT COALESCE(SUM(a.payamt),0) as saldo " +
				  " FROM C_Payment a INNER JOIN C_DocType b ON a.c_doctype_id = b.c_doctype_id " +
				  " WHERE a.ad_client_id =" + this.acctStatusFilters.adClientID +
				  whereOrg +
				  " AND a.datetrx <? " + 
				  " AND a.docstatus = 'CO' " +
				  " AND a.C_BPartner_ID =" + cBPartnerID + whereMoneda +
				  " AND b.issotrx='N'" +
				  " AND b.docbasetype='APP'";

			pstmt = DB.prepareStatement(sql, null);
			pstmt.setTimestamp(1, this.acctStatusFilters.dateFrom);			
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
	 * OpenUp.	
	 * Descripcion : Obtiene saldo inicial de facturas de compra para un determinado proveedor.
	 * @param idSocioNegocio. int : ID del proveedor a considerar.
	 * @return
	 * @author  Gabriel Vila 
	 * Fecha : 28/09/2010
	 * @throws Exception 
	 */
	private BigDecimal getSaldoInicialHaberProvInvoice(int cBPartnerID) throws Exception{
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		BigDecimal value = new BigDecimal(0);
		
		try{
		
			String whereMoneda = "";
			// Condicion de moneda
			if (this.acctStatusFilters.cCurrencyID > 0) whereMoneda = " AND a.C_Currency_ID = " + this.acctStatusFilters.cCurrencyID;

			String whereOrg = "";
			if (this.acctStatusFilters.adOrgID > 0) 
				whereOrg = " AND a.ad_org_id =" + this.acctStatusFilters.adOrgID;
			
			
			sql = "SELECT COALESCE(SUM(a.grandtotal),0) as saldo " +
				  " FROM C_Invoice a INNER JOIN C_DocType b ON a.c_doctype_id = b.c_doctype_id " +
				  " WHERE a.ad_client_id =" + this.acctStatusFilters.adClientID +
				  whereOrg +
				  " AND a.dateinvoiced <? " + 
				  " AND a.docstatus = 'CO' " +
				  " AND a.ispaid = 'N' " +				  
				  " AND a.C_BPartner_ID =" + cBPartnerID + whereMoneda +
				  " AND b.issotrx='N'" +
				  " AND b.allocationbehaviour ='INV'";

			pstmt = DB.prepareStatement(sql, null);
			pstmt.setTimestamp(1, this.acctStatusFilters.dateFrom);			
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

	/***
	* A los registros que solo salen porque tienen saldo inicial pero NO tuvieron movimientos
	* en este rango de fecha, debo hacerles la trampa de actualizarles la fecha para que el 
	* report view no los filtre y los deje afuera.
 	 */
	/*private void updateSoloConSaldoInicial(){
		
		try{
			
			String action = " update " + TABLA_MOLDE +
						    " set datetrx ='" + this.acctStatusFilters.dateFrom + "', " +
						    " startdate ='" + this.acctStatusFilters.dateFrom + "', " +
						    " debe =0, haber =0 " +
						    " where idreporte='" + this.idReporte + "' " +
						    " and datetrx <'" + this.acctStatusFilters.dateFrom + "'";
			DB.executeUpdateEx(action, null);
			
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
	}*/
	
	private void showHelp(String text){
		if (this.getWaiting() != null){
			this.getWaiting().setText(text);
		}			
	}	

}
