/**
 * PStockTransactionLoad.java
 * 05/04/2011
 */
package org.openup.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

/**
 * OpenUp.
 * PStockTransactionLoad
 * Descripcion : Proceso de carga de tabla de movimientos de stock (UY_StockTransaction),
 * tomando como origen la tabla de stock de adempiere (M_Transaction) para stock fisico,
 * pedidos pendientes para stock pendiente,
 * reserva de pedidos para stock reservado,
 * reserva de produccion para stock reservado.
 * 
 * @author Gabriel Vila
 * Fecha : 05/04/2011
 */
public class PStockTransactionLoad extends SvrProcess {

	private static final int ID_SEQUENCE_STOCK_TRANSACTION = 1000907;
	private static final int ID_SEQUENCE_STOCK_LOAD = 1000911;
	private static final int ID_PERIOD = 1000087;
	private static final int ID_CARGAINICIAL = 1000200;
	private static final String fechaSaldoInicial = "2011-04-06 00:00:00";
	private static final int ID_TABLE = 1000188;
	
	/**
	 * Constructor
	 */
	public PStockTransactionLoad() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {

		String sql = "", insert = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try{
			
			insert = " INSERT INTO UY_StockTransaction (uy_stocktransaction_id, ad_client_id, ad_org_id, " +
					 "isactive, created, createdby, updated, updatedby, ad_window_id, ad_user_id, datetrx, " +
					 "movementdate, c_period_id, uy_stockstatus_id, sign," +
					 "c_doctype_id, ad_table_id, " +
					 "record_id, documentno, c_doctype_affected_id, ad_table_affected_id, record_affected_id, documentno_affected, " +
					 "docaction, " +
					 "m_attributesetinstance_id, m_product_id, m_locator_id, m_warehouse_id, c_uom_id, movementqty)";    
			
			// Stock Fisico desde M_Transaction
			sql = " select nextid(" + ID_SEQUENCE_STOCK_TRANSACTION + ",'N') as id, tr.ad_client_id, tr.ad_org_id, " +
				  "'Y','" + fechaSaldoInicial + "',100,'" + fechaSaldoInicial + "',100,0,100,'" + fechaSaldoInicial + "','" +
				  fechaSaldoInicial + "'," + ID_PERIOD + ", 'stock', 1, " +
				  ID_CARGAINICIAL + "," + ID_TABLE + ", nextid(" + ID_SEQUENCE_STOCK_LOAD + ",'N') as idrecord,'0', " +
				  ID_CARGAINICIAL + "," + ID_TABLE + ", nextid(" + ID_SEQUENCE_STOCK_LOAD + ",'N')-1 as idrecord,'0','CO', " +
				  "0, tr.m_product_id, tr.m_locator_id, loc.m_warehouse_id, coalesce(prod.c_uom_id,100) as uom, sum(tr.movementqty) as stkfisico " +
				  " from m_transaction tr inner join m_product prod on tr.m_product_id = prod.m_product_id " +
				  " inner join m_locator loc ON tr.m_locator_id = loc.m_locator_id " +
				  " group by tr.ad_client_id, tr.ad_org_id, tr.m_product_id, tr.m_locator_id, loc.m_warehouse_id, prod.c_uom_id " +
				  " having sum(tr.movementqty)>=0 ";

			log.info(insert + sql);
			DB.executeUpdate(insert + sql, get_TrxName());

			// Stock pendiente de pedidos
			sql = " select nextid(" + ID_SEQUENCE_STOCK_TRANSACTION + ",'N') as id, line.ad_client_id, line.ad_org_id, " +
				  "'Y','" + fechaSaldoInicial + "',100,'" + fechaSaldoInicial + "',100,0,100,'" + fechaSaldoInicial + "','" +
				  fechaSaldoInicial + "'," + ID_PERIOD + ", 'pendiente', 1, " +
				  ID_CARGAINICIAL + "," + ID_TABLE + ", nextid(" + ID_SEQUENCE_STOCK_LOAD + ",'N') as idrecord,'0', " +
				  ID_CARGAINICIAL + "," + ID_TABLE + ", nextid(" + ID_SEQUENCE_STOCK_LOAD + ",'N')-1 as idrecord,'0','CO', 0, " +
				  " pend.m_product_id, prod.m_locator_id, loc.m_warehouse_id, coalesce(prod.c_uom_id,100) as uom, " +
				  " sum(pend.qtyentered * uomc.dividerate) as stkpend " +
				  " from vuy_pedidos_pendientes_lineas pend " +
				  " inner join m_product prod on pend.m_product_id = prod.m_product_id " + 
				  " inner join m_locator loc ON prod.m_locator_id = loc.m_locator_id " +
				  " inner join c_uom_conversion uomc on (pend.m_product_id = uomc.m_product_id and pend.c_uom_id = uomc.c_uom_to_id) " +
				  " inner join c_orderline line on pend.c_orderline_id = line.c_orderline_id " +
				  " group by line.ad_client_id, line.ad_org_id, pend.m_product_id, prod.m_locator_id, loc.m_warehouse_id, prod.c_uom_id " +
				  " having sum(pend.qtyentered * uomc.dividerate)>=0";

			log.info(insert + sql);
			DB.executeUpdate(insert + sql, get_TrxName());
			
			commitEx();
			
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
		
		
		return "OK";

	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		// TODO Auto-generated method stub

	}

}
