/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 05/10/2012
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MProvisionPartner
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Hp - 05/10/2012
 * @see
 */
public class MProvisionPartner extends X_UY_ProvisionPartner {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4795213764868341975L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_ProvisionPartner_ID
	 * @param trxName
	 */
	public MProvisionPartner(Properties ctx, int UY_ProvisionPartner_ID,
			String trxName) {
		super(ctx, UY_ProvisionPartner_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MProvisionPartner(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
