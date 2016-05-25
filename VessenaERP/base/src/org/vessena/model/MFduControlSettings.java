/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * @author gbrust
 *
 */
public class MFduControlSettings extends X_UY_Fdu_ControlSettings {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9039014070167143650L;

	/**
	 * @param ctx
	 * @param UY_Fdu_ControlSettings_ID
	 * @param trxName
	 */
	public MFduControlSettings(Properties ctx, int UY_Fdu_ControlSettings_ID,
			String trxName) {
		super(ctx, UY_Fdu_ControlSettings_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MFduControlSettings(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
