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
public class MTRConfigCrt extends X_UY_TR_ConfigCrt {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6044675515634234811L;

	/**
	 * @param ctx
	 * @param UY_TR_ConfigCrt_ID
	 * @param trxName
	 */
	public MTRConfigCrt(Properties ctx, int UY_TR_ConfigCrt_ID, String trxName) {
		super(ctx, UY_TR_ConfigCrt_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTRConfigCrt(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	/***
	 * Obtiene y retorna modelo segun ID de cabezal recibido. 
	 * OpenUp Ltda. Issue #4110
	 * @author Nicolas Sarlabos - 08/05/2015
	 * @see
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static MTRConfigCrt forConfig(Properties ctx, int hdrID, String trxName){
		
		String whereClause = X_UY_TR_ConfigCrt.COLUMNNAME_UY_TR_Config_ID + "=" + hdrID;
		
		MTRConfigCrt model = new Query(ctx, I_UY_TR_ConfigCrt.Table_Name, whereClause, trxName)
		.first();
		
		return model;
	}

}
