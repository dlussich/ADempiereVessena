/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. gabriel - Sep 30, 2015
*/
package org.openup.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.openup.model.MTTReceipt;
import org.openup.model.MTTReceiptBox;

/**
 * org.openup.process - PTTLoadSubAgency
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author gabriel - Sep 30, 2015
*/
public class PTTLoadSubAgency extends SvrProcess {

	private MTTReceipt receipt = null;
	
	/***
	 * Constructor.
	*/

	public PTTLoadSubAgency() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {

		this.receipt = new MTTReceipt(getCtx(), this.getRecord_ID(), get_TrxName());
		
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try {
			
			sql = " select uy_deliverypoint_id_to, max(uy_tt_box_id)::numeric(10,0) as uy_tt_box_id "
				+ " from uy_tt_box "
				+ " where isactive='Y' "
				+ " and boxstatus in('CERRADO', 'RECEPCUENTA') "
				+ " and uy_deliverypoint_id_to in (select uy_deliverypoint_id from uy_deliverypoint "
				+ " where uy_deliverypoint_id_from is not null and isactive='Y') "
				+ " group by uy_deliverypoint_id_to ";
			
			pstmt = DB.prepareStatement(sql.toString(), null);
			rs = pstmt.executeQuery();

			while (rs.next()) {				
			
				MTTReceiptBox rBox = new MTTReceiptBox(getCtx(), 0, get_TrxName());
				
				rBox.setUY_TT_Receipt_ID(receipt.get_ID());
				rBox.setUY_TT_Box_ID(rs.getInt("uy_tt_box_id"));
				rBox.setUY_DeliveryPoint_ID(rs.getInt("uy_deliverypoint_id_to"));
				rBox.setIsRetained(false);
				rBox.setUnificaCardCarrier(false);
				rBox.setComunicaUsuario(false);

				rBox.saveEx();
				
			}
			
		} 
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		finally{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		
		return "OK";

	}

}
