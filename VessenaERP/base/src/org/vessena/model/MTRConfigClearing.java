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
public class MTRConfigClearing extends X_UY_TR_ConfigClearing {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2309940494403768879L;

	/**
	 * @param ctx
	 * @param UY_TR_ConfigClearing_ID
	 * @param trxName
	 */
	public MTRConfigClearing(Properties ctx, int UY_TR_ConfigClearing_ID,
			String trxName) {
		super(ctx, UY_TR_ConfigClearing_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTRConfigClearing(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	/***
	 * Obtiene y retorna modelo segun ID de cabezal recibido. 
	 * OpenUp Ltda. Issue #3505
	 * @author Nicolas Sarlabos - 07/04/2015
	 * @see
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static MTRConfigClearing forConfig(Properties ctx, int hdrID, String trxName){
		
		String whereClause = X_UY_TR_ConfigClearing.COLUMNNAME_UY_TR_Config_ID + "=" + hdrID;
		
		MTRConfigClearing model = new Query(ctx, I_UY_TR_ConfigClearing.Table_Name, whereClause, trxName)
		.first();
		
		return model;
	}

}
