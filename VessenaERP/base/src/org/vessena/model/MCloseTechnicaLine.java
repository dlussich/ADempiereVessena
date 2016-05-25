package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

public class MCloseTechnicaLine extends X_UY_CloseTechnica_Line {

	private static final long serialVersionUID = 6091352179482854137L;

	public MCloseTechnicaLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	public MCloseTechnicaLine(Properties ctx, int UY_CloseTechnica_Line_ID, String trxName) {
		super(ctx, UY_CloseTechnica_Line_ID, trxName);
		// TODO Auto-generated constructor stub
	}
}
