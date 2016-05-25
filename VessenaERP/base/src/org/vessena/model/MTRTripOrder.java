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
public class MTRTripOrder extends X_UY_TR_TripOrder {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4522628925090148596L;

	/**
	 * @param ctx
	 * @param UY_TR_TripOrder_ID
	 * @param trxName
	 */
	public MTRTripOrder(Properties ctx, int UY_TR_TripOrder_ID, String trxName) {
		super(ctx, UY_TR_TripOrder_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTRTripOrder(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	
	
	public static MTRTripOrder forTripIDAndOrderID(Properties ctx, int tripID, int orderID, String trxName){
		
		String whereClause = X_UY_TR_TripOrder.COLUMNNAME_UY_TR_Trip_ID + " = " + tripID + 
						" AND " + X_UY_TR_TripOrder.COLUMNNAME_UY_TR_TransOrder_ID + " = " + orderID;
		
		return new Query(ctx, I_UY_TR_TripOrder.Table_Name, whereClause, trxName).first();
				
	}

}
