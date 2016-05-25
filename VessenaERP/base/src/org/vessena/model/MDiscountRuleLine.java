/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * @author IN
 *
 */
public class MDiscountRuleLine extends X_UY_DiscountRuleLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 132082270430693894L;
	
	public static final String REWARDTYPE_Operativo = "O";
	public static final String REWARDTYPE_FinancEnFact = "I";
	public static final String REWARDTYPE_FinancFueraFact = "J";
	public static final String REWARDTYPE_AlPago = "K";
	public static final String REWARDTYPE_Retorno = "R";
	public static final String REWARDTYPE_BonifS = "S";
	public static final String REWARDTYPE_BonifC = "C";

	/**
	 * @param ctx
	 * @param UY_DiscountRuleLine_ID
	 * @param trxName
	 */
	public MDiscountRuleLine(Properties ctx, int UY_DiscountRuleLine_ID,
			String trxName) {
		super(ctx, UY_DiscountRuleLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MDiscountRuleLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public boolean isOperativo(){
		if(this.getRewardType().equals(REWARDTYPE_Operativo)) return true;
		return false;
	}
	public boolean isFinancEnFact(){
		if(this.getRewardType().equals(REWARDTYPE_FinancEnFact)) return true;
		return false;
	}
	public boolean isFinancFueraFact(){
		if(this.getRewardType().equals(REWARDTYPE_FinancFueraFact)) return true;
		return false;
	}
	public boolean isAlPago(){
		if(this.getRewardType().equals(REWARDTYPE_AlPago)) return true;
		return false;
	}
	public boolean isRetono(){
		if(this.getRewardType().equals(REWARDTYPE_Retorno)) return true;
		return false;
	}
	public boolean isBonificS(){
		if(this.getRewardType().equals(REWARDTYPE_BonifS)) return true;
		return false;
	}
	public boolean isBonificC(){
		if(this.getRewardType().equals(REWARDTYPE_BonifC)) return true;
		return false;
	}

}
