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
import org.compiere.model.MDocType;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;

/**
 * @author gbrust
 *
 */
public class MTRTransOrder extends X_UY_TR_TransOrder implements DocAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4294715742186500803L;
	
	private String processMsg = null;
	private boolean justPrepared = false;
	

	/**
	 * @param ctx
	 * @param UY_TR_TransOrder_ID
	 * @param trxName
	 */
	public MTRTransOrder(Properties ctx, int UY_TR_TransOrder_ID, String trxName) {
		super(ctx, UY_TR_TransOrder_ID, trxName);		
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTRTransOrder(Properties ctx, ResultSet rs, String trxName) {
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
		return false;
	}

	
	@Override
	public boolean applyIt() {
		
		try {

			// Debo tener lineas en este documento
			if ((this.getLines().size() <= 0) && (!this.isLastre())){
				throw new AdempiereException("Debe seleccionar al menos un CRT");
			}
			
			//No se puede aplicar una órden de transporte si el remolque seleccionado está en otra órden de transporte aplicada.
			/*
			if(this.existeRemolqueEnOTAplicada()){
				String message = "El vehiculo contenedor seleccionado esta siendo utilizado en otro Documento de Carga.";
				if (this.tOrderAux != null){
					message = "El vehiculo contenedor seleccionado esta siendo utilizado en el Documento de Carga : " + this.tOrderAux.getDocumentNo();
				}
				throw new AdempiereException(message);		
			}
			*/
			
			// Si este documento se ingrso manualmente y no vino por otro lado
			if (this.isManual()){
				
				// Genero y completo seguimiento de carga indicando remolque cargado
				MTRLoadMonitor monitor = new MTRLoadMonitor(getCtx(), 0, get_TrxName());
				monitor.setDateTrx(this.getDateTrx());
				monitor.setDescription(this.getDescription());
				monitor.setC_DocType_ID(MDocType.forValue(getCtx(), "gestcarga", null).get_ID());
				monitor.setUY_TR_LoadStatus_ID(MTRLoadStatus.forValue(getCtx(), "cargado", null).get_ID());
				monitor.setUY_TR_TransOrder_ID(this.get_ID());
				
				monitor.setRemolque_ID(this.getRemolque_ID());
				monitor.setUY_TR_Truck_ID(this.getRemolque_ID());
				monitor.setUY_TR_TruckType_ID(this.getUY_TR_TruckType_ID());
				
				monitor.setOnMove(true);
				monitor.setIsWarehouseRequired(false);
				if (this.getTractor_ID() > 0){
					monitor.setTractor_ID(this.getTractor_ID());
					monitor.setUY_TR_Truck_ID_2(this.getTractor_ID());
					monitor.setUY_TR_TruckType_ID_2(this.getUY_TR_TruckType_ID_2());
				}
				if (this.getUY_TR_Driver_ID() > 0){
					monitor.setUY_TR_Driver_ID(this.getUY_TR_Driver_ID());
				}
				monitor.setIsBeforeLoad(false);
				monitor.setIsTrasbordo(false);
				monitor.setProcessed(true);
				monitor.saveEx();
				
				// Lineas del seguimiento se cargan en el AfterSave del cabezal del seguimiento (MTRLoadMonitor)

				// Completo seguimiento de carga
				if (!monitor.processIt(DocumentEngine.ACTION_Complete)){
					throw new AdempiereException(monitor.getProcessMsg());
				}

			}
			
			this.setDocStatus(DocumentEngine.STATUS_Applied);
			this.setDocAction(DocAction.ACTION_None);

		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		
		
		return true;
	}
	
	@Override
	public boolean rejectIt() {		
		return false;
	}

	
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

		// No dejo completar una orden de transporte sin lineas
		List<MTRTransOrderLine> lines = this.getLines();
		if ((lines == null) || (lines.size() <= 0)){
			if (!this.isLastre()){
				this.processMsg = "No es posible completar la Orden de Transporte ya que la misma no tiene lineas.";
				return DocAction.STATUS_Invalid;	
			}
		}
		
		//elimino lineas de distribucion en expedientes
		DB.executeUpdateEx("delete from uy_tr_tripqty where uy_tr_transorder_id = " + this.get_ID(), get_TrxName());
				
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
	 * OpenUp. Guillermo Brust. 07/01/2014. ISSUE #1624
	 * Método que retorna las lineas de este cabezal, que en realidad son los expedientes que tiene esta orden de transporte.
	 * 
	 * */
	public List<MTRTransOrderLine> getLines(){
		
		String whereClause = X_UY_TR_TransOrderLine.COLUMNNAME_UY_TR_TransOrder_ID + " = " + this.get_ID(); 
							
		
		return new Query(this.getCtx(), I_UY_TR_TransOrderLine.Table_Name, whereClause, this.get_TrxName()).list();		
	}
	

	/***
	 * Obtiene y retorna lineas de este modelo asociadas a un documento de movimiento de carga recibido.
	 * OpenUp Ltda. Issue #1405 
	 * @author Gabriel Vila - 23/12/2014
	 * @see
	 * @param uyLoadMonitorID
	 * @return
	 */
	public List<MTRTransOrderLine> getLinesForLoadMonitor(int uyLoadMonitorID){
		
		String whereClause = X_UY_TR_TransOrderLine.COLUMNNAME_UY_TR_TransOrder_ID + " = " + this.get_ID() +
				" and uy_tr_loadmonitorline_id in (select uy_tr_loadmonitorline_id from uy_tr_loadmonitorline " +
				" where uy_tr_loadmonitor_id =" + uyLoadMonitorID + " and IsSelected ='Y') ";
							
		
		return new Query(this.getCtx(), I_UY_TR_TransOrderLine.Table_Name, whereClause, this.get_TrxName()).list();		
	} 
	
	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		// No hago nada si estoy actualizando datos cuando esta completa la orden.
		if (this.getDocStatus() != null){
			if (this.getDocStatus().equalsIgnoreCase(STATUS_Completed)){
				return true;
			}
		}
		
		// Dependiendo de los tipo de vehiculo, seteo valores de remolque y tractor
		boolean haveRemolque = false, haveTractor = false;
		if (this.getUY_TR_TruckType_ID() > 0){
			MTRTruckType truckType = (MTRTruckType)this.getUY_TR_TruckType();
			if (truckType.isContainer()){
				this.setRemolque_ID(this.getUY_TR_Truck_ID());
				haveRemolque = true;
			}
			else{
				this.setTractor_ID(this.getUY_TR_Truck_ID());
				haveTractor = true;
			}
		}

		if (this.getUY_TR_TruckType_ID_2() > 0){
			MTRTruckType truckType = new MTRTruckType(getCtx(), this.getUY_TR_TruckType_ID_2(), null);
			if (truckType.isContainer()){
				if (haveRemolque){
					log.saveError(null, "No es posible seleccionar dos vehiculos contenedores de carga.");
					return false;
				}
				this.setRemolque_ID(this.getUY_TR_Truck_ID_Aux());
				haveRemolque = true;
			}
			else{
				if (haveTractor){
					log.saveError(null, "Debe seleccionar un vehiculo contenedor de carga.");
					return false;
				}
				this.setTractor_ID(this.getUY_TR_Truck_ID_Aux());
				haveTractor = true;
			}
		}
		
		// Si no tengo remolque y no es lastre, aviso ya que para un movimiento de carga debo tener un vehiculo contenedor de carga
		if (!haveRemolque && !this.isLastre()){
			log.saveError(null, "Debe seleccionar un vehiculo contenedor de carga.");
			return false;
		}
		
		// Si no tengo tractor ahora me aseguro que el campo quede en null.
		if (!haveTractor){
			if (!newRecord){
				DB.executeUpdateEx(" update uy_tr_transorder set tractor_id = null where uy_tr_transorder_id =" + this.get_ID(), get_TrxName());	
			}
		}
		
		return true;
	}

	@Override
	protected boolean beforeDelete() {
		
		List<MTRTransOrderLine> lines = this.getLines();
		
		for(MTRTransOrderLine oLine : lines){
			
			oLine.deleteEx(true);			
			
		}	
		
		return true;
	}
	

	@Override
	public boolean voidIt() {
		// Before Void
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_VOID);
		if (this.processMsg != null)
			return false;
		
		//elimino lineas de distribucion en expedientes
		DB.executeUpdateEx("delete from uy_tr_tripqty where uy_tr_transorder_id = " + this.get_ID(), get_TrxName());
		
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
		return false;
	}

	
	@Override
	public boolean reverseCorrectIt() {		
		return false;
	}

	
	@Override
	public boolean reverseAccrualIt() {		
		return false;
	}

	
	@Override
	public boolean reActivateIt() {		
		return false;
	}

	
	@Override
	public String getSummary() {		
		return null;
	}

	
	@Override
	public String getDocumentInfo() {
		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		return dt.getName() + " " + getDocumentNo();
	}

	
	@Override
	public File createPDF() {		
		return null;
	}

	
	@Override
	public String getProcessMsg() {		
		return null;
	}

	
	@Override
	public int getDoc_User_ID() {		
		return 0;
	}

	
	@Override
	public BigDecimal getApprovalAmt() {		
		return null;
	}

	
	/***
	 * Obtiene y retorna modelo segun id de remolque recibido y estado de documento aplicado.
	 * OpenUp Ltda. Issue #1405 
	 * @author Gabriel Vila - 10/12/2014
	 * @see
	 * @param ctx
	 * @param remolqueID
	 * @param trxName
	 * @return
	 */
	public static MTRTransOrder forRemolqueIDApply(Properties ctx, int remolqueID, String trxName){
		
		String whereClause = X_UY_TR_TransOrder.COLUMNNAME_Remolque_ID + "=" + remolqueID +
				" AND " + X_UY_TR_TransOrder.COLUMNNAME_DocStatus + "='AY' ";
		
		MTRTransOrder model = new Query(ctx, I_UY_TR_TransOrder.Table_Name, whereClause, trxName)
		.first();
		
		return model;
		
	}

	
	/***
	 * Obtiene y retorna modelo segun id de tractor recibido y estado de documento aplicado.
	 * OpenUp Ltda. Issue #1405 
	 * @author Gabriel Vila - 30/12/2014
	 * @see
	 * @param ctx
	 * @param tractorID
	 * @param trxName
	 * @return
	 */
	public static MTRTransOrder forTractorIDApply(Properties ctx, int tractorID, String trxName){
		
		String whereClause = X_UY_TR_TransOrder.COLUMNNAME_Tractor_ID + "=" + tractorID +
				" AND " + X_UY_TR_TransOrder.COLUMNNAME_DocStatus + "='AY' ";
		
		MTRTransOrder model = new Query(ctx, I_UY_TR_TransOrder.Table_Name, whereClause, trxName)
		.first();
		
		return model;
		
	}

	/***
	 * Verifica si todos los CRTS de esta orden de transporte fueron entregados en destino.
	 * OpenUp Ltda. Issue #1405 
	 * @author Gabriel Vila - 15/12/2014
	 * @see
	 * @return
	 */
	public boolean isAllCrtsDelivered() {

		boolean result = true;
		
		try {
			
			List<MTRTransOrderLine> lines = this.getLines();
			for (MTRTransOrderLine line: lines){
				if (!line.isDelivered()){
					result = false;
				}
			}
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		
		return result;
	}

	
	/***
	 * Verifica si todos los CRTS de esta orden de transporte fueron entregados en destino o bajados.
	 * OpenUp Ltda. Issue #1405 
	 * @author Gabriel Vila - 15/12/2014
	 * @see
	 * @return
	 */
	public boolean isAllCrtsDeliveredOrEnded() {

		boolean result = true;
		
		try {
			
			List<MTRTransOrderLine> lines = this.getLines();
			for (MTRTransOrderLine line: lines){
				if (!line.isDelivered()) {
					if (!line.isEndTrackStatus()){
						result = false;	
					}
				}
			}
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		
		return result;
	}

	
	/***
	 * Verifica si todos los CRTS de esta orde de transporte tienen la marca de tracking finalizado.
	 * OpenUp Ltda. Issue #1405 
	 * @author Gabriel Vila - 15/12/2014
	 * @see
	 * @return
	 */
	public boolean isAllCrtsEndTrack() {

		boolean result = true;
		
		try {
			
			List<MTRTransOrderLine> lines = this.getLines();
			for (MTRTransOrderLine line: lines){
				if (!line.isEndTrackStatus()){
					result = false;
				}
			}
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		
		return result;
	}
	
	
	/***
	 * ELimina lineas de ordenes de transporte asociadas a una determinada carga
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 08/01/2015
	 * @see
	 * @param uyLoadMonitorID
	 */
	public void deleteLinesForMonitor(int uyLoadMonitorID){

		try {

			// Elimino lineaas de ordenes de transporte creadas por este documento al completarse anteriormente
			// Si la orden de transporte tiene MIC emitido elimino las lineas por el modelo
			if (this.getUY_TR_Mic_ID() > 0){
				
				List<MTRTransOrderLine> lines = this.getLinesForLoadMonitor(uyLoadMonitorID);
				for (MTRTransOrderLine line: lines){
					line.deleteEx(true);
				}
				
			}
			else{
				// Borro sin modelo ya que no importa porque no tiene mic
				String action = " delete from uy_tr_transorderline " +
								" where uy_tr_transorder_id =" + this.get_ID() +
								" and uy_tr_crt_id in " +
				                " (select uy_tr_crt_id from uy_tr_loadmonitorline " +
				                " where uy_tr_loadmonitor_id =" + uyLoadMonitorID + 
				                " and isselected = 'Y')";
				
				DB.executeUpdateEx(action, get_TrxName());
			}
			
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		
	}
}
