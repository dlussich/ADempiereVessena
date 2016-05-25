/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.Query;

/**
 * @author gbrust
 *
 */
public class MFduModeloLiquidacion extends X_UY_Fdu_ModeloLiquidacion {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8748843515201493770L;

	/**
	 * @param ctx
	 * @param UY_Fdu_ModeloLiquidacion_ID
	 * @param trxName
	 */
	public MFduModeloLiquidacion(Properties ctx,
			int UY_Fdu_ModeloLiquidacion_ID, String trxName) {
		super(ctx, UY_Fdu_ModeloLiquidacion_ID, trxName);
		
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MFduModeloLiquidacion(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		
	}
	
	public static MFduModeloLiquidacion getMFduModeloLiquidacionForValue(Properties ctx, String trxName, String value){
		
		String whereClause = X_UY_Fdu_ModeloLiquidacion.COLUMNNAME_Value + " = '" + value.toUpperCase() + "'" +
							" AND " + X_UY_Fdu_ModeloLiquidacion.COLUMNNAME_IsActive + " = 'Y'";
		
		MFduModeloLiquidacion model = new Query(ctx, I_UY_Fdu_ModeloLiquidacion.Table_Name, whereClause, trxName)
		.first();
		
		
		return model;		
	}

}
