/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 25/04/2012
 */
 
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.util.Env;

/**
 * org.openup.model - MHRResult
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Hp - 25/04/2012
 * @see
 */
public class MHRResult extends X_UY_HRResult {

	private static final long serialVersionUID = -8679254345509084038L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_HRResult_ID
	 * @param trxName
	 */
	public MHRResult(Properties ctx, int UY_HRResult_ID, String trxName) {
		super(ctx, UY_HRResult_ID, trxName);

		if (UY_HRResult_ID <= 0){
			this.setAmtAcctCr(Env.ZERO);
			this.setAmtAcctDr(Env.ZERO);
			this.setTotalAmt(Env.ZERO);
			this.setSuccess(false);
			this.setReProcessing(false);
		}
		
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MHRResult(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
