package org.openup.model;

import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;

public class MTRDuaMic extends X_UY_TR_DuaMic{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4348049088618100443L;

	public MTRDuaMic(Properties ctx, int UY_TR_DuaMic_ID, String trxName) {
		super(ctx, UY_TR_DuaMic_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		String sql = "SELECT COUNT(*) FROM UY_TR_DuaMic WHERE UY_TR_Mic_ID = " + this.getUY_TR_Mic_ID();
		if (newRecord && Integer.valueOf(DB.getSQLValue(get_TrxName(), sql)) != 0) throw new AdempiereException("Mic ya seleccionado para asociar");
		
		
		return true;
	}

	
}
