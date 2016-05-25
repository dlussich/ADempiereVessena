/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. gabriel - Aug 27, 2015
*/
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MRReclamoAutoriz
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author gabriel - Aug 27, 2015
*/
public class MRReclamoAutoriz extends X_UY_R_ReclamoAutoriz {

	private static final long serialVersionUID = -8560776429099164295L;

	/***
	 * Constructor.
	 * @param ctx
	 * @param UY_R_ReclamoAutoriz_ID
	 * @param trxName
	*/
	public MRReclamoAutoriz(Properties ctx, int UY_R_ReclamoAutoriz_ID, String trxName) {
		super(ctx, UY_R_ReclamoAutoriz_ID, trxName);
	}

	/***
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	*/
	public MRReclamoAutoriz(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

}
