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

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;

/** Generated Model for UY_StoreCash
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_StoreCash extends PO implements I_UY_StoreCash, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20160229L;

    /** Standard Constructor */
    public X_UY_StoreCash (Properties ctx, int UY_StoreCash_ID, String trxName)
    {
      super (ctx, UY_StoreCash_ID, trxName);
      /** if (UY_StoreCash_ID == 0)
        {
			setName (null);
			setStatusStoreCash (null);
// CERRADA
			setUY_StoreCash_ID (0);
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_UY_StoreCash (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_StoreCash[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Amount.
		@param Amt 
		Amount
	  */
	public void setAmt (BigDecimal Amt)
	{
		set_Value (COLUMNNAME_Amt, Amt);
	}

	/** Get Amount.
		@return Amount
	  */
	public BigDecimal getAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Amt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtopen.
		@param amtopen amtopen	  */
	public void setamtopen (BigDecimal amtopen)
	{
		set_Value (COLUMNNAME_amtopen, amtopen);
	}

	/** Get amtopen.
		@return amtopen	  */
	public BigDecimal getamtopen () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtopen);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	public org.compiere.model.I_M_Warehouse getM_Warehouse() throws RuntimeException
    {
		return (org.compiere.model.I_M_Warehouse)MTable.get(getCtx(), org.compiere.model.I_M_Warehouse.Table_Name)
			.getPO(getM_Warehouse_ID(), get_TrxName());	}

	/** Set Warehouse.
		@param M_Warehouse_ID 
		Storage Warehouse and Service Point
	  */
	public void setM_Warehouse_ID (int M_Warehouse_ID)
	{
		if (M_Warehouse_ID < 1) 
			set_Value (COLUMNNAME_M_Warehouse_ID, null);
		else 
			set_Value (COLUMNNAME_M_Warehouse_ID, Integer.valueOf(M_Warehouse_ID));
	}

	/** Get Warehouse.
		@return Storage Warehouse and Service Point
	  */
	public int getM_Warehouse_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Warehouse_ID);
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

	/** StatusStoreCash AD_Reference_ID=1000521 */
	public static final int STATUSSTORECASH_AD_Reference_ID=1000521;
	/** ABIERTA = ABIERTA */
	public static final String STATUSSTORECASH_ABIERTA = "ABIERTA";
	/** CERRADA = CERRADA */
	public static final String STATUSSTORECASH_CERRADA = "CERRADA";
	/** Set StatusStoreCash.
		@param StatusStoreCash StatusStoreCash	  */
	public void setStatusStoreCash (String StatusStoreCash)
	{

		set_Value (COLUMNNAME_StatusStoreCash, StatusStoreCash);
	}

	/** Get StatusStoreCash.
		@return StatusStoreCash	  */
	public String getStatusStoreCash () 
	{
		return (String)get_Value(COLUMNNAME_StatusStoreCash);
	}

	/** Set UY_StoreCash.
		@param UY_StoreCash_ID UY_StoreCash	  */
	public void setUY_StoreCash_ID (int UY_StoreCash_ID)
	{
		if (UY_StoreCash_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_StoreCash_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_StoreCash_ID, Integer.valueOf(UY_StoreCash_ID));
	}

	/** Get UY_StoreCash.
		@return UY_StoreCash	  */
	public int getUY_StoreCash_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_StoreCash_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Search Key.
		@param Value 
		Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Search Key.
		@return Search key for the record in the format required - must be unique
	  */
	public String getValue () 
	{
		return (String)get_Value(COLUMNNAME_Value);
	}
}