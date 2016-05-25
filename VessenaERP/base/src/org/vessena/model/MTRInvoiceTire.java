/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 07/07/2014
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MTRInvoiceTire
 * OpenUp Ltda. Issue #1405 
 * Description: Modelo para detalle de un neumatico en factura proveedor.
 * @author Gabriel Vila - 07/07/2014
 * @see
 */
public class MTRInvoiceTire extends X_UY_TR_InvoiceTire {

	private static final long serialVersionUID = -263961392790250822L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_TR_InvoiceTire_ID
	 * @param trxName
	 */
	public MTRInvoiceTire(Properties ctx, int UY_TR_InvoiceTire_ID,
			String trxName) {
		super(ctx, UY_TR_InvoiceTire_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTRInvoiceTire(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

}
