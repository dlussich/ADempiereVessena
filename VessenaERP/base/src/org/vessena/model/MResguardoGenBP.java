/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.compiere.model.Query;

/**
 * @author emiliano
 *
 */
public class MResguardoGenBP extends X_UY_ResguardoGenBP {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3512108443136874723L;

	/**
	 * @param ctx
	 * @param UY_ResguardoGenBP_ID
	 * @param trxName
	 */
	public MResguardoGenBP(Properties ctx, int UY_ResguardoGenBP_ID, String trxName) {
		super(ctx, UY_ResguardoGenBP_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MResguardoGenBP(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	/***
	 * Obtiene y retorna BP ids de la grilla para poder hacer el filtro al momento de crear Resguardos genericos.
	 * OpenUp Ltda. Issue #5144
	 * @author Emiliano Bentancor - 7/12/2015
	 * @see
	 * @return
	 */
	public static List<MResguardoGenBP> forResguardoGen(Properties ctx, int mResguardoGenID, String trxName){
		
		String whereClause = X_UY_ResguardoGenBP.COLUMNNAME_UY_ResguardoGen_ID  + "=" + mResguardoGenID +
				" AND " + X_UY_ResguardoGenBP.COLUMNNAME_IsActive + "='Y' ";
		
		List<MResguardoGenBP> lines = new Query(ctx, I_UY_ResguardoGenBP.Table_Name, whereClause, trxName)
		.list();
		
		return lines;
	}

}
