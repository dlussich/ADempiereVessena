/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 06/03/2013
 */
package org.openup.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.openup.model.MMediosPago;

/**
 * org.openup.process - PGenerateCheckLetter
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Hp - 06/03/2013
 * @see
 */
public class PGenerateCheckLetter extends SvrProcess {

	/**
	 * Constructor.
	 */
	public PGenerateCheckLetter() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 06/03/2013
	 * @see
	 */
	@Override
	protected void prepare() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 06/03/2013
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
			sql = " SELECT uy_mediospago_id, payamt " +
				  " FROM uy_mediospago "; 
				  //" WHERE checkletter is null";
			
			pstmt = DB.prepareStatement(sql, null);
			rs = pstmt.executeQuery ();
			
			
			while (rs.next()){
				
				MMediosPago mpago = new MMediosPago(Env.getCtx(), rs.getInt("uy_mediospago_id"), null);
				mpago.setLiteral();
				mpago.saveEx();
				
			}
			
		}
		catch (Exception e)
		{
			throw new AdempiereException(e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}

		return "OK";
	}

}
