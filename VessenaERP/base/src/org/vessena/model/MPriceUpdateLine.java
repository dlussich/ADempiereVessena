/**
 * 
 */
package org.openup.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.MPriceList;

/**
 * @author Nicolas
 *
 */
public class MPriceUpdateLine extends X_UY_PriceUpdateLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1023825152835565549L;

	/**
	 * @param ctx
	 * @param UY_PriceUpdateLine_ID
	 * @param trxName
	 */
	public MPriceUpdateLine(Properties ctx, int UY_PriceUpdateLine_ID,
			String trxName) {
		super(ctx, UY_PriceUpdateLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MPriceUpdateLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		if(!newRecord && is_ValueChanged("PriceList")) {
			
			MPriceUpdate hdr = (MPriceUpdate)this.getUY_PriceUpdate();
			
			MPriceList list = (MPriceList)hdr.getM_PriceList();
			
			BigDecimal diffAmt = (this.getPriceList().subtract(this.getPriceActual())).setScale(list.getPricePrecision(), RoundingMode.HALF_UP);
			
			this.setDifferenceAmt(diffAmt);
			
		}
		if (newRecord){//SBT 21/01/2016 Issue #5345
			//Se debe agregar el cálculo ya que lo amerita el proceso de lista de precio por UPC cuando el prod no esta en la lista anterior.
			MPriceUpdate hdr = (MPriceUpdate)this.getUY_PriceUpdate();
			
			MPriceList list = (MPriceList)hdr.getM_PriceList();
			
			BigDecimal diffAmt = (this.getPriceList().subtract(this.getPriceActual())).setScale(list.getPricePrecision(), RoundingMode.HALF_UP);
			
			this.setDifferenceAmt(diffAmt);
		}
		
		return true;
	}

}
