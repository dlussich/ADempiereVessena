package org.openup.model;

import java.util.Properties;

import org.compiere.model.Query;

/**
 * org.openup.model - MGerencialBP OpenUp Ltda. Issue #2978 Description:
 * Navegador contable Socio de Negocio
 * 
 * @author Matias Carbajal - 12/11/2014
 * @see
 */

public class MAcctNavCCBP extends X_UY_AcctNavCC_BP {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6908916838633300272L;

	public MAcctNavCCBP(Properties ctx, int UY_AcctNavCC_BP_ID, String trxName) {
		super(ctx, UY_AcctNavCC_BP_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/***
	 * Obtiene y retorna modelo segun cuenta y socio de negocio. OpenUp Ltda.
	 * Issue #2978
	 * 
	 * @author Matias Carbajal - 12/11/2014
	 * @see
	 * @param ctx
	 * @param uyAcctNavCCBPID
	 * @param cBPartnerID
	 * @param trxName
	 * @return
	 */
	public static MAcctNavCCBP forAccountBP(Properties ctx,
			int uyAcctNavCCBPID,  int accountID, int cBPartnerID, String trxName) {

		String whereClause = X_UY_AcctNavCC_BP.COLUMNNAME_UY_AcctNavCC_BP_ID + "=" + uyAcctNavCCBPID +
				" AND " + X_UY_AcctNavCC_BP.COLUMNNAME_Parent_ID + "=" + accountID +
				" AND " + X_UY_AcctNavCC_BP.COLUMNNAME_C_BPartner_ID + "=" + cBPartnerID;
		
		MAcctNavCCBP value = new Query(ctx, I_UY_AcctNavCC_BP.Table_Name,
				whereClause, trxName).setClient_ID().first();

		return value;

	}

}
