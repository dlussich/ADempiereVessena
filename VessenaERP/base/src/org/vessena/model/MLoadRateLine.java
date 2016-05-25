/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 14/07/2014
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MLoadRateLine
 * OpenUp Ltda. Issue #1918 
 * Description: 
 * @author Gabriel Vila - 14/07/2014
 * @see
 */
public class MLoadRateLine extends X_UY_LoadRateLine {

	private static final long serialVersionUID = -8295566845994687380L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_LoadRateLine_ID
	 * @param trxName
	 */
	public MLoadRateLine(Properties ctx, int UY_LoadRateLine_ID, String trxName) {
		super(ctx, UY_LoadRateLine_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MLoadRateLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

}
