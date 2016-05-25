/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 01/07/2013
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MRReclamoEmail
 * OpenUp Ltda. Issue #1077 
 * Description: Modelo para asociacion de emails enviados en una incidencia.
 * @author Gabriel Vila - 01/07/2013
 * @see
 */
public class MRReclamoEmail extends X_UY_R_ReclamoEmail {

	private static final long serialVersionUID = -3861419801429468344L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_R_ReclamoEmail_ID
	 * @param trxName
	 */
	public MRReclamoEmail(Properties ctx, int UY_R_ReclamoEmail_ID,
			String trxName) {
		super(ctx, UY_R_ReclamoEmail_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRReclamoEmail(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

}
