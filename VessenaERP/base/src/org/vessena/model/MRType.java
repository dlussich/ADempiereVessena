/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 05/02/2013
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.Query;

/**
 * org.openup.model - MRType
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Hp - 05/02/2013
 * @see
 */
public class MRType extends X_UY_R_Type {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1487510856643167112L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_R_Type_ID
	 * @param trxName
	 */
	public MRType(Properties ctx, int UY_R_Type_ID, String trxName) {
		super(ctx, UY_R_Type_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRType(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/***
	 * Retorna modelo segun value recibido.
	 * OpenUp Ltda. Issue #1173 
	 * @author Gabriel Vila - 12/09/2013
	 * @see
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static MRType forValue(Properties ctx, String value, String trxName){
		
		String whereClause = X_UY_R_Type.COLUMNNAME_Value + "='" + value + "'";
		
		MRType model = new Query(ctx, I_UY_R_Type.Table_Name, whereClause, trxName)
		.setClient_ID()
		.first();
		
		return model;
		
	}

	public static MRType forName(Properties ctx, String name, String trxName){
		
		String whereClause = X_UY_R_Type.COLUMNNAME_Name + "='" + name + "'";
		
		MRType model = new Query(ctx, I_UY_R_Type.Table_Name, whereClause, trxName)
		.setClient_ID()
		.first();
		
		return model;
		
	}
	
}
