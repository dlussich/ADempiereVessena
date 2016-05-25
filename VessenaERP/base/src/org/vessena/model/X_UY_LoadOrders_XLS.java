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

/** Generated Model for UY_LoadOrders_XLS
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_UY_LoadOrders_XLS extends PO implements I_UY_LoadOrders_XLS, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_UY_LoadOrders_XLS (Properties ctx, int UY_LoadOrders_XLS_ID, String trxName)
    {
      super (ctx, UY_LoadOrders_XLS_ID, trxName);
      /** if (UY_LoadOrders_XLS_ID == 0)
        {
			setFileName (null);
			setUY_LoadOrders_XLS_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_LoadOrders_XLS (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_LoadOrders_XLS[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set AuditSave.
		@param AuditSave AuditSave	  */
	public void setAuditSave (boolean AuditSave)
	{
		set_Value (COLUMNNAME_AuditSave, Boolean.valueOf(AuditSave));
	}

	/** Get AuditSave.
		@return AuditSave	  */
	public boolean isAuditSave () 
	{
		Object oo = get_Value(COLUMNNAME_AuditSave);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set ErrorReplication.
		@param ErrorReplication ErrorReplication	  */
	public void setErrorReplication (boolean ErrorReplication)
	{
		set_Value (COLUMNNAME_ErrorReplication, Boolean.valueOf(ErrorReplication));
	}

	/** Get ErrorReplication.
		@return ErrorReplication	  */
	public boolean isErrorReplication () 
	{
		Object oo = get_Value(COLUMNNAME_ErrorReplication);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set Process Now.
		@param Processing Process Now	  */
	public void setProcessing (boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Boolean.valueOf(Processing));
	}

	/** Get Process Now.
		@return Process Now	  */
	public boolean isProcessing () 
	{
		Object oo = get_Value(COLUMNNAME_Processing);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set UY_LoadOrders_XLS_ID.
		@param UY_LoadOrders_XLS_ID UY_LoadOrders_XLS_ID	  */
	public void setUY_LoadOrders_XLS_ID (int UY_LoadOrders_XLS_ID)
	{
		if (UY_LoadOrders_XLS_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_LoadOrders_XLS_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_LoadOrders_XLS_ID, Integer.valueOf(UY_LoadOrders_XLS_ID));
	}

	/** Get UY_LoadOrders_XLS_ID.
		@return UY_LoadOrders_XLS_ID	  */
	public int getUY_LoadOrders_XLS_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_LoadOrders_XLS_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}