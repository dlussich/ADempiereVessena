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

/** Generated Model for C_InvoicePrintFlete
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_C_InvoicePrintFlete extends PO implements I_C_InvoicePrintFlete, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150112L;

    /** Standard Constructor */
    public X_C_InvoicePrintFlete (Properties ctx, int C_InvoicePrintFlete_ID, String trxName)
    {
      super (ctx, C_InvoicePrintFlete_ID, trxName);
      /** if (C_InvoicePrintFlete_ID == 0)
        {
			setC_Invoice_ID (0);
			setC_InvoicePrintFlete_ID (0);
        } */
    }

    /** Load Constructor */
    public X_C_InvoicePrintFlete (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_C_InvoicePrintFlete[")
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

	/** Set amt2.
		@param amt2 amt2	  */
	public void setamt2 (BigDecimal amt2)
	{
		set_Value (COLUMNNAME_amt2, amt2);
	}

	/** Get amt2.
		@return amt2	  */
	public BigDecimal getamt2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amt2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amt3.
		@param amt3 amt3	  */
	public void setamt3 (BigDecimal amt3)
	{
		set_Value (COLUMNNAME_amt3, amt3);
	}

	/** Get amt3.
		@return amt3	  */
	public BigDecimal getamt3 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amt3);
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

	/** Set C_Currency_ID_2.
		@param C_Currency_ID_2 C_Currency_ID_2	  */
	public void setC_Currency_ID_2 (int C_Currency_ID_2)
	{
		set_Value (COLUMNNAME_C_Currency_ID_2, Integer.valueOf(C_Currency_ID_2));
	}

	/** Get C_Currency_ID_2.
		@return C_Currency_ID_2	  */
	public int getC_Currency_ID_2 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency_ID_2);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set C_Currency_ID_3.
		@param C_Currency_ID_3 C_Currency_ID_3	  */
	public void setC_Currency_ID_3 (int C_Currency_ID_3)
	{
		set_Value (COLUMNNAME_C_Currency_ID_3, Integer.valueOf(C_Currency_ID_3));
	}

	/** Get C_Currency_ID_3.
		@return C_Currency_ID_3	  */
	public int getC_Currency_ID_3 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency_ID_3);
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

	/** Set C_InvoicePrintFlete.
		@param C_InvoicePrintFlete_ID C_InvoicePrintFlete	  */
	public void setC_InvoicePrintFlete_ID (int C_InvoicePrintFlete_ID)
	{
		if (C_InvoicePrintFlete_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_InvoicePrintFlete_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_InvoicePrintFlete_ID, Integer.valueOf(C_InvoicePrintFlete_ID));
	}

	/** Get C_InvoicePrintFlete.
		@return C_InvoicePrintFlete	  */
	public int getC_InvoicePrintFlete_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_InvoicePrintFlete_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TR_PrintFlete getUY_TR_PrintFlete() throws RuntimeException
    {
		return (I_UY_TR_PrintFlete)MTable.get(getCtx(), I_UY_TR_PrintFlete.Table_Name)
			.getPO(getUY_TR_PrintFlete_ID(), get_TrxName());	}

	/** Set UY_TR_PrintFlete.
		@param UY_TR_PrintFlete_ID UY_TR_PrintFlete	  */
	public void setUY_TR_PrintFlete_ID (int UY_TR_PrintFlete_ID)
	{
		if (UY_TR_PrintFlete_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_PrintFlete_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_PrintFlete_ID, Integer.valueOf(UY_TR_PrintFlete_ID));
	}

	/** Get UY_TR_PrintFlete.
		@return UY_TR_PrintFlete	  */
	public int getUY_TR_PrintFlete_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_PrintFlete_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}