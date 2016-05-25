/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * @author emiliano
 *
 */
public class MResguardoGenLine extends X_UY_ResguardoGenLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6924444941378160145L;

	/**
	 * @param ctx
	 * @param UY_ResguardoGenLine_ID
	 * @param trxName
	 */
	public MResguardoGenLine(Properties ctx, int UY_ResguardoGenLine_ID, String trxName) {
		super(ctx, UY_ResguardoGenLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MResguardoGenLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
