/**
 * CreateFromTransporteFact.java
 * 20/01/2011
 */
package org.compiere.grid;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Vector;
import java.util.logging.Level;

import org.compiere.minigrid.IMiniTable;
import org.compiere.model.GridTab;
import org.compiere.model.MInvoice;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Msg;
import org.openup.model.MAsignaTransporteFact;
import org.openup.model.MAsignaTransporteHdr;

/**
 * OpenUp.
 * CreateFromTransporteFact
 * Descripcion :
 * @author Gabriel Vila
 * Fecha : 20/01/2011
 */
public class CreateFromTransporteFact extends CreateFrom {

	/**
	 * Constructor
	 * @param gridTab
	 */
	public CreateFromTransporteFact(GridTab gridTab) {
		super(gridTab);
		log.info(gridTab.toString());
	}

	/* (non-Javadoc)
	 * @see org.compiere.grid.CreateFrom#dynInit()
	 */
	@Override
	public boolean dynInit() throws Exception {
		log.config("");
		setTitle("Seleccion de Facturas");
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.grid.CreateFrom#info()
	 */
	@Override
	public void info() {
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
				// ID de la Factura Seleccionada
				int idFactura = (Integer)miniTable.getValueAt(i, 9);
				MInvoice fact = new MInvoice(Env.getCtx(), idFactura, null);
				
				// Nueva linea de factura asociada a asignacion de transporte
				MAsignaTransporteFact linea = new MAsignaTransporteFact(Env.getCtx(), 0, trxName);
				linea.setUY_AsignaTransporteHdr_ID(UY_AsignaTransporteHdr_ID);
				linea.setC_Invoice_ID(idFactura);
				linea.setC_BPartner_ID(fact.getC_BPartner_ID());
				linea.setC_Order_ID(fact.getC_Order_ID());

				// OpenUp. Gabriel Vila. 24/03/2011. Issue #487
				//linea.setuy_cantbultos(fact.getuy_cantbultos());
				linea.setuy_cantbultos(fact.getCantidadBultos());
				linea.setuy_cantbultos_manual(fact.getuy_cantbultos_manual());
				// Fin OpenUp
				
				// Guardo linea en DB
				if (!linea.save())
					log.log(Level.SEVERE, "No se pudo guardar la linea de factura en asignacion de transporte : " + i);

			}   //   if selected
		}   //  for all rows
		
		return true;
	}

	public String getSQLWhere(Object BPartner, Object Factura, Object DateFrom, Object DateTo,
							  Object zonaReparto1, Object zonaReparto2, Object zonaReparto3)
	{
		StringBuffer sql = new StringBuffer(" WHERE fhdr.IsSOTrx='Y' AND fhdr.Processed='Y' AND fhdr.DocStatus IN ('CO')"); 
		
	    sql.append( " AND NOT EXISTS (SELECT * FROM UY_AsignaTransporteFact line " 
			      + " WHERE line.C_Invoice_ID = fhdr.C_Invoice_ID)");
    
		if (BPartner != null){
			Integer idCli = (Integer)BPartner;
			sql.append(" AND fhdr.C_BPartner_ID=" + idCli.intValue());
		}
			
		if (Factura != null){
			Integer idFactura = (Integer)Factura;
			sql.append(" AND fhdr.c_invoice_id=" + idFactura.intValue());
		}
			
		if (DateFrom != null || DateTo != null)
		{
			Timestamp from = (Timestamp) DateFrom;
			Timestamp to = (Timestamp) DateTo;
			if (from == null && to != null) {
				sql.append(" AND TRUNC(fhdr.DateInvoiced) <='" + to + "'");
			}				
			else if (from != null && to == null)
				sql.append(" AND TRUNC(fhdr.DateInvoiced) >='" + from + "'");
			else if (from != null && to != null)
				sql.append(" AND TRUNC(fhdr.DateInvoiced) BETWEEN '" + from + "' AND '" +  to + "'");
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
	
	
	protected Vector<Vector<Object>> getData(Object BPartner, Object Factura, Object DateFrom, Object DateTo,
											 Object zonaReparto1, Object zonaReparto2, Object zonaReparto3)
	{
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		
		String sql = " SELECT fhdr.DateInvoiced, fhdr.DocumentNo, fhdr.C_BPartner_ID, bp.name as nombrecliente, bploc.name as sucursal," +
					 " (coalesce(loca.address1,'') || coalesce(loca.address2,'')) as direccion, " +
					 " pedido.DocumentNo as nropedido, pedido.DateOrdered as fechapedido, pedido.DatePromised as fechaentrega, " +
					 " fhdr.C_Invoice_ID " +
					 " FROM C_Invoice fhdr " +
					 " INNER JOIN C_BPartner bp ON fhdr.C_BPartner_ID = bp.C_BPartner_ID " +
					 " INNER JOIN C_Order pedido ON fhdr.C_Order_ID = pedido.C_Order_ID " +
					 " INNER JOIN C_BPartner_Location bploc ON pedido.C_BPartner_Location_ID = bploc.C_BPartner_Location_ID " +
					 " INNER JOIN C_Location loca ON bploc.C_Location_ID = loca.C_Location_ID ";

		sql = sql + getSQLWhere(BPartner, Factura, DateFrom, DateTo, zonaReparto1, zonaReparto2, zonaReparto3) + " ORDER BY fhdr.DateInvoiced";

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql.toString(), null);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				Vector<Object> line = new Vector<Object>(8);
				line.add(new Boolean(false));       		//  0-Selection
				line.add(rs.getTimestamp("DateInvoiced"));       //  1-Fecha
				line.add(rs.getString("DocumentNo"));      		//  2-Numero
				KeyNamePair bpartner = new KeyNamePair(rs.getInt("C_BPartner_ID"), rs.getString("nombrecliente"));
				line.add(bpartner);      	//  3-Cliente
				line.add(rs.getString("sucursal"));      	//  4-Sucursal
				line.add(rs.getString("direccion"));      	//  5-Direccion
				line.add(rs.getString("nropedido"));      		// 6-Numero Pedido
				line.add(rs.getTimestamp("fechapedido"));       //  7-Fecha Pedido
				line.add(rs.getTimestamp("fechaentrega"));       //  8-Fecha Entrega			
				line.add(rs.getInt("C_Invoice_ID"));    //  9-ID
				
				
				data.add(line);
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
		miniTable.setColumnClass(0, Boolean.class, false);      //  0-Selection
		miniTable.setColumnClass(1, Timestamp.class, true);     //  1-Fecha
		miniTable.setColumnClass(2, String.class, true);    	//  2-Numero
		miniTable.setColumnClass(3, String.class, true);    	//  3-Cliente
		miniTable.setColumnClass(4, String.class, true);    	//  4-Sucursal
		miniTable.setColumnClass(5, String.class, true);    	//  4-Direccion
		miniTable.setColumnClass(6, String.class, true);        //  5-NroPedido
		miniTable.setColumnClass(7, Timestamp.class, true);    //  6-FechaPedido
		miniTable.setColumnClass(8, Timestamp.class, true);    //  7-FechaEntrega
		miniTable.setColumnClass(9, Integer.class, true);    	//  8-ID
		//  Table UI
		miniTable.autoSize();
	}

	protected Vector<String> getOISColumnNames()
	{
		//  Header Info
		Vector<String> columnNames = new Vector<String>(6);
		columnNames.add(Msg.getMsg(Env.getCtx(), "Select"));
		columnNames.add("Fecha Factura");
		columnNames.add("Numero Factura");
		columnNames.add("Cliente");
		columnNames.add("Sucursal");
		columnNames.add("Direccion");
		columnNames.add("Numero Pedido");
		columnNames.add("Fecha Pedido");
		columnNames.add("Fecha Entrega");
		columnNames.add("ID Factura");
	    
	    return columnNames;
	}

}
