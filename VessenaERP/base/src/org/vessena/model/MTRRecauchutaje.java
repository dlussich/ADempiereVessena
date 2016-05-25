/**
 * 
 */
package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.MDocType;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.Env;

/**
 * @author gbrust
 *
 */
public class MTRRecauchutaje extends X_UY_TR_Recauchutaje implements DocAction {

	
	private static final long serialVersionUID = 3280879373015621382L;
	
	private String processMsg = null;
	private boolean justPrepared = false;

	
	public MTRRecauchutaje(Properties ctx, int UY_TR_Recauchutaje_ID,
			String trxName) {
		super(ctx, UY_TR_Recauchutaje_ID, trxName);		
	}

	
	public MTRRecauchutaje(Properties ctx, ResultSet rs, String trxName) {
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
		
		MTRTire tire  = new MTRTire(getCtx(),this.getUY_TR_Tire_ID_Aux(),get_TrxName());
		
		// Valido que este neumatico no haya cambiado de estado
		if (tire.getEstadoActual().equalsIgnoreCase(X_UY_TR_Tire.ESTADOACTUAL_RECAUCHUTADO)){
			this.processMsg = "Este Neumatico ya fue Recauchutado en otra transacción.";
			return DocAction.STATUS_Invalid;
		}
		else if (tire.getEstadoActual().equalsIgnoreCase(X_UY_TR_Tire.ESTADOACTUAL_BAJA)){
			this.processMsg = "Este Neumatico fue dado de Baja en otra transacción.";
			return DocAction.STATUS_Invalid;
		}

		// Actualizo datos del neumatico
		tire.setQtyRecauchutaje(Env.ONE);
		tire.setEstadoActual(X_UY_TR_Tire.ESTADOACTUAL_RECAUCHUTADO);
		tire.setPrice2(this.getAmount());
		tire.setTireType(this.getTireType());
		//OpenUp. Nicolas Sarlabos. 28/08/2015. #4675.
		tire.setC_BPartner_ID_P(this.getC_BPartner_ID());
		tire.setDateOperation(this.getDateTrx());
		tire.setC_Currency_ID_2(this.getC_Currency_ID());
		tire.setUY_TR_Recauchutaje_ID(this.get_ID());
		//Fin OpenUp.
		tire.saveEx();
		
		this.setUY_TR_Tire_ID(tire.get_ID());
		
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
		
		MTRTire tire  = (MTRTire) this.getUY_TR_Tire();
		
		BigDecimal cantRec = tire.getQtyRecauchutaje().subtract(new BigDecimal(1));
		tire.setQtyRecauchutaje(cantRec);		
		
		if(tire.getEstadoActual().equals(X_UY_TR_Tire.ESTADOACTUAL_RECAUCHUTADO)){
			tire.setEstadoActual(X_UY_TR_Tire.ESTADOACTUAL_NUEVO);		
			
			tire.setQtyKm2(0);
		
		}
		
		tire.saveEx();
		
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
	public BigDecimal getApprovalAmt() {
		// TODO Auto-generated method stub
		return null;
	}

}
