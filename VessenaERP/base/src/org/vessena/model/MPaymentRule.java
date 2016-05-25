/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 18/10/2012
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.Query;

/**
 * org.openup.model - MPaymentRule
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Hp - 18/10/2012
 * @see
 */
public class MPaymentRule extends X_UY_PaymentRule {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5122468525746957089L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_PaymentRule_ID
	 * @param trxName
	 */
	public MPaymentRule(Properties ctx, int UY_PaymentRule_ID, String trxName) {
		super(ctx, UY_PaymentRule_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MPaymentRule(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	/***
	 * Obtiene y retorna tipo de documento segun value recibido
	 * OpenUp Ltda. Issue #76 
	 * @author Gabriel Vila - 18/10/2012
	 * @see
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static MPaymentRule forValue(Properties ctx, String value, String trxName){
		
		String whereClause = X_UY_PaymentRule.COLUMNNAME_Value + " = '" + value + "'";
		
		MPaymentRule model = new Query(ctx, I_UY_PaymentRule.Table_Name, whereClause, trxName)
		.setClient_ID()
		.first();
				
		return model;
	}

}
