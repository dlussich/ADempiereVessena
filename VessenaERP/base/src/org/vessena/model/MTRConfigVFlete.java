/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 01/01/2015
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.Query;

/**
 * org.openup.model - MTRConfigVFlete
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Gabriel Vila - 01/01/2015
 * @see
 */
public class MTRConfigVFlete extends X_UY_TR_ConfigVFlete {

	private static final long serialVersionUID = 3696330287462275211L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_TR_ConfigVFlete_ID
	 * @param trxName
	 */
	public MTRConfigVFlete(Properties ctx, int UY_TR_ConfigVFlete_ID,
			String trxName) {
		super(ctx, UY_TR_ConfigVFlete_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTRConfigVFlete(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/***
	 * Obtiene y retorna producto vale flete para empresa de crts.
	 * OpenUp Ltda. Issue #1405 
	 * @author Gabriel Vila - 01/01/2015
	 * @see
	 * @param adClientID
	 * @return
	 */
	public MTRConfigVFleteProd getVFleteProdForClient(int adClientID){
		
		String whereClause = X_UY_TR_ConfigVFleteProd.COLUMNNAME_UY_TR_ConfigVFlete_ID + "=" + this.get_ID() +
				" and " + X_UY_TR_ConfigVFleteProd.COLUMNNAME_AD_Client_ID_Aux + "=" + adClientID;
		
		MTRConfigVFleteProd model = new org.compiere.model.Query(getCtx(), I_UY_TR_ConfigVFleteProd.Table_Name, whereClause, null).first();
		
		return model;
		
	}
	
	/***
	 * Obtiene y retorna modelo segun ID de cabezal recibido. 
	 * OpenUp Ltda. Issue #
	 * @author Nicolas Sarlabos - 11/03/2015
	 * @see
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static MTRConfigVFlete forConfig(Properties ctx, int hdrID, String trxName){
		
		String whereClause = X_UY_TR_ConfigVFlete.COLUMNNAME_UY_TR_Config_ID + "=" + hdrID;
		
		MTRConfigVFlete model = new Query(ctx, I_UY_TR_ConfigVFlete.Table_Name, whereClause, trxName)
		.first();
		
		return model;
	}
}
