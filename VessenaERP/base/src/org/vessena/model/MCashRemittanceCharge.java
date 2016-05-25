/**
 * 
 */
package org.openup.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MDocType;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * @author Nicolás
 *
 */
public class MCashRemittanceCharge extends X_UY_CashRemittanceCharge {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param ctx
	 * @param UY_CashRemittanceCharge_ID
	 * @param trxName
	 */
	public MCashRemittanceCharge(Properties ctx, int UY_CashRemittanceCharge_ID, String trxName) {
		super(ctx, UY_CashRemittanceCharge_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MCashRemittanceCharge(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		MCashRemittance hdr = (MCashRemittance)this.getUY_CashRemittance();	
		MDocType doc = (MDocType)hdr.getC_DocType();
		
		if(newRecord || is_ValueChanged("Amount")){
			if(this.getAmount().compareTo(Env.ZERO)<=0) throw new AdempiereException("Monto debe ser mayor a cero");
		}	
		
		if(doc.getValue()!=null && !doc.getValue().equalsIgnoreCase("")){
			
			if(doc.getValue().equalsIgnoreCase("cashremitt")){
				
				if(newRecord || is_ValueChanged("Amount2")){
					if(this.getAmount2()<=0) throw new AdempiereException("Cantidad de documentos debe ser mayor a cero");			
				}				
				
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

}
