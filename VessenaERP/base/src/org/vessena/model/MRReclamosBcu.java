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
public class MRReclamosBcu extends X_UY_R_ReclamosBcu {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5254681813063687198L;

	/**
	 * @param ctx
	 * @param UY_R_ReclamosBcu_ID
	 * @param trxName
	 */
	public MRReclamosBcu(Properties ctx, int UY_R_ReclamosBcu_ID, String trxName) {
		super(ctx, UY_R_ReclamosBcu_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRReclamosBcu(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
