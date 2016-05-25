/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 16/02/2014
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MPOPolicyCategory
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Gabriel Vila - 16/02/2014
 * @see
 */
public class MPOPolicyCategory extends X_UY_POPolicyCategory {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2903902284895892058L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_POPolicyCategory_ID
	 * @param trxName
	 */
	public MPOPolicyCategory(Properties ctx, int UY_POPolicyCategory_ID,
			String trxName) {
		super(ctx, UY_POPolicyCategory_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MPOPolicyCategory(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

}
