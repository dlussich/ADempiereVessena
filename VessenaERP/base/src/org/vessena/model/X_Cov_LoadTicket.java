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

/** Generated Model for Cov_LoadTicket
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_Cov_LoadTicket extends PO implements I_Cov_LoadTicket, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150226L;

    /** Standard Constructor */
    public X_Cov_LoadTicket (Properties ctx, int Cov_LoadTicket_ID, String trxName)
    {
      super (ctx, Cov_LoadTicket_ID, trxName);
      /** if (Cov_LoadTicket_ID == 0)
        {
			setCov_LoadTicket_ID (0);
			setFileName (null);
        } */
    }

    /** Load Constructor */
    public X_Cov_LoadTicket (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_Cov_LoadTicket[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Cov_LoadTicket.
		@param Cov_LoadTicket_ID Cov_LoadTicket	  */
	public void setCov_LoadTicket_ID (int Cov_LoadTicket_ID)
	{
		if (Cov_LoadTicket_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Cov_LoadTicket_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Cov_LoadTicket_ID, Integer.valueOf(Cov_LoadTicket_ID));
	}

	/** Get Cov_LoadTicket.
		@return Cov_LoadTicket	  */
	public int getCov_LoadTicket_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Cov_LoadTicket_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set File Name.
		@param FileName 
		Name of the local file or URL
	  */
	public void setFileName (String FileName)
	{
		set_Value (COLUMNNAME_FileName, FileName);
	}

	/** Get File Name.
		@return Name of the local file or URL
	  */
	public String getFileName () 
	{
		return (String)get_Value(COLUMNNAME_FileName);
	}
}