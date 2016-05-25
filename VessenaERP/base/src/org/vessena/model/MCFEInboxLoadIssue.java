/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. gabriel - Jan 14, 2016
*/
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MCFEInboxLoadIssue
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author gabriel - Jan 14, 2016
*/
public class MCFEInboxLoadIssue extends X_UY_CFE_InboxLoadIssue {

	private static final long serialVersionUID = -4846400841578890593L;

	/***
	 * Constructor.
	 * @param ctx
	 * @param UY_CFE_InboxLoadIssue_ID
	 * @param trxName
	*/

	public MCFEInboxLoadIssue(Properties ctx, int UY_CFE_InboxLoadIssue_ID, String trxName) {
		super(ctx, UY_CFE_InboxLoadIssue_ID, trxName);
	}

	/***
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	*/

	public MCFEInboxLoadIssue(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

}
