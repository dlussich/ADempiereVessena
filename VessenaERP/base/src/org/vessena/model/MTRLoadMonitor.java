/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 16/11/2014
 */
package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBPartner;
import org.compiere.model.MDocType;
import org.compiere.model.MInvoice;
import org.compiere.model.MLocator;
import org.compiere.model.MPeriod;
import org.compiere.model.MWarehouse;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * org.openup.model - MTRLoadMonitor
 * OpenUp Ltda. Issue #1625 
 * Description: Modelo para documento de Seguimiento de Cargas en modulo de transporte.
 * @author Gabriel Vila - 16/11/2014
 * @see
 */
public class MTRLoadMonitor extends X_UY_TR_LoadMonitor implements DocAction {

	private static final long serialVersionUID = -2308043012366994715L;

	private String processMsg = null;
	private boolean justPrepared = false;

	
	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_TR_LoadMonitor_ID
	 * @param trxName
	 */
	public MTRLoadMonitor(Properties ctx, int UY_TR_LoadMonitor_ID,
			String trxName) {
		super(ctx, UY_TR_LoadMonitor_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTRLoadMonitor(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#processIt(java.lang.String)
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 16/11/2014
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
	 * @author Gabriel Vila - 16/11/2014
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
	 * @author Gabriel Vila - 16/11/2014
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
	 * @author Gabriel Vila - 16/11/2014
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
	 * @author Gabriel Vila - 16/11/2014
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
	 * @author Gabriel Vila - 16/11/2014
	 * @see
	 * @return
	 */
	@Override
	public boolean applyIt() {

		boolean loadLines = true;
		
		// Enganche de vehiculos
		if (this.isChangeTruck()){
			loadLines = false;
		}
		// Carga de mercaderias
		else if (this.isLoadProduct()){
			if ((this.getUY_Ciudad_ID() <= 0) || (this.getUY_Ciudad_ID_1() <= 0)){
				throw new AdempiereException("Debe indicar Ciudad Origen y Destino.");
			}
			if (this.getTypeLoad() == null){
				throw new AdempiereException("Debe indicar donde se Carga la Mercaderia");
			}
			
			if (this.getTypeLoad().equalsIgnoreCase(X_UY_TR_LoadMonitor.TYPELOAD_ALMACENPROPIO)){
				if (this.getM_Warehouse_ID() <= 0){
					throw new AdempiereException("Debe indicar Almacen donde se Carga la Mercaderia");
				}
			}
		}
		// Descarga de mercaderias
		else if (this.isUnloadProduct()){
			if (!this.isDelivered()){
				if (this.getM_Warehouse_ID() <= 0){
					throw new AdempiereException("Debe indicar Almacen donde se Descarga la Mercaderia");
				}
			}
		}
		// Trasbordos
		else if (this.isTrasbordo()){
			// Trasbordos directos que siguen de largo en el mismo vehiculo, y ese vehiculo es de terceros,
			// debo generar nueva orden por tema de pago de flete a terceros.
			// Si viene en flota propia alcanza con reactivar el movimiento original y cambiar el destino.
			if ((this.getUY_TR_TransOrder_ID()) > 0 && (this.getUY_TR_TransOrder_ID() == this.getUY_TR_TransOrder_ID_2())){
				// Si me cambiaron la ciudad destino con respecto a la orden de transporte inicial, entonces
				// asumo trasbordo directo (no se cambio de vehiculo) y se requiere entonces nueva OT.
				MTRTransOrder torder = (MTRTransOrder)this.getUY_TR_TransOrder();
				if (torder.getUY_Ciudad_ID_1() != this.getUY_Ciudad_ID_3()){
					// Destinos distintos, mismo vehiculo, voy a necesitar otra OT y completar luego la anterior.
					DB.executeUpdateEx(" update uy_tr_loadmonitor set uy_tr_transorder_id_2 = null where uy_tr_loadmonitor_id =" + this.get_ID(), get_TrxName());
				}
			}
		}

		// Viaje en lastre
		else if (this.isLastre()){
			loadLines = false;
			// Si no tengo tractor no puedo completar.
			if (this.getTractor_ID() <= 0){
				throw new AdempiereException("Para viajes en Lastre se debe indicar un Tractor");
			}

			// Si tengo orden de transporte no puedo completar accion de viaje en lastre
			if ((this.getUY_TR_TransOrder_ID() > 0) && (!this.isConfirmation())){
				throw new AdempiereException("No puedo considerar para viaje en Lastre a vehiculos con orden de transporte asociadas");
			}
		}
		// Cambio de conductor
		else if (this.isChangeDriver()){
			// Verifico si tengo tractor
			if (this.getTractor_ID() <= 0){
				throw new AdempiereException("Debe indicar vehiculo tractor para asociación de Conductor.");
			}
		}
		
		if(this.isWarehouseRequired()){
			if (this.getM_Warehouse_ID() <= 0){
				throw new AdempiereException("Esta accion de carga requiere que se indique Almacen");
			}
		}
		
		/*if(this.isOnMove()){
			if(this.getUY_TR_TransOrder_ID()<=0){
				throw new AdempiereException("Esta accion de carga requiere que se indique Orden de Transporte");
			}
		}*/
		
		if (loadLines) this.loadCRTS();
		
		this.setDocStatus(DocumentEngine.STATUS_Applied);
		this.setDocAction(DocAction.ACTION_Complete);

		return true;
	}

	/***
	 * Obtiene y carga CRTS asociados a una orden de transporte en la grilla de lineas de este documento.
	 * OpenUp Ltda. Issue #1405 
	 * @author Gabriel Vila - 12/12/2014
	 * @see
	 */
	private void loadCRTS() {

		try {
			
			// Si tengo orden de transporte, obtengo y despliego CRTS asociados
			if (this.getUY_TR_TransOrder_ID() > 0){

				// Elimino lineas de seguimiento existentes
				DB.executeUpdateEx(" delete from uy_tr_loadmonitorline where uy_tr_loadmonitor_id =" + this.get_ID(), get_TrxName());
				
				MTRTransOrder torder = (MTRTransOrder)this.getUY_TR_TransOrder();
				List<MTRTransOrderLine> otlines = torder.getLines();

				for (MTRTransOrderLine otline: otlines){
					if (!otline.isDelivered() && !otline.isEndTrackStatus()){
						MTRLoadMonitorLine mline = new MTRLoadMonitorLine(getCtx(), 0, get_TrxName());
						mline.setUY_TR_LoadMonitor_ID(this.get_ID());
						mline.setUY_TR_TransOrderLine_ID(otline.get_ID());
						mline.setUY_TR_Trip_ID(otline.getUY_TR_Trip_ID());
						mline.setWeight(otline.getWeight());
						mline.setWeightAux(otline.getWeight());
						mline.setWeight2(otline.getWeight2());
						mline.setWeight2Aux(otline.getWeight2());
						mline.setVolume(otline.getVolume());
						mline.setVolume2(otline.getVolume());
						mline.setQtyPackage(otline.getQtyPackage());
						mline.setQtyPackage2(otline.getQtyPackage());
						mline.setAmount(otline.getAmount());
						mline.setAmount2(otline.getAmount());
						mline.setC_Currency_ID(otline.getC_Currency_ID());
						mline.setProductAmt(otline.getProductAmt());
						mline.setProductAmt2(otline.getProductAmt());
						mline.setC_BPartner_ID(otline.getC_BPartner_ID());
						mline.setUY_TR_PackageType_ID(otline.getUY_TR_PackageType_ID());
						mline.setUY_TR_Remito_ID(otline.getUY_TR_Remito_ID());
						mline.setUY_TR_Crt_ID(otline.getUY_TR_Crt_ID());
						mline.setIsSelected(false);
						mline.setIsManual(false);
						mline.saveEx();
					}
				}
			}
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
	
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#rejectIt()
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 16/11/2014
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
	 * @author Gabriel Vila - 16/11/2014
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

		// Obtengo modelo de estado de carga seleccionado
		MTRLoadStatus sts = (MTRLoadStatus)this.getUY_TR_LoadStatus();
		
		// Si estoy en un documento reactivado
		//if (this.getDocStatus().equalsIgnoreCase(DOCSTATUS_InProgress) || (this.getDocStatus().equalsIgnoreCase(DOCSTATUS_Invalid))){
		if (this.getDocStatus().equalsIgnoreCase(DOCSTATUS_InProgress)){
			// Obtengo orden de transporte si es que existe
			MTRTransOrder torder = null;
			// Si es trasbordo, obtengo orden de transporte de trasbordo 
			if (this.getUY_TR_TransOrder_ID_2() > 0){
				torder = new MTRTransOrder(getCtx(), this.getUY_TR_TransOrder_ID_2(), get_TrxName());
			}
			else{
				if (this.getUY_TR_TransOrder_ID() > 0){
					torder = new MTRTransOrder(getCtx(), this.getUY_TR_TransOrder_ID(), get_TrxName());	
				}
			}
			
			if (torder != null){
				if (torder.get_ID() > 0){
					
					// Si no es un movimiento de descarga de mercaderia, eliminto lineas de ordenes de transporte ya que en se vuelven a generar en este proceso.
					// En un movimiento de descarga de mercaderia, las lineas se eliminan mas adelante en el metodo: executeUnLoadProduct
					if (!this.isUnloadProduct()){
						torder.deleteLinesForMonitor(this.get_ID());	
					}
				}
			}
			
			// Si estoy moviendo mercaderia utilizando un almacen
			if ((this.getTypeLoad() != null) && (this.getTypeLoad().equalsIgnoreCase(X_UY_TR_LoadMonitor.TYPELOAD_ALMACENPROPIO))){
				
				// Elimino registros creados en stock de mercaderia
				DB.executeUpdateEx(" delete from uy_tr_stock where uy_tr_loadmonitorline_id in " +
				           " (select uy_tr_loadmonitorline_id from uy_tr_loadmonitorline where uy_tr_loadmonitor_id =" + this.get_ID() + ")", get_TrxName());
				
			}
		}
		
		// Proceso segun el comportammiento de este estado o accion de carga
		if (sts.isLoadProduct()){
			this.processMsg = this.executeLoadProduct(sts);
		}
		else if (sts.isTrasbordo()){
			this.processMsg = this.executeTrasbordo(sts);
		}
		else if (sts.isEndTrackStatus()){
			this.processMsg = this.executeUnLoadProduct(sts);
		}
		else if (sts.isChangeTruck()){
			this.processMsg = this.executeChangeTruck();
		}
		else if (sts.isChangeDriver()){
			this.processMsg = this.executeChangeDriver(this.getTractor_ID());
		}
		else if (sts.isLastre()){
			this.processMsg = this.executeLastre();
		}
		else if (!sts.isOnMove() && !sts.isBeforeLoad() && sts.isWarehouseRequired() && sts.isHandleCRT()){
			this.processMsg = this.executeProductToWarehouse();
		}
				
		if (this.processMsg != null){
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

	/***
	 * Ejecuta acciones de carga asociadas a Enganche de Vehiculo.
	 * OpenUp Ltda. Issue #1405 
	 * @author Gabriel Vila - 15/12/2014
	 * @see
	 * @param sts
	 * @return
	 */
	private String executeChangeTruck() {

		String message = null;
		
		boolean processAssociation = false;
		
		MTRTruck remolque = null, tractor = null;
		
		try {

			// Verifico si hubo cambio de asociacion en vehiculos
			if (this.getRemolque_ID() > 0){
				remolque = new MTRTruck(getCtx(), this.getRemolque_ID(), get_TrxName());
				if (remolque.getUY_TR_Truck_ID_2() != this.getTractor_ID()){
					processAssociation = true;
				}
			}
			
			if (this.getTractor_ID() > 0){
				tractor = new MTRTruck(getCtx(), this.getTractor_ID(), get_TrxName());
				if (!processAssociation){
					if (tractor.getUY_TR_Truck_ID_2() != this.getRemolque_ID()){
						processAssociation = true;
					}
				}
			}

			// Si es necesario generar una nueva asociacion, lo hago utilizando el modelo.
			if (processAssociation){
				MTRTruckAssociation ta = new MTRTruckAssociation(getCtx(), 0, get_TrxName());
				ta.setC_DocType_ID(MDocType.forValue(getCtx(), "truckassoc", null).get_ID());
				ta.setDateTrx(this.getDateTrx());
				ta.setDescription(this.getDescription());
				ta.setTruckAssociationType("ASOCIAR");
				
				if (this.getTractor_ID() > 0){
					
					ta.setUY_TR_Truck_ID(this.getTractor_ID());					
					ta.setUY_TR_TruckType_ID(tractor.getUY_TR_TruckType_ID());
					
					if (tractor.getUY_TR_Truck_ID_2() > 0){
						
						MTRTruck truck2 = new MTRTruck(getCtx(), tractor.getUY_TR_Truck_ID_2(), null);

						ta.setUY_TR_Truck_ID_Old(tractor.getUY_TR_Truck_ID_2());
						ta.setUY_TR_TruckType_ID_2(truck2.getUY_TR_TruckType_ID());
					}
					
					if (this.getRemolque_ID() > 0){
						
						ta.setUY_TR_Truck_ID_New(this.getRemolque_ID());
						ta.setUY_TR_TruckType_ID_3(remolque.getUY_TR_TruckType_ID());
					}
				}
				else if (this.getRemolque_ID() > 0){
					
					ta.setUY_TR_Truck_ID(this.getRemolque_ID());					
					ta.setUY_TR_TruckType_ID(remolque.getUY_TR_TruckType_ID());
					
					if (remolque.getUY_TR_Truck_ID_2() > 0){
						
						MTRTruck truck2 = new MTRTruck(getCtx(), remolque.getUY_TR_Truck_ID_2(), null);

						ta.setUY_TR_Truck_ID_Old(remolque.getUY_TR_Truck_ID_2());
						ta.setUY_TR_TruckType_ID_2(truck2.getUY_TR_TruckType_ID());
					}
				}
				
				ta.saveEx();
				
				if (!ta.processIt(ACTION_Complete)){
					message = ta.getProcessMsg();
				}
				
			}
			
			//si tengo OT actualizo los datos de vehiculo
			if(this.getUY_TR_TransOrder_ID()>0){
				
				boolean refreshMic = false;
				
				MTRTransOrder order = new MTRTransOrder(getCtx(),this.getUY_TR_TransOrder_ID(),get_TrxName());
								
				if(this.getUY_TR_Truck_ID_3()>0) order.setUY_TR_Truck_ID(this.getUY_TR_Truck_ID_3());
				if(this.getUY_TR_TruckType_ID_3()>0) order.setUY_TR_TruckType_ID(this.getUY_TR_TruckType_ID_3());
				if(this.getUY_TR_Truck_ID_4()>0) order.setUY_TR_Truck_ID_Aux(this.getUY_TR_Truck_ID_4());
				if(this.getUY_TR_TruckType_ID_4()>0) order.setUY_TR_TruckType_ID_2(this.getUY_TR_TruckType_ID_4());
				
				if(this.getTractor_ID_2()>0) order.setTractor_ID(this.getTractor_ID_2());
				if(this.getRemolque_ID_2()>0) order.setRemolque_ID(this.getRemolque_ID_2());
				
				if(this.getUY_TR_Driver_ID_2()>0) {
					
					if(order.getUY_TR_Driver_ID()!=this.getUY_TR_Driver_ID_2()) order.setUY_TR_Driver_ID(this.getUY_TR_Driver_ID_2());					
					
				} else if(order.getUY_TR_Driver_ID()!=this.getUY_TR_Driver_ID()) order.setUY_TR_Driver_ID(this.getUY_TR_Driver_ID());
								
				order.saveEx();	
				
				//busco y actualizo MIC si lo hay
				MTRMic mic = MTRMic.forOT(getCtx(), order.get_ID(), get_TrxName());
				
				if(mic!=null && mic.get_ID()>0){
					
					if(order.getTractor_ID()>0 && mic.getTractor_ID()!=order.getTractor_ID()) {
						mic.setTractor_ID(order.getTractor_ID());
						refreshMic = true;
					}
					
					if(order.getRemolque_ID()>0 && mic.getRemolque_ID()!=order.getRemolque_ID()) {
						mic.setRemolque_ID(order.getRemolque_ID());
						refreshMic = true;
					}
					
					if(order.getUY_TR_Driver_ID()>0 && mic.getUY_TR_Driver_ID()!=order.getUY_TR_Driver_ID()){
						mic.setUY_TR_Driver_ID(order.getUY_TR_Driver_ID());
						refreshMic = true;
					}
					
					if(refreshMic){
						
						mic.saveEx();
						
						this.updateMicShipperInfo(mic); //actualizo campos 36 de MIC
 						
					}
									
				}
				
			}		
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		}
		
		return message;
	}


	private void updateMicShipperInfo(MTRMic mic) {

		MTRCrt crt = null;
		MTRTrip trip = null;
		MTRWay way = null;
		MTRMicCont cont = null;
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;		

		try{

			if(mic.getUY_TR_Crt_ID()>0){

				crt = new MTRCrt(getCtx(), mic.getUY_TR_Crt_ID(), get_TrxName());
				trip = new MTRTrip(getCtx(), crt.getUY_TR_Trip_ID(), get_TrxName());
				way = new MTRWay(getCtx(), trip.getUY_TR_Way_ID(), get_TrxName());

				this.updateShipperInfo(mic,trip,way,true,null,false);		

			}

			//recorro las continuaciones
			sql = "select uy_tr_miccont_id, uy_tr_crt_id, uy_tr_crt_id_1" +
					" from uy_tr_miccont" +
					" where uy_tr_mic_id = " + mic.get_ID() +
					" order by sheet asc";

			pstmt = DB.prepareStatement (sql, null);
			rs = pstmt.executeQuery ();

			while (rs.next()){

				cont = new MTRMicCont(getCtx(),  rs.getInt("uy_tr_miccont_id"), get_TrxName());

				if(rs.getInt("uy_tr_crt_id")>0){
					
					crt = new MTRCrt(getCtx(), rs.getInt("uy_tr_crt_id"), get_TrxName());
					trip = new MTRTrip(getCtx(), crt.getUY_TR_Trip_ID(), get_TrxName());
					way = new MTRWay(getCtx(), trip.getUY_TR_Way_ID(), get_TrxName());

					this.updateShipperInfo(mic,trip,way,false,cont,true);						
				}
				
				if(rs.getInt("uy_tr_crt_id_1")>0){
					
					crt = new MTRCrt(getCtx(), rs.getInt("uy_tr_crt_id_1"), get_TrxName());
					trip = new MTRTrip(getCtx(), crt.getUY_TR_Trip_ID(), get_TrxName());
					way = new MTRWay(getCtx(), trip.getUY_TR_Way_ID(), get_TrxName());

					this.updateShipperInfo(mic,trip,way,false,cont,false);					
				}
			}	

		}catch (Exception e)
		{
			throw new AdempiereException(e.getMessage());
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}		

	}

	private void updateShipperInfo(MTRMic mic, MTRTrip trip, MTRWay way,
			boolean isHdr, MTRMicCont cont, boolean isCrt1) {
		
		String value = "";

		if(way.isPrintExpo()){

			value = "";			

			MBPartner partner = new MBPartner(getCtx(),trip.getC_BPartner_ID_To(),get_TrxName()); //instancio el exportador

			value = "EXPORTADOR: " + partner.getName() + "\n";

			if(trip.getReferenceNo()!=null && !trip.getReferenceNo().equalsIgnoreCase("")) value += "FACTURA N°: " + trip.getReferenceNo();				

		} else if (way.isPrintDeclaration()){

			value = "";

			if(trip.getUY_TR_Despachante_ID_3()>0){

				MTRDespachante desp = new MTRDespachante(getCtx(),trip.getUY_TR_Despachante_ID_3(),get_TrxName()); //obtengo representante en frontera del exportador

				value = "DESP: " + desp.getName() + "\n";

			}	

			if(trip.getDeclarationType()!=null && (trip.getNumero()!=null && !trip.getNumero().equalsIgnoreCase(""))){

				if(value.equalsIgnoreCase("")){

					value = trip.getDeclarationType() + ": " + trip.getNumero();					

				} else value += trip.getDeclarationType() + ": " + trip.getNumero();			

			}
		}
		
		if(isHdr){
			
			mic.setObservaciones2(mic.getTruckData() + "\n" + value);
			mic.saveEx();
			
		} else {
			
			if(isCrt1){
				
				cont.setObservaciones2(mic.getTruckData() + "\n" + value);
				
			} else cont.setObservaciones4(mic.getTruckData() + "\n" + value);
			
			cont.saveEx();
			
		}
		
	}

	/***
	 * Ejecuta acciones de carga asociadas a Cambio de Conductor en vehiculo.
	 * OpenUp Ltda. Issue #1405 
	 * @author Gabriel Vila - 30/12/2014
	 * @see
	 * @param sts
	 * @return
	 */
	private String executeChangeDriver(int tractorID) {

		String message = null;
		
		MTRTruck tractor = null;
		
		try {

			// Verifico si tengo tractor en caso de que hayan digitado nuevo conductor
			if ((tractorID <= 0) && (this.getUY_TR_Driver_ID_2() > 0)){
				message = "Debe indicar vehiculo tractor para asociación de Conductor.";
				return message;
			}
			
			tractor = new MTRTruck(getCtx(), tractorID, get_TrxName());
			
			// Si el conductor actual del tractor es distinto del nuevo conductor
			if (tractor.getUY_TR_Driver_ID() != this.getUY_TR_Driver_ID_2()){
				if (this.getUY_TR_Driver_ID_2() > 0){

					// Desasocio nuevo conductor con un posible vehiculo anterior
					DB.executeUpdateEx(" update uy_tr_truck set uy_tr_driver_id = null where uy_tr_driver_id =" + this.getUY_TR_Driver_ID_2(), get_TrxName());
					
					// Asocio vehiculo con nuevo conductor
					tractor.setUY_TR_Driver_ID(this.getUY_TR_Driver_ID_2());
					tractor.saveEx();
					
				}
				else{
					// No tengo nuevo conductor, dejo nulo en vehiculo siempre y cuando sea una accion de cambio de conductor.
					// Si estoy en una carga de mercaderia y no pone nuevo conductor no hago nada
					if (this.isChangeDriver()){
						DB.executeUpdateEx(" update uy_tr_truck set uy_tr_driver_id = null where uy_tr_truck_id =" + tractor.get_ID(), get_TrxName());	
					}
				}
			}
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		
		return message;
	}

	
	/***
	 * Ejecuta acciones de carga asociada con viajes en lastre.
	 * OpenUp Ltda. Issue #1405 
	 * @author Gabriel Vila - 22/12/2014
	 * @see
	 * @return
	 */
	private String executeLastre() {

		String message = null;
		
		try {

			// Si no tengo tractor no puedo completar.
			if (this.getTractor_ID() <= 0){
				message = "Para viajes en Lastre se debe indicar un Tractor";
				return message;
			}

			// Si tengo orden de transporte no puedo completar accion de viaje en lastre
			if ((this.getUY_TR_TransOrder_ID() > 0) && (!this.isConfirmation())){
				message = "No puedo considerar para viaje en Lastre a vehiculos con orden de transporte asociadas";
				return message;
			}
			
			MTRTransOrder torder = null;
			
			// Si es un nuevo viaje en lastre
			if (!this.isConfirmation()){

				// Creo nueva OT para viaje en lastre
				torder = new MTRTransOrder(getCtx(), 0, get_TrxName());
				torder.setDateTrx(this.getDateTrx());
				torder.setDescription(this.getDescription());
				torder.setC_DocType_ID(MDocType.forValue(getCtx(), "transorder", null).get_ID());
				torder.setC_Currency_ID(this.getC_Currency_ID());
				torder.setIsOwn(this.isOwn());
				torder.setIsLastre(true);
				torder.setUY_TR_Truck_ID(this.getUY_TR_Truck_ID());
				torder.setUY_Ciudad_ID(this.getUY_Ciudad_ID());
				torder.setUY_Ciudad_ID_1(this.getUY_Ciudad_ID_1());
				torder.setTractor_ID(this.getTractor_ID());
				torder.setIsAssociated(this.isAssociated());
				torder.setUY_TR_TruckType_ID(this.getUY_TR_TruckType_ID());
				torder.setIsManual(false);
				
				if (this.getRemolque_ID() > 0) torder.setRemolque_ID(this.getRemolque_ID());
				if (this.getUY_TR_Truck_ID_2() > 0) torder.setUY_TR_Truck_ID_Aux(this.getUY_TR_Truck_ID_2());
				if (this.getUY_TR_TruckType_ID_2() > 0) torder.setUY_TR_TruckType_ID_2(this.getUY_TR_TruckType_ID_2());
				if (this.getUY_TR_Driver_ID() > 0) torder.setUY_TR_Driver_ID(this.getUY_TR_Driver_ID());
				
				torder.saveEx();

				// Aplico nueva orden de transporte
				if (!torder.processIt(ACTION_Apply)){
					message = torder.getProcessMsg();	
					if ((message == null) || (message.equalsIgnoreCase(""))){
						message = "No fue posible Aplicar documento de Orden de Transporte.";
					}
					return message;
				}
				
				torder.saveEx();
				
				// Actualizo orden de transporte en este documento.
				DB.executeUpdateEx(" update uy_tr_loadmonitor set uy_tr_transorder_id = " + torder.get_ID() + 
						           " where uy_tr_loadmonitor_id =" + this.get_ID(), get_TrxName());
				
			}
			else{
				// Es una confirmacion de llegada de un lastre
				// Completo la orden de transporte asociada al viaje en lastre que estoy confirmando
				torder = (MTRTransOrder)this.getUY_TR_TransOrder();

				if (!torder.processIt(ACTION_Complete)){
					message = torder.getProcessMsg();	
					if ((message == null) || (message.equalsIgnoreCase(""))){
						message = "No fue posible Completar documento de Orden de Transporte.";
					}
					return message;
				}
				torder.saveEx();
			}

			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		
		return message;
	}

	
	/***
	 * Ejecuta acciones de carga asociada con descarga de mercaderias. 
	 * OpenUp Ltda. Issue #1405 
	 * @author Gabriel Vila - 12/12/2014
	 * @see
	 * @param sts
	 * @return
	 */
	private String executeUnLoadProduct(MTRLoadStatus sts) {

		String message = null;
				
		try {
			
			if (!sts.isEndTrackStatus()){
				message = "Accion de Carga seleccionada no permite Descarga de Mercaderias.";
				return message;
			}
			
			// Obtengo lineas seleccionadas en este documento para ser procesadas
			List <MTRLoadMonitorLine> mlines = this.getSelectedLines();
			
			// Si no tengo lineas seleccionadas aviso y no proceso nada
			if (mlines.size() <= 0){
				message = "Debe seleccionar al menos una linea para poder Completar el Documento.";
				return message;
			}

			boolean unloadAll = true;
			List<MTRTransOrderLine> otLinesToDelete = new ArrayList<MTRTransOrderLine>();
			
			// Recorro lineas seleccionadas
			for (MTRLoadMonitorLine mline: mlines){
				
				// Obtengo modelo de linea de orden de transporte asociada a esta linea de seguimiento de carga
				MTRTransOrderLine otline = new MTRTransOrderLine(getCtx(), mline.getUY_TR_TransOrderLine_ID(), get_TrxName());
				
				// Si descargo toda la cantidad
				if (mline.getQtyPackage().compareTo(mline.getQtyPackage2()) == 0){
					// Marco la linea como finalizada y no muevo valores en la linea
					if (otline != null){
						otline.setEndTrackStatus(true);	
					}
				}
				// Descargo obviamente menos que la cantidad total original de la linea al momento de cargarla en este documento
				else if(mline.getQtyPackage().compareTo(mline.getQtyPackage2()) < 0){

					unloadAll = false;
					
					// Ajusto la orden de linea de transporte, dejando los valores que el usuario NO DESCARGO (LO ANTERIOR MENOS LO DESCARGADO)
					otline.setAmount(mline.getAmount2().subtract(mline.getAmount()));
					otline.setProductAmt(mline.getProductAmt2().subtract(mline.getProductAmt()));
					otline.setQtyPackage(mline.getQtyPackage2().subtract(mline.getQtyPackage()));
					otline.setVolume(mline.getVolume2().subtract(mline.getVolume()));
					otline.setWeight(mline.getWeightAux().subtract(mline.getWeight()));
					otline.setWeight2(mline.getWeight2Aux().subtract(mline.getWeight2()));
					otline.setEndTrackStatus(false);
				}
				else{
					message = "No se puede descargar mas cantidad que la que tiene el CRT : " + mline.getUY_TR_Crt_ID();
					return message;
				}
				
				// Si se descarga por ser entrega en destino
				if (sts.isDelivered()){
					if (otline != null){
						otline.setIsDelivered(true);
						otline.setEndTrackStatus(true);
						otline.setDateDelivered(this.getDateTrx());
					}
				}
				else{
					// si se descarga en almacen
					if (otline != null){
						otline.setIsDelivered(false);
						otline.setM_Warehouse_ID(this.getM_Warehouse_ID());
					}
				}

				if (otline != null){
					
					if (otline.get_ID() > 0){
						otline.saveEx();
						
						if (otline.isEndTrackStatus() && !otline.isDelivered()){
							otLinesToDelete.add(otline);
						}
					}
					
				}

				// Si es descarga en almacen
				if (!sts.isDelivered()){

					// Bajo mercaderia en el almacen y por lo tanto sube stock del almacen
					MTRStock stock = new MTRStock(getCtx(), 0, get_TrxName());
					stock.setAD_User_ID(Env.getAD_User_ID(getCtx()));
					stock.setDateTrx(this.getDateTrx());
					stock.setMovementDate(this.getDateTrx());
					
					MPeriod period = MPeriod.get(getCtx(), this.getDateTrx(), 0);
					if ((period != null) && (period.get_ID() > 0)) stock.setC_Period_ID(period.get_ID());
					
					stock.setsign(Env.ONE);
					stock.setM_Warehouse_ID(this.getM_Warehouse_ID());
					stock.setM_Locator_ID(MLocator.getDefault((MWarehouse)this.getM_Warehouse()).get_ID());
					stock.setUY_TR_LoadMonitor_ID(this.get_ID());
					stock.setUY_TR_LoadMonitorLine_ID(mline.get_ID());
					
					if (this.getUY_TR_TransOrder_ID() > 0) stock.setUY_TR_TransOrder_ID(this.getUY_TR_TransOrder_ID());
					
					if (otline != null){
						if (otline.get_ID() > 0){
							stock.setUY_TR_TransOrderLine_ID(otline.get_ID());	
						}
							
					}
					
					stock.setC_BPartner_ID(mline.getC_BPartner_ID());
					stock.setUY_TR_Crt_ID(mline.getUY_TR_Crt_ID());
					stock.setUY_TR_Trip_ID(mline.getUY_TR_Trip_ID());
					stock.setAmount(mline.getAmount());
					stock.setProductAmt(mline.getProductAmt());
					stock.setQtyPackage(mline.getQtyPackage());
					stock.setVolume(mline.getVolume());
					stock.setWeight(mline.getWeight());
					stock.setWeight2(mline.getWeight2());
					//stock.setUY_TR_PackageType_ID(mline.getUY_TR_PackageType_ID());
					stock.setC_Currency_ID(mline.getC_Currency_ID());
					stock.saveEx();
					
					//registro en tabla de distribucion de expediente y proforma
					MTRTripQty tripQty = MTRTripQty.forWarehouseCrt(getCtx(),this.getM_Warehouse_ID(), mline.getUY_TR_Crt_ID(),get_TrxName());
					MTRInvoiceFreightAmt freightAmt = MTRInvoiceFreightAmt.forWarehouseCrt(this.getM_Warehouse_ID(), this.getUY_TR_Crt_ID(), get_TrxName());
					
					MTRCrt crt = (MTRCrt)mline.getUY_TR_Crt();//instancio el CRT
					MInvoice inv = null;
					
					if(crt.getC_Invoice_ID()>0) inv = (MInvoice)crt.getC_Invoice();//instancio la proforma
										
					if(tripQty!=null && tripQty.get_ID()>0){
						
						tripQty.setUY_TR_Trip_ID(mline.getUY_TR_Trip_ID());
						tripQty.setUY_TR_Crt_ID(mline.getUY_TR_Crt_ID());
						tripQty.setWeight(mline.getWeight().add(tripQty.getWeight()));
						tripQty.setWeight2(mline.getWeight2().add(tripQty.getWeight2()));
						tripQty.setVolume(mline.getVolume().add(tripQty.getVolume()));
						tripQty.setQtyPackage(mline.getQtyPackage().add(tripQty.getQtyPackage()));
						tripQty.setC_Currency_ID(mline.getC_Currency_ID());
						tripQty.setProductAmt(mline.getProductAmt().add(tripQty.getProductAmt()));
						tripQty.setM_Warehouse_ID(this.getM_Warehouse_ID());
						tripQty.setUY_TR_LoadMonitorLine_ID(mline.get_ID());
						tripQty.setUY_TR_Stock_ID(stock.get_ID());
						//if(this.getUY_TR_TransOrder_ID() > 0) tripQty.setUY_TR_TransOrder_ID(this.getUY_TR_TransOrder_ID());
						//if(otline!=null && otline.get_ID()>0) tripQty.setUY_TR_TransOrderLine_ID(otline.get_ID());
						tripQty.saveEx();			
						
					} else {
						
						tripQty = new MTRTripQty(getCtx(), 0, get_TrxName());
						tripQty.setUY_TR_Trip_ID(mline.getUY_TR_Trip_ID());
						tripQty.setUY_TR_Crt_ID(mline.getUY_TR_Crt_ID());
						tripQty.setWeight(mline.getWeight());
						tripQty.setWeight2(mline.getWeight2());
						tripQty.setVolume(mline.getVolume());
						tripQty.setQtyPackage(mline.getQtyPackage());
						tripQty.setC_Currency_ID(mline.getC_Currency_ID());
						tripQty.setProductAmt(mline.getProductAmt());
						tripQty.setM_Warehouse_ID(this.getM_Warehouse_ID());
						tripQty.setUY_TR_LoadMonitorLine_ID(mline.get_ID());
						tripQty.setUY_TR_Stock_ID(stock.get_ID());
						//if(this.getUY_TR_TransOrder_ID() > 0) tripQty.setUY_TR_TransOrder_ID(this.getUY_TR_TransOrder_ID());
						//if(otline!=null && otline.get_ID()>0) tripQty.setUY_TR_TransOrderLine_ID(otline.get_ID());
						tripQty.saveEx();							
					}

					//si el CRT tiene proforma asociada
					if(inv!=null && inv.get_ID()>0){

						if(freightAmt!=null && freightAmt.get_ID()>0){

							freightAmt.setC_Invoice_ID(crt.getC_Invoice_ID());
							freightAmt.setAmount(mline.getAmount());
							freightAmt.setUY_TR_Crt_ID(mline.getUY_TR_Crt_ID());
							freightAmt.setWeight(mline.getWeight().add(freightAmt.getWeight()));
							freightAmt.setWeight2(mline.getWeight2().add(freightAmt.getWeight2()));
							freightAmt.setVolume(mline.getVolume().add(freightAmt.getVolume()));
							freightAmt.setQtyPackage(mline.getQtyPackage().add(freightAmt.getQtyPackage()));
							freightAmt.setC_Currency_ID(mline.getC_Currency_ID());
							freightAmt.setC_Currency2_ID(inv.getC_Currency_ID());
							freightAmt.setProductAmt(mline.getProductAmt().add(freightAmt.getProductAmt()));
							freightAmt.setM_Warehouse_ID(this.getM_Warehouse_ID());
							freightAmt.setUY_TR_LoadMonitorLine_ID(mline.get_ID());
							freightAmt.setUY_TR_Stock_ID(stock.get_ID());
							//if(this.getUY_TR_TransOrder_ID() > 0) freightAmt.setUY_TR_TransOrder_ID(this.getUY_TR_TransOrder_ID());
							//if(otline!=null && otline.get_ID()>0) freightAmt.setUY_TR_TransOrderLine_ID(otline.get_ID());
							freightAmt.saveEx();						

						} else {

							freightAmt = new MTRInvoiceFreightAmt(getCtx(), 0, get_TrxName());
							freightAmt.setC_Invoice_ID(inv.get_ID());
							freightAmt.setAmount(mline.getAmount());
							freightAmt.setUY_TR_Crt_ID(mline.getUY_TR_Crt_ID());
							freightAmt.setWeight(mline.getWeight());
							freightAmt.setWeight2(mline.getWeight2());
							freightAmt.setVolume(mline.getVolume());
							freightAmt.setQtyPackage(mline.getQtyPackage());
							freightAmt.setC_Currency_ID(mline.getC_Currency_ID());
							freightAmt.setC_Currency2_ID(inv.getC_Currency_ID());
							freightAmt.setProductAmt(mline.getProductAmt());
							freightAmt.setM_Warehouse_ID(this.getM_Warehouse_ID());
							freightAmt.setUY_TR_LoadMonitorLine_ID(mline.get_ID());
							freightAmt.setUY_TR_Stock_ID(stock.get_ID());
							//if(this.getUY_TR_TransOrder_ID() > 0) freightAmt.setUY_TR_TransOrder_ID(this.getUY_TR_TransOrder_ID());
							//if(otline!=null && otline.get_ID()>0) freightAmt.setUY_TR_TransOrderLine_ID(otline.get_ID());
							freightAmt.saveEx();
							
						}
					}				
					
				}
			
			}
			
			// Si fue una entrega en cliente, verifico si todos los crts de esta orden de servicio estan entregados en cliente.
			// Si es asi, completo automaticamente la orden de transporte.
			MTRTransOrder torder = (MTRTransOrder)this.getUY_TR_TransOrder();
			
			if ((sts.isDelivered() || (unloadAll))){
				if (torder.isAllCrtsDeliveredOrEnded()){
					if(!torder.getDocStatus().equalsIgnoreCase(DOCSTATUS_Completed)){
						if (!torder.processIt(ACTION_Complete)){
							message = torder.getProcessMsg();
						}
						
					}
				}
			}

			// Si es una descarga y no una entrega en destino
			if (!sts.isDelivered()){
				// Si no todas las lineas de la orden fueron seleccionadas y utilzadas en esta carga,
				if (mlines.size() < torder.getLines().size()){
					// Elimino las lineas marcadas de la orden
					for (MTRTransOrderLine lindel: otLinesToDelete){
						lindel.deleteEx(true);
					}
				}
				
			}
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		
		return message;
		
	}
	
	
	/***
	 * Ejecuta acciones de carga asociada con trasbordo de mercaderias. 
	 * OpenUp Ltda. Issue #1405 
	 * @author Gabriel Vila - 15/12/2014
	 * @see
	 * @param sts
	 * @return
	 */
	private String executeTrasbordo(MTRLoadStatus sts) {
		
		String message = null;
		
		try {
			if (!sts.isTrasbordo()){
				message = "Accion de Carga seleccionada no permite Trasbordo de Mercaderias.";
				return message;
			}
			
			// Obtengo lineas seleccionadas en este documento para ser procesadas
			List <MTRLoadMonitorLine> mlines = this.getSelectedLines();
			
			// Si no tengo lineas seleccionadas aviso y no proceso nada
			if (mlines.size() <= 0){
				message = "Debe seleccionar al menos una linea para poder Completar el Documento.";
				return message;
			}

			MTRTransOrder oldOrder = (MTRTransOrder)this.getUY_TR_TransOrder();
			MTRTransOrder torder = null;
			boolean applyOrder = false;
			
			// Si estoy cargando mercaderia en una OT nueva
			if (this.getUY_TR_TransOrder_ID_2() <= 0){
				
				// Creo nueva OT
				torder = new MTRTransOrder(getCtx(), 0, get_TrxName());
				torder.setDateTrx(this.getDateTrx());
				torder.setDescription(this.getDescription());
				torder.setC_DocType_ID(MDocType.forValue(getCtx(), "transorder", null).get_ID());
				torder.setRemolque_ID(this.getRemolque_ID_2());
				torder.setC_Currency_ID(this.getC_Currency_ID());
				torder.setTotalAmt(this.getTotalAmt());
				torder.setIsOwn(this.isOwn2());
				torder.setIsLastre(false);
				torder.setUY_TR_Truck_ID(this.getUY_TR_Truck_ID_3());
				if (this.getUY_TR_Truck_ID_4() > 0) torder.setUY_TR_Truck_ID_Aux(this.getUY_TR_Truck_ID_4());
				torder.setUY_Ciudad_ID(this.getUY_Ciudad_ID_2());
				torder.setUY_Ciudad_ID_1(this.getUY_Ciudad_ID_3());
				torder.setTractor_ID(this.getTractor_ID_2());
				torder.setIsAssociated(this.isAssociated2());
				torder.setUY_TR_TruckType_ID(this.getUY_TR_TruckType_ID_3());
				if (this.getUY_TR_TruckType_ID_4() > 0) torder.setUY_TR_TruckType_ID_2(this.getUY_TR_TruckType_ID_4());
				
				// Si en carga de mercaderia selecciona un nuevo conductor, la orden de trans. queda con el nuevo conductor.
				if (this.getUY_TR_Driver_ID_2() > 0){
					torder.setUY_TR_Driver_ID(this.getUY_TR_Driver_ID_2());
				}
				else{
					// Si tengo conductor actual, la orden va con el conductor actual
					if (this.getUY_TR_Driver_ID() > 0) torder.setUY_TR_Driver_ID(this.getUY_TR_Driver_ID());	
				}
				
				torder.setIsManual(false);
				torder.setTotalWeight(this.getTotalWeight2());
				torder.setC_Currency_ID_2(this.getC_Currency_ID_3());
				torder.setPayAmt(this.getPayAmt2());
				torder.saveEx();
				
				applyOrder = true;
				
			}
			else{
				// Tomo modelo de la seleccionada por el usuario
				torder = new MTRTransOrder(getCtx(), this.getUY_TR_TransOrder_ID_2(), get_TrxName());

				// Si en carga de mercaderia selecciona un nuevo conductor, la orden de trans. queda con el nuevo conductor.
				if (this.getUY_TR_Driver_ID_2() > 0){
					torder.setUY_TR_Driver_ID(this.getUY_TR_Driver_ID_2());
				}
				else{
					// Si tengo conductor actual, la orden va con el conductor actual
					if (this.getUY_TR_Driver_ID() > 0) torder.setUY_TR_Driver_ID(this.getUY_TR_Driver_ID());	
				}
				torder.saveEx();
			}
			
			boolean unloadAll = true;
			List<MTRTransOrderLine> otLinesToDelete = new ArrayList<MTRTransOrderLine>();
			
			// Recorro lineas seleccionadas
			for (MTRLoadMonitorLine mline: mlines){
				
				// Obtengo modelo de linea de orden de transporte asociada a esta linea de seguimiento de carga
				MTRTransOrderLine otline = (MTRTransOrderLine)mline.getUY_TR_TransOrderLine();
				
				// Si descargo toda la cantidad
				if (mline.getQtyPackage().compareTo(mline.getQtyPackage2()) == 0){
					// Marco la linea como finalizada y no muevo valores en la linea
					if (otline != null){
						otline.setEndTrackStatus(true);	
					}
					
				}
				// Descargo obviamente menos que la cantidad total original de la linea al momento de cargarla en este documento
				else if(mline.getQtyPackage().compareTo(mline.getQtyPackage2()) < 0){

					unloadAll = false;
					
					// Ajusto la orden de linea de transporte, dejando los valores que el usuario NO DESCARGO (LO ANTERIOR MENOS LO DESCARGADO)
					otline.setAmount(mline.getAmount2().subtract(mline.getAmount()));
					otline.setProductAmt(mline.getProductAmt2().subtract(mline.getProductAmt()));
					otline.setQtyPackage(mline.getQtyPackage2().subtract(mline.getQtyPackage()));
					otline.setVolume(mline.getVolume2().subtract(mline.getVolume()));
					otline.setWeight(mline.getWeightAux().subtract(mline.getWeight()));
					otline.setWeight2(mline.getWeight2Aux().subtract(mline.getWeight2()));
					otline.setEndTrackStatus(false);
				}
				
				if (otline != null){

					otline.setIsDelivered(false);
					otline.saveEx();

					if (otline.isEndTrackStatus()){
						otLinesToDelete.add(otline);
					}

				}

				
				// Creo nueva linea para orden de transporte asociada a nuevo vehiculos
				MTRTransOrderLine tline = new MTRTransOrderLine(getCtx(), 0, get_TrxName());
				tline.setUY_TR_TransOrder_ID(torder.get_ID());
				tline.setUY_TR_Trip_ID(mline.getUY_TR_Trip_ID());
				tline.setWeight(mline.getWeight());
				tline.setVolume(mline.getVolume());
				tline.setQtyPackage(mline.getQtyPackage());
				tline.setAmount(mline.getAmount());
				tline.setC_Currency_ID(mline.getC_Currency_ID());
				tline.setProductAmt(mline.getProductAmt());
				tline.setUY_TR_Truck_ID(this.getUY_TR_Truck_ID_3());
				tline.setEndTrackStatus(false);
				tline.setIsDelivered(false);
				tline.setC_BPartner_ID(mline.getC_BPartner_ID());
				tline.setUY_TR_PackageType_ID(mline.getUY_TR_PackageType_ID());
				tline.setWeight2(mline.getWeight2());
				tline.setUY_TR_Crt_ID(mline.getUY_TR_Crt_ID());
				
				//if (this.getUY_TR_Driver_ID() > 0) tline.setUY_TR_Driver_ID(this.getUY_TR_Driver_ID());
				//if (this.getM_Warehouse_ID() > 0) tline.setM_Warehouse_ID(this.getM_Warehouse_ID());
				
				tline.saveEx();
			
			}
			
			// El usuario pudo haber cambiado la asociacion de conductor en vehiculo
			message = this.executeChangeDriver(this.getTractor_ID_2());

			// Si tengo que aplicar nueva orden lo hago
			if (applyOrder){
				if (!torder.processIt(ACTION_Apply)){
					message = torder.getProcessMsg();	
					if ((message == null) || (message.equalsIgnoreCase(""))){
						message = "No fue posible Aplicar documento de Orden de Transporte.";
					}
					return message;
				}
				
				torder.saveEx();
				
				// Actualizo orden de transporte en este documento para nuevo vehiculo
				DB.executeUpdateEx(" update uy_tr_loadmonitor set uy_tr_transorder_id_2 = " + torder.get_ID() + 
						           " where uy_tr_loadmonitor_id =" + this.get_ID(), get_TrxName());
				
			}

			// Si no todas las lineas de la orden fueron seleccionadas y utilzadas en esta carga,
			if (mlines.size() < oldOrder.getLines().size()){
				// Elimino las lineas marcadas de la orden
				for (MTRTransOrderLine lindel: otLinesToDelete){
					lindel.deleteEx(true);
				}
			}
			
			// Si todas los crts de la orden de transporte del vehiculo viejo estan con marca de fin de trazabilidad
			// Completo esta orden
			if (unloadAll){
				if (oldOrder.isAllCrtsEndTrack()){
					if (!oldOrder.processIt(ACTION_Complete)){
						message = oldOrder.getProcessMsg();
					}
				}
			}
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}	
				
		return message;
	}

	
	/***
	 * Ejecuta acciones de carga asociada con carga de mercaderias. 
	 * OpenUp Ltda. Issue #1405 
	 * @author Gabriel Vila - 11/12/2014
	 * @see
	 * @param sts
	 * @return
	 */
	private String executeLoadProduct(MTRLoadStatus sts) {

		String message = null;
		
		try {
			if (!sts.isLoadProduct()){
				message = "Accion de Carga seleccionada no permite Carga de Mercaderias.";
				return message;
			}
			
			// Obtengo lineas cargadas manualmente en este documento.
			List<MTRLoadMonitorLine> lines = this.getManualLines();
			
			// Si no tengo lineas manuales cargadas en este documento aviso y no proceso nada
			if (lines.size() <= 0){
				message = "Debe seleccionar al menos una linea para poder Completar el Documento.";
				return message;
			}
			
			MTRTransOrder torder = null;
			boolean applyOrder = false;
			
			// Si estoy cargando mercaderia en una OT nueva
			if (this.getUY_TR_TransOrder_ID() <= 0){
				
				// Creo nueva OT
				torder = new MTRTransOrder(getCtx(), 0, get_TrxName());
				torder.setDateTrx(this.getDateTrx());
				torder.setDescription(this.getDescription());
				torder.setC_DocType_ID(MDocType.forValue(getCtx(), "transorder", null).get_ID());
				torder.setRemolque_ID(this.getRemolque_ID());
				torder.setC_Currency_ID(this.getC_Currency_ID());
				torder.setTotalAmt(this.getTotalAmt());
				torder.setIsOwn(this.isOwn());
				torder.setIsLastre(false);
				torder.setUY_TR_Truck_ID(this.getUY_TR_Truck_ID());
				torder.setUY_Ciudad_ID(this.getUY_Ciudad_ID());
				torder.setUY_Ciudad_ID_1(this.getUY_Ciudad_ID_1());
				torder.setIsAssociated(this.isAssociated());
				torder.setUY_TR_TruckType_ID(this.getUY_TR_TruckType_ID());

				if (this.getUY_TR_Truck_ID_2() > 0) torder.setUY_TR_Truck_ID_Aux(this.getUY_TR_Truck_ID_2());
				if (this.getUY_TR_TruckType_ID_2() > 0) torder.setUY_TR_TruckType_ID_2(this.getUY_TR_TruckType_ID_2());
				
				// Si en carga de mercaderia selecciona un nuevo conductor, la orden de trans. queda con el nuevo conductor.
				if (this.getUY_TR_Driver_ID_2() > 0){
					torder.setUY_TR_Driver_ID(this.getUY_TR_Driver_ID_2());
				}
				else{
					// Si tengo conductor actual, la orden va con el conductor actual
					if (this.getUY_TR_Driver_ID() > 0) torder.setUY_TR_Driver_ID(this.getUY_TR_Driver_ID());	
				}
				
				
				if (this.getTractor_ID() > 0) torder.setTractor_ID(this.getTractor_ID());
				if (this.getC_Currency_ID_2() > 0) torder.setC_Currency_ID_2(this.getC_Currency_ID_2());
				
				torder.setIsManual(false);
				torder.setTotalWeight(this.getTotalWeight());
				torder.setPayAmt(this.getPayAmt());
				torder.saveEx();
				
				applyOrder = true;
				
			}
			else{
				// Tomo modelo de la seleccionada por el usuario
				torder = (MTRTransOrder)this.getUY_TR_TransOrder();
				
				boolean actualizar = false;
				
				// Si el documento esta reactivado, actualizo datos del cabezal de la orden
				if (this.getDocStatus().equalsIgnoreCase(DocumentEngine.STATUS_InProgress) || this.getDocStatus().equalsIgnoreCase(DocumentEngine.STATUS_Invalid)){
					if (torder.getUY_Ciudad_ID_1() != this.getUY_Ciudad_ID_1()){
						torder.setUY_Ciudad_ID_1(this.getUY_Ciudad_ID_1());
						actualizar = true;
					}
					if (torder.getUY_Ciudad_ID() != this.getUY_Ciudad_ID()){
						torder.setUY_Ciudad_ID(this.getUY_Ciudad_ID());
						actualizar = true;
					}
					
					// Me aseguro de atualizar el monto de vale flete
					if (this.getC_Currency_ID_2() > 0){
						torder.setC_Currency_ID_2(this.getC_Currency_ID_2());
						torder.setPayAmt(this.getPayAmt());
						actualizar = true;
					}
					
					//actualizo conductor
					if(this.getUY_TR_Driver_ID_2() > 0){
						
						if(torder.getUY_TR_Driver_ID() != this.getUY_TR_Driver_ID_2()) torder.setUY_TR_Driver_ID(this.getUY_TR_Driver_ID_2());
						actualizar = true;
					}
								
					if (actualizar){
						torder.saveEx();
					}
				}
			}
			
			// Proceso CRTS cargados manualmente en este documento			
			for (MTRLoadMonitorLine line: lines){
				if (line.isSelected()){
					
					MTRTransOrderLine tline = MTRTransOrderLine.forTransOrderAndCrt(getCtx(), get_TrxName(), torder.get_ID(), line.getUY_TR_Crt_ID());
					
					if(tline==null) tline = new MTRTransOrderLine(getCtx(), 0, get_TrxName());
		
					//tline = new MTRTransOrderLine(getCtx(), 0, get_TrxName());
					tline.setUY_TR_TransOrder_ID(torder.get_ID());
					tline.setUY_TR_Trip_ID(line.getUY_TR_Trip_ID());
					tline.setWeight(line.getWeight());
					tline.setVolume(line.getVolume());
					tline.setQtyPackage(line.getQtyPackage());
					tline.setAmount(line.getAmount());
					tline.setC_Currency_ID(line.getC_Currency_ID());
					tline.setProductAmt(line.getProductAmt());
					tline.setUY_TR_Truck_ID(this.getUY_TR_Truck_ID());
					tline.setEndTrackStatus(false);
					tline.setIsDelivered(false);
					tline.setC_BPartner_ID(line.getC_BPartner_ID());
					tline.setUY_TR_PackageType_ID(line.getUY_TR_PackageType_ID());
					tline.setWeight2(line.getWeight2());
					tline.setUY_TR_Crt_ID(line.getUY_TR_Crt_ID());
					tline.setUY_TR_LoadMonitorLine_ID(line.get_ID());
					
					if (this.getUY_TR_Driver_ID() > 0) tline.setUY_TR_Driver_ID(this.getUY_TR_Driver_ID());
					if (this.getM_Warehouse_ID() > 0) tline.setM_Warehouse_ID(this.getM_Warehouse_ID());
					
					tline.saveEx();
					
					// Si estoy cargando mercaderia desde un almacen
					if ((this.getTypeLoad() != null) && (this.getTypeLoad().equalsIgnoreCase(X_UY_TR_LoadMonitor.TYPELOAD_ALMACENPROPIO))){

						// Cargo mercaderia desde el almacen y por lo tanto baja stock del almacen
						MTRStock stock = new MTRStock(getCtx(), 0, get_TrxName());
						stock.setAD_User_ID(Env.getAD_User_ID(getCtx()));
						stock.setDateTrx(this.getDateTrx());
						stock.setMovementDate(this.getDateTrx());
						
						MPeriod period = MPeriod.get(getCtx(), this.getDateTrx(), 0);
						if ((period != null) && (period.get_ID() > 0)) stock.setC_Period_ID(period.get_ID());
						
						stock.setsign(Env.ONE.negate());
						stock.setM_Warehouse_ID(this.getM_Warehouse_ID());
						stock.setM_Locator_ID(MLocator.getDefault((MWarehouse)this.getM_Warehouse()).get_ID());
						stock.setUY_TR_LoadMonitor_ID(this.get_ID());
						stock.setUY_TR_LoadMonitorLine_ID(line.get_ID());
						stock.setUY_TR_TransOrder_ID(torder.get_ID());
						stock.setUY_TR_TransOrderLine_ID(tline.get_ID());
						stock.setC_BPartner_ID(line.getC_BPartner_ID());
						stock.setUY_TR_Crt_ID(line.getUY_TR_Crt_ID());
						stock.setUY_TR_Trip_ID(line.getUY_TR_Trip_ID());
						
						// Saca del almacen el monto digitado por el usuario
						stock.setAmount(line.getAmount());
						stock.setProductAmt(line.getProductAmt());
						stock.setQtyPackage(line.getQtyPackage());
						stock.setVolume(line.getVolume());
						stock.setWeight(line.getWeight());
						stock.setWeight2(line.getWeight2());
						//stock.setUY_TR_PackageType_ID(line.getUY_TR_PackageType_ID());
						stock.setC_Currency_ID(line.getC_Currency_ID());
						stock.saveEx();
						
						//registro en tabla de distribucion de expediente y proforma
						MTRTripQty tripQty = MTRTripQty.forWarehouseCrt(getCtx(),this.getM_Warehouse_ID(), line.getUY_TR_Crt_ID(),get_TrxName());
						MTRInvoiceFreightAmt freightAmt = MTRInvoiceFreightAmt.forWarehouseCrt(this.getM_Warehouse_ID(), line.getUY_TR_Crt_ID(), get_TrxName());
						
						MTRCrt crt = (MTRCrt)line.getUY_TR_Crt();//instancio el CRT
						MInvoice inv = null;
						
						if(crt.getC_Invoice_ID()>0) inv = (MInvoice)crt.getC_Invoice();//instancio la proforma
						
						if(tripQty!=null && tripQty.get_ID()>0){
							
							tripQty.setUY_TR_Trip_ID(line.getUY_TR_Trip_ID());
							tripQty.setUY_TR_Crt_ID(line.getUY_TR_Crt_ID());
							tripQty.setWeight(tripQty.getWeight().subtract(line.getWeight()));
							tripQty.setWeight2(tripQty.getWeight2().subtract(line.getWeight2()));
							tripQty.setVolume(tripQty.getVolume().subtract(line.getVolume()));
							tripQty.setQtyPackage(tripQty.getQtyPackage().subtract(line.getQtyPackage()));
							tripQty.setC_Currency_ID(line.getC_Currency_ID());
							tripQty.setProductAmt(tripQty.getProductAmt().subtract(line.getProductAmt()));
							tripQty.setM_Warehouse_ID(this.getM_Warehouse_ID());
							tripQty.setUY_TR_LoadMonitorLine_ID(line.get_ID());
							tripQty.setUY_TR_Stock_ID(stock.get_ID());
							//tripQty.setUY_TR_TransOrder_ID(torder.get_ID());
							//tripQty.setUY_TR_TransOrderLine_ID(tline.get_ID());
							
							if(tripQty.getQtyPackage().compareTo(Env.ZERO)==0){
								
								tripQty.deleteEx(true);
								
							} else tripQty.saveEx();			
							
						} 
						
						if(inv!=null && inv.get_ID()>0){
							
							if(freightAmt!=null && freightAmt.get_ID()>0){
								
								freightAmt.setC_Invoice_ID(inv.get_ID());
								freightAmt.setAmount(freightAmt.getAmount().subtract(line.getAmount()));
								freightAmt.setUY_TR_Crt_ID(line.getUY_TR_Crt_ID());
								freightAmt.setWeight(freightAmt.getWeight().subtract(line.getWeight()));
								freightAmt.setWeight2(freightAmt.getWeight2().subtract(line.getWeight2()));
								freightAmt.setVolume(freightAmt.getVolume().subtract(line.getVolume()));
								freightAmt.setQtyPackage(freightAmt.getQtyPackage().subtract(line.getQtyPackage()));
								freightAmt.setC_Currency_ID(line.getC_Currency_ID());
								freightAmt.setC_Currency2_ID(inv.getC_Currency_ID());
								freightAmt.setProductAmt(freightAmt.getProductAmt().subtract(line.getProductAmt()));
								freightAmt.setM_Warehouse_ID(this.getM_Warehouse_ID());
								freightAmt.setUY_TR_LoadMonitorLine_ID(line.get_ID());
								freightAmt.setUY_TR_Stock_ID(stock.get_ID());
								//freightAmt.setUY_TR_TransOrder_ID(torder.get_ID());
								//freightAmt.setUY_TR_TransOrderLine_ID(tline.get_ID());
				
								if(freightAmt.getAmount().compareTo(Env.ZERO)==0){
									
									freightAmt.deleteEx(true);
									
								} else freightAmt.saveEx();						
								
							}							
							
						}						
						
					}
				}
			}

			// El usuario pudo haber cambiado la asociacion de vehiculos y por lo tanto debo procesar un posible enganche
			message = this.executeChangeTruck();

			// El usuario pudo haber cambiado la asociacion de conductor en vehiculo
			message = this.executeChangeDriver(this.getTractor_ID());

			// Si tengo que aplicar nueva orden lo hago
			if (applyOrder){
				if (!torder.processIt(ACTION_Apply)){
					message = torder.getProcessMsg();	
					if ((message == null) || (message.equalsIgnoreCase(""))){
						message = "No fue posible Aplicar documento de Orden de Transporte.";
					}
					return message;
				}
				
				torder.saveEx();
				
				// Actualizo orden de transporte en este documento.
				DB.executeUpdateEx(" update uy_tr_loadmonitor set uy_tr_transorder_id = " + torder.get_ID() + 
						           " where uy_tr_loadmonitor_id =" + this.get_ID(), get_TrxName());
				
			}
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		}	
				
		return message;
	}
	
	
	/***
	 * Ejecuta accion de descarga de mercaderias en almacen sin orden de transporte y vehiculos asociados.
	 * Es por ejemplo para descargas que un cliente hace directamente en un almacen propio sin coleta.
	 * OpenUp Ltda. Issue #1405 
	 * @author Gabriel Vila - 26/12/2014
	 * @see
	 * @return
	 */
	private String executeProductToWarehouse() {

		String message = null;
		
		try {

			// Si no tengo almacen aviso
			if (this.getM_Warehouse_ID() <= 0){
				message = "Debe seleccionar Almacen donde Descarga Mercaderías.";
				return message;
			}
			
			// Obtengo lineas cargadas manualmente en este documento.
			List<MTRLoadMonitorLine> lines = this.getSelectedLines();
			
			// Si no tengo lineas cargadas aviso y salgo
			if (lines.size() <= 0){
				message = "Debe seleccionar al menos una linea para poder Completar el Documento.";
				return message;
			}
			
			// Proceso CRTS cargados en este documento			
			for (MTRLoadMonitorLine line: lines){

					// Descargo mercaderias de este crt en almacen
					MTRStock stock = new MTRStock(getCtx(), 0, get_TrxName());
					stock.setAD_User_ID(Env.getAD_User_ID(getCtx()));
					stock.setDateTrx(this.getDateTrx());
					stock.setMovementDate(this.getDateTrx());
					
					MPeriod period = MPeriod.get(getCtx(), this.getDateTrx(), 0);
					if ((period != null) && (period.get_ID() > 0)) stock.setC_Period_ID(period.get_ID());
					
					stock.setsign(Env.ONE);
					stock.setM_Warehouse_ID(this.getM_Warehouse_ID());
					stock.setM_Locator_ID(MLocator.getDefault((MWarehouse)this.getM_Warehouse()).get_ID());
					stock.setUY_TR_LoadMonitor_ID(this.get_ID());
					stock.setUY_TR_LoadMonitorLine_ID(line.get_ID());
					stock.setC_BPartner_ID(line.getC_BPartner_ID());
					stock.setUY_TR_Crt_ID(line.getUY_TR_Crt_ID());
					stock.setUY_TR_Trip_ID(line.getUY_TR_Trip_ID());
					
					// Sube en almacen el monto digitado por el usuario
					stock.setAmount(line.getAmount());
					stock.setProductAmt(line.getProductAmt());
					stock.setQtyPackage(line.getQtyPackage());
					stock.setVolume(line.getVolume());
					stock.setWeight(line.getWeight());
					stock.setWeight2(line.getWeight2());
					//stock.setUY_TR_PackageType_ID(line.getUY_TR_PackageType_ID());
					stock.setC_Currency_ID(line.getC_Currency_ID());
					stock.saveEx();
					
					//registro en tabla de distribucion de expediente y proforma
					MTRTripQty tripQty = MTRTripQty.forWarehouseCrt(getCtx(),this.getM_Warehouse_ID(), line.getUY_TR_Crt_ID(),get_TrxName());
					MTRInvoiceFreightAmt freightAmt = MTRInvoiceFreightAmt.forWarehouseCrt(this.getM_Warehouse_ID(), this.getUY_TR_Crt_ID(), get_TrxName());
					
					MTRCrt crt = (MTRCrt)line.getUY_TR_Crt();//instancio el CRT
					MInvoice inv = null;
					
					if(crt.getC_Invoice_ID()>0) inv = (MInvoice)crt.getC_Invoice();//instancio la proforma
					
					if(tripQty!=null && tripQty.get_ID()>0){
						
						tripQty.setUY_TR_Trip_ID(line.getUY_TR_Trip_ID());
						tripQty.setUY_TR_Crt_ID(line.getUY_TR_Crt_ID());
						tripQty.setWeight(line.getWeight().add(tripQty.getWeight()));
						tripQty.setWeight2(line.getWeight2().add(tripQty.getWeight2()));
						tripQty.setVolume(line.getVolume().add(tripQty.getVolume()));
						tripQty.setQtyPackage(line.getQtyPackage().add(tripQty.getQtyPackage()));
						tripQty.setC_Currency_ID(line.getC_Currency_ID());
						tripQty.setProductAmt(line.getProductAmt().add(tripQty.getProductAmt()));
						tripQty.setM_Warehouse_ID(this.getM_Warehouse_ID());
						tripQty.setUY_TR_LoadMonitorLine_ID(line.get_ID());
						tripQty.setUY_TR_Stock_ID(stock.get_ID());
						tripQty.saveEx();			
						
					} else {
						
						tripQty = new MTRTripQty(getCtx(), 0, get_TrxName());
						tripQty.setUY_TR_Trip_ID(line.getUY_TR_Trip_ID());
						tripQty.setUY_TR_Crt_ID(line.getUY_TR_Crt_ID());
						tripQty.setWeight(line.getWeight());
						tripQty.setWeight2(line.getWeight2());
						tripQty.setVolume(line.getVolume());
						tripQty.setQtyPackage(line.getQtyPackage());
						tripQty.setC_Currency_ID(line.getC_Currency_ID());
						tripQty.setProductAmt(line.getProductAmt());
						tripQty.setM_Warehouse_ID(this.getM_Warehouse_ID());
						tripQty.setUY_TR_LoadMonitorLine_ID(line.get_ID());
						tripQty.setUY_TR_Stock_ID(stock.get_ID());
						tripQty.saveEx();							
					}
					
					//si el CRT tiene proforma asociada
					if(inv!=null && inv.get_ID()>0){

						if(freightAmt!=null && freightAmt.get_ID()>0){

							freightAmt.setC_Invoice_ID(inv.get_ID());
							freightAmt.setAmount(line.getAmount());
							freightAmt.setUY_TR_Crt_ID(line.getUY_TR_Crt_ID());
							freightAmt.setWeight(line.getWeight().add(tripQty.getWeight()));
							freightAmt.setWeight2(line.getWeight2().add(tripQty.getWeight2()));
							freightAmt.setVolume(line.getVolume().add(tripQty.getVolume()));
							freightAmt.setQtyPackage(line.getQtyPackage().add(tripQty.getQtyPackage()));
							freightAmt.setC_Currency_ID(line.getC_Currency_ID());
							freightAmt.setC_Currency2_ID(inv.getC_Currency_ID());
							freightAmt.setProductAmt(line.getProductAmt().add(tripQty.getProductAmt()));
							freightAmt.setM_Warehouse_ID(this.getM_Warehouse_ID());
							freightAmt.setUY_TR_LoadMonitorLine_ID(line.get_ID());
							freightAmt.setUY_TR_Stock_ID(stock.get_ID());
							freightAmt.saveEx();						

						} else {

							freightAmt = new MTRInvoiceFreightAmt(getCtx(), 0, get_TrxName());
							freightAmt.setC_Invoice_ID(inv.get_ID());
							freightAmt.setAmount(line.getAmount());
							freightAmt.setUY_TR_Crt_ID(line.getUY_TR_Crt_ID());
							freightAmt.setWeight(line.getWeight().add(tripQty.getWeight()));
							freightAmt.setWeight2(line.getWeight2().add(tripQty.getWeight2()));
							freightAmt.setVolume(line.getVolume().add(tripQty.getVolume()));
							freightAmt.setQtyPackage(line.getQtyPackage().add(tripQty.getQtyPackage()));
							freightAmt.setC_Currency_ID(line.getC_Currency_ID());
							freightAmt.setC_Currency2_ID(inv.getC_Currency_ID());
							freightAmt.setProductAmt(line.getProductAmt().add(tripQty.getProductAmt()));
							freightAmt.setM_Warehouse_ID(this.getM_Warehouse_ID());
							freightAmt.setUY_TR_LoadMonitorLine_ID(line.get_ID());
							freightAmt.setUY_TR_Stock_ID(stock.get_ID());
							freightAmt.saveEx();
							
						}
					}				
					
					
			}

		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}	
				
		return message;
	}
	

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#voidIt()
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 16/11/2014
	 * @see
	 * @return
	 */
	@Override
	public boolean voidIt() {

		// Before Void
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_VOID);
		if (this.processMsg != null)
			return false;

		// Validaciones necesarias para reactivar un movimiento
		MTRTransOrder torder = null;

		if (this.getUY_TR_TransOrder_ID_2() > 0){
			torder = new MTRTransOrder(getCtx(), this.getUY_TR_TransOrder_ID_2(), get_TrxName());
		}
		else{
			if (this.getUY_TR_TransOrder_ID() > 0){
				torder = new MTRTransOrder(getCtx(), this.getUY_TR_TransOrder_ID(), get_TrxName());	
			}
		}
		
		String sql = "";
		
		if (torder != null){
			if (torder.get_ID() > 0){

				// Si la orden de transporte asociada ya esta completa, no puedo hacer nada.
				if (torder.getDocStatus().equalsIgnoreCase(DocumentEngine.STATUS_Completed)){
					this.processMsg = "No es posible anular documento ya que la Orden de Transporte asociada esta Completa.";
					return false;
				}
				
				// Verifico que esta orden de transporte no tengo un movimiento posterior a este.
				// Si se da el caso, aviso para que primero se anule el movimiento posterior.
				sql = " select documentno " +
					  " from uy_tr_loadmonitor " +
					  " where (uy_tr_transorder_id =? OR uy_tr_transorder_id_2 =?) " +
					  " and created >? and docstatus <> 'VO'"; 
				String nroDoc = DB.getSQLValueStringEx(null, sql, new Object[]{torder.get_ID(), torder.get_ID(), this.getCreated()});
				if (nroDoc != null){
					this.processMsg = " No es posible anular este documento ya que la Orden de Transporte tiene el movimiento posterior Numero: " + nroDoc +
									  "\n Anule el movimiento posterior para poder anular este movimiento.";
					return false;
				}

				// Verifico que no haya un MIC enviado a aduana para esta orden
				sql = " select documentno " +
					  " from uy_tr_mic " +
					  " where uy_tr_transorder_id =? " +
					  " and NroMic is not null";

				String nroMic = DB.getSQLValueStringEx(null, sql, new Object[]{torder.get_ID()});
				if (nroMic != null){
					this.processMsg = " No es posible anular este documento ya que la Orden de Transporte tiene un MIC enviado a Aduana con el Numero Interno: " + nroMic;
					return false;
				}

			}
		}
		
		// Si todas las lineas de la orden son originadas por este movimiento
		if(torder!=null){
			boolean deleteOT = false;
			if (torder.getLines().size() == torder.getLinesForLoadMonitor(this.get_ID()).size()){
				deleteOT = true;  // Marco para que se borre 
			}

			// Si debo eliminar la orden de transporte entera o si solo algunas lineas originadas por este movimiento
			if (deleteOT){
				DB.executeUpdateEx(" delete from uy_tr_mic cascade where uy_tr_transorder_id =" + torder.get_ID(), get_TrxName() + " and nromic is null");
				DB.executeUpdateEx(" delete from uy_tr_remito cascade where uy_tr_transorder_id =" + torder.get_ID(), get_TrxName());
				DB.executeUpdateEx(" update uy_tr_loadmonitor set uy_tr_transorder_id = null where uy_tr_loadmonitor_id=" + this.get_ID(), null);
				DB.executeUpdateEx(" delete from uy_tr_tripqty where uy_tr_transorder_id =" + torder.get_ID(), get_TrxName());
				DB.executeUpdateEx(" delete from uy_tr_invoicefreightamt where uy_tr_transorder_id =" + torder.get_ID(), get_TrxName());		
				//DB.executeUpdateEx(" delete from uy_tr_transorder cascade where uy_tr_transorder_id =" + torder.get_ID(), get_TrxName());
								
				try {
					torder.processIt(DocumentEngine.ACTION_Void);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					throw new AdempiereException(e.getMessage());
				}
				
				torder.deleteEx(true, get_TrxName());				
				
			}
			else{
				DB.executeUpdateEx(" delete from uy_tr_tripqty where uy_tr_transorderline_id in (select uy_tr_transorderline_id from uy_tr_transorderline where" +
						" uy_tr_transorder_id = " + torder.get_ID() + " and uy_tr_loadmonitorline_id in " +
						" (select uy_tr_loadmonitorline_id from uy_tr_loadmonitorline where uy_tr_loadmonitor_id =" + this.get_ID() + "))", get_TrxName());

				DB.executeUpdateEx(" delete from uy_tr_invoicefreightamt where uy_tr_transorderline_id in (select uy_tr_transorderline_id from uy_tr_transorderline where" +
						" uy_tr_transorder_id = " + torder.get_ID() + " and uy_tr_loadmonitorline_id in " +
						" (select uy_tr_loadmonitorline_id from uy_tr_loadmonitorline where uy_tr_loadmonitor_id =" + this.get_ID() + "))", get_TrxName());

				DB.executeUpdateEx(" delete from uy_tr_transorderline where uy_tr_transorder_id = " + torder.get_ID() +
						" and uy_tr_loadmonitorline_id in " +
						" (select uy_tr_loadmonitorline_id from uy_tr_loadmonitorline where uy_tr_loadmonitor_id =" + this.get_ID() + ")", get_TrxName());
			}
		} else {
			
			DB.executeUpdateEx(" delete from uy_tr_tripqty where uy_tr_loadmonitorline_id in (select uy_tr_loadmonitorline_id from " +
					" uy_tr_loadmonitorline where uy_tr_loadmonitor_id = " + this.get_ID() + ")", get_TrxName());
			DB.executeUpdateEx(" delete from uy_tr_invoicefreightamt where uy_tr_loadmonitorline_id in (select uy_tr_loadmonitorline_id from " +
					" uy_tr_loadmonitorline where uy_tr_loadmonitor_id = " + this.get_ID() + ")", get_TrxName());
			
		}
		
		// Si estoy cargando mercaderia desde un almacen
		if ((this.getTypeLoad() != null) && ((this.getTypeLoad().equalsIgnoreCase(X_UY_TR_LoadMonitor.TYPELOAD_ALMACENPROPIO)) || 
				(this.getTypeLoad().equalsIgnoreCase(X_UY_TR_LoadMonitor.TYPELOAD_CLIENTE)))){
			
			// Elimino registros creados en stock de mercaderia
			DB.executeUpdateEx(" delete from uy_tr_stock where uy_tr_loadmonitor_id = " + this.get_ID(), get_TrxName());
		}
		
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
	 * @author Gabriel Vila - 16/11/2014
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
	 * @author Gabriel Vila - 16/11/2014
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
	 * @author Gabriel Vila - 16/11/2014
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
	 * @author Gabriel Vila - 16/11/2014
	 * @see
	 * @return
	 */
	@Override
	public boolean reActivateIt() {

		// Before reActivate
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_REACTIVATE);
		if (this.processMsg != null)
			return false;

		String sql = "";
		
		// Validaciones necesarias para reactivar un movimiento
		MTRTransOrder torder = null;

		if (this.getUY_TR_TransOrder_ID_2() > 0){
			torder = new MTRTransOrder(getCtx(), this.getUY_TR_TransOrder_ID_2(), get_TrxName());
		}
		else{
			if (this.getUY_TR_TransOrder_ID() > 0){
				torder = new MTRTransOrder(getCtx(), this.getUY_TR_TransOrder_ID(), get_TrxName());	
			}
		}
		
		if (torder != null){
			if (torder.get_ID() > 0){

				// Si la orden de transporte asociada ya esta completa, no puedo hacer nada.
				if (torder.getDocStatus().equalsIgnoreCase(DocumentEngine.STATUS_Completed)){
					this.processMsg = "No es posible reactivar documento ya que la Orden de Transporte asociada esta Completa.";
					return false;
				}
				
				// Verifico que esta orden de transporte no tengo un movimiento posterior a este.
				// Si se da el caso, aviso para que primero se anule el movimiento posterior.
				sql = " select documentno " +
					  " from uy_tr_loadmonitor " +
					  " where (uy_tr_transorder_id =? OR uy_tr_transorder_id_2 =?) " +
					  " and created >? and docstatus <> 'VO'"; 
				String nroDoc = DB.getSQLValueStringEx(null, sql, new Object[]{torder.get_ID(), torder.get_ID(), this.getCreated()});
				if (nroDoc != null){
					this.processMsg = " No es posible reactivar documento ya que la Orden de Transporte tiene el movimiento posterior Numero: " + nroDoc +
									  "\n Anule el movimiento posterior para poder reactivar este movimiento.";
					return false;
				}
			}
		}
		
		// After reActivate
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,	ModelValidator.TIMING_AFTER_REACTIVATE);
		if (this.processMsg != null)
			return false;
		
		// Seteo estado de documento
		this.setProcessed(false);
		this.setDocStatus(DocumentEngine.STATUS_InProgress);
		this.setDocAction(DocumentEngine.ACTION_Complete);

		return true;

	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getSummary()
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 16/11/2014
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
	 * @author Gabriel Vila - 16/11/2014
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
	 * @author Gabriel Vila - 16/11/2014
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
	 * @author Gabriel Vila - 16/11/2014
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
	 * @author Gabriel Vila - 16/11/2014
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
	 * @author Gabriel Vila - 16/11/2014
	 * @see
	 * @return
	 */
	@Override
	public BigDecimal getApprovalAmt() {
		// TODO Auto-generated method stub
		return null;
	}

	/***
	 * Obtiene y retorna lineas de este documento.
	 * OpenUp Ltda. Issue #1625 
	 * @author Gabriel Vila - 16/11/2014
	 * @see
	 * @return
	 */
	public List<MTRLoadMonitorLine> getLines(){
		
		String whereClause = X_UY_TR_LoadMonitorLine.COLUMNNAME_UY_TR_LoadMonitor_ID + "=" + this.get_ID();
		
		List<MTRLoadMonitorLine> lines = new Query(getCtx(), I_UY_TR_LoadMonitorLine.Table_Name, whereClause, get_TrxName())
		.list();
		
		return lines;
		
	}

	/***
	 * Obtiene y retorna lineas de este documento cargadas manualmente.
	 * OpenUp Ltda. Issue #1405 
	 * @author Gabriel Vila - 16/11/2014
	 * @see
	 * @return
	 */
	public List<MTRLoadMonitorLine> getManualLines(){
		
		String whereClause = X_UY_TR_LoadMonitorLine.COLUMNNAME_UY_TR_LoadMonitor_ID + "=" + this.get_ID() +
				" AND " + X_UY_TR_LoadMonitorLine.COLUMNNAME_IsManual + "='Y' "; 
		
		List<MTRLoadMonitorLine> lines = new Query(getCtx(), I_UY_TR_LoadMonitorLine.Table_Name, whereClause, get_TrxName())
		.list();
		
		return lines;
		
	}

	
	/***
	 * Obtiene y retorna lineas seleccionadas de este documento.
	 * OpenUp Ltda. Issue #1625 
	 * @author Gabriel Vila - 16/11/2014
	 * @see
	 * @return
	 */
	public List<MTRLoadMonitorLine> getSelectedLines(){
		
		String whereClause = X_UY_TR_LoadMonitorLine.COLUMNNAME_UY_TR_LoadMonitor_ID + "=" + this.get_ID() +
				" AND " + X_UY_TR_LoadMonitorLine.COLUMNNAME_IsSelected + "='Y'";
		
		List<MTRLoadMonitorLine> lines = new Query(getCtx(), I_UY_TR_LoadMonitorLine.Table_Name, whereClause, get_TrxName())
		.list();
		
		return lines;
		
	}

	@Override
	protected boolean beforeDelete() {

		// No permito eliminar un registro en estado reactivado, ya que pierdo la asociacion movimiento-orden.
		if (this.getDocStatus().equalsIgnoreCase(DocumentEngine.STATUS_InProgress)){
			log.saveError(null, "No es posible borrar un movimiento que fue Reactivado.\n" +
						  "Si quiere eliminarlo, primero debe completarlo y luego Anularlo.");
			return false;
		}
		
		return true;
	}

	 
	
}
