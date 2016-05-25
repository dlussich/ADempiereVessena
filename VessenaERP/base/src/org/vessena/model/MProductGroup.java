/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.Query;
import org.compiere.util.Env;
import org.compiere.util.Util;

/**
 * @author Nicolas
 *
 */
public class MProductGroup extends X_UY_ProductGroup {

	/**
	 * 
	 */
	private static final long serialVersionUID = -724729976441390099L;

	/**
	 * @param ctx
	 * @param UY_ProductGroup_ID
	 * @param trxName
	 */
	public MProductGroup(Properties ctx, int UY_ProductGroup_ID, String trxName) {
		super(ctx, UY_ProductGroup_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MProductGroup(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	/***
	 * Obtiene modelo segun un determinado value recibido.
	 * OpenUp Ltda. Issue #4392
	 * @author Nicolas Sarlabos - 18/09/2015
	 * @see
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static MProductGroup forValue(Properties ctx, String value, String trxName)
	{
		if (Util.isEmpty(value, true))
		{
			return null;
		}
		
		MProductGroup model = null;
		
		final String whereClause = "trim(lower(value))=? AND AD_Client_ID IN (?,?)"; 
		model = new Query(ctx, Table_Name, whereClause, trxName)
							.setParameters(new Object[]{value.toLowerCase().trim(), 0, Env.getAD_Client_ID(ctx)})
							.setOnlyActiveRecords(false)
							.first();
		return model;
	}
	
	/**
	 * OpenUp Ltda Issue#
	 * @author SBouissa 7/9/2015
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static MProductGroup forSection(Properties ctx, int sectionID, String trxName) {
		
		String whereClause = X_UY_ProductGroup.COLUMNNAME_UY_Linea_Negocio_ID + " = " + sectionID +													
				" AND " + X_UY_ProductGroup.COLUMNNAME_IsActive + " = 'Y'";

		MProductGroup model = new Query(ctx, I_UY_ProductGroup.Table_Name, whereClause, trxName).first();		

		return model;	
	}

}
