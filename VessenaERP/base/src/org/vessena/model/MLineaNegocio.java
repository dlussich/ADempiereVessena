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
public class MLineaNegocio extends X_UY_Linea_Negocio {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6341181547284462230L;

	/**
	 * @param ctx
	 * @param UY_Linea_Negocio_ID
	 * @param trxName
	 */
	public MLineaNegocio(Properties ctx, int UY_Linea_Negocio_ID, String trxName) {
		super(ctx, UY_Linea_Negocio_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MLineaNegocio(Properties ctx, ResultSet rs, String trxName) {
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
	public static MLineaNegocio forValue(Properties ctx, String value, String trxName)
	{
		if (Util.isEmpty(value, true))
		{
			return null;
		}
		
		MLineaNegocio model = null;
		
		final String whereClause = "trim(lower(value))=? AND AD_Client_ID IN (?,?)"; 
		model = new Query(ctx, Table_Name, whereClause, trxName)
							.setParameters(new Object[]{value.toLowerCase().trim(), 0, Env.getAD_Client_ID(ctx)})
							.setOnlyActiveRecords(false)
							.first();
		return model;
	}

}
