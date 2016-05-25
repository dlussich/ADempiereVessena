/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 05/02/2013
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.compiere.model.Query;

/**
 * org.openup.model - MRCause
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Hp - 05/02/2013
 * @see
 */
public class MRCause extends X_UY_R_Cause {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2153635059016726765L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_R_Cause_ID
	 * @param trxName
	 */
	public MRCause(Properties ctx, int UY_R_Cause_ID, String trxName) {
		super(ctx, UY_R_Cause_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRCause(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/***
	 * Obtiene y retorna lista de subtemas de este tema de incidencia.
	 * OpenUp Ltda. Issue #281 
	 * @author Gabriel Vila - 17/06/2013
	 * @see
	 * @return
	 */
	public List<MRSubCause> getSubTemas(){
		
		String whereClause = X_UY_R_SubCause.COLUMNNAME_UY_R_Cause_ID + "=" + this.get_ID();
		
		List<MRSubCause> lines = new Query(getCtx(), I_UY_R_SubCause.Table_Name, whereClause, get_TrxName())
		.setOnlyActiveRecords(true).list();
		
		return lines;
		
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
	public static MRCause forValue(Properties ctx, String value, String trxName){
		
		String whereClause = X_UY_R_Cause.COLUMNNAME_Value + "='" + value + "'";
		
		MRCause model = new Query(ctx, I_UY_R_Cause.Table_Name, whereClause, trxName)
		.setClient_ID()
		.first();
		
		return model;
		
	}
	
	
	/***
	 * Retorna modelo segun tipo de incidencia padre y value recibido.
	 * OpenUp Ltda. Issue #1173 
	 * @author Gabriel Vila - 05/02/2014
	 * @see
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static MRCause forTypeAndValue(Properties ctx, int uyRTypeID, String value, String trxName){
		
		String whereClause = X_UY_R_Cause.COLUMNNAME_UY_R_Type_ID + "=" + uyRTypeID +  
				   " AND " + X_UY_R_Cause.COLUMNNAME_Value + "='" + value + "'";
		
		MRCause model = new Query(ctx, I_UY_R_Cause.Table_Name, whereClause, trxName)
		.first();
		
		return model;
		
	}
	
	public static MRCause forTypeAndName(Properties ctx, int uyRTypeID, String name, String trxName){
		
		String whereClause = X_UY_R_Cause.COLUMNNAME_UY_R_Type_ID + "=" + uyRTypeID +  
				   " AND " + X_UY_R_Cause.COLUMNNAME_Name + "='" + name + "'";
		
		MRCause model = new Query(ctx, I_UY_R_Cause.Table_Name, whereClause, trxName)
		.first();
		
		return model;
		
	}	
}
