/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Gabriel Vila - 17/06/2015
 */
package org.openup.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.MProduct;
import org.compiere.model.MSysConfig;
import org.compiere.model.Query;

/**
 * org.openup.model - MProductAttribute
 * OpenUp Ltda. Issue #4404 
 * Description: Modelo para asociacion de atributo de producto con un determinado producto.
 * @author Gabriel Vila - 17/06/2015
 * @see
 */
public class MProductAttribute extends X_M_ProductAttribute {

	private static final long serialVersionUID = -381402546537709874L;

	/**
	 * Constructor.
	 * @param ctx
	 * @param M_ProductAttribute_ID
	 * @param trxName
	 */
	public MProductAttribute(Properties ctx, int M_ProductAttribute_ID,
			String trxName) {
		super(ctx, M_ProductAttribute_ID, trxName);
	}

	/**
	 * Constructor.
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MProductAttribute(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	public static MProductAttribute forAttributeProd(Properties ctx, int prodAtrID, int prodID, String trxName){

		String whereClause = X_M_ProductAttribute.COLUMNNAME_UY_ProdAttribute_ID + "=" + prodAtrID + 
				" and " + X_M_ProductAttribute.COLUMNNAME_M_Product_ID + "=" + prodID;

		MProductAttribute model = new Query(ctx, I_M_ProductAttribute.Table_Name, whereClause, trxName)
		.first();

		return model;
	}
	
	
	// OpenUp. Ines Fernandez. 17/06/2015. Issue #4404
	// impacta una linea de update de producto en la tabla de interface de producto.
		@Override
		protected boolean afterSave (boolean newRecord, boolean success)
		{//public void toInterfaceRow(int prod_ID, String action, String UPC)
			if (!success)
				return success;

		//OpenUp SBouissa 06-10-2015 - Calculo hexa para el producto a partir de los cambios
			if (MSysConfig.getBooleanValue("UY_RETAIL_INTERFACE", false, this.getAD_Client_ID())) {
				MProduct prod = (MProduct) this.getM_Product();
				if(prod.isSold())//Se quita la condición activo del producto ()
					if(prod.updateVerssionNo(true)){
						// Obtengo (si existe) registro de interface para este producto que
						// aun no haya sido leído
						MRTInterfaceProd it = (MRTInterfaceProd) MRTInterfaceProd.forProduct(
								getCtx(), this.getM_Product_ID(), get_TrxName());
						
						if(it== null) it= new MRTInterfaceProd(getCtx(), 0, get_TrxName());
						it.setM_Product_ID(this.getM_Product_ID());
						it.insertInterface(newRecord);
					}
				
//				//OpenUp sevans 19/02/2016 #5319
//				//Se controla que el producto sea pesable
//
//				MProdAttribute attr13 = MProdAttribute.forValue(getCtx(), "attr_13", get_TrxName());
//				if(this.getUY_ProdAttribute_ID() == attr13.get_ID()){
//					MRTInterfaceScales scales = new MRTInterfaceScales(getCtx(), 0, get_TrxName());
//					if (this.isSelected()){						
//						scales.toInterfaceScalesRow(this.getM_Product_ID(), "0");							
//					}
//					else{						
//						scales.deleteInterfaceScalesRow(this.getM_Product_ID(), "0");													
//					}
//				}
			}
		return success;
			
		}	//	afterSave

}
