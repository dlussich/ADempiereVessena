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
public class MFduCargoModelo extends X_UY_Fdu_CargoModelo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3012636430391338520L;

	/**
	 * @param ctx
	 * @param UY_Fdu_CargoModelo_ID
	 * @param trxName
	 */
	public MFduCargoModelo(Properties ctx, int UY_Fdu_CargoModelo_ID,
			String trxName) {
		super(ctx, UY_Fdu_CargoModelo_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MFduCargoModelo(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	
	/***
	 * OpenUp. Guillermo Brust. 20/11/2013. ISSUE#
	 * Método que devuelve una instancia de éste modelo, a partir de cargo y un modelo de liquidacion
	 * 
	 */
	public static MFduCargoModelo forCargoAndModelo(Properties ctx, String trxName, int cargoID, int modeloLiquidacionID){
		
		String whereClause = X_UY_Fdu_CargoModelo.COLUMNNAME_UY_Fdu_Cargo_ID + " = " + cargoID +
						     " AND " + X_UY_Fdu_CargoModelo.COLUMNNAME_UY_Fdu_ModeloLiquidacion_ID + " = " + modeloLiquidacionID;
		
		MFduCargoModelo model = new Query(ctx, I_UY_Fdu_CargoModelo.Table_Name, whereClause, trxName)
		.first();
		
		return model;
		
	}

}
