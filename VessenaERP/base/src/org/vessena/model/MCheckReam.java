/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 19/02/2013
 */
package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBankAccount;
import org.compiere.model.MDocType;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;

/**
 * org.openup.model - MCheckReam
 * OpenUp Ltda. Issue #366 
 * Description: Proceso de Libretas y Resmas de Cheques.
 * @author Gabriel Vila - 19/02/2013
 * @see
 */
public class MCheckReam extends X_UY_CheckReam implements DocAction {

	private static final long serialVersionUID = -3429447915146448687L;
	private String processMsg = null;
	private boolean justPrepared = false;

	
	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_CheckReam_ID
	 * @param trxName
	 */
	public MCheckReam(Properties ctx, int UY_CheckReam_ID, String trxName) {
		super(ctx, UY_CheckReam_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MCheckReam(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#processIt(java.lang.String)
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 19/02/2013
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
	 * @author Hp - 19/02/2013
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
	 * @author Hp - 19/02/2013
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
	 * @author Hp - 19/02/2013
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
	 * @author Hp - 19/02/2013
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
	 * @author Hp - 19/02/2013
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
	 * @author Hp - 19/02/2013
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
	 * @author Hp - 19/02/2013
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

		// Valido rango de numero de cheques
		int start = this.getstarcheckno();
		int end = this.getendcheckno();
		
		if (end < start){
			this.processMsg = "Rango de numeros de cheques incorrecto. Verifique.";
			return DocAction.STATUS_Invalid;
		}
		
		// Genera lineas de libreta con cheques asociados
		this.generateLines(start, end);
		
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
	 * Genera lineas de libreta de cheques. Cada linea tendra un numero de cheque asociada.
	 * OpenUp Ltda. Issue #366 
	 * @author Gabriel Vila - 19/02/2013
	 * @param start
	 * @param end
	 * @see
	 */
	private void generateLines(int start, int end) {

		try{
			
			for (int i = start; i <= end; i++){
				
				MCheckReamLine line = new MCheckReamLine(getCtx(), 0, get_TrxName());
				line.setUY_CheckReam_ID(this.get_ID());
				line.setCheckNo(i);
				line.setisused(false);
				line.saveEx();
			}
			
		}
		catch (Exception ex){
			throw new AdempiereException(ex);
		}
		
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#voidIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 19/02/2013
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
	 * @author Hp - 19/02/2013
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
	 * @author Hp - 19/02/2013
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
	 * @author Hp - 19/02/2013
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
	 * @author Hp - 19/02/2013
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
	 * @author Hp - 19/02/2013
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
	 * @author Hp - 19/02/2013
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
	 * @author Hp - 19/02/2013
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
	 * @author Hp - 19/02/2013
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
	 * @author Hp - 19/02/2013
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
	 * @author Hp - 19/02/2013
	 * @see
	 * @return
	 */
	@Override
	public BigDecimal getApprovalAmt() {
		// TODO Auto-generated method stub
		return null;
	}

	/***
	 * Obtiene y retorna siguiente linea de libreta que no esta siendo utilizada en ningun cheque.
	 * Linea libre para ser asociada a una nueva emision de cheque.
	 * OpenUp Ltda. Issue #351 
	 * @author Gabriel Vila - 21/02/2013
	 * @see
	 * @return modelo de siguiente linea libre o null si no hay.
	 */
	public MCheckReamLine getNextLineNotUsed(){
		
		MCheckReamLine value = null;
		
		try{
	
			String sql = " select min(uy_checkreamline_id) " +
					     " from uy_checkreamline " +
					     " where uy_checkream_id =? " +
					     " and isused='N'";
			int uyCheckReamLineID = DB.getSQLValue(get_TrxName(), sql, this.get_ID());
			
			if (uyCheckReamLineID > 0){
				value = new MCheckReamLine(getCtx(), uyCheckReamLineID, get_TrxName());
			}
			 
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
		
		return value;
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {

		try{
			
			MBankAccount ba = (MBankAccount)this.getC_BankAccount();
			if (ba != null){
				this.setC_Currency_ID(ba.getC_Currency_ID());
			}
			
		}
		catch(Exception e){
			throw new AdempiereException(e);
		}
		
		return true;
	}

	/***
	 * Verifica y actualiza el estado de resma totalmente emitida.
	 * OpenUp Ltda. Issue #684 
	 * @author Gabriel Vila - 12/04/2013
	 * @see
	 */
	public void verifyEmitted() {

		try{
			// Si no tengo al menos un cheque disponible para emitir
			MCheckReamLine line = this.getNextLineNotUsed();
			if (line == null){
				// Marco esta resma como totalmente emitida
				this.setIsEmitted(true);
			}
			else{
				this.setIsEmitted(false);
			}
			this.saveEx();
			
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
		
	}
	
	
}
