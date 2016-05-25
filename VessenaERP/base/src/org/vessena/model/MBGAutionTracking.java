/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MBGAutionTracking
 * OpenUp Ltda. Issue # 
 * Description: Modelo para tracking de registro de subastas
 * @author Andrea Odriozola - 26/06/2015
 * @see
 */

public class MBGAutionTracking extends X_UY_BG_AutionTracking {

	private static final long serialVersionUID = -4791025284314073087L;
	
	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_BG_AutionTracking_ID
	 * @param trxName
	 */

	public MBGAutionTracking(Properties ctx, int UY_BG_AutionTracking_ID,
			String trxName) {
		super(ctx, UY_BG_AutionTracking_ID, trxName);
		
	}
	
	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */

	
	public MBGAutionTracking(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		
	}

}
