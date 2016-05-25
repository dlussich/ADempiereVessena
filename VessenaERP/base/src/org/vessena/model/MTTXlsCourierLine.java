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
public class MTTXlsCourierLine extends X_UY_TT_XlsCourierLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6445757661315745261L;

	/**
	 * @param ctx
	 * @param UY_TT_XlsCourierLine_ID
	 * @param trxName
	 */
	public MTTXlsCourierLine(Properties ctx, int UY_TT_XlsCourierLine_ID,
			String trxName) {
		super(ctx, UY_TT_XlsCourierLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTTXlsCourierLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
