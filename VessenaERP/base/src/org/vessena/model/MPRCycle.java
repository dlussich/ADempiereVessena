/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 04/09/2014
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MPRCycle
 * OpenUp Ltda. Issue #2167 
 * Description: Modelo de ciclo de cronograma
 * @author Gabriel Vila - 04/09/2014
 * @see
 */
public class MPRCycle extends X_UY_PR_Cycle {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4828909943723130966L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_PR_Cycle_ID
	 * @param trxName
	 */
	public MPRCycle(Properties ctx, int UY_PR_Cycle_ID, String trxName) {
		super(ctx, UY_PR_Cycle_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MPRCycle(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

}
