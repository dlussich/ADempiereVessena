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
public class MCovTicketLineCtaCte extends
		X_Cov_Ticket_LineCtaCte {

	/**
	 * @param ctx
	 * @param Cov_Ticket_LineCtaCte_ID
	 * @param trxName
	 */
	public MCovTicketLineCtaCte(Properties ctx, int Cov_Ticket_LineCtaCte_ID,
			String trxName) {
		super(ctx, Cov_Ticket_LineCtaCte_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MCovTicketLineCtaCte(Properties ctx, ResultSet rs,
			String trxName) {
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
	public void setearDatosLineaCC(String[] lineSplit, String fchCabezal,
			int headerId) {
		MCovTicketType tipoTkt = MCovTicketType.forValueAndHeader(getCtx(), lineSplit[2], "N", get_TrxName());
		MCovCodigoMedioPago codMdoPago = MCovCodigoMedioPago.forValue(getCtx(),lineSplit[4],get_TrxName());
		if(tipoTkt!=null && codMdoPago!=null){
			this.setnumerodelinea(lineSplit[1]); //1
			this.setCov_TicketType_ID(tipoTkt.get_ID()); //2
			this.settimestamplinea(Converter.convertirHHMMss(lineSplit[3], fchCabezal)); //3
			this.setCov_CodigoMedioPago_ID(codMdoPago.get_ID()); //4
			this.setcodigomoneda(lineSplit[5]); //5
			this.settotalmeidopagomoneda(new BigDecimal(lineSplit[6])); //6
			this.settotalmediopagomonedareferencia(new BigDecimal(lineSplit[7])); //7
			this.settotalentregado(new BigDecimal(lineSplit[8])); //8
			this.settotalentregadomonedareferencia(new  BigDecimal(lineSplit[9])); //9
			this.setcambio(new BigDecimal(lineSplit[10])); //10
			this.settipooperacion(lineSplit[11]); //11
			this.setlineaultimopago(lineSplit[12]); //12
			this.setautorizasupervisora(lineSplit[13]); //13
			if(lineSplit[13].equals("1")){
				this.setcodigosupervisora(lineSplit[14]); //14
			}
			this.setlineacancelada(lineSplit[15]); //15 
			//this.settimestamplinea(timestamplinea);
			this.setCov_Ticket_Header_ID(headerId);
		}
		
	}

}
