/**
 * 
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.Query;

/**
 * @author gbrust
 *
 */
public class MTTDeliveryPointStatus extends X_UY_TT_DeliveryPointStatus {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6707252850769436604L;

	/**
	 * @param ctx
	 * @param UY_TT_DeliveryPointStatus_ID
	 * @param trxName
	 */
	public MTTDeliveryPointStatus(Properties ctx,
			int UY_TT_DeliveryPointStatus_ID, String trxName) {
		super(ctx, UY_TT_DeliveryPointStatus_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTTDeliveryPointStatus(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	
	/***
	 * Obtengo y devuelvo modelo de Estados de tarjetas segun al estado de la tarjeta para el courier
	 * OpenUp Ltda. Issue #1186 
	 * @author Guillermo Brust - 13/08/2013
	 * @see
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static MTTDeliveryPointStatus getMTTDeliveryPointStatusForEstado(Properties ctx, String trxName, int courierID, String estadoCourier){

		MTTDeliveryPointStatus model = null;

		if(estadoCourier!=null){

			estadoCourier = estadoCourier.toLowerCase().trim();

			String whereClause = " lower(" + X_UY_TT_DeliveryPointStatus.COLUMNNAME_Name + ")" + " = lower('" + estadoCourier.trim() + "')" +
					" AND " + X_UY_TT_DeliveryPointStatus.COLUMNNAME_UY_DeliveryPoint_ID + " = " + courierID;

			model = new Query(ctx, I_UY_TT_DeliveryPointStatus.Table_Name, whereClause, trxName)
			.first();

		}

		return model;		
	}

}
