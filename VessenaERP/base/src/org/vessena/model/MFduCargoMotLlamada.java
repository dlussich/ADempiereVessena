/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.compiere.model.Query;

/**
 * @author gbrust
 *
 */
public class MFduCargoMotLlamada extends X_UY_Fdu_CargoMotLlamada {

	/**
	 * 
	 */
	private static final long serialVersionUID = 420380336442726956L;

	/**
	 * @param ctx
	 * @param UY_Fdu_CargoMotLlamada_ID
	 * @param trxName
	 */
	public MFduCargoMotLlamada(Properties ctx, int UY_Fdu_CargoMotLlamada_ID,
			String trxName) {
		super(ctx, UY_Fdu_CargoMotLlamada_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MFduCargoMotLlamada(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	/***
	 * OpenUp. Guillermo Brust. 22/11/2013. ISSUE#
	 * Método que devuelve una lista de motivos de llamadas a fdu, los cuales no se aplican el cargo, dado el cargo que se pase por parametro
	 * 
	 * */
	public static List<MFduCargoMotLlamada> forCargoID(Properties ctx, int cargoID){
		
		String whereClause = X_UY_Fdu_CargoMotLlamada.COLUMNNAME_UY_Fdu_Cargo_ID + " = " + cargoID;
		
		return new Query(ctx, I_UY_Fdu_CargoMotLlamada.Table_Name, whereClause, null).list();
				
	}

}
