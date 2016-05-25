/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * @author gbrust
 *
 */
public class MBcuDatosTxtCapital extends X_UY_Bcu_DatosTxtCapital {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6142817077351451917L;

	/**
	 * @param ctx
	 * @param UY_Bcu_DatosTxtCapital_ID
	 * @param trxName
	 */
	public MBcuDatosTxtCapital(Properties ctx, int UY_Bcu_DatosTxtCapital_ID,
			String trxName) {
		super(ctx, UY_Bcu_DatosTxtCapital_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MBcuDatosTxtCapital(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
