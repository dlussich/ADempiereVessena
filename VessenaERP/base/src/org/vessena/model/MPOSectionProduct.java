/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 03/11/2012
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MPOSectionProduct
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Hp - 03/11/2012
 * @see
 */
public class MPOSectionProduct extends X_UY_POSectionProduct {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2353457958042411551L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_POSectionProduct_ID
	 * @param trxName
	 */
	public MPOSectionProduct(Properties ctx, int UY_POSectionProduct_ID,
			String trxName) {
		super(ctx, UY_POSectionProduct_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MPOSectionProduct(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
