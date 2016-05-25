/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 21/11/2012
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MRetention
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Hp - 21/11/2012
 * @see
 */
public class MRetention extends X_UY_Retention {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6637734308758502534L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_Retention_ID
	 * @param trxName
	 */
	public MRetention(Properties ctx, int UY_Retention_ID, String trxName) {
		super(ctx, UY_Retention_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRetention(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
