/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 21/01/2014
 */
package org.openup.model;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MActivity;
import org.compiere.model.MDocType;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MLocator;
import org.compiere.model.MPaymentTerm;
import org.compiere.model.MProduct;
import org.compiere.model.MTaxCategory;
import org.compiere.model.MWarehouse;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;


/**
 * org.openup.model - MTRServiceOrder
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Gabriel Vila - 21/01/2014
 * @see
 */
public class MTRServiceOrder extends X_UY_TR_ServiceOrder implements DocAction {

	private String processMsg = null;
	private boolean justPrepared = false;

	
	private static final long serialVersionUID = -6696199319756493387L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_TR_ServiceOrder_ID
	 * @param trxName
	 */
	public MTRServiceOrder(Properties ctx, int UY_TR_ServiceOrder_ID,
			String trxName) {
		super(ctx, UY_TR_ServiceOrder_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTRServiceOrder(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#processIt(java.lang.String)
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 21/01/2014
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
	 * @author Gabriel Vila - 21/01/2014
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
	 * @author Gabriel Vila - 21/01/2014
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
	 * @author Gabriel Vila - 21/01/2014
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
	 * @author Gabriel Vila - 21/01/2014
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
	 * @author Gabriel Vila - 21/01/2014
	 * @see
	 * @return
	 */
	@Override
	public boolean applyIt() {

		this.setStatusOrder(STATUSORDER_ENPROCESO);
		
		//OpenUp. Nicolas Sarlabos. 09/07/2015. #4451. Se comenta codigo.
		this.loadFailures(); //se cargan las fallas autorizadas del vehiculo
		//Fin OpenUp.
		
		//OpenUp. Nicolas Sarlabos. 12/08/2015. #4627.
		if(this.isOwn()) this.loadMaintenance(); //se cargan los mantenimientos expirados del vehiculo
		//Fin OpenUp.
		
		this.setDocStatus(DocumentEngine.STATUS_Applied);
		this.setDocAction(DocAction.ACTION_Complete);
		
		return true;

	}

	@Override
	protected boolean beforeSave(boolean newRecord) {

		if(is_ValueChanged("M_Warehouse_ID")){

			List<MTRServiceOrderProd> servprods = this.getProducts();

			if(servprods.size()>0) throw new AdempiereException("Debe eliminar las lineas de repuestos para poder cambiar el almacen");		

		}

		if(newRecord || is_ValueChanged("M_Warehouse_ID")){

			MLocator loc = MLocator.getDefault((MWarehouse)this.getM_Warehouse());

			if(loc != null) this.setM_Locator_ID(loc.get_ID());		

		}	
		
		return true;
	}

	/**Metodo que carga las fallas autorizadas del vehiculo.
	 * OpenUp Ltda. Issue #1620
	 * @author Nicolas Sarlabos - 14/05/2014
	 */
	private void loadFailures() {
	
		String sql = "";
		MTRServiceOrderFailure line = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;	
		
		try{
			
			sql = "select uy_tr_faultmanage_id" +
			      " from uy_tr_faultmanage" +
				  " where uy_tr_truck_id = " + this.getUY_TR_Truck_ID() +
				  " and docstatus = 'AP' and ad_org_id = " + this.getAD_Org_ID() +
				  " and ad_client_id = " + this.getAD_Client_ID();
			
			pstmt = DB.prepareStatement (sql, get_TrxName());
		    rs = pstmt.executeQuery();
		    
		    while(rs.next()){
		    
		    	line = new MTRServiceOrderFailure(getCtx(),0,get_TrxName());
		    	
		    	MTRFaultManage fault = new MTRFaultManage(getCtx(),rs.getInt("uy_tr_faultmanage_id"),get_TrxName());
		    	
		    	line.setUY_TR_ServiceOrder_ID(this.get_ID());
		    	line.setUY_TR_FaultManage_ID(fault.get_ID());
		    	line.setUY_TR_Failure_ID(fault.getUY_TR_Failure_ID());
		    	line.setDescription(fault.getDescription());
		    	
		    	line.saveEx();		    	
		    }			
			
		} catch (Exception e){
			throw new AdempiereException(e);
		}
		finally {
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}	
		
	}
		
	/**Metodo que carga las tareas de mantenimiento expiradas del vehiculo.
	 * OpenUp Ltda. Issue #1620
	 * @author Nicolas Sarlabos - 14/05/2014
	 */
	private void loadMaintenance() {
	
		String sql = "";
		MTRServiceOrderMaintain line = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;	
		
		try{
			
			sql = "select uy_tr_maintain_id, uy_tr_truckmaintain_id" +
			      " from uy_tr_truckmaintain" +
				  " where uy_tr_truck_id = " + this.getUY_TR_Truck_ID() +
				  " and (((qtykmnext - qtykm) <= 1000) or ((qtykmnext - " + this.getKilometros() + ") <= 1000))" +
				  " and ad_org_id = " + this.getAD_Org_ID() +
				  " and ad_client_id = " + this.getAD_Client_ID();
			
			pstmt = DB.prepareStatement (sql, get_TrxName());
		    rs = pstmt.executeQuery();
		    
		    while(rs.next()){
		    
		    	line = new MTRServiceOrderMaintain(getCtx(),0,get_TrxName());
		    			    	
		    	line.setUY_TR_ServiceOrder_ID(this.get_ID());
		    	line.setUY_TR_Maintain_ID(rs.getInt("uy_tr_maintain_id"));
		    	line.setUY_TR_TruckMaintain_ID(rs.getInt("uy_tr_truckmaintain_id"));
		    			    	
		    	line.saveEx();		    	
		    }			
			
		} catch (Exception e){
			throw new AdempiereException(e);
		}
		finally {
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}	
		
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#rejectIt()
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 21/01/2014
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
	 * @author Gabriel Vila - 21/01/2014
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
		
		//MTRTruck truck = (MTRTruck)this.getUY_TR_Truck();
		
		if(this.getKilometros() > 0){

			//if(this.getKilometros() >= truck.getQtyKm()){

				this.createReadKm(); //se crea lectura de kilometraje

			//} else throw new AdempiereException("Kilometraje ingresado no puede ser menor al actual");			

		} else throw new AdempiereException("Debe ingresar kilometraje actual del vehiculo");
		
		this.completeFaultManage(); //se completan las fallas asociadas a la orden de servicio
		
		this.updateMaintenanceTask(); //se actualizan las tareas asociadas al vehiculo
		
		if(this.isOwn()) this.createTruckHistory(); //se impacta en historial del vehiculo cuando el taller es propio
		
		if (this.isWarranty() && !this.isOwn())
			this.createTruckHistoryWarranty();	
		
		List<MTRServiceOrderProd> servprods = this.getProducts();
		
		//si se utilizaron repuestos, se genera el consumo
		if(servprods.size() > 0) this.generateConsume();
		
		this.setStatusOrder(STATUSORDER_CERRADA);

		// Timing After Complete		
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (this.processMsg != null)
			return DocAction.STATUS_Invalid;

		// Refresco atributos
		this.setDocAction(DocAction.ACTION_Close);
		this.setDocStatus(DocumentEngine.STATUS_Completed);
		this.setProcessing(false);
		this.setProcessed(true);
		
		return DocAction.STATUS_Completed;
	}

	/***
	 * OpenUp. Nicolas Sarlabos. 12/08/2014. ISSUE#2472
	 * Metodo que impacta en el historial del vehiculo si el taller es propio.
	 * 
	 */	
	private void createTruckHistory() {
		
		List<MTRServiceOrderMaintain> mLines = this.getMaintainLines(); //obtengo lineas de tareas de mantenimiento
		List<MTRServiceOrderFailure> fLines = this.getFailureLines(); //obtengo lineas de fallas
		
		for(MTRServiceOrderMaintain ml : mLines){

			if(ml.getUY_TR_Maintain_ID() > 0){
				
				//OpenUp. Nicolas Sarlabos. 17/08/2015. #4671.
				MTRMaintain mant = (MTRMaintain)ml.getUY_TR_Maintain();
				
				MTRTruckMaintainHistory line = new MTRTruckMaintainHistory(getCtx(), 0, get_TrxName());
				line.setUY_TR_Truck_ID(this.getUY_TR_Truck_ID());
				line.setUY_TR_ServiceOrder_ID(this.get_ID());
				line.setDescription(ml.getDescription());
				line.setUY_TR_Maintain_ID(mant.get_ID());
				if(mant.getUY_TR_Failure_ID()>0) line.setUY_TR_Failure_ID(mant.getUY_TR_Failure_ID());
				line.setIsOwn(true);
				line.saveEx();	
				//Fin OpenUp.
				
			}		
		}
		
		for(MTRServiceOrderFailure fl : fLines){
			
				MTRTruckRepair line = new MTRTruckRepair(getCtx(), 0, get_TrxName());
				line.setUY_TR_Truck_ID(this.getUY_TR_Truck_ID());
				line.setUY_TR_ServiceOrder_ID(this.get_ID());
				line.setDateOrdered(this.getDateTrx());
				line.setKilometros(this.getKilometros());
				line.setUY_TR_Failure_ID(fl.getUY_TR_Failure_ID());
				line.setIsOwn(true);
				
				if(fl.getDescription()!=null && !fl.getDescription().equalsIgnoreCase("")){
					
					line.setDescription(fl.getDescription());
					
				} else {
					
					MTRFailure fail = new MTRFailure(getCtx(),fl.getUY_TR_Failure_ID(),get_TrxName());
					
					line.setDescription(fail.getName());					
				}				
				
				line.saveEx();							
		}		
	}
		
	private void createTruckHistoryWarranty() {		//almaceno en el historial cuando esta garantía
		

		try{
			
			MTRServiceOrder order = new MTRServiceOrder(getCtx(),this.get_ID(),get_TrxName()); //obtengo orden de servicio
			MTRTruckRepair line = new MTRTruckRepair(getCtx(),0,get_TrxName()); 
			line.setUY_TR_Truck_ID(order.getUY_TR_Truck_ID());
			line.setDateOrdered(order.getDateTrx());
			line.setKilometros(order.getKilometros());
			line.setC_BPartner_ID(order.getC_BPartner_ID());
			line.setUY_TR_ServiceOrder_ID(order.get_ID());
			line.setDescription(this.getDescription());
			line.setIsOwn(false);
			
			line.saveEx();				
			
			} catch (Exception e) {
				throw new AdempiereException(e);
			}
	
	}

		
	
	
	
	
	/***
	 * OpenUp. Nicolas Sarlabos. 14/05/2014. ISSUE#1620
	 * Metodo que completa las fallas asociadas a esta orden de servicio.
	 * 
	 */
	private void completeFaultManage() {

		try{

			List<MTRServiceOrderFailure> fLines = this.getFailureLines(); //obtengo lineas de fallas

			for(MTRServiceOrderFailure fl : fLines){

				if (fl.getUY_TR_FaultManage_ID() > 0){//si esta asociada a un documento de gestion de fallas
					
					MTRFaultManage fm = new MTRFaultManage(getCtx(),fl.getUY_TR_FaultManage_ID(),get_TrxName());

					if(!fm.processIt(DocumentEngine.ACTION_Complete)){
						throw new AdempiereException("Error al completar gestion de falla N° " + fm.getDocumentNo());
					}			
				}
			}	

		} catch (Exception e){
			throw new AdempiereException(e);
		}
	}
	
	/***
	 * OpenUp. Nicolas Sarlabos. 14/05/2014. ISSUE#1620
	 * Metodo que actualiza las tareas de mantenimiento del vehiculo que se realizaron en esta orden de servicio.
	 * 
	 */
	private void updateMaintenanceTask() {

		try{

			List<MTRServiceOrderMaintain> mLines = this.getMaintainLines(); //obtengo lineas de tareas de mantenimiento
			
			MTRTruck truck = (MTRTruck)this.getUY_TR_Truck();//obtengo el vehiculo

			for(MTRServiceOrderMaintain ml : mLines){

				if(ml.getUY_TR_TruckMaintain_ID() > 0){
					
					MTRTruckMaintain mant = new MTRTruckMaintain(getCtx(),ml.getUY_TR_TruckMaintain_ID(),get_TrxName());
					mant.setIsExpired(false);
					mant.setDateLast(this.getDateAction());
					mant.setQtyKmLast(this.getKilometros());
					mant.setQtyKmNext(this.getKilometros() + mant.getKilometros());
					
					//OpenUp. Nicolas Sarlabos. 31/07/2015. #4635.
					//si kms del vehiculo es mayor a cero, y ademas es menor al ingresado en la orden
					if(truck.getQtyKm()>0 && this.getKilometros()>truck.getQtyKm()){
						
						mant.setQtyKm(this.getKilometros());
						
					} else if (truck.getQtyKm()==0) mant.setQtyKm(this.getKilometros());
					//Fin OpenUp. 
					
					mant.saveEx();					
				}				

			}	

		} catch (Exception e){
			throw new AdempiereException(e);
		}
	}

	/***
	 * OpenUp. Nicolas Sarlabos. 22/04/2014. ISSUE#1620
	 * Metodo que crea una lectura de kilometraje al completarse la orden de servicio.
	 * 
	 */
	private void createReadKm() {

		try {				
			
			//Guardo la toma de kilometraje actual		
			MTRReadKM read = new MTRReadKM(this.getCtx(), 0, this.get_TrxName());
			read.setAD_Client_ID(this.getAD_Client_ID());
			read.setAD_Org_ID(this.getAD_Org_ID());
			read.setC_DocType_ID(MDocType.forValue(this.getCtx(), "readkm", null).get_ID());
			read.setUY_TR_Truck_ID(this.getUY_TR_Truck_ID());		
			read.setDateAction(this.getDateAction());
			read.setKilometros(this.getKilometros());
			read.setIsValid(true);
			read.setIsManual(false);
			read.isHubodometro = false;
			read.saveEx();
			
			if (!read.processIt(DocumentEngine.ACTION_Complete)){
				throw new AdempiereException(read.getProcessMsg());
			}				
			
		} catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		}			
				
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#voidIt()
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 21/01/2014
	 * @see
	 * @return
	 */
	@Override
	public boolean voidIt() {

		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#closeIt()
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 21/01/2014
	 * @see
	 * @return
	 */
	@Override
	public boolean closeIt() {
		
		log.info(toString());
		// Before Close
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_CLOSE);
		if (this.processMsg != null)
			return false;		

		// After Close
		this.processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_CLOSE);
		if (this.processMsg != null)
			return false;

		setDocStatus(DOCSTATUS_Closed);
		setProcessing(false);
		setProcessed(true);
		setDocAction(DOCACTION_None);

		return true;
	}

	/***
	 * OpenUp. Nicolas Sarlabos. 20/11/2014. ISSUE#
	 * Metodo que realiza el consumo de stock de repuestos en la orden de servicio.
	 * 
	 */
	private void generateConsume() {
		
		MProductConsume con = null;
		MProductConsumeLine line = null;
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;	
		BigDecimal qtyAvailable = Env.ZERO;
		MProduct prod = null;
				
		try{		
			
			List<MTRServiceOrderProd> servprods = this.getProducts();
			
			for(MTRServiceOrderProd prodLine : servprods){
				
				if(con == null){
					
					//creo nuevo cabezal de consumo
			    	con = new MProductConsume (getCtx(),0,get_TrxName());
			    	con.setC_DocType_ID(MDocType.forValue(this.getCtx(), "prodconsume", null).get_ID());
					con.setDateTrx(TimeUtil.trunc(new Timestamp (System.currentTimeMillis()), TimeUtil.TRUNC_DAY));
					con.setMovementDate(TimeUtil.trunc(new Timestamp (System.currentTimeMillis()), TimeUtil.TRUNC_DAY));
					con.setM_Warehouse_ID(this.getM_Warehouse_ID());
					con.setM_Locator_ID(this.getM_Locator_ID());
					con.setUY_StockStatus_ID(this.getUY_StockStatus_ID());
					con.setAD_User_ID(Env.getContextAsInt(getCtx(), 0, "AD_User_ID"));
					con.setUY_TR_ServiceOrder_ID(this.get_ID());
					con.setUY_TR_Truck_ID(this.getUY_TR_Truck_ID());
					con.setDocStatus(DocumentEngine.STATUS_Drafted);
					con.setDocAction(DocumentEngine.ACTION_Complete);
					con.saveEx();					
				}
				
				//chequeo disponibilidad
				
				sql = "select stk_available(?, ?, ?, 0, 0, ?)";
				
				pstmt = DB.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, null);
				pstmt.setInt(1, prodLine.getM_Product_ID());
				pstmt.setInt(2, con.getM_Warehouse_ID());
				pstmt.setInt(3, con.getM_Locator_ID());
				pstmt.setTimestamp(4, con.getMovementDate());
				
				rs = pstmt.executeQuery ();				
				
				if(rs.next()){
					qtyAvailable = rs.getBigDecimal(1);
				}		
				
				prod = (MProduct)prodLine.getM_Product();
				
				if(qtyAvailable.compareTo(prodLine.getQtyRequired()) < 0) 
					throw new AdempiereException("La cantidad disponible para el producto '" + prod.getName() + "' es insuficiente (Requerida = " + prodLine.getQtyRequired().setScale(0, RoundingMode.HALF_UP) + ", Disponible = " + qtyAvailable.setScale(0, RoundingMode.HALF_UP) + ")");
								
				//creo nueva linea de consumo
				line = new MProductConsumeLine(getCtx(),0,get_TrxName());
				line.setUY_ProductConsume_ID(con.get_ID());
				line.setM_Product_ID(prodLine.getM_Product_ID());
				line.setM_Warehouse_ID(this.getM_Warehouse_ID());
				line.setM_Locator_ID(this.getM_Locator_ID());
				line.setMovementQty(prodLine.getQtyRequired());
				line.setQtyAvailable(prodLine.getQtyAvailable());
				line.setUY_StockStatus_ID(this.getUY_StockStatus_ID());
				line.setUY_TR_Failure_ID(prodLine.getUY_TR_Failure_ID());
				line.saveEx();				
			}
			
			//completo cabezal de consumo
			if (!con.processIt(DocumentEngine.ACTION_Complete)){
				throw new AdempiereException(con.getProcessMsg());
			}		    
			
		} catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		}		
		
	}
	
	/***
	 * OpenUp. Nicolas Sarlabos. 20/11/2014. ISSUE#
	 * Metodo que realiza el consumo de stock de repuestos en la orden de servicio para diferentes almacenes y ubicaciones.
	 * 
	 */
	/*private void generateConsume() {
		
		ResultSet rs = null;
		PreparedStatement pstmt = null;	
		MProductConsume con = null;
		MProductConsumeLine line = null;
		int warehouseID = 0;
		int locatorID = 0;
		
		try{
			
			String sql = "select uy_tr_serviceorderprod_id, m_warehouse_id, m_locator_id, m_product_id, qtyrequired" +
                         " from uy_tr_serviceorderprod" +
                         " where uy_tr_serviceorder_id = " + this.get_ID() +
                         " order by m_warehouse_id, m_locator_id";
			
			pstmt = DB.prepareStatement (sql, get_TrxName());
		    rs = pstmt.executeQuery();
		    
		    while(rs.next()){
		    	
		    	if(warehouseID != rs.getInt("m_warehouse_id") && locatorID != rs.getInt("m_locator_id")){
		    		
		    		//si el cabezal no es nulo, entonces lo completo
		    		if(con != null && con.get_ID() > 0){
		    			
		    			if (!con.processIt(DocumentEngine.ACTION_Complete)){
		    				throw new AdempiereException(con.getProcessMsg());
		    			}		
		    			
		    		}
		    		
		    		warehouseID = rs.getInt("m_warehouse_id");
			    	locatorID = rs.getInt("m_locator_id");  	
			    	
			    	//creo nuevo cabezal de consumo
			    	con = new MProductConsume (getCtx(),0,get_TrxName());
			    	con.setC_DocType_ID(MDocType.forValue(this.getCtx(), "prodconsume", null).get_ID());
					con.setDateTrx(new Timestamp(System.currentTimeMillis()));
					con.setMovementDate(con.getDateTrx());
					con.setM_Warehouse_ID(warehouseID);
					con.setM_Locator_ID(locatorID);
					con.setUY_StockStatus_ID(MStockStatus.forValue(getCtx(), "aprobado", get_TrxName()).get_ID());
					con.setAD_User_ID(Env.getContextAsInt(getCtx(), 0, "AD_User_ID"));
					con.setUY_TR_ServiceOrder_ID(this.get_ID());
					con.setUY_TR_Truck_ID(this.getUY_TR_Truck_ID());
					con.setDocStatus(DocumentEngine.STATUS_Drafted);
					con.setDocAction(DocumentEngine.ACTION_Complete);
					con.saveEx();
					
					//creo nueva linea de consumo
					line = new MProductConsumeLine(getCtx(),0,get_TrxName());
					line.setUY_ProductConsumeLine_ID(con.get_ID());
					line.setM_Product_ID(rs.getInt("m_product_id"));
					line.setM_Locator_ID(con.getM_Locator_ID());
					line.setMovementQty(rs.getBigDecimal("qtyrequired"));
					line.setUY_StockStatus_ID(con.getUY_StockStatus_ID());
					line.setM_Warehouse_ID(con.getM_Warehouse_ID());
					line.saveEx();					
		    		
		    	} else {
		    		
		    		//creo nueva linea de consumo
					line = new MProductConsumeLine(getCtx(),0,get_TrxName());
					line.setUY_ProductConsumeLine_ID(con.get_ID());
					line.setM_Product_ID(rs.getInt("m_product_id"));
					line.setM_Locator_ID(con.getM_Locator_ID());
					line.setMovementQty(rs.getBigDecimal("qtyrequired"));
					line.setUY_StockStatus_ID(con.getUY_StockStatus_ID());
					line.setM_Warehouse_ID(con.getM_Warehouse_ID());
					line.saveEx();	
		    		
		    	}	    	
		    	
		    }
			
		    //me aseguro de completar el ultimo documento de consumo
			if(con.getDocStatus().equalsIgnoreCase(DocumentEngine.STATUS_Drafted)){
				
				if (!con.processIt(DocumentEngine.ACTION_Complete)){
    				throw new AdempiereException(con.getProcessMsg());
    			}				
				
			}
			
		} catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		}		
		
	}*/

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#reverseCorrectIt()
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 21/01/2014
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
	 * @author Gabriel Vila - 21/01/2014
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
	 * @author Gabriel Vila - 21/01/2014
	 * @see
	 * @return
	 */
	@Override
	public boolean reActivateIt() {

		return false;
	}

	/***
	 * OpenUp. Nicolas Sarlabos. 17/10/2014. #3132
	 * Metodo que verifica que no existan facturas asociadas a la orden de servicio antes de reactivar o anular.
	 * Recibe un parametro booleano que indica si se esta anulando o reactivando la orden.
	 */
	/*private void verify(boolean isVoid){
		
		String sql = "", value = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;	
		MInvoice inv = null;
		
		try{
			
		if(isVoid){
			
			value = "Imposible anular la orden, primero debe borrar o anular la factura proveedor N° ";
			
		} else value = "Imposible reactivar la orden, primero debe borrar o anular la factura proveedor N° ";		
		
		sql = "select c_invoice_id from c_invoice where docstatus <> 'VO' and uy_tr_serviceorder_id = " + this.get_ID();
		
		pstmt = DB.prepareStatement (sql, get_TrxName());
	    rs = pstmt.executeQuery();
	    
	    while(rs.next()){
	    	
	    	inv = new MInvoice(getCtx(), rs.getInt("c_invoice_id"), get_TrxName());
	    	
	    	throw new AdempiereException(value + inv.getDocumentNo());	    	
	    	
	    }	
		
		}catch (Exception e){
			throw new AdempiereException(e.getMessage());
		}
		finally {
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}		
		
	}*/

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getSummary()
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 21/01/2014
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
	 * @author Gabriel Vila - 21/01/2014
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
	 * @author Gabriel Vila - 21/01/2014
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
	 * @author Gabriel Vila - 21/01/2014
	 * @see
	 * @return
	 */
	@Override
	public String getProcessMsg() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getDoc_User_ID()
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 21/01/2014
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
	 * @author Gabriel Vila - 21/01/2014
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
	 * @author Gabriel Vila - 21/01/2014
	 * @see
	 * @return
	 */
	@Override
	public BigDecimal getApprovalAmt() {
		// TODO Auto-generated method stub
		return null;
	}

	/**Metodo que genera la factura proveedor sin OC desde la orden de servicio.
	 * Recibe como parametro el numero de documento.
	 * org.openup.model - MTRServiceOrder
	 * OpenUp Ltda. Issue #1620
	 * Description: 
	 * @author Nicolas Sarlabos - 13/05/2014
	 * @param documentNo 
	 * @see
	 */
	public void generateInvoice(String documentNo) {
		
		MInvoice inv = new MInvoice(getCtx(), 0, get_TrxName()); // instancio nuevo documento a crear

		MDocType doc = MDocType.forValue(getCtx(), "factprovsinoc", get_TrxName());

		if (doc.get_ID() == 0)
			throw new AdempiereException(
					"No se encontro documento Factura Proveedor sin OC");
		
		Timestamp today = new Timestamp(System.currentTimeMillis());

		// comienzo a setear atributos del cabezal de factura...
		inv.set_ValueOfColumn("UY_TR_ServiceOrder_ID", this.get_ID());
		inv.setC_DocType_ID(doc.getC_DocType_ID());
		inv.setC_DocTypeTarget_ID(doc.getC_DocType_ID());
		inv.setDocumentNoAux(documentNo);
		inv.setDateInvoiced(today);
		inv.setDateVendor(today);
		inv.setDateAcct(today);
		inv.setC_BPartner_ID(this.getC_BPartner_ID());
		inv.setC_Currency_ID(142);
		inv.setpaymentruletype(this.getpaymentruletype());
		
		MPaymentTerm payTerm = MPaymentTerm.forValue(getCtx(), "credito", get_TrxName());
		
		if(payTerm != null && payTerm.get_ID() > 0) {
			inv.setC_PaymentTerm_ID(payTerm.get_ID());
		} else throw new AdempiereException ("No se pudo obtener termino de pago CREDITO");
		
		inv.setIsSOTrx(false);
		inv.setProcessing(false);
		inv.setProcessed(false);
		inv.setDocStatus(DocumentEngine.STATUS_Drafted);
		inv.setDocAction(DocumentEngine.ACTION_Complete);
		inv.saveEx(); // guardo cabezal
		
		//se generan las lineas de factura...............................
		//se cargan lineas de fallas, sin repetir la clasificacion de falla
		this.createLinesFailure(inv);
		//se cargan lineas de mantenimiento
		this.createLinesMaintain(inv);
			
		//asocio nro de factura creada a la OS actual
		this.setC_Invoice_ID(inv.get_ID());
		
		this.saveEx();
		
		//DB.executeUpdateEx("update uy_tr_serviceorder set c_invoice_id = " + inv.get_ID() + " where uy_tr_serviceorder_id = " + this.get_ID(), get_TrxName());
		
		//ADialog.info(0,null,"Factura Proveedor N° " + inv.getDocumentNoAux() + " generada con exito");
		
		/*MTable table = new MTable(Env.getCtx(), I_C_Invoice.Table_ID, null);
		
		String whereClause = table.getTableName() + "_ID =" + inv.get_ID();
		AWindow poFrame = new AWindow(null);
		MQuery query = new MQuery(table.getTableName());
		query.addRestriction(whereClause);
		
		int adWindowID = table.getAD_Window_ID();
		
		if (doc.get_ID() > 0){
			if (!doc.isSOTrx()) adWindowID = table.getPO_Window_ID();
			if (doc.getAD_Window_ID() > 0) adWindowID = doc.getAD_Window_ID();
		}
					
		boolean ok = poFrame.initWindow(adWindowID, query);
		if (ok){
			poFrame.pack(); 
			AEnv.showCenterScreen(poFrame);
			poFrame.toFront();
		}*/			
		
	}
	
	/***
	 * Crea las lineas de fallas para la factura proveedor recibida.
	 * OpenUp Ltda. Issue #1620
	 * @author Nicolas Sarlabos - 13/05/2014
	 * @see
	 * @return
	 */
	public void createLinesFailure(MInvoice hdr){

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try{

			sql = "select distinct cat.m_product_id" +
					" from uy_tr_serviceorderfailure of" +  
					" inner join uy_tr_failure f on of.uy_tr_failure_id = f.uy_tr_failure_id" +
					" inner join uy_tr_failurecategory cat on f.uy_tr_failurecategory_id = cat.uy_tr_failurecategory_id" +
					" where uy_tr_serviceorder_id = " + this.get_ID();

			pstmt = DB.prepareStatement (sql, get_TrxName());
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				
				MProduct prod = new MProduct(getCtx(),rs.getInt("m_product_id"),get_TrxName());
				
				MInvoiceLine line = new MInvoiceLine(getCtx(), 0, get_TrxName()); // instancio nueva linea
				
				line.setC_Invoice_ID(hdr.get_ID());
				line.setLine(line.getLine());
				line.setM_Product_ID(prod.get_ID());
				line.setQtyInvoiced(Env.ONE);
				line.setC_UOM_ID(100);
				line.setPriceEntered(Env.ONE);
				line.setPriceActual(Env.ONE);
				line.setPriceList(Env.ONE);
				
				MTaxCategory taxCat = new MTaxCategory(getCtx(),prod.getC_TaxCategory_ID(),get_TrxName());
				
				line.setC_Tax_ID(taxCat.getDefaultTax().get_ID());				
				line.setQtyEntered(Env.ONE);
				
				MActivity act = MActivity.forValue(getCtx(), "Montevideo", get_TrxName());
				
				if(act!=null && act.get_ID()>0) line.setC_Activity_ID_1(act.get_ID());

				line.saveEx();				
			}
			
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
		finally {
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
	}
	
	/***
	 * Crea las lineas de tareas de mantenimeinto para la factura proveedor recibida.
	 * OpenUp Ltda. Issue #1620
	 * @author Nicolas Sarlabos - 13/05/2014
	 * @see
	 * @return
	 */
	public void createLinesMaintain(MInvoice hdr){
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try{

			sql = "select distinct m.m_product_id" +
                  " from uy_tr_serviceordermaintain om" +
                  " inner join uy_tr_maintain m on om.uy_tr_maintain_id = m.uy_tr_maintain_id" +
                  " where om.uy_tr_serviceorder_id = " + this.get_ID();

			pstmt = DB.prepareStatement (sql, get_TrxName());
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				
				MProduct prod = new MProduct(getCtx(),rs.getInt("m_product_id"),get_TrxName());
				
				MInvoiceLine line = new MInvoiceLine(getCtx(), 0, get_TrxName()); // instancio nueva linea
				
				line.setC_Invoice_ID(hdr.get_ID());
				line.setLine(line.getLine());
				line.setM_Product_ID(prod.get_ID());
				line.setQtyInvoiced(Env.ONE);
				line.setC_UOM_ID(100);
				line.setPriceEntered(Env.ONE);
				line.setPriceActual(Env.ONE);
				line.setPriceList(Env.ONE);
				
				MTaxCategory taxCat = new MTaxCategory(getCtx(),prod.getC_TaxCategory_ID(),get_TrxName());
				
				if (taxCat.get_ID() <= 0){
					taxCat = MTaxCategory.getDefault();
				}
				
				line.setC_Tax_ID(taxCat.getDefaultTax().get_ID());				
				line.setQtyEntered(Env.ONE);
				
				MActivity act = MActivity.forValue(getCtx(), "Montevideo", get_TrxName());
				
				if(act!=null && act.get_ID()>0) line.setC_Activity_ID_1(act.get_ID());
				
				line.saveEx();				
			}
			
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
		finally {
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}		
	}
	
	/***
	 * Obtiene y retorna lineas de mantenimiento de esta orden de servicio.
	 * OpenUp Ltda. Issue #1620
	 * @author Nicolas Sarlabos - 13/05/2014
	 * @see
	 * @return
	 */
	public List<MTRServiceOrderMaintain> getMaintainLines(){

		String whereClause = X_UY_TR_ServiceOrderMaintain.COLUMNNAME_UY_TR_ServiceOrder_ID + "=" + this.get_ID();

		List<MTRServiceOrderMaintain> lines = new Query(getCtx(), I_UY_TR_ServiceOrderMaintain.Table_Name, whereClause, get_TrxName())
		.list();

		return lines;
	}
	
	/***
	 * Obtiene y retorna lineas de fallas de esta orden de servicio.
	 * OpenUp Ltda. Issue #1620
	 * @author Nicolas Sarlabos - 13/05/2014
	 * @see
	 * @return
	 */
	public List<MTRServiceOrderFailure> getFailureLines(){

		String whereClause = X_UY_TR_ServiceOrderFailure.COLUMNNAME_UY_TR_ServiceOrder_ID + "=" + this.get_ID();

		List<MTRServiceOrderFailure> lines = new Query(getCtx(), I_UY_TR_ServiceOrderFailure.Table_Name, whereClause, get_TrxName())
		.list();

		return lines;
	}

	
	/***
	 * Obtiene y retorna lista de productos asociados a esta orden de servicio. 
	 * OpenUp Ltda. Issue #1620 
	 * @author Gabriel Vila - 25/05/2014
	 * @see
	 * @return
	 */
	public List<MTRServiceOrderProd> getProducts(){

		String whereClause = X_UY_TR_ServiceOrderProd.COLUMNNAME_UY_TR_ServiceOrder_ID + "=" + this.get_ID();

		List<MTRServiceOrderProd> lines = new Query(getCtx(), I_UY_TR_ServiceOrderProd.Table_Name, whereClause, get_TrxName())
		.list();

		return lines;
	}

}
