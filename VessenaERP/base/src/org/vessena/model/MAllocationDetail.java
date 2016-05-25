/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * @author Hp
 *
 */
public class MAllocationDetail extends X_UY_AllocationDetail {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1718864590014069870L;

	/**
	 * @param ctx
	 * @param UY_AllocationDetail_ID
	 * @param trxName
	 */
	public MAllocationDetail(Properties ctx, int UY_AllocationDetail_ID,
			String trxName) {
		super(ctx, UY_AllocationDetail_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MAllocationDetail(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
