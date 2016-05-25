/**
 * CalloutLogistica.java
 * 02/12/2010
 */
package org.openup.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;

import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.util.DB;

/**
 * OpenUp.
 * CalloutLogistica
 * Descripcion : Callouts del modulo de Logistica.
 * @author Gabriel Vila
 * Fecha : 02/12/2010
 */
public class CalloutLogistica extends CalloutEngine {

	/**
	 * Constructor
	 */
	public CalloutLogistica() {
	}

	/**
	 * OpenUp.	
	 * Descripcion : 
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 * @author  Gabriel Vila 
	 * Fecha : 02/12/2010
	 */
	public String getAsignacionTransporteInfo(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value){

		try{

			if (value==null) return "";
			if ((Integer)value == 0) return "";
			
			if (!mTab.getKeyColumnName().equalsIgnoreCase(X_UY_PickingHdr.COLUMNNAME_UY_PickingHdr_ID)) return "";
			
			// Verifico que este dentro de la ventana de confirmacion de la orden
						
			// Obtengo informacion del cabezal de la asignacion de transporte
			MAsignaTransporteHdr hdr = new MAsignaTransporteHdr(ctx, (Integer)value, null);
			
			// Si obtuve datos
			if (hdr.get_ID() > 0){
				
				// Cargo campos de la ventana con la informacion de la orden
				mTab.setValue("M_Shipper_ID", hdr.getM_Shipper_ID());
			
				// Save pesta�a actual (cabezal)
				mTab.dataSave(true);
				
				// Refresca pesta�a actual
				mTab.dataRefresh();
				
				// Obtengo lineas para picking
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				String sql ="";
				try
				{
					// Cargo sumarizacion de cantidades por producto-uom
					sql= " SELECT rl.m_product_id, rl.c_uom_id, SUM(rl.QtyEntered) as cantidad " +
						 " FROM UY_ReservaPedidoLine rl " +
						 " INNER JOIN UY_AsignaTransporteLine atl ON rl.UY_ReservaPedidoHdr_ID = atl.UY_ReservaPedidoHdr_ID " +
						 " WHERE atl.UY_AsignaTransporteHdr_ID = ?" +
						 " GROUP BY rl.m_product_id, rl.c_uom_id " +
						 " HAVING SUM(rl.QtyEntered)>0";
					pstmt = DB.prepareStatement(sql, null);
					pstmt.setInt(1, hdr.getUY_AsignaTransporteHdr_ID());
					rs = pstmt.executeQuery();
					while (rs.next())
					{
						MPickingLine line = new MPickingLine(ctx, 0, null);
						line.setUY_PickingHdr_ID((Integer)mTab.getValue("UY_PickingHdr_ID"));
						line.setM_Product_ID(rs.getInt("m_product_id"));
						line.setC_UOM_ID(rs.getInt("c_uom_id"));
						line.setQtyEntered(rs.getBigDecimal("cantidad"));
						line.saveEx();
					}
					
					DB.close(rs, pstmt);
					rs = null; pstmt = null;

					// Cargo detalle de cantidades por reserva - producto - uom
					sql= " SELECT rl.UY_ReservaPedidoHdr_ID, rl.UY_ReservaPedidoLine_ID, rl.m_product_id, rl.c_uom_id, rl.QtyEntered, " +
						 " rl.QtyReserved, pickline.uy_pickingline_id " +
						 " FROM UY_ReservaPedidoLine rl " +
						 " INNER JOIN UY_AsignaTransporteLine atl ON rl.UY_ReservaPedidoHdr_ID = atl.UY_ReservaPedidoHdr_ID " +
						 " INNER JOIN uy_pickingline pickline on rl.m_product_id = pickline.m_product_id and rl.c_uom_id = pickline.c_uom_id " +
						 " WHERE atl.UY_AsignaTransporteHdr_ID = ? " +
						 " and pickline.uy_pickinghdr_id = ? " +
						 " order by pickline.uy_pickingline_id";
					
					pstmt = DB.prepareStatement(sql, null);
					pstmt.setInt(1, hdr.getUY_AsignaTransporteHdr_ID());
					pstmt.setInt(2, (Integer)mTab.getValue("UY_PickingHdr_ID"));
					rs = pstmt.executeQuery();
					while (rs.next())
					{
						MPickingLineDetail lineDet = new MPickingLineDetail(ctx, 0, null);

						lineDet.setM_Product_ID(rs.getInt("m_product_id"));
						lineDet.setC_UOM_ID(rs.getInt("c_uom_id"));
						lineDet.setUY_ReservaPedidoHdr_ID(rs.getInt("UY_ReservaPedidoHdr_ID"));
						lineDet.setUY_ReservaPedidoLine_ID(rs.getInt("UY_ReservaPedidoLine_ID"));
						lineDet.setQtyEntered(rs.getBigDecimal("QtyEntered"));
						lineDet.setQtyReserved(rs.getBigDecimal("QtyReserved"));
						lineDet.setuy_qtyentered_original(rs.getBigDecimal("QtyEntered"));
						lineDet.setuy_qtyreserved_original(rs.getBigDecimal("QtyReserved"));
						lineDet.setUY_PickingLine_ID(rs.getBigDecimal("uy_pickingline_id"));
						lineDet.saveEx();
					}
					
				}
				catch (SQLException e)
				{
					log.log(Level.SEVERE, sql, e);
				}
				finally
				{
					DB.close(rs, pstmt);
					rs = null; pstmt = null;
				}
			}
			else{
				return "No se pudo obtener la informaci�n de la Asignacion de Transporte : " + (Integer)value; 
			}

			// Refresca pesta�a actual
			mTab.dataRefresh();

			return "";
		}
		catch (Exception e){
			log.log(Level.SEVERE, "No se pudo obtener informaci�n de la Asignacion de Transporte", e);
			return e.getMessage();
		}
	}

	
	public String getProcesoFactReservaInfo(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value){

		try{

			if (value==null) return "";
			if (!mTab.getKeyColumnName().equalsIgnoreCase(X_UY_ProcesoFactmasHdr.COLUMNNAME_UY_ProcesoFactmasHdr_ID)) return "";
						
			// Obtengo informacion del cabezal de la asignacion de transporte
			MAsignaTransporteHdr hdr = new MAsignaTransporteHdr(ctx, (Integer)value, null);
			
			// Si obtuve datos
			if (hdr.get_ID() > 0){
				
				// Cargo campos de la ventana con la informacion de la orden
				mTab.setValue("M_Shipper_ID", hdr.getM_Shipper_ID());

				boolean primerRegistro = true;
				
				// Obtengo cabezales de reservas
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				String sql ="";
				try
				{
					sql= " SELECT res.*, tline.UY_AsignaTransporteHdr_ID, tline.UY_AsignaTransporteLine_ID, pick.UY_PickingHdr_ID " +
					 " FROM UY_ReservaPedidoHdr res " +
					 " INNER JOIN C_BPartner bp ON res.C_BPartner_ID = bp.C_BPartner_ID " +
					 " INNER JOIN C_Order pedido ON res.C_Order_ID = pedido.C_Order_ID " +
					 " INNER JOIN UY_AsignaTransporteLine tline ON res.UY_ReservaPedidoHdr_ID = tline.UY_ReservaPedidoHdr_ID " +
					 " INNER JOIN UY_AsignaTransporteHdr thdr ON tline.UY_AsignaTransporteHdr_ID = thdr.UY_AsignaTransporteHdr_ID " +
					 " INNER JOIN UY_PickingHdr pick ON thdr.UY_AsignaTransporteHdr_ID = pick.UY_AsignaTransporteHdr_ID " +
					 " WHERE thdr.UY_AsignaTransporteHdr_ID = ?" +
					 " AND thdr.DocStatus IN ('WC') " + 
					 " AND pick.DocStatus IN ('CO') " + 
					 " AND NOT EXISTS (SELECT * FROM UY_ProcesoFactmasLine line " + 
				     				 " WHERE line.UY_ReservaPedidoHdr_ID = res.UY_ReservaPedidoHdr_ID AND line.c_invoice_id is not null)" ;

					pstmt = DB.prepareStatement(sql, null);
					pstmt.setInt(1, hdr.getUY_AsignaTransporteHdr_ID());
					rs = pstmt.executeQuery();
					while (rs.next())
					{
						// Hago esta trampita ya que el cabezal tiene que estar grabado para poder obtener
						// el ID del mismo y ponerlo en la linea.
						if (primerRegistro){
							primerRegistro = false;
							mTab.setValue("UY_PickingHDr_ID", rs.getInt("UY_PickingHdr_ID"));
							mTab.dataSave(true);
							mTab.dataRefresh();
						}
						
						// Verifico que la reserva tenga lineas antes de agregarla
						MReservaPedidoHdr resHdr = new MReservaPedidoHdr(ctx, rs.getInt("UY_ReservaPedidoHdr_ID"), null);
						MReservaPedidoLine[] lines = resHdr.getLinesWithQtyReserved();
						if (lines != null){
							if (lines.length > 0){
								MProcesoFactmasLine line = new MProcesoFactmasLine(ctx, 0, null);
								line.setUY_AsignaTransporteHdr_ID(rs.getInt("UY_AsignaTransporteHdr_ID"));
								line.setUY_AsignaTransporteLine_ID(rs.getInt("UY_AsignaTransporteLine_ID"));
								line.setUY_ProcesoFactmasHdr_ID((Integer)mTab.getValue("UY_ProcesoFactmasHdr_ID"));
								line.setUY_ReservaPedidoHdr_ID(rs.getInt("UY_ReservaPedidoHdr_ID"));
								line.setC_Order_ID(rs.getInt("C_Order_ID"));
								line.setC_BPartner_ID(rs.getInt("C_BPartner_ID"));
								//line.setDatePromised(rs.getTimestamp("DatePromised"));
								line.setdatereserved(rs.getTimestamp("DateReserved"));
								
								line.saveEx();
							}
						}
						
					}
					
				}
				catch (SQLException e)
				{
					log.log(Level.SEVERE, sql, e);
				}
				finally
				{
					DB.close(rs, pstmt);
					rs = null; pstmt = null;
				}
			}
			else{
				return "No se pudo obtener la informaci�n de la Asignacion de Transporte : " + (Integer)value; 
			}

			// Refresca pesta�a actual
			mTab.dataRefresh();

			return "";
		}
		catch (Exception e){
			log.log(Level.SEVERE, "No se pudo obtener informaci�n de la Asignacion de Transporte", e);
			return e.getMessage();
		}
	}

	/**
	 * 
	 * OpenUp. #950	
	 * Descripcion : Setea por defecto la cantidad de bultos a pagar segun las facturas del ATR, tambien se admitira valor cero
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 * @author  Nicolas Sarlabos
	 * Fecha : 29/12/2011
	 */

	public String setBultosAPagar(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {

		try {

			if (value == null) return "";
			if ((Integer) value == 0) return "";
			if (!mTab.getKeyColumnName().equalsIgnoreCase(X_UY_CierreTransporteHdr.COLUMNNAME_UY_CierreTransporteHdr_ID)) return "";


			Integer id = (Integer) mTab.getValue("UY_AsignaTransporteHdr_ID");

			String sql = "SELECT sum(f.uy_cantbultos + f.uy_cantbultos_manual)" + " FROM uy_asignatransportefact f"
			+ " INNER JOIN uy_asignatransportehdr h ON f.uy_asignatransportehdr_id=h.uy_asignatransportehdr_id" + " WHERE h.uy_asignatransportehdr_id="
					+ id;

			Integer bultos = DB.getSQLValue(null, sql);

			mTab.setValue("uy_bultosapagar", bultos);
			

			return "";
		} catch (Exception e) {
			log.log(Level.SEVERE, "No se pudo actualizar cantidad de bultos a pagar.", e);
			return e.getMessage();
		}
	}

	
}
