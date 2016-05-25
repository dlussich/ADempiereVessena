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
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.MPromotion;
import org.adempiere.model.MPromotionReward;
//import org.adempiere.exceptions.ProductNotOnPriceListException;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.openup.model.MStockStatus;
import org.openup.model.MStockTransaction;

/**
 *  Order Line Model.
 * 	<code>
 * 			MOrderLine ol = new MOrderLine(m_order);
			ol.setM_Product_ID(wbl.getM_Product_ID());
			ol.setQtyOrdered(wbl.getQuantity());
			ol.setPrice();
			ol.setPriceActual(wbl.getPrice());
			ol.setTax();
			ol.save();

 *	</code>
 *  @author Jorg Janke
 *  @version $Id: MOrderLine.java,v 1.6 2006/10/02 05:18:39 jjanke Exp $
 * 
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 *			<li>BF [ 2588043 ] Insufficient message ProductNotOnPriceList
 * @author Michael Judd, www.akunagroup.com
 * 			<li>BF [ 1733602 ] Price List including Tax Error - when a user changes the orderline or
 * 				invoice line for a product on a price list that includes tax, the net amount is
 * 				incorrectly calculated.
 */
public class MOrderLine extends X_C_OrderLine
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7305265800857547603L;

	// OpenUp. Gabriel Vila. 03/03/2011.
	// Necesito que la linea conserve el mensaje de error en validaciones.
	private String m_processMsg = null;
	// Fin OpenUp.
	
	/**
	 * 	Get Order Unreserved Qty
	 *	@param ctx context
	 *	@param M_Warehouse_ID wh
	 *	@param M_Product_ID product
	 *	@param M_AttributeSetInstance_ID asi
	 *	@param excludeC_OrderLine_ID exclude C_OrderLine_ID
	 *	@return Unreserved Qty
	 */
	public static BigDecimal getNotReserved (Properties ctx, int M_Warehouse_ID, 
		int M_Product_ID, int M_AttributeSetInstance_ID, int excludeC_OrderLine_ID)
	{
		BigDecimal retValue = Env.ZERO;
		String sql = "SELECT SUM(QtyOrdered-QtyDelivered-QtyReserved) "
			+ "FROM C_OrderLine ol"
			+ " INNER JOIN C_Order o ON (ol.C_Order_ID=o.C_Order_ID) "
			+ "WHERE ol.M_Warehouse_ID=?"	//	#1
			+ " AND M_Product_ID=?"			//	#2
			+ " AND o.IsSOTrx='Y' AND o.DocStatus='DR'"
			+ " AND QtyOrdered-QtyDelivered-QtyReserved<>0"
			+ " AND ol.C_OrderLine_ID<>?";
		if (M_AttributeSetInstance_ID != 0)
			sql += " AND M_AttributeSetInstance_ID=?";
		
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt (1, M_Warehouse_ID);
			pstmt.setInt (2, M_Product_ID);
			pstmt.setInt (3, excludeC_OrderLine_ID);
			if (M_AttributeSetInstance_ID != 0)
				pstmt.setInt (4, M_AttributeSetInstance_ID);
			ResultSet rs = pstmt.executeQuery ();
			if (rs.next ())
				retValue = rs.getBigDecimal(1);
			rs.close ();
			pstmt.close ();
			pstmt = null;
		}
		catch (Exception e)
		{
			s_log.log (Level.SEVERE, sql, e);
		}
		try
		{
			if (pstmt != null)
				pstmt.close ();
			pstmt = null;
		}
		catch (Exception e)
		{
			pstmt = null;
		}
		if (retValue == null)
			s_log.fine("-");
		else
			s_log.fine(retValue.toString());
		return retValue;
	}	//	getNotReserved
	
	
	/**	Logger	*/
	private static CLogger s_log = CLogger.getCLogger (MOrderLine.class);
	
	/**************************************************************************
	 *  Default Constructor
	 *  @param ctx context
	 *  @param  C_OrderLine_ID  order line to load
	 *  @param trxName trx name
	 */
	public MOrderLine (Properties ctx, int C_OrderLine_ID, String trxName)
	{
		super (ctx, C_OrderLine_ID, trxName);
		if (C_OrderLine_ID == 0)
		{
		//	setC_Order_ID (0);
		//	setLine (0);
		//	setM_Warehouse_ID (0);	// @M_Warehouse_ID@
		//	setC_BPartner_ID(0);
		//	setC_BPartner_Location_ID (0);	// @C_BPartner_Location_ID@
		//	setC_Currency_ID (0);	// @C_Currency_ID@
		//	setDateOrdered (new Timestamp(System.currentTimeMillis()));	// @DateOrdered@
			//
		//	setC_Tax_ID (0);
		//	setC_UOM_ID (0);
			//
			setFreightAmt (Env.ZERO);
			setLineNetAmt (Env.ZERO);
			//
			setPriceEntered(Env.ZERO);
			setPriceActual (Env.ZERO);
			setPriceLimit (Env.ZERO);
			setPriceList (Env.ZERO);
			//
			setM_AttributeSetInstance_ID(0);
			//
			setQtyEntered (Env.ZERO);
			setQtyOrdered (Env.ZERO);	// 1
			setQtyDelivered (Env.ZERO);
			setQtyInvoiced (Env.ZERO);
			setQtyReserved (Env.ZERO);
			//
			setIsDescription (false);	// N
			setProcessed (false);
			setLine (0);

			this.setRefreshWindow(true); // OpenUp. Gabriel Vila. 17/02/2012.
		}
	}	//	MOrderLine
	
	/**
	 *  Parent Constructor.
	 		ol.setM_Product_ID(wbl.getM_Product_ID());
			ol.setQtyOrdered(wbl.getQuantity());
			ol.setPrice();
			ol.setPriceActual(wbl.getPrice());
			ol.setTax();
			ol.save();
	 *  @param  order parent order
	 */
	public MOrderLine (MOrder order)
	{
		this (order.getCtx(), 0, order.get_TrxName());
		if (order.get_ID() == 0)
			throw new IllegalArgumentException("Header not saved");
		setC_Order_ID (order.getC_Order_ID());	//	parent
		setOrder(order);
		this.setRefreshWindow(true); // OpenUp. Gabriel Vila. 17/02/2012.
	}	//	MOrderLine

	/**
	 *  Load Constructor
	 *  @param ctx context
	 *  @param rs result set record
	 *  @param trxName transaction
	 */
	public MOrderLine (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
		this.setRefreshWindow(true); // OpenUp. Gabriel Vila. 17/02/2012.
	}	//	MOrderLine

	private int 			m_M_PriceList_ID = 0;
	//
	private boolean			m_IsSOTrx = true;
	//	Product Pricing
	private MProductPricing	m_productPrice = null;

	/** Tax							*/
	private MTax 		m_tax = null;
	
	/** Cached Currency Precision	*/
	private Integer			m_precision = null;
	/**	Product					*/
	private MProduct 		m_product = null;
	/**	Charge					*/
	private MCharge 		m_charge = null;
	/** Parent					*/
	private MOrder			m_parent = null;
	
	/**
	 * 	Set Defaults from Order.
	 * 	Does not set Parent !!
	 * 	@param order order
	 */
	public void setOrder (MOrder order)
	{
		setClientOrg(order);
		setC_BPartner_ID(order.getC_BPartner_ID());
		setC_BPartner_Location_ID(order.getC_BPartner_Location_ID());
		setM_Warehouse_ID(order.getM_Warehouse_ID());
		setDateOrdered(order.getDateOrdered());
		setDatePromised(order.getDatePromised());
		setC_Currency_ID(order.getC_Currency_ID());
		//
		setHeaderInfo(order);	//	sets m_order
		//	Don't set Activity, etc as they are overwrites
	}	//	setOrder

	/**
	 * 	Set Header Info
	 *	@param order order
	 */
	public void setHeaderInfo (MOrder order)
	{
		m_parent = order;
		m_IsSOTrx = order.isSOTrx();
		//OpenUp. INes Fernandez. 19/08/2015. Considera caso lista de precio en la linea
		if((!MSysConfig.getBooleanValue("UY_RETAIL_INTERFACE", false, Env.getAD_Client_ID(getCtx())))){
			m_precision = new Integer(order.getPrecision());
			m_M_PriceList_ID = order.getM_PriceList_ID();
		}
		else{ //la lista de precios está en la linea
			m_M_PriceList_ID = this.get_ValueAsInt("M_PriceList_ID");
		}
		
	}	//	setHeaderInfo
	
	/**
	 * 	Get Parent
	 *	@return parent
	 */
	public MOrder getParent()
	{
		if (m_parent == null)
			m_parent = new MOrder(getCtx(), getC_Order_ID(), get_TrxName());
		return m_parent;
	}	//	getParent
	
	/**
	 * 	Set Price Entered/Actual.
	 * 	Use this Method if the Line UOM is the Product UOM 
	 *	@param PriceActual price
	 */
	public void setPrice (BigDecimal PriceActual)
	{
		setPriceEntered(PriceActual);
		setPriceActual (PriceActual);
	}	//	setPrice

	/**
	 * 	Set Price Actual.
	 * 	(actual price is not updateable)
	 *	@param PriceActual actual price
	 */
	public void setPriceActual (BigDecimal PriceActual)
	{
		if (PriceActual == null) 
			throw new IllegalArgumentException ("PriceActual is mandatory");
		set_ValueNoCheck("PriceActual", PriceActual);
	}	//	setPriceActual

	/**
	 * 	Set Price for Product and PriceList.
	 * 	Use only if newly created.
	 * 	Uses standard price list of not set by order constructor
	 */
	public void setPrice()
	{
		
		if (getM_Product_ID() == 0)
			return;
		if (m_M_PriceList_ID == 0)
			throw new IllegalStateException("Por favor seleccione una lista de precios!");
		setPrice (m_M_PriceList_ID);
	}	//	setPrice

	/**
	 * 	Set Price for Product and PriceList
	 * 	@param M_PriceList_ID price list
	 */
	public void setPrice (int M_PriceList_ID)
	{
		if (getM_Product_ID() == 0)
			return;
		//
		log.fine(toString() + " - M_PriceList_ID=" + M_PriceList_ID);
		getProductPricing (M_PriceList_ID);
		setPriceActual (m_productPrice.getPriceStd());
		setPriceList (m_productPrice.getPriceList());
		setPriceLimit (m_productPrice.getPriceLimit());
		//
		if (getQtyEntered().compareTo(getQtyOrdered()) == 0)
			setPriceEntered(getPriceActual());
		else
			setPriceEntered(getPriceActual().multiply(getQtyOrdered()
				.divide(getQtyEntered(), 12, BigDecimal.ROUND_HALF_UP)));	//	recision
		
		//	Calculate Discount
		setDiscount(m_productPrice.getDiscount());
		//	Set UOM
		// OpenUp. Nicolas Garcia. 13/10/2011. Issue #887.
		//Se comenta linea.
		// setC_UOM_ID(m_productPrice.getC_UOM_ID());
		//Fin Issue #887

	}	//	setPrice

	/**
	 * 	Get and calculate Product Pricing
	 *	@param M_PriceList_ID id
	 *	@return product pricing
	 */
	private MProductPricing getProductPricing (int M_PriceList_ID)
	{
		m_productPrice = new MProductPricing (getM_Product_ID(), 
			getC_BPartner_ID(), getQtyOrdered(), m_IsSOTrx);
		m_productPrice.setM_PriceList_ID(M_PriceList_ID);
		m_productPrice.setPriceDate(getDateOrdered());
		//
		//OpenUp. INes Fernandez. 19/08/2015. Issue #4616
		if (this.getC_Order_ID()>0){
			MOrder hdr = new MOrder(getCtx(), this.getC_Order_ID(), this.get_TrxName());
			if (!hdr.isSOTrx() && this.isUY_EsBonificCruzada()){
				return m_productPrice; 
			}
		}// End Issue #4616
		m_productPrice.calculatePrice();
		return m_productPrice;
	}	//	getProductPrice
	
	/**
	 *	Set Tax
	 *	@return true if tax is set
	 */
	public boolean setTax()
	{
		int ii = Tax.get(getCtx(), getM_Product_ID(), getC_Charge_ID(), getDateOrdered(), getDateOrdered(),
			getAD_Org_ID(), getM_Warehouse_ID(),
			getC_BPartner_Location_ID(),		//	should be bill to
			getC_BPartner_Location_ID(), m_IsSOTrx);
		if (ii == 0)
		{
			log.log(Level.SEVERE, "No Tax found");
			return false;
		}
		setC_Tax_ID (ii);
		return true;
	}	//	setTax
	
	/**
	 * 	Calculate Extended Amt.
	 * 	May or may not include tax
	 */
	public void setLineNetAmt ()
	{
		BigDecimal bd = getPriceActual().multiply(getQtyOrdered()); 
		
		boolean documentLevel = getTax().isDocumentLevel();
		
		//	juddm: Tax Exempt & Tax Included in Price List & not Document Level - Adjust Line Amount
		//  http://sourceforge.net/tracker/index.php?func=detail&aid=1733602&group_id=176962&atid=879332
		if (isTaxIncluded() && !documentLevel)	{
			BigDecimal taxStdAmt = Env.ZERO, taxThisAmt = Env.ZERO;
			
			MTax orderTax = getTax();
			MTax stdTax = null;
			
			//	get the standard tax
			if (getProduct() == null)
			{
				if (getCharge() != null)	// Charge 
				{
					stdTax = new MTax (getCtx(), 
							((MTaxCategory) getCharge().getC_TaxCategory()).getDefaultTax().getC_Tax_ID(),
							get_TrxName());
				}
					
			}
			else	// Product
				stdTax = new MTax (getCtx(), 
							((MTaxCategory) getProduct().getC_TaxCategory()).getDefaultTax().getC_Tax_ID(), 
							get_TrxName());

			if (stdTax != null)
			{
				log.fine("stdTax rate is " + stdTax.getRate());
				log.fine("orderTax rate is " + orderTax.getRate());
				
				taxThisAmt = taxThisAmt.add(orderTax.calculateTax(bd, isTaxIncluded(), getPrecision()));
				taxStdAmt = taxStdAmt.add(stdTax.calculateTax(bd, isTaxIncluded(), getPrecision()));
				
				bd = bd.subtract(taxStdAmt).add(taxThisAmt);
				
				log.fine("Price List includes Tax and Tax Changed on Order Line: New Tax Amt: " 
						+ taxThisAmt + " Standard Tax Amt: " + taxStdAmt + " Line Net Amt: " + bd);	
			}
			
		}
		
		if (bd.scale() > getPrecision())
			bd = bd.setScale(getPrecision(), BigDecimal.ROUND_HALF_UP);
		super.setLineNetAmt (bd);
	}	//	setLineNetAmt
	
	/**
	 * 	Get Charge
	 *	@return product or null
	 */
	public MCharge getCharge()
	{
		if (m_charge == null && getC_Charge_ID() != 0)
			m_charge =  MCharge.get (getCtx(), getC_Charge_ID());
		return m_charge;
	}
	/**
	 * 	Get Tax
	 *	@return tax
	 */
	protected MTax getTax()
	{
		if (m_tax == null)
			m_tax = MTax.get(getCtx(), getC_Tax_ID());
		return m_tax;
	}	//	getTax

	/**
	 * 	Get Currency Precision from Currency
	 *	@return precision
	 */
	public int getPrecision()
	{
		if (m_precision != null)
			return m_precision.intValue();
		//
		if (getC_Currency_ID() == 0)
		{
			setOrder (getParent());
			if (m_precision != null)
				return m_precision.intValue();
		}
		if (getC_Currency_ID() != 0)
		{
			MCurrency cur = MCurrency.get(getCtx(), getC_Currency_ID());
			if (cur.get_ID() != 0)
			{
				m_precision = new Integer (cur.getStdPrecision());
				return m_precision.intValue();
			}
		}
		//	Fallback
		String sql = "SELECT c.StdPrecision "
			+ "FROM C_Currency c INNER JOIN C_Order x ON (x.C_Currency_ID=c.C_Currency_ID) "
			+ "WHERE x.C_Order_ID=?";
		int i = DB.getSQLValue(get_TrxName(), sql, getC_Order_ID());
		m_precision = new Integer(i);
		return m_precision.intValue();
	}	//	getPrecision
	
	/**
	 * 	Set Product
	 *	@param product product
	 */
	public void setProduct (MProduct product)
	{
		m_product = product;
		if (m_product != null)
		{
			setM_Product_ID(m_product.getM_Product_ID());
			setC_UOM_ID (m_product.getC_UOM_ID());
		}
		else
		{
			setM_Product_ID(0);
			set_ValueNoCheck ("C_UOM_ID", null);
		}
		setM_AttributeSetInstance_ID(0);
	}	//	setProduct

	
	/**
	 * 	Set M_Product_ID
	 *	@param M_Product_ID product
	 *	@param setUOM set also UOM
	 */
	public void setM_Product_ID (int M_Product_ID, boolean setUOM)
	{
		if (setUOM)
			setProduct(MProduct.get(getCtx(), M_Product_ID));
		else
			super.setM_Product_ID (M_Product_ID);
		setM_AttributeSetInstance_ID(0);
	}	//	setM_Product_ID
	
	/**
	 * 	Set Product and UOM
	 *	@param M_Product_ID product
	 *	@param C_UOM_ID uom
	 */
	public void setM_Product_ID (int M_Product_ID, int C_UOM_ID)
	{
		super.setM_Product_ID (M_Product_ID);
		if (C_UOM_ID != 0)
			super.setC_UOM_ID(C_UOM_ID);
		setM_AttributeSetInstance_ID(0);
	}	//	setM_Product_ID
	
	
	/**
	 * 	Get Product
	 *	@return product or null
	 */
	public MProduct getProduct()
	{
		if (m_product == null && getM_Product_ID() != 0)
			m_product =  MProduct.get (getCtx(), getM_Product_ID());
		return m_product;
	}	//	getProduct
	
	/**
	 * 	Set M_AttributeSetInstance_ID
	 *	@param M_AttributeSetInstance_ID id
	 */
	public void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID)
	{
		if (M_AttributeSetInstance_ID == 0)		//	 0 is valid ID
			set_Value("M_AttributeSetInstance_ID", new Integer(0));
		else
			super.setM_AttributeSetInstance_ID (M_AttributeSetInstance_ID);
	}	//	setM_AttributeSetInstance_ID
	
	/**
	 * 	Set Warehouse
	 *	@param M_Warehouse_ID warehouse
	 */
	public void setM_Warehouse_ID (int M_Warehouse_ID)
	{
		if (getM_Warehouse_ID() > 0
			&& getM_Warehouse_ID() != M_Warehouse_ID
			&& !canChangeWarehouse())
			log.severe("Ignored - Already Delivered/Invoiced/Reserved");
		else
			super.setM_Warehouse_ID (M_Warehouse_ID);
	}	//	setM_Warehouse_ID
	
	/**
	 * 	Can Change Warehouse
	 *	@return true if warehouse can be changed
	 */
	public boolean canChangeWarehouse()
	{
		if (getQtyDelivered().signum() != 0)
		{
			log.saveError("Error", Msg.translate(getCtx(), "QtyDelivered") + "=" + getQtyDelivered());
			return false;
		}
		if (getQtyInvoiced().signum() != 0)
		{
			log.saveError("Error", Msg.translate(getCtx(), "QtyInvoiced") + "=" + getQtyInvoiced());
			return false;
		}
		if (getQtyReserved().signum() != 0)
		{
			log.saveError("Error", Msg.translate(getCtx(), "QtyReserved") + "=" + getQtyReserved());
			return false;
		}
		//	We can change
		return true;
	}	//	canChangeWarehouse
	
	/**
	 * 	Get C_Project_ID
	 *	@return project
	 */
	public int getC_Project_ID()
	{
		int ii = super.getC_Project_ID ();
		if (ii == 0)
			ii = getParent().getC_Project_ID();
		return ii;
	}	//	getC_Project_ID
	
	/**
	 * 	Get C_Activity_ID
	 *	@return Activity
	 */
	public int getC_Activity_ID()
	{
		int ii = super.getC_Activity_ID ();
		if (ii == 0)
			ii = getParent().getC_Activity_ID();
		return ii;
	}	//	getC_Activity_ID
	
	/**
	 * 	Get C_Campaign_ID
	 *	@return Campaign
	 */
	public int getC_Campaign_ID()
	{
		int ii = super.getC_Campaign_ID ();
		if (ii == 0)
			ii = getParent().getC_Campaign_ID();
		return ii;
	}	//	getC_Campaign_ID
	
	/**
	 * 	Get User2_ID
	 *	@return User2
	 */
	public int getUser1_ID ()
	{
		int ii = super.getUser1_ID ();
		if (ii == 0)
			ii = getParent().getUser1_ID();
		return ii;
	}	//	getUser1_ID

	/**
	 * 	Get User2_ID
	 *	@return User2
	 */
	public int getUser2_ID ()
	{
		int ii = super.getUser2_ID ();
		if (ii == 0)
			ii = getParent().getUser2_ID();
		return ii;
	}	//	getUser2_ID

	/**
	 * 	Get AD_OrgTrx_ID
	 *	@return trx org
	 */
	public int getAD_OrgTrx_ID()
	{
		int ii = super.getAD_OrgTrx_ID();
		if (ii == 0)
			ii = getParent().getAD_OrgTrx_ID();
		return ii;
	}	//	getAD_OrgTrx_ID

	/**************************************************************************
	 * 	String Representation
	 * 	@return info
	 */
	public String toString ()
	{
		StringBuffer sb = new StringBuffer ("MOrderLine[")
			.append(get_ID())
			.append(", Line=").append(getLine())
			.append(", Ordered=").append(getQtyOrdered())
			.append(", Delivered=").append(getQtyDelivered())
			.append(", Invoiced=").append(getQtyInvoiced())
			.append(", Reserved=").append(getQtyReserved())
			.append(", LineNet=").append(getLineNetAmt())
			.append ("]");
		return sb.toString ();
	}	//	toString

	/**
	 * 	Add to Description
	 *	@param description text
	 */
	public void addDescription (String description)
	{
		String desc = getDescription();
		if (desc == null)
			setDescription(description);
		else
			setDescription(desc + " | " + description);
	}	//	addDescription
	
	/**
	 * 	Get Description Text.
	 * 	For jsp access (vs. isDescription)
	 *	@return description
	 */
	public String getDescriptionText()
	{
		return super.getDescription();
	}	//	getDescriptionText
	
	/**
	 * 	Get Name
	 *	@return get the name of the line (from Product)
	 */
	public String getName()
	{
		getProduct();
		if (m_product != null)
			return m_product.getName();
		if (getC_Charge_ID() != 0)
		{
			MCharge charge = MCharge.get(getCtx(), getC_Charge_ID());
			return charge.getName();
		}
		return "";
	}	//	getName

	/**
	 * 	Set C_Charge_ID
	 *	@param C_Charge_ID charge
	 */
	public void setC_Charge_ID (int C_Charge_ID)
	{
		super.setC_Charge_ID (C_Charge_ID);
		if (C_Charge_ID > 0)
			set_ValueNoCheck ("C_UOM_ID", null);
	}	//	setC_Charge_ID
	/**
	 *	Set Discount
	 */
	public void setDiscount()
	{
		BigDecimal list = getPriceList();
		//	No List Price
		if (Env.ZERO.compareTo(list) == 0)
			return;
		BigDecimal discount = list.subtract(getPriceActual())
			.multiply(new BigDecimal(100))
			.divide(list, getPrecision(), BigDecimal.ROUND_HALF_UP);
		setDiscount(discount);
	}	//	setDiscount

	/**
	 *	Is Tax Included in Amount
	 *	@return true if tax calculated
	 */
	public boolean isTaxIncluded()
	{
		if (m_M_PriceList_ID == 0)
		{
			m_M_PriceList_ID = DB.getSQLValue(get_TrxName(),
				"SELECT M_PriceList_ID FROM C_Order WHERE C_Order_ID=?",
				getC_Order_ID());
		}
		MPriceList pl = MPriceList.get(getCtx(), m_M_PriceList_ID, get_TrxName());
		return pl.isTaxIncluded();
	}	//	isTaxIncluded

	
	/**
	 * 	Set Qty Entered/Ordered.
	 * 	Use this Method if the Line UOM is the Product UOM 
	 *	@param Qty QtyOrdered/Entered
	 */
	public void setQty (BigDecimal Qty)
	{
		super.setQtyEntered (Qty);
		super.setQtyOrdered (getQtyEntered());
	}	//	setQty

	/**
	 * 	Set Qty Entered - enforce entered UOM 
	 *	@param QtyEntered
	 */
	public void setQtyEntered (BigDecimal QtyEntered)
	{
		if (QtyEntered != null && getC_UOM_ID() != 0)
		{
			int precision = MUOM.getPrecision(getCtx(), getC_UOM_ID());
			QtyEntered = QtyEntered.setScale(precision, BigDecimal.ROUND_HALF_UP);
		}
		super.setQtyEntered (QtyEntered);
	}	//	setQtyEntered

	/**
	 * 	Set Qty Ordered - enforce Product UOM 
	 *	@param QtyOrdered
	 */
	public void setQtyOrdered (BigDecimal QtyOrdered)
	{
		MProduct product = getProduct();
		if (QtyOrdered != null && product != null)
		{
			int precision = product.getUOMPrecision();
			QtyOrdered = QtyOrdered.setScale(precision, BigDecimal.ROUND_HALF_UP);
		}
		super.setQtyOrdered(QtyOrdered);
	}	//	setQtyOrdered

	/**************************************************************************
	 * 	Before Save
	 *	@param newRecord
	 *	@return true if it can be saved
	 */
	protected boolean beforeSave (boolean newRecord)
	{
		if (newRecord && getParent().isComplete()) {
			log.saveError("ParentComplete", Msg.translate(getCtx(), "C_OrderLine"));
			return false;
		}
		//	Get Defaults from Parent
		if (getC_BPartner_ID() == 0 || getC_BPartner_Location_ID() == 0
			|| getM_Warehouse_ID() == 0 
			|| getC_Currency_ID() == 0)
			setOrder (getParent());

		// OpenUp. Obtengo modelos necesarios
		MOrder hdr = new MOrder (getCtx(),this.getC_Order_ID(),get_TrxName()); //obtengo cabezal
		MDocType doc = new MDocType (getCtx(),hdr.getC_DocTypeTarget_ID(),get_TrxName()); //obtengo tipo documento
		// Fin OpenUP		
		
		if (m_M_PriceList_ID == 0)
			setHeaderInfo(getParent());
		//OpenUp. INes Fernandez. 19/08/2015. Issue #4616
		if(this.isUY_EsBonificCruzada() && this.get_ValueAsBoolean("UY_BonificaSimple")){
			throw new AdempiereException("No puede seleccionarse Bonificacion Simple y Cruzada en una misma linea");
		}
		
		if(!newRecord && (is_ValueChanged("UY_BonificaSimple") || is_ValueChanged("UY_EsBonificCruzada"))){
			if(!this.isUY_EsBonificCruzada()) {
				this.set_Value("M_Product_ID_2", null);
			}
			if (!this.isUY_EsBonificCruzada() && !this.get_ValueAsBoolean("UY_BonificaSimple"))
				this.set_Value("QtyReward", BigDecimal.ZERO);
		}
		//End Issue #4616
		//	R/O Check - Product/Warehouse Change
		if (!newRecord 
			&& (is_ValueChanged("M_Product_ID") || is_ValueChanged("M_Warehouse_ID"))) 
		{
			if (!canChangeWarehouse())
				return false;
		}	//	Product Changed
		
		//	Charge
		//OpenUp. INes Fernandez
		if (getC_Charge_ID() != 0 && getM_Product_ID() != 0)
				setM_Product_ID(0);
		//	No Product
		if (getM_Product_ID() == 0)
			setM_AttributeSetInstance_ID(0);
		//	Product
		else	//	Set/check Product Price
		{
			//	Set Price if Actual = 0
			if ( m_productPrice == null 
				&&  Env.ZERO.compareTo(getPriceActual()) == 0
				&&  Env.ZERO.compareTo(getPriceList()) == 0
				&& (!this.isUY_EsBonificCruzada() && !doc.isSOTrx()))
				if ((doc.getValue() == null) || (doc.getValue() != null && !doc.getValue().equalsIgnoreCase("pointernal"))){
					setPrice();	
					setLineNetAmt();
				}
				else{
					this.setPriceLimit(this.getPriceEntered());
					this.setPriceActual(this.getPriceEntered());
				}
				
			//	Check if on Price list
			if (m_productPrice == null)
				getProductPricing(m_M_PriceList_ID);
			//OpenUp. INes Fernandez. 09/04/2015.
			//Permite ingresar productos que no pertenecen a la lista de precios seleccionada.(#2565)
			//OpenUp. INes Fernandez. 31/07/2015.
			//No controla contra la lista de compra que se setea en el cabezal. USO: cuando la lista esta en la linea.(#4616)
			if (!m_productPrice.isCalculated() && !MSysConfig.getBooleanValue("UY_ALLOW_NOT_IN_PRICELIST_PRODS", false, this.getAD_Client_ID())
					&& !MSysConfig.getBooleanValue("UY_RETAIL_INTERFACE", false, this.getAD_Client_ID()))
			{
				//OpenUp. Nicolas Sarlabos. 13/07/2015. #4552.
				//obtengo ultima version activa para la lista de origen seleccionada
				/*MPriceListVersion version = MPriceListVersion.forPriceList(getCtx(), m_M_PriceList_ID, get_TrxName());
				
				MProductPrice pprice = new MProductPrice(getCtx(), 0, get_TrxName());
				pprice.setM_PriceList_Version_ID(version.get_ID());
				pprice.setM_Product_ID(this.getM_Product_ID());
				pprice.setPriceList(Env.ONE);
				pprice.setPriceLimit(Env.ONE);
				pprice.setPriceStd(Env.ONE);
				pprice.saveEx();
				
				this.setPriceEntered(Env.ONE);		
				this.setLineNetAmt(Env.ONE);
				this.setPriceList(Env.ONE);
				this.setPriceLimit(Env.ONE);
				this.setPriceActual(Env.ONE);*/				
				//Fin #4552.
				
				// OpenUp. Gabriel Vila. 03/03/2011.
				// Guardo Mensaje de error en validacion controlada.
				MProduct prod = new MProduct(getCtx(), getM_Product_ID(), null);
				this.m_processMsg = "El Producto no esta en lista de Precios o la fecha del Pedido no entra en fecha de vigencia de lista." +  
				"\nProducto : " + prod.getValue() + " - " + prod.getName() +
				"\nLista de Precios : " + m_M_PriceList_ID;
				
				//throw new AdempiereException("@ProductNotOnPriceList@ @Line@: "+getLine());
				throw new AdempiereException(this.m_processMsg);
				// Fin OpenUp.
				
			}
			
			// Check zero prices against the base price list. OpenUP FL 11/03/2011, issue #480
			if (!this.checkZeroPrices()) {
				throw new AdempiereException("El precio ingresado, de lista o limite es cero. El precio del producto en la lista de precio base no es cero. Verifique los precios en la lista de precios del pedido, del cliente y la base.");		// TODO: Translate
			}
			
			// OpenUp. INes Fernandez. 10/08/2015. Issue #4616.
			// Si es una linea de bonificacion cruzada para compra, el precio es cero y la cantidad es la bonificada
			if (this.isUY_EsBonificCruzada() && !this.m_parent.isSOTrx()) {
				this.setPriceEntered(BigDecimal.ZERO);
				this.setPriceActual(BigDecimal.ZERO);
				this.setPriceLimit(BigDecimal.ZERO);
				this.setQtyEntered((BigDecimal)this.get_Value("QtyReward"));
				
				BigDecimal QtyOrdered = BigDecimal.ZERO;
				QtyOrdered = MUOMConversion.convertProductFrom (getCtx(), this.getM_Product_ID(),this.getC_UOM_ID(), this.getQtyEntered());
					if (QtyOrdered == null)	QtyOrdered = this.getQtyEntered();
				this.setQtyOrdered(QtyOrdered);
			}
			//End Issue #4616.
		}

		//	UOM
		if (getC_UOM_ID() == 0 
			&& (getM_Product_ID() != 0 
				|| getPriceEntered().compareTo(Env.ZERO) != 0
				|| getC_Charge_ID() != 0))
		{
			int C_UOM_ID = MUOM.getDefault_UOM_ID(getCtx());
			if (C_UOM_ID > 0)
				setC_UOM_ID (C_UOM_ID);
		}
		
		// OpenUp. Gabriel Vila. 28/07/2011. Issue #816
		// Obtengo factor de conversion de UM-UV
		BigDecimal factorUOM = Env.ONE;
		if (this.getC_UOM_ID() != 100) factorUOM = MUOMConversion.getProductRateFrom(getCtx(), this.getM_Product_ID(), this.getC_UOM_ID());
		if (factorUOM == null){
			MProduct prod = new MProduct(getCtx(), this.getM_Product_ID(), null);
			MUOM uom = new MUOM(getCtx(), this.getC_UOM_ID(), null);
			throw new AdempiereException("El Producto : " + prod.getValue() + " - " + prod.getName() + "\n" + 
			" no tiene FACTOR DE CONVERSION DEFINIDO entre la unidad de medida y la unidad de venta (" + uom.getUOMSymbol() + ")");
		}

		// Fin Issue #816
		
		//OpenUp Nicolas Sarlabos 12/03/2012 se comenta IF y se modifica abajo
		//	Qty Precision
		/*if (newRecord || is_ValueChanged("QtyEntered"))
			setQtyEntered(getQtyEntered());
		if (newRecord || is_ValueChanged("QtyOrdered")){
			setQtyOrdered(getQtyOrdered());
			setQtyReserved(getQtyOrdered());
		}*/
			
				
//		Qty Precision
		if (newRecord || is_ValueChanged("QtyEntered"))
			setQtyEntered(getQtyEntered());
		//OpenUp INes Fernandez 19/08/2015. Bonificacion Cruzada para compra. Issue #4616
		if (!doc.isSOTrx() && (this.isUY_EsBonificCruzada() && (newRecord || is_ValueChanged("QtyReward"))))
			setQtyEntered((BigDecimal)get_Value("QtyReward"));
		//End Issue #4616
		
		if (newRecord || is_ValueChanged("QtyOrdered")){
			setQtyOrdered(getQtyOrdered());
			//si es orden de compra seteo cantidad reservada=ordenada
			if (!doc.isSOTrx())
				setQtyReserved(getQtyOrdered());
		}
		//Fin OpenUp Nicolas Sarlabos 12/03/2012
		
		
		
		//	Qty on instance ASI for SO
		if (m_IsSOTrx 
			&& getM_AttributeSetInstance_ID() != 0
			&& (newRecord || is_ValueChanged("M_Product_ID")
				|| is_ValueChanged("M_AttributeSetInstance_ID")
				|| is_ValueChanged("M_Warehouse_ID")))
		{
			MProduct product = getProduct();
			if (product.isStocked())
			{
				// OpenUp. Gabriel Vila. 14/06/2011. Issue #719.
				// Comento esta validacion de stock que hace Adempiere
				// y la mejoro segun el modelo de openup.
				
				/*int M_AttributeSet_ID = product.getM_AttributeSet_ID();				
				boolean isInstance = M_AttributeSet_ID != 0;
				if (isInstance)
				{
					MAttributeSet mas = MAttributeSet.get(getCtx(), M_AttributeSet_ID);
					isInstance = mas.isInstanceAttribute();
				}
				//	Max
				if (isInstance)
				{
					MStorage[] storages = MStorage.getWarehouse(getCtx(), 
						getM_Warehouse_ID(), getM_Product_ID(), getM_AttributeSetInstance_ID(), 
						M_AttributeSet_ID, false, null, true, get_TrxName());
					BigDecimal qty = Env.ZERO;
					for (int i = 0; i < storages.length; i++)
					{
						if (storages[i].getM_AttributeSetInstance_ID() == getM_AttributeSetInstance_ID())
							qty = qty.add(storages[i].getQtyOnHand());
					}
					
					if (getQtyOrdered().compareTo(qty) > 0)
					{
						log.warning("Qty - Stock=" + qty + ", Ordered=" + getQtyOrdered());
						log.saveError("QtyInsufficient", "=" + qty); 
						return false;
					}
				}*/

				if (MSysConfig.getBooleanValue("UY_SALEORDER_RESERVE_STOCK", false, getAD_Client_ID(), this.getAD_Org_ID())){
					if (!MStockTransaction.haveSufficientStock(getQtyOrdered(), getM_Warehouse_ID(), 0, getM_Product_ID(),
							getM_AttributeSetInstance_ID(), 0, null, get_TrxName())){
						this.m_processMsg = "No hay stock suficiente para la cantidad que se ingresa en este producto.";
						throw new AdempiereException(this.m_processMsg); 
					}
				}
				// Fin OpenUp.
			}	//	stocked
		}	//	SO instance
		
		//	FreightAmt Not used
		if (Env.ZERO.compareTo(getFreightAmt()) != 0)
			setFreightAmt(Env.ZERO);

		//	Set Tax
		if (getC_Tax_ID() == 0)
			setTax();

		//	Get Line No
		if (getLine() == 0)
		{
			String sql = "SELECT COALESCE(MAX(Line),0)+10 FROM C_OrderLine WHERE C_Order_ID=?";
			int ii = DB.getSQLValue (get_TrxName(), sql, getC_Order_ID());
			setLine (ii);
		}
		
		// OpenUp. Gabriel Vila. 30/12/2010
		// Comento lineas de recalculo de neto y descuentos
		
		//	Calculations & Rounding
		//setLineNetAmt();	//	extended Amount with or without tax
		//setDiscount();

		// Fin OpenUp.
		
		
		// OpenUp. Gabriel Vila. 01/03/2011. Issue #443.
		// Me aseguro que el neto de la linea este bien calculado en base a descuentos.
		// No lo hago en pedidos edi ya que me pueden venir descuentos manuales y esto
		// ya se calcula antes.
		//this.verificoNetoLinea();
		// OpenUp. Nicolas Garcia. 27/10/2011. Issue #821.
		//OpenUp Nicolas Sarlabos 24/07/2012, se elimina ID quemado
		if (doc.isSOTrx()){
			if (doc.getDocBaseType().equalsIgnoreCase("SOO") && doc.getDocSubTypeSO()!=null){
				if(doc.getDocSubTypeSO().equalsIgnoreCase("SO") && !doc.isDefault()){
					if(!this.isUY_EsBonificCruzada() && !getParent().isComplete()) {
						this.setFormatInfo(null);
					}
				}
			}
			//OpenUp. Nicolas Sarlabos. 17/01/2014. #1819.  
			if(doc.getValue()!=null){
				if(doc.getValue().equalsIgnoreCase("salesorder")){
					if(newRecord || (!newRecord && (is_ValueChanged("M_Product_ID") || is_ValueChanged("C_Uom_ID")))){
						this.validateLine();
					}
				}
			}
			//Fin OpenUp. #1819.
		}
		//Fin OpenUp Nicolas Sarlabos 24/07/2012	
		
		//Fin Issue #821
		if (isUY_EsBonificCruzada()) { //both SO and PO
			this.setPriceActual(Env.ZERO);
			this.setPriceCost(Env.ZERO);
			this.setPriceEntered(Env.ZERO);
			this.setPriceLimit(Env.ZERO);
			this.setPriceList(Env.ZERO);
			this.setLineNetAmt(Env.ZERO);
			this.setPrice(Env.ZERO);
			this.setuy_promodiscountmax(Env.ZERO);
			this.setuy_promodiscount(Env.ZERO);
			this.setRRAmt(Env.ZERO);

		}
		// Fin OpenUp.
		
		// Openup. Gabriel Vila. 11/03/2011. Issue #424.
		// Guardo cantidad del pedido original cuando estoy en Drafted

		if (hdr.getDocStatus().equalsIgnoreCase(X_C_Order.DOCSTATUS_Drafted)){
			this.setuy_qtyentered_original(this.getQtyEntered());
		}
		// Fin OpenUp
		
		setuy_factor(factorUOM);
		setUY_BonificaReglaUM(getuy_bonificaregla().multiply(factorUOM));
		
		// OpenUp Nicolas Sarlabos 24/07/2012, se controla que qtyentered no sea mayor a qtyordered
		if(!doc.isSOTrx()){
			//OpenUp INes Fernandez 13/05/2015. Issue #3445
			//se controla que qtyentered no sea mayor a qtyordered, exceptuando los casos 
			//en que se usan unidades menores a la definida para el producto
			//if(doc.getDocBaseType()!=null){ 
			if(doc.getDocBaseType()!=null && !MSysConfig.getBooleanValue("ProductUOMToSmallerUOMConversion", false, getAD_Client_ID())){
			//Fin Issue	#3445
				if (doc.getDocBaseType().equalsIgnoreCase("POO") && doc.getDocSubTypeSO()==null){
					if(this.getQtyEntered().compareTo(this.getQtyOrdered())>0) throw new AdempiereException ("Cantidad en unidad de venta no debe ser mayor a cantidad en unidades simples");
				}
			}
			//OpenUp. Nicolas Sarlabos. 13/10/2014. #3057.	
			if(doc.getValue()!=null){
				if(doc.getValue().equalsIgnoreCase("poflete")){
					
					MTax tax = MTax.forValue(getCtx(), "exento", get_TrxName());
					
					if(tax != null) this.setC_Tax_ID(tax.get_ID());	
					
					
				}
			}
			//Fin OpenUp #3057.
		}
		//Fin OpenUp Nicolas Sarlabos 24/07/2012
		
		//OpenUp. Nicolas Sarlabos. 18/10/2012
		//siempre guardo el importe original por UM, sin descuentos, para imprimir en la factura
		//y lo redondeo sin decimales si la unidad es una CAJA
		if (MSysConfig.getBooleanValue("UY_IS_SOLUTION", false, this.getAD_Client_ID())){

			if (hdr.isSOTrx()){

				int M_PriceList_ID = hdr.getM_PriceList_ID();
				int StdPrecision = MPriceList.getStandardPrecision(getCtx(), M_PriceList_ID);
				MUOM uom = new MUOM(getCtx(),this.getC_UOM_ID(),get_TrxName());
				if(uom!=null){
					if(uom.getX12DE355().equalsIgnoreCase("CX")) StdPrecision=0;
				}
				BigDecimal priceOriginal = Env.ZERO;
				priceOriginal = MUOMConversion.convertProductFrom (getCtx(), this.getM_Product_ID(), 
						this.getC_UOM_ID(), this.getPriceList());
				if(StdPrecision==0){
					priceOriginal = priceOriginal.setScale(StdPrecision, BigDecimal.ROUND_HALF_UP);
				}
				this.setuy_printprice(priceOriginal);
			}
		}
		//Fin OpenUp
		
		//OpenUp SBT 10/11/2015 Issue # 5028
		//Se modifica la cantidad reservada si la linea es BonificaciónSimple
		if(newRecord && this.get_ValueAsBoolean("UY_BonificaSimple")){
			this.setQtyReserved(this.getQtyEntered()
					.add((this.get_Value("QtyReward")!=null)? (BigDecimal)this.get_Value("QtyReward"):Env.ZERO));
		}
		
		if(MSysConfig.getBooleanValue("UY_RETAIL_INTERFACE", false, this.getAD_Client_ID())){
			
			if(this.getPriceList() != null){
				String sql1 = "SELECT 1 FROM C_BPartner_Product WHERE M_Product_ID = " + this.getM_Product_ID() + 
					      " AND M_PriceList_Version_ID = "+ this.get_ValueAsInt("M_PriceList_Version_ID");
			
				int priceListOK = DB.getSQLValueEx(null, sql1);
				if(priceListOK <= 0 ){
					throw new AdempiereException("El producto no pertence a la lista de precios seleccionada");
				}
			}			
			
		}		
		
		return true;
	}	//	beforeSave

	/***
	 * Metodo que valida la existencia de una linea con igual producto y UM en la orden de venta actual.
	 * OpenUp Ltda. Issue #1819
	 * @author Nicolas Sarlabos - 17/01/2014
	 * @see
	 * @return
	 */	
	private void validateLine() {
		
		String sql = "select c_orderline_id" +
	             " from c_orderline" +
			     " where c_order_id = " + this.getC_Order_ID() +
			     " and m_product_id = " + this.getM_Product_ID() +
			     " and c_uom_id = " + this.getC_UOM_ID();
	
	int ID = DB.getSQLValueEx(get_TrxName(), sql);
	
	if(ID > 0) throw new AdempiereException ("Ya existe una linea para este producto y unidad de medida");
		
	}

	/**
	 * 	Before Delete
	 *	@return true if it can be deleted
	 */
	protected boolean beforeDelete ()
	{
		//	R/O Check - Something delivered. etc.
		if (Env.ZERO.compareTo(getQtyDelivered()) != 0)
		{
			log.saveError("DeleteError", Msg.translate(getCtx(), "QtyDelivered") + "=" + getQtyDelivered());
			return false;
		}
		if (Env.ZERO.compareTo(getQtyInvoiced()) != 0)
		{
			log.saveError("DeleteError", Msg.translate(getCtx(), "QtyInvoiced") + "=" + getQtyInvoiced());
			return false;
		}
				
		//OpenUp Nicolas Sarlabos 12/03/2012 se comenta IF y se modifica abajo
		/*if (Env.ZERO.compareTo(getQtyReserved()) != 0 && this.getQtyDelivered().compareTo(Env.ZERO)!=0)
		{
			//	For PO should be On Order
			log.saveError("DeleteError", Msg.translate(getCtx(), "QtyReserved") + "=" + getQtyReserved());
			return false;
		}*/
		
		MOrder hdr = new MOrder (getCtx(),this.getC_Order_ID(),get_TrxName()); //obtengo cabezal
		MDocType doc = new MDocType (getCtx(),hdr.getC_DocTypeTarget_ID(),get_TrxName()); //obtengo tipo documento
		
		//si es orden de compra NO se permite eliminar la linea cuando la qtyreserved y qtydelivered son <> 0
		if (!doc.isSOTrx()){
			if (Env.ZERO.compareTo(getQtyReserved()) != 0 && this.getQtyDelivered().compareTo(Env.ZERO)!=0){

				log.saveError("DeleteError", Msg.translate(getCtx(), "QtyReserved") + "=" + getQtyReserved());
				return false;
			}
			
		} else if (Env.ZERO.compareTo(getQtyReserved()) != 0){

			log.saveError("DeleteError", Msg.translate(getCtx(), "QtyReserved") + "=" + getQtyReserved());
			return false;
		}
		//Fin OpenUp Nicolas Sarlabos 12/03/2012
				
		// UnLink All Requisitions
		MRequisitionLine.unlinkC_OrderLine_ID(getCtx(), get_ID(), get_TrxName());
		
		// OpenUp. Nicolas Garcia. 18/10/2011. Issue #821.

		// Si es una linea de bonificacion no la deja borrar
		if (this.isUY_EsBonificCruzada()) {
			if (this.getParent().isSOTrx()){
				throw new AdempiereException("Esta linea es la  bonificacion de otra linea.");	
			}

		} else {
			if (this.getParent().isSOTrx()){
				// Borro si es que tiene lineas hijos
				DB.executeUpdate("DELETE FROM c_orderline WHERE c_order_id =" + this.getC_Order_ID() + 
						" AND uy_lineapadre_id=" + this.get_ID(), this.get_TrxName());
			}
		}
		// Fin Issue #821

		return true;
	}	//	beforeDelete
	
	/**
	 * 	After Save
	 *	@param newRecord new
	 *	@param success success
	 *	@return saved
	 */
	protected boolean afterSave (boolean newRecord, boolean success)
	{
		// OpenUp. Nicolas Garcia. 27/10/2011. Issue #821.


		// Me aseguro de guardar correctamente el idPadre
		if (!this.isUY_EsBonificCruzada()) {
			if (getParent().isSOTrx()){
				DB.executeUpdate("Update c_orderline SET uy_lineapadre_ID=" + this.get_ID()
				+ "  WHERE uy_lineapadre_ID = 0 AND uy_esbonificcruzada='Y' AND c_order_id=" + this.getC_Order_ID(), this.get_TrxName());
			}
		}
		
		
		//Fin Issue #821
		if (!success)
			return success;
		if (!newRecord && is_ValueChanged("C_Tax_ID"))
		{
			//	Recalculate Tax for old Tax
			if (!getParent().isProcessed())
				if (!updateOrderTax(true))
					return false;
		}
		return updateHeaderTax();
	}	//	afterSave

	/**
	 * 	After Delete
	 *	@param success success
	 *	@return deleted
	 */
	protected boolean afterDelete (boolean success)
	{
		if (!success)
			return success;
		if (getS_ResourceAssignment_ID() != 0)
		{
			MResourceAssignment ra = new MResourceAssignment(getCtx(), getS_ResourceAssignment_ID(), get_TrxName());
			ra.delete(true);
		}
		
		return updateHeaderTax();
	}	//	afterDelete
	
	
	/**
	 * Check zero prices against the base price list. OpenUP FL 11/03/2011, issue #480
	 * @return true if success, false otherwise
	 * 
	 * @author OpenUP FL
	 */
	private boolean checkZeroPrices() {
		
		// If an error hapend in this method, it should return true to avoid block. Defensive
		try	{
			
			// First check if the base price list is requiered, cheking if any price is zero
			if (m_IsSOTrx) {																																	// TODO: m_IsSOTrx should be get from the parent, now this is set by setHeaderInfo
				if ((this.getPriceEntered().equals(BigDecimal.ZERO))||(this.getPriceActual().equals(BigDecimal.ZERO))||(this.getPriceLimit().equals(BigDecimal.ZERO))) {
					
					// Get the system base price list value
					String UY_BASE_PRICE_LIST=MSysConfig.getValue("UY_BASE_PRICE_LIST",Env.getAD_Client_ID(Env.getCtx()));
					
					// Check if the system base price list its defined. Defensive
					if (UY_BASE_PRICE_LIST!=null) {
						
						// Cast the system base price list value. Defensive
						Integer basePriceListId=new Integer(UY_BASE_PRICE_LIST);
						
						// The base price list id cannot be null neither 0. Defensive
						if ((basePriceListId!=null)&&(!basePriceListId.equals(0))) {
							
							// Get the product pricing for the base price list
							MProductPricing baseProductPricing=new MProductPricing(this.getM_Product_ID(),this.getC_BPartner_ID(), getQtyOrdered(), m_IsSOTrx);		// TODO: m_IsSOTrx should be get from the parent, now this is set by setHeaderInfo
							baseProductPricing.setM_PriceList_ID(basePriceListId);
				
							// Check if price entred is zero and the base standard price is not zero 
							if ((this.getPriceEntered().equals(BigDecimal.ZERO))&&(!baseProductPricing.getPriceStd().equals(BigDecimal.ZERO))) {
								return(false); 
							}
			
							// Check if price actual is zero and the base list price is not zero 
							if ((this.getPriceActual().equals(BigDecimal.ZERO))&&(!baseProductPricing.getPriceList().equals(BigDecimal.ZERO))) {
								return(false); 
							}
			
							// Check if price limit is zero and the base limit price is not zero 
							if ((this.getPriceLimit().equals(BigDecimal.ZERO))&&(!baseProductPricing.getPriceLimit().equals(BigDecimal.ZERO))) {
								return(false); 
							}
						}
					}
				}
			}
		}
		catch (Exception e) {
			s_log.log(Level.SEVERE, "checkZeroPrices", e);		// Do nothing, just log the problem and continue without errors. Defencive
		}
		
		return(true);
	}

	/**
	 * Recalculate order tax
	 * @param oldTax true if the old C_Tax_ID should be used
	 * @return true if success, false otherwise
	 * 
	 * @author teo_sarca [ 1583825 ]
	 */
	private boolean updateOrderTax(boolean oldTax) {
		MOrderTax tax = MOrderTax.get (this, getPrecision(), oldTax, get_TrxName());
		if (tax != null) {
			if (!tax.calculateTaxFromLines())
				return false;
			if (tax.getTaxAmt().signum() != 0) {
				if (!tax.save(get_TrxName()))
					return false;
			}
			else {
				if (!tax.is_new() && !tax.delete(false, get_TrxName()))
					return false;
			}
		}
		return true;
	}
	
	/**
	 *	Update Tax & Header
	 *	@return true if header updated
	 */
	private boolean updateHeaderTax()
	{
		//	Recalculate Tax for this Tax
		if (!getParent().isProcessed())
			if (!updateOrderTax(false))
				return false;
		
		//	Update Order Header
		String sql = "UPDATE C_Order i"
			+ " SET TotalLines="
				+ "(SELECT COALESCE(SUM(LineNetAmt),0) FROM C_OrderLine il WHERE i.C_Order_ID=il.C_Order_ID) "
			+ "WHERE C_Order_ID=" + getC_Order_ID();
		int no = DB.executeUpdate(sql, get_TrxName());
		if (no != 1)
			log.warning("(1) #" + no);

		if (isTaxIncluded())
			sql = "UPDATE C_Order i "
				+ " SET GrandTotal=TotalLines "
				+ "WHERE C_Order_ID=" + getC_Order_ID();
		else
			sql = "UPDATE C_Order i "
				+ " SET GrandTotal=TotalLines+"
					+ "(SELECT COALESCE(SUM(TaxAmt),0) FROM C_OrderTax it WHERE i.C_Order_ID=it.C_Order_ID) "
					+ "WHERE C_Order_ID=" + getC_Order_ID();
		no = DB.executeUpdate(sql, get_TrxName());
		if (no != 1)
			log.warning("(2) #" + no);
		m_parent = null;
		
		//OpenUp. Nicolas Sarlabos 22/12/2012. Se actualizan los montos cuando se utiliza descuento al pie de factura
		if (MSysConfig.getBooleanValue("UY_IS_SOLUTION", false, this.getAD_Client_ID())){

			MOrder hdr = new MOrder(getCtx(),this.getC_Order_ID(),get_TrxName());

			if(hdr.isSOTrx()){

				if(hdr.getDiscount().compareTo(Env.ZERO)>0){

					int StdPrecision = getPrecision();
					BigDecimal discount = hdr.getDiscount().divide(Env.ONEHUNDRED);
					discount = (hdr.getTotalLines().multiply(discount)).setScale(StdPrecision, BigDecimal.ROUND_HALF_UP);
					//aplico descuento global al total de lineas y actualizo
					BigDecimal totalLines = (hdr.getTotalLines().subtract(discount)).setScale(StdPrecision, BigDecimal.ROUND_HALF_UP);
					//DB.executeUpdateEx("UPDATE c_order set totallines=" + totalLines + " WHERE c_order_id=" + hdr.get_ID() , get_TrxName());
					//obtengo nuevo importe de impuesto, aplico y actualizo el TOTAL
					MTax tax = MTax.get (getCtx(), getC_Tax_ID());
					BigDecimal TaxAmt = (tax.calculateTax(totalLines, isTaxIncluded(), getPrecision())).setScale(StdPrecision, BigDecimal.ROUND_HALF_UP);
					BigDecimal grandTotal = (totalLines.add(TaxAmt)).setScale(StdPrecision, BigDecimal.ROUND_HALF_UP);
					DB.executeUpdateEx("UPDATE c_order set grandtotal=" + grandTotal + " WHERE c_order_id=" + hdr.get_ID() , get_TrxName());
					//actualizo valor del impuesto
					DB.executeUpdateEx("UPDATE c_ordertax set taxamt=" + TaxAmt + " ,taxbaseamt=" + totalLines + " WHERE c_order_id=" + hdr.get_ID() , get_TrxName());

				}

			}
		}
		//Fin OpenUp.		
		
		return no == 1;
	}	//	updateHeaderTax

	/**
	 * OpenUp.	
	 * Descripcion : Setea valores de linea de orden cuando viene de carga masiva por formato.
	 * @author  Gabriel Vila 
	 * Fecha : 03/01/2011
	 */
	public void setFormatInfo(BigDecimal porcentajeManualAPromocion){
		
		MOrder orderHdr = new MOrder(getCtx(),this.getC_Order_ID(),get_TrxName());
		if (orderHdr.get_ID() <= 0) orderHdr = new MOrder(getCtx(),this.getC_Order_ID(), null);
		
		int C_UOM_To_ID = this.getC_UOM_ID();
		int M_Product_ID = this.getM_Product_ID();
		int M_PriceList_ID = orderHdr.getM_PriceList_ID();
		int StdPrecision = MPriceList.getStandardPrecision(getCtx(), M_PriceList_ID);
		int C_BPartner_ID = orderHdr.getC_BPartner_ID();
		BigDecimal flatDiscount = this.getDiscount(); //OpenUp. Nicolas Sarlabos. 06/03/2013. #475
		BigDecimal Discount = this.getDiscount(); 
		boolean IsSOTrx = true;
				
		BigDecimal QtyOrdered, PriceEntered, PriceActual;
		QtyOrdered = this.getQtyOrdered();
		
		// OpenUp. Gabriel Vila. 15/10/2012. Issue #
		// Me aseguro que los precios tengan redondeo segun moneda de la lista
		this.setPriceEntered(this.getPriceEntered().setScale(StdPrecision, RoundingMode.HALF_UP));
		this.setPriceActual(this.getPriceActual().setScale(StdPrecision, RoundingMode.HALF_UP));
		// Fin OpenUp
		
		PriceEntered = this.getPriceEntered();
		PriceActual = this.getPriceActual();
		
		MProductPricing pp = new MProductPricing (M_Product_ID, C_BPartner_ID, QtyOrdered, IsSOTrx);
		pp.setM_PriceList_ID(M_PriceList_ID);
		pp.setPriceDate(orderHdr.getDateOrdered());

		//OpenUp. Nicolas Sarlabos. 16/10/2012. Issue #
		if (MSysConfig.getBooleanValue("UY_IS_SOLUTION", false, this.getAD_Client_ID())){
			//comento codigo 
			/*PriceActual = pp.getPriceStd().setScale(StdPrecision, RoundingMode.HALF_UP);
			PriceEntered = MUOMConversion.convertProductFrom (getCtx(), M_Product_ID, C_UOM_To_ID, PriceActual);*/
			//Fin OpenUp
					
			if (PriceEntered == null)
				PriceEntered = PriceActual;
			
			//OpenUp #867 Gabriel Vila 13/09/2011
			if (orderHdr.isSOTrx()){
				
				PriceEntered = MUOMConversion.convertProductFrom (getCtx(), M_Product_ID, C_UOM_To_ID, this.getPriceList());
				PriceActual = this.getPriceList();
			
				this.setPriceActual(this.getPriceList().setScale(StdPrecision, RoundingMode.HALF_UP));
				this.setPriceList(this.getPriceList().setScale(StdPrecision, RoundingMode.HALF_UP));
				this.setPriceLimit(this.getPriceList().setScale(StdPrecision, RoundingMode.HALF_UP));
				this.setPriceEntered(PriceEntered);
				this.setDiscount(Discount);
				this.setFlatDiscount(flatDiscount);	
	
				//Fin OpenUp
			}
			else{
				if (PriceActual.compareTo(this.getPriceActual()) != 0){
					PriceActual = this.getPriceActual();
				}
			}
			//OpenUp. Nicolas Sarlabos. 21/10/2012.
			//si la UM es caja entonces se redondea el neto de linea sin decimales
			MUOM uom = new MUOM(getCtx(),this.getC_UOM_ID(),get_TrxName());
			if(uom!=null){
				if(uom.getX12DE355().equalsIgnoreCase("CX")) StdPrecision=0;
			}
					
		}
		else{
			PriceActual = pp.getPriceStd();
			PriceEntered = MUOMConversion.convertProductFrom (getCtx(), M_Product_ID, C_UOM_To_ID, PriceActual);
			if (PriceEntered == null)
				PriceEntered = PriceActual;
			
			//OpenUp #867 Gabriel Vila 13/09/2011
			if (orderHdr.isSOTrx()){
				this.setPriceList(pp.getPriceList());
				this.setPriceLimit(pp.getPriceLimit());
				this.setPriceActual(PriceActual);
				this.setPriceEntered(PriceEntered);
				this.setDiscount(pp.getDiscount());
				this.setFlatDiscount(this.getDiscount());
			}
			else{
				if (PriceActual.compareTo(this.getPriceActual()) != 0){
					PriceActual = this.getPriceActual();
				}
			}
		}

		BigDecimal LineNetAmt = QtyOrdered.multiply(PriceActual);
		if (LineNetAmt.scale() > StdPrecision)
			LineNetAmt = LineNetAmt.setScale(StdPrecision, BigDecimal.ROUND_HALF_UP);
		this.setLineNetAmt(LineNetAmt);
		
		//OpenUp. Nicolas Sarlabos. 19/12/2012. Recalculo precios segun porcentaje de descuento
		if (MSysConfig.getBooleanValue("UY_IS_SOLUTION", false, this.getAD_Client_ID())){
			
			if (orderHdr.isSOTrx()){

				if(flatDiscount.compareTo(Env.ZERO)>0){

					BigDecimal discount = flatDiscount.divide(Env.ONEHUNDRED);
					BigDecimal amtDiscount1 = this.getLineNetAmt().multiply(discount);

					this.setLineNetAmt((this.getLineNetAmt().subtract(amtDiscount1)).setScale(StdPrecision, BigDecimal.ROUND_HALF_UP));

					BigDecimal amtDiscount2 = this.getPriceEntered().multiply(discount);
					this.setPriceEntered((PriceEntered.subtract(amtDiscount2)).setScale(StdPrecision, BigDecimal.ROUND_HALF_UP));

					BigDecimal amtDiscount3 = this.getPriceActual().multiply(discount);
					this.setPriceActual(PriceActual.subtract(amtDiscount3));

				}	
			}
		}
		//Fin OpenUp.
		// Promociones
		if (LineNetAmt.compareTo(Env.ZERO)<=0) return;

		// Obtengo factor de conversion de UM-UV
		BigDecimal factorUnidadMedida = Env.ONE;
		if (this.getC_UOM_ID() != 100) factorUnidadMedida = MUOMConversion.getProductRateFrom(getCtx(), this.getM_Product_ID(), this.getC_UOM_ID());
		
		MPromotionReward reward = null;
		try {
			reward = MPromotion.getReward(orderHdr.getC_BPartner_ID(), this.getM_Product_ID(), this.getQtyEntered().multiply(factorUnidadMedida), orderHdr.getDateOrdered(), LineNetAmt);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.set_Value("uy_bonificaregla", Env.ZERO);

		// Si tengo promocion
		if (reward != null){
			
			// Si el porcentaje de promocion es positivo
			if (reward.getAmount().compareTo(Env.ZERO)>0){

				BigDecimal porcPromoAux = reward.getAmount();
				// Si me viene un porcentaje manual a aplicar en vez de la promocion
				if (porcentajeManualAPromocion != null){
					// Si ese porcentaje manual no supera el porcentaje de promocio, me sirve
					if (porcentajeManualAPromocion.compareTo(porcPromoAux)<=0 && porcentajeManualAPromocion.compareTo(BigDecimal.ZERO)>=0){
						porcPromoAux = porcentajeManualAPromocion;		
					}
				}

				// Si finalmente tengo un porcentaje de descuento 
				if (porcPromoAux.compareTo(BigDecimal.ZERO)>0){

					this.setDiscount(this.getDiscount().add(porcPromoAux));
					this.setuy_promodiscountmax(porcPromoAux);
					this.setuy_promodiscount(porcPromoAux);
					
					// Valido que el valor no supere al maximo, si es asi, se deja el antiguo valor 
					BigDecimal valorNuevo = (BigDecimal)this.get_Value("uy_promodiscount");
					BigDecimal descPlano = (BigDecimal)this.get_Value("flatdiscount");
					BigDecimal valorDesc = descPlano;
					BigDecimal onehundred = new BigDecimal(100);
					BigDecimal multiplier = (onehundred).subtract(valorDesc);
					multiplier = multiplier.divide(onehundred, 6, BigDecimal.ROUND_HALF_UP);
					
					// Aplico descuento promo
					valorDesc = valorNuevo;
					multiplier = (onehundred).subtract(valorDesc);
					multiplier = multiplier.divide(onehundred, 6, BigDecimal.ROUND_HALF_UP);
					PriceActual = this.getPriceActual().multiply(multiplier); 
					PriceEntered = MUOMConversion.convertProductFrom (getCtx(), M_Product_ID, C_UOM_To_ID, PriceActual);
					if (PriceEntered == null)
						PriceEntered = PriceActual;
					this.setPriceActual(PriceActual);
					this.setPriceEntered(PriceEntered);
					
					//	Line Net Amt
					LineNetAmt = QtyOrdered.multiply(PriceActual);
					if (LineNetAmt.scale() > StdPrecision)
						LineNetAmt = LineNetAmt.setScale(StdPrecision, BigDecimal.ROUND_HALF_UP);
					this.setLineNetAmt(LineNetAmt);
				}
			}
			
			
		}
		// OpenUp. Nicolas Garcia. 19/10/2011. Issue #821.

		// Caso Bonificaciones.
		// Si la linea no fue creada como bonificacion cruzada
		if (!this.isUY_EsBonificCruzada()) {
			MPromotion.generarPromoSimpleYCruzada(orderHdr.getC_BPartner_ID(), this);
		}
		// Fin Issue #821

	}

	/**
	 * OpenUp.	
	 * Descripcion : Retorna mensaje de error en validacion controlada.
	 * @return
	 * @author  Gabriel Vila 
	 * Fecha : 03/03/2011
	 */
	public String getProcessMsg() {
		return m_processMsg;
	} // getProcessMsg
	
	/**
	 * 
	 * OpenUp.	ISSUE #682
	 * Descripcion :Metodo que borra la linea y revierte las reservas.
	 * @author  Nicolas Garcia 
	 * Fecha : 14/06/2011 21/09/2011
	 */
	public void cancelarLinea() {
		//BigDecimal cantPedida = this.getQtyOrdered();
		BigDecimal cantPendiente = this.getQtyOrdered().subtract(this.getQtyReserved()).subtract(this.getQtyInvoiced());
		this.addDescription("***** Pendiente Cancelado por : " + cantPendiente);
		
		// Bajo el aprobado
		String msg=this.bajarPendiente(cantPendiente);
		if(msg!=null){
			throw new AdempiereException(msg);
		}

		// Dejo cantidad reservada = cantidad ordenada para que no aparezca nunca mas para reserva
		this.setQtyReserved(this.getQtyOrdered());

		// Guardo cantidad pendiente actual a cancelar
		this.set_Value("uy_cantcancelada", cantPendiente);

		// OpenUp. Gabriel Vila. 07/04/2011. Issue #115.
		// Al anular orden de venta me aseguro que no quede asociacion con posible Orden de Proceso
		this.setPP_Order_ID(0);
		// Fin OpenUp
						
		this.save(get_TrxName());
		
	}

	/**
	 * 
	 * OpenUp. ISSUE #682	
	 * Descripcion :Metodo que baja el pendiente
	 * @param cantBajar (Positiva)
	 * @return
	 * @author  Nicolas Garcia
	 * Fecha : 20/09/2011
	 */
	private String bajarPendiente(BigDecimal cantBajar) {

		String salida = null;

		Timestamp ahora = new Timestamp(System.currentTimeMillis());
		int stockstatusID = MStockStatus.getStatusPendingID(null);

		salida = MStockTransaction.add(((MOrder) this.getC_Order()), ((MOrder) this.getC_Order()), this.getM_Warehouse_ID(), 0, 
				this.getM_Product_ID(), this.getM_AttributeSetInstance_ID(), stockstatusID,
				ahora, cantBajar.multiply(new BigDecimal(-1)), 0, null);

		return salida;

	}
	
	/**
	 * 
	 * OpenUp.	
	 * Descripcion :Issue #821 Funcion que devuelve para una orderline sus lineas bonificadas
	 * @param whereClause
	 * @param orderClause
	 * @return
	 * @author  Nicolas Garcia
	 * Fecha : 19/10/2011
	 */
	public MOrderLine[] getLinesBonificacionCruzada() {

		ArrayList<MOrderLine> list = new ArrayList<MOrderLine>();

		String sql = "SELECT * FROM c_orderline WHERE uy_esbonificcruzada='Y' AND c_order_id=" + this.getC_Order_ID() + " AND uy_lineapadre_id="
				+ this.get_ID();
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try {
			pstmt = DB.prepareStatement(sql, get_TrxName());
			rs = pstmt.executeQuery();

			while (rs.next()) {

				list.add(new MOrderLine(getCtx(), rs, get_TrxName()));
			}

		} catch (Exception e) {
			log.log(Level.SEVERE, sql, e);
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		return list.toArray(new MOrderLine[list.size()]);
	}

}	//	MOrderLine
