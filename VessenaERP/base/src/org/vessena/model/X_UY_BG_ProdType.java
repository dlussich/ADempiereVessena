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

/** Generated Model for UY_BG_ProdType
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_BG_ProdType extends PO implements I_UY_BG_ProdType, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150720L;

    /** Standard Constructor */
    public X_UY_BG_ProdType (Properties ctx, int UY_BG_ProdType_ID, String trxName)
    {
      super (ctx, UY_BG_ProdType_ID, trxName);
      /** if (UY_BG_ProdType_ID == 0)
        {
			setUY_BG_ProdType_ID (0);
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_UY_BG_ProdType (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_BG_ProdType[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	/** Set UY_BG_ProdType.
		@param UY_BG_ProdType_ID UY_BG_ProdType	  */
	public void setUY_BG_ProdType_ID (int UY_BG_ProdType_ID)
	{
		if (UY_BG_ProdType_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_BG_ProdType_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_BG_ProdType_ID, Integer.valueOf(UY_BG_ProdType_ID));
	}

	/** Get UY_BG_ProdType.
		@return UY_BG_ProdType	  */
	public int getUY_BG_ProdType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_BG_ProdType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_BG_Retention getUY_BG_Retention() throws RuntimeException
    {
		return (I_UY_BG_Retention)MTable.get(getCtx(), I_UY_BG_Retention.Table_Name)
			.getPO(getUY_BG_Retention_ID(), get_TrxName());	}

	/** Set UY_BG_Retentio.
		@param UY_BG_Retention_ID UY_BG_Retentio	  */
	public void setUY_BG_Retention_ID (int UY_BG_Retention_ID)
	{
		if (UY_BG_Retention_ID < 1) 
			set_Value (COLUMNNAME_UY_BG_Retention_ID, null);
		else 
			set_Value (COLUMNNAME_UY_BG_Retention_ID, Integer.valueOf(UY_BG_Retention_ID));
	}

	/** Get UY_BG_Retentio.
		@return UY_BG_Retentio	  */
	public int getUY_BG_Retention_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_BG_Retention_ID);
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