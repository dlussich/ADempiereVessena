package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

public class MStoreLOSaleLine extends X_UY_StoreLOSaleLine implements I_UY_StoreLOSaleLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3165115751374594982L;
	
	public MStoreLOSaleLine(Properties ctx, int UY_StoreLOSaleLine_ID, String trxName) {
		super(ctx, UY_StoreLOSaleLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 *  Load Constructor
	 *  @param ctx context
	 *  @param rs result set record
	 *  @param trxName transaction
	 */
	public MStoreLOSaleLine (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	


}
