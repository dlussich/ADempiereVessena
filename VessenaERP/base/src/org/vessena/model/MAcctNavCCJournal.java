package org.openup.model;

import java.util.Properties;

import org.compiere.model.Query;

public class MAcctNavCCJournal extends X_UY_AcctNavCC_Journal{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1725773542222125293L;

	public MAcctNavCCJournal(Properties ctx, int UY_AcctNavCC_Journal_ID,
			String trxName) {
		super(ctx, UY_AcctNavCC_Journal_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/***
	 * Obtiene y retorna modelo segun cuenta y asiento.
	 * OpenUp Ltda. Issue #2978
	 * @author Matias Carbajal - 12/11/2014
	 * @see
	 * @param ctx
	 * @param uyAcctNavCCID
	 * @param cBPartnerID
	 * @param trxName
	 * @return
	 */
	public static MAcctNavCCJournal forAccountJournal(Properties ctx, int uyAcctNavCCID,  int glJournalID, String trxName){
		
		String whereClause = X_UY_AcctNavCC_Journal.COLUMNNAME_UY_AcctNavCC_Journal_ID + "=" + uyAcctNavCCID +
						" AND " + X_UY_AcctNavCC_Journal.COLUMNNAME_GL_Journal_ID + "=" + glJournalID;
		
		MAcctNavCCJournal value = new Query(ctx, I_UY_AcctNavCC_Journal.Table_Name, whereClause, trxName).first();
		
		return value;
		
	}
}
