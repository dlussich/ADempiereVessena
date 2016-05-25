/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay (LUY).
 * OpenUp Ltda. Hp - 09/07/2012
 */
 
package org.openup.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.GridTabWrapper;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MBankAccount;
import org.compiere.model.MLocator;
import org.compiere.model.MOrder;
import org.compiere.model.MPriceList;
import org.compiere.model.MPriceListVersion;
import org.compiere.model.MProductPrice;
import org.compiere.model.MWarehouse;
import org.compiere.model.X_C_Currency;
import org.compiere.model.X_M_Requisition;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * org.openup.model - CalloutPurchase
 * OpenUp Ltda. Issue #1031 
 * Description: Mejoras en circuito de compras. Callouts asociados a transacciones de compra.
 * @author Gabriel Vila - 09/07/2012
 * @see
 */
public class CalloutPurchase extends CalloutEngine {

	/**
	 * Constructor.
	 */
	public CalloutPurchase() {
	}


	/**
	 * Nuevo valor para solicitud de compra en una cotizacion a proveedor con solicitud.
	 * OpenUp Ltda. Issue #1031 
	 * @author Gabriel Vila - 09/07/2012
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String quoteRequisition(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
		
		if (value == null) return "";

		int rfqRequisitionID = ((Integer)value).intValue();
		if (rfqRequisitionID <= 0){
			mTab.setValue(X_UY_QuoteVendor.COLUMNNAME_C_BPartner_ID, null);
			return "";
		}

		MRFQRequisition req = new MRFQRequisition(Env.getCtx(), rfqRequisitionID, null);
		mTab.setValue(X_UY_QuoteVendor.COLUMNNAME_C_BPartner_ID, req.getC_BPartner_ID());
		
		return "";

	}
	
	/***
	 * Calcula total de linea de cotizacion de vendedor al cambiar el precio
	 * OpenUp Ltda. Issue #93 
	 * @author Gabriel Vila - 14/12/2012
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String changeQuoteLinePrice(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {
		
		if (value == null) return "";

		BigDecimal price = (BigDecimal)value;
		BigDecimal total = ((BigDecimal)mTab.getValue(X_UY_QuoteVendorLine.COLUMNNAME_QtyQuote)).multiply(price); 
		
		mTab.setValue(X_UY_QuoteVendorLine.COLUMNNAME_TotalAmt, total);
		
		return "";

	}
	
	/***
	 * Al cambiar la cuenta bancaria, despligo moneda de la misma.
	 * OpenUp Ltda. Issue #285 
	 * @author Gabriel Vila - 05/04/2013
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String changeBankAccount(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {

		if (value == null) return "";

		int cBankAccountID = ((Integer)value).intValue();
		if (cBankAccountID <= 0){
			mTab.setValue(X_C_Currency.COLUMNNAME_C_Currency_ID, null);
			return "";
		}

		MBankAccount ba = new MBankAccount(Env.getCtx(), cBankAccountID, null);
		mTab.setValue(X_C_Currency.COLUMNNAME_C_Currency_ID, ba.getC_Currency_ID());

		return "";
	}
	
	/***
	 * Al cambiar el sector de compras despliego datos asociados al mismo.
	 * OpenUp Ltda. Issue #1832 
	 * @author Gabriel Vila - 06/03/2014
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String changeSection(Properties ctx, int WindowNo, GridTab mTab,GridField mField, Object value) {

		if (value == null) return "";

		int poSectionID = ((Integer)value).intValue();
		if (poSectionID <= 0){
			mTab.setValue(X_M_Requisition.COLUMNNAME_POUser_ID, null);
			return "";
		}

		//MPOSection model = new MPOSection(Env.getCtx(), poSectionID, null);
		//mTab.setValue(X_M_Requisition.COLUMNNAME_POUser_ID, model.getAD_User_ID());

		return "";
	}
	
	
	/***
	 * Al cambiar el sector de compras en un consumo de stock, debo setear atributos dependientes de él.
	 * OpenUp Ltda. Issue #1405 
	 * @author Gabriel Vila - 23/07/2014
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String changeSectionConsume(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {

		if (value == null) return "";

		int poSectionID = ((Integer)value).intValue();
		if (poSectionID <= 0){
			mTab.setValue(X_UY_ProductConsume.COLUMNNAME_C_Activity_ID, null);
			mTab.setValue(X_UY_ProductConsume.COLUMNNAME_M_Warehouse_ID, null);
			mTab.setValue(X_UY_ProductConsume.COLUMNNAME_M_Locator_ID, null);
			return "";
		}

		MPOSection model = new MPOSection(Env.getCtx(), poSectionID, null);
		mTab.setValue(X_UY_ProductConsume.COLUMNNAME_C_Activity_ID, model.getC_Activity_ID_1());
		mTab.setValue(X_UY_ProductConsume.COLUMNNAME_M_Warehouse_ID, model.getM_Warehouse_ID());
		
		MWarehouse wh = (MWarehouse)model.getM_Warehouse();
		MLocator locator = MLocator.getDefault(wh);
		if (locator != null){
			if (locator.get_ID() > 0){
				mTab.setValue(X_UY_ProductConsume.COLUMNNAME_M_Locator_ID, locator.get_ID());	
			}
		}

		return "";
	}
	
	
	/***
	 * Al cambiar el sector de compras en un consumo de stock, debo setear atributos dependientes de él.
	 * OpenUp Ltda. Issue #1405 
	 * @author Gabriel Vila - 23/07/2014
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String changeProductConsume(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {

		if (value == null) return "";

		int id = ((Integer)value).intValue();
		if (id <= 0){
			mTab.setValue(X_UY_ProductConsumeLine.COLUMNNAME_QtyAvailable, Env.ZERO);
		}

		I_UY_ProductConsumeLine line = GridTabWrapper.create(mTab, I_UY_ProductConsumeLine.class);
		MProductConsume consume = (MProductConsume)line.getUY_ProductConsume();
		

		BigDecimal qtyAvailable = MStockTransaction.getQtyAvailable(consume.getM_Warehouse_ID(), line.getM_Locator_ID(), line.getM_Product_ID(), 0, 0, null, null);

		if (qtyAvailable != null){
			mTab.setValue(X_UY_ProductConsumeLine.COLUMNNAME_QtyAvailable, qtyAvailable);
		}
		
		return "";
	}
	

	/**UY_Discount_Rule - M_PriceList_ID
	 * A partir de la lista de precios DE COMPRA seleccionada, setea la versión vigente a la fecha de inicio de la cond de negocio
	 * OpenUp Ltda. #4527
	 * @author INes Fernandez - 16/07/2015
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value C_BPartner_ID
	 * @return
	 */
	public String setPriceListVersionVigente(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {

		if (isCalloutActive() || value == null)
			return "";

		int priceList_ID = Env.getContextAsInt(ctx, WindowNo, 0, "M_PriceList_ID");
		Timestamp startDate = Env.getContextAsDate(ctx, WindowNo, "StartDate");

		String sql = "SELECT MAX (m_pricelist_version_id) FROM m_pricelist_version v INNER JOIN m_pricelist pl "
				+ "ON v.m_pricelist_id = pl.m_pricelist_id "
				+ "WHERE pl.isactive = 'Y' "
				+ "AND v.isactive = 'Y' "
				+ "AND pl.m_pricelist_id = "
				+ priceList_ID
				+ " AND v.validFrom <= '" + startDate + "'";

		int M_PriceList_Version = DB.getSQLValueEx(null, sql);

		if (M_PriceList_Version > 0) {
			mTab.setValue("PriceList_Version_ID", M_PriceList_Version);
			return "";
		}
		else{
			throw new AdempiereException(
					"La lista de precios seleccionada no tiene versión vigente asociada");
		}
	}

	/***
	 * Al seleccionar un producto en la grilla de productos no encontrados en carga de lista de precios
	 * de compra, se debe cargar los datos de precio de venta y margen de este producto.
	 * OpenUp Ltda. Issue #
	 * @author gabriel - Nov 19, 2015
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String changeProdPriceLoadLine(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {

		if (value == null) return "";

		int id = ((Integer)value).intValue();
		if (id <= 0){
			mTab.setValue(X_UY_PriceLoadLine.COLUMNNAME_PriceSOList, null);
			mTab.setValue(X_UY_PriceLoadLine.COLUMNNAME_NewPrice, null);
			mTab.setValue(X_UY_PriceLoadLine.COLUMNNAME_Margin, null);
			return "";
		}

		I_UY_PriceLoadLine line = GridTabWrapper.create(mTab, I_UY_PriceLoadLine.class);
		
		// Solo para productos no encontrados en el base, al momento de cargar el excel
		if (!line.isSuccess()){
			MPriceLoad header = (MPriceLoad)line.getUY_PriceLoad();

			if (header != null){

				// Obtengo precio de venta de este producto, puede venir en cero
				MPriceListVersion version = new MPriceListVersion(ctx, header.getM_PriceList_Version_ID_2(), null);
				MProductPrice prodPrice = null;
				if ((version != null) && (version.get_ID() > 0)){
					prodPrice = MProductPrice.forVersionProduct(ctx, version.get_ID(), id, null);
				}
				if ((prodPrice != null) && (prodPrice.get_ID() > 0)){
					mTab.setValue(X_UY_PriceLoadLine.COLUMNNAME_PriceSOList, prodPrice.getPriceList());
					mTab.setValue(X_UY_PriceLoadLine.COLUMNNAME_NewPrice, prodPrice.getPriceList());
					BigDecimal margin = Env.ZERO;
					if ((line.getPriceList() != null) && (line.getPriceList().compareTo(Env.ZERO) > 0)){
						margin = Env.ONEHUNDRED.subtract((prodPrice.getPriceList().multiply(Env.ONEHUNDRED).setScale(2, RoundingMode.HALF_UP)).divide(line.getPriceList(), 2, RoundingMode.HALF_UP));
					}
					mTab.setValue(X_UY_PriceLoadLine.COLUMNNAME_Margin, margin);
				}
				else{
					mTab.setValue(X_UY_PriceLoadLine.COLUMNNAME_PriceSOList, null);
					mTab.setValue(X_UY_PriceLoadLine.COLUMNNAME_NewPrice, null);
					mTab.setValue(X_UY_PriceLoadLine.COLUMNNAME_Margin, null);
				}
			}
			
		}
		
		return "";
	}
	
	/**
	 * 	UY_PriceLoadLine && UY_PriceUpdateLine
	 *  E. Bentancor
	 *  #5408
	 */
	public String setNewPriceNMargins(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value){
		
		if (value == null) return "";
		
		
		if (mField.getColumnName().equalsIgnoreCase(X_UY_PriceLoadLine.COLUMNNAME_NewPrice)){
			if(mTab.getValue("NewPrice") != null){
				if(mTab.getValue("PricePO") != null){
					BigDecimal np = new BigDecimal(mTab.get_ValueAsString("NewPrice"));
					BigDecimal po = new BigDecimal(mTab.get_ValueAsString("PricePO"));
					BigDecimal margin = ((np.multiply(Env.ONEHUNDRED).setScale(2, RoundingMode.HALF_UP)).divide(po, 2, RoundingMode.HALF_UP)).subtract(Env.ONEHUNDRED);
					mTab.setValue("Margin2", margin);
				}
				
				if(mTab.getValue("PriceCostFinal") != null){
					BigDecimal np = new BigDecimal(mTab.get_ValueAsString("NewPrice"));
					BigDecimal pcf = new BigDecimal(mTab.get_ValueAsString("PriceCostFinal"));
					BigDecimal margin = ((np.multiply(Env.ONEHUNDRED).setScale(2, RoundingMode.HALF_UP)).divide(pcf, 2, RoundingMode.HALF_UP)).subtract(Env.ONEHUNDRED);
					mTab.setValue("Margin3", margin);
				}
				
				if(mTab.getValue("PriceInvoiced") != null){
					BigDecimal np = new BigDecimal(mTab.get_ValueAsString("NewPrice"));
					BigDecimal pInv = new BigDecimal(mTab.get_ValueAsString("PriceInvoiced"));
					BigDecimal margin = ((np.multiply(Env.ONEHUNDRED).setScale(2, RoundingMode.HALF_UP)).divide(pInv, 2, RoundingMode.HALF_UP)).subtract(Env.ONEHUNDRED);
					mTab.setValue("Margin4", margin);
				}
			}
		}else if(mField.getColumnName().equalsIgnoreCase("Margin2")){
			
			
			if(mTab.getValue("PricePO") != null){
				BigDecimal newPrice = Env.ZERO;
				BigDecimal po = new BigDecimal(mTab.get_ValueAsString("PricePO"));
				BigDecimal newMargin = new BigDecimal(mTab.get_ValueAsString("Margin2"));
				newPrice = (newMargin.add(Env.ONEHUNDRED)).multiply(po).setScale(2, RoundingMode.HALF_UP).divide(Env.ONEHUNDRED);
				mTab.setValue(X_UY_PriceLoadLine.COLUMNNAME_NewPrice, newPrice);
			}
			
			
			if(mTab.getValue("PriceCostFinal") != null){
				BigDecimal np = new BigDecimal(mTab.get_ValueAsString("NewPrice"));
				BigDecimal pcf = new BigDecimal(mTab.get_ValueAsString("PriceCostFinal"));
				BigDecimal margin = ((np.multiply(Env.ONEHUNDRED).setScale(2, RoundingMode.HALF_UP)).divide(pcf, 2, RoundingMode.HALF_UP)).subtract(Env.ONEHUNDRED);
				mTab.setValue("Margin3", margin);
			}
			
			if(mTab.getValue("PriceInvoiced") != null){
				BigDecimal np = new BigDecimal(mTab.get_ValueAsString("NewPrice"));
				BigDecimal pInv = new BigDecimal(mTab.get_ValueAsString("PriceCostFinal"));
				BigDecimal margin = ((np.multiply(Env.ONEHUNDRED).setScale(2, RoundingMode.HALF_UP)).divide(pInv, 2, RoundingMode.HALF_UP)).subtract(Env.ONEHUNDRED);
				mTab.setValue("Margin4", margin);
			
			}
		}else if(mField.getColumnName().equalsIgnoreCase("Margin3")){
			
			
			if(mTab.getValue("PriceCostFinal") != null){
				BigDecimal newPrice = Env.ZERO;
				BigDecimal pcf = new BigDecimal(mTab.get_ValueAsString("PriceCostFinal"));
				BigDecimal newMargin = new BigDecimal(mTab.get_ValueAsString("Margin3"));
				newPrice = (newMargin.add(Env.ONEHUNDRED)).multiply(pcf).setScale(2, RoundingMode.HALF_UP).divide(Env.ONEHUNDRED);
				mTab.setValue(X_UY_PriceLoadLine.COLUMNNAME_NewPrice, newPrice);
			}
			
			if(mTab.getValue("PricePO") != null){
				BigDecimal np = new BigDecimal(mTab.get_ValueAsString("NewPrice"));
				BigDecimal po = new BigDecimal(mTab.get_ValueAsString("PricePO"));
				BigDecimal margin = ((np.multiply(Env.ONEHUNDRED).setScale(2, RoundingMode.HALF_UP)).divide(po, 2, RoundingMode.HALF_UP)).subtract(Env.ONEHUNDRED);
				mTab.setValue("Margin2", margin);
			}
			
			if(mTab.getValue("PriceInvoiced") != null){
				BigDecimal np = new BigDecimal(mTab.get_ValueAsString("NewPrice"));
				BigDecimal pInv = new BigDecimal(mTab.get_ValueAsString("PriceCostFinal"));
				BigDecimal margin = ((np.multiply(Env.ONEHUNDRED).setScale(2, RoundingMode.HALF_UP)).divide(pInv, 2, RoundingMode.HALF_UP)).subtract(Env.ONEHUNDRED);
				mTab.setValue("Margin4", margin);
			
			}
		}else if(mField.getColumnName().equalsIgnoreCase("Margin4")){
			
			
			if(mTab.getValue("PriceInvoiced") != null){
				BigDecimal newPrice = Env.ZERO;
				BigDecimal pcf = new BigDecimal(mTab.get_ValueAsString("PriceInvoiced"));
				BigDecimal newMargin = new BigDecimal(mTab.get_ValueAsString("Margin4"));
				newPrice = (newMargin.add(Env.ONEHUNDRED)).multiply(pcf).setScale(2, RoundingMode.HALF_UP).divide(Env.ONEHUNDRED);
				mTab.setValue(X_UY_PriceLoadLine.COLUMNNAME_NewPrice, newPrice);
			}
			
			if(mTab.getValue("PricePO") != null){
				BigDecimal np = new BigDecimal(mTab.get_ValueAsString("NewPrice"));
				BigDecimal po = new BigDecimal(mTab.get_ValueAsString("PricePO"));
				BigDecimal margin = ((np.multiply(Env.ONEHUNDRED).setScale(2, RoundingMode.HALF_UP)).divide(po, 2, RoundingMode.HALF_UP)).subtract(Env.ONEHUNDRED);
				mTab.setValue("Margin2", margin);
			}
			
			if(mTab.getValue("PriceCostFinal") != null){
				BigDecimal np = new BigDecimal(mTab.get_ValueAsString("NewPrice"));
				BigDecimal pcf = new BigDecimal(mTab.get_ValueAsString("PriceCostFinal"));
				BigDecimal margin = ((np.multiply(Env.ONEHUNDRED).setScale(2, RoundingMode.HALF_UP)).divide(pcf, 2, RoundingMode.HALF_UP)).subtract(Env.ONEHUNDRED);
				mTab.setValue("Margin3", margin);
			
			}
		}
		
		
		return "";
	}
	
	
	/***
	 * En la carga de lista de precios de compra, al modificar un precio de venta o el margen de un producto
	 * se debe recalcular el campo asociado (nuevo precio de venta -> margen, margen -> nuevo precio de venta)
	 * OpenUp Ltda. Issue #
	 * @author gabriel - Nov 19, 2015
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String changeSOPriceLoadLine(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {

		if (isCalloutActive() || value == null) return "";

		int mPriceLoadID = Env.getContextAsInt(ctx, WindowNo, X_UY_PriceLoadLine.COLUMNNAME_UY_PriceLoad_ID);
		if (mPriceLoadID <= 0) return "";
		MPriceLoad header = new MPriceLoad(ctx, mPriceLoadID, null);
		MPriceList priceList = new MPriceList(ctx, header.getM_PriceList_ID_2(), null);
		
		if ((priceList == null) || (priceList.get_ID() <= 0)) return "";
		
		BigDecimal valueBD = (BigDecimal)value;
		
		BigDecimal POPriceList = (BigDecimal)mTab.getValue(X_UY_PriceLoadLine.COLUMNNAME_PriceList);
		
		
		// Segun el valor que estoy recibiendo
		if (mField.getColumnName().equalsIgnoreCase(X_UY_PriceLoadLine.COLUMNNAME_NewPrice)){

			BigDecimal margin = Env.ZERO;
			if ((POPriceList != null) && (POPriceList.compareTo(Env.ZERO) > 0)){
				margin = ((valueBD.multiply(Env.ONEHUNDRED).setScale(2, RoundingMode.HALF_UP)).divide(POPriceList, 2, RoundingMode.HALF_UP)).subtract(Env.ONEHUNDRED);
			}
			mTab.setValue(X_UY_PriceLoadLine.COLUMNNAME_Margin, margin);

		}
		else if (mField.getColumnName().equalsIgnoreCase(X_UY_PriceLoadLine.COLUMNNAME_Margin)){
			
			BigDecimal newPrice = Env.ZERO;
			if ((POPriceList != null) && (POPriceList.compareTo(Env.ZERO) > 0)){
				newPrice = POPriceList.add( (valueBD.multiply(POPriceList).setScale(2, RoundingMode.HALF_UP)).divide(Env.ONEHUNDRED, priceList.getPricePrecision(), RoundingMode.HALF_UP));
			}
			mTab.setValue(X_UY_PriceLoadLine.COLUMNNAME_NewPrice, newPrice);
			
		}
		
		return "";
	}

	/***
	 * Al indicar lista de precio de venta en la carga de precios de compra, debo cargar la version vigente.
	 * OpenUp Ltda. Issue #
	 * @author gabriel - Nov 19, 2015
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String setPriceListVersion(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {

		if (isCalloutActive() || value == null)
			return "";

		int id = (Integer)value;

		if (id <= 0) return "";
		
		Timestamp validFrom = (Timestamp)mTab.getValue(X_UY_PriceLoad.COLUMNNAME_ValidFrom);
		
		MPriceList pl = new MPriceList(ctx, id, null);
		MPriceListVersion versionVig = pl.getVersionVigente(validFrom);
		
		if (versionVig != null && versionVig.get_ID() > 0) { //encontro version vigente
			mTab.setValue(X_UY_PriceLoad.COLUMNNAME_M_PriceList_Version_ID_2, versionVig.get_ID());
		} 
		else{
			throw new AdempiereException("La lista de precios seleccionada no tiene ninguna versión vigente");
		}

		return "";
	}

	/***
	 * Setea fecha de orden segun id de orden recibido.
	 * OpenUp Ltda. Issue #
	 * @author gabriel - Feb 1, 2016
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String changeInternalDeliveryOrder(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {

		if (value == null) return "";

		int id = ((Integer)value).intValue();
		if (id <= 0){
			mTab.setValue(X_UY_RT_InternalDelivery.COLUMNNAME_DateOrdered, null);
		}

		MOrder order = new MOrder(ctx, id, null);
		if (order.get_ID() > 0){
			mTab.setValue(X_UY_RT_InternalDelivery.COLUMNNAME_DateOrdered, order.getDateOrdered());
		}
		
		return "";
	}

}
