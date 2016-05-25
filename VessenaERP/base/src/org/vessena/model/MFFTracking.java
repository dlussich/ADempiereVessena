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
public class MFFTracking extends X_UY_FF_Tracking {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3207221483045965523L;

	/**
	 * @param ctx
	 * @param UY_FF_Tracking_ID
	 * @param trxName
	 */
	public MFFTracking(Properties ctx, int UY_FF_Tracking_ID, String trxName) {
		super(ctx, UY_FF_Tracking_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MFFTracking(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
