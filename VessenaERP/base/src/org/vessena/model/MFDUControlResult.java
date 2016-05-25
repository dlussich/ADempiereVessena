/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 15/01/2013
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MFDUControlResult
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Hp - 15/01/2013
 * @see
 */
public class MFDUControlResult extends X_UY_FDU_ControlResult {


	private static final long serialVersionUID = -1864004749348462882L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_FDU_ControlResult_ID
	 * @param trxName
	 */
	public MFDUControlResult(Properties ctx, int UY_FDU_ControlResult_ID,
			String trxName) {
		super(ctx, UY_FDU_ControlResult_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MFDUControlResult(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

}
