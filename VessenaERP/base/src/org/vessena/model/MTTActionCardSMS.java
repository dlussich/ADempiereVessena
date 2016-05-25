/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 10/10/2013
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MTTActionCardSMS
 * OpenUp Ltda. Issue #1173 
 * Description: Modelo para detalle de sms.
 * @author Gabriel Vila - 10/10/2013
 * @see
 */
public class MTTActionCardSMS extends X_UY_TT_ActionCardSMS {

	private static final long serialVersionUID = -1059335155808697291L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_TT_ActionCardSMS_ID
	 * @param trxName
	 */
	public MTTActionCardSMS(Properties ctx, int UY_TT_ActionCardSMS_ID,
			String trxName) {
		super(ctx, UY_TT_ActionCardSMS_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTTActionCardSMS(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

}
