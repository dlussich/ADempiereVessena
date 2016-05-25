/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 13/10/2013
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MTTMensaje
 * OpenUp Ltda. Issue #1173 
 * Description: Modelo de Mensaje Tracking
 * @author Gabriel Vila - 13/10/2013
 * @see
 */
public class MTTMensaje extends X_UY_TT_Mensaje {

	private static final long serialVersionUID = -1516815710158470037L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_TT_Mensaje_ID
	 * @param trxName
	 */
	public MTTMensaje(Properties ctx, int UY_TT_Mensaje_ID, String trxName) {
		super(ctx, UY_TT_Mensaje_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTTMensaje(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

}
