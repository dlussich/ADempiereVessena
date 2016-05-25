/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 20/09/2013
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.Query;

/**
 * org.openup.model - MTTLoadPlasticLine
 * OpenUp Ltda. Issue #1173 
 * Description: Modelo del linea de carga de plasticos desde proveedor.
 * @author Gabriel Vila - 20/09/2013
 * @see
 */
public class MTTLoadPlasticLine extends X_UY_TT_LoadPlasticLine {

	private static final long serialVersionUID = 8037696022096802964L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_TT_LoadPlasticLine_ID
	 * @param trxName
	 */
	public MTTLoadPlasticLine(Properties ctx, int UY_TT_LoadPlasticLine_ID,
			String trxName) {
		super(ctx, UY_TT_LoadPlasticLine_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTTLoadPlasticLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/***
	 * Obtiene y retorna modelo segun clave unica interna.
	 * OpenUp Ltda. Issue #1173 
	 * @author Gabriel Vila - 23/09/2013
	 * @see
	 * @param ctx
	 * @param internalCode
	 * @param tarjeta
	 * @param trxName
	 * @return
	 */
	public static MTTLoadPlasticLine forInternalKey(Properties ctx, String internalCode, String tarjeta, String trxName){
		
		String whereClause = X_UY_TT_LoadPlasticLine.COLUMNNAME_InternalCode + "='" + internalCode + "'" +
				" AND " + X_UY_TT_LoadPlasticLine.COLUMNNAME_NroTarjetaNueva + "='" + tarjeta + "'"; 
		
		MTTLoadPlasticLine model = new Query(ctx, I_UY_TT_LoadPlasticLine.Table_Name, whereClause, trxName).first();
		
		return model;
		
	}
	
}
