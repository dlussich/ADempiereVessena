/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.compiere.model.Query;

/**
 * @author Nicolás
 *
 */
public class MRTCashBox extends X_UY_RT_CashBox {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3687691215467142782L;

	/**
	 * @param ctx
	 * @param UY_RT_CashBox_ID
	 * @param trxName
	 */
	public MRTCashBox(Properties ctx, int UY_RT_CashBox_ID, String trxName) {
		super(ctx, UY_RT_CashBox_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRTCashBox(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * OpenUp Ltda Issue#
	 * @author Sylvie Bouissa 07/05/2015
	 * @param ctx
	 * @param string
	 * @param get_TrxName
	 * @return
	 */
	public static MRTCashBox forValue(Properties ctx, String value, String trxName){
		
		String whereClause = X_UY_RT_CashBox.COLUMNNAME_Value + " = '" + value + "'";
		
		MRTCashBox model = new Query(ctx, X_UY_RT_CashBox.Table_Name, whereClause, trxName)
		.first();		
		
		return model;
	}

	/**
	 * Retorna las cajas activas para la sucursal recibida
	 * @author OpenUp SBT Issue #5555  1/3/2016 9:57:55
	 * @param ctx
	 * @param mADOrgID
	 * @param trxName
	 * @return
	 */
	public static List<MRTCashBox> forSucursal(Properties ctx,int mADOrgID,String trxName) {
		String where = X_UY_RT_CashBox.COLUMNNAME_AD_Org_ID +" = "+ mADOrgID 
				+ " AND " + X_UY_RT_CashBox.COLUMNNAME_IsActive + " = 'Y'"
				+ " AND " + X_UY_RT_CashBox.COLUMNNAME_Value + " != '21'"; //La caja de envases no registra ventas codigo 21.
		
		List<MRTCashBox> model = new Query(ctx, I_UY_RT_CashBox.Table_Name,where,trxName).list();
		return model;		
	}

}
