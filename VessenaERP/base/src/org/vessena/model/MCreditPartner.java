/**
 * MCreditPartner.java
 * 08/02/2011
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * OpenUp.
 * MCreditPartner
 * Descripcion :
 * @author Gabriel Vila
 * Fecha : 08/02/2011
 */
public class MCreditPartner extends X_UY_Credit_Partner {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8167516582224265021L;

	/**
	 * Constructor
	 * @param ctx
	 * @param UY_Credit_Partner_ID
	 * @param trxName
	 */
	public MCreditPartner(Properties ctx, int UY_Credit_Partner_ID,
			String trxName) {
		super(ctx, UY_Credit_Partner_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MCreditPartner(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
