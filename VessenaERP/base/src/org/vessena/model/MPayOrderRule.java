/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. gabriel - Nov 16, 2015
*/
package org.openup.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.compiere.model.Query;

/**
 * org.openup.model - MPayOrderRule
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author gabriel - Nov 16, 2015
*/
public class MPayOrderRule extends X_UY_PayOrderRule {

	private static final long serialVersionUID = 7612109595505954710L;

	/***
	 * Constructor.
	 * @param ctx
	 * @param UY_PayOrderRule_ID
	 * @param trxName
	*/

	public MPayOrderRule(Properties ctx, int UY_PayOrderRule_ID, String trxName) {
		super(ctx, UY_PayOrderRule_ID, trxName);
	}

	/***
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	*/

	public MPayOrderRule(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	/**
	 * Obtiene y retorna lineas de pago para la orden de pago enviada
	 * @author OpenUp SBT Issue#  17/11/2015 16:11:17
	 * @param mPayOrderID
	 * @return
	 */
	public static List<MPayOrderRule> getPayOrdeRules(Properties ctx,int mPayOrderID, String trxName) {
		String whereClause = X_UY_PayOrderRule.COLUMNNAME_UY_PayOrder_ID + "=" + mPayOrderID 
				+ " AND "+X_UY_PayOrderRule.COLUMNNAME_IsActive + " = 'Y' ";
		
		List<MPayOrderRule> values = new Query(ctx, I_UY_PayOrderRule.Table_Name, whereClause, trxName)
		.list();
		
		return values;
	}
}
