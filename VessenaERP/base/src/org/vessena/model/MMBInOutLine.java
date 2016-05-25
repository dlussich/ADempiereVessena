/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * @author SBT
 *
 */
public class MMBInOutLine extends X_UY_MB_InOutLine {

	/**
	 * @param ctx
	 * @param UY_MB_InOutLine_ID
	 * @param trxName
	 */
	public MMBInOutLine(Properties ctx, int UY_MB_InOutLine_ID, String trxName) {
		super(ctx, UY_MB_InOutLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MMBInOutLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
