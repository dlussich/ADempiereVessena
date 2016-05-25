/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 23/11/2014
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MFactWildCard
 * OpenUp Ltda. Issue #3315 
 * Description: Modelo para definicion de comodines de asientos tipo.
 * @author Gabriel Vila - 23/11/2014
 * @see
 */
public class MFactWildCard extends X_UY_FactWildCard {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7720138327177181862L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_FactWildCard_ID
	 * @param trxName
	 */
	public MFactWildCard(Properties ctx, int UY_FactWildCard_ID, String trxName) {
		super(ctx, UY_FactWildCard_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MFactWildCard(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

}
