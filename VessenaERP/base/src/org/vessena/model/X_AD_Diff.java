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
import java.util.Properties;
import org.compiere.model.*;

/** Generated Model for AD_Diff
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_AD_Diff extends PO implements I_AD_Diff, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_AD_Diff (Properties ctx, int AD_Diff_ID, String trxName)
    {
      super (ctx, AD_Diff_ID, trxName);
      /** if (AD_Diff_ID == 0)
        {
			setAD_Diff_ID (0);
        } */
    }

    /** Load Constructor */
    public X_AD_Diff (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 4 - System 
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
      StringBuffer sb = new StringBuffer ("X_AD_Diff[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Active Dictionary Difference.
		@param AD_Diff_ID Active Dictionary Difference	  */
	public void setAD_Diff_ID (int AD_Diff_ID)
	{
		if (AD_Diff_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Diff_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Diff_ID, Integer.valueOf(AD_Diff_ID));
	}

	/** Get Active Dictionary Difference.
		@return Active Dictionary Difference	  */
	public int getAD_Diff_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Diff_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Application Dictionary Difference Table.
		@param AD_DiffTable_ID Application Dictionary Difference Table	  */
	public void setAD_DiffTable_ID (int AD_DiffTable_ID)
	{
		if (AD_DiffTable_ID < 1) 
			set_Value (COLUMNNAME_AD_DiffTable_ID, null);
		else 
			set_Value (COLUMNNAME_AD_DiffTable_ID, Integer.valueOf(AD_DiffTable_ID));
	}

	/** Get Application Dictionary Difference Table.
		@return Application Dictionary Difference Table	  */
	public int getAD_DiffTable_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_DiffTable_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Application Dictionary Difference Type.
		@param AD_DiffType 
		The type of differences that and Application Dictionary could have
	  */
	public void setAD_DiffType (String AD_DiffType)
	{
		set_Value (COLUMNNAME_AD_DiffType, AD_DiffType);
	}

	/** Get Application Dictionary Difference Type.
		@return The type of differences that and Application Dictionary could have
	  */
	public String getAD_DiffType () 
	{
		return (String)get_Value(COLUMNNAME_AD_DiffType);
	}

	/** Set Tab.
		@param AD_Tab_ID 
		Tab within a Window
	  */
	public void setAD_Tab_ID (int AD_Tab_ID)
	{
		if (AD_Tab_ID < 1) 
			set_Value (COLUMNNAME_AD_Tab_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Tab_ID, Integer.valueOf(AD_Tab_ID));
	}

	/** Get Tab.
		@return Tab within a Window
	  */
	public int getAD_Tab_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Tab_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Window.
		@param AD_Window_ID 
		Data entry or display window
	  */
	public void setAD_Window_ID (int AD_Window_ID)
	{
		if (AD_Window_ID < 1) 
			set_Value (COLUMNNAME_AD_Window_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Window_ID, Integer.valueOf(AD_Window_ID));
	}

	/** Get Window.
		@return Data entry or display window
	  */
	public int getAD_Window_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Window_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set DiffCount.
		@param DiffCount DiffCount	  */
	public void setDiffCount (int DiffCount)
	{
		set_Value (COLUMNNAME_DiffCount, Integer.valueOf(DiffCount));
	}

	/** Get DiffCount.
		@return DiffCount	  */
	public int getDiffCount () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DiffCount);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Record ID.
		@param Record_ID 
		Direct internal record ID
	  */
	public void setRecord_ID (int Record_ID)
	{
		if (Record_ID < 0) 
			set_Value (COLUMNNAME_Record_ID, null);
		else 
			set_Value (COLUMNNAME_Record_ID, Integer.valueOf(Record_ID));
	}

	/** Get Record ID.
		@return Direct internal record ID
	  */
	public int getRecord_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Record_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set DB Table Name.
		@param TableName 
		Name of the table in the database
	  */
	public void setTableName (String TableName)
	{
		throw new IllegalArgumentException ("TableName is virtual column");	}

	/** Get DB Table Name.
		@return Name of the table in the database
	  */
	public String getTableName () 
	{
		return (String)get_Value(COLUMNNAME_TableName);
	}
}