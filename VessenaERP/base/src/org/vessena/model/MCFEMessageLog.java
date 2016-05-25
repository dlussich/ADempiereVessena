package org.openup.model;

import java.util.Properties;

public class MCFEMessageLog extends X_UY_CFE_MessageLog implements I_UY_CFE_MessageLog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1801489444570213247L;

	public MCFEMessageLog(Properties ctx, int UY_CFE_MessageLog_ID, String trxName) {
		super(ctx, UY_CFE_MessageLog_ID, trxName);
		
	}

}
