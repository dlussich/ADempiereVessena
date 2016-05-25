/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. gabriel - Dec 22, 2015
*/
package org.openup.model;

import java.util.Properties;

import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.util.DB;

/**
 * org.openup.model - CalloutStore
 * OpenUp Ltda. Issue #5150 
 * Description: Callouts para gestion de tiendas
 * @author gabriel - Dec 22, 2015
*/
public class CalloutStore extends CalloutEngine {

	/***
	 * Constructor.
	*/
	public CalloutStore() {
	}
	
	/***
	 * Al indicar UPC se debe obtener y desplegar producto.
	 * OpenUp Ltda. Issue #5150
	 * @author gabriel - Dec 22, 2015
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String setProductFromUPC(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {

 		if (value == null || value.equals("")){
 			return "";
 		}
		
		MProductUpc productUpc = MProductUpc.forUPC(ctx, value.toString().trim(), null);
	  	if ((productUpc != null) && (productUpc.get_ID() > 0)){
	  		mTab.setValue("M_Product_ID", productUpc.getM_Product_ID());	
	  	}

	  	return "";
	  	
	}

	/***
	 * Al seleccionar producto se obtiene y despliega ultimo codigo de barra asociado al mismo.
	 * OpenUp Ltda. Issue #5150
	 * @author gabriel - Dec 22, 2015
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String setUPCFromProduct(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {

    	if (value == null) return "";
    		 
   		// Get the shipment type ID from the value
    	int productID = (Integer) value;
    	if (productID <= 0) return "";

    	//MProduct product = new MProduct(ctx, productID, null);
    	//mTab.setValue("InternalCode", product.getValue());
			
		// Obtengo ultimo codigo de barra para este producto
		String sqlUPC = " select max(uy_productupc_id) as produpcid from uy_productupc where m_product_id =" + productID;
		
		int productUpcID = DB.getSQLValueEx(null, sqlUPC);
		
		if (productUpcID > 0){
			MProductUpc prodUPC = new MProductUpc(ctx, productUpcID, null);
			mTab.setValue("UPC", prodUPC.getUPC());
		}

   		return "";
   		
   	}

	
}
