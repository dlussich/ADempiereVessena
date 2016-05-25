package org.compiere.grid;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Vector;
import java.util.logging.Level;

import org.compiere.minigrid.IMiniTable;
import org.compiere.model.GridTab;
import org.compiere.model.MInOut;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Msg;
import org.openup.model.MAsignaTransporteDevol;
import org.openup.model.MAsignaTransporteHdr;

public class CreateFromTransporteDevol extends CreateFrom{

	public CreateFromTransporteDevol(GridTab gridTab) {
		super(gridTab);
		log.info(gridTab.toString());
	}

	/* (non-Javadoc)
	 * @see org.compiere.grid.CreateFrom#dynInit()
	 */
	@Override
	public boolean dynInit() throws Exception {
		log.config("");
		setTitle("Seleccion de Devoluciones");
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
				// ID de la Devolucion Seleccionada
				int idDevol = (Integer)miniTable.getValueAt(i, 7);
				MInOut dev = new MInOut(Env.getCtx(), idDevol, null);
				
				// Nueva linea de devolucion asociada a asignacion de transporte
				MAsignaTransporteDevol linea = new MAsignaTransporteDevol(Env.getCtx(), 0, trxName);
				linea.setUY_AsignaTransporteHdr_ID(UY_AsignaTransporteHdr_ID);
				linea.setM_InOut_ID(idDevol);
				linea.setC_BPartner_ID(dev.getC_BPartner_ID());
				linea.setC_BPartner_Location_ID((Integer)miniTable.getValueAt(i, 6));
				linea.saveEx(trxName);
				
				dev.setUY_AsignaTransporteHdr_ID(UY_AsignaTransporteHdr_ID);
				dev.setM_Shipper_ID(header.getM_Shipper_ID());
				// Guardo linea en DB
				/*if (!linea.save())
					log.log(Level.SEVERE, "No se pudo guardar la linea de devolución en asignacion de transporte : " + i);*/
				
				if (!dev.asignIt()) return false;
				dev.saveEx(trxName);

			}
		}   //  for all rows
		
		return true;
	}

	public String getSQLWhere(Object BPartner, Object Devolucion, Object DateFrom, Object DateTo,
							  Object zonaReparto1, Object zonaReparto2, Object zonaReparto3)
	{
		StringBuffer sql = new StringBuffer(" WHERE mhdr.Processed='N' AND mhdr.DocStatus IN ('RQ') AND mhdr.c_doctype_id=1000066" +
				            " AND mhdr.uy_asignatransportehdrfirst_id IS NOT NULL AND mhdr.uy_asignatransportehdr_id IS NULL"); 
		
	    sql.append( " AND NOT EXISTS (SELECT * FROM UY_AsignaTransporteDevol line " 
			      + " WHERE line.M_InOut_ID = mhdr.M_InOut_ID)");
    
		if (BPartner != null){
			Integer idCli = (Integer)BPartner;
			sql.append(" AND mhdr.C_BPartner_ID=" + idCli.intValue());
		}
			
		if (Devolucion != null){
			Integer idDevol = (Integer)Devolucion;
			sql.append(" AND mhdr.m_inout_id=" + idDevol.intValue());
		}
			
		if (DateFrom != null || DateTo != null)
		{
			Timestamp from = (Timestamp) DateFrom;
			Timestamp to = (Timestamp) DateTo;
			if (from == null && to != null) {
				sql.append(" AND TRUNC(mhdr.MovementDate) <='" + to + "'");
			}				
			else if (from != null && to == null)
				sql.append(" AND TRUNC(mhdr.MovementDate) >='" + from + "'");
			else if (from != null && to != null)
				sql.append(" AND TRUNC(mhdr.MovementDate) BETWEEN '" + from + "' AND '" +  to + "'");
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
	
	
	protected Vector<Vector<Object>> getData(Object BPartner, Object Devolucion, Object DateFrom, Object DateTo,
											 Object zonaReparto1, Object zonaReparto2, Object zonaReparto3)
	{
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
			
		String sql =" SELECT mhdr.m_inout_id, mhdr.movementdate, mhdr.DocumentNo, mhdr.C_BPartner_ID, bp.name as nombrecliente," + 
					" bploc.name as sucursal, (coalesce(loca.address1,'') || coalesce(loca.address2,'')) as direccion, bploc.c_bpartner_location_id" +
					" FROM m_inout mhdr" +  
					" INNER JOIN C_BPartner bp ON mhdr.C_BPartner_ID = bp.C_BPartner_ID" +  
					" INNER JOIN C_BPartner_Location bploc ON mhdr.C_BPartner_Location_ID = bploc.C_BPartner_Location_ID" +  
					" INNER JOIN C_Location loca ON bploc.C_Location_ID = loca.C_Location_ID";
		
		sql = sql + getSQLWhere(BPartner, Devolucion, DateFrom, DateTo, zonaReparto1, zonaReparto2, zonaReparto3) + " ORDER BY mhdr.movementdate";

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
				line.add(rs.getTimestamp("MovementDate"));       //  1-Fecha
				line.add(rs.getString("DocumentNo"));      		//  2-Numero
				KeyNamePair bpartner = new KeyNamePair(rs.getInt("C_BPartner_ID"), rs.getString("nombrecliente"));
				line.add(bpartner);      	//  3-Cliente
				line.add(rs.getString("sucursal"));      	//  4-Sucursal
				line.add(rs.getString("direccion"));      	//  5-Direccion
				line.add(rs.getInt("c_bpartner_location_id")); // 6-ID Sucursal
				line.add(rs.getInt("M_InOut_ID"));    //  7-ID Devol
								
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
		miniTable.setColumnClass(5, String.class, true);    	//  5-Direccion
		miniTable.setColumnClass(6, Integer.class, true);    	//  6-ID Sucursal
		miniTable.setColumnClass(7, Integer.class, true);      // 7-ID Devol
		//  Table UI
		miniTable.autoSize();
	}

	protected Vector<String> getOISColumnNames()
	{
		//  Header Info
		Vector<String> columnNames = new Vector<String>(6);
		columnNames.add(Msg.getMsg(Env.getCtx(), "Select"));
		columnNames.add("Fecha Devolucion");
		columnNames.add("Numero Devolucion");
		columnNames.add("Cliente");
		columnNames.add("Sucursal");
		columnNames.add("Direccion");
		columnNames.add("ID Sucursal");
		columnNames.add("ID Devolucion");
		    
	    return columnNames;
	}
	
	
	
	

}
