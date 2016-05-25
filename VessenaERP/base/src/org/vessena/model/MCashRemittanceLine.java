/**
 * 
 */
package org.openup.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MDocType;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

/**
 * @author Nicolas
 *
 */
public class MCashRemittanceLine extends X_UY_CashRemittanceLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4967645345303250370L;

	/**
	 * @param ctx
	 * @param UY_CashRemittanceLine_ID
	 * @param trxName
	 */
	public MCashRemittanceLine(Properties ctx, int UY_CashRemittanceLine_ID,
			String trxName) {
		super(ctx, UY_CashRemittanceLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MCashRemittanceLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	/***
	 * Obtiene y retorna modelo de linea segun ID de cabezal de documento, caja POS y moneda recibidos. 
	 * OpenUp Ltda. Issue #4449
	 * @author Nicolas Sarlabos - 23/07/2015
	 * @see
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static MCashRemittanceLine forPOSAndCurrency(Properties ctx, int hdrID, int posID, int currencyID, String trxName){

		String whereClause = X_UY_CashRemittanceLine.COLUMNNAME_UY_CashRemittance_ID + "=" + hdrID + 
				" and " + X_UY_CashRemittanceLine.COLUMNNAME_C_Currency_ID + "=" + currencyID +
				" and " + X_UY_CashRemittanceLine.COLUMNNAME_UY_RT_CashBox_ID + "=" + posID;

		MCashRemittanceLine model = new Query(ctx, I_UY_CashRemittanceLine.Table_Name, whereClause, trxName)
		.first();

		return model;
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		MCashRemittance hdr = (MCashRemittance)this.getUY_CashRemittance();		
		MDocType doc = (MDocType)hdr.getC_DocType();
		
		if(newRecord || is_ValueChanged("Amount")){
			if(this.getAmount().compareTo(Env.ZERO)<=0) throw new AdempiereException("Monto debe ser mayor a cero");
		}
		
		if(doc.getValue()!=null && !doc.getValue().equalsIgnoreCase("")){
			
			if(!doc.getValue().equalsIgnoreCase("cashremittcheck")){
				
				if(newRecord || is_ValueChanged("Amount2")){
					if(this.getAmount2()<=0) throw new AdempiereException("Cantidad de documentos debe ser mayor a cero");			
				}	
				
			} else {
				
				if(this.getDueDate().compareTo(this.getDateTrx()) < 0) throw new AdempiereException("Vencimiento no puede ser menor a fecha de Emision");
				
				//seteo el medio de pago a partir de las fechas
				MPaymentRule payRule = null;
				
				Timestamp dateTrx = TimeUtil.trunc(this.getDateTrx(), TimeUtil.TRUNC_DAY);
				Timestamp dueDate = TimeUtil.trunc(this.getDueDate(), TimeUtil.TRUNC_DAY);
				
				if(dateTrx.compareTo(dueDate)==0){ //si fecha emision = vencimiento, es cheque al dia
					
					payRule = MPaymentRule.forValue(getCtx(), "chequedia", get_TrxName());				
					
					//si fecha emision != vencimiento, es cheque diferido	
				} else payRule = MPaymentRule.forValue(getCtx(), "cheque diferido", get_TrxName());
					
				if(payRule != null) this.setUY_PaymentRule_ID(payRule.get_ID());
				
			}
		}				
		
		return true;
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {

		MCashRemittance hdr = (MCashRemittance)this.getUY_CashRemittance();
		MDocType doc = (MDocType)hdr.getC_DocType();
		
		//si es REMESA EFECTIVO se impacta tabla de totalizadores
		if(doc.getValue()!=null && doc.getValue().equalsIgnoreCase("cashremitt")){

			MCashRemitSum sumLine = MCashRemitSum.forHdrAndCurrency(getCtx(), this.getUY_CashRemittance_ID(), this.getC_Currency_ID(), get_TrxName());

			if(sumLine==null){

				sumLine = new MCashRemitSum(getCtx(), 0, get_TrxName());
				sumLine.setUY_CashRemittance_ID(this.getUY_CashRemittance_ID());
				sumLine.setC_Currency_ID(this.getC_Currency_ID());
				sumLine.saveEx();

			}

			sumLine.setAmount(this.totalAmountForCurrency(this.getC_Currency_ID()));
			sumLine.setQtyCount(this.totalQtyForCurrency(this.getC_Currency_ID()));
			sumLine.saveEx();	

			if(sumLine.getAmount().compareTo(Env.ZERO)==0 && sumLine.getAmount2().compareTo(Env.ZERO)==0 && 
					sumLine.getQtyCount()==0 && sumLine.getQtyCount2()==0) sumLine.deleteEx(true);

		}

		return true;
	}
	
	public BigDecimal totalAmountForCurrency(int curID){
		
		BigDecimal total = Env.ZERO, amt1 = Env.ZERO, amt2 = Env.ZERO;

		String sql = "select coalesce(sum(amount),0)" +
				" from uy_cashremittanceline" +
				" where uy_cashremittance_id = " + getUY_CashRemittance_ID() +
				" and c_currency_id = " + curID;

		amt1 = DB.getSQLValueBDEx(get_TrxName(), sql);

		sql = "select coalesce(sum(amount),0)" +
				" from uy_cashremittancecharge" +
				" where uy_cashremittance_id = " + getUY_CashRemittance_ID() +
				" and c_currency_id = " + curID;

		amt2 = DB.getSQLValueBDEx(get_TrxName(), sql);
			
		total = amt1.add(amt2);
		
		return total;
	}
	
	public int totalQtyForCurrency(int curID){
		
		int total = 0, amt1 = 0, amt2 = 0;

		String sql = "select coalesce(sum(amount2),0)" +
				" from uy_cashremittanceline" +
				" where uy_cashremittance_id = " + getUY_CashRemittance_ID() +
				" and c_currency_id = " + curID;

		amt1 = DB.getSQLValueEx(get_TrxName(), sql);

		sql = "select coalesce(sum(amount2),0)" +
				" from uy_cashremittancecharge" +
				" where uy_cashremittance_id = " + getUY_CashRemittance_ID() +
				" and c_currency_id = " + curID;

		amt2 = DB.getSQLValueEx(get_TrxName(), sql);

		total = amt1 + amt2;

		return total;
	}

	@Override
	protected boolean afterDelete(boolean success) {
		
		MCashRemittance hdr = (MCashRemittance)this.getUY_CashRemittance();
		MDocType doc = (MDocType)hdr.getC_DocType();
		
		//si es REMESA EFECTIVO se impacta tabla de totalizadores
		if(doc.getValue()!=null && doc.getValue().equalsIgnoreCase("cashremitt")){

			MCashRemitSum sumLine = MCashRemitSum.forHdrAndCurrency(getCtx(), this.getUY_CashRemittance_ID(), this.getC_Currency_ID(), get_TrxName());

			if(sumLine==null){

				sumLine = new MCashRemitSum(getCtx(), 0, get_TrxName());
				sumLine.setUY_CashRemittance_ID(this.getUY_CashRemittance_ID());
				sumLine.setC_Currency_ID(this.getC_Currency_ID());
				sumLine.saveEx();

			}

			sumLine.setAmount(this.totalAmountForCurrency(this.getC_Currency_ID()));
			sumLine.setQtyCount(this.totalQtyForCurrency(this.getC_Currency_ID()));
			sumLine.saveEx();

			if(sumLine.getAmount().compareTo(Env.ZERO)==0 && sumLine.getAmount2().compareTo(Env.ZERO)==0 && 
					sumLine.getQtyCount()==0 && sumLine.getQtyCount2()==0) sumLine.deleteEx(true);

		}

		return true;
	}

}
