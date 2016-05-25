/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.util.DB;

/**
 * @author Nicolas
 *
 */
public class MCashConfigChest extends X_UY_CashConfigChest {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1338560830721322122L;

	/**
	 * @param ctx
	 * @param UY_CashConfigChest_ID
	 * @param trxName
	 */
	public MCashConfigChest(Properties ctx, int UY_CashConfigChest_ID,
			String trxName) {
		super(ctx, UY_CashConfigChest_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MCashConfigChest(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	/***
	 * Obtiene y retorna modelo segun ID de moneda recibido. 
	 * OpenUp Ltda. Issue #4437
	 * @author Nicolas Sarlabos - 15/09/2015
	 * @see
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static MCashConfigChest forCurrency(Properties ctx, int currencyID, String trxName){

		MCashConfigChest model = null;
		
		String sql = "select l.uy_cashconfigchest_id" +
		             " from uy_cashconfigchest l" +
				     " inner join c_bankaccount ba on l.c_bankaccount_id = ba.c_bankaccount_id" +
				     " where ba.c_currency_id = " + currencyID;
		
		int modelID = DB.getSQLValueEx(trxName, sql);
		
		if(modelID > 0) model = new MCashConfigChest(ctx,modelID,trxName);

		return model;
	}

}
