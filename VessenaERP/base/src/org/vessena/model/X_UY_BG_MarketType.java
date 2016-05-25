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
import org.compiere.util.KeyNamePair;

/** Generated Model for UY_BG_MarketType
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_BG_MarketType extends PO implements I_UY_BG_MarketType, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150804L;

    /** Standard Constructor */
    public X_UY_BG_MarketType (Properties ctx, int UY_BG_MarketType_ID, String trxName)
    {
      super (ctx, UY_BG_MarketType_ID, trxName);
      /** if (UY_BG_MarketType_ID == 0)
        {
			setUY_BG_MarketType_ID (0);
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_UY_BG_MarketType (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_BG_MarketType[")
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

	/** Set finishingtime.
		@param finishingtime finishingtime	  */
	public void setfinishingtime (Timestamp finishingtime)
	{
		set_Value (COLUMNNAME_finishingtime, finishingtime);
	}

	/** Get finishingtime.
		@return finishingtime	  */
	public Timestamp getfinishingtime () 
	{
		return (Timestamp)get_Value(COLUMNNAME_finishingtime);
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

	/** Set Friday.
		@param OnFriday 
		Available on Fridays
	  */
	public void setOnFriday (boolean OnFriday)
	{
		set_Value (COLUMNNAME_OnFriday, Boolean.valueOf(OnFriday));
	}

	/** Get Friday.
		@return Available on Fridays
	  */
	public boolean isOnFriday () 
	{
		Object oo = get_Value(COLUMNNAME_OnFriday);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Monday.
		@param OnMonday 
		Available on Mondays
	  */
	public void setOnMonday (boolean OnMonday)
	{
		set_Value (COLUMNNAME_OnMonday, Boolean.valueOf(OnMonday));
	}

	/** Get Monday.
		@return Available on Mondays
	  */
	public boolean isOnMonday () 
	{
		Object oo = get_Value(COLUMNNAME_OnMonday);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Saturday.
		@param OnSaturday 
		Available on Saturday
	  */
	public void setOnSaturday (boolean OnSaturday)
	{
		set_Value (COLUMNNAME_OnSaturday, Boolean.valueOf(OnSaturday));
	}

	/** Get Saturday.
		@return Available on Saturday
	  */
	public boolean isOnSaturday () 
	{
		Object oo = get_Value(COLUMNNAME_OnSaturday);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Sunday.
		@param OnSunday 
		Available on Sundays
	  */
	public void setOnSunday (boolean OnSunday)
	{
		set_Value (COLUMNNAME_OnSunday, Boolean.valueOf(OnSunday));
	}

	/** Get Sunday.
		@return Available on Sundays
	  */
	public boolean isOnSunday () 
	{
		Object oo = get_Value(COLUMNNAME_OnSunday);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Thursday.
		@param OnThursday 
		Available on Thursdays
	  */
	public void setOnThursday (boolean OnThursday)
	{
		set_Value (COLUMNNAME_OnThursday, Boolean.valueOf(OnThursday));
	}

	/** Get Thursday.
		@return Available on Thursdays
	  */
	public boolean isOnThursday () 
	{
		Object oo = get_Value(COLUMNNAME_OnThursday);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Tuesday.
		@param OnTuesday 
		Available on Tuesdays
	  */
	public void setOnTuesday (boolean OnTuesday)
	{
		set_Value (COLUMNNAME_OnTuesday, Boolean.valueOf(OnTuesday));
	}

	/** Get Tuesday.
		@return Available on Tuesdays
	  */
	public boolean isOnTuesday () 
	{
		Object oo = get_Value(COLUMNNAME_OnTuesday);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Wednesday.
		@param OnWednesday 
		Available on Wednesdays
	  */
	public void setOnWednesday (boolean OnWednesday)
	{
		set_Value (COLUMNNAME_OnWednesday, Boolean.valueOf(OnWednesday));
	}

	/** Get Wednesday.
		@return Available on Wednesdays
	  */
	public boolean isOnWednesday () 
	{
		Object oo = get_Value(COLUMNNAME_OnWednesday);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set startingtime.
		@param startingtime startingtime	  */
	public void setstartingtime (Timestamp startingtime)
	{
		set_Value (COLUMNNAME_startingtime, startingtime);
	}

	/** Get startingtime.
		@return startingtime	  */
	public Timestamp getstartingtime () 
	{
		return (Timestamp)get_Value(COLUMNNAME_startingtime);
	}

	/** Set UY_BG_MarketType.
		@param UY_BG_MarketType_ID UY_BG_MarketType	  */
	public void setUY_BG_MarketType_ID (int UY_BG_MarketType_ID)
	{
		if (UY_BG_MarketType_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_BG_MarketType_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_BG_MarketType_ID, Integer.valueOf(UY_BG_MarketType_ID));
	}

	/** Get UY_BG_MarketType.
		@return UY_BG_MarketType	  */
	public int getUY_BG_MarketType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_BG_MarketType_ID);
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
}