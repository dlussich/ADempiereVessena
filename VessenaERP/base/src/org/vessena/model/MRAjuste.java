/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 01/04/2013
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.Query;



/**
 * org.openup.model - MRAjuste
 * OpenUp Ltda. Issue #285 
 * Description: Modelo de ajuste de reclamos.
 * @author Gabriel Vila - 01/04/2013
 * @see
 */
public class MRAjuste extends X_UY_R_Ajuste {

	private static final long serialVersionUID = -436890872498547385L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_R_Ajuste_ID
	 * @param trxName
	 */
	public MRAjuste(Properties ctx, int UY_R_Ajuste_ID, String trxName) {
		super(ctx, UY_R_Ajuste_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRAjuste(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	
	/***
	 * Obtiene y retorna Ajuste segun codigo financial recibido.
	 * OpenUp Ltda. Issue #657 
	 * @author Gabriel Vila - 09/04/2013
	 * @see
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static MRAjuste forFinancialCode(Properties ctx, String value, String trxName){
		
		String whereClause = X_UY_R_Ajuste.COLUMNNAME_financialcode + "='" + value + "'";
		
		MRAjuste model = new Query(ctx, I_UY_R_Ajuste.Table_Name, whereClause, trxName)
		.first();
				
		return model;
	}
}
