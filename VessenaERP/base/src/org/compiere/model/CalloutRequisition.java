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
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.model.GridTabWrapper;
import org.compiere.util.Env;
import org.openup.model.MStockTransaction;

/**
 *	Requisition Callouts
 *	
 *  @author Jorg Janke
 *  @version $Id: CalloutRequisition.java,v 1.3 2006/07/30 00:51:05 jjanke Exp $
 *  @author Michael McKay (mjmckay)
 *          <li> BF3468429 Show attribute set instance field on the requisition line
 */
public class CalloutRequisition extends CalloutEngine
{
	/**
	 *	Requisition Line - Product.
	 *		- PriceStd
	 *  @param ctx context
	 *  @param WindowNo current Window No
	 *  @param mTab Grid Tab
	 *  @param mField Grid Field
	 *  @param value New Value
	 *  @return null or error message
	 */
	public String product (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		Integer M_Product_ID = (Integer)value;
		if (M_Product_ID == null || M_Product_ID.intValue() == 0)
			return "";

		final I_M_Requisition req = GridTabWrapper.create(mTab, I_M_Requisition.class);
		final I_M_RequisitionLine line = GridTabWrapper.create(mTab, I_M_RequisitionLine.class);
		setPrice(ctx, WindowNo, req, line);
		
		MProduct product = MProduct.get(ctx, M_Product_ID);
		line.setC_UOM_ID(product.getC_UOM_ID());
		line.setM_Product_Category_ID(product.getM_Product_Category_ID());
		line.setM_AttributeSetInstance_ID(product.getM_AttributeSetInstance_ID());
		
		// Openup. Gabriel Vila. 10/03/2014. Issue #1832.
		// Obtengo y seteo stock disponible para este producto en el almacen del cabezal		
		BigDecimal qtyAvailable = MStockTransaction.getQtyAvailable(req.getM_Warehouse_ID(), 0, line.getM_Product_ID(), 0, 0, null, null);
		if (qtyAvailable == null) qtyAvailable = Env.ZERO;
		line.setQtyAvailable(qtyAvailable);
		// Fin OpenUp. Issue #1832.
		

		return "";
	}	//	product

	/**
	 * Requisition line - Qty
	 * 	- Price, LineNetAmt
	 *  @param ctx context
	 *  @param WindowNo current Window No
	 *  @param mTab Grid Tab
	 *  @param mField Grid Field
	 *  @param value New Value
	 *  @return null or error message
	 */
	public String amt (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		if (isCalloutActive() || value == null)
			return "";
		
		final I_M_Requisition req = GridTabWrapper.create(mTab, I_M_Requisition.class);
		final I_M_RequisitionLine line = GridTabWrapper.create(mTab, I_M_RequisitionLine.class);
		//	Qty changed - recalc price
		if (mField.getColumnName().equals(I_M_RequisitionLine.COLUMNNAME_Qty) 
			&& "Y".equals(Env.getContext(ctx, WindowNo, "DiscountSchema")))
		{
			setPrice(ctx, WindowNo, req, line);
		}

		int StdPrecision = Env.getContextAsInt(ctx, WindowNo, "StdPrecision");
		BigDecimal Qty = line.getQty();
		BigDecimal PriceActual = line.getPriceActual();
		log.fine("amt - Qty=" + Qty + ", Price=" + PriceActual + ", Precision=" + StdPrecision);

		//	Multiply
		BigDecimal LineNetAmt = Qty.multiply(PriceActual);
		if (LineNetAmt.scale() > StdPrecision)
			LineNetAmt = LineNetAmt.setScale(StdPrecision, BigDecimal.ROUND_HALF_UP);
		line.setLineNetAmt(LineNetAmt);
		log.info("amt - LineNetAmt=" + LineNetAmt);
		//
		
		return "";
	}	//	amt

	private void setPrice(Properties ctx, int WindowNo, I_M_Requisition req, I_M_RequisitionLine line)
	{
		int C_BPartner_ID = line.getC_BPartner_ID();
		BigDecimal Qty = line.getQty();
		boolean isSOTrx = false;
		MProductPricing pp = new MProductPricing (line.getM_Product_ID(), C_BPartner_ID, Qty, isSOTrx);
		//
		int M_PriceList_ID = req.getM_PriceList_ID();
		pp.setM_PriceList_ID(M_PriceList_ID);
		int M_PriceList_Version_ID = Env.getContextAsInt(ctx, WindowNo, "M_PriceList_Version_ID");
		pp.setM_PriceList_Version_ID(M_PriceList_Version_ID);
		Timestamp orderDate = req.getDateRequired();
		pp.setPriceDate(orderDate);
		//
		line.setPriceActual(pp.getPriceStd());
		Env.setContext(ctx, WindowNo, "EnforcePriceLimit", pp.isEnforcePriceLimit() ? "Y" : "N");	//	not used
		Env.setContext(ctx, WindowNo, "DiscountSchema", pp.isDiscountSchema() ? "Y" : "N");
	}
	
	
	/***
	 * Cambio en lista de precios actualiza moneda, precio y tasa de cambio.
	 * OpenUp Ltda. Issue #88
	 * @author Gabriel Vila - 30/10/2012
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String changePriceList (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value){

		if (value == null) return "";
		
		int mPriceListID = (Integer)value;
		if (mPriceListID == 0) return "";

		MPriceList priceList = new MPriceList(ctx, mPriceListID, null);
		if (priceList.get_ID() <= 0) return "";
		
		// Seteo Moneda
		mTab.setValue("C_Currency_ID", priceList.getC_Currency_ID());

		// Seteo Tasa de Cambio
		MClient client = new MClient(ctx, Env.getAD_Client_ID(ctx), null);
		MAcctSchema schema = client.getAcctSchema();
		int idMonedaNacional = 142;
		if (schema.get_ID() > 0) idMonedaNacional = schema.getC_Currency_ID();
		
		if (priceList.getC_Currency_ID() == idMonedaNacional){
			mTab.setValue("DivideRate", Env.ONE);
		}
		else{
			Timestamp dateDoc = Env.getContextAsDate(ctx, WindowNo, "DateDoc");
			BigDecimal divideRate = MConversionRate.getRate(priceList.getC_Currency_ID(), idMonedaNacional, dateDoc, 0, Env.getAD_Client_ID(ctx), Env.getAD_Org_ID(ctx));
			if (divideRate == null) divideRate = Env.ONE;
			mTab.setValue("DivideRate", divideRate);
		}
		
		// Seteo precio producto
		if (mTab.getValue("M_Product_ID") != null){
			final I_M_RequisitionLine line = GridTabWrapper.create(mTab, I_M_RequisitionLine.class);
			setPrice(ctx, WindowNo, line, Env.getContextAsDate(ctx, WindowNo, "DateDoc"));
		}
		
		return "";
	}

	/***
	 * Metodo que setea precio de producto. 
	 * OpenUp Ltda. Issue #88 
	 * @author Gabriel Vila - 30/10/2012
	 * @see
	 * @param ctx
	 * @param WindowNo
	 * @param line
	 * @param dateDoc
	 */
	private void setPrice(Properties ctx, int WindowNo, I_M_RequisitionLine line, Timestamp dateDoc)
	{
		int C_BPartner_ID = line.getC_BPartner_ID();
		BigDecimal Qty = line.getQty();
		boolean isSOTrx = false;
		MProductPricing pp = new MProductPricing (line.getM_Product_ID(), C_BPartner_ID, Qty, isSOTrx);
		//
		int M_PriceList_ID = line.getM_PriceList_ID();
		pp.setM_PriceList_ID(M_PriceList_ID);
		int M_PriceList_Version_ID = Env.getContextAsInt(ctx, WindowNo, "M_PriceList_Version_ID");
		pp.setM_PriceList_Version_ID(M_PriceList_Version_ID);
		Timestamp orderDate = dateDoc;
		pp.setPriceDate(orderDate);
		//
		line.setPriceActual(pp.getPriceStd());
		Env.setContext(ctx, WindowNo, "EnforcePriceLimit", pp.isEnforcePriceLimit() ? "Y" : "N");	//	not used
		Env.setContext(ctx, WindowNo, "DiscountSchema", pp.isDiscountSchema() ? "Y" : "N");
	}

	
}	//	CalloutRequisition