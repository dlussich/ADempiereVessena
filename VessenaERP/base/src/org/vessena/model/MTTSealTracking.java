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
public class MTTSealTracking extends X_UY_TT_SealTracking {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9086918739669506137L;

	/**
	 * @param ctx
	 * @param UY_TT_SealTracking_ID
	 * @param trxName
	 */
	public MTTSealTracking(Properties ctx, int UY_TT_SealTracking_ID,
			String trxName) {
		super(ctx, UY_TT_SealTracking_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTTSealTracking(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
