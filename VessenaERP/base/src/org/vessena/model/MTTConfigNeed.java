/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. gabriel - Aug 27, 2015
*/
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MTTConfigNeed
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author gabriel - Aug 27, 2015
*/
public class MTTConfigNeed extends X_UY_TT_ConfigNeed {

	private static final long serialVersionUID = 7563014272903543648L;

	/***
	 * Constructor.
	 * @param ctx
	 * @param UY_TT_ConfigNeed_ID
	 * @param trxName
	*/
	public MTTConfigNeed(Properties ctx, int UY_TT_ConfigNeed_ID, String trxName) {
		super(ctx, UY_TT_ConfigNeed_ID, trxName);
	}

	/***
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	*/
	public MTTConfigNeed(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
}
