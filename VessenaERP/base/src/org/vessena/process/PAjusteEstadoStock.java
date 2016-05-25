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

import org.compiere.model.MPeriod;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.openup.model.MStockTransaction;

/**
 * @author root
 *
 */
public class PAjusteEstadoStock extends SvrProcess {

	private int uyStockStatusID = 0;
	private int mProductID = 0;
	private int mWarehouseID = 0;
	private int mLocatorID = 0;
	private BigDecimal cantidad = Env.ZERO;
	private int sign = 0;
	private Timestamp fechaMovimiento = new Timestamp(System.currentTimeMillis()); 
	
	private boolean esParaAlianzur = false;
	
	/**
	 * 
	 */
	public PAjusteEstadoStock() {
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
					if(para[i].getParameter()!=null){
						this.mWarehouseID = ((BigDecimal)para[i].getParameter()).intValueExact();
					}
				}
				if (name.equalsIgnoreCase("M_Locator_ID")){
					if(para[i].getParameter()!=null){
						this.mLocatorID = ((BigDecimal)para[i].getParameter()).intValueExact();
					}
				}
				if (name.equalsIgnoreCase("M_Product_ID")){
					if(para[i].getParameter()!=null){
						this.mProductID = ((BigDecimal)para[i].getParameter()).intValueExact();
					}
				}
				if (name.equalsIgnoreCase("UY_StockStatus_ID")){
					if(para[i].getParameter()!=null){
						this.uyStockStatusID = ((BigDecimal)para[i].getParameter()).intValueExact();
					}
				}
				if (name.equalsIgnoreCase("sign")){
					if(para[i].getParameter()!=null){
						this.sign = Integer.parseInt(para[i].getParameter().toString());
					}
				}
				if (name.equalsIgnoreCase("MovementQty")){
					if(para[i].getParameter()!=null){
						this.cantidad = ((BigDecimal)para[i].getParameter());
					}
				}
				if (name.equalsIgnoreCase("MovementDate")){
					if(para[i].getParameter()!=null){
						this.fechaMovimiento = ((Timestamp)para[i].getParameter());
					}
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		
		if (this.mProductID > 0) this.ajusteProducto();
		else this.ajusteTodos();
		
		return "ok";
	}
	
	private void ajusteProducto() throws Exception{

		if (mWarehouseID <= 0) throw new Exception("Debe indicar almacen");
		if (mLocatorID <= 0) throw new Exception("Debe indicar ubicacion");
		if (sign == 0) throw new Exception("Debe indicar signo");
		if (cantidad.compareTo(Env.ZERO) == 0) throw new Exception("Debe indicar cantidad");

		Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
	    Timestamp hoy = Timestamp.valueOf(sdf.format(cal.getTime()));
		
		MStockTransaction stk = new MStockTransaction(getCtx(), 0, get_TrxName());
		stk.setAD_User_ID(getAD_User_ID());
		stk.setDateTrx(hoy);
		stk.setMovementDate(this.fechaMovimiento);
		stk.setC_Period_ID(MPeriod.getC_Period_ID(getCtx(), hoy, stk.getAD_Org_ID()));
		stk.setMovementQty(this.cantidad);
		stk.setUY_StockStatus_ID(this.uyStockStatusID);
		stk.setsign(new BigDecimal(this.sign));
		stk.setM_Warehouse_ID(this.mWarehouseID);
		stk.setM_Locator_ID(this.mLocatorID);
		stk.setM_Product_ID(this.mProductID);
		stk.setC_UOM_ID(100);
		stk.setM_AttributeSetInstance_ID(0);
		stk.setC_DocType_ID(1000077);
		stk.setAD_Table_ID(1000207);
		stk.setRecord_ID(9000000);
		stk.setDocumentNo("0");
		stk.setc_doctype_affected_id(1000077);
		stk.setad_table_affected_id(1000207);
		stk.setrecord_affected_id(9000000);
		stk.setdocumentno_affected("0");
		stk.setDocAction("CO");
		stk.saveEx(get_TrxName());
	}
	
	private void ajusteTodos() throws Exception{

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
	    Timestamp hoy = Timestamp.valueOf(sdf.format(cal.getTime()));
		
		try{
			sql = " select * from stk_sts_prodxwarxlocxasi " +
				  " where qty < 0 and uy_stockstatus_id =" + this.uyStockStatusID;   // BUSCO NEGATIVOS
			    //" where qty > 0 and uy_stockstatus_id =" + this.uyStockStatusID +  // BUSCO POSITIVOS
			
			//sql = " select * from stk_pend_prodxwarxloc  where m_warehouse_id = 1000002 and m_locator_id = 1000002 and qty < 0 ";
			
			pstmt = DB.prepareStatement (sql, null);
			log.info(sql);
			rs = pstmt.executeQuery();
			
			int docAjusteID = 0;
			int tableID = 0;
			
			if (esParaAlianzur){
				docAjusteID = 1000077; // ALIANZUR
				tableID = 1000207; // ALIANZUR
			}
			else{
				docAjusteID = 1000064; // VESSENA
				tableID = 1000124; // VESSENA
			}
			
			// Para cada linea obtenida
			while (rs.next()){

				MStockTransaction stk = new MStockTransaction(getCtx(), 0, get_TrxName());
				stk.setAD_User_ID(getAD_User_ID());
				stk.setDateTrx(hoy);
				stk.setMovementDate(this.fechaMovimiento);
				stk.setC_Period_ID(MPeriod.getC_Period_ID(getCtx(), hoy, stk.getAD_Org_ID()));

				// CUANDO BUSCO CANTIDADES NEGATIVAS EN EL QUERY DE ARRIBA (qty < 0)
				stk.setMovementQty(rs.getBigDecimal("qty").negate());
				stk.setsign(Env.ONE);
				
				// CUANDO BUSCO CANTIDADES POSITIVA EN EL QUERY DE ARRIBA (qty > 0)
				//stk.setMovementQty(rs.getBigDecimal("qty"));
				//stk.setsign(new BigDecimal(-1));

				
				stk.setUY_StockStatus_ID(this.uyStockStatusID);
				stk.setM_Warehouse_ID(rs.getInt("m_warehouse_id"));
				stk.setM_Locator_ID(rs.getInt("m_locator_id"));
				stk.setM_Product_ID(rs.getInt("m_product_id"));
				stk.setC_UOM_ID(100);
				stk.setM_AttributeSetInstance_ID(0);
				stk.setC_DocType_ID(docAjusteID);
				stk.setAD_Table_ID(tableID);
				stk.setRecord_ID(9000000);
				stk.setDocumentNo("0");
				stk.setc_doctype_affected_id(docAjusteID);
				stk.setad_table_affected_id(tableID);
				stk.setrecord_affected_id(9000000);
				stk.setdocumentno_affected("0");
				stk.setDocAction("CO");
				stk.saveEx(get_TrxName());
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
	}
}
