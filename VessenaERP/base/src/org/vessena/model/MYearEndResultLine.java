package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

public class MYearEndResultLine extends X_UY_YearEndResultLine {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5833998078464771402L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_YearEndResultLine_ID
	 * @param trxName
	 */
	public MYearEndResultLine(Properties ctx, int UY_YearEndResultLine_ID, String trxName) {
		super(ctx, UY_YearEndResultLine_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MYearEndResultLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	

}
