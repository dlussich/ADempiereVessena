/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 20/05/2013
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.Query;

/**
 * org.openup.model - MRReclamoObserver
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Hp - 20/05/2013
 * @see
 */
public class MRReclamoObserver extends X_UY_R_ReclamoObserver {

	private static final long serialVersionUID = -862319669180741439L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_R_ReclamoObserver_ID
	 * @param trxName
	 */
	public MRReclamoObserver(Properties ctx, int UY_R_ReclamoObserver_ID,
			String trxName) {
		super(ctx, UY_R_ReclamoObserver_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRReclamoObserver(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	
	/***
	 * Obtiene y retorna modelo segun id de usuario recibido.
	 * OpenUp Ltda. Issue #285 
	 * @author Gabriel Vila - 25/06/2013
	 * @see
	 * @param ctx
	 * @param adUserID
	 * @param trxName
	 * @return
	 */
	public static MRReclamoObserver forUserID(Properties ctx, int adUserID, String trxName) {
		
		String whereClause = X_UY_R_ReclamoObserver.COLUMNNAME_AD_User_ID + "=" + adUserID;
		
		MRReclamoObserver model = new Query(ctx, I_UY_R_ReclamoObserver.Table_Name, whereClause, trxName)
		.first();
		
		return model;
		
	}
}
