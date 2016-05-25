/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**OpenUp Ltda Issue#
 * @author SBouissa 4/8/2015
 *
 */
public class MBGMarketType extends X_UY_BG_MarketType {

	/**
	 * @author SBouissa 4/8/2015
	 * @param ctx
	 * @param UY_BG_MarketType_ID
	 * @param trxName
	 */
	public MBGMarketType(Properties ctx, int UY_BG_MarketType_ID, String trxName) {
		super(ctx, UY_BG_MarketType_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @author SBouissa 4/8/2015
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MBGMarketType(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
