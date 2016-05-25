/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. gabriel - Dec 14, 2015
*/
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MCFEInboxProd
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author gabriel - Dec 14, 2015
*/
public class MCFEInboxProd extends X_UY_CFE_InboxProd {

	private static final long serialVersionUID = -5109038390319818330L;

	/***
	 * Constructor.
	 * @param ctx
	 * @param UY_CFE_InboxProd_ID
	 * @param trxName
	*/

	public MCFEInboxProd(Properties ctx, int UY_CFE_InboxProd_ID, String trxName) {
		super(ctx, UY_CFE_InboxProd_ID, trxName);
	}

	/***
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	*/

	public MCFEInboxProd(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

}
