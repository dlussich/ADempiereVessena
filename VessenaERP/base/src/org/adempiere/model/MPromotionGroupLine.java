/******************************************************************************
 * Copyright (C) 2009 Low Heng Sin                                            *
 * Copyright (C) 2009 Idalica Corporation                                     *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/
package org.adempiere.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.X_M_PromotionGroupLine;
import org.compiere.util.DB;

/**
 *
 * @author hengsin
 *
 */
public class MPromotionGroupLine extends X_M_PromotionGroupLine {

	private static final long serialVersionUID = -3945719908086926013L;

	public MPromotionGroupLine(Properties ctx, int M_PromotionGroupLine_ID,
			String trxName) {
		super(ctx, M_PromotionGroupLine_ID, trxName);
	}

	public MPromotionGroupLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/**
	 * OpenUp.	
	 * Descripcion : Verifica si un determinado producto pertenece a las lineas de un 
	 * grupo de promociones.
	 * @param mProductID
	 * @param mPromotionGroupID
	 * @return
	 * @throws Exception
	 * @author  Gabriel Vila 
	 * Fecha : 20/12/2010
	 */
	public static boolean containsProduct(int mProductID, int mPromotionGroupID) throws Exception{
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		boolean value = false;
		
		try{
			sql ="SELECT * " + 
 		  	" FROM " + X_M_PromotionGroupLine.Table_Name + 
		  	" WHERE m_promotiongroup_id =?" +
		  	" AND m_product_id =?";
			
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt(1, mPromotionGroupID);
			pstmt.setInt(2, mProductID);
			
			rs = pstmt.executeQuery ();
		
			if (rs.next()) value = true;

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
