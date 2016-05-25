/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Nico - 04/05/2012
 */
 
package org.openup.model;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.compiere.model.Query;

/**
 * org.openup.model - MMarcasReloj
 * OpenUp Ltda. Issue #776 
 * Description: modelo para almacenar las marcas de reloj en la tabla uy_marcasreloj
 * @author Nico - 08/07/2011
 * @see
 */
public class MMarcasReloj extends X_UY_MarcasReloj {

	private static final long serialVersionUID = -3982634157805811562L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_MarcasReloj_ID
	 * @param trxName
	 */
	public MMarcasReloj(Properties ctx, int UY_MarcasReloj_ID, String trxName) {
		super(ctx, UY_MarcasReloj_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MMarcasReloj(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	/***
	 * Obtiene y retorna registro segun valores recibidos
	 * OpenUp Ltda. Issue #
	 * @author Nicolas Sarlabos - 30/11/2015
	 * @see
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static MMarcasReloj getRecord(Properties ctx, int partnerID, Timestamp date, String tarjeta, int marcaID, String trxName){
		
		String whereClause = X_UY_MarcasReloj.COLUMNNAME_C_BPartner_ID + " = " + partnerID + " AND " + X_UY_MarcasReloj.COLUMNNAME_fechahora + " = '" + date + "' AND " +
				X_UY_MarcasReloj.COLUMNNAME_nrotarjeta + " = '" + tarjeta + "' AND " + X_UY_MarcasReloj.COLUMNNAME_tipomarca_id + " = " + marcaID;
		
		MMarcasReloj rec = new Query(ctx, I_UY_MarcasReloj.Table_Name, whereClause, trxName)
		.setClient_ID()
		.first();
				
		return rec;
	}

}
