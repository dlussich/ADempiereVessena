package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

public class MTTChequeraLine extends X_UY_TT_ChequeraLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3134261415683712630L;

	public MTTChequeraLine(Properties ctx, int UY_TT_ChequeraLine_ID,
			String trxName) {
		super(ctx, UY_TT_ChequeraLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public MTTChequeraLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
