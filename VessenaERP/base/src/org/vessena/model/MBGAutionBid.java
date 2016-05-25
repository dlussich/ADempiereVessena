/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.compiere.util.TimeUtil;

/**OpenUp Ltda Issue#
 * @author SBouissa 5/8/2015
 *
 */
public class MBGAutionBid extends X_UY_BG_AutionBid {

	/**
	 * @author SBouissa 5/8/2015
	 * @param ctx
	 * @param UY_BG_AutionBid_ID
	 * @param trxName
	 */
	public MBGAutionBid(Properties ctx, int UY_BG_AutionBid_ID, String trxName) {
		super(ctx, UY_BG_AutionBid_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @author SBouissa 5/8/2015
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MBGAutionBid(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	
	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		if(newRecord){
			//Seteo fecha actual
			Timestamp today = TimeUtil.trunc(new Timestamp(System.currentTimeMillis()), TimeUtil.TRUNC_DAY);
			this.setDateTrx(today);
			Timestamp time = new Timestamp(System.currentTimeMillis());
			this.settimebid(time);
			//TODO SBT Verloooo 
			String nameAux = this.getAD_User().getName();
			if(1<nameAux.length()){
				String ini = nameAux.substring(0,1);
				int i = nameAux.length()-1;
				this.setName(ini+"****"+nameAux.charAt(i));
			}else if(1==nameAux.length()){
				this.setName(nameAux+"****");
			}
			
			//MBGBursa bursa = new MBGBursa(getCtx(),0,null);

		}
		return super.beforeSave(newRecord);
	}
}
