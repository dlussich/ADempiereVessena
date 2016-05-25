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

/** Generated Model for UY_MB_StoreLoadOrder
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_MB_StoreLoadOrder extends PO implements I_UY_MB_StoreLoadOrder, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20160301L;

    /** Standard Constructor */
    public X_UY_MB_StoreLoadOrder (Properties ctx, int UY_MB_StoreLoadOrder_ID, String trxName)
    {
      super (ctx, UY_MB_StoreLoadOrder_ID, trxName);
      /** if (UY_MB_StoreLoadOrder_ID == 0)
        {
			setUY_MB_StoreLoadOrder_ID (0);
			setUY_StoreLoadOrder_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_MB_StoreLoadOrder (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_MB_StoreLoadOrder[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set cockpitbossuserid.
		@param cockpitbossuserid cockpitbossuserid	  */
	public void setcockpitbossuserid (int cockpitbossuserid)
	{
		set_Value (COLUMNNAME_cockpitbossuserid, Integer.valueOf(cockpitbossuserid));
	}

	/** Get cockpitbossuserid.
		@return cockpitbossuserid	  */
	public int getcockpitbossuserid () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_cockpitbossuserid);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set IsExecuted.
		@param IsExecuted IsExecuted	  */
	public void setIsExecuted (boolean IsExecuted)
	{
		set_Value (COLUMNNAME_IsExecuted, Boolean.valueOf(IsExecuted));
	}

	/** Get IsExecuted.
		@return IsExecuted	  */
	public boolean isExecuted () 
	{
		Object oo = get_Value(COLUMNNAME_IsExecuted);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Movement Date.
		@param MovementDate 
		Date a product was moved in or out of inventory
	  */
	public void setMovementDate (Timestamp MovementDate)
	{
		set_Value (COLUMNNAME_MovementDate, MovementDate);
	}

	/** Get Movement Date.
		@return Date a product was moved in or out of inventory
	  */
	public Timestamp getMovementDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_MovementDate);
	}

	/** Set observaciones.
		@param observaciones observaciones	  */
	public void setobservaciones (String observaciones)
	{
		set_Value (COLUMNNAME_observaciones, observaciones);
	}

	/** Get observaciones.
		@return observaciones	  */
	public String getobservaciones () 
	{
		return (String)get_Value(COLUMNNAME_observaciones);
	}

	/** Set UY_MB_StoreLoadOrder.
		@param UY_MB_StoreLoadOrder_ID UY_MB_StoreLoadOrder	  */
	public void setUY_MB_StoreLoadOrder_ID (int UY_MB_StoreLoadOrder_ID)
	{
		if (UY_MB_StoreLoadOrder_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_MB_StoreLoadOrder_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_MB_StoreLoadOrder_ID, Integer.valueOf(UY_MB_StoreLoadOrder_ID));
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
}