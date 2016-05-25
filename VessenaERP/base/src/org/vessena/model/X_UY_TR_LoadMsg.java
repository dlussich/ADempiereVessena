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

/** Generated Model for UY_TR_LoadMsg
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_TR_LoadMsg extends PO implements I_UY_TR_LoadMsg, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150225L;

    /** Standard Constructor */
    public X_UY_TR_LoadMsg (Properties ctx, int UY_TR_LoadMsg_ID, String trxName)
    {
      super (ctx, UY_TR_LoadMsg_ID, trxName);
      /** if (UY_TR_LoadMsg_ID == 0)
        {
			setProcessed (false);
			setUY_TR_LoadMsg_ID (0);
			setUY_TR_LoadMsgType_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_TR_LoadMsg (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_TR_LoadMsg[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	/** Set Processed.
		@param Processed 
		The document has been processed
	  */
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Processed.
		@return The document has been processed
	  */
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
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

	/** Set UY_TR_LoadMsg.
		@param UY_TR_LoadMsg_ID UY_TR_LoadMsg	  */
	public void setUY_TR_LoadMsg_ID (int UY_TR_LoadMsg_ID)
	{
		if (UY_TR_LoadMsg_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_TR_LoadMsg_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_TR_LoadMsg_ID, Integer.valueOf(UY_TR_LoadMsg_ID));
	}

	/** Get UY_TR_LoadMsg.
		@return UY_TR_LoadMsg	  */
	public int getUY_TR_LoadMsg_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_LoadMsg_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TR_LoadMsgType getUY_TR_LoadMsgType() throws RuntimeException
    {
		return (I_UY_TR_LoadMsgType)MTable.get(getCtx(), I_UY_TR_LoadMsgType.Table_Name)
			.getPO(getUY_TR_LoadMsgType_ID(), get_TrxName());	}

	/** Set UY_TR_LoadMsgType.
		@param UY_TR_LoadMsgType_ID UY_TR_LoadMsgType	  */
	public void setUY_TR_LoadMsgType_ID (int UY_TR_LoadMsgType_ID)
	{
		if (UY_TR_LoadMsgType_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_LoadMsgType_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_LoadMsgType_ID, Integer.valueOf(UY_TR_LoadMsgType_ID));
	}

	/** Get UY_TR_LoadMsgType.
		@return UY_TR_LoadMsgType	  */
	public int getUY_TR_LoadMsgType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_LoadMsgType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}