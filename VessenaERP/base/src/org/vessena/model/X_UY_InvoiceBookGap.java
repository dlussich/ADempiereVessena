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

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;

/** Generated Model for UY_InvoiceBookGap
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_UY_InvoiceBookGap extends PO implements I_UY_InvoiceBookGap, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_UY_InvoiceBookGap (Properties ctx, int UY_InvoiceBookGap_ID, String trxName)
    {
      super (ctx, UY_InvoiceBookGap_ID, trxName);
      /** if (UY_InvoiceBookGap_ID == 0)
        {
			setUY_InvoiceBookControl_ID (0);
			setUY_InvoiceBookGap_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_InvoiceBookGap (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_InvoiceBookGap[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	/** Set Date Invoiced.
		@param DateInvoiced 
		Date printed on Invoice
	  */
	public void setDateInvoiced (Timestamp DateInvoiced)
	{
		throw new IllegalArgumentException ("DateInvoiced is virtual column");	}

	/** Get Date Invoiced.
		@return Date printed on Invoice
	  */
	public Timestamp getDateInvoiced () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateInvoiced);
	}

	/** Set End document number.
		@param DocumentNoEnd 
		End document number
	  */
	public void setDocumentNoEnd (int DocumentNoEnd)
	{
		set_Value (COLUMNNAME_DocumentNoEnd, Integer.valueOf(DocumentNoEnd));
	}

	/** Get End document number.
		@return End document number
	  */
	public int getDocumentNoEnd () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DocumentNoEnd);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Start document number.
		@param DocumentNoStart 
		Start document number
	  */
	public void setDocumentNoStart (int DocumentNoStart)
	{
		set_Value (COLUMNNAME_DocumentNoStart, Integer.valueOf(DocumentNoStart));
	}

	/** Get Start document number.
		@return Start document number
	  */
	public int getDocumentNoStart () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DocumentNoStart);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_InvoiceBookControl getUY_InvoiceBookControl() throws RuntimeException
    {
		return (I_UY_InvoiceBookControl)MTable.get(getCtx(), I_UY_InvoiceBookControl.Table_Name)
			.getPO(getUY_InvoiceBookControl_ID(), get_TrxName());	}

	/** Set UY_InvoiceBookControl.
		@param UY_InvoiceBookControl_ID UY_InvoiceBookControl	  */
	public void setUY_InvoiceBookControl_ID (int UY_InvoiceBookControl_ID)
	{
		if (UY_InvoiceBookControl_ID < 1) 
			set_Value (COLUMNNAME_UY_InvoiceBookControl_ID, null);
		else 
			set_Value (COLUMNNAME_UY_InvoiceBookControl_ID, Integer.valueOf(UY_InvoiceBookControl_ID));
	}

	/** Get UY_InvoiceBookControl.
		@return UY_InvoiceBookControl	  */
	public int getUY_InvoiceBookControl_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_InvoiceBookControl_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_InvoiceBookGap.
		@param UY_InvoiceBookGap_ID UY_InvoiceBookGap	  */
	public void setUY_InvoiceBookGap_ID (int UY_InvoiceBookGap_ID)
	{
		if (UY_InvoiceBookGap_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_InvoiceBookGap_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_InvoiceBookGap_ID, Integer.valueOf(UY_InvoiceBookGap_ID));
	}

	/** Get UY_InvoiceBookGap.
		@return UY_InvoiceBookGap	  */
	public int getUY_InvoiceBookGap_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_InvoiceBookGap_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}