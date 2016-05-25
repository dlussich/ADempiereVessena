/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 13/01/2015
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MTRPrintFlete
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Gabriel Vila - 13/01/2015
 * @see
 */
public class MTRPrintFlete extends X_UY_TR_PrintFlete {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8742021161482472926L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_TR_PrintFlete_ID
	 * @param trxName
	 */
	public MTRPrintFlete(Properties ctx, int UY_TR_PrintFlete_ID, String trxName) {
		super(ctx, UY_TR_PrintFlete_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTRPrintFlete(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

}
