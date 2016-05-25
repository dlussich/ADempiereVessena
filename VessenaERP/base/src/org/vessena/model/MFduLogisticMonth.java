/**
 * 
 */
package org.openup.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;

/**
 * @author gbrust
 *
 */
public class MFduLogisticMonth extends X_UY_Fdu_LogisticMonth {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7578912344485076328L;

	/**
	 * @param ctx
	 * @param UY_Fdu_LogisticMonth_ID
	 * @param trxName
	 */
	public MFduLogisticMonth(Properties ctx, int UY_Fdu_LogisticMonth_ID,
			String trxName) {
		super(ctx, UY_Fdu_LogisticMonth_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MFduLogisticMonth(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	
	public Timestamp getFirsDate(){
		
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		Timestamp retorno = null;
		
		try{		
					
			String sql = "select min(datetrx) as datetrx" +
					     " from uy_fdu_logisticmonthdates" + 
					     " where uy_fdu_logisticmonth_id = " + this.get_ID();					    
			
			pstmt = DB.prepareStatement (sql, get_TrxName());
		    rs = pstmt.executeQuery();
		    
		   	while (rs.next()){
				retorno = rs.getTimestamp("datetrx");
			}
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
		finally {
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
		return retorno;
	}
	
	public Timestamp getLastDate(){
		
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		Timestamp retorno = null;
		
		try{		
					
			String sql = "select max(datetrx) as datetrx" +
					     " from uy_fdu_logisticmonthdates" + 
					     " where uy_fdu_logisticmonth_id = " + this.get_ID();
					     			
			pstmt = DB.prepareStatement (sql, get_TrxName());
		    rs = pstmt.executeQuery();
		    
		   	while (rs.next()){
				retorno = rs.getTimestamp("datetrx");
			}
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
		finally {
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
		return retorno;
	}
	
	
	
	
	

}
