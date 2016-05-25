/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.Query;

/**
 * @author SBouissa
 *
 */
public class MCovCodigoMedioPago extends X_Cov_CodigoMedioPago {

	/**
	 * @param ctx
	 * @param Cov_CodigoMedioPago_ID
	 * @param trxName
	 */
	public MCovCodigoMedioPago(Properties ctx, int Cov_CodigoMedioPago_ID,
			String trxName) {
		super(ctx, Cov_CodigoMedioPago_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MCovCodigoMedioPago(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	
	/***
	 * Obtengo modelo segun value recibido.
	 * OpenUp Ltda. 
	 * @author Sylvie Bouissa - 22/04/2015
	 * @see
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static MCovCodigoMedioPago forValue(Properties ctx, String value, String trxName){
		
		String whereClause = X_Cov_CodigoMedioPago.COLUMNNAME_Value + "='" + value + "'";
		
		MCovCodigoMedioPago model = new Query(ctx, X_Cov_CodigoMedioPago.Table_Name, whereClause, trxName)
		.first();
		
		
		return model;
	}
}
