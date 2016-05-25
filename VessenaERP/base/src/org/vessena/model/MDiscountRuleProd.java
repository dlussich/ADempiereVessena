package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;

public class MDiscountRuleProd extends X_UY_DiscountRule_Prod {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3571535652690506422L;
	
	
	public MDiscountRuleProd(Properties ctx, int UY_DiscountRule_Prod_ID, String trxName) {
		super(ctx, UY_DiscountRule_Prod_ID, trxName);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MDiscountRuleProd(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		// TODO Auto-generated method stub
		
		MDiscountRuleGroup drGroup = new MDiscountRuleGroup(getCtx(), this.getUY_DiscountRule_Group_ID(), get_TrxName());
		
		String sql = "select COALESCE(a.m_pricelist_id, 0) from UY_DiscountRule a" +
						" join UY_DiscountRule_Version b" + 
							" on a.UY_DiscountRule_ID = b.UY_DiscountRule_ID" + 
						" join UY_DiscountRule_Group c" +
							" on b.UY_DiscountRule_Version_ID = c.UY_DiscountRule_Version_ID" +
						" where c.UY_DiscountRule_Group_ID = " + drGroup.get_ID();
		
		int pricelist_id = DB.getSQLValue(get_TrxName(), sql);
		
		if(pricelist_id == 0) throw new AdempiereException("La condicion no tiene una lista de precios asociada");
		
		return true;
	}
	
	

}
