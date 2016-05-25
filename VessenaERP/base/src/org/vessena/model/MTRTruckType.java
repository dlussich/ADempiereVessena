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
public class MTRTruckType extends X_UY_TR_TruckType {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7990971720704821707L;

	/**
	 * @param ctx
	 * @param UY_TR_TruckType_ID
	 * @param trxName
	 */
	public MTRTruckType(Properties ctx, int UY_TR_TruckType_ID, String trxName) {
		super(ctx, UY_TR_TruckType_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTRTruckType(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
