/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. gabriel - Aug 23, 2015
*/
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MTTLoadSubAgencyLine
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author gabriel - Aug 23, 2015
*/
public class MTTLoadSubAgencyLine extends X_UY_TT_LoadSubAgencyLine {

	private static final long serialVersionUID = 4837701271384277872L;

	/***
	 * Constructor.
	 * @param ctx
	 * @param UY_TT_LoadSubAgencyLine_ID
	 * @param trxName
	*/
	public MTTLoadSubAgencyLine(Properties ctx, int UY_TT_LoadSubAgencyLine_ID, String trxName) {
		super(ctx, UY_TT_LoadSubAgencyLine_ID, trxName);
	}

	/***
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	*/
	public MTTLoadSubAgencyLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

}
