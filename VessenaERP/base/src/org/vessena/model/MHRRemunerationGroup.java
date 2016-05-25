package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

public class MHRRemunerationGroup extends X_UY_HRRemunerationGroup {

	private static final long serialVersionUID = 1L;

	public MHRRemunerationGroup(Properties ctx, int UY_HRRemunerationGroup_ID,
			String trxName) {
		super(ctx, UY_HRRemunerationGroup_ID, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public MHRRemunerationGroup(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	

}
