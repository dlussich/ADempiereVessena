/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 27/08/2013
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MTTReceiptCard
 * OpenUp Ltda. Issue #1173 
 * Description: Modelo para cuenta escaneada en una recepcion de bolsin.
 * @author Gabriel Vila - 27/08/2013
 * @see
 */
public class MTTReceiptCard extends X_UY_TT_ReceiptCard {

	private static final long serialVersionUID = 3280755180761840230L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_TT_ReceiptCard_ID
	 * @param trxName
	 */
	public MTTReceiptCard(Properties ctx, int UY_TT_ReceiptCard_ID,
			String trxName) {
		super(ctx, UY_TT_ReceiptCard_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTTReceiptCard(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	
}
