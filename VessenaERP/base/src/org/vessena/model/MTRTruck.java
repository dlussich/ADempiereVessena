/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.Query;
import org.compiere.util.DB;

/**
 * @author Nicolas
 *
 */
public class MTRTruck extends X_UY_TR_Truck {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2623960733567883052L;

	/**
	 * @param ctx
	 * @param UY_TR_Truck_ID
	 * @param trxName
	 */
	public MTRTruck(Properties ctx, int UY_TR_Truck_ID, String trxName) {
		super(ctx, UY_TR_Truck_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTRTruck(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		//OpenUp. Nicolas Sarlabos. 03/07/2015. #4363.
		if(newRecord || is_ValueChanged("Value")){
			
			//verifico que no exista otro vehiculo con la misma matricula
			MTRTruck t = MTRTruck.forValue(getCtx(), this.getValue(), get_TrxName());
			
			if(t!=null) throw new AdempiereException("Ya existe un vehiculo con la matricula ingresada");			
			
		}
		//Fin OpenUp.
		
		return true;
	}

	/***
	 * Obtiene y retorna un vehiculo segun value (matricula) recibido
	 * OpenUp Ltda. Issue #1609
	 * @author Nicolas Sarlabos - 28/11/2013
	 * @see
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static MTRTruck forValue(Properties ctx, String value, String trxName){
		
		MTRTruck truck = null;
		
		if(value != null){
			
			value = value.toLowerCase().trim();
			
			String whereClause = " lower(" + X_UY_TR_Truck.COLUMNNAME_Value + ")='" + value + "'";
			
			truck = new Query(ctx, I_UY_TR_Truck.Table_Name, whereClause, trxName)
			.setClient_ID()
			.first();
			
		}	
				
		return truck;
	}

	/***
	 * Metodo main desde el cual se actualizan todos los kms de entidades relacionadas a este vehiculo.
	 * OpenUp Ltda. Issue #1617. 
	 * @author Gabriel Vila - 14/05/2014
	 * @see
	 * @param kilometros
	 */
	public void updateKmsAll(int kilometros) {
		
		try {
			
			// Obtengo y actualizo kms de neumaticos colocados actualmente en este vehiculo
			List<MTRTire> tires = this.getTires();
			for (MTRTire tire: tires){
				if (tire.isUpdateQty() || (!tire.isUpdateQty() && !tire.isAuxiliar() && !tire.isChanged())){
					tire.updateKmReales(kilometros);
				}
				else{
					tire.setQtyKm6(kilometros);
				}
				
				tire.saveEx();
			
				DB.executeUpdateEx("update uy_tr_tire set updateqty = 'N', ischanged = 'N' where uy_tr_tire_id = " + tire.get_ID(), get_TrxName());
			}			

			// Obtengo y actualizo estado de tareas de mantenimiento asociadas a este vehiculo
			List<MTRTruckMaintain> mts = this.getMaintains();
			for (MTRTruckMaintain mt: mts){ 
				mt.updateKm(kilometros);
				
			}			
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		
	}

	
	/***
	 * Obtiene y retorna lista de neumaticos colocados en este vehiculo.
	 * OpenUp Ltda. Issue #1617 
	 * @author Gabriel Vila - 14/05/2014
	 * @see
	 * @return
	 */
	public List<MTRTire> getTires(){
	
		String whereClause = X_UY_TR_Tire.COLUMNNAME_UY_TR_Truck_ID + "=" + this.get_ID();
		
		List<MTRTire> lines = new Query(getCtx(), I_UY_TR_Tire.Table_Name, whereClause, get_TrxName())
		.setOrderBy(I_UY_TR_Tire.COLUMNNAME_LocatorValue)
		.list();
		
		return lines;
	}

	
	/***
	 * Obtiene y retorna lista de tareas de mantenimiento asociadas a este vehiculo.
	 * OpenUp Ltda. Issue #1617 
	 * @author Gabriel Vila - 14/05/2014
	 * @see
	 * @return
	 */
	public List<MTRTruckMaintain> getMaintains(){
	
		String whereClause = X_UY_TR_TruckMaintain.COLUMNNAME_UY_TR_Truck_ID + "=" + this.get_ID();
		
		List<MTRTruckMaintain> lines = new Query(getCtx(), I_UY_TR_TruckMaintain.Table_Name, whereClause, get_TrxName())
		.setOnlyActiveRecords(true)
		.setOrderBy(I_UY_TR_TruckMaintain.COLUMNNAME_UY_TR_TruckMaintain_ID)
		.list();
		
		return lines;
	}

	
	/***
	 * Verifica si este vehiculo tiene asociado una determinada tarea de mantenimiento.
	 * OpenUp Ltda. Issue #1405 
	 * @author Gabriel Vila - 05/07/2014
	 * @see
	 * @param truckMaintainID
	 * @return
	 */
	public boolean containsMaintain(int truckMaintainID){
	
		try {
			
			String sql = " SELECT coalesce(uy_tr_truck_id,0) as id " +
					     " from uy_tr_truckmaintain " +
					     " where uy_tr_truck_id =" + this.get_ID() +
					     " and uy_tr_maintain_id =" + truckMaintainID;
			return (DB.getSQLValue(null, sql) > 0);
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		
		
	}
	
}
