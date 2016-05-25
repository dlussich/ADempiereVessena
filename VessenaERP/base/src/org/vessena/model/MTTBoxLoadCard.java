/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 13/11/2013
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MTTBoxLoadCard
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Gabriel Vila - 13/11/2013
 * @see
 */
public class MTTBoxLoadCard extends X_UY_TT_BoxLoadCard {

	private static final long serialVersionUID = 2540039459272663531L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_TT_BoxLoadCard_ID
	 * @param trxName
	 */
	public MTTBoxLoadCard(Properties ctx, int UY_TT_BoxLoadCard_ID,
			String trxName) {
		super(ctx, UY_TT_BoxLoadCard_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTTBoxLoadCard(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

}
