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
public class MTRTruckRepair extends X_UY_TR_TruckRepair {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8613650302917077897L;

	/**
	 * @param ctx
	 * @param UY_TR_TruckRepair_ID
	 * @param trxName
	 */
	public MTRTruckRepair(Properties ctx, int UY_TR_TruckRepair_ID,
			String trxName) {
		super(ctx, UY_TR_TruckRepair_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTRTruckRepair(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
