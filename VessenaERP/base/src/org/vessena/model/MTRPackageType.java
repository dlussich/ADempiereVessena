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
public class MTRPackageType extends X_UY_TR_PackageType {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6646831384761067988L;

	/**
	 * @param ctx
	 * @param UY_TR_PackageType_ID
	 * @param trxName
	 */
	public MTRPackageType(Properties ctx, int UY_TR_PackageType_ID,
			String trxName) {
		super(ctx, UY_TR_PackageType_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTRPackageType(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
