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
public class MCanalVentas extends X_UY_CanalVentas {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1150972658672805625L;

	/**
	 * @param ctx
	 * @param UY_CanalVentas_ID
	 * @param trxName
	 */
	public MCanalVentas(Properties ctx, int UY_CanalVentas_ID, String trxName) {
		super(ctx, UY_CanalVentas_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MCanalVentas(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
