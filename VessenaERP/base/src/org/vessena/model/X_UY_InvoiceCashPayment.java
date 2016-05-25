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

/** Generated Model for UY_InvoiceCashPayment
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_InvoiceCashPayment extends PO implements I_UY_InvoiceCashPayment, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130514L;

    /** Standard Constructor */
    public X_UY_InvoiceCashPayment (Properties ctx, int UY_InvoiceCashPayment_ID, String trxName)
    {
      super (ctx, UY_InvoiceCashPayment_ID, trxName);
      /** if (UY_InvoiceCashPayment_ID == 0)
        {
			setC_BankAccount_ID (0);
			setC_Invoice_ID (0);
			setPayAmt (Env.ZERO);
			setUY_InvoiceCashPayment_ID (0);
			setUY_PaymentRule_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_InvoiceCashPayment (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_InvoiceCashPayment[")
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

	public org.compiere.model.I_C_BankAccount getC_BankAccount() throws RuntimeException
    {
		return (org.compiere.model.I_C_BankAccount)MTable.get(getCtx(), org.compiere.model.I_C_BankAccount.Table_Name)
			.getPO(getC_BankAccount_ID(), get_TrxName());	}

	/** Set Bank Account.
		@param C_BankAccount_ID 
		Account at the Bank
	  */
	public void setC_BankAccount_ID (int C_BankAccount_ID)
	{
		if (C_BankAccount_ID < 1) 
			set_Value (COLUMNNAME_C_BankAccount_ID, null);
		else 
			set_Value (COLUMNNAME_C_BankAccount_ID, Integer.valueOf(C_BankAccount_ID));
	}

	/** Get Bank Account.
		@return Account at the Bank
	  */
	public int getC_BankAccount_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BankAccount_ID);
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

	/** Set Rate.
		@param CurrencyRate 
		Currency Conversion Rate
	  */
	public void setCurrencyRate (BigDecimal CurrencyRate)
	{
		set_Value (COLUMNNAME_CurrencyRate, CurrencyRate);
	}

	/** Get Rate.
		@return Currency Conversion Rate
	  */
	public BigDecimal getCurrencyRate () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CurrencyRate);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Date From.
		@param DateFrom 
		Starting date for a range
	  */
	public void setDateFrom (Timestamp DateFrom)
	{
		set_Value (COLUMNNAME_DateFrom, DateFrom);
	}

	/** Get Date From.
		@return Starting date for a range
	  */
	public Timestamp getDateFrom () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateFrom);
	}

	/** Set Due Date.
		@param DueDate 
		Date when the payment is due
	  */
	public void setDueDate (Timestamp DueDate)
	{
		set_Value (COLUMNNAME_DueDate, DueDate);
	}

	/** Get Due Date.
		@return Date when the payment is due
	  */
	public Timestamp getDueDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DueDate);
	}

	/** Set nrodoc.
		@param nrodoc nrodoc	  */
	public void setnrodoc (String nrodoc)
	{
		set_Value (COLUMNNAME_nrodoc, nrodoc);
	}

	/** Get nrodoc.
		@return nrodoc	  */
	public String getnrodoc () 
	{
		return (String)get_Value(COLUMNNAME_nrodoc);
	}

	/** Set Payment amount.
		@param PayAmt 
		Amount being paid
	  */
	public void setPayAmt (BigDecimal PayAmt)
	{
		set_Value (COLUMNNAME_PayAmt, PayAmt);
	}

	/** Get Payment amount.
		@return Amount being paid
	  */
	public BigDecimal getPayAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PayAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Tender type.
		@param TenderType 
		Method of Payment
	  */
	public void setTenderType (String TenderType)
	{
		set_Value (COLUMNNAME_TenderType, TenderType);
	}

	/** Get Tender type.
		@return Method of Payment
	  */
	public String getTenderType () 
	{
		return (String)get_Value(COLUMNNAME_TenderType);
	}

	/** Set UY_InvoiceCashPayment.
		@param UY_InvoiceCashPayment_ID UY_InvoiceCashPayment	  */
	public void setUY_InvoiceCashPayment_ID (int UY_InvoiceCashPayment_ID)
	{
		if (UY_InvoiceCashPayment_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_InvoiceCashPayment_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_InvoiceCashPayment_ID, Integer.valueOf(UY_InvoiceCashPayment_ID));
	}

	/** Get UY_InvoiceCashPayment.
		@return UY_InvoiceCashPayment	  */
	public int getUY_InvoiceCashPayment_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_InvoiceCashPayment_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_MediosPago getUY_MediosPago() throws RuntimeException
    {
		return (I_UY_MediosPago)MTable.get(getCtx(), I_UY_MediosPago.Table_Name)
			.getPO(getUY_MediosPago_ID(), get_TrxName());	}

	/** Set Medios de Pago.
		@param UY_MediosPago_ID Medios de Pago	  */
	public void setUY_MediosPago_ID (int UY_MediosPago_ID)
	{
		if (UY_MediosPago_ID < 1) 
			set_Value (COLUMNNAME_UY_MediosPago_ID, null);
		else 
			set_Value (COLUMNNAME_UY_MediosPago_ID, Integer.valueOf(UY_MediosPago_ID));
	}

	/** Get Medios de Pago.
		@return Medios de Pago	  */
	public int getUY_MediosPago_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_MediosPago_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_PaymentRule getUY_PaymentRule() throws RuntimeException
    {
		return (I_UY_PaymentRule)MTable.get(getCtx(), I_UY_PaymentRule.Table_Name)
			.getPO(getUY_PaymentRule_ID(), get_TrxName());	}

	/** Set UY_PaymentRule.
		@param UY_PaymentRule_ID UY_PaymentRule	  */
	public void setUY_PaymentRule_ID (int UY_PaymentRule_ID)
	{
		if (UY_PaymentRule_ID < 1) 
			set_Value (COLUMNNAME_UY_PaymentRule_ID, null);
		else 
			set_Value (COLUMNNAME_UY_PaymentRule_ID, Integer.valueOf(UY_PaymentRule_ID));
	}

	/** Get UY_PaymentRule.
		@return UY_PaymentRule	  */
	public int getUY_PaymentRule_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_PaymentRule_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}