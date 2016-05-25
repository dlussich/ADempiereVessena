/**
 * 
 */
package org.openup.model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.openup.util.Converter;

/**
 * @author SBouissa
 *
 */
public class MCovTicketLineEfectivo extends X_Cov_Ticket_LineEfectivo {

	/**
	 * @param ctx
	 * @param Cov_Ticket_LineEfectivo_ID
	 * @param trxName
	 */
	public MCovTicketLineEfectivo(Properties ctx,
			int Cov_Ticket_LineEfectivo_ID, String trxName) {
		super(ctx, Cov_Ticket_LineEfectivo_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MCovTicketLineEfectivo(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Metodo que se encarga de setear los valores correspondientes segun la posicion
	 * OpenUp Ltda Issue#
	 * @author Sylvie Bouissa 22/04/2015
	 * @param lineSplit
	 */
	public void setearDatosLineaEfectivo(String[] lineSplit,String fchCabezal,int idHeader) {
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
			this.setCov_Ticket_Header_ID(idHeader);
		}
//		lineEfectvio.setCov_TicketType_ID(Cov_TicketType_ID);
//		this.set
//		
	}

	/**Sumo las diferencias entre total entregado y el cambio de las lineas correspondientes al Cov_LoadTicket_ID pasado .
	 * OpenUp Ltda Issue#
	 * @author Sylvie Bouissa 30/04/2015
	 * @param ctx
	 * @param codMoneda
	 * @param cdoMedioPago
	 * @param Cov_LoadTicket_ID
	 * @param get_TrxName
	 * @return
	 */
	public static BigDecimal calcularVentaMonedaCodigoMdioPago(Properties ctx,String codMoneda, String cdoMedioPago, int Cov_LoadTicket_ID,
			String trxName) {
		PreparedStatement pstmt = null;
		BigDecimal retorno = BigDecimal.ZERO;
		String whereClause = X_Cov_Ticket_LineEfectivo.COLUMNNAME_codigomoneda+ " = '"+codMoneda+"' AND "
				+ X_Cov_Ticket_LineEfectivo.COLUMNNAME_Cov_CodigoMedioPago_ID +" = "+
				MCovCodigoMedioPago.forValue(ctx, cdoMedioPago, trxName).get_ID();
				;
		try{
			MCovTicketType venta = MCovTicketType.forValueAndHeader(ctx, "1", "Y", trxName);
			MCovTicketType dev = MCovTicketType.forValueAndHeader(ctx, "3", "Y", trxName);
			// Sumo las diferencias entre total entregado y el cambio de las lineas correspondientes
			String sql = "SELECT SUM(totalentregado - cambio) FROM Cov_Ticket_LineEfectivo WHERE "+whereClause+" AND Cov_Ticket_Header_ID IN"
					+ "(SELECT Cov_Ticket_Header_ID FROM Cov_Ticket_Header WHERE Cov_LoadTicket_ID = " +Cov_LoadTicket_ID +
					" AND estadoticket = 'F' AND Cov_ticketType_ID IN ("+venta.get_ID()+","+dev.get_ID()+"))";
		
			pstmt = DB.prepareStatement (sql, trxName);
			ResultSet rs = pstmt.executeQuery ();
					
			while (rs.next()){
				retorno = rs.getBigDecimal(0);
			}
		
		}catch (Exception ex){
			throw new AdempiereException(ex.getMessage());
		}
		return retorno;
	}
	
	//SODEXO
	
	/**Sumoa las diferencias entre total entregado y el cambio de las lineas correspondientes al Cov_LoadTicket_ID pasado .
	 * OpenUp Ltda Issue#
	 * @author Sylvie Bouissa 30/04/2015
	 * @param ctx
	 * @param codMoneda
	 * @param cdoMedioPago
	 * @param Cov_LoadTicket_ID
	 * @param get_TrxName
	 * @return
	 */
	public static BigDecimal calcularVentaSodexoMoneda(Properties ctx,String codMoneda, String cdoMedioPago, int Cov_LoadTicket_ID,
			String trxName) {
		PreparedStatement pstmt = null;
		BigDecimal retorno = BigDecimal.ZERO;
		String whereClause = X_Cov_Ticket_LineEfectivo.COLUMNNAME_codigomoneda+ " = '"+codMoneda+"' AND "
				+ X_Cov_Ticket_LineEfectivo.COLUMNNAME_Cov_CodigoMedioPago_ID +" = "+
				MCovCodigoMedioPago.forValue(ctx, cdoMedioPago, trxName).get_ID();
				;
		try{
			MCovTicketType venta = MCovTicketType.forValueAndHeader(ctx, "1", "Y", trxName);
			MCovTicketType dev = MCovTicketType.forValueAndHeader(ctx, "3", "Y", trxName);
			// Sumo las diferencias entre total entregado y el cambio de las lineas correspondientes
			String sql = "SELECT SUM(totalentregado - cambio) FROM Cov_Ticket_LineEfectivo WHERE "+whereClause+" AND Cov_Ticket_Header_ID IN"
					+ "(SELECT Cov_Ticket_Header_ID FROM Cov_Ticket_Header WHERE Cov_LoadTicket_ID = " +Cov_LoadTicket_ID +
					" AND estadoticket = 'F' AND Cov_ticketType_ID IN ("+venta.get_ID()+","+dev.get_ID()+"))";
		
			pstmt = DB.prepareStatement (sql, trxName);
			ResultSet rs = pstmt.executeQuery ();
					
			while (rs.next()){
				retorno = rs.getBigDecimal(0);
			}
		
		}catch (Exception ex){
			throw new AdempiereException(ex.getMessage());
		}
		return retorno;
	}

	/**Se calcula el cambio de las lineas en efectivo 
	 * OpenUp Ltda Issue#
	 * @author Sylvie Bouissa 30/04/2015
	 * @param ctx
	 * @param string
	 * @param string2
	 * @param get_ID
	 * @param get_TrxName
	 * @return
	 */
	public static BigDecimal calcularCambioMoneda(Properties ctx,
			String codMoneda, String cdoMedioPago, int Cov_LoadTicket_ID, String trxName) {
		PreparedStatement pstmt = null;
		BigDecimal retorno = BigDecimal.ZERO;
		String whereClause = X_Cov_Ticket_LineEfectivo.COLUMNNAME_codigomoneda+ " = '+codMoneda+' AND "
				+ X_Cov_Ticket_LineEfectivo.COLUMNNAME_Cov_CodigoMedioPago_ID +" = "+
				MCovCodigoMedioPago.forValue(ctx, cdoMedioPago, trxName).get_ID();
				;
		try{
			MCovTicketType venta = MCovTicketType.forValueAndHeader(ctx, "1", "Y", trxName);
			MCovTicketType dev = MCovTicketType.forValueAndHeader(ctx, "3", "Y", trxName);
			// Sumo las diferencias entre total entregado y el cambio de las lineas correspondientes
			String sql = "SELECT SUM(cambio) FROM Cov_Ticket_LineEfectivo WHERE "+whereClause+" AND Cov_Ticket_Header_ID IN"
					+ "(SELECT Cov_Ticket_Header_ID FROM Cov_Ticket_Header WHERE Cov_LoadTicket_ID = " +Cov_LoadTicket_ID +
					" AND estadoticket = 'F' AND Cov_ticketType_ID IN ("+venta.get_ID()+","+dev.get_ID()+"))";
		
			pstmt = DB.prepareStatement (sql, trxName);
			ResultSet rs = pstmt.executeQuery ();
					
			while (rs.next()){
				retorno = rs.getBigDecimal(0);
			}
		
		}catch (Exception ex){
			throw new AdempiereException(ex.getMessage());
		}
		return retorno;
	}

}
