/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 20/12/2012
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.Query;

/**
 * org.openup.model - MGerencialBP
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Hp - 20/12/2012
 * @see
 */
public class MGerencialBP extends X_UY_Gerencial_BP {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3349592695202397499L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_Gerencial_BP_ID
	 * @param trxName
	 */
	public MGerencialBP(Properties ctx, int UY_Gerencial_BP_ID, String trxName) {
		super(ctx, UY_Gerencial_BP_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MGerencialBP(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/***
	 * Obtiene y retorna modelo segun cuenta y socio de negocio.
	 * OpenUp Ltda. Issue #116 
	 * @author Gabriel Vila - 18/02/2013
	 * @see
	 * @param ctx
	 * @param uyGerencialID
	 * @param accountID
	 * @param cBPartnerID
	 * @param trxName
	 * @return
	 */
	public static MGerencialBP forAccountBP(Properties ctx, int uyGerencialID, int accountID, int cBPartnerID, String trxName){
		
		String whereClause = X_UY_Gerencial_BP.COLUMNNAME_UY_Gerencial_ID + "=" + uyGerencialID +
						" AND " + X_UY_Gerencial_BP.COLUMNNAME_Parent_ID + "=" + accountID +
						" AND " + X_UY_Gerencial_BP.COLUMNNAME_C_BPartner_ID + "=" + cBPartnerID;
		
		MGerencialBP value = new Query(ctx, I_UY_Gerencial_BP.Table_Name, whereClause, trxName)
		.first();
		
		return value;
		
	}
}
