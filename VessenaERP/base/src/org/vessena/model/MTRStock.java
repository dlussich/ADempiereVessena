/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 12/12/2014
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MTRStock
 * OpenUp Ltda. Issue #1405 
 * Description: Modelo de ficha de stock para cargas y descargas de mercaderias en almacenes. Modulo de Transporte.
 * @author Gabriel Vila - 12/12/2014
 * @see
 */
public class MTRStock extends X_UY_TR_Stock {

	private static final long serialVersionUID = -5679085860497768572L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_TR_Stock_ID
	 * @param trxName
	 */
	public MTRStock(Properties ctx, int UY_TR_Stock_ID, String trxName) {
		super(ctx, UY_TR_Stock_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTRStock(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

}
