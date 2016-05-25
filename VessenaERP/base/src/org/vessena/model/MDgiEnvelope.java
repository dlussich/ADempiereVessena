package org.openup.model;

import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;

public class MDgiEnvelope extends X_UY_DGI_Envelope{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5717371638071719684L;

	public MDgiEnvelope(Properties ctx, int UY_DGI_Envelope_ID, String trxName) {
		super(ctx, UY_DGI_Envelope_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean beforeDelete() {
		throw new AdempiereException("Eliminación no permitida");
		//return super.beforeDelete();
	}
	
}
