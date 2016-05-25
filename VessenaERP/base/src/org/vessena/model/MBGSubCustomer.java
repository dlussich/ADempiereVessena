/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 18/06/2015
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.compiere.model.Query;

/**
 * org.openup.model - MBGSubCustomer
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Gabriel Vila - 18/06/2015
 * @see
 */
public class MBGSubCustomer extends X_UY_BG_SubCustomer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3053059889966104590L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_BG_SubCustomer_ID
	 * @param trxName
	 */
	public MBGSubCustomer(Properties ctx, int UY_BG_SubCustomer_ID,
			String trxName) {
		super(ctx, UY_BG_SubCustomer_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MBGSubCustomer(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	public static MBGSubCustomer forCedula(Properties ctx, String cI,
			String trxName) {
		String whereClause = X_UY_BG_SubCustomer.COLUMNNAME_Cedula + " = '" + cI.trim() + "'" +	
				 " AND " + X_UY_BG_SubCustomer.COLUMNNAME_IsActive + " = 'Y'";

		MBGSubCustomer model=null;
		List<MBGSubCustomer> lines = new Query(ctx, I_UY_BG_SubCustomer.Table_Name, whereClause, trxName).list();
		if (lines != null){
			for (MBGSubCustomer line: lines){
				if (line.getCedula().equals(cI)){
					model = line;
					break;
				}
			}
		}			
		return model;
	}

}
