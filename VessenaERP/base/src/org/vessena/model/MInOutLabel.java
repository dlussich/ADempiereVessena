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
public class MInOutLabel extends X_UY_InOutLabel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1990317272744372394L;

	/**
	 * @param ctx
	 * @param UY_InOutLabel_ID
	 * @param trxName
	 */
	public MInOutLabel(Properties ctx, int UY_InOutLabel_ID, String trxName) {
		super(ctx, UY_InOutLabel_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MInOutLabel(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
