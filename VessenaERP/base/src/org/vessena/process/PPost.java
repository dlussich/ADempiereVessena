/**
 * 
 */
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

/**
 * @author Nicolas
 *
 */
public class PPost extends SvrProcess {
	
	/* The Accounting Schema */
	private int  p_C_AcctSchema_ID = 0;
	/* The Table */
	private int  p_AD_Table_ID = 0;

	/**	Accounting Schema			*/
	private MAcctSchema[] 		m_ass = null;
	
	private int clientID = 0;
	
	private int doctypeID = 0;
	
	private String whereClause = "";
	
	private boolean rePost = false;

	/**
	 * 
	 */
	public PPost() {
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
			else if (name.equals("AD_Table_ID"))
				p_AD_Table_ID = para[i].getParameterAsInt();
			else if (name.equals("AD_Client_ID"))
				clientID = para[i].getParameterAsInt();
			else if (name.equals("C_DocType_ID"))
				doctypeID = para[i].getParameterAsInt();
			else if (name.equals("WhereClause"))
				whereClause = para[i].getParameter().toString();
			else if (name.equals("IsRePost")){
				String repost = ((String)para[i].getParameter());
				if(repost.equalsIgnoreCase("Y")){
					this.rePost = true;
				} else this.rePost = false;
			}
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
	
	/*private String post(){
		
		String message = null,sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		MInvoice hdr = null;
				
		try{
			
			sql = "select distinct c_invoice_id" +
                  " from c_invoice" +
                  " where c_doctypetarget_id = 1000293" +
                  " and ad_client_id = 1000006";
			pstmt = DB.prepareStatement (sql, get_TrxName());
			rs = pstmt.executeQuery ();
			
			while(rs.next()){
				
				hdr = new MInvoice(getCtx(),rs.getInt("c_invoice_id"),get_TrxName());
				
				message = DocumentEngine.postImmediate(getCtx(), getAD_Client_ID(), I_C_Invoice.Table_ID, hdr.get_ID(), true, get_TrxName());
				if(message != null) return message;
				
			}
						
		} catch(Exception e) {
			throw new AdempiereException(e);

		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
				
		return message;
		
	}*/
	
	private void post(){

		String TableName = "", error = "", where1;

		try{

			MTable table = new MTable(getCtx(),this.p_AD_Table_ID,get_TrxName());
			TableName = MTable.getTableName(getCtx(), this.p_AD_Table_ID);

			where1 = " WHERE ad_client_ID = " + this.clientID + " AND c_doctype_id = " + this.doctypeID;

			//si es la tabla C_Invoice
			if(table.get_ID()==318) where1 += " AND c_doctypetarget_id = " + this.doctypeID;
			
			String sql = "SELECT * FROM " + TableName + where1 + " " + this.whereClause;

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
							
							if(this.rePost){
								
								error = DocumentEngine.postImmediate(getCtx(), this.clientID, this.p_AD_Table_ID, doc.get_ID(), true, innerTrxName);
								ok = (error == null);
								postStatus = doc.getPostStatus();
								
							} else {
								
								error = doc.post(false, false);   //  post no force/repost
								ok = (error == null);
								postStatus = doc.getPostStatus();							
								
							}					
						
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
