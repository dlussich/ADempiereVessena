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

/** Generated Model for UY_DB_ConfigTable
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_DB_ConfigTable extends PO implements I_UY_DB_ConfigTable, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20131111L;

    /** Standard Constructor */
    public X_UY_DB_ConfigTable (Properties ctx, int UY_DB_ConfigTable_ID, String trxName)
    {
      super (ctx, UY_DB_ConfigTable_ID, trxName);
      /** if (UY_DB_ConfigTable_ID == 0)
        {
			setDeleteRows (false);
// N
			setDeleteTable (false);
// N
			setInDictionary (true);
// Y
			setIsSelected (false);
// N
			setUY_DB_Config_ID (0);
			setUY_DB_ConfigTable_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_DB_ConfigTable (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_DB_ConfigTable[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_AD_Table getAD_Table() throws RuntimeException
    {
		return (org.compiere.model.I_AD_Table)MTable.get(getCtx(), org.compiere.model.I_AD_Table.Table_Name)
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

	/** Set DeleteRows.
		@param DeleteRows DeleteRows	  */
	public void setDeleteRows (boolean DeleteRows)
	{
		set_Value (COLUMNNAME_DeleteRows, Boolean.valueOf(DeleteRows));
	}

	/** Get DeleteRows.
		@return DeleteRows	  */
	public boolean isDeleteRows () 
	{
		Object oo = get_Value(COLUMNNAME_DeleteRows);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set DeleteTable.
		@param DeleteTable DeleteTable	  */
	public void setDeleteTable (boolean DeleteTable)
	{
		set_Value (COLUMNNAME_DeleteTable, Boolean.valueOf(DeleteTable));
	}

	/** Get DeleteTable.
		@return DeleteTable	  */
	public boolean isDeleteTable () 
	{
		Object oo = get_Value(COLUMNNAME_DeleteTable);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set DeleteWhere.
		@param DeleteWhere DeleteWhere	  */
	public void setDeleteWhere (String DeleteWhere)
	{
		set_Value (COLUMNNAME_DeleteWhere, DeleteWhere);
	}

	/** Get DeleteWhere.
		@return DeleteWhere	  */
	public String getDeleteWhere () 
	{
		return (String)get_Value(COLUMNNAME_DeleteWhere);
	}

	/** Set InDictionary.
		@param InDictionary InDictionary	  */
	public void setInDictionary (boolean InDictionary)
	{
		set_Value (COLUMNNAME_InDictionary, Boolean.valueOf(InDictionary));
	}

	/** Get InDictionary.
		@return InDictionary	  */
	public boolean isInDictionary () 
	{
		Object oo = get_Value(COLUMNNAME_InDictionary);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Selected.
		@param IsSelected Selected	  */
	public void setIsSelected (boolean IsSelected)
	{
		set_Value (COLUMNNAME_IsSelected, Boolean.valueOf(IsSelected));
	}

	/** Get Selected.
		@return Selected	  */
	public boolean isSelected () 
	{
		Object oo = get_Value(COLUMNNAME_IsSelected);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	public I_UY_DB_Config getUY_DB_Config() throws RuntimeException
    {
		return (I_UY_DB_Config)MTable.get(getCtx(), I_UY_DB_Config.Table_Name)
			.getPO(getUY_DB_Config_ID(), get_TrxName());	}

	/** Set UY_DB_Config.
		@param UY_DB_Config_ID UY_DB_Config	  */
	public void setUY_DB_Config_ID (int UY_DB_Config_ID)
	{
		if (UY_DB_Config_ID < 1) 
			set_Value (COLUMNNAME_UY_DB_Config_ID, null);
		else 
			set_Value (COLUMNNAME_UY_DB_Config_ID, Integer.valueOf(UY_DB_Config_ID));
	}

	/** Get UY_DB_Config.
		@return UY_DB_Config	  */
	public int getUY_DB_Config_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_DB_Config_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_DB_ConfigTable.
		@param UY_DB_ConfigTable_ID UY_DB_ConfigTable	  */
	public void setUY_DB_ConfigTable_ID (int UY_DB_ConfigTable_ID)
	{
		if (UY_DB_ConfigTable_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_DB_ConfigTable_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_DB_ConfigTable_ID, Integer.valueOf(UY_DB_ConfigTable_ID));
	}

	/** Get UY_DB_ConfigTable.
		@return UY_DB_ConfigTable	  */
	public int getUY_DB_ConfigTable_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_DB_ConfigTable_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}