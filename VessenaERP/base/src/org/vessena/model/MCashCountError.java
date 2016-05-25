/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * @author Nicolás
 *
 */
public class MCashCountError extends X_UY_CashCountError {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7976391248747957071L;

	/**
	 * @param ctx
	 * @param UY_CashCountError_ID
	 * @param trxName
	 */
	public MCashCountError(Properties ctx, int UY_CashCountError_ID, String trxName) {
		super(ctx, UY_CashCountError_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MCashCountError(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
