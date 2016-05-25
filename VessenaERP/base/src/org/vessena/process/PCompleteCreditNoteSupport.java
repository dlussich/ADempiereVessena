package org.openup.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;

import org.compiere.model.MInvoice;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Trx;

public class PCompleteCreditNoteSupport extends SvrProcess {
	
	@Override
	protected String doIt() throws Exception {
		
		// Get all records from product having a value
		String sql = "SELECT c_invoice.c_invoice_id FROM c_invoice WHERE c_invoice.docstatus='RQ' AND c_invoice.c_doctypetarget_id=1000067";
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
				int C_Invoice_ID=rs.getInt(1);
				
				// 0 means null
				if (C_Invoice_ID!=0) {
					// Get the model
					MInvoice invoice = new MInvoice(Env.getCtx(),C_Invoice_ID,trxName);
					
					// Null means that cannot get the model
					if (invoice!=null) {
						
						// Set all object to draft to avoid problems with invalid ones
						invoice.setDocStatus(MInvoice.DOCSTATUS_Drafted);
						
						if (invoice.processIt(MInvoice.DOCACTION_Complete)) {
							invoice.saveEx(trxName);
							count++;
						}
						else {
							error++;
							log.log(Level.SEVERE,"No se pudo completar (m_inout_id="+C_Invoice_ID+")");				// TODO: translate
						}
					} 
					else {
						error++;
						log.log(Level.SEVERE,"No pudo encontrar (m_inout_id="+C_Invoice_ID+")");					// TODO: translate
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
		
		return(count+" notas de creditos confirmadas, "+error+" notas de credito con error");						// TODO: translate
	}

	@Override
	protected void prepare() {
		// TODO: an optional shipment asignamente could be used
	}
	

}
