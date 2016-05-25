package org.openup.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;

public class MMoldePresupAprobado extends X_UY_Molde_PresupAprobado{

	/**
	 * 
	 */
	private static final long serialVersionUID = -573965898631668127L;
	
	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_Molde_PresupAprobado_ID
	 * @param trxName
	 */
	public MMoldePresupAprobado(Properties ctx, int UY_Molde_PresupAprobado_ID, String trxName) {
		super(ctx, UY_Molde_PresupAprobado_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MMoldePresupAprobado(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	/***
	 * Obtiene y retorna lineas segun condicion.
	 * OpenUp Ltda. Issue #
	 * @author Nicolas Sarlabos - 24/05/2013
	 * @see
	 * @return
	 */
	public static MMoldePresupAprobado[] getLines(Properties ctx, String where,String orderby, String trxName){
		
		List <MMoldePresupAprobado> list = new ArrayList <MMoldePresupAprobado>();
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
				
		try{
			
			sql = " select uy_molde_presupaprobado_id " +
				  " from uy_molde_presupaprobado " + 
				  where + orderby;
			
			pstmt = DB.prepareStatement (sql,trxName);
			rs = pstmt.executeQuery ();
			
			while(rs.next()){
				MMoldePresupAprobado p = new MMoldePresupAprobado(ctx, rs.getInt("uy_molde_presupaprobado_id"), trxName);
				list.add(p);
			}			
			
		}catch(Exception e) {
			throw new AdempiereException(e);

		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}	

		return list.toArray(new MMoldePresupAprobado[list.size()]);
	}



}
