/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 11/02/2013
 */
package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBankAccount;
import org.compiere.model.MConversionRate;
import org.compiere.model.MDocType;
import org.compiere.model.MInvoice;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

/**
 * org.openup.model - MPayOrderGen
 * OpenUp Ltda. Issue #348 
 * Description: Generacion de Ordenes de Pago
 * @author Gabriel Vila - 11/02/2013
 * @see
 */
public class MPayOrderGen extends X_UY_PayOrderGen implements DocAction {

	private static final long serialVersionUID = 422449759189212341L;
	private String processMsg = null;
	private boolean justPrepared = false;
	
	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_PayOrderGen_ID
	 * @param trxName
	 */
	public MPayOrderGen(Properties ctx, int UY_PayOrderGen_ID, String trxName) {
		super(ctx, UY_PayOrderGen_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MPayOrderGen(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#processIt(java.lang.String)
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 11/02/2013
	 * @see
	 * @param action
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean processIt(String action) throws Exception {
		this.processMsg = null;
		DocumentEngine engine = new DocumentEngine (this, getDocStatus());
		return engine.processIt (action, getDocAction());
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#unlockIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 11/02/2013
	 * @see
	 * @return
	 */
	@Override
	public boolean unlockIt() {
		log.info("unlockIt - " + toString());
		setProcessing(false);
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#invalidateIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 11/02/2013
	 * @see
	 * @return
	 */
	@Override
	public boolean invalidateIt() {
		log.info("invalidateIt - " + toString());
		setDocAction(DocAction.ACTION_Prepare);
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#prepareIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 11/02/2013
	 * @see
	 * @return
	 */
	@Override
	public String prepareIt() {
		// Todo bien
		this.justPrepared = true;
		if (!DocAction.ACTION_Complete.equals(getDocAction()))
			setDocAction(DocAction.ACTION_Complete);
		return DocAction.STATUS_InProgress;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#approveIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 11/02/2013
	 * @see
	 * @return
	 */
	@Override
	public boolean approveIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#applyIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 11/02/2013
	 * @see
	 * @return
	 */
	@Override
	public boolean applyIt() {

		boolean result = false;
		
		try {

			this.loadData();
			
			// Genero ordenes de pago en borrador para modificar atributos especificos de las mismas durante
			// este proceso. No se permite modificarles lineas, etc. que involucren cambios en las lineas de generacion.
			// Si se permite que al modificar lineas en la generacion, se modifique automaticamente la orden de pago en borrador.
			this.generatePayOrders();
			
			this.setDocAction(DocAction.ACTION_Complete);
			this.setDocStatus(DocumentEngine.STATUS_Applied);

			result = true;
			
		} 
		catch (Exception e) {
			throw new AdempiereException();
		}
		
		return result;
	}

	/***
	 * Carga factura proveedor pendientes de pago.
	 * OpenUp Ltda. Issue #348 
	 * @author Gabriel Vila - 11/02/2013
	 * @see
	 */
	private void loadData() {
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try{
			
			Timestamp today = TimeUtil.trunc(new Timestamp (System.currentTimeMillis()), TimeUtil.TRUNC_DAY);
			
			String whereFiltros = "";
			//SBT Issue 17-02-2015 #5443 Se agrega condición para no tomar facturas que ya se encuentren en órdenes de pago completas
			String invoiceNotIn =   " AND al.C_Invoice_ID NOT IN (SELECT pol.C_Invoice_ID FROM UY_PayOrderLine pol INNER JOIN UY_PayOrder "
					  + "po on (po.UY_PayOrder_ID = pol.UY_PayOrder_ID) "
					  + " WHERE po.DocStatus = 'CO' AND po.IsActive = 'Y' AND pol.IsActive = 'Y' "
					  + " AND po.AD_Client_ID = "+this.getAD_Client_ID()+") ";
			
			if (this.getStartDate() != null) whereFiltros += " AND al.dateinvoiced >='" + this.getStartDate() + "'";
			if (this.getEndDate() != null) whereFiltros += " AND al.dateinvoiced <='" + this.getEndDate() + "'";
			if (this.getDueDate2() != null) whereFiltros += " AND al.duedate <='" + this.getDueDate2() + "'";
			
			// Filtro de facturas
			String whereInvoices = "";
			List<MPayOrderGenInv> invfilters = this.getInvoiceFilters();
			if (invfilters != null){
				if (invfilters.size() > 0){
					whereInvoices = " AND al.c_invoice_id in " +
							"(select c_invoice_id from uy_payordergen_inv " +
							" where uy_payordergen_id =" + this.get_ID() + ")";
				}
			}
			
			// Filtro de proveedores
			String whereVendors = "";
			List<MPayOrderGenBP> bpfilters = this.getVendorFilters();
			if (bpfilters != null){
				if (bpfilters.size() > 0){
					whereInvoices = " AND al.c_bpartner_id in " +
							"(select c_bpartner_id from uy_payordergen_bp " +
							" where uy_payordergen_id =" + this.get_ID() + ")";
				}
			}
			
			sql = " SELECT al.c_bpartner_id, al.c_invoice_id, al.c_doctype_id, al.dateinvoiced, al.c_currency_id, " +
				  " al.amtinvoiced, al.amtallocated, al.amtopen, "
				  + " al.c_invoicepayschedule_id, coalesce(invsch.literalquote,'1/1') as literalquote, al.duedate " +
				  " FROM alloc_invoiceamtopen_sch al "
				  + " left outer join c_invoicepayschedule invsch on al.c_invoicepayschedule_id = invsch.c_invoicepayschedule_id " +
				  " WHERE al.ad_client_id =? " +
				  " AND al.issotrx='N' " +
				  whereFiltros + whereInvoices + whereVendors +
				  " AND al.amtopen > 0 " + 
				  invoiceNotIn + //SBT Issue 17-02-2015 #5443
				  " order by al.c_bpartner_id, al.duedate, al.dateinvoiced  ";
			
			pstmt = DB.prepareStatement(sql.toString(), null);
			pstmt.setInt(1, this.getAD_Client_ID());

			rs = pstmt.executeQuery();

			// Cargo linea con documentos a considerar en la generación de ordenes de pago
			while (rs.next()) {
				
				MPayOrderGenLine genline = new MPayOrderGenLine(getCtx(), 0, get_TrxName());
				genline.setUY_PayOrderGen_ID(this.get_ID());
				genline.setC_BPartner_ID(rs.getInt("c_bpartner_id"));
				genline.setC_Currency_ID(rs.getInt("c_currency_id"));
				genline.setC_DocType_ID(rs.getInt("c_doctype_id"));
				genline.setC_Invoice_ID(rs.getInt("c_invoice_id"));
				
				MInvoice invoice = (MInvoice)genline.getC_Invoice();
				if (invoice != null){
					if (invoice.getC_Order_ID() > 0){
						genline.setC_Order_ID(invoice.getC_Order_ID());	
					}
					if (rs.getInt("C_InvoicePaySchedule_ID") > 0){
						genline.setC_InvoicePaySchedule_ID(rs.getInt("C_InvoicePaySchedule_ID"));
						genline.setDueDate2(rs.getTimestamp("DueDate"));
					}
					else{
						genline.setDueDate2(invoice.getDueDate());	
					}
				}
				genline.setLiteralQuote(rs.getString("LiteralQuote"));

				genline.setGrandTotal(rs.getBigDecimal("amtinvoiced"));
				genline.setamtallocated(rs.getBigDecimal("amtopen"));
				genline.setamtopen(rs.getBigDecimal("amtopen"));
				genline.setDateInvoiced(rs.getTimestamp("dateinvoiced"));
				genline.setIsManual(false);
				
				if (this.getC_BankAccount_ID() > 0){
					MBankAccount ba = (MBankAccount)this.getC_BankAccount();
					genline.setC_BankAccount_ID(this.getC_BankAccount_ID());
					genline.setC_Currency_Bank_ID(ba.getC_Currency_ID());
					
					// Tasa de cambio entre moneda comprobante y moneda cuenta bancaria en caso de ser distintas
					BigDecimal currencyRate = Env.ONE;
					if (rs.getInt("c_currency_id") != ba.getC_Currency_ID()){
						currencyRate = MConversionRate.getCurrencyRate(getCtx(), this.getAD_Client_ID(), rs.getInt("c_currency_id"), ba.getC_Currency_ID(), today);
					}
					genline.setCurrencyRate(currencyRate);
					genline.setAmount(genline.getamtallocated().divide(currencyRate, 2, RoundingMode.HALF_UP));
				}

				if (this.getUY_PaymentRule_ID() > 0) genline.setUY_PaymentRule_ID(this.getUY_PaymentRule_ID());
				
				if (genline.getDueDate2() != null){
					if (genline.getDueDate2().before(today)){
						genline.setDueDate(today);
					}
					else{
						genline.setDueDate(genline.getDueDate2());	
					}
				}
				else{
					if (this.getDueDate() != null){
						genline.setDueDate(this.getDueDate());	
					}
				}
				
				genline.saveEx();
			}
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		} 
		finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

	}

	

	
	/***
	 * Obtiene y retorna lista de filtros de facturas.
	 * OpenUp Ltda. Issue #348 
	 * @author Gabriel Vila - 12/02/2013
	 * @see
	 * @return
	 */
	private List<MPayOrderGenInv> getInvoiceFilters() {

		String whereClause = X_UY_PayOrderGen_Inv.COLUMNNAME_UY_PayOrderGen_ID + "=" + this.get_ID();
		
		List<MPayOrderGenInv> values = new Query(getCtx(), I_UY_PayOrderGen_Inv.Table_Name, whereClause, get_TrxName())
		.list();
		
		return values;
	}
	
	
	/***
	 * Obtiene y retorna lista de filtros de proveedores.
	 * OpenUp Ltda. Issue #348 
	 * @author Gabriel Vila - 12/02/2013
	 * @see
	 * @return
	 */
	private List<MPayOrderGenBP> getVendorFilters() {

		String whereClause = X_UY_PayOrderGen_BP.COLUMNNAME_UY_PayOrderGen_ID + "=" + this.get_ID();
		
		List<MPayOrderGenBP> values = new Query(getCtx(), I_UY_PayOrderGen_BP.Table_Name, whereClause, get_TrxName())
		.list();
		
		return values;
		
	}
	
	/***
	 * Obtiene y retorna lista de ordenes de pago generadas.
	 * OpenUp Ltda. Issue #348 
	 * @author Gabriel Vila - 12/02/2013
	 * @see
	 * @return
	 */
	private List<MPayOrder> getPayOrders(){
		
		String whereClause = X_UY_PayOrder.COLUMNNAME_UY_PayOrderGen_ID + "=" + this.get_ID();
		
		List<MPayOrder> values = new Query(getCtx(), I_UY_PayOrder.Table_Name, whereClause, get_TrxName())
		.list();
		
		return values;
		
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#rejectIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 11/02/2013
	 * @see
	 * @return
	 */
	@Override
	public boolean rejectIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#completeIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 11/02/2013
	 * @see
	 * @return
	 */
	@Override
	public String completeIt() {

		//	Re-Check
		if (!this.justPrepared)
		{
			String status = prepareIt();
			if (!DocAction.STATUS_InProgress.equals(status))
				return status;
		}
		
		// Timing Before Complete
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
		if (this.processMsg != null)
			return DocAction.STATUS_Invalid;

		// Si el general tiene cambios sin reflejar en las ordenes, no dejo completar hasta que se refresquen.
		if (this.isChanged()){
			this.processMsg = "Debe refrescar los cambios en las ordenes de compra antes de completar";
			return DocAction.STATUS_Invalid;
		}
		
		try {
			//Ini OpenUp SBT 17-02-2016 Issue #5442 
			//Verifico que todas las líneas tengan MP asociada
			List<MPayOrderGenLine> lines = this.getLines();
			for(MPayOrderGenLine line: lines){
				if(!(line.getUY_PaymentRule_ID()>0)){
					if(this.processMsg==null){
						this.processMsg = "No se ha asignado un Medio de Pago, a la Orden Nº "+line.getUY_PayOrder().getDocumentNo();
					}else{
						this.processMsg = this.processMsg +", Nº "+line.getUY_PayOrder().getDocumentNo();
					}
				}
			}
			if(this.processMsg!=null) return DocAction.STATUS_Invalid; //si alguna línea no tiene MP 
			//Fin OpenUp Issue #5442 

			// Completo ordenes de pago generadas en este proceso
			List<MPayOrder> orders = this.getPayOrders();
			
			for (MPayOrder order: orders){
				if (!order.processIt(ACTION_Complete)){
					this.processMsg = order.getProcessMsg();
					return DocAction.STATUS_Invalid;
				}
			}
			
		} catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		}
		
		// Timing After Complete		
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (this.processMsg != null)
			return DocAction.STATUS_Invalid;

		// Refresco atributos
		this.setDocAction(DocAction.ACTION_None);
		this.setDocStatus(DocumentEngine.STATUS_Completed);
		this.setProcessing(false);
		this.setProcessed(true);
		
		return DocAction.STATUS_Completed;
		
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#voidIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 11/02/2013
	 * @see
	 * @return
	 */
	@Override
	public boolean voidIt() {

		// Before Void
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_VOID);
		if (this.processMsg != null)
			return false;
		
		//Ini OpenUp SBT 16-02-2016 Issue #5440 Permitir anulación de GEN OP
		List<MPayOrder> orders = this.getPayOrders();		
		for (MPayOrder order: orders){
			boolean ret = order.voidIt();
			if(!ret){
				this.processMsg = order.getProcessMsg();
				return false;
			}
			order.saveEx();
		}
		//FIN OpenUp SBT 16-02-2016 Issue #5440 Permitir anulación de GEN OP

		
		// After Void
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_VOID);
		if (this.processMsg != null)
			return false;

		setProcessed(true);
		setDocStatus(DocAction.STATUS_Voided);
		setDocAction(DocAction.ACTION_None);
		
		return true;
		
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#closeIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 11/02/2013
	 * @see
	 * @return
	 */
	@Override
	public boolean closeIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#reverseCorrectIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 11/02/2013
	 * @see
	 * @return
	 */
	@Override
	public boolean reverseCorrectIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#reverseAccrualIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 11/02/2013
	 * @see
	 * @return
	 */
	@Override
	public boolean reverseAccrualIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#reActivateIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 11/02/2013
	 * @see
	 * @return
	 */
	@Override
	public boolean reActivateIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getSummary()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 11/02/2013
	 * @see
	 * @return
	 */
	@Override
	public String getSummary() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getDocumentInfo()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 11/02/2013
	 * @see
	 * @return
	 */
	@Override
	public String getDocumentInfo() {
		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		return dt.getName() + " " + getDocumentNo();
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#createPDF()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 11/02/2013
	 * @see
	 * @return
	 */
	@Override
	public File createPDF() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getProcessMsg()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 11/02/2013
	 * @see
	 * @return
	 */
	@Override
	public String getProcessMsg() {
		return this.processMsg;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getDoc_User_ID()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 11/02/2013
	 * @see
	 * @return
	 */
	@Override
	public int getDoc_User_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getC_Currency_ID()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 11/02/2013
	 * @see
	 * @return
	 */
	@Override
	public int getC_Currency_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getApprovalAmt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 11/02/2013
	 * @see
	 * @return
	 */
	@Override
	public BigDecimal getApprovalAmt() {
		// TODO Auto-generated method stub
		return null;
	}

	/***
	 * Genera ordenes de pago segun seleccion de datos manual en lineas de generacion.
	 * OpenUp Ltda. Issue #348 
	 * @author Gabriel Vila - 30/04/2013
	 * @see
	 */
	public void generatePayOrders() {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try{

			// Elimino toda orden de pago en borrador asociada a esta generacion
			String action = " delete from uy_payorder cascade where uy_payordergen_id =" + this.get_ID();
			DB.executeUpdateEx(action, get_TrxName());
			
			// Obtengo lineas de generacion. Estas lineas contienen todos los cambios manuales
			// que hizo el usuario.
			sql = " SELECT * " +
				  " FROM uy_payordergenline " +
				  " WHERE uy_payordergen_id =? "+
				  " order by c_bpartner_id, c_bankaccount_id, uy_paymentrule_id, duedate  ";
				
			pstmt = DB.prepareStatement(sql, get_TrxName());
			pstmt.setInt(1, this.get_ID());

			rs = pstmt.executeQuery();

			int cBPartnerID = 0, cbankAccountID = 0, uyPaymentRuleID = 0;
			Timestamp dueDate = null;

			MPayOrder pord = null;
			
			while (rs.next()) {
				
				// Corte por socio de negocio, cuenta bancaria, medio de pago y vencimiento
				if ((rs.getInt("c_bpartner_id") != cBPartnerID)
							|| (rs.getInt("c_bankaccount_id") != cbankAccountID)
							|| (rs.getInt("uy_paymentrule_id") != uyPaymentRuleID)
							|| (!rs.getTimestamp("duedate").equals(dueDate))){

					cBPartnerID = rs.getInt("c_bpartner_id");
					cbankAccountID = rs.getInt("c_bankaccount_id");
					uyPaymentRuleID = rs.getInt("uy_paymentrule_id");
					dueDate = rs.getTimestamp("duedate");
					
					// Nueva orden de pago para este socio de negocio - cuenta bancaria - medio de pago - vencimiento
					pord = new MPayOrder(getCtx(), 0, get_TrxName());
					pord.setC_BPartner_ID(cBPartnerID);
					pord.setC_DocType_ID(MDocType.forValue(getCtx(), "payorder", get_TrxName()).get_ID());
					pord.setDateTrx(TimeUtil.trunc(new Timestamp (System.currentTimeMillis()), TimeUtil.TRUNC_DAY));
					pord.setDescription("Generada Automaticamente.");
					pord.setUY_PayOrderGen_ID(this.get_ID());
					pord.setSubtotal(Env.ZERO);
					pord.setDiscountAmt(Env.ZERO);
					pord.setPayAmt(Env.ZERO);
					if (cbankAccountID > 0){
						MBankAccount ba = new MBankAccount(getCtx(), cbankAccountID, null);
						pord.setC_BankAccount_ID(cbankAccountID);
						pord.setC_Currency_ID(ba.getC_Currency_ID());
					}

					if (uyPaymentRuleID > 0) pord.setUY_PaymentRule_ID(uyPaymentRuleID);
					if (dueDate != null) pord.setDueDate(dueDate);
					
					pord.saveEx();
				}
					
				// Nueva linea de orden de pago
				MPayOrderLine pordline = new MPayOrderLine(getCtx(), 0, get_TrxName());
				pordline.setUY_PayOrder_ID(pord.get_ID());
				pordline.setC_BPartner_ID(cBPartnerID);
				pordline.setC_DocType_ID(rs.getInt("c_doctype_id"));
				pordline.setC_Invoice_ID(rs.getInt("c_invoice_id"));
				MInvoice invoice = (MInvoice)pordline.getC_Invoice();
				if (invoice.getC_Order_ID() > 0){
					pordline.setC_Order_ID(invoice.getC_Order_ID());
				}
				pordline.setC_Currency_ID(rs.getInt("c_currency_id"));
				pordline.setGrandTotal(rs.getBigDecimal("grandtotal"));
				pordline.setamtopen(rs.getBigDecimal("amtopen"));
				pordline.setamtallocated(rs.getBigDecimal("amtallocated"));
				pordline.setDateInvoiced(rs.getTimestamp("dateinvoiced"));
				pordline.setCurrencyRate(rs.getBigDecimal("currencyrate"));
				pordline.setAmount(rs.getBigDecimal("amtallocated"));
				if (rs.getInt("C_InvoicePaySchedule_ID") > 0){
					pordline.setC_InvoicePaySchedule_ID(rs.getInt("C_InvoicePaySchedule_ID"));
				}
				pordline.setLiteralQuote(rs.getString("LiteralQuote"));
				pordline.setDueDate(rs.getTimestamp("DueDate2"));
				
				pordline.saveEx();
					
				// Actualizo orden de linea de generacion
				DB.executeUpdateEx("update uy_payordergenline set uy_payorder_id =" + pord.get_ID() + 
									", uy_payorderline_id =" + pordline.get_ID() + 
									" where uy_payordergenline_id=" + rs.getInt("uy_payordergenline_id"), get_TrxName());

			}

			// Actualizo el cabezal como no cambiado
			if (this.isChanged()){
				this.setIsChanged(false);
				this.saveEx();
			}
				
		} 
		catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		} 
		finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		
	}
	
	/**
	 * Obtiene y retorna las líneas del GOP.
	 * @author OpenUp SBT Issue #5442  17/2/2016 11:04:29
	 * @return
	 */
	private List<MPayOrderGenLine> getLines(){
		
		String whereClause = X_UY_PayOrderGenLine.COLUMNNAME_UY_PayOrderGen_ID + "=" + this.get_ID()
				+ " AND "+ X_UY_PayOrderGenLine.COLUMNNAME_IsActive + " = 'Y' " ;
		
		List<MPayOrderGenLine> values = new Query(getCtx(), I_UY_PayOrderGenLine.Table_Name,
				whereClause, get_TrxName()).list();
		
		return values;
		
	}

}
