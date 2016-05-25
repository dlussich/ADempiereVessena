/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 24/02/2015
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MTRLoadMsgLine
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Gabriel Vila - 24/02/2015
 * @see
 */
public class MTRLoadMsgLine extends X_UY_TR_LoadMsgLine {

	private static final long serialVersionUID = 5489397718162668359L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_TR_LoadMsgLine_ID
	 * @param trxName
	 */
	public MTRLoadMsgLine(Properties ctx, int UY_TR_LoadMsgLine_ID,
			String trxName) {
		super(ctx, UY_TR_LoadMsgLine_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTRLoadMsgLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

}
