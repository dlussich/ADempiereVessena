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
public class MHRPatronalesLine extends X_UY_HRPatronalesLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8787561407389985849L;

	/**
	 * @param ctx
	 * @param UY_HRPatronalesLine_ID
	 * @param trxName
	 */
	public MHRPatronalesLine(Properties ctx, int UY_HRPatronalesLine_ID,
			String trxName) {
		super(ctx, UY_HRPatronalesLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MHRPatronalesLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		
		MHRPatronalesDetail detail = new MHRPatronalesDetail(getCtx(),this.getUY_HRPatronalesDetail_ID(),get_TrxName());
		
		String sql = "select coalesce(sum(amount),0) from uy_hrpatronalesline where uy_hrpatronalesdetail_id = " + detail.get_ID();
		BigDecimal amount = DB.getSQLValueBDEx(get_TrxName(), sql);
		
		detail.setAmount(amount);
		detail.saveEx();
		
		return true;
	}

}
