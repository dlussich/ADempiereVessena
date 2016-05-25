package org.openup.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import java.util.logging.Level;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.Env;

public class MFduCargo extends X_UY_Fdu_Cargo {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7218368609743946496L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_Fdu_Cargo_ID
	 * @param trxName
	 */
	public MFduCargo(Properties ctx, int UY_Fdu_Cargo_ID, String trxName) {
		super(ctx, UY_Fdu_Cargo_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MFduCargo(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		
		if(newRecord){
			
			ResultSet rs = null;
			PreparedStatement pstmt = null;
			
			String sql = "select p.uy_fdu_productos_id as producto, a.uy_fdu_afinidad_id as afinidad" +
						 " from uy_fdu_afinidad a" +
						 " inner join uy_fdu_productos p on a.uy_fdu_productos_id = p.uy_fdu_productos_id" +
						 " where a.uy_fdufile_id = 1000008 and p.uy_fdufile_id = 1000008" +
						 " order by p.value, a.value";
			
			try {
				
				pstmt = DB.prepareStatement (sql, null);
				rs = pstmt.executeQuery ();
				
				while(rs.next()) {
					
					MFduCargoImporte carImp = new MFduCargoImporte (getCtx(),0,get_TrxName());
					
					carImp.setUY_Fdu_Cargo_ID(this.get_ID());
					carImp.setUY_Fdu_Productos_ID(rs.getInt("producto"));
					carImp.setUY_Fdu_Afinidad_ID(rs.getInt("afinidad"));
					carImp.setAmount(Env.ZERO);
					carImp.saveEx();	
				}				
				
			} catch (Exception e){
				log.log(Level.SEVERE, sql, e);
			}
			finally	{
				DB.close(rs, pstmt);
				rs = null; pstmt = null;
			}		
			
		}
		
		return true;
	}
	
	/***
	 * OpenUp. Guillermo Brust. 04/11/2013. ISSUE#
	 * Método que devuelve una instancia de este modelo, a partir del value pasado por parametro y con la mayor validez menor a la fecha pasada por parametro.
	 * 
	 * **/
	public static MFduCargo forCodigoIDAndMaxValidate(Properties ctx, int codigoID, Timestamp fecha, String trxName){
		
		String whereClause = X_UY_Fdu_Cargo.COLUMNNAME_UY_FduCod_ID + " = " + codigoID +
						     " AND " + X_UY_Fdu_Cargo.COLUMNNAME_validity + " < '" + fecha + "'";
		
		MFduCargo model = new Query(ctx, I_UY_Fdu_Cargo.Table_Name, whereClause, trxName).setOrderBy(X_UY_Fdu_Cargo.COLUMNNAME_validity + " desc")
		.first();
		
		return model;
		
	}
	
	

}
