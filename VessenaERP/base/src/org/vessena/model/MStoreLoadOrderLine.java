/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. gabriel - Dec 21, 2015
*/
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Env;

/**
 * org.openup.model - MStoreLoadOrderLine
 * OpenUp Ltda. Issue #5150 
 * Description: Modelo de linea de orden de carga de tienda.
 * @author gabriel - Dec 21, 2015
*/
public class MStoreLoadOrderLine extends X_UY_StoreLoadOrderLine {

	private static final long serialVersionUID = -990223867710576523L;

	/***
	 * Constructor.
	 * @param ctx
	 * @param UY_StoreLoadOrderLine_ID
	 * @param trxName
	*/

	public MStoreLoadOrderLine(Properties ctx, int UY_StoreLoadOrderLine_ID, String trxName) {
		super(ctx, UY_StoreLoadOrderLine_ID, trxName);
	}

	/***
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	*/

	public MStoreLoadOrderLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		try {
			
			// Valido ingreso de cantidad
			if ((this.getQtyEntered() == null) || (this.getQtyEntered().compareTo(Env.ZERO) <= 0)){
				throw new AdempiereException("Debe indicar cantidad para este producto.");
			}

			// Si no hay cantidad web, seteo en cero
			if (this.getQtyWebEntered() == null) this.setQtyWebEntered(Env.ZERO);

			// Seteo cantidad total 			
			this.setTotalQtyEntered(this.getQtyEntered().add(this.getQtyWebEntered()));
			
			// Por ahora no uso factor de conversion entre distintas unidades de medida.
			this.setQtyOrdered(this.getQtyEntered());
			this.setQtyWebOrdered(this.getQtyWebEntered());
			this.setTotalQty(this.getQtyOrdered().add(this.getQtyWebOrdered()));
		} 
		catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		}
		
		return true;
	}

	
}
