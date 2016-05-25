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
public class MTRClearingFuel extends X_UY_TR_ClearingFuel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4736052936395836089L;

	/**
	 * @param ctx
	 * @param UY_TR_ClearingFuel_ID
	 * @param trxName
	 */
	public MTRClearingFuel(Properties ctx, int UY_TR_ClearingFuel_ID,
			String trxName) {
		super(ctx, UY_TR_ClearingFuel_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTRClearingFuel(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
