package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

public class MYearEndAcumulateLine extends X_UY_YearEndAcumulateLine {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2789500534354889538L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_YearEndAcumulateLine_ID
	 * @param trxName
	 */
	public MYearEndAcumulateLine(Properties ctx, int UY_YearEndAcumulateLine_ID, String trxName) {
		super(ctx, UY_YearEndAcumulateLine_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MYearEndAcumulateLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

}
