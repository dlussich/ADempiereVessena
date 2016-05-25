/**
 * MInvoiceBook.java
 * 29/03/2011
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * OpenUp.
 * MInvoiceBookControl
 * Descripcion :
 * @author Gabriel Vila
 * Fecha : 29/03/2011
 */
public class MCheckBookGap extends X_UY_CheckBookGap {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7836398863738909053L;

	/**
	 * Constructor
	 * @param ctx
	 * @param UY_InvoiceBookGap_ID
	 * @param trxName
	 */
	public MCheckBookGap(Properties ctx, int UY_CheckBookGap_ID, String trxName) {
		super(ctx, UY_CheckBookGap_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MCheckBookGap(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
