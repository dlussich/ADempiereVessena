/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 12/01/2015
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MTRPaymentFlete
 * OpenUp Ltda. Issue #1405 
 * Description: 
 * @author Gabriel Vila - 12/01/2015
 * @see
 */
public class MTRPaymentFlete extends X_UY_TR_PaymentFlete {

	private static final long serialVersionUID = 8595853809334031031L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_TR_PaymentFlete_ID
	 * @param trxName
	 */
	public MTRPaymentFlete(Properties ctx, int UY_TR_PaymentFlete_ID,
			String trxName) {
		super(ctx, UY_TR_PaymentFlete_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTRPaymentFlete(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

}
