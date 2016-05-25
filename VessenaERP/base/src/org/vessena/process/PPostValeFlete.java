package org.openup.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.acct.Doc;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MTable;
import org.compiere.process.DocumentEngine;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Trx;

public class PPostValeFlete extends SvrProcess {
	
	/* The Accounting Schema */
	private int  p_C_AcctSchema_ID = 0;
	/* The Table */
	private int  p_AD_Table_ID = 318;

	/**	Accounting Schema			*/
	private MAcctSchema[] 		m_ass = null;
	
	private int clientID = 0;

	public PPostValeFlete() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void prepare() {
		
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("C_AcctSchema_ID"))
				p_C_AcctSchema_ID = para[i].getParameterAsInt();
			else if (name.equals("AD_Client_ID"))
				clientID = para[i].getParameterAsInt();
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}

	}

	@Override
	protected String doIt() throws Exception {
		
		if (p_C_AcctSchema_ID == 0)
			m_ass = MAcctSchema.getClientAcctSchema(getCtx(), this.clientID);
		else	//	only specific accounting schema
			m_ass = new MAcctSchema[] {new MAcctSchema (getCtx(), p_C_AcctSchema_ID, get_TrxName())};
		
		post();
	
		return "OK";
	}
	
	private void post(){

		String TableName = "", error = "";

		try{

			MTable table = new MTable(getCtx(),this.p_AD_Table_ID,get_TrxName());
						
			String sql = "select *" +
                         " from c_invoice i" +  
                         " inner join uy_tr_transorder o on i.uy_tr_transorder_id = o.uy_tr_transorder_id" +
                         " inner join alloc_invoiceamtopen v on i.c_invoice_id = v.c_invoice_id" +
                         " where i.ad_client_id = " + this.clientID + " and i.c_doctypetarget_id = 1000293 and i.docstatus='CO'" + 
                         " and i.dateinvoiced between '2015-02-01 00:00:00' and '2015-02-28 00:00:00' and i.c_currency_id = 142" +
                         " and (v.amtopen >= -0.99 and v.amtopen <= 0.99)"; 
                         
                         /*
                         +
                         " and NOT EXISTS (select uy_tr_crt_id from uy_tr_transorderline ol where ol.uy_tr_transorder_id = o.uy_tr_transorder_id" + 
                         " and ol.uy_tr_crt_id NOT IN (select uy_tr_crt_id from vuy_facturasfletecomas))";
                         */

			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try
			{
				pstmt = DB.prepareStatement(sql.toString(), get_TrxName());
				rs = pstmt.executeQuery();

				while (rs.next())
				{

					boolean ok = true;
					// Run every posting document in own transaction
					String innerTrxName = Trx.createTrxName("CAP");
					Trx innerTrx = Trx.get(innerTrxName, true);
					String postStatus = Doc.STATUS_NotPosted; 
					Doc doc = Doc.get (m_ass, table.get_ID(), rs, innerTrxName);
					try
					{
						if (doc == null)
						{
							log.severe(getName() + ": No Doc for " + TableName);
							ok = false;
						}
						else
						{

							error = DocumentEngine.postImmediate(getCtx(), this.clientID, this.p_AD_Table_ID, doc.get_ID(), true, innerTrxName);
							ok = (error == null);
							postStatus = doc.getPostStatus();

						}
					}
					catch (Exception e)
					{
						log.log(Level.SEVERE, getName() + ": " + TableName, e);
						ok = false;
					}
					finally
					{
						if (ok)
							innerTrx.commit();
						else {
							innerTrx.rollback();
							// save the posted status error (out of trx)
							DB.executeUpdateEx("update " + TableName + " set posted = '" + postStatus + "', processing = 'N' where " + doc.get_TableName() + "_ID = " + doc.get_ID(), null);
						}
						innerTrx.close();
						innerTrx = null;
					}
			
				}
			}
			catch (Exception e)
			{
				throw new AdempiereException(e);
			}
			finally
			{
				DB.close(rs, pstmt);
				rs = null;
				pstmt = null;
			}

		} catch(Exception e) {
			throw new AdempiereException(e);

		} 


	}

}
