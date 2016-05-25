/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. gabriel - Nov 12, 2015
*/
package org.openup.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;


import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.openup.model.MLabelPrint;
import org.openup.model.MLabelPrintScan;
import org.openup.model.MProductUpc;

/**
 * org.openup.process - PRTLoadProductLabel
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author gabriel - Nov 12, 2015
*/
public class PRTLoadProductLabel extends SvrProcess {

	private MLabelPrint labelPrint = null;
	
	/***
	 * Constructor.
	*/

	public PRTLoadProductLabel() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		
		this.labelPrint = new MLabelPrint(getCtx(), this.getRecord_ID(), get_TrxName());
		
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try{

			sql = " select distinct(m_product_id), c_currency_id from uy_productprintlabel "; 
			
			pstmt = DB.prepareStatement(sql, get_TrxName());

			rs = pstmt.executeQuery();

			while (rs.next()){
				
				// Obtengo ultimo codigo de barra para este producto
				String sqlUPC = " select max(uy_productupc_id) from uy_productupc where m_product_id =" + rs.getInt("m_product_id");
				int productUpcID = DB.getSQLValueEx(null, sqlUPC);
				
				if (productUpcID > 0){
					MProductUpc prodUPC = new MProductUpc(getCtx(), productUpcID, null);
					MLabelPrintScan printScan = new MLabelPrintScan(getCtx(), 0, get_TrxName());
					printScan.setUY_LabelPrint_ID(this.labelPrint.get_ID());
					printScan.setScanText(prodUPC.getUPC());
					printScan.set_ValueOfColumn("C_Currency_ID", rs.getInt("c_currency_id"));//SBT 08/04/2016 Issue #5733
					printScan.saveEx();
				}
			
			}
			
			DB.executeUpdateEx(" delete from uy_productprintlabel ", get_TrxName());

			
		} 
		catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		} 
		finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		
		return "OK";
	}

}
