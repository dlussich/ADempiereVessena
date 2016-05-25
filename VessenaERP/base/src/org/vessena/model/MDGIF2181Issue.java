/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 20/08/2013
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MDGIF2181Issue
 * OpenUp Ltda. Issue # 
 * Description: Modelo para inconsistencia del proceso de DGI para el formato 2181.
 * @author Gabriel Vila - 20/08/2013
 * @see
 */
public class MDGIF2181Issue extends X_UY_DGI_F2181_Issue {

	private static final long serialVersionUID = -2035750634891143021L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_DGI_F2181_Issue_ID
	 * @param trxName
	 */
	public MDGIF2181Issue(Properties ctx, int UY_DGI_F2181_Issue_ID,
			String trxName) {
		super(ctx, UY_DGI_F2181_Issue_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MDGIF2181Issue(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

}
