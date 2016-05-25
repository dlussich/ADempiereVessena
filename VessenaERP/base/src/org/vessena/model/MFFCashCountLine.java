/**
 * 
 */
package org.openup.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.util.DB;

/**
 * @author Nicolas
 *
 */
public class MFFCashCountLine extends X_UY_FF_CashCountLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1240957124033157656L;

	/**
	 * @param ctx
	 * @param UY_FF_CashCountLine_ID
	 * @param trxName
	 */
	public MFFCashCountLine(Properties ctx, int UY_FF_CashCountLine_ID,
			String trxName) {
		super(ctx, UY_FF_CashCountLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MFFCashCountLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
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
	 * Metodo que actualiza importe faltante/sobrante del cabezal.
	 * 
	 * */
	private void updateHeader(){
		
		String sql = "";
						
		//actualizo importe monedas y billetes arqueados
		sql = "select coalesce(sum(amount),0)" +
		      " FROM uy_ff_cashcountline" +
			  " WHERE uy_ff_cashcount_id = " + getUY_FF_CashCount_ID() +
			  " AND estado = 'Y'";
		BigDecimal amt = DB.getSQLValueBDEx(get_TrxName(), sql);
				
		sql = "UPDATE UY_FF_CashCount" +
			  " SET amt2 = " + amt +
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
