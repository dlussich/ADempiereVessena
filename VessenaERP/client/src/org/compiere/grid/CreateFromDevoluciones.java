/**
 * CreateFromReserva.java
 * 01/12/2010
 */
package org.compiere.grid;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Vector;
import java.util.logging.Level;

import org.compiere.grid.ed.VCheckBox;
import org.compiere.minigrid.IMiniTable;
import org.compiere.model.GridTab;
import org.compiere.model.MInOut;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Msg;
import org.openup.model.MAsignaTransporteDevol;
import org.openup.model.MAsignaTransporteHdr;


/**
 * OpenUp.
 * CreateFromReserva
 * Descripcion :
 * @author Gabriel Vila
 * Fecha : 01/12/2010
 */
public class CreateFromDevoluciones extends CreateFrom {

	/**
	 * Constructor
	 * @param gridTab
	 */
	public CreateFromDevoluciones(GridTab gridTab) {
		super(gridTab);
		log.info(gridTab.toString());
	}

	/* (non-Javadoc)
	 * @see org.compiere.grid.CreateFrom#dynInit()
	 */
	@Override
	public boolean dynInit() throws Exception {
		log.config("");
		setTitle("Seleccion de Devoluciones de Clientes");
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
				// ID de la Devolucion Seleccionada
				int inoutID = (Integer)miniTable.getValueAt(i, 6);
				MInOut devol = new MInOut(Env.getCtx(), inoutID, null);
				
				// Nueva linea de asignacion de transporte
				MAsignaTransporteDevol linea = new MAsignaTransporteDevol(Env.getCtx(), 0, trxName);
				linea.setUY_AsignaTransporteHdr_ID(UY_AsignaTransporteHdr_ID);
				linea.setM_InOut_ID(inoutID);
				linea.setC_BPartner_ID(devol.getC_BPartner_ID());
				//OpenUp Nicolas Sarlabos issue #823
				//guardo el Id de la sucursal del cliente
				linea.setC_BPartner_Location_ID(devol.getC_BPartner_Location_ID());
				//fin OpenUp Nicolas Sarlabos issue #823
				linea.saveEx(trxName);
				
				// Guardo informacion de asignacion en la devolucion y
				// asigno la devolucion
				devol.setUY_AsignaTransporteHdr_ID(UY_AsignaTransporteHdr_ID);
				
				//OpenUp Nicolas Sarlabos issue #854
				//guardo el ID del ATR en el campo uy_asignatransportehdrfirst_id, lo cual servira
				//para determinar si esta devolucion ya tuvo una asignacion previa y cual fue su ID
				if(devol.getuy_asignatransportehdrfirst_id()<=0)
					devol.setuy_asignatransportehdrfirst_id(UY_AsignaTransporteHdr_ID);
				//fin OpenUp Nicolas Sarlabos issue #854
				devol.setM_Shipper_ID(header.getM_Shipper_ID());
				if (!devol.asignIt()) return false;
				devol.saveEx(trxName);
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
	public String getSQLWhere(int uyAsignaTransporteHdr_ID,VCheckBox checkDevol)
	{
		//StringBuffer sql = new StringBuffer(" WHERE dev.Processed='N' AND dev.DocStatus IN ('RQ')"); 
		StringBuffer sql = new StringBuffer(" WHERE dev.DocStatus IN ('RQ')");
		
	    sql.append( " AND NOT EXISTS (SELECT * FROM UY_AsignaTransporteDevol line " 
			      + " WHERE line.M_InOut_ID = dev.M_InOut_ID)");
		//OpenUp Nicolas Sarlabos issue #823
		//ahora se filtra por la sucursal del cliente y no por el cliente en sí
	    //OpenUp Nicolas Sarlabos issue #886 06/10/2011, si el check "Solo Devoluciones de ATR" es true entonces
	    //se traen las solicitudes de devolcuiones para clientes del ATR, si es false se traen TODAS las solicitudes
	    if(checkDevol!=null){
	    	if(checkDevol.getValue().equals(true)){
	    		//OpenUp Nicolas Sarlabos #914 31/10/2011, se modifica sql para buscar por facturas cargadas ademas de por las reservas
	    		sql.append(" AND bploc.C_BPartner_Location_ID IN (SELECT c_bpartner_location_id FROM UY_AsignaTransporteLine WHERE UY_AsignaTransporteHdr_ID =" + uyAsignaTransporteHdr_ID + 
				" UNION SELECT inv.C_BPartner_Location_ID" + 
				" FROM UY_AsignaTransporteFact af" +
				" INNER JOIN c_invoice inv ON af.c_invoice_id = inv.c_invoice_id" +
		        " WHERE af.UY_AsignaTransporteHdr_ID =" + uyAsignaTransporteHdr_ID + ")"); 
	    		//fin OpenUp Nicolas Sarlabos #914 31/10/2011
	    	}
	    
	    }
	    //fin OpenUp Nicolas Sarlabos #886
	    //fin OpenUp Nicolas Sarlabos #823
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
	
	
	protected Vector<Vector<Object>> getData(VCheckBox checkDevol)
	{
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		
		int UY_AsignaTransporteHdr_ID = ((Integer)getGridTab().getValue("UY_AsignaTransporteHdr_ID")).intValue();
		
		String sql = " SELECT "+
					 "	dev.C_BPartner_ID, "+ 
					 "	bp.name as nombrecliente, "+ 
					 "	bploc.name as sucursal, (coalesce(loca.address1,'') || coalesce(loca.address2,'')) as direccion, "+
					 "  dev.DocumentNo, dev.MovementDate, dev.M_InOut_ID "+
					 "	FROM M_InOut dev "+  
					 "	INNER JOIN C_BPartner bp ON dev.C_BPartner_ID = bp.C_BPartner_ID "+  
					 "	LEFT JOIN C_Order pedido ON dev.C_Order_ID = pedido.C_Order_ID  "+
					 "	INNER JOIN C_BPartner_Location bploc ON DEV.C_BPartner_Location_ID = bploc.C_BPartner_Location_ID "+  
					 "	LEFT JOIN C_Location loca ON bploc.C_Location_ID = loca.C_Location_ID ";

		sql = sql + getSQLWhere(UY_AsignaTransporteHdr_ID,checkDevol) + " ORDER BY bp.value, dev.MovementDate";

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql.toString(), null);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				Vector<Object> line = new Vector<Object>(11);
				line.add(new Boolean(false));       							// 0-Selection
				KeyNamePair bpartner = new KeyNamePair(rs.getInt("C_BPartner_ID"), rs.getString("nombrecliente"));
				line.add(bpartner);      										// 1-Cliente
				line.add(rs.getString("sucursal"));      						// 2-Sucursal
				line.add(rs.getString("direccion"));      						// 3-Direccion
				line.add(rs.getString("DocumentNo"));      						// 4-Nro.Devolucion								
				line.add(rs.getTimestamp("MovementDate"));      						// 5-Nro.Devolucion
				line.add(rs.getInt("M_InOut_ID"));    							// 6-ID

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
		miniTable.setColumnClass(1, String.class, true);    	//  1-Cliente
		miniTable.setColumnClass(2, String.class, true);    	//  2-Sucursal
		miniTable.setColumnClass(3, String.class, true);    	//  3-Direccion
		miniTable.setColumnClass(4, String.class, true);    	//  4-Nro.Devolucion
		miniTable.setColumnClass(5, Timestamp.class, true);    	//  5-Fecha Devolucion
		miniTable.setColumnClass(6, Integer.class, true);    	//  6-ID Devolucion
		//  Table UI
		miniTable.autoSize();
	}

	protected Vector<String> getOISColumnNames()
	{
		//  Header Info
		Vector<String> columnNames = new Vector<String>(12);
		columnNames.add(Msg.getMsg(Env.getCtx(), "Select"));
		columnNames.add("Cliente");
		columnNames.add("Sucursal");
		columnNames.add("Direccion");
		columnNames.add("Nro.Devolucion");
		columnNames.add("Fecha Devolucion");
		columnNames.add("ID Devolucion");
					    
	    return columnNames;
	}
	
}
