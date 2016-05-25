/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 18/10/2013
 */
package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;

/**
 * org.openup.model - MTTBoxLoad
 * OpenUp Ltda. Issue #1173 
 * Description: Modelo de control de caja en Tracking.
 * @author Gabriel Vila - 18/10/2013
 * @see
 */
public class MTTBoxLoad extends X_UY_TT_BoxLoad implements DocAction {

	private static final long serialVersionUID = -3189887661654796203L;

	private String processMsg = null;
	private boolean justPrepared = false;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_TT_BoxLoad_ID
	 * @param trxName
	 */
	public MTTBoxLoad(Properties ctx, int UY_TT_BoxLoad_ID, String trxName) {
		super(ctx, UY_TT_BoxLoad_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTTBoxLoad(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
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

		try {
		
			MTTBox box =(MTTBox)this.getUY_TT_Box();
			
			box.setQtyCount(0);
			box.setIsComplete(false);
			box.saveEx();
			
			// Limpio cuentas de caja
			String action = " delete from uy_tt_boxcard where uy_tt_box_id = " + box.get_ID();
			DB.executeUpdateEx(action, get_TrxName());
			
			action = " update uy_tt_card set uy_tt_box_id = null, locatorvalue = null  where uy_tt_box_id = " + box.get_ID();
			DB.executeUpdateEx(action, get_TrxName());
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}

		this.setDocAction(DocAction.ACTION_Complete);
		this.setDocStatus(DocumentEngine.STATUS_Applied);
		
		return true;

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

		// Verifico si tengo al menos una cuenta escaneada, sino tengo no dejo completar.
		List<MTTBoxLoadCard> cards = this.getScanCards();
		if ((cards == null) || (cards.size() <= 0)){
			this.processMsg = "Para Completar se debe escanear al menos una Cuenta.";
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File createPDF() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getProcessMsg() {
		// TODO Auto-generated method stub
		return null;
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

	/***
	 * Obtiene y retona lista de Cuenta Escaneadas asociadas a este modelo.
	 * OpenUp Ltda. Issue #1173 
	 * @author Gabriel Vila - 03/09/2013
	 * @see
	 * @return
	 */
	public List<MTTBoxLoadCard> getScanCards(){
		
		String whereClause = X_UY_TT_BoxLoadCard.COLUMNNAME_UY_TT_BoxLoad_ID + "=" + this.get_ID();
		
		List<MTTBoxLoadCard> lines = new Query(getCtx(), I_UY_TT_BoxLoadCard.Table_Name, whereClause, get_TrxName())
		.list();
		
		return lines;
		
	}

}
