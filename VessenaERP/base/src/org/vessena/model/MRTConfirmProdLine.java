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
public class MRTConfirmProdLine extends X_UY_RT_ConfirmProdLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8618342980988676842L;

	/**
	 * @param ctx
	 * @param UY_RT_ConfirmProdLine_ID
	 * @param trxName
	 */
	public MRTConfirmProdLine(Properties ctx, int UY_RT_ConfirmProdLine_ID,
			String trxName) {
		super(ctx, UY_RT_ConfirmProdLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRTConfirmProdLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
