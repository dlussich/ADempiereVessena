/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 30/06/2013
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MRReclamoSign
 * OpenUp Ltda. Issue #1091 
 * Description: Modelo donde manejar señales de semaforo asociadas a una determinada incidencia.
 * @author Gabriel Vila - 30/06/2013
 * @see
 */
public class MRReclamoSign extends X_UY_R_ReclamoSign {

	private static final long serialVersionUID = 4316354801620864497L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_R_ReclamoSign_ID
	 * @param trxName
	 */
	public MRReclamoSign(Properties ctx, int UY_R_ReclamoSign_ID, String trxName) {
		super(ctx, UY_R_ReclamoSign_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRReclamoSign(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

}
