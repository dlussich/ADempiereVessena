/**
 * MStockStatusListLine.java
 * 29/03/2011
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * OpenUp.
 * MStockStatusListLine
 * Descripcion :
 * @author Gabriel Vila
 * Fecha : 29/03/2011
 */
public class MStockStatusListLine extends X_UY_StockStatusListLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4389554706606885415L;

	/**
	 * Constructor
	 * @param ctx
	 * @param UY_StockStatusListLine_ID
	 * @param trxName
	 */
	public MStockStatusListLine(Properties ctx, int UY_StockStatusListLine_ID,
			String trxName) {
		super(ctx, UY_StockStatusListLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MStockStatusListLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
