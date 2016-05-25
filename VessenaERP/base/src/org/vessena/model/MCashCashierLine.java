/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Env;

/**
 * @author Nicolás
 *
 */
public class MCashCashierLine extends X_UY_CashCashierLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6611514948312298657L;

	/**
	 * @param ctx
	 * @param UY_CashCashierLine_ID
	 * @param trxName
	 */
	public MCashCashierLine(Properties ctx, int UY_CashCashierLine_ID, String trxName) {
		super(ctx, UY_CashCashierLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MCashCashierLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		
		MCashCashier hdr = (MCashCashier)this.getUY_CashCashier();
		hdr.setAmount(hdr.getTotalAmount());
		hdr.saveEx();	
		
		return true;
	}

	@Override
	protected boolean afterDelete(boolean success) {
		
		MCashCashier hdr = (MCashCashier)this.getUY_CashCashier();
		hdr.setAmount(hdr.getTotalAmount());
		hdr.saveEx();		
		
		return true;
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		if(this.getAmount().compareTo(Env.ZERO)<=0) throw new AdempiereException("Monto debe ser mayor a cero");
		
		return true;
	}

}
