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

import org.compiere.process.DocAction;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.openup.model.MStockAdjustment;
import org.openup.model.MStockAdjustmentLine;
import org.openup.model.MStockStatus;

/**
 * @author Hp
 *
 */
public class PFixAprobadoAtr extends SvrProcess {

	/**
	 * 
	 */
	public PFixAprobadoAtr() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		// TODO Auto-generated method stub

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
		
		try{
			sql = " select * from aux_ajuatr1 ";
			pstmt = DB.prepareStatement (sql, null);
			rs = pstmt.executeQuery();

			boolean hayInfo = false;
			MStockAdjustment hdr = null;
			
			int idStatusApproved = MStockStatus.getStatusApprovedID(null);
			
			// Para cada linea obtenida
			while (rs.next()){

				
				if (!hayInfo){
					hayInfo = true;
					hdr = new MStockAdjustment(getCtx(), 0, get_TrxName());
					hdr.setC_DocType_ID(1000077);
					hdr.setDateTrx(hoy);
					hdr.setMovementDate(hoy);
					hdr.setAD_User_ID(this.getAD_User_ID());
					hdr.setUY_StockStatus_ID(idStatusApproved);
					hdr.setDocStatus(DocAction.STATUS_Drafted);
					hdr.setDocAction(DocAction.ACTION_Complete);
					hdr.setProcessed(false);
					hdr.setUY_AdjustmentReason_ID(1000002);
					hdr.saveEx(get_TrxName());
				}
				
				MStockAdjustmentLine line = new MStockAdjustmentLine(getCtx(), 0, get_TrxName());
				line.setUY_StockAdjustment_ID(hdr.getUY_StockAdjustment_ID());
				line.setM_Locator_ID(1000008);
				line.setM_AttributeSetInstance_ID(0);
				line.setUY_StockStatus_ID(hdr.getUY_StockStatus_ID());
				line.setM_Product_ID(rs.getInt("m_product_id"));
				
				BigDecimal cantidad = rs.getBigDecimal("saldo");
				
				line.setMovementQty(cantidad);
				line.setProcessed(false);
				line.saveEx(get_TrxName());
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
