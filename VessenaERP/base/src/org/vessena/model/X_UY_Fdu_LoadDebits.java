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

/** Generated Model for UY_Fdu_LoadDebits
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_Fdu_LoadDebits extends PO implements I_UY_Fdu_LoadDebits, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130131L;

    /** Standard Constructor */
    public X_UY_Fdu_LoadDebits (Properties ctx, int UY_Fdu_LoadDebits_ID, String trxName)
    {
      super (ctx, UY_Fdu_LoadDebits_ID, trxName);
      /** if (UY_Fdu_LoadDebits_ID == 0)
        {
			setFileName (null);
			setProcessed (false);
			setsheets (0);
			setUY_Fdu_LoadDebits_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_Fdu_LoadDebits (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_Fdu_LoadDebits[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set comparatormonth.
		@param comparatormonth comparatormonth	  */
	public void setcomparatormonth (String comparatormonth)
	{
		set_Value (COLUMNNAME_comparatormonth, comparatormonth);
	}

	/** Get comparatormonth.
		@return comparatormonth	  */
	public String getcomparatormonth () 
	{
		return (String)get_Value(COLUMNNAME_comparatormonth);
	}

	/** Set File Name.
		@param FileName 
		Name of the local file or URL
	  */
	public void setFileName (String FileName)
	{
		set_Value (COLUMNNAME_FileName, FileName);
	}

	/** Get File Name.
		@return Name of the local file or URL
	  */
	public String getFileName () 
	{
		return (String)get_Value(COLUMNNAME_FileName);
	}

	/** Set presentationdate.
		@param presentationdate presentationdate	  */
	public void setpresentationdate (Timestamp presentationdate)
	{
		set_Value (COLUMNNAME_presentationdate, presentationdate);
	}

	/** Get presentationdate.
		@return presentationdate	  */
	public Timestamp getpresentationdate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_presentationdate);
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

	/** Set sheets.
		@param sheets sheets	  */
	public void setsheets (int sheets)
	{
		set_Value (COLUMNNAME_sheets, Integer.valueOf(sheets));
	}

	/** Get sheets.
		@return sheets	  */
	public int getsheets () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_sheets);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_Fdu_LoadDebits.
		@param UY_Fdu_LoadDebits_ID UY_Fdu_LoadDebits	  */
	public void setUY_Fdu_LoadDebits_ID (int UY_Fdu_LoadDebits_ID)
	{
		if (UY_Fdu_LoadDebits_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_Fdu_LoadDebits_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_Fdu_LoadDebits_ID, Integer.valueOf(UY_Fdu_LoadDebits_ID));
	}

	/** Get UY_Fdu_LoadDebits.
		@return UY_Fdu_LoadDebits	  */
	public int getUY_Fdu_LoadDebits_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Fdu_LoadDebits_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}