/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 12/10/2012
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.Query;

/**
 * org.openup.model - MInvoiceProvision
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Hp - 12/10/2012
 * @see
 */
public class MInvoiceProvision extends X_UY_Invoice_Provision {

	private static final long serialVersionUID = -1872022350309752901L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_Invoice_Provision_ID
	 * @param trxName
	 */
	public MInvoiceProvision(Properties ctx, int UY_Invoice_Provision_ID,
			String trxName) {
		super(ctx, UY_Invoice_Provision_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MInvoiceProvision(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/***
	 * Obtiene provision de factura segun linea de provision. 
	 * OpenUp Ltda. Issue #49 
	 * @author Gabriel Vila - 12/10/2012
	 * @see
	 * @param ctx
	 * @param get_ID
	 * @param get_TrxName
	 * @return
	 */
	public static MInvoiceProvision forProvisionLine(Properties ctx, int uyProvisionLineID, int cInvoiceID, String trxName) {
		
		String whereClause = X_UY_Invoice_Provision.COLUMNNAME_UY_ProvisionLine_ID + "=" + uyProvisionLineID + " AND " +
				             X_UY_Invoice_Provision.COLUMNNAME_C_Invoice_ID + "=" + cInvoiceID;
		MInvoiceProvision value = new Query(ctx, I_UY_Invoice_Provision.Table_Name, whereClause, trxName)
								 .first();

		if (value == null) return null;
		if (value.get_ID() <= 0) return null;
		
		return value;
		
	}

}
