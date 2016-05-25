/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 25/02/2015
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MTRLoadMsgType
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Gabriel Vila - 25/02/2015
 * @see
 */
public class MTRLoadMsgType extends X_UY_TR_LoadMsgType {

	/**
	 * 
	 */
	private static final long serialVersionUID = -438131536252040651L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_TR_LoadMsgType_ID
	 * @param trxName
	 */
	public MTRLoadMsgType(Properties ctx, int UY_TR_LoadMsgType_ID,
			String trxName) {
		super(ctx, UY_TR_LoadMsgType_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTRLoadMsgType(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

}
