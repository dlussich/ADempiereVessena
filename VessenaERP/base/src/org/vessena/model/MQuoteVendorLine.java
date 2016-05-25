/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 11/07/2012
 */
 
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;

/**
 * org.openup.model - MQuoteVendorLine
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Hp - 11/07/2012
 * @see
 */
public class MQuoteVendorLine extends X_UY_QuoteVendorLine {

	private static final long serialVersionUID = -1276487887409396832L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_QuoteVendorLine_ID
	 * @param trxName
	 */
	public MQuoteVendorLine(Properties ctx, int UY_QuoteVendorLine_ID,
			String trxName) {
		super(ctx, UY_QuoteVendorLine_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MQuoteVendorLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		if (!success)
			return success;
		
		this.updateHeader();
		
		return true;
	}
	
	/***
	 * Actualiza datos del cabezal segun lineas.
	 * OpenUp Ltda. Issue #93 
	 * @author Gabriel Vila - 14/12/2012
	 * @see
	 */
	private void updateHeader() {
		try{
			
			String action = "update uy_quotevendor set totalamt=" +
					"(select coalesce(sum(totalamt),0) from uy_quotevendorline " +
					" where uy_quotevendor_id =" + this.getUY_QuoteVendor_ID() + ") " +
					" where uy_quotevendor_id =" + this.getUY_QuoteVendor_ID();
			
			DB.executeUpdateEx(action, get_TrxName());
		}
		catch(Exception e){
			throw new AdempiereException(e);
		}
	}

	
	
}
