package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
/**
 * org.openup.model - MTTRetPrintValeBox
 * OpenUp Ltda. Issue #3273 
 * Description: Modelo de caja escaneada para proceso impresion de vale
 * @author Sylvie Bouissa - 06/01/2015
 * @see
 */
public class MTTRetPrintValeBox extends X_UY_TT_RetPrintValeBox {

	public MTTRetPrintValeBox(Properties ctx, int UY_TT_RetPrintValeBox_ID,
			String trxName) {
		super(ctx, UY_TT_RetPrintValeBox_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public MTTRetPrintValeBox(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord) {

		String valid = null;
		
		// Valido caja y la dejo en estado En Recepcion.
		MTTBox box = (MTTBox)this.getUY_TT_Box(); 

		valid = box.setInImpresionVale();
		this.setUY_DeliveryPoint_ID(box.getUY_DeliveryPoint_ID_To());
		this.setIsRetained(box.isRetained());
		this.setImpresionVale(box.isImpresionVale());
		//valid = box.setInComunica();
		
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
