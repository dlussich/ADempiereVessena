/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 20/06/2013
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MRReclamoAdjunto
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Hp - 20/06/2013
 * @see
 */
public class MRReclamoAdjunto extends X_UY_R_ReclamoAdjunto {

	private static final long serialVersionUID = 8400151692102190186L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_R_ReclamoAdjunto_ID
	 * @param trxName
	 */
	public MRReclamoAdjunto(Properties ctx, int UY_R_ReclamoAdjunto_ID,
			String trxName) {
		super(ctx, UY_R_ReclamoAdjunto_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRReclamoAdjunto(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

}
