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
import org.compiere.util.KeyNamePair;

/** Generated Model for UY_CFE_InboxFileType
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_CFE_InboxFileType extends PO implements I_UY_CFE_InboxFileType, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20160114L;

    /** Standard Constructor */
    public X_UY_CFE_InboxFileType (Properties ctx, int UY_CFE_InboxFileType_ID, String trxName)
    {
      super (ctx, UY_CFE_InboxFileType_ID, trxName);
      /** if (UY_CFE_InboxFileType_ID == 0)
        {
			setDescription (null);
			setName (null);
			setUY_CFE_InboxFileType_ID (0);
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_UY_CFE_InboxFileType (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_CFE_InboxFileType[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_C_ValidCombination getAccount_A() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getAccount_Acct(), get_TrxName());	}

	/** Set Account_Acct.
		@param Account_Acct Account_Acct	  */
	public void setAccount_Acct (int Account_Acct)
	{
		set_Value (COLUMNNAME_Account_Acct, Integer.valueOf(Account_Acct));
	}

	/** Get Account_Acct.
		@return Account_Acct	  */
	public int getAccount_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Account_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Description.
		@param Description 
		Optional short description of the record
	  */
	public void setDescription (String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Description.
		@return Optional short description of the record
	  */
	public String getDescription () 
	{
		return (String)get_Value(COLUMNNAME_Description);
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

	/** Set UY_CFE_InboxFileType.
		@param UY_CFE_InboxFileType_ID UY_CFE_InboxFileType	  */
	public void setUY_CFE_InboxFileType_ID (int UY_CFE_InboxFileType_ID)
	{
		if (UY_CFE_InboxFileType_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_CFE_InboxFileType_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_CFE_InboxFileType_ID, Integer.valueOf(UY_CFE_InboxFileType_ID));
	}

	/** Get UY_CFE_InboxFileType.
		@return UY_CFE_InboxFileType	  */
	public int getUY_CFE_InboxFileType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_CFE_InboxFileType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Search Key.
		@param Value 
		Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Search Key.
		@return Search key for the record in the format required - must be unique
	  */
	public String getValue () 
	{
		return (String)get_Value(COLUMNNAME_Value);
	}
}