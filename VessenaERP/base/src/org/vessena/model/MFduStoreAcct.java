/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * @author gbrust
 *
 */
public class MFduStoreAcct extends X_UY_Fdu_StoreAcct {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2614431600476635909L;

	/**
	 * @param ctx
	 * @param UY_Fdu_StoreAcct_ID
	 * @param trxName
	 */
	public MFduStoreAcct(Properties ctx, int UY_Fdu_StoreAcct_ID, String trxName) {
		super(ctx, UY_Fdu_StoreAcct_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MFduStoreAcct(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
