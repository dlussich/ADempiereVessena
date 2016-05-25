/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.compiere.model.Query;

/**
 * @author Nicolás
 *
 */
public class MTRConfigMaintainLine extends X_UY_TR_ConfigMaintainLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param ctx
	 * @param UY_TR_ConfigMaintainLine_ID
	 * @param trxName
	 */
	public MTRConfigMaintainLine(Properties ctx, int UY_TR_ConfigMaintainLine_ID, String trxName) {
		super(ctx, UY_TR_ConfigMaintainLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTRConfigMaintainLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public static List<MTRConfigMaintainLine> getUserLines(Properties ctx, String trxName){

		List<MTRConfigMaintainLine> lines = new Query(ctx, I_UY_TR_ConfigMaintainLine.Table_Name, null, trxName)
		.setClient_ID()
		.list();

		return lines;
	}

}
