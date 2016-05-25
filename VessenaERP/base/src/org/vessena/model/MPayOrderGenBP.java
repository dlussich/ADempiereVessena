/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 12/02/2013
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MPayOrderGenBP
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Hp - 12/02/2013
 * @see
 */
public class MPayOrderGenBP extends X_UY_PayOrderGen_BP {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9005113185258599565L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_PayOrderGen_BP_ID
	 * @param trxName
	 */
	public MPayOrderGenBP(Properties ctx, int UY_PayOrderGen_BP_ID,
			String trxName) {
		super(ctx, UY_PayOrderGen_BP_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MPayOrderGenBP(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

}
