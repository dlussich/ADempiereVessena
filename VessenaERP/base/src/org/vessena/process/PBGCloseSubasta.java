package org.openup.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.compiere.model.I_AD_Client;
import org.compiere.model.MClient;
import org.compiere.model.Query;
import org.compiere.model.X_AD_Client;
import org.compiere.process.DocumentEngine;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.openup.model.MBGAution;
import org.openup.model.MBGAutionBid;
import org.openup.model.MBGAutionReq;
import org.openup.model.MBGCommission;
import org.openup.model.MBGContract;
import org.openup.util.BagsaNotifications;

/**
 * org.openup.process - PBGCloseSubasta
 * OpenUp Ltda. Issue #4206
 * Description: Clase encargada de cerrar las subastas abiertas que no tienen contrato ni adjudicacion establecida
 * @author Raul Capecce - 17/08/2015
 * @see
 */
public class PBGCloseSubasta extends SvrProcess{

	public enum SubastaType {
		Compra,
		Venta
	}
	
	@Override
	protected void prepare() {
		
	}

	@Override
	protected String doIt() throws Exception {
		
		notificarAperturaDeSubastas();
		
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
//		String sqlContrato = "SELECT subasta.UY_BG_AutionReq_ID " +
//				"FROM UY_BG_AutionReq AS subasta " +
//				"WHERE subasta.processedAutomatically = 'N' "+
//				"AND subasta.UY_BG_AutionReq_ID NOT IN ( " +
//				"   SELECT contrato.UY_BG_AutionReq_ID FROM UY_BG_Contract AS contrato WHERE contrato.UY_BG_AutionReq_ID IS NOT NULL " +
//				") ";
//		String sqlAdjudicacion = "SELECT subasta.UY_BG_AutionReq_ID " +
//				"FROM UY_BG_AutionReq AS subasta " +
//				"WHERE subasta.processedAutomatically = 'N' " +
//				"AND subasta.UY_BG_AutionReq_ID NOT IN ( " +
//				"   SELECT adjudicacion.UY_BG_AutionReq_ID FROM UY_BG_Aution AS adjudicacion WHERE adjudicacion.UY_BG_AutionReq_ID IS NOT NULL " +
//				") ";
		
		String sqlSubastas = "SELECT subasta.UY_BG_AutionReq_ID " +
				"FROM UY_BG_AutionReq AS subasta " +
				"WHERE subasta.processedAutomatically = 'N' " +
				"AND ( " +
				"  (to_date(to_char(subasta.dateEnd, 'YYYY/MM/DD'), 'YYYY/MM/DD') < current_date ) " +
				"  OR ( " +
				"    to_date(to_char(subasta.dateEnd, 'YYYY/MM/DD'), 'YYYY/MM/DD') = current_date " + 
				"    AND subasta.time_end::time < current_timestamp::time " +
				"  ) " +
				") ";
		
		try {
			
			int idComision = 0;
			BigDecimal porcentajeComision = Env.ZERO;
			// Definición de porcentaje de comision
			String sqlCountIsDefault = "SELECT COUNT(*) FROM UY_BG_Commission WHERE isDefault='Y'";
			if (DB.getSQLValueEx(null, sqlCountIsDefault) > 0) {
				String sqlGetDefaultCommission = "SELECT UY_BG_Commission_ID FROM UY_BG_Commission WHERE isDefault='Y'";
				MBGCommission commission = new MBGCommission(getCtx(), DB.getSQLValueEx(null, sqlGetDefaultCommission), null);
				if ((commission != null) && (commission.get_ID() > 0)){
					
					if (commission.getAmount() != null) {
						porcentajeComision = commission.getAmount();
						idComision = commission.get_ID();
					}
												
				}
			}
			
			
			//DB.getSQLValueString(this.get_TrxName(), sqlSubastas);
			pstmt = DB.prepareStatement (sqlSubastas, null);
			rs = pstmt.executeQuery ();
	
			while (rs.next()){
				// Obtengo la subasta
				MBGAutionReq subasta = new MBGAutionReq(getCtx(), rs.getInt("UY_BG_AutionReq_ID"), get_TrxName());
				
				
				// Obtengo la puja mas alta o mas baja si supera el monto base
				MBGAutionBid puja = null;
				if (subasta.getC_DocType().getValue().equalsIgnoreCase("bgsubasta")) { // Subasta VENTA
					puja = superaPrecioBase(subasta, SubastaType.Venta);
				} else if (subasta.getC_DocType().getValue().equalsIgnoreCase("bgsubastapo")) { // Subasta COMPRA
					puja = superaPrecioBase(subasta, SubastaType.Compra);
				}
				
				
				
				// En caso de que supere el monto base creo un nuevo contrato y adjudicacion
				if (puja != null) {

					String sqlContrato = "SELECT COUNT(*) FROM UY_BG_Contract WHERE UY_BG_AutionReq_ID = " + rs.getInt("UY_BG_AutionReq_ID");
					if (DB.getSQLValueEx(null, sqlContrato) == 0) {
						
						MBGContract contrato = new MBGContract(getCtx(), 0, get_TrxName());
						contrato.setAD_Client_ID(getDefaultClient().get_ID());
						
						//contrato.setC_DocType_ID(MDocType.forValue(getCtx(), "bgctosubasta", get_TrxName()).get_ID());
						// A pedido de GV se quema el id del tipo de documento bgctosubasta debido a que como system administrator (cuando se corre el proceso como cron), no puede acceder a la tabla por el modelo
						contrato.setC_DocType_ID(1001059);

						contrato.setUY_BG_AutionReq_ID(subasta.get_ID());
						contrato.setUY_BG_AutionBid_ID(puja.get_ID());
						
						contrato.setUY_BG_Bursa_ID(subasta.getUY_BG_Bursa_ID());
						contrato.setDateTrx(puja.getDateTrx());
						contrato.setM_Product_ID(subasta.getM_Product_ID());
						contrato.setVolume(subasta.getQty());
						contrato.setC_UOM_ID(subasta.getC_UOM_ID());
						contrato.setUY_BG_Quality_ID(subasta.getUY_BG_Quality_ID());
						// Se carga despues | Forma de pago - paymentRuleType
						// Se carga despues | Nro de factura - factura 
						contrato.setPriceEntered(puja.getPrice());
						contrato.setAmt(puja.getPrice().multiply(subasta.getQty()));
						// Se carga despues | Fecha de pago - DatePrintered
						contrato.setDateDelivered(subasta.getDateDelivered());
						// Se carga despues | Lugar de Origen - LocationFrom
						contrato.setBodega(subasta.getBodega());
						contrato.setC_Region_ID_2(subasta.getC_Region_ID_2());
						contrato.setC_City_ID_2(subasta.getC_City_ID_2());
						contrato.setAddress2(subasta.getAddress2());
						contrato.setC_Region_ID(subasta.getC_Region_ID());
						contrato.setC_City_ID(subasta.getC_City_ID());
						contrato.setAddress1(subasta.getAddress2());
						// Se carga despues | Garnantia - AmtRetention
						
						contrato.setAD_User_ID(subasta.getAD_User_ID());
						contrato.setAD_User_ID_2(puja.getAD_User_ID());
						
						// obtengo el porcentaje de comision
						contrato.setAmtRetention2(puja.getPrice().multiply(subasta.getQty().multiply(porcentajeComision.divide(Env.ONEHUNDRED))));
						contrato.setUY_BG_Commission_ID(idComision);
						
						

						contrato.setDocStatus(DocumentEngine.STATUS_Drafted);
						contrato.setDocAction(DocumentEngine.ACTION_Complete);
						contrato.setProcessed(false);
						contrato.setProcessing(false);
						
						contrato.saveEx();
					}

					String sqlAdjudicacion = "SELECT COUNT(*) FROM UY_BG_Aution WHERE UY_BG_AutionReq_ID = " + rs.getInt("UY_BG_AutionReq_ID");
					if (DB.getSQLValueEx(null, sqlAdjudicacion) == 0) {
						
						MBGAution adjudicacion = new MBGAution(getCtx(), 0, get_TrxName());
						adjudicacion.setAD_Client_ID(getDefaultClient().get_ID());
						
						//adjudicacion.setC_DocType_ID(MDocType.forValue(getCtx(), "bgadjsub", get_TrxName()).get_ID());
						// A pedido de GV se quema el id del tipo de documento bgadjsub debido a que como system administrator (cuando se corre el proceso como cron), no puede acceder a la tabla por el modelo
						adjudicacion.setC_DocType_ID(1001055);

						adjudicacion.setUY_BG_AutionReq_ID(subasta.get_ID());
						adjudicacion.setUY_BG_AutionBid_ID(puja.get_ID());
						
						adjudicacion.setDateTrx(subasta.getDateEnd());
						adjudicacion.setM_Product_ID(subasta.getM_Product_ID());
						adjudicacion.setQty(BigDecimal.valueOf(DB.getSQLValueEx(null, "SELECT COUNT(DISTINCT(UY_BG_Bursa_ID)) FROM UY_BG_AutionBid WHERE UY_BG_AutionReq_ID = " + subasta.get_ID())));
						adjudicacion.setAmt(subasta.getQty());
						adjudicacion.setamt2(subasta.getQty());
						adjudicacion.setPriceEntered(subasta.getPrice());
						adjudicacion.setC_UOM_ID(subasta.getC_UOM_ID());
						adjudicacion.setPrice(puja.getPrice());
						adjudicacion.setamt3(puja.getPrice().multiply(subasta.getQty()));
						
						adjudicacion.setAmtRetention2(puja.getPrice().multiply(subasta.getQty().multiply(porcentajeComision.divide(Env.ONEHUNDRED))));
						
						adjudicacion.setDocStatus(DocumentEngine.STATUS_Drafted);
						adjudicacion.setDocAction(DocumentEngine.ACTION_Complete);
//						adjudicacion.setProcessed(true);
//						adjudicacion.setProcessing(false);
						
						adjudicacion.saveEx();
					}
					
				}
				subasta.setprocessedAutomatically(true);
				subasta.setDocStatus(DocumentEngine.STATUS_Closed);
				subasta.setDocAction(DocumentEngine.ACTION_None);
				subasta.saveEx();
				//OpenUp Ltda SBouissa 03-09-2015 //TODO SBT
				//Se debe notificar a los usuario moviles que la subasta se cerró
				notificarDipositivos("Cierre de subasta#Se cerro la subasta Nro. "
										+subasta.getDocumentNo()+"#Cierre de subasta Nro. "+subasta.getDocumentNo()+", sobre el producto "
										+subasta.getM_Product().getName());	
				if(null!=puja){
					notificarGanador("Subasta#Ganador subasta#Usted es el ganador de la subasta Nro. "+subasta.getDocumentNo()+".",puja.getAD_User_ID());
				}
			}
			rs.close();
			pstmt.close();
			
		} catch (Exception e) {
			throw e;
		} finally {
			rs.close();
			pstmt.close();
		}
		
		
		
		return "OK";
	}
	
	private MClient getDefaultClient() {		
		String whereClause = X_AD_Client.COLUMNNAME_AD_Client_ID + "!=0";
			
		MClient value = new Query(getCtx(), I_AD_Client.Table_Name, whereClause, null)
		.first();
			
		return value;
	}
	
	/**Notificar al ganador siempre y cuando este tenga usuario movil
	 * OpenUp Ltda Issue#
	 * @author SBouissa 4/9/2015
	 * @param string
	 * @param ad_User_ID
	 */
	private void notificarGanador(String mje, int ad_User_ID) {
		BagsaNotifications bg = new BagsaNotifications();
		bg.gcmSenderGanador(mje, ad_User_ID);
	}

	/**Se debe notificar a los usuarios móviles la apertura de cada subasta
	 * OpenUp Ltda Issue#
	 * @author SBouissa 3/9/2015
	 */
	private void notificarAperturaDeSubastas() {
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		String sqlSubastas = "SELECT subasta.UY_BG_AutionReq_ID " +
				"FROM UY_BG_AutionReq AS subasta " +
				" WHERE subasta.Notificado = 'N' AND subasta.processedAutomatically = 'N'" +
				" AND to_char(subasta.dateStart, 'YYYY/MM/DD') = to_char(current_date, 'YYYY/MM/DD')";
		
		try {
			
			pstmt = DB.prepareStatement (sqlSubastas, null);
			rs = pstmt.executeQuery ();
	
			while (rs.next()){
				// Obtengo la subasta
				MBGAutionReq subasta = new MBGAutionReq(getCtx(), rs.getInt("UY_BG_AutionReq_ID"), get_TrxName());
				notificarDipositivos("Apertura de subasta#Se abrio la subasta Nro. "+subasta.getDocumentNo()
						+"#Subasta Nro. "+subasta.getDocumentNo()+", sobre el producto "+subasta.getM_Product().getName().toString());
				subasta.set_ValueOfColumn("Notificado", true);
				subasta.saveEx();
				
			}
			rs.close();
			pstmt.close();
			
		} catch (Exception e) {
			try {
				throw e;
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			try {
				rs.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	/**Notifico a todos los usuarios que la subasta se cerró o se abrió
	 * OpenUp Ltda Issue#
	 * @author SBouissa 3/9/2015
	 * @param subasta
	 */
	private void notificarDipositivos(String datos) {
		//TODO SBT
		BagsaNotifications bg = new BagsaNotifications();
		bg.gcmSender(datos);
	}

	/***
	 * Obtiene la puja mas alta y que además supera el precio base
	 * OpenUp Ltda. Issue #4206 
	 * @author Raul Capecce - 17/08/2015
	 * @see
	 */
	private MBGAutionBid superaPrecioBase(MBGAutionReq subasta, SubastaType tipoSubasta) throws Exception {
		
		MBGAutionBid ret = null;
		
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		
		// Obtengo el monto base
		BigDecimal precioBase = subasta.getPrice();
		
		// Si no tiene pujas retorno null
		String sqlCantidadPujas = "SELECT COUNT(*) FROM UY_BG_AutionBid WHERE UY_BG_AutionReq_ID = " + subasta.get_ID();
		if (DB.getSQLValueEx(get_TrxName(), sqlCantidadPujas) == 0) {
			return null;
		}
		
		// Obtengo la puja con el monto mas alta y mas vieja (teniendo en cuenta que pueden haber pujas con el mismo monto)
		String sqlPujaMasAlta = "SELECT UY_BG_AutionBid_ID " +
				"FROM UY_BG_AutionBid " +
				"WHERE price = ( " +
				"  SELECT MAX(price) " +
				"  FROM UY_BG_AutionBid puja " +
				"  WHERE UY_BG_AutionReq_ID = " + subasta.get_ID() + " " +
				") AND UY_BG_AutionReq_ID = " + subasta.get_ID() + " " +
				"ORDER BY created ASC";
		// Obtengo la puja con el monto mas bajo y mas vieja (teniendo en cuenta que pueden haber pujas con el mismo monto)
		String sqlPujaMasBaja = "SELECT UY_BG_AutionBid_ID " +
				"FROM UY_BG_AutionBid " +
				"WHERE price = ( " +
				"  SELECT MIN(price) " +
				"  FROM UY_BG_AutionBid puja " +
				"  WHERE UY_BG_AutionReq_ID = " + subasta.get_ID() + " " +
				") AND UY_BG_AutionReq_ID = " + subasta.get_ID() + " " +
				"ORDER BY created ASC";
		
		try {
			if (tipoSubasta == SubastaType.Venta) {
				pstmt = DB.prepareStatement (sqlPujaMasAlta, null);
				rs = pstmt.executeQuery ();
				if (rs.next()) {
					MBGAutionBid puja = new MBGAutionBid(getCtx(), rs.getInt("UY_BG_AutionBid_ID"), get_TrxName());
					if (puja.getPrice().compareTo(subasta.getPrice()) > 0) {
						ret = puja;
					}
				}
			} else if (tipoSubasta == SubastaType.Compra) {
				pstmt = DB.prepareStatement (sqlPujaMasBaja, null);
				rs = pstmt.executeQuery ();
				if (rs.next()) {
					MBGAutionBid puja = new MBGAutionBid(getCtx(), rs.getInt("UY_BG_AutionBid_ID"), get_TrxName());
					if (puja.getPrice().compareTo(subasta.getPrice()) < 0) {
						ret = puja;
					}
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			rs.close();
			pstmt.close();
		}
		
		return ret;
	}

}
