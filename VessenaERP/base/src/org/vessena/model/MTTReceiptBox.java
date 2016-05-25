/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 28/08/2013
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;

/**
 * org.openup.model - MTTReceiptBox
 * OpenUp Ltda. Issue #1173 
 * Description: Modelo de caja seleccionada en recepcion.
 * @author Gabriel Vila - 28/08/2013
 * @see
 */
public class MTTReceiptBox extends X_UY_TT_ReceiptBox {

	private static final long serialVersionUID = 1714905165025774833L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_TT_ReceiptBox_ID
	 * @param trxName
	 */
	public MTTReceiptBox(Properties ctx, int UY_TT_ReceiptBox_ID, String trxName) {
		super(ctx, UY_TT_ReceiptBox_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTTReceiptBox(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {

		String valid = null;
		
		// Valido caja y la dejo en estado En Recepcion.
		MTTBox box = (MTTBox)this.getUY_TT_Box(); 

		valid = box.setInReceipt();
		this.setImpresionVale(box.isImpresionVale());
		
		if (valid != null){
			throw new AdempiereException(valid);
		}

		return true;
	}

	@Override
	protected boolean beforeDelete() {

		// Si elimino caja la dejo en estado cerrado.
		MTTBox box = (MTTBox)this.getUY_TT_Box(); 
		box.setBoxStatus(X_UY_TT_Box.BOXSTATUS_Cerrado);
		box.saveEx();
		
		return true;
	}

	
	
}
