/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 01/01/2015
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MTRConfigVFleteProd
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Gabriel Vila - 01/01/2015
 * @see
 */
public class MTRConfigVFleteProd extends X_UY_TR_ConfigVFleteProd {

	private static final long serialVersionUID = -3666047719622638088L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_TR_ConfigVFleteProd_ID
	 * @param trxName
	 */
	public MTRConfigVFleteProd(Properties ctx, int UY_TR_ConfigVFleteProd_ID,
			String trxName) {
		super(ctx, UY_TR_ConfigVFleteProd_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTRConfigVFleteProd(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

}
