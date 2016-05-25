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
import org.compiere.util.KeyNamePair;

/** Generated Model for UY_InvoiceBook
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS - $Id$ */
public class X_UY_InvoiceBook extends PO implements I_UY_InvoiceBook, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130204L;

    /** Standard Constructor */
    public X_UY_InvoiceBook (Properties ctx, int UY_InvoiceBook_ID, String trxName)
    {
      super (ctx, UY_InvoiceBook_ID, trxName);
      /** if (UY_InvoiceBook_ID == 0)
        {
			setCopies (0);
// 3
			setDateStart (new Timestamp( System.currentTimeMillis() ));
			setDocumentNoEnd (0);
			setDocumentNoStart (0);
			setDueDate (new Timestamp( System.currentTimeMillis() ));
			setName (null);
			setProcessed (false);
			setUY_InvoiceBook_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_InvoiceBook (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_InvoiceBook[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Copies.
		@param Copies Copies	  */
	public void setCopies (int Copies)
	{
		set_Value (COLUMNNAME_Copies, Integer.valueOf(Copies));
	}

	/** Get Copies.
		@return Copies	  */
	public int getCopies () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Copies);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Counter.
		@param Counter 
		Count Value
	  */
	public void setCounter (int Counter)
	{
		throw new IllegalArgumentException ("Counter is virtual column");	}

	/** Get Counter.
		@return Count Value
	  */
	public int getCounter () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Counter);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Date Start.
		@param DateStart 
		Date Start for this Order
	  */
	public void setDateStart (Timestamp DateStart)
	{
		set_Value (COLUMNNAME_DateStart, DateStart);
	}

	/** Get Date Start.
		@return Date Start for this Order
	  */
	public Timestamp getDateStart () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateStart);
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

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	public void setName (String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName () 
	{
		return (String)get_Value(COLUMNNAME_Name);
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getName());
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

	/** Set UY_InvoiceBook.
		@param UY_InvoiceBook_ID UY_InvoiceBook	  */
	public void setUY_InvoiceBook_ID (int UY_InvoiceBook_ID)
	{
		if (UY_InvoiceBook_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_InvoiceBook_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_InvoiceBook_ID, Integer.valueOf(UY_InvoiceBook_ID));
	}

	/** Get UY_InvoiceBook.
		@return UY_InvoiceBook	  */
	public int getUY_InvoiceBook_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_InvoiceBook_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_RepiteNumero.
		@param UY_RepiteNumero UY_RepiteNumero	  */
	public void setUY_RepiteNumero (boolean UY_RepiteNumero)
	{
		set_Value (COLUMNNAME_UY_RepiteNumero, Boolean.valueOf(UY_RepiteNumero));
	}

	/** Get UY_RepiteNumero.
		@return UY_RepiteNumero	  */
	public boolean isUY_RepiteNumero () 
	{
		Object oo = get_Value(COLUMNNAME_UY_RepiteNumero);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}
}