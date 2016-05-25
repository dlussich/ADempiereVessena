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
public class MTRMark extends X_UY_TR_Mark {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3937419334161752763L;

	/**
	 * @param ctx
	 * @param UY_TR_Mark_ID
	 * @param trxName
	 */
	public MTRMark(Properties ctx, int UY_TR_Mark_ID, String trxName) {
		super(ctx, UY_TR_Mark_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTRMark(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
