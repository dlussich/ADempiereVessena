package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

public class MYearEndIntegralLine extends X_UY_YearEndIntegralLine {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5586864086615879402L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_YearEndIntegralLine_ID
	 * @param trxName
	 */
	public MYearEndIntegralLine(Properties ctx, int UY_YearEndIntegralLine_ID, String trxName) {
		super(ctx, UY_YearEndIntegralLine_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MYearEndIntegralLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	

}
