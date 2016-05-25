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
public class MTRDespachante extends X_UY_TR_Despachante {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5702592915289630017L;

	/**
	 * @param ctx
	 * @param UY_TR_Despachante_ID
	 * @param trxName
	 */
	public MTRDespachante(Properties ctx, int UY_TR_Despachante_ID,
			String trxName) {
		super(ctx, UY_TR_Despachante_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTRDespachante(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
