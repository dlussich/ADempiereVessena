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
public class MTRDriverCash extends X_UY_TR_DriverCash {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2934926629763001270L;

	public MTRDriverCash(Properties ctx, int UY_TR_DriverCash_ID, String trxName) {
		super(ctx, UY_TR_DriverCash_ID, trxName);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTRDriverCash(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	/***
	 * Obtiene y retorna modelo segun ID de chofer y moneda recibidos.
	 * OpenUp Ltda. Issue #4340 
	 * @author Nicolas Sarlabos - 11/06/2015
	 * @see
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static MTRDriverCash forDriverCurrency(Properties ctx, int driverID, int currID, String trxName){
		
		MTRDriverCash model = null;
		
		String sql = "select uy_tr_drivercash_id" +
		             " from uy_tr_drivercash cash" +
				     " inner join c_bankaccount ba on cash.c_bankaccount_id = ba.c_bankaccount_id" +
				     " where cash.uy_tr_driver_id = " + driverID +
				     " and ba.c_currency_id = " + currID;
		
		int modelID = DB.getSQLValueEx(null, sql);
		
		if(modelID > 0) model = new MTRDriverCash(ctx, modelID, null);
				
		return model;
	}

}
