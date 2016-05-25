/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 12/11/2014
 */
package org.openup.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MDocType;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * org.openup.model - CalloutStock
 * OpenUp Ltda. Issue #2699 
 * Description: Callouts para gestion de stock
 * @author Gabriel Vila - 12/11/2014
 * @see
 */
public class CalloutStock extends CalloutEngine {

	/**
	 * Constructor.
	 */
	public CalloutStock() {
	
	}

	
	/***
	 * Obtiene stock disponible al indicar producto, almacen y ubicacion. Se considera stock aprobado.
	 * OpenUp Ltda. Issue #2699 
	 * @author Gabriel Vila - 12/11/2014
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String setStkAvailableByProdWarLoc(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
		
		if (value == null) return "";
		
		int id = ((Integer)value).intValue();
		
		if (id == 0) return "";		
		
		int docID = Env.getContextAsInt (ctx, WindowNo, 0, "C_DocType_ID");
		
		if(docID <= 0) return "";
		
		MDocType doc = new MDocType(ctx,docID,null); //obtengo tipo de documento

		// Producto
		int mProductID = 0;
		Integer product = (Integer)mTab.getValue("M_Product_ID");
		if (product != null) mProductID = product.intValue();
		if (mProductID == 0) return "";
		
		// Almacen
		int mWarehouseID = 0;
		Integer warehouse = (Integer)mTab.getValue("M_Warehouse_ID");
		if (warehouse != null) mWarehouseID = warehouse.intValue();
		if (mWarehouseID == 0) return "";
		
		// Ubicacion
		int mLocatorID = 0;
		Integer locator = (Integer)mTab.getValue("M_Locator_ID");
		if (locator != null) mLocatorID = locator.intValue();
		if (mLocatorID == 0) return "";

		// Estado
		int uyStockStatus = 0;
		Integer StockStatus = (Integer)mTab.getValue("UY_StockStatus_ID");
		if (StockStatus != null) uyStockStatus = StockStatus.intValue();
		if (uyStockStatus == 0)	return "";		
		
		Timestamp movementDate = null;
		Integer header = null;
		
		// Para fecha necesito Header
		if(doc.getValue().equalsIgnoreCase("prodconsume")){
			
			header = (Integer)mTab.getValue(X_UY_ProductConsume.COLUMNNAME_UY_ProductConsume_ID);
			
			if (header != null){
				MProductConsume model = new MProductConsume(ctx, header.intValue(), null);
				movementDate = model.getMovementDate();
			} else return "";		
			
		} else if (doc.getValue().equalsIgnoreCase("serviceorder")){
			
			/*header = (Integer)mTab.getValue(X_UY_TR_ServiceOrder.COLUMNNAME_UY_TR_ServiceOrder_ID);
			
			if (header != null){
				MTRServiceOrder model = new MTRServiceOrder(ctx, header.intValue(), null);
				movementDate = model.getDateTrx();
			}*/
			
			movementDate = new Timestamp(System.currentTimeMillis());		
			
		}		
		
		if (movementDate == null) return "";
		
		ResultSet rs = null;
		PreparedStatement pstmt = null;		

		try {		

			String sql = " select stk_available(?, ?, ?, 0, 0, ?) "; 

			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, null);
			pstmt.setInt(1, mProductID);
			pstmt.setInt(2, mWarehouseID);
			pstmt.setInt(3, mLocatorID);
			pstmt.setTimestamp(4, movementDate);
			
			rs = pstmt.executeQuery ();				
		
			if(rs.next()){
				mTab.setValue("QtyAvailable", rs.getBigDecimal(1));
			}
			
		} catch (Exception e) {
			DB.close(rs, pstmt);	
			throw new AdempiereException(e);
		}
		finally{
			DB.close(rs, pstmt);
		}
		
		return "";
	}

	

}
