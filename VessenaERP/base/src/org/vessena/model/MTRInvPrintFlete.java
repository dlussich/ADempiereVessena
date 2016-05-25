/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 12/01/2015
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.compiere.model.Query;

/**
 * org.openup.model - MTRInvPrintFlete
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Gabriel Vila - 12/01/2015
 * @see
 */
public class MTRInvPrintFlete extends X_UY_TR_InvPrintFlete {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8230491463769876422L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_TR_InvPrintFlete_ID
	 * @param trxName
	 */
	public MTRInvPrintFlete(Properties ctx, int UY_TR_InvPrintFlete_ID,
			String trxName) {
		super(ctx, UY_TR_InvPrintFlete_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTRInvPrintFlete(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	
	/***
	 * Obtiene y retonra modelo segun invoice y concepto de impresion recibido.
	 * OpenUp Ltda. Issue #1405 
	 * @author Gabriel Vila - 18/01/2015
	 * @see
	 * @param ctx
	 * @param cInvoiceID
	 * @param uyTRPrintFleteID
	 * @param trxName
	 * @return
	 */
	public static MTRInvPrintFlete forInvoicePrintFlete(Properties ctx, int cInvoiceID, int uyTRPrintFleteID, String trxName) {
		
		String whereClause = X_UY_TR_InvPrintFlete.COLUMNNAME_C_Invoice_ID + "=" + cInvoiceID +
				" AND " + X_UY_TR_InvPrintFlete.COLUMNNAME_UY_TR_PrintFlete_ID + "=" + uyTRPrintFleteID;
		
		MTRInvPrintFlete model = new Query(ctx, I_UY_TR_InvPrintFlete.Table_Name, whereClause, trxName).first();
		
		return model;
	}

	
	/***
	 * Obtiene y retonra modelo segun invoice recibida.
	 * OpenUp Ltda. Issue #1405 
	 * @author Gabriel Vila - 18/01/2015
	 * @see
	 * @param ctx
	 * @param cInvoiceID
	 * @param uyTRPrintFleteID
	 * @param trxName
	 * @return
	 */
	public static List<MTRInvPrintFlete> forInvoice(Properties ctx, int cInvoiceID, String trxName) {
		
		String whereClause = X_UY_TR_InvPrintFlete.COLUMNNAME_C_Invoice_ID + "=" + cInvoiceID;
		
		List<MTRInvPrintFlete> lines = new Query(ctx, I_UY_TR_InvPrintFlete.Table_Name, whereClause, trxName).list();
		
		return lines;
	}

}
