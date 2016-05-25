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
public class MTTConfigCardSMS extends X_UY_TT_ConfigCardSMS {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6737982472227751975L;

	/**
	 * @param ctx
	 * @param UY_TT_ConfigCardSMS_ID
	 * @param trxName
	 */
	public MTTConfigCardSMS(Properties ctx, int UY_TT_ConfigCardSMS_ID,
			String trxName) {
		super(ctx, UY_TT_ConfigCardSMS_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTTConfigCardSMS(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
