/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 31/01/2013
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MAcctNavCCMain
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Hp - 31/01/2013
 * @see
 */
public class MAcctNavCCMain extends X_UY_AcctNavCC_Main {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9139308195623062284L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_AcctNavCC_Main_ID
	 * @param trxName
	 */
	public MAcctNavCCMain(Properties ctx, int UY_AcctNavCC_Main_ID,
			String trxName) {
		super(ctx, UY_AcctNavCC_Main_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MAcctNavCCMain(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

}
