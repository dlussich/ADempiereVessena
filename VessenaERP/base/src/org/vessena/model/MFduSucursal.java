/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Formatter;
import java.util.Properties;

import org.compiere.model.Query;

/**
 * @author gbrust
 *
 */
public class MFduSucursal extends X_UY_Fdu_Sucursal {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1057797225346115095L;

	/**
	 * @param ctx
	 * @param UY_Fdu_Sucursal_ID
	 * @param trxName
	 */
	public MFduSucursal(Properties ctx, int UY_Fdu_Sucursal_ID, String trxName) {
		super(ctx, UY_Fdu_Sucursal_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MFduSucursal(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	public static MFduSucursal forValue(Properties ctx, Formatter fmt, String trxName){
		
		String whereClause = X_UY_Fdu_Sucursal.COLUMNNAME_Value + "='" + fmt + "'";
		
		MFduSucursal ccosto = new Query(ctx, I_UY_Fdu_Sucursal.Table_Name, whereClause, trxName)
		.first();
				
		return ccosto;
	}

}
