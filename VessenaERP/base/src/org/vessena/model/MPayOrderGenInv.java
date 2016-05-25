/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 12/02/2013
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MPayOrderGenInv
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Hp - 12/02/2013
 * @see
 */
public class MPayOrderGenInv extends X_UY_PayOrderGen_Inv {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6629499372887470087L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_PayOrderGen_Inv_ID
	 * @param trxName
	 */
	public MPayOrderGenInv(Properties ctx, int UY_PayOrderGen_Inv_ID,
			String trxName) {
		super(ctx, UY_PayOrderGen_Inv_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MPayOrderGenInv(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

}
