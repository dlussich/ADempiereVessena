package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

public class MMoldeFduCaja extends X_UY_Molde_Fdu_Caja{

	private static final long serialVersionUID = -7103771632130746085L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_Molde_Fdu_Caja_ID
	 * @param trxName
	 */
	public MMoldeFduCaja(Properties ctx, int UY_Molde_Fdu_Caja_ID,
			String trxName) {
		super(ctx, UY_Molde_Fdu_Caja_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MMoldeFduCaja(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	

	
	
}
