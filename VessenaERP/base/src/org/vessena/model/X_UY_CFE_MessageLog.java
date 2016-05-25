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

/** Generated Model for UY_CFE_MessageLog
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_CFE_MessageLog extends PO implements I_UY_CFE_MessageLog, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20160212L;

    /** Standard Constructor */
    public X_UY_CFE_MessageLog (Properties ctx, int UY_CFE_MessageLog_ID, String trxName)
    {
      super (ctx, UY_CFE_MessageLog_ID, trxName);
      /** if (UY_CFE_MessageLog_ID == 0)
        {
			setUY_CFE_MessageLog_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_CFE_MessageLog (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_CFE_MessageLog[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	/** Set Message.
		@param Message 
		EMail Message
	  */
	public void setMessage (String Message)
	{
		set_Value (COLUMNNAME_Message, Message);
	}

	/** Get Message.
		@return EMail Message
	  */
	public String getMessage () 
	{
		return (String)get_Value(COLUMNNAME_Message);
	}

	/** Set UY_CFE_MessageLog.
		@param UY_CFE_MessageLog_ID UY_CFE_MessageLog	  */
	public void setUY_CFE_MessageLog_ID (int UY_CFE_MessageLog_ID)
	{
		if (UY_CFE_MessageLog_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_CFE_MessageLog_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_CFE_MessageLog_ID, Integer.valueOf(UY_CFE_MessageLog_ID));
	}

	/** Get UY_CFE_MessageLog.
		@return UY_CFE_MessageLog	  */
	public int getUY_CFE_MessageLog_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_CFE_MessageLog_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}