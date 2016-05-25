/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. gabriel - Jan 14, 2016
*/
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MCFEInboxFileType
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author gabriel - Jan 14, 2016
*/
public class MCFEInboxFileType extends X_UY_CFE_InboxFileType {

	private static final long serialVersionUID = -7863539505167451441L;

	/***
	 * Constructor.
	 * @param ctx
	 * @param UY_CFE_InboxFileType_ID
	 * @param trxName
	*/

	public MCFEInboxFileType(Properties ctx, int UY_CFE_InboxFileType_ID, String trxName) {
		super(ctx, UY_CFE_InboxFileType_ID, trxName);
	}

	/***
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	*/

	public MCFEInboxFileType(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

}
