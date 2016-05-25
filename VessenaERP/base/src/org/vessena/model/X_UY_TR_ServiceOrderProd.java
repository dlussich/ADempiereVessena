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

/** Generated Model for UY_TR_ServiceOrderProd
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_TR_ServiceOrderProd extends PO implements I_UY_TR_ServiceOrderProd, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150901L;

    /** Standard Constructor */
    public X_UY_TR_ServiceOrderProd (Properties ctx, int UY_TR_ServiceOrderProd_ID, String trxName)
    {
      super (ctx, UY_TR_ServiceOrderProd_ID, trxName);
      /** if (UY_TR_ServiceOrderProd_ID == 0)
        {
			setM_Locator_ID (0);
			setM_Product_ID (0);
			setM_Warehouse_ID (0);
			setQtyRequired (Env.ZERO);
			setUY_StockStatus_ID (0);
			setUY_TR_ServiceOrder_ID (0);
			setUY_TR_ServiceOrderProd_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_TR_ServiceOrderProd (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_TR_ServiceOrderProd[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_M_Locator getM_Locator() throws RuntimeException
    {
		return (org.compiere.model.I_M_Locator)MTable.get(getCtx(), org.compiere.model.I_M_Locator.Table_Name)
			.getPO(getM_Locator_ID(), get_TrxName());	}

	/** Set Locator.
		@param M_Locator_ID 
		Warehouse Locator
	  */
	public void setM_Locator_ID (int M_Locator_ID)
	{
		if (M_Locator_ID < 1) 
			set_Value (COLUMNNAME_M_Locator_ID, null);
		else 
			set_Value (COLUMNNAME_M_Locator_ID, Integer.valueOf(M_Locator_ID));
	}

	/** Get Locator.
		@return Warehouse Locator
	  */
	public int getM_Locator_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Locator_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Available Quantity.
		@param QtyAvailable 
		Available Quantity (On Hand - Reserved)
	  */
	public void setQtyAvailable (BigDecimal QtyAvailable)
	{
		set_Value (COLUMNNAME_QtyAvailable, QtyAvailable);
	}

	/** Get Available Quantity.
		@return Available Quantity (On Hand - Reserved)
	  */
	public BigDecimal getQtyAvailable () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyAvailable);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Qty Required.
		@param QtyRequired Qty Required	  */
	public void setQtyRequired (BigDecimal QtyRequired)
	{
		set_Value (COLUMNNAME_QtyRequired, QtyRequired);
	}

	/** Get Qty Required.
		@return Qty Required	  */
	public BigDecimal getQtyRequired () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyRequired);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public I_UY_StockStatus getUY_StockStatus() throws RuntimeException
    {
		return (I_UY_StockStatus)MTable.get(getCtx(), I_UY_StockStatus.Table_Name)
			.getPO(getUY_StockStatus_ID(), get_TrxName());	}

	/** Set UY_StockStatus.
		@param UY_StockStatus_ID UY_StockStatus	  */
	public void setUY_StockStatus_ID (int UY_StockStatus_ID)
	{
		if (UY_StockStatus_ID < 1) 
			set_Value (COLUMNNAME_UY_StockStatus_ID, null);
		else 
			set_Value (COLUMNNAME_UY_StockStatus_ID, Integer.valueOf(UY_StockStatus_ID));
	}

	/** Get UY_StockStatus.
		@return UY_StockStatus	  */
	public int getUY_StockStatus_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_StockStatus_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TR_Failure getUY_TR_Failure() throws RuntimeException
    {
		return (I_UY_TR_Failure)MTable.get(getCtx(), I_UY_TR_Failure.Table_Name)
			.getPO(getUY_TR_Failure_ID(), get_TrxName());	}

	/** Set UY_TR_Failure.
		@param UY_TR_Failure_ID UY_TR_Failure	  */
	public void setUY_TR_Failure_ID (int UY_TR_Failure_ID)
	{
		if (UY_TR_Failure_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_Failure_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_Failure_ID, Integer.valueOf(UY_TR_Failure_ID));
	}

	/** Get UY_TR_Failure.
		@return UY_TR_Failure	  */
	public int getUY_TR_Failure_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Failure_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TR_ServiceOrderFailure getUY_TR_ServiceOrderFailure() throws RuntimeException
    {
		return (I_UY_TR_ServiceOrderFailure)MTable.get(getCtx(), I_UY_TR_ServiceOrderFailure.Table_Name)
			.getPO(getUY_TR_ServiceOrderFailure_ID(), get_TrxName());	}

	/** Set UY_TR_ServiceOrderFailure.
		@param UY_TR_ServiceOrderFailure_ID UY_TR_ServiceOrderFailure	  */
	public void setUY_TR_ServiceOrderFailure_ID (int UY_TR_ServiceOrderFailure_ID)
	{
		if (UY_TR_ServiceOrderFailure_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_ServiceOrderFailure_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_ServiceOrderFailure_ID, Integer.valueOf(UY_TR_ServiceOrderFailure_ID));
	}

	/** Get UY_TR_ServiceOrderFailure.
		@return UY_TR_ServiceOrderFailure	  */
	public int getUY_TR_ServiceOrderFailure_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_ServiceOrderFailure_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TR_ServiceOrder getUY_TR_ServiceOrder() throws RuntimeException
    {
		return (I_UY_TR_ServiceOrder)MTable.get(getCtx(), I_UY_TR_ServiceOrder.Table_Name)
			.getPO(getUY_TR_ServiceOrder_ID(), get_TrxName());	}

	/** Set UY_TR_ServiceOrder.
		@param UY_TR_ServiceOrder_ID UY_TR_ServiceOrder	  */
	public void setUY_TR_ServiceOrder_ID (int UY_TR_ServiceOrder_ID)
	{
		if (UY_TR_ServiceOrder_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_ServiceOrder_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_ServiceOrder_ID, Integer.valueOf(UY_TR_ServiceOrder_ID));
	}

	/** Get UY_TR_ServiceOrder.
		@return UY_TR_ServiceOrder	  */
	public int getUY_TR_ServiceOrder_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_ServiceOrder_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TR_ServiceOrderMaintain getUY_TR_ServiceOrderMaintain() throws RuntimeException
    {
		return (I_UY_TR_ServiceOrderMaintain)MTable.get(getCtx(), I_UY_TR_ServiceOrderMaintain.Table_Name)
			.getPO(getUY_TR_ServiceOrderMaintain_ID(), get_TrxName());	}

	/** Set UY_TR_ServiceOrderMaintain.
		@param UY_TR_ServiceOrderMaintain_ID UY_TR_ServiceOrderMaintain	  */
	public void setUY_TR_ServiceOrderMaintain_ID (int UY_TR_ServiceOrderMaintain_ID)
	{
		if (UY_TR_ServiceOrderMaintain_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_ServiceOrderMaintain_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_ServiceOrderMaintain_ID, Integer.valueOf(UY_TR_ServiceOrderMaintain_ID));
	}

	/** Get UY_TR_ServiceOrderMaintain.
		@return UY_TR_ServiceOrderMaintain	  */
	public int getUY_TR_ServiceOrderMaintain_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_ServiceOrderMaintain_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TR_ServiceOrderProd.
		@param UY_TR_ServiceOrderProd_ID UY_TR_ServiceOrderProd	  */
	public void setUY_TR_ServiceOrderProd_ID (int UY_TR_ServiceOrderProd_ID)
	{
		if (UY_TR_ServiceOrderProd_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_TR_ServiceOrderProd_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_TR_ServiceOrderProd_ID, Integer.valueOf(UY_TR_ServiceOrderProd_ID));
	}

	/** Get UY_TR_ServiceOrderProd.
		@return UY_TR_ServiceOrderProd	  */
	public int getUY_TR_ServiceOrderProd_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_ServiceOrderProd_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}