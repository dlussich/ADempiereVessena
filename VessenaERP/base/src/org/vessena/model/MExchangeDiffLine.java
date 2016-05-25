/**
 * MExchangeDiffLine.java
 * 11/04/2011
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * OpenUp.
 * MExchangeDiffLine
 * Descripcion :
 * @author Gabriel Vila
 * Fecha : 11/04/2011
 */
public class MExchangeDiffLine extends X_UY_ExchangeDiffLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2152452066684194699L;

	/**
	 * Constructor
	 * @param ctx
	 * @param UY_ExchangeDiffLine_ID
	 * @param trxName
	 */
	public MExchangeDiffLine(Properties ctx, int UY_ExchangeDiffLine_ID,
			String trxName) {
		super(ctx, UY_ExchangeDiffLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MExchangeDiffLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
