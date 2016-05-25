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

/** Generated Model for UY_TT_ConfigCardSMS
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS - $Id$ */
public class X_UY_TT_ConfigCardSMS extends PO implements I_UY_TT_ConfigCardSMS, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20131022L;

    /** Standard Constructor */
    public X_UY_TT_ConfigCardSMS (Properties ctx, int UY_TT_ConfigCardSMS_ID, String trxName)
    {
      super (ctx, UY_TT_ConfigCardSMS_ID, trxName);
      /** if (UY_TT_ConfigCardSMS_ID == 0)
        {
			setMobile (null);
			setName (null);
			setUY_TT_ConfigCardSMS_ID (0);
			setUY_TT_Config_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_TT_ConfigCardSMS (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_TT_ConfigCardSMS[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Mobile.
		@param Mobile Mobile	  */
	public void setMobile (String Mobile)
	{
		set_Value (COLUMNNAME_Mobile, Mobile);
	}

	/** Get Mobile.
		@return Mobile	  */
	public String getMobile () 
	{
		return (String)get_Value(COLUMNNAME_Mobile);
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

	/** Set UY_TT_ConfigCardSMS.
		@param UY_TT_ConfigCardSMS_ID UY_TT_ConfigCardSMS	  */
	public void setUY_TT_ConfigCardSMS_ID (int UY_TT_ConfigCardSMS_ID)
	{
		if (UY_TT_ConfigCardSMS_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_TT_ConfigCardSMS_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_TT_ConfigCardSMS_ID, Integer.valueOf(UY_TT_ConfigCardSMS_ID));
	}

	/** Get UY_TT_ConfigCardSMS.
		@return UY_TT_ConfigCardSMS	  */
	public int getUY_TT_ConfigCardSMS_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TT_ConfigCardSMS_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TT_Config getUY_TT_Config() throws RuntimeException
    {
		return (I_UY_TT_Config)MTable.get(getCtx(), I_UY_TT_Config.Table_Name)
			.getPO(getUY_TT_Config_ID(), get_TrxName());	}

	/** Set UY_TT_Config.
		@param UY_TT_Config_ID UY_TT_Config	  */
	public void setUY_TT_Config_ID (int UY_TT_Config_ID)
	{
		if (UY_TT_Config_ID < 1) 
			set_Value (COLUMNNAME_UY_TT_Config_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TT_Config_ID, Integer.valueOf(UY_TT_Config_ID));
	}

	/** Get UY_TT_Config.
		@return UY_TT_Config	  */
	public int getUY_TT_Config_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TT_Config_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}