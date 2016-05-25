/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 13/02/2013
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MPayEmitLine
 * OpenUp Ltda. Issue #351 
 * Description: Lineas de Emision de Pagos
 * @author Gabriel Vila - 13/02/2013
 * @see
 */
public class MPayEmitLine extends X_UY_PayEmitLine {

	private static final long serialVersionUID = 8200246993748314912L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_PayEmitLine_ID
	 * @param trxName
	 */
	public MPayEmitLine(Properties ctx, int UY_PayEmitLine_ID, String trxName) {
		super(ctx, UY_PayEmitLine_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MPayEmitLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

}
