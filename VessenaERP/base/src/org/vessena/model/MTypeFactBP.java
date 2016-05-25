/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 02/06/2015
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MTypeFactBP
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Gabriel Vila - 02/06/2015
 * @see
 */
public class MTypeFactBP extends X_UY_TypeFactBP {

	private static final long serialVersionUID = 266821714119226543L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_TypeFactBP_ID
	 * @param trxName
	 */
	public MTypeFactBP(Properties ctx, int UY_TypeFactBP_ID, String trxName) {
		super(ctx, UY_TypeFactBP_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTypeFactBP(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

}
