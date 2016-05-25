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
public class MFduAutomaticsDebits extends X_UY_Fdu_AutomaticsDebits {

	/**
	 * 
	 */
	private static final long serialVersionUID = 570616718199173647L;

	/**
	 * @param ctx
	 * @param UY_Fdu_AutomaticsDebits_ID
	 * @param trxName
	 */
	public MFduAutomaticsDebits(Properties ctx, int UY_Fdu_AutomaticsDebits_ID,
			String trxName) {
		super(ctx, UY_Fdu_AutomaticsDebits_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MFduAutomaticsDebits(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
