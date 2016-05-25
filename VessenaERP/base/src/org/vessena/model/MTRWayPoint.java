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
public class MTRWayPoint extends X_UY_TR_WayPoint {

	/**
	 * 
	 */
	private static final long serialVersionUID = -880476481777194555L;

	/**
	 * @param ctx
	 * @param UY_TR_WayPoint_ID
	 * @param trxName
	 */
	public MTRWayPoint(Properties ctx, int UY_TR_WayPoint_ID, String trxName) {
		super(ctx, UY_TR_WayPoint_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTRWayPoint(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
