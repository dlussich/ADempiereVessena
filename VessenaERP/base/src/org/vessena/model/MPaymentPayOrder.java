/**
 * @author OpenUp SBT Issue#  16/11/2015 18:05:57
 */
package org.openup.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.MDocType;
import org.compiere.model.MPayment;
import org.compiere.util.Env;

/**
 * @author OpenUp SBT Issue#  16/11/2015 18:05:57
 *
 */
public class MPaymentPayOrder extends X_C_PaymentPayOrder {

	/**
	 * @author OpenUp SBT Issue#  16/11/2015 18:05:59
	 * @param ctx
	 * @param C_PaymentPayOrder_ID
	 * @param trxName
	 */
	public MPaymentPayOrder(Properties ctx, int C_PaymentPayOrder_ID,
			String trxName) {
		super(ctx, C_PaymentPayOrder_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @author OpenUp SBT Issue#  16/11/2015 18:05:59
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MPaymentPayOrder(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		//Se debe actualizar los montos ya sea un nuevo registro o una actualización
		MPayment mp =(MPayment) this.getC_Payment();
		MDocType doc = (MDocType) mp.getC_DocType();
		if(mp!=null && !mp.isReceipt() && (doc.getValue().equalsIgnoreCase("paymentpo"))){//Si es pago
			mp.updateTotalDiscounts(Env.ZERO);
			mp.updateTotalResguardo(Env.ZERO);
			mp.saveEx();
		}	
			
		return super.afterSave(newRecord, success);
	}
	
	@Override
	protected boolean beforeDelete() {
		MPayment mp =(MPayment) this.getC_Payment();
		MDocType doc = (MDocType) mp.getC_DocType();
		if(mp!=null && !mp.isReceipt() && (doc.getValue().equalsIgnoreCase("paymentpo"))){//Si es pago
			if(this.get_Value("TotalDiscounts")!=null){
				mp.updateTotalDiscounts(new BigDecimal(this.get_ValueAsString("TotalDiscounts")));
			}
			if(this.get_Value("AmtResguardo")!=null){
				mp.updateTotalResguardo(this.getAmtResguardo());

			}
			mp.saveEx();
		}	
		return super.beforeDelete();
	}
	
	@Override
	protected boolean afterDelete(boolean success) {
		
		this.get_ValueOldAsInt("C_Payment_ID");
		MPayment mp = new MPayment(getCtx(), this.get_ValueOldAsInt("C_Payment_ID"), get_TrxName());
		MDocType doc = (MDocType) mp.getC_DocType();
		if(mp!=null && !mp.isReceipt() && (doc.getValue().equalsIgnoreCase("paymentpo"))){//Si es pago
			mp.updateAllocDirect(true);
		}
		return super.afterDelete(success);
	}


}
