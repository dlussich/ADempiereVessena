/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. gabriel - Sep 30, 2015
*/
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MRLoadXlsLine
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author gabriel - Sep 30, 2015
*/
public class MRLoadXlsLine extends X_UY_R_LoadXlsLine {

	private static final long serialVersionUID = 1903896135760673160L;

	/***
	 * Constructor.
	 * @param ctx
	 * @param UY_R_LoadXlsLine_ID
	 * @param trxName
	*/

	public MRLoadXlsLine(Properties ctx, int UY_R_LoadXlsLine_ID, String trxName) {
		super(ctx, UY_R_LoadXlsLine_ID, trxName);
	}

	/***
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	*/

	public MRLoadXlsLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

}
