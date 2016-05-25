/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.Query;

/**
 * @author SBouissa
 *
 */
public class MCovCodigoServicios extends X_Cov_CodigoServicios {

	/**
	 * @param ctx
	 * @param Cov_CodigoServicios_ID
	 * @param trxName
	 */
	public MCovCodigoServicios(Properties ctx, int Cov_CodigoServicios_ID,
			String trxName) {
		super(ctx, Cov_CodigoServicios_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MCovCodigoServicios(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * OpenUp Ltda Issue#
	 * @author Sylvie Bouissa 29/04/2015
	 * @param ctx
	 * @param string
	 * @param get_TrxName
	 * @return
	 */
	public static MCovCodigoServicios forValue(Properties ctx, String value,
			String trxName) {
		String whereClause = X_Cov_CodigoServicios.COLUMNNAME_Value + " = '" + value + "'" ;
		
		MCovCodigoServicios model = new Query(ctx, I_Cov_CodigoServicios.Table_Name, whereClause, trxName)
		.first();
		
		return model;
	}

}
