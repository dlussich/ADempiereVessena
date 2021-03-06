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

/** Generated Model for UY_RT_Config_IdOrg
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_RT_Config_IdOrg extends PO implements I_UY_RT_Config_IdOrg, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20160316L;

    /** Standard Constructor */
    public X_UY_RT_Config_IdOrg (Properties ctx, int UY_RT_Config_IdOrg_ID, String trxName)
    {
      super (ctx, UY_RT_Config_IdOrg_ID, trxName);
      /** if (UY_RT_Config_IdOrg_ID == 0)
        {
			setAD_Org_ID_To (0);
			setUY_RT_Config_IdOrg_ID (0);
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_UY_RT_Config_IdOrg (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_RT_Config_IdOrg[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set AD_Org_ID_To.
		@param AD_Org_ID_To AD_Org_ID_To	  */
	public void setAD_Org_ID_To (int AD_Org_ID_To)
	{
		set_Value (COLUMNNAME_AD_Org_ID_To, Integer.valueOf(AD_Org_ID_To));
	}

	/** Get AD_Org_ID_To.
		@return AD_Org_ID_To	  */
	public int getAD_Org_ID_To () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Org_ID_To);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set identifempresa.
		@param identifempresa identifempresa	  */
	public void setidentifempresa (int identifempresa)
	{
		set_Value (COLUMNNAME_identifempresa, Integer.valueOf(identifempresa));
	}

	/** Get identifempresa.
		@return identifempresa	  */
	public int getidentifempresa () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_identifempresa);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set UY_RT_Config_IdOrg.
		@param UY_RT_Config_IdOrg_ID UY_RT_Config_IdOrg	  */
	public void setUY_RT_Config_IdOrg_ID (int UY_RT_Config_IdOrg_ID)
	{
		if (UY_RT_Config_IdOrg_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_RT_Config_IdOrg_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_RT_Config_IdOrg_ID, Integer.valueOf(UY_RT_Config_IdOrg_ID));
	}

	/** Get UY_RT_Config_IdOrg.
		@return UY_RT_Config_IdOrg	  */
	public int getUY_RT_Config_IdOrg_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_RT_Config_IdOrg_ID);
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