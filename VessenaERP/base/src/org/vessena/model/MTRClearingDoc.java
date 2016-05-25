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
public class MTRClearingDoc extends X_UY_TR_ClearingDoc {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4818715562067470279L;

	/**
	 * @param ctx
	 * @param UY_TR_ClearingDoc_ID
	 * @param trxName
	 */
	public MTRClearingDoc(Properties ctx, int UY_TR_ClearingDoc_ID,
			String trxName) {
		super(ctx, UY_TR_ClearingDoc_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTRClearingDoc(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
