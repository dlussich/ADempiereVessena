/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 05/02/2013
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.Query;

/**
 * org.openup.model - MRSubCause
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Hp - 05/02/2013
 * @see
 */
public class MRSubCause extends X_UY_R_SubCause {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8529195913248107063L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_R_SubCause_ID
	 * @param trxName
	 */
	public MRSubCause(Properties ctx, int UY_R_SubCause_ID, String trxName) {
		super(ctx, UY_R_SubCause_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRSubCause(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	

	/***
	 * Retorna modelo segun value recibido.
	 * OpenUp Ltda. Issue #1173 
	 * @author Gabriel Vila - 12/09/2013
	 * @see
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static MRSubCause forValue(Properties ctx, String value, String trxName){
		
		String whereClause = X_UY_R_SubCause.COLUMNNAME_Value + "='" + value + "'";
		
		MRSubCause model = new Query(ctx, I_UY_R_SubCause.Table_Name, whereClause, trxName)
		.setClient_ID()
		.first();
		
		return model;
		
	}	
	
	public static MRSubCause forCauseAndName(Properties ctx, int uyRCauseID, String name, String trxName){
		
		String whereClause = X_UY_R_SubCause.COLUMNNAME_UY_R_Cause_ID + "=" + uyRCauseID +  
				   " AND " + X_UY_R_SubCause.COLUMNNAME_Name + "='" + name + "'";
		
		MRSubCause model = new Query(ctx, I_UY_R_SubCause.Table_Name, whereClause, trxName)
		.first();
		
		return model;
		
	}	
	
}
