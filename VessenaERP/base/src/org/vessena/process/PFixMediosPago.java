/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 02/05/2013
 */
package org.openup.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MPayment;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

/**
 * org.openup.process - PFixMediosPago
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Hp - 02/05/2013
 * @see
 */
public class PFixMediosPago extends SvrProcess {

	/**
	 * Constructor.
	 */
	public PFixMediosPago() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 02/05/2013
	 * @see
	 */
	@Override
	protected void prepare() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 02/05/2013
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
			
			sql = " SELECT c_payment_id from aux_mp "; 
			
			pstmt = DB.prepareStatement(sql.toString(), null);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				MPayment payment = new MPayment(getCtx(), rs.getInt(1), get_TrxName());
				payment.reActivateIt();
				payment.completeIt();
				
			}

			
		} catch (Exception e) {

			throw new AdempiereException(e.getMessage());

		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		
		return "OK";
	}

}
