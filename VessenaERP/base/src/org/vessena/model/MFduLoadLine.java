/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 19/10/2012
 */
package org.openup.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.Query;

/**
 * org.openup.model - MFduLoadLine
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Hp - 19/10/2012
 * @see
 */
public class MFduLoadLine extends X_UY_FduLoadLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8771466732634542864L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_FduLoadLine_ID
	 * @param trxName
	 */
	public MFduLoadLine(Properties ctx, int UY_FduLoadLine_ID, String trxName) {
		super(ctx, UY_FduLoadLine_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MFduLoadLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
//	SELECT sum(line.amtsourcedr) as subtotal
//	FROM uy_fduloadline line   
//	WHERE (select extract(month from line.dateacct)) = ((select extract(month from current_date) - 2))  
//	AND uy_fdu_store_id = 1000003
	
	public static BigDecimal getAmountForStore(Properties ctx, int uy_fdu_store_id){
		
		String whereClause = "(select extract(month from line.dateacct)) = ((select extract(month from current_date) - 1)) AND " + 
							 X_UY_FduLoadLine.COLUMNNAME_UY_Fdu_Store_ID + "=" + uy_fdu_store_id;
		
		BigDecimal amt = new Query(ctx, I_UY_FduLoadLine.Table_Name, whereClause, null)
		.sum(COLUMNNAME_AmtSourceDr);
				
		return amt;		
	}

}
