/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 04/11/2012
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MPOPolicyUser
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Hp - 04/11/2012
 * @see
 */
public class MPOPolicyUser extends X_UY_POPolicyUser {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6434277139782638724L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_POPolicyUser_ID
	 * @param trxName
	 */
	public MPOPolicyUser(Properties ctx, int UY_POPolicyUser_ID, String trxName) {
		super(ctx, UY_POPolicyUser_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MPOPolicyUser(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
