/**
 * 
 */
package org.openup.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.DocAction;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.openup.model.MStockAdjustment;
import org.openup.model.MStockAdjustmentLine;
import org.openup.model.MStockStatus;


/**
 * @author Hp
 *
 */
public class PFixPending extends SvrProcess {
	
	//OpenUp. Nicolas Sarlabos. 01/10/2013. #1365. Se agregan parametros al proceso (todos son obligatorios).
	private int docTypeID = 0;
	private int warehouseID = 0;
	private int locatorID = 0;
	private int reasonID = 0;
	//Fin OpenUp.

	/**
	 * 
	 */
	public PFixPending() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		//OpenUp. Nicolas Sarlabos. 01/10/2013. #1365. Se recorren parametros.
		ProcessInfoParameter[] para = getParameter();

		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName().trim();
			if (name!= null){
					
						
				if (name.equalsIgnoreCase("C_DocType_ID")){
					if (para[i].getParameter()!=null){
						this.docTypeID = ((BigDecimal)para[i].getParameter()).intValueExact();
					}
				}			
				if (name.equalsIgnoreCase("M_Warehouse_ID")){
					if (para[i].getParameter()!=null){
						this.warehouseID = ((BigDecimal)para[i].getParameter()).intValueExact();
					}					
				}
				if (name.equalsIgnoreCase("M_Locator_ID")){
					if (para[i].getParameter()!=null){
						this.locatorID = ((BigDecimal)para[i].getParameter()).intValueExact();
					}					
				}
				if (name.equalsIgnoreCase("UY_AdjustmentReason_ID")){
					if (para[i].getParameter()!=null){
						this.reasonID = ((BigDecimal)para[i].getParameter()).intValueExact();
					}					
				}
		
			}
		}
		//Fin OpenUp.
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
	    Timestamp hoy = Timestamp.valueOf(sdf.format(cal.getTime()));
	    
	    //OpenUp. Nicolas Sarlabos. 01/10/2013. #1365. Se eliminan ID quemados para poder ejecutar el proceso en cualquier cliente del sistema.
	    MStockStatus status = MStockStatus.forValue(getCtx(), "pendiente", null); //obtengo estado stock "pendiente"
	    
	    if(status.get_ID() <= 0) throw new AdempiereException("No se encontro estado de stock: PENDIENTE");
		
		try{
			
			DB.executeUpdateEx("drop table aux_fixpend;", null);
			DB.executeUpdateEx("drop table aux_fixpend0;", null);
			DB.executeUpdateEx("drop table aux_fixpend1;", null);
			
			sql = "select t.m_product_id, stk_pending(t.m_product_id, " + warehouseID + ", " + locatorID + " , null, null, null) as stkpend" +
				  "	into aux_fixpend " +
                  " from stk_distinct_prod t " +
                  " order by t.m_product_id";
			DB.executeUpdateEx(sql, null);
			
			sql = "select v.m_product_id, sum(v.pendiente * coalesce(uc.dividerate,1)) as vpend" +
                  " into aux_fixpend0" + 
                  " from vuy_pedidos_pendientes_lineas v join c_order hdr on v.c_order_id = hdr.c_order_id" +
                  " left outer join c_uom_conversion uc on (v.m_product_id = uc.m_product_id AND v.c_uom_id = uc.c_uom_to_id)" +
                  " where hdr.docstatus ='CO'" +
                  " group by v.m_product_id" +
                  " order by m_product_id";
			DB.executeUpdateEx(sql, null);
			
			sql = "select a.m_product_id, a.stkpend, coalesce(b.vpend,0) as vpend" +
                  " into aux_fixpend1" + 
                  " from aux_fixpend a left outer join aux_fixpend0 b on a.m_product_id = b.m_product_id";
			DB.executeUpdateEx(sql, null);
		
			sql = " select * from aux_fixpend1 where vpend<>stkpend ";
			pstmt = DB.prepareStatement (sql, null);
			rs = pstmt.executeQuery();

			boolean hayInfo = false;
			MStockAdjustment hdr = null;
			
			// Para cada linea obtenida
			while (rs.next()){

				if (!hayInfo){
					hayInfo = true;
					hdr = new MStockAdjustment(getCtx(), 0, get_TrxName());
					hdr.setC_DocType_ID(docTypeID);
					hdr.setDateTrx(hoy);
					hdr.setMovementDate(hoy);
					hdr.setAD_User_ID(this.getAD_User_ID());
					hdr.setUY_StockStatus_ID(status.get_ID());
					hdr.setDocStatus(DocAction.STATUS_Drafted);
					hdr.setDocAction(DocAction.ACTION_Complete);
					hdr.setProcessed(false);
					hdr.setUY_AdjustmentReason_ID(reasonID);
					hdr.saveEx(get_TrxName());
				}
				
				MStockAdjustmentLine line = new MStockAdjustmentLine(getCtx(), 0, get_TrxName());
				line.setUY_StockAdjustment_ID(hdr.getUY_StockAdjustment_ID());
				line.setM_Locator_ID(locatorID);
				line.setM_AttributeSetInstance_ID(0);
				line.setUY_StockStatus_ID(hdr.getUY_StockStatus_ID());
				line.setM_Product_ID(rs.getInt("m_product_id"));
				
				BigDecimal cantidad = rs.getBigDecimal("stkpend").subtract(rs.getBigDecimal("vpend"));
				
				line.setMovementQty(cantidad.negate());
				line.setProcessed(false);
				line.saveEx(get_TrxName());
			}
			//Fin OpenUp.
			//si tengo cabezal entonces lo completo
			
			if(hdr!=null){
				hdr.processIt(DocAction.ACTION_Complete);
			}
			
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

		return "ok";
	}

}
