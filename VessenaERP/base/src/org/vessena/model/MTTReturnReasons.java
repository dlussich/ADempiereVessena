/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * @author gbrust
 *
 */
public class MTTReturnReasons extends X_UY_TT_ReturnReasons {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6216704848693136557L;

	/**
	 * @param ctx
	 * @param UY_TT_ReturnReasons_ID
	 * @param trxName
	 */
	public MTTReturnReasons(Properties ctx, int UY_TT_ReturnReasons_ID,
			String trxName) {
		super(ctx, UY_TT_ReturnReasons_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTTReturnReasons(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
