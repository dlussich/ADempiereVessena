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

/** Generated Model for AD_DiffTable
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_AD_DiffTable extends PO implements I_AD_DiffTable, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_AD_DiffTable (Properties ctx, int AD_DiffTable_ID, String trxName)
    {
      super (ctx, AD_DiffTable_ID, trxName);
      /** if (AD_DiffTable_ID == 0)
        {
			setAD_DiffTable_ID (0);
        } */
    }

    /** Load Constructor */
    public X_AD_DiffTable (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_AD_DiffTable[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_AD_DiffProcess getAD_DiffProcess() throws RuntimeException
    {
		return (I_AD_DiffProcess)MTable.get(getCtx(), I_AD_DiffProcess.Table_Name)
			.getPO(getAD_DiffProcess_ID(), get_TrxName());	}

	/** Set Active Dictionary Difference Process.
		@param AD_DiffProcess_ID Active Dictionary Difference Process	  */
	public void setAD_DiffProcess_ID (int AD_DiffProcess_ID)
	{
		if (AD_DiffProcess_ID < 1) 
			set_Value (COLUMNNAME_AD_DiffProcess_ID, null);
		else 
			set_Value (COLUMNNAME_AD_DiffProcess_ID, Integer.valueOf(AD_DiffProcess_ID));
	}

	/** Get Active Dictionary Difference Process.
		@return Active Dictionary Difference Process	  */
	public int getAD_DiffProcess_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_DiffProcess_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Application Dictionary Difference Table.
		@param AD_DiffTable_ID Application Dictionary Difference Table	  */
	public void setAD_DiffTable_ID (int AD_DiffTable_ID)
	{
		if (AD_DiffTable_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_DiffTable_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_DiffTable_ID, Integer.valueOf(AD_DiffTable_ID));
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

	public I_AD_Table getAD_Table() throws RuntimeException
    {
		return (I_AD_Table)MTable.get(getCtx(), I_AD_Table.Table_Name)
			.getPO(getAD_Table_ID(), get_TrxName());	}

	/** Set Table.
		@param AD_Table_ID 
		Database Table information
	  */
	public void setAD_Table_ID (int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_Value (COLUMNNAME_AD_Table_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
	}

	/** Get Table.
		@return Database Table information
	  */
	public int getAD_Table_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Table_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set CreateCount.
		@param CreateCount CreateCount	  */
	public void setCreateCount (int CreateCount)
	{
		set_Value (COLUMNNAME_CreateCount, Integer.valueOf(CreateCount));
	}

	/** Get CreateCount.
		@return CreateCount	  */
	public int getCreateCount () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CreateCount);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set rowcount.
		@param rowcount rowcount	  */
	public void setrowcount (int rowcount)
	{
		set_Value (COLUMNNAME_rowcount, Integer.valueOf(rowcount));
	}

	/** Get rowcount.
		@return rowcount	  */
	public int getrowcount () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_rowcount);
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
		set_Value (COLUMNNAME_TableName, TableName);
	}

	/** Get DB Table Name.
		@return Name of the table in the database
	  */
	public String getTableName () 
	{
		return (String)get_Value(COLUMNNAME_TableName);
	}

	/** Set UpdateCount.
		@param UpdateCount 
		UpdateCount
	  */
	public void setUpdateCount (int UpdateCount)
	{
		set_Value (COLUMNNAME_UpdateCount, Integer.valueOf(UpdateCount));
	}

	/** Get UpdateCount.
		@return UpdateCount
	  */
	public int getUpdateCount () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UpdateCount);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}