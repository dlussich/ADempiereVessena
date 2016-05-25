/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 01/07/2013
 */
package org.openup.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.openup.model.MRReclamo;

/**
 * org.openup.process - PReclamoSetEmails
 * OpenUp Ltda. Issue #1077 
 * Description: Proceso para envio automaticos de emails de incidencias.
 * @author Gabriel Vila - 01/07/2013
 * @see
 */
public class PReclamoSetEmails extends SvrProcess {

	/**
	 * Constructor.
	 */
	public PReclamoSetEmails() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 01/07/2013
	 * @see
	 */
	@Override
	protected void prepare() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 * OpenUp Ltda. Issue # 
	 * @author Hp - 01/07/2013
	 * @see
	 * @return
	 * @throws Exception
	 */
	@Override
	protected String doIt() throws Exception {

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		String action = "";
		
		try{
			
			Timestamp today = new Timestamp(System.currentTimeMillis());
			
			sql = " select uy_r_reclamo_id, reclamoemailtype, uy_r_reclamoemail_id " +
				  " from uy_r_reclamoemail " +
				  " where isexecuted = 'N' " +
				  " and startdate <=? " +
				  " order by startdate ";
			
			pstmt = DB.prepareStatement(sql.toString(), null);
			pstmt.setTimestamp(1, today);			

			rs = pstmt.executeQuery();

			while (rs.next()) {

				// Instancio reclamo y envio email segun tipo de aviso
				MRReclamo reclamo = new MRReclamo(getCtx(), rs.getInt("uy_r_reclamo_id"), null);
				String mailText = reclamo.sendAdviceEmail(rs.getString("reclamoemailtype"));
				
				if (mailText != null){
					mailText = " mailtext ='" + mailText.replaceAll("'", "''") + "', ";
				}
				else{
					mailText="";
				}
				
				// Actualizo este registro de email
				action = " update uy_r_reclamoemail " +
						 " set isexecuted ='Y', " +
						 mailText +
						 " enddate ='" + today + "' " +
						 " where uy_r_reclamoemail_id =" + rs.getInt("uy_r_reclamoemail_id");
				DB.executeUpdateEx(action, get_TrxName());

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
