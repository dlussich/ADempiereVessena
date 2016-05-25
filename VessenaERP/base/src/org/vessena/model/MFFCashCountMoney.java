/**
 * 
 */
package org.openup.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * @author Nicolas
 *
 */
public class MFFCashCountMoney extends X_UY_FF_CashCountMoney {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5105448141944818111L;

	/**
	 * @param ctx
	 * @param UY_FF_CashCountMoney_ID
	 * @param trxName
	 */
	public MFFCashCountMoney(Properties ctx, int UY_FF_CashCountMoney_ID,
			String trxName) {
		super(ctx, UY_FF_CashCountMoney_ID, trxName);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MFFCashCountMoney(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		if(this.getAmount().compareTo(Env.ZERO) <= 0) throw new AdempiereException("El valor del billete/moneda debe ser mayor a cero");
		if(this.getQtyEntered().compareTo(Env.ZERO) < 0) throw new AdempiereException("La cantidad no puede ser menor a cero");
				
		this.setLineTotalAmt(this.getAmount().multiply(this.getQtyEntered())); //seteo importe total
		
		return true;
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		
		this.updateHeader(); //actualizo importes del cabezal
		
		return true;
	}

	@Override
	protected boolean afterDelete(boolean success) {
		
		this.updateHeader(); //actualizo importes del cabezal
			
		return true;
	}
	
	/**
	 * OpenUp. Nicolas Sarlabos. 30/01/2014. #1745.
	 * Metodo que actualiza importes del cabezal.
	 * 
	 * */
	private void updateHeader(){
		
		String sql = "";
				
		//actualizo importe monedas y billetes arqueados
		sql = "select coalesce(sum(m.linetotalamt),0) FROM uy_ff_cashcountmoney m where m.uy_ff_cashcount_id = " + getUY_FF_CashCount_ID();
		BigDecimal amt = DB.getSQLValueBDEx(get_TrxName(), sql);
		
		sql = "UPDATE UY_FF_CashCount" +
			  " SET amt1 = " + amt +
		      " WHERE uy_ff_cashcount_id = " + getUY_FF_CashCount_ID();
		
		DB.executeUpdateEx(sql, get_TrxName());
		
		//actualizo importe faltante/sobrante
		sql = "UPDATE UY_FF_CashCount" +
			  " SET differenceamt = " +
		      " (select coalesce(amt1 + amt2, 0) - coalesce(amt3 + amt4, 0))" +
			  " WHERE UY_FF_CashCount_ID = " + getUY_FF_CashCount_ID();
		
		DB.executeUpdateEx(sql, get_TrxName());

	}	
}
