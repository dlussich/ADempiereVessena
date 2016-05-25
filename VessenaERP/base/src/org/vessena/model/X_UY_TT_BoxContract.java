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
import org.compiere.util.KeyNamePair;

/** Generated Model for UY_TT_BoxContract
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_TT_BoxContract extends PO implements I_UY_TT_BoxContract, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20151011L;

    /** Standard Constructor */
    public X_UY_TT_BoxContract (Properties ctx, int UY_TT_BoxContract_ID, String trxName)
    {
      super (ctx, UY_TT_BoxContract_ID, trxName);
      /** if (UY_TT_BoxContract_ID == 0)
        {
			setAD_User_ID (0);
			setDateTrx (new Timestamp( System.currentTimeMillis() ));
			setUY_TT_BoxContract_ID (0);
			setUY_TT_Box_ID (0);
			setUY_TT_Contract_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_TT_BoxContract (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_TT_BoxContract[")
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

	/** Set solnro.
		@param solnro solnro	  */
	public void setsolnro (String solnro)
	{
		set_Value (COLUMNNAME_solnro, solnro);
	}

	/** Get solnro.
		@return solnro	  */
	public String getsolnro () 
	{
		return (String)get_Value(COLUMNNAME_solnro);
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

	/** Set UY_TT_BoxContract.
		@param UY_TT_BoxContract_ID UY_TT_BoxContract	  */
	public void setUY_TT_BoxContract_ID (int UY_TT_BoxContract_ID)
	{
		if (UY_TT_BoxContract_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_TT_BoxContract_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_TT_BoxContract_ID, Integer.valueOf(UY_TT_BoxContract_ID));
	}

	/** Get UY_TT_BoxContract.
		@return UY_TT_BoxContract	  */
	public int getUY_TT_BoxContract_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TT_BoxContract_ID);
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

	public I_UY_TT_Contract getUY_TT_Contract() throws RuntimeException
    {
		return (I_UY_TT_Contract)MTable.get(getCtx(), I_UY_TT_Contract.Table_Name)
			.getPO(getUY_TT_Contract_ID(), get_TrxName());	}

	/** Set UY_TT_Contract.
		@param UY_TT_Contract_ID UY_TT_Contract	  */
	public void setUY_TT_Contract_ID (int UY_TT_Contract_ID)
	{
		if (UY_TT_Contract_ID < 1) 
			set_Value (COLUMNNAME_UY_TT_Contract_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TT_Contract_ID, Integer.valueOf(UY_TT_Contract_ID));
	}

	/** Get UY_TT_Contract.
		@return UY_TT_Contract	  */
	public int getUY_TT_Contract_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TT_Contract_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}