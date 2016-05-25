/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. usuario - 04/10/2013
 */
package org.openup.model;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;

/**
 * org.openup.model - MTTPostalGrp
 * OpenUp Ltda. Issue #1173 
 * Description: Modelo de Cierre por Modelo de Liquidacion en Tracking de Tarjetas.
 * @author usuario - 04/10/2013
 * @see
 */
public class MTTPostalGrp extends X_UY_TT_PostalGrp {

	private static final long serialVersionUID = 385422421124374317L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_TT_PostalGrp_ID
	 * @param trxName
	 */
	public MTTPostalGrp(Properties ctx, int UY_TT_PostalGrp_ID, String trxName) {
		super(ctx, UY_TT_PostalGrp_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTTPostalGrp(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/***
	 * Obtiene y retorna modelo segun codigo postal, modelo de liquidacion y grupo de ctacte. 
	 * OpenUp Ltda. Issue #1173 
	 * @author Gabriel Vila - 15/10/2013
	 * @see
	 * @param ctx
	 * @param postal
	 * @param modLiq
	 * @param grpCtaCte
	 * @param trxName
	 * @return
	 */
	public static MTTPostalGrp forPostalLiqGrp(Properties ctx, String postal, String modLiq, String grpCtaCte, String trxName){
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		MTTPostalGrp value = null;
		
		try{
			sql = " select grp.uy_tt_postalgrp_id " +
				  " from uy_tt_postalgrp grp " +
				  " inner join uy_tt_postalliq liq on grp.uy_tt_postalliq_id = liq.uy_tt_postalliq_id " +
				  " inner join uy_tt_postal pt on liq.uy_tt_postal_id = pt.uy_tt_postal_id " +
				  " where pt.postal =? " +
				  " and liq.value =? " +
				  " and grp.value =? "; 
			
			pstmt = DB.prepareStatement (sql, trxName);
			pstmt.setString(1, postal);
			pstmt.setString(2, modLiq);
			pstmt.setString(3, grpCtaCte);
			rs = pstmt.executeQuery ();
		
			if (rs.next()){
				value = new MTTPostalGrp(ctx, rs.getInt(1), trxName);
			}
				
		}
		catch (Exception e)
		{
			throw new AdempiereException(e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
		return value;
	}
	
}
