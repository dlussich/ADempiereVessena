/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 15/01/2013
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MFDUControlTypeLine
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Hp - 15/01/2013
 * @see
 */
public class MFDUControlTypeLine extends X_UY_FDU_ControlTypeLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1261983215720361893L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_FDU_ControlTypeLine_ID
	 * @param trxName
	 */
	public MFDUControlTypeLine(Properties ctx, int UY_FDU_ControlTypeLine_ID,
			String trxName) {
		super(ctx, UY_FDU_ControlTypeLine_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MFDUControlTypeLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

}
