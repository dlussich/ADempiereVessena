/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.Query;

/**
 * @author Nicolas
 *
 */
public class MTRClearingTruck extends X_UY_TR_ClearingTruck {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6047304895822087082L;

	/**
	 * @param ctx
	 * @param UY_TR_ClearingTruck_ID
	 * @param trxName
	 */
	public MTRClearingTruck(Properties ctx, int UY_TR_ClearingTruck_ID,
			String trxName) {
		super(ctx, UY_TR_ClearingTruck_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTRClearingTruck(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	/***
	 * Obtiene y retorna modelo segun ID de cabezal de liquidacion y vehiculo recibidos. 
	 * OpenUp Ltda. Issue #3505
	 * @author Nicolas Sarlabos - 11/04/2015
	 * @see
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static MTRClearingTruck forHdrAndTruck(Properties ctx, int hdrID, int truckID, String trxName){

		String whereClause = X_UY_TR_ClearingTruck.COLUMNNAME_UY_TR_Clearing_ID + "=" + hdrID + 
				" and " + X_UY_TR_ClearingTruck.COLUMNNAME_UY_TR_Truck_ID + "=" + truckID;

		MTRClearingTruck model = new Query(ctx, I_UY_TR_ClearingTruck.Table_Name, whereClause, trxName)
		.first();

		return model;
	}

}
