/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. gabriel - Oct 11, 2015
*/
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MTTBoxContract
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author gabriel - Oct 11, 2015
*/
public class MTTBoxContract extends X_UY_TT_BoxContract {

	private static final long serialVersionUID = -8517978724655567972L;

	/***
	 * Constructor.
	 * @param ctx
	 * @param UY_TT_BoxContract_ID
	 * @param trxName
	*/

	public MTTBoxContract(Properties ctx, int UY_TT_BoxContract_ID, String trxName) {
		super(ctx, UY_TT_BoxContract_ID, trxName);
	}

	/***
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	*/

	public MTTBoxContract(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

}
