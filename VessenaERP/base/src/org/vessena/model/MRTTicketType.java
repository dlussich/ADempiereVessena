/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.Query;

/**OpenUp Ltda Issue#
 * @author SBouissa 07/05/2015
 *
 */
public class MRTTicketType extends X_UY_RT_TicketType {
	
	public static final String vVenta = "1";
	public static final String vDevolucion = "3";		
	/**
	 * @param ctx
	 * @param UY_RT_TicketType_ID
	 * @param trxName
	 */
	public MRTTicketType(Properties ctx, int UY_RT_TicketType_ID, String trxName) {
		super(ctx, UY_RT_TicketType_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRTTicketType(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * OpenUp Ltda Issue#
	 * @author Sylvie Bouissa 07/05/2015
	 * @param ctx
	 * @param string
	 * @param string2
	 * @param get_TrxName
	 * @return
	 */
	public static MRTTicketType forValueAndHeader(Properties ctx,String value, String isHeder, String trxName) {
		String whereClause = X_UY_RT_TicketType.COLUMNNAME_codigo + "='" + value +"' AND " + 
				X_UY_RT_TicketType.COLUMNNAME_isheader + "='" + isHeder +"'";
		MRTTicketType model = new Query(ctx, X_UY_RT_TicketType.Table_Name, whereClause, trxName).first();
		return model;
	}

}
