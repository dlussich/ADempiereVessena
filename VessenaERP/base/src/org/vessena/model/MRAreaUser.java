/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 19/03/2013
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MRAreaUser
 * OpenUp Ltda. Issue #285 
 * Description: Modelo de usuarios por punto de resolucion por area.
 * @author Gabriel Vila - 19/03/2013
 * @see
 */
public class MRAreaUser extends X_UY_R_AreaUser {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2080886410179834476L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_R_AreaUser_ID
	 * @param trxName
	 */
	public MRAreaUser(Properties ctx, int UY_R_AreaUser_ID, String trxName) {
		super(ctx, UY_R_AreaUser_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRAreaUser(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

}
