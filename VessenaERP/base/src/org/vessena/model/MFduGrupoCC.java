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
public class MFduGrupoCC extends X_UY_Fdu_GrupoCC {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7638301581515876026L;

	/**
	 * @param ctx
	 * @param UY_Fdu_GrupoCC_ID
	 * @param trxName
	 */
	public MFduGrupoCC(Properties ctx, int UY_Fdu_GrupoCC_ID, String trxName) {
		super(ctx, UY_Fdu_GrupoCC_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MFduGrupoCC(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
