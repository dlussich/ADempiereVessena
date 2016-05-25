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
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;

/** Generated Model for UY_PayOrder_Resguardo
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_PayOrder_Resguardo extends PO implements I_UY_PayOrder_Resguardo, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130430L;

    /** Standard Constructor */
    public X_UY_PayOrder_Resguardo (Properties ctx, int UY_PayOrder_Resguardo_ID, String trxName)
    {
      super (ctx, UY_PayOrder_Resguardo_ID, trxName);
      /** if (UY_PayOrder_Resguardo_ID == 0)
        {
			setDateTrx (new Timestamp( System.currentTimeMillis() ));
			setUY_PayOrder_ID (0);
			setUY_PayOrder_Resguardo_ID (0);
			setUY_Resguardo_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_PayOrder_Resguardo (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_PayOrder_Resguardo[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Amount.
		@param Amount 
		Amount in a defined currency
	  */
	public void setAmount (BigDecimal Amount)
	{
		set_Value (COLUMNNAME_Amount, Amount);
	}

	/** Get Amount.
		@return Amount in a defined currency
	  */
	public BigDecimal getAmount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Amount);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	public I_UY_PayOrder getUY_PayOrder() throws RuntimeException
    {
		return (I_UY_PayOrder)MTable.get(getCtx(), I_UY_PayOrder.Table_Name)
			.getPO(getUY_PayOrder_ID(), get_TrxName());	}

	/** Set UY_PayOrder.
		@param UY_PayOrder_ID UY_PayOrder	  */
	public void setUY_PayOrder_ID (int UY_PayOrder_ID)
	{
		if (UY_PayOrder_ID < 1) 
			set_Value (COLUMNNAME_UY_PayOrder_ID, null);
		else 
			set_Value (COLUMNNAME_UY_PayOrder_ID, Integer.valueOf(UY_PayOrder_ID));
	}

	/** Get UY_PayOrder.
		@return UY_PayOrder	  */
	public int getUY_PayOrder_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_PayOrder_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_PayOrder_Resguardo.
		@param UY_PayOrder_Resguardo_ID UY_PayOrder_Resguardo	  */
	public void setUY_PayOrder_Resguardo_ID (int UY_PayOrder_Resguardo_ID)
	{
		if (UY_PayOrder_Resguardo_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_PayOrder_Resguardo_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_PayOrder_Resguardo_ID, Integer.valueOf(UY_PayOrder_Resguardo_ID));
	}

	/** Get UY_PayOrder_Resguardo.
		@return UY_PayOrder_Resguardo	  */
	public int getUY_PayOrder_Resguardo_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_PayOrder_Resguardo_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_Resguardo getUY_Resguardo() throws RuntimeException
    {
		return (I_UY_Resguardo)MTable.get(getCtx(), I_UY_Resguardo.Table_Name)
			.getPO(getUY_Resguardo_ID(), get_TrxName());	}

	/** Set UY_Resguardo.
		@param UY_Resguardo_ID UY_Resguardo	  */
	public void setUY_Resguardo_ID (int UY_Resguardo_ID)
	{
		if (UY_Resguardo_ID < 1) 
			set_Value (COLUMNNAME_UY_Resguardo_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Resguardo_ID, Integer.valueOf(UY_Resguardo_ID));
	}

	/** Get UY_Resguardo.
		@return UY_Resguardo	  */
	public int getUY_Resguardo_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Resguardo_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}