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

/** Generated Model for UY_DB_Config
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_DB_Config extends PO implements I_UY_DB_Config, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20131111L;

    /** Standard Constructor */
    public X_UY_DB_Config (Properties ctx, int UY_DB_Config_ID, String trxName)
    {
      super (ctx, UY_DB_Config_ID, trxName);
      /** if (UY_DB_Config_ID == 0)
        {
			setDateTrx (new Timestamp( System.currentTimeMillis() ));
			setUY_DB_Config_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_DB_Config (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_DB_Config[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set AD_Client_ID_Actual.
		@param AD_Client_ID_Actual AD_Client_ID_Actual	  */
	public void setAD_Client_ID_Actual (int AD_Client_ID_Actual)
	{
		set_Value (COLUMNNAME_AD_Client_ID_Actual, Integer.valueOf(AD_Client_ID_Actual));
	}

	/** Get AD_Client_ID_Actual.
		@return AD_Client_ID_Actual	  */
	public int getAD_Client_ID_Actual () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Client_ID_Actual);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set AD_Org_ID_Actual.
		@param AD_Org_ID_Actual AD_Org_ID_Actual	  */
	public void setAD_Org_ID_Actual (int AD_Org_ID_Actual)
	{
		set_Value (COLUMNNAME_AD_Org_ID_Actual, Integer.valueOf(AD_Org_ID_Actual));
	}

	/** Get AD_Org_ID_Actual.
		@return AD_Org_ID_Actual	  */
	public int getAD_Org_ID_Actual () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Org_ID_Actual);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set ClientName.
		@param ClientName ClientName	  */
	public void setClientName (String ClientName)
	{
		set_Value (COLUMNNAME_ClientName, ClientName);
	}

	/** Get ClientName.
		@return ClientName	  */
	public String getClientName () 
	{
		return (String)get_Value(COLUMNNAME_ClientName);
	}

	/** Set Transaction Date.
		@param DateTrx 
		Transaction Date
	  */
	public void setDateTrx (Timestamp DateTrx)
	{
		set_Value (COLUMNNAME_DateTrx, DateTrx);
	}

	/** Get Transaction Date.
		@return Transaction Date
	  */
	public Timestamp getDateTrx () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateTrx);
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

	/** Set ExecuteAction2.
		@param ExecuteAction2 ExecuteAction2	  */
	public void setExecuteAction2 (String ExecuteAction2)
	{
		set_Value (COLUMNNAME_ExecuteAction2, ExecuteAction2);
	}

	/** Get ExecuteAction2.
		@return ExecuteAction2	  */
	public String getExecuteAction2 () 
	{
		return (String)get_Value(COLUMNNAME_ExecuteAction2);
	}

	/** Set Organization Name.
		@param OrgName 
		Name of the Organization
	  */
	public void setOrgName (String OrgName)
	{
		set_Value (COLUMNNAME_OrgName, OrgName);
	}

	/** Get Organization Name.
		@return Name of the Organization
	  */
	public String getOrgName () 
	{
		return (String)get_Value(COLUMNNAME_OrgName);
	}

	/** Set UY_DB_Config.
		@param UY_DB_Config_ID UY_DB_Config	  */
	public void setUY_DB_Config_ID (int UY_DB_Config_ID)
	{
		if (UY_DB_Config_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_DB_Config_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_DB_Config_ID, Integer.valueOf(UY_DB_Config_ID));
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
}