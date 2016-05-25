/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * @author Nicolas
 *
 */
public class MHRPatronalesDetail extends X_UY_HRPatronalesDetail {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5087331573460380636L;

	/**
	 * @param ctx
	 * @param UY_HRPatronalesDetail_ID
	 * @param trxName
	 */
	public MHRPatronalesDetail(Properties ctx, int UY_HRPatronalesDetail_ID,
			String trxName) {
		super(ctx, UY_HRPatronalesDetail_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MHRPatronalesDetail(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
