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

/** Generated Model for UY_MB_InOutLog
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_MB_InOutLog extends PO implements I_UY_MB_InOutLog, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150925L;

    /** Standard Constructor */
    public X_UY_MB_InOutLog (Properties ctx, int UY_MB_InOutLog_ID, String trxName)
    {
      super (ctx, UY_MB_InOutLog_ID, trxName);
      /** if (UY_MB_InOutLog_ID == 0)
        {
			setfechalog (new Timestamp( System.currentTimeMillis() ));
			setUY_MB_InOut_ID (0);
			setUY_MB_InOutLog_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_MB_InOutLog (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_MB_InOutLog[")
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

	/** Set fechalog.
		@param fechalog fechalog	  */
	public void setfechalog (Timestamp fechalog)
	{
		set_Value (COLUMNNAME_fechalog, fechalog);
	}

	/** Get fechalog.
		@return fechalog	  */
	public Timestamp getfechalog () 
	{
		return (Timestamp)get_Value(COLUMNNAME_fechalog);
	}

	public I_UY_MB_InOut getUY_MB_InOut() throws RuntimeException
    {
		return (I_UY_MB_InOut)MTable.get(getCtx(), I_UY_MB_InOut.Table_Name)
			.getPO(getUY_MB_InOut_ID(), get_TrxName());	}

	/** Set UY_MB_InOut.
		@param UY_MB_InOut_ID UY_MB_InOut	  */
	public void setUY_MB_InOut_ID (int UY_MB_InOut_ID)
	{
		if (UY_MB_InOut_ID < 1) 
			set_Value (COLUMNNAME_UY_MB_InOut_ID, null);
		else 
			set_Value (COLUMNNAME_UY_MB_InOut_ID, Integer.valueOf(UY_MB_InOut_ID));
	}

	/** Get UY_MB_InOut.
		@return UY_MB_InOut	  */
	public int getUY_MB_InOut_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_MB_InOut_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_MB_InOutLog.
		@param UY_MB_InOutLog_ID UY_MB_InOutLog	  */
	public void setUY_MB_InOutLog_ID (int UY_MB_InOutLog_ID)
	{
		if (UY_MB_InOutLog_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_MB_InOutLog_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_MB_InOutLog_ID, Integer.valueOf(UY_MB_InOutLog_ID));
	}

	/** Get UY_MB_InOutLog.
		@return UY_MB_InOutLog	  */
	public int getUY_MB_InOutLog_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_MB_InOutLog_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}