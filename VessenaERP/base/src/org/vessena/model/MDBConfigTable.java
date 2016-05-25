/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 11/11/2013
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MDBConfigTable
 * OpenUp Ltda. Issue #1539 
 * Description: Modelo de configuracion de tabla de base de datos
 * @author Gabriel Vila - 11/11/2013
 * @see
 */
public class MDBConfigTable extends X_UY_DB_ConfigTable {

	private static final long serialVersionUID = -8902627890319680319L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_DB_ConfigTable_ID
	 * @param trxName
	 */
	public MDBConfigTable(Properties ctx, int UY_DB_ConfigTable_ID,
			String trxName) {
		super(ctx, UY_DB_ConfigTable_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MDBConfigTable(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

}
