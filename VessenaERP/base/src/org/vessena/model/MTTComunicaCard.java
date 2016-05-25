/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 06/10/2013
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MTTComunicaCard
 * OpenUp Ltda. Issue #1173 
 * Description: Modelo de cuenta para proceso de comunicacion a usuario.
 * @author Gabriel Vila - 06/10/2013
 * @see
 */
public class MTTComunicaCard extends X_UY_TT_ComunicaCard {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8709840073994076904L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_TT_ComunicaCard_ID
	 * @param trxName
	 */
	public MTTComunicaCard(Properties ctx, int UY_TT_ComunicaCard_ID,
			String trxName) {
		super(ctx, UY_TT_ComunicaCard_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTTComunicaCard(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

}
