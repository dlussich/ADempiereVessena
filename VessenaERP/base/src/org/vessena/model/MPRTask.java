/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 03/09/2014
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MPRTask
 * OpenUp Ltda. Issue #2167 
 * Description: Modelo de tarea de cronograma.
 * @author Gabriel Vila - 03/09/2014
 * @see
 */
public class MPRTask extends X_UY_PR_Task {

	private static final long serialVersionUID = -866255316800264947L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_PR_Task_ID
	 * @param trxName
	 */
	public MPRTask(Properties ctx, int UY_PR_Task_ID, String trxName) {
		super(ctx, UY_PR_Task_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MPRTask(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

}
