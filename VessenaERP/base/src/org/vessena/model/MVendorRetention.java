/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 21/11/2012
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MVendorRetention
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Hp - 21/11/2012
 * @see
 */
public class MVendorRetention extends X_UY_VendorRetention {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5957923632192056389L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_VendorRetention_ID
	 * @param trxName
	 */
	public MVendorRetention(Properties ctx, int UY_VendorRetention_ID,
			String trxName) {
		super(ctx, UY_VendorRetention_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MVendorRetention(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
