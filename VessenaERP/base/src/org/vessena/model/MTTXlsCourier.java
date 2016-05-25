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
public class MTTXlsCourier extends X_UY_TT_XlsCourier {

	/**
	 * 
	 */
	private static final long serialVersionUID = -78121552542538134L;

	/**
	 * @param ctx
	 * @param UY_TT_XlsCourier_ID
	 * @param trxName
	 */
	public MTTXlsCourier(Properties ctx, int UY_TT_XlsCourier_ID, String trxName) {
		super(ctx, UY_TT_XlsCourier_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTTXlsCourier(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
