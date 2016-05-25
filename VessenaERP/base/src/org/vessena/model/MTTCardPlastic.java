/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 26/08/2013
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.Query;

/**
 * org.openup.model - MTTCardPlastic
 * OpenUp Ltda. Issue #1173 
 * Description: Modelo de plasticos asociados a una cuenta.
 * @author Gabriel Vila - 26/08/2013
 * @see
 */
public class MTTCardPlastic extends X_UY_TT_CardPlastic {

	private static final long serialVersionUID = 8654273411258276209L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_TT_CardPlastic_ID
	 * @param trxName
	 */
	public MTTCardPlastic(Properties ctx, int UY_TT_CardPlastic_ID,
			String trxName) {
		super(ctx, UY_TT_CardPlastic_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTTCardPlastic(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}


	/***
	 * Obtiene y retorna modelo segun cedula recibida.
	 * OpenUp Ltda. Issue #1173 
	 * @author Gabriel Vila - 25/09/2013
	 * @see
	 * @param ctx
	 * @param uyTTCardID
	 * @param cedula
	 * @param trxName
	 * @return
	 */
	public static MTTCardPlastic forCedula(Properties ctx, int uyTTCardID, String cedula, String trxName){
		
		String whereClause = X_UY_TT_CardPlastic.COLUMNNAME_UY_TT_Card_ID + "=" + uyTTCardID +
				" AND " + X_UY_TT_CardPlastic.COLUMNNAME_Cedula + "='" + cedula + "'";
		
		MTTCardPlastic model = new Query(ctx, I_UY_TT_CardPlastic.Table_Name, whereClause, trxName)
		.first();
		
		return model;
		
	}
	
	
}
