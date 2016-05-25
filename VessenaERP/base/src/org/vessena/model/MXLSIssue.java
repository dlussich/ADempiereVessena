/**
 * MReserveOrders.java
 * 11/01/2011
 */
package org.openup.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.util.DB;

/**
 * OpenUp.
 * MReserveOrders
 * Descripcion :
 * @author FL
 * Fecha : 11/01/2011
 */
public class MXLSIssue extends X_UY_XLSIssue {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4758633381879049856L;


	/**
	 * Constructor
	 * @param ctx
	 * @param UY_InOutType
	 * @param trxName
	 */
	public MXLSIssue(Properties ctx, int UY_InOutType_ID,String trxName) {
		super(ctx, UY_InOutType_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MXLSIssue(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Factory method, create an save add an issue 
	 * @param ctx
	 * @param AD_Table_ID, parent of the table that the cell should be inserted or updated
	 * @param Record_ID, record id of the parent of the table that the cell should be inserted or updated
	 * @param Filename, the Excel file where the cell was readed
	 * @param sheet, name of the sheet in the Excel file 
	 * @param cell, name of the cell in the Excel file 
	 * @param ContentText, string value of the cell
	 * @param ErrorMsg, description 
	 * @param trxName
	 */
	public static MXLSIssue Add(Properties ctx, int AD_Table_ID,int Record_ID, String Filename, String sheet, String cell, String ContentText, String ErrorMsg, String trxName) {

		MXLSIssue XLSIssue=new MXLSIssue(ctx,0,trxName);
		
		XLSIssue.setAD_Table_ID(AD_Table_ID);
		XLSIssue.setRecord_ID(Record_ID);
		XLSIssue.setFileName(Filename);
		XLSIssue.setsheet(sheet);
		XLSIssue.setcell(cell);
		XLSIssue.setContentText(ContentText);
		if (ErrorMsg == null) ErrorMsg = "Error";
		XLSIssue.setErrorMsg(ErrorMsg);
		
		XLSIssue.saveEx();
		
		return(XLSIssue);
		
	}

	/**
	 * Factory method, delete all Excel issues form a record of a parent table
	 * @param ctx
	 * @param AD_Table_ID, parent of the table that the cell should be inserted or updated
	 * @param Record_ID, record id of the parent of the table that the cell should be inserted or updated
	 * @param trxName
	 * @throws Exception 
	 */
	public static boolean Delete(Properties ctx, int AD_Table_ID,int Record_ID, String trxName) throws Exception {
		
		boolean deleted=false;

		String SQL="DELETE FROM uy_xlsissue WHERE uy_xlsissue.ad_table_id=? AND uy_xlsissue.record_id=?";
		PreparedStatement pstmt=null;
		
		try {
			pstmt=DB.prepareStatement (SQL, null);
			pstmt.setInt(1,AD_Table_ID);
			pstmt.setInt(2,Record_ID);
			
			// Just run de query, 
			pstmt.executeUpdate();
			deleted=true;
		}
		catch (Exception e) {
			throw e;
		}
		finally {
			pstmt= null;
		}
		
		return(deleted);
		
	}

}
