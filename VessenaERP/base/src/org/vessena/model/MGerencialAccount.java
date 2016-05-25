/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 20/12/2012
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.Query;

/**
 * org.openup.model - MGerencialAccount
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Hp - 20/12/2012
 * @see
 */
public class MGerencialAccount extends X_UY_Gerencial_Account {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1433177504183735809L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_Gerencial_Account_ID
	 * @param trxName
	 */
	public MGerencialAccount(Properties ctx, int UY_Gerencial_Account_ID,
			String trxName) {
		super(ctx, UY_Gerencial_Account_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MGerencialAccount(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/***
	 * Obtiene y retorna id de segundo nivel para determinada cuenta.
	 * OpenUp Ltda. Issue #116 
	 * @author Gabriel Vila - 20/12/2012
	 * @see
	 * @param ctx
	 * @param get_ID
	 * @param parentID
	 * @param cElementValueID
	 * @param object
	 * @return
	 */
	public static MGerencialAccount forAccountID(Properties ctx, int uyGerencialID,	int parentID, 
				int cElementValueID, String trxName) {

		String whereClause = X_UY_Gerencial_Account.COLUMNNAME_UY_Gerencial_ID + "=" + uyGerencialID +
				" AND " + X_UY_Gerencial_Account.COLUMNNAME_Parent_ID + "=" + parentID +
				" AND " + X_UY_Gerencial_Account.COLUMNNAME_C_ElementValue_ID + "=" + cElementValueID;

		MGerencialAccount value = new Query(ctx, I_UY_Gerencial_Account.Table_Name, whereClause, trxName)
		.first();

		return value; 
		
	}

}
