/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 06/02/2013
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.Query;

/**
 * org.openup.model - MRTarea
 * OpenUp Ltda. Issue #281 
 * Description: 
 * @author Gabriel Vila - 06/02/2013
 * @see
 */
public class MRTarea extends X_UY_R_Tarea {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2019224008561551596L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_R_Tarea_ID
	 * @param trxName
	 */
	public MRTarea(Properties ctx, int UY_R_Tarea_ID, String trxName) {
		super(ctx, UY_R_Tarea_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRTarea(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/***
	 * Obtiene y retorna Tarea segun value recibido
	 * OpenUp Ltda. Issue #281 
	 * @author Gabriel Vila - 28/03/2013
	 * @see
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static MRTarea forValue(Properties ctx, String value, String trxName){
		
		String whereClause = X_UY_R_Tarea.COLUMNNAME_Value + "='" + value + "'";
		
		MRTarea model = new Query(ctx, I_UY_R_Tarea.Table_Name, whereClause, trxName)
		.setClient_ID()
		.first();
				
		return model;
	}
	
	
}
