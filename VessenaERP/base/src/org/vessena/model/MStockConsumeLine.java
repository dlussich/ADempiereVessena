/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 11/07/2014
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.compiere.model.Query;

/**
 * org.openup.model - MStockConsumeLine
 * OpenUp Ltda. Issue #1405 
 * Description: Modelo para linea en la trazabilidad de consumos de stock.
 * @author Gabriel Vila - 11/07/2014
 * @see
 */
public class MStockConsumeLine extends X_UY_StockConsumeLine {

	private static final long serialVersionUID = 5828549575888792887L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param UY_StockConsumeLine_ID
	 * @param trxName
	 */
	public MStockConsumeLine(Properties ctx, int UY_StockConsumeLine_ID,
			String trxName) {
		super(ctx, UY_StockConsumeLine_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MStockConsumeLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/***
	 * Obtiene y retorna lineas de consumo para una determinada linea recibida.
	 * OpenUp Ltda. Issue #1405 
	 * @author Gabriel Vila - 25/07/2014
	 * @see
	 * @param ctx
	 * @param adTableID
	 * @param lineID
	 * @param trxName
	 * @return
	 */
	public static List<MStockConsumeLine> getLines(Properties ctx, int adTableID, int lineID, String trxName){
		
		String whereClause = "ad_table_id =" + adTableID + " and line_id =" + lineID;
		List<MStockConsumeLine> lines = new Query(ctx, I_UY_StockConsumeLine.Table_Name, whereClause, trxName).list();

		return lines;
		
	}

}
