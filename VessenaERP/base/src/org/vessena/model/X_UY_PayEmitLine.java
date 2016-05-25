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
import org.compiere.util.KeyNamePair;

/** Generated Model for UY_PayEmitLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_PayEmitLine extends PO implements I_UY_PayEmitLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20151110L;

    /** Standard Constructor */
    public X_UY_PayEmitLine (Properties ctx, int UY_PayEmitLine_ID, String trxName)
    {
      super (ctx, UY_PayEmitLine_ID, trxName);
      /** if (UY_PayEmitLine_ID == 0)
        {
			setC_BPartner_ID (0);
			setC_Currency_ID (0);
			setDateTrx (new Timestamp( System.currentTimeMillis() ));
			setIsPayEmit (false);
// N
			setIsResma (false);
// N
			setIsSelected (true);
// Y
			setIsStocked (false);
// N
			setPayAmt (Env.ZERO);
			setUY_PayEmit_ID (0);
			setUY_PayEmitLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_PayEmitLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_PayEmitLine[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_Name)
			.getPO(getC_BPartner_ID(), get_TrxName());	}

	/** Set Business Partner .
		@param C_BPartner_ID 
		Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Business Partner .
		@return Identifies a Business Partner
	  */
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getC_BPartner_ID()));
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

	public org.compiere.model.I_C_DocType getC_DocType() throws RuntimeException
    {
		return (org.compiere.model.I_C_DocType)MTable.get(getCtx(), org.compiere.model.I_C_DocType.Table_Name)
			.getPO(getC_DocType_ID(), get_TrxName());	}

	/** Set Document Type.
		@param C_DocType_ID 
		Document type or rules
	  */
	public void setC_DocType_ID (int C_DocType_ID)
	{
		if (C_DocType_ID < 0) 
			set_Value (COLUMNNAME_C_DocType_ID, null);
		else 
			set_Value (COLUMNNAME_C_DocType_ID, Integer.valueOf(C_DocType_ID));
	}

	/** Get Document Type.
		@return Document type or rules
	  */
	public int getC_DocType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DocType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Due Date.
		@param DueDate 
		Date when the payment is due
	  */
	public void setDueDate (Timestamp DueDate)
	{
		set_ValueNoCheck (COLUMNNAME_DueDate, DueDate);
	}

	/** Get Due Date.
		@return Date when the payment is due
	  */
	public Timestamp getDueDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DueDate);
	}

	/** Set InternalCode.
		@param InternalCode InternalCode	  */
	public void setInternalCode (String InternalCode)
	{
		set_Value (COLUMNNAME_InternalCode, InternalCode);
	}

	/** Get InternalCode.
		@return InternalCode	  */
	public String getInternalCode () 
	{
		return (String)get_Value(COLUMNNAME_InternalCode);
	}

	/** Set IsPayEmit.
		@param IsPayEmit IsPayEmit	  */
	public void setIsPayEmit (boolean IsPayEmit)
	{
		set_Value (COLUMNNAME_IsPayEmit, Boolean.valueOf(IsPayEmit));
	}

	/** Get IsPayEmit.
		@return IsPayEmit	  */
	public boolean isPayEmit () 
	{
		Object oo = get_Value(COLUMNNAME_IsPayEmit);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsResma.
		@param IsResma IsResma	  */
	public void setIsResma (boolean IsResma)
	{
		set_Value (COLUMNNAME_IsResma, Boolean.valueOf(IsResma));
	}

	/** Get IsResma.
		@return IsResma	  */
	public boolean isResma () 
	{
		Object oo = get_Value(COLUMNNAME_IsResma);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Selected.
		@param IsSelected Selected	  */
	public void setIsSelected (boolean IsSelected)
	{
		set_Value (COLUMNNAME_IsSelected, Boolean.valueOf(IsSelected));
	}

	/** Get Selected.
		@return Selected	  */
	public boolean isSelected () 
	{
		Object oo = get_Value(COLUMNNAME_IsSelected);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Stocked.
		@param IsStocked 
		Organization stocks this product
	  */
	public void setIsStocked (boolean IsStocked)
	{
		set_Value (COLUMNNAME_IsStocked, Boolean.valueOf(IsStocked));
	}

	/** Get Stocked.
		@return Organization stocks this product
	  */
	public boolean isStocked () 
	{
		Object oo = get_Value(COLUMNNAME_IsStocked);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	public I_UY_CheckReamLine getUY_CheckReamLine() throws RuntimeException
    {
		return (I_UY_CheckReamLine)MTable.get(getCtx(), I_UY_CheckReamLine.Table_Name)
			.getPO(getUY_CheckReamLine_ID(), get_TrxName());	}

	/** Set UY_CheckReamLine.
		@param UY_CheckReamLine_ID UY_CheckReamLine	  */
	public void setUY_CheckReamLine_ID (int UY_CheckReamLine_ID)
	{
		if (UY_CheckReamLine_ID < 1) 
			set_Value (COLUMNNAME_UY_CheckReamLine_ID, null);
		else 
			set_Value (COLUMNNAME_UY_CheckReamLine_ID, Integer.valueOf(UY_CheckReamLine_ID));
	}

	/** Get UY_CheckReamLine.
		@return UY_CheckReamLine	  */
	public int getUY_CheckReamLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_CheckReamLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_PayEmit getUY_PayEmit() throws RuntimeException
    {
		return (I_UY_PayEmit)MTable.get(getCtx(), I_UY_PayEmit.Table_Name)
			.getPO(getUY_PayEmit_ID(), get_TrxName());	}

	/** Set UY_PayEmit.
		@param UY_PayEmit_ID UY_PayEmit	  */
	public void setUY_PayEmit_ID (int UY_PayEmit_ID)
	{
		if (UY_PayEmit_ID < 1) 
			set_Value (COLUMNNAME_UY_PayEmit_ID, null);
		else 
			set_Value (COLUMNNAME_UY_PayEmit_ID, Integer.valueOf(UY_PayEmit_ID));
	}

	/** Get UY_PayEmit.
		@return UY_PayEmit	  */
	public int getUY_PayEmit_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_PayEmit_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_PayEmitLine.
		@param UY_PayEmitLine_ID UY_PayEmitLine	  */
	public void setUY_PayEmitLine_ID (int UY_PayEmitLine_ID)
	{
		if (UY_PayEmitLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_PayEmitLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_PayEmitLine_ID, Integer.valueOf(UY_PayEmitLine_ID));
	}

	/** Get UY_PayEmitLine.
		@return UY_PayEmitLine	  */
	public int getUY_PayEmitLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_PayEmitLine_ID);
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

	public I_UY_PayOrder getUY_PayOrder() throws RuntimeException
    {
		return (I_UY_PayOrder)MTable.get(getCtx(), I_UY_PayOrder.Table_Name)
			.getPO(getUY_PayOrder_ID(), get_TrxName());	}

	/** Set UY_PayOrder.
		@param UY_PayOrder_ID UY_PayOrder	  */
	public void setUY_PayOrder_ID (int UY_PayOrder_ID)
	{
		if (UY_PayOrder_ID < 1) 
			set_Value (COLUMNNAME_UY_PayOrder_ID, null);
		else 
			set_Value (COLUMNNAME_UY_PayOrder_ID, Integer.valueOf(UY_PayOrder_ID));
	}

	/** Get UY_PayOrder.
		@return UY_PayOrder	  */
	public int getUY_PayOrder_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_PayOrder_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}