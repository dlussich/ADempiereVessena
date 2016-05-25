/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 09/05/2014
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
import org.compiere.util.DB;


/**
 * org.openup.model - MTRTruckAssociation
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Gabriel Vila - 09/05/2014
 * @see
 */
public class MTRTruckAssociation extends X_UY_TR_TruckAssociation implements DocAction {

	private String processMsg = null;
	private boolean justPrepared = false;
	
	private static final long serialVersionUID = 5608815912482247508L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_TR_TruckAssociation_ID
	 * @param trxName
	 */
	public MTRTruckAssociation(Properties ctx, int UY_TR_TruckAssociation_ID,
			String trxName) {
		super(ctx, UY_TR_TruckAssociation_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTRTruckAssociation(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#processIt(java.lang.String)
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 09/05/2014
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
	 * @author Gabriel Vila - 09/05/2014
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
	 * @author Gabriel Vila - 09/05/2014
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
	 * @author Gabriel Vila - 09/05/2014
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
	 * @author Gabriel Vila - 09/05/2014
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
	 * @author Gabriel Vila - 09/05/2014
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
	 * @author Gabriel Vila - 09/05/2014
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
	 * @author Gabriel Vila - 09/05/2014
	 * @see
	 * @return
	 */
	@Override
	public String completeIt() {

		//Re-Check
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


		MTRTruck truck1 = (MTRTruck)this.getUY_TR_Truck();
		MTRTruck truck2 = null;
		
		// Valido que en la asociacion haya un vehiculo contenedor de carga y otro no.
		if (this.getUY_TR_Truck_ID_New() > 0){
			truck2 = new MTRTruck(getCtx(), this.getUY_TR_Truck_ID_New(), get_TrxName());
			MTRTruckType type1 = (MTRTruckType)truck1.getUY_TR_TruckType();
			MTRTruckType type2 = (MTRTruckType)truck2.getUY_TR_TruckType();
			if (type1.isContainer() && type2.isContainer()){
				this.processMsg = "No se puede asociar dos vehiculos contenedores de carga.";
				return DocAction.STATUS_Invalid;
			}
			else if (!type1.isContainer() && !type2.isContainer()){
				this.processMsg = "No se puede asociar dos vehiculos NO contenedores de carga.";
				return DocAction.STATUS_Invalid;
			}
		}
		
		// Primero proceso asociacion desde el punto de vista del vehiculo seleccionado
		if (truck1.getUY_TR_Truck_ID_2() > 0){
			// Desasocio el viejo
			DB.executeUpdateEx(" update uy_tr_truck set uy_tr_truck_id_2 = null where uy_tr_truck_id =" + truck1.getUY_TR_Truck_ID_2(), get_TrxName());
		}
		
		// Si no asocie nuevo vehiculo
		if (this.getUY_TR_Truck_ID_New() <= 0){
			// Dejo vehiculo sin nada asociado
			DB.executeUpdateEx(" update uy_tr_truck set uy_tr_truck_id_2 = null where uy_tr_truck_id =" + truck1.get_ID(), get_TrxName());
		}
		else{
			// Asocio vehiculos en ambos sentidos
			truck1.setUY_TR_Truck_ID_2(truck2.get_ID());
			truck1.saveEx();
			
			// Si el nuevo vehiculo estaba asociado a un tercero, debo desasociar el tercero
			if (truck2.getUY_TR_Truck_ID_2() > 0){
				DB.executeUpdateEx(" update uy_tr_truck set uy_tr_truck_id_2 = null where uy_tr_truck_id =" + truck2.getUY_TR_Truck_ID_2(), get_TrxName());
			}
			
			truck2.setUY_TR_Truck_ID_2(truck1.get_ID());
			truck2.saveEx();
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
	 * @author Gabriel Vila - 09/05/2014
	 * @see
	 * @return
	 */
	@Override
	public boolean voidIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#closeIt()
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 09/05/2014
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
	 * @author Gabriel Vila - 09/05/2014
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
	 * @author Gabriel Vila - 09/05/2014
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
	 * @author Gabriel Vila - 09/05/2014
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
	 * @author Gabriel Vila - 09/05/2014
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
	 * @author Gabriel Vila - 09/05/2014
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
	 * @author Gabriel Vila - 09/05/2014
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
	 * @author Gabriel Vila - 09/05/2014
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
	 * @author Gabriel Vila - 09/05/2014
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
	 * @author Gabriel Vila - 09/05/2014
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
	 * @author Gabriel Vila - 09/05/2014
	 * @see
	 * @return
	 */
	@Override
	public BigDecimal getApprovalAmt() {
		// TODO Auto-generated method stub
		return null;
	}

}
