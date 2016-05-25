/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 01/02/2013
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MAcctNavCCAccount
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Hp - 01/02/2013
 * @see
 */
public class MAcctNavCCAccount extends X_UY_AcctNavCC_Account {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3873367205238071369L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_AcctNavCC_Account_ID
	 * @param trxName
	 */
	public MAcctNavCCAccount(Properties ctx, int UY_AcctNavCC_Account_ID,
			String trxName) {
		super(ctx, UY_AcctNavCC_Account_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MAcctNavCCAccount(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

}
