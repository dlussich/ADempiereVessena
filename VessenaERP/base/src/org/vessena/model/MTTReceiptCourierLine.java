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
public class MTTReceiptCourierLine extends X_UY_TT_ReceiptCourierLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8981729822298132893L;

	/**
	 * @param ctx
	 * @param UY_TT_ReceiptCourierLine_ID
	 * @param trxName
	 */
	public MTTReceiptCourierLine(Properties ctx,
			int UY_TT_ReceiptCourierLine_ID, String trxName) {
		super(ctx, UY_TT_ReceiptCourierLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTTReceiptCourierLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
