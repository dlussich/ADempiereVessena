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

/** Generated Model for UY_TT_CardTracking
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_TT_CardTracking extends PO implements I_UY_TT_CardTracking, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150903L;

    /** Standard Constructor */
    public X_UY_TT_CardTracking (Properties ctx, int UY_TT_CardTracking_ID, String trxName)
    {
      super (ctx, UY_TT_CardTracking_ID, trxName);
      /** if (UY_TT_CardTracking_ID == 0)
        {
			setAD_User_ID (0);
			setDateTrx (new Timestamp( System.currentTimeMillis() ));
			setUY_TT_Card_ID (0);
			setUY_TT_CardTracking_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_TT_CardTracking (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_TT_CardTracking[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_AD_User getAD_User() throws RuntimeException
    {
		return (org.compiere.model.I_AD_User)MTable.get(getCtx(), org.compiere.model.I_AD_User.Table_Name)
			.getPO(getAD_User_ID(), get_TrxName());	}

	/** Set User/Contact.
		@param AD_User_ID 
		User within the system - Internal or Business Partner Contact
	  */
	public void setAD_User_ID (int AD_User_ID)
	{
		if (AD_User_ID < 1) 
			set_Value (COLUMNNAME_AD_User_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_ID, Integer.valueOf(AD_User_ID));
	}

	/** Get User/Contact.
		@return User within the system - Internal or Business Partner Contact
	  */
	public int getAD_User_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Transaction Date.
		@param DateTrx 
		Transaction Date
	  */
	public void setDateTrx (Timestamp DateTrx)
	{
		set_Value (COLUMNNAME_DateTrx, DateTrx);
	}

	/** Get Transaction Date.
		@return Transaction Date
	  */
	public Timestamp getDateTrx () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateTrx);
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

	/** Set Locator Key.
		@param LocatorValue 
		Key of the Warehouse Locator
	  */
	public void setLocatorValue (int LocatorValue)
	{
		set_Value (COLUMNNAME_LocatorValue, Integer.valueOf(LocatorValue));
	}

	/** Get Locator Key.
		@return Key of the Warehouse Locator
	  */
	public int getLocatorValue () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_LocatorValue);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set SubAgencia.
		@param SubAgencia SubAgencia	  */
	public void setSubAgencia (String SubAgencia)
	{
		set_Value (COLUMNNAME_SubAgencia, SubAgencia);
	}

	/** Get SubAgencia.
		@return SubAgencia	  */
	public String getSubAgencia () 
	{
		return (String)get_Value(COLUMNNAME_SubAgencia);
	}

	/** Set UY_DeliveryPoint_ID_Actual.
		@param UY_DeliveryPoint_ID_Actual UY_DeliveryPoint_ID_Actual	  */
	public void setUY_DeliveryPoint_ID_Actual (int UY_DeliveryPoint_ID_Actual)
	{
		set_Value (COLUMNNAME_UY_DeliveryPoint_ID_Actual, Integer.valueOf(UY_DeliveryPoint_ID_Actual));
	}

	/** Get UY_DeliveryPoint_ID_Actual.
		@return UY_DeliveryPoint_ID_Actual	  */
	public int getUY_DeliveryPoint_ID_Actual () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_DeliveryPoint_ID_Actual);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TT_Box getUY_TT_Box() throws RuntimeException
    {
		return (I_UY_TT_Box)MTable.get(getCtx(), I_UY_TT_Box.Table_Name)
			.getPO(getUY_TT_Box_ID(), get_TrxName());	}

	/** Set UY_TT_Box.
		@param UY_TT_Box_ID UY_TT_Box	  */
	public void setUY_TT_Box_ID (int UY_TT_Box_ID)
	{
		if (UY_TT_Box_ID < 1) 
			set_Value (COLUMNNAME_UY_TT_Box_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TT_Box_ID, Integer.valueOf(UY_TT_Box_ID));
	}

	/** Get UY_TT_Box.
		@return UY_TT_Box	  */
	public int getUY_TT_Box_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TT_Box_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TT_Card getUY_TT_Card() throws RuntimeException
    {
		return (I_UY_TT_Card)MTable.get(getCtx(), I_UY_TT_Card.Table_Name)
			.getPO(getUY_TT_Card_ID(), get_TrxName());	}

	/** Set UY_TT_Card.
		@param UY_TT_Card_ID UY_TT_Card	  */
	public void setUY_TT_Card_ID (int UY_TT_Card_ID)
	{
		if (UY_TT_Card_ID < 1) 
			set_Value (COLUMNNAME_UY_TT_Card_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TT_Card_ID, Integer.valueOf(UY_TT_Card_ID));
	}

	/** Get UY_TT_Card.
		@return UY_TT_Card	  */
	public int getUY_TT_Card_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TT_Card_ID);
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

	/** Set UY_TT_CardTracking.
		@param UY_TT_CardTracking_ID UY_TT_CardTracking	  */
	public void setUY_TT_CardTracking_ID (int UY_TT_CardTracking_ID)
	{
		if (UY_TT_CardTracking_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_TT_CardTracking_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_TT_CardTracking_ID, Integer.valueOf(UY_TT_CardTracking_ID));
	}

	/** Get UY_TT_CardTracking.
		@return UY_TT_CardTracking	  */
	public int getUY_TT_CardTracking_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TT_CardTracking_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_TT_Seal getUY_TT_Seal() throws RuntimeException
    {
		return (I_UY_TT_Seal)MTable.get(getCtx(), I_UY_TT_Seal.Table_Name)
			.getPO(getUY_TT_Seal_ID(), get_TrxName());	}

	/** Set UY_TT_Seal.
		@param UY_TT_Seal_ID UY_TT_Seal	  */
	public void setUY_TT_Seal_ID (int UY_TT_Seal_ID)
	{
		if (UY_TT_Seal_ID < 1) 
			set_Value (COLUMNNAME_UY_TT_Seal_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TT_Seal_ID, Integer.valueOf(UY_TT_Seal_ID));
	}

	/** Get UY_TT_Seal.
		@return UY_TT_Seal	  */
	public int getUY_TT_Seal_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TT_Seal_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}