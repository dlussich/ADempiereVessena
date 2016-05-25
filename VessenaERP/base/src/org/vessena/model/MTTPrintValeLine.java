/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * @author Sylvie Bouissa
 *
 */
public class MTTPrintValeLine extends X_UY_TT_PrintValeLine {


	public MTTPrintValeLine(Properties ctx, int UY_TT_PrintValeLine_ID,
			String trxName) {
		super(ctx, UY_TT_PrintValeLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}


	public MTTPrintValeLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
