/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. gabriel - Dec 22, 2015
*/
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Env;

/**
 * org.openup.model - MStoreStkListProd
 * OpenUp Ltda. Issue #5150 
 * Description: Modelo de producto sugerido en lista de sugerencias para tiendas.
 * @author gabriel - Dec 22, 2015
*/
public class MStoreStkListProd extends X_UY_StoreStkListProd {

	private static final long serialVersionUID = 2576898004902515197L;

	/***
	 * Constructor.
	 * @param ctx
	 * @param UY_StoreStkListProd_ID
	 * @param trxName
	*/
	public MStoreStkListProd(Properties ctx, int UY_StoreStkListProd_ID, String trxName) {
		super(ctx, UY_StoreStkListProd_ID, trxName);
	}

	/***
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	*/
	public MStoreStkListProd(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {

		try {
			
			// Valido ingreso de cantidad
			if ((this.getQtyEntered() == null) || (this.getQtyEntered().compareTo(Env.ZERO) <= 0)){
				throw new AdempiereException("Debe indicar cantidad sugerida del producto.");
			}

			// Por ahora no uso factor de conversion entre distintas unidades de medida.
			this.setQtyOrdered(this.getQtyEntered());
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		}
		
		return true;
	}

	
}
