/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 22/11/2012
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.MPayment;

/**
 * org.openup.model - MPaymentResguardo
 * OpenUp Ltda. Issue #100 
 * Description: Resguardos asociados a un recibo de pago.
 * @author Hp - 22/11/2012
 * @see
 */
public class MPaymentResguardo extends X_UY_Payment_Resguardo {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6109998823580455075L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_Payment_Resguardo_ID
	 * @param trxName
	 */
	public MPaymentResguardo(Properties ctx, int UY_Payment_Resguardo_ID,
			String trxName) {
		super(ctx, UY_Payment_Resguardo_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MPaymentResguardo(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
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
