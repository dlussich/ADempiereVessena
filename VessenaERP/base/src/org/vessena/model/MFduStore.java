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
public class MFduStore extends X_UY_Fdu_Store {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5742767458043132223L;

	/**
	 * @param ctx
	 * @param UY_Fdu_Store_ID
	 * @param trxName
	 */
	public MFduStore(Properties ctx, int UY_Fdu_Store_ID, String trxName) {
		super(ctx, UY_Fdu_Store_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MFduStore(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	
	public static MFduStore getStoreForValue(Properties ctx, String codigoComercio){
		
		String whereClause = I_UY_Fdu_Store.COLUMNNAME_Value + " = '" + codigoComercio + "'";
		
		MFduStore store = new Query(ctx, I_UY_Fdu_Store.Table_Name, whereClause, null).firstOnly();
		
		
		return store;
	}
	
	

	

}
