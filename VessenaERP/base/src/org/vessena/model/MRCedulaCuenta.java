/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 03/07/2013
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.Query;

/**
 * org.openup.model - MRCedulaCuenta
 * OpenUp Ltda. Issue #1112. 
 * Description: Modelo de numeros de cuenta por cedula de la base de Financial.
 * @author Hp - 03/07/2013
 * @see
 */
public class MRCedulaCuenta extends X_UY_R_CedulaCuenta {

	private static final long serialVersionUID = 7890622192447366240L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_R_CedulaCuenta_ID
	 * @param trxName
	 */
	public MRCedulaCuenta(Properties ctx, int UY_R_CedulaCuenta_ID,
			String trxName) {
		super(ctx, UY_R_CedulaCuenta_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRCedulaCuenta(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/***
	 * Obtiene y retorna modelo para una determinada cedula y cuenta.
	 * OpenUp Ltda. Issue #281 
	 * @author Gabriel Vila - 15/07/2013
	 * @see
	 * @param ctx
	 * @param cedula
	 * @param accountNo
	 * @param trxName
	 * @return
	 */
	public static MRCedulaCuenta forCedulaCuenta(Properties ctx, String cedula, String accountNo, String trxName){
		
		String whereClause = X_UY_R_CedulaCuenta.COLUMNNAME_Value + "='" + cedula + "' " +
		" AND " + X_UY_R_CedulaCuenta.COLUMNNAME_AccountNo + "='" + accountNo + "'"; 
		
		MRCedulaCuenta model = new Query(ctx, I_UY_R_CedulaCuenta.Table_Name, whereClause, trxName)
		.first();
		
		return model;
		
	}
}


