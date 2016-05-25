/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 17/02/2013
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MGerencialProd
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Hp - 17/02/2013
 * @see
 */
public class MGerencialProd extends X_UY_Gerencial_Prod {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4337039776899061053L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_Gerencial_Prod_ID
	 * @param trxName
	 */
	public MGerencialProd(Properties ctx, int UY_Gerencial_Prod_ID,
			String trxName) {
		super(ctx, UY_Gerencial_Prod_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MGerencialProd(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

}
