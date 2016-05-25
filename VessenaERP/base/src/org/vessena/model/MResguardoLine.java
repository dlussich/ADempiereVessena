/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 14/11/2012
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.Query;
import org.compiere.util.DB;

/**
 * org.openup.model - MResguardoLine
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Hp - 14/11/2012
 * @see
 */
public class MResguardoLine extends X_UY_ResguardoLine {

	private static final long serialVersionUID = -5398940977117426864L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_ResguardoLine_ID
	 * @param trxName
	 */
	public MResguardoLine(Properties ctx, int UY_ResguardoLine_ID,
			String trxName) {
		super(ctx, UY_ResguardoLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MResguardoLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		
		if (!success) return success;
		
		this.updateHeader();
		
		return true;
	}

	private void updateHeader() {
		try{
			
			String action = "update uy_resguardo set payamt=" +
					"(select coalesce(sum(amt),0) from uy_resguardoline " +
					" where uy_resguardo_id =" + this.getUY_Resguardo_ID() + ") " +
					" where uy_resguardo_id =" + this.getUY_Resguardo_ID();
			
			DB.executeUpdateEx(action, get_TrxName());
		}
		catch(Exception e){
			throw new AdempiereException(e);
		}
	}

}
