/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 23/09/2013
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.Query;

/**
 * org.openup.model - MTTConfigCardAction
 * OpenUp Ltda. Issue #1173 
 * Description: Modelo de configuracion de acciones para cuentas.
 * @author Gabriel Vila - 23/09/2013
 * @see
 */
public class MTTConfigCardAction extends X_UY_TT_ConfigCardAction {

	private static final long serialVersionUID = -7352761918604616586L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_TT_ConfigCardAction_ID
	 * @param trxName
	 */
	public MTTConfigCardAction(Properties ctx, int UY_TT_ConfigCardAction_ID,
			String trxName) {
		super(ctx, UY_TT_ConfigCardAction_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTTConfigCardAction(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	
	/***
	 * Obtiene y retorna modelo segun codigo interno recibido.
	 * OpenUp Ltda. Issue #1173 
	 * @author Gabriel Vila - 25/09/2013
	 * @see
	 * @param ctx
	 * @param configID
	 * @param internalCode
	 * @param trxName
	 * @return
	 */
	public static MTTConfigCardAction forInternalCode(Properties ctx, int configID, String internalCode, String trxName){
		
		String whereClause = X_UY_TT_ConfigCardAction.COLUMNNAME_UY_TT_Config_ID + "=" + configID +
				" AND " + X_UY_TT_ConfigCardAction.COLUMNNAME_InternalCode + "='" + internalCode + "'";
		
		MTTConfigCardAction model = new Query(ctx, I_UY_TT_ConfigCardAction.Table_Name, whereClause, trxName)
		.setOnlyActiveRecords(true)
		.first();
		
		return model;
		
	}
	
}
