/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.Query;

/**OpenUp Ltda Issue#
 * @author SBouissa 07/05/2015
 *
 */
public class MRTCodigoMedioPago extends X_UY_RT_CodigoMedioPago {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3085163037409849589L;

	/**
	 * @param ctx
	 * @param UY_RT_CodigoMedioPago_ID
	 * @param trxName
	 */
	public MRTCodigoMedioPago(Properties ctx, int UY_RT_CodigoMedioPago_ID,
			String trxName) {
		super(ctx, UY_RT_CodigoMedioPago_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRTCodigoMedioPago(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * OpenUp Ltda Issue#
	 * @author Sylvie Bouissa 07/05/2015
	 * @param ctx
	 * @param string
	 * @param get_TrxName
	 * @return
	 */
	public static MRTCodigoMedioPago forValue(Properties ctx, String value, String trxName){
		
		String whereClause = X_UY_RT_CodigoMedioPago.COLUMNNAME_Value + "='" + value + "'";
		
		MRTCodigoMedioPago model = new Query(ctx, X_UY_RT_CodigoMedioPago.Table_Name, whereClause, trxName)
		.first();
		
		
		return model;
	}

}
