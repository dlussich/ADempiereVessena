/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 03/09/2014
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MPRSchedule
 * OpenUp Ltda. Issue #2167 
 * Description: Modelo de cronograma.
 * @author Gabriel Vila - 03/09/2014
 * @see
 */
public class MPRSchedule extends X_UY_PR_Schedule {

	private static final long serialVersionUID = 2724862260501255275L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_PR_Schedule_ID
	 * @param trxName
	 */
	public MPRSchedule(Properties ctx, int UY_PR_Schedule_ID, String trxName) {
		super(ctx, UY_PR_Schedule_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MPRSchedule(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

}
