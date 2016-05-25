/**
 * CreateFromReserva.java
 * 01/12/2010
 */
package org.compiere.grid;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Vector;
import java.util.logging.Level;

import org.compiere.minigrid.IMiniTable;
import org.compiere.model.GridTab;
import org.compiere.model.MOrder;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Msg;
import org.openup.model.MAsignaTransporteHdr;
import org.openup.model.MAsignaTransporteLine;
import org.openup.model.MReservaPedidoHdr;

/**
 * OpenUp.
 * CreateFromReserva
 * Descripcion :
 * @author Gabriel Vila
 * Fecha : 01/12/2010
 */
public class CreateFromReserva extends CreateFrom {

	/**
	 * Constructor
	 * @param gridTab
	 */
	public CreateFromReserva(GridTab gridTab) {
		super(gridTab);
		log.info(gridTab.toString());
	}

	/* (non-Javadoc)
	 * @see org.compiere.grid.CreateFrom#dynInit()
	 */
	@Override
	public boolean dynInit() throws Exception {
		log.config("");
		setTitle("Seleccion de Reservas de Ordenes de Venta");
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.grid.CreateFrom#info()
	 */
	@Override
	public void info() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.compiere.grid.CreateFrom#save(org.compiere.minigrid.IMiniTable, java.lang.String)
	 */
	@Override
	public boolean save(IMiniTable miniTable, String trxName) {
		
		int UY_AsignaTransporteHdr_ID = ((Integer)getGridTab().getValue("UY_AsignaTransporteHdr_ID")).intValue();
		MAsignaTransporteHdr header = new MAsignaTransporteHdr(Env.getCtx(), UY_AsignaTransporteHdr_ID, trxName);
		
		log.config(header.toString());

		//  Lines
		for (int i = 0; i < miniTable.getRowCount(); i++)
		{
			if (((Boolean)miniTable.getValueAt(i, 0)).booleanValue())
			{	
				// ID de la Reserva Seleccionada
				int UY_ReservaPedidoHdr_ID = (Integer)miniTable.getValueAt(i, 12);
				MReservaPedidoHdr mreserva = new MReservaPedidoHdr(Env.getCtx(), UY_ReservaPedidoHdr_ID, null);
				
				// Nueva linea de asignacion de transporte
				MAsignaTransporteLine linea = new MAsignaTransporteLine(Env.getCtx(), 0, trxName);
				linea.setUY_AsignaTransporteHdr_ID(UY_AsignaTransporteHdr_ID);
				linea.setC_Order_ID(mreserva.getC_Order_ID());
				linea.setC_BPartner_ID(mreserva.getC_BPartner_ID());
				MOrder order = new MOrder(Env.getCtx(), mreserva.getC_Order_ID(), null);
				linea.setDatePromised(order.getDatePromised());
				linea.setdatereserved(mreserva.getdatereserved());
				linea.setUY_ReservaPedidoHdr_ID(UY_ReservaPedidoHdr_ID);
				//OpenUp Nicolas Sarlabos issue #853
				linea.setAmount(mreserva.getTotalLines());
				//fin OpenUp Nicolas Sarlabos issue #853
				//OpenUp Nicolas Sarlabos issue #823
				//guardo el Id de la sucursal del cliente
				linea.setC_BPartner_Location_ID(order.getC_BPartner_Location_ID());
				//fin OpenUp Nicolas Sarlabos issue #823
				// Guardo linea en DB
				if (!linea.save())
					log.log(Level.SEVERE, "No se pudo guardar la linea de asignacion de transporte : " + i);

			}   //   if selected
		}   //  for all rows

		// Actualizo informacion del cabezal a partir de informacion de las lineas
		//header.saveEx();
		
		return true;
	}

	/**************************************************************************
	 *	Construct SQL Where Clause and define parameters
	 *  (setParameters needs to set parameters)
	 *  Includes first AND
	 *  @return sql where clause
	 */
	public String getSQLWhere(Object BPartner, Object CanalVenta, Object DateFrom, Object DateTo,
							  Object zonaReparto1, Object zonaReparto2, Object zonaReparto3)
	{
		StringBuffer sql = new StringBuffer(" WHERE res.Processed='Y' AND res.DocStatus IN ('CO')"); 
		
	    sql.append( " AND NOT EXISTS (SELECT * FROM UY_AsignaTransporteLine line " 
			      + " WHERE line.UY_ReservaPedidoHdr_ID = res.UY_ReservaPedidoHdr_ID)");
    
		if (BPartner != null)
			sql.append(" AND res.C_BPartner_ID=?");
		if (CanalVenta != null)
			sql.append(" AND bp.uy_canalventas_id=?");
		if (DateFrom != null || DateTo != null)
		{
			Timestamp from = (Timestamp) DateFrom;
			Timestamp to = (Timestamp) DateTo;
			if (from == null && to != null)
				sql.append(" AND TRUNC(pedido.DatePromised) <= ?");
			else if (from != null && to == null)
				sql.append(" AND TRUNC(pedido.DatePromised) >= ?");
			else if (from != null && to != null)
				sql.append(" AND TRUNC(pedido.DatePromised) BETWEEN ? AND ?");
		}
		
		String whereZonas = "";
		Integer z1, z2, z3;

		if (zonaReparto1 != null) z1 = (Integer)zonaReparto1;
		if (zonaReparto2 != null) z2 = (Integer)zonaReparto2;
		if (zonaReparto3 != null) z3 = (Integer)zonaReparto3;

		if (zonaReparto1 != null) {
			z1 = (Integer)zonaReparto1;
			whereZonas = " AND bploc.uy_zonareparto_id IN (" + z1.intValue() + ")";
		}
		
		if (zonaReparto2 != null) {
			z2 = (Integer)zonaReparto2;
			if (!whereZonas.equalsIgnoreCase("")){
				z1 = (Integer)zonaReparto1;
				whereZonas = " AND bploc.uy_zonareparto_id IN (" + z1.intValue() + 
				 "," + z2.intValue() + ")";
			}
			else
				whereZonas = " AND bploc.uy_zonareparto_id IN (" + z2.intValue() + ")";
		}

		if (zonaReparto3 != null) {
			z3 = (Integer)zonaReparto3;
			if (whereZonas.equalsIgnoreCase(""))
				whereZonas = " AND bploc.uy_zonareparto_id IN (" + z3.intValue() + ")";
			else{
				if ( (zonaReparto1 != null) && (zonaReparto2 != null)){
					z1 = (Integer)zonaReparto1;
					z2 = (Integer)zonaReparto2;
					whereZonas = " AND bploc.uy_zonareparto_id IN (" + z1.intValue() + 
					 "," + z2.intValue() + 
					 "," + z3.intValue() + ")";
				}
				else if (zonaReparto1 != null){
					z1 = (Integer)zonaReparto1;
					whereZonas = " AND bploc.uy_zonareparto_id IN (" + z1.intValue() + 
					 "," + z3.intValue() + ")";

				}
				else if (zonaReparto2 != null){
					z2 = (Integer)zonaReparto2;
					whereZonas = " AND bploc.uy_zonareparto_id IN (" + z2.intValue() + 
					 "," + z3.intValue() + ")";
				}
			}
		}

		if (!whereZonas.equalsIgnoreCase("")){
			sql.append(whereZonas);
		}

		log.fine(sql.toString());
		return sql.toString();
	}	//	getSQLWhere
	
	/**
	 *  Set Parameters for Query.
	 *  (as defined in getSQLWhere)
	 *  @param pstmt statement
	 *  @param forCount for counting records
	 *  @throws SQLException
	 */
	void setParameters(PreparedStatement pstmt, boolean forCount, Object BPartner, Object CanalVenta, Object DateFrom, Object DateTo) throws SQLException
	{
		int index = 1;
		
		if (BPartner != null)
		{
			Integer bp = (Integer)BPartner;
			pstmt.setInt(index++, bp.intValue());
			log.fine("BPartner=" + bp);
		}
		
		if (CanalVenta != null)
		{
			Integer cv = (Integer)CanalVenta;
			pstmt.setInt(index++, cv.intValue());
			log.fine("Canal=" + cv);
		}

		if (DateFrom != null || DateTo != null)
		{
			Timestamp from = (Timestamp) DateFrom;
			Timestamp to = (Timestamp) DateTo;
			log.fine("Date From=" + from + ", To=" + to);
			if (from == null && to != null)
				pstmt.setTimestamp(index++, to);
			else if (from != null && to == null)
				pstmt.setTimestamp(index++, from);
			else if (from != null && to != null)
			{
				pstmt.setTimestamp(index++, from);
				pstmt.setTimestamp(index++, to);
			}
		}
	}   //  setParameters
	
	
	protected Vector<Vector<Object>> getData(Object BPartner, Object CanalVenta, Object DateFrom, Object DateTo,
											 Object zonaReparto1, Object zonaReparto2, Object zonaReparto3)
	{
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		
		String sql = " SELECT res.DateTrx, res.DocumentNo, res.C_BPartner_ID, bp.name as nombrecliente, bploc.name as sucursal," +
					 " (coalesce(loca.address1,'') || coalesce(loca.address2,'')) as direccion, " +
					 " pedido.DocumentNo as nropedido, pedido.DateOrdered as fechapedido, pedido.DatePromised as fechaentrega, " +
					 " res.UY_ReservaPedidoHdr_ID, zona.nombre as zname, pedido.c_order_id " +
					 " FROM UY_ReservaPedidoHdr res " +
					 " INNER JOIN C_BPartner bp ON res.C_BPartner_ID = bp.C_BPartner_ID " +
					 " INNER JOIN C_Order pedido ON res.C_Order_ID = pedido.C_Order_ID " +
					 " INNER JOIN C_BPartner_Location bploc ON pedido.C_BPartner_Location_ID = bploc.C_BPartner_Location_ID " +
					 " INNER JOIN UY_ZonaReparto zona ON bploc.uy_zonareparto_id = zona.uy_zonareparto_id " +
					 " INNER JOIN C_Location loca ON bploc.C_Location_ID = loca.C_Location_ID ";

		sql = sql + getSQLWhere(BPartner, CanalVenta, DateFrom, DateTo, zonaReparto1, zonaReparto2, zonaReparto3) + " ORDER BY res.DateTrx";

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql.toString(), null);
			setParameters(pstmt, false, BPartner, CanalVenta, DateFrom, DateTo);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				// OpenUp Nicolas Sarlabos #949 27/12/2011, se modifica orden de
				// campos
				Vector<Object> line = new Vector<Object>(11);
				line.add(new Boolean(false));       							// 0-Selection
				KeyNamePair bpartner = new KeyNamePair(rs.getInt("C_BPartner_ID"), rs.getString("nombrecliente"));
				line.add(bpartner);      										// 1-Cliente
				line.add(rs.getString("sucursal"));      						// 2-Sucursal
				line.add(rs.getString("direccion"));      						// 3-Direccion
				line.add(rs.getString("zname"));    							// 4-ZonaReparto
				
				MReservaPedidoHdr resHdr = new MReservaPedidoHdr(Env.getCtx(), rs.getInt("UY_ReservaPedidoHdr_ID"), null);
				line.add(resHdr.getCantidadBultos()); 						// 5-cantidad de bultos
				//OpenUp Nicolas Sarlabos issue #853 26/09/2011
				// se agrega columna Importe Reserva en la ventana Seleccion de
				// Reservas
				line.add(resHdr.getTotalLines()); // 6-Importe Reserva
				//fin OpenUp Nicolas Sarlabos issue #853 26/09/2011
				line.add(rs.getString("DocumentNo")); // 7-Numero Reserva
				line.add(rs.getTimestamp("DateTrx")); // 8-Fecha Reserva
				line.add(rs.getTimestamp("fechapedido")); // 9-Fecha Pedido
				line.add(rs.getTimestamp("fechaentrega")); // 10-Fecha Entrega
				line.add(rs.getString("nropedido"));      						// 11-Numero Pedido
				line.add(rs.getInt("UY_ReservaPedidoHdr_ID"));    				// 12-ID
				
				data.add(line);
				// Fin OpenUp Nicolas Sarlabos #949 27/12/2011
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
		
		return data;
	}

	protected void configureMiniTable (IMiniTable miniTable)
	{
		// OpenUp Nicolas Sarlabos #949 27/12/2011, se modifica orden de campos
		miniTable.setColumnClass(0, Boolean.class, false);      //  0-Selection
		miniTable.setColumnClass(1, String.class, true);    	//  1-Cliente
		miniTable.setColumnClass(2, String.class, true);    	//  2-Sucursal
		miniTable.setColumnClass(3, String.class, true);    	//  3-Direccion
		miniTable.setColumnClass(4, String.class, true);    	//  4-ZonaReparto
		miniTable.setColumnClass(5, BigDecimal.class, true);    //  5-CantidadBultos
		//OpenUp Nicolas Sarlabos issue #853 26/09/2011
		//se agrega columna Importe Reserva en la venta Seleccion de Reservas
		miniTable.setColumnClass(6, BigDecimal.class, true); // 6-Importe
																// Reserva
		//fin OpenUp Nicolas Sarlabos issue #853 26/09/2011
		miniTable.setColumnClass(7, String.class, true); // 7-Numero Reserva
		miniTable.setColumnClass(8, Timestamp.class, true); // 8-Fecha Reserva
		miniTable.setColumnClass(9, Timestamp.class, true); // 9-FechaPedido
		miniTable.setColumnClass(10, Timestamp.class, true); // 10-FechaEntrega
		miniTable.setColumnClass(11, String.class, true);       //  11-NroPedido
		miniTable.setColumnClass(12, Integer.class, true);    	//  12-ID Reserva
		//  Table UI
		miniTable.autoSize();
		// Fin OpenUp Nicolas Sarlabos #949 27/12/2011
	}

	protected Vector<String> getOISColumnNames()
	{
		//  Header Info
		// OpenUp Nicolas Sarlabos #949 27/12/2011, se modifica orden de campos
		Vector<String> columnNames = new Vector<String>(12);
		columnNames.add(Msg.getMsg(Env.getCtx(), "Select"));
		columnNames.add("Cliente");
		columnNames.add("Sucursal");
		columnNames.add("Direccion");
		columnNames.add("Zona Reparto");
		columnNames.add("Cantidad Bultos");
		//OpenUp Nicolas Sarlabos issue #853 26/09/2011
		//se agrega columna Importe Reserva en la venta Seleccion de Reservas
		columnNames.add("Importe Reserva");
		//fin OpenUp Nicolas Sarlabos issue #853 26/09/2011
		columnNames.add("Numero Reserva");
		columnNames.add("Fecha Reserva");
		columnNames.add("Fecha Pedido");
		columnNames.add("Fecha Entrega");
		columnNames.add("Numero Pedido");
		columnNames.add("ID Reserva");
	    
	    return columnNames;
		// Fin OpenUp Nicolas Sarlabos #949 27/12/2011
	}
	
}
