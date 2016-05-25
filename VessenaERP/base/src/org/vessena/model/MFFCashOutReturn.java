/**
 * 
 */
package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MDocType;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.Env;

/**
 * @author Nicolas
 *
 */
public class MFFCashOutReturn extends X_UY_FF_CashOutReturn implements DocAction{
	
	private String processMsg = null;
	private boolean justPrepared = false;

	/**
	 * 
	 */
	private static final long serialVersionUID = 2719850152677168234L;

	/**
	 * @param ctx
	 * @param UY_FF_CashOutReturn_ID
	 * @param trxName
	 */
	public MFFCashOutReturn(Properties ctx, int UY_FF_CashOutReturn_ID,
			String trxName) {
		super(ctx, UY_FF_CashOutReturn_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		if(newRecord || (!newRecord && is_ValueChanged("Description"))) {

			if(this.getDescription()!=null) this.setDescription(this.getDescription().toUpperCase());
		}

		if(newRecord || (!newRecord &&is_ValueChanged("UY_FF_CashOut_ID"))){

			if(this.getUY_FF_CashOut_ID() > 0){

				MFFCashOut cash = new MFFCashOut(getCtx(),this.getUY_FF_CashOut_ID(),get_TrxName());
				this.setAmount(cash.getGrandTotal());
				this.setUY_FF_Branch_ID(cash.getUY_FF_Branch_ID());
				this.setUY_POSection_ID(cash.getUY_POSection_ID());
				this.setC_Currency_ID(cash.getC_Currency_ID());

			}

		}	
		
		return true;
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MFFCashOutReturn(Properties ctx, ResultSet rs, String trxName) {
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

	public boolean requestIt() {
		
		// Refresco atributos
		this.setProcessed(true);
		this.setDocAction(DocAction.ACTION_Approve);
		this.setDocStatus(DocumentEngine.STATUS_Requested);
					
		return true;
	}
	
	@Override
	public boolean approveIt() {
			
		// Refresco atributos
		this.setProcessed(true);
		this.setDocAction(DocAction.ACTION_Complete);
		this.setDocStatus(DocumentEngine.STATUS_Approved);
		
		return true;
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
		
		MFFTracking track = null;
		
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
		
		this.updateReplenish(); //se actualiza documento de reposicion de FF		
				
		// Timing After Complete		
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (this.processMsg != null)
			return DocAction.STATUS_Invalid;
		
		//registro trazabilidad
		MFFCashOut cash = new MFFCashOut(getCtx(),this.getUY_FF_CashOut_ID(),get_TrxName()); //instancio salida de FF
		
		track = new MFFTracking(getCtx(),0,get_TrxName());
		track.setUY_FF_CashOut_ID(cash.get_ID());
		track.setDateTrx(new Timestamp(System.currentTimeMillis()));
		track.setAD_User_ID(Env.getAD_User_ID(Env.getCtx()));
		track.setDescription("Se completa la devolucion de fondo fijo N° " + this.getDocumentNo());
		track.saveEx();
		
		//anulo la salida de fondo fijo
		cash.isVoidAuto = true; //seteo variable de anulacion automatica
		cash.msgVoidAuto = "Anulado automaticamente por devolucion N° " + this.getDocumentNo();
		
		try {
			if(!cash.processIt(DocumentEngine.ACTION_Void)){
				throw new AdempiereException("Error al anular la salida de fondo fijo N° " + cash.getDocumentNo());
			}
		} catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		}
		cash.saveEx();

		// Refresco atributos
		this.setDocAction(DocAction.ACTION_None);
		this.setDocStatus(DocumentEngine.STATUS_Completed);
		this.setProcessing(false);
		this.setProcessed(true);
		
		return DocAction.STATUS_Completed;
	}
	
	/***
	 * Metodo que actualiza el documento de reposicion de FF, eliminando la linea de salida de caja o insertando linea de devolucion.
	 * OpenUp Ltda. Issue #1426
	 * @author Nicolas Sarlabos - 02/01/2014
	 * @see
	 * @return
	 */	
	private void updateReplenish() {

		//obtengo documento de reposicion en curso para la sucursal y moneda actual
		MFFReplenish replenish = MFFReplenish.forBranchCurrency(getCtx(), this.getUY_FF_Branch_ID(), this.getC_Currency_ID(), get_TrxName());

		if(replenish==null) throw new AdempiereException("No se encontro documento de reposicion de fondo fijo en curso, por favor verifique");

		MFFCashOut cash = new MFFCashOut(getCtx(),this.getUY_FF_CashOut_ID(),get_TrxName()); //obtengo la salida asociada a la devolucion actual

		//obtengo la linea de reposicion asociada a la salida de FF, para poder actualizarla
		MFFReplenishLine rline = MFFReplenishLine.forTableReplenishLine(getCtx(), cash.getC_DocType_ID(), X_UY_FF_CashOut.Table_ID, 
				cash.get_ID(), 0, get_TrxName());

		if(rline!=null){
			
			//si el doc de reposicion en curso es el mismo que el de la salida de FF elimino la linea. Si no, inserto la linea en la reposicion en curso
			if(replenish.get_ID() == rline.getUY_FF_Replenish_ID()){
				
				rline.deleteEx(true); //elimino linea de salida de FF	
				
			} else {
							
				MFFReplenishLine line = new MFFReplenishLine(getCtx(),0,get_TrxName());
				line.setUY_FF_Replenish_ID(replenish.get_ID());
				line.setDateTrx(this.getDateTrx());
				line.setRecord_ID(this.get_ID());
				line.setAD_Table_ID(I_UY_FF_CashOutReturn.Table_ID);
				line.setC_DocType_ID(this.getC_DocType_ID());
				line.setDocumentNo(this.getDocumentNo());
				line.setChargeName(MFFCashOut.getChargeNames(cash.get_ID()));
				line.setAD_User_ID(this.getAD_User_ID());
				line.setAmount(this.getAmount().negate());
				line.setDescription(this.getDescription());
				line.setApprovedBy(cash.getApprovedBy());
				line.saveEx();				
			}	

		} else throw new AdempiereException("Imposible actualizar documento de reposicion, no se encontro la linea de reposicion para la salida de caja N° " + cash.getDocumentNo());

	}

	@Override
	public boolean voidIt() {
		// Before Void
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_VOID);
		if (this.processMsg != null)
			return false;
		
		//se actualiza linea de doc de reposicion
		//obtengo linea de reposicion 
		MFFReplenishLine rline = MFFReplenishLine.forTableReplenishLine(getCtx(), this.getC_DocType_ID(), X_UY_FF_CashOutReturn.Table_ID, this.get_ID(), 0, get_TrxName());
		
		if(rline!=null){
			
			MFFReplenish replenish = new MFFReplenish(getCtx(),rline.getUY_FF_Replenish_ID(),get_TrxName()); //instancio cabezal de doc de reposicion
			
			if(!replenish.getDocStatus().equalsIgnoreCase("CO")) { //solo si la reposicion no esta completa puedo anular y actualizar
				
				MFFCashOut salida = new MFFCashOut(getCtx(),this.getUY_FF_CashOut_ID(),get_TrxName()); //instancio la salida de caja
				
				rline.setDateTrx(salida.getDateTrx());
				rline.setRecord_ID(salida.get_ID());
				rline.setAD_Table_ID(I_UY_FF_CashOut.Table_ID);
				rline.setC_DocType_ID(salida.getC_DocType_ID());
				rline.setDocumentNo(salida.getDocumentNo());
				rline.setChargeName(MFFCashOut.getChargeNames(salida.get_ID()));
				rline.setAD_User_ID(salida.getAD_User_ID());
				rline.setAmount(salida.getGrandTotal());
				rline.setDescription(salida.getDescription());
				rline.setApprovedBy(salida.getApprovedBy());
				rline.saveEx();	
				
			} else throw new AdempiereException("Imposible anular por estar la devolucion en el documento de reposicion N° " + 					
					replenish.getDocumentNo() + " en estado completo");						
		}	
			
		// After Void
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_VOID);
		if (this.processMsg != null)
			return false;		
		
		//registro trazabilidad
		MFFCashOut cash = new MFFCashOut(getCtx(),this.getUY_FF_CashOut_ID(),get_TrxName()); //instancio salida de FF
		
		MFFTracking track = new MFFTracking(getCtx(),0,get_TrxName());
		track.setUY_FF_CashOut_ID(cash.get_ID());
		track.setDateTrx(new Timestamp(System.currentTimeMillis()));
		track.setAD_User_ID(Env.getAD_User_ID(Env.getCtx()));
		track.setDescription("Se anula la devolucion de fondo fijo N° " + this.getDocumentNo());
		track.saveEx();	
		
		setProcessed(true);
		setDocStatus(DOCSTATUS_Voided);
		setDocAction(DOCACTION_None);
		
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
