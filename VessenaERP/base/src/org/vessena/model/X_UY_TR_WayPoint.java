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

/** Generated Model for UY_TR_WayPoint
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_TR_WayPoint extends PO implements I_UY_TR_WayPoint, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20141208L;

    /** Standard Constructor */
    public X_UY_TR_WayPoint (Properties ctx, int UY_TR_WayPoint_ID, String trxName)
    {
      super (ctx, UY_TR_WayPoint_ID, trxName);
      /** if (UY_TR_WayPoint_ID == 0)
        {
			setUY_Ciudad_ID (0);
			setUY_TR_Way_ID (0);
			setUY_TR_WayPoint_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_TR_WayPoint (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_TR_WayPoint[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_UY_Ciudad getUY_Ciudad() throws RuntimeException
    {
		return (I_UY_Ciudad)MTable.get(getCtx(), I_UY_Ciudad.Table_Name)
			.getPO(getUY_Ciudad_ID(), get_TrxName());	}

	/** Set UY_Ciudad.
		@param UY_Ciudad_ID UY_Ciudad	  */
	public void setUY_Ciudad_ID (int UY_Ciudad_ID)
	{
		if (UY_Ciudad_ID < 1) 
			set_Value (COLUMNNAME_UY_Ciudad_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Ciudad_ID, Integer.valueOf(UY_Ciudad_ID));
	}

	/** Get UY_Ciudad.
		@return UY_Ciudad	  */
	public int getUY_Ciudad_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Ciudad_ID);
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

	/** Set UY_TR_WayPoint.
		@param UY_TR_WayPoint_ID UY_TR_WayPoint	  */
	public void setUY_TR_WayPoint_ID (int UY_TR_WayPoint_ID)
	{
		if (UY_TR_WayPoint_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_TR_WayPoint_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_TR_WayPoint_ID, Integer.valueOf(UY_TR_WayPoint_ID));
	}

	/** Get UY_TR_WayPoint.
		@return UY_TR_WayPoint	  */
	public int getUY_TR_WayPoint_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_WayPoint_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}