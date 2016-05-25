/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 17/02/2013
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MGerencialCC
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Hp - 17/02/2013
 * @see
 */
public class MGerencialCC extends X_UY_Gerencial_CC {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1232377690232062997L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_Gerencial_CC_ID
	 * @param trxName
	 */
	public MGerencialCC(Properties ctx, int UY_Gerencial_CC_ID, String trxName) {
		super(ctx, UY_Gerencial_CC_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MGerencialCC(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

}
