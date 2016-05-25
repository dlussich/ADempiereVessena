/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MNote;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * @author Nicolas
 *
 */
public class MTRTruckMaintain extends X_UY_TR_TruckMaintain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7110482619739431959L;

	/**
	 * @param ctx
	 * @param UY_TR_TruckMaintain_ID
	 * @param trxName
	 */
	public MTRTruckMaintain(Properties ctx, int UY_TR_TruckMaintain_ID,
			String trxName) {
		super(ctx, UY_TR_TruckMaintain_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTRTruckMaintain(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		MTRTruck truck = (MTRTruck)this.getUY_TR_Truck();
		
		if(newRecord || is_ValueChanged("UY_TR_Maintain_ID")){
			
			MTRTruckMaintain line = MTRTruckMaintain.forTruckMaintain(getCtx(), truck.get_ID(), this.getUY_TR_Maintain_ID(), get_TrxName());
			
			if(line!=null && line.get_ID()>0) throw new AdempiereException("La tarea seleccionada ya se encuentra asignada a este vehiculo");			
			
		}		
		
		if(this.getKilometros() <= 0) throw new AdempiereException("Frecuencia de Kilometros debe ser mayor a cero");		
		
		if(newRecord) this.setQtyKm(truck.getQtyKm());
			
		if(is_ValueChanged("QtyKmLast") && this.getQtyKmLast() > 0){
			
			this.setQtyKmNext(this.getQtyKmLast() + this.getKilometros());				
			
		}		
		
		if(is_ValueChanged("kilometros")) { //si se modifica frecuencia de la tarea

			if(this.getQtyKmLast() > 0){ //si la tarea fue realizada alguna vez

				this.setQtyKmNext(this.getQtyKmLast() + this.getKilometros());
				
				int diffKm = this.getQtyKmNext() - this.getQtyKm(); 

				if(diffKm <= 0) {
					
					this.setIsExpired(true);
					
				} else this.setIsExpired(false);

			}
		}

		// Seteo km actuales vehiculo en la linea de mantenimiento preventivo
		/*if (truck.getQtyKm() > 0){
			this.setQtyKm(truck.getQtyKm());
		}*/
		
		return true;
	}

	/***
	 * A partir de un nuevo valor de km del vehiculo de esta tarea, se debe verificar si la misma llega al tiempo de expiracion.
	 * En caso de llegar y ademas tener un porcentaje de alarma, la misma debe ser enviada a quienes correspondan.
	 * OpenUp Ltda. Issue #1615 
	 * @author Gabriel Vila - 14/05/2014
	 * @see
	 * @param kilometros
	 */
	public void updateKm(int kilometros) {
		
		try {
			
			this.setQtyKm(kilometros);

			if(this.getQtyKmLast() > 0){ //si la tarea fue realizada alguna vez

				int diffKm = this.getQtyKmNext() - this.getQtyKm(); 

				if(diffKm <= 0) this.setIsExpired(true);

			}
			
			this.saveEx();
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		
		MRSign sign = null;
		MTRMaintain mant = (MTRMaintain)this.getUY_TR_Maintain();
		MTRTruck truck = (MTRTruck)this.getUY_TR_Truck();
		
		//obtengo registro de tracking para este vehiculo y tarea, si no hay lo creo
		MTRTrackingMaintain tracking = MTRTrackingMaintain.forTruckTask(getCtx(), this.getUY_TR_Truck_ID(), this.getUY_TR_Maintain_ID(), get_TrxName());
		
		if(tracking==null) tracking = new MTRTrackingMaintain(getCtx(), 0, get_TrxName());
		
		//seteo valores
		tracking.setUY_TR_Truck_ID(truck.get_ID());
		tracking.setUY_TR_Maintain_ID(mant.get_ID());
		tracking.setKilometros(this.getKilometros());
		tracking.setQtyKm(this.getQtyKm());
		tracking.setDateLast(this.getDateLast());
		tracking.setQtyKmLast(this.getQtyKmLast());
		tracking.setQtyKmNext(this.getQtyKmNext());
		tracking.setobservaciones(this.getobservaciones());
		tracking.setSeqNo(this.getSeqNo());
		
		// Se crea aviso de tarea de mantenimiento expirada
		List<MTRConfigMaintainLine> userLines = null;
		String mensaje = "";
		
		if(this.getQtyKmNext()>0 && (this.getQtyKmNext()-this.getQtyKm() <=0)){ //la tarea se encuentra expirada			 
				 
			mensaje = "La tarea de mantenimiento '" + mant.getName() + "' para el vehiculo " + truck.getTruckNo() + " ha expirado.";
			
			sign = MRSign.forValue(getCtx(), "red", null);
			
			tracking.setTrackImage_ID(sign.getAD_Image_ID());
			tracking.setPrintSeqNo(sign.getSeqNo());
			tracking.saveEx();			
			
		//la tarea es por KILOMETRAJE y tiene definido, o no, un porcentaje de alarma	
		} else if(mant.getMaintainProg().equalsIgnoreCase("KILOMETRAJE") && mant.getporcentaje().compareTo(Env.ZERO)>0){
			
			int porcentaje = mant.getporcentaje().intValue();
			
			if(this.getQtyKmLast() > 0){
				
				int x = (this.getKilometros() * porcentaje)/100;
				
				if(this.getQtyKm() >= (this.getQtyKmLast() + x)){
					
					mensaje = "La tarea de mantenimiento '" + mant.getName() + "' para el vehiculo " + truck.getTruckNo() + " esta proxima a expirar.";	
					
					sign = MRSign.forValue(getCtx(), "yellow", null);
					
					tracking.setTrackImage_ID(sign.getAD_Image_ID());
					tracking.setPrintSeqNo(sign.getSeqNo());
					tracking.saveEx();						
				}			
			}				

		} 

		if(sign==null && tracking.getQtyKmLast()>0){

			sign = MRSign.forValue(getCtx(), "green", null);

			tracking.setTrackImage_ID(sign.getAD_Image_ID());
			tracking.setPrintSeqNo(sign.getSeqNo());
			tracking.saveEx();		

		}		

		if(!mensaje.equalsIgnoreCase("")){

			userLines = MTRConfigMaintainLine.getUserLines(getCtx(), get_TrxName());

			for(MTRConfigMaintainLine line : userLines){

				MNote note = new MNote(getCtx(), 339, line.getAD_User_ID(), this.get_Table_ID(), this.get_ID(), this.toString(), mensaje, get_TrxName());
				note.save();					 
			}			

		}

		return true;
	}

	@Override
	protected boolean afterDelete(boolean success) {
		
		DB.executeUpdateEx("delete from uy_tr_trackingmaintain where uy_tr_truck_id = " + this.getUY_TR_Truck_ID() +
				" and uy_tr_maintain_id = " + this.getUY_TR_Maintain_ID(), get_TrxName());
		
		
		return true;
	}
	
	/***
	 * Obtiene y retorna linea para el vehiculo y tarea recibidos.
	 * OpenUp Ltda. Issue #5355
	 * @author Nicolas Sarlabos - 25/01/2016
	 * @see
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static MTRTruckMaintain forTruckMaintain(Properties ctx, int truckID, int maintainID, String trxName){
		
		String whereClause = X_UY_TR_TruckMaintain.COLUMNNAME_UY_TR_Truck_ID + " = " + truckID +
				" AND " + X_UY_TR_TruckMaintain.COLUMNNAME_UY_TR_Maintain_ID + " = " + maintainID;
		
		MTRTruckMaintain model = new Query(ctx, I_UY_TR_TruckMaintain.Table_Name, whereClause, trxName)
		.setClient_ID()
		.first();
				
		return model;
	}

}
