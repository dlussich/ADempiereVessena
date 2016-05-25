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

/** Generated Model for UY_TT_DeliveryDelPoint
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_TT_DeliveryDelPoint extends PO implements I_UY_TT_DeliveryDelPoint, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130912L;

    /** Standard Constructor */
    public X_UY_TT_DeliveryDelPoint (Properties ctx, int UY_TT_DeliveryDelPoint_ID, String trxName)
    {
      super (ctx, UY_TT_DeliveryDelPoint_ID, trxName);
      /** if (UY_TT_DeliveryDelPoint_ID == 0)
        {
			setUY_TT_DeliveryDelPoint_ID (0);
			setUY_TT_Delivery_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_TT_DeliveryDelPoint (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_TT_DeliveryDelPoint[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Selected.
		@param IsSelected Selected	  */
	public void setIsSelected (boolean IsSelected)
	{
		set_Value (COLUMNNAME_IsSelected, Boolean.valueOf(IsSelected));
	}

	/** Get Selected.
		@return Selected	  */
	public boolean isSelected () 
	{
		Object oo = get_Value(COLUMNNAME_IsSelected);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	public I_UY_DeliveryPoint getUY_DeliveryPoint() throws RuntimeException
    {
		return (I_UY_DeliveryPoint)MTable.get(getCtx(), I_UY_DeliveryPoint.Table_Name)
			.getPO(getUY_DeliveryPoint_ID(), get_TrxName());	}

	/** Set UY_DeliveryPoint.
		@param UY_DeliveryPoint_ID UY_DeliveryPoint	  */
	public void setUY_DeliveryPoint_ID (int UY_DeliveryPoint_ID)
	{
		if (UY_DeliveryPoint_ID < 1) 
			set_Value (COLUMNNAME_UY_DeliveryPoint_ID, null);
		else 
			set_Value (COLUMNNAME_UY_DeliveryPoint_ID, Integer.valueOf(UY_DeliveryPoint_ID));
	}

	/** Get UY_DeliveryPoint.
		@return UY_DeliveryPoint	  */
	public int getUY_DeliveryPoint_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_DeliveryPoint_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TT_DeliveryDelPoint.
		@param UY_TT_DeliveryDelPoint_ID UY_TT_DeliveryDelPoint	  */
	public void setUY_TT_DeliveryDelPoint_ID (int UY_TT_DeliveryDelPoint_ID)
	{
		if (UY_TT_DeliveryDelPoint_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_TT_DeliveryDelPoint_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_TT_DeliveryDelPoint_ID, Integer.valueOf(UY_TT_DeliveryDelPoint_ID));
	}

	/** Get UY_TT_DeliveryDelPoint.
		@return UY_TT_DeliveryDelPoint	  */
	public int getUY_TT_DeliveryDelPoint_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TT_DeliveryDelPoint_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TT_Delivery getUY_TT_Delivery() throws RuntimeException
    {
		return (I_UY_TT_Delivery)MTable.get(getCtx(), I_UY_TT_Delivery.Table_Name)
			.getPO(getUY_TT_Delivery_ID(), get_TrxName());	}

	/** Set UY_TT_Delivery.
		@param UY_TT_Delivery_ID UY_TT_Delivery	  */
	public void setUY_TT_Delivery_ID (int UY_TT_Delivery_ID)
	{
		if (UY_TT_Delivery_ID < 1) 
			set_Value (COLUMNNAME_UY_TT_Delivery_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TT_Delivery_ID, Integer.valueOf(UY_TT_Delivery_ID));
	}

	/** Get UY_TT_Delivery.
		@return UY_TT_Delivery	  */
	public int getUY_TT_Delivery_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TT_Delivery_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}