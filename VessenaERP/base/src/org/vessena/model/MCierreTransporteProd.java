/**
 * MCierreTransporteProd.java
 * 15/02/2011
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * OpenUp.
 * MCierreTransporteProd
 * Descripcion :
 * @author Gabriel Vila
 * Fecha : 15/02/2011
 */
public class MCierreTransporteProd extends X_UY_CierreTransporteProd {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8298152950018318859L;

	/**
	 * Constructor
	 * @param ctx
	 * @param UY_CierreTransporteProd_ID
	 * @param trxName
	 */
	public MCierreTransporteProd(Properties ctx,
			int UY_CierreTransporteProd_ID, String trxName) {
		super(ctx, UY_CierreTransporteProd_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MCierreTransporteProd(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
