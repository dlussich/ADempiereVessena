/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 14/07/2014
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.compiere.model.Query;

/**
 * org.openup.model - MLoadRate
 * OpenUp Ltda. Issue #1918 
 * Description: 
 * @author Gabriel Vila - 14/07/2014
 * @see
 */
public class MLoadRate extends X_UY_LoadRate {

	private static final long serialVersionUID = -5561954369898864396L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_LoadRate_ID
	 * @param trxName
	 */
	public MLoadRate(Properties ctx, int UY_LoadRate_ID, String trxName) {
		super(ctx, UY_LoadRate_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MLoadRate(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	
	/***
	 * Obtiene y retorna lineas de parametrizacion de carga de TC.
	 * OpenUp Ltda. Issue #1918 
	 * @author Gabriel Vila - 14/07/2014
	 * @see
	 * @param ctx
	 * @param trxName
	 * @return
	 */
	public static List<MLoadRateLine> getLines(Properties ctx, String trxName){
		
		String whereClause = "";
		
		List<MLoadRateLine> lines = new Query(ctx, I_UY_LoadRateLine.Table_Name, whereClause, trxName).setOnlyActiveRecords(true).list();
		
		return lines;
		
	}
	
}
