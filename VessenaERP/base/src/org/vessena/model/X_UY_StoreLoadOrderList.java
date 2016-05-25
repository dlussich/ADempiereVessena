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

/** Generated Model for UY_StoreLoadOrderList
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_StoreLoadOrderList extends PO implements I_UY_StoreLoadOrderList, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20151221L;

    /** Standard Constructor */
    public X_UY_StoreLoadOrderList (Properties ctx, int UY_StoreLoadOrderList_ID, String trxName)
    {
      super (ctx, UY_StoreLoadOrderList_ID, trxName);
      /** if (UY_StoreLoadOrderList_ID == 0)
        {
			setUY_StoreLoadOrder_ID (0);
			setUY_StoreLoadOrderList_ID (0);
			setUY_StoreStkList_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_StoreLoadOrderList (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_StoreLoadOrderList[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	public I_UY_StoreLoadOrder getUY_StoreLoadOrder() throws RuntimeException
    {
		return (I_UY_StoreLoadOrder)MTable.get(getCtx(), I_UY_StoreLoadOrder.Table_Name)
			.getPO(getUY_StoreLoadOrder_ID(), get_TrxName());	}

	/** Set UY_StoreLoadOrder.
		@param UY_StoreLoadOrder_ID UY_StoreLoadOrder	  */
	public void setUY_StoreLoadOrder_ID (int UY_StoreLoadOrder_ID)
	{
		if (UY_StoreLoadOrder_ID < 1) 
			set_Value (COLUMNNAME_UY_StoreLoadOrder_ID, null);
		else 
			set_Value (COLUMNNAME_UY_StoreLoadOrder_ID, Integer.valueOf(UY_StoreLoadOrder_ID));
	}

	/** Get UY_StoreLoadOrder.
		@return UY_StoreLoadOrder	  */
	public int getUY_StoreLoadOrder_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_StoreLoadOrder_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_StoreLoadOrderList.
		@param UY_StoreLoadOrderList_ID UY_StoreLoadOrderList	  */
	public void setUY_StoreLoadOrderList_ID (int UY_StoreLoadOrderList_ID)
	{
		if (UY_StoreLoadOrderList_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_StoreLoadOrderList_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_StoreLoadOrderList_ID, Integer.valueOf(UY_StoreLoadOrderList_ID));
	}

	/** Get UY_StoreLoadOrderList.
		@return UY_StoreLoadOrderList	  */
	public int getUY_StoreLoadOrderList_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_StoreLoadOrderList_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_StoreStkList getUY_StoreStkList() throws RuntimeException
    {
		return (I_UY_StoreStkList)MTable.get(getCtx(), I_UY_StoreStkList.Table_Name)
			.getPO(getUY_StoreStkList_ID(), get_TrxName());	}

	/** Set UY_StoreStkList.
		@param UY_StoreStkList_ID UY_StoreStkList	  */
	public void setUY_StoreStkList_ID (int UY_StoreStkList_ID)
	{
		if (UY_StoreStkList_ID < 1) 
			set_Value (COLUMNNAME_UY_StoreStkList_ID, null);
		else 
			set_Value (COLUMNNAME_UY_StoreStkList_ID, Integer.valueOf(UY_StoreStkList_ID));
	}

	/** Get UY_StoreStkList.
		@return UY_StoreStkList	  */
	public int getUY_StoreStkList_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_StoreStkList_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}