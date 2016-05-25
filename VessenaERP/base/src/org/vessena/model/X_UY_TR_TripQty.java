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

/** Generated Model for UY_TR_TripQty
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_TR_TripQty extends PO implements I_UY_TR_TripQty, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150415L;

    /** Standard Constructor */
    public X_UY_TR_TripQty (Properties ctx, int UY_TR_TripQty_ID, String trxName)
    {
      super (ctx, UY_TR_TripQty_ID, trxName);
      /** if (UY_TR_TripQty_ID == 0)
        {
			setUY_TR_Crt_ID (0);
			setUY_TR_Trip_ID (0);
			setUY_TR_TripQty_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_TR_TripQty (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_TR_TripQty[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_C_Currency getC_Currency() throws RuntimeException
    {
		return (org.compiere.model.I_C_Currency)MTable.get(getCtx(), org.compiere.model.I_C_Currency.Table_Name)
			.getPO(getC_Currency_ID(), get_TrxName());	}

	/** Set Currency.
		@param C_Currency_ID 
		The Currency for this record
	  */
	public void setC_Currency_ID (int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
	}

	/** Get Currency.
		@return The Currency for this record
	  */
	public int getC_Currency_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_M_Warehouse getM_Warehouse() throws RuntimeException
    {
		return (org.compiere.model.I_M_Warehouse)MTable.get(getCtx(), org.compiere.model.I_M_Warehouse.Table_Name)
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

	/** Set ProductAmt.
		@param ProductAmt ProductAmt	  */
	public void setProductAmt (BigDecimal ProductAmt)
	{
		set_Value (COLUMNNAME_ProductAmt, ProductAmt);
	}

	/** Get ProductAmt.
		@return ProductAmt	  */
	public BigDecimal getProductAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ProductAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set QtyPackage.
		@param QtyPackage QtyPackage	  */
	public void setQtyPackage (BigDecimal QtyPackage)
	{
		set_Value (COLUMNNAME_QtyPackage, QtyPackage);
	}

	/** Get QtyPackage.
		@return QtyPackage	  */
	public BigDecimal getQtyPackage () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyPackage);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public I_UY_TR_Crt getUY_TR_Crt() throws RuntimeException
    {
		return (I_UY_TR_Crt)MTable.get(getCtx(), I_UY_TR_Crt.Table_Name)
			.getPO(getUY_TR_Crt_ID(), get_TrxName());	}

	/** Set UY_TR_Crt.
		@param UY_TR_Crt_ID UY_TR_Crt	  */
	public void setUY_TR_Crt_ID (int UY_TR_Crt_ID)
	{
		if (UY_TR_Crt_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_Crt_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_Crt_ID, Integer.valueOf(UY_TR_Crt_ID));
	}

	/** Get UY_TR_Crt.
		@return UY_TR_Crt	  */
	public int getUY_TR_Crt_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Crt_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TR_LoadMonitorLine getUY_TR_LoadMonitorLine() throws RuntimeException
    {
		return (I_UY_TR_LoadMonitorLine)MTable.get(getCtx(), I_UY_TR_LoadMonitorLine.Table_Name)
			.getPO(getUY_TR_LoadMonitorLine_ID(), get_TrxName());	}

	/** Set UY_TR_LoadMonitorLine.
		@param UY_TR_LoadMonitorLine_ID UY_TR_LoadMonitorLine	  */
	public void setUY_TR_LoadMonitorLine_ID (int UY_TR_LoadMonitorLine_ID)
	{
		if (UY_TR_LoadMonitorLine_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_LoadMonitorLine_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_LoadMonitorLine_ID, Integer.valueOf(UY_TR_LoadMonitorLine_ID));
	}

	/** Get UY_TR_LoadMonitorLine.
		@return UY_TR_LoadMonitorLine	  */
	public int getUY_TR_LoadMonitorLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_LoadMonitorLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TR_Stock.
		@param UY_TR_Stock_ID UY_TR_Stock	  */
	public void setUY_TR_Stock_ID (int UY_TR_Stock_ID)
	{
		if (UY_TR_Stock_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_Stock_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_Stock_ID, Integer.valueOf(UY_TR_Stock_ID));
	}

	/** Get UY_TR_Stock.
		@return UY_TR_Stock	  */
	public int getUY_TR_Stock_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Stock_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TR_TransOrder getUY_TR_TransOrder() throws RuntimeException
    {
		return (I_UY_TR_TransOrder)MTable.get(getCtx(), I_UY_TR_TransOrder.Table_Name)
			.getPO(getUY_TR_TransOrder_ID(), get_TrxName());	}

	/** Set UY_TR_TransOrder.
		@param UY_TR_TransOrder_ID UY_TR_TransOrder	  */
	public void setUY_TR_TransOrder_ID (int UY_TR_TransOrder_ID)
	{
		if (UY_TR_TransOrder_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_TransOrder_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_TransOrder_ID, Integer.valueOf(UY_TR_TransOrder_ID));
	}

	/** Get UY_TR_TransOrder.
		@return UY_TR_TransOrder	  */
	public int getUY_TR_TransOrder_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_TransOrder_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TR_TransOrderLine getUY_TR_TransOrderLine() throws RuntimeException
    {
		return (I_UY_TR_TransOrderLine)MTable.get(getCtx(), I_UY_TR_TransOrderLine.Table_Name)
			.getPO(getUY_TR_TransOrderLine_ID(), get_TrxName());	}

	/** Set UY_TR_TransOrderLine.
		@param UY_TR_TransOrderLine_ID UY_TR_TransOrderLine	  */
	public void setUY_TR_TransOrderLine_ID (int UY_TR_TransOrderLine_ID)
	{
		if (UY_TR_TransOrderLine_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_TransOrderLine_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_TransOrderLine_ID, Integer.valueOf(UY_TR_TransOrderLine_ID));
	}

	/** Get UY_TR_TransOrderLine.
		@return UY_TR_TransOrderLine	  */
	public int getUY_TR_TransOrderLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_TransOrderLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TR_Trip getUY_TR_Trip() throws RuntimeException
    {
		return (I_UY_TR_Trip)MTable.get(getCtx(), I_UY_TR_Trip.Table_Name)
			.getPO(getUY_TR_Trip_ID(), get_TrxName());	}

	/** Set UY_TR_Trip.
		@param UY_TR_Trip_ID UY_TR_Trip	  */
	public void setUY_TR_Trip_ID (int UY_TR_Trip_ID)
	{
		if (UY_TR_Trip_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_Trip_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_Trip_ID, Integer.valueOf(UY_TR_Trip_ID));
	}

	/** Get UY_TR_Trip.
		@return UY_TR_Trip	  */
	public int getUY_TR_Trip_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Trip_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TR_TripQty.
		@param UY_TR_TripQty_ID UY_TR_TripQty	  */
	public void setUY_TR_TripQty_ID (int UY_TR_TripQty_ID)
	{
		if (UY_TR_TripQty_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_TR_TripQty_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_TR_TripQty_ID, Integer.valueOf(UY_TR_TripQty_ID));
	}

	/** Get UY_TR_TripQty.
		@return UY_TR_TripQty	  */
	public int getUY_TR_TripQty_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_TripQty_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Volume.
		@param Volume 
		Volume of a product
	  */
	public void setVolume (BigDecimal Volume)
	{
		set_Value (COLUMNNAME_Volume, Volume);
	}

	/** Get Volume.
		@return Volume of a product
	  */
	public BigDecimal getVolume () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Volume);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Weight.
		@param Weight 
		Weight of a product
	  */
	public void setWeight (BigDecimal Weight)
	{
		set_Value (COLUMNNAME_Weight, Weight);
	}

	/** Get Weight.
		@return Weight of a product
	  */
	public BigDecimal getWeight () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Weight);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Weight2.
		@param Weight2 Weight2	  */
	public void setWeight2 (BigDecimal Weight2)
	{
		set_Value (COLUMNNAME_Weight2, Weight2);
	}

	/** Get Weight2.
		@return Weight2	  */
	public BigDecimal getWeight2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Weight2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}