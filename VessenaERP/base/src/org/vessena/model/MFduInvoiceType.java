/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.compiere.model.Query;

/**
 * @author gbrust
 *
 */
public class MFduInvoiceType extends X_UY_Fdu_InvoiceType {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2919095203763306331L;

	/**
	 * @param ctx
	 * @param UY_Fdu_InvoiceType_ID
	 * @param trxName
	 */
	public MFduInvoiceType(Properties ctx, int UY_Fdu_InvoiceType_ID,
			String trxName) {
		super(ctx, UY_Fdu_InvoiceType_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MFduInvoiceType(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	
	public static List<MFduInvoiceType> getMFduInvoiceTypeForFduFile(Properties ctx, int uy_fduFile_id){

		String whereClause = X_UY_Fdu_InvoiceType.COLUMNNAME_UY_FduFile_ID + "=" + uy_fduFile_id;
		
		List<MFduInvoiceType> lines = new Query(ctx, I_UY_Fdu_InvoiceType.Table_Name, whereClause, null)
		.list();
		
		return lines;	
	}

}
