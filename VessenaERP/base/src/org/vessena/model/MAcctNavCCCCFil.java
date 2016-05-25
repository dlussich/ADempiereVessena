/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 31/01/2013
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MAcctNavCCCCFil
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Hp - 31/01/2013
 * @see
 */
public class MAcctNavCCCCFil extends X_UY_AcctNavCC_CCFil {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2957713902858173657L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_AcctNavCC_CCFil_ID
	 * @param trxName
	 */
	public MAcctNavCCCCFil(Properties ctx, int UY_AcctNavCC_CCFil_ID,
			String trxName) {
		super(ctx, UY_AcctNavCC_CCFil_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MAcctNavCCCCFil(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

}
