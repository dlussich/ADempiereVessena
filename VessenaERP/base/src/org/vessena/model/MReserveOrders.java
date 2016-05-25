/**
 * MReserveOrders.java
 * 11/01/2011
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * OpenUp.
 * MReserveOrders
 * Descripcion :
 * @author Gabriel Vila
 * Fecha : 11/01/2011
 */
public class MReserveOrders extends X_UY_Reserve_Orders {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3471989171518374256L;

	/**
	 * Constructor
	 * @param ctx
	 * @param UY_Reserve_Orders_ID
	 * @param trxName
	 */
	public MReserveOrders(Properties ctx, int UY_Reserve_Orders_ID,
			String trxName) {
		super(ctx, UY_Reserve_Orders_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MReserveOrders(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
