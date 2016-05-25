/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.MPayment;

/**
 * @author OpenUp. Gabriel Vila. 01/11/2011.
 *
 */
public class MAllocDetailPayment extends X_UY_AllocDetailPayment {

	private static final long serialVersionUID = 1642459263280404122L;

	/**
	 * @param ctx
	 * @param UY_AllocDetailPayment_ID
	 * @param trxName
	 */
	public MAllocDetailPayment(Properties ctx, int UY_AllocDetailPayment_ID,
			String trxName) {
		super(ctx, UY_AllocDetailPayment_ID, trxName);
		this.setRefreshWindow(true);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MAllocDetailPayment(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		this.setRefreshWindow(true);
	}

	/**
	 * OpenUp. Gabriel Vila. 28/10/2011.
	 * Obtengo y retorno modelo de cabezal de recibo.
	 * @return
	 */
	public MPayment getParent(){
		if (this.getC_Payment_ID() > 0)
			return new MPayment(getCtx(), this.getC_Payment_ID(), get_TrxName());
		else
			return null;
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		if (success){
			// Actualizo saldo afectacion en cabezal
			this.getParent().updateAllocationInfo();
		}
		return true;
	}

	@Override
	protected boolean afterDelete(boolean success) {
		if (success){
			// Actualizo saldo afectacion en cabezal
			this.getParent().updateAllocationInfo();
		}
		return true;
	}
	
	
	
}
