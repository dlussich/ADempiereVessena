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

/** Generated Model for UY_StockAdjustmentLine
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_UY_StockAdjustmentLine extends PO implements I_UY_StockAdjustmentLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_UY_StockAdjustmentLine (Properties ctx, int UY_StockAdjustmentLine_ID, String trxName)
    {
      super (ctx, UY_StockAdjustmentLine_ID, trxName);
      /** if (UY_StockAdjustmentLine_ID == 0)
        {
			setM_Locator_ID (0);
			setMovementQty (Env.ZERO);
			setM_Product_ID (0);
			setProcessed (false);
			setUY_StockAdjustment_ID (0);
			setUY_StockAdjustmentLine_ID (0);
			setUY_StockStatus_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_StockAdjustmentLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_StockAdjustmentLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_M_AttributeSetInstance getM_AttributeSetInstance() throws RuntimeException
    {
		return (I_M_AttributeSetInstance)MTable.get(getCtx(), I_M_AttributeSetInstance.Table_Name)
			.getPO(getM_AttributeSetInstance_ID(), get_TrxName());	}

	/** Set Attribute Set Instance.
		@param M_AttributeSetInstance_ID 
		Product Attribute Set Instance
	  */
	public void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID)
	{
		if (M_AttributeSetInstance_ID < 0) 
			set_Value (COLUMNNAME_M_AttributeSetInstance_ID, null);
		else 
			set_Value (COLUMNNAME_M_AttributeSetInstance_ID, Integer.valueOf(M_AttributeSetInstance_ID));
	}

	/** Get Attribute Set Instance.
		@return Product Attribute Set Instance
	  */
	public int getM_AttributeSetInstance_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_AttributeSetInstance_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_M_Locator getM_Locator() throws RuntimeException
    {
		return (I_M_Locator)MTable.get(getCtx(), I_M_Locator.Table_Name)
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

	/** Set Movement Quantity.
		@param MovementQty 
		Quantity of a product moved.
	  */
	public void setMovementQty (BigDecimal MovementQty)
	{
		set_Value (COLUMNNAME_MovementQty, MovementQty);
	}

	/** Get Movement Quantity.
		@return Quantity of a product moved.
	  */
	public BigDecimal getMovementQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_MovementQty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set Quantity book.
		@param QtyBook 
		Book Quantity
	  */
	public void setQtyBook (BigDecimal QtyBook)
	{
		throw new IllegalArgumentException ("QtyBook is virtual column");	}

	/** Get Quantity book.
		@return Book Quantity
	  */
	public BigDecimal getQtyBook () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyBook);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Quantity count.
		@param QtyCount 
		Counted Quantity
	  */
	public void setQtyCount (BigDecimal QtyCount)
	{
		set_Value (COLUMNNAME_QtyCount, QtyCount);
	}

	/** Get Quantity count.
		@return Counted Quantity
	  */
	public BigDecimal getQtyCount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyCount);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public I_UY_StockAdjustment getUY_StockAdjustment() throws RuntimeException
    {
		return (I_UY_StockAdjustment)MTable.get(getCtx(), I_UY_StockAdjustment.Table_Name)
			.getPO(getUY_StockAdjustment_ID(), get_TrxName());	}

	/** Set UY_StockAdjustment.
		@param UY_StockAdjustment_ID UY_StockAdjustment	  */
	public void setUY_StockAdjustment_ID (int UY_StockAdjustment_ID)
	{
		if (UY_StockAdjustment_ID < 1) 
			set_Value (COLUMNNAME_UY_StockAdjustment_ID, null);
		else 
			set_Value (COLUMNNAME_UY_StockAdjustment_ID, Integer.valueOf(UY_StockAdjustment_ID));
	}

	/** Get UY_StockAdjustment.
		@return UY_StockAdjustment	  */
	public int getUY_StockAdjustment_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_StockAdjustment_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_StockAdjustmentLine.
		@param UY_StockAdjustmentLine_ID UY_StockAdjustmentLine	  */
	public void setUY_StockAdjustmentLine_ID (int UY_StockAdjustmentLine_ID)
	{
		if (UY_StockAdjustmentLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_StockAdjustmentLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_StockAdjustmentLine_ID, Integer.valueOf(UY_StockAdjustmentLine_ID));
	}

	/** Get UY_StockAdjustmentLine.
		@return UY_StockAdjustmentLine	  */
	public int getUY_StockAdjustmentLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_StockAdjustmentLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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
}