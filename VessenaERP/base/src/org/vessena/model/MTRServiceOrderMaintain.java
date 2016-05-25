/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;

/**
 * @author Nicolas
 *
 */
public class MTRServiceOrderMaintain extends X_UY_TR_ServiceOrderMaintain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4299514138726007696L;

	/**
	 * @param ctx
	 * @param UY_TR_ServiceOrderMaintain_ID
	 * @param trxName
	 */
	public MTRServiceOrderMaintain(Properties ctx,
			int UY_TR_ServiceOrderMaintain_ID, String trxName) {
		super(ctx, UY_TR_ServiceOrderMaintain_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTRServiceOrderMaintain(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		String sql = "";
		
		MTRServiceOrder hdr = new MTRServiceOrder(getCtx(),this.getUY_TR_ServiceOrder_ID(),get_TrxName()); //obtengo cabezal de orden
		
		//se controla que la tarea no se repita en esta orden de servicio	
		if (newRecord || (!newRecord && is_ValueChanged("UY_TR_Maintain_ID"))){
			sql = "SELECT count(uy_tr_maintain_id)" +
			      " FROM uy_tr_serviceordermaintain" +
				  "	WHERE uy_tr_maintain_id=" + this.getUY_TR_Maintain_ID() +
				  " AND uy_tr_serviceorder_id= " + this.getUY_TR_ServiceOrder_ID() +
				  " AND ad_client_id = " + this.getAD_Client_ID() +
				  " AND ad_org_id = " + this.getAD_Org_ID();
			
			int res = DB.getSQLValueEx(get_TrxName(), sql);

			if (res > 0) throw new AdempiereException("Ya existe la tarea en esta orden de servicio");				
		}
		
		if(this.getUY_TR_TruckMaintain_ID() <= 0){
			
			sql = "select uy_tr_truckmaintain_id" +
			      " from uy_tr_truckmaintain" +
				  " where uy_tr_maintain_id = " + this.getUY_TR_Maintain_ID() +
				  " and uy_tr_truck_id = " + hdr.getUY_TR_Truck_ID() +
				  " and ad_client_id = " + this.getAD_Client_ID() +
				  " and ad_org_id = " + this.getAD_Org_ID();
			
			int truckMantID = DB.getSQLValueEx(get_TrxName(), sql);
			
			if(truckMantID > 0) this.setUY_TR_TruckMaintain_ID(truckMantID);
						
		}		
		
		return true;
	}

}
