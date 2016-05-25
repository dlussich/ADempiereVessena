/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 18/12/2012
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MGerencial
 * OpenUp Ltda. Issue #116 
 * Description: Cabezal Filtros de Informe Gerencial Navagable
 * @author Gabriel Vila - 18/12/2012
 * @see
 */
public class MGerencial extends X_UY_Gerencial {

	/**
	 * 
	 */
	private static final long serialVersionUID = 764305216904285633L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_Gerencial_ID
	 * @param trxName
	 */
	public MGerencial(Properties ctx, int UY_Gerencial_ID, String trxName) {
		super(ctx, UY_Gerencial_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MGerencial(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

}
