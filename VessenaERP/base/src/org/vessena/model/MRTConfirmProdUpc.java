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
public class MRTConfirmProdUpc extends X_UY_RT_ConfirmProdUpc {

	/**
	 * 
	 */
	private static final long serialVersionUID = 959397141595024879L;

	/**
	 * @param ctx
	 * @param UY_RT_ConfirmProdUpc_ID
	 * @param trxName
	 */
	public MRTConfirmProdUpc(Properties ctx, int UY_RT_ConfirmProdUpc_ID,
			String trxName) {
		super(ctx, UY_RT_ConfirmProdUpc_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRTConfirmProdUpc(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
