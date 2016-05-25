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
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;

/** Generated Model for UY_CloseTechnica_Line
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_UY_CloseTechnica_Line extends PO implements I_UY_CloseTechnica_Line, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_UY_CloseTechnica_Line (Properties ctx, int UY_CloseTechnica_Line_ID, String trxName)
    {
      super (ctx, UY_CloseTechnica_Line_ID, trxName);
      /** if (UY_CloseTechnica_Line_ID == 0)
        {
			setPP_Order_ID (0);
			setUY_CloseTechnica_Filter_ID (0);
			setUY_CloseTechnica_Line_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_CloseTechnica_Line (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_CloseTechnica_Line[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Date Ordered.
		@param DateOrdered 
		Date of Order
	  */
	public void setDateOrdered (Timestamp DateOrdered)
	{
		set_ValueNoCheck (COLUMNNAME_DateOrdered, DateOrdered);
	}

	/** Get Date Ordered.
		@return Date of Order
	  */
	public Timestamp getDateOrdered () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateOrdered);
	}

	public I_M_Product getM_Product() throws RuntimeException
    {
		return (I_M_Product)MTable.get(getCtx(), I_M_Product.Table_Name)
			.getPO(getM_Product_ID(), get_TrxName());	}

	/** Set Product.
		@param M_Product_ID 
		Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
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

	public org.eevolution.model.I_PP_Order getPP_Order() throws RuntimeException
    {
		return (org.eevolution.model.I_PP_Order)MTable.get(getCtx(), org.eevolution.model.I_PP_Order.Table_Name)
			.getPO(getPP_Order_ID(), get_TrxName());	}

	/** Set Manufacturing Order.
		@param PP_Order_ID Manufacturing Order	  */
	public void setPP_Order_ID (int PP_Order_ID)
	{
		if (PP_Order_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PP_Order_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PP_Order_ID, Integer.valueOf(PP_Order_ID));
	}

	/** Get Manufacturing Order.
		@return Manufacturing Order	  */
	public int getPP_Order_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PP_Order_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Delivered Quantity.
		@param QtyDelivered 
		Delivered Quantity
	  */
	public void setQtyDelivered (BigDecimal QtyDelivered)
	{
		set_ValueNoCheck (COLUMNNAME_QtyDelivered, QtyDelivered);
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

	/** Set Ordered Quantity.
		@param QtyOrdered 
		Ordered Quantity
	  */
	public void setQtyOrdered (BigDecimal QtyOrdered)
	{
		set_ValueNoCheck (COLUMNNAME_QtyOrdered, QtyOrdered);
	}

	/** Get Ordered Quantity.
		@return Ordered Quantity
	  */
	public BigDecimal getQtyOrdered () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyOrdered);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set UY_CloseTechnica_Filter_ID.
		@param UY_CloseTechnica_Filter_ID UY_CloseTechnica_Filter_ID	  */
	public void setUY_CloseTechnica_Filter_ID (int UY_CloseTechnica_Filter_ID)
	{
		if (UY_CloseTechnica_Filter_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_CloseTechnica_Filter_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_CloseTechnica_Filter_ID, Integer.valueOf(UY_CloseTechnica_Filter_ID));
	}

	/** Get UY_CloseTechnica_Filter_ID.
		@return UY_CloseTechnica_Filter_ID	  */
	public int getUY_CloseTechnica_Filter_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_CloseTechnica_Filter_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_CloseTechnica_Line_ID.
		@param UY_CloseTechnica_Line_ID UY_CloseTechnica_Line_ID	  */
	public void setUY_CloseTechnica_Line_ID (int UY_CloseTechnica_Line_ID)
	{
		if (UY_CloseTechnica_Line_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_CloseTechnica_Line_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_CloseTechnica_Line_ID, Integer.valueOf(UY_CloseTechnica_Line_ID));
	}

	/** Get UY_CloseTechnica_Line_ID.
		@return UY_CloseTechnica_Line_ID	  */
	public int getUY_CloseTechnica_Line_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_CloseTechnica_Line_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_PacksLiteral.
		@param UY_PacksLiteral UY_PacksLiteral	  */
	public void setUY_PacksLiteral (String UY_PacksLiteral)
	{
		set_ValueNoCheck (COLUMNNAME_UY_PacksLiteral, UY_PacksLiteral);
	}

	/** Get UY_PacksLiteral.
		@return UY_PacksLiteral	  */
	public String getUY_PacksLiteral () 
	{
		return (String)get_Value(COLUMNNAME_UY_PacksLiteral);
	}

	/** Set UY_Procesar.
		@param UY_Procesar UY_Procesar	  */
	public void setUY_Procesar (boolean UY_Procesar)
	{
		set_Value (COLUMNNAME_UY_Procesar, Boolean.valueOf(UY_Procesar));
	}

	/** Get UY_Procesar.
		@return UY_Procesar	  */
	public boolean isUY_Procesar () 
	{
		Object oo = get_Value(COLUMNNAME_UY_Procesar);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}
}