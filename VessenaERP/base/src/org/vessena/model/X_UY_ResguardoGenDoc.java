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

/** Generated Model for UY_ResguardoGenDoc
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_ResguardoGenDoc extends PO implements I_UY_ResguardoGenDoc, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20160115L;

    /** Standard Constructor */
    public X_UY_ResguardoGenDoc (Properties ctx, int UY_ResguardoGenDoc_ID, String trxName)
    {
      super (ctx, UY_ResguardoGenDoc_ID, trxName);
      /** if (UY_ResguardoGenDoc_ID == 0)
        {
			setC_BPartner_ID (0);
			setC_Currency_ID (0);
			setC_DocType_ID (0);
			setC_Invoice_ID (0);
			setDateInvoiced (new Timestamp( System.currentTimeMillis() ));
			setDocumentNo (null);
			setUY_ResguardoGenDoc_ID (0);
			setUY_ResguardoGen_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_ResguardoGenDoc (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_ResguardoGenDoc[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set AmtIVA.
		@param AmtIVA AmtIVA	  */
	public void setAmtIVA (BigDecimal AmtIVA)
	{
		set_Value (COLUMNNAME_AmtIVA, AmtIVA);
	}

	/** Get AmtIVA.
		@return AmtIVA	  */
	public BigDecimal getAmtIVA () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtIVA);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set AmtIVAMT.
		@param AmtIVAMT AmtIVAMT	  */
	public void setAmtIVAMT (BigDecimal AmtIVAMT)
	{
		set_Value (COLUMNNAME_AmtIVAMT, AmtIVAMT);
	}

	/** Get AmtIVAMT.
		@return AmtIVAMT	  */
	public BigDecimal getAmtIVAMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtIVAMT);
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

	/** Set AmtRetention.
		@param AmtRetention AmtRetention	  */
	public void setAmtRetention (BigDecimal AmtRetention)
	{
		set_Value (COLUMNNAME_AmtRetention, AmtRetention);
	}

	/** Get AmtRetention.
		@return AmtRetention	  */
	public BigDecimal getAmtRetention () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtRetention);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtretentionsource.
		@param amtretentionsource amtretentionsource	  */
	public void setamtretentionsource (BigDecimal amtretentionsource)
	{
		set_Value (COLUMNNAME_amtretentionsource, amtretentionsource);
	}

	/** Get amtretentionsource.
		@return amtretentionsource	  */
	public BigDecimal getamtretentionsource () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtretentionsource);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set Grand Total.
		@param GrandTotal 
		Total amount of document
	  */
	public void setGrandTotal (BigDecimal GrandTotal)
	{
		set_Value (COLUMNNAME_GrandTotal, GrandTotal);
	}

	/** Get Grand Total.
		@return Total amount of document
	  */
	public BigDecimal getGrandTotal () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_GrandTotal);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set GrandTotalSource.
		@param GrandTotalSource GrandTotalSource	  */
	public void setGrandTotalSource (BigDecimal GrandTotalSource)
	{
		set_Value (COLUMNNAME_GrandTotalSource, GrandTotalSource);
	}

	/** Get GrandTotalSource.
		@return GrandTotalSource	  */
	public BigDecimal getGrandTotalSource () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_GrandTotalSource);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Total Lines.
		@param TotalLines 
		Total of all document lines
	  */
	public void setTotalLines (BigDecimal TotalLines)
	{
		set_Value (COLUMNNAME_TotalLines, TotalLines);
	}

	/** Get Total Lines.
		@return Total of all document lines
	  */
	public BigDecimal getTotalLines () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalLines);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set TotalLinesSource.
		@param TotalLinesSource TotalLinesSource	  */
	public void setTotalLinesSource (BigDecimal TotalLinesSource)
	{
		set_Value (COLUMNNAME_TotalLinesSource, TotalLinesSource);
	}

	/** Get TotalLinesSource.
		@return TotalLinesSource	  */
	public BigDecimal getTotalLinesSource () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalLinesSource);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set UY_ResguardoGenDoc.
		@param UY_ResguardoGenDoc_ID UY_ResguardoGenDoc	  */
	public void setUY_ResguardoGenDoc_ID (int UY_ResguardoGenDoc_ID)
	{
		if (UY_ResguardoGenDoc_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_ResguardoGenDoc_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_ResguardoGenDoc_ID, Integer.valueOf(UY_ResguardoGenDoc_ID));
	}

	/** Get UY_ResguardoGenDoc.
		@return UY_ResguardoGenDoc	  */
	public int getUY_ResguardoGenDoc_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_ResguardoGenDoc_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_ResguardoGen getUY_ResguardoGen() throws RuntimeException
    {
		return (I_UY_ResguardoGen)MTable.get(getCtx(), I_UY_ResguardoGen.Table_Name)
			.getPO(getUY_ResguardoGen_ID(), get_TrxName());	}

	/** Set UY_ResguardoGen.
		@param UY_ResguardoGen_ID UY_ResguardoGen	  */
	public void setUY_ResguardoGen_ID (int UY_ResguardoGen_ID)
	{
		if (UY_ResguardoGen_ID < 1) 
			set_Value (COLUMNNAME_UY_ResguardoGen_ID, null);
		else 
			set_Value (COLUMNNAME_UY_ResguardoGen_ID, Integer.valueOf(UY_ResguardoGen_ID));
	}

	/** Get UY_ResguardoGen.
		@return UY_ResguardoGen	  */
	public int getUY_ResguardoGen_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_ResguardoGen_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}