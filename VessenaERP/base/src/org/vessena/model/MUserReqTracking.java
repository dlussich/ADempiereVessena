/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 27/05/2015
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MUserReqTracking
 * OpenUp Ltda. Issue #4168 
 * Description: Modelo para trazabilidad de solicitudes de registro de usuario.
 * @author Gabriel Vila - 27/05/2015
 * @see
 */
public class MUserReqTracking extends X_UY_UserReqTracking {

	private static final long serialVersionUID = 1709179034361966930L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_UserReqTracking_ID
	 * @param trxName
	 */
	public MUserReqTracking(Properties ctx, int UY_UserReqTracking_ID,
			String trxName) {
		super(ctx, UY_UserReqTracking_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUserReqTracking(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

}
