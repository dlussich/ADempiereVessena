/**
 * 
 */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.openup.model.X_UY_TR_OperativeSite;

/**
 * @author Nicolas
 *
 */
public class MTROperativeSite extends X_UY_TR_OperativeSite {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5831038733386580786L;

	/**
	 * @param ctx
	 * @param UY_TR_OperativeSite_ID
	 * @param trxName
	 */
	public MTROperativeSite(Properties ctx, int UY_TR_OperativeSite_ID,
			String trxName) {
		super(ctx, UY_TR_OperativeSite_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTROperativeSite(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
