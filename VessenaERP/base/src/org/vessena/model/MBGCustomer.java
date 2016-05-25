/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 16/06/2015
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.compiere.model.Query;

/**
 * org.openup.model - MBGCustomer
 * OpenUp Ltda. Issue #4169 
 * Description: Modelo para el mantenimiento de clientes de bolsa.
 * @author Gabriel Vila - 16/06/2015
 * @see
 */
public class MBGCustomer extends X_UY_BG_Customer {

	private static final long serialVersionUID = -8039970304855191403L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_BG_Customer_ID
	 * @param trxName
	 */
	public MBGCustomer(Properties ctx, int UY_BG_Customer_ID, String trxName) {
		super(ctx, UY_BG_Customer_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MBGCustomer(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/**
	 * OpenUp Ltda Issue#
	 * @author SBouissa 3/9/2015
	 * @return
	 */
	public static MBGCustomer forCedula(Properties ctx, String cI, String trxName){
		String whereClause = X_UY_BG_Customer.COLUMNNAME_Cedula + " = '" + cI.trim() + "'" +	
				 " AND " + X_UY_BG_Customer.COLUMNNAME_IsActive + " = 'Y'";

		MBGCustomer model=null;
		List<MBGCustomer> lines = new Query(ctx, I_UY_BG_Customer.Table_Name, whereClause, trxName).list();
		if (lines != null){
			for (MBGCustomer line: lines){
				if (line.getCedula().equals(cI)){
					model = line;
					break;
				}
			}
		}			
		return model;
	}
	
	
}
