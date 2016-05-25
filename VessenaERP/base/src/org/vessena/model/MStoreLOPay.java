package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

public class MStoreLOPay extends X_UY_StoreLOPay implements I_UY_StoreLOPay {


	/**
	 * 
	 */
	private static final long serialVersionUID = -8220679695399669696L;

	
	public MStoreLOPay(Properties ctx, int UY_StoreLOPay_ID, String trxName) {
		super(ctx, UY_StoreLOPay_ID, trxName);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 *  Load Constructor
	 *  @param ctx context
	 *  @param rs result set record
	 *  @param trxName transaction
	 */
	public MStoreLOPay (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	

}
