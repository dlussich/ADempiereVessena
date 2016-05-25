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
public class MTRReadKmMaintain extends X_UY_TR_ReadKmMaintain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 747889739089286463L;

	/**
	 * @param ctx
	 * @param UY_TR_ReadKmMaintain_ID
	 * @param trxName
	 */
	public MTRReadKmMaintain(Properties ctx, int UY_TR_ReadKmMaintain_ID,
			String trxName) {
		super(ctx, UY_TR_ReadKmMaintain_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTRReadKmMaintain(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
