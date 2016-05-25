/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 12/02/2013
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;

/**
 * org.openup.model - MPayOrderLine
 * OpenUp Ltda. Issue #348 
 * Description: Lineas de Ordenes de Pago
 * @author Gabriel Vila - 12/02/2013
 * @see
 */
public class MPayOrderLine extends X_UY_PayOrderLine {

	private static final long serialVersionUID = -5861642100593761743L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_PayOrderLine_ID
	 * @param trxName
	 */
	public MPayOrderLine(Properties ctx, int UY_PayOrderLine_ID, String trxName) {
		super(ctx, UY_PayOrderLine_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MPayOrderLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	
	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {

		if (!success) return success;

		// Actualiza orden de pago
		this.updateHeader();
		
		return true;

	}

	@Override
	protected boolean afterDelete(boolean success) {

		if (!success) return success;

		// Actualiza orden de pago		
		this.updateHeader();
		
		return true;

	}

	/***
	 * Actualiza total del cabezal.
	 * OpenUp Ltda. Issue #285 
	 * @author Gabriel Vila - 05/04/2013.
	 * @see
	 */
	private void updateHeader() {
		try{
			
			String action = "update uy_payorder set payamt=" +
					"(select coalesce(sum(amount),0) from uy_payorderline " +
					" where uy_payorder_id =" + this.getUY_PayOrder_ID() + ") - coalesce(amtresguardo,0) , " +
					" subtotal =" +
					"(select coalesce(sum(amount),0) from uy_payorderline " +
					" where uy_payorder_id =" + this.getUY_PayOrder_ID() + ") " +					
					" where uy_payorder_id =" + this.getUY_PayOrder_ID();
			
			DB.executeUpdateEx(action, get_TrxName());
		}
		catch(Exception e){
			throw new AdempiereException(e);
		}
	}
	
}
