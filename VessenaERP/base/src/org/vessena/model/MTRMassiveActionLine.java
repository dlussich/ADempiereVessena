/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 20/04/2015
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MTRMassiveActionLine
 * OpenUp Ltda. Issue #3996 
 * Description: 
 * @author Gabriel Vila - 20/04/2015
 * @see
 */
public class MTRMassiveActionLine extends X_UY_TR_MassiveActionLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2852508368942520444L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_TR_MassiveActionLine_ID
	 * @param trxName
	 */
	public MTRMassiveActionLine(Properties ctx, int UY_TR_MassiveActionLine_ID,
			String trxName) {
		super(ctx, UY_TR_MassiveActionLine_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTRMassiveActionLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	
	
}
