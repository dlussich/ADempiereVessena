/**
 * MStockStatusByDocHdr.java
 * 29/03/2011
 */
package org.openup.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.MDocType;
import org.compiere.util.DB;

/**
 * OpenUp.
 * MStockStatusByDocHdr
 * Descripcion :
 * @author Gabriel Vila
 * Fecha : 29/03/2011
 */
public class MStockStatusByDocHdr extends X_UY_StockStatusByDocHdr {

	/**
	 * 
	 */
	private static final long serialVersionUID = 451340992731241019L;

	/**
	 * Constructor
	 * @param ctx
	 * @param UY_StockStatusByDocHdr_ID
	 * @param trxName
	 */
	public MStockStatusByDocHdr(Properties ctx, int UY_StockStatusByDocHdr_ID,
			String trxName) {
		super(ctx, UY_StockStatusByDocHdr_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MStockStatusByDocHdr(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor
	 * @param doc : MDocType. Modelo del documento asociado.
	 * @throws Exception 
	 */
	public static MStockStatusByDocHdr get(MDocType doc) throws Exception {
	
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		MStockStatusByDocHdr value = null;
		
		try{
			sql = " SELECT UY_StockStatusByDocHdr_ID " + 
 		  		  " FROM UY_StockStatusByDocHdr " + 
 		  		  " WHERE c_doctype_id =?" +
 		  		  " AND isActive='Y' "; 
			
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt(1, doc.getC_DocType_ID());
			
			rs = pstmt.executeQuery ();
		
			if (rs.next()){
				value = new MStockStatusByDocHdr(doc.getCtx(), rs.getInt(1), doc.get_TrxName());
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

		return value;
	}

}
