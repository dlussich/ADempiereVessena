/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 04/06/2014
 */
package org.openup.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.openup.model.MAsignaTransporteHdr;
import org.openup.model.MAsignaTransporteLine;
import org.openup.model.MReservaPedidoHdr;

/**
 * org.openup.process - PLoadReserveATR
 * OpenUp Ltda. Issue #2174 
 * Description: Proceso de carga de reservas en un envio a franquiciado.
 * @author Gabriel Vila - 04/06/2014
 * @see
 */
public class PLoadReserveATR extends SvrProcess {

	private MAsignaTransporteHdr asigTransHdr = null;	
	
	/**
	 * Constructor.
	 */
	public PLoadReserveATR() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 04/06/2014
	 * @see
	 */
	@Override
	protected void prepare() {
		this.asigTransHdr = new MAsignaTransporteHdr(getCtx(), this.getRecord_ID(), get_TrxName());
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 * OpenUp Ltda. Issue # 
	 * @author Gabriel Vila - 04/06/2014
	 * @see
	 * @return
	 * @throws Exception
	 */
	@Override
	protected String doIt() throws Exception {

		String sql = "";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			sql = " SELECT res.DateTrx, res.DocumentNo, res.C_BPartner_ID, " +
					 " pedido.DocumentNo as nropedido, pedido.DateOrdered as fechapedido, pedido.DatePromised as fechaentrega, " +
					 " res.UY_ReservaPedidoHdr_ID, pedido.c_order_id, pedido.c_bpartner_location_id " +
					 " FROM UY_ReservaPedidoHdr res " +
					 " INNER JOIN C_Order pedido ON res.C_Order_ID = pedido.C_Order_ID " +
				     " WHERE res.DocStatus ='CO' " +
				     " AND pedido.C_BPartner_ID =" + this.asigTransHdr.get_ValueAsInt("C_BPartner_ID") +
				     " AND pedido.C_BPartner_Location_ID =" + this.asigTransHdr.get_ValueAsInt("C_BPartner_Location_ID") +
				     " AND NOT EXISTS (SELECT * FROM UY_AsignaTransporteLine line " +
				     " WHERE line.UY_ReservaPedidoHdr_ID = res.UY_ReservaPedidoHdr_ID) ";
			
			pstmt = DB.prepareStatement(sql.toString(), null);
			rs = pstmt.executeQuery();

			while (rs.next())
			{
				
				MReservaPedidoHdr reserva = new MReservaPedidoHdr(getCtx(), rs.getInt("UY_ReservaPedidoHdr_ID"), null);
				
				// Nueva linea de asignacion de transporte
				MAsignaTransporteLine linea = new MAsignaTransporteLine(getCtx(), 0, get_TrxName());
				linea.setUY_AsignaTransporteHdr_ID(this.asigTransHdr.get_ID());
				linea.setC_Order_ID(rs.getInt("c_order_id"));
				linea.setC_BPartner_ID(rs.getInt("c_bpartner_id"));
				linea.setDatePromised(rs.getTimestamp("fechaentrega"));
				linea.setdatereserved(rs.getTimestamp("datetrx"));
				linea.setUY_ReservaPedidoHdr_ID(reserva.get_ID());
				linea.setAmount(reserva.getTotalLines());
				linea.setC_BPartner_Location_ID(rs.getInt("c_bpartner_location_id"));
				linea.saveEx();
				
			}
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
		return "OK";
		
	}

}
