/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 30/04/2013
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MPayOrderResguardo
 * OpenUp Ltda. Issue #348 
 * Description: Modelo de resguardos de facturas asociadas a ordenes de pago. 
 * @author Gabriel Vila - 30/04/2013
 * @see
 */
public class MPayOrderResguardo extends X_UY_PayOrder_Resguardo {

	private static final long serialVersionUID = -8056338287713594616L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_PayOrder_Resguardo_ID
	 * @param trxName
	 */
	public MPayOrderResguardo(Properties ctx, int UY_PayOrder_Resguardo_ID,
			String trxName) {
		super(ctx, UY_PayOrder_Resguardo_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MPayOrderResguardo(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	
}
