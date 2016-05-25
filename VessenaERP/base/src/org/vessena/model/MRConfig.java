/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 27/06/2013
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.Query;

/**
 * org.openup.model - MRConfig
 * OpenUp Ltda. Issue #281 
 * Description: Modelo de configuracion general del modulo de incidencias.
 * @author Gabriel Vila - 27/06/2013
 * @see
 */
public class MRConfig extends X_UY_R_Config {

	private static final long serialVersionUID = -5072239270683472623L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_R_Config_ID
	 * @param trxName
	 */
	public MRConfig(Properties ctx, int UY_R_Config_ID, String trxName) {
		super(ctx, UY_R_Config_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRConfig(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/***
	 * Obtiene y retorna modelo segun value recibido
	 * OpenUp Ltda. Issue #281 
	 * @author Gabriel Vila - 27/06/2013
	 * @see
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static MRConfig forValue(Properties ctx, String value, String trxName){
		
		String whereClause = X_UY_R_Config.COLUMNNAME_Value + "='" + value + "'";
		
		MRConfig model = new Query(ctx, I_UY_R_Config.Table_Name, whereClause, trxName)
		.setClient_ID()
		.first();
				
		return model;
	}

}
