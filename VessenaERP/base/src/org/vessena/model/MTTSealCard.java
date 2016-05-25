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
public class MTTSealCard extends X_UY_TT_SealCard {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7131806030506062953L;

	/**
	 * @param ctx
	 * @param UY_TT_SealCard_ID
	 * @param trxName
	 */
	public MTTSealCard(Properties ctx, int UY_TT_SealCard_ID, String trxName) {
		super(ctx, UY_TT_SealCard_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTTSealCard(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
