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

/** Generated Model for UY_CashCountError
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_CashCountError extends PO implements I_UY_CashCountError, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20151228L;

    /** Standard Constructor */
    public X_UY_CashCountError (Properties ctx, int UY_CashCountError_ID, String trxName)
    {
      super (ctx, UY_CashCountError_ID, trxName);
      /** if (UY_CashCountError_ID == 0)
        {
			setUY_CashCountError_ID (0);
			setUY_CashCount_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_CashCountError (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_CashCountError[")
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

	/** Set UY_CashCountError.
		@param UY_CashCountError_ID UY_CashCountError	  */
	public void setUY_CashCountError_ID (int UY_CashCountError_ID)
	{
		if (UY_CashCountError_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_CashCountError_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_CashCountError_ID, Integer.valueOf(UY_CashCountError_ID));
	}

	/** Get UY_CashCountError.
		@return UY_CashCountError	  */
	public int getUY_CashCountError_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_CashCountError_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_CashCount getUY_CashCount() throws RuntimeException
    {
		return (I_UY_CashCount)MTable.get(getCtx(), I_UY_CashCount.Table_Name)
			.getPO(getUY_CashCount_ID(), get_TrxName());	}

	/** Set UY_CashCount.
		@param UY_CashCount_ID UY_CashCount	  */
	public void setUY_CashCount_ID (int UY_CashCount_ID)
	{
		if (UY_CashCount_ID < 1) 
			set_Value (COLUMNNAME_UY_CashCount_ID, null);
		else 
			set_Value (COLUMNNAME_UY_CashCount_ID, Integer.valueOf(UY_CashCount_ID));
	}

	/** Get UY_CashCount.
		@return UY_CashCount	  */
	public int getUY_CashCount_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_CashCount_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}