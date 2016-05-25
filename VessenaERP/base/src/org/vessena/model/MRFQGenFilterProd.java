/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 12/12/2012
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MRFQGenFilterProd
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Hp - 12/12/2012
 * @see
 */
public class MRFQGenFilterProd extends X_UY_RFQGen_Filter_Prod {

	/**
	 * 
	 */
	private static final long serialVersionUID = -89818117525717116L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_RFQGen_Filter_Prod_ID
	 * @param trxName
	 */
	public MRFQGenFilterProd(Properties ctx, int UY_RFQGen_Filter_Prod_ID,
			String trxName) {
		super(ctx, UY_RFQGen_Filter_Prod_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRFQGenFilterProd(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
