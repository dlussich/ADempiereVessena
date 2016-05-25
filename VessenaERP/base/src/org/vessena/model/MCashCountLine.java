/**
 * 
 */
package org.openup.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * @author Nicolas
 *
 */
public class MCashCountLine extends X_UY_CashCountLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8036769210998299868L;

	/**
	 * @param ctx
	 * @param UY_CashCountLine_ID
	 * @param trxName
	 */
	public MCashCountLine(Properties ctx, int UY_CashCountLine_ID,
			String trxName) {
		super(ctx, UY_CashCountLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MCashCountLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	/***
	 * Obtiene y retorna modelo de linea segun ID de cabezal de arqueo, caja POS, medio de pago y moneda. 
	 * OpenUp Ltda. Issue #4437
	 * @author Nicolas Sarlabos - 08/09/2015
	 * @see
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static MCashCountLine get(Properties ctx, int hdrID, int currencyID, int posID, int payRuleID, String trxName){

		MCashCountLine model = null;
		
		String sql = "select l.uy_cashcountline_id" +
		             " from uy_cashcountline l" +
				     " inner join uy_cashcountpayrule pr on l.uy_cashcountpayrule_id = pr.uy_cashcountpayrule_id" +
		             " where pr.uy_cashcount_id = " + hdrID + " and pr.c_currency_id = " + currencyID +
		             " and l.uy_rt_cashbox_id = " + posID + " and pr.uy_paymentrule_id = " + payRuleID;
		
		int id = DB.getSQLValueEx(trxName, sql);
		
		if(id > 0) {
			
			model = new MCashCountLine(ctx, id, trxName);			
			
		}		

		return model;
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
	
		MCashCountPayRule hdr = (MCashCountPayRule)this.getUY_CashCountPayRule();
		
		hdr.setAmount(this.totalTeoricoForPayRule());
		hdr.setAmount2(this.totalRemesaForPayRule());
		hdr.saveEx();		
		
		return true;
	}

	@Override
	protected boolean afterDelete(boolean success) {
		
		MCashCountPayRule hdr = (MCashCountPayRule)this.getUY_CashCountPayRule();
		
		hdr.setAmount(this.totalTeoricoForPayRule());
		hdr.setAmount2(this.totalRemesaForPayRule());
		hdr.saveEx();			
		
		return true;
	}
	
	public BigDecimal totalTeoricoForPayRule(){
		
		BigDecimal total = Env.ZERO;
		
		String sql = "select coalesce(sum(l.amount),0)" +
		             " from uy_cashcountline l" +
		             " inner join uy_cashcountpayrule pr on l.uy_cashcountpayrule_id = pr.uy_cashcountpayrule_id" +
		             " where pr.uy_cashcountpayrule_id = " + this.getUY_CashCountPayRule_ID();				     
		
		total = DB.getSQLValueBDEx(get_TrxName(), sql);
					
		return total;
	}
	
	public BigDecimal totalRemesaForPayRule(){
		
		BigDecimal total = Env.ZERO;
		
		String sql = "select coalesce(sum(l.amount2),0)" +
		             " from uy_cashcountline l" +
		             " inner join uy_cashcountpayrule pr on l.uy_cashcountpayrule_id = pr.uy_cashcountpayrule_id" +
		             " where pr.uy_cashcountpayrule_id = " + this.getUY_CashCountPayRule_ID();				     
		
		total = DB.getSQLValueBDEx(get_TrxName(), sql);
					
		return total;
	}

}
