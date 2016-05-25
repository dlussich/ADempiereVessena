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
public class MTRMicTask extends X_UY_TR_MicTask {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7002524852111981254L;

	/**
	 * @param ctx
	 * @param UY_TR_MicTask_ID
	 * @param trxName
	 */
	public MTRMicTask(Properties ctx, int UY_TR_MicTask_ID, String trxName) {
		super(ctx, UY_TR_MicTask_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTRMicTask(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
