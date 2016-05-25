/**
 * MConfirmOrderLine.java
 * 13/09/2010
 */
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.openup.model.MProdTransf;

/**
 * OpenUp.
 * MConfirmOrderLine
 * Descripcion : Modelo para Lineas de Confirmacion de Ordenes de Produccion.
 * @author Gabriel Vila
 * Fecha : 13/09/2010
 */
public class MConfirmOrderline extends X_UY_ConfirmOrderline {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1366933738124287267L;

	/**
	 * Constructor
	 * @param ctx
	 * @param UY_ConfirmOrderline_ID
	 * @param trxName
	 */
	public MConfirmOrderline(Properties ctx, int UY_ConfirmOrderline_ID,
			String trxName) {
		super(ctx, UY_ConfirmOrderline_ID, trxName);
		
		if (UY_ConfirmOrderline_ID == 0){
			setProcessed(false);
		}
	}

	/**
	 * Constructor
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MConfirmOrderline(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * OpenUp. Mario Reyes. 02/03/2011. Issue #362
	 * Me aseguro que el consumo total quede bien registrado al momento de guardar la linea.
	 */	
	protected boolean beforeSave (boolean newRecord){

		// OpenUp. Nicolas Garcia. 18/08/2011. Issue #824.
		if (this.getC_UOM_ID() <= 0) {
			// Defensivo
			MProduct prod = new MProduct(getCtx(), this.getM_Product_ID(), null);
			this.setC_UOM_ID(prod.getC_UOM_ID());
		}

		//OpenUp. INes Fernandez. 21/08/2015. contempla caso prod. de transformacion. Issue #4125
		if(!MSysConfig.getBooleanValue("UY_IS_PROD_TRANSF", false, getAD_Client_ID())){
			
			// OpenUp. Nicolas Garcia. 15/08/2011. Issue #811.
			BigDecimal rechazado = this.getQtyReject();
			this.setUY_ConsumoTotal(this.getQtyRequired().add(this.getqtymanual().add(this.getQtyScrap()).add(rechazado)));
			// Fin Issue #811
			
			//OpenUp Nicolas Sarlabos #958 18/01/2012
			MProduct prod = new MProduct(getCtx(), this.getM_Product_ID(), null);

			int wHouseID = DB.getSQLValue(null, "SELECT m_warehouse_id FROM uy_productcategorywarehouse WHERE isdefault ='Y' AND m_product_category_id ="
						+ prod.getM_Product_Category_ID());

			if (wHouseID <= 0) {
				MProductCategory category = (MProductCategory) prod.getM_Product_Category();
				throw new AdempiereException("La categoria de producto \"" + category.getName() + "\"\n no tiene un almacen por defecto.");
			} else
				this.setM_Warehouse_ID(wHouseID);

			//fin OpenUp Nicolas Sarlabos #958 18/01/2012
			
			// Fin Issue #824
			
		} else {
			
			if(newRecord) this.setUY_ConsumoTotal(this.getQtyRequired());
			
			//OpenUp. Nicolas Sarlabos. 04/04/2016. #5720.
			MConfirmorderhdr hdr = (MConfirmorderhdr)this.getUY_Confirmorderhdr();
			MProdTransf order = hdr.getProdTransfOrder();
			MDocType doc = new MDocType(getCtx(),order.getC_DocTypeTarget_ID(),get_TrxName());
			
			if (doc.getValue().equalsIgnoreCase("ordencorte")){

				if(!newRecord){

					if(this.get_ValueAsString("numero")==null || this.get_ValueAsString("numero").equalsIgnoreCase(""))
						throw new AdempiereException("Debe ingresar el numero de bobina");

					String sql = "select uy_confirmorderbobbin_id" +
							" from uy_confirmorderbobbin" +
							" where uy_confirmorderhdr_id = " + hdr.get_ID() +
							" and trim(numero) = trim('" + this.get_ValueAsString("numero") + "')";

					int lineID = DB.getSQLValueEx(get_TrxName(), sql);

					if(lineID <= 0) throw new AdempiereException("El numero de trazabilidad ingresado no pertenece a ninguna bobina usada");	

				}
			}		
			//Fin OpenUp.
			
			/*if(!MSysConfig.getBooleanValue("UY_ALLOW_NEGATIVE_STOCK_PRODTRANSF", false, this.getAD_Client_ID())){ //si no se permite stock negativo en OP
				
				Timestamp today = TimeUtil.trunc(new Timestamp (System.currentTimeMillis()), TimeUtil.TRUNC_DAY);
					
				MProduct prod = (MProduct)this.getM_Product();
				MUOM uomProd = (MUOM)prod.getC_UOM();
				MWarehouse warehouse = (MWarehouse)this.getM_Warehouse();
				MLocator locator = (MLocator)warehouse.getDefaultLocator();
				BigDecimal qty = this.getUY_ConsumoTotal();
							
				//obtengo cantidad disponible en UM del producto
				BigDecimal stock = MStockTransaction.getQtyAvailable(warehouse.get_ID(), locator.get_ID(), this.getM_Product_ID(), 
						0, 0, today, this.get_TrxName());
									
				//si la UM ingresada no es igual a la UM de stock del producto
				if(this.getC_UOM_ID() != prod.getC_UOM_ID()){
					
					BigDecimal factor = MUOMConversion.getProductRateFrom(getCtx(), this.getM_Product_ID(), this.getC_UOM_ID());
					
					if(factor==null) throw new AdempiereException("No se obtuvo tasa de conversion");
					
					qty = this.getUY_ConsumoTotal().multiply(factor);
					
				}			
				
				if(stock.compareTo(qty)<0) throw new AdempiereException("No hay stock suficiente en el almacén '" + warehouse.getName() + "'" + 
						" para este producto. \n Cantidad necesaria: " + qty.setScale(2, RoundingMode.HALF_UP) + " " + uomProd.getUOMSymbol() + 
						" \n Cantidad disponible: " + stock.setScale(2, RoundingMode.HALF_UP) + " " + uomProd.getUOMSymbol());	
				
			}*/
			
		}		

		return true;
	}
	
	/**
	 * OpenUp. Nicolas Sarlabos. 03/11/2015.
	 * Devuelve el total de Kg de bobinas seccionadas para una orden de corte.
	 */	
	public static BigDecimal totalWeightProd (Properties ctx, int confHdrID, String trxName) {
		
		BigDecimal value = Env.ZERO;
		
		String sql = "select coalesce(sum(uy_numrollos * weight),0)" +
                     " from uy_confirmorderline" +
                     " where uy_confirmorderhdr_id = " + confHdrID;
		
		value = DB.getSQLValueBDEx(trxName, sql);		
		
		return value;
		
	}
	
}
