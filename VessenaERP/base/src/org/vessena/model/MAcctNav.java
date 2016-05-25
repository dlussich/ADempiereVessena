/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 23/11/2012
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.compiere.model.Query;

/**
 * org.openup.model - MAcctNav
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Hp - 23/11/2012
 * @see
 */
public class MAcctNav extends X_UY_AcctNav {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2063997216099204114L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_AcctNav_ID
	 * @param trxName
	 */
	public MAcctNav(Properties ctx, int UY_AcctNav_ID, String trxName) {
		super(ctx, UY_AcctNav_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MAcctNav(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	
	/***
	 * Obtiene y retorna lista de cuenta seleccionadas como filtro por el usuario.
	 * OpenUp Ltda. Issue #127 
	 * @author Gabriel Vila - 23/11/2012
	 * @see
	 * @return
	 */
	public List<MAcctNavAccount> getAccounts(){
		
		String whereClause = X_UY_AcctNav_Account.COLUMNNAME_UY_AcctNav_ID + "=" + get_ID();
		
		List<MAcctNavAccount> lines = new Query(getCtx(), I_UY_AcctNav_Account.Table_Name, whereClause, get_TrxName())
		.list();
		
		return lines;
	}
	
}
