/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 24/06/2013
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.Query;

/**
 * org.openup.model - MRActionType
 * OpenUp Ltda. Issue #281 
 * Description: Modelo de Tipo de Acciones
 * @author Gabriel Vila - 24/06/2013
 * @see
 */
public class MRActionType extends X_UY_R_ActionType {

	private static final long serialVersionUID = 4356157200215988837L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_R_ActionType_ID
	 * @param trxName
	 */
	public MRActionType(Properties ctx, int UY_R_ActionType_ID, String trxName) {
		super(ctx, UY_R_ActionType_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRActionType(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/***
	 * Obtiene y retorna ID de modelo segun value recibido.
	 * OpenUp Ltda. Issue #281 
	 * @author Gabriel Vila - 24/06/2013
	 * @see
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static int IDforValue(Properties ctx, String value, String trxName){

		int id = 0;
		
		String whereClause = X_UY_R_ActionType.COLUMNNAME_Value + "='" + value + "'";
		MRActionType model = new Query(ctx, I_UY_R_ActionType.Table_Name, whereClause, trxName)
		.setClient_ID()
		.first();
		
		if (model != null){
			id = model.get_ID();
		}
		
		return id;
	}
}
