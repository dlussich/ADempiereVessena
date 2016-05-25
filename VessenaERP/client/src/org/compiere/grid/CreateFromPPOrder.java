/**
 * CreateFromPPOrder.java
 * 06/04/2011
 */
package org.compiere.grid;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Vector;
import java.util.logging.Level;
import org.compiere.apps.ADialog;
import org.compiere.minigrid.IMiniTable;
import org.compiere.model.GridTab;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.eevolution.model.MPPOrder;
import org.openup.model.MManufOrderLine;

/**
 * OpenUp.
 * CreateFromPPOrder
 * Descripcion :
 * @author Gabriel Vila
 * Fecha : 06/04/2011
 */
public class CreateFromPPOrder extends CreateFrom {

	/**
	 * Constructor
	 * @param gridTab
	 */
	public CreateFromPPOrder(GridTab gridTab) {
		super(gridTab);
		log.info(gridTab.toString());
	}

	/* (non-Javadoc)
	 * @see org.compiere.grid.CreateFrom#dynInit()
	 */
	@Override
	public boolean dynInit() throws Exception {
		log.config("");
		setTitle("Seleccion de Ordenes de Proceso");
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
		
		// Busco ID de la order y la instancio
		int cOrderID = ((Integer) getGridTab().getValue("C_Order_ID")).intValue();

		try {
			// Lines
			for (int i = 0; i < miniTable.getRowCount(); i++) {

				if (((Boolean) miniTable.getValueAt(i, 0)).booleanValue()) {
					
					// Busco ID de la pporder y la instancio
					int idPPOrder = (Integer) miniTable.getValueAt(i, 8);
					MPPOrder ppOrder = new MPPOrder(Env.getCtx(), idPPOrder,trxName);

					// Creo la linea detalles
					MManufOrderLine linea = new MManufOrderLine(Env.getCtx(),0, trxName);
					// Seteo Campos
					linea.setC_Order_ID(cOrderID);
					linea.setPP_Order_ID(idPPOrder);
					linea.setM_Product_ID(ppOrder.getM_Product_ID());
					linea.setC_UOM_ID(ppOrder.getC_UOM_ID());
					
					linea.save(trxName);
				}
			}

		} catch (Exception e) {

			ADialog.error(0, null, e.getMessage());
			log.log(Level.SEVERE, e.getMessage());
			return false;
		}
		return true;
	}
	
	//OpenUp Nicolas Garcia 15/05/2011 Issue #632
	//OpenUp Nicolas Garcia 29/06/2011 Issue #752
	public String getSQLWhere(Object mProduct, Object ppOrder, Object docType,Object dateFrom, Object dateTo) {
		
		StringBuffer sql = new StringBuffer(" WHERE vuy_ordersalesid.isActive='Y' "); 

		if (mProduct != null){
			Integer idProd = (Integer)mProduct;
			sql.append(" AND vuy_ordersalesid.M_Product_ID=" + idProd.intValue());
		}
		
		if (docType != null){
			Integer idDocType = (Integer)docType;
			sql.append(" AND vuy_ordersalesid.C_DocType_ID=" + idDocType.intValue());
		}
		//fin Nicolas Garcia
	
		if (ppOrder != null){
			Integer idPPOrder = (Integer)ppOrder;
			sql.append(" AND vuy_ordersalesid.PP_Order_ID=" + idPPOrder.intValue());
		}
	
		if (dateFrom != null || dateTo != null)
		{
			Timestamp from = (Timestamp) dateFrom;
			Timestamp to = (Timestamp) dateTo;
			if (from == null && to != null) {
				sql.append(" AND TRUNC(vuy_ordersalesid.DateOrdered) <='" + to + "'");
			}				
			else if (from != null && to == null)
				sql.append(" AND TRUNC(vuy_ordersalesid.DateOrdered) >='" + from + "'");
			else if (from != null && to != null)
				sql.append(" AND TRUNC(vuy_ordersalesid.DateOrdered) BETWEEN '" + from + "' AND '" +  to + "'");
		}
	
		log.fine(sql.toString());
		return sql.toString();
		
	}	//	getSQLWhere
	

	/**
	 * OpenUp.	
	 * Descripcion : Metodo donde se obtiene la informacion.
	 * @param mProduct
	 * @param ppOrder
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 * @author  Gabriel Vila 
	 * Fecha : 07/04/2011
	 */
	protected Vector<Vector<Object>> getData(Object mProduct, Object ppOrder,Object doctype, Object dateFrom, Object dateTo) {

		Vector<Vector<Object>> data = new Vector<Vector<Object>>();

		String sql = " SELECT vuy_ordersalesid.*,prod.value,prod.name FROM vuy_ordersalesid"+
					 " INNER JOIN M_Product prod ON prod.M_Product_ID = vuy_ordersalesid.M_Product_ID ";
			
					//	+" LEFT OUTER JOIN vuy_ppordersales ppsales ON pporder.pp_order_id = ppsales.pp_order_id ";
		
		sql = sql + getSQLWhere(mProduct, ppOrder,doctype, dateFrom, dateTo) + " ORDER BY vuy_ordersalesid.DateOrdered DESC ";
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = DB.prepareStatement(sql.toString(), null);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
			
				Vector<Object> line = new Vector<Object>(9);
				line.add(new Boolean(false));       			 //  0-Selection
				line.add(rs.getTimestamp("DateOrdered"));       //  1-Fecha
				line.add(rs.getString("DocumentNo"));      		 //  2-Numero
				KeyNamePair prod = new KeyNamePair(rs.getInt("M_Product_ID"), rs.getString("value"));
				line.add(prod);      				    	 //  3-Producto
				line.add(rs.getString("name"));      	     //  4-Nombre del Producto
				line.add(rs.getBigDecimal("disponible"));     //  5-Cantidad disponible
				line.add(rs.getBigDecimal("qtyordered"));   //  6-Cantidad de la orden
				line.add(rs.getBigDecimal("qtydelivered"));     //  7-Cantidad producida en orden
				line.add(rs.getInt("PP_Order_ID"));    			 //  8-ID
				
				data.add(line);
			}
		}
		catch (SQLException e) {
			log.log(Level.SEVERE, sql, e);
		}
		finally {
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
		miniTable.setColumnClass(3, String.class, true);    	//  3-Producto
		miniTable.setColumnClass(4, String.class, true);    	//  4-Nombre del Producto
		miniTable.setColumnClass(5, BigDecimal.class, true);    //  5-Cantidad pendiente de venta de la Orden
		miniTable.setColumnClass(6, BigDecimal.class, true);    //  6-Cantidad de la Orden
		miniTable.setColumnClass(7, BigDecimal.class, true);    //  7-Cantidad pendiente de venta de la Orden
		miniTable.setColumnClass(8, Integer.class, true);    	//  7-ID
		//  Table UI
		miniTable.autoSize();
		
	}

	protected Vector<String> getOISColumnNames()
	{
		//  Header Info
		Vector<String> columnNames = new Vector<String>(8);
		columnNames.add("Seleccion");
		columnNames.add("Fecha OP");
		columnNames.add("Numero OP");
		columnNames.add("Producto");
		columnNames.add("Nombre");
		columnNames.add("Cantidad Disponible");
		columnNames.add("Cantidad Ordenada");
		columnNames.add("Cantidad Producida");
		
		columnNames.add("ID OP");
	    
	    return columnNames;
	}

}
