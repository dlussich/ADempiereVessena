/**
 * PStorageFixes.java
 * 16/02/2011
 */
package org.openup.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.compiere.model.MStorage;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

/**
 * OpenUp.
 * PStorageFixes
 * Descripcion : Proceso de verificacion y correccion de cantidad diponible y reservada en Storage.
 * El disponible lo corrige en base al stock fisico de la Transaction.
 * El reservado considera las Reservas Comerciales y las Ordenes de Produccion.
 * @author Gabriel Vila
 * Fecha : 16/02/2011
 */
public class PStorageFixes extends SvrProcess {

	/**
	 * Constructor
	 */
	public PStorageFixes() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {

	}
	
	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		this.fixStorage();
		return "Ok";	
	}

	
	/**
	 * OpenUp.	
	 * Descripcion : Ejecuta el proceso de correccion de storage.
	 * @throws Exception
	 * @author  Gabriel Vila 
	 * Fecha : 16/02/2011
	 */
	private void fixStorage() throws Exception{
		
		String sql = "", action ="";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try{

			log.info("Inicio proceso de Correccion de Storage");
								
			// Obtengo stock fisico de productos desde m_transaction
			sql = " select tr.m_product_id, tr.m_locator_id, tr.m_attributesetinstance_id, sum(tr.movementqty) as stkfisico " +
				  " from m_transaction tr inner join m_product prod on tr.m_product_id = prod.m_product_id " +
				  " where prod.isactive='Y' " +
 			      " group by tr.m_product_id, tr.m_locator_id, tr.m_attributesetinstance_id " +
 			      " order by tr.m_product_id, tr.m_locator_id, tr.m_attributesetinstance_id";
			pstmt = DB.prepareStatement (sql, null);
			log.info(sql);
			rs = pstmt.executeQuery();
			int productID = 0, productIDAux = 0; 
			// Para cada linea obtenida
			while (rs.next()){

				log.info("FISICO : Producto : " + rs.getInt("m_product_id") + " - Ubicacion : " + rs.getInt("m_locator_id") + " - Cantidad : " + rs.getBigDecimal("stkfisico").toString());

				// Corte de control por producto para delete de informacion de producto en storage
				productIDAux = rs.getInt("m_product_id");
				
				if (productID != productIDAux){
					productID = productIDAux;
					// Deleteo Storage para este producto
					action = "DELETE FROM M_Storage WHERE m_product_id=" + productID;
					DB.executeUpdate(action, get_TrxName());
				}
				
				// Nuevo registro en Storage para prod-loc-setinstance
				MStorage storage = MStorage.getCreate(getCtx(), rs.getInt("m_locator_id"),  rs.getInt("m_product_id"), rs.getInt("m_attributesetinstance_id"), get_TrxName());
				storage.changeQtyOnHand(rs.getBigDecimal("stkfisico"), true);
				storage.saveEx(get_TrxName());
			
			}

			DB.close(rs, pstmt);
			rs = null; pstmt = null;

			

			// Obtengo reserva comerciales de productos
			sql = " select line.m_product_id, sum(line.qtyreserved) stkreservado" +
				  " from uy_reservapedidoline line " +
				  " inner join uy_reservapedidohdr hdr on line.uy_reservapedidohdr_id = hdr.uy_reservapedidohdr_id " +
				  " where hdr.docstatus in('CO','CL') " +
				  " and hdr.uy_reservapedidohdr_id not in " +
				  "	(select coalesce(pline.uy_reservapedidohdr_id,0) " +
				  "	from uy_procesofactmasline pline left outer join c_invoice inv on pline.c_invoice_id = inv.c_invoice_id " +
				  " where pline.c_invoice_id is not null and inv.docstatus in('CO','CL','RE','VO')) " +
				  " group by line.m_product_id " +
				  " order by line.m_product_id";

			pstmt = DB.prepareStatement (sql, null);
			log.info(sql);
			rs = pstmt.executeQuery();
			while (rs.next()){

				log.info("RESERVA COM: Producto : " + rs.getInt("m_product_id") + " - Cantidad : " + rs.getBigDecimal("stkreservado").toString());

				// Update Reserved en Storage
				MStorage storage = MStorage.getCreate(getCtx(), 1000008,  rs.getInt("m_product_id"), 0, get_TrxName());
				storage.setQtyReserved(rs.getBigDecimal("stkreservado"));
				storage.saveEx(get_TrxName());
			}

			DB.close(rs, pstmt);
			rs = null; pstmt = null;

			// Obtengo reservas por produccion
			/*sql = " select line.m_product_id, line.m_locator_id, sum(line.qtyreserved) as stkprod " +
				  " from pp_order_bomline line inner join pp_order hdr on line.pp_order_id = hdr.pp_order_id " +
				  " where hdr.docstatus in('CO','CL') " +
				  " and hdr.pp_order_id not in (select coalesce(pp_order_id,0) from m_transaction) " +
				  " group by line.m_product_id, line.m_locator_id " +
				  " order by line.m_product_id, line.m_locator_id ";*/
			
			sql = " select line.m_product_id, sum(line.qtyreserved) as stkprod " +
			  " from pp_order_bomline line inner join pp_order hdr on line.pp_order_id = hdr.pp_order_id " +
			  " where hdr.docstatus in('CO','CL') " +
			  " and hdr.pp_order_id not in (select coalesce(pp_order_id,0) from m_transaction) " +
			  " group by line.m_product_id " +
			  " order by line.m_product_id ";

			
			pstmt = DB.prepareStatement (sql, null);
			log.info(sql);
			rs = pstmt.executeQuery();
			while (rs.next()){

				//log.info("RESERVA PROD: Producto : " + rs.getInt("m_product_id") + " - Ubicacion : " + rs.getInt("m_locator_id") + " - Cantidad : " + rs.getBigDecimal("stkprod").toString());
				log.info("RESERVA PROD: Producto : " + rs.getInt("m_product_id") + " - Cantidad : " + rs.getBigDecimal("stkprod").toString());
				
				// Update Reserved en Storage
				MStorage storage = MStorage.getCreate(getCtx(), 1000008,  rs.getInt("m_product_id"), 0, get_TrxName());
				//MStorage storage = MStorage.getCreate(getCtx(), rs.getInt("m_locator_id"),  rs.getInt("m_product_id"), 0, get_TrxName());
				storage.setQtyReserved(storage.getQtyReserved().add(rs.getBigDecimal("stkprod")));
				storage.saveEx(get_TrxName());
			}
			
			commit();
			
			log.info("Fin Proceso de Correccion de Storage.");
			
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
