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
public class MTTDelPointRetReasons extends X_UY_TT_DelPointRetReasons {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8634264093635065127L;

	/**
	 * @param ctx
	 * @param UY_TT_DelPointRetReasons_ID
	 * @param trxName
	 */
	public MTTDelPointRetReasons(Properties ctx,
			int UY_TT_DelPointRetReasons_ID, String trxName) {
		super(ctx, UY_TT_DelPointRetReasons_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTTDelPointRetReasons(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	
	/***
	 * Obtengo y devuelvo modelo de UY_TT_DelPointRetReasons a partir de un courierID y un motivo de devolucion de la tarjeta para este courier
	 * OpenUp Ltda. Issue #1186 
	 * @author Guillermo Brust - 11/09/2013
	 * @see
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static MTTDelPointRetReasons forCourierIDAndMotivo(Properties ctx, String trxName, int courierID, String motivoDevolucion){
		
		MTTDelPointRetReasons model = null;

		if(motivoDevolucion!=null){

			motivoDevolucion = motivoDevolucion.toLowerCase().trim();

			String whereClause = " lower(" + X_UY_TT_DelPointRetReasons.COLUMNNAME_Name + ")" + " ='" + motivoDevolucion + "'" +
					" AND " + X_UY_TT_DelPointRetReasons.COLUMNNAME_UY_DeliveryPoint_ID + " = " + courierID;

			model = new Query(ctx, I_UY_TT_DelPointRetReasons.Table_Name, whereClause, trxName)
			.first();		

		}
		
		return model;		
	}

}
