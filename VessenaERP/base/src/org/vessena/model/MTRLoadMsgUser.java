/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 25/02/2015
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MTRLoadMsgUser
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Gabriel Vila - 25/02/2015
 * @see
 */
public class MTRLoadMsgUser extends X_UY_TR_LoadMsgUser {

	private static final long serialVersionUID = -7107818630425186113L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_TR_LoadMsgUser_ID
	 * @param trxName
	 */
	public MTRLoadMsgUser(Properties ctx, int UY_TR_LoadMsgUser_ID,
			String trxName) {
		super(ctx, UY_TR_LoadMsgUser_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTRLoadMsgUser(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

}
