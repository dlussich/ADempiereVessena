/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.Query;

/**
 * @author Nicolás
 *
 */
public class MTRConfigMaintain extends X_UY_TR_ConfigMaintain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param ctx
	 * @param UY_TR_ConfigMaintain_ID
	 * @param trxName
	 */
	public MTRConfigMaintain(Properties ctx, int UY_TR_ConfigMaintain_ID, String trxName) {
		super(ctx, UY_TR_ConfigMaintain_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTRConfigMaintain(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public static MTRConfigMaintain forClient(Properties ctx, String trxName){
		
		MTRConfigMaintain model = new Query(ctx, I_UY_TR_ConfigMaintain.Table_Name, null, trxName)
		.setClient_ID()
		.first();
		
		return model;
	}

}
