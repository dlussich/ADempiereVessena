package org.openup.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.Query;
import org.compiere.util.DB;

public class MBGCommission extends X_UY_BG_Commission {

	public MBGCommission(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	public MBGCommission(Properties ctx, int UY_BG_Commission_ID, String trxName) {
		super(ctx, UY_BG_Commission_ID, trxName);
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		String sqlCountIsDefault = "SELECT COUNT(*) FROM UY_BG_Commission WHERE isDefault='Y'" + ((newRecord) ? "" : "AND UY_BG_Commission_ID <> " + this.get_ID());
		if (DB.getSQLValueEx(get_TrxName(), sqlCountIsDefault) > 0) {
			throw new AdempiereException("Ya hay otra comisión por defecto en el sistema");
		}
		
		
		return true;
	}

	/**Retorna comision predeterminada
	 * OpenUp Ltda Issue#
	 * @author SBouissa 3/9/2015
	 * @param ctx
	 * @param c
	 * @param trxName
	 * @return
	 */
	public static MBGCommission forIsDefault(Properties ctx, char c,
			String trxName) {
		String whereClause = //X_UY_BG_Commission.COLUMNNAME_IsDefault + " = '" + c + "'";
			 X_UY_BG_Commission.COLUMNNAME_IsActive + " = 'Y'";

		List<MBGCommission> model=null;
		
		model = new Query(ctx, I_UY_BG_Commission.Table_Name, whereClause, trxName).list();
		
		if(null != model){
			for (MBGCommission com : model){
				if(com.isDefault()){
					return com;
				}
			}	
		}
		
		return null;
	}

}
