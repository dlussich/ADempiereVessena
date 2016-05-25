package org.compiere.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Properties;

import org.compiere.util.Env;
import org.openup.model.X_UY_PriceLoadLine;

/**
	 * 	UY_PriceLoadLine && UY_PriceUpdateLine
	 *  E. Bentancor
	 *  #5408
	 */

public class CalloutPriceLoad extends CalloutEngine {
	
	/**
	 * 	UY_PriceLoadLine && UY_PriceUpdateLine
	 *  E. Bentancor
	 *  #5408
	 */
	/**
	 * 	UY_PriceLoadLine && UY_PriceUpdateLine
	 *  E. Bentancor
	 *  #5408
	 */
	public String setNewPriceNMargins(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value){
		
		if (value == null) return "";
		
		
		if (mField.getColumnName().equalsIgnoreCase("PriceList")){
			if(mTab.getValue("PriceList") != null){
				if(mTab.getValue("PricePO") != null){
					BigDecimal np = new BigDecimal(mTab.get_ValueAsString("PriceList"));
					BigDecimal po = new BigDecimal(mTab.get_ValueAsString("PricePO"));
					BigDecimal margin = ((np.multiply(Env.ONEHUNDRED).setScale(2, RoundingMode.HALF_UP)).divide(po, 2, RoundingMode.HALF_UP)).subtract(Env.ONEHUNDRED);
					mTab.setValue("Margin", margin);
				}
				
				if(mTab.getValue("PriceCostFinal") != null){
					BigDecimal np = new BigDecimal(mTab.get_ValueAsString("PriceList"));
					BigDecimal pcf = new BigDecimal(mTab.get_ValueAsString("PriceCostFinal"));
					BigDecimal margin = ((np.multiply(Env.ONEHUNDRED).setScale(2, RoundingMode.HALF_UP)).divide(pcf, 2, RoundingMode.HALF_UP)).subtract(Env.ONEHUNDRED);
					mTab.setValue("Margin2", margin);
				}
				
				if(mTab.getValue("PriceInvoiced") != null){
					BigDecimal np = new BigDecimal(mTab.get_ValueAsString("PriceList"));
					BigDecimal pInv = new BigDecimal(mTab.get_ValueAsString("PriceInvoiced"));
					BigDecimal margin = ((np.multiply(Env.ONEHUNDRED).setScale(2, RoundingMode.HALF_UP)).divide(pInv, 2, RoundingMode.HALF_UP)).subtract(Env.ONEHUNDRED);
					mTab.setValue("Margin3", margin);
				}
			}
		}else if(mField.getColumnName().equalsIgnoreCase("Margin")){
			
			
			if(mTab.getValue("PricePO") != null){
				BigDecimal newPrice = Env.ZERO;
				BigDecimal po = new BigDecimal(mTab.get_ValueAsString("PricePO"));
				BigDecimal newMargin = new BigDecimal(mTab.get_ValueAsString("Margin"));
				newPrice = (newMargin.add(Env.ONEHUNDRED)).multiply(po).setScale(2, RoundingMode.HALF_UP).divide(Env.ONEHUNDRED);
				mTab.setValue("PriceList", newPrice);
			}
			
			
			if(mTab.getValue("PriceCostFinal") != null){
				BigDecimal np = new BigDecimal(mTab.get_ValueAsString("PriceList"));
				BigDecimal pcf = new BigDecimal(mTab.get_ValueAsString("PriceCostFinal"));
				BigDecimal margin = ((np.multiply(Env.ONEHUNDRED).setScale(2, RoundingMode.HALF_UP)).divide(pcf, 2, RoundingMode.HALF_UP)).subtract(Env.ONEHUNDRED);
				mTab.setValue("Margin2", margin);
			}
			
			if(mTab.getValue("PriceInvoiced") != null){
				BigDecimal np = new BigDecimal(mTab.get_ValueAsString("PriceList"));
				BigDecimal pInv = new BigDecimal(mTab.get_ValueAsString("PriceCostFinal"));
				BigDecimal margin = ((np.multiply(Env.ONEHUNDRED).setScale(2, RoundingMode.HALF_UP)).divide(pInv, 2, RoundingMode.HALF_UP)).subtract(Env.ONEHUNDRED);
				mTab.setValue("Margin3", margin);
			
			}
		}else if(mField.getColumnName().equalsIgnoreCase("Margin2")){
			
			
			if(mTab.getValue("PriceCostFinal") != null){
				BigDecimal newPrice = Env.ZERO;
				BigDecimal pcf = new BigDecimal(mTab.get_ValueAsString("PriceCostFinal"));
				BigDecimal newMargin = new BigDecimal(mTab.get_ValueAsString("Margin2"));
				newPrice = (newMargin.add(Env.ONEHUNDRED)).multiply(pcf).setScale(2, RoundingMode.HALF_UP).divide(Env.ONEHUNDRED);
				mTab.setValue("PriceList", newPrice);
			}
			
			if(mTab.getValue("PricePO") != null){
				BigDecimal np = new BigDecimal(mTab.get_ValueAsString("PriceList"));
				BigDecimal po = new BigDecimal(mTab.get_ValueAsString("PricePO"));
				BigDecimal margin = ((np.multiply(Env.ONEHUNDRED).setScale(2, RoundingMode.HALF_UP)).divide(po, 2, RoundingMode.HALF_UP)).subtract(Env.ONEHUNDRED);
				mTab.setValue("Margin", margin);
			}
			
			if(mTab.getValue("PriceInvoiced") != null){
				BigDecimal np = new BigDecimal(mTab.get_ValueAsString("PriceList"));
				BigDecimal pInv = new BigDecimal(mTab.get_ValueAsString("PriceCostFinal"));
				BigDecimal margin = ((np.multiply(Env.ONEHUNDRED).setScale(2, RoundingMode.HALF_UP)).divide(pInv, 2, RoundingMode.HALF_UP)).subtract(Env.ONEHUNDRED);
				mTab.setValue("Margin3", margin);
			
			}
		}else if(mField.getColumnName().equalsIgnoreCase("Margin3")){
			
			
			if(mTab.getValue("PriceInvoiced") != null){
				BigDecimal newPrice = Env.ZERO;
				BigDecimal pcf = new BigDecimal(mTab.get_ValueAsString("PriceInvoiced"));
				BigDecimal newMargin = new BigDecimal(mTab.get_ValueAsString("Margin3"));
				newPrice = (newMargin.add(Env.ONEHUNDRED)).multiply(pcf).setScale(2, RoundingMode.HALF_UP).divide(Env.ONEHUNDRED);
				mTab.setValue("PriceList", newPrice);
			}
			
			if(mTab.getValue("PricePO") != null){
				BigDecimal np = new BigDecimal(mTab.get_ValueAsString("PriceList"));
				BigDecimal po = new BigDecimal(mTab.get_ValueAsString("PricePO"));
				BigDecimal margin = ((np.multiply(Env.ONEHUNDRED).setScale(2, RoundingMode.HALF_UP)).divide(po, 2, RoundingMode.HALF_UP)).subtract(Env.ONEHUNDRED);
				mTab.setValue("Margin", margin);
			}
			
			if(mTab.getValue("PriceCostFinal") != null){
				BigDecimal np = new BigDecimal(mTab.get_ValueAsString("PriceList"));
				BigDecimal pcf = new BigDecimal(mTab.get_ValueAsString("PriceCostFinal"));
				BigDecimal margin = ((np.multiply(Env.ONEHUNDRED).setScale(2, RoundingMode.HALF_UP)).divide(pcf, 2, RoundingMode.HALF_UP)).subtract(Env.ONEHUNDRED);
				mTab.setValue("Margin2", margin);
			
			}
		}
		
		
		return "";
	}

}
