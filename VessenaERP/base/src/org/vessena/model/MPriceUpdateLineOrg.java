/**
 * @author OpenUp SBT Issue#  29/1/2016 12:46:26
 */
package org.openup.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.Query;
import org.compiere.util.DB;

/**
 * @author OpenUp SBT Issue#  29/1/2016 12:46:26
 *
 */
public class MPriceUpdateLineOrg extends X_UY_PriceUpdateLineOrg {

	/**
	 * @author OpenUp SBT Issue#  29/1/2016 12:46:30
	 * @param ctx
	 * @param UY_PriceUpdateLineOrg_ID
	 * @param trxName
	 */
	public MPriceUpdateLineOrg(Properties ctx, int UY_PriceUpdateLineOrg_ID,
			String trxName) {
		super(ctx, UY_PriceUpdateLineOrg_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @author OpenUp SBT Issue#  29/1/2016 12:46:30
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MPriceUpdateLineOrg(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord) {
		if(newRecord){
			MPriceUpdateLine plLine = new MPriceUpdateLine(getCtx(), this.getUY_PriceUpdateLine_ID(), get_TrxName());
			if(!plLine.isSelected2()) 
				throw new AdempiereException("El producto no está seleccionado para aplicar venta diferencial");

		}
		return super.beforeSave(newRecord);
	}

	/**
	 * 
	 * @author OpenUp SBT Issue #5392  29/1/2016 16:46:53
	 * @param ctx
	 * @param m_PriceUpdateLineID
	 * @param m_OrgID
	 * @param get_TrxName
	 * @return
	 */
	public static MPriceUpdateLineOrg forUpdateLineAndOrg(Properties ctx,
			int m_PriceUpdateLineID, int m_OrgID, String get_TrxName) {
		
		MPriceUpdateLineOrg lineORg = null;
		
		String whereClause = X_UY_PriceUpdateLineOrg.COLUMNNAME_UY_PriceUpdateLine_ID +" = "+m_PriceUpdateLineID
				+" AND "+ X_UY_PriceUpdateLineOrg.COLUMNNAME_AD_Org_ID_To + " = " + m_OrgID 
				 +" AND "+ X_UY_PriceUpdateLineOrg.COLUMNNAME_IsActive + " = 'Y' ";
				
		lineORg = new Query(ctx, Table_Name, whereClause, null)
					.setClient_ID()
					.first();

		return lineORg;
	}

	/**
	 * 
	 * @author OpenUp SBT Issue#  29/1/2016 16:48:21
	 * @param get_ID
	 * @param get_TrxName
	 * @return
	 */
	public static int[] getOrgs(String get_TrxName) {
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

	public static int[] getOrgsUpdate(int mPriceUpdateID, String get_TrxName) {
		ResultSet rs = null;PreparedStatement pstmt =  null;int[] retorno = null;	
		try{
			String sql = "select distinct(AD_Org_ID_To) from UY_PriceUpdateLineOrg where UY_PriceUpdateLine_ID in"
					+ " (Select UY_PriceUpdateLine_ID from UY_PriceUpdateLine where UY_PriceUpdate_ID = "+mPriceUpdateID+")";
		
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

}
