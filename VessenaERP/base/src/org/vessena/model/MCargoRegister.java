package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.compiere.acct.FactLine;
import org.compiere.model.MDocType;
import org.compiere.model.MPeriod;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;

public class MCargoRegister extends X_UY_CargoRegister implements DocAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5273970571174706164L;
	
	private String processMsg = null;
	private boolean justPrepared = false;
	
	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_CargoRegister_ID
	 * @param trxName
	 */
	public MCargoRegister(Properties ctx, int UY_CargoRegister_ID,
			String trxName) {
		super(ctx, UY_CargoRegister_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MCargoRegister(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	/***
	 * Obtiene y retorna lista de lineas de este registro de cargo.
	 * OpenUp Ltda. Issue #760
	 * @author Nicolas Sarlabos - 26/04/2013
	 * @see
	 * @return
	 */
	public List<MCargoRegisterLine> getLines(){
		
		String whereClause = X_UY_CargoRegister.COLUMNNAME_UY_CargoRegister_ID + "=" + this.get_ID();
		
		List<MCargoRegisterLine> lines = new Query(getCtx(), I_UY_CargoRegisterLine.Table_Name, whereClause, get_TrxName())
		.list();
		
		return lines;
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
		// Todo bien
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
		
		// Test period
		MPeriod.testPeriodOpen(getCtx(), getDateAcct(), getC_DocType_ID(), getAD_Org_ID());
		
		// Obtengo lineas
		List <MCargoRegisterLine> lines = this.getLines();
		
		if (lines.size()==0){
			this.processMsg = "Debe ingresar lineas para Procesar.";
			return DocAction.STATUS_Invalid;
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
	
	@Override
	public boolean voidIt() {
		// Before Void
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_VOID);
		if (this.processMsg != null)
			return false;

		// Test period
		MPeriod.testPeriodOpen(getCtx(), getDateAcct(), getC_DocType_ID(), getAD_Org_ID());
				
		// Elimino asientos contables
		FactLine.deleteFact(I_UY_CargoRegister.Table_ID, this.get_ID(), get_TrxName());
		
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
		// TODO Auto-generated method stub
		return false;
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
