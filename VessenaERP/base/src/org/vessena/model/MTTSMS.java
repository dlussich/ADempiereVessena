/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 10/10/2013
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MTTSMS
 * OpenUp Ltda. Issue #1173 
 * Description: Modelo de SMS
 * @author Gabriel Vila - 10/10/2013
 * @see
 */
public class MTTSMS extends X_UY_TT_SMS {

	private static final long serialVersionUID = -8367008990490718932L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_TT_SMS_ID
	 * @param trxName
	 */
	public MTTSMS(Properties ctx, int UY_TT_SMS_ID, String trxName) {
		super(ctx, UY_TT_SMS_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTTSMS(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

}
