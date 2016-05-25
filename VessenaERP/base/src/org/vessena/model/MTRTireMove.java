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
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;

/***
 * Modelo para documento de cambio de neumaticos.
 * org.openup.model - MTRTireMove
 * OpenUp Ltda. Issue #1605 
 * Description: 
 * @author Gabriel Vila - 14/05/2014
 * @see
 */
public class MTRTireMove extends X_UY_TR_TireMove implements DocAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4910905138552492374L;
	
	private String processMsg = null;
	private boolean justPrepared = false;

	/**
	 * @param ctx
	 * @param UY_TR_TireMove_ID
	 * @param trxName
	 */
	public MTRTireMove(Properties ctx, int UY_TR_TireMove_ID, String trxName) {
		super(ctx, UY_TR_TireMove_ID, trxName);
		
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTRTireMove(Properties ctx, ResultSet rs, String trxName) {
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
		
		//Cargo las grillas de neumaticos del vehiculo seleccionado y la grilla de neumaticos dispobles en los distintos almacenes.
		this.cargarPosicionesdeNeumaticos();
		this.cargarGrillaNeumaticosVehiculo();		
		this.cargarGrillaNeumaticosDisponibles();
		
		this.setDocAction(DocAction.ACTION_Complete);
		this.setDocStatus(DocumentEngine.STATUS_Applied);
		return true;
	}

	
	@Override
	public boolean rejectIt() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	/**
	 * OpenUp. Guillermo Brust. 29/11/2013 ISSUE #1605
	 * Método que carga las líneas de neumaticos utilizados en el vehiculo
	 * 
	 * **/
	private void cargarPosicionesdeNeumaticos(){		
		
		for (int i = this.getLocatorValue(); i < this.getLocatorValue() + this.getQty().intValueExact(); i++) {
			
			MTRTireMoveLine line = new MTRTireMoveLine(this.getCtx(), 0, this.get_TrxName());
			
			line.setUY_TR_TireMove_ID(this.get_ID());
			line.setLocatorValue(i);			
			line.setIsSelected(false);
			line.setIsAuxiliar(false);
						
			line.saveEx();
		}
		
		for (int i = this.getLocatorValue() + this.getQty().intValueExact(); i < this.getLocatorValue() + this.getQty().intValueExact() + this.getQtyAux().intValueExact(); i++) {
			
			MTRTireMoveLine line = new MTRTireMoveLine(this.getCtx(), 0, this.get_TrxName());
			
			line.setUY_TR_TireMove_ID(this.get_ID());
			line.setLocatorValue(i);
			line.setIsSelected(false);
			line.setIsAuxiliar(true);
						
			line.saveEx();
		}
		
	}	
	
	/**
	 * OpenUp. Guillermo Brust. 27/11/2013 ISSUE #1605
	 * Método que carga las líneas de neumaticos utilizados en el vehiculo
	 * 
	 * **/
	private void cargarGrillaNeumaticosVehiculo(){
		
		ResultSet rs = null;
		PreparedStatement pstmt = null;			

		try {		
			
			String sql = "select ti.locatorvalue as Posicion, ti.uy_tr_tire_id as Neumatico, ti.uy_tr_tiremark_id as Marca," +
						 " ti.uy_tr_tiremeasure_id as Medida, ti.observaciones as Modelo, ti.qtyrecauchutaje as Recauchutajes, ti.qtykm5 as KmCasco, ti.estadoactual as Estado" +
						 " from uy_tr_tire ti" +
						 " where ti.uy_tr_truck_id = " + this.getUY_TR_Truck_ID();

			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, null);
			rs = pstmt.executeQuery ();				
		
			while(rs.next()){
				
				MTRTireMoveLine line = MTRTireMoveLine.forPosition(this.getCtx(), rs.getInt("Posicion"), this.get_ID(), this.get_TrxName());
											
				line.setUY_TR_Tire_ID(rs.getInt("Neumatico"));
				line.setUY_TR_TireMark_ID(rs.getInt("Marca"));
				line.setUY_TR_TireMeasure_ID(rs.getInt("Medida"));
				line.setobservaciones(rs.getString("Modelo"));
				line.setQty(rs.getInt("Recauchutajes"));
				line.setQtyKm(rs.getBigDecimal("KmCasco"));
				line.setEstadoActual(rs.getString("Estado"));
				line.setIsChanged(false);
				line.setIsSelected(false);				
				
				line.saveEx();
			}		
						
		} catch (Exception e) {
			DB.close(rs, pstmt);	
			throw new AdempiereException(e);
		}
		finally{
			DB.close(rs, pstmt);
		}	
		
	}
	
	
	
	/**
	 * OpenUp. Guillermo Brust. 27/11/2013 ISSUE #1605
	 * Método que carga las líneas de neumaticos disponibles en los distintos almacenes 
	 * 
	 * **/
	private void cargarGrillaNeumaticosDisponibles(){
		
		ResultSet rs = null;
		PreparedStatement pstmt = null;			

		try {		
			
			String sql = "select ti.m_warehouse_id as Almacen, ti.uy_tr_tire_id as Neumatico, ti.uy_tr_tiremark_id as Marca," +
						 " ti.uy_tr_tiremeasure_id as Medida, ti.observaciones as Modelo, ti.qtyrecauchutaje as Recauchutajes, ti.qtykm5 as KmCasco, ti.estadoactual as Estado" +
						 " from uy_tr_tire ti" +
						 " where ti.uy_tr_truck_id is null and ti.isactive = 'Y'" +
						 " and not exists (select uy_tr_tire_id from uy_tr_tiremoveline l inner join uy_tr_tiremove h on l.uy_tr_tiremove_id = h.uy_tr_tiremove_id where l.uy_tr_tire_id = ti.uy_tr_tire_id and h.docstatus = 'AY')" +
                         " and not exists (select uy_tr_tire_id from uy_tr_tiremoveopen o inner join uy_tr_tiremove h on o.uy_tr_tiremove_id = h.uy_tr_tiremove_id where o.uy_tr_tire_id = ti.uy_tr_tire_id and h.docstatus = 'AY')";

			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, null);
			rs = pstmt.executeQuery ();				
		
			while(rs.next()){
				
				MTRTireMoveOpen line = new MTRTireMoveOpen(this.getCtx(), 0, this.get_TrxName());
				
				line.setUY_TR_TireMove_ID(this.get_ID());
				line.setM_Warehouse_ID(rs.getInt("Almacen"));				
				line.setUY_TR_Tire_ID(rs.getInt("Neumatico"));
				line.setUY_TR_TireMark_ID(rs.getInt("Marca"));
				line.setUY_TR_TireMeasure_ID(rs.getInt("Medida"));
				line.setobservaciones(rs.getString("Modelo"));
				line.setQty(rs.getInt("Recauchutajes"));
				line.setQtyKm(rs.getBigDecimal("KmCasco"));
				line.setEstadoActual(rs.getString("Estado"));				
				line.setIsSelected(false);				
				
				line.saveEx();
			}
			
		} catch (Exception e) {
			DB.close(rs, pstmt);	
			throw new AdempiereException(e);
		}
		finally{
			DB.close(rs, pstmt);
		}	
		
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
		
		MTRTruck truck = (MTRTruck)this.getUY_TR_Truck();
		
		if(this.getQtyKm() > 0){

			if(this.getQtyKm() < truck.getQtyKm()) throw new AdempiereException("Kilometraje ingresado no puede ser menor al actual");			

		} else throw new AdempiereException("Debe ingresar kilometraje actual del vehiculo");

		// Valido informacion antes de completar.
		this.validateData();
		
		DB.executeUpdateEx("UPDATE uy_tr_tire t SET updateqty = l.updateqty, ischanged = l.ischanged from uy_tr_tiremoveline l WHERE l.uy_tr_tiremove_id = " + this.get_ID() + 
				" AND t.uy_tr_tire_id = l.uy_tr_tire_id", get_TrxName());
				
		// Proceso las lineas de las grillas segun sea el caso
		// En el caso de la grilla de los neumaticos colocados, tengo que ubicar el neumatico asociado a esta linea y 
		// setearle el vehiculo que se selecciono en el cabezal y la posición que tiene la linea dentro de este vehiculo.
		for (MTRTireMoveLine moveLine : this.getLinesWithChange()) {
			
			MTRTire tire = (MTRTire) moveLine.getUY_TR_Tire();
			tire.setUY_TR_Truck_ID(this.getUY_TR_Truck_ID());
			tire.setLocatorValue(moveLine.getLocatorValue());
			tire.setDateTrx(this.getDateTrx());
			if(!moveLine.isRotated()) tire.setQtyKm6(this.getQtyKm());
			tire.setUY_TR_TireMove_ID(this.get_ID());
			tire.setIsAuxiliar(moveLine.isAuxiliar());
			tire.setQtyKmLocate(tire.getQtyKm5());
			tire.setQtyKmTruckLocate(this.getQtyKm());
			tire.setQtyKmRecorrido(0);
			tire.setIsChanged(moveLine.isChanged());
			//tire.setUpdateQty(moveLine.isUpdateQty());
			tire.saveEx();			
		
			//Desasocio el almacen del neumatico
			tire.removeLocation(false);	
		}
		
		for (MTRTireMoveLine moveLine : this.getLinesWithRotation()) {
		
			MTRTire tire = (MTRTire) moveLine.getUY_TR_Tire();
			tire.setUY_TR_Truck_ID(this.getUY_TR_Truck_ID());
			tire.setLocatorValue(moveLine.getLocatorValue());
			tire.setDateTrx(this.getDateTrx());
			//tire.setQtyKm6(this.getQtyKm());
			tire.setUY_TR_TireMove_ID(this.get_ID());
			tire.setIsAuxiliar(moveLine.isAuxiliar());
			tire.setQtyKmLocate(tire.getQtyKm5());
			tire.setQtyKmTruckLocate(this.getQtyKm());
			//tire.setUpdateQty(moveLine.isUpdateQty());
			//tire.setQtyKmRecorrido(0);
			
			tire.saveEx();		
					
		}
		
		//Me aseguro de que las auxiliares queden marcadas como tales
		for (MTRTireMoveLine moveLineAux : this.getLinesAuxiliar()) {
			
			MTRTire tire = (MTRTire) moveLineAux.getUY_TR_Tire();
			tire.setUY_TR_Truck_ID(this.getUY_TR_Truck_ID());
			tire.setLocatorValue(moveLineAux.getLocatorValue());
			tire.setIsAuxiliar(moveLineAux.isAuxiliar());
			//tire.setUpdateQty(moveLineAux.isUpdateQty());
			tire.saveEx();			
						
			//Desasocio el almacen del neumatico
			tire.removeLocation(false);				
		}		
		
		// En el caso de la grilla de los neumaticos en los almacenes, debo asignar el almacen que tenga en la grilla y 
		// dejar nulos los campos de vehiculo y posicion del neumatico
		for (MTRTireMoveOpen moveOpen : this.getOpenWithChange()) {
			
			MTRTire tire = new MTRTire(this.getCtx(), moveOpen.getUY_TR_Tire_ID(), this.get_TrxName());
			tire.setM_Warehouse_ID(moveOpen.getM_Warehouse_ID());
			tire.setUY_TR_TireMove_ID(this.get_ID());
			if(!moveOpen.isAuxiliar()) tire.updateKmReales(this.getQtyKm());
			tire.setIsAuxiliar(false);
			
			tire.saveEx();			
			
			//Desasocio el vehiculo y la posicion del neumatico
			tire.removeLocation(true);
			
		}

		// Genero y completo documento de lectura de kilometraje para este vehiculo		
		MTRReadKM read = new MTRReadKM(this.getCtx(), 0, this.get_TrxName());
		read.setC_DocType_ID(MDocType.forValue(this.getCtx(), "readkm", null).get_ID());
		read.setUY_TR_Truck_ID(this.getUY_TR_Truck_ID());		
		read.setDateAction(this.getDateTrx());
		read.setKilometros(this.getQtyKm());
		read.setIsValid(true);
		read.setIsManual(false);
		read.isHubodometro = false;
		read.saveEx();	
		
		try {				
			if (!read.processIt(DocumentEngine.ACTION_Complete)){
				throw new AdempiereException(read.getProcessMsg());
			}				
		} catch (Exception e) {
			throw new AdempiereException(e.getMessage());
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
	 * OpenUp. Nicolas Sarlabos. 09/04/2014. ISSUE#1605
	 * Realiza verificaciones antes de completar el documento
	 * 
	 */
	private void validateData() {
		
		String sql = "";
		
		//Verifico que el vehiculo tenga todas los neumaticos colocados
		//if(this.getLinesWithTireID().size() < this.getQty().intValueExact()) throw new AdempiereException("No es posible completar el documento. El vehículo no tiene todos los neumáticos colocados");
		
		if (this.getQtyKm() <= 0) throw new AdempiereException("No es posible completar el documento. Debe ingresar el kilometraje actual.");
		
		//verifico que no exista otro cambio de neumatico para el vehiculo actual en estado APLICADO
		sql = "select uy_tr_tiremove_id" +
		      " from uy_tr_tiremove" +
			  " where uy_tr_truck_id = " + this.getUY_TR_Truck_ID() +
			  " and docstatus = 'AY' and uy_tr_tiremove_id <> " + this.get_ID();
		int moveID = DB.getSQLValueEx(get_TrxName(), sql);
		
		if(moveID > 0){
			
			MTRTireMove move = new MTRTireMove(getCtx(),moveID,get_TrxName());
			
			throw new AdempiereException("Imposible completar, existe el documento N° " + move.getDocumentNo() + " para el vehiculo actual en estado APLICADO");
		}		
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
	
	/**
	 * OpenUp. Nicolas Sarlabos. 11/06/2014. ISSUE #
	 * Método que devuelve una lista con las lineas de neumaticos auxiliares.
	 * 
	 */
	public List<MTRTireMoveLine> getLinesAuxiliar(){
		
		String whereClause = X_UY_TR_TireMoveLine.COLUMNNAME_IsAuxiliar + " = 'Y'" +
							" AND " + X_UY_TR_TireMoveLine.COLUMNNAME_UY_TR_TireMove_ID + " = " + this.get_ID() +
							" AND " + X_UY_TR_TireMoveLine.COLUMNNAME_UY_TR_Tire_ID + " is not null";
		
		List<MTRTireMoveLine> lista = new Query(this.getCtx(), I_UY_TR_TireMoveLine.Table_Name, whereClause, this.get_TrxName())
		.list();		
		
		return lista;
	}
	

	/**
	 * OpenUp. Guillermo Brust. 28/11/2013. ISSUE #
	 * Método que devuelve una lista con las lineas seleccionadas
	 * 
	 */
	public List<MTRTireMoveOpen> getLinesSelected(){
		
		String whereClause = X_UY_TR_TireMoveOpen.COLUMNNAME_IsSelected + " = 'Y'" +
							" AND " + X_UY_TR_TireMoveOpen.COLUMNNAME_UY_TR_TireMove_ID + " = " + this.get_ID();
		
		List<MTRTireMoveOpen> lista = new Query(this.getCtx(), I_UY_TR_TireMoveOpen.Table_Name, whereClause, this.get_TrxName())
		.list();		
		
		return lista;
	}
	
	
	/**
	 * OpenUp. Guillermo Brust. 03/12/2013. ISSUE #
	 * Método que devuelve una lista con las lineas que que tiene el uy_tr_tiremove_id
	 * 
	 */
	public List<MTRTireMoveOpen> getOpenForMoveTireID(){
		
		String whereClause = X_UY_TR_TireMoveOpen.COLUMNNAME_UY_TR_TireMove_ID + " = " + this.get_ID();
		
		List<MTRTireMoveOpen> lista = new Query(this.getCtx(), I_UY_TR_TireMoveOpen.Table_Name, whereClause, this.get_TrxName())
		.list();		
		
		return lista;
	}	
	
	/**
	 * OpenUp. Guillermo Brust. 03/12/2013. ISSUE #
	 * Método que devuelve una lista con las lineas que que tiene el uy_tr_tiremove_id
	 * 
	 */
	public List<MTRTireMoveOpen> getOpenWithChange(){
		
		String whereClause = X_UY_TR_TireMoveOpen.COLUMNNAME_UY_TR_TireMove_ID + " = " + this.get_ID() +
							" AND " + X_UY_TR_TireMoveOpen.COLUMNNAME_IsChanged + " = 'Y'";
		
		List<MTRTireMoveOpen> lista = new Query(this.getCtx(), I_UY_TR_TireMoveOpen.Table_Name, whereClause, this.get_TrxName())
		.list();		
		
		return lista;
	}
	
	/**
	 * OpenUp. Nicolas Sarlabos. 11/04/2014. ISSUE #
	 * Método que devuelve una lista con las lineas de neumaticos que han sido cambiados
	 * 
	 */
	public List<MTRTireMoveLine> getLinesWithChange(){
		
		String whereClause = X_UY_TR_TireMoveLine.COLUMNNAME_UY_TR_Tire_ID + " is not null " +
							" AND " + X_UY_TR_TireMoveLine.COLUMNNAME_IsChanged + " = 'Y'" +
							" AND " + X_UY_TR_TireMoveLine.COLUMNNAME_UY_TR_TireMove_ID + " = " + this.get_ID();
		
		List<MTRTireMoveLine> lista = new Query(this.getCtx(), I_UY_TR_TireMoveLine.Table_Name, whereClause, this.get_TrxName())
		.list();		
		
		return lista;
	}
	
	/**
	 * OpenUp. Nicolas Sarlabos. 16/07/2015. ISSUE #4570
	 * Método que devuelve una lista con las lineas de neumaticos colocados que han sido rotados
	 * 
	 */
	public List<MTRTireMoveLine> getLinesWithRotation(){
		
		String whereClause = X_UY_TR_TireMoveLine.COLUMNNAME_UY_TR_Tire_ID + " is not null " +
							" AND " + X_UY_TR_TireMoveLine.COLUMNNAME_IsRotated + " = 'Y'" +
							" AND " + X_UY_TR_TireMoveLine.COLUMNNAME_UY_TR_TireMove_ID + " = " + this.get_ID();
		
		List<MTRTireMoveLine> lista = new Query(this.getCtx(), I_UY_TR_TireMoveLine.Table_Name, whereClause, this.get_TrxName())
		.list();		
		
		return lista;
	}
	
	/**
	 * OpenUp. Guillermo Brust. 29/11/2013. ISSUE #
	 * Método que devuelve una lista con las lineas que tienen tireID y han sido cambiados
	 * 
	 */
	public List<MTRTireMoveLine> getLinesWithTireID(){
		
		String whereClause = X_UY_TR_TireMoveLine.COLUMNNAME_UY_TR_Tire_ID + " is not null " +
							" AND " + X_UY_TR_TireMoveLine.COLUMNNAME_UY_TR_TireMove_ID + " = " + this.get_ID();
		
		List<MTRTireMoveLine> lista = new Query(this.getCtx(), I_UY_TR_TireMoveLine.Table_Name, whereClause, this.get_TrxName())
		.list();		
		
		return lista;
	}
	
	/**
	 * OpenUp. Nicolas Sarlabos. 14/07/2015. ISSUE #
	 * Método que devuelve una lista con las lineas seleccionadas para rotacion.
	 * 
	 */
	public List<MTRTireMoveLine> getLinesRotate(){
		
		String whereClause = X_UY_TR_TireMoveLine.COLUMNNAME_IsRotate + " = 'Y'" +
							" AND " + X_UY_TR_TireMoveLine.COLUMNNAME_UY_TR_TireMove_ID + " = " + this.get_ID();
		
		List<MTRTireMoveLine> lista = new Query(this.getCtx(), I_UY_TR_TireMoveLine.Table_Name, whereClause, this.get_TrxName())
		.list();		
		
		return lista;
	}

	/**
	 * OpenUp. Nicolas Sarlabos. 09/04/2014. ISSUE #2027.
	 * Método que refresca la grilla de neumaticos disponibles.
	 * 
	 */
	public void refreshTire() {
		
		ResultSet rs = null;
		PreparedStatement pstmt = null;			

		try {		
			
			String sql = "select ti.m_warehouse_id as Almacen, ti.uy_tr_tire_id as Neumatico, ti.uy_tr_tiremark_id as Marca," +
						 " ti.uy_tr_tiremeasure_id as Medida, ti.observaciones as Modelo, ti.qtyrecauchutaje as Recauchutajes, ti.qtykm5 as KmCasco, ti.estadoactual as Estado" +
						 " from uy_tr_tire ti" +
						 " where ti.uy_tr_truck_id is null and ti.isactive = 'Y'" +
						 " and not exists (select uy_tr_tire_id from uy_tr_tiremoveline l inner join uy_tr_tiremove h on l.uy_tr_tiremove_id = h.uy_tr_tiremove_id where l.uy_tr_tire_id = ti.uy_tr_tire_id and h.docstatus = 'AY')" +
                         " and not exists (select uy_tr_tire_id from uy_tr_tiremoveopen o inner join uy_tr_tiremove h on o.uy_tr_tiremove_id = h.uy_tr_tiremove_id where o.uy_tr_tire_id = ti.uy_tr_tire_id and h.docstatus = 'AY')" +
						 " and not exists (select uy_tr_tire_id from uy_tr_tiremoveopen where uy_tr_tire_id = ti.uy_tr_tire_id and uy_tr_tiremove_id = " + this.getUY_TR_TireMove_ID() + ")";

			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, null);
			rs = pstmt.executeQuery ();				
		
			while(rs.next()){
				
				MTRTireMoveOpen line = new MTRTireMoveOpen(this.getCtx(), 0, this.get_TrxName());
				
				line.setUY_TR_TireMove_ID(this.get_ID());
				line.setM_Warehouse_ID(rs.getInt("Almacen"));				
				line.setUY_TR_Tire_ID(rs.getInt("Neumatico"));
				line.setUY_TR_TireMark_ID(rs.getInt("Marca"));
				line.setUY_TR_TireMeasure_ID(rs.getInt("Medida"));
				line.setobservaciones(rs.getString("Modelo"));
				line.setQty(rs.getInt("Recauchutajes"));
				line.setQtyKm(rs.getBigDecimal("KmCasco"));
				line.setEstadoActual(rs.getString("Estado"));				
				line.setIsSelected(false);				
				
				line.saveEx();
			}
			
		} catch (Exception e) {
			DB.close(rs, pstmt);	
			throw new AdempiereException(e);
		}
		finally{
			DB.close(rs, pstmt);
		}			
	}

	/**
	 * OpenUp. Nicolas Sarlabos. 14/07/2015. ISSUE #
	 * Método que rota posiciones de neumaticos seleccionados.
	 * 
	 */
	public void rotate() {
		
		MTRTireMoveLine moveLine = null;
		int locatorVal = 0;
		String auxiliar = "N";
		MTRTire tire1 = null, tire2 = null;
		
		List<MTRTireMoveLine> lines = this.getLinesRotate();		
		
		if(lines.size() == 0) throw new AdempiereException("Debe seleccionar dos neumaticos colocados para la rotacion");
		if(lines.size() > 2) throw new AdempiereException("No es posible rotar mas de dos neumaticos a la vez");
		
		try{
			
			for(MTRTireMoveLine line : lines){
				
				if(moveLine == null){ //si es la primer linea

					moveLine = new MTRTireMoveLine(getCtx(), line.getUY_TR_TireMoveLine_ID(), get_TrxName());
					locatorVal = line.getLocatorValue();
					tire1 = (MTRTire)line.getUY_TR_Tire();
					
					if(line.isAuxiliar()) {
						auxiliar = "Y";
					}
					
				} else {//es la segunda linea
					
					tire2 = (MTRTire)line.getUY_TR_Tire();
					String aux = "N";
					
					if(line.isAuxiliar()) {
						aux = "Y";
					}

					DB.executeUpdateEx("update uy_tr_tiremoveline set isrotated = 'Y', locatorvalue = " + line.getLocatorValue() + ", isauxiliar = '" + aux + "' where uy_tr_tiremoveline_id = " + moveLine.get_ID(), get_TrxName());//actualizo primer linea
					DB.executeUpdateEx("update uy_tr_tiremoveline set isrotated = 'Y', locatorvalue = " + locatorVal + ", isauxiliar = '" + auxiliar + "' where uy_tr_tiremoveline_id = " + line.get_ID(), get_TrxName());//actualizo segunda linea
					
					if(moveLine.getLocatorValue()==tire2.getLocatorValue()){
						
						DB.executeUpdateEx("update uy_tr_tiremoveline set isrotated = 'N' where uy_tr_tiremoveline_id = " + moveLine.get_ID(), get_TrxName());//actualizo primer linea						
					}
					
					if(line.getLocatorValue()==tire1.getLocatorValue()){
						
						DB.executeUpdateEx("update uy_tr_tiremoveline set isrotated = 'N' where uy_tr_tiremoveline_id = " + line.get_ID(), get_TrxName());//actualizo segunda linea
					}				
					
				}			
				
			}
			
			//limpio checks de rotacion
			DB.executeUpdateEx("update uy_tr_tiremoveline set isrotate = 'N' where uy_tr_tiremove_id = " + this.get_ID(), get_TrxName());			
			
		} catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		}		
		
	}

}
