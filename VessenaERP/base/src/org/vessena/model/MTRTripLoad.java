/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.Query;

/**
 * @author gbrust
 *
 */
public class MTRTripLoad extends X_UY_TR_TripLoad {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8337216155076144242L;

	/**
	 * @param ctx
	 * @param UY_TR_TripLoad_ID
	 * @param trxName
	 */
	public MTRTripLoad(Properties ctx, int UY_TR_TripLoad_ID, String trxName) {
		super(ctx, UY_TR_TripLoad_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTRTripLoad(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	
	
	public static MTRTripLoad forTripIDAndOrderID(Properties ctx, int tripID, int orderID, String trxName){
		
		String whereClause = X_UY_TR_TripLoad.COLUMNNAME_UY_TR_Trip_ID + " = " + tripID + 
						" AND " + X_UY_TR_TripLoad.COLUMNNAME_UY_TR_TransOrder_ID + " = " + orderID;
		
		return new Query(ctx, I_UY_TR_TripLoad.Table_Name, whereClause, trxName).first();
				
	}

}
