/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 07/01/2015
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MInvoicePrintConfig
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Gabriel Vila - 07/01/2015
 * @see
 */
public class MInvoicePrintConfig extends X_C_InvoicePrintConfig {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2772561498627502865L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param C_InvoicePrintConfig_ID
	 * @param trxName
	 */
	public MInvoicePrintConfig(Properties ctx, int C_InvoicePrintConfig_ID,
			String trxName) {
		super(ctx, C_InvoicePrintConfig_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MInvoicePrintConfig(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
