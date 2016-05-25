/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 10/09/2014
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.Query;

/**
 * org.openup.model - MTRConfigAcct
 * OpenUp Ltda. Issue #1405 
 * Description: Modelo de contabilizacion en configuracion general del modulo de transporte.
 * @author Gabriel Vila - 10/09/2014
 * @see
 */
public class MTRConfigAcct extends X_UY_TR_Config_Acct {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1781380231112549278L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_TR_Config_Acct_ID
	 * @param trxName
	 */
	public MTRConfigAcct(Properties ctx, int UY_TR_Config_Acct_ID,
			String trxName) {
		super(ctx, UY_TR_Config_Acct_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTRConfigAcct(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	
	/***
	 * Obtiene y retorna modelo segun id de configuracion recibido.
	 * OpenUp Ltda. Issue #1405 
	 * @author Gabriel Vila - 10/09/2014
	 * @see
	 * @param ctx
	 * @param uyTRConfigID
	 * @param trxName
	 * @return
	 */
	public static MTRConfigAcct forConfigID(Properties ctx, int uyTRConfigID, String trxName){
		
		String whereClause = X_UY_TR_Config_Acct.COLUMNNAME_UY_TR_Config_ID + "=" + uyTRConfigID;
		
		MTRConfigAcct model = new Query(ctx, I_UY_TR_Config_Acct.Table_Name, whereClause, trxName).first();
		
		return model;
		
	}

}
