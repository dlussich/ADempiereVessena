/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.Query;

/**OpenUp Ltda Issue#
 * @author SBT 15/12/2015
 *
 */
public class MRTConfig extends X_UY_RT_Config {

	/**
	 * @param ctx
	 * @param UY_RT_Config_ID
	 * @param trxName
	 */
	public MRTConfig(Properties ctx, int UY_RT_Config_ID, String trxName) {
		super(ctx, UY_RT_Config_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRTConfig(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	/**
	 * Obtiene y retorna modelo según value
	 * OpenUp Ltda Issue#
	 * @author Sylvie Bouissa 15/12/2015
	 * @param ctx
	 * @param valueIn
	 * @param trxName
	 * @return
	 */
	public static MRTConfig forValue(Properties ctx, String valueIn, String trxName) {
		String where = X_UY_RT_Config.COLUMNNAME_IsActive + " = 'Y'"
				+" AND "+ X_UY_RT_Config.COLUMNNAME_Value + " = '"+valueIn+"'";
		
		MRTConfig model = new Query(ctx,I_UY_RT_Config.Table_Name,where,trxName).first();
		if(null!=model && model.get_ID()>0){
			return model;
		}
		return null;
	}

}
