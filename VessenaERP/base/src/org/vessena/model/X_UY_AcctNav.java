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

/** Generated Model for UY_AcctNav
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_AcctNav extends PO implements I_UY_AcctNav, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20121123L;

    /** Standard Constructor */
    public X_UY_AcctNav (Properties ctx, int UY_AcctNav_ID, String trxName)
    {
      super (ctx, UY_AcctNav_ID, trxName);
      /** if (UY_AcctNav_ID == 0)
        {
			setUY_AcctNav_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_AcctNav (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_AcctNav[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Currency Type.
		@param CurrencyType Currency Type	  */
	public void setCurrencyType (String CurrencyType)
	{
		set_Value (COLUMNNAME_CurrencyType, CurrencyType);
	}

	/** Get Currency Type.
		@return Currency Type	  */
	public String getCurrencyType () 
	{
		return (String)get_Value(COLUMNNAME_CurrencyType);
	}

	/** Set End Date.
		@param EndDate 
		Last effective date (inclusive)
	  */
	public void setEndDate (Timestamp EndDate)
	{
		set_Value (COLUMNNAME_EndDate, EndDate);
	}

	/** Get End Date.
		@return Last effective date (inclusive)
	  */
	public Timestamp getEndDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_EndDate);
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

	/** Set UY_AcctNav.
		@param UY_AcctNav_ID UY_AcctNav	  */
	public void setUY_AcctNav_ID (int UY_AcctNav_ID)
	{
		if (UY_AcctNav_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_AcctNav_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_AcctNav_ID, Integer.valueOf(UY_AcctNav_ID));
	}

	/** Get UY_AcctNav.
		@return UY_AcctNav	  */
	public int getUY_AcctNav_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_AcctNav_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set WithCurrentBalance.
		@param WithCurrentBalance 
		WithCurrentBalance
	  */
	public void setWithCurrentBalance (boolean WithCurrentBalance)
	{
		set_Value (COLUMNNAME_WithCurrentBalance, Boolean.valueOf(WithCurrentBalance));
	}

	/** Get WithCurrentBalance.
		@return WithCurrentBalance
	  */
	public boolean isWithCurrentBalance () 
	{
		Object oo = get_Value(COLUMNNAME_WithCurrentBalance);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}
}