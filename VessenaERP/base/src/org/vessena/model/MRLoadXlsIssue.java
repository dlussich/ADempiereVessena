/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. gabriel - Sep 30, 2015
*/
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MRLoadXlsIssue
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author gabriel - Sep 30, 2015
*/
public class MRLoadXlsIssue extends X_UY_R_LoadXlsIssue {

	private static final long serialVersionUID = 8311845363258744340L;

	/***
	 * Constructor.
	 * @param ctx
	 * @param UY_R_LoadXlsIssue_ID
	 * @param trxName
	*/

	public MRLoadXlsIssue(Properties ctx, int UY_R_LoadXlsIssue_ID, String trxName) {
		super(ctx, UY_R_LoadXlsIssue_ID, trxName);
	}

	/***
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	*/

	public MRLoadXlsIssue(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

}
