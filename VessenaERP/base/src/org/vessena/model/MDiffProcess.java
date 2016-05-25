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
public class MDiffProcess extends X_AD_DiffProcess {

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
	public MDiffProcess(Properties ctx, int UY_InOutType_ID,String trxName) {
		super(ctx, UY_InOutType_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MDiffProcess(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Factory method, delete all records of a child table
	 * @param trxName
	 * @throws Exception 
	 */
	public boolean DeleteTables(String trxName) throws Exception {
		
		boolean deleted=false;

		String SQL="DELETE FROM ad_difftable WHERE ad_difftable.ad_diffprocess_id=?";
		PreparedStatement pstmt=null;
		
		try {
			pstmt=DB.prepareStatement(SQL, trxName);
			pstmt.setInt(1,this.getAD_DiffProcess_ID());
			
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
