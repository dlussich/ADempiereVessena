/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 06/11/2012
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MDevengamientoLine
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Hp - 06/11/2012
 * @see
 */
public class MDevengamientoLine extends X_UY_DevengamientoLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4665741613638528533L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_DevengamientoLine_ID
	 * @param trxName
	 */
	public MDevengamientoLine(Properties ctx, int UY_DevengamientoLine_ID,
			String trxName) {
		super(ctx, UY_DevengamientoLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MDevengamientoLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
