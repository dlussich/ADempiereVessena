/**
 * MStockStatusListHdr.java
 * 29/03/2011
 */
package org.openup.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * OpenUp.
 * MStockStatusListHdr
 * Descripcion :
 * @author Gabriel Vila
 * Fecha : 29/03/2011
 */
public class MStockStatusListHdr extends X_UY_StockStatusListHdr {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4002726712182912858L;

	/**
	 * Constructor
	 * @param ctx
	 * @param UY_StockStatusListHdr_ID
	 * @param trxName
	 */
	public MStockStatusListHdr(Properties ctx, int UY_StockStatusListHdr_ID,
			String trxName) {
		super(ctx, UY_StockStatusListHdr_ID, trxName);
	}

	/**
	 * Constructor
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MStockStatusListHdr(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/**
	 * OpenUp.	
	 * Descripcion : Obtiene y retorna lineas de estados de stock para esta lista.
	 * @return
	 * @throws Exception
	 * @author  Gabriel Vila 
	 * Fecha : 30/03/2011
	 */
	public MStockStatusListLine[] getLines() throws Exception{
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		List<MStockStatusListLine> list = new ArrayList<MStockStatusListLine>();
		
		try{
			sql ="SELECT uy_stockstatuslistline_id " + 
 		  	" FROM " + X_UY_StockStatusListLine.Table_Name + 
		  	" WHERE uy_stockstatuslisthdr_id =?";
			
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt(1, this.getUY_StockStatusListHdr_ID());
			
			rs = pstmt.executeQuery ();
		
			while (rs.next()){
				MStockStatusListLine value = new MStockStatusListLine(Env.getCtx(), rs.getInt(1), null);
				list.add(value);
			}
		}
		catch (Exception e)
		{
			throw e;
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
		return list.toArray(new MStockStatusListLine[list.size()]);		
	}

}
