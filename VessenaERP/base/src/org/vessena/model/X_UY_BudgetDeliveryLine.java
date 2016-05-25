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

/** Generated Model for UY_BudgetDeliveryLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS - $Id$ */
public class X_UY_BudgetDeliveryLine extends PO implements I_UY_BudgetDeliveryLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130606L;

    /** Standard Constructor */
    public X_UY_BudgetDeliveryLine (Properties ctx, int UY_BudgetDeliveryLine_ID, String trxName)
    {
      super (ctx, UY_BudgetDeliveryLine_ID, trxName);
      /** if (UY_BudgetDeliveryLine_ID == 0)
        {
			setQty (Env.ZERO);
			setQtyDelivered (Env.ZERO);
			setQtyToDeliver (Env.ZERO);
			setUY_BudgetDelivery_ID (0);
			setUY_BudgetDeliveryLine_ID (0);
			setUY_BudgetLine_ID (0);
			setUY_ManufLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_BudgetDeliveryLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_BudgetDeliveryLine[")
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

	/** design AD_Reference_ID=1000188 */
	public static final int DESIGN_AD_Reference_ID=1000188;
	/** DISEÃ‘O 1 = D1 */
	public static final String DESIGN_DISEÑO1 = "D1";
	/** DISEÃ‘O 2 = D2 */
	public static final String DESIGN_DISEÑO2 = "D2";
	/** DISEÃ‘O 3 = D3 */
	public static final String DESIGN_DISEÑO3 = "D3";
	/** Set design.
		@param design design	  */
	public void setdesign (String design)
	{

		set_Value (COLUMNNAME_design, design);
	}

	/** Get design.
		@return design	  */
	public String getdesign () 
	{
		return (String)get_Value(COLUMNNAME_design);
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

	/** Set Qty to deliver.
		@param QtyToDeliver Qty to deliver	  */
	public void setQtyToDeliver (BigDecimal QtyToDeliver)
	{
		set_Value (COLUMNNAME_QtyToDeliver, QtyToDeliver);
	}

	/** Get Qty to deliver.
		@return Qty to deliver	  */
	public BigDecimal getQtyToDeliver () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyToDeliver);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public I_UY_BudgetDelivery getUY_BudgetDelivery() throws RuntimeException
    {
		return (I_UY_BudgetDelivery)MTable.get(getCtx(), I_UY_BudgetDelivery.Table_Name)
			.getPO(getUY_BudgetDelivery_ID(), get_TrxName());	}

	/** Set UY_BudgetDelivery.
		@param UY_BudgetDelivery_ID UY_BudgetDelivery	  */
	public void setUY_BudgetDelivery_ID (int UY_BudgetDelivery_ID)
	{
		if (UY_BudgetDelivery_ID < 1) 
			set_Value (COLUMNNAME_UY_BudgetDelivery_ID, null);
		else 
			set_Value (COLUMNNAME_UY_BudgetDelivery_ID, Integer.valueOf(UY_BudgetDelivery_ID));
	}

	/** Get UY_BudgetDelivery.
		@return UY_BudgetDelivery	  */
	public int getUY_BudgetDelivery_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_BudgetDelivery_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_BudgetDeliveryLine.
		@param UY_BudgetDeliveryLine_ID UY_BudgetDeliveryLine	  */
	public void setUY_BudgetDeliveryLine_ID (int UY_BudgetDeliveryLine_ID)
	{
		if (UY_BudgetDeliveryLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_BudgetDeliveryLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_BudgetDeliveryLine_ID, Integer.valueOf(UY_BudgetDeliveryLine_ID));
	}

	/** Get UY_BudgetDeliveryLine.
		@return UY_BudgetDeliveryLine	  */
	public int getUY_BudgetDeliveryLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_BudgetDeliveryLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	public I_UY_ManufLine getUY_ManufLine() throws RuntimeException
    {
		return (I_UY_ManufLine)MTable.get(getCtx(), I_UY_ManufLine.Table_Name)
			.getPO(getUY_ManufLine_ID(), get_TrxName());	}

	/** Set UY_ManufLine.
		@param UY_ManufLine_ID UY_ManufLine	  */
	public void setUY_ManufLine_ID (int UY_ManufLine_ID)
	{
		if (UY_ManufLine_ID < 1) 
			set_Value (COLUMNNAME_UY_ManufLine_ID, null);
		else 
			set_Value (COLUMNNAME_UY_ManufLine_ID, Integer.valueOf(UY_ManufLine_ID));
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
}