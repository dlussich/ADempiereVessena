/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 15/01/2013
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MFDUControlResultDetail
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Hp - 15/01/2013
 * @see
 */
public class MFDUControlResultDetail extends X_UY_FDU_ControlResultDetail {


	private static final long serialVersionUID = -8524037954922407953L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_FDU_ControlResultDetail_ID
	 * @param trxName
	 */
	public MFDUControlResultDetail(Properties ctx,
			int UY_FDU_ControlResultDetail_ID, String trxName) {
		super(ctx, UY_FDU_ControlResultDetail_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MFDUControlResultDetail(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

}
