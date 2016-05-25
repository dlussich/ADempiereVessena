/**
 * 
 */
package org.openup.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MProduct;
import org.compiere.model.MProductPrice;


/**
 * @author emiliano
 *
 */
public class MPriceUpdateScan extends X_UY_PriceUpdateScan {

	/**
	 * @param ctx
	 * @param UY_PriceUpdateScan_ID
	 * @param trxName
	 */
	public MPriceUpdateScan(Properties ctx, int UY_PriceUpdateScan_ID, String trxName) {
		super(ctx, UY_PriceUpdateScan_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MPriceUpdateScan(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		
		if(success){
			
			MPriceUpdate mpu = (MPriceUpdate) this.getUY_PriceUpdate();
			MProductPrice prodP = MProductPrice.get(getCtx(), mpu.getM_PriceList_Version_ID(), this.getM_Product_ID(), null);
			Timestamp dateTrx = new Timestamp(System.currentTimeMillis());
			if(mpu != null && prodP != null){
				MPriceUpdateLine ul = new MPriceUpdateLine(getCtx(), 0, null);
				ul.setM_Product_ID(this.getM_Product_ID());
				ul.setUY_PriceUpdate_ID(this.getUY_PriceUpdate_ID());
				ul.setPriceActual(prodP.getPriceList());
				ul.setPriceList(prodP.getPriceList());
				ul.setDateAction(dateTrx);
				ul.saveEx();
			}else if (mpu != null && prodP == null){ //SBT 21/01/2016 Issue #5345 --> 
				MProduct prod = (MProduct)this.getM_Product();
				if(null != prod && prod.get_ID() > 0 ){
									
					if(!prod.isSold()) throw new AdempiereException("El producto no es vendible");
					//Cuando el producto no existe en la versión anterior y tiene precio establecido el prod.
					//se coloca como precio actual 0,01 y precio actual el seteado en el producto
					
					MPriceUpdateLine ul = new MPriceUpdateLine(getCtx(), 0, null);
					ul.setM_Product_ID(this.getM_Product_ID());
					ul.setUY_PriceUpdate_ID(this.getUY_PriceUpdate_ID());
//					BigDecimal price = (BigDecimal) prod.get_Value("PriceActual");
//					ul.setPriceActual(new BigDecimal("0.01"));
//					if(null==price)
//						ul.setPriceList(price);
//					else
//						ul.setPriceList(new BigDecimal("0.01"));
					ul.setDateAction(dateTrx);
					ul.saveEx();
				}
			}
		}
		// TODO Auto-generated method stub
		return super.afterSave(newRecord, success);
	}

}
