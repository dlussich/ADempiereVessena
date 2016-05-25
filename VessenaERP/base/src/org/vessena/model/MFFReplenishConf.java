/**
 * 
 */
package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.compiere.model.MUser;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.Env;

/**
 * @author Nicolas
 *
 */
public class MFFReplenishConf extends X_UY_FF_ReplenishConf implements DocAction{
	
	private String processMsg = null;
	private boolean justPrepared = false;

	/**
	 * 
	 */
	private static final long serialVersionUID = -3011737251775824326L;

	/**
	 * @param ctx
	 * @param UY_FF_ReplenishConf_ID
	 * @param trxName
	 */
	public MFFReplenishConf(Properties ctx, int UY_FF_ReplenishConf_ID,
			String trxName) {
		super(ctx, UY_FF_ReplenishConf_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MFFReplenishConf(Properties ctx, ResultSet rs, String trxName) {
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
		
		this.updateReplenish(); //actualiza el documento de reposicion	
		
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
	 * Metodo que actualiza el documento de reposicion asociado, insertando una linea para aumentar su saldo. 
	 * OpenUp Ltda. Issue #1427
	 * @author Nicolas Sarlabos - 02/01/2014
	 * @see
	 * @return
	 */	
	private void updateReplenish() {
		
		MFFReplenish replenish = new MFFReplenish(getCtx(),this.getUY_FF_Replenish_ID(),get_TrxName()); //obtengo documento de reposicion
		
		MUser user = new MUser(getCtx(),Env.getAD_User_ID(Env.getCtx()),get_TrxName());
		
		MFFReplenishLine line = new MFFReplenishLine(getCtx(),0,get_TrxName());
		line.setUY_FF_Replenish_ID(replenish.get_ID());
		line.setDateTrx(new Timestamp(System.currentTimeMillis()));
		line.setRecord_ID(this.get_ID());
		line.setAD_Table_ID(I_UY_FF_ReplenishConf.Table_ID);
		line.setC_DocType_ID(this.getC_DocType_ID());
		line.setDocumentNo(this.getDocumentNo());
		line.setChargeName("--");
		line.setAD_User_ID(user.get_ID());
		line.setAmount(this.getAmount().negate());
		line.setDescription("GENERADO AUTOMATICAMENTE POR DOCUMENTO DE CONFIRMACION N° " + this.getDocumentNo());
		
		if(user.getDescription()!=null && !user.getDescription().equalsIgnoreCase("")){
			line.setApprovedBy(user.getDescription());			
		} else line.setApprovedBy(user.getName());
		
		line.saveEx();		
		
	}

	@Override
	public boolean voidIt() {
		// TODO Auto-generated method stub
		return false;
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
