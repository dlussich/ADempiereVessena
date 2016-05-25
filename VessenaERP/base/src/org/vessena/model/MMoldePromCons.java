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
public class MMoldePromCons extends X_UY_Molde_PromCons {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7493924597066541415L;

	/**
	 * @param ctx
	 * @param UY_Molde_PromCons_ID
	 * @param trxName
	 */
	public MMoldePromCons(Properties ctx, int UY_Molde_PromCons_ID,
			String trxName) {
		super(ctx, UY_Molde_PromCons_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MMoldePromCons(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
