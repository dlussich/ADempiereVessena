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

/** Generated Model for UY_Fdu_ConnectionData
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_Fdu_ConnectionData extends PO implements I_UY_Fdu_ConnectionData, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130116L;

    /** Standard Constructor */
    public X_UY_Fdu_ConnectionData (Properties ctx, int UY_Fdu_ConnectionData_ID, String trxName)
    {
      super (ctx, UY_Fdu_ConnectionData_ID, trxName);
      /** if (UY_Fdu_ConnectionData_ID == 0)
        {
			setdatabase_name (null);
			setDateTrx (new Timestamp( System.currentTimeMillis() ));
			setpassword_db (null);
			setServer (null);
			setserver_ip (null);
			setuser_db (null);
			setUY_Fdu_ConnectionData_ID (0);
			setUY_FduFile_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_Fdu_ConnectionData (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_Fdu_ConnectionData[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set database_name.
		@param database_name database_name	  */
	public void setdatabase_name (String database_name)
	{
		set_Value (COLUMNNAME_database_name, database_name);
	}

	/** Get database_name.
		@return database_name	  */
	public String getdatabase_name () 
	{
		return (String)get_Value(COLUMNNAME_database_name);
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

	/** Set password_db.
		@param password_db password_db	  */
	public void setpassword_db (String password_db)
	{
		set_Value (COLUMNNAME_password_db, password_db);
	}

	/** Get password_db.
		@return password_db	  */
	public String getpassword_db () 
	{
		return (String)get_Value(COLUMNNAME_password_db);
	}

	/** Set Schema.
		@param Schema 
		Schema
	  */
	public void setSchema (String Schema)
	{
		set_Value (COLUMNNAME_Schema, Schema);
	}

	/** Get Schema.
		@return Schema
	  */
	public String getSchema () 
	{
		return (String)get_Value(COLUMNNAME_Schema);
	}

	/** Set Server.
		@param Server 
		Server
	  */
	public void setServer (String Server)
	{
		set_Value (COLUMNNAME_Server, Server);
	}

	/** Get Server.
		@return Server
	  */
	public String getServer () 
	{
		return (String)get_Value(COLUMNNAME_Server);
	}

	/** Set server_ip.
		@param server_ip server_ip	  */
	public void setserver_ip (String server_ip)
	{
		set_Value (COLUMNNAME_server_ip, server_ip);
	}

	/** Get server_ip.
		@return server_ip	  */
	public String getserver_ip () 
	{
		return (String)get_Value(COLUMNNAME_server_ip);
	}

	/** Set user_db.
		@param user_db user_db	  */
	public void setuser_db (String user_db)
	{
		set_Value (COLUMNNAME_user_db, user_db);
	}

	/** Get user_db.
		@return user_db	  */
	public String getuser_db () 
	{
		return (String)get_Value(COLUMNNAME_user_db);
	}

	/** Set UY_Fdu_ConnectionData.
		@param UY_Fdu_ConnectionData_ID UY_Fdu_ConnectionData	  */
	public void setUY_Fdu_ConnectionData_ID (int UY_Fdu_ConnectionData_ID)
	{
		if (UY_Fdu_ConnectionData_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_Fdu_ConnectionData_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_Fdu_ConnectionData_ID, Integer.valueOf(UY_Fdu_ConnectionData_ID));
	}

	/** Get UY_Fdu_ConnectionData.
		@return UY_Fdu_ConnectionData	  */
	public int getUY_Fdu_ConnectionData_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Fdu_ConnectionData_ID);
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