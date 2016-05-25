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

/** Generated Model for UY_Invoice_Provision
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_Invoice_Provision extends PO implements I_UY_Invoice_Provision, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20121016L;

    /** Standard Constructor */
    public X_UY_Invoice_Provision (Properties ctx, int UY_Invoice_Provision_ID, String trxName)
    {
      super (ctx, UY_Invoice_Provision_ID, trxName);
      /** if (UY_Invoice_Provision_ID == 0)
        {
			setC_Currency_ID (0);
			setC_ElementValue_ID (0);
			setC_Invoice_ID (0);
			setUY_Invoice_Provision_ID (0);
			setUY_Provision_ID (0);
			setUY_ProvisionLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_Invoice_Provision (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_Invoice_Provision[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Amount.
		@param Amt 
		Amount
	  */
	public void setAmt (BigDecimal Amt)
	{
		set_Value (COLUMNNAME_Amt, Amt);
	}

	/** Get Amount.
		@return Amount
	  */
	public BigDecimal getAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Amt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtallocated.
		@param amtallocated amtallocated	  */
	public void setamtallocated (BigDecimal amtallocated)
	{
		set_Value (COLUMNNAME_amtallocated, amtallocated);
	}

	/** Get amtallocated.
		@return amtallocated	  */
	public BigDecimal getamtallocated () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtallocated);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtopen.
		@param amtopen amtopen	  */
	public void setamtopen (BigDecimal amtopen)
	{
		set_Value (COLUMNNAME_amtopen, amtopen);
	}

	/** Get amtopen.
		@return amtopen	  */
	public BigDecimal getamtopen () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtopen);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	public org.compiere.model.I_C_ElementValue getC_ElementValue() throws RuntimeException
    {
		return (org.compiere.model.I_C_ElementValue)MTable.get(getCtx(), org.compiere.model.I_C_ElementValue.Table_Name)
			.getPO(getC_ElementValue_ID(), get_TrxName());	}

	/** Set Account Element.
		@param C_ElementValue_ID 
		Account Element
	  */
	public void setC_ElementValue_ID (int C_ElementValue_ID)
	{
		if (C_ElementValue_ID < 1) 
			set_Value (COLUMNNAME_C_ElementValue_ID, null);
		else 
			set_Value (COLUMNNAME_C_ElementValue_ID, Integer.valueOf(C_ElementValue_ID));
	}

	/** Get Account Element.
		@return Account Element
	  */
	public int getC_ElementValue_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_ElementValue_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_Invoice getC_Invoice() throws RuntimeException
    {
		return (org.compiere.model.I_C_Invoice)MTable.get(getCtx(), org.compiere.model.I_C_Invoice.Table_Name)
			.getPO(getC_Invoice_ID(), get_TrxName());	}

	/** Set Invoice.
		@param C_Invoice_ID 
		Invoice Identifier
	  */
	public void setC_Invoice_ID (int C_Invoice_ID)
	{
		if (C_Invoice_ID < 1) 
			set_Value (COLUMNNAME_C_Invoice_ID, null);
		else 
			set_Value (COLUMNNAME_C_Invoice_ID, Integer.valueOf(C_Invoice_ID));
	}

	/** Get Invoice.
		@return Invoice Identifier
	  */
	public int getC_Invoice_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Invoice_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_InvoiceLine getC_InvoiceLine() throws RuntimeException
    {
		return (org.compiere.model.I_C_InvoiceLine)MTable.get(getCtx(), org.compiere.model.I_C_InvoiceLine.Table_Name)
			.getPO(getC_InvoiceLine_ID(), get_TrxName());	}

	/** Set Invoice Line.
		@param C_InvoiceLine_ID 
		Invoice Detail Line
	  */
	public void setC_InvoiceLine_ID (int C_InvoiceLine_ID)
	{
		if (C_InvoiceLine_ID < 1) 
			set_Value (COLUMNNAME_C_InvoiceLine_ID, null);
		else 
			set_Value (COLUMNNAME_C_InvoiceLine_ID, Integer.valueOf(C_InvoiceLine_ID));
	}

	/** Get Invoice Line.
		@return Invoice Detail Line
	  */
	public int getC_InvoiceLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_InvoiceLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_Period getC_Period() throws RuntimeException
    {
		return (org.compiere.model.I_C_Period)MTable.get(getCtx(), org.compiere.model.I_C_Period.Table_Name)
			.getPO(getC_Period_ID(), get_TrxName());	}

	/** Set Period.
		@param C_Period_ID 
		Period of the Calendar
	  */
	public void setC_Period_ID (int C_Period_ID)
	{
		if (C_Period_ID < 1) 
			set_Value (COLUMNNAME_C_Period_ID, null);
		else 
			set_Value (COLUMNNAME_C_Period_ID, Integer.valueOf(C_Period_ID));
	}

	/** Get Period.
		@return Period of the Calendar
	  */
	public int getC_Period_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Period_ID);
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

	/** Set Quantity.
		@param Qty 
		Quantity
	  */
	public void setQty (BigDecimal Qty)
	{
		set_Value (COLUMNNAME_Qty, Qty);
	}

	/** Get Quantity.
		@return Quantity
	  */
	public BigDecimal getQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Qty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set qtyallocated.
		@param qtyallocated qtyallocated	  */
	public void setqtyallocated (BigDecimal qtyallocated)
	{
		set_Value (COLUMNNAME_qtyallocated, qtyallocated);
	}

	/** Get qtyallocated.
		@return qtyallocated	  */
	public BigDecimal getqtyallocated () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_qtyallocated);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set qtyopen.
		@param qtyopen qtyopen	  */
	public void setqtyopen (BigDecimal qtyopen)
	{
		set_Value (COLUMNNAME_qtyopen, qtyopen);
	}

	/** Get qtyopen.
		@return qtyopen	  */
	public BigDecimal getqtyopen () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_qtyopen);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set UY_Invoice_Provision.
		@param UY_Invoice_Provision_ID UY_Invoice_Provision	  */
	public void setUY_Invoice_Provision_ID (int UY_Invoice_Provision_ID)
	{
		if (UY_Invoice_Provision_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_Invoice_Provision_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_Invoice_Provision_ID, Integer.valueOf(UY_Invoice_Provision_ID));
	}

	/** Get UY_Invoice_Provision.
		@return UY_Invoice_Provision	  */
	public int getUY_Invoice_Provision_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Invoice_Provision_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_Provision getUY_Provision() throws RuntimeException
    {
		return (I_UY_Provision)MTable.get(getCtx(), I_UY_Provision.Table_Name)
			.getPO(getUY_Provision_ID(), get_TrxName());	}

	/** Set UY_Provision.
		@param UY_Provision_ID UY_Provision	  */
	public void setUY_Provision_ID (int UY_Provision_ID)
	{
		if (UY_Provision_ID < 1) 
			set_Value (COLUMNNAME_UY_Provision_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Provision_ID, Integer.valueOf(UY_Provision_ID));
	}

	/** Get UY_Provision.
		@return UY_Provision	  */
	public int getUY_Provision_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Provision_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_ProvisionLine getUY_ProvisionLine() throws RuntimeException
    {
		return (I_UY_ProvisionLine)MTable.get(getCtx(), I_UY_ProvisionLine.Table_Name)
			.getPO(getUY_ProvisionLine_ID(), get_TrxName());	}

	/** Set UY_ProvisionLine.
		@param UY_ProvisionLine_ID UY_ProvisionLine	  */
	public void setUY_ProvisionLine_ID (int UY_ProvisionLine_ID)
	{
		if (UY_ProvisionLine_ID < 1) 
			set_Value (COLUMNNAME_UY_ProvisionLine_ID, null);
		else 
			set_Value (COLUMNNAME_UY_ProvisionLine_ID, Integer.valueOf(UY_ProvisionLine_ID));
	}

	/** Get UY_ProvisionLine.
		@return UY_ProvisionLine	  */
	public int getUY_ProvisionLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_ProvisionLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}