/**
 * 
 */
package org.openup.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;

/***
 * org.openup.model - MTRTire
 * OpenUp Ltda. Issue #1605 
 * Description: Modelo para neumaticos en modulo de transporte. 
 * @author Gabriel Vila - 14/05/2014
 * @see
 */
public class MTRTire extends X_UY_TR_Tire {

	private static final long serialVersionUID = -5794741595947695557L;

	/**
	 * @param ctx
	 * @param UY_TR_Tire_ID
	 * @param trxName
	 */
	public MTRTire(Properties ctx, int UY_TR_Tire_ID, String trxName) {
		super(ctx, UY_TR_Tire_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTRTire(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		if(is_ValueChanged("observaciones")) this.updateTireModel(); //si se cambio el modelo del neumatico, lo actualizo en tablas de cambio
		if(is_ValueChanged("uy_tr_tiremark_id")) this.updateTireMark(); //si se cambio la marca del neumatico, lo actualizo en tablas de cambio
		if(is_ValueChanged("uy_tr_tiremeasure_id")) this.updateTireMeasure(); //si se cambio la medida del neumatico, lo actualizo en tablas de cambio
		
		if(is_ValueChanged("qtykm2") || is_ValueChanged("qtykm4")) this.setQtyKm5(this.getQtyKm2() + this.getQtyKm4());
		
		return true;
	}

	/***
	 * OpenUp. Nicolas Sarlabos. 11/04/2014. ISSUE#1629
	 * Metodo que actualiza modelo de neumatico en las grillas de cambio de neumatico.
	 * 
	 * */
	private void updateTireModel() {
		
		DB.executeUpdateEx("UPDATE uy_tr_tiremoveline SET observaciones = '" + this.getobservaciones() + "' WHERE uy_tr_tire_id = " + this.getUY_TR_Tire_ID(), get_TrxName());
		DB.executeUpdateEx("UPDATE uy_tr_tiremoveopen SET observaciones = '" + this.getobservaciones() + "' WHERE uy_tr_tire_id = " + this.getUY_TR_Tire_ID(), get_TrxName());
		
	}
	
	/***
	 * OpenUp. Nicolas Sarlabos. 01/05/2014. ISSUE#1629
	 * Metodo que actualiza la marca del neumatico en las grillas de cambio de neumatico.
	 * 
	 * */
	private void updateTireMark() {
		
		DB.executeUpdateEx("UPDATE uy_tr_tiremoveline SET uy_tr_tiremark_id = " + this.getUY_TR_TireMark_ID() + " WHERE uy_tr_tire_id = " + this.getUY_TR_Tire_ID(), get_TrxName());
		DB.executeUpdateEx("UPDATE uy_tr_tiremoveopen SET uy_tr_tiremark_id = " + this.getUY_TR_TireMark_ID() + " WHERE uy_tr_tire_id = " + this.getUY_TR_Tire_ID(), get_TrxName());
		
	}
	
	/***
	 * OpenUp. Nicolas Sarlabos. 01/05/2014. ISSUE#1629
	 * Metodo que actualiza la medida del neumatico en las grillas de cambio de neumatico.
	 * 
	 * */
	private void updateTireMeasure() {
		
		DB.executeUpdateEx("UPDATE uy_tr_tiremoveline SET uy_tr_tiremeasure_id = " + this.getUY_TR_TireMeasure_ID() + " WHERE uy_tr_tire_id = " + this.getUY_TR_Tire_ID(), get_TrxName());
		DB.executeUpdateEx("UPDATE uy_tr_tiremoveopen SET uy_tr_tiremeasure_id = " + this.getUY_TR_TireMeasure_ID() + " WHERE uy_tr_tire_id = " + this.getUY_TR_Tire_ID(), get_TrxName());
		
	}

	/***
	 * Actualiza kms reales para el estado actual de este neumatico (nuevo o recauchutado), segun km de lectura recibidos por parametro.
	 * La actualizacion es simplemente sumarle los km que salen de la resta de la lectura recibida menos los km de lectura anterior a la ultima.	
	 * OpenUp Ltda. Issue #1605 
	 * @author Gabriel Vila - 14/05/2014
	 * @see
	 * @param qtyKm
	 */
	public void updateKmReales(int qtyKmLectura) {
		
		int diffKm = qtyKmLectura - this.getQtyKm6(); 
		
		if (this.getEstadoActual().equalsIgnoreCase(X_UY_TR_Tire.ESTADOACTUAL_NUEVO)){
			
			this.setQtyKm2(this.getQtyKm2() + diffKm);
			
			// Costo/Kms.Nuevo
			if (this.getPrice() != null){
				if (this.getQtyKm2() > 0){
					this.setPriceCost(this.getPrice().divide(new BigDecimal(this.getQtyKm2()), 4, RoundingMode.HALF_UP));		
				}
			}
			
		}
		else if (this.getEstadoActual().equalsIgnoreCase(X_UY_TR_Tire.ESTADOACTUAL_RECAUCHUTADO)){

			this.setQtyKm4(this.getQtyKm4() + diffKm);
			
			// Costo/Kms.Recauchutado
			if (this.getPrice2() != null){
				if (this.getQtyKm4() > 0){
					this.setPriceCost2(this.getPrice2().divide(new BigDecimal(this.getQtyKm4()), 4, RoundingMode.HALF_UP));		
				}
			}

		}
		
		// Actualizo kms casco de este neumatico que es igual a la suma de kms de reales de todos sus estados
		this.setQtyKm5(this.getQtyKm2() + this.getQtyKm4());
		
		// Actualizo kms de ultima lectura
		this.setQtyKm6(qtyKmLectura);
		
		// Actualizo kms recorridos desde que este neumatico se coloco en el vehiculo
		this.setQtyKmRecorrido(qtyKmLectura - this.getQtyKmTruckLocate());

	}

	/***
	 * Metodo que remueve o desasocia el neumatico de su ubicacion actual. Por parametro se recibo True si se remueve su vehiculo actual o False si se remueve
	 * de su almacen actual.
	 * OpenUp Ltda. Issue #1605 
	 * @author Gabriel Vila - 14/05/2014
	 * @see
	 * @param removeTruck
	 */
	public void removeLocation(boolean removeTruck){

		String action = "";
		
		try {		
			if (removeTruck){
				
				// Desasocio vehiculo
				action = " UPDATE UY_TR_Tire SET UY_TR_Truck_ID = null, LocatorValue = null, qtykm6 = null, " +
						 " qtykmlocate = null, qtykmtrucklocate = null, qtykmrecorrido = null,  datetrx = null  " +
						 " WHERE UY_TR_Tire_ID = " + this.get_ID();
				DB.executeUpdateEx(action, this.get_TrxName());
				
			}
			else{
				
				//Desasocio almacen
				action = "UPDATE UY_TR_Tire SET M_WareHouse_ID = null WHERE UY_TR_Tire_ID = " + this.get_ID();
				DB.executeUpdateEx(action, this.get_TrxName());			
			}
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		
	}
	
}
