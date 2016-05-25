/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * @author SBT
 *
 */
public class MMBInOut extends X_UY_MB_InOut {

	/**
	 * @param ctx
	 * @param UY_MB_InOut_ID
	 * @param trxName
	 */
	public MMBInOut(Properties ctx, int UY_MB_InOut_ID, String trxName) {
		super(ctx, UY_MB_InOut_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MMBInOut(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
