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

/** Generated Model for UY_StoreLoadOrderWay
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_StoreLoadOrderWay extends PO implements I_UY_StoreLoadOrderWay, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20160229L;

    /** Standard Constructor */
    public X_UY_StoreLoadOrderWay (Properties ctx, int UY_StoreLoadOrderWay_ID, String trxName)
    {
      super (ctx, UY_StoreLoadOrderWay_ID, trxName);
      /** if (UY_StoreLoadOrderWay_ID == 0)
        {
			setUY_StoreLoadOrder_ID (0);
			setUY_StoreLoadOrderWay_ID (0);
			setUY_Way_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_StoreLoadOrderWay (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_StoreLoadOrderWay[")
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

	/** Set paxno.
		@param paxno paxno	  */
	public void setpaxno (int paxno)
	{
		set_Value (COLUMNNAME_paxno, Integer.valueOf(paxno));
	}

	/** Get paxno.
		@return paxno	  */
	public int getpaxno () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_paxno);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set UY_StoreLoadOrderWay.
		@param UY_StoreLoadOrderWay_ID UY_StoreLoadOrderWay	  */
	public void setUY_StoreLoadOrderWay_ID (int UY_StoreLoadOrderWay_ID)
	{
		if (UY_StoreLoadOrderWay_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_StoreLoadOrderWay_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_StoreLoadOrderWay_ID, Integer.valueOf(UY_StoreLoadOrderWay_ID));
	}

	/** Get UY_StoreLoadOrderWay.
		@return UY_StoreLoadOrderWay	  */
	public int getUY_StoreLoadOrderWay_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_StoreLoadOrderWay_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_Way getUY_Way() throws RuntimeException
    {
		return (I_UY_Way)MTable.get(getCtx(), I_UY_Way.Table_Name)
			.getPO(getUY_Way_ID(), get_TrxName());	}

	/** Set UY_Way.
		@param UY_Way_ID UY_Way	  */
	public void setUY_Way_ID (int UY_Way_ID)
	{
		if (UY_Way_ID < 1) 
			set_Value (COLUMNNAME_UY_Way_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Way_ID, Integer.valueOf(UY_Way_ID));
	}

	/** Get UY_Way.
		@return UY_Way	  */
	public int getUY_Way_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Way_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}