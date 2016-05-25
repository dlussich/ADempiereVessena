/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 13/03/2013
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.MPayment;

/**
 * org.openup.model - MReceiptResguardo
 * OpenUp Ltda. Issue #243
 * Description: Manejo de Retenciones y Resguardos de Clientes
 * @author Gabriel Vila - 13/03/2013
 * @see
 */
public class MReceiptResguardo extends X_UY_Receipt_Resguardo {

	private static final long serialVersionUID = -2372225163488947192L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_Receipt_Resguardo_ID
	 * @param trxName
	 */
	public MReceiptResguardo(Properties ctx, int UY_Receipt_Resguardo_ID,
			String trxName) {
		super(ctx, UY_Receipt_Resguardo_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MReceiptResguardo(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		
		if (success){
			// Actulizo subtotal del recibo
			MPayment payment = (MPayment)this.getC_Payment();
			payment.updateSubTotal();
		}

		return true;
	}

	@Override
	protected boolean afterDelete(boolean success) {

		if (success){
			// Actulizo subtotal del recibo
			MPayment payment = (MPayment)this.getC_Payment();
			payment.updateSubTotal();
		}
		
		return true;
	}

	
}
