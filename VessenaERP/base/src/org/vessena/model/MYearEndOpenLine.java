package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

public class MYearEndOpenLine extends X_UY_YearEndOpenLine {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2955700078843678441L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_YearEndOpenLine_ID
	 * @param trxName
	 */
	public MYearEndOpenLine(Properties ctx, int UY_YearEndOpenLine_ID, String trxName) {
		super(ctx, UY_YearEndOpenLine_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MYearEndOpenLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

}
