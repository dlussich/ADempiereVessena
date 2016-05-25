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
public class MFduCoefficientIpc extends X_UY_Fdu_CoefficientIpc {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2287257536643968750L;

	/**
	 * @param ctx
	 * @param UY_Fdu_CoefficientIpc_ID
	 * @param trxName
	 */
	public MFduCoefficientIpc(Properties ctx, int UY_Fdu_CoefficientIpc_ID,
			String trxName) {
		super(ctx, UY_Fdu_CoefficientIpc_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MFduCoefficientIpc(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
