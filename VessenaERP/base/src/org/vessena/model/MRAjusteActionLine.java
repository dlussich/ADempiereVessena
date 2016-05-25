/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 09/04/2013
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;

/**
 * org.openup.model - MRAjusteActionLine
 * OpenUp Ltda. Issue #657 
 * Description: 
 * @author Gabriel Vila - 09/04/2013
 * @see
 */
public class MRAjusteActionLine extends X_UY_R_AjusteActionLine {

	private static final long serialVersionUID = -3998748541680535931L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_R_AjusteActionLine_ID
	 * @param trxName
	 */
	public MRAjusteActionLine(Properties ctx, int UY_R_AjusteActionLine_ID,
			String trxName) {
		super(ctx, UY_R_AjusteActionLine_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRAjusteActionLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {

		// Valido que no marquen confirmar y anular al mismo tiempo en esta linea.
		if (this.isSelected() && this.isRejected()){
			throw new AdempiereException("Debe marcar si Confirma o Anula este Ajuste. No se permite marcar ambas opciones al mismo tiempo.");
		}
		
		return true;
	}

	
}
