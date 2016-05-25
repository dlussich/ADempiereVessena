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
public class MTRBudgetParameters extends X_UY_TR_BudgetParameters {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8534857072472467389L;

	/**
	 * @param ctx
	 * @param UY_TR_BudgetParameters_ID
	 * @param trxName
	 */
	public MTRBudgetParameters(Properties ctx, int UY_TR_BudgetParameters_ID,
			String trxName) {
		super(ctx, UY_TR_BudgetParameters_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTRBudgetParameters(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	/***
	 * Obtiene y retorna el modelo de parametros para la empresa actual y organizacion recibida.
	 * OpenUp Ltda. Issue #3169
	 * @author Nicolas Sarlabos - 08/12/2014
	 * @see
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static MTRBudgetParameters forClient(Properties ctx, int orgID, String trxName){
		
		String whereClause = X_UY_TR_BudgetParameters.COLUMNNAME_AD_Org_ID + "=" + orgID;
		
		MTRBudgetParameters p = new Query(ctx, I_UY_TR_BudgetParameters.Table_Name, whereClause, trxName)
		.setClient_ID()		
		.first();
				
		return p;
	}
	
}
