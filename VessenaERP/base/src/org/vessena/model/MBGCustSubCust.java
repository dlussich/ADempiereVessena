/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.Query;

/**OpenUp Ltda Issue#
 * @author SBouissa 3/9/2015
 *
 */
public class MBGCustSubCust extends X_UY_BG_CustSubCust {

	/**
	 * @author SBouissa 3/9/2015
	 * @param ctx
	 * @param UY_BG_CustSubCust_ID
	 * @param trxName
	 */
	public MBGCustSubCust(Properties ctx, int UY_BG_CustSubCust_ID,
			String trxName) {
		super(ctx, UY_BG_CustSubCust_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @author SBouissa 3/9/2015
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MBGCustSubCust(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * OpenUp Ltda Issue#
	 * @author SBouissa 3/9/2015
	 * @param ctx
	 * @param uy_BG_SubCustomer_ID
	 * @param uy_BG_Customer_ID
	 * @param trxName
	 * @return
	 */
	public static MBGCustSubCust forSubCustAndCust(Properties ctx,
			int uy_BG_SubCustomer_ID, int uy_BG_Customer_ID, String trxName) {
		String whereClause = X_UY_BG_CustSubCust.COLUMNNAME_UY_BG_SubCustomer_ID + " = " + uy_BG_SubCustomer_ID  +	
				 " AND " + X_UY_BG_CustSubCust.COLUMNNAME_UY_BG_Customer_ID+ " = " + uy_BG_Customer_ID +
				 " AND " + X_UY_BG_CustSubCust.COLUMNNAME_IsActive + " = 'Y'";

		MBGCustSubCust model=null;
		model= new Query(ctx, I_UY_BG_CustSubCust.Table_Name, whereClause, trxName).first();
		return model;
	}

}
