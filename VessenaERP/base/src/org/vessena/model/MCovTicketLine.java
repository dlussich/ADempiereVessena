/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 26/02/2015
 */
package org.openup.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import org.openup.util.Converter;

/**
 * org.openup.model - MCovTicketLine
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Gabriel Vila - 26/02/2015
 * @see
 */
public class MCovTicketLine extends X_Cov_Ticket_Line {

	/**
	 * 
	 */
	private static final long serialVersionUID = 53639974385252994L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param Cov_Ticket_Line_ID
	 * @param trxName
	 */
	public MCovTicketLine(Properties ctx, int Cov_Ticket_Line_ID, String trxName) {
		super(ctx, Cov_Ticket_Line_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MCovTicketLine(Properties ctx, ResultSet rs, String trxName) {
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
	public void setearDatosLineaItemVta(String[] lineSplit, String fchCabezal,
			int headerId) {
		MCovTicketType tipoTkt = MCovTicketType.forValueAndHeader(getCtx(), lineSplit[2], "N", get_TrxName());
		if(tipoTkt!=null){
			
			this.setnumerodelinea(lineSplit[1]); //1
			this.setCov_TicketType_ID(tipoTkt.get_ID()); //2
			this.settimestamplinea(Converter.convertirHHMMss(lineSplit[3], fchCabezal)); //3
			
			this.setcodigoarticulo((lineSplit[4]!=null)? (lineSplit[4]):null);;//4
			this.setcantidad((lineSplit[5]!=null)? new BigDecimal(lineSplit[5]).intValue():null); //5
			this.setpreciounitario((lineSplit[6]!=null)? new BigDecimal(lineSplit[6]):null); //6
			this.setiva((lineSplit[7]!=null)? new BigDecimal(lineSplit[7]):null); //7
			this.setpreciodescuento((lineSplit[8]!=null)? new BigDecimal(lineSplit[8]):null); //8
			this.setivadescuento((lineSplit[9]!=null)? new BigDecimal(lineSplit[9]):null);; //9
			this.setpreciodescuentocombo((lineSplit[10]!=null)? new BigDecimal(lineSplit[10]):null);; //10
			this.setivadescuentocombo((lineSplit[11]!=null)? new BigDecimal(lineSplit[11]):null);; //11
			this.setpreciodescuentomarca((lineSplit[12]!=null)? new BigDecimal(lineSplit[12]):null);; //12
			
			
			this.setivadescuentomarca((lineSplit[13]!=null)? new BigDecimal(lineSplit[13]):null); //13
			this.setpreciodescuentototal((lineSplit[14]!=null)? new BigDecimal(lineSplit[14]):null);; //14
			this.setivadescuentototal((lineSplit[15]!=null)? new BigDecimal(lineSplit[15]):null);//15
			this.setcantdescmanuales((lineSplit[1]!=null)? new BigDecimal(lineSplit[5]).intValue():null);//16
			
			this.setlineacancelada((lineSplit[17]!=null)? Integer.valueOf(lineSplit[17]):null); //17
			this.setmodoingreso((lineSplit[18]!=null)? lineSplit[18]:null);
			
			this.setcodigovendedor((lineSplit[19]!=null)? lineSplit[19]:null); //19
			this.settalle((lineSplit[20]!=null)? lineSplit[20]:null);
			this.setcolor((lineSplit[21]!=null)? lineSplit[21]:null);
			this.setmarca((lineSplit[22]!=null)? lineSplit[22]:null);
			this.setmodelo((lineSplit[23]!=null)? lineSplit[23]:null);
			this.setsiestandem((lineSplit[24]!=null)? lineSplit[24]:null);
			this.setcodigoarticulooriginal((lineSplit[25]!=null)? lineSplit[25]:null);
			this.setcodigoiva((lineSplit[26]!=null)? lineSplit[26]:null);
			this.setsiaplicadescfidel((lineSplit[27]!=null)? lineSplit[27]:null);
			this.setmontorealdescfidel((lineSplit[28]!=null)? new BigDecimal(lineSplit[28]):null);
			this.setsiesconvenio((lineSplit[29]!=null)? lineSplit[29]:null);
			this.setnrolineaconvenio((lineSplit[30]!=null)? Integer.valueOf(lineSplit[30]):null);
			this.setpuntosoferta((lineSplit[31]!=null)? new BigDecimal(lineSplit[31]):null);
			this.setsiesobsequio((lineSplit[32]!=null)? lineSplit[32]:null);
			this.setcantdescmanuales((lineSplit[33]!=null)? new BigDecimal(lineSplit[5]).intValue():null);
			//this.settimestamplinea(timestamplinea);
			this.setCov_Ticket_Header_ID(headerId);
		}
	}

	

}
