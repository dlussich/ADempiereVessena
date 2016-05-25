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
public class MFduAdjCallFduLine extends X_UY_Fdu_AdjCallFduLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8790057794890453974L;

	/**
	 * @param ctx
	 * @param UY_Fdu_AdjCallFduLine_ID
	 * @param trxName
	 */
	public MFduAdjCallFduLine(Properties ctx, int UY_Fdu_AdjCallFduLine_ID,
			String trxName) {
		super(ctx, UY_Fdu_AdjCallFduLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MFduAdjCallFduLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
