package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.Query;

public class MFduCargoImporte extends X_UY_Fdu_CargoImporte {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7114066665831936001L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_Fdu_CargoImporte_ID
	 * @param trxName
	 */
	public MFduCargoImporte(Properties ctx, int UY_Fdu_CargoImporte_ID, String trxName) {
		super(ctx, UY_Fdu_CargoImporte_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MFduCargoImporte(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	
	/***
	 * OpenUp. Guillermo Brust. 04/11/2013. ISSUE#
	 * Método que devuelve una instancia de éste modelo, a partir de los parametros pasados
	 * 
	 */
	public static MFduCargoImporte forCargoProductoAfinidad(Properties ctx, String trxName, int cargoID, int productoID, int afinidadID){
		
		String whereClause = X_UY_Fdu_CargoImporte.COLUMNNAME_UY_Fdu_Cargo_ID + " = " + cargoID +
						     " AND " + X_UY_Fdu_CargoImporte.COLUMNNAME_UY_Fdu_Productos_ID + " = " + productoID +
						     " AND " + X_UY_Fdu_CargoImporte.COLUMNNAME_UY_Fdu_Afinidad_ID + " = " + afinidadID;
		
		MFduCargoImporte model = new Query(ctx, I_UY_Fdu_CargoImporte.Table_Name, whereClause, trxName)
		.first();
		
		return model;
		
	}

}
