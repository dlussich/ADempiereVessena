/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 20/06/2013
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.Query;

/**
 * org.openup.model - MRAdjunto
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Hp - 20/06/2013
 * @see
 */
public class MRAdjunto extends X_UY_R_Adjunto {

	private static final long serialVersionUID = 4881105550276867848L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_R_Adjunto_ID
	 * @param trxName
	 */
	public MRAdjunto(Properties ctx, int UY_R_Adjunto_ID, String trxName) {
		super(ctx, UY_R_Adjunto_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRAdjunto(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	public static MRAdjunto forValue (Properties ctx, String value, String trxName){
		
		String whereClause = X_UY_R_Adjunto.COLUMNNAME_Value + "='" + value + "'";
		
		MRAdjunto model = new Query(ctx, I_UY_R_Adjunto.Table_Name, whereClause, trxName).first();
		
		return model;
		
	}
	
}
