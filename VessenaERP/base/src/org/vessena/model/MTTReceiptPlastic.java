/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 05/09/2013
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;


/**
 * org.openup.model - MTTReceiptPlastic
 * OpenUp Ltda. Issue #1173 
 * Description: Modelo de recepcion de plasticos.
 * @author Gabriel Vila - 05/09/2013
 * @see
 */
public class MTTReceiptPlastic extends X_UY_TT_ReceiptPlastic {

	private static final long serialVersionUID = -5121820512689580117L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_TT_ReceiptPlastic_ID
	 * @param trxName
	 */
	public MTTReceiptPlastic(Properties ctx, int UY_TT_ReceiptPlastic_ID,
			String trxName) {
		super(ctx, UY_TT_ReceiptPlastic_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTTReceiptPlastic(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {

		MTTReceipt receipt = (MTTReceipt)this.getUY_TT_Receipt();
		
		MTTCardPlastic plastic = (MTTCardPlastic)this.getUY_TT_CardPlastic();
		if (plastic != null){
			
			// Valido que no modifique un plastico seleccionado en otra recepcion
			if (plastic.isSelected() != this.isSelected()){
				if (receipt != null){
					if (plastic.getUY_TT_Receipt_ID() != receipt.get_ID()){
						throw new AdempiereException("No es posible modificar seleccion de Plastico cargado en otra Recepcion.");
					}
				}	
			}

			// Actualizo plastico
			if (receipt != null){
				plastic.setUY_TT_Receipt_ID(receipt.get_ID());
			}
			
			plastic.setIsSelected(this.isSelected());
			plastic.saveEx();
		}
		
		return true;
	}

	
	
	
}
