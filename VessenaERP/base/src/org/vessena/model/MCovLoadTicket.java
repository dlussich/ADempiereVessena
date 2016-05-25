/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 26/02/2015
 */
package org.openup.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * org.openup.model - MCov_LoadTicket
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Gabriel Vila - 26/02/2015
 * @see
 */
public class MCovLoadTicket extends X_Cov_LoadTicket {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1009785718654014449L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param Cov_LoadTicket_ID
	 * @param trxName
	 */
	public MCovLoadTicket(Properties ctx, int Cov_LoadTicket_ID, String trxName) {
		super(ctx, Cov_LoadTicket_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MCovLoadTicket(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	
	//Metodo que usaban anteriormente
	//int headerId, String numLinea, String tipoLinea, String timestamplinea, String totalLine,String codMedioDePago,String codMoneda
	//String TotalMedioPagoMoneda,String TotalMedioPagoMonedaMonedaReferencia,String TotalPagado,String TotalPagadoMonedaReferencia,String Cambio,
	//String TipoOperacion,String LineaUltimoPago,String AutorizaSupervisora,String CodigoSupervisora,String LineaCancelada
	// Linea de venta de item..tienen un producto etcetc
//	private void saveLine1(int headerId,String numLinea, String tipoLinea, String timestamplinea,
//			
//			
//			String codProd, String qProd, String unitPrice, String totalLine,String codMedioPago){
//		MCovTicketLine objLine = new MCovTicketLine(getCtx(), 0, get_TrxName());
//		
//		objLine.setCov_Ticket_Header_ID(headerId);
//		
//		if(!(tipoLinea.equals("1"))){
//			codProd = "0"; qProd="0";unitPrice="0";unitPrice="0";
//		}
//		if(!codProd.equals("")){
//			objLine.setcodigoarticulo(Integer.valueOf(codProd));
//		}
//		if(!qProd.equals("")){
//			objLine.setcantidad(Integer.valueOf(qProd));
//		}
//		if(!unitPrice.equals("")){
//			objLine.setpreciounitario(new BigDecimal(Double.valueOf(unitPrice)));
//		}
//		if(!totalLine.equals("")){
//			objLine.setpreciodescuentototal(new BigDecimal(Double.valueOf(totalLine)));
//		}
//		if(!tipoLinea.equals("")){
//			objLine.set_ValueOfColumn("TipoLinea", tipoLinea);			
//		}
//		if(!codMedioPago.equals("")){
//			objLine.set_ValueOfColumn("CodigoMedioDePago", codMedioPago);
//		}
//
//		objLine.saveEx(get_TrxName());
//	}
	
}
