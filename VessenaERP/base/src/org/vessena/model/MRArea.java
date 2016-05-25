/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 21/05/2013
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.Query;

/**
 * org.openup.model - MRArea
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Hp - 21/05/2013
 * @see
 */
public class MRArea extends X_UY_R_Area {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8880675881005119275L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_R_Area_ID
	 * @param trxName
	 */
	public MRArea(Properties ctx, int UY_R_Area_ID, String trxName) {
		super(ctx, UY_R_Area_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRArea(Properties ctx, ResultSet rs, String trxName) {
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
	public static MRArea forValue(Properties ctx, String value, String trxName){
		
		String whereClause = X_UY_R_Area.COLUMNNAME_Value + "='" + value + "'";
		
		MRArea model = new Query(ctx, I_UY_R_Area.Table_Name, whereClause, trxName)
		.setClient_ID()
		.first();
		
		return model;
		
	}
	
	
}
