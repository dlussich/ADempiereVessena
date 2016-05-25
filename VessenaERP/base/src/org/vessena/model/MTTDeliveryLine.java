/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.compiere.model.Query;

/**
 * @author gbrust
 *
 */
public class MTTDeliveryLine extends X_UY_TT_DeliveryLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4029918424775078675L;

	/**
	 * @param ctx
	 * @param UY_TT_DeliveryLine_ID
	 * @param trxName
	 */
	public MTTDeliveryLine(Properties ctx, int UY_TT_DeliveryLine_ID,
			String trxName) {
		super(ctx, UY_TT_DeliveryLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTTDeliveryLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	
	/**
	 * OpenUp. Guillemo Brust. 27/08/2013. ISSUE #
	 * Metodo que devuelve una lista de MTTDeliveryLine, a partir de un MTTDelivery_ID
	 * 
	 * */
	public static List<MTTDeliveryLine> getForMTTDeliveryID(Properties ctx, String trxName, int mTTDeliveryID){ 
				
		String whereClause = X_UY_TT_DeliveryLine.COLUMNNAME_UY_TT_Delivery_ID + " = " + mTTDeliveryID + 						
							" AND " + X_UY_TT_DeliveryLine.COLUMNNAME_IsSelected + " = 'Y'";
		
		List<MTTDeliveryLine> model = new Query(ctx, I_UY_TT_DeliveryLine.Table_Name, whereClause, trxName).list();
		
		
		return model;
		
	}

}
