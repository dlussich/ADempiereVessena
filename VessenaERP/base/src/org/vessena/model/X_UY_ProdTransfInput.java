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

/** Generated Model for UY_ProdTransfInput
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_ProdTransfInput extends PO implements I_UY_ProdTransfInput, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20151116L;

    /** Standard Constructor */
    public X_UY_ProdTransfInput (Properties ctx, int UY_ProdTransfInput_ID, String trxName)
    {
      super (ctx, UY_ProdTransfInput_ID, trxName);
      /** if (UY_ProdTransfInput_ID == 0)
        {
			setC_UOM_ID (0);
			setM_Product_ID (0);
			setM_Warehouse_ID (0);
// 1000026
			setUY_ProdTransf_ID (0);
			setUY_ProdTransfInput_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_ProdTransfInput (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_ProdTransfInput[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	public I_UY_ProdTransf getUY_ProdTransf() throws RuntimeException
    {
		return (I_UY_ProdTransf)MTable.get(getCtx(), I_UY_ProdTransf.Table_Name)
			.getPO(getUY_ProdTransf_ID(), get_TrxName());	}

	/** Set UY_ProdTransf.
		@param UY_ProdTransf_ID UY_ProdTransf	  */
	public void setUY_ProdTransf_ID (int UY_ProdTransf_ID)
	{
		if (UY_ProdTransf_ID < 1) 
			set_Value (COLUMNNAME_UY_ProdTransf_ID, null);
		else 
			set_Value (COLUMNNAME_UY_ProdTransf_ID, Integer.valueOf(UY_ProdTransf_ID));
	}

	/** Get UY_ProdTransf.
		@return UY_ProdTransf	  */
	public int getUY_ProdTransf_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_ProdTransf_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_ProdTransfInput.
		@param UY_ProdTransfInput_ID UY_ProdTransfInput	  */
	public void setUY_ProdTransfInput_ID (int UY_ProdTransfInput_ID)
	{
		if (UY_ProdTransfInput_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_ProdTransfInput_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_ProdTransfInput_ID, Integer.valueOf(UY_ProdTransfInput_ID));
	}

	/** Get UY_ProdTransfInput.
		@return UY_ProdTransfInput	  */
	public int getUY_ProdTransfInput_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_ProdTransfInput_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}