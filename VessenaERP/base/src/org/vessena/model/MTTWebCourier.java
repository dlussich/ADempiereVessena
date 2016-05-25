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
public class MTTWebCourier extends X_UY_TT_WebCourier {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2426708101211615696L;

	/**
	 * @param ctx
	 * @param UY_TT_WebCourier_ID
	 * @param trxName
	 */
	public MTTWebCourier(Properties ctx, int UY_TT_WebCourier_ID, String trxName) {
		super(ctx, UY_TT_WebCourier_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTTWebCourier(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
