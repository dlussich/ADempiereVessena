/**
 * 
 */
package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBankAccount;
import org.compiere.model.MDocType;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;

/**
 * @author Nicolas
 *
 */
public class MBankBalance extends X_UY_BankBalance implements DocAction {
	
	private String processMsg = null;
	private boolean justPrepared = false;

	/**
	 * 
	 */
	private static final long serialVersionUID = 2279500607568884769L;

	/**
	 * @param ctx
	 * @param UY_BankBalance_ID
	 * @param trxName
	 */
	public MBankBalance(Properties ctx, int UY_BankBalance_ID, String trxName) {
		super(ctx, UY_BankBalance_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MBankBalance(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean processIt(String action) throws Exception {
		this.processMsg = null;
		DocumentEngine engine = new DocumentEngine (this, getDocStatus());
		return engine.processIt (action, getDocAction());
	}

	@Override
	public boolean unlockIt() {
		log.info("unlockIt - " + toString());
		setProcessing(false);
		return true;
	}

	@Override
	public boolean invalidateIt() {
		log.info("invalidateIt - " + toString());
		setDocAction(DocAction.ACTION_Prepare);
		return true;
	}

	@Override
	public String prepareIt() {
		this.justPrepared = true;
		if (!DocAction.ACTION_Complete.equals(getDocAction()))
			setDocAction(DocAction.ACTION_Complete);
		return DocAction.STATUS_InProgress;
	}

	@Override
	public boolean approveIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean applyIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean rejectIt() {
		// TODO Auto-generated method stub
		return false;
	}

	public List<MBankBalanceLine> getLines(){
		
		String whereClause = X_UY_BankBalanceLine.COLUMNNAME_UY_BankBalance_ID + " = " + this.get_ID(); 
									
		return new Query(this.getCtx(), I_UY_BankBalanceLine.Table_Name, whereClause, this.get_TrxName()).list();		
	}

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
		
		if(this.getLines().size()==0) throw new AdempiereException("Imposible completar, el documento no tiene lineas");
		
		this.createMovements();
			
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

	/***
	 * OpenUp. Nicolas Sarlabos. 26/09/2014. #
	 * Metodo que recorre la lineas del documento e impacta la tabla sumarizadora de cuentas bancarias.
	 *
	 * */
	private void createMovements() {
		
		List<MBankBalanceLine> bLines = this.getLines();
		
		for (MBankBalanceLine line : bLines) {
			
			MBankAccount account = (MBankAccount) line.getC_BankAccount();
			
			MSUMAccountStatus sumba = new MSUMAccountStatus(getCtx(), 0, get_TrxName());
			sumba.setC_BankAccount_ID(line.getC_BankAccount_ID());
			sumba.setDateTrx(this.getDateTrx());
			sumba.setDueDate(this.getDateTrx());
			sumba.setC_DocType_ID(this.getC_DocType_ID());
			sumba.setDocumentNo(this.getDocumentNo());
			sumba.setAD_Table_ID(I_UY_BankBalance.Table_ID);
			sumba.setRecord_ID(this.get_ID());
			sumba.setC_Currency_ID(account.getC_Currency_ID());
			sumba.setAmtSourceCr(line.getAmtSourceCr());
			sumba.setAmtSourceDr(line.getAmtSourceDr());
			sumba.setDescription(this.getDescription());
			
			sumba.saveEx();			
		}		
	}

	@Override
	public boolean voidIt() {
		// Before Void
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_VOID);
		if (this.processMsg != null)
			return false;
		
		//elimino movimientos en tabla sumarizadora		
		String sql = "delete from uy_sum_accountstatus where ad_table_id = " + I_UY_BankBalance.Table_ID +
				     " and c_doctype_id = " + this.getC_DocType_ID() + " and record_id = " + this.get_ID();
		
		DB.executeUpdateEx(sql, get_TrxName());
		
		// After Void
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_VOID);
		if (this.processMsg != null)
			return false;

		setProcessed(true);
		setDocStatus(DocAction.STATUS_Voided);
		setDocAction(DocAction.ACTION_None);

		return true;
	}

	@Override
	public boolean closeIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean reverseCorrectIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean reverseAccrualIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean reActivateIt() {
		// Before reActivate
		processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_BEFORE_REACTIVATE);
		if (processMsg != null)
			return false;
		
		//elimino movimientos en tabla sumarizadora		
		String sql = "delete from uy_sum_accountstatus where ad_table_id = " + I_UY_BankBalance.Table_ID +
				     " and c_doctype_id = " + this.getC_DocType_ID() + " and record_id = " + this.get_ID();
		
		DB.executeUpdateEx(sql, get_TrxName());

		// After reActivate
		processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_AFTER_REACTIVATE);
		if (processMsg != null)
			return false;

		// Seteo estado de documento
		this.setProcessed(false);
		this.setDocStatus(DocumentEngine.STATUS_InProgress);
		this.setDocAction(DocumentEngine.ACTION_Complete);

		return true;
	}

	@Override
	public String getSummary() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDocumentInfo() {
		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		return dt.getName() + " " + getDocumentNo();
	}

	@Override
	public File createPDF() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getProcessMsg() {
		return this.processMsg;
	}

	@Override
	public int getDoc_User_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getC_Currency_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public BigDecimal getApprovalAmt() {
		// TODO Auto-generated method stub
		return null;
	}

}
