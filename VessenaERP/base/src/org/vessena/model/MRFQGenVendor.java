/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 18/06/2012
 */
 
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MRFQGenVendor
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Hp - 18/06/2012
 * @see
 */
public class MRFQGenVendor extends X_UY_RFQGen_Vendor {

	private static final long serialVersionUID = -6447377351795831138L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_RFQGen_Vendor_ID
	 * @param trxName
	 */
	public MRFQGenVendor(Properties ctx, int UY_RFQGen_Vendor_ID, String trxName) {
		super(ctx, UY_RFQGen_Vendor_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRFQGenVendor(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
