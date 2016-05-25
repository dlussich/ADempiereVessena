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
public class MFduMassiveAdjustLine extends X_UY_Fdu_MassiveAdjustLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7291753969139926527L;

	/**
	 * @param ctx
	 * @param UY_Fdu_MassiveAdjustLine_ID
	 * @param trxName
	 */
	public MFduMassiveAdjustLine(Properties ctx,
			int UY_Fdu_MassiveAdjustLine_ID, String trxName) {
		super(ctx, UY_Fdu_MassiveAdjustLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MFduMassiveAdjustLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
