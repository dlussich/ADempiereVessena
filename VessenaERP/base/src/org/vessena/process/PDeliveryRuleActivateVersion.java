package org.openup.process;


import java.math.BigDecimal;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.openup.model.MDiscountRule;
import org.openup.model.MDiscountRuleVersion;

public class PDeliveryRuleActivateVersion extends SvrProcess {
	
	private int drID;
	private int drVersionID;

	@Override
	protected void prepare() {
		
		ProcessInfoParameter[] para = getParameter();
		
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName().trim();
			if (name!= null){
				if (name.equalsIgnoreCase("UY_DiscountRule_ID")){
					drID = ((BigDecimal)para[i].getParameter()).intValueExact();
				}
			}
			if (name!= null){
				if (name.equalsIgnoreCase("UY_DiscountRule_Version_ID")){
					drVersionID = ((BigDecimal)para[i].getParameter()).intValueExact();
				}
			}
		}
		
	}

	@Override
	protected String doIt() throws Exception {
		
		if(this.isLastVersion()){
			String sql = "update UY_DiscountRule_Version" +
					" set isValid = 'N'"+
					" where uy_discountrule_id = " + drID;
	
			DB.executeUpdate(sql, get_TrxName());
			
			MDiscountRuleVersion drVersion = new MDiscountRuleVersion(getCtx(), drVersionID, get_TrxName());
			drVersion.setIsValid(true);
			drVersion.setWasValidated(true);
			drVersion.saveEx();
			MDiscountRule dr = new MDiscountRule(getCtx(), drID, get_TrxName());
			
			if(dr.getM_PriceList_ID() > 0){
				drVersion.impactarSobreVersionVigente(dr.getC_BPartner_ID(), dr.getM_PriceList_ID(), drVersion.getStartDate(), dr.get_ID());
			}
		}else{
			throw new AdempiereException("No se puede activar, existe una version mas nueva");
		}
		return null;
	}
	
	private boolean isLastVersion(){
		boolean res = false;
		
		String sql = "select UY_DiscountRule_Version_ID from UY_DiscountRule_Version where uy_discountrule_id = "+ drID
						+ " order by created desc";
		
		int drVerAux = DB.getSQLValue(get_TrxName(), sql);
		
		if(drVerAux == drVersionID){
			res = true;
		}
		
		return res;
	}

}
