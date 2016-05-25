/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 12/09/2013
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MTTUnifyCard
 * OpenUp Ltda. Issue #1173 
 * Description: Modelo de cuenta asociada a documento de unificacion de cardcarriers.
 * @author Gabriel Vila - 12/09/2013
 * @see
 */
public class MTTUnifyCard extends X_UY_TT_UnifyCard {

	private static final long serialVersionUID = 6680099965473465818L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_TT_UnifyCard_ID
	 * @param trxName
	 */
	public MTTUnifyCard(Properties ctx, int UY_TT_UnifyCard_ID, String trxName) {
		super(ctx, UY_TT_UnifyCard_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTTUnifyCard(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

}
