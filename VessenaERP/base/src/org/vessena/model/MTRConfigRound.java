/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.Query;

/**
 * @author Nicolas
 *
 */
public class MTRConfigRound extends X_UY_TR_ConfigRound {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8616066759707503542L;

	/**
	 * @param ctx
	 * @param UY_TR_ConfigRound_ID
	 * @param trxName
	 */
	public MTRConfigRound(Properties ctx, int UY_TR_ConfigRound_ID,
			String trxName) {
		super(ctx, UY_TR_ConfigRound_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTRConfigRound(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	/***
	 * Obtiene y retorna modelo segun ID de cabezal recibido. 
	 * OpenUp Ltda. Issue #
	 * @author Nicolas Sarlabos - 17/07/2015
	 * @see
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static MTRConfigRound forConfig(Properties ctx, int hdrID, String trxName){
		
		String whereClause = X_UY_TR_ConfigRound.COLUMNNAME_UY_TR_Config_ID + "=" + hdrID;
		
		MTRConfigRound model = new Query(ctx, I_UY_TR_ConfigRound.Table_Name, whereClause, trxName)
		.first();
		
		return model;
	}

}
