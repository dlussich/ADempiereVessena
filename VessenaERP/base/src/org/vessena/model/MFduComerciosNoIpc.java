/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.Query;

/**
 * @author gbrust
 *
 */
public class MFduComerciosNoIpc extends X_UY_Fdu_ComerciosNoIpc {

	/**
	 * 
	 */
	private static final long serialVersionUID = -844478071204334027L;

	/**
	 * @param ctx
	 * @param UY_Fdu_ComerciosNoIpc_ID
	 * @param trxName
	 */
	public MFduComerciosNoIpc(Properties ctx, int UY_Fdu_ComerciosNoIpc_ID,
			String trxName) {
		super(ctx, UY_Fdu_ComerciosNoIpc_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MFduComerciosNoIpc(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public static MFduComerciosNoIpc getMFduComerciosNoIpcForValue(Properties ctx, String value){
		
		String whereClause = X_UY_Fdu_ComerciosNoIpc.COLUMNNAME_Value + " = '" + value + "'";
		
		return new Query(ctx, I_UY_Fdu_ComerciosNoIpc.Table_Name, whereClause, null).first();
				
	}

}
