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
public class MProdConfig extends X_UY_ProdConfig {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1689255865730435490L;

	/**
	 * @param ctx
	 * @param UY_ProdConfig_ID
	 * @param trxName
	 */
	public MProdConfig(Properties ctx, int UY_ProdConfig_ID, String trxName) {
		super(ctx, UY_ProdConfig_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MProdConfig(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
