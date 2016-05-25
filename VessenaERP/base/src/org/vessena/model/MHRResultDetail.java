/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 25/04/2012
 */
 
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.util.Env;

/**
 * org.openup.model - MHRResultDetail
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Hp - 25/04/2012
 * @see
 */
public class MHRResultDetail extends X_UY_HRResultDetail {

	private static final long serialVersionUID = 779355813990076261L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_HRResultDetail_ID
	 * @param trxName
	 */
	public MHRResultDetail(Properties ctx, int UY_HRResultDetail_ID, String trxName) {
		super(ctx, UY_HRResultDetail_ID, trxName);

		if (UY_HRResultDetail_ID <= 0){
			this.setTotalAmt(Env.ZERO);
			this.setSuccess(false);
		}

	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MHRResultDetail(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
