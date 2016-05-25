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
public class MCovTicketLinePagoServ extends X_Cov_Ticket_LinePagoServ {

	/**
	 * @param ctx
	 * @param Cov_Ticket_LinePagoServ_ID
	 * @param trxName
	 */
	public MCovTicketLinePagoServ(Properties ctx,
			int Cov_Ticket_LinePagoServ_ID, String trxName) {
		super(ctx, Cov_Ticket_LinePagoServ_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MCovTicketLinePagoServ(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * OpenUp Ltda Issue#
	 * @author Sylvie Bouissa 29/04/2015
	 * @param lineSplit
	 * @param fchCabezal
	 * @param headerId
	 */
	public void setearDatosPagoServicios(String[] lineSplit, String fchCabezal,
			int headerId,String numroLineFile) {
		MCovTicketType tipoTkt = MCovTicketType.forValueAndHeader(getCtx(), lineSplit[2], "N", get_TrxName());
		MCovCodigoServicios tipoServ = MCovCodigoServicios.forValue(getCtx(), lineSplit[4], get_TrxName()); //4
		if(tipoTkt!=null && tipoServ!= null){
			this.setnumerodelinea(lineSplit[1]); //1
			this.setCov_TicketType_ID(tipoTkt.get_ID()); //2
			this.settimestamplinea(Converter.convertirHHMMss(lineSplit[3], fchCabezal)); //3
			
			this.setCov_CodigoServicios_ID(tipoServ.get_ID());
			this.setmonto((lineSplit[5]!=null)? new BigDecimal(lineSplit[5]):null); //5
			this.setcodigomoneda((lineSplit[6]!=null)? lineSplit[6]:null); //6
			this.setmodoingreso((lineSplit[7]!=null)?lineSplit[7]:null); //7
			this.setcodigovendedor((lineSplit[8]!=null)?lineSplit[8]:null); //8
			this.setreferencia((lineSplit[9]!=null)?lineSplit[9]:null); //9
			this.setlineacancelada((lineSplit[10]!=null)?lineSplit[10]:null); //10
			
			this.setCov_Ticket_Header_ID(headerId);
		}else{
			throw new AdempiereException("No existe tipo de lina o codigo de servicio (linea"+numroLineFile+")");
		}
		
	}

}
