/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 04/06/2015
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MBGTransTracking
 * OpenUp Ltda. Issue #4174 
 * Description: Modelo para tracking de registro de mandatos
 * @author Gabriel Vila - 04/06/2015
 * @see
 */
public class MBGTransTracking extends X_UY_BG_TransTracking {

	private static final long serialVersionUID = -7750251121490221460L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_BG_TransTracking_ID
	 * @param trxName
	 */
	public MBGTransTracking(Properties ctx, int UY_BG_TransTracking_ID,
			String trxName) {
		super(ctx, UY_BG_TransTracking_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MBGTransTracking(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

}
