/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 23/11/2012
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MAcctNavAccount
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Hp - 23/11/2012
 * @see
 */
public class MAcctNavAccount extends X_UY_AcctNav_Account {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7157017121712256691L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_AcctNav_Account_ID
	 * @param trxName
	 */
	public MAcctNavAccount(Properties ctx, int UY_AcctNav_Account_ID,
			String trxName) {
		super(ctx, UY_AcctNav_Account_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MAcctNavAccount(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
