/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 06/10/2013
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;

/**
 * org.openup.model - MTTComunicaBox
 * OpenUp Ltda. Issue #1173 
 * Description: Modelo de caja escaneada para proceso de cuentas en comunicacion a usuarios.
 * @author Gabriel Vila - 06/10/2013
 * @see
 */
public class MTTComunicaBox extends X_UY_TT_ComunicaBox {

	private static final long serialVersionUID = 2267106251695221521L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_TT_ComunicaBox_ID
	 * @param trxName
	 */
	public MTTComunicaBox(Properties ctx, int UY_TT_ComunicaBox_ID,
			String trxName) {
		super(ctx, UY_TT_ComunicaBox_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTTComunicaBox(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {

		String valid = null;
		
		// Valido caja y la dejo en estado En Recepcion.
		MTTBox box = (MTTBox)this.getUY_TT_Box(); 

		valid = box.setInComunica();
		
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
