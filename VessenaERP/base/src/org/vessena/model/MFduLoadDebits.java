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
public class MFduLoadDebits extends X_UY_Fdu_LoadDebits {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4698602933680880954L;

	/**
	 * @param ctx
	 * @param UY_Fdu_LoadDebits_ID
	 * @param trxName
	 */
	public MFduLoadDebits(Properties ctx, int UY_Fdu_LoadDebits_ID,
			String trxName) {
		super(ctx, UY_Fdu_LoadDebits_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MFduLoadDebits(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
