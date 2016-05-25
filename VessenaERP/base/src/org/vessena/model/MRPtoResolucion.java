/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 05/02/2013
 */
package org.openup.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.Query;
import org.compiere.util.DB;

/**
 * org.openup.model - MRPtoResolucion
 * OpenUp Ltda. Issue # 
 * Description: 
 * @author Hp - 05/02/2013
 * @see
 */
public class MRPtoResolucion extends X_UY_R_PtoResolucion {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2228041062383033823L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_R_PtoResolucion_ID
	 * @param trxName
	 */
	public MRPtoResolucion(Properties ctx, int UY_R_PtoResolucion_ID,
			String trxName) {
		super(ctx, UY_R_PtoResolucion_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRPtoResolucion(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/***
	 * Obtiene y retorna lista de usuarios asociados a este punto de resolucion.
	 * OpenUp Ltda. Issue #285 
	 * @author Gabriel Vila - 19/03/2013
	 * @see
	 * @return
	 */
	public List<MRAreaUser> getUsers() {

		String whereClause = X_UY_R_AreaUser.COLUMNNAME_UY_R_PtoResolucion_ID + "=" + this.get_ID();

		List<MRAreaUser> values = new Query(getCtx(), I_UY_R_AreaUser.Table_Name, whereClause, get_TrxName())
		.setOnlyActiveRecords(true)
		.list();
		
		return values;
	}

	/***
	 * Valida que un usuario determinado pertenezca a este punto de resolucion. 
	 * OpenUp Ltda. Issue #285 
	 * @author Gabriel Vila - 21/05/2013
	 * @see
	 * @param adUserID
	 * @return
	 */
	public boolean validUser(int adUserID){

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		boolean hayRow = false;
		
		try{
			
			sql = " select coalesce(isactive,'Y') as isactive " +
				  " from uy_r_areauser " +
				  " where uy_r_ptoresolucion_id =? " +
				  " and ad_user_id =?"; 
			
			pstmt = DB.prepareStatement(sql.toString(), null);
			pstmt.setInt(1, this.get_ID());
			pstmt.setInt(2, adUserID);
			
			rs = pstmt.executeQuery();

			if (rs.next()) {
				if (rs.getString(1).equalsIgnoreCase("Y")){
					hayRow = true;
				}
			}

			
		} catch (Exception e) {

			throw new AdempiereException(e.getMessage());

		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		return hayRow;
	}

	/***
	 * Valida que un determinado usuario pueda solicitar ajustes para este punto de resolucion.
	 * OpenUp Ltda. Issue #281 
	 * @author Gabriel - 12/07/2013
	 * @see
	 * @param adUserID
	 * @return
	 */
	public boolean canRequest(int adUserID){

		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		boolean result = false;
		
		try{
			
			sql = " select coalesce(solicitaajuste,'N') as solicita " +
				  " from uy_r_areauser " +
				  " where uy_r_ptoresolucion_id =? " +
				  " and ad_user_id =?"; 
			
			pstmt = DB.prepareStatement(sql.toString(), null);
			pstmt.setInt(1, this.get_ID());
			pstmt.setInt(2, adUserID);
			
			rs = pstmt.executeQuery();

			if (rs.next()) {
				if (rs.getString(1).equalsIgnoreCase("Y")){
					result = true;
				}
			}

			
		} catch (Exception e) {

			throw new AdempiereException(e.getMessage());

		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		return result;
	}

	
	/***
	 * Retorna modelo segun value recibido.
	 * OpenUp Ltda. Issue #1173 
	 * @author Gabriel Vila - 12/09/2013
	 * @see
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static MRPtoResolucion forValue(Properties ctx, String value, String trxName){
		
		String whereClause = X_UY_R_PtoResolucion.COLUMNNAME_Value + "='" + value + "'";
		
		MRPtoResolucion model = new Query(ctx, I_UY_R_PtoResolucion.Table_Name, whereClause, trxName)
		.setClient_ID()
		.first();
		
		return model;
		
	}
}
