/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 21/12/2012
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.Query;

/**
 * org.openup.model - MGerencialDetail
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Hp - 21/12/2012
 * @see
 */
public class MGerencialDetail extends X_UY_Gerencial_Detail {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2002463746533211451L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_Gerencial_Detail_ID
	 * @param trxName
	 */
	public MGerencialDetail(Properties ctx, int UY_Gerencial_Detail_ID,
			String trxName) {
		super(ctx, UY_Gerencial_Detail_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MGerencialDetail(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/***
	 * Obtiene y retorna id de segundo nivel para determinada cuenta.
	 * OpenUp Ltda. Issue #116 
	 * @author Gabriel Vila - 20/12/2012
	 * @see
	 * @param ctx
	 * @param get_ID
	 * @param parentID
	 * @param cElementValueID
	 * @param object
	 * @return
	 */
	public static MGerencialDetail forAccountID(Properties ctx, int uyGerencialID,	int parentID, 
				int cElementValueID, String trxName) {

		String whereClause = X_UY_Gerencial_Detail.COLUMNNAME_UY_Gerencial_ID + "=" + uyGerencialID +
				" AND " + X_UY_Gerencial_Detail.COLUMNNAME_Parent_ID + "=" + parentID +
				" AND " + X_UY_Gerencial_Detail.COLUMNNAME_C_ElementValue_ID + "=" + cElementValueID;

		MGerencialDetail value = new Query(ctx, I_UY_Gerencial_Detail.Table_Name, whereClause, trxName)
		.first();

		return value; 
		
	}
}
