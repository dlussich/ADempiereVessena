package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;
/**
 * org.openup.model - MTTRetPrintValeCard
 * OpenUp Ltda. Issue #3273 
 * Description: Modelo de cuenta para proceso de impresion de vale.
 * @author Sylvie Bouissa - 06/01/2015
 * @see
 */
public class MTTRetPrintValeCard extends X_UY_TT_RetPrintValeCard {

	public MTTRetPrintValeCard(Properties ctx, int UY_TT_RetPrintValeCard_ID,
			String trxName) {
		super(ctx, UY_TT_RetPrintValeCard_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public MTTRetPrintValeCard(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
