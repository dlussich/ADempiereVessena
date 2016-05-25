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
public class MMBInOutLog extends X_UY_MB_InOutLog {

	/**
	 * @param ctx
	 * @param UY_MB_InOutLog_ID
	 * @param trxName
	 */
	public MMBInOutLog(Properties ctx, int UY_MB_InOutLog_ID, String trxName) {
		super(ctx, UY_MB_InOutLog_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MMBInOutLog(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
