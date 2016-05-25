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

/** Generated Model for UY_ReceiptBook
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_UY_ReceiptBook extends PO implements I_UY_ReceiptBook, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_UY_ReceiptBook (Properties ctx, int UY_ReceiptBook_ID, String trxName)
    {
      super (ctx, UY_ReceiptBook_ID, trxName);
      /** if (UY_ReceiptBook_ID == 0)
        {
			setName (null);
			setUY_ReceiptBook_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_ReceiptBook (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_ReceiptBook[")
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

	/** Set DateStart.
		@param DateStart DateStart	  */
	public void setDateStart (Timestamp DateStart)
	{
		set_Value (COLUMNNAME_DateStart, DateStart);
	}

	/** Get DateStart.
		@return DateStart	  */
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

	/** Set serie.
		@param serie serie	  */
	public void setserie (String serie)
	{
		set_Value (COLUMNNAME_serie, serie);
	}

	/** Get serie.
		@return serie	  */
	public String getserie () 
	{
		return (String)get_Value(COLUMNNAME_serie);
	}

	/** Set UY_ReceiptBook.
		@param UY_ReceiptBook_ID UY_ReceiptBook	  */
	public void setUY_ReceiptBook_ID (int UY_ReceiptBook_ID)
	{
		if (UY_ReceiptBook_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_ReceiptBook_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_ReceiptBook_ID, Integer.valueOf(UY_ReceiptBook_ID));
	}

	/** Get UY_ReceiptBook.
		@return UY_ReceiptBook	  */
	public int getUY_ReceiptBook_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_ReceiptBook_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}