package org.openup.model;
import java.sql.ResultSet;
import java.util.Properties;


/**
 * 
 */

/**
 * @author Nicolas
 *
 */
public class MTRLoadFuelLine extends X_UY_TR_LoadFuelLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3309119277457309854L;

	/**
	 * @param ctx
	 * @param UY_TR_LoadFuelLine_ID
	 * @param trxName
	 */
	public MTRLoadFuelLine(Properties ctx, int UY_TR_LoadFuelLine_ID,
			String trxName) {
		super(ctx, UY_TR_LoadFuelLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTRLoadFuelLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
