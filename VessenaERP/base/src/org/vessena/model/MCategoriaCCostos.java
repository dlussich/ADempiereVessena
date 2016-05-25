/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 18/09/2012
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MCategoriaCCostos
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Hp - 18/09/2012
 * @see
 */
public class MCategoriaCCostos extends X_UY_Categoria_CCostos {

	private static final long serialVersionUID = 3658731303217627802L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_Categoria_CCostos_ID
	 * @param trxName
	 */
	public MCategoriaCCostos(Properties ctx, int UY_Categoria_CCostos_ID,
			String trxName) {
		super(ctx, UY_Categoria_CCostos_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MCategoriaCCostos(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
