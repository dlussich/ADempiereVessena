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

/** Generated Model for UY_InvoiceBookControl
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_UY_InvoiceBookControl extends PO implements I_UY_InvoiceBookControl, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_UY_InvoiceBookControl (Properties ctx, int UY_InvoiceBookControl_ID, String trxName)
    {
      super (ctx, UY_InvoiceBookControl_ID, trxName);
      /** if (UY_InvoiceBookControl_ID == 0)
        {
			setDateFinish (new Timestamp( System.currentTimeMillis() ));
// @#Date@
			setDocumentNoEnd (0);
			setUY_InvoiceBook_ID (0);
			setUY_InvoiceBookControl_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_InvoiceBookControl (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_InvoiceBookControl[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Control.
		@param Control Control	  */
	public void setControl (int Control)
	{
		throw new IllegalArgumentException ("Control is virtual column");	}

	/** Get Control.
		@return Control	  */
	public int getControl () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Control);
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
		set_ValueNoCheck (COLUMNNAME_Counter, Integer.valueOf(Counter));
	}

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

	/** Set Finish Date.
		@param DateFinish 
		Finish or (planned) completion date
	  */
	public void setDateFinish (Timestamp DateFinish)
	{
		set_Value (COLUMNNAME_DateFinish, DateFinish);
	}

	/** Get Finish Date.
		@return Finish or (planned) completion date
	  */
	public Timestamp getDateFinish () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateFinish);
	}

	/** Set DateStart.
		@param DateStart DateStart	  */
	public void setDateStart (Timestamp DateStart)
	{
		set_ValueNoCheck (COLUMNNAME_DateStart, DateStart);
	}

	/** Get DateStart.
		@return DateStart	  */
	public Timestamp getDateStart () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateStart);
	}

	/** Set Diference.
		@param Diference Diference	  */
	public void setDiference (int Diference)
	{
		throw new IllegalArgumentException ("Diference is virtual column");	}

	/** Get Diference.
		@return Diference	  */
	public int getDiference () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Diference);
		if (ii == null)
			 return 0;
		return ii.intValue();
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
		set_ValueNoCheck (COLUMNNAME_DocumentNoStart, Integer.valueOf(DocumentNoStart));
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

	/** Set NotUsed.
		@param NotUsed NotUsed	  */
	public void setNotUsed (int NotUsed)
	{
		set_ValueNoCheck (COLUMNNAME_NotUsed, Integer.valueOf(NotUsed));
	}

	/** Get NotUsed.
		@return NotUsed	  */
	public int getNotUsed () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_NotUsed);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Process Now.
		@param Processing Process Now	  */
	public void setProcessing (boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Boolean.valueOf(Processing));
	}

	/** Get Process Now.
		@return Process Now	  */
	public boolean isProcessing () 
	{
		Object oo = get_Value(COLUMNNAME_Processing);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	public I_UY_InvoiceBook getUY_InvoiceBook() throws RuntimeException
    {
		return (I_UY_InvoiceBook)MTable.get(getCtx(), I_UY_InvoiceBook.Table_Name)
			.getPO(getUY_InvoiceBook_ID(), get_TrxName());	}

	/** Set UY_InvoiceBook.
		@param UY_InvoiceBook_ID UY_InvoiceBook	  */
	public void setUY_InvoiceBook_ID (int UY_InvoiceBook_ID)
	{
		if (UY_InvoiceBook_ID < 1) 
			set_Value (COLUMNNAME_UY_InvoiceBook_ID, null);
		else 
			set_Value (COLUMNNAME_UY_InvoiceBook_ID, Integer.valueOf(UY_InvoiceBook_ID));
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

	/** Set UY_InvoiceBookControl.
		@param UY_InvoiceBookControl_ID UY_InvoiceBookControl	  */
	public void setUY_InvoiceBookControl_ID (int UY_InvoiceBookControl_ID)
	{
		if (UY_InvoiceBookControl_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_InvoiceBookControl_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_InvoiceBookControl_ID, Integer.valueOf(UY_InvoiceBookControl_ID));
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
}