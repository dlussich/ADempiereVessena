/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 19/01/2015
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MTRTransOrderLoad
 * OpenUp Ltda. Issue #1405 
 * Description: 
 * @author Gabriel Vila - 19/01/2015
 * @see
 */
public class MTRTransOrderLoad extends X_UY_TR_TransOrderLoad {

	private static final long serialVersionUID = 3925307662603143225L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_TR_TransOrderLoad_ID
	 * @param trxName
	 */
	public MTRTransOrderLoad(Properties ctx, int UY_TR_TransOrderLoad_ID,
			String trxName) {
		super(ctx, UY_TR_TransOrderLoad_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTRTransOrderLoad(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

}
