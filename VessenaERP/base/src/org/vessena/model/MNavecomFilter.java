/**
 * MNavecomFilter.java
 * 09/03/2011
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * OpenUP.
 * MNavecomFilter
 * Descripcion :
 * @author FL
 * Fecha : 09/03/2011
 */
public class MNavecomFilter extends X_UY_Navecom_Filter {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4758633381879049856L;


	/**
	 * Constructor
	 * @param ctx
	 * @param id
	 * @param trxName
	 */
	public MNavecomFilter(Properties ctx, int id,String trxName) {
		super(ctx, id, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MNavecomFilter(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
}
