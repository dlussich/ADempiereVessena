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
public class MFduConnectionData extends X_UY_Fdu_ConnectionData {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8519887913043054798L;

	/**
	 * @param ctx
	 * @param UY_Fdu_ConnectionData_ID
	 * @param trxName
	 */
	public MFduConnectionData(Properties ctx, int UY_Fdu_ConnectionData_ID,
			String trxName) {
		super(ctx, UY_Fdu_ConnectionData_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MFduConnectionData(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public static MFduConnectionData forFduFileID(Properties ctx, int uyFduFileID, String trxName){
		
		String whereClause = X_UY_Fdu_ConnectionData.COLUMNNAME_UY_FduFile_ID + "=" + uyFduFileID;
		
		MFduConnectionData value = new Query(ctx, I_UY_Fdu_ConnectionData.Table_Name, whereClause, trxName)		
		.first();
		
		return value;
		
	}
	
}
