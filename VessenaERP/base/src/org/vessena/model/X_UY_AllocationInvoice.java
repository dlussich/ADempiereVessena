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

/** Generated Model for UY_AllocationInvoice
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_UY_AllocationInvoice extends PO implements I_UY_AllocationInvoice, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_UY_AllocationInvoice (Properties ctx, int UY_AllocationInvoice_ID, String trxName)
    {
      super (ctx, UY_AllocationInvoice_ID, trxName);
      /** if (UY_AllocationInvoice_ID == 0)
        {
			setamtallocated (Env.ZERO);
			setamtdocument (Env.ZERO);
			setamtopen (Env.ZERO);
			setC_Currency_ID (0);
			setC_DocType_ID (0);
			setdatedocument (new Timestamp( System.currentTimeMillis() ));
			setDocumentNo (null);
			setProcessed (false);
// N
			setUY_Allocation_ID (0);
			setUY_AllocationInvoice_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_AllocationInvoice (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_AllocationInvoice[")
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

	public I_C_DocType getC_DocType() throws RuntimeException
    {
		return (I_C_DocType)MTable.get(getCtx(), I_C_DocType.Table_Name)
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

	/** Set datedocument.
		@param datedocument datedocument	  */
	public void setdatedocument (Timestamp datedocument)
	{
		set_Value (COLUMNNAME_datedocument, datedocument);
	}

	/** Get datedocument.
		@return datedocument	  */
	public Timestamp getdatedocument () 
	{
		return (Timestamp)get_Value(COLUMNNAME_datedocument);
	}

	/** Set Document No.
		@param DocumentNo 
		Document sequence number of the document
	  */
	public void setDocumentNo (String DocumentNo)
	{
		set_Value (COLUMNNAME_DocumentNo, DocumentNo);
	}

	/** Get Document No.
		@return Document sequence number of the document
	  */
	public String getDocumentNo () 
	{
		return (String)get_Value(COLUMNNAME_DocumentNo);
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

	/** Set UY_AllocationInvoice.
		@param UY_AllocationInvoice_ID UY_AllocationInvoice	  */
	public void setUY_AllocationInvoice_ID (int UY_AllocationInvoice_ID)
	{
		if (UY_AllocationInvoice_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_AllocationInvoice_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_AllocationInvoice_ID, Integer.valueOf(UY_AllocationInvoice_ID));
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
}