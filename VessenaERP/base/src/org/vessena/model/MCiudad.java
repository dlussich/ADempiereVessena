/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * @author Nicolas
 *
 */
public class MCiudad extends X_UY_Ciudad {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5324130784044538392L;

	/**
	 * @param ctx
	 * @param UY_Ciudad_ID
	 * @param trxName
	 */
	public MCiudad(Properties ctx, int UY_Ciudad_ID, String trxName) {
		super(ctx, UY_Ciudad_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MCiudad(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
