/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. gabriel - Feb 1, 2016
*/
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MRTInternalDeliveryLine
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author gabriel - Feb 1, 2016
*/
public class MRTInternalDeliveryLine extends X_UY_RT_InternalDeliveryLine {

	private static final long serialVersionUID = -2758613417116698909L;

	/***
	 * Constructor.
	 * @param ctx
	 * @param UY_RT_InternalDeliveryLine_ID
	 * @param trxName
	*/

	public MRTInternalDeliveryLine(Properties ctx, int UY_RT_InternalDeliveryLine_ID, String trxName) {
		super(ctx, UY_RT_InternalDeliveryLine_ID, trxName);
	}

	/***
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	*/

	public MRTInternalDeliveryLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

}
