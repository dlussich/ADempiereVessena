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
public class MInfGerPeriodo extends X_UY_InfGer_Periodo {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1605938046248736572L;

	/**
	 * @param ctx
	 * @param UY_InfGer_Periodo_ID
	 * @param trxName
	 */
	public MInfGerPeriodo(Properties ctx, int UY_InfGer_Periodo_ID,
			String trxName) {
		super(ctx, UY_InfGer_Periodo_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MInfGerPeriodo(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
