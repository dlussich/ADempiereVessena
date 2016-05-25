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

/** Generated Model for UY_InvoiceGenerateLog
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_InvoiceGenerateLog extends PO implements I_UY_InvoiceGenerateLog, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20151127L;

    /** Standard Constructor */
    public X_UY_InvoiceGenerateLog (Properties ctx, int UY_InvoiceGenerateLog_ID, String trxName)
    {
      super (ctx, UY_InvoiceGenerateLog_ID, trxName);
      /** if (UY_InvoiceGenerateLog_ID == 0)
        {
			setUY_InvoiceGenerateLog_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_InvoiceGenerateLog (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_InvoiceGenerateLog[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	/** Set Message 2.
		@param Message2 
		Optional second part of the EMail Message
	  */
	public void setMessage2 (String Message2)
	{
		set_Value (COLUMNNAME_Message2, Message2);
	}

	/** Get Message 2.
		@return Optional second part of the EMail Message
	  */
	public String getMessage2 () 
	{
		return (String)get_Value(COLUMNNAME_Message2);
	}

	public I_UY_InvoiceGenerate getUY_InvoiceGenerate() throws RuntimeException
    {
		return (I_UY_InvoiceGenerate)MTable.get(getCtx(), I_UY_InvoiceGenerate.Table_Name)
			.getPO(getUY_InvoiceGenerate_ID(), get_TrxName());	}

	/** Set UY_InvoiceGenerate.
		@param UY_InvoiceGenerate_ID UY_InvoiceGenerate	  */
	public void setUY_InvoiceGenerate_ID (int UY_InvoiceGenerate_ID)
	{
		if (UY_InvoiceGenerate_ID < 1) 
			set_Value (COLUMNNAME_UY_InvoiceGenerate_ID, null);
		else 
			set_Value (COLUMNNAME_UY_InvoiceGenerate_ID, Integer.valueOf(UY_InvoiceGenerate_ID));
	}

	/** Get UY_InvoiceGenerate.
		@return UY_InvoiceGenerate	  */
	public int getUY_InvoiceGenerate_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_InvoiceGenerate_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_InvoiceGenerateLog.
		@param UY_InvoiceGenerateLog_ID UY_InvoiceGenerateLog	  */
	public void setUY_InvoiceGenerateLog_ID (int UY_InvoiceGenerateLog_ID)
	{
		if (UY_InvoiceGenerateLog_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_InvoiceGenerateLog_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_InvoiceGenerateLog_ID, Integer.valueOf(UY_InvoiceGenerateLog_ID));
	}

	/** Get UY_InvoiceGenerateLog.
		@return UY_InvoiceGenerateLog	  */
	public int getUY_InvoiceGenerateLog_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_InvoiceGenerateLog_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}