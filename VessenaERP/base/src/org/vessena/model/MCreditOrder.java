/**
 * MCreditOrder.java
 * 08/02/2011
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * OpenUp.
 * MCreditOrder
 * Descripcion :
 * @author Gabriel Vila
 * Fecha : 08/02/2011
 */
public class MCreditOrder extends X_UY_Credit_Order {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2881689027285304519L;

	/**
	 * Constructor
	 * @param ctx
	 * @param UY_Credit_Order_ID
	 * @param trxName
	 */
	public MCreditOrder(Properties ctx, int UY_Credit_Order_ID, String trxName) {
		super(ctx, UY_Credit_Order_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MCreditOrder(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
