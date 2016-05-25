/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * @author SBouissa
 *
 */
public class MLogFile extends X_UY_LogFile {

	/**
	 * @param ctx
	 * @param UY_LogFile_ID
	 * @param trxName
	 */
	public MLogFile(Properties ctx, int UY_LogFile_ID, String trxName) {
		super(ctx, UY_LogFile_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MLogFile(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
