package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.util.DB;

public class MConciliated extends X_UY_Conciliated {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2520626432171825055L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_Conciliated_ID
	 * @param trxName
	 */
	public MConciliated(Properties ctx, int UY_Conciliated_ID, String trxName) {
		super(ctx, UY_Conciliated_ID, trxName);
	
	}
	
	@Override
	protected boolean afterDelete(boolean success) {
		
		//al eliminar una partida conciliada se elimina el ID de la misma de las lineas correspondientes en las tablas del cabezal
		if(success){
			
			String sql = "";
			
			sql = "UPDATE uy_conciliasystem SET uy_conciliated_id=null WHERE uy_conciliated_id=" + this.get_IDOld();
			DB.executeUpdateEx(sql, get_TrxName());
			
			sql = "UPDATE uy_conciliabank SET uy_conciliated_id=null WHERE uy_conciliated_id=" + this.get_IDOld();
			DB.executeUpdateEx(sql, get_TrxName());
									
		}
				
		return true;
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MConciliated(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}



}
