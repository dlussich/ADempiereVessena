/**
 * MStockStatus.java
 * 29/03/2011
 */
package org.openup.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * OpenUp.
 * MStockStatus
 * Descripcion :
 * @author Gabriel Vila
 * Fecha : 29/03/2011
 */
public class MStockStatus extends X_UY_StockStatus {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7836398863738909053L;

	/**
	 * Constructor
	 * @param ctx
	 * @param UY_StockStatus_ID
	 * @param trxName
	 */
	public MStockStatus(Properties ctx, int UY_StockStatus_ID, String trxName) {
		super(ctx, UY_StockStatus_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MStockStatus(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * OpenUp. Gabriel Vila. 30/05/2011. Obtiene y retorna ID de estado de stock reservado.
	 * @param trxName
	 * @return
	 */
	public static int getStatusReservedID(String trxName){
		String sql = "select uy_stockstatus_id from uy_stockstatus where lower(value)='reservado'";
		return DB.getSQLValue(trxName, sql);
	}
	
	
	/**
	 * OpenUp. Gabriel Vila. 01/06/2011. Obtiene y retorna ID de estado de stock pendiente.
	 * @param trxName
	 * @return
	 */
	public static int getStatusPendingID(String trxName){
		String sql = "select uy_stockstatus_id from uy_stockstatus where lower(value)='pendiente'";
		return DB.getSQLValue(trxName, sql);
	}
	
	/**
	 * OpenUp. Gabriel Vila. 01/06/2011. Obtiene y retorna ID de estado de stock fisico aprobado.
	 * @param trxName
	 * @return
	 */
	public static int getStatusApprovedID(String trxName){
		String sql = "select uy_stockstatus_id from uy_stockstatus where lower(value)='aprobado'";
		return DB.getSQLValue(trxName, sql);
	}
	
	
	/**
	 * OpenUp. Issue#889 Nicolas Garcia. 07/10/2011. Obtiene y retorna ID de estado de stock fisico cuarentena y bloqueado.
	 * @param trxName
	 * @return
	 */
	public static int getStatusQuarantineID(String trxName) {
		String sql = "select uy_stockstatus_id from uy_stockstatus where lower(value)='cuarentena'";
		return DB.getSQLValue(trxName, sql);
	}

	public static int getStatusBblockedID(String trxName) {
		String sql = "select uy_stockstatus_id from uy_stockstatus where lower(value)='bloqueado'";
		return DB.getSQLValue(trxName, sql);
	}

	/**
	 * OpenUp. Gabriel Vila. 01/06/2011. Obtiene y retorna ID de estado de stock entregado.
	 * @param trxName
	 * @return
	 */
	public static int getStatusDeliveredID(String trxName){
		String sql = "select uy_stockstatus_id from uy_stockstatus where lower(value)='entregado'";
		return DB.getSQLValue(trxName, sql);
	}
	
	/**
	 * OpenUp. Gabriel Vila. 08/06/2011. Issue #719. 
	 * Obtiene y retorna ID de estado de stock en transito.
	 * @param trxName
	 * @return
	 */
	public static int getStatusTransitID(String trxName){
		String sql = "select uy_stockstatus_id from uy_stockstatus where lower(value)='transito'";
		return DB.getSQLValue(trxName, sql);
	}
	
	/**
	 * OpenUp. Gabriel Vila. 08/06/2011. Issue #719. 
	 * Obtiene y retorna ID de estado de stock facturado.
	 * @param trxName
	 * @return
	 */
	public static int getStatusInvoicedID(String trxName){
		String sql = "select uy_stockstatus_id from uy_stockstatus where lower(value)='facturado'";
		return DB.getSQLValue(trxName, sql);
	}
	
	/**
	 * Obtiene y retorna un array con los estados de stock del tipo FISICO
	 * OpenUp Ltda. Issue #1010 
	 * @author Nicolas Sarlabos - 26/04/2012
	 * @see
	 * @param trxName
	 * @return
	 */
	public static MStockStatus[] getPhysicalStatus(String trxName){
		
		String sql = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		List<MStockStatus> list = new ArrayList<MStockStatus>();
		
		try {

	 		sql = " SELECT uy_stockstatus_id " +
	 	 		      " FROM uy_stockstatus s " + 
	 	 		      " INNER JOIN uy_stockstatustype t ON s.uy_stockstatustype_id=t.uy_stockstatustype_id " +
	 			      " WHERE t.isphysical='Y' ";
			
			pstmt = DB.prepareStatement (sql, trxName);
			rs = pstmt.executeQuery ();
			
			while (rs.next()){
				MStockStatus value = new MStockStatus(Env.getCtx(), rs.getInt(1), trxName);
				list.add(value);
			}
						
		}
		catch (Exception e)
		{
			throw new AdempiereException(e.getMessage());
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
		return list.toArray(new MStockStatus[list.size()]);
	}
	
	/***
	 * Obtiene y retorna estado de stock segun value recibido
	 * OpenUp Ltda. Issue #455 
	 * @author Nicolas Sarlabos - 05/03/2013
	 * @see
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static MStockStatus forValue(Properties ctx, String value, String trxName){
		
		String whereClause = X_UY_StockStatus.COLUMNNAME_Value + "='" + value + "'";
		
		MStockStatus status = new Query(ctx, I_UY_StockStatus.Table_Name, whereClause, trxName)
		.setClient_ID()
		.first();
				
		return status;
	}


}
