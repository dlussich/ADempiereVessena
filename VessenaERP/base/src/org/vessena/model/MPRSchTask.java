/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 03/09/2014
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MPRSchTask
 * OpenUp Ltda. Issue #2167 
 * Description: Modelo para asociacion de tarea con cronograma
 * @author Gabriel Vila - 03/09/2014
 * @see
 */
public class MPRSchTask extends X_UY_PR_SchTask {

	private static final long serialVersionUID = -4309362694462820578L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_PR_SchTask_ID
	 * @param trxName
	 */
	public MPRSchTask(Properties ctx, int UY_PR_SchTask_ID, String trxName) {
		super(ctx, UY_PR_SchTask_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MPRSchTask(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

}
