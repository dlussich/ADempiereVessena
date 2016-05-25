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
public class MRePrintCheckLine extends X_UY_RePrintCheckLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8340304838061159109L;

	/**
	 * @param ctx
	 * @param UY_RePrintCheckLine_ID
	 * @param trxName
	 */
	public MRePrintCheckLine(Properties ctx, int UY_RePrintCheckLine_ID,
			String trxName) {
		super(ctx, UY_RePrintCheckLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRePrintCheckLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
