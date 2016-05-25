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
 * Fecha : 29/03/2011
 */
public class MDiff extends X_AD_Diff {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4758633381879049456L;


	/**
	 * Constructor
	 * @param ctx
	 * @param UY_InOutType
	 * @param trxName
	 */
	public MDiff(Properties ctx, int UY_InOutType_ID,String trxName) {
		super(ctx, UY_InOutType_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MDiff(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 	Factory, get diff by record id
	 *	@param ctx context
	 *	@param tableName case insensitive table name
	 *	@return Table
	 * @throws Exception 
	 */
	public static MDiff getByRecordID(Properties ctx, int AD_DiffTable_ID,int Record_ID,String trxName) throws Exception {
		
		String SQL="SELECT * FROM ad_diff WHERE ad_difftable_id=? AND record_id=?";
		ResultSet rs = null;
		PreparedStatement pstmt=null;

		MDiff diff=null;
		
		try {
			pstmt=DB.prepareStatement(SQL,trxName);
			pstmt.setInt(1,AD_DiffTable_ID);
			pstmt.setInt(2,Record_ID);
			rs=pstmt.executeQuery();
			
			// Get the object 
			if (rs.next()) {
				diff=new MDiff(ctx,rs,trxName);
			}
			else { 
				diff=new MDiff(ctx,0,trxName);
				diff.setAD_DiffTable_ID(AD_DiffTable_ID);
				diff.setRecord_ID(Record_ID);
			}
		}
		catch (Exception e) {
			throw e;
		}
		finally {
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
		return(diff);			
	}
	
}
