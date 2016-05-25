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

/** Generated Model for UY_TR_ClearingPayment
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_TR_ClearingPayment extends PO implements I_UY_TR_ClearingPayment, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150323L;

    /** Standard Constructor */
    public X_UY_TR_ClearingPayment (Properties ctx, int UY_TR_ClearingPayment_ID, String trxName)
    {
      super (ctx, UY_TR_ClearingPayment_ID, trxName);
      /** if (UY_TR_ClearingPayment_ID == 0)
        {
			setC_Payment_ID (0);
			setIsExecuted (false);
			setIsSelected (false);
			setUY_TR_Clearing_ID (0);
			setUY_TR_ClearingPayment_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_TR_ClearingPayment (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_TR_ClearingPayment[")
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

	/** Set amtdocument.
		@param amtdocument amtdocument	  */
	public void setamtdocument (BigDecimal amtdocument)
	{
		set_Value (COLUMNNAME_amtdocument, amtdocument);
	}

	/** Get amtdocument.
		@return amtdocument	  */
	public BigDecimal getamtdocument () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtdocument);
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

	public org.compiere.model.I_C_Payment getC_Payment() throws RuntimeException
    {
		return (org.compiere.model.I_C_Payment)MTable.get(getCtx(), org.compiere.model.I_C_Payment.Table_Name)
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

	/** Set IsExecuted.
		@param IsExecuted IsExecuted	  */
	public void setIsExecuted (boolean IsExecuted)
	{
		set_Value (COLUMNNAME_IsExecuted, Boolean.valueOf(IsExecuted));
	}

	/** Get IsExecuted.
		@return IsExecuted	  */
	public boolean isExecuted () 
	{
		Object oo = get_Value(COLUMNNAME_IsExecuted);
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

	public I_UY_TR_Clearing getUY_TR_Clearing() throws RuntimeException
    {
		return (I_UY_TR_Clearing)MTable.get(getCtx(), I_UY_TR_Clearing.Table_Name)
			.getPO(getUY_TR_Clearing_ID(), get_TrxName());	}

	/** Set UY_TR_Clearing.
		@param UY_TR_Clearing_ID UY_TR_Clearing	  */
	public void setUY_TR_Clearing_ID (int UY_TR_Clearing_ID)
	{
		if (UY_TR_Clearing_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_Clearing_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_Clearing_ID, Integer.valueOf(UY_TR_Clearing_ID));
	}

	/** Get UY_TR_Clearing.
		@return UY_TR_Clearing	  */
	public int getUY_TR_Clearing_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Clearing_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TR_ClearingPayment.
		@param UY_TR_ClearingPayment_ID UY_TR_ClearingPayment	  */
	public void setUY_TR_ClearingPayment_ID (int UY_TR_ClearingPayment_ID)
	{
		if (UY_TR_ClearingPayment_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_TR_ClearingPayment_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_TR_ClearingPayment_ID, Integer.valueOf(UY_TR_ClearingPayment_ID));
	}

	/** Get UY_TR_ClearingPayment.
		@return UY_TR_ClearingPayment	  */
	public int getUY_TR_ClearingPayment_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_ClearingPayment_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}