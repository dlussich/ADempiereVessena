/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 13/10/2013
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.Query;

/**
 * org.openup.model - MTTAction
 * OpenUp Ltda. Issue #1173 
 * Description: Modelo de acccion de tracking.
 * @author Gabriel Vila - 13/10/2013
 * @see
 */
public class MTTAction extends X_UY_TT_Action {

	private static final long serialVersionUID = -5704011852085971730L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_TT_Action_ID
	 * @param trxName
	 */
	public MTTAction(Properties ctx, int UY_TT_Action_ID, String trxName) {
		super(ctx, UY_TT_Action_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTTAction(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/***
	 * Obtengo modelo segun value recibido.
	 * OpenUp Ltda. Issue #1933 
	 * @author Gabriel Vila - 10/03/2013
	 * @see
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static MTTAction forValue(Properties ctx, String value, String trxName){
		
		String whereClause = X_UY_TT_Action.COLUMNNAME_Value + "='" + value + "'";
		
		MTTAction model = new Query(ctx, I_UY_TT_Action.Table_Name, whereClause, trxName)
		.first();
		
		
		return model;
	}

	
}
