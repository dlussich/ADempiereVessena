package org.openup.model;

/* (non-Javadoc)
 * @see org.compiere
 * OpenUp Ltda. Issue # 
 * @author Andrea Odriozola - 29/07/2015
 * @see
 * @return
 */

import java.sql.ResultSet;
import java.util.Properties;

public class MDocApprovalLine extends X_UY_DocApprovalLine {

	private static final long serialVersionUID = 3517965825862620228L;

	public MDocApprovalLine(Properties ctx, int UY_DocApprovalLine_ID,
			String trxName) {
		super(ctx, UY_DocApprovalLine_ID, trxName);
		
	}

	public MDocApprovalLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		
	}

}
