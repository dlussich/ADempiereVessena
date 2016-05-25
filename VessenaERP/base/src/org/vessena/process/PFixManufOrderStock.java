/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 09/06/2013
 */
package org.openup.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MDocType;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.openup.model.MManufLine;
import org.openup.model.MManufOrder;
import org.openup.model.MStockStatus;
import org.openup.model.MStockTransaction;

/**
 * org.openup.process - PFixManufOrderStock
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Hp - 09/06/2013
 * @see
 */
public class PFixManufOrderStock extends SvrProcess {

	/**
	 * Constructor.
	 */
	public PFixManufOrderStock() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 09/06/2013
	 * @see
	 */
	@Override
	protected void prepare() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 09/06/2013
	 * @see
	 * @return
	 * @throws Exception
	 */
	@Override
	protected String doIt() throws Exception {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try{
			
			this.getProcessInfo().getWaiting().setText("Iniciando...");
			
			sql = " select uy_manuforder_id " +
				  " from uy_manuforder " +
				  " where ad_org_id = 1000010 " +
				  " and docstatus='CO' " +
				  " and datetrx > '2013-05-26 00:00:00'";
			
			pstmt = DB.prepareStatement (sql, null);
		
			rs = pstmt.executeQuery ();

			String message = null;
			
			// Corte de control cabezal de afectacion y recibo
			while (rs.next()){
				
				MManufOrder model = new MManufOrder(getCtx(), rs.getInt(1), get_TrxName());
				
				Timestamp fechaMovimiento = model.getDateTrx(); 
				
				List<MManufLine> lines = model.getLines();

				for (MManufLine line: lines){

					MDocType doc = new MDocType(model.getCtx(), model.getC_DocType_ID(), model.get_TrxName());
					if (doc.getC_DocType_ID() <= 0) return null;
					
					BigDecimal qty = line.getQty();
						
					// Me aseguro cantidad negativa
					if (qty.compareTo(Env.ZERO) > 0) qty = qty.multiply(new BigDecimal(-1));

					// Muevo stock fisico aprobado
					int idStatusApproved = MStockStatus.getStatusApprovedID(null);
					message = MStockTransaction.add(model, null, model.getM_Warehouse_ID(), model.getM_Locator_ID(), 
							line.getM_Product_ID(),	0, idStatusApproved, fechaMovimiento, qty, line.getUY_ManufLine_ID(), null);

					if (message != null) throw new AdempiereException(message);
					
				}
				
			}
			
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

		return "OK";
		
	}

}
