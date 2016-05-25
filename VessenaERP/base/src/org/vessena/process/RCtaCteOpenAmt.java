/**
 * 
 */
package org.openup.process;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import org.compiere.apps.Waiting;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MBPartner;
import org.compiere.model.MClient;
import org.compiere.model.MConversionRate;
import org.compiere.model.MCurrency;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.openup.beans.ReportOpenAmt;


/**
 * @author OpenUp. Gabriel Vila. 03/11/2011. Issue #902.
 * Informe y Proceso de Saldos Pendientes en formato Report View.
 */
public class RCtaCteOpenAmt {

	private ReportOpenAmt openAmtFilters;
	private String idReporte = "";
	private static final String TABLA_MOLDE = "uy_molde_openamt";
	private Waiting waiting = null;
	
	/**
	 * Constructor. Recibo filtros seleccionados para este reporte.
	 * @param openAmtFilters
	 */
	public RCtaCteOpenAmt(ReportOpenAmt openAmtFilters) {
		this.openAmtFilters = openAmtFilters;
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
		
		if (this.openAmtFilters.tipoReporte.equalsIgnoreCase(ReportOpenAmt.CTA_ABIERTA)){
			this.getDataCuentaAbierta();
			this.calculoSaldosCuentaAbierta();
		}
		else{
			this.getDataCuentaDocumentada();
			this.calculoSaldosCuentaDocumentada();
		}		

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
				  " WHERE ad_user_id =" + this.openAmtFilters.adUserID;
			
			DB.executeUpdateEx(sql,null);
		}
		catch (Exception e)
		{
			throw e;
		}
	}

	
	
	/**
	 * Carga datos de cuenta abierta en tabla molde considerando filtros.
	 * @throws Exception 
	 */
	private void getDataCuentaAbierta() throws Exception{
		
		String insert ="", sql = "", whereFiltros = "";

		try
		{
			// Obtengo id para este reporte
			this.idReporte = UtilReportes.getReportID(Long.valueOf(this.openAmtFilters.adUserID));
		
			MClient client = new MClient(Env.getCtx(), this.openAmtFilters.adClientID, null);
			MAcctSchema schema = client.getAcctSchema();
			//OpenUp. Nicolas Sarlabos. 16/07/2013. #1143
			// Armo where de filtros
			if (this.openAmtFilters.customerID > 0) {

				MBPartner partner = new MBPartner(Env.getCtx(),this.openAmtFilters.customerID,null);//instancio cliente

				//si es un cliente padre
				if(partner.isParent()){

					whereFiltros += " AND hdr.c_bpartner_id IN (select c_bpartner_id from c_bpartner where bpartner_parent_id = " + this.openAmtFilters.customerID + " OR c_bpartner_id = " + this.openAmtFilters.customerID + ")";

				} else whereFiltros += " AND hdr.c_bpartner_id = " + this.openAmtFilters.customerID;

			} 			
			//Fin OpenUp. #1143
			if (this.openAmtFilters.vendorID > 0)
				whereFiltros += " AND hdr.c_bpartner_id = " + this.openAmtFilters.vendorID;
		
			if (this.openAmtFilters.tipoMoneda.equalsIgnoreCase(ReportOpenAmt.TIPO_MONEDA_SMN)){
				this.openAmtFilters.cCurrencyID = schema.getC_Currency_ID();
			}

			if (this.openAmtFilters.tipoMoneda.equalsIgnoreCase(ReportOpenAmt.TIPO_MONEDA_SMN) || this.openAmtFilters.tipoMoneda.equalsIgnoreCase(ReportOpenAmt.TIPO_MONEDA_SME)) 
				whereFiltros += " AND hdr.c_currency_id = " + this.openAmtFilters.cCurrencyID;
		
			if (this.openAmtFilters.partnerType != null){

				if (this.openAmtFilters.partnerType.equalsIgnoreCase(ReportOpenAmt.PARTNER_CLIENTE))
					whereFiltros += " AND doc.issotrx='Y' ";

				if (this.openAmtFilters.partnerType.equalsIgnoreCase(ReportOpenAmt.PARTNER_PROVEEDOR))
					whereFiltros += " AND doc.issotrx='N' ";

			}
			else{

				if (this.openAmtFilters.cBPGroupID == ReportOpenAmt.GRUPO_CLIENTE)
					whereFiltros += " AND doc.issotrx='Y' ";

				if (this.openAmtFilters.cBPGroupID == ReportOpenAmt.GRUPO_PROVEEDOR)
					whereFiltros += " AND doc.issotrx='N' ";
				
			}

			if (this.openAmtFilters.salesRepID > 0)
				whereFiltros += " AND hdr.salesrep_id = " + this.openAmtFilters.salesRepID;
			
			if (this.openAmtFilters.collectorID > 0)
				whereFiltros += " AND bp.collector_id = " + this.openAmtFilters.collectorID;

			if (this.openAmtFilters.canalVentaID > 0)
				whereFiltros += " AND bp.uy_canalventas_id = " + this.openAmtFilters.canalVentaID;

			if (this.openAmtFilters.departamentoID > 0)
				whereFiltros += " AND loc.uy_departamentos_id = " + this.openAmtFilters.departamentoID;
			
			if (this.openAmtFilters.localidadID > 0)
				whereFiltros += " AND loc.uy_localidades_id = " + this.openAmtFilters.localidadID;


			insert = "INSERT INTO " + TABLA_MOLDE + " (fechahasta, idreporte, fecreporte, ad_user_id, ad_client_id, ad_org_id," +
					" isinvoice, c_bpartner_id, bpvalue, bpname, isreceipt, record_id, documentno, c_currency_id, currencyname, c_doctype_id," +					
					" docname, datetrx, duedate, amtdocument, amtacumulate, amtopen, poreference)";

			StringBuilder strb = new StringBuilder("");
			Timestamp dateTo = TimeUtil.trunc(this.openAmtFilters.dateTo, TimeUtil.TRUNC_DAY);
			
			String whereOrg = "";
			if (this.openAmtFilters.adOrgID > 0) 
				whereOrg = " AND hdr.ad_org_id =" + this.openAmtFilters.adOrgID;
			
			this.showHelp("Obteniendo datos...");
			
			// Facturas
			strb.append(" SELECT '" + dateTo + "'::timestamp without time zone,'" + this.idReporte + "',current_date," + this.openAmtFilters.adUserID + "," +
						this.openAmtFilters.adClientID + "," + this.openAmtFilters.adOrgID + "," +
						" 'Y' as isinvoice, hdr.c_bpartner_id, bp.value as bpvalue, (bp.name || ' - ' ||coalesce(bp.name2,'')) as bpname, 'N' as isreceipt, " +
						" hdr.c_invoice_id, hdr.documentno, hdr.c_currency_id, cur.description as curname, " +
						" hdr.c_doctype_id, doc.printname, hdr.dateinvoiced, " +
						//" paymenttermduedate(hdr.c_paymentterm_id, hdr.dateinvoiced) as duedate, " +
						" coalesce(hdr.duedate, paymenttermduedate(hdr.c_paymentterm_id, hdr.dateinvoiced)) as duedate, " +
						" coalesce(hdr.grandtotal,0) as grandtotal,0,0, coalesce(hdr.poreference,'') as poreference " +
						" FROM c_invoice hdr " +
						" inner join c_doctype doc on hdr.c_doctype_id = doc.c_doctype_id" +
						" INNER JOIN c_currency cur on hdr.c_currency_id = cur.c_currency_id " +
						" INNER JOIN c_bpartner bp on hdr.c_bpartner_id = bp.c_bpartner_id " +
						" left outer join c_bpartner_location suc on hdr.c_bpartner_location_id = suc.c_bpartner_location_id " +		
					    " left outer join c_location loc on suc.c_location_id = loc.c_location_id " +					    
					    " WHERE hdr.ad_client_id =" + this.openAmtFilters.adClientID + 
					    whereOrg +
					    " AND hdr.paymentruletype !='CO' " +
					    " AND doc.allocationbehaviour='INV' AND hdr.docstatus='CO' " +
					    " AND " + ((!this.openAmtFilters.isDueDate) ? " hdr.dateinvoiced <='" : " hdr.duedate <='") + this.openAmtFilters.dateTo + "'" + whereFiltros);

			/*
			// Asientos Tipo
			// Facturas
			strb.append(" UNION SELECT '" + dateTo + "'::timestamp without time zone,'" + this.idReporte + "',current_date," + this.openAmtFilters.adUserID + "," +
						this.openAmtFilters.adClientID + "," + this.openAmtFilters.adOrgID + "," +
						" 'Y' as isinvoice, atbp.c_bpartner_id, bp.value as bpvalue, (bp.name || ' - ' ||coalesce(bp.name2,'')) as bpname, 'N' as isreceipt, " +
						" at.uy_typefact_id, at.documentno, at.c_currency_id, cur.description as curname, " +
						" at.c_doctype_id_2, doc.printname, at.datetrx, " +
						//" paymenttermduedate(hdr.c_paymentterm_id, hdr.dateinvoiced) as duedate, " +
						" at.datetrx as duedate, " +
						" coalesce(atbp.amt,0) as grandtotal,0,0 " +
						" FROM uy_typefact at " +
						" inner join uy_typefactbp atbp on at.uy_typefact_id = atbp.uy_typefact_id " +
						" inner join c_doctype doc on at.c_doctype_id_2 = doc.c_doctype_id" +
						" INNER JOIN c_currency cur on at.c_currency_id = cur.c_currency_id " +
						" INNER JOIN c_bpartner bp on atbp.c_bpartner_id = bp.c_bpartner_id " +
					    " WHERE at.ad_client_id =" + this.openAmtFilters.adClientID + 
					    whereOrg +
					    " AND hdr.paymentruletype !='CO' " +
					    " AND doc.allocationbehaviour='INV' AND hdr.docstatus='CO' " +
					    " AND " + ((!this.openAmtFilters.isDueDate) ? " hdr.dateinvoiced <='" : " hdr.duedate <='") + this.openAmtFilters.dateTo + "'" + whereFiltros);
			*/
			
			// Notas de Credito (se toma como recibo para cambiar el signo)
			strb.append(" UNION SELECT '" + dateTo + "'::timestamp without time zone,'" + this.idReporte + "',current_date," + this.openAmtFilters.adUserID + "," +
						this.openAmtFilters.adClientID + "," + this.openAmtFilters.adOrgID + "," +						
						" 'N' as isinvoice, hdr.c_bpartner_id, bp.value as bpvalue, (bp.name || ' - ' ||coalesce(bp.name2,'')) as bpname, 'N' as isreceipt, " +
						" hdr.c_invoice_id, hdr.documentno, hdr.c_currency_id, cur.description as curname, " +
						" hdr.c_doctype_id, doc.printname, hdr.dateinvoiced, " +
						" paymenttermduedate(hdr.c_paymentterm_id, hdr.dateinvoiced) as duedate, " + 
						" coalesce(hdr.grandtotal,0) as grandtotal,0,0, coalesce(hdr.poreference,'') as poreference " +
						" FROM c_invoice hdr " +
						" inner join c_doctype doc on hdr.c_doctype_id = doc.c_doctype_id " +
						" INNER JOIN c_currency cur on hdr.c_currency_id = cur.c_currency_id " +
						" INNER JOIN c_bpartner bp on hdr.c_bpartner_id = bp.c_bpartner_id " +
						" left outer join c_bpartner_location suc on hdr.c_bpartner_location_id = suc.c_bpartner_location_id " +
						" left outer join c_location loc on suc.c_location_id = loc.c_location_id " + 
						" WHERE hdr.ad_client_id =" + this.openAmtFilters.adClientID + 
						whereOrg +
						" AND hdr.paymentruletype !='CO' " +
					    " AND doc.allocationbehaviour='PAY' AND hdr.docstatus='CO' AND hdr.dateinvoiced <='" + this.openAmtFilters.dateTo + "'" + whereFiltros);

			// Recibos
			strb.append(" UNION SELECT '" + dateTo + "'::timestamp without time zone,'" + this.idReporte + "',current_date," + this.openAmtFilters.adUserID + "," +
						this.openAmtFilters.adClientID + "," + this.openAmtFilters.adOrgID + "," +						
						" 'N' as isinvoice, hdr.c_bpartner_id, bp.value as bpvalue, (bp.name || ' - ' ||coalesce(bp.name2,'')) as bpname, 'Y' as isreceipt, " +					
						" hdr.c_payment_id, hdr.documentno, hdr.c_currency_id, cur.description as curname, " +
						" hdr.c_doctype_id, doc.printname, hdr.datetrx, null, coalesce(hdr.payamt,0) as payamt, " + 
						" 0,0, coalesce(hdr.description,'') as poreference " + //Openup. Nicolas Sarlabos. 01/04/2016. #5681.					
						" FROM c_payment hdr inner join c_doctype doc on hdr.c_doctype_id = doc.c_doctype_id " +
						" INNER JOIN c_currency cur on hdr.c_currency_id = cur.c_currency_id " +
						" INNER JOIN c_bpartner bp on hdr.c_bpartner_id = bp.c_bpartner_id " + 
						" WHERE hdr.ad_client_id =" + this.openAmtFilters.adClientID +
						whereOrg +
						" AND hdr.isreconciled ='N' " +
						" AND doc.allocationbehaviour='PAY' AND hdr.docstatus='CO' AND hdr.datetrx <='" + this.openAmtFilters.dateTo + "'" + whereFiltros);

			sql = strb.toString();
			
			DB.executeUpdateEx(insert + sql, null);
			
 		}
		catch (Exception e)
		{
			throw e;
		}
	}

	/**
	 * Calcula acumulado, saldos en moneda y signo correcto en tabla temporal. 
	 * @throws Exception
	 */
	private void calculoSaldosCuentaAbierta() throws Exception{

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		BigDecimal dividerate = Env.ZERO;
		int curFromID = 0, curToID = 0;
		MCurrency currency = new MCurrency(Env.getCtx(), this.openAmtFilters.cCurrencyID,null);
		
		try{
			
			this.showHelp("Calculo de saldos...");
			
			sql = "SELECT * " +
				  " FROM " + TABLA_MOLDE +
				  " WHERE idreporte=?" +
				  " ORDER BY bpvalue, datetrx, c_doctype_id, record_id ASC";
			
			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, null);
			pstmt.setString(1, this.idReporte);
			
			rs = pstmt.executeQuery ();
			
			rs.last();
			int totalRowCount = rs.getRow(), rowCount = 0;
			rs.beforeFirst();
			
			String action = ""; 
			BigDecimal saldoAcumulado = Env.ZERO, totalDocumento = Env.ZERO, saldoPendiente = Env.ZERO;
			int cBPartnerID = 0, cBPartnerIDAux = 0, cDocTypeID = 0, recordID = 0;
			Timestamp fechaConversionMoneda = TimeUtil.trunc(this.openAmtFilters.dateTo, TimeUtil.TRUNC_DAY);
			Timestamp today = TimeUtil.trunc(new Timestamp (System.currentTimeMillis()), TimeUtil.TRUNC_DAY);
			
			if (fechaConversionMoneda.compareTo(today) > 0){
				fechaConversionMoneda = today;
			}
			
			
			// Corte de control por socio de negocio
			while (rs.next()){

				this.showHelp("Procesando linea " + rowCount++ + " de " + totalRowCount);
				
				cDocTypeID = rs.getInt("c_doctype_id");
				recordID = rs.getInt("record_id");
				cBPartnerIDAux = rs.getInt("c_bpartner_id");

				// Si hay cambio de socio de negocio
				if (cBPartnerIDAux != cBPartnerID){
					saldoAcumulado = Env.ZERO;
					cBPartnerID = cBPartnerIDAux;
				}

				// Si el documento tiene una moneda distinta a la moneda del reporte, hago la conversion al tipo de cambio del dia
				totalDocumento = rs.getBigDecimal("amtdocument");

				//OpenUp Nicolas Sarlabos 12/01/2012 
				String nombreTabla = "";
				String var = "";
				
				if (rs.getString("isinvoice").equalsIgnoreCase("Y")) { //si es factura
					nombreTabla = "alloc_invoiceamtopenxdate";
					var = "c_invoice_id";
				} else if (rs.getString("isreceipt").equalsIgnoreCase("Y")) { //si es recibo
					nombreTabla = "alloc_paymentamtopenxdate";
					var = "c_payment_id";
				} else if (rs.getString("isinvoice").equalsIgnoreCase("N") & rs.getString("isreceipt").equalsIgnoreCase("N")) {
					nombreTabla = "alloc_creditnoteamtopenxdate"; //si es nota de credito
					var = "c_invoice_id";
				}

				saldoPendiente = totalDocumento.subtract(getTotalAfectado(nombreTabla, var, recordID));				
				
				//fin OpenUp Nicolas Sarlabos 12/01/2012
				if (rs.getInt("c_currency_id") != this.openAmtFilters.cCurrencyID){
										
					if(rs.getInt("c_currency_id") == 142 || this.openAmtFilters.cCurrencyID == 142){
						
						totalDocumento = MConversionRate.convert(Env.getCtx(), totalDocumento, rs.getInt("c_currency_id"), 
								 this.openAmtFilters.cCurrencyID, fechaConversionMoneda, 0, this.openAmtFilters.adClientID, 
								 this.openAmtFilters.adOrgID);
						saldoPendiente = MConversionRate.convert(Env.getCtx(), saldoPendiente, rs.getInt("c_currency_id"), 
						 this.openAmtFilters.cCurrencyID, fechaConversionMoneda, 0, this.openAmtFilters.adClientID, 
						 this.openAmtFilters.adOrgID);						
						
					} else {
						
						curFromID = rs.getInt("c_currency_id");
						curToID = this.openAmtFilters.cCurrencyID;
						
						BigDecimal rateFrom = MConversionRate.getDivideRate(142, curToID, fechaConversionMoneda, 0, this.openAmtFilters.adClientID, 0);
						BigDecimal rateTo = MConversionRate.getDivideRate(142, curFromID, fechaConversionMoneda, 0, this.openAmtFilters.adClientID, 0);
						
						if ((rateFrom != null) && (rateTo != null)){
							if (rateTo.compareTo(Env.ZERO) > 0) {
								dividerate = rateFrom.divide(rateTo, 3, RoundingMode.HALF_UP);		
							}
						} else {
							
							totalDocumento = Env.ZERO;
							saldoPendiente = Env.ZERO;							
						}
						
						if(dividerate!=null && dividerate.compareTo(Env.ZERO)!=0){										

							totalDocumento = totalDocumento.divide(dividerate, 2, RoundingMode.HALF_UP);
							saldoPendiente = saldoPendiente.divide(dividerate, 2, RoundingMode.HALF_UP);							
							
						} else {
							
							totalDocumento = Env.ZERO;
							saldoPendiente = Env.ZERO;							
						}				
						
					}				
			
				}
								
				if (this.openAmtFilters.partnerType != null){

					if (this.openAmtFilters.partnerType.equalsIgnoreCase(ReportOpenAmt.PARTNER_CLIENTE)){
						if (rs.getString("isinvoice").equalsIgnoreCase("Y"))
							saldoAcumulado = saldoAcumulado.add(saldoPendiente);
						else{
							saldoAcumulado = saldoAcumulado.subtract(saldoPendiente);
							saldoPendiente = saldoPendiente.negate();
							totalDocumento = totalDocumento.negate();
						}
							
					}
					else{
						if (rs.getString("isinvoice").equalsIgnoreCase("Y")){
							saldoAcumulado = saldoAcumulado.subtract(saldoPendiente);
							saldoPendiente = saldoPendiente.negate();
							totalDocumento = totalDocumento.negate();

						}	
						else
							saldoAcumulado = saldoAcumulado.add(saldoPendiente);
					}
					
				}
				else{
					if (this.openAmtFilters.cBPGroupID == ReportOpenAmt.GRUPO_CLIENTE){
						if (rs.getString("isinvoice").equalsIgnoreCase("Y"))
							saldoAcumulado = saldoAcumulado.add(saldoPendiente);
						else{
							saldoAcumulado = saldoAcumulado.subtract(saldoPendiente);
							saldoPendiente = saldoPendiente.negate();
							totalDocumento = totalDocumento.negate();
						}
							
					}
					else{
						if (rs.getString("isinvoice").equalsIgnoreCase("Y")){
							saldoAcumulado = saldoAcumulado.subtract(saldoPendiente);
							saldoPendiente = saldoPendiente.negate();
							totalDocumento = totalDocumento.negate();

						}	
						else
							saldoAcumulado = saldoAcumulado.add(saldoPendiente);
					}
					
				}
				
				action = " UPDATE " + TABLA_MOLDE + 
		 		 		 " SET amtacumulate =" + saldoAcumulado + "," +
		 		 		 " amtdocument = " + totalDocumento + "," +
		 		 		 " amtopen = " + saldoPendiente + "," +
		 		 		 " c_currency_id = " + this.openAmtFilters.cCurrencyID + "," +
		 		 		 " currencyname = '" + currency.getDescription() + "'" +
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
	 * 
	 * OpenUp. 	
	 * Descripcion : Metodo que devuelve el importe total afectado
	 * @return
	 * @author  Nicolas Sarlabos
	 * Fecha : 12/01/2012
	 */

	public BigDecimal getTotalAfectado(String nombre_tabla,String var,Integer recordID) {
		
		BigDecimal totalAfectado=Env.ZERO;
		
		if (nombre_tabla != "" & var != "" & recordID > 0) {
			String sql = "SELECT coalesce(SUM(amtallocated),0) FROM " + nombre_tabla + " WHERE " + var + " =" + recordID + " AND dateallocated <='"
					+ this.openAmtFilters.dateTo + "'";

			totalAfectado = DB.getSQLValueBD(null, sql);
		}
	
		return totalAfectado;
		

	}
	
	/**
	 * Carga datos de cuenta documentada en tabla molde considerando filtros.
	 * @throws Exception 
	 */
	private void getDataCuentaDocumentada() throws Exception{

		String insert ="", sql = "", whereFiltros = "";

		try
		{
			// Obtengo id para este reporte
			this.idReporte = UtilReportes.getReportID(Long.valueOf(this.openAmtFilters.adUserID));
			
			MClient client = new MClient(Env.getCtx(), this.openAmtFilters.adClientID, null);
			MAcctSchema schema = client.getAcctSchema();
			//OpenUp. Nicolas Sarlabos. 16/07/2013. #1143
			// Armo where de filtros
			if (this.openAmtFilters.customerID > 0) {

				MBPartner partner = new MBPartner(Env.getCtx(),this.openAmtFilters.customerID,null);//instancio cliente

				//si es un cliente padre
				if(partner.isParent()){

					whereFiltros += " AND mp.c_bpartner_id IN (select c_bpartner_id from c_bpartner where bpartner_parent_id = " + this.openAmtFilters.customerID + " OR c_bpartner_id = " + this.openAmtFilters.customerID + ")";

				} else whereFiltros += " AND mp.c_bpartner_id = " + this.openAmtFilters.customerID;

			} 				
			//Fin OpenUp. #1143
			if (this.openAmtFilters.vendorID > 0)
				whereFiltros += " AND mp.c_bpartner_id = " + this.openAmtFilters.vendorID;
		
			if (this.openAmtFilters.tipoMoneda.equalsIgnoreCase(ReportOpenAmt.TIPO_MONEDA_SMN))
				this.openAmtFilters.cCurrencyID = schema.getC_Currency_ID();

			if (this.openAmtFilters.tipoMoneda.equalsIgnoreCase(ReportOpenAmt.TIPO_MONEDA_SMN) || this.openAmtFilters.tipoMoneda.equalsIgnoreCase(ReportOpenAmt.TIPO_MONEDA_SME)) 
				whereFiltros += " AND mp.c_currency_id = " + this.openAmtFilters.cCurrencyID;
		
			if (this.openAmtFilters.partnerType != null){
				if (this.openAmtFilters.partnerType.equalsIgnoreCase(ReportOpenAmt.PARTNER_CLIENTE))
					whereFiltros += " AND mp.tipomp='TER' ";

				if (this.openAmtFilters.partnerType.equalsIgnoreCase(ReportOpenAmt.PARTNER_PROVEEDOR))
					whereFiltros += " AND mp.tipomp='PRO' ";
			}
			else{
				if (this.openAmtFilters.cBPGroupID == ReportOpenAmt.GRUPO_CLIENTE)
					whereFiltros += " AND mp.tipomp='TER' ";

				if (this.openAmtFilters.cBPGroupID == ReportOpenAmt.GRUPO_PROVEEDOR)
					whereFiltros += " AND mp.tipomp='PRO' ";
				
			}
			

			if (this.openAmtFilters.salesRepID > 0)
				whereFiltros += " AND bp.salesrep_id = " + this.openAmtFilters.salesRepID;
			
			if (this.openAmtFilters.collectorID > 0)
				whereFiltros += " AND bp.collector_id = " + this.openAmtFilters.collectorID;

			if (this.openAmtFilters.canalVentaID > 0)
				whereFiltros += " AND bp.uy_canalventas_id = " + this.openAmtFilters.canalVentaID;

			insert = "INSERT INTO " + TABLA_MOLDE + " (fechahasta, idreporte, fecreporte, ad_user_id, ad_client_id, ad_org_id," +
					" isinvoice, c_bpartner_id, bpvalue, bpname, isreceipt, record_id, documentno, c_currency_id, currencyname, c_doctype_id," +					
					" docname, datetrx, duedate, amtdocument, amtacumulate, amtopen)";

			StringBuilder strb = new StringBuilder("");
			Timestamp dateTo = TimeUtil.trunc(this.openAmtFilters.dateTo, TimeUtil.TRUNC_DAY);
			
			this.showHelp("Obteniendo datos...");
			
			String whereOrg = "";
			if (this.openAmtFilters.adOrgID > 0) 
				whereOrg = " AND mp.ad_org_id =" + this.openAmtFilters.adOrgID;
			
			// Facturas
			strb.append(" SELECT '" + dateTo + "'::timestamp without time zone,'" + this.idReporte + "',current_date," + 
						this.openAmtFilters.adUserID + "," + this.openAmtFilters.adClientID + "," + this.openAmtFilters.adOrgID + "," +
						" 'N' as isinvoice, mp.c_bpartner_id, bp.value as bpvalue, (bp.name || ' - ' ||coalesce(bp.name2,'')) as bpname, " +
						" CASE WHEN mp.tipomp='TER' THEN 'Y' ELSE 'N' END as issotrx, " +
						" mp.uy_mediospago_id, mp.checkno, mp.c_currency_id, cur.description as curname, " +
					    " mp.c_doctype_id, doc.printname, mp.datetrx, mp.duedate, mp.payamt, 0, mp.payamt " +  
					    " FROM uy_mediospago mp " +
					    " INNER JOIN uy_linepayment lp ON mp.uy_mediospago_id = lp.uy_mediospago_id " +
					    " INNER JOIN c_payment payment ON lp.c_payment_id = payment.c_payment_id " +
					    " INNER JOIN c_doctype doc ON mp.c_doctype_id = doc.c_doctype_id " +
					    " INNER JOIN c_currency cur ON mp.c_currency_id = cur.c_currency_id " +
					    " INNER JOIN c_bpartner bp ON mp.c_bpartner_id = bp.c_bpartner_id " +
						" WHERE mp.ad_client_id =" + this.openAmtFilters.adClientID +
						  whereOrg +
					    " AND mp.duedate >='" + this.openAmtFilters.dateTo + "'" + 
					    " AND mp.estado!='REC' "  +
					    " AND mp.docstatus='CO' " + whereFiltros +					    
					    " AND payment.docstatus='CO'");

		
			sql = strb.toString();
			
			DB.executeUpdateEx(insert + sql, null);
			
 		}
		catch (Exception e)
		{
			throw e;
		}		
	}

	
	/**
	 * Calcula acumulado, saldos en moneda y signo correcto en tabla temporal. 
	 * @throws Exception
	 */
	private void calculoSaldosCuentaDocumentada() throws Exception{

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		BigDecimal dividerate = Env.ZERO;
		int curFromID = 0, curToID = 0;
		MCurrency currency = new MCurrency(Env.getCtx(), this.openAmtFilters.cCurrencyID,null);
		
		try{
			
			this.showHelp("Calculo de saldos...");
			
			sql = "SELECT * " +
				  " FROM " + TABLA_MOLDE +
				  " WHERE idreporte=?" +
				  " ORDER BY bpvalue, datetrx, c_doctype_id, record_id ASC";
			
			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, null);
			pstmt.setString(1, this.idReporte);
			
			rs = pstmt.executeQuery ();
			
			rs.last();
			int totalRowCount = rs.getRow(), rowCount = 0;
			rs.beforeFirst();
			
			String action = ""; 
			BigDecimal saldoAcumulado = Env.ZERO, totalDocumento = Env.ZERO, saldoPendiente = Env.ZERO;
			int cBPartnerID = 0, cBPartnerIDAux = 0, cDocTypeID = 0, recordID = 0;
			Timestamp fechaConversionMoneda = TimeUtil.trunc(this.openAmtFilters.dateTo, TimeUtil.TRUNC_DAY);
			
			// Corte de control por socio de negocio
			while (rs.next()){

				this.showHelp("Procesando linea " + rowCount++ + " de " + totalRowCount);
				
				cDocTypeID = rs.getInt("c_doctype_id");
				recordID = rs.getInt("record_id");
				cBPartnerIDAux = rs.getInt("c_bpartner_id");

				// Si hay cambio de socio de negocio
				if (cBPartnerIDAux != cBPartnerID){
					saldoAcumulado = Env.ZERO;
					cBPartnerID = cBPartnerIDAux;
				}

				// Si el documento tiene una moneda distinta a la moneda del reporte, hago la conversion al tipo de cambio del dia
				totalDocumento = rs.getBigDecimal("amtdocument");
				saldoPendiente = rs.getBigDecimal("amtopen");
				
				if (rs.getInt("c_currency_id") != this.openAmtFilters.cCurrencyID){
					
					if(rs.getInt("c_currency_id") == 142){
						
						totalDocumento = MConversionRate.convert(Env.getCtx(), totalDocumento, rs.getInt("c_currency_id"), 
								 this.openAmtFilters.cCurrencyID, fechaConversionMoneda, 0, this.openAmtFilters.adClientID, 
								 this.openAmtFilters.adOrgID);
						saldoPendiente = MConversionRate.convert(Env.getCtx(), saldoPendiente, rs.getInt("c_currency_id"), 
						 this.openAmtFilters.cCurrencyID, fechaConversionMoneda, 0, this.openAmtFilters.adClientID, 
						 this.openAmtFilters.adOrgID);						
						
					} else {
						
						curFromID = rs.getInt("c_currency_id");
						curToID = this.openAmtFilters.cCurrencyID;
						
						BigDecimal rateFrom = MConversionRate.getDivideRate(142, curToID, fechaConversionMoneda, 0, this.openAmtFilters.adClientID, 0);
						BigDecimal rateTo = MConversionRate.getDivideRate(142, curFromID, fechaConversionMoneda, 0, this.openAmtFilters.adClientID, 0);
						
						if ((rateFrom != null) && (rateTo != null)){
							if (rateTo.compareTo(Env.ZERO) > 0) {
								dividerate = rateFrom.divide(rateTo, 3, RoundingMode.HALF_UP);		
							}
						} else {
							
							totalDocumento = Env.ZERO;
							saldoPendiente = Env.ZERO;							
						}
						
						if(dividerate!=null && dividerate.compareTo(Env.ZERO)!=0){										

							totalDocumento = totalDocumento.divide(dividerate, 2, RoundingMode.HALF_UP);
							saldoPendiente = saldoPendiente.divide(dividerate, 2, RoundingMode.HALF_UP);							
							
						} else {
							
							totalDocumento = Env.ZERO;
							saldoPendiente = Env.ZERO;							
						}									
						
					}				
					
				}

				//OpenUp Nicolas Sarlabos #879 28/11/2011, se elimina codigo innecesario 				

				saldoAcumulado = saldoAcumulado.add(saldoPendiente);
				
				action = " UPDATE " + TABLA_MOLDE + 
		 		 		 " SET amtacumulate =" + saldoAcumulado + "," +
		 		 		 " amtdocument = " + totalDocumento + "," +
		 		 		 " amtopen = " + saldoPendiente + "," +
		 		 		 " c_currency_id = " + this.openAmtFilters.cCurrencyID + "," +
		 		 		 " currencyname = '" + currency.getDescription() + "'" +
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
	 * Elimina registros de temporal con saldos en cero.
	 * @throws Exception 
	 */
	private void deleteSaldosCero() throws Exception{
		
		String sql = "";
		try{
			
			this.showHelp("Eliminando sin saldo...");
			
			sql = " DELETE FROM " + TABLA_MOLDE +
					" WHERE idreporte ='" + this.idReporte + "'" +
					" AND amtopen >=-0.99 and amtopen <= 0.99 ";
			
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
}
