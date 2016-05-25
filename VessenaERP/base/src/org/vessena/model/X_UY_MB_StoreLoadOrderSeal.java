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

/** Generated Model for UY_MB_StoreLoadOrderSeal
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_MB_StoreLoadOrderSeal extends PO implements I_UY_MB_StoreLoadOrderSeal, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20160225L;

    /** Standard Constructor */
    public X_UY_MB_StoreLoadOrderSeal (Properties ctx, int UY_MB_StoreLoadOrderSeal_ID, String trxName)
    {
      super (ctx, UY_MB_StoreLoadOrderSeal_ID, trxName);
      /** if (UY_MB_StoreLoadOrderSeal_ID == 0)
        {
			setUY_MB_StoreLoadOrder_ID (0);
			setUY_MB_StoreLoadOrderSeal_ID (0);
			setUY_StoreLoadOrderSeal_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_MB_StoreLoadOrderSeal (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_MB_StoreLoadOrderSeal[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set dev_WayID1.
		@param dev_WayID dev_WayID1	  */
	public void setdev_WayID (int dev_WayID)
	{
		set_Value (COLUMNNAME_dev_WayID, Integer.valueOf(dev_WayID));
	}

	/** Get dev_WayID1.
		@return dev_WayID1	  */
	public int getdev_WayID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_dev_WayID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_MB_StoreLoadOrder getUY_MB_StoreLoadOrder() throws RuntimeException
    {
		return (I_UY_MB_StoreLoadOrder)MTable.get(getCtx(), I_UY_MB_StoreLoadOrder.Table_Name)
			.getPO(getUY_MB_StoreLoadOrder_ID(), get_TrxName());	}

	/** Set UY_MB_StoreLoadOrder.
		@param UY_MB_StoreLoadOrder_ID UY_MB_StoreLoadOrder	  */
	public void setUY_MB_StoreLoadOrder_ID (int UY_MB_StoreLoadOrder_ID)
	{
		if (UY_MB_StoreLoadOrder_ID < 1) 
			set_Value (COLUMNNAME_UY_MB_StoreLoadOrder_ID, null);
		else 
			set_Value (COLUMNNAME_UY_MB_StoreLoadOrder_ID, Integer.valueOf(UY_MB_StoreLoadOrder_ID));
	}

	/** Get UY_MB_StoreLoadOrder.
		@return UY_MB_StoreLoadOrder	  */
	public int getUY_MB_StoreLoadOrder_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_MB_StoreLoadOrder_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_MB_StoreLoadOrderSeal.
		@param UY_MB_StoreLoadOrderSeal_ID UY_MB_StoreLoadOrderSeal	  */
	public void setUY_MB_StoreLoadOrderSeal_ID (int UY_MB_StoreLoadOrderSeal_ID)
	{
		if (UY_MB_StoreLoadOrderSeal_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_MB_StoreLoadOrderSeal_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_MB_StoreLoadOrderSeal_ID, Integer.valueOf(UY_MB_StoreLoadOrderSeal_ID));
	}

	/** Get UY_MB_StoreLoadOrderSeal.
		@return UY_MB_StoreLoadOrderSeal	  */
	public int getUY_MB_StoreLoadOrderSeal_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_MB_StoreLoadOrderSeal_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_StoreLoadOrderSeal getUY_StoreLoadOrderSeal() throws RuntimeException
    {
		return (I_UY_StoreLoadOrderSeal)MTable.get(getCtx(), I_UY_StoreLoadOrderSeal.Table_Name)
			.getPO(getUY_StoreLoadOrderSeal_ID(), get_TrxName());	}

	/** Set UY_StoreLoadOrderSeal.
		@param UY_StoreLoadOrderSeal_ID UY_StoreLoadOrderSeal	  */
	public void setUY_StoreLoadOrderSeal_ID (int UY_StoreLoadOrderSeal_ID)
	{
		if (UY_StoreLoadOrderSeal_ID < 1) 
			set_Value (COLUMNNAME_UY_StoreLoadOrderSeal_ID, null);
		else 
			set_Value (COLUMNNAME_UY_StoreLoadOrderSeal_ID, Integer.valueOf(UY_StoreLoadOrderSeal_ID));
	}

	/** Get UY_StoreLoadOrderSeal.
		@return UY_StoreLoadOrderSeal	  */
	public int getUY_StoreLoadOrderSeal_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_StoreLoadOrderSeal_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}