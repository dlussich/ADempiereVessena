/**
 * 
 */
package org.openup.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MLocator;
import org.compiere.model.MProduct;
import org.compiere.model.MSysConfig;
import org.compiere.model.MUOM;
import org.compiere.model.MUOMConversion;
import org.compiere.model.MWarehouse;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

/**
 * @author Nicolas
 *
 */
public class MProdTransfInput extends X_UY_ProdTransfInput {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4855513939255713336L;

	/**
	 * @param ctx
	 * @param UY_ProdTransfInput_ID
	 * @param trxName
	 */
	public MProdTransfInput(Properties ctx, int UY_ProdTransfInput_ID, String trxName) {
		super(ctx, UY_ProdTransfInput_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MProdTransfInput(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		if(this.getQtyEntered().compareTo(Env.ZERO)<=0) throw new AdempiereException("La cantidad debe ser mayor a cero");		
		
		if(!MSysConfig.getBooleanValue("UY_ALLOW_NEGATIVE_STOCK_PRODTRANSF", false, this.getAD_Client_ID())){ //si no se permite stock negativo en OP
			
			Timestamp today = TimeUtil.trunc(new Timestamp (System.currentTimeMillis()), TimeUtil.TRUNC_DAY);
				
			MProduct prod = (MProduct)this.getM_Product();
			MUOM uomProd = (MUOM)prod.getC_UOM();
			MWarehouse warehouse = (MWarehouse)this.getM_Warehouse();
			MLocator locator = (MLocator)warehouse.getDefaultLocator();
			BigDecimal qty = this.getQtyEntered();
						
			//obtengo cantidad disponible en UM del producto
			BigDecimal stock = MStockTransaction.getQtyAvailable(warehouse.get_ID(), locator.get_ID(), this.getM_Product_ID(), 
					0, 0, today, this.get_TrxName());
			
			//si la UM ingresada no es igual a la UM de stock del producto
			if(this.getC_UOM_ID() != prod.getC_UOM_ID()){
				
				BigDecimal factor = MUOMConversion.getProductRateFrom(getCtx(), this.getM_Product_ID(), this.getC_UOM_ID());
				
				if(factor==null) throw new AdempiereException("No hay factor de conversion entre la UM indicada y la UM de stock del producto");
				
				qty = this.getQtyEntered().multiply(factor);
				
			}	
			
			this.setQtyOrdered(qty);
			
			if(stock.compareTo(qty)<0) throw new AdempiereException("No hay stock suficiente en el almacén '" + warehouse.getName() + "'" + 
					" para este producto. \n Cantidad necesaria: " + qty.setScale(2, RoundingMode.HALF_UP) + " " + uomProd.getUOMSymbol() + 
					" \n Cantidad disponible: " + stock.setScale(2, RoundingMode.HALF_UP) + " " + uomProd.getUOMSymbol());			
			
		}
		
		return true;
	}

}
