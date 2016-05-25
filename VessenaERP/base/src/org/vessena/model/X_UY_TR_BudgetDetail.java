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

/** Generated Model for UY_TR_BudgetDetail
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_TR_BudgetDetail extends PO implements I_UY_TR_BudgetDetail, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20141106L;

    /** Standard Constructor */
    public X_UY_TR_BudgetDetail (Properties ctx, int UY_TR_BudgetDetail_ID, String trxName)
    {
      super (ctx, UY_TR_BudgetDetail_ID, trxName);
      /** if (UY_TR_BudgetDetail_ID == 0)
        {
			setAplicaValorem (false);
			setC_Tax_ID (0);
			setLineNetAmt (Env.ZERO);
			setM_Product_ID (0);
			setPriceActual (Env.ZERO);
			setPriceEntered (Env.ZERO);
			setPriceLimit (Env.ZERO);
			setPriceList (Env.ZERO);
			setQtyEntered (Env.ZERO);
			setQtyInvoiced (Env.ZERO);
// 1
			setUY_TR_BudgetDetail_ID (0);
			setUY_TR_BudgetLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_TR_BudgetDetail (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_TR_BudgetDetail[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set AplicaValorem.
		@param AplicaValorem 
		AplicaValorem
	  */
	public void setAplicaValorem (boolean AplicaValorem)
	{
		set_Value (COLUMNNAME_AplicaValorem, Boolean.valueOf(AplicaValorem));
	}

	/** Get AplicaValorem.
		@return AplicaValorem
	  */
	public boolean isAplicaValorem () 
	{
		Object oo = get_Value(COLUMNNAME_AplicaValorem);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set InternationalAmt.
		@param InternationalAmt 
		InternationalAmt
	  */
	public void setInternationalAmt (BigDecimal InternationalAmt)
	{
		set_Value (COLUMNNAME_InternationalAmt, InternationalAmt);
	}

	/** Get InternationalAmt.
		@return InternationalAmt
	  */
	public BigDecimal getInternationalAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_InternationalAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set InterPercentage.
		@param InterPercentage InterPercentage	  */
	public void setInterPercentage (BigDecimal InterPercentage)
	{
		set_Value (COLUMNNAME_InterPercentage, InterPercentage);
	}

	/** Get InterPercentage.
		@return InterPercentage	  */
	public BigDecimal getInterPercentage () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_InterPercentage);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set NationalAmt.
		@param NationalAmt 
		NationalAmt
	  */
	public void setNationalAmt (BigDecimal NationalAmt)
	{
		set_Value (COLUMNNAME_NationalAmt, NationalAmt);
	}

	/** Get NationalAmt.
		@return NationalAmt
	  */
	public BigDecimal getNationalAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_NationalAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set NationalPercentage.
		@param NationalPercentage NationalPercentage	  */
	public void setNationalPercentage (BigDecimal NationalPercentage)
	{
		set_Value (COLUMNNAME_NationalPercentage, NationalPercentage);
	}

	/** Get NationalPercentage.
		@return NationalPercentage	  */
	public BigDecimal getNationalPercentage () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_NationalPercentage);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Unit Price.
		@param PriceActual 
		Actual Price 
	  */
	public void setPriceActual (BigDecimal PriceActual)
	{
		set_Value (COLUMNNAME_PriceActual, PriceActual);
	}

	/** Get Unit Price.
		@return Actual Price 
	  */
	public BigDecimal getPriceActual () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceActual);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set Limit Price.
		@param PriceLimit 
		Lowest price for a product
	  */
	public void setPriceLimit (BigDecimal PriceLimit)
	{
		set_Value (COLUMNNAME_PriceLimit, PriceLimit);
	}

	/** Get Limit Price.
		@return Lowest price for a product
	  */
	public BigDecimal getPriceLimit () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceLimit);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set List Price.
		@param PriceList 
		List Price
	  */
	public void setPriceList (BigDecimal PriceList)
	{
		set_Value (COLUMNNAME_PriceList, PriceList);
	}

	/** Get List Price.
		@return List Price
	  */
	public BigDecimal getPriceList () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceList);
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

	/** Set Quantity Invoiced.
		@param QtyInvoiced 
		Invoiced Quantity
	  */
	public void setQtyInvoiced (BigDecimal QtyInvoiced)
	{
		set_Value (COLUMNNAME_QtyInvoiced, QtyInvoiced);
	}

	/** Get Quantity Invoiced.
		@return Invoiced Quantity
	  */
	public BigDecimal getQtyInvoiced () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyInvoiced);
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

	/** Set UY_TR_BudgetDetail.
		@param UY_TR_BudgetDetail_ID UY_TR_BudgetDetail	  */
	public void setUY_TR_BudgetDetail_ID (int UY_TR_BudgetDetail_ID)
	{
		if (UY_TR_BudgetDetail_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_TR_BudgetDetail_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_TR_BudgetDetail_ID, Integer.valueOf(UY_TR_BudgetDetail_ID));
	}

	/** Get UY_TR_BudgetDetail.
		@return UY_TR_BudgetDetail	  */
	public int getUY_TR_BudgetDetail_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_BudgetDetail_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TR_BudgetLine getUY_TR_BudgetLine() throws RuntimeException
    {
		return (I_UY_TR_BudgetLine)MTable.get(getCtx(), I_UY_TR_BudgetLine.Table_Name)
			.getPO(getUY_TR_BudgetLine_ID(), get_TrxName());	}

	/** Set UY_TR_BudgetLine.
		@param UY_TR_BudgetLine_ID UY_TR_BudgetLine	  */
	public void setUY_TR_BudgetLine_ID (int UY_TR_BudgetLine_ID)
	{
		if (UY_TR_BudgetLine_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_BudgetLine_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_BudgetLine_ID, Integer.valueOf(UY_TR_BudgetLine_ID));
	}

	/** Get UY_TR_BudgetLine.
		@return UY_TR_BudgetLine	  */
	public int getUY_TR_BudgetLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_BudgetLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set ValoremPercentage.
		@param ValoremPercentage 
		ValoremPercentage
	  */
	public void setValoremPercentage (BigDecimal ValoremPercentage)
	{
		set_Value (COLUMNNAME_ValoremPercentage, ValoremPercentage);
	}

	/** Get ValoremPercentage.
		@return ValoremPercentage
	  */
	public BigDecimal getValoremPercentage () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ValoremPercentage);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}