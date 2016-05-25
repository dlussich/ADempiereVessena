/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 29/05/2013
 */
package org.openup.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.Query;
import org.compiere.util.DB;

/**
 * org.openup.model - MRCanal
 * OpenUp Ltda. Issue #285 
 * Description: Modelo de canales de incidencias.
 * @author Gabriel Vila - 29/05/2013
 * @see
 */
public class MRCanal extends X_UY_R_Canal {

	private static final long serialVersionUID = -2752238419811483004L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_R_Canal_ID
	 * @param trxName
	 */
	public MRCanal(Properties ctx, int UY_R_Canal_ID, String trxName) {
		super(ctx, UY_R_Canal_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MRCanal(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	/***
	 * Valida que un usuario determinado pertenezca a este canal. 
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
				  " from uy_r_canaluser " +
				  " where uy_r_canal_id =? " +
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
	 * Obtiene canal de notificacion segun value recibido.
	 * OpenUp Ltda. Issue #285 
	 * @author Gabriel Vila - 05/06/2013
	 * @see
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static MRCanal forValue(Properties ctx, String value, String trxName){
		
		String whereClause = X_UY_R_Canal.COLUMNNAME_Value + "='" + value + "'";
		
		MRCanal model = new Query(ctx, I_UY_R_Canal.Table_Name, whereClause, trxName)
		.setClient_ID()
		.first();
		
		return model;
		
	}

	public static MRCanal forName(Properties ctx, String name, String trxName){
		
		String whereClause = X_UY_R_Canal.COLUMNNAME_Name + "='" + name + "'";
		
		MRCanal model = new Query(ctx, I_UY_R_Canal.Table_Name, whereClause, trxName)
		.setClient_ID()
		.first();
		
		return model;
		
	}
	
}
