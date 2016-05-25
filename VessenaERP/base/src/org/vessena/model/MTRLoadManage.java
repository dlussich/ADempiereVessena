/**
 * 
 */
package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MDocType;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;


/**
 * @author gbrust
 *
 */
public class MTRLoadManage extends X_UY_TR_LoadManage implements DocAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4528205043482356376L;
	
	private String processMsg = null;
	private boolean justPrepared = false;

	/**
	 * @param ctx
	 * @param UY_TR_LoadManage_ID
	 * @param trxName
	 */
	public MTRLoadManage(Properties ctx, int UY_TR_LoadManage_ID, String trxName) {
		super(ctx, UY_TR_LoadManage_ID, trxName);
		
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
				
		
		
		return true;
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTRLoadManage(Properties ctx, ResultSet rs, String trxName) {
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
		this.setDocAction(DocAction.ACTION_Complete);
		this.setDocStatus(DocumentEngine.STATUS_Applied);
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

		
		//OpenUp. Guillermo Brust. 13/12/2013. ISSUE #1625
		this.completar();		
		//Fin OpenUp.
	
		
		// Timing After Complete		
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (this.processMsg != null)
			return DocAction.STATUS_Invalid;

		// Refresco atributos
		this.setDocAction(DocAction.ACTION_None);
		this.setDocStatus(DocumentEngine.STATUS_Completed);
		this.setProcessing(false);
		this.setProcessed(true);
		
		
		//OpenUp. Guillermo Brust. 09/01/2014. ISSUE #1625
		//Luego de completar, guarda trazabilidad en ventana de gestion de expedientes
		this.guardarTrazabilidad();		
		//Fin OpenUp.
		
		return DocAction.STATUS_Completed;
	}
	
	
	
	/***
	 * OpenUp. Guillermo Brust. 13/12/2013. ISSUE #1625
	 * Método que contiene toda la logica aplicar a la hora de completar el documento. 
	 * 
	 * **/
	private void completar(){
		
		//Obtengo las ordenes para este expediente
		List<MTRTransOrder> ordenes = MTRTransOrderLine.getTransOrderForTripID(this.getCtx(), this.get_TrxName(), this.getUY_TR_Trip_ID());		
		
		//Primero chequeamos que algunos campos tengan coherencia en sus datos		
		//Se traen las ordenes de este expediente, en el caso que existe una sola, se setea la misma en el campo de esta tabla, si no existe ninguna, aviso que no hay orden de transporte, y si hay mas de una, aviso que seleccione una.
		if(ordenes.size() != 1) {
			if(this.isOnMove() && this.getUY_TR_TransOrder_ID() == 0){
				throw new AdempiereException("No es posible completar el documento. La carga no puede estar en movimiento, sin estar asociada a una Orden de Transporte.");
			}
		}else {
			this.setUY_TR_TransOrder_ID(((MTRTransOrder) ordenes.get(0)).get_ID());
			this.setIsConsolidated(this.transOrderIsConsolidated(((MTRTransOrder) ordenes.get(0)).get_ID()));			
		}		
		
		if(this.isOnMove() && this.getUY_TR_TransOrder_ID() > 0 && this.getM_Warehouse_ID() > 0) {
			throw new AdempiereException("No es posible completar el documento. La carga no puede estar en movimiento, estar asociada a una Orden de Transporte y estar en un Almacén.");
		}		
		
		if(this.isOnMove() && this.getUY_TR_Truck_ID() == 0) {
			throw new AdempiereException("No es posible completar el documento. La carga no puede estar en movimiento, sin tener un tractor asociado.");
		}
		
		if(this.isOnMove() && this.getUY_TR_Driver_ID() == 0) {
			throw new AdempiereException("No es posible completar el documento. La carga no puede estar en movimiento, sin tener un conductor asociado.");
		}
		
		if(((MTRLoadStatus)this.getUY_TR_LoadStatus()).isWarehouseRequired() && this.getM_Warehouse_ID() == 0){
			throw new AdempiereException("No es posible completar el documento. El estado de la carga indica que debe seleccionar un Almacén.");
		}
				
		//Si tengo guardado el tractor y/o el remolque, lo guardo en la UY_TR_TransOrderLine, es ahi donde guardo el ultimo tractor y el ultimo conductor para este expediente 
		//y tambien guardo si está finalizado o no el expediente segun el estado de la ultima trazabilidad de carga (gestión de carga)
		if(this.getUY_TR_TransOrder_ID() > 0 && this.getUY_TR_Trip_ID() > 0){
			
			MTRTransOrderLine tOrderLine = MTRTransOrderLine.forTransOrderIDAndTripID(this.getCtx(), this.get_TrxName(), this.getUY_TR_TransOrder_ID(), this.getUY_TR_Trip_ID());
			
			if(tOrderLine == null || (tOrderLine != null && tOrderLine.get_ID() == 0)){
				throw new AdempiereException("La órden de transporte seleccionada no se encuentra asociada al expediente. Es posible que se haya modificado la asociacion en la ventana de Órden de Transporte");
				
			}
			
			//Si tengo dato en el campo de tractor lo guardo en el registro de ultimo tractor para este expedienteen la transorderline
			if(this.getUY_TR_Truck_ID() > 0) tOrderLine.setUY_TR_Truck_ID(this.getUY_TR_Truck_ID());
			
			//Si tengo dato en el campo de conductor lo guardo en el registro de ultimo conductor para este expediente en la transorderline
			if(this.getUY_TR_Driver_ID() > 0) tOrderLine.setUY_TR_Driver_ID(this.getUY_TR_Driver_ID());
			
			//Si tiene un estado asociado, verifico si este desasocia la carga de la orden de transporte (Finaliza la carga)
			if(this.getUY_TR_LoadStatus_ID() > 0){
				boolean cargaFinalizada = ((MTRLoadStatus) this.getUY_TR_LoadStatus()).isEndTrackStatus();
				
				tOrderLine.setEndTrackStatus(cargaFinalizada);
				
				MTRLoadStatus status = (MTRLoadStatus)this.getUY_TR_LoadStatus();
				
				if(status.getValue().equalsIgnoreCase("entregado")) tOrderLine.setIsDelivered(true); //marco check de mercaderia entregada
				
				if(this.getM_Warehouse_ID()>0) tOrderLine.setM_Warehouse_ID(this.getM_Warehouse_ID()); //seteo el almacen
			}		
			
			tOrderLine.saveEx();			
		}	

		
		//Acá vamos a controlar primero los casos en que la orden de transporte no es consolidada
		if(!this.isConsolidated()){
			
			//Si tiene algun estado que tiene marcado como que finaliza tracking
			if(this.getUY_TR_LoadStatus().isEndTrackStatus()){
				
				//Solo si se ingresa una orden de transporte, completo la misma
				if(this.getUY_TR_TransOrder_ID() > 0){
					
					MTRTransOrder torder = (MTRTransOrder) this.getUY_TR_TransOrder();
					
					try {				
						if (!torder.processIt(DocumentEngine.ACTION_Complete)){
							throw new AdempiereException(torder.getProcessMsg());
						}				
					} catch (Exception e) {
						throw new AdempiereException(e.getMessage());
					}					
										
					//Se actualiza el campo del estado de la orden en la pestana de trazabilidad de orden de transporte en la ventana de gestion de expediente 
					MTRTripOrder tripOrder = MTRTripOrder.forTripIDAndOrderID(this.getCtx(), this.getUY_TR_Trip_ID(), this.getUY_TR_TransOrder_ID(), this.get_TrxName());
					
					if(tripOrder != null && tripOrder.get_ID() > 0){
						tripOrder.setDocStatus(DOCSTATUS_Completed);
						tripOrder.saveEx();
					}				
				}		
			}
		}else{ //Si es consolidado 
			
			//Si tiene el check de aplica a todos, tengo que generar una trazabilidad por cada uno de los expedientes que conforma la órden de trasporte seleccionada
			if(this.isApplyToAll()){
				for (MTRTransOrderLine line : ((MTRTransOrder) this.getUY_TR_TransOrder()).getLines()) {				

					//Primero segun sea el estado que se ingresó en esta gestion de carga, es si ponemos como finalizada o no todos los expedientes asociados a esta órden de trasporte 
					if(this.getUY_TR_LoadStatus().isEndTrackStatus()) line.setEndTrackStatus(true);
					
					MTRLoadStatus status = (MTRLoadStatus)this.getUY_TR_LoadStatus();
					
					if(status.getValue().equalsIgnoreCase("entregado")) line.setIsDelivered(true); //marco check de mercaderia entregada
					
					if(this.getM_Warehouse_ID()>0) line.setM_Warehouse_ID(this.getM_Warehouse_ID()); //seteo el almacen					
					
					MTRTripLoad tripLoad = MTRTripLoad.forTripIDAndOrderID(this.getCtx(), line.getUY_TR_Trip_ID(), this.getUY_TR_TransOrder_ID(), this.get_TrxName());
					
					if(tripLoad == null || (tripLoad != null && tripLoad.get_ID() == 0)) tripLoad = new MTRTripLoad(this.getCtx(), 0, this.get_TrxName());
									
					tripLoad.setUY_TR_Trip_ID(this.getUY_TR_Trip_ID());
					if(this.getUY_TR_TransOrder_ID() > 0) tripLoad.setUY_TR_TransOrder_ID(this.getUY_TR_TransOrder_ID());
					tripLoad.setRemolque_ID(this.getRemolque_ID());
					tripLoad.setUY_TR_Truck_ID(this.getUY_TR_Truck_ID());
					tripLoad.setUY_TR_Driver_ID(this.getUY_TR_Driver_ID());
					tripLoad.setIsConsolidated(this.isConsolidated());
					tripLoad.setOnMove(this.isOnMove());
					tripLoad.setUY_TR_LoadStatus_ID(this.getUY_TR_LoadStatus_ID());
					if(this.getM_Warehouse_ID() > 0) tripLoad.setM_Warehouse_ID(this.getM_Warehouse_ID());
					tripLoad.setDocStatus(DOCSTATUS_Completed);
					tripLoad.setAD_User_ID(this.getUpdatedBy());
					tripLoad.setDateTrx(this.getUpdated());		
					
					tripLoad.saveEx();
				}
			}		
			
			boolean cargasFinalizadas = true;
			for (MTRTransOrderLine line : ((MTRTransOrder) this.getUY_TR_TransOrder()).getLines()) {				
				if(!line.isEndTrackStatus()){
					cargasFinalizadas = false;
				}			
			}
			
			if(cargasFinalizadas){ //Si están todas finalizadas completo la orden de transporte
				
				//Solo si se ingresa una orden de transporte
				if(this.getUY_TR_TransOrder_ID() > 0){					

					MTRTransOrder torder = (MTRTransOrder) this.getUY_TR_TransOrder();
					
					if(torder.getDocStatus().equals(DOCSTATUS_Applied)){
						
						try {				
							if (!torder.processIt(DocumentEngine.ACTION_Complete)){
								throw new AdempiereException(torder.getProcessMsg());
							}				
						} catch (Exception e) {
							throw new AdempiereException(e.getMessage());
						}
						
						//Se actualiza el campo del estado de la orden en la pestana de trazabilidad de orden de transporte en la ventana de gestion de expediente 
						MTRTripOrder tripOrder = MTRTripOrder.forTripIDAndOrderID(this.getCtx(), this.getUY_TR_Trip_ID(), this.getUY_TR_TransOrder_ID(), this.get_TrxName());
						
						if(tripOrder != null && tripOrder.get_ID() > 0){
							tripOrder.setDocStatus(DOCSTATUS_Completed);
							tripOrder.saveEx();
						}								
					}					
					
				}
			}	
		}	
	}
	
	/**
	 * OpenUp. Guillermo Brust. 09/01/2014. ISSUE #1625
	 * Método que guarda la trazabilidad de la carga en la ventana de Gestión de Expedientes.
	 * 
	 * */
	private void guardarTrazabilidad(){
		
		//Guardo datos en la pestania de trazabilidad de las cargas en la ventana de gestion de expedientes
		MTRTripLoad tripLoad = MTRTripLoad.forTripIDAndOrderID(this.getCtx(), this.getUY_TR_Trip_ID(), this.getUY_TR_TransOrder_ID(), this.get_TrxName());
		
		if(tripLoad == null || (tripLoad != null && tripLoad.get_ID() == 0)) tripLoad = new MTRTripLoad(this.getCtx(), 0, this.get_TrxName());
						
		tripLoad.setUY_TR_Trip_ID(this.getUY_TR_Trip_ID());
		if(this.getUY_TR_TransOrder_ID() > 0) tripLoad.setUY_TR_TransOrder_ID(this.getUY_TR_TransOrder_ID());
		tripLoad.setRemolque_ID(this.getRemolque_ID());
		tripLoad.setUY_TR_Truck_ID(this.getUY_TR_Truck_ID());
		tripLoad.setUY_TR_Driver_ID(this.getUY_TR_Driver_ID());
		tripLoad.setIsConsolidated(this.isConsolidated());
		tripLoad.setOnMove(this.isOnMove());
		tripLoad.setUY_TR_LoadStatus_ID(this.getUY_TR_LoadStatus_ID());
		if(this.getM_Warehouse_ID() > 0) tripLoad.setM_Warehouse_ID(this.getM_Warehouse_ID());
		tripLoad.setDocStatus(this.getDocStatus());
		tripLoad.setAD_User_ID(this.getUpdatedBy());
		tripLoad.setDateTrx(this.getUpdated());		
		
		tripLoad.saveEx();
	}
	
	
	/***
	 * OpenUp. Guillermo Brust. 13/12/2013. ISSUE #1625
	 * Metodo que devuelve si una orden de transporte es consolidada
	 * 
	 * */
	private boolean transOrderIsConsolidated(int transOrderID){
		
		boolean retorno = false;
		
		ResultSet rs = null;
		PreparedStatement pstmt = null;		

		try {
			
			String sql = "select count(ol.uy_tr_transorderline_id) as cargas" +
						 " from uy_tr_transorderline ol" +
						 " where ol.uy_tr_transorder_id = " + transOrderID;

			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, null);
			rs = pstmt.executeQuery ();				
		
			if(rs.next()){		
				
				if(rs.getInt("cargas") > 1) {
					retorno = true;
				}				
			}
			
		} catch (Exception e) {
			DB.close(rs, pstmt);	
			throw new AdempiereException(e);
		}
		finally{
			DB.close(rs, pstmt);
		}
		
		return retorno;
	}

	@Override
	public boolean voidIt() {

		// Before Void
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_VOID);
		if (this.processMsg != null)
			return false;
		
		
		//OpenUp. Guillermo Brust. 08/01/2014. ISSUE #1625
		//Se elimina el registro de la trazabilidad en la pestania de carga en la ventana de gestion de expediente
		if(this.getUY_TR_TransOrder_ID() > 0){
			
			MTRTripLoad tripLoad = MTRTripLoad.forTripIDAndOrderID(this.getCtx(), this.getUY_TR_Trip_ID(), this.getUY_TR_TransOrder_ID(), this.get_TrxName());
			if(tripLoad != null) tripLoad.deleteEx(true);			
		}
		//Fin OpenUp.
		
		
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
	public int getC_Currency_ID() {		
		return 0;
	}

	@Override
	public BigDecimal getApprovalAmt() {
		return null;
	}

}
