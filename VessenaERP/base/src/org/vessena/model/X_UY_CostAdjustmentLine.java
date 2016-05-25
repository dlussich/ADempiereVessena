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

/** Generated Model for UY_CostAdjustmentLine
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_UY_CostAdjustmentLine extends PO implements I_UY_CostAdjustmentLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_UY_CostAdjustmentLine (Properties ctx, int UY_CostAdjustmentLine_ID, String trxName)
    {
      super (ctx, UY_CostAdjustmentLine_ID, trxName);
      /** if (UY_CostAdjustmentLine_ID == 0)
        {
			setAmount (Env.ZERO);
			setC_Currency_ID (0);
			setM_Product_ID (0);
			setProcessed (false);
			setUY_CostAdjustment_ID (0);
			setUY_CostAdjustmentLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_CostAdjustmentLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_CostAdjustmentLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Amount.
		@param Amount 
		Amount in a defined currency
	  */
	public void setAmount (BigDecimal Amount)
	{
		set_Value (COLUMNNAME_Amount, Amount);
	}

	/** Get Amount.
		@return Amount in a defined currency
	  */
	public BigDecimal getAmount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Amount);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public I_C_Currency getC_Currency() throws RuntimeException
    {
		return (I_C_Currency)MTable.get(getCtx(), I_C_Currency.Table_Name)
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

	public I_UY_CostAdjustment getUY_CostAdjustment() throws RuntimeException
    {
		return (I_UY_CostAdjustment)MTable.get(getCtx(), I_UY_CostAdjustment.Table_Name)
			.getPO(getUY_CostAdjustment_ID(), get_TrxName());	}

	/** Set UY_CostAdjustment.
		@param UY_CostAdjustment_ID UY_CostAdjustment	  */
	public void setUY_CostAdjustment_ID (int UY_CostAdjustment_ID)
	{
		if (UY_CostAdjustment_ID < 1) 
			set_Value (COLUMNNAME_UY_CostAdjustment_ID, null);
		else 
			set_Value (COLUMNNAME_UY_CostAdjustment_ID, Integer.valueOf(UY_CostAdjustment_ID));
	}

	/** Get UY_CostAdjustment.
		@return UY_CostAdjustment	  */
	public int getUY_CostAdjustment_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_CostAdjustment_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_CostAdjustmentLine.
		@param UY_CostAdjustmentLine_ID UY_CostAdjustmentLine	  */
	public void setUY_CostAdjustmentLine_ID (int UY_CostAdjustmentLine_ID)
	{
		if (UY_CostAdjustmentLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_CostAdjustmentLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_CostAdjustmentLine_ID, Integer.valueOf(UY_CostAdjustmentLine_ID));
	}

	/** Get UY_CostAdjustmentLine.
		@return UY_CostAdjustmentLine	  */
	public int getUY_CostAdjustmentLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_CostAdjustmentLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set uy_tipoajuste.
		@param uy_tipoajuste uy_tipoajuste	  */
	public void setuy_tipoajuste (String uy_tipoajuste)
	{
		set_Value (COLUMNNAME_uy_tipoajuste, uy_tipoajuste);
	}

	/** Get uy_tipoajuste.
		@return uy_tipoajuste	  */
	public String getuy_tipoajuste () 
	{
		return (String)get_Value(COLUMNNAME_uy_tipoajuste);
	}
}