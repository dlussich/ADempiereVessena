package org.openup.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;

public class MMoldePresupuestoProdFab extends X_UY_Molde_PresupuestoProdFab {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1220870921400387443L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_Molde_RComPresupuestoProdFabrica_ID
	 * @param trxName
	 */
	public MMoldePresupuestoProdFab(Properties ctx, int UY_Molde_RComPresupuestoProdFabrica_ID, String trxName) {
		super(ctx, UY_Molde_RComPresupuestoProdFabrica_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MMoldePresupuestoProdFab(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	/***
	 * Obtiene y retorna lineas segun condicion.
	 * OpenUp Ltda. Issue #715
	 * @author Nicolas Sarlabos - 18/05/2013
	 * @see
	 * @return
	 */
	public static MMoldePresupuestoProdFab[] getLines(Properties ctx, String where,String orderby, String trxName){
		
		List <MMoldePresupuestoProdFab> list = new ArrayList <MMoldePresupuestoProdFab>();
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
				
		try{
			
			sql = " select uy_molde_presupuestoprodfab_id " +
				  " from uy_molde_presupuestoprodfab " + 
				  where + orderby;
			
			pstmt = DB.prepareStatement (sql,trxName);
			rs = pstmt.executeQuery ();
			
			while(rs.next()){
				MMoldePresupuestoProdFab p = new MMoldePresupuestoProdFab(ctx, rs.getInt("uy_molde_presupuestoprodfab_id"), trxName);
				list.add(p);
			}			
			
		}catch(Exception e) {
			throw new AdempiereException(e);

		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}	

		return list.toArray(new MMoldePresupuestoProdFab[list.size()]);
	}
}
