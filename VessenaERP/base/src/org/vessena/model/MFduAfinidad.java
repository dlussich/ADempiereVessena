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
public class MFduAfinidad extends X_UY_Fdu_Afinidad {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5566577596997926176L;

	/**
	 * @param ctx
	 * @param UY_Fdu_Afinidad_ID
	 * @param trxName
	 */
	public MFduAfinidad(Properties ctx, int UY_Fdu_Afinidad_ID, String trxName) {
		super(ctx, UY_Fdu_Afinidad_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MFduAfinidad(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	
	public static MFduAfinidad getMFduAfinidadForValue(Properties ctx, String value){
		
		String whereClause = X_UY_Fdu_Afinidad.COLUMNNAME_Value + " = '" + value + "' and " + X_UY_Fdu_Afinidad.COLUMNNAME_UY_FduFile_ID + " = 1000008";
		
		return new Query(ctx, I_UY_Fdu_Afinidad.Table_Name, whereClause, null).first();
				
	}

}
