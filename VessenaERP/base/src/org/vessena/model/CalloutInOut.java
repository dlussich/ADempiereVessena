/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.openup.model;

import java.math.BigDecimal;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MBPartner;
import org.compiere.model.MInOut;
import org.compiere.model.MInOutLine;
import org.compiere.model.MProduct;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * 	Callout for shipment/receipts
 *	
 *  @author OpenUp FL
 */
public class CalloutInOut extends CalloutEngine
{
	/**
	 *  OpenUp FL 26/01/2011  
	 *  Update the shipment/receipts type
	 *  
	 *  @param ctx context
	 *  @param WindowNo current Window No
	 *  @param mTab Grid Tab
	 *  @param mField Grid Field
	 *  @param value New Value
	 *  @return null or error message
	 */	
	public String inOutType(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {

		if (value == null) return "";
		
		// Get the shipment type ID from the value
		Integer UY_InOutType_ID=(Integer) value;
		
		// If not set, get the shipmet type ID from the header
		if ((UY_InOutType_ID==null)||(UY_InOutType_ID.equals(0))) {

			// Get the shipmet type ID from the header
			Integer M_InOut_ID=(Integer) mTab.getValue(MInOutLine.COLUMNNAME_M_InOut_ID);
			if ((M_InOut_ID!=null)&&(!(M_InOut_ID.equals(0)))) {
				MInOut inout=new MInOut(ctx, M_InOut_ID, null);
				
				// Get the type id
				UY_InOutType_ID=(Integer) inout.get_Value(MInOutType.COLUMNNAME_UY_InOutType_ID);
				
				// If not null or 0 then set the value in the tab
				if ((UY_InOutType_ID!=null)&&(!(UY_InOutType_ID.equals(0)))) {
					mTab.setValue(MInOutType.COLUMNNAME_UY_InOutType_ID,UY_InOutType_ID);
				}
			}
		}
		
		
		if ((UY_InOutType_ID!=null)&&(!(UY_InOutType_ID.equals(0)))) {
			MInOutType inouttype=new MInOutType(ctx, UY_InOutType_ID, null);
			
			// Set the warehouse and the default locator ids
			mTab.setValue(MInOut.COLUMNNAME_M_Warehouse_ID,inouttype.getM_Warehouse_ID());
			mTab.setValue(MInOutLine.COLUMNNAME_M_Locator_ID,inouttype.getDefaultM_Locator_ID());
		}		
		else {
			return("No type shipment/recipt was set");
		}
		
		return("");
	} 
	
	/**
	 *  OpenUp FL 26/01/2011  
	 *  Update the default shipment/receipts type used in the header
	 *  
	 *  @param ctx context
	 *  @param WindowNo current Window No
	 *  @param mTab Grid Tab
	 *  @param mField Grid Field
	 *  @param value New Value
	 *  @return null or error message
	 */	
 	 public String inOutTypeDefault(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {

 		 if (value == null) return "";
 		 
		// Get the shipment type ID from the value
		Integer UY_InOutType_ID=(Integer) value;
		
		if ((UY_InOutType_ID!=null)&&(!(UY_InOutType_ID.equals(0)))) {
			MInOutType inouttype=new MInOutType(ctx, UY_InOutType_ID, null);
			
			// Set the warehouse and the default locator ids
			mTab.setValue(MInOut.COLUMNNAME_M_Warehouse_ID,inouttype.getM_Warehouse_ID());
			mTab.setValue(MInOutLine.COLUMNNAME_M_Locator_ID,inouttype.getDefaultM_Locator_ID());
		}		
		else {
			return("No type shipment/recipt was set");
		}
		
		return("");
	} 
	
 	/**
 	 *  OpenUp FL 26/01/2011  
 	 *  Update the buisnnes parter, just set or  reset the sales representative
 	 *  
 	 *  @param ctx context
 	 *  @param WindowNo current Window No
 	 *  @param mTab Grid Tab
 	 *  @param mField Grid Field
 	 *  @param value New Value
 	 *  @return null or error message
 	 */	
 	 public String bpartner(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {

		try {
			// Get the ID from the value
			Integer C_BPartner_ID=(Integer) value;
			
			// No change needed if no value. Defensive
			if ((C_BPartner_ID==null)||(C_BPartner_ID.equals(0))) {
				return("");
			}
			
			// Get the object
			MBPartner bpartner=new MBPartner(ctx, C_BPartner_ID, null);
			
			// No change needed if object is null, cannot get the bpartner. Defensive: this should not happend
			if (bpartner.get_ID() <= 0) {
				return("");
			}
			
			// Set or reset the sales representative id
			mTab.setValue(MInOut.COLUMNNAME_SalesRep_ID, bpartner.getSalesRep_ID());
			
			return("");

		}
		catch (Exception e) {
			log.log(Level.SEVERE, "No fue posible traer el vendedor de cliente", e); // TODO: define the message in the dictionary
			return (e.getMessage());
		}
		
	}
 	 
 	/**
  	 *  OpenUp Emiliano Bentancor 10/11/2015  
  	 *  Set Product ID when the UPC is filled
  	 *  
  	 *  @param ctx context
  	 *  @param WindowNo current Window No
  	 *  @param mTab Grid Tab
  	 *  @param mField Grid Field
  	 *  @param value New Value
  	 *  @return null or error message
  	 */	
   	 public String setProdID(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {

   		 if (value == null || value.equals("")) return "";
  		
  		MProductUpc productUpc = MProductUpc.forUPC(ctx, value.toString(), null);
  		if(null!=productUpc){
  			Integer UY_Product_ID = productUpc.getM_Product_ID();
  	  		if(UY_Product_ID != 0){
  	  			mTab.setValue("M_Product_ID", UY_Product_ID);
  	  		}
  		}else{
  			mTab.setValue("UPC", "");
  		}
  		
  		return("");
  	} 
   	 
   	/**
   	 *  OpenUp Emiliano Bentancor 10/11/2015  
   	 *  Set InternalCode when the Product ID is filled
   	 *  
   	 *  @param ctx context
   	 *  @param WindowNo current Window No
   	 *  @param mTab Grid Tab
   	 *  @param mField Grid Field
   	 *  @param value New Value
   	 *  @return null or error message
   	 */	
     public String setProdInternalCode(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {

    	if (value == null) return "";
    		 
   		// Get the shipment type ID from the value
    	Integer UY_Product_ID=(Integer) value;
  		
   		if ((UY_Product_ID!=null)&&(!(UY_Product_ID.equals(0)))) {
   			MProduct product = MProduct.get(ctx, UY_Product_ID);
   			if(null!=product){
   				mTab.setValue("InternalCode", product.getValue());
   				mTab.setValue("M_Locator_ID",Env.getContextAsInt(Env.getCtx(), "M_Locator_ID"));
   				mTab.setValue("C_UOM_ID", product.getC_UOM_ID());
   				
				// Obtengo ultimo codigo de barra para este producto
				String sqlUPC = " select max(uy_productupc_id) from uy_productupc where m_product_id =" + UY_Product_ID;
				int productUpcID = DB.getSQLValueEx(null, sqlUPC);
				if (productUpcID > 0){
					MProductUpc prodUPC = new MProductUpc(ctx, productUpcID, null);
					mTab.setValue("UPC", prodUPC.getUPC());
				}
   			}
   			
   		}   		
   		return("");
   	}
     
     /**
    	 *  OpenUp Emiliano Bentancor 10/11/2015  
    	 *  Set InternalCode when the Product ID is filled for OC
    	 *  
    	 *  @param ctx context
    	 *  @param WindowNo current Window No
    	 *  @param mTab Grid Tab
    	 *  @param mField Grid Field
    	 *  @param value New Value
    	 *  @return null or error message
    	 */	////////BORRAR -> Se movio a CalloutOrder
      /*public String setUPCnProdInternalCode(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {

     	if (value == null) return "";
     		 
    		// Get the shipment type ID from the value
     	Integer UY_Product_ID=(Integer) value;
   		
    		if ((UY_Product_ID!=null)&&(!(UY_Product_ID.equals(0)))) {
    			MProduct product = MProduct.get(ctx, UY_Product_ID);
    			if(null!=product){
    				mTab.setValue("InternalCode", product.getValue());
    				
 				// Obtengo ultimo codigo de barra para este producto
 				String sqlUPC = " select max(uy_productupc_id) from uy_productupc where m_product_id =" + UY_Product_ID;
 				int productUpcID = DB.getSQLValueEx(null, sqlUPC);
 				if (productUpcID > 0){
 					MProductUpc prodUPC = new MProductUpc(ctx, productUpcID, null);
 					mTab.setValue("UPC", prodUPC.getUPC());
 					}
    			}
    		}
    		return("");
    	}*/
      
      /**
    	 *  OpenUp Emiliano Bentancor 10/11/2015  
    	 *  Set Product ID when the UPC is filled for OC
    	 *  
    	 *  @param ctx context
    	 *  @param WindowNo current Window No
    	 *  @param mTab Grid Tab
    	 *  @param mField Grid Field
    	 *  @param value New Value
    	 *  @return null or error message
    	 *  Borrar
    	 */	
	      public String setProdIDforOC(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {

     		if (value == null || value.equals("") || mTab.getValue("M_Product_ID") != null) return "";
    		
     		if((Integer)mTab.getValue("M_PriceList_Version_ID") == null){
     			throw new AdempiereException("Por favor seleccione una lista de precios!");
     		}
			int plv_ID = (Integer)mTab.getValue("M_PriceList_Version_ID");
			if (plv_ID > 0){ 
				
    			String sql1 = "SELECT pp.m_product_id FROM M_ProductPrice pp JOIN UY_ProductUPC pu"
									+ " ON pp.m_product_id = pu.m_product_id"
									+ " WHERE pp.m_pricelist_version_id = " + plv_ID 
									+ " AND pu.UPC = '"+ value +"'";
    			
    			int UY_Product_ID = DB.getSQLValueEx(null, sql1);
    			
    			if(UY_Product_ID <= 0) throw new AdempiereException("No se encontró el precio vigente del producto seleccionado");
    			
    			//E. Bentancor #5408
    			int C_BPartner_ID = Env.getContextAsInt(ctx, WindowNo, WindowNo, "C_BPartner_ID");
     			String sqlProvCod = " select vendorProductNo from C_BPartner_Product"+
     					 			" where c_bpartner_id = "+ C_BPartner_ID + " and m_product_id = " + UY_Product_ID;
    			int vendorProductNo = DB.getSQLValueEx(null, sqlProvCod);
    			mTab.setValue("vendorProductNo", String.valueOf(vendorProductNo));
    			// Fin #5408
    			
    			mTab.setValue("M_Product_ID", UY_Product_ID);
    			
    			
    		}else{
    			mTab.setValue("UPC", "");
    		}
    		
    		return("");
    	} 
      
      
     	/**
     	 *  OpenUp Emiliano Bentancor 09/03/2016  
     	 *  Set Product ID when the Prov. Id is filled for OC
     	 *  #5408
     	 *  @param ctx context
     	 *  @param WindowNo current Window No
     	 *  @param mTab Grid Tab
     	 *  @param mField Grid Field
     	 *  @param value New Value
     	 *  @return null or error message
     	 */	/// BORRAR -> Se movio a CalloutOrder
      	 /*public String setProdIDforProvId(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {

      		if (value == null || value.equals("") || mTab.getValue("M_Product_ID") != null) return "";
      		

     		if((Integer)mTab.getValue("M_PriceList_Version_ID") == null){
     			throw new AdempiereException("Por favor seleccione una lista de precios!");
     		}
      		
    		int provId = Integer.valueOf((String)mTab.getValue("vendorProductNo"));
 			if (provId > 0){ 
 				
     			String sql1 = "select max(m_product_id) from C_BPartner_Product where vendorProductNo = '"+ provId +"'";
     			
     			int UY_Product_ID = DB.getSQLValueEx(null, sql1);
     			
     			if(UY_Product_ID <= 0) throw new AdempiereException("No se encontró ningun producto para el codigo insertado");
     			
     			mTab.setValue("M_Product_ID", UY_Product_ID);
     			
     			
     		}else{
     			mTab.setValue("UPC", "");
     		}
     		
     		return("");
     	} */
      	 
      	 
      	/**
      	 *  OpenUp Emiliano Bentancor 10/03/2016  
      	 *  Set Product ID when the Prov. Id is filled for OC
      	 *  
      	 *  @param ctx context
      	 *  @param WindowNo current Window No
      	 *  @param mTab Grid Tab
      	 *  @param mField Grid Field
      	 *  @param value New Value
      	 *  @return null or error message
      	 */	
       	 public String setProdIDforInvNoOC(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {

       		if (value == null || value.equals("") || mTab.getValue("M_Product_ID") != null) return "";
       		
     		int provId = Integer.valueOf((String)mTab.getValue("vendorProductNo"));
  			if (provId > 0){ 
  				
      			String sql1 = "select max(m_product_id) from C_BPartner_Product where vendorProductNo = '"+ provId +"'";
      			
      			int UY_Product_ID = DB.getSQLValueEx(null, sql1);
      			
      			if(UY_Product_ID <= 0) throw new AdempiereException("No se encontró ningun producto para el codigo insertado");
      			
      			mTab.setValue("M_Product_ID", UY_Product_ID);
      			
      			
      		}else{
      			mTab.setValue("UPC", "");
      		}
      		
      		return("");
      	} 

}	//	CalloutAllocation
