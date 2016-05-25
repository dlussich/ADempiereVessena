/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.Query;

/**
 * @author Nicolas
 *
 */
public class MProdConfigLine extends X_UY_ProdConfigLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8751935919010060338L;

	/**
	 * @param ctx
	 * @param UY_ProdConfigLine_ID
	 * @param trxName
	 */
	public MProdConfigLine(Properties ctx, int UY_ProdConfigLine_ID, String trxName) {
		super(ctx, UY_ProdConfigLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MProdConfigLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	/***
	 * Obtiene y retorna modelo segun id de documento recibido. 
	 * OpenUp Ltda. Issue #4125
	 * @author Nicolas Sarlabos - 30/10/2015
	 * @see
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static MProdConfigLine forDoc(Properties ctx, int docID, String trxName){
		
		String whereClause = X_UY_ProdConfigLine.COLUMNNAME_C_DocType_ID + "=" + docID;
		
		MProdConfigLine model = new Query(ctx, I_UY_ProdConfigLine.Table_Name, whereClause, trxName)
		.setClient_ID()
		.first();
		
		return model;
	}

}
