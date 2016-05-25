/**
 * 
 */
package org.openup.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBank;
import org.compiere.model.MBankAccount;
import org.compiere.model.MConversionRate;
import org.compiere.model.Query;
import org.compiere.util.Env;

/**
 * @author Nicolas
 *
 */
public class MTRClearingLine extends X_UY_TR_ClearingLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4025032446550029365L;

	/**
	 * @param ctx
	 * @param UY_TR_ClearingLine_ID
	 * @param trxName
	 */
	public MTRClearingLine(Properties ctx, int UY_TR_ClearingLine_ID,
			String trxName) {
		super(ctx, UY_TR_ClearingLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTRClearingLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	/***
	 * Obtiene y retorna modelo de linea segun ID de cabezal de liquidacion y moneda recibidos. 
	 * OpenUp Ltda. Issue #3505
	 * @author Nicolas Sarlabos - 08/04/2015
	 * @see
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static MTRClearingLine forHdrAndCurrency(Properties ctx, int hdrID, int currencyID, String trxName){

		String whereClause = X_UY_TR_ClearingLine.COLUMNNAME_UY_TR_Clearing_ID + "=" + hdrID + 
				" and " + X_UY_TR_ClearingLine.COLUMNNAME_C_Currency_ID + "=" + currencyID;

		MTRClearingLine model = new Query(ctx, I_UY_TR_ClearingLine.Table_Name, whereClause, trxName)
		.first();

		return model;
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		MTRClearing hdr = (MTRClearing)this.getUY_TR_Clearing();//instancio cabezal
		
		if(!newRecord) this.setDifferenceAmt(this.getAmount().subtract(this.getExpenseAmt()));
		
		if(this.getDifferenceAmt().compareTo(Env.ZERO)<=0) this.setAmount2(Env.ZERO);
		
		if((this.getDifferenceAmt().subtract(this.getAmount2())).compareTo(Env.ZERO)>0) {
			
			this.setAmount3(this.getDifferenceAmt().subtract(this.getAmount2()));
			
		} else {
			
			this.setAmount3(Env.ZERO);
			this.setmontopesos(Env.ZERO);
		}
							
		this.setSaldoFinal((this.getDifferenceAmt().subtract(this.getAmount2())).subtract(this.getAmount3()));
		
		if(this.getAmount2().compareTo(Env.ZERO)>0 && this.getC_BankAccount_ID()<=0) throw new AdempiereException("Debe indicar caja destino para el importe devuelto");
		
		//si es nuevo registro o se modifica el importe de adelanto de sueldo
		if(newRecord || is_ValueChanged("Amount3")){
						
			if(this.getC_Currency_ID()==142){ //si son pesos
				
				this.setmontopesos(this.getAmount3());				
				
			} else {
				
				int curFromID = this.getC_Currency_ID();
				int curToID = 142;
				
				BigDecimal dividerate = MConversionRate.getDivideRate(curFromID, curToID, hdr.getDateTrx(), 0, this.getAD_Client_ID(), 0);
				
				if (dividerate == null || dividerate.compareTo(Env.ZERO)==0) throw new AdempiereException ("No se obtuvo tasa de cambio para fecha de documento");	
				
				BigDecimal multiplyrate = MConversionRate.getRate(curFromID, curToID, hdr.getDateTrx(), 0, this.getAD_Client_ID(), 0);
				
				if(multiplyrate!=null)this.setCurrencyRate(multiplyrate);//seteo tasa de cambio
				
				//convierto importe de adelanto
				if(this.getAmount3().compareTo(Env.ZERO)!=0){
					
					BigDecimal amt = this.getAmount3();					

					amt = amt.divide(dividerate, 2, RoundingMode.HALF_UP);
					
					this.setmontopesos(amt);							
				}				
			}		
			
		}
		
		if(!newRecord){
			
			if(this.getAmount2().compareTo(Env.ZERO)<0) throw new AdempiereException("Importe devuelto debe ser mayor o igual a cero");
			if(this.getAmount3().compareTo(Env.ZERO)<0) throw new AdempiereException("Importe de adelanto de sueldo debe ser mayor o igual a cero");

			if(this.getDifferenceAmt().compareTo(Env.ZERO)==0 && this.getAmount2().compareTo(Env.ZERO)>0) throw new AdempiereException("No se permite ingresar importe devuelto por ser CERO la diferencia en esta moneda");
			if(this.getDifferenceAmt().compareTo(Env.ZERO)==0 && this.getAmount3().compareTo(Env.ZERO)>0) throw new AdempiereException("No se permite ingresar importe de adelanto de sueldo por ser CERO la diferencia en esta moneda");

		}
		
		MBankAccount ba = (MBankAccount)this.getC_BankAccount();
		MBank bank = (MBank)ba.getC_Bank();
		
		if (ba != null && bank != null && hdr != null){
			if(ba.getBankAccountType()!=null){
				if(!bank.isBankHandler() && ba.getBankAccountType().equalsIgnoreCase("X")){
					ba.validateCashOpen(hdr.getDateTrx());
				}
			}		
		}
		
		return true;
	}

}
