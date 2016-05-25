/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 19/02/2013
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MCheckReamLine
 * OpenUp Ltda. Issue #366 
 * Description: Lineas conteniendo numeros de cheques para una libreta o resma.
 * @author Gabriel Vila - 19/02/2013
 * @see
 */
public class MCheckReamLine extends X_UY_CheckReamLine {

	private static final long serialVersionUID = -2730125441782139069L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_CheckReamLine_ID
	 * @param trxName
	 */
	public MCheckReamLine(Properties ctx, int UY_CheckReamLine_ID,
			String trxName) {
		super(ctx, UY_CheckReamLine_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MCheckReamLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

}
