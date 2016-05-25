/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 15/07/2013
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MRDerivado
 * OpenUp Ltda. Issue #281 
 * Description: Modelo para tarjetas derivadas de otras.
 * @author Gabriel Vila - 15/07/2013
 * @see
 */
public class MRDerivado extends X_UY_R_Derivado {

	private static final long serialVersionUID = -9209586784747026318L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_R_Derivado_ID
	 * @param trxName
	 */
	public MRDerivado(Properties ctx, int UY_R_Derivado_ID, String trxName) {
		super(ctx, UY_R_Derivado_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRDerivado(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

}
