/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 17/06/2015
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.compiere.model.Query;

/**
 * org.openup.model - MBGBursaCust
 * OpenUp Ltda. Issue #4169 
 * Description: Modelo de asociacion de bolsa-cliente
 * @author Gabriel Vila - 17/06/2015
 * @see
 */
public class MBGBursaCust extends X_UY_BG_BursaCust {

	private static final long serialVersionUID = -670776686190906134L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_BG_BursaCust_ID
	 * @param trxName
	 */
	public MBGBursaCust(Properties ctx, int UY_BG_BursaCust_ID, String trxName) {
		super(ctx, UY_BG_BursaCust_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MBGBursaCust(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/**
	 * OpenUp Ltda Issue#
	 * @author SBouissa 3/9/2015
	 * @param ctx
	 * @param uy_BG_Bursa_ID
	 * @param get_ID
	 * @param object
	 * @return
	 */
	public static MBGBursaCust forBursaAndCust(Properties ctx,
			int uy_BG_Bursa_ID, int uy_BG_Customer_ID, String trxName) {
		String whereClause = X_UY_BG_BursaCust.COLUMNNAME_UY_BG_Bursa_ID + " = " + uy_BG_Bursa_ID  +	
				 " AND " + X_UY_BG_BursaCust.COLUMNNAME_UY_BG_Customer_ID+ " = " + uy_BG_Customer_ID +
				 " AND " + X_UY_BG_BursaCust.COLUMNNAME_IsActive + " = 'Y'";

		MBGBursaCust model=null;
		model= new Query(ctx, I_UY_BG_BursaCust.Table_Name, whereClause, trxName).first();
		return model;
	}

}
