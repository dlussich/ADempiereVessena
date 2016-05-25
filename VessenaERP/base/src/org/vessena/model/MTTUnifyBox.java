/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 12/09/2013
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;

/**
 * org.openup.model - MTTUnifyBox
 * OpenUp Ltda. Issue #1173 
 * Description: Modelo de caja asociada a documento de unificacion de cardcarriers.
 * @author Gabriel Vila - 12/09/2013
 * @see
 */
public class MTTUnifyBox extends X_UY_TT_UnifyBox {

	private static final long serialVersionUID = -1720729707014875825L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_TT_UnifyBox_ID
	 * @param trxName
	 */
	public MTTUnifyBox(Properties ctx, int UY_TT_UnifyBox_ID, String trxName) {
		super(ctx, UY_TT_UnifyBox_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTTUnifyBox(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		String valid = null;
		
		// Valido caja y la dejo en estado En Recepcion.
		MTTBox box = (MTTBox)this.getUY_TT_Box(); 

		valid = box.setInUnify();
		
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
