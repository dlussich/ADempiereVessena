/**
 * @author OpenUp SBT Issue#  28/1/2016 12:36:33
 */
package org.openup.model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MProductPO;
import org.compiere.model.Query;
import org.compiere.model.X_M_Product_PO;
import org.compiere.util.DB;

/**
 * @author OpenUp SBT Issue #5386  28/1/2016 12:36:33
 *
 */
public class MPriceLoadLineOrg extends X_UY_PriceLoadLineOrg {

	/**
	 * @author OpenUp SBT Issue #5386  28/1/2016 12:37:04
	 * @param ctx
	 * @param UY_PriceLoadLineOrg_ID
	 * @param trxName
	 */
	public MPriceLoadLineOrg(Properties ctx, int UY_PriceLoadLineOrg_ID,
			String trxName) {
		super(ctx, UY_PriceLoadLineOrg_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @author OpenUp SBT Issue #5386  28/1/2016 12:37:04
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MPriceLoadLineOrg(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	
	@Override
	protected boolean beforeSave(boolean newRecord) {
		if(newRecord){
			MPriceLoadLine plLine = new MPriceLoadLine(getCtx(), this.getUY_PriceLoadLine_ID(), get_TrxName());
			if(!plLine.get_ValueAsBoolean("IsSelected")) 
				throw new AdempiereException("El producto no está seleccionado para aplicar venta diferencial");

		}
		return super.beforeSave(newRecord);
	}

	
	public static int[] getOrgs(int mPriceLoadID,String trxName) {
		ResultSet rs = null;PreparedStatement pstmt =  null;int[] retorno = null;	
		try{
			String sql = "select AD_Org_ID from AD_Org where IsActive = 'Y' AND AD_Org_ID != 0";
		
			pstmt = DB.prepareStatement (sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, null );
			rs = pstmt.executeQuery ();
			if(rs!=null){
				rs.last();
				retorno = new int[rs.getRow()];
				rs.beforeFirst();
				while (rs.next()){
					retorno[rs.getRow()-1] = rs.getInt(1);
				}
			}
			return retorno;
		
		}catch (Exception ex){
			throw new AdempiereException(ex.getMessage());
		}finally{
			try{
				if(rs!=null){
					rs.close();
					if(pstmt!=null){
						pstmt.close();
					}
				}
			}catch(Exception e){
				
			}	
		}
		
	}
	
	/**
	 * Obtengo lista de id de org que participan en la actualización de precio de venta
	 * @author OpenUp SBT Issue #5386  29/1/2016 10:04:48
	 * @param mPriceLoadID
	 * @param trxName
	 * @return
	 */
	public static int[] getOrgsLoad(int mPriceLoadID,String trxName) {
		ResultSet rs = null;PreparedStatement pstmt =  null;int[] retorno = null;	
		try{
			String sql = "select distinct(AD_Org_ID_To) from UY_PriceLoadLineOrg where UY_PriceLoadLine_ID in"
					+ " (Select UY_PriceLoadLine_ID from UY_PriceLoadLine where UY_PriceLoad_ID = "+mPriceLoadID+")";
		
			pstmt = DB.prepareStatement (sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, null );
			rs = pstmt.executeQuery ();
			if(rs!=null){
				rs.last();
				retorno = new int[rs.getRow()];
				rs.beforeFirst();
				while (rs.next()){
					retorno[rs.getRow()-1] = rs.getInt(1);
				}
			}
			return retorno;
		
		}catch (Exception ex){
			throw new AdempiereException(ex.getMessage());
		}finally{
			try{
				if(rs!=null){
					rs.close();
					if(pstmt!=null){
						pstmt.close();
					}
				}
			}catch(Exception e){
				
			}	
		}
		
	}

	/**
	 * 
	 * @author OpenUp SBT Issue #5386  28/1/2016 18:15:51
	 * @param ctx
	 * @param m_PriceLoadLineID
	 * @param m_OrgID
	 * @param get_TrxName
	 * @return
	 */
	public static MPriceLoadLineOrg forLoadLineAndOrg(Properties ctx,int m_PriceLoadLineID, int m_OrgID, String get_TrxName) {
		
		MPriceLoadLineOrg lineORg = null;
		
		String whereClause = X_UY_PriceLoadLineOrg.COLUMNNAME_UY_PriceLoadLine_ID +" = "+m_PriceLoadLineID
				+" AND "+ X_UY_PriceLoadLineOrg.COLUMNNAME_AD_Org_ID_To + " = " + m_OrgID 
				 +" AND "+ X_UY_PriceLoadLineOrg.COLUMNNAME_IsActive + " = 'Y' ";
				
		lineORg = new Query(ctx, Table_Name, whereClause, null)
					.setClient_ID()
					.first();

		return lineORg;
	}
}
