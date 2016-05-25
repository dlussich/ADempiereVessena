/**
 * 
 */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * @author Nicolas
 *
 */
public class MJobCategory extends X_C_JobCategory {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5821515432083589155L;

	/**
	 * @param ctx
	 * @param C_JobCategory_ID
	 * @param trxName
	 */
	public MJobCategory(Properties ctx, int C_JobCategory_ID, String trxName) {
		super(ctx, C_JobCategory_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MJobCategory(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	/***
	 * Obtiene y retorna una categoria de posicion nombre recibido
	 * OpenUp Ltda. Issue #1760
	 * @author Nicolas Sarlabos - 29/04/2014
	 * @see
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static MJobCategory forCategoryName(Properties ctx, String name, String trxName){
		
		String whereClause = X_C_JobCategory.COLUMNNAME_Name + "='" + name + "'";
		
		MJobCategory cat = new Query(ctx, I_C_JobCategory.Table_Name, whereClause, trxName)
		.first();
				
		return cat;
	}

}
