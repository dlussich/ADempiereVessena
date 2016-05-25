/**
 * MProcesoFactmasLine.java
 * 28/12/2010
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * OpenUp.
 * MProcesoFactmasLine
 * Descripcion :
 * @author Gabriel Vila
 * Fecha : 28/12/2010
 */
public class MProcesoFactmasLine extends X_UY_ProcesoFactmasLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5257054987001628562L;

	/**
	 * Constructor
	 * @param ctx
	 * @param UY_ProcesoFactmasLine_ID
	 * @param trxName
	 */
	public MProcesoFactmasLine(Properties ctx, int UY_ProcesoFactmasLine_ID,
			String trxName) {
		super(ctx, UY_ProcesoFactmasLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MProcesoFactmasLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
