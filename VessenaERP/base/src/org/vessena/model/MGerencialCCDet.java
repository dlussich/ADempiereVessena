/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 04/03/2013
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MGerencialCCDet
 * OpenUp Ltda. Issue #116 
 * Description: Modelo de detalle de centro de costo
 * @author Gabriel Vila - 04/03/2013
 * @see
 */
public class MGerencialCCDet extends X_UY_Gerencial_CCDet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4545209904694136057L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_Gerencial_CCDet_ID
	 * @param trxName
	 */
	public MGerencialCCDet(Properties ctx, int UY_Gerencial_CCDet_ID,
			String trxName) {
		super(ctx, UY_Gerencial_CCDet_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MGerencialCCDet(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

}
