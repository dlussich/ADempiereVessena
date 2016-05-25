/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2007 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package org.openup.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;

/** Generated Model for UY_MB_OrderLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_MB_OrderLine extends PO implements I_UY_MB_OrderLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140902L;

    /** Standard Constructor */
    public X_UY_MB_OrderLine (Properties ctx, int UY_MB_OrderLine_ID, String trxName)
    {
      super (ctx, UY_MB_OrderLine_ID, trxName);
      /** if (UY_MB_OrderLine_ID == 0)
        {
			setC_Tax_ID (0);
			setC_UOM_ID (0);
			setLineNetAmt (Env.ZERO);
			setPriceActual (Env.ZERO);
			setPriceEntered (Env.ZERO);
			setPriceList (Env.ZERO);
			setQtyEntered (Env.ZERO);
// 1
			setUY_MB_Order_ID (0);
			setUY_MB_OrderLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_MB_OrderLine (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 7 - System - Client - Org 
      */
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    protected POInfo initPO (Properties ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    public String toString()
    {
      StringBuffer sb = new StringBuffer ("X_UY_MB_OrderLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_C_Tax getC_Tax() throws RuntimeException
    {
		return (org.compiere.model.I_C_Tax)MTable.get(getCtx(), org.compiere.model.I_C_Tax.Table_Name)
			.getPO(getC_Tax_ID(), get_TrxName());	}

	/** Set Tax.
		@param C_Tax_ID 
		Tax identifier
	  */
	public void setC_Tax_ID (int C_Tax_ID)
	{
		if (C_Tax_ID < 1) 
			set_Value (COLUMNNAME_C_Tax_ID, null);
		else 
			set_Value (COLUMNNAME_C_Tax_ID, Integer.valueOf(C_Tax_ID));
	}

	/** Get Tax.
		@return Tax identifier
	  */
	public int getC_Tax_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Tax_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_UOM getC_UOM() throws RuntimeException
    {
		return (org.compiere.model.I_C_UOM)MTable.get(getCtx(), org.compiere.model.I_C_UOM.Table_Name)
			.getPO(getC_UOM_ID(), get_TrxName());	}

	/** Set UOM.
		@param C_UOM_ID 
		Unit of Measure
	  */
	public void setC_UOM_ID (int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_ID, Integer.valueOf(C_UOM_ID));
	}

	/** Get UOM.
		@return Unit of Measure
	  */
	public int getC_UOM_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_UOM_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Discount %.
		@param Discount 
		Discount in percent
	  */
	public void setDiscount (BigDecimal Discount)
	{
		set_Value (COLUMNNAME_Discount, Discount);
	}

	/** Get Discount %.
		@return Discount in percent
	  */
	public BigDecimal getDiscount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Discount);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Flat Discount %.
		@param FlatDiscount 
		Flat discount percentage 
	  */
	public void setFlatDiscount (BigDecimal FlatDiscount)
	{
		set_Value (COLUMNNAME_FlatDiscount, FlatDiscount);
	}

	/** Get Flat Discount %.
		@return Flat discount percentage 
	  */
	public BigDecimal getFlatDiscount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FlatDiscount);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set IsSynced.
		@param IsSynced IsSynced	  */
	public void setIsSynced (boolean IsSynced)
	{
		set_Value (COLUMNNAME_IsSynced, Boolean.valueOf(IsSynced));
	}

	/** Get IsSynced.
		@return IsSynced	  */
	public boolean isSynced () 
	{
		Object oo = get_Value(COLUMNNAME_IsSynced);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Line Amount.
		@param LineNetAmt 
		Line Extended Amount (Quantity * Actual Price) without Freight and Charges
	  */
	public void setLineNetAmt (BigDecimal LineNetAmt)
	{
		set_Value (COLUMNNAME_LineNetAmt, LineNetAmt);
	}

	/** Get Line Amount.
		@return Line Extended Amount (Quantity * Actual Price) without Freight and Charges
	  */
	public BigDecimal getLineNetAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_LineNetAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException
    {
		return (org.compiere.model.I_M_Product)MTable.get(getCtx(), org.compiere.model.I_M_Product.Table_Name)
			.getPO(getM_Product_ID(), get_TrxName());	}

	/** Set Product.
		@param M_Product_ID 
		Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	/** Get Product.
		@return Product, Service, Item
	  */
	public int getM_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Unit Price.
		@param PriceActual 
		Actual Price 
	  */
	public void setPriceActual (BigDecimal PriceActual)
	{
		set_Value (COLUMNNAME_PriceActual, PriceActual);
	}

	/** Get Unit Price.
		@return Actual Price 
	  */
	public BigDecimal getPriceActual () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceActual);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Price.
		@param PriceEntered 
		Price Entered - the price based on the selected/base UoM
	  */
	public void setPriceEntered (BigDecimal PriceEntered)
	{
		set_Value (COLUMNNAME_PriceEntered, PriceEntered);
	}

	/** Get Price.
		@return Price Entered - the price based on the selected/base UoM
	  */
	public BigDecimal getPriceEntered () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceEntered);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set List Price.
		@param PriceList 
		List Price
	  */
	public void setPriceList (BigDecimal PriceList)
	{
		set_Value (COLUMNNAME_PriceList, PriceList);
	}

	/** Get List Price.
		@return List Price
	  */
	public BigDecimal getPriceList () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceList);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set qtydesign1.
		@param qtydesign1 qtydesign1	  */
	public void setqtydesign1 (BigDecimal qtydesign1)
	{
		set_Value (COLUMNNAME_qtydesign1, qtydesign1);
	}

	/** Get qtydesign1.
		@return qtydesign1	  */
	public BigDecimal getqtydesign1 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_qtydesign1);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set qtydesign2.
		@param qtydesign2 qtydesign2	  */
	public void setqtydesign2 (BigDecimal qtydesign2)
	{
		set_Value (COLUMNNAME_qtydesign2, qtydesign2);
	}

	/** Get qtydesign2.
		@return qtydesign2	  */
	public BigDecimal getqtydesign2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_qtydesign2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set qtydesign3.
		@param qtydesign3 qtydesign3	  */
	public void setqtydesign3 (BigDecimal qtydesign3)
	{
		set_Value (COLUMNNAME_qtydesign3, qtydesign3);
	}

	/** Get qtydesign3.
		@return qtydesign3	  */
	public BigDecimal getqtydesign3 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_qtydesign3);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Quantity.
		@param QtyEntered 
		The Quantity Entered is based on the selected UoM
	  */
	public void setQtyEntered (BigDecimal QtyEntered)
	{
		set_Value (COLUMNNAME_QtyEntered, QtyEntered);
	}

	/** Get Quantity.
		@return The Quantity Entered is based on the selected UoM
	  */
	public BigDecimal getQtyEntered () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyEntered);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public I_UY_MB_Order getUY_MB_Order() throws RuntimeException
    {
		return (I_UY_MB_Order)MTable.get(getCtx(), I_UY_MB_Order.Table_Name)
			.getPO(getUY_MB_Order_ID(), get_TrxName());	}

	/** Set UY_MB_Order ID.
		@param UY_MB_Order_ID UY_MB_Order ID	  */
	public void setUY_MB_Order_ID (int UY_MB_Order_ID)
	{
		if (UY_MB_Order_ID < 1) 
			set_Value (COLUMNNAME_UY_MB_Order_ID, null);
		else 
			set_Value (COLUMNNAME_UY_MB_Order_ID, Integer.valueOf(UY_MB_Order_ID));
	}

	/** Get UY_MB_Order ID.
		@return UY_MB_Order ID	  */
	public int getUY_MB_Order_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_MB_Order_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_MB_OrderLine ID.
		@param UY_MB_OrderLine_ID UY_MB_OrderLine ID	  */
	public void setUY_MB_OrderLine_ID (int UY_MB_OrderLine_ID)
	{
		if (UY_MB_OrderLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_MB_OrderLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_MB_OrderLine_ID, Integer.valueOf(UY_MB_OrderLine_ID));
	}

	/** Get UY_MB_OrderLine ID.
		@return UY_MB_OrderLine ID	  */
	public int getUY_MB_OrderLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_MB_OrderLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set uy_promodiscount.
		@param uy_promodiscount uy_promodiscount	  */
	public void setuy_promodiscount (BigDecimal uy_promodiscount)
	{
		set_Value (COLUMNNAME_uy_promodiscount, uy_promodiscount);
	}

	/** Get uy_promodiscount.
		@return uy_promodiscount	  */
	public BigDecimal getuy_promodiscount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_uy_promodiscount);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}