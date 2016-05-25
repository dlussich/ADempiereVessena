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

/** Generated Model for UY_FF_CashOutLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS - $Id$ */
public class X_UY_FF_CashOutLine extends PO implements I_UY_FF_CashOutLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20131219L;

    /** Standard Constructor */
    public X_UY_FF_CashOutLine (Properties ctx, int UY_FF_CashOutLine_ID, String trxName)
    {
      super (ctx, UY_FF_CashOutLine_ID, trxName);
      /** if (UY_FF_CashOutLine_ID == 0)
        {
			setApprovedBy (null);
			setC_Activity_ID_1 (0);
			setC_Tax_ID (0);
			setfactura (0);
// 1
			setLineNetAmt (Env.ZERO);
			setLineTotalAmt (Env.ZERO);
			setM_Product_ID (0);
			setPriceActual (Env.ZERO);
			setPriceEntered (Env.ZERO);
			setQtyEntered (Env.ZERO);
// 1
			setQtyInvoiced (Env.ZERO);
// 1
			setUY_FF_CashOut_ID (0);
			setUY_FF_CashOutLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_FF_CashOutLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_FF_CashOutLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set ApprovedBy.
		@param ApprovedBy ApprovedBy	  */
	public void setApprovedBy (String ApprovedBy)
	{
		set_Value (COLUMNNAME_ApprovedBy, ApprovedBy);
	}

	/** Get ApprovedBy.
		@return ApprovedBy	  */
	public String getApprovedBy () 
	{
		return (String)get_Value(COLUMNNAME_ApprovedBy);
	}

	public org.compiere.model.I_C_Activity getC_Activity_I() throws RuntimeException
    {
		return (org.compiere.model.I_C_Activity)MTable.get(getCtx(), org.compiere.model.I_C_Activity.Table_Name)
			.getPO(getC_Activity_ID_1(), get_TrxName());	}

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

	/** Set factura.
		@param factura factura	  */
	public void setfactura (int factura)
	{
		set_Value (COLUMNNAME_factura, Integer.valueOf(factura));
	}

	/** Get factura.
		@return factura	  */
	public int getfactura () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_factura);
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

	public I_UY_FF_CashOut getUY_FF_CashOut() throws RuntimeException
    {
		return (I_UY_FF_CashOut)MTable.get(getCtx(), I_UY_FF_CashOut.Table_Name)
			.getPO(getUY_FF_CashOut_ID(), get_TrxName());	}

	/** Set UY_FF_CashOut.
		@param UY_FF_CashOut_ID UY_FF_CashOut	  */
	public void setUY_FF_CashOut_ID (int UY_FF_CashOut_ID)
	{
		if (UY_FF_CashOut_ID < 1) 
			set_Value (COLUMNNAME_UY_FF_CashOut_ID, null);
		else 
			set_Value (COLUMNNAME_UY_FF_CashOut_ID, Integer.valueOf(UY_FF_CashOut_ID));
	}

	/** Get UY_FF_CashOut.
		@return UY_FF_CashOut	  */
	public int getUY_FF_CashOut_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_FF_CashOut_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_FF_CashOutLine.
		@param UY_FF_CashOutLine_ID UY_FF_CashOutLine	  */
	public void setUY_FF_CashOutLine_ID (int UY_FF_CashOutLine_ID)
	{
		if (UY_FF_CashOutLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_FF_CashOutLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_FF_CashOutLine_ID, Integer.valueOf(UY_FF_CashOutLine_ID));
	}

	/** Get UY_FF_CashOutLine.
		@return UY_FF_CashOutLine	  */
	public int getUY_FF_CashOutLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_FF_CashOutLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}