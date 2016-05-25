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
public class MTRBorder extends X_UY_TR_Border {

	/**
	 * 
	 */
	private static final long serialVersionUID = 258800795389620779L;

	/**
	 * @param ctx
	 * @param UY_TR_Border_ID
	 * @param trxName
	 */
	public MTRBorder(Properties ctx, int UY_TR_Border_ID, String trxName) {
		super(ctx, UY_TR_Border_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTRBorder(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	/***
	 * Obtiene y retorna modelo segun nombre recibido.
	 * OpenUp Ltda. Issue #5505
	 * @author Nicolas Sarlabos - 06/04/2016
	 * @see
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static MTRBorder forName(Properties ctx, String name, String trxName){
		
		String whereClause = X_UY_TR_Border.COLUMNNAME_Name + "='" + name + "'";
		
		MTRBorder border = new Query(ctx, I_UY_TR_Border.Table_Name, whereClause, trxName)
		.setClient_ID()
		.first();
				
		return border;
	}

}
