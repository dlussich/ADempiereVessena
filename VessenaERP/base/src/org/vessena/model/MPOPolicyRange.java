/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 04/11/2012
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MPOPolicyRange
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Hp - 04/11/2012
 * @see
 */
public class MPOPolicyRange extends X_UY_POPolicyRange {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3472774523584111727L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_POPolicyRange_ID
	 * @param trxName
	 */
	public MPOPolicyRange(Properties ctx, int UY_POPolicyRange_ID,
			String trxName) {
		super(ctx, UY_POPolicyRange_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MPOPolicyRange(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
