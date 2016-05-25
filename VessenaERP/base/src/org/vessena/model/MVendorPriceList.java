/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * @author IN
 *
 */
public class MVendorPriceList extends X_UY_Vendor_PriceList {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7660189605640324262L;

	/**
	 * @param ctx
	 * @param UY_Vendor_PriceList_ID
	 * @param trxName
	 */
	public MVendorPriceList(Properties ctx, int UY_Vendor_PriceList_ID,
			String trxName) {
		super(ctx, UY_Vendor_PriceList_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MVendorPriceList(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
