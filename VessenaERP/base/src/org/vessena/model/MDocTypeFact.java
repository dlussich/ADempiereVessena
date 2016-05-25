/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 20/11/2014
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MDocTypeFact
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Gabriel Vila - 20/11/2014
 * @see
 */
public class MDocTypeFact extends X_UY_DocTypeFact {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6739724138790206594L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_DocTypeFact_ID
	 * @param trxName
	 */
	public MDocTypeFact(Properties ctx, int UY_DocTypeFact_ID, String trxName) {
		super(ctx, UY_DocTypeFact_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MDocTypeFact(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
