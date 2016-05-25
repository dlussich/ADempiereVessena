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
public class MTTControlSettings extends X_UY_TT_ControlSettings {

	/**
	 * 
	 */
	private static final long serialVersionUID = -390017504200049995L;

	/**
	 * @param ctx
	 * @param UY_TT_ControlSettings_ID
	 * @param trxName
	 */
	public MTTControlSettings(Properties ctx, int UY_TT_ControlSettings_ID,
			String trxName) {
		super(ctx, UY_TT_ControlSettings_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTTControlSettings(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	
	/***
	 * Obtengo modelo si existe con el mismo mensaje para la misma carga web.
	 * OpenUp Ltda. Issue #
	 * @author Guillermo Brust - 13/09/2013
	 * @see
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static MTTControlSettings forMensaje(Properties ctx, String msj, int webCourierID, String trxName){
		
		String whereClause = X_UY_TT_ControlSettings.COLUMNNAME_Message + " = '" + msj + "'" +
							 " AND " + X_UY_TT_ControlSettings.COLUMNNAME_UY_TT_WebCourier_ID + " = " + webCourierID;
		
		MTTControlSettings model = new Query(ctx, I_UY_TT_ControlSettings.Table_Name, whereClause, trxName)
		.first();		
		
		return model;
	}

}
