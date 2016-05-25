/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.Query;

/**
 * @author Nicolas
 *
 */
public class MCashConfigAccount extends X_UY_CashConfigAccount {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param ctx
	 * @param UY_CashConfigAccount_ID
	 * @param trxName
	 */
	public MCashConfigAccount(Properties ctx, int UY_CashConfigAccount_ID,
			String trxName) {
		super(ctx, UY_CashConfigAccount_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MCashConfigAccount(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	/***
	 * Obtiene y retorna modelo segun ID de cuenta bancaria recibido. 
	 * OpenUp Ltda. Issue #4449
	 * @author Nicolas Sarlabos - 26/08/2015
	 * @see
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static MCashConfigAccount forBankAccount(Properties ctx, int accountID, String trxName){

		String whereClause = X_UY_CashConfigAccount.COLUMNNAME_C_BankAccount_ID + "=" + accountID;

		MCashConfigAccount model = new Query(ctx, I_UY_CashConfigAccount.Table_Name, whereClause, trxName)
		.first();

		return model;
	}

}
