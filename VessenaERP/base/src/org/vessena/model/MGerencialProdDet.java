/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 02/03/2013
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MGerencialProdDet
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Hp - 02/03/2013
 * @see
 */
public class MGerencialProdDet extends X_UY_Gerencial_ProdDet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7942119812483092423L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_Gerencial_ProdDet_ID
	 * @param trxName
	 */
	public MGerencialProdDet(Properties ctx, int UY_Gerencial_ProdDet_ID,
			String trxName) {
		super(ctx, UY_Gerencial_ProdDet_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MGerencialProdDet(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

}
