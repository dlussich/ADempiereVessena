package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;


import org.compiere.model.Query;

public class MCFEInvoice extends X_UY_CFE_Invoice{

	private static final long serialVersionUID = -2561080427552417909L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_CFE_Invoice_ID
	 * @param trxName
	 */
	public MCFEInvoice(Properties ctx, int UY_CFE_Invoice_ID, String trxName) {
		super(ctx, UY_CFE_Invoice_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MCFEInvoice(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	public MCFEInvoice getforDocumentNo(Properties ctx, String get_TrxName, int C_Invoice_ID) {
		
		final String whereClause = MCFEInvoice.COLUMNNAME_C_Invoice_ID +"= " + C_Invoice_ID;
		 
		MCFEInvoice invoice = new Query(ctx, I_UY_CFE_Invoice.Table_Name, whereClause, get_TrxName).first();		 
		 
		return invoice;

		
	}

}
