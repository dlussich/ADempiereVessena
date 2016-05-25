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

/** Generated Model for UY_TT_ReceiptCourierLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_TT_ReceiptCourierLine extends PO implements I_UY_TT_ReceiptCourierLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150822L;

    /** Standard Constructor */
    public X_UY_TT_ReceiptCourierLine (Properties ctx, int UY_TT_ReceiptCourierLine_ID, String trxName)
    {
      super (ctx, UY_TT_ReceiptCourierLine_ID, trxName);
      /** if (UY_TT_ReceiptCourierLine_ID == 0)
        {
			setIsValid (false);
			setUY_TT_ReceiptCourier_ID (0);
			setUY_TT_ReceiptCourierLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_TT_ReceiptCourierLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_TT_ReceiptCourierLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Address 1.
		@param Address1 
		Address line 1 for this location
	  */
	public void setAddress1 (String Address1)
	{
		set_Value (COLUMNNAME_Address1, Address1);
	}

	/** Get Address 1.
		@return Address line 1 for this location
	  */
	public String getAddress1 () 
	{
		return (String)get_Value(COLUMNNAME_Address1);
	}

	/** Set Valid.
		@param IsValid 
		Element is valid
	  */
	public void setIsValid (boolean IsValid)
	{
		set_Value (COLUMNNAME_IsValid, Boolean.valueOf(IsValid));
	}

	/** Get Valid.
		@return Element is valid
	  */
	public boolean isValid () 
	{
		Object oo = get_Value(COLUMNNAME_IsValid);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set localidad.
		@param localidad localidad	  */
	public void setlocalidad (String localidad)
	{
		set_Value (COLUMNNAME_localidad, localidad);
	}

	/** Get localidad.
		@return localidad	  */
	public String getlocalidad () 
	{
		return (String)get_Value(COLUMNNAME_localidad);
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

	/** Set ZIP.
		@param Postal 
		Postal code
	  */
	public void setPostal (String Postal)
	{
		set_Value (COLUMNNAME_Postal, Postal);
	}

	/** Get ZIP.
		@return Postal code
	  */
	public String getPostal () 
	{
		return (String)get_Value(COLUMNNAME_Postal);
	}

	public I_UY_R_Reclamo getUY_R_Reclamo() throws RuntimeException
    {
		return (I_UY_R_Reclamo)MTable.get(getCtx(), I_UY_R_Reclamo.Table_Name)
			.getPO(getUY_R_Reclamo_ID(), get_TrxName());	}

	/** Set UY_R_Reclamo.
		@param UY_R_Reclamo_ID UY_R_Reclamo	  */
	public void setUY_R_Reclamo_ID (int UY_R_Reclamo_ID)
	{
		if (UY_R_Reclamo_ID < 1) 
			set_Value (COLUMNNAME_UY_R_Reclamo_ID, null);
		else 
			set_Value (COLUMNNAME_UY_R_Reclamo_ID, Integer.valueOf(UY_R_Reclamo_ID));
	}

	/** Get UY_R_Reclamo.
		@return UY_R_Reclamo	  */
	public int getUY_R_Reclamo_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_R_Reclamo_ID);
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

	public I_UY_TT_ReceiptCourier getUY_TT_ReceiptCourier() throws RuntimeException
    {
		return (I_UY_TT_ReceiptCourier)MTable.get(getCtx(), I_UY_TT_ReceiptCourier.Table_Name)
			.getPO(getUY_TT_ReceiptCourier_ID(), get_TrxName());	}

	/** Set UY_TT_ReceiptCourier.
		@param UY_TT_ReceiptCourier_ID UY_TT_ReceiptCourier	  */
	public void setUY_TT_ReceiptCourier_ID (int UY_TT_ReceiptCourier_ID)
	{
		if (UY_TT_ReceiptCourier_ID < 1) 
			set_Value (COLUMNNAME_UY_TT_ReceiptCourier_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TT_ReceiptCourier_ID, Integer.valueOf(UY_TT_ReceiptCourier_ID));
	}

	/** Get UY_TT_ReceiptCourier.
		@return UY_TT_ReceiptCourier	  */
	public int getUY_TT_ReceiptCourier_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TT_ReceiptCourier_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TT_ReceiptCourierLine.
		@param UY_TT_ReceiptCourierLine_ID UY_TT_ReceiptCourierLine	  */
	public void setUY_TT_ReceiptCourierLine_ID (int UY_TT_ReceiptCourierLine_ID)
	{
		if (UY_TT_ReceiptCourierLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_TT_ReceiptCourierLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_TT_ReceiptCourierLine_ID, Integer.valueOf(UY_TT_ReceiptCourierLine_ID));
	}

	/** Get UY_TT_ReceiptCourierLine.
		@return UY_TT_ReceiptCourierLine	  */
	public int getUY_TT_ReceiptCourierLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TT_ReceiptCourierLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}