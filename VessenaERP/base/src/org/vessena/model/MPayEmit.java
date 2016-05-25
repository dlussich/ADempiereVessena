/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 13/02/2013
 */
package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.apps.ProcessCtl;
import org.compiere.model.MBank;
import org.compiere.model.MBankAccount;
import org.compiere.model.MDocType;
import org.compiere.model.MPInstance;
import org.compiere.model.MPInstancePara;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.process.ProcessInfo;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * org.openup.model - MPayEmit
 * OpenUp Ltda. Issue #351 
 * Description: Emision de Pagos
 * @author Gabriel Vila - 13/02/2013
 * @see
 */
public class MPayEmit extends X_UY_PayEmit implements DocAction {
	
	private static final long serialVersionUID = 5688998744340439730L;
	private String processMsg = null;
	private boolean justPrepared = false;
	

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_PayEmit_ID
	 * @param trxName
	 */
	public MPayEmit(Properties ctx, int UY_PayEmit_ID, String trxName) {
		super(ctx, UY_PayEmit_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MPayEmit(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#processIt(java.lang.String)
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 13/02/2013
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
	 * @author Hp - 13/02/2013
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
	 * @author Hp - 13/02/2013
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
	 * @author Hp - 13/02/2013
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
	 * @author Hp - 13/02/2013
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
	 * @author Hp - 13/02/2013
	 * @see
	 * @return
	 */
	@Override
	public boolean applyIt() {
		this.loadData();
		this.setDocAction(DocAction.ACTION_Complete);
		this.setDocStatus(DocumentEngine.STATUS_Applied);
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#rejectIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 13/02/2013
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
	 * @author Hp - 13/02/2013
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

		// Valido ordenes de pago y genero documentos de pago y afectacion para cada una.
		List<MPayEmitLine> lines = this.getLinesSelected();
		if (lines.size() <= 0) {
			this.processMsg = "No hay ordenes de pago seleccionadas para procesar.";
			return DocAction.STATUS_Invalid;
		}
		
		MPaymentRule payRule = (MPaymentRule)this.getUY_PaymentRule();
		
		// Si tengo libreta la instancio
		MCheckReam ream = null;
		if (this.getUY_CheckReam_ID() > 0){
			ream = (MCheckReam)this.getUY_CheckReam();
			ream.lock();
		}
		
		for (MPayEmitLine line: lines){
			
			MPayOrder order = (MPayOrder)line.getUY_PayOrder();
			if (!order.getDocStatus().equalsIgnoreCase(STATUS_Completed)){
				this.processMsg = "Orden de pago : " + order.getDocumentNo() + " no esta completa.";
				return DocAction.STATUS_Invalid;
			}
			
			if (order.getUY_PayEmit_ID() > 0){
				MPayEmit emit = (MPayEmit)order.getUY_PayEmit();
				this.processMsg = "Orden de pago : " + order.getDocumentNo() + " ya fue emitida con el numero de Documento de Emision : " + emit.getDocumentNo();
				return DocAction.STATUS_Invalid;				
			}

			// Modelo para medio de pago asociado a esta orden de pago
			MPayOrderRule payOrderRule = new MPayOrderRule(getCtx(), 0, get_TrxName());
			payOrderRule.setUY_PayOrder_ID(order.get_ID());
			payOrderRule.setDateTrx(this.getDateTrx());
			payOrderRule.setDueDate(order.getDueDate());
			payOrderRule.setC_BankAccount_ID(this.getC_BankAccount_ID());
			payOrderRule.setPayAmt(order.getPayAmt());
			
			// Si uso libreta
			if (ream != null){
				
				MCheckReamLine reamLine = null;
				
				// Si es libreta manual
				if (!ream.isResma()){
					reamLine = (MCheckReamLine)line.getUY_CheckReamLine();
					if (reamLine == null){
						this.processMsg = "Debe indicar Numero de Medio de Pago para Orden de Pago " + order.getDocumentNo();
						return DocAction.STATUS_Invalid;
					}
					if (reamLine.isused()){
						this.processMsg = "El medio de pago numero: " + reamLine.getCheckNo() + " ya fue emitido.";
						return DocAction.STATUS_Invalid;
					}
				}
				else{
					// Es una resma de cheques a imprimir
					// Obtiengo siguiente linea de libreta con numero de cheque disponible para este nuevo recibo de pago.
					reamLine = ream.getNextLineNotUsed();
					if (reamLine == null){
						this.processMsg = "No hay mas medios de pago disponibles para la Libreta seleccionada.";
						return DocAction.STATUS_Invalid;
					}
				}
				
				// Emito el medio de pago
				MMediosPago mpago = new MMediosPago(getCtx(), 0, get_TrxName());
				mpago.setUY_CheckReam_ID(ream.get_ID());
				mpago.setDateTrx(this.getDateTrx());
				mpago.setDueDate(order.getDueDate());
				mpago.setC_DocType_ID(payRule.getC_DocType_ID_PayEmit());
				mpago.setC_BankAccount_ID(this.getC_BankAccount_ID());
				mpago.setC_BPartner_ID(order.getC_BPartner_ID());
				mpago.setC_Currency_ID(order.getC_Currency_ID());
				mpago.setPayAmt(order.getPayAmt());
				mpago.setDateAcct(mpago.getDateTrx());
				mpago.settipomp(X_UY_MediosPago.TIPOMP_Propio);
				mpago.setestado(X_UY_MediosPago.ESTADO_Emitido);
				mpago.setserie(ream.getserie());
				mpago.setoldstatus(mpago.getestado());
				mpago.setisrechazado(false);
				mpago.setuy_isreemplazo(false);
				mpago.setTenderType(X_UY_MediosPago.TENDERTYPE_Check);
				mpago.setIsInitialLoad(false);
				mpago.setUY_PaymentRule_ID(payRule.get_ID());
				mpago.setUY_CheckReamLine_ID(reamLine.get_ID());
				mpago.setCheckNo(String.valueOf(reamLine.getCheckNo()));
				mpago.setUY_PayOrder_ID(order.get_ID());
				mpago.setUY_PayEmit_ID(this.get_ID());
				mpago.saveEx();
				
				// Completo Medio de Pago
				try {
					if (!mpago.processIt(DocumentEngine.ACTION_Complete)){
						throw new AdempiereException(mpago.getProcessMsg());
					}
				} 
				catch (Exception e) {
					throw new AdempiereException(e.getMessage());
				}

				// Seteo datos del medio de pago asociado a la orden de pago
				payOrderRule.setUY_MediosPago_ID(mpago.get_ID());
				payOrderRule.setCheckNo(mpago.getCheckNo());
				payOrderRule.setInternalCode(mpago.getDocumentNo());
				payOrderRule.setTenderType(mpago.getTenderType());
				
			}
			else{
				// No tengo libreta para este medio de pago
				// Si el medio de pago hace emision
				if (payRule.isPayEmit()){
					// Valido numero manual
					if ((line.getInternalCode() == null) || (line.getInternalCode().trim().equalsIgnoreCase(""))){
						this.processMsg = "Debe indicar Numero Manual para Orden de Pago " + order.getDocumentNo();
						return DocAction.STATUS_Invalid;
					}
					
					// Emito el medio de pago
					MMediosPago mpago = new MMediosPago(getCtx(), 0, get_TrxName());
					mpago.setDateTrx(this.getDateTrx());
					mpago.setDueDate(order.getDueDate());
					mpago.setC_DocType_ID(payRule.getC_DocType_ID_PayEmit());
					mpago.setC_BankAccount_ID(this.getC_BankAccount_ID());
					mpago.setC_BPartner_ID(order.getC_BPartner_ID());
					mpago.setC_Currency_ID(order.getC_Currency_ID());
					mpago.setPayAmt(order.getPayAmt());
					mpago.setDateAcct(mpago.getDateTrx());
					mpago.settipomp(X_UY_MediosPago.TIPOMP_Propio);
					mpago.setestado(X_UY_MediosPago.ESTADO_Emitido);
					mpago.setoldstatus(mpago.getestado());
					mpago.setisrechazado(false);
					mpago.setuy_isreemplazo(false);
					mpago.setTenderType(X_UY_MediosPago.TENDERTYPE_Check);
					mpago.setIsInitialLoad(false);
					mpago.setUY_PaymentRule_ID(payRule.get_ID());
					mpago.setCheckNo(line.getInternalCode().trim());
					mpago.setUY_PayOrder_ID(order.get_ID());
					mpago.setUY_PayEmit_ID(this.get_ID());
					mpago.saveEx();
					
					// Completo Medio de Pago
					try {
						if (!mpago.processIt(DocumentEngine.ACTION_Complete)){
							throw new AdempiereException(mpago.getProcessMsg());
						}
					} 
					catch (Exception e) {
						throw new AdempiereException(e.getMessage());
					}

					// Seteo datos del medio de pago asociado a la orden de pago
					payOrderRule.setUY_MediosPago_ID(mpago.get_ID());
					payOrderRule.setCheckNo(mpago.getCheckNo());
					payOrderRule.setInternalCode(mpago.getDocumentNo());
					payOrderRule.setTenderType(mpago.getTenderType());

				}
				else{
					// Seteo datos del medio de pago asociado a la orden de pago
					payOrderRule.setTenderType(X_UY_MediosPago.TENDERTYPE_Cash);
				}
			}

			order.setUY_PayEmit_ID(this.get_ID());
			order.saveEx();

			payOrderRule.setUY_PaymentRule_ID(payRule.get_ID());
			payOrderRule.saveEx();
			
		}

		if (ream != null){
			ream.unlock(get_TrxName());
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
	 * @author Hp - 13/02/2013
	 * @see
	 * @return
	 */
	@Override
	public boolean voidIt() {

		// Before Void
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_VOID);
		if (this.processMsg != null)
			return false;
		
		
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
	 * @author Hp - 13/02/2013
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
	 * @author Hp - 13/02/2013
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
	 * @author Hp - 13/02/2013
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
	 * @author Hp - 13/02/2013
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
	 * @author Hp - 13/02/2013
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
	 * @author Hp - 13/02/2013
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
	 * @author Hp - 13/02/2013
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
	 * @author Hp - 13/02/2013
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
	 * @author Hp - 13/02/2013
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
	 * @author Hp - 13/02/2013
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
	 * @author Hp - 13/02/2013
	 * @see
	 * @return
	 */
	@Override
	public BigDecimal getApprovalAmt() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/***
	 * Carga ordenes de pago pendientes de emision.
	 * OpenUp Ltda. Issue #351 
	 * @author Gabriel Vila - 13/02/2013
	 * @see
	 */
	private void loadData() {
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		
		try{
			
			String whereFiltros = "";

			if (this.getStartDate() != null) whereFiltros += " AND op.datetrx >='" + this.getStartDate() + "'";
			if (this.getEndDate() != null) whereFiltros += " AND op.datetrx <='" + this.getEndDate() + "'";

			if (this.getC_BPartner_ID() > 0){
				whereFiltros += " and op.c_bpartner_id =" + this.getC_BPartner_ID();
			}
			
			if (this.getUY_PayOrder_ID() > 0){
				whereFiltros += " and op.uy_payorder_id =" + this.getUY_PayOrder_ID();
			}			
			
			MBankAccount ba = (MBankAccount)this.getC_BankAccount(); 
			MPaymentRule payRule = (MPaymentRule)this.getUY_PaymentRule();
			MCheckReam ream = null;
			if ((this.isStocked()) && (this.getUY_CheckReam_ID() > 0)){
				ream = (MCheckReam)this.getUY_CheckReam();
			}
			
			sql = " select op.c_bpartner_id, op.datetrx, op.uy_payorder_id, op.c_doctype_id, " +
				  " op.payamt, op.c_currency_id, op.duedate " +
				  " from uy_payorder op " +
				  " where op.ad_client_id =? " +
				  " and op.c_bankaccount_id =? " +
				  " and op.uy_paymentrule_id =?" +
				  " and op.docstatus='CO' " +
				  " and op.uy_payemit_id is null " + whereFiltros +
				  " order by op.c_bpartner_id, op.datetrx ";
			
			pstmt = DB.prepareStatement(sql.toString(), null);
			pstmt.setInt(1, this.getAD_Client_ID());
			pstmt.setInt(2, ba.get_ID());
			pstmt.setInt(3, payRule.get_ID());

			rs = pstmt.executeQuery();

			while (rs.next()) {
			
				// Nueva linea de emision
				MPayEmitLine line = new MPayEmitLine(getCtx(), 0, get_TrxName());
				line.setUY_PayEmit_ID(this.get_ID());
				line.setC_BPartner_ID(rs.getInt("c_bpartner_id"));
				line.setUY_PayOrder_ID(rs.getInt("uy_payorder_id"));
				line.setDateTrx(rs.getTimestamp("datetrx"));
				line.setPayAmt(rs.getBigDecimal("payamt"));
				line.setC_Currency_ID(rs.getInt("c_currency_id"));
				line.setC_DocType_ID(rs.getInt("c_doctype_id"));
				line.setIsSelected(true);
				line.setIsStocked(this.isStocked());
				
				// Marco flag en linea en TRUE si requiere numero manual sin libreta y en FALSE si no requiere.
				line.setIsPayEmit(false);
				if (payRule.isPayEmit()){
					if (ream != null){
						if (!ream.isResma()){
							line.setIsPayEmit(true);
						}
					}
				}

				if (ream != null){
					line.setIsResma(ream.isResma());
				}
				line.saveEx();
			}

			
		} catch (Exception e) {

			throw new AdempiereException(e.getMessage());

		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

	}

	
	/***
	 * Obtiene y retorna cheques emitidos.
	 * OpenUp Ltda. Issue #678 
	 * @author Gabriel Vila - 12/04/2013
	 * @see
	 * @return
	 */
	public List<MMediosPago> getNotPrintedChecks(){
		
		String whereClause = X_UY_MediosPago.COLUMNNAME_UY_PayEmit_ID + "=" + this.get_ID() +
				" AND " + X_UY_MediosPago.COLUMNNAME_IsPrinted + "='N' ";
		
		List<MMediosPago> values = new Query(getCtx(), I_UY_MediosPago.Table_Name, whereClause, null)
		.setOrderBy(" uy_mediospago.uy_mediospago_id ")
		.list();
		
		return values;
	}

	/***
	 * Obtiene y retorna lineas de esta emision de pago.
	 * OpenUp Ltda. Issue #351 
	 * @author Gabriel Vila - 13/02/2013
	 * @see
	 * @return
	 */
	public List<MPayEmitLine> getLines(){
		
		String whereClause = X_UY_PayEmitLine.COLUMNNAME_UY_PayEmit_ID + "=" + this.get_ID();
		
		List<MPayEmitLine> values = new Query(getCtx(), I_UY_PayEmitLine.Table_Name, whereClause, get_TrxName())
		.list();
		
		return values;
	}

	
	/***
	 * Obtiene y retorna lineas seleccionadas de esta emision de pago.
	 * OpenUp Ltda. Issue #351 
	 * @author Gabriel Vila - 13/02/2013
	 * @see
	 * @return
	 */
	public List<MPayEmitLine> getLinesSelected(){
		
		String whereClause = X_UY_PayEmitLine.COLUMNNAME_UY_PayEmit_ID + "=" + this.get_ID() +
				" AND " + X_UY_PayEmitLine.COLUMNNAME_IsSelected + "='Y' ";
		
		List<MPayEmitLine> values = new Query(getCtx(), I_UY_PayEmitLine.Table_Name, whereClause, get_TrxName())
		.list();
		
		return values;
	}


	/***
	 * Impresion de cheques emitidos en esta transaccion de emision de pagos.
	 * OpenUp Ltda. Issue #678 
	 * @author Gabriel Vila - 12/04/2013
	 * @see
	 */
	public void printChecks(){

		try{
			
			MCheckReam ream = new MCheckReam(getCtx(), this.getUY_CheckReam_ID(), null);
			MPaymentRule reamPayRule = (MPaymentRule)ream.getUY_PaymentRule();

			if ((reamPayRule == null) || (reamPayRule.get_ID() <= 0)){
				throw new AdempiereException("Falta definir medio de pago asociado a la Resma seleccionada.");
			}
			
			// Obtengo id de formato de impresion segun banco y tipo de cheque de la resma (contado o diferido)
			MBankAccount ba = (MBankAccount)ream.getC_BankAccount();
			MBank bank = (MBank)ba.getC_Bank();
			int adProcessID = 0;
			if (reamPayRule.getpaymentruletype().equalsIgnoreCase(X_UY_PaymentRule.PAYMENTRULETYPE_CREDITO)){
				adProcessID = bank.get_ValueAsInt("Process_ID_1");
			}
			else if (reamPayRule.getpaymentruletype().equalsIgnoreCase(X_UY_PaymentRule.PAYMENTRULETYPE_CONTADO)){
				adProcessID = bank.get_ValueAsInt("Process_ID_2");
			}
			else{
				throw new AdempiereException("Tipo de Libreta NO VALIDO para esta Libreta de Cheques.");
			}
	
			
			List<MMediosPago> mpagos = this.getNotPrintedChecks();
			for (MMediosPago mpago: mpagos){
				this.printCheck(mpago.get_ID(), adProcessID);
			}

			// Marco cheques como Impresos
			for (MMediosPago mpago: mpagos){
				mpago.setIsPrinted(true);
				mpago.saveEx();
			}
			
		}
		catch(Exception e){
			throw new AdempiereException(e);
		}
	}
	
	
	/***
	 * Metodo que imprime un cheque.
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 05/04/2013
	 * @see
	 * @param uyMediosPagoID
	 * @param adProcessID
	 */
	private void printCheck(int uyMediosPagoID, int adProcessID){

		try {

			MPInstance instance = new MPInstance(Env.getCtx(), adProcessID, 0);
			instance.saveEx();
	
			ProcessInfo pi = new ProcessInfo ("PrintCheck", adProcessID);
			pi.setAD_PInstance_ID (instance.getAD_PInstance_ID());
			
			MPInstancePara para = new MPInstancePara(instance, 10);
			para.setParameter("UY_MediosPago_ID", new BigDecimal(uyMediosPagoID));
			para.saveEx();
			
			ProcessCtl worker = new ProcessCtl(null, 0, pi, null);
			worker.start();     
			
			java.lang.Thread.sleep(2000);
			
			para.deleteEx(true, null);
			instance.deleteEx(true, null);
			
			java.lang.Thread.sleep(2000);
			
		} catch (Exception e) {
			System.out.println("Exception Controlada para Tiempo de Impresion de Cheque");
		}
		
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {

		try {
			// Valido que para medios de pago que requieren libreta, el usuario indique una libreta
			if (this.isStocked()){
				if (this.getUY_CheckReam_ID() <= 0){
					throw new AdempiereException("Debe indicar Libreta para Cuenta Bancaria y Medio de Pago seleccionado");
				}
				else{
					// Seteo si es resma o no
					MCheckReam ream = (MCheckReam)this.getUY_CheckReam();
					if (ream != null){
						this.setIsResma(ream.isResma());
					}
				}
			}
		} 
		catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		}
		
		return true;
	}

	
	
}
