/**
 * MGruposClientes.java
 * 17/12/2010
 */
package org.openup.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.model.MPromotionPreCondition;
import org.compiere.model.X_M_PromotionPreCondition;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * OpenUp.
 * MGruposClientes
 * Descripcion :
 * @author Gabriel Vila
 * Fecha : 17/12/2010
 */
public class MGruposClientes extends X_UY_GruposClientes {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2998853830963418524L;

	/**
	 * Constructor
	 * @param ctx
	 * @param UY_GruposClientes_ID
	 * @param trxName
	 */
	public MGruposClientes(Properties ctx, int UY_GruposClientes_ID,
			String trxName) {
		super(ctx, UY_GruposClientes_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MGruposClientes(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * OpenUp.	
	 * Descripcion :
	 * @param cBPartnerID
	 * @return
	 * @throws Exception
	 * @author  Gabriel Vila 
	 * Fecha : 17/12/2010
	 */
	public MPromotionPreCondition [] getPromotionPreConditions(Timestamp DateTrx) throws Exception{
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		List<MPromotionPreCondition> list = new ArrayList<MPromotionPreCondition>();
		
		try{
			sql ="SELECT m_promotionprecondition_id " + 
 		  	" FROM " + X_M_PromotionPreCondition.Table_Name + 
		  	" WHERE uy_gruposclientes_id =?" +
		  	" AND startdate<=? AND enddate>=?" +
		  	" AND IsActive='Y'";
			
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt(1, this.getUY_GruposClientes_ID());
			pstmt.setTimestamp(2, DateTrx);
			pstmt.setTimestamp(3, DateTrx);
			
			rs = pstmt.executeQuery ();
		
			while (rs.next()){
				MPromotionPreCondition value = new MPromotionPreCondition(Env.getCtx(), rs.getInt(1), null);
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
		
		return list.toArray(new MPromotionPreCondition[list.size()]);		
	}

	
}
