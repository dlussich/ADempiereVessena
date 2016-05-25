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
public class MBPCustomerAcct extends X_C_BP_Customer_Acct {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7185623109018070297L;

	/**
	 * @param ctx
	 * @param C_BP_Customer_Acct_ID
	 * @param trxName
	 */
	public MBPCustomerAcct(Properties ctx, int C_BP_Customer_Acct_ID,
			String trxName) {
		super(ctx, C_BP_Customer_Acct_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MBPCustomerAcct(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	
	/**
	 * OpenUp 01/04/2013 Guillermo Brust.
	 * Issue #165
	 * Retorna una instancia de esta clase a partir de c_bpartner_id
	 */
	public static MBPCustomerAcct getMBPCustomerAcctForCbPartner(Properties ctx, int c_bpartner_id){
		
		String whereClause = X_C_BP_Customer_Acct.COLUMNNAME_C_BPartner_ID + "=" + c_bpartner_id;
		
		MBPCustomerAcct ret = new Query(ctx, I_C_BP_Customer_Acct.Table_Name, whereClause, null).first();
		
		return ret;		
	}


}
