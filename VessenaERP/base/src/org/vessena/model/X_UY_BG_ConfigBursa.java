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

/** Generated Model for UY_BG_ConfigBursa
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_BG_ConfigBursa extends PO implements I_UY_BG_ConfigBursa, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150722L;

    /** Standard Constructor */
    public X_UY_BG_ConfigBursa (Properties ctx, int UY_BG_ConfigBursa_ID, String trxName)
    {
      super (ctx, UY_BG_ConfigBursa_ID, trxName);
      /** if (UY_BG_ConfigBursa_ID == 0)
        {
			setUY_BG_ConfigBursa_ID (0);
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_UY_BG_ConfigBursa (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_BG_ConfigBursa[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Role1_ID.
		@param Role1_ID Role1_ID	  */
	public void setRole1_ID (int Role1_ID)
	{
		if (Role1_ID < 1) 
			set_Value (COLUMNNAME_Role1_ID, null);
		else 
			set_Value (COLUMNNAME_Role1_ID, Integer.valueOf(Role1_ID));
	}

	/** Get Role1_ID.
		@return Role1_ID	  */
	public int getRole1_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Role1_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Role2_ID.
		@param Role2_ID Role2_ID	  */
	public void setRole2_ID (int Role2_ID)
	{
		if (Role2_ID < 1) 
			set_Value (COLUMNNAME_Role2_ID, null);
		else 
			set_Value (COLUMNNAME_Role2_ID, Integer.valueOf(Role2_ID));
	}

	/** Get Role2_ID.
		@return Role2_ID	  */
	public int getRole2_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Role2_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Role3_ID.
		@param Role3_ID Role3_ID	  */
	public void setRole3_ID (int Role3_ID)
	{
		if (Role3_ID < 1) 
			set_Value (COLUMNNAME_Role3_ID, null);
		else 
			set_Value (COLUMNNAME_Role3_ID, Integer.valueOf(Role3_ID));
	}

	/** Get Role3_ID.
		@return Role3_ID	  */
	public int getRole3_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Role3_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_BG_ConfigBursa.
		@param UY_BG_ConfigBursa_ID UY_BG_ConfigBursa	  */
	public void setUY_BG_ConfigBursa_ID (int UY_BG_ConfigBursa_ID)
	{
		if (UY_BG_ConfigBursa_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_BG_ConfigBursa_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_BG_ConfigBursa_ID, Integer.valueOf(UY_BG_ConfigBursa_ID));
	}

	/** Get UY_BG_ConfigBursa.
		@return UY_BG_ConfigBursa	  */
	public int getUY_BG_ConfigBursa_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_BG_ConfigBursa_ID);
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
		set_ValueNoCheck (COLUMNNAME_Value, Value);
	}

	/** Get Search Key.
		@return Search key for the record in the format required - must be unique
	  */
	public String getValue () 
	{
		return (String)get_Value(COLUMNNAME_Value);
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getValue());
    }
}