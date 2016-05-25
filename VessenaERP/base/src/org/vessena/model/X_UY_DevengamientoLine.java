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

/** Generated Model for UY_DevengamientoLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_DevengamientoLine extends PO implements I_UY_DevengamientoLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20121129L;

    /** Standard Constructor */
    public X_UY_DevengamientoLine (Properties ctx, int UY_DevengamientoLine_ID, String trxName)
    {
      super (ctx, UY_DevengamientoLine_ID, trxName);
      /** if (UY_DevengamientoLine_ID == 0)
        {
			setActualQuote (0);
			setC_DocType_ID (0);
			setC_Invoice_ID (0);
			setDateTrx (new Timestamp( System.currentTimeMillis() ));
			setIsApproved (false);
// N
			setUY_Devengamiento_ID (0);
			setUY_DevengamientoLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_DevengamientoLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_DevengamientoLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set ActualQuote.
		@param ActualQuote ActualQuote	  */
	public void setActualQuote (int ActualQuote)
	{
		set_Value (COLUMNNAME_ActualQuote, Integer.valueOf(ActualQuote));
	}

	/** Get ActualQuote.
		@return ActualQuote	  */
	public int getActualQuote () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ActualQuote);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	public org.compiere.model.I_C_Period getC_Period_ID_F() throws RuntimeException
    {
		return (org.compiere.model.I_C_Period)MTable.get(getCtx(), org.compiere.model.I_C_Period.Table_Name)
			.getPO(getC_Period_ID_From(), get_TrxName());	}

	/** Set C_Period_ID_From.
		@param C_Period_ID_From C_Period_ID_From	  */
	public void setC_Period_ID_From (int C_Period_ID_From)
	{
		set_Value (COLUMNNAME_C_Period_ID_From, Integer.valueOf(C_Period_ID_From));
	}

	/** Get C_Period_ID_From.
		@return C_Period_ID_From	  */
	public int getC_Period_ID_From () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Period_ID_From);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_Period getC_Period_To() throws RuntimeException
    {
		return (org.compiere.model.I_C_Period)MTable.get(getCtx(), org.compiere.model.I_C_Period.Table_Name)
			.getPO(getC_Period_ID_To(), get_TrxName());	}

	/** Set C_Period_ID_To.
		@param C_Period_ID_To C_Period_ID_To	  */
	public void setC_Period_ID_To (int C_Period_ID_To)
	{
		set_Value (COLUMNNAME_C_Period_ID_To, Integer.valueOf(C_Period_ID_To));
	}

	/** Get C_Period_ID_To.
		@return C_Period_ID_To	  */
	public int getC_Period_ID_To () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Period_ID_To);
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

	/** Set Approved.
		@param IsApproved 
		Indicates if this document requires approval
	  */
	public void setIsApproved (boolean IsApproved)
	{
		set_Value (COLUMNNAME_IsApproved, Boolean.valueOf(IsApproved));
	}

	/** Get Approved.
		@return Indicates if this document requires approval
	  */
	public boolean isApproved () 
	{
		Object oo = get_Value(COLUMNNAME_IsApproved);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set QtyQuote.
		@param QtyQuote QtyQuote	  */
	public void setQtyQuote (BigDecimal QtyQuote)
	{
		set_Value (COLUMNNAME_QtyQuote, QtyQuote);
	}

	/** Get QtyQuote.
		@return QtyQuote	  */
	public BigDecimal getQtyQuote () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyQuote);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set QuoteAmt.
		@param QuoteAmt QuoteAmt	  */
	public void setQuoteAmt (BigDecimal QuoteAmt)
	{
		set_Value (COLUMNNAME_QuoteAmt, QuoteAmt);
	}

	/** Get QuoteAmt.
		@return QuoteAmt	  */
	public BigDecimal getQuoteAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QuoteAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set UY_Devengamiento.
		@param UY_Devengamiento_ID UY_Devengamiento	  */
	public void setUY_Devengamiento_ID (int UY_Devengamiento_ID)
	{
		if (UY_Devengamiento_ID < 1) 
			set_Value (COLUMNNAME_UY_Devengamiento_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Devengamiento_ID, Integer.valueOf(UY_Devengamiento_ID));
	}

	/** Get UY_Devengamiento.
		@return UY_Devengamiento	  */
	public int getUY_Devengamiento_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Devengamiento_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_DevengamientoLine.
		@param UY_DevengamientoLine_ID UY_DevengamientoLine	  */
	public void setUY_DevengamientoLine_ID (int UY_DevengamientoLine_ID)
	{
		if (UY_DevengamientoLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_DevengamientoLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_DevengamientoLine_ID, Integer.valueOf(UY_DevengamientoLine_ID));
	}

	/** Get UY_DevengamientoLine.
		@return UY_DevengamientoLine	  */
	public int getUY_DevengamientoLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_DevengamientoLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}