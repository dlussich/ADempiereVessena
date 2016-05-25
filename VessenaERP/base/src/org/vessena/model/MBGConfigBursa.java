/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 26/05/2015
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.Query;

/**
 * org.openup.model - MBGConfigBursa
 * OpenUp Ltda. Issue #4168 
 * Description: Modelo para parametrizacion de la funcionalidad de bolsa
 * @author Gabriel Vila - 26/05/2015
 * @see
 */
public class MBGConfigBursa extends X_UY_BG_ConfigBursa {

	private static final long serialVersionUID = -3815907321572277816L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_BG_ConfigBursa_ID
	 * @param trxName
	 */
	public MBGConfigBursa(Properties ctx, int UY_BG_ConfigBursa_ID,
			String trxName) {
		super(ctx, UY_BG_ConfigBursa_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MBGConfigBursa(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}


	/***
	 * Obtiene y retorna modelo segun compañia actual.
	 * OpenUp Ltda. Issue #4168 
	 * @author Gabriel Vila - 26/05/2015
	 * @see
	 * @param ctx
	 * @param trxName
	 * @return
	 */
	public static MBGConfigBursa forClient(Properties ctx, String trxName){
		
		MBGConfigBursa model = new Query(ctx, I_UY_BG_ConfigBursa.Table_Name, null, trxName)
		.setClient_ID()
		.first();
		
		return model;
	}
	
	
}
