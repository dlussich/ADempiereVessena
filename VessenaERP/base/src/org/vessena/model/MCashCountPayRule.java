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
public class MCashCountPayRule extends X_UY_CashCountPayRule {

	/**
	 * 
	 */
	private static final long serialVersionUID = -916870953343165464L;

	/**
	 * @param ctx
	 * @param UY_CashCountPayRule_ID
	 * @param trxName
	 */
	public MCashCountPayRule(Properties ctx, int UY_CashCountPayRule_ID,
			String trxName) {
		super(ctx, UY_CashCountPayRule_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MCashCountPayRule(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * OpenUp Ltda Issue #4437
	 * @author Nicolas Sarlabos 18/12/2015
	 * @param ctx
	 * @param string
	 * @param get_TrxName
	 * @return
	 */
	public static MCashCountPayRule forPayruleAndCurrency(Properties ctx, int payRuleID, int currID, int hdrID, String trxName){
		
		String whereClause = X_UY_CashCountPayRule.COLUMNNAME_UY_CashCount_ID + " = " + hdrID + " AND " + 
				X_UY_CashCountPayRule.COLUMNNAME_C_Currency_ID + " = " + currID + " AND " + 
				X_UY_CashCountPayRule.COLUMNNAME_UY_PaymentRule_ID + " = " + payRuleID;
		
		MCashCountPayRule model = new Query(ctx, X_UY_CashCountPayRule.Table_Name, whereClause, trxName)
		.first();		
		
		return model;
	}

}
