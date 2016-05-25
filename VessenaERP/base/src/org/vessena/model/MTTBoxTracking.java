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
public class MTTBoxTracking extends X_UY_TT_BoxTracking {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2400508733731929835L;

	/**
	 * @param ctx
	 * @param UY_TT_BoxTracking_ID
	 * @param trxName
	 */
	public MTTBoxTracking(Properties ctx, int UY_TT_BoxTracking_ID,
			String trxName) {
		super(ctx, UY_TT_BoxTracking_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTTBoxTracking(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
