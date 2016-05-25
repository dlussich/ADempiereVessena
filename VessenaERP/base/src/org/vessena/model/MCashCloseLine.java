package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

public class MCashCloseLine extends X_UY_CashCloseLine {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 659508863152566025L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_CashCloseLine_ID
	 * @param trxName
	 */
	public MCashCloseLine(Properties ctx, int UY_CashCloseLine_ID, String trxName) {
		super(ctx, UY_CashCloseLine_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MCashCloseLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}


}
