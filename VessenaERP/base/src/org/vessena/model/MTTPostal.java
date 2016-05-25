/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. usuario - 04/10/2013
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.compiere.model.Query;

/**
 * org.openup.model - MTTPostal
 * OpenUp Ltda. Issue #1173 
 * Description: Modelo de codigo postales para Tracking de Tarjetas.
 * @author Gabriel Vila - 04/10/2013
 * @see
 */
public class MTTPostal extends X_UY_TT_Postal {

	private static final long serialVersionUID = 2773795625803731107L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_TT_Postal_ID
	 * @param trxName
	 */
	public MTTPostal(Properties ctx, int UY_TT_Postal_ID, String trxName) {
		super(ctx, UY_TT_Postal_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTTPostal(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	
	/***
	 * Obtiene y retorna lista de este modelo. 
	 * OpenUp Ltda. Issue #1173 
	 * @author Gabriel Vila - 04/10/2013
	 * @see
	 * @param ctx
	 * @param trxName
	 * @return
	 */
	public static List<MTTPostal> getPostals(Properties ctx, String trxName){
		
		List <MTTPostal> lines = new Query(ctx, I_UY_TT_Postal.Table_Name, null, trxName).list();
		
		return lines;
		
	}
	
	
}
