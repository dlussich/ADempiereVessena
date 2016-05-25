/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * @author Nicolas
 *
 */
public class MClientesMeAcct extends X_UY_ClientesMe_Acct {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5081622838403515014L;

	/**
	 * @param ctx
	 * @param UY_ClientesMe_Acct_ID
	 * @param trxName
	 */
	public MClientesMeAcct(Properties ctx, int UY_ClientesMe_Acct_ID,
			String trxName) {
		super(ctx, UY_ClientesMe_Acct_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MClientesMeAcct(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
