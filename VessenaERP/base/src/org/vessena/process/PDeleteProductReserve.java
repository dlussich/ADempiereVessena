/**
 * PDeleteProductReserve.java
 * 08/01/2011
 */
package org.openup.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.compiere.model.X_M_Product;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.openup.model.MReservaPedidoLine;
import org.openup.model.X_UY_AsignaTransporteHdr;

/**
 * OpenUp.
 * PDeleteProductReserve
 * Descripcion :
 * @author Gabriel Vila
 * Fecha : 08/01/2011
 */
public class PDeleteProductReserve extends SvrProcess {

	private int asignaTransporteHdrID = -1;
	private int productID = -1;
	
	/**
	 * Constructor
	 */
	public PDeleteProductReserve() {
		// TODO Auto-generated constructor stub
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
				if (name.equalsIgnoreCase(X_UY_AsignaTransporteHdr.COLUMNNAME_UY_AsignaTransporteHdr_ID)){
					this.asignaTransporteHdrID = ((BigDecimal)para[i].getParameter()).intValueExact();
				}
				if (name.equalsIgnoreCase(X_M_Product.COLUMNNAME_M_Product_ID)){
					this.productID = ((BigDecimal)para[i].getParameter()).intValueExact();
				}
			}
		}

	}
	
	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {

		String sql ="";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try
		{		
			// Obtengo lineas del pedido con reservas asociadas el picking
			sql = "SELECT a.uy_reservapedidoline_id, a.c_orderline_id, a.m_product_id, coalesce(a.qtyreserved,0) as cantreservada, a.qtyentered, " + 
				  " coalesce(oline.m_attributesetinstance_id,0) as instancia, oline.qtyordered, oline.qtyreserved as lres, a.c_uom_id " +
				  " FROM uy_reservapedidoline a " +
				  " inner join uy_reservapedidohdr b on a.uy_reservapedidohdr_id = b.uy_reservapedidohdr_id " +
				  " inner join uy_asignatransporteline line on b.uy_reservapedidohdr_id = line.uy_reservapedidohdr_id " +
				  " inner join c_orderline oline on a.c_orderline_id = oline.c_orderline_id " +
				  " where line.uy_asignatransportehdr_id =? " +
				  " and a.m_product_id =?";

			pstmt = DB.prepareStatement (sql, get_TrxName());
			pstmt.setInt (1, asignaTransporteHdrID);
			pstmt.setInt (2, productID);
			rs = pstmt.executeQuery ();

			String action = "";
			
			
			// Recorro lineas
			while (rs.next ())
			{
				// Actualizo stock en reserva en m_storage
				BigDecimal cantidadReservada = rs.getBigDecimal("qtyentered");
				
				/*action = " update m_storage set qtyreserved = qtyreserved - " + cantidadReservada +
						 " where m_product_id =" + productID +
						 " and m_locator_id =" + locatorID +
						 " and m_attributesetinstance_id =" + rs.getInt("instancia");
				DB.executeUpdateEx(action, get_TrxName());
				
				action = " update c_orderline set qtyreserved = qtyreserved - " + cantidadReservada +
						 " where c_orderline_id =" + rs.getInt("c_orderline_id") +
						 " and m_product_id =" + productID;
				DB.executeUpdateEx(action, get_TrxName());
				
				action = " delete from uy_reservapedidoline " + 
						 " where uy_reservapedidoline_id =" + rs.getInt("uy_reservapedidoline_id") +
						 " and m_product_id =" + productID;
				DB.executeUpdateEx(action, get_TrxName());*/

				
				// Anulo cantidad reservada
				MReservaPedidoLine resLine = new MReservaPedidoLine(getCtx(), rs.getInt("uy_reservapedidoline_id"), get_TrxName());
				resLine.anularCantidadReservada(cantidadReservada, null, null);

				
				action = " update uy_pickingline set qtyentered = qtyentered - " + rs.getBigDecimal("qtyentered") +  
				 		" from uy_pickinghdr " +
						" where uy_pickingline.uy_pickinghdr_id = uy_pickinghdr.uy_pickinghdr_id " +
						" and uy_pickinghdr.uy_asignatransportehdr_id =" + asignaTransporteHdrID +
						" and uy_pickingline.c_uom_id=" + rs.getInt("c_uom_id") +
						" and uy_pickingline.m_product_id =" + productID;
				DB.executeUpdateEx(action, get_TrxName());
				
				
			}
			
			commit();
 		}
		catch (Exception e)
		{
			throw e;	
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		return "Proceso ejecutado con Exito.";
	}

/*
select uy_asignatransportehdr_id
from uy_asignatransportehdr
where documentno = 'ATR-235'  --1000030

select a.uy_reservapedidoline_id, a.c_orderline_id, a.m_product_id, a.qtyreserved, a.qtyentered, 
oline.m_attributesetinstance_id, oline.qtyordered, oline.qtyreserved as lres
from uy_reservapedidoline a
inner join uy_reservapedidohdr b on a.uy_reservapedidohdr_id = b.uy_reservapedidohdr_id
inner join m_product prod on a.m_product_id = prod.m_product_id
inner join uy_asignatransporteline line on b.uy_reservapedidohdr_id = line.uy_reservapedidohdr_id
inner join c_orderline oline on a.c_orderline_id = oline.c_orderline_id
where line.uy_asignatransportehdr_id = 1000040
and prod.value='62304'

select * from m_storage 
where m_product_id = 1030533
and m_locator_id = 1000008
and m_attributesetinstance_id = 0

update m_storage set qtyreserved = qtyreserved - 12
where m_product_id = 1030533
and m_locator_id = 1000008
and m_attributesetinstance_id = 0

update c_orderline set qtyreserved = qtyreserved - 12
where c_orderline_id = 1004237
and m_product_id = 1030533

delete from uy_reservapedidoline 
where uy_reservapedidoline_id = 1000678
and m_product_id = 1030533



 * 
 */

}
