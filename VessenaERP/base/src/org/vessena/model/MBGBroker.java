/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 05/06/2015
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.Query;

/**
 * org.openup.model - MBGBroker
 * OpenUp Ltda. Issue #4173 
 * Description: 
 * @author Gabriel Vila - 05/06/2015
 * @see
 */
public class MBGBroker extends X_UY_BG_Broker {

	private static final long serialVersionUID = 5381137904957334161L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_BG_Broker_ID
	 * @param trxName
	 */
	public MBGBroker(Properties ctx, int UY_BG_Broker_ID, String trxName) {
		super(ctx, UY_BG_Broker_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MBGBroker(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 */
	public static MBGBroker forADUser(Properties ctx,  int adUserID, String trxName){
		
		String whereClause = X_UY_BG_Broker.COLUMNNAME_AD_User_ID + " = " + adUserID + "" +													
					" AND " + X_UY_BG_Broker.COLUMNNAME_IsActive + " = 'Y'";

		MBGBroker model = new Query(ctx, I_UY_BG_Broker.Table_Name, whereClause, trxName)
			//.setOrderBy(" uy_tt_card.uy_tt_card_id desc ")
			.first();		

		return model;
	}

}
