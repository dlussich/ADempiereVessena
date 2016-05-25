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
public class MTRFailure extends X_UY_TR_Failure {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2410322409240700769L;

	/**
	 * @param ctx
	 * @param UY_TR_Failure_ID
	 * @param trxName
	 */
	public MTRFailure(Properties ctx, int UY_TR_Failure_ID, String trxName) {
		super(ctx, UY_TR_Failure_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTRFailure(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
