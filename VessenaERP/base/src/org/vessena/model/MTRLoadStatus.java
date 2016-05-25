/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.Query;

/**
 * @author gbrust
 *
 */
public class MTRLoadStatus extends X_UY_TR_LoadStatus {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8410393654893754676L;

	/**
	 * @param ctx
	 * @param UY_TR_LoadStatus_ID
	 * @param trxName
	 */
	public MTRLoadStatus(Properties ctx, int UY_TR_LoadStatus_ID, String trxName) {
		super(ctx, UY_TR_LoadStatus_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTRLoadStatus(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	
	/***
	 * Obtiene y retorna modelo dado un value determinado
	 * OpenUp Ltda. Issue #1405 
	 * @author Gabriel Vila - 08/12/2014
	 * @see
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static MTRLoadStatus forValue(Properties ctx, String value, String trxName){
		
		String whereClause = X_UY_TR_LoadStatus.COLUMNNAME_Value + "='" + value + "'";
		
		MTRLoadStatus model = new Query(ctx, I_UY_TR_LoadStatus.Table_Name, whereClause, trxName)
		.setClient_ID()
		.first();
		
		return model;
		
	}

}
