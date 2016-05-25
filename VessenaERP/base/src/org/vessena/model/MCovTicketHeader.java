/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 26/02/2015
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MCovTicketHeader
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Gabriel Vila - 26/02/2015
 * @see
 */
public class MCovTicketHeader extends X_Cov_Ticket_Header {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8480394351749885318L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param Cov_Ticket_Header_ID
	 * @param trxName
	 */
	public MCovTicketHeader(Properties ctx, int Cov_Ticket_Header_ID,
			String trxName) {
		super(ctx, Cov_Ticket_Header_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MCovTicketHeader(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
