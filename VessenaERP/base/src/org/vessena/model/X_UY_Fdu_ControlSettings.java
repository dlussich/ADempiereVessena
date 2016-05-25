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

/** Generated Model for UY_Fdu_ControlSettings
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_Fdu_ControlSettings extends PO implements I_UY_Fdu_ControlSettings, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130620L;

    /** Standard Constructor */
    public X_UY_Fdu_ControlSettings (Properties ctx, int UY_Fdu_ControlSettings_ID, String trxName)
    {
      super (ctx, UY_Fdu_ControlSettings_ID, trxName);
      /** if (UY_Fdu_ControlSettings_ID == 0)
        {
			setDateTrx (new Timestamp( System.currentTimeMillis() ));
			setUY_Fdu_ControlSettings_ID (0);
			setUY_FduFile_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_Fdu_ControlSettings (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_Fdu_ControlSettings[")
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
		@param Message Message	  */
	public void setMessage (String Message)
	{
		set_Value (COLUMNNAME_Message, Message);
	}

	/** Get Message.
		@return Message	  */
	public String getMessage () 
	{
		return (String)get_Value(COLUMNNAME_Message);
	}

	/** Set UY_Fdu_ControlSettings.
		@param UY_Fdu_ControlSettings_ID UY_Fdu_ControlSettings	  */
	public void setUY_Fdu_ControlSettings_ID (int UY_Fdu_ControlSettings_ID)
	{
		if (UY_Fdu_ControlSettings_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_Fdu_ControlSettings_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_Fdu_ControlSettings_ID, Integer.valueOf(UY_Fdu_ControlSettings_ID));
	}

	/** Get UY_Fdu_ControlSettings.
		@return UY_Fdu_ControlSettings	  */
	public int getUY_Fdu_ControlSettings_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Fdu_ControlSettings_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_FduFile getUY_FduFile() throws RuntimeException
    {
		return (I_UY_FduFile)MTable.get(getCtx(), I_UY_FduFile.Table_Name)
			.getPO(getUY_FduFile_ID(), get_TrxName());	}

	/** Set UY_FduFile_ID.
		@param UY_FduFile_ID UY_FduFile_ID	  */
	public void setUY_FduFile_ID (int UY_FduFile_ID)
	{
		if (UY_FduFile_ID < 1) 
			set_Value (COLUMNNAME_UY_FduFile_ID, null);
		else 
			set_Value (COLUMNNAME_UY_FduFile_ID, Integer.valueOf(UY_FduFile_ID));
	}

	/** Get UY_FduFile_ID.
		@return UY_FduFile_ID	  */
	public int getUY_FduFile_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_FduFile_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}