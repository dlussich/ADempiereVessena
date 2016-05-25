/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.compiere.model.Query;

/**
 * @author Hp
 *
 */
public class MCheckTracking extends X_UY_CheckTracking {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3777882971236844255L;

	/**
	 * @param ctx
	 * @param UY_CheckTracking_ID
	 * @param trxName
	 */
	public MCheckTracking(Properties ctx, int UY_CheckTracking_ID,
			String trxName) {
		super(ctx, UY_CheckTracking_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MCheckTracking(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	/***
	 * Obtiene y retorna modelo de linea segun parametros recibidos. 
	 * OpenUp Ltda. Issue #2359
	 * @author Nicolas Sarlabos - 09/12/2015
	 * @see
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static MCheckTracking forHdrLineDate(Properties ctx, int medioPagoID, int lineID, Timestamp date, String trxName){

		String whereClause = X_UY_CheckTracking.COLUMNNAME_UY_MediosPago_ID + " = " + medioPagoID + 
				" AND " + X_UY_CheckTracking.COLUMNNAME_UY_CheckTracking_ID + " > " + lineID +
				" AND " + X_UY_CheckTracking.COLUMNNAME_DateTrx + " <= '" + date + "'" +
				" AND " + X_UY_CheckTracking.COLUMNNAME_DocAction + " = 'CO'";

		MCheckTracking model = new Query(ctx, I_UY_CheckTracking.Table_Name, whereClause, trxName)
		.first();

		return model;
	}

}
