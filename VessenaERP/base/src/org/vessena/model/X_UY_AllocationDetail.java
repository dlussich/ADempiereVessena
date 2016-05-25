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
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;

/** Generated Model for UY_AllocationDetail
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_UY_AllocationDetail extends PO implements I_UY_AllocationDetail, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_UY_AllocationDetail (Properties ctx, int UY_AllocationDetail_ID, String trxName)
    {
      super (ctx, UY_AllocationDetail_ID, trxName);
      /** if (UY_AllocationDetail_ID == 0)
        {
			setamtallocated (Env.ZERO);
			setamtinvnativeallocated (Env.ZERO);
			setamtpaynativeallocated (Env.ZERO);
			setc_currency_allocation_id (0);
			setc_currency_invoice_id (0);
			setc_currency_payment_id (0);
			setDateInvoiced (new Timestamp( System.currentTimeMillis() ));
			setdatepayment (new Timestamp( System.currentTimeMillis() ));
			setDateTrx (new Timestamp( System.currentTimeMillis() ));
			setDivideRate (Env.ZERO);
			setIsSOTrx (false);
			setUY_AllocationDetail_ID (0);
			setUY_Allocation_ID (0);
			setUY_AllocationInvoice_ID (0);
			setUY_AllocationPayment_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_AllocationDetail (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_AllocationDetail[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	/** Set amtinvnativeallocated.
		@param amtinvnativeallocated amtinvnativeallocated	  */
	public void setamtinvnativeallocated (BigDecimal amtinvnativeallocated)
	{
		set_Value (COLUMNNAME_amtinvnativeallocated, amtinvnativeallocated);
	}

	/** Get amtinvnativeallocated.
		@return amtinvnativeallocated	  */
	public BigDecimal getamtinvnativeallocated () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtinvnativeallocated);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtpaynativeallocated.
		@param amtpaynativeallocated amtpaynativeallocated	  */
	public void setamtpaynativeallocated (BigDecimal amtpaynativeallocated)
	{
		set_Value (COLUMNNAME_amtpaynativeallocated, amtpaynativeallocated);
	}

	/** Get amtpaynativeallocated.
		@return amtpaynativeallocated	  */
	public BigDecimal getamtpaynativeallocated () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtpaynativeallocated);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set c_currency_allocation_id.
		@param c_currency_allocation_id c_currency_allocation_id	  */
	public void setc_currency_allocation_id (int c_currency_allocation_id)
	{
		set_Value (COLUMNNAME_c_currency_allocation_id, Integer.valueOf(c_currency_allocation_id));
	}

	/** Get c_currency_allocation_id.
		@return c_currency_allocation_id	  */
	public int getc_currency_allocation_id () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_c_currency_allocation_id);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set c_currency_invoice_id.
		@param c_currency_invoice_id c_currency_invoice_id	  */
	public void setc_currency_invoice_id (int c_currency_invoice_id)
	{
		set_Value (COLUMNNAME_c_currency_invoice_id, Integer.valueOf(c_currency_invoice_id));
	}

	/** Get c_currency_invoice_id.
		@return c_currency_invoice_id	  */
	public int getc_currency_invoice_id () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_c_currency_invoice_id);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set c_currency_payment_id.
		@param c_currency_payment_id c_currency_payment_id	  */
	public void setc_currency_payment_id (int c_currency_payment_id)
	{
		set_Value (COLUMNNAME_c_currency_payment_id, Integer.valueOf(c_currency_payment_id));
	}

	/** Get c_currency_payment_id.
		@return c_currency_payment_id	  */
	public int getc_currency_payment_id () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_c_currency_payment_id);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_Invoice getC_Invoice() throws RuntimeException
    {
		return (I_C_Invoice)MTable.get(getCtx(), I_C_Invoice.Table_Name)
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

	public I_C_Payment getC_Payment() throws RuntimeException
    {
		return (I_C_Payment)MTable.get(getCtx(), I_C_Payment.Table_Name)
			.getPO(getC_Payment_ID(), get_TrxName());	}

	/** Set Payment.
		@param C_Payment_ID 
		Payment identifier
	  */
	public void setC_Payment_ID (int C_Payment_ID)
	{
		if (C_Payment_ID < 1) 
			set_Value (COLUMNNAME_C_Payment_ID, null);
		else 
			set_Value (COLUMNNAME_C_Payment_ID, Integer.valueOf(C_Payment_ID));
	}

	/** Get Payment.
		@return Payment identifier
	  */
	public int getC_Payment_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Payment_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set creditnote_id.
		@param creditnote_id creditnote_id	  */
	public void setcreditnote_id (int creditnote_id)
	{
		set_Value (COLUMNNAME_creditnote_id, Integer.valueOf(creditnote_id));
	}

	/** Get creditnote_id.
		@return creditnote_id	  */
	public int getcreditnote_id () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_creditnote_id);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Date Invoiced.
		@param DateInvoiced 
		Date printed on Invoice
	  */
	public void setDateInvoiced (Timestamp DateInvoiced)
	{
		set_Value (COLUMNNAME_DateInvoiced, DateInvoiced);
	}

	/** Get Date Invoiced.
		@return Date printed on Invoice
	  */
	public Timestamp getDateInvoiced () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateInvoiced);
	}

	/** Set datepayment.
		@param datepayment datepayment	  */
	public void setdatepayment (Timestamp datepayment)
	{
		set_Value (COLUMNNAME_datepayment, datepayment);
	}

	/** Get datepayment.
		@return datepayment	  */
	public Timestamp getdatepayment () 
	{
		return (Timestamp)get_Value(COLUMNNAME_datepayment);
	}

	/** Set Transaction Date.
		@param DateTrx 
		Transaction Date
	  */
	public void setDateTrx (Timestamp DateTrx)
	{
		set_Value (COLUMNNAME_DateTrx, DateTrx);
	}

	/** Get Transaction Date.
		@return Transaction Date
	  */
	public Timestamp getDateTrx () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateTrx);
	}

	/** Set Divide Rate.
		@param DivideRate 
		To convert Source number to Target number, the Source is divided
	  */
	public void setDivideRate (BigDecimal DivideRate)
	{
		set_Value (COLUMNNAME_DivideRate, DivideRate);
	}

	/** Get Divide Rate.
		@return To convert Source number to Target number, the Source is divided
	  */
	public BigDecimal getDivideRate () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DivideRate);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Sales Transaction.
		@param IsSOTrx 
		This is a Sales Transaction
	  */
	public void setIsSOTrx (boolean IsSOTrx)
	{
		set_Value (COLUMNNAME_IsSOTrx, Boolean.valueOf(IsSOTrx));
	}

	/** Get Sales Transaction.
		@return This is a Sales Transaction
	  */
	public boolean isSOTrx () 
	{
		Object oo = get_Value(COLUMNNAME_IsSOTrx);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set UY_AllocationDetail.
		@param UY_AllocationDetail_ID UY_AllocationDetail	  */
	public void setUY_AllocationDetail_ID (int UY_AllocationDetail_ID)
	{
		if (UY_AllocationDetail_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_AllocationDetail_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_AllocationDetail_ID, Integer.valueOf(UY_AllocationDetail_ID));
	}

	/** Get UY_AllocationDetail.
		@return UY_AllocationDetail	  */
	public int getUY_AllocationDetail_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_AllocationDetail_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_Allocation getUY_Allocation() throws RuntimeException
    {
		return (I_UY_Allocation)MTable.get(getCtx(), I_UY_Allocation.Table_Name)
			.getPO(getUY_Allocation_ID(), get_TrxName());	}

	/** Set UY_Allocation_ID.
		@param UY_Allocation_ID UY_Allocation_ID	  */
	public void setUY_Allocation_ID (int UY_Allocation_ID)
	{
		if (UY_Allocation_ID < 1) 
			set_Value (COLUMNNAME_UY_Allocation_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Allocation_ID, Integer.valueOf(UY_Allocation_ID));
	}

	/** Get UY_Allocation_ID.
		@return UY_Allocation_ID	  */
	public int getUY_Allocation_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Allocation_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_AllocationInvoice getUY_AllocationInvoice() throws RuntimeException
    {
		return (I_UY_AllocationInvoice)MTable.get(getCtx(), I_UY_AllocationInvoice.Table_Name)
			.getPO(getUY_AllocationInvoice_ID(), get_TrxName());	}

	/** Set UY_AllocationInvoice.
		@param UY_AllocationInvoice_ID UY_AllocationInvoice	  */
	public void setUY_AllocationInvoice_ID (int UY_AllocationInvoice_ID)
	{
		if (UY_AllocationInvoice_ID < 1) 
			set_Value (COLUMNNAME_UY_AllocationInvoice_ID, null);
		else 
			set_Value (COLUMNNAME_UY_AllocationInvoice_ID, Integer.valueOf(UY_AllocationInvoice_ID));
	}

	/** Get UY_AllocationInvoice.
		@return UY_AllocationInvoice	  */
	public int getUY_AllocationInvoice_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_AllocationInvoice_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_AllocationPayment getUY_AllocationPayment() throws RuntimeException
    {
		return (I_UY_AllocationPayment)MTable.get(getCtx(), I_UY_AllocationPayment.Table_Name)
			.getPO(getUY_AllocationPayment_ID(), get_TrxName());	}

	/** Set UY_AllocationPayment.
		@param UY_AllocationPayment_ID UY_AllocationPayment	  */
	public void setUY_AllocationPayment_ID (int UY_AllocationPayment_ID)
	{
		if (UY_AllocationPayment_ID < 1) 
			set_Value (COLUMNNAME_UY_AllocationPayment_ID, null);
		else 
			set_Value (COLUMNNAME_UY_AllocationPayment_ID, Integer.valueOf(UY_AllocationPayment_ID));
	}

	/** Get UY_AllocationPayment.
		@return UY_AllocationPayment	  */
	public int getUY_AllocationPayment_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_AllocationPayment_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}