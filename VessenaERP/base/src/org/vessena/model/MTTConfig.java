/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.compiere.model.Query;

/**
 * @author gbrust
 *
 */
public class MTTConfig extends X_UY_TT_Config {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1246085890604730725L;

	/**
	 * @param ctx
	 * @param UY_TT_Config_ID
	 * @param trxName
	 */
	public MTTConfig(Properties ctx, int UY_TT_Config_ID, String trxName) {
		super(ctx, UY_TT_Config_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTTConfig(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	

	/***
	 * Obtiene modelo segun value recibido.
	 * OpenUp Ltda. Issue #1173 
	 * @author Gabriel Vila - 03/09/2013
	 * @see
	 * @param ctx
	 * @param trxName
	 * @param value
	 * @return
	 */
	public static MTTConfig forValue(Properties ctx, String trxName, String value){
		
		String whereClause = X_UY_TT_Config.COLUMNNAME_Value + " = '" + value + "'" ;
							
		MTTConfig model = new Query(ctx, I_UY_TT_Config.Table_Name, whereClause, trxName)
		.first();
		
		return model;		
	}

	/***
	 * Obtiene y retorna lineas de SMS para los parametros actuales.
	 * OpenUp Ltda. Issue #1170
	 * @author Nicolas Sarlabos - 22/10/2013
	 * @see
	 * @return
	 */
	public List<MTTConfigCardSMS> getSMSLines(){

		String whereClause = X_UY_TT_ConfigCardSMS.COLUMNNAME_UY_TT_Config_ID + "=" + this.get_ID();

		List<MTTConfigCardSMS> lines = new Query(getCtx(), I_UY_TT_ConfigCardSMS.Table_Name, whereClause, get_TrxName())
		.list();

		return lines;
	}

	/***
	 * Obtiene modelo de necesidades segun tipo de necesidad desde la configuracion general.
	 * OpenUp Ltda. Issue #
	 * @author gabriel - Aug 27, 2015
	 * @param cardAction
	 * @return
	 */
	public MTTConfigNeed getNeedForCardAction(String cardAction){
		
		String whereClause = X_UY_TT_ConfigNeed.COLUMNNAME_UY_TT_Config_ID + "=" + this.get_ID() +
						" and " + X_UY_TT_ConfigNeed.COLUMNNAME_CardAction + "='" + cardAction + "'"; 
							
		MTTConfigNeed model = new Query(getCtx(), I_UY_TT_ConfigNeed.Table_Name, whereClause, get_TrxName())
		.first();
		
		return model;		
	}

}
