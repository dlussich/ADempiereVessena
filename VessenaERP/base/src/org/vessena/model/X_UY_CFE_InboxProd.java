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

/** Generated Model for UY_CFE_InboxProd
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_CFE_InboxProd extends PO implements I_UY_CFE_InboxProd, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20151214L;

    /** Standard Constructor */
    public X_UY_CFE_InboxProd (Properties ctx, int UY_CFE_InboxProd_ID, String trxName)
    {
      super (ctx, UY_CFE_InboxProd_ID, trxName);
      /** if (UY_CFE_InboxProd_ID == 0)
        {
			setM_Product_ID (0);
			setUY_CFE_InboxLine_ID (0);
			setUY_CFE_InboxProd_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_CFE_InboxProd (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_CFE_InboxProd[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set C_Activity_ID_1.
		@param C_Activity_ID_1 C_Activity_ID_1	  */
	public void setC_Activity_ID_1 (int C_Activity_ID_1)
	{
		set_Value (COLUMNNAME_C_Activity_ID_1, Integer.valueOf(C_Activity_ID_1));
	}

	/** Get C_Activity_ID_1.
		@return C_Activity_ID_1	  */
	public int getC_Activity_ID_1 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Activity_ID_1);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set C_Activity_ID_2.
		@param C_Activity_ID_2 C_Activity_ID_2	  */
	public void setC_Activity_ID_2 (int C_Activity_ID_2)
	{
		set_Value (COLUMNNAME_C_Activity_ID_2, Integer.valueOf(C_Activity_ID_2));
	}

	/** Get C_Activity_ID_2.
		@return C_Activity_ID_2	  */
	public int getC_Activity_ID_2 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Activity_ID_2);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set C_Activity_ID_3.
		@param C_Activity_ID_3 C_Activity_ID_3	  */
	public void setC_Activity_ID_3 (int C_Activity_ID_3)
	{
		set_Value (COLUMNNAME_C_Activity_ID_3, Integer.valueOf(C_Activity_ID_3));
	}

	/** Get C_Activity_ID_3.
		@return C_Activity_ID_3	  */
	public int getC_Activity_ID_3 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Activity_ID_3);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_Tax getC_Tax() throws RuntimeException
    {
		return (org.compiere.model.I_C_Tax)MTable.get(getCtx(), org.compiere.model.I_C_Tax.Table_Name)
			.getPO(getC_Tax_ID(), get_TrxName());	}

	/** Set Tax.
		@param C_Tax_ID 
		Tax identifier
	  */
	public void setC_Tax_ID (int C_Tax_ID)
	{
		if (C_Tax_ID < 1) 
			set_Value (COLUMNNAME_C_Tax_ID, null);
		else 
			set_Value (COLUMNNAME_C_Tax_ID, Integer.valueOf(C_Tax_ID));
	}

	/** Get Tax.
		@return Tax identifier
	  */
	public int getC_Tax_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Tax_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Line Amount.
		@param LineNetAmt 
		Line Extended Amount (Quantity * Actual Price) without Freight and Charges
	  */
	public void setLineNetAmt (BigDecimal LineNetAmt)
	{
		set_Value (COLUMNNAME_LineNetAmt, LineNetAmt);
	}

	/** Get Line Amount.
		@return Line Extended Amount (Quantity * Actual Price) without Freight and Charges
	  */
	public BigDecimal getLineNetAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_LineNetAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Line Total.
		@param LineTotalAmt 
		Total line amount incl. Tax
	  */
	public void setLineTotalAmt (BigDecimal LineTotalAmt)
	{
		set_Value (COLUMNNAME_LineTotalAmt, LineTotalAmt);
	}

	/** Get Line Total.
		@return Total line amount incl. Tax
	  */
	public BigDecimal getLineTotalAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_LineTotalAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set Price.
		@param PriceEntered 
		Price Entered - the price based on the selected/base UoM
	  */
	public void setPriceEntered (BigDecimal PriceEntered)
	{
		set_Value (COLUMNNAME_PriceEntered, PriceEntered);
	}

	/** Get Price.
		@return Price Entered - the price based on the selected/base UoM
	  */
	public BigDecimal getPriceEntered () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceEntered);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set Tax Amount.
		@param TaxAmt 
		Tax Amount for a document
	  */
	public void setTaxAmt (BigDecimal TaxAmt)
	{
		set_Value (COLUMNNAME_TaxAmt, TaxAmt);
	}

	/** Get Tax Amount.
		@return Tax Amount for a document
	  */
	public BigDecimal getTaxAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TaxAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public I_UY_CFE_InboxLine getUY_CFE_InboxLine() throws RuntimeException
    {
		return (I_UY_CFE_InboxLine)MTable.get(getCtx(), I_UY_CFE_InboxLine.Table_Name)
			.getPO(getUY_CFE_InboxLine_ID(), get_TrxName());	}

	/** Set UY_CFE_InboxLine.
		@param UY_CFE_InboxLine_ID UY_CFE_InboxLine	  */
	public void setUY_CFE_InboxLine_ID (int UY_CFE_InboxLine_ID)
	{
		if (UY_CFE_InboxLine_ID < 1) 
			set_Value (COLUMNNAME_UY_CFE_InboxLine_ID, null);
		else 
			set_Value (COLUMNNAME_UY_CFE_InboxLine_ID, Integer.valueOf(UY_CFE_InboxLine_ID));
	}

	/** Get UY_CFE_InboxLine.
		@return UY_CFE_InboxLine	  */
	public int getUY_CFE_InboxLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_CFE_InboxLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_CFE_InboxProd.
		@param UY_CFE_InboxProd_ID UY_CFE_InboxProd	  */
	public void setUY_CFE_InboxProd_ID (int UY_CFE_InboxProd_ID)
	{
		if (UY_CFE_InboxProd_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_CFE_InboxProd_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_CFE_InboxProd_ID, Integer.valueOf(UY_CFE_InboxProd_ID));
	}

	/** Get UY_CFE_InboxProd.
		@return UY_CFE_InboxProd	  */
	public int getUY_CFE_InboxProd_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_CFE_InboxProd_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}