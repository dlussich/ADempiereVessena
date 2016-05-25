/**
 * 
 */
package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MDocType;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * @author Nicolas
 *
 */
public class MTRTireDrop extends X_UY_TR_TireDrop implements DocAction{
	
	private String processMsg = null;
	private boolean justPrepared = false;

	/**
	 * 
	 */
	private static final long serialVersionUID = -2264946837166114382L;

	/**
	 * @param ctx
	 * @param UY_TR_TireDrop_ID
	 * @param trxName
	 */
	public MTRTireDrop(Properties ctx, int UY_TR_TireDrop_ID, String trxName) {
		super(ctx, UY_TR_TireDrop_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTRTireDrop(Properties ctx, ResultSet rs, String trxName) {
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
		
		
		this.setDateAcct(this.getDateTrx());
		
		MTRTire tire = new MTRTire(getCtx(),this.getUY_TR_Tire_ID(),get_TrxName()); //instancio el neumatico
		
		this.validComplete(tire);//realizo validaciones previas
		
		this.setoldstatus(tire.getEstadoActual()); //guardo estado actual
		this.setM_Warehouse_ID(tire.getM_Warehouse_ID()); //guardo almacen actual
		tire.setEstadoActual(X_UY_TR_Tire.ESTADOACTUAL_BAJA); //seteo estado BAJA
		tire.setIsActive(false); //marco neumatico como inactivo
		
		// Seteo costo/km final de este neumatico.
		BigDecimal costo = Env.ZERO;
		if (tire.getPrice() != null) costo = costo.add(tire.getPrice());
		if (tire.getPrice2() != null) costo = costo.add(tire.getPrice2());
		
		// Si tengo km. casco
		if (tire.getQtyKm5() > 0){
			costo = costo.divide(new BigDecimal(tire.getQtyKm5()), 2, RoundingMode.HALF_UP);
		}
		tire.setPriceCostFinal(costo);
		
		
		tire.saveEx();
		
		//elimino el almacen del neumatico
		DB.executeUpdate("UPDATE UY_TR_Tire SET m_warehouse_id = null WHERE UY_TR_Tire_ID = " + tire.get_ID(), this.get_TrxName());
					
		// Timing After Complete		
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (this.processMsg != null)
			return DocAction.STATUS_Invalid;

		// Refresco atributos
		this.setDocAction(DocAction.ACTION_Void);
		this.setDocStatus(DocumentEngine.STATUS_Completed);
		this.setProcessing(false);
		this.setProcessed(true);
		
		return DocAction.STATUS_Completed;
	}

	/***
	 * OpenUp. Nicolas Sarlabos. 07/04/2014. ISSUE#1629
	 * Metodo que realiza validaciones previas a completar el documento.
	 * @param order 
	 * @param trip 
	 * 
	 * */
	private void validComplete(MTRTire tire) {
		
		String sql = "";
		
		//si el neumatico esta colocado en un vehiculo
		if(tire.getUY_TR_Truck_ID() > 0){
			
			MTRTruck truck = new MTRTruck(getCtx(),tire.getUY_TR_Truck_ID(),get_TrxName());
			
			throw new AdempiereException("No es posible dar de baja el neumatico por estar colocado en el vehiculo " + truck.getTruckNo() + " en la posicion " + tire.getLocatorValue() + 
					". Primero debe quitar el neumatico del vehiculo y colocarlo en un almacen.");
			
		}	
		
		//verifico que el neumatico no este en un documento de cambio en estado aplicado
		sql = "select m.uy_tr_tiremove_id" +
		      " from uy_tr_tiremove m" +
			  " inner join uy_tr_tiremoveline l on m.uy_tr_tiremove_id = l.uy_tr_tiremove_id" +
		      " inner join uy_tr_tiremoveopen o on m.uy_tr_tiremove_id = o.uy_tr_tiremove_id" +
		      " where m.docstatus = 'AY' and (l.uy_tr_tire_id = " + tire.get_ID() + " or o.uy_tr_tire_id = " + tire.get_ID() + ")";
		int moveID = DB.getSQLValueEx(get_TrxName(), sql);
		
		if(moveID > 0){
			
			MTRTireMove move = new MTRTireMove(getCtx(),moveID,get_TrxName());
			
			throw new AdempiereException("No es posible dar de baja el neumatico por estar presente en el cambio de neumatico N° " + move.getDocumentNo() + " en estado aplicado");
			
		}		
		
	}

	@Override
	public boolean voidIt() {

		// Before Void
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_VOID);
		if (this.processMsg != null)
			return false;
		
		MTRTire tire = new MTRTire(getCtx(),this.getUY_TR_Tire_ID(),get_TrxName()); //instancio el neumatico
		
		tire.setEstadoActual(this.getoldstatus()); //seteo su estado anterior a la baja
		tire.setM_Warehouse_ID(this.getM_Warehouse_ID()); //seteo su almacen anterior a la baja
		tire.setIsActive(true); //marco neumatico como activo
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
