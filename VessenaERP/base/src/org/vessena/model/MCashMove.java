/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 24/05/2013
 */
package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.compiere.model.MBankAccount;
import org.compiere.model.MDocType;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

/**
 * org.openup.model - MCashMove
 * OpenUp Ltda. Issue #869 
 * Description: Transaccion de movimientos manuales de cajas.
 * @author Gabriel Vila - 24/05/2013
 * @see
 */
public class MCashMove extends X_UY_CashMove implements DocAction {

	private static final long serialVersionUID = 7693800094118278963L;
	private String processMsg = null;
	private boolean justPrepared = false;
	
	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_CashMove_ID
	 * @param trxName
	 */
	public MCashMove(Properties ctx, int UY_CashMove_ID, String trxName) {
		super(ctx, UY_CashMove_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MCashMove(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#processIt(java.lang.String)
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 24/05/2013
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
	 * @author Hp - 24/05/2013
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
	 * @author Hp - 24/05/2013
	 * @see
	 * @return
	 */
	@Override
	public boolean invalidateIt() {
		log.info("invalidateIt - " + toString());
		setDocAction(DocAction.ACTION_Prepare);
		return true;
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		// Me aseguro fecha sin hora
		Timestamp dateAux = this.getDateTrx();
		this.setDateTrx(TimeUtil.trunc(dateAux, TimeUtil.TRUNC_DAY));
		
		//OpenUp. Nicolas Sarlabos. 14/08/2012. #1218
		MBankAccount ba = (MBankAccount)this.getC_BankAccount();
	
		if (ba != null){
			if(ba.get_ValueAsBoolean("IsCashOpen")){//OpenUp. Nicolas Sarlabos. 26/08/2015.Se consulta si la cuenta requiere apertura y cierre de caja.
				if(ba.getBankAccountType()!=null){
					if(ba.getBankAccountType().equalsIgnoreCase("X")){
						ba.validateCashOpen(this.getDateTrx());
					}
				}		
			}
		}		
		//Fin OpenUp #1218	
		
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#prepareIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 24/05/2013
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
	 * @author Hp - 24/05/2013
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
	 * @author Hp - 24/05/2013
	 * @see
	 * @return
	 */
	@Override
	public boolean applyIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#rejectIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 24/05/2013
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
	 * @author Hp - 24/05/2013
	 * @see
	 * @return
	 */
	@Override
	public String completeIt() {

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

		
		// Nueva entrada en tabla sumarizadora por Cuenta Bancaria.
		MBankAccount ba = (MBankAccount)this.getC_BankAccount();
		MSUMAccountStatus sumba = new MSUMAccountStatus(getCtx(), 0, get_TrxName());
		sumba.setC_BankAccount_ID(this.getC_BankAccount_ID());
		sumba.setDateTrx(this.getDateTrx());
		sumba.setC_DocType_ID(this.getC_DocType_ID());
		sumba.setDocumentNo(this.getDocumentNo());
		sumba.setAD_Table_ID(I_UY_CashMove.Table_ID);
		sumba.setRecord_ID(this.get_ID());
		sumba.setDueDate(this.getDateTrx());
		sumba.setC_Currency_ID(ba.getC_Currency_ID());
		sumba.setamtdocument(this.getAmount());
		sumba.setDescription(this.getDescription());
		if (this.getCashMoveType().equalsIgnoreCase(X_UY_CashMove.CASHMOVETYPE_Salida)){
			sumba.setAmtSourceDr(this.getAmount());
			sumba.setAmtSourceCr(Env.ZERO);
			sumba.setAmtAcctCr(Env.ZERO);
		}
		else{
			sumba.setAmtSourceCr(this.getAmount());
			sumba.setAmtSourceDr(Env.ZERO);
			sumba.setAmtAcctDr(Env.ZERO);
		}
		sumba.saveEx();
		
		
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
	 * @author Hp - 24/05/2013
	 * @see
	 * @return
	 */
	@Override
	public boolean voidIt() {

		// Before Void
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_VOID);
		if (this.processMsg != null)
			return false;

		String action = " delete from uy_sum_accountstatus where ad_table_id = " + I_UY_CashMove.Table_ID +
						" and record_id = " + this.get_ID();
		DB.executeUpdateEx(action, get_TrxName());
		
		
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
	 * @author Hp - 24/05/2013
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
	 * @author Hp - 24/05/2013
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
	 * @author Hp - 24/05/2013
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
	 * @author Hp - 24/05/2013
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
	 * @author Hp - 24/05/2013
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
	 * @author Hp - 24/05/2013
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
	 * @author Hp - 24/05/2013
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
	 * @author Hp - 24/05/2013
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
	 * @author Hp - 24/05/2013
	 * @see
	 * @return
	 */
	@Override
	public int getDoc_User_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getApprovalAmt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 24/05/2013
	 * @see
	 * @return
	 */
	@Override
	public BigDecimal getApprovalAmt() {
		// TODO Auto-generated method stub
		return null;
	}

}
