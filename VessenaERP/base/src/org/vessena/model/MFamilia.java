/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Util;

/**
 * @author IN
 *
 */
public class MFamilia extends X_UY_Familia {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1724028453944533780L;

	/**
	 * @param ctx
	 * @param UY_Familia_ID
	 * @param trxName
	 */
	public MFamilia(Properties ctx, int UY_Familia_ID, String trxName) {
		super(ctx, UY_Familia_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MFamilia(Properties ctx, ResultSet rs, String trxName) {
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
	public static MFamilia forValue(Properties ctx, String value, String trxName)
	{
		if (Util.isEmpty(value, true))
		{
			return null;
		}
		
		MFamilia model = null;
		
		final String whereClause = "trim(lower(value))=? AND AD_Client_ID IN (?,?)"; 
		model = new Query(ctx, Table_Name, whereClause, trxName)
							.setParameters(new Object[]{value.toLowerCase().trim(), 0, Env.getAD_Client_ID(ctx)})
							.setOnlyActiveRecords(false)
							.first();
		return model;
	}
	
	/***
	 * Obtiene y retorna modelo segun seccion y rubro.
	 * OpenUp Ltda. Issue #4392
	 * @author Nicolas Sarlabos - 21/09/2015
	 * @see
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static MFamilia forAttributes(Properties ctx, int seccionID, int rubroID, String trxName){
		
		MFamilia model = null;
		
		String sql = "select uy_familia_id" +
		             " from uy_familia" +
				     " where uy_linea_negocio_id = " + seccionID +
				     " and uy_productgroup_id = " + rubroID +
				     " and isactive = 'Y'";
		
		int ID = DB.getSQLValueEx(trxName, sql);
		
		if(ID > 0) model = new MFamilia(ctx, ID, trxName);
				
		return model;
	}

}
