package org.openup.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;

public class MMoldeConciliaDet extends X_UY_Molde_ConciliaDet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6047250395950725379L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_Molde_ConciliaDet_ID
	 * @param trxName
	 */
	public MMoldeConciliaDet(Properties ctx, int UY_Molde_ConciliaDet_ID, String trxName) {
		super(ctx, UY_Molde_ConciliaDet_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MMoldeConciliaDet(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	/***
	 * Obtiene y retorna lineas segun condicion.
	 * OpenUp Ltda. Issue #741
	 * @author Nicolas Sarlabos - 24/04/2013
	 * @see
	 * @return
	 */
	public static MMoldeConciliaDet[] getLines(Properties ctx, String where,String orderby, String trxName){
		
		List <MMoldeConciliaDet> list = new ArrayList <MMoldeConciliaDet>();
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
				
		try{
			
			sql = " select uy_molde_conciliadet_id " +
				  " from uy_molde_conciliadet " + 
				  where + orderby;
			
			pstmt = DB.prepareStatement (sql,trxName);
			rs = pstmt.executeQuery ();
			
			while(rs.next()){
				MMoldeConciliaDet d = new MMoldeConciliaDet(ctx, rs.getInt("uy_molde_conciliadet_id"), trxName);
				list.add(d);
			}			
			
		}catch(Exception e) {
			throw new AdempiereException(e);

		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}	

		return list.toArray(new MMoldeConciliaDet[list.size()]);
	}

}
