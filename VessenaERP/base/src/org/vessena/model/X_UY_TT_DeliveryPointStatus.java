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
import org.compiere.util.KeyNamePair;

/** Generated Model for UY_TT_DeliveryPointStatus
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_TT_DeliveryPointStatus extends PO implements I_UY_TT_DeliveryPointStatus, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130814L;

    /** Standard Constructor */
    public X_UY_TT_DeliveryPointStatus (Properties ctx, int UY_TT_DeliveryPointStatus_ID, String trxName)
    {
      super (ctx, UY_TT_DeliveryPointStatus_ID, trxName);
      /** if (UY_TT_DeliveryPointStatus_ID == 0)
        {
			setName (null);
			setUY_DeliveryPoint_ID (0);
			setUY_TT_CardStatus_ID (0);
			setUY_TT_DeliveryPointStatus_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_TT_DeliveryPointStatus (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_TT_DeliveryPointStatus[")
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

	/** Set InCourier.
		@param InCourier 
		InCourier
	  */
	public void setInCourier (boolean InCourier)
	{
		set_Value (COLUMNNAME_InCourier, Boolean.valueOf(InCourier));
	}

	/** Get InCourier.
		@return InCourier
	  */
	public boolean isInCourier () 
	{
		Object oo = get_Value(COLUMNNAME_InCourier);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	public void setName (String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName () 
	{
		return (String)get_Value(COLUMNNAME_Name);
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getName());
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

	public I_UY_TT_CardStatus getUY_TT_CardStatus() throws RuntimeException
    {
		return (I_UY_TT_CardStatus)MTable.get(getCtx(), I_UY_TT_CardStatus.Table_Name)
			.getPO(getUY_TT_CardStatus_ID(), get_TrxName());	}

	/** Set UY_TT_CardStatus.
		@param UY_TT_CardStatus_ID UY_TT_CardStatus	  */
	public void setUY_TT_CardStatus_ID (int UY_TT_CardStatus_ID)
	{
		if (UY_TT_CardStatus_ID < 1) 
			set_Value (COLUMNNAME_UY_TT_CardStatus_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TT_CardStatus_ID, Integer.valueOf(UY_TT_CardStatus_ID));
	}

	/** Get UY_TT_CardStatus.
		@return UY_TT_CardStatus	  */
	public int getUY_TT_CardStatus_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TT_CardStatus_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TT_DeliveryPointStatus.
		@param UY_TT_DeliveryPointStatus_ID UY_TT_DeliveryPointStatus	  */
	public void setUY_TT_DeliveryPointStatus_ID (int UY_TT_DeliveryPointStatus_ID)
	{
		if (UY_TT_DeliveryPointStatus_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_TT_DeliveryPointStatus_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_TT_DeliveryPointStatus_ID, Integer.valueOf(UY_TT_DeliveryPointStatus_ID));
	}

	/** Get UY_TT_DeliveryPointStatus.
		@return UY_TT_DeliveryPointStatus	  */
	public int getUY_TT_DeliveryPointStatus_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TT_DeliveryPointStatus_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}