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
public class MTRAduana extends X_UY_TR_Aduana {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9145258622449509390L;

	/**
	 * @param ctx
	 * @param UY_TR_Aduana_ID
	 * @param trxName
	 */
	public MTRAduana(Properties ctx, int UY_TR_Aduana_ID, String trxName) {
		super(ctx, UY_TR_Aduana_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTRAduana(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
