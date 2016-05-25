/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 03/03/2015
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MTRLoadMsgLog
 * OpenUp Ltda. Issue #1405 
 * Description: Modelo de log de envio de mensajes de carga.
 * @author Gabriel Vila - 03/03/2015
 * @see
 */
public class MTRLoadMsgLog extends X_UY_TR_LoadMsgLog {

	private static final long serialVersionUID = -3518586245377273528L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_TR_LoadMsgLog_ID
	 * @param trxName
	 */
	public MTRLoadMsgLog(Properties ctx, int UY_TR_LoadMsgLog_ID, String trxName) {
		super(ctx, UY_TR_LoadMsgLog_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTRLoadMsgLog(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

}
