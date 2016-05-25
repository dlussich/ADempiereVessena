/**
 * 
 */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * @author gbrust
 *
 */
public class MProductAcct extends X_M_Product_Acct {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8388236870336237094L;

	/**
	 * @param ctx
	 * @param M_Product_Acct_ID
	 * @param trxName
	 */
	public MProductAcct(Properties ctx, int M_Product_Acct_ID, String trxName) {
		super(ctx, M_Product_Acct_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MProductAcct(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	
	/**
	 * OpenUp 01/04/2013 Guillermo Brust.
	 * Issue #165
	 * Retorna una instancia de esta clase a partir de m_product_id
	 */
	public static MProductAcct getMProductAcctForProduct(Properties ctx, int m_product_id){
		
		String whereClause = X_M_Product_Acct.COLUMNNAME_M_Product_ID + "=" + m_product_id;
		
		MProductAcct ret = new Query(ctx, I_M_Product_Acct.Table_Name, whereClause, null).first();
		
		return ret;		
	}


}
