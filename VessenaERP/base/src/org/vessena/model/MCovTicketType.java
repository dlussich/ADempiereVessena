/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.Query;

/**
 * @author SBouissa
 *
 */
public class MCovTicketType extends X_Cov_TicketType {

	/**
	 * @param ctx
	 * @param Cov_TicketType_ID
	 * @param trxName
	 */
	public MCovTicketType(Properties ctx, int Cov_TicketType_ID, String trxName) {
		super(ctx, Cov_TicketType_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MCovTicketType(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	/***
	 * Obtengo modelo segun value recibido.
	 * OpenUp Ltda. 
	 * @author Sylvie Bouissa - 22/04/2015
	 * @see
	 * @param ctx
	 * @param value
	 * @param isHeder
	 * @param trxName
	 * @return
	 */
	public static MCovTicketType forValueAndHeader(Properties ctx, String value,String isHeder, String trxName){
		
		String whereClause = X_Cov_TicketType.COLUMNNAME_codigo + "='" + value +"' AND " + 
							X_Cov_TicketType.COLUMNNAME_isheather + "='" + isHeder +"'";
		
		MCovTicketType model = new Query(ctx, X_Cov_TicketType.Table_Name, whereClause, trxName)
		.first();
		
		
		return model;
	}

}
