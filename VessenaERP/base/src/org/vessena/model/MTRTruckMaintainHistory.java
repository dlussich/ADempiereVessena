/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * @author Nicolas
 *
 */
public class MTRTruckMaintainHistory extends X_UY_TR_TruckMaintainHistory {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3580402954086411890L;

	/**
	 * @param ctx
	 * @param UY_TR_TruckMaintainHistory_ID
	 * @param trxName
	 */
	public MTRTruckMaintainHistory(Properties ctx,
			int UY_TR_TruckMaintainHistory_ID, String trxName) {
		super(ctx, UY_TR_TruckMaintainHistory_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTRTruckMaintainHistory(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
