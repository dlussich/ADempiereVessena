package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

public class MMoldeRemito extends X_UY_Molde_Remito {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5951155420574401919L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_Molde_Remito_ID
	 * @param trxName
	 */
	public MMoldeRemito(Properties ctx, int UY_Molde_Remito_ID, String trxName) {
		super(ctx, UY_Molde_Remito_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MMoldeRemito(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	

}
