/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 30/06/2013
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.Query;

/**
 * org.openup.model - MRSign
 * OpenUp Ltda. Issue #1091 
 * Description: Modelo de señales de semforo en incidencias.
 * @author Gabriel Vila - 30/06/2013
 * @see
 */
public class MRSign extends X_UY_R_Sign {

	private static final long serialVersionUID = -7483587373609621786L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_R_Sign_ID
	 * @param trxName
	 */
	public MRSign(Properties ctx, int UY_R_Sign_ID, String trxName) {
		super(ctx, UY_R_Sign_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRSign(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/***
	 * Obtiene y retorna modelo segun value recibido.
	 * OpenUp Ltda. Issue #1091 
	 * @author Gabriel Vila - 30/06/2013
	 * @see
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static MRSign forValue(Properties ctx, String value, String trxName){
		
		String whereClause = X_UY_R_Sign.COLUMNNAME_Value + "='" + value + "'";
		
		MRSign model = new Query(ctx, I_UY_R_Sign.Table_Name, whereClause, trxName)
		.setClient_ID()
		.first();
		
		return model;
	}
	
}
