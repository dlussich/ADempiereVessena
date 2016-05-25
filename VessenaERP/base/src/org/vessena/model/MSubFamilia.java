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
 * @author Nicolas
 *
 */
public class MSubFamilia extends X_UY_SubFamilia {

	/**
	 * 
	 */
	private static final long serialVersionUID = -534644227617842132L;

	/**
	 * @param ctx
	 * @param UY_SubFamilia_ID
	 * @param trxName
	 */
	public MSubFamilia(Properties ctx, int UY_SubFamilia_ID, String trxName) {
		super(ctx, UY_SubFamilia_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MSubFamilia(Properties ctx, ResultSet rs, String trxName) {
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
	public static MSubFamilia forValue(Properties ctx, String value, String trxName)
	{
		if (Util.isEmpty(value, true))
		{
			return null;
		}
		
		MSubFamilia model = null;
		
		final String whereClause = "trim(lower(value))=? AND AD_Client_ID IN (?,?)"; 
		model = new Query(ctx, Table_Name, whereClause, trxName)
							.setParameters(new Object[]{value.toLowerCase().trim(), 0, Env.getAD_Client_ID(ctx)})
							.setOnlyActiveRecords(false)
							.first();
		return model;
	}
	
	/***
	 * Obtiene y retorna modelo segun seccion, rubro y famila recibidos.
	 * OpenUp Ltda. Issue #4392
	 * @author Nicolas Sarlabos - 21/09/2015
	 * @see
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static MSubFamilia forAttributes(Properties ctx, int seccionID, int rubroID, int familiaID, String trxName){
		
		MSubFamilia model = null;
		
		String sql = "select uy_subfamilia_id" +
		             " from uy_subfamilia" +
				     " where uy_linea_negocio_id = " + seccionID +
				     " and uy_productgroup_id = " + rubroID +
				     " and uy_familia_id = " + familiaID +
				     " and isactive = 'Y'";
		
		int ID = DB.getSQLValueEx(trxName, sql);
		
		if(ID > 0) model = new MSubFamilia(ctx, ID, trxName);
				
		return model;
	}

}
