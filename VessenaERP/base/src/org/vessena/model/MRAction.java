/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 24/06/2013
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.Query;

/**
 * org.openup.model - MRAction
 * OpenUp Ltda. Issue #281 
 * Description: Modelo de acciones 
 * @author Gabriel Vila - 24/06/2013
 * @see
 */
public class MRAction extends X_UY_R_Action {

	private static final long serialVersionUID = 6659259762138791745L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_R_Action_ID
	 * @param trxName
	 */
	public MRAction(Properties ctx, int UY_R_Action_ID, String trxName) {
		super(ctx, UY_R_Action_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRAction(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/***
	 * Obtiene y retorna modelo segun value recibido.
	 * OpenUp Ltda. Issue #
	 * @author gabriel - Oct 11, 2015
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static MRAction forValue (Properties ctx, String value, String trxName){
	
		String whereClause = X_UY_R_Action.COLUMNNAME_Value + "='" + value + "'";
		
		MRAction model = new Query(ctx, I_UY_R_Action.Table_Name, whereClause, trxName).first();
		
		return model;
		
	}
	
}
