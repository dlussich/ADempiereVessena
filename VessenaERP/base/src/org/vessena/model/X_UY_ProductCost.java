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

/** Generated Model for UY_ProductCost
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_UY_ProductCost extends PO implements I_UY_ProductCost, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_UY_ProductCost (Properties ctx, int UY_ProductCost_ID, String trxName)
    {
      super (ctx, UY_ProductCost_ID, trxName);
      /** if (UY_ProductCost_ID == 0)
        {
			setC_Currency_ID (0);
			setM_Product_ID (0);
			setUY_ProductCost_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_ProductCost (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_ProductCost[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Accounted Amount.
		@param AmtAcct 
		Amount Balance in Currency of Accounting Schema
	  */
	public void setAmtAcct (BigDecimal AmtAcct)
	{
		set_Value (COLUMNNAME_AmtAcct, AmtAcct);
	}

	/** Get Accounted Amount.
		@return Amount Balance in Currency of Accounting Schema
	  */
	public BigDecimal getAmtAcct () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtAcct);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set AmtInCurrency.
		@param AmtInCurrency AmtInCurrency	  */
	public void setAmtInCurrency (BigDecimal AmtInCurrency)
	{
		set_Value (COLUMNNAME_AmtInCurrency, AmtInCurrency);
	}

	/** Get AmtInCurrency.
		@return AmtInCurrency	  */
	public BigDecimal getAmtInCurrency () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtInCurrency);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public I_C_Currency getC_Currency() throws RuntimeException
    {
		return (I_C_Currency)MTable.get(getCtx(), I_C_Currency.Table_Name)
			.getPO(getC_Currency_ID(), get_TrxName());	}

	/** Set Currency.
		@param C_Currency_ID 
		The Currency for this record
	  */
	public void setC_Currency_ID (int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
	}

	/** Get Currency.
		@return The Currency for this record
	  */
	public int getC_Currency_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency_ID);
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

	/** Set Divide Rate.
		@param DivideRate 
		To convert Source number to Target number, the Source is divided
	  */
	public void setDivideRate (BigDecimal DivideRate)
	{
		set_Value (COLUMNNAME_DivideRate, DivideRate);
	}

	/** Get Divide Rate.
		@return To convert Source number to Target number, the Source is divided
	  */
	public BigDecimal getDivideRate () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DivideRate);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public I_M_Product getM_Product() throws RuntimeException
    {
		return (I_M_Product)MTable.get(getCtx(), I_M_Product.Table_Name)
			.getPO(getM_Product_ID(), get_TrxName());	}

	/** Set Product.
		@param M_Product_ID 
		Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	/** Get Product.
		@return Product, Service, Item
	  */
	public int getM_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_ProductCost.
		@param UY_ProductCost_ID UY_ProductCost	  */
	public void setUY_ProductCost_ID (int UY_ProductCost_ID)
	{
		if (UY_ProductCost_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_ProductCost_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_ProductCost_ID, Integer.valueOf(UY_ProductCost_ID));
	}

	/** Get UY_ProductCost.
		@return UY_ProductCost	  */
	public int getUY_ProductCost_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_ProductCost_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TipoCosteo.
		@param UY_TipoCosteo UY_TipoCosteo	  */
	public void setUY_TipoCosteo (String UY_TipoCosteo)
	{
		set_Value (COLUMNNAME_UY_TipoCosteo, UY_TipoCosteo);
	}

	/** Get UY_TipoCosteo.
		@return UY_TipoCosteo	  */
	public String getUY_TipoCosteo () 
	{
		return (String)get_Value(COLUMNNAME_UY_TipoCosteo);
	}
}