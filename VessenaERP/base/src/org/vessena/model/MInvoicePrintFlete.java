/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 12/01/2015
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MInvoicePrintFlete
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Gabriel Vila - 12/01/2015
 * @see
 */
public class MInvoicePrintFlete extends X_C_InvoicePrintFlete {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6468429606180750249L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param C_InvoicePrintFlete_ID
	 * @param trxName
	 */
	public MInvoicePrintFlete(Properties ctx, int C_InvoicePrintFlete_ID,
			String trxName) {
		super(ctx, C_InvoicePrintFlete_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MInvoicePrintFlete(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

}
