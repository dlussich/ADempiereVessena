/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.util.DB;

/**
 * @author raul
 *
 */
public class MInvoiceGenerateLine extends X_UY_InvoiceGenerateLine {

	/**
	 * @param ctx
	 * @param UY_InvoiceGenerateLine_ID
	 * @param trxName
	 */
	public MInvoiceGenerateLine(Properties ctx, int UY_InvoiceGenerateLine_ID,
			String trxName) {
		super(ctx, UY_InvoiceGenerateLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MInvoiceGenerateLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected boolean beforeDelete() {
		
		String sqlDelete = "UPDATE UY_InvoiceGenerateDetail SET UY_InvoiceGenerateLine_ID = NULL WHERE UY_InvoiceGenerateLine_ID = " + getUY_InvoiceGenerateLine_ID();
		DB.executeUpdate(sqlDelete, get_TrxName());
		
		return true;
	}

}
