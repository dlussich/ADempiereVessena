/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. gabriel - Aug 23, 2015
*/
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MTTLoadSubAgencyIssue
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author gabriel - Aug 23, 2015
*/
public class MTTLoadSubAgencyIssue extends X_UY_TT_LoadSubAgencyIssue {

	private static final long serialVersionUID = -1495847522457395603L;

	/***
	 * Constructor.
	 * @param ctx
	 * @param UY_TT_LoadSubAgencyIssue_ID
	 * @param trxName
	*/
	public MTTLoadSubAgencyIssue(Properties ctx, int UY_TT_LoadSubAgencyIssue_ID, String trxName) {
		super(ctx, UY_TT_LoadSubAgencyIssue_ID, trxName);
	}

	/***
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	*/
	public MTTLoadSubAgencyIssue(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

}
