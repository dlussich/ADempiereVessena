/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Nico - 10/03/2014
 */
 
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.Query;


/**
 * org.openup.model - MFduCajaType
 * OpenUp Ltda. Issue # 1932
 * Description: Creada para manejar la Clasificaciones de los Movimientos diarios de las Cajas Italcred
 * @author Leonardo Boccone - 10/03/2014
 * @see
 */
public class MFduCajaType extends X_UY_Fdu_Caja_Type {

	private static final long serialVersionUID = 2132053295505779725L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_Fdu_Caja_Type_ID
	 * @param trxName
	 */
	public MFduCajaType(Properties ctx, int UY_Fdu_Caja_Type_ID, String trxName) {
		super(ctx, UY_Fdu_Caja_Type_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MFduCajaType(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	public static MFduCajaType forName(Properties ctx, String name, String trxName){
		
		String whereClause = X_UY_Fdu_Caja_Type.COLUMNNAME_Name + "='" + name + "'";
		
		MFduCajaType type = new Query(ctx, I_UY_Fdu_Caja_Type.Table_Name, whereClause, trxName)
		.first();
				
		return type;
	}

}
