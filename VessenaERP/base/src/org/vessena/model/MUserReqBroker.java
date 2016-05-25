/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 05/06/2015
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MUserReqBroker
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Gabriel Vila - 05/06/2015
 * @see
 */
public class MUserReqBroker extends X_UY_UserReqBroker {

	private static final long serialVersionUID = -5898151056272973180L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_UserReqBroker_ID
	 * @param trxName
	 */
	public MUserReqBroker(Properties ctx, int UY_UserReqBroker_ID,
			String trxName) {
		super(ctx, UY_UserReqBroker_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUserReqBroker(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

}
