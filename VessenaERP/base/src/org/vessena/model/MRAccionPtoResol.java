/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 18/06/2013
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.Query;

/**
 * org.openup.model - MRAccionPtoResol
 * OpenUp Ltda. Issue #281 
 * Description: Modelo de acciones de puntos de resolucion de incidencias.
 * @author Gabriel Vila - 18/06/2013
 * @see
 */
public class MRAccionPtoResol extends X_UY_R_AccionPtoResol {

	private static final long serialVersionUID = -6732783284999678053L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_R_AccionPtoResol_ID
	 * @param trxName
	 */
	public MRAccionPtoResol(Properties ctx, int UY_R_AccionPtoResol_ID,
			String trxName) {
		super(ctx, UY_R_AccionPtoResol_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRAccionPtoResol(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	/***
	 * Obtengo modelo segun value recibido.
	 * OpenUp Ltda. Issue #1383
	 * @author Nicolas Sarlabos - 10/10/2013
	 * @see
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static MRAccionPtoResol forValue(Properties ctx, String value, String trxName){
		
		String whereClause = X_UY_R_AccionPtoResol.COLUMNNAME_Value + "='" + value + "'";
		
		MRAccionPtoResol model = new Query(ctx, I_UY_R_AccionPtoResol.Table_Name, whereClause, trxName)
		.setClient_ID()
		.first();		
		
		return model;
	}

}
