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

/** Generated Model for AD_DiffColumn
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_AD_DiffColumn extends PO implements I_AD_DiffColumn, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_AD_DiffColumn (Properties ctx, int AD_DiffColumn_ID, String trxName)
    {
      super (ctx, AD_DiffColumn_ID, trxName);
      /** if (AD_DiffColumn_ID == 0)
        {
			setAD_DiffColumn_ID (0);
        } */
    }

    /** Load Constructor */
    public X_AD_DiffColumn (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_AD_DiffColumn[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_AD_Diff getAD_Diff() throws RuntimeException
    {
		return (I_AD_Diff)MTable.get(getCtx(), I_AD_Diff.Table_Name)
			.getPO(getAD_Diff_ID(), get_TrxName());	}

	/** Set Active Dictionary Difference.
		@param AD_Diff_ID Active Dictionary Difference	  */
	public void setAD_Diff_ID (int AD_Diff_ID)
	{
		if (AD_Diff_ID < 1) 
			set_Value (COLUMNNAME_AD_Diff_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Diff_ID, Integer.valueOf(AD_Diff_ID));
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

	/** Set Application Dictionary Difference Column.
		@param AD_DiffColumn_ID Application Dictionary Difference Column	  */
	public void setAD_DiffColumn_ID (int AD_DiffColumn_ID)
	{
		if (AD_DiffColumn_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_DiffColumn_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_DiffColumn_ID, Integer.valueOf(AD_DiffColumn_ID));
	}

	/** Get Application Dictionary Difference Column.
		@return Application Dictionary Difference Column	  */
	public int getAD_DiffColumn_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_DiffColumn_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set DB Column Name.
		@param ColumnName 
		Name of the column in the database
	  */
	public void setColumnName (String ColumnName)
	{
		set_Value (COLUMNNAME_ColumnName, ColumnName);
	}

	/** Get DB Column Name.
		@return Name of the column in the database
	  */
	public String getColumnName () 
	{
		return (String)get_Value(COLUMNNAME_ColumnName);
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

	/** Set Source.
		@param Source Source	  */
	public void setSource (String Source)
	{
		set_Value (COLUMNNAME_Source, Source);
	}

	/** Get Source.
		@return Source	  */
	public String getSource () 
	{
		return (String)get_Value(COLUMNNAME_Source);
	}

	/** Set Target.
		@param Target Target	  */
	public void setTarget (String Target)
	{
		set_Value (COLUMNNAME_Target, Target);
	}

	/** Get Target.
		@return Target	  */
	public String getTarget () 
	{
		return (String)get_Value(COLUMNNAME_Target);
	}
}