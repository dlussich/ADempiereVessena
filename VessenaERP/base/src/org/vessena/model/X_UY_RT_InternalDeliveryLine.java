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

/** Generated Model for UY_RT_InternalDeliveryLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_RT_InternalDeliveryLine extends PO implements I_UY_RT_InternalDeliveryLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20160201L;

    /** Standard Constructor */
    public X_UY_RT_InternalDeliveryLine (Properties ctx, int UY_RT_InternalDeliveryLine_ID, String trxName)
    {
      super (ctx, UY_RT_InternalDeliveryLine_ID, trxName);
      /** if (UY_RT_InternalDeliveryLine_ID == 0)
        {
			setUY_RT_InternalDelivery_ID (0);
			setUY_RT_InternalDeliveryLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_RT_InternalDeliveryLine (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 3 - Client - Org 
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
      StringBuffer sb = new StringBuffer ("X_UY_RT_InternalDeliveryLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_C_OrderLine getC_OrderLine() throws RuntimeException
    {
		return (org.compiere.model.I_C_OrderLine)MTable.get(getCtx(), org.compiere.model.I_C_OrderLine.Table_Name)
			.getPO(getC_OrderLine_ID(), get_TrxName());	}

	/** Set Sales Order Line.
		@param C_OrderLine_ID 
		Sales Order Line
	  */
	public void setC_OrderLine_ID (int C_OrderLine_ID)
	{
		if (C_OrderLine_ID < 1) 
			set_Value (COLUMNNAME_C_OrderLine_ID, null);
		else 
			set_Value (COLUMNNAME_C_OrderLine_ID, Integer.valueOf(C_OrderLine_ID));
	}

	/** Get Sales Order Line.
		@return Sales Order Line
	  */
	public int getC_OrderLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_OrderLine_ID);
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

	/** Set Description.
		@param Description 
		Optional short description of the record
	  */
	public void setDescription (String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Description.
		@return Optional short description of the record
	  */
	public String getDescription () 
	{
		return (String)get_Value(COLUMNNAME_Description);
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

	/** Set Line Total.
		@param LineTotalAmt 
		Total line amount incl. Tax
	  */
	public void setLineTotalAmt (BigDecimal LineTotalAmt)
	{
		set_Value (COLUMNNAME_LineTotalAmt, LineTotalAmt);
	}

	/** Get Line Total.
		@return Total line amount incl. Tax
	  */
	public BigDecimal getLineTotalAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_LineTotalAmt);
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

	/** Set qtyallocated.
		@param qtyallocated qtyallocated	  */
	public void setqtyallocated (BigDecimal qtyallocated)
	{
		set_Value (COLUMNNAME_qtyallocated, qtyallocated);
	}

	/** Get qtyallocated.
		@return qtyallocated	  */
	public BigDecimal getqtyallocated () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_qtyallocated);
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

	/** Set qtyopen.
		@param qtyopen qtyopen	  */
	public void setqtyopen (BigDecimal qtyopen)
	{
		set_Value (COLUMNNAME_qtyopen, qtyopen);
	}

	/** Get qtyopen.
		@return qtyopen	  */
	public BigDecimal getqtyopen () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_qtyopen);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public I_UY_RT_InternalDelivery getUY_RT_InternalDelivery() throws RuntimeException
    {
		return (I_UY_RT_InternalDelivery)MTable.get(getCtx(), I_UY_RT_InternalDelivery.Table_Name)
			.getPO(getUY_RT_InternalDelivery_ID(), get_TrxName());	}

	/** Set UY_RT_InternalDelivery.
		@param UY_RT_InternalDelivery_ID UY_RT_InternalDelivery	  */
	public void setUY_RT_InternalDelivery_ID (int UY_RT_InternalDelivery_ID)
	{
		if (UY_RT_InternalDelivery_ID < 1) 
			set_Value (COLUMNNAME_UY_RT_InternalDelivery_ID, null);
		else 
			set_Value (COLUMNNAME_UY_RT_InternalDelivery_ID, Integer.valueOf(UY_RT_InternalDelivery_ID));
	}

	/** Get UY_RT_InternalDelivery.
		@return UY_RT_InternalDelivery	  */
	public int getUY_RT_InternalDelivery_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_RT_InternalDelivery_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_RT_InternalDeliveryLine.
		@param UY_RT_InternalDeliveryLine_ID UY_RT_InternalDeliveryLine	  */
	public void setUY_RT_InternalDeliveryLine_ID (int UY_RT_InternalDeliveryLine_ID)
	{
		if (UY_RT_InternalDeliveryLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_RT_InternalDeliveryLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_RT_InternalDeliveryLine_ID, Integer.valueOf(UY_RT_InternalDeliveryLine_ID));
	}

	/** Get UY_RT_InternalDeliveryLine.
		@return UY_RT_InternalDeliveryLine	  */
	public int getUY_RT_InternalDeliveryLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_RT_InternalDeliveryLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}