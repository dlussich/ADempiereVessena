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
public class MExportInvTracking extends X_UY_ExportInvTracking {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2553128112532826254L;

	/**
	 * @param ctx
	 * @param UY_ExportInvTracking_ID
	 * @param trxName
	 */
	public MExportInvTracking(Properties ctx, int UY_ExportInvTracking_ID,
			String trxName) {
		super(ctx, UY_ExportInvTracking_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MExportInvTracking(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
