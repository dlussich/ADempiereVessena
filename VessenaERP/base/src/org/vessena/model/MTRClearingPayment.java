/**
 * 
 */
package org.openup.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * @author Nicolas
 *
 */
public class MTRClearingPayment extends X_UY_TR_ClearingPayment {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3624811181153204379L;

	/**
	 * @param ctx
	 * @param UY_TR_ClearingPayment_ID
	 * @param trxName
	 */
	public MTRClearingPayment(Properties ctx, int UY_TR_ClearingPayment_ID,
			String trxName) {
		super(ctx, UY_TR_ClearingPayment_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTRClearingPayment(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		
		if(!newRecord && success){
			
			MTRClearingLine line = MTRClearingLine.forHdrAndCurrency(getCtx(), this.getUY_TR_Clearing_ID(), this.getC_Currency_ID(), get_TrxName());

			if(line!=null && line.get_ID()>0){
				
				if(this.isSelected()){
					
					BigDecimal anticipos = line.getAmount().add(this.getamtopen());

					line.setAmount(anticipos);
					line.saveEx();						
					
				} else {
					
					BigDecimal anticipos = line.getAmount().subtract(this.getamtopen());

					line.setAmount(anticipos);
					line.saveEx();						
				}

			}			
		}
		
		return true;
	}

}
