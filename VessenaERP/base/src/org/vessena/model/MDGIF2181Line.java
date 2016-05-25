/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 20/08/2013
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MDGIF2181Line
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Hp - 20/08/2013
 * @see
 */
public class MDGIF2181Line extends X_UY_DGI_F2181_Line {

	private static final long serialVersionUID = 8477684875183202527L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_DGI_F2181_Line_ID
	 * @param trxName
	 */
	public MDGIF2181Line(Properties ctx, int UY_DGI_F2181_Line_ID,
			String trxName) {
		super(ctx, UY_DGI_F2181_Line_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MDGIF2181Line(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

}
