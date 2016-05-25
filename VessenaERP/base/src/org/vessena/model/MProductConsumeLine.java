/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 23/07/2014
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MProductConsumeLine
 * OpenUp Ltda. Issue #1405 
 * Description: Modelo para linea de consumo de stock de producto.
 * @author Gabriel Vila - 23/07/2014
 * @see
 */
public class MProductConsumeLine extends X_UY_ProductConsumeLine {

	private static final long serialVersionUID = 6670895045697482827L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_ProductConsumeLine_ID
	 * @param trxName
	 */
	public MProductConsumeLine(Properties ctx, int UY_ProductConsumeLine_ID,
			String trxName) {
		super(ctx, UY_ProductConsumeLine_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MProductConsumeLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

}
