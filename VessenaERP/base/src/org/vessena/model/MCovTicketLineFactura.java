/**
 * 
 */
package org.openup.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.openup.util.Converter;

/**
 * @author SBouissa
 *
 */
public class MCovTicketLineFactura extends X_Cov_Ticket_LineFactura {

	/**
	 * @param ctx
	 * @param Cov_Ticket_LineFactura_ID
	 * @param trxName
	 */
	public MCovTicketLineFactura(Properties ctx, int Cov_Ticket_LineFactura_ID,
			String trxName) {
		super(ctx, Cov_Ticket_LineFactura_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MCovTicketLineFactura(Properties ctx, ResultSet rs, String trxName) {
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
	public void setDatosLineaFactura(String[] lineSplit, String fchCabezal,
			int headerId) {
		MCovTicketType tipoTkt = MCovTicketType.forValueAndHeader(getCtx(), lineSplit[2], "N", get_TrxName());
		if(tipoTkt!=null){
			try{
				this.setnumerodelinea(lineSplit[1]); //1
				this.setCov_TicketType_ID(tipoTkt.get_ID()); //2
				this.settimestamplinea(Converter.convertirHHMMss(lineSplit[3], fchCabezal)); //3
				
				this.setruc(lineSplit[4]); //4
				this.setcaja(lineSplit[5]); //5
				this.setcajaticket(lineSplit[6]); //6
				this.setnumeroticket(lineSplit[7]); //7
				this.setticketreferencia(lineSplit[8]);  //8
				this.setfechafactura(Converter.convertirddMMYYYY_YYYYMMdd(lineSplit[9])); //11-10-2008
				
				this.settotalfactura(new BigDecimal (lineSplit[10]));
				this.setimpoteivacodigo(new BigDecimal (lineSplit[11]));
				
				this.setimpoteivacodigo1(new BigDecimal (lineSplit[12]));
				this.setimpoteivacodigo2(new BigDecimal (lineSplit[13]));
				this.setimpoteivacodigo3(new BigDecimal (lineSplit[14]));
				this.setimpoteivacodigo4(new BigDecimal (lineSplit[15]));
				this.setimpoteivacodigo5(new BigDecimal (lineSplit[16]));
				this.setimpoteivacodigo6(new BigDecimal (lineSplit[17]));
				this.setimpoteivacodigo7(new BigDecimal (lineSplit[18]));
				
				this.setserienrofactura(lineSplit[19]);
		
				this.setCov_Ticket_Header_ID(headerId);
			}catch(Exception e){
				throw new AdempiereException(e.getMessage());
			}
			
		}else{
			throw new AdempiereException();
		}
	}

}
