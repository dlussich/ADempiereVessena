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

/** Generated Model for UY_ManufLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS - $Id$ */
public class X_UY_ManufLine extends PO implements I_UY_ManufLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20121202L;

    /** Standard Constructor */
    public X_UY_ManufLine (Properties ctx, int UY_ManufLine_ID, String trxName)
    {
      super (ctx, UY_ManufLine_ID, trxName);
      /** if (UY_ManufLine_ID == 0)
        {
			setQty (Env.ZERO);
			setQtyDelivered (Env.ZERO);
			setUY_ManufLine_ID (0);
			setUY_ManufOrder_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_ManufLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_ManufLine[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	/** Set IsPending.
		@param IsPending IsPending	  */
	public void setIsPending (boolean IsPending)
	{
		set_Value (COLUMNNAME_IsPending, Boolean.valueOf(IsPending));
	}

	/** Get IsPending.
		@return IsPending	  */
	public boolean isPending () 
	{
		Object oo = get_Value(COLUMNNAME_IsPending);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

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

	/** Set Quantity.
		@param Qty 
		Quantity
	  */
	public void setQty (BigDecimal Qty)
	{
		set_Value (COLUMNNAME_Qty, Qty);
	}

	/** Get Quantity.
		@return Quantity
	  */
	public BigDecimal getQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Qty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Delivered Quantity.
		@param QtyDelivered 
		Delivered Quantity
	  */
	public void setQtyDelivered (BigDecimal QtyDelivered)
	{
		set_Value (COLUMNNAME_QtyDelivered, QtyDelivered);
	}

	/** Get Delivered Quantity.
		@return Delivered Quantity
	  */
	public BigDecimal getQtyDelivered () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyDelivered);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Quantity Invoiced.
		@param QtyInvoiced 
		Invoiced Quantity
	  */
	public void setQtyInvoiced (BigDecimal QtyInvoiced)
	{
		set_Value (COLUMNNAME_QtyInvoiced, QtyInvoiced);
	}

	/** Get Quantity Invoiced.
		@return Invoiced Quantity
	  */
	public BigDecimal getQtyInvoiced () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyInvoiced);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public I_UY_BudgetLine getUY_BudgetLine() throws RuntimeException
    {
		return (I_UY_BudgetLine)MTable.get(getCtx(), I_UY_BudgetLine.Table_Name)
			.getPO(getUY_BudgetLine_ID(), get_TrxName());	}

	/** Set UY_BudgetLine.
		@param UY_BudgetLine_ID UY_BudgetLine	  */
	public void setUY_BudgetLine_ID (int UY_BudgetLine_ID)
	{
		if (UY_BudgetLine_ID < 1) 
			set_Value (COLUMNNAME_UY_BudgetLine_ID, null);
		else 
			set_Value (COLUMNNAME_UY_BudgetLine_ID, Integer.valueOf(UY_BudgetLine_ID));
	}

	/** Get UY_BudgetLine.
		@return UY_BudgetLine	  */
	public int getUY_BudgetLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_BudgetLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_ManufLine.
		@param UY_ManufLine_ID UY_ManufLine	  */
	public void setUY_ManufLine_ID (int UY_ManufLine_ID)
	{
		if (UY_ManufLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_ManufLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_ManufLine_ID, Integer.valueOf(UY_ManufLine_ID));
	}

	/** Get UY_ManufLine.
		@return UY_ManufLine	  */
	public int getUY_ManufLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_ManufLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_ManufOrder getUY_ManufOrder() throws RuntimeException
    {
		return (I_UY_ManufOrder)MTable.get(getCtx(), I_UY_ManufOrder.Table_Name)
			.getPO(getUY_ManufOrder_ID(), get_TrxName());	}

	/** Set UY_ManufOrder.
		@param UY_ManufOrder_ID UY_ManufOrder	  */
	public void setUY_ManufOrder_ID (int UY_ManufOrder_ID)
	{
		if (UY_ManufOrder_ID < 1) 
			set_Value (COLUMNNAME_UY_ManufOrder_ID, null);
		else 
			set_Value (COLUMNNAME_UY_ManufOrder_ID, Integer.valueOf(UY_ManufOrder_ID));
	}

	/** Get UY_ManufOrder.
		@return UY_ManufOrder	  */
	public int getUY_ManufOrder_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_ManufOrder_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}