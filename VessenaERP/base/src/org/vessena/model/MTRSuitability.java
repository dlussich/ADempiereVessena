/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 01/01/2015
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MTRSuitability
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Gabriel Vila - 01/01/2015
 * @see
 */
public class MTRSuitability extends X_UY_TR_Suitability {

	private static final long serialVersionUID = -3083261949137418013L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_TR_Suitability_ID
	 * @param trxName
	 */
	public MTRSuitability(Properties ctx, int UY_TR_Suitability_ID,
			String trxName) {
		super(ctx, UY_TR_Suitability_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTRSuitability(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

}
