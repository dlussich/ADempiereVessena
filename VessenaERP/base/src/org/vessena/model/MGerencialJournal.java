/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 21/12/2012
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.Query;

/**
 * org.openup.model - MGerencialJournal
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Hp - 21/12/2012
 * @see
 */
public class MGerencialJournal extends X_UY_Gerencial_Journal {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1401879311530047535L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_Gerencial_Journal_ID
	 * @param trxName
	 */
	public MGerencialJournal(Properties ctx, int UY_Gerencial_Journal_ID,
			String trxName) {
		super(ctx, UY_Gerencial_Journal_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MGerencialJournal(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/***
	 * Obtiene y retorna modelo segun cuenta y asiento.
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
	public static MGerencialJournal forAccountJournal(Properties ctx, int uyGerencialID, int accountID, int glJournalID, String trxName){
		
		String whereClause = X_UY_Gerencial_Journal.COLUMNNAME_UY_Gerencial_ID + "=" + uyGerencialID +
						" AND " + X_UY_Gerencial_Journal.COLUMNNAME_Parent_ID + "=" + accountID +
						" AND " + X_UY_Gerencial_Journal.COLUMNNAME_GL_Journal_ID + "=" + glJournalID;
		
		MGerencialJournal value = new Query(ctx, I_UY_Gerencial_Journal.Table_Name, whereClause, trxName)
		.first();
		
		return value;
		
	}
	
}
