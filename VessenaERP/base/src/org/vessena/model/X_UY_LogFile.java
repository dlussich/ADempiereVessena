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
import org.compiere.util.KeyNamePair;

/** Generated Model for UY_LogFile
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_LogFile extends PO implements I_UY_LogFile, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150428L;

    /** Standard Constructor */
    public X_UY_LogFile (Properties ctx, int UY_LogFile_ID, String trxName)
    {
      super (ctx, UY_LogFile_ID, trxName);
      /** if (UY_LogFile_ID == 0)
        {
			setName (null);
			setUY_LogFile_ID (0);
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_UY_LogFile (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_LogFile[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Cov_Ticket_Header.
		@param Cov_Ticket_Header_ID Cov_Ticket_Header	  */
	public void setCov_Ticket_Header_ID (int Cov_Ticket_Header_ID)
	{
		if (Cov_Ticket_Header_ID < 1) 
			set_Value (COLUMNNAME_Cov_Ticket_Header_ID, null);
		else 
			set_Value (COLUMNNAME_Cov_Ticket_Header_ID, Integer.valueOf(Cov_Ticket_Header_ID));
	}

	/** Get Cov_Ticket_Header.
		@return Cov_Ticket_Header	  */
	public int getCov_Ticket_Header_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Cov_Ticket_Header_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set datofila.
		@param datofila datofila	  */
	public void setdatofila (String datofila)
	{
		set_Value (COLUMNNAME_datofila, datofila);
	}

	/** Get datofila.
		@return datofila	  */
	public String getdatofila () 
	{
		return (String)get_Value(COLUMNNAME_datofila);
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

	/** Set numerofila.
		@param numerofila numerofila	  */
	public void setnumerofila (String numerofila)
	{
		set_Value (COLUMNNAME_numerofila, numerofila);
	}

	/** Get numerofila.
		@return numerofila	  */
	public String getnumerofila () 
	{
		return (String)get_Value(COLUMNNAME_numerofila);
	}

	/** Set Record ID.
		@param Record_ID 
		Direct internal record ID
	  */
	public void setRecord_ID (int Record_ID)
	{
		if (Record_ID < 0) 
			set_Value (COLUMNNAME_Record_ID, null);
		else 
			set_Value (COLUMNNAME_Record_ID, Integer.valueOf(Record_ID));
	}

	/** Get Record ID.
		@return Direct internal record ID
	  */
	public int getRecord_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Record_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set registropadre.
		@param registropadre registropadre	  */
	public void setregistropadre (int registropadre)
	{
		set_Value (COLUMNNAME_registropadre, Integer.valueOf(registropadre));
	}

	/** Get registropadre.
		@return registropadre	  */
	public int getregistropadre () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_registropadre);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_LogFile.
		@param UY_LogFile_ID UY_LogFile	  */
	public void setUY_LogFile_ID (int UY_LogFile_ID)
	{
		if (UY_LogFile_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_LogFile_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_LogFile_ID, Integer.valueOf(UY_LogFile_ID));
	}

	/** Get UY_LogFile.
		@return UY_LogFile	  */
	public int getUY_LogFile_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_LogFile_ID);
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
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Search Key.
		@return Search key for the record in the format required - must be unique
	  */
	public String getValue () 
	{
		return (String)get_Value(COLUMNNAME_Value);
	}
}