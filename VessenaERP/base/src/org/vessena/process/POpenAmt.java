/**
 * @author OpenUp SBT Issue#  11/3/2016 15:39:16
 */
package org.openup.process;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.apps.ProcessCtl;
import org.compiere.apps.Waiting;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MBPartner;
import org.compiere.model.MClient;
import org.compiere.model.MConversionRate;
import org.compiere.model.MCurrency;
import org.compiere.model.MOrg;
import org.compiere.model.MPInstance;
import org.compiere.model.MPInstancePara;
import org.compiere.model.MProcess;
import org.compiere.model.MUser;
import org.compiere.process.ProcessInfo;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.report.ReportStarter;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.openup.beans.ReportAccountStatus;
import org.openup.beans.ReportOpenAmt;
import org.openup.util.OpenUpUtils;

/**
 * @author OpenUp SBT Issue #5069  11/3/2016 15:39:16
 *	Clase para procesar reporte de Saldos de Cuenta Corriente tanto RV como Jasper
 */
public class POpenAmt extends SvrProcess {

	private Waiting waiting = null;
	private ReportOpenAmt openAmtFilters = new ReportOpenAmt();
	private static final String TABLA_MOLDE = "uy_molde_openamt";
	private String idReporte = "";

	/**
	 * @author OpenUp SBT Issue #5069  11/3/2016 15:39:16
	 */
	public POpenAmt() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		//prepare igual que clase RCtaCteOpenAmtRV
		//Los mismos campos que el reporte cta crte mas uno
		// Obtengo parametros y los recorro
		ProcessInfoParameter[] para = getParameter();

		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName().trim();
			System.out.println(name);
			if (name!= null){
			
				//Obtengo vencimiento al 
				if (name.equalsIgnoreCase("fechaHasta")){
					this.openAmtFilters.dateTo = (Timestamp)para[i].getParameter();
				}

				//Obtengo el tipo de moneda del reporte, una o todas
				if (name.equalsIgnoreCase("ReportCurrencyType")){
					this.openAmtFilters.tipoMoneda = (String)para[i].getParameter();
				}
				//Obtengo moneda del reporte
				if (name.equalsIgnoreCase("C_Currency_ID")){
					this.openAmtFilters.cCurrencyID = ((BigDecimal)para[i].getParameter()).intValueExact();
				}
				//Obtengo modalidad (cuenta abierta o cuenta docuementada)
				if (name.equalsIgnoreCase("tipoReporteSaldoPend")){
					this.openAmtFilters.tipoReporte = (String)para[i].getParameter();
				}
				//Tipo Reporte (CLIENTE - PROVEDOR) 
				if (name.equalsIgnoreCase("AcctStatusType")){
					if (para[i].getParameter() != null) this.openAmtFilters.partnerType = ((String)para[i].getParameter());
				}		
				//Obtengo el socio de negocio
				if (name.equalsIgnoreCase("C_BPartner_ID")){
					if (para[i].getParameter() != null) this.openAmtFilters.cBPartnerID = ((BigDecimal)para[i].getParameter()).intValueExact();
				}
				//Obtengo el formato del reporte (JASPER O RV)
				if (name.equalsIgnoreCase("ReportType")){
					if (para[i].getParameter() != null) this.openAmtFilters.reportType = ((String)para[i].getParameter());
				}
				if (name.equalsIgnoreCase("AD_User_ID")) {
					this.openAmtFilters.adUserID = ((BigDecimal)para[i].getParameter()).intValueExact();
				}
				if (name.equalsIgnoreCase("AD_Client_ID")){
					this.openAmtFilters.adClientID = ((BigDecimal)para[i].getParameter()).intValueExact();
				}
				if (name.equalsIgnoreCase("AD_Org_ID")) {
					this.openAmtFilters.adOrgID = ((BigDecimal)para[i].getParameter()).intValueExact();
				}
			}
		}
							
		if(this.openAmtFilters.partnerType.equalsIgnoreCase(ReportOpenAmt.PARTNER_CLIENTE)){
			this.openAmtFilters.isSoTrx = ("Y");
		}else{
			this.openAmtFilters.isSoTrx = ("N");
		}
		this.openAmtFilters.isDueDate = true;
		
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		this.setWaiting(this.getProcessInfo().getWaiting());
		//igual que el execute de la clase RCtaCteAccountStatus
		// Delete de reportes anteriores de este usuario para ir limpiando la tabla molde
		this.deleteInstanciasViejasReporte();
		
		if (this.openAmtFilters.tipoReporte.equalsIgnoreCase(ReportOpenAmt.CTA_ABIERTA)){
			this.getDataCuentaAbierta();
			this.calculoSaldosCuentaAbierta();
		}
		else{
//			this.getDataCuentaDocumentada();
//			this.calculoSaldosCuentaDocumentada();
		}		

		this.deleteSaldosCero();
				
		this.showResult();
				
		return "OK";
	}

	
	
	/**
	 * Carga datos para el reporte.
	 * @author OpenUp SBT Issue #5069  11/3/2016 17:16:40
	 * @throws Exception
	 */
	private void getDataCuentaAbierta()  throws Exception {

		
		String insert ="", sql = "", whereFiltros = "";

		try
		{
			// Obtengo id para este reporte
			this.idReporte = UtilReportes.getReportID(Long.valueOf(this.openAmtFilters.adUserID));
		
			MClient client = new MClient(Env.getCtx(), this.openAmtFilters.adClientID, null);
			MAcctSchema schema = client.getAcctSchema();
			//OpenUp. Nicolas Sarlabos. 16/07/2013. #1143
			
			// Armo where de filtros
			if(this.openAmtFilters.isSoTrx.equalsIgnoreCase("Y")){
				if (this.openAmtFilters.cBPartnerID > 0){
					//if (this.openAmtFilters.customerID > 0) {
					MBPartner partner = new MBPartner(Env.getCtx(),this.openAmtFilters.cBPartnerID,null);//instancio cliente
					//si es un cliente padre
					if(partner.isParent()){
						whereFiltros += " AND hdr.c_bpartner_id IN (select c_bpartner_id from c_bpartner where bpartner_parent_id = " + this.openAmtFilters.cBPartnerID
									+ " OR c_bpartner_id = " + this.openAmtFilters.cBPartnerID + ")";
					} else {
						whereFiltros += " AND hdr.c_bpartner_id = " + this.openAmtFilters.cBPartnerID;
					}
				}

			} 			
			//Fin OpenUp. #1143
			if(this.openAmtFilters.isSoTrx.equalsIgnoreCase("N")){
				if (this.openAmtFilters.cBPartnerID > 0){
					whereFiltros += " AND hdr.c_bpartner_id = " + this.openAmtFilters.cBPartnerID;
				}
			}
			
//			if (this.openAmtFilters.tipoMoneda.equalsIgnoreCase(ReportOpenAmt.TIPO_MONEDA_SMN)){
//				this.openAmtFilters.cCurrencyID = schema.getC_Currency_ID();
//			}
//
//			if (this.openAmtFilters.tipoMoneda.equalsIgnoreCase(ReportOpenAmt.TIPO_MONEDA_SMN) || 
//					this.openAmtFilters.tipoMoneda.equalsIgnoreCase(ReportOpenAmt.TIPO_MONEDA_SME)){
//				whereFiltros += " AND hdr.c_currency_id = " + this.openAmtFilters.cCurrencyID;
//			}
			
			//OpenUp SBT 29/03/2016 Issue #5657
			if(this.openAmtFilters.tipoMoneda.equals(ReportOpenAmt.REPORTCURRTYPE_UNA)){//Si es una moneda obtengo solo documentos de la moneda seleccionada
				if(this.openAmtFilters.cCurrencyID<=0) throw new AdempiereException("Seleccione moneda de reporte");
				whereFiltros += " AND hdr.c_currency_id = " + this.openAmtFilters.cCurrencyID;
			}
			
			//Se agrega condición isSoTrx
			whereFiltros += " AND doc.issotrx  = '" +this.openAmtFilters.isSoTrx+ "' ";

			if (this.openAmtFilters.salesRepID > 0){
				whereFiltros += " AND hdr.salesrep_id = " + this.openAmtFilters.salesRepID;
				System.out.println("ENTRA 1-SALES_REP");
			}
			
			if (this.openAmtFilters.collectorID > 0){
				whereFiltros += " AND bp.collector_id = " + this.openAmtFilters.collectorID;
				System.out.println("ENTRA 1-COLLECTOR");

			}

			if (this.openAmtFilters.canalVentaID > 0){
				whereFiltros += " AND bp.uy_canalventas_id = " + this.openAmtFilters.canalVentaID;
				System.out.println("ENTRA 1-CANALVENTAS");
			}

			if (this.openAmtFilters.departamentoID > 0){
				whereFiltros += " AND loc.uy_departamentos_id = " + this.openAmtFilters.departamentoID;
				System.out.println("ENTRA 1-DEPARTAMENTOS");
			}
			
			if (this.openAmtFilters.localidadID > 0){
				whereFiltros += " AND loc.uy_localidades_id = " + this.openAmtFilters.localidadID;
				System.out.println("ENTRA 1-LOCALIDADES");
			}


			insert = "INSERT INTO " + TABLA_MOLDE + " (fechahasta, idreporte, fecreporte, ad_user_id, ad_client_id, ad_org_id," +
					" isinvoice, c_bpartner_id, bpvalue, bpname, isreceipt, record_id, documentno, c_currency_id, currencyname, c_doctype_id," +					
					" docname, datetrx, duedate, amtdocument, amtacumulate, amtopen, poreference,amtdocumentsrc,amtopensrc,literalquote)";

			StringBuilder strb = new StringBuilder("");
			Timestamp dateTo = TimeUtil.trunc(this.openAmtFilters.dateTo, TimeUtil.TRUNC_DAY);
			
			String whereOrg = "";
			if (this.openAmtFilters.adOrgID > 0){
				whereOrg = " AND hdr.ad_org_id =" + this.openAmtFilters.adOrgID;
			}
			
			this.showHelp("Obteniendo datos...");
			String bpName = "";
			if (this.openAmtFilters.reportType.equalsIgnoreCase("RV"))
				bpName = "(bp.name) as bpname";
			else
				bpName = "(bp.name || ' - ' ||coalesce(bp.name2,'')) as bpname"; // (bp.name || ' - ' ||coalesce(bp.name2,'')) as bpname
			// Facturas
			strb.append(" SELECT '" + dateTo + "'::timestamp without time zone,'" + this.idReporte + "',current_date," + this.openAmtFilters.adUserID + "," +
						this.openAmtFilters.adClientID + "," + this.openAmtFilters.adOrgID + "," +
					//--	" 'Y' as isinvoice, hdr.c_bpartner_id, bp.value as bpvalue, (bp.name || ' - ' ||coalesce(bp.name2,'')) as bpname, 'N' as isreceipt, " +
					" 'Y' as isinvoice, hdr.c_bpartner_id, bp.value as bpvalue,"+bpName+", 'N' as isreceipt, " +	
					//--" hdr.c_invoice_id, hdr.documentno, hdr.c_currency_id, cur.description as curname, " +
						" invsch.c_invoicepayschedule_id, hdr.documentno, hdr.c_currency_id, cur.description as curname, " +
						" hdr.c_doctype_id, doc.printname, hdr.dateinvoiced, " +
						//" paymenttermduedate(hdr.c_paymentterm_id, hdr.dateinvoiced) as duedate, " +
						//--" coalesce(hdr.duedate, paymenttermduedate(hdr.c_paymentterm_id, hdr.dateinvoiced)) as duedate, " +
						" coalesce(invsch.duedate) as duedate, " +
						//---SBT" coalesce(hdr.grandtotal,0) as grandtotal,0,0, coalesce(hdr.poreference,'') as poreference, " +
						" coalesce(invsch.dueamt,0) as grandtotal,0,0, coalesce(hdr.poreference,'') as poreference, " +
						//OpenUp SBT 14-03-2016 Se agregan nuevos campos en 0
						" coalesce(invsch.dueamt,0),0,invsch.literalquote " +//--> amtopensrc,amtedocuemtnsrc y literalquote
						" FROM c_invoice hdr " +
						//OpenUp SBT 14-03-2016 Se agrega join con tabla c_invoicepayschedule
						" inner join c_invoicepayschedule  invsch ON (hdr.c_invoice_id =  invsch.c_invoice_id) "+
						// FIN Openup SBT
						" inner join c_doctype doc on hdr.c_doctype_id = doc.c_doctype_id" +
						" INNER JOIN c_currency cur on hdr.c_currency_id = cur.c_currency_id " +
						" INNER JOIN c_bpartner bp on hdr.c_bpartner_id = bp.c_bpartner_id " +
						" left outer join c_bpartner_location suc on hdr.c_bpartner_location_id = suc.c_bpartner_location_id " +		
					    " left outer join c_location loc on suc.c_location_id = loc.c_location_id " +					    
					    " WHERE hdr.ad_client_id =" + this.openAmtFilters.adClientID + 
					    whereOrg +
					    " AND hdr.paymentruletype !='CO' " +
					    " AND doc.allocationbehaviour='INV' AND hdr.docstatus='CO' " +
					    //" AND " + ((!this.openAmtFilters.isDueDate) ? " hdr.dateinvoiced <='" : " invsch.duedate <='") + this.openAmtFilters.dateTo + "'" + whereFiltros);
					    " AND  invsch.duedate <='" + this.openAmtFilters.dateTo + "' " + whereFiltros);

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
						" 'N' as isinvoice, hdr.c_bpartner_id, bp.value as bpvalue, "+bpName+", 'N' as isreceipt, " +
						//" hdr.c_invoice_id, hdr.documentno, hdr.c_currency_id, cur.description as curname, " +
						" invsch.c_invoicepayschedule_id, hdr.documentno, hdr.c_currency_id, cur.description as curname, " +
						" hdr.c_doctype_id, doc.printname, hdr.dateinvoiced, " +
						//--SBT OpenUp 14-03-2016 
						//--SBT-" paymenttermduedate(hdr.c_paymentterm_id, hdr.dateinvoiced) as duedate, " +
						" invsch.duedate, "+
						//" coalesce(hdr.grandtotal,0) as grandtotal,0,0, coalesce(hdr.poreference,'') as poreference, " +
						" coalesce(invsch.dueamt,0) as grandtotal,0,0, coalesce(hdr.poreference,'') as poreference, " +
						//OpenUp SBT 14-03-2016 Se agregan nuevos campos en 0
						" coalesce(invsch.dueamt,0) ,0,invsch.literalquote " +//--> amtopensrc,amtedocuemtnsrc y literalquote
						" FROM c_invoice hdr " +
						//OpenUp SBT 14-03-2016 Se agrega join con tabla c_invoicepayschedule
						" inner join c_invoicepayschedule  invsch ON (hdr.c_invoice_id =  invsch.c_invoice_id) "+
						// FIN Openup SBT
						" inner join c_doctype doc on hdr.c_doctype_id = doc.c_doctype_id " +
						" INNER JOIN c_currency cur on hdr.c_currency_id = cur.c_currency_id " +
						" INNER JOIN c_bpartner bp on hdr.c_bpartner_id = bp.c_bpartner_id " +
						" left outer join c_bpartner_location suc on hdr.c_bpartner_location_id = suc.c_bpartner_location_id " +
						" left outer join c_location loc on suc.c_location_id = loc.c_location_id " + 
						" WHERE hdr.ad_client_id =" + this.openAmtFilters.adClientID + 
						whereOrg +
						" AND hdr.paymentruletype !='CO' " +
						//--SBT Consultar el tema de la fecha !!!!!!!!!!!!!!!!!!!!!!!!!!
					   //-- SBT  " AND doc.allocationbehaviour='PAY' AND hdr.docstatus='CO' AND hdr.dateinvoiced <='" + this.openAmtFilters.dateTo + "'" + whereFiltros);
					   " AND doc.allocationbehaviour='PAY' AND hdr.docstatus='CO' AND invsch.duedate <='" + this.openAmtFilters.dateTo + "'" + whereFiltros);
			// Recibos
			strb.append(" UNION SELECT '" + dateTo + "'::timestamp without time zone,'" + this.idReporte + "',current_date," + this.openAmtFilters.adUserID + "," +
						this.openAmtFilters.adClientID + "," + this.openAmtFilters.adOrgID + "," +						
						" 'N' as isinvoice, hdr.c_bpartner_id, bp.value as bpvalue, "+bpName+", 'Y' as isreceipt, " +					
						" hdr.c_payment_id, hdr.documentno, hdr.c_currency_id, cur.description as curname, " +
						" hdr.c_doctype_id, doc.printname, hdr.datetrx, null, coalesce(hdr.payamt,0) as payamt, " + 
						" 0,0, '' as poreference, " +
						//OpenUp SBT 14-03-2016 Se agregan nuevos campos en 0
						" 0,0,'0' " +//--> amtopensrc,amtedocuemtnsrc y literalquote
						" FROM c_payment hdr inner join c_doctype doc on hdr.c_doctype_id = doc.c_doctype_id " +
						" INNER JOIN c_currency cur on hdr.c_currency_id = cur.c_currency_id " +
						" INNER JOIN c_bpartner bp on hdr.c_bpartner_id = bp.c_bpartner_id " + 
						" WHERE hdr.ad_client_id =" + this.openAmtFilters.adClientID +
						whereOrg +
						" AND hdr.isreconciled ='N' " +
						" AND doc.allocationbehaviour='PAY' AND hdr.docstatus='CO' AND hdr.datetrx <='" + this.openAmtFilters.dateTo + "' " + whereFiltros);

			sql = strb.toString();
			
			DB.executeUpdateEx(insert + sql, null);
			
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
			
			this.showHelp("Eliminando sin saldo...");
			
			sql = " DELETE FROM " + TABLA_MOLDE +
					" WHERE idreporte ='" + this.idReporte + "'" +
					" AND amtopen >=-0.99 and amtopen <= 0.99 ";
			
			DB.executeUpdateEx(sql,null);
			this.showHelp("Iniciando Vista Previa...");
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
	
	public Waiting getWaiting() {
		return this.waiting;
	}
	
	public void setWaiting(Waiting value) {
		this.waiting = value;
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
			if(this.openAmtFilters.isSoTrx.equalsIgnoreCase("Y")){
		//	if (this.openAmtFilters.customerID > 0) {

				MBPartner partner = new MBPartner(Env.getCtx(),this.openAmtFilters.cBPartnerID,null);//instancio cliente

				//si es un cliente padre
				if(partner.isParent()){

					whereFiltros += " AND mp.c_bpartner_id IN (select c_bpartner_id from c_bpartner where bpartner_parent_id = " 
					+ this.openAmtFilters.cBPartnerID + " OR c_bpartner_id = " + this.openAmtFilters.cBPartnerID + ")";

				} else whereFiltros += " AND mp.c_bpartner_id = " + this.openAmtFilters.cBPartnerID;

			} 				
			//Fin OpenUp. #1143
			if(this.openAmtFilters.isSoTrx.equalsIgnoreCase("N")){
			//if (this.openAmtFilters.vendorID > 0)
				whereFiltros += " AND mp.c_bpartner_id = " + this.openAmtFilters.cBPartnerID;
			}
			
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
				  //--" ORDER BY bpvalue, datetrx, c_doctype_id, record_id ASC";
				  " ORDER BY bpname, duedate, c_doctype_id, record_id ASC";
			
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
	 * Calcula acumulado, saldos en moneda y signo correcto en tabla temporal. 
	 * @throws Exception
	 */
	private void calculoSaldosCuentaAbierta() throws Exception{

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		BigDecimal dividerate = Env.ZERO;
		int curFromID = 0, curToID = 0;int curFromIDAux = 0;
		MCurrency currencyTo = new MCurrency(Env.getCtx(), this.openAmtFilters.cCurrencyID,null);
		MCurrency currencyFrom = null;
		curToID = this.openAmtFilters.cCurrencyID;
		try{
			
			this.showHelp("Calculo de saldos...");
			String orderBy = "";
			if(this.openAmtFilters.tipoMoneda.equals(ReportOpenAmt.REPORTCURRTYPE_TODASTRX)){
				//orderBy = " ORDER BY c_currency_id, bpname, datetrx, c_doctype_id, record_id ASC";
				orderBy = " ORDER BY c_currency_id, bpname, duedate, c_doctype_id, record_id ASC";

			}else{
				//orderBy = " ORDER BY bpname, datetrx, c_doctype_id, record_id ASC";
				orderBy = " ORDER BY bpname, duedate, c_doctype_id, record_id ASC";

			}
			sql = "SELECT * " +
				  " FROM " + TABLA_MOLDE +
				  " WHERE idreporte=?" +
				  orderBy;
				  //" ORDER BY bpname, datetrx, c_doctype_id, record_id ASC";
			
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
			
			//SBT OpenUp 14-03-2015
			BigDecimal saldoPendienteSrc = Env.ZERO;
			BigDecimal totalDocumentoSrc = Env.ZERO;
			BigDecimal currencyRate = Env.ONE; 
			BigDecimal currencyRateOp = Env.ONE;
			boolean multiCurr = (this.openAmtFilters.tipoMoneda.equalsIgnoreCase(ReportAccountStatus.REPORTCURRTYPE_TODAS));
			
			if (fechaConversionMoneda.compareTo(today) > 0){
				fechaConversionMoneda = today;
			}
			
			// Corte de control por socio de negocio
			while (rs.next()){

				this.showHelp("Procesando linea " + rowCount++ + " de " + totalRowCount);
				
				cDocTypeID = rs.getInt("c_doctype_id");
				recordID = rs.getInt("record_id");
				cBPartnerIDAux = rs.getInt("c_bpartner_id");
				curFromIDAux = rs.getInt("c_currency_id");
				
				//OpenUp SBT 29/03/2016 Issue #5657 Si es moneda trx tengo que hacer corte de control por moneda y por socio de negocio
				// Si hay cambio de socio de negocio
				if(this.openAmtFilters.tipoMoneda.equals(ReportOpenAmt.REPORTCURRTYPE_TODASTRX)){
					if (curFromIDAux!=curFromID  || cBPartnerIDAux != cBPartnerID){
						saldoAcumulado = Env.ZERO;
						cBPartnerID = cBPartnerIDAux;
						curFromID = curFromIDAux;
					}
				}else{
					if (cBPartnerIDAux != cBPartnerID){
						saldoAcumulado = Env.ZERO;
						cBPartnerID = cBPartnerIDAux;
					}	
					curFromID =  rs.getInt("c_currency_id");
				}

				// Si el documento tiene una moneda distinta a la moneda del reporte, hago la conversion al tipo de cambio del dia
				totalDocumento = rs.getBigDecimal("amtdocument");
				totalDocumentoSrc = rs.getBigDecimal("amtdocumentsrc");
				currencyFrom =  new MCurrency(Env.getCtx(), curFromID,null);
				
				//OpenUp Nicolas Sarlabos 12/01/2012 
				String nombreTabla = "";
				String var = "";
				
				if (rs.getString("isinvoice").equalsIgnoreCase("Y")) { //si es factura
					nombreTabla = "alloc_invoiceamtopenxdate_sch";
					var = "c_invoicepayschedule_id"; //--> consultar si corresponde c_invoice o c_invoicepayschedule_id 
				} else if (rs.getString("isreceipt").equalsIgnoreCase("Y")) { //si es recibo
					nombreTabla = "alloc_paymentamtopenxdate";
					var = "c_payment_id";
				} else if (rs.getString("isinvoice").equalsIgnoreCase("N") & rs.getString("isreceipt").equalsIgnoreCase("N")) {
					nombreTabla = "alloc_creditnoteamtopenxdate_sch"; //si es nota de credito NUEVA VISTA
					var = "c_invoicepayschedule_id"; //--> consultar si corresponde c_invoice o c_invoicepayschedule_id 
				}

				saldoPendiente = totalDocumento.subtract(getTotalAfectado(nombreTabla, var, recordID));				
				saldoPendienteSrc = saldoPendiente;
				//SBT INI
				if(multiCurr){
					if(this.openAmtFilters.cCurrencyID!=rs.getInt("c_currency_id")){
						//Obtengo el tipo de cambio 
						currencyRate = OpenUpUtils.getCurrencyRateForCur1Cur2(today,
								this.openAmtFilters.cCurrencyID,rs.getInt("c_currency_id"), 
								this.openAmtFilters.adClientID, this.openAmtFilters.adOrgID);
						
						currencyRateOp = OpenUpUtils.getCurrencyRateForCur1Cur2(today,rs.getInt("c_currency_id"),
								this.openAmtFilters.cCurrencyID, this.openAmtFilters.adClientID, this.openAmtFilters.adOrgID);
						
						if(currencyRate.equals(Env.ZERO)) throw new AdempiereException("No hay tasa de cambio para el docuemnto");
						currencyRateOp = currencyRateOp.setScale(2, RoundingMode.HALF_UP);
						currencyRate = currencyRate.setScale(2, RoundingMode.HALF_UP);
						saldoPendiente = saldoPendiente.multiply(currencyRate).setScale(2, RoundingMode.HALF_UP);
					}else{
						
					}
				}	
				//SBT FIN
				
				//fin OpenUp Nicolas Sarlabos 12/01/2012
//				if (rs.getInt("c_currency_id") != this.openAmtFilters.cCurrencyID){
//										
//					if(rs.getInt("c_currency_id") == 142 || this.openAmtFilters.cCurrencyID == 142){
//						
//						totalDocumento = MConversionRate.convert(Env.getCtx(), totalDocumento, rs.getInt("c_currency_id"), 
//								 this.openAmtFilters.cCurrencyID, fechaConversionMoneda, 0, this.openAmtFilters.adClientID, 
//								 this.openAmtFilters.adOrgID);
//						saldoPendiente = MConversionRate.convert(Env.getCtx(), saldoPendiente, rs.getInt("c_currency_id"), 
//						 this.openAmtFilters.cCurrencyID, fechaConversionMoneda, 0, this.openAmtFilters.adClientID, 
//						 this.openAmtFilters.adOrgID);						
//						
//					} else {
//						
//						curFromID = rs.getInt("c_currency_id");
//						curToID = this.openAmtFilters.cCurrencyID;
//						
//						BigDecimal rateFrom = MConversionRate.getDivideRate(142, curToID, fechaConversionMoneda, 0, this.openAmtFilters.adClientID, 0);
//						BigDecimal rateTo = MConversionRate.getDivideRate(142, curFromID, fechaConversionMoneda, 0, this.openAmtFilters.adClientID, 0);
//						
//						if ((rateFrom != null) && (rateTo != null)){
//							if (rateTo.compareTo(Env.ZERO) > 0) {
//								dividerate = rateFrom.divide(rateTo, 3, RoundingMode.HALF_UP);		
//							}
//						} else {
//							
//							totalDocumento = Env.ZERO;
//							saldoPendiente = Env.ZERO;							
//						}
//						
//						if(dividerate!=null && dividerate.compareTo(Env.ZERO)!=0){										
//
//							totalDocumento = totalDocumento.divide(dividerate, 2, RoundingMode.HALF_UP);
//							saldoPendiente = saldoPendiente.divide(dividerate, 2, RoundingMode.HALF_UP);							
//							
//						} else {
//							
//							totalDocumento = Env.ZERO;
//							saldoPendiente = Env.ZERO;							
//						}				
//						
//					}				
//			
//				}
								
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
							saldoAcumulado = saldoAcumulado.add(saldoPendiente);//.setScale(2, RoundingMode.HALF_UP);
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
				if(multiCurr){
					if(rs.getInt("c_currency_id") != this.openAmtFilters.cCurrencyID){
						//saldoPendiente = saldoPendiente.multiply(currencyRate);//.setScale(2, RoundingMode.HALF_UP);
						totalDocumento = totalDocumento.multiply(currencyRate);
						//saldoPendienteSrc = saldoPendienteSrc.multiply(currencyRateOp);//.setScale(2, RoundingMode.HALF_UP);
					}
				}
				
				action = " UPDATE " + TABLA_MOLDE + 
		 		 		 " SET amtacumulate =" + saldoAcumulado + "," +
		 		 		 " amtdocument = " + totalDocumento + "," +
		 		 		 " amtopen = " + saldoPendiente + "," +
		 		 		 " c_currency_id = " + curFromID  + "," +
		 		 		 " currencyname = '" + currencyFrom.getDescription() + "'," +
		 		 		 //SBT 14-03-2016
		 		 		 " CurrencyRate = "+currencyRate+ "," +
		 		 		 " amtopensrc = "+saldoPendienteSrc +" "+
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
		System.out.println(recordID);
		BigDecimal totalAfectado=Env.ZERO;
		
		if (nombre_tabla != "" & var != "" & recordID > 0) {
			String sql = "SELECT coalesce(SUM(amtallocated),0) FROM " + nombre_tabla + " WHERE " + var + " =" + recordID + " AND dateallocated <='"
					+ this.openAmtFilters.dateTo + "'";
			System.out.println(sql);
			totalAfectado = DB.getSQLValueBD(null, sql);
		}
	
		return totalAfectado;

	}

	/**
	 * Se obtienen los datos a ser despelado en los reportes y dependiendo de la opción
	 * seleccionada se muestra un tipo de reporte u otro.
	 * @author OpenUp SBT Issue #5069  14/3/2016 14:47:15
	 */
	private void showResult() {
		String valueRep = ""; String currName = ""; String userName = ""; String orgName="";
		String titulo = "Saldos Pendentes de Cuenta Corriente";
		boolean jasper = false;
		if(this.openAmtFilters.reportType.equalsIgnoreCase("JASPER")){
			jasper = true;
		}

		if(this.openAmtFilters.tipoMoneda.equalsIgnoreCase(ReportAccountStatus.REPORTCURRTYPE_TODAS)){
			titulo += " Multimoneda - ";
			if(jasper){
				valueRep = "jpropenamtmulticur";//jasper multimoneda	
			}else{
				valueRep = "rvpopenamtmulticur";//rv multimoneda
			}
		}else if(this.openAmtFilters.tipoMoneda.equalsIgnoreCase(ReportAccountStatus.REPORTCURRTYPE_UNA)){
			titulo += " - ";
			if(jasper){
				valueRep = "jpropenamtsm";//jasper una moneda

			}else{
				valueRep = "rvpopenamtsm";//rv una moneda
			}
		}else{
			titulo += " MT - ";
			if(jasper){
				valueRep = "jpropenamtmtrx";
			}else{
				valueRep = "rvpopenamtmtrx";
			}
		}
		
		if(this.openAmtFilters.partnerType.equalsIgnoreCase(ReportAccountStatus.PARTNER_CLIENTE)){
			titulo = titulo + "Cliente";
		}else{
			titulo = titulo + "Proveedor";
		}
		
		MUser user = new MUser(getCtx(),this.openAmtFilters.adUserID,null);
		userName = user.getDescription();
		
		if(this.openAmtFilters.cCurrencyID>0){
			MCurrency curr = new MCurrency(getCtx(), this.openAmtFilters.cCurrencyID, null);
			currName = curr.getDescription();
		}
		
		MOrg org = new MOrg(getCtx(),this.openAmtFilters.adOrgID,null);
		orgName = org.getName();
		
		if(jasper){
			showJasper(valueRep,titulo,currName,orgName,userName);
		}else{
			showRV(valueRep,titulo,currName,orgName,userName);
		}
		
	}

	/**
	 *  Metodo para iniciar Report View correspondiente
	 * @author OpenUp SBT Issue#  14/3/2016 14:59:36
	 * @param valueRep
	 * @param titulo
	 * @param currName
	 * @param orgName
	 * @param userName
	 */	
	private void showRV(String valueRep, String titulo, String currName,
			String orgName, String userName) {
		int processID = MProcess.getProcess_ID(valueRep, null); //AD_process UY_RTT_CaratulaSolicitud
		if(0<processID){
			MPInstance instance = new MPInstance(Env.getCtx(), processID, 0);
			instance.saveEx();

			ProcessInfo pi = new ProcessInfo (titulo, processID);
			
			pi.setAD_Client_ID(this.openAmtFilters.adClientID);
			pi.setAD_User_ID(this.openAmtFilters.adUserID);
			pi.setAD_PInstance_ID (instance.getAD_PInstance_ID());
			
			//NUEVO
			MPInstancePara para = new MPInstancePara(instance, 10);
			para.setParameter("AD_Client_ID", this.openAmtFilters.adClientID);
			para.saveEx();
			MPInstancePara para5 = new MPInstancePara(instance, 20);
			para5.setParameter("AD_User_ID", this.openAmtFilters.adUserID);
			para5.saveEx();
			MPInstancePara para8 = new MPInstancePara(instance, 30);
			para8.setParameter("AD_Org_ID",this.openAmtFilters.adOrgID);
			para8.saveEx();
			
//			ReportStarter starter = new ReportStarter();
//			starter.startProcess(getCtx(), pi, null);
			
			ProcessCtl worker = new ProcessCtl(null, 0, pi, null);
			worker.start(); 
		
		}
	}
	
	/**
	 * Metodo para iniciar Jasper report correspondiente
	 * @author OpenUp SBT Issue#  11/3/2016 9:25:38
	 * @param valueRep
	 * @param titulo
	 * @param currName
	 * @param orgName
	 * @param userName
	 */
	private void showJasper(String valueRep,String titulo, String currName,String orgName, String userName) {
		int processID = MProcess.getProcess_ID(valueRep, null); //AD_process UY_RTT_CaratulaSolicitud
		if(0<processID){
			//currencyname, fechahasta(vto),orgname,aduserid,title,username 
			MPInstance instance = new MPInstance(Env.getCtx(), processID, 0);
			instance.saveEx();

			ProcessInfo pi = new ProcessInfo (titulo, processID);
			pi.setAD_PInstance_ID (instance.getAD_PInstance_ID());
			
			MPInstancePara para = new MPInstancePara(instance, 10);
			para.setParameter("currencyname", currName);
			para.saveEx();
			MPInstancePara para2 = new MPInstancePara(instance, 20);
			para2.setParameter("FechaHasta", this.openAmtFilters.dateTo);
			para2.saveEx();
			MPInstancePara para3 = new MPInstancePara(instance, 30);
			para3.setParameter("OrgName", orgName);
			para3.saveEx();
			MPInstancePara para4 = new MPInstancePara(instance, 40);
			para4.setParameter("AD_User_ID", new BigDecimal(this.openAmtFilters.adUserID));
			para4.saveEx();
			MPInstancePara para6 = new MPInstancePara(instance, 50);
			para6.setParameter("Title", titulo);
			para6.saveEx();
			MPInstancePara para7 = new MPInstancePara(instance, 60);
			para7.setParameter("UserName", userName);
			para7.saveEx();
			if(valueRep.equalsIgnoreCase("jpropenamtsm")){
				MPInstancePara para8 = new MPInstancePara(instance, 70);
				para8.setParameter("C_Currency_ID",this.openAmtFilters.cCurrencyID);
				para8.saveEx();
			}
//			ProcessCtl worker = new ProcessCtl(null, 0, pi, null);
//			worker.start();
			
			ReportStarter starter = new ReportStarter();
			starter.startProcess(getCtx(), pi, null);
		}
	}
}
