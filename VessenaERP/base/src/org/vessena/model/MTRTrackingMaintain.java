/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.Query;

/**
 * @author Nicolás
 *
 */
public class MTRTrackingMaintain extends X_UY_TR_TrackingMaintain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param ctx
	 * @param UY_TR_TrackingMaintain_ID
	 * @param trxName
	 */
	public MTRTrackingMaintain(Properties ctx, int UY_TR_TrackingMaintain_ID, String trxName) {
		super(ctx, UY_TR_TrackingMaintain_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTRTrackingMaintain(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	/***
	 * Obtiene y retorna modelo de linea segun ID de vehiculo y tarea de mantenimiento. 
	 * OpenUp Ltda. Issue #5355
	 * @author Nicolas Sarlabos - 22/01/2016
	 * @see
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static MTRTrackingMaintain forTruckTask(Properties ctx, int truckID, int maintainID, String trxName){

		MTRTrackingMaintain model = null;
		
		String whereClause = X_UY_TR_TrackingMaintain.COLUMNNAME_UY_TR_Truck_ID + " = " + truckID + 
				" and " + X_UY_TR_TrackingMaintain.COLUMNNAME_UY_TR_Maintain_ID + " = " + maintainID;				

		model = new Query(ctx, I_UY_TR_TrackingMaintain.Table_Name, whereClause, trxName)
		.first();

		return model;
	}

}
