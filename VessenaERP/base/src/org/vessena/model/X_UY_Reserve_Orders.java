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

/** Generated Model for UY_Reserve_Orders
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_UY_Reserve_Orders extends PO implements I_UY_Reserve_Orders, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_UY_Reserve_Orders (Properties ctx, int UY_Reserve_Orders_ID, String trxName)
    {
      super (ctx, UY_Reserve_Orders_ID, trxName);
      /** if (UY_Reserve_Orders_ID == 0)
        {
			setDateOrdered (new Timestamp( System.currentTimeMillis() ));
			setGrandTotal (Env.ZERO);
			setUY_Reserve_Orders_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_Reserve_Orders (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_Reserve_Orders[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_C_BPartner getC_BPartner() throws RuntimeException
    {
		return (I_C_BPartner)MTable.get(getCtx(), I_C_BPartner.Table_Name)
			.getPO(getC_BPartner_ID(), get_TrxName());	}

	/** Set Business Partner .
		@param C_BPartner_ID 
		Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Business Partner .
		@return Identifies a Business Partner
	  */
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_BPartner_Location getC_BPartner_Location() throws RuntimeException
    {
		return (I_C_BPartner_Location)MTable.get(getCtx(), I_C_BPartner_Location.Table_Name)
			.getPO(getC_BPartner_Location_ID(), get_TrxName());	}

	/** Set Partner Location.
		@param C_BPartner_Location_ID 
		Identifies the (ship to) address for this Business Partner
	  */
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID)
	{
		if (C_BPartner_Location_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, Integer.valueOf(C_BPartner_Location_ID));
	}

	/** Get Partner Location.
		@return Identifies the (ship to) address for this Business Partner
	  */
	public int getC_BPartner_Location_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_Location_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_Order getC_Order() throws RuntimeException
    {
		return (I_C_Order)MTable.get(getCtx(), I_C_Order.Table_Name)
			.getPO(getC_Order_ID(), get_TrxName());	}

	/** Set Order.
		@param C_Order_ID 
		Order
	  */
	public void setC_Order_ID (int C_Order_ID)
	{
		if (C_Order_ID < 1) 
			set_Value (COLUMNNAME_C_Order_ID, null);
		else 
			set_Value (COLUMNNAME_C_Order_ID, Integer.valueOf(C_Order_ID));
	}

	/** Get Order.
		@return Order
	  */
	public int getC_Order_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Order_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Date Ordered.
		@param DateOrdered 
		Date of Order
	  */
	public void setDateOrdered (Timestamp DateOrdered)
	{
		set_Value (COLUMNNAME_DateOrdered, DateOrdered);
	}

	/** Get Date Ordered.
		@return Date of Order
	  */
	public Timestamp getDateOrdered () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateOrdered);
	}

	/** Set Grand Total.
		@param GrandTotal 
		Total amount of document
	  */
	public void setGrandTotal (BigDecimal GrandTotal)
	{
		set_Value (COLUMNNAME_GrandTotal, GrandTotal);
	}

	/** Get Grand Total.
		@return Total amount of document
	  */
	public BigDecimal getGrandTotal () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_GrandTotal);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set uy_cantbultos.
		@param uy_cantbultos uy_cantbultos	  */
	public void setuy_cantbultos (BigDecimal uy_cantbultos)
	{
		set_Value (COLUMNNAME_uy_cantbultos, uy_cantbultos);
	}

	/** Get uy_cantbultos.
		@return uy_cantbultos	  */
	public BigDecimal getuy_cantbultos () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_uy_cantbultos);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public I_UY_Reserve_Filter getUY_Reserve_Filter() throws RuntimeException
    {
		return (I_UY_Reserve_Filter)MTable.get(getCtx(), I_UY_Reserve_Filter.Table_Name)
			.getPO(getUY_Reserve_Filter_ID(), get_TrxName());	}

	/** Set UY_Reserve_Filter.
		@param UY_Reserve_Filter_ID UY_Reserve_Filter	  */
	public void setUY_Reserve_Filter_ID (int UY_Reserve_Filter_ID)
	{
		if (UY_Reserve_Filter_ID < 1) 
			set_Value (COLUMNNAME_UY_Reserve_Filter_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Reserve_Filter_ID, Integer.valueOf(UY_Reserve_Filter_ID));
	}

	/** Get UY_Reserve_Filter.
		@return UY_Reserve_Filter	  */
	public int getUY_Reserve_Filter_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Reserve_Filter_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_Reserve_Orders.
		@param UY_Reserve_Orders_ID UY_Reserve_Orders	  */
	public void setUY_Reserve_Orders_ID (int UY_Reserve_Orders_ID)
	{
		if (UY_Reserve_Orders_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_Reserve_Orders_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_Reserve_Orders_ID, Integer.valueOf(UY_Reserve_Orders_ID));
	}

	/** Get UY_Reserve_Orders.
		@return UY_Reserve_Orders	  */
	public int getUY_Reserve_Orders_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Reserve_Orders_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set uy_verpedidos.
		@param uy_verpedidos uy_verpedidos	  */
	public void setuy_verpedidos (String uy_verpedidos)
	{
		set_Value (COLUMNNAME_uy_verpedidos, uy_verpedidos);
	}

	/** Get uy_verpedidos.
		@return uy_verpedidos	  */
	public String getuy_verpedidos () 
	{
		return (String)get_Value(COLUMNNAME_uy_verpedidos);
	}

	/** Set UY_ZonaReparto.
		@param UY_ZonaReparto_ID UY_ZonaReparto	  */
	public void setUY_ZonaReparto_ID (int UY_ZonaReparto_ID)
	{
		if (UY_ZonaReparto_ID < 1) 
			set_Value (COLUMNNAME_UY_ZonaReparto_ID, null);
		else 
			set_Value (COLUMNNAME_UY_ZonaReparto_ID, Integer.valueOf(UY_ZonaReparto_ID));
	}

	/** Get UY_ZonaReparto.
		@return UY_ZonaReparto	  */
	public int getUY_ZonaReparto_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_ZonaReparto_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}