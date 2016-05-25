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
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.compiere.model.X_M_PromotionDistribution;
import org.compiere.model.X_M_PromotionLine;
import org.compiere.util.DB;
import org.compiere.util.Env;

public class MPromotionLine extends X_M_PromotionLine {

	private static final long serialVersionUID = -8284722914757724765L;

	public MPromotionLine(Properties ctx, int M_PromotionLine_ID, String trxName) {
		super(ctx, M_PromotionLine_ID, trxName);
	}

	public MPromotionLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/**
	 * OpenUp.	
	 * Descripcion : Obtiene distribuciones posibles de esta promotionline
	 * @param cBPartnerID
	 * @return
	 * @throws Exception
	 * @author  Gabriel Vila 
	 * Fecha : 17/12/2010
	 */
	public MPromotionDistribution [] getDistributions() throws Exception{
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		List<MPromotionDistribution> list = new ArrayList<MPromotionDistribution>();
		
		try{
			sql ="SELECT m_promotiondistribution_id " + 
 		  	" FROM " + X_M_PromotionDistribution.Table_Name + 
		  	" WHERE m_promotion_id =?" +
		  	" AND m_promotionline_id =?" +
		  	" AND IsActive='Y'" +
		  	" ORDER BY Qty";
			
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt(1, this.getM_Promotion_ID());
			pstmt.setInt(2, this.getM_PromotionLine_ID());
			
			rs = pstmt.executeQuery ();
		
			while (rs.next()){
				MPromotionDistribution value = new MPromotionDistribution(Env.getCtx(), rs.getInt(1), null);
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
		
		return list.toArray(new MPromotionDistribution[list.size()]);		
	}

}
