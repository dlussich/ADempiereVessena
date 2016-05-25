/**
 * @author OpenUp SBT Issue#  25/2/2016 16:40:08
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MInvoice;

/**
 * @author OpenUp SBT Issue#  25/2/2016 16:40:08
 *
 */
public class MAllocDirectCreditNote extends X_UY_AllocDirectCreditNote {

	/**
	 * @author OpenUp SBT Issue#  25/2/2016 16:40:12
	 * @param ctx
	 * @param UY_AllocDirectCreditNote_ID
	 * @param trxName
	 */
	public MAllocDirectCreditNote(Properties ctx,
			int UY_AllocDirectCreditNote_ID, String trxName) {
		super(ctx, UY_AllocDirectCreditNote_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @author OpenUp SBT Issue#  25/2/2016 16:40:12
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MAllocDirectCreditNote(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	
	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		if (success){
			// Actualizo saldo afectacion en cabezal
			this.getParent().updateAllocDirect(true);
		}
		return true;
	}
	
	@Override
	protected boolean afterDelete(boolean success) {
		if (success){
			// Actualizo saldo afectacion en cabezal
			this.getParent().updateTotalInvoices(false);
			this.getParent().updateAllocDirect(true);
		}
		return true;
	}

	/**
	 * Obtengo y retorno modelo del cabezal
	 * @author OpenUp SBT Issue #5516  26/2/2016 13:45:48
	 * @return
	 */
	private MInvoice getParent() {
		if (this.getC_Invoice_ID() > 0)
			return new MInvoice(getCtx(), this.getC_Invoice_ID(), get_TrxName());
		else
			return null;
	}
	
	@Override
	protected boolean beforeDelete() {
		if(this.isProcessed()) throw new AdempiereException("No se puede eliminar la línea");
		MInvoice nc = new MInvoice(getCtx(),this.getC_Invoice2_ID(),get_TrxName());
		if(!nc.getDocStatus().equals("DR")) throw new AdempiereException("No se puede eliminar la línea");
		return super.beforeDelete();
	}
}
