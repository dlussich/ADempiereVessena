package org.openup.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;

import org.compiere.model.MInOut;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Trx;

public class PCompleteInOutReturns extends SvrProcess {
	
	@Override
	protected String doIt() throws Exception {
		
		// Get all records from product having a value
		String sql = "SELECT m_inout_id FROM m_inout WHERE (docstatus='DR' OR docstatus='IN') AND movementtype='C+' AND (c_order_id IS NULL OR c_order_id=0) AND (m_rma_id IS NULL OR m_rma_id=0)";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		String trxName = null;
		Trx trx = null;

		// This counters are used only to show the totals of returns processed or errors.
		int count=0;
		int error=0;
		
		// Create lines form from interfase
		try{
			pstmt = DB.prepareStatement (sql, get_TrxName());
			rs = pstmt.executeQuery();
		
			trxName = Trx.createTrxName();
			trx = Trx.get(trxName, true);
			
			// Just get the first record, else a null product will be returned
			while (rs.next()){
				int M_InOut_ID=rs.getInt(1);
				
				// 0 means null
				if (M_InOut_ID!=0) {
					// Get the model
					MInOut inOut = new MInOut(Env.getCtx(),M_InOut_ID,trxName);
					
					// Null means that cannot get the model
					if (inOut!=null) {
						
						// Set all object to draft to avoid problems with invalid ones
						inOut.setDocStatus(MInOut.DOCSTATUS_Drafted);
						
						if (inOut.processIt(MInOut.DOCACTION_Complete)) {
							inOut.saveEx(trxName);
							count++;
						}
						else {
							error++;
							log.log(Level.SEVERE,"No se pudo completar (m_inout_id="+M_InOut_ID+")");				// TODO: translate
						}
					} 
					else {
						error++;
						log.log(Level.SEVERE,"No pudo encontrar (m_inout_id="+M_InOut_ID+")");						// TODO: translate
					}
				}
				else {
					error++;
					log.log(Level.SEVERE,"No obtuvo un indentificador correcto");									// TODO: translate
				}
				
			}
			
			// Close, the commit also should ocurr here
			trx.close();
		}
		catch (Exception e)
		{
			log.info(e.getMessage());
			if (trx!=null) {
				trx.rollback();
			}
			throw new Exception(e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
		return(count+" devoluciones confirmadas, "+error+" devoluciones con error");								// TODO: translate
	}

	@Override
	protected void prepare() {
		// TODO: an optional shipment asignamente could be used
	}
	

}
