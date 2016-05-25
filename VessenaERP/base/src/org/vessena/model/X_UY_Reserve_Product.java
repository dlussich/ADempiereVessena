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

/** Generated Model for UY_Reserve_Product
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_UY_Reserve_Product extends PO implements I_UY_Reserve_Product, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_UY_Reserve_Product (Properties ctx, int UY_Reserve_Product_ID, String trxName)
    {
      super (ctx, UY_Reserve_Product_ID, trxName);
      /** if (UY_Reserve_Product_ID == 0)
        {
			setM_Product_ID (0);
			setQtyEntered (Env.ZERO);
			setQtyOrdered (Env.ZERO);
			setuy_qtyonhand_after (Env.ZERO);
			setUY_QtyOnHand_AfterUM (Env.ZERO);
// 0
			setuy_qtyonhand_before (Env.ZERO);
			setuy_qtypending (Env.ZERO);
			setUY_Reserve_Filter_ID (0);
			setUY_Reserve_Product_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_Reserve_Product (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_Reserve_Product[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	public I_M_Warehouse getM_Warehouse() throws RuntimeException
    {
		return (I_M_Warehouse)MTable.get(getCtx(), I_M_Warehouse.Table_Name)
			.getPO(getM_Warehouse_ID(), get_TrxName());	}

	/** Set Warehouse.
		@param M_Warehouse_ID 
		Storage Warehouse and Service Point
	  */
	public void setM_Warehouse_ID (int M_Warehouse_ID)
	{
		if (M_Warehouse_ID < 1) 
			set_Value (COLUMNNAME_M_Warehouse_ID, null);
		else 
			set_Value (COLUMNNAME_M_Warehouse_ID, Integer.valueOf(M_Warehouse_ID));
	}

	/** Get Warehouse.
		@return Storage Warehouse and Service Point
	  */
	public int getM_Warehouse_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Warehouse_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Processed.
		@param Processed 
		The document has been processed
	  */
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Processed.
		@return The document has been processed
	  */
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set Ordered Quantity.
		@param QtyOrdered 
		Ordered Quantity
	  */
	public void setQtyOrdered (BigDecimal QtyOrdered)
	{
		set_Value (COLUMNNAME_QtyOrdered, QtyOrdered);
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

	/** Set uy_factor.
		@param uy_factor uy_factor	  */
	public void setuy_factor (BigDecimal uy_factor)
	{
		set_Value (COLUMNNAME_uy_factor, uy_factor);
	}

	/** Get uy_factor.
		@return uy_factor	  */
	public BigDecimal getuy_factor () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_uy_factor);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set uy_nrotrx.
		@param uy_nrotrx uy_nrotrx	  */
	public void setuy_nrotrx (int uy_nrotrx)
	{
		set_Value (COLUMNNAME_uy_nrotrx, Integer.valueOf(uy_nrotrx));
	}

	/** Get uy_nrotrx.
		@return uy_nrotrx	  */
	public int getuy_nrotrx () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_uy_nrotrx);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set uy_qtyonhand_after.
		@param uy_qtyonhand_after uy_qtyonhand_after	  */
	public void setuy_qtyonhand_after (BigDecimal uy_qtyonhand_after)
	{
		set_Value (COLUMNNAME_uy_qtyonhand_after, uy_qtyonhand_after);
	}

	/** Get uy_qtyonhand_after.
		@return uy_qtyonhand_after	  */
	public BigDecimal getuy_qtyonhand_after () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_uy_qtyonhand_after);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set UY_QtyOnHand_AfterUM.
		@param UY_QtyOnHand_AfterUM UY_QtyOnHand_AfterUM	  */
	public void setUY_QtyOnHand_AfterUM (BigDecimal UY_QtyOnHand_AfterUM)
	{
		set_Value (COLUMNNAME_UY_QtyOnHand_AfterUM, UY_QtyOnHand_AfterUM);
	}

	/** Get UY_QtyOnHand_AfterUM.
		@return UY_QtyOnHand_AfterUM	  */
	public BigDecimal getUY_QtyOnHand_AfterUM () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_UY_QtyOnHand_AfterUM);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set uy_qtyonhand_before.
		@param uy_qtyonhand_before uy_qtyonhand_before	  */
	public void setuy_qtyonhand_before (BigDecimal uy_qtyonhand_before)
	{
		set_Value (COLUMNNAME_uy_qtyonhand_before, uy_qtyonhand_before);
	}

	/** Get uy_qtyonhand_before.
		@return uy_qtyonhand_before	  */
	public BigDecimal getuy_qtyonhand_before () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_uy_qtyonhand_before);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set uy_qtypending.
		@param uy_qtypending uy_qtypending	  */
	public void setuy_qtypending (BigDecimal uy_qtypending)
	{
		set_Value (COLUMNNAME_uy_qtypending, uy_qtypending);
	}

	/** Get uy_qtypending.
		@return uy_qtypending	  */
	public BigDecimal getuy_qtypending () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_uy_qtypending);
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

	/** Set UY_Reserve_Product.
		@param UY_Reserve_Product_ID UY_Reserve_Product	  */
	public void setUY_Reserve_Product_ID (int UY_Reserve_Product_ID)
	{
		if (UY_Reserve_Product_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_Reserve_Product_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_Reserve_Product_ID, Integer.valueOf(UY_Reserve_Product_ID));
	}

	/** Get UY_Reserve_Product.
		@return UY_Reserve_Product	  */
	public int getUY_Reserve_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Reserve_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** uy_reserve_status AD_Reference_ID=1000052 */
	public static final int UY_RESERVE_STATUS_AD_Reference_ID=1000052;
	/** Sin Reserva = 1 */
	public static final String UY_RESERVE_STATUS_SinReserva = "1";
	/** Reserva Parcial = 2 */
	public static final String UY_RESERVE_STATUS_ReservaParcial = "2";
	/** Reserva Total = 3 */
	public static final String UY_RESERVE_STATUS_ReservaTotal = "3";
	/** Set uy_reserve_status.
		@param uy_reserve_status uy_reserve_status	  */
	public void setuy_reserve_status (String uy_reserve_status)
	{

		set_Value (COLUMNNAME_uy_reserve_status, uy_reserve_status);
	}

	/** Get uy_reserve_status.
		@return uy_reserve_status	  */
	public String getuy_reserve_status () 
	{
		return (String)get_Value(COLUMNNAME_uy_reserve_status);
	}
}