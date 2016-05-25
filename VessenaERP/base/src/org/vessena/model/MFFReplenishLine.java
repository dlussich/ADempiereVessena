/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.Query;
import org.compiere.util.DB;

/**
 * @author Nicolas
 *
 */
public class MFFReplenishLine extends X_UY_FF_ReplenishLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7815000407086549950L;

	/**
	 * @param ctx
	 * @param UY_FF_ReplenishLine_ID
	 * @param trxName
	 */
	public MFFReplenishLine(Properties ctx, int UY_FF_ReplenishLine_ID,
			String trxName) {
		super(ctx, UY_FF_ReplenishLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MFFReplenishLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		if(newRecord){
			//asigno nro de linea solo si es nuevo registro
			if(this.getLine()==0){

				String sql = "SELECT COALESCE(MAX(line),0)+10 FROM UY_FF_ReplenishLine WHERE UY_FF_Replenish_ID = " + this.getUY_FF_Replenish_ID();
				int l = DB.getSQLValueEx(get_TrxName(), sql);
				this.setLine(l);		
			}		
		}

		return true;
	}	
	
	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {

		this.updateHeader();
				
		return true;
	}

	@Override
	protected boolean afterDelete(boolean success) {

		this.updateHeader();
		
		return true;
	}

	/**
	 * OpenUp. Nicolas Sarlabos. 30/12/2013. #1426.
	 * Metodo que devuelve una linea de reposicion para una determinada reposicion, ID y tabla.
	 * 
	 * */
	public static MFFReplenishLine forTableReplenishLine(Properties ctx, int docID, int tableID, int recordID, int replenishID,String trxName){

		String whereClause = X_UY_FF_ReplenishLine.COLUMNNAME_C_DocType_ID + "=" + docID + " AND " + X_UY_FF_ReplenishLine.COLUMNNAME_AD_Table_ID + "=" + tableID + " AND " + X_UY_FF_ReplenishLine.COLUMNNAME_Record_ID + "=" + recordID;
				
		if(replenishID > 0) whereClause += " AND " + X_UY_FF_ReplenishLine.COLUMNNAME_UY_FF_Replenish_ID + "=" + replenishID; 

		MFFReplenishLine line = new Query(ctx, I_UY_FF_ReplenishLine.Table_Name, whereClause, trxName).first();

		return line;
	}
	
	/**
	 * OpenUp. Nicolas Sarlabos. 28/01/2014. #1426.
	 * Metodo que actualiza los importes del cabezal.
	 * 
	 * */
	private void updateHeader(){
		
		String sql = "";
		
		//actualizo importe total de comprobantes
		sql = "UPDATE UY_FF_Replenish r" +
			  " SET amtacumulate = " +
		      " (select coalesce(sum(amount),0) from uy_ff_replenishline rl" +
			  " where r.uy_ff_replenish_id = rl.uy_ff_replenish_id)" +
			  " WHERE UY_FF_Replenish_ID = " + getUY_FF_Replenish_ID();
		
		DB.executeUpdateEx(sql, get_TrxName());
		
		//actualizo saldo actual
		sql = "UPDATE uy_ff_replenish" +
		      " SET actualamt = (amtoriginal - amtacumulate)" +
              " WHERE uy_ff_replenish_id = " + getUY_FF_Replenish_ID();
		
		DB.executeUpdateEx(sql, get_TrxName());

	}	

}
