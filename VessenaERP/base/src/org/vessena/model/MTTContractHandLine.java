/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. gabriel - Oct 12, 2015
*/
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MTTContractHandLine
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author gabriel - Oct 12, 2015
*/
public class MTTContractHandLine extends X_UY_TT_ContractHandLine {

	private static final long serialVersionUID = 5053088510971185092L;

	/***
	 * Constructor.
	 * @param ctx
	 * @param UY_TT_ContractHandLine_ID
	 * @param trxName
	*/

	public MTTContractHandLine(Properties ctx, int UY_TT_ContractHandLine_ID, String trxName) {
		super(ctx, UY_TT_ContractHandLine_ID, trxName);
	}

	/***
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	*/

	public MTTContractHandLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

}
