/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 05/06/2013
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MRComercio
 * OpenUp Ltda. Issue #285 
 * Description: Modelo de comercios para gestion de incidencias.
 * @author Gabriel Vila - 05/06/2013
 * @see
 */
public class MRComercio extends X_UY_R_Comercio {

	private static final long serialVersionUID = -6831640509852123177L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_R_Comercio_ID
	 * @param trxName
	 */
	public MRComercio(Properties ctx, int UY_R_Comercio_ID, String trxName) {
		super(ctx, UY_R_Comercio_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRComercio(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

}
