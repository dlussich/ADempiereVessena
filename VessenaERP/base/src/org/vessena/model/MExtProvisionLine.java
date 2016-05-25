/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 14/03/2013
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MExtProvisionLine
 * OpenUp Ltda. Issue #296 
 * Description: Modelo del linea de extorno de provision pendiente.
 * @author Gabriel Vila - 14/03/2013
 * @see
 */
public class MExtProvisionLine extends X_UY_ExtProvisionLine {

	private static final long serialVersionUID = -1435376949212789982L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_ExtProvisionLine_ID
	 * @param trxName
	 */
	public MExtProvisionLine(Properties ctx, int UY_ExtProvisionLine_ID,
			String trxName) {
		super(ctx, UY_ExtProvisionLine_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MExtProvisionLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

}
