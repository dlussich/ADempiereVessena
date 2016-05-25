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
public class MTRTireMoveOpen extends X_UY_TR_TireMoveOpen {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2377354600957446913L;

	/**
	 * @param ctx
	 * @param UY_TR_TireMoveOpen_ID
	 * @param trxName
	 */
	public MTRTireMoveOpen(Properties ctx, int UY_TR_TireMoveOpen_ID,
			String trxName) {
		super(ctx, UY_TR_TireMoveOpen_ID, trxName);
		
	}
	

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTRTireMoveOpen(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		
	}	
	
	
	/***
	 * Obtengo modelo segun value recibido.
	 * OpenUp Ltda. Issue #1605
	 * @author Guillermo Brust - 29/11/2013
	 * @see
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static MTRTireMoveOpen forTireID(Properties ctx, int tireID, int tireMoveID, String trxName){
		
		String whereClause = X_UY_TR_TireMoveOpen.COLUMNNAME_UY_TR_Tire_ID + " = " + tireID + 
							" AND " + X_UY_TR_TireMoveOpen.COLUMNNAME_UY_TR_TireMove_ID + " = " + tireMoveID;
		
		MTRTireMoveOpen model = new Query(ctx, I_UY_TR_TireMoveOpen.Table_Name, whereClause, trxName)
		.first();		
		
		return model;
	}

	
}
