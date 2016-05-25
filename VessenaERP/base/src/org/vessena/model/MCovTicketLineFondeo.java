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
public class MCovTicketLineFondeo extends X_Cov_Ticket_LineFondeo {

	/**
	 * @param ctx
	 * @param Cov_Ticket_LineFondeo_ID
	 * @param trxName
	 */
	public MCovTicketLineFondeo(Properties ctx, int Cov_Ticket_LineFondeo_ID,
			String trxName) {
		super(ctx, Cov_Ticket_LineFondeo_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MCovTicketLineFondeo(Properties ctx, ResultSet rs, String trxName) {
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
	public void setDatosLineaFondeo(String[] lineSplit, String fchCabezal,
			int headerId) {
		MCovTicketType tipoTkt = MCovTicketType.forValueAndHeader(getCtx(), lineSplit[2], "N", get_TrxName());
		MCovCodigoMedioPago codMdoPago = MCovCodigoMedioPago.forValue(getCtx(),lineSplit[4],get_TrxName());
		if(tipoTkt!=null && codMdoPago!=null){
			this.setnumerodelinea(lineSplit[1]); //1
			this.setCov_TicketType_ID(tipoTkt.get_ID()); //2
			this.settimestamplinea(Converter.convertirHHMMss(lineSplit[3], fchCabezal)); //3
			this.setCov_CodigoMedioPago_ID(codMdoPago.get_ID()); //4
			this.setcodigomoneda(lineSplit[5]); //5
			this.setmontofondeo(new BigDecimal(lineSplit[6]));
			this.setCov_Ticket_Header_ID(headerId);
		}
		
	}

}
