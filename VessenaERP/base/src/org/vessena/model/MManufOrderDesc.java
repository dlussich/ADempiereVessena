/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

/**
 * @author Nicolas
 *
 */
public class MManufOrderDesc extends X_UY_ManufOrderDesc {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5409662460345878602L;

	/**
	 * @param ctx
	 * @param UY_ManufOrderDesc_ID
	 * @param trxName
	 */
	public MManufOrderDesc(Properties ctx, int UY_ManufOrderDesc_ID,
			String trxName) {
		super(ctx, UY_ManufOrderDesc_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MManufOrderDesc(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean afterDelete(boolean success) {
	
		MBudget budget = new MBudget(getCtx(), this.getUY_Budget_ID(), get_TrxName());
		List<MManufOrderDesc> descLines = budget.getDescLines(); //obtengo lineas de observaciones de OF para el presupuesto
		
		if(descLines.size() <= 0){ //si no hay mas lineas seteo el check en FALSE
			
			budget.setIsDescription(false);
			budget.saveEx();
		}				
		
		return true;
	}

}
