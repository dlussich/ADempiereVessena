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

/** Generated Model for UY_DiscountRule_Version
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_DiscountRule_Version extends PO implements I_UY_DiscountRule_Version, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20160408L;

    /** Standard Constructor */
    public X_UY_DiscountRule_Version (Properties ctx, int UY_DiscountRule_Version_ID, String trxName)
    {
      super (ctx, UY_DiscountRule_Version_ID, trxName);
      /** if (UY_DiscountRule_Version_ID == 0)
        {
			setIsValid (false);
// N
			setStartDate (new Timestamp( System.currentTimeMillis() ));
			setUY_DiscountRule_ID (0);
			setUY_DiscountRule_Version_ID (0);
			setWasValidated (false);
// N
        } */
    }

    /** Load Constructor */
    public X_UY_DiscountRule_Version (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_DiscountRule_Version[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set ExecuteAction.
		@param ExecuteAction ExecuteAction	  */
	public void setExecuteAction (String ExecuteAction)
	{
		set_Value (COLUMNNAME_ExecuteAction, ExecuteAction);
	}

	/** Get ExecuteAction.
		@return ExecuteAction	  */
	public String getExecuteAction () 
	{
		return (String)get_Value(COLUMNNAME_ExecuteAction);
	}

	/** Set Valid.
		@param IsValid 
		Element is valid
	  */
	public void setIsValid (boolean IsValid)
	{
		set_Value (COLUMNNAME_IsValid, Boolean.valueOf(IsValid));
	}

	/** Get Valid.
		@return Element is valid
	  */
	public boolean isValid () 
	{
		Object oo = get_Value(COLUMNNAME_IsValid);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Start Date.
		@param StartDate 
		First effective day (inclusive)
	  */
	public void setStartDate (Timestamp StartDate)
	{
		set_Value (COLUMNNAME_StartDate, StartDate);
	}

	/** Get Start Date.
		@return First effective day (inclusive)
	  */
	public Timestamp getStartDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_StartDate);
	}

	public I_UY_DiscountRule getUY_DiscountRule() throws RuntimeException
    {
		return (I_UY_DiscountRule)MTable.get(getCtx(), I_UY_DiscountRule.Table_Name)
			.getPO(getUY_DiscountRule_ID(), get_TrxName());	}

	/** Set UY_DiscountRule.
		@param UY_DiscountRule_ID UY_DiscountRule	  */
	public void setUY_DiscountRule_ID (int UY_DiscountRule_ID)
	{
		if (UY_DiscountRule_ID < 1) 
			set_Value (COLUMNNAME_UY_DiscountRule_ID, null);
		else 
			set_Value (COLUMNNAME_UY_DiscountRule_ID, Integer.valueOf(UY_DiscountRule_ID));
	}

	/** Get UY_DiscountRule.
		@return UY_DiscountRule	  */
	public int getUY_DiscountRule_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_DiscountRule_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_DiscountRule_Version.
		@param UY_DiscountRule_Version_ID UY_DiscountRule_Version	  */
	public void setUY_DiscountRule_Version_ID (int UY_DiscountRule_Version_ID)
	{
		if (UY_DiscountRule_Version_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_DiscountRule_Version_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_DiscountRule_Version_ID, Integer.valueOf(UY_DiscountRule_Version_ID));
	}

	/** Get UY_DiscountRule_Version.
		@return UY_DiscountRule_Version	  */
	public int getUY_DiscountRule_Version_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_DiscountRule_Version_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Was Validated.
		@param WasValidated 
		Element is valid
	  */
	public void setWasValidated (boolean WasValidated)
	{
		set_Value (COLUMNNAME_WasValidated, Boolean.valueOf(WasValidated));
	}

	/** Get Was Validated.
		@return Element is valid
	  */
	public boolean isWasValidated () 
	{
		Object oo = get_Value(COLUMNNAME_WasValidated);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}
}