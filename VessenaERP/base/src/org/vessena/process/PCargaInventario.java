/**
 * PPatchInventory.java
 * 29/09/2010
 */
package org.openup.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import org.compiere.model.MInventory;
import org.compiere.model.MInventoryLine;
import org.compiere.model.MStorage;
import org.compiere.model.MTransaction;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * OpenUp.
 * PPatchInventory
 * Descripcion :
 * @author Gabriel Vila
 * Fecha : 29/09/2010
 */
public class PCargaInventario extends SvrProcess {

	private static final String leyenda = "OPENUP. INVENTARIO 30/12/2011.";
	private static final String fechaSaldoInicial = "2011-12-30 00:00:00";
	private static final int idDocTypeInventario = 1000023;
	private static final int idWarehouse = 1000013;
	
	
	/**
	 * Constructor
	 */
	public PCargaInventario() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {

		this.processCarga();
		
		return "Ok";
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		// Obtengo parametros y los recorro
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName().trim();
			if (name!= null){
				if (name.equalsIgnoreCase("M_Warehouse_ID")){
					//this.idWarehouse = (Integer)para[i].getParameter();
				}
			}
		}

	}

	
	private void processCarga() throws Exception{

		MInventory header = null;
		
		log.info("Iniciando...");
		
		try{
	
			// Creacion de Inventario (cabezal y lineas) para saldo inicial
			// Un Registro para el Cabezal en la m_inventory
			header = new MInventory(getCtx(), 0, get_TrxName());
			header.setDescription(leyenda);
			header.setM_Warehouse_ID(idWarehouse);
			header.setMovementDate(Timestamp.valueOf(fechaSaldoInicial));
			header.setUpdateQty("N");
			header.setGenerateList("N");
			header.setApprovalAmt(Env.ZERO);
			header.setC_DocType_ID(idDocTypeInventario);

			header.setPosted(false);
			header.setProcessed(false);
			header.setProcessing(false);
			header.setIsApproved(false);
			header.setDocStatus("DR");
			header.setDocAction("CO");

			header.saveEx();
			
		}
		catch (Exception e){
			log.info(e.getMessage());
			throw new Exception(e);
		}
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try{

			// Obtengo lineas a procesar desde tabla main del proceso
			sql = "SELECT * FROM uy_cargainventario";
			pstmt = DB.prepareStatement (sql, get_TrxName());
			rs = pstmt.executeQuery();
		
			int lineCount = 0;
			
			// Para cada linea obtenida
			while (rs.next()){
				log.info("Producto : " + rs.getInt("m_product_id") + " - Ubicacion : " + rs.getInt("m_locator_id") + " - Cantidad : " + rs.getBigDecimal("cantidad").toString());

				// Nueva inventory line
				MInventoryLine line = new MInventoryLine(header, rs.getInt("m_locator_id"), rs.getInt("m_product_id"), 0, rs.getBigDecimal("libros"), rs.getBigDecimal("cantidad"));
				lineCount += 10;
				line.setLine(lineCount);
				line.setDescription(leyenda);
				line.saveEx();
				
				// Nueva transaction line
				/*MTransaction trans = new MTransaction(getCtx(), 0, get_TrxName());
				trans.setMovementType("I+");
				trans.setM_Locator_ID(line.getM_Locator_ID());
				trans.setM_Product_ID(line.getM_Product_ID());
				trans.setMovementDate(header.getMovementDate());
				trans.setMovementQty(line.getQtyCount());
				trans.setM_InventoryLine_ID(line.getM_InventoryLine_ID());
				trans.setM_AttributeSetInstance_ID(0);
				trans.saveEx();
				
				// Storage
				MStorage storage = MStorage.getCreate(getCtx(), rs.getInt("m_locator_id"),  rs.getInt("m_product_id"), 0, get_TrxName());
				storage.changeQtyOnHand(rs.getBigDecimal("cantidad"), true);
				storage.saveEx();*/
			
			}

			header.setPosted(true);
			header.setProcessed(false);
			header.setProcessing(false);
			header.setIsApproved(true);
			header.setDocStatus("DR");
			header.setDocAction("CO");
			header.saveEx();
			
			commitEx();
			
			log.info("Fin Procesamiento lineas");
			
		}
		catch (Exception e)
		{			
			log.info(e.getMessage());
			throw new Exception(e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
	}

}
