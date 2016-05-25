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
import org.compiere.model.X_M_PromotionReward;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 *
 * @author hengsin
 *
 */
public class MPromotionReward extends X_M_PromotionReward {

	private static final long serialVersionUID = -1466367082383341103L;

	public MPromotionReward(Properties ctx, int M_PromotionReward_ID,
			String trxName) {
		super(ctx, M_PromotionReward_ID, trxName);
	}

	public MPromotionReward(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/**
	 * OpenUp.	
	 * Descripcion : Obtiene reward para una determinada distribution. 
	 * @param ctx
	 * @param mPromotionDistributionID
	 * @param mPromotionID
	 * @param trxName
	 * @return
	 * @throws Exception
	 * @author  Gabriel Vila 
	 * Fecha : 20/12/2010
	 */
	public static MPromotionReward get(Properties ctx, int mPromotionDistributionID, int mPromotionID, String trxName) throws Exception{
	
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		MPromotionReward value = null;
		
		try{
			sql ="SELECT m_promotionreward_id " + 
			  	" FROM " + X_M_PromotionReward.Table_Name + 
			  	" WHERE m_promotion_id =?" +
			  	" AND m_promotiondistribution_id =?";
			
			pstmt = DB.prepareStatement (sql, trxName);
			pstmt.setInt(1, mPromotionID);
			pstmt.setInt(2, mPromotionDistributionID);
			
			rs = pstmt.executeQuery ();
		
			if (rs.next()){
				value = new MPromotionReward(Env.getCtx(), rs.getInt(1), trxName);
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
