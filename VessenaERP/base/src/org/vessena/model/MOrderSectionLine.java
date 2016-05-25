/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 22/02/2013
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MOrderSectionLine
 * OpenUp Ltda. Issue #381 
 * Description: Lineas de orden de compra parcial por sector.
 * @author Gabriel Vila - 22/02/2013
 * @see
 */
public class MOrderSectionLine extends X_UY_OrderSectionLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2744546493468021711L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_OrderSectionLine_ID
	 * @param trxName
	 */
	public MOrderSectionLine(Properties ctx, int UY_OrderSectionLine_ID,
			String trxName) {
		super(ctx, UY_OrderSectionLine_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MOrderSectionLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

}
