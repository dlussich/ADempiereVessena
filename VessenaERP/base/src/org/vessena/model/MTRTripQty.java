/**
 * 
 */
package org.openup.model;

import java.math.RoundingMode;
import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.Query;

/**
 * @author Nicolas
 *
 */
public class MTRTripQty extends X_UY_TR_TripQty {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6670700462138522969L;

	/**
	 * @param ctx
	 * @param UY_TR_TripQty_ID
	 * @param trxName
	 */
	public MTRTripQty(Properties ctx, int UY_TR_TripQty_ID, String trxName) {
		super(ctx, UY_TR_TripQty_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTRTripQty(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	/***
	 * Obtiene y retorna modelo para ID de linea de orden de transporte recibido.
	 * OpenUp Ltda. Issue #
	 * @author Nicolas Sarlabos - 13/04/2015
	 * @see
	 * @return
	 */
	public static MTRTripQty forOTLine(Properties ctx,int lineID,String trxName){

		String whereClause = X_UY_TR_TripQty.COLUMNNAME_UY_TR_TransOrderLine_ID + "=" + lineID;

		MTRTripQty model = new Query(ctx, I_UY_TR_TripQty.Table_Name, whereClause, trxName).first();

		return model;
	}
	
	/***
	 * Obtiene y retorna modelo para ID de OT y CRT recibidos.
	 * OpenUp Ltda. Issue #
	 * @author Nicolas Sarlabos - 13/04/2015
	 * @see
	 * @return
	 */
	public static MTRTripQty forOTCrt(Properties ctx,int orderID, int crtID,String trxName){

		String whereClause = X_UY_TR_TripQty.COLUMNNAME_UY_TR_TransOrder_ID + "=" + orderID + " and " + X_UY_TR_TripQty.COLUMNNAME_UY_TR_Crt_ID + "=" + crtID;

		MTRTripQty model = new Query(ctx, I_UY_TR_TripQty.Table_Name, whereClause, trxName).first();

		return model;
	}
	
	/***
	 * Obtiene y retorna modelo para ID de CRT y almacen recibidos.
	 * OpenUp Ltda. Issue #
	 * @author Nicolas Sarlabos - 14/04/2015
	 * @see
	 * @return
	 */
	public static MTRTripQty forWarehouseCrt(Properties ctx, int warehouseID, int crtID, String trxName){

		String whereClause = X_UY_TR_TripQty.COLUMNNAME_UY_TR_Crt_ID + "=" + crtID + " and " + X_UY_TR_TripQty.COLUMNNAME_M_Warehouse_ID + "=" + warehouseID;

		MTRTripQty model = new Query(ctx, I_UY_TR_TripQty.Table_Name, whereClause, trxName).first();

		return model;
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		MTRConfig param = MTRConfig.forClient(getCtx(), get_TrxName());
		
		if (param==null) throw new AdempiereException("No se obtuvieron parametros de transporte para la empresa actual");
		
		MTRConfigRound round = MTRConfigRound.forConfig(getCtx(), param.get_ID(), get_TrxName());
		
		if (round==null) throw new AdempiereException("No se obtuvieron parametros de precision decimal para la empresa actual");
		
		if(is_ValueChanged("Weight")){
			
			this.setWeight(this.getWeight().setScale(round.getWeight(), RoundingMode.HALF_UP));
			
		}
		if(is_ValueChanged("Weight2")){
			
			this.setWeight2(this.getWeight2().setScale(round.getWeight2(), RoundingMode.HALF_UP));
			
		}
		if(is_ValueChanged("Volume")){
			
			this.setVolume(this.getVolume().setScale(round.getVolume(), RoundingMode.HALF_UP));
			
		}
		if(is_ValueChanged("QtyPackage")){
			
			this.setQtyPackage(this.getQtyPackage().setScale(round.getQtyPackage(), RoundingMode.HALF_UP));
			
		}
		if(is_ValueChanged("ProductAmt")){
			
			this.setProductAmt(this.getProductAmt().setScale(round.getProductAmt(), RoundingMode.HALF_UP));
			
		}				
		
		return true;
	}

}
