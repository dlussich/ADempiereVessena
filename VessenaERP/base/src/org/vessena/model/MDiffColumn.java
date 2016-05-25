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
public class MDiffColumn extends X_AD_DiffColumn {

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
	public MDiffColumn(Properties ctx, int AD_DiffColumn_ID,String trxName) {
		super(ctx, AD_DiffColumn_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MDiffColumn(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 	Factory, get diff column by column name
	 *	@param ctx context
	 *	@param tableName case insensitive table name
	 *	@return Table
	 * @throws Exception 
	 */
	public static MDiffColumn getByColumnName(Properties ctx, int AD_Diff_ID,String columnName,String trxName) throws Exception {
		
		String SQL="SELECT * FROM ad_diffcolumn WHERE ad_diff_id=? AND columnname=?";
		ResultSet rs = null;
		PreparedStatement pstmt=null;

		MDiffColumn diffColumn=null;
		
		try {
			pstmt=DB.prepareStatement(SQL,trxName);
			pstmt.setInt(1,AD_Diff_ID);
			pstmt.setString(2,columnName);
			rs=pstmt.executeQuery();
			
			// Get the object 
			if (rs.next()) {
				diffColumn=new MDiffColumn(ctx,rs,trxName);
			}
			else { 
				diffColumn=new MDiffColumn(ctx,0,trxName);
				diffColumn.setAD_Diff_ID(AD_Diff_ID);
				diffColumn.setColumnName(columnName);
			}
		}
		catch (Exception e) {
			throw e;
		}
		finally {
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
		return(diffColumn);			
	}
}
