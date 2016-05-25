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
public class MCashRemitSum extends X_UY_CashRemitSum {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2122826127572312098L;

	/**
	 * @param ctx
	 * @param UY_CashRemitSum_ID
	 * @param trxName
	 */
	public MCashRemitSum(Properties ctx, int UY_CashRemitSum_ID, String trxName) {
		super(ctx, UY_CashRemitSum_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MCashRemitSum(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	/***
	 * Obtiene y retorna modelo de linea segun ID de cabezal y moneda recibidos. 
	 * OpenUp Ltda. Issue #4449
	 * @author Nicolas Sarlabos - 24/07/2015
	 * @see
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static MCashRemitSum forHdrAndCurrency(Properties ctx, int hdrID, int currencyID, String trxName){

		MCashRemitSum model = null;
		
		String whereClause = X_UY_CashRemitSum.COLUMNNAME_UY_CashRemittance_ID + "=" + hdrID + 
				" and " + X_UY_CashRemitSum.COLUMNNAME_C_Currency_ID + "=" + currencyID;				

		model = new Query(ctx, I_UY_CashRemitSum.Table_Name, whereClause, trxName)
		.first();

		return model;
	}

}
