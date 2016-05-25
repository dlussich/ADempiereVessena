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
public class MTRTireMoveLine extends X_UY_TR_TireMoveLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9100828206803943940L;

	/**
	 * @param ctx
	 * @param UY_TR_TireMoveLine_ID
	 * @param trxName
	 */
	public MTRTireMoveLine(Properties ctx, int UY_TR_TireMoveLine_ID,
			String trxName) {
		super(ctx, UY_TR_TireMoveLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MTRTireMoveLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	
	/***
	 * Obtengo modelo segun value recibido.
	 * OpenUp Ltda. Issue #1605
	 * @author Guillermo Brust - 28/11/2013
	 * @see
	 * @param ctx
	 * @param value
	 * @param trxName
	 * @return
	 */
	public static MTRTireMoveLine forPosition(Properties ctx, int position, int tireMoveID, String trxName){
		
		String whereClause = X_UY_TR_TireMoveLine.COLUMNNAME_LocatorValue + "=" + position + 
							" AND " + X_UY_TR_TireMoveLine.COLUMNNAME_UY_TR_TireMove_ID + " = " + tireMoveID;
		
		MTRTireMoveLine model = new Query(ctx, I_UY_TR_TireMoveLine.Table_Name, whereClause, trxName)
		.first();		
		
		return model;
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		if(newRecord && !this.isAuxiliar()) this.setUpdateQty(true);		
		
		if(this.isChanged()) this.setUpdateQty(false);		
		
		return true;
	}



}
