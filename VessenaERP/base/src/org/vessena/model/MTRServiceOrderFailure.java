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
public class MTRServiceOrderFailure extends X_UY_TR_ServiceOrderFailure {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4628165094505546908L;

	/**
	 * @param ctx
	 * @param UY_TR_ServiceOrderFailure_ID
	 * @param trxName
	 */
	public MTRServiceOrderFailure(Properties ctx,
			int UY_TR_ServiceOrderFailure_ID, String trxName) {
		super(ctx, UY_TR_ServiceOrderFailure_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTRServiceOrderFailure(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
