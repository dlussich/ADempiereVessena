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
public class MRePrintCheckTracking extends X_UY_RePrintCheckTracking {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7158919165765039560L;

	/**
	 * @param ctx
	 * @param UY_RePrintCheckTracking_ID
	 * @param trxName
	 */
	public MRePrintCheckTracking(Properties ctx,
			int UY_RePrintCheckTracking_ID, String trxName) {
		super(ctx, UY_RePrintCheckTracking_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRePrintCheckTracking(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
