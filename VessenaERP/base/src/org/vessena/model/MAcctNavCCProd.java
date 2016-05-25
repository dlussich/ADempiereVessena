package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;
/**
 * org.openup.model - MAcctNavCCProd
 * OpenUp Ltda. Issue #2978 
 * Description: 
 * @author Matias Carbajal - 18/11/2014
 * @see
 */
public class MAcctNavCCProd extends X_UY_AcctNavCC_Prod{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6909995953388489137L;

	public MAcctNavCCProd(Properties ctx, int UY_AcctNavCC_Prod_ID,
			String trxName) {
		super(ctx, UY_AcctNavCC_Prod_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MAcctNavCCProd(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	
}
