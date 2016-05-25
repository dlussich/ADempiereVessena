/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 05/12/2012
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MPurchaseTracking
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Hp - 05/12/2012
 * @see
 */
public class MPurchaseTracking extends X_UY_PurchaseTracking {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7679000946824311237L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_PurchaseTracking_ID
	 * @param trxName
	 */
	public MPurchaseTracking(Properties ctx, int UY_PurchaseTracking_ID,
			String trxName) {
		super(ctx, UY_PurchaseTracking_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MPurchaseTracking(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
