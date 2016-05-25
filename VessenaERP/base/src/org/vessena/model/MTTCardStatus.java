/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.Query;

/**
 * @author gbrust
 *
 */
public class MTTCardStatus extends X_UY_TT_CardStatus {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8839564201592123253L;

	/**
	 * @param ctx
	 * @param UY_TT_CardStatus_ID
	 * @param trxName
	 */
	public MTTCardStatus(Properties ctx, int UY_TT_CardStatus_ID, String trxName) {
		super(ctx, UY_TT_CardStatus_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTTCardStatus(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	
	public static MTTCardStatus forValue(Properties ctx, String trxName, String value){
					
		String whereClause = X_UY_TT_CardStatus.COLUMNNAME_Value + " = '" + value + "'" +
							" AND " + X_UY_TT_CardStatus.COLUMNNAME_IsActive + " = 'Y'";
		
		MTTCardStatus model = new Query(ctx, I_UY_TT_CardStatus.Table_Name, whereClause, trxName)
		.first();
		
		
		return model;		
	}

}
