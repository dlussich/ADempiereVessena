/**
 * 
 */
package org.openup.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import org.openup.util.Converter;

/**
 * @author SBouissa
 *
 */
public class MCovTicketLineClienteCC extends X_Cov_Ticket_LineClienteCC {

	/**
	 * @param ctx
	 * @param Cov_Ticket_LineClienteCC_ID
	 * @param trxName
	 */
	public MCovTicketLineClienteCC(Properties ctx,
			int Cov_Ticket_LineClienteCC_ID, String trxName) {
		super(ctx, Cov_Ticket_LineClienteCC_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MCovTicketLineClienteCC(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * OpenUp Ltda Issue#
	 * @author Sylvie Bouissa 24/04/2015
	 * @param lineSplit
	 * @param fchCabezal
	 * @param headerId
	 */
	public void setearDatosLineaClienteCC(String[] lineSplit,
			String fchCabezal, int headerId) {
		MCovTicketType tipoTkt = MCovTicketType.forValueAndHeader(getCtx(), lineSplit[2], "N", get_TrxName());
		if(tipoTkt!=null){
			this.setnumerodelinea(lineSplit[1]); //1
			this.setCov_TicketType_ID(tipoTkt.get_ID()); //2
			this.settimestamplinea(Converter.convertirHHMMss(lineSplit[3], fchCabezal)); //3
			
			this.setcodigocc(Integer.valueOf(lineSplit[3])); //4
			this.setnombrecc(lineSplit[3]); //5
			this.setmontopagocc(new BigDecimal(lineSplit[6])); //6
			
			this.setCov_Ticket_Header_ID(headerId);
		}
		
	}

}
