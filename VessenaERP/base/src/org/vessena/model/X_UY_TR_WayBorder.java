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

/** Generated Model for UY_TR_WayBorder
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_TR_WayBorder extends PO implements I_UY_TR_WayBorder, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20141110L;

    /** Standard Constructor */
    public X_UY_TR_WayBorder (Properties ctx, int UY_TR_WayBorder_ID, String trxName)
    {
      super (ctx, UY_TR_WayBorder_ID, trxName);
      /** if (UY_TR_WayBorder_ID == 0)
        {
			setUY_TR_Border_ID (0);
			setUY_TR_WayBorder_ID (0);
			setUY_TR_Way_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_TR_WayBorder (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_TR_WayBorder[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_UY_TR_Border getUY_TR_Border() throws RuntimeException
    {
		return (I_UY_TR_Border)MTable.get(getCtx(), I_UY_TR_Border.Table_Name)
			.getPO(getUY_TR_Border_ID(), get_TrxName());	}

	/** Set UY_TR_Border.
		@param UY_TR_Border_ID UY_TR_Border	  */
	public void setUY_TR_Border_ID (int UY_TR_Border_ID)
	{
		if (UY_TR_Border_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_Border_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_Border_ID, Integer.valueOf(UY_TR_Border_ID));
	}

	/** Get UY_TR_Border.
		@return UY_TR_Border	  */
	public int getUY_TR_Border_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Border_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TR_WayBorder.
		@param UY_TR_WayBorder_ID UY_TR_WayBorder	  */
	public void setUY_TR_WayBorder_ID (int UY_TR_WayBorder_ID)
	{
		if (UY_TR_WayBorder_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_TR_WayBorder_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_TR_WayBorder_ID, Integer.valueOf(UY_TR_WayBorder_ID));
	}

	/** Get UY_TR_WayBorder.
		@return UY_TR_WayBorder	  */
	public int getUY_TR_WayBorder_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_WayBorder_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TR_Way getUY_TR_Way() throws RuntimeException
    {
		return (I_UY_TR_Way)MTable.get(getCtx(), I_UY_TR_Way.Table_Name)
			.getPO(getUY_TR_Way_ID(), get_TrxName());	}

	/** Set UY_TR_Way.
		@param UY_TR_Way_ID UY_TR_Way	  */
	public void setUY_TR_Way_ID (int UY_TR_Way_ID)
	{
		if (UY_TR_Way_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_Way_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_Way_ID, Integer.valueOf(UY_TR_Way_ID));
	}

	/** Get UY_TR_Way.
		@return UY_TR_Way	  */
	public int getUY_TR_Way_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Way_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}