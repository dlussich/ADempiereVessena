/**
 * 
 */
package org.openup.process;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import oracle.net.aso.p;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.apps.ProcessCtl;
import org.compiere.apps.Waiting;
import org.compiere.model.MBPartner;
import org.compiere.model.MCurrency;
import org.compiere.model.MDocType;
import org.compiere.model.MInvoice;
import org.compiere.model.MOrg;
import org.compiere.model.MPInstance;
import org.compiere.model.MPInstancePara;
import org.compiere.model.MPayment;
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
import org.python.core.exceptions;
import org.zkoss.lang.Exceptions;

/**
 * @author sevans
 *
 */
public class PAccountStatus extends SvrProcess {

	private ReportAccountStatus acctStatusFilters = new ReportAccountStatus();
	private static final String TABLA_MOLDE = "uy_molde_accountstatus";
	private String idReporte = "";	
	private Waiting waiting = null;
	/**
	 * 
	 */
	public PAccountStatus() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		// Obtengo parametros y los recorro
		ProcessInfoParameter[] para = getParameter();

		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName().trim();
			if (name!= null){
				
				if (name.equalsIgnoreCase("StartDate")){
					this.acctStatusFilters.dateFrom = (Timestamp)para[i].getParameter();
					this.acctStatusFilters.dateTo = (Timestamp)para[i].getParameter_To();
				}

				if (name.equalsIgnoreCase("C_Currency_ID")) 
					this.acctStatusFilters.cCurrencyID = ((BigDecimal)para[i].getParameter()).intValueExact();
				
				if (name.equalsIgnoreCase("ReportCurrencyType")) 
					this.acctStatusFilters.currencyType = (String)para[i].getParameter();
				
				if (name.equalsIgnoreCase("ReportType")){
					if (para[i].getParameter() != null) this.acctStatusFilters.reportType = ((String)para[i].getParameter());
				}

				if (name.equalsIgnoreCase("C_BPartner_ID")){
					if (para[i].getParameter() != null) this.acctStatusFilters.cBPartnerID = ((BigDecimal)para[i].getParameter()).intValueExact();
				}	
				
				if (name.equalsIgnoreCase("AcctStatusType")){
					if (para[i].getParameter() != null) this.acctStatusFilters.acctStatusType = ((String)para[i].getParameter());
					this.acctStatusFilters.partnerType = this.acctStatusFilters.acctStatusType;
				}	
				
				if (name.equalsIgnoreCase("AD_User_ID")) 
					this.acctStatusFilters.adUserID = ((BigDecimal)para[i].getParameter()).intValueExact();
				if (name.equalsIgnoreCase("AD_Client_ID")) 
					this.acctStatusFilters.adClientID = ((BigDecimal)para[i].getParameter()).intValueExact();
				if (name.equalsIgnoreCase("AD_Org_ID")) 
					this.acctStatusFilters.adOrgID = ((BigDecimal)para[i].getParameter()).intValueExact();				
			}
		}
		
		this.acctStatusFilters.today = TimeUtil.trunc(new Timestamp (System.currentTimeMillis()), TimeUtil.TRUNC_DAY);
		
		if(this.acctStatusFilters.partnerType != null){
			if(this.acctStatusFilters.partnerType.equalsIgnoreCase(ReportAccountStatus.PARTNER_PROVEEDOR)){
				this.acctStatusFilters.customerID = 0;
				this.acctStatusFilters.vendorID = this.acctStatusFilters.cBPartnerID;
				this.acctStatusFilters.isSoTrx = "N";
			}else{
				this.acctStatusFilters.customerID = this.acctStatusFilters.cBPartnerID;
				this.acctStatusFilters.vendorID = 0;
				this.acctStatusFilters.isSoTrx = "Y";
			}
		}
	
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		
		this.setWaiting(this.getProcessInfo().getWaiting());
		
		this.deleteData();
		
		this.getData();
				
		this.updateData();
		
		this.showHelp("Iniciando Vista Previa...");
		
		this.showReport();
		
		return "OK";
	}

	
	/**
	 * Elimina corridas anteriores de este reporte para este usuario.
	 * @throws Exception 
	 */
	private void deleteData() throws Exception{
		
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
			
			//OpenUP SBT 09/03/2016 Issue 5069 Si se especifica que es para una sola moneda tengo que obtener doc solo del tipo de moneda especificado.
			if(this.acctStatusFilters.currencyType.equals(ReportAccountStatus.REPORTCURRTYPE_UNA)){
				whereFiltros += " AND st.c_currency_id = " + this.acctStatusFilters.cCurrencyID;
			}else{
				//Docuemntos de todas las monedas. 
			}
		
			insert = "INSERT INTO " + TABLA_MOLDE + " (idreporte, fecreporte, ad_user_id, ad_client_id, ad_org_id," +
					" isinvoice, c_bpartner_id, bpvalue, bpname, isreceipt, record_id, documentno, c_currency_id, currencyname, c_doctype_id," +					
					" docname, startdate, datetrx, duedate, amtdocument, serie,saldoinicial, debe, haber, saldoacumulado, "
					+ "poreference,amtsourcedr,amtsourcecr,description,currencyrate,totalquotes)";

			StringBuilder strb = new StringBuilder("");
			
			Timestamp dateFrom = TimeUtil.trunc(this.acctStatusFilters.dateFrom, TimeUtil.TRUNC_DAY);
			Timestamp dateTo = TimeUtil.trunc(this.acctStatusFilters.dateTo, TimeUtil.TRUNC_DAY);
			
			this.showHelp("Obteniendo datos...");
			String bpname = "";
			String whereOrg = "";
			if (this.acctStatusFilters.adOrgID > 0) 
				whereOrg = " AND st.ad_org_id =" + this.acctStatusFilters.adOrgID;
			if (this.acctStatusFilters.reportType.equalsIgnoreCase("RV"))
				bpname = "(bp.name) as bpname";
			else
				bpname = "(bp.name || ' - ' ||coalesce(bp.name2,'')) as bpname";
			// Facturas y Notas de Credito
			strb.append(" SELECT '" + this.idReporte + "',current_date," + this.acctStatusFilters.adUserID + "," +
						this.acctStatusFilters.adClientID + "," + this.acctStatusFilters.adOrgID + "," +
						" case when doc.allocationbehaviour ='INV' then 'Y' else 'N' end as isinvoice, st.c_bpartner_id," + 
						//--" bp.value as bpvalue, (bp.name || ' - ' ||coalesce(bp.name2,'')) as bpname, st.issotrx, " +
						" bp.value as bpvalue, "+bpname+", st.issotrx, " +
						" st.c_invoice_id, st.documentno, st.c_currency_id, cur.description as curname, " +
						" st.c_doctype_id, doc.printname, st.dateinvoiced as startdate, st.dateinvoiced, " +
						" coalesce(st.duedate, paymenttermduedate(st.c_paymentterm_id, st.dateinvoiced)) as duedate, st.grandtotal, st.serie,"
						+ "0,0,0,0, coalesce(st.POReference,'') as poreference,0,0,st.POReference,0,0 " +
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
					    " AND st.issotrx = '" + this.acctStatusFilters.isSoTrx  +"' "+//Se agrega y se comenta el siguiente SBT 10-03-2016 
					    " AND st.ispaid='N' " + whereFiltros ); //OpenUp. Nicolas Sarlabos. 16/07/2013. #1141. Se modifica WHERE para contemplar todos los tipos de documentos

			// Payments
			strb.append(" UNION SELECT '" + this.idReporte + "',current_date," + this.acctStatusFilters.adUserID + "," +
						this.acctStatusFilters.adClientID + "," + this.acctStatusFilters.adOrgID + "," +
						" 'N' as isinvoice,st.c_bpartner_id," + 
						//--" bp.value as bpvalue, (bp.name || ' - ' ||coalesce(bp.name2,'')) as bpname, st.isreceipt, " +
						" bp.value as bpvalue, "+bpname+", st.isreceipt, " +
						" st.c_payment_id, st.documentno, st.c_currency_id, cur.description as curname, " +
						" st.c_doctype_id, doc.printname, st.datetrx as startdate, st.datetrx, st.datetrx as duedate, st.payamt, st.serie,"
						+ " 0,0,0,0, '' as poreference,0,0,st.description,0,0 " +
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
					    " AND st.isreceipt =  '" + this.acctStatusFilters.isSoTrx  +"' "+//Se agrega y se comenta el siguiente SBT 10-03-2016 
					    " AND st.docstatus = 'CO' " + whereFiltros);
			
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
		String sql2 = "";
		ResultSet rs2 = null;
		PreparedStatement pstmt2 = null;
		
		try{
			
			this.showHelp("Calculo de saldos...");
			
			//OpenUp SBT 29/03/2016 Issue #5683
			String orderBy = "";
			if(this.acctStatusFilters.currencyType.equals(ReportAccountStatus.REPORTCURRTYPE_TODASTRX)){
				orderBy = " ORDER BY c_currency_id,bpname, datetrx, c_doctype_id, record_id ASC";
			}else{
				orderBy = " ORDER BY bpname, datetrx, c_doctype_id, record_id ASC";
			}			
			sql = " SELECT * " +
				  " FROM " + TABLA_MOLDE +
				  " WHERE idreporte=?" +  orderBy;
			
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
			BigDecimal totalDocMonedaDoc = Env.ZERO;
			int cBPartnerID = 0, cBPartnerIDAux = 0, cDocTypeID = 0, recordID = 0;
			boolean isDebe = false;
			boolean multiCurr = (this.acctStatusFilters.currencyType.equalsIgnoreCase(ReportAccountStatus.REPORTCURRTYPE_TODAS));
			int curFromIDAux = 0, curFromID = 0; MCurrency currencyFrom=null;
			
			// Corte de control por socio de negocio
			while (rs.next()){
				
				this.showHelp("Procesando linea " + rowCount++ + " de " + totalRowCount);
				
				cDocTypeID = rs.getInt("c_doctype_id");
				recordID = rs.getInt("record_id");
				cBPartnerIDAux = rs.getInt("c_bpartner_id");
				curFromIDAux = rs.getInt("c_currency_id");
				
				//OpenUp SBT 29/03/2016 Issue #5683 
				//Si es moneda trx tengo que hacer corte de control por moneda y por socio de negocio
				if(this.acctStatusFilters.currencyType.equals(ReportAccountStatus.REPORTCURRTYPE_TODASTRX)){
					if (curFromIDAux!=curFromID  || cBPartnerIDAux != cBPartnerID){
						cBPartnerID = cBPartnerIDAux;
						curFromID = curFromIDAux;
						saldoInicial = getSaldoInicial(cBPartnerID,curFromIDAux);
						saldoAcumulado = saldoInicial;
					}
				}else{// Si hay cambio de socio de negocio
					if (cBPartnerIDAux != cBPartnerID){
						cBPartnerID = cBPartnerIDAux;
						saldoInicial = getSaldoInicial(cBPartnerID,curFromIDAux);
						saldoAcumulado = saldoInicial;
					}
				}				
				currencyFrom =  new MCurrency(Env.getCtx(), curFromID,null);
				totalDocumento = rs.getBigDecimal("amtdocument");
				totalDocMonedaDoc = totalDocumento;//.setScale(2, RoundingMode.HALF_UP);
				// Si fecha menor a rango considero monto cero
				if (rs.getTimestamp("datetrx").compareTo(this.acctStatusFilters.dateFrom) < 0){
					totalDocumento = Env.ZERO;
				}
					
				//INI SBT ----------------------------------------------
				//OpenUp SBT 10/03/2016 Isuue #5569 Se deben actualizar datos nuevos Tasa de cambio, TotalDocumento en la mondeda del doc,
				//Calcular la tasa de cambio si corresponde y si es dif moneda caluclar el monto y actualizarlo.
	
				//Se obtiene la fecha de vencimiento y la cantidad total de cuotas
				
				Timestamp fechaVto = null;  //--> Fecha vencimiento 
				int totalQuotes = 1; // --> Cantidad Cuotas
				BigDecimal currencyRate = Env.ONE; // --> Tipo de cambio
				//-------------------------SBTMDocType doc = new MDocType(getCtx(), rs.getInt("c_doctype_id"), null);
				String docBaseType = "";
				ResultSet rs3 = null;
				PreparedStatement pstmt3 = null;
				try{
					String sql3 = "SELECT DocBaseType FROM C_DocType "
							 + " WHERE C_DocType_ID = ?"
							 + " ORDER BY quoteno DESC ";
					pstmt3 = DB.prepareStatement(sql3, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, null);
					pstmt3.setInt(1, rs.getInt("c_doctype_id"));
					rs3 = pstmt3.executeQuery();
					if(rs3.first()){
						docBaseType = rs3.getString("DocBaseType");
					}
				}catch(Exception e){
					e.getMessage();
				}finally{
					DB.close(rs3, pstmt3);
					rs3 = null; pstmt3 = null;
				}
				
//	--SBT			if(doc.getDocBaseType().equalsIgnoreCase("ARC") || doc.getDocBaseType().equalsIgnoreCase("APC") ||
//	--SBT					doc.getDocBaseType().equalsIgnoreCase("API") || doc.getDocBaseType().equalsIgnoreCase("ARI")){
				if(docBaseType.equalsIgnoreCase("ARC") || docBaseType.equalsIgnoreCase("APC") ||
						docBaseType.equalsIgnoreCase("API") || docBaseType.equalsIgnoreCase("ARI")){
					// select quoteno,literalquote,duedate from C_InvoicePaySchedule where C_Invoice_ID=1009952 order by 
					// literalquote desc limit 1
					 sql2 = "SELECT quoteno,DueDate,c_invoice_id FROM C_InvoicePaySchedule "
							 + " WHERE C_Invoice_ID = ?" 
							 + " ORDER BY quoteno DESC ";
					pstmt2 = DB.prepareStatement(sql2, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, null);
					pstmt2.setInt(1,recordID);
					try{
						rs2 = pstmt2.executeQuery();
						if(rs2.first()){
							totalQuotes = rs2.getInt("quoteno");
							fechaVto = rs2.getTimestamp("DueDate");
						}
					}catch(Exception e){
						e.getMessage();
					}finally{
						DB.close(rs2, pstmt2);
						rs2 = null; pstmt2 = null;
					}
		
					if(multiCurr){
						//Si la moneda del reporte no es igual a la moneda del documento, 
						//calculo TC con la fecha de la corrida del reporte y calculo el monto con dicha TC y actualizo el mismo
//	---SBT					MInvoice inv = new MInvoice(getCtx(),recordID,null);
//	---SBT					if(inv == null || inv.get_ID()==0) throw new AdempiereException("No se encontro docuemnto-");
						 
//	---SBT					if(this.acctStatusFilters.cCurrencyID!=inv.getC_Currency_ID()){
//	---SBT						currencyRate = OpenUpUtils.getCurrencyRateForCur1Cur2(this.acctStatusFilters.today,
//	---SBT								this.acctStatusFilters.cCurrencyID,inv.getC_Currency_ID(), 
//	---SBT								this.acctStatusFilters.adClientID, this.acctStatusFilters.adOrgID);
							
							if(this.acctStatusFilters.cCurrencyID!=rs.getInt("C_Currency_ID")){
								currencyRate = OpenUpUtils.getCurrencyRateForCur1Cur2(this.acctStatusFilters.today,
								this.acctStatusFilters.cCurrencyID,rs.getInt("C_Currency_ID"),
								this.acctStatusFilters.adClientID, this.acctStatusFilters.adOrgID);		
							if(currencyRate.equals(Env.ZERO)) throw new AdempiereException("No hay tasa de cambio para el docuemnto");
							currencyRate = currencyRate.setScale(2, RoundingMode.HALF_UP);
							totalDocumento = totalDocumento.multiply(currencyRate);//.setScale(2, RoundingMode.HALF_UP);
						}	
					}
					
				}else{
					fechaVto = rs.getTimestamp("DueDate");
					totalQuotes = 1;
					
					if(multiCurr){
						//Si la moneda del reporte no es igual a la moneda del documento, 
						//calculo TC con la fecha de la corrida del reporte y calculo el monto con dicha TC y actualizo el mismo
						
//	---SBT					MPayment pay = new MPayment(getCtx(), recordID, null);
//	---SBT					if(pay == null || pay.get_ID()==0) 
//	---SBT						throw new AdempiereException("No se encontro docuemnto-");
						
//	---SBT					if(this.acctStatusFilters.cCurrencyID!=pay.getC_Currency_ID()){
//	---SBT						currencyRate = OpenUpUtils.getCurrencyRateForCur1Cur2(this.acctStatusFilters.today,
//	---SBT								 this.acctStatusFilters.cCurrencyID,pay.getC_Currency_ID(),
//	---SBT								this.acctStatusFilters.adClientID, this.acctStatusFilters.adOrgID);
						
						if(this.acctStatusFilters.cCurrencyID!=rs.getInt("C_Currency_ID")){
							currencyRate = OpenUpUtils.getCurrencyRateForCur1Cur2(this.acctStatusFilters.today,
							this.acctStatusFilters.cCurrencyID,rs.getInt("C_Currency_ID"),
							this.acctStatusFilters.adClientID, this.acctStatusFilters.adOrgID);						
							if(currencyRate == null || currencyRate.equals(Env.ZERO)) throw new AdempiereException("No hay tasa de cambio para el docuemnto");
							currencyRate = currencyRate.setScale(2, RoundingMode.HALF_UP);
							totalDocumento = totalDocumento.multiply(currencyRate);//.setScale(2, RoundingMode.HALF_UP);
						}
					}
					

				}
				//FIN SBT -----------------------------------------------
		
				if (this.acctStatusFilters.partnerType != null){

					if (this.acctStatusFilters.partnerType.equalsIgnoreCase(ReportAccountStatus.PARTNER_CLIENTE)){
						if (rs.getString("isinvoice").equalsIgnoreCase("Y")){
							saldoAcumulado = saldoAcumulado.add(totalDocumento);//.setScale(2, RoundingMode.HALF_UP);
							isDebe = true;
						}
						else{
							saldoAcumulado = saldoAcumulado.subtract(totalDocumento);//.setScale(2, RoundingMode.HALF_UP);						
							isDebe = false;
						}
							
					}
					else{
						if (rs.getString("isinvoice").equalsIgnoreCase("Y")){
							saldoAcumulado = saldoAcumulado.subtract(totalDocumento);//.setScale(2, RoundingMode.HALF_UP);
							isDebe = false;
						}	
						else{
							saldoAcumulado = saldoAcumulado.add(totalDocumento);//.setScale(2, RoundingMode.HALF_UP);
							isDebe = true;
						}
					}
					
				}
				else{
					if (this.acctStatusFilters.cBPGroupID == ReportOpenAmt.GRUPO_CLIENTE){
						if (rs.getString("isinvoice").equalsIgnoreCase("Y")){
							saldoAcumulado = saldoAcumulado.add(totalDocumento);//.setScale(2, RoundingMode.HALF_UP);
							isDebe = true;
						}
						else{
							saldoAcumulado = saldoAcumulado.subtract(totalDocumento);//.setScale(2, RoundingMode.HALF_UP);						
							isDebe = false;
						}
							
					}
					else{
						if (rs.getString("isinvoice").equalsIgnoreCase("Y")){
							saldoAcumulado = saldoAcumulado.subtract(totalDocumento);//.setScale(2, RoundingMode.HALF_UP);
							isDebe = false;
						}	
						else{
							saldoAcumulado = saldoAcumulado.add(totalDocumento);//.setScale(2, RoundingMode.HALF_UP);
							isDebe = true;
						}
					}
					
				}
				
				action = " UPDATE " + TABLA_MOLDE + 
		 		 		 " SET saldoacumulado =" + saldoAcumulado + "," +
  	 		 		     " saldoinicial = " + saldoInicial + "," +
		 		 		 ((isDebe) ? " debe = " : " haber = ") + totalDocumento + "," +
		 		 		 //SBT se agrega campo 09/03/2016
		 		 		 ((isDebe) ? " amtsourcedr = " : " amtsourcecr = ") + totalDocMonedaDoc + "," + //cr = haber -->>Seteo el monto del documento
		 		 		 " DueDate = '"+fechaVto + "'," +
		 		 		 " TotalQuotes = "+ totalQuotes + "," +
				 		 " currencyname = '" + currencyFrom.getDescription() + "'," +
		 		 		 " CurrencyRate = "+ currencyRate+
		 		 		 //FIN SBT
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
	 * OpenUp. Santiago Evans. 08/03/2016. Issue #5069
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
		
		
		sql = "select c_bpartner_id,value,name from c_bpartner bp where bp.isactive='Y' " + where + " order by bp.value";
		
		pstmt = DB.prepareStatement(sql, null);
		rs = pstmt.executeQuery ();
		
		while (rs.next()){
			partnerID = rs.getInt("c_bpartner_id");
			name = rs.getString("name").replace("'", "").trim();
			//OpenUp SBT 30/03/2016 Issue #5683 
			int[] lstCurr = null;
			if(this.acctStatusFilters.currencyType.equalsIgnoreCase(ReportOpenAmt.REPORTCURRTYPE_TODASTRX)){
				//Tengo que realizar la misma consulta para los diferentes tipos de moneda
				lstCurr = MCurrency.getAllIDs("C_Currency", "IsActive = 'Y'", null);
			}else{
				lstCurr = new int[1];
				lstCurr[0]=this.acctStatusFilters.cCurrencyID;
			}
			for(int i = 0 ;i<lstCurr.length;i++){
				
				saldoInicial = getSaldoInicial(partnerID,lstCurr[i]);
				
				MCurrency curr = new MCurrency(getCtx(), lstCurr[i], get_TrxName());
				
				if(saldoInicial.compareTo(Env.ZERO) != 0) {
					
					insert = "INSERT INTO " + TABLA_MOLDE + " (idreporte, fecreporte, ad_user_id, ad_client_id, ad_org_id," +
							" isinvoice, c_bpartner_id, bpvalue, bpname, isreceipt, record_id, documentno, c_currency_id, currencyname, c_doctype_id," +					
							" docname, startdate, datetrx, duedate, amtdocument, serie,saldoinicial, debe, haber, saldoacumulado)";

					select = " SELECT '" + this.idReporte + "',current_date," + this.acctStatusFilters.adUserID + "," +
							this.acctStatusFilters.adClientID + "," + this.acctStatusFilters.adOrgID + ",'N'," + partnerID + ",'" + rs.getString("value") + "','" +
							name + "','N',0,0," + lstCurr[i] + ",'"+curr.getDescription()+"',0,'-','" 
							+ this.acctStatusFilters.dateFrom + "','" + this.acctStatusFilters.dateFrom
							+ "',null,null,null," + saldoInicial + ",null,null," + saldoInicial;

					DB.executeUpdateEx(insert + select, null);	

				}
			}
			

		}		

	}

	/**
	 * OpenUp. Santiago Evans. 08/03/2016. Issue #5069
	 * Obtiene y retorna saldo inicial de un cliente.
	 * @param cBPartnerID
	 * @return
	 * @throws Exception 
	 */
	private BigDecimal getSaldoInicial(int cBPartnerID,int currID) throws Exception {
		return this.getSaldoInicialDebe(cBPartnerID, currID).subtract(this.getSaldoInicialHaber(cBPartnerID, currID));
	}

	/**
	 * OpenUp. Santiago Evans. 08/03/2016. Issue #5069
	 * Descripcion : Obtiene saldo inicial al DEBE de un determinado socio de negocio (deudor o proveedor).
	 */
	private BigDecimal getSaldoInicialDebe(int cBPartnerID,int currID) throws Exception {
		
		BigDecimal value = new BigDecimal(0);
		
		if (this.acctStatusFilters.partnerType != null){

			if (this.acctStatusFilters.partnerType.equalsIgnoreCase(ReportAccountStatus.PARTNER_CLIENTE)){
				value = this.getSaldoInicialDebeDeudInvoice(cBPartnerID,currID);
			}
			else{
				value = this.getSaldoInicialDebeProvInvoice(cBPartnerID,currID).add(this.getSaldoInicialDebeProvPayment(cBPartnerID,currID));
			}

		}
		else{

			if (this.acctStatusFilters.cBPGroupID == ReportOpenAmt.GRUPO_CLIENTE){
				value = this.getSaldoInicialDebeDeudInvoice(cBPartnerID,currID);
			}
			else{
				value = this.getSaldoInicialDebeProvInvoice(cBPartnerID,currID).add(this.getSaldoInicialDebeProvPayment(cBPartnerID,currID));
			}
			
		}
		
		return value;
	}
	
	/**
	 * OpenUp. Santiago Evans. 08/03/2016. Issue #5069
	 * Descripcion : Obtiene saldo inicial al HABER de un determinado socio de negocio (deudor o proveedor).
	 */
	private BigDecimal getSaldoInicialHaber(int cBPartnerID,int currID) throws Exception {

		BigDecimal value = new BigDecimal(0);
		
		if (this.acctStatusFilters.partnerType != null){
			if (this.acctStatusFilters.partnerType.equalsIgnoreCase(ReportAccountStatus.PARTNER_CLIENTE)){
				value = this.getSaldoInicialHaberDeudInvoice(cBPartnerID,currID).add(this.getSaldoInicialHaberDeudPayment(cBPartnerID,currID));
			}
			else{
				value = this.getSaldoInicialHaberProvInvoice(cBPartnerID,currID);
			}
		}
		else{
			if (this.acctStatusFilters.cBPGroupID == ReportOpenAmt.GRUPO_CLIENTE){
				value = this.getSaldoInicialHaberDeudInvoice(cBPartnerID,currID).add(this.getSaldoInicialHaberDeudPayment(cBPartnerID,currID));
			}
			else{
				value = this.getSaldoInicialHaberProvInvoice(cBPartnerID,currID);
			}
		}

		
		
		return value;
	}

	/**
	 * OpenUp. Santiago Evans. 08/03/2016. Issue #5069	
	 * Descripcion : Obtiene saldo inicial de facturas de venta para un determinado cliente.
	 */
	private BigDecimal getSaldoInicialDebeDeudInvoice(int cBPartnerID,int currID) throws Exception{
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		BigDecimal value = new BigDecimal(0);
		
		try{
		
			String whereMoneda = "";
			
			// Condicion de moneda
			if (this.acctStatusFilters.cCurrencyID > 0) whereMoneda = " AND a.C_Currency_ID = " +currID; //---SBT+ this.acctStatusFilters.cCurrencyID;

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
	 * OpenUp. Santiago Evans. 08/03/2016. Issue #5069
	 * Descripcion : Obtiene saldo inicial de notas de credito de venta para un determinado cliente.
	 */
	private BigDecimal getSaldoInicialHaberDeudInvoice(int cBPartnerID,int currID) throws Exception{
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		BigDecimal value = new BigDecimal(0);
		
		try{
		
			String whereMoneda = "";
			// Condicion de moneda
			if (this.acctStatusFilters.cCurrencyID > 0) whereMoneda = " AND a.C_Currency_ID = " + currID;// this.acctStatusFilters.cCurrencyID;

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
	 * OpenUp. Santiago Evans. 08/03/2016. Issue #5069
	 * Descripcion : Obtiene saldo inicial de cobranzas de ventas para un determinado cliente.
	 */
	private BigDecimal getSaldoInicialHaberDeudPayment(int cBPartnerID,int currID) throws Exception{
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		BigDecimal value = new BigDecimal(0);
		
		try{
		
			String whereMoneda = "";
			// Condicion de moneda
			if (this.acctStatusFilters.cCurrencyID > 0) whereMoneda = " AND a.C_Currency_ID = " + currID;//this.acctStatusFilters.cCurrencyID;

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
	 * OpenUp. Santiago Evans. 08/03/2016. Issue #5069
	 * Descripcion : Obtiene saldo inicial de notas de credito de compra para un determinado proveedor.
	 */
	private BigDecimal getSaldoInicialDebeProvInvoice(int cBPartnerID,int currID) throws Exception{
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		BigDecimal value = new BigDecimal(0);
		
		try{
		
			String whereMoneda = "";
			// Condicion de moneda
			if (this.acctStatusFilters.cCurrencyID > 0) whereMoneda = " AND a.C_Currency_ID = " + currID;//this.acctStatusFilters.cCurrencyID;

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
	 * OpenUp. Santiago Evans. 08/03/2016. Issue #5069
	 * Descripcion : Obtiene saldo inicial de pagos de compras para un determinado proveedor.
	 */
	private BigDecimal getSaldoInicialDebeProvPayment(int cBPartnerID,int currID) throws Exception{
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		BigDecimal value = new BigDecimal(0);
		
		try{
		
			String whereMoneda = "";
			// Condicion de moneda
			if (this.acctStatusFilters.cCurrencyID > 0) whereMoneda = " AND a.C_Currency_ID = " + currID;//this.acctStatusFilters.cCurrencyID;

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
	 * OpenUp. Santiago Evans. 08/03/2016. Issue #5069
	 * Descripcion : Obtiene saldo inicial de facturas de compra para un determinado proveedor.
	 */
	private BigDecimal getSaldoInicialHaberProvInvoice(int cBPartnerID,int currID) throws Exception{
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		BigDecimal value = new BigDecimal(0);
		
		try{
		
			String whereMoneda = "";
			// Condicion de moneda
			if (this.acctStatusFilters.cCurrencyID > 0) whereMoneda = " AND a.C_Currency_ID = " +  currID ;//this.acctStatusFilters.cCurrencyID;

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
	
	/**
	 * Se obtienen los datos a ser despelado en los reportes y dependiendo de la opción
	 * seleccionada se muestra un tipo de reporte u otro.
	 * @author OpenUp SBT Issue#  10/3/2016 16:28:28
	 */
	private void showReport(){
		String valueRep = ""; String currName = ""; String userName = ""; String orgName="";
		String titulo = "Estado de Cuenta Corriente";
		boolean jasper = false;
		if(this.acctStatusFilters.reportType.equalsIgnoreCase("JASPER")){
			jasper = true;
		}

		if(this.acctStatusFilters.currencyType.equalsIgnoreCase(ReportAccountStatus.REPORTCURRTYPE_TODAS)){
			titulo += " Multimoneda - ";
			if(jasper){
				valueRep = "jpracctstatusmulticur";//jasper multimoneda	
			}else{
				valueRep = "rvpacctstatusmulticur";//rv multimoneda rvpacctstatusmulticur
			}
		}else if(this.acctStatusFilters.currencyType.equalsIgnoreCase(ReportAccountStatus.REPORTCURRTYPE_UNA)){
			titulo += " - ";
			if(jasper){
				valueRep = "jpracctstatussm";//jasper una moneda

			}else{
				valueRep = "rvpacctstatussm";//rv una moneda
			}
		}else{
			titulo += " MT - ";
			if(jasper){
				valueRep = "jpracctstatusmtrx";//jasper una moneda

			}else{
				valueRep = "rvpacctstatusmtrx";//rv una moneda
			}
		}
		
		if(this.acctStatusFilters.acctStatusType.equalsIgnoreCase(ReportAccountStatus.PARTNER_CLIENTE)){
			titulo = titulo + "Cliente";
		}else{
			titulo = titulo + "Proveedor";
		}
		
		MUser user = new MUser(getCtx(),this.acctStatusFilters.adUserID,null);
		userName = user.getDescription();
		
		if(this.acctStatusFilters.cCurrencyID>0){
			MCurrency curr = new MCurrency(getCtx(), this.acctStatusFilters.cCurrencyID, null);
			currName = curr.getDescription();
		}
		
		MOrg org = new MOrg(getCtx(),this.acctStatusFilters.adOrgID,null);
		orgName = org.getName();
		
		if(jasper){
			showJasper(valueRep,titulo,currName,orgName,userName);
		}else{
			showRV(valueRep,titulo,currName,orgName,userName);
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
			MPInstance instance = new MPInstance(Env.getCtx(), processID, 0);
			instance.saveEx();

			ProcessInfo pi = new ProcessInfo (titulo, processID);
			pi.setAD_PInstance_ID (instance.getAD_PInstance_ID());
			
			MPInstancePara para = new MPInstancePara(instance, 10);
			para.setParameter("currencyname", currName);
			para.saveEx();
			MPInstancePara para2 = new MPInstancePara(instance, 20);
			para2.setParameter("FechaInicio", this.acctStatusFilters.dateFrom);
			para2.saveEx();
			MPInstancePara para3 = new MPInstancePara(instance, 30);
			para3.setParameter("FechaFin", this.acctStatusFilters.dateTo);
			para3.saveEx();
			MPInstancePara para4 = new MPInstancePara(instance, 40);
			para4.setParameter("OrgName", orgName);
			para4.saveEx();
			MPInstancePara para5 = new MPInstancePara(instance, 50);
			para5.setParameter("AD_User_ID", new BigDecimal(this.acctStatusFilters.adUserID));
			para5.saveEx();
			MPInstancePara para6 = new MPInstancePara(instance, 60);
			para6.setParameter("Title", titulo);
			para6.saveEx();
			MPInstancePara para7 = new MPInstancePara(instance, 70);
			para7.setParameter("UserName", userName);
			para7.saveEx();
			if(valueRep.equalsIgnoreCase("jpracctstatussm")){
				MPInstancePara para8 = new MPInstancePara(instance, 80);
				para8.setParameter("C_Currency_ID",this.acctStatusFilters.cCurrencyID);
				para8.saveEx();
			}
			ReportStarter starter = new ReportStarter();
			starter.startProcess(getCtx(), pi, null);
			
//			ProcessCtl worker = new ProcessCtl(null, 0, pi, null);
//			worker.start(); 
		}
	}
	
	/**
	 * Metodo para iniciar Report View correspondiente
	 * @author OpenUp SBT Issue#  11/3/2016 9:48:09
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
			
			pi.setAD_Client_ID(this.acctStatusFilters.adClientID);
			pi.setAD_User_ID(this.acctStatusFilters.adUserID);
			pi.setAD_PInstance_ID (instance.getAD_PInstance_ID());
			
			//NUEVO
			MPInstancePara para = new MPInstancePara(instance, 10);
			para.setParameter("AD_Client_ID", this.acctStatusFilters.adClientID);
			para.saveEx();
			MPInstancePara para5 = new MPInstancePara(instance, 20);
			para5.setParameter("AD_User_ID", this.acctStatusFilters.adUserID);
			para5.saveEx();
			MPInstancePara para8 = new MPInstancePara(instance, 30);
			para8.setParameter("AD_Org_ID",this.acctStatusFilters.adOrgID);
			para8.saveEx();
			
//			ReportStarter starter = new ReportStarter();
//			starter.startProcess(getCtx(), pi, null);

			ProcessCtl worker = new ProcessCtl(null, 0, pi, null);
			worker.start(); 
		}
	}


	
}
