/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 25/04/2012
 */
 
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MHRCalculos
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Hp - 25/04/2012
 * @see
 */
public class MHRCalculos extends X_UY_HRCalculos {

	private static final long serialVersionUID = -1591766350772577535L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_HRCalculos_ID
	 * @param trxName
	 */
	public MHRCalculos(Properties ctx, int UY_HRCalculos_ID, String trxName) {
		super(ctx, UY_HRCalculos_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MHRCalculos(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
