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
public class MFduProductos extends X_UY_Fdu_Productos {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6117615290548419743L;

	/**
	 * @param ctx
	 * @param UY_Fdu_Productos_ID
	 * @param trxName
	 */
	public MFduProductos(Properties ctx, int UY_Fdu_Productos_ID, String trxName) {
		super(ctx, UY_Fdu_Productos_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MFduProductos(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public static MFduProductos getMFduProductosForValue(Properties ctx, String value){
		
		String whereClause = X_UY_Fdu_Productos.COLUMNNAME_Value + " = '" + value + "' and " + X_UY_Fdu_Productos.COLUMNNAME_UY_FduFile_ID + " = 1000008";
		
		return new Query(ctx, I_UY_Fdu_Productos.Table_Name, whereClause, null).first();
				
	}

}
