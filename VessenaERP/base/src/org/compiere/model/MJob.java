/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 25/02/2015
 */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.compiere.model - MJob
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Gabriel Vila - 25/02/2015
 * @see
 */
public class MJob extends X_C_Job {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7221410887913427477L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param C_Job_ID
	 * @param trxName
	 */
	public MJob(Properties ctx, int C_Job_ID, String trxName) {
		super(ctx, C_Job_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MJob(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

}
