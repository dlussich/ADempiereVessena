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

/** Generated Model for UY_StoreLOPay
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_StoreLOPay extends PO implements I_UY_StoreLOPay, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20160216L;

    /** Standard Constructor */
    public X_UY_StoreLOPay (Properties ctx, int UY_StoreLOPay_ID, String trxName)
    {
      super (ctx, UY_StoreLOPay_ID, trxName);
      /** if (UY_StoreLOPay_ID == 0)
        {
			setamtmt (Env.ZERO);
			setamtusd (Env.ZERO);
			setC_Currency_ID (0);
			setUY_StoreLoadOrder_ID (0);
			setUY_StoreLOPay_ID (0);
			setUY_StoreLOSale_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_StoreLOPay (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_StoreLOPay[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set amtmt.
		@param amtmt amtmt	  */
	public void setamtmt (BigDecimal amtmt)
	{
		set_Value (COLUMNNAME_amtmt, amtmt);
	}

	/** Get amtmt.
		@return amtmt	  */
	public BigDecimal getamtmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtusd.
		@param amtusd amtusd	  */
	public void setamtusd (BigDecimal amtusd)
	{
		set_Value (COLUMNNAME_amtusd, amtusd);
	}

	/** Get amtusd.
		@return amtusd	  */
	public BigDecimal getamtusd () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtusd);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public org.compiere.model.I_C_Currency getC_Currency() throws RuntimeException
    {
		return (org.compiere.model.I_C_Currency)MTable.get(getCtx(), org.compiere.model.I_C_Currency.Table_Name)
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

	/** Set creditcard.
		@param creditcard creditcard	  */
	public void setcreditcard (String creditcard)
	{
		set_Value (COLUMNNAME_creditcard, creditcard);
	}

	/** Get creditcard.
		@return creditcard	  */
	public String getcreditcard () 
	{
		return (String)get_Value(COLUMNNAME_creditcard);
	}

	/** Set paytype.
		@param paytype paytype	  */
	public void setpaytype (String paytype)
	{
		set_Value (COLUMNNAME_paytype, paytype);
	}

	/** Get paytype.
		@return paytype	  */
	public String getpaytype () 
	{
		return (String)get_Value(COLUMNNAME_paytype);
	}

	/** Set transno.
		@param transno transno	  */
	public void settransno (String transno)
	{
		set_Value (COLUMNNAME_transno, transno);
	}

	/** Get transno.
		@return transno	  */
	public String gettransno () 
	{
		return (String)get_Value(COLUMNNAME_transno);
	}

	public I_UY_StoreLoadOrder getUY_StoreLoadOrder() throws RuntimeException
    {
		return (I_UY_StoreLoadOrder)MTable.get(getCtx(), I_UY_StoreLoadOrder.Table_Name)
			.getPO(getUY_StoreLoadOrder_ID(), get_TrxName());	}

	/** Set UY_StoreLoadOrder.
		@param UY_StoreLoadOrder_ID UY_StoreLoadOrder	  */
	public void setUY_StoreLoadOrder_ID (int UY_StoreLoadOrder_ID)
	{
		if (UY_StoreLoadOrder_ID < 1) 
			set_Value (COLUMNNAME_UY_StoreLoadOrder_ID, null);
		else 
			set_Value (COLUMNNAME_UY_StoreLoadOrder_ID, Integer.valueOf(UY_StoreLoadOrder_ID));
	}

	/** Get UY_StoreLoadOrder.
		@return UY_StoreLoadOrder	  */
	public int getUY_StoreLoadOrder_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_StoreLoadOrder_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_StoreLOPay.
		@param UY_StoreLOPay_ID UY_StoreLOPay	  */
	public void setUY_StoreLOPay_ID (int UY_StoreLOPay_ID)
	{
		if (UY_StoreLOPay_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_StoreLOPay_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_StoreLOPay_ID, Integer.valueOf(UY_StoreLOPay_ID));
	}

	/** Get UY_StoreLOPay.
		@return UY_StoreLOPay	  */
	public int getUY_StoreLOPay_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_StoreLOPay_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_StoreLOSale getUY_StoreLOSale() throws RuntimeException
    {
		return (I_UY_StoreLOSale)MTable.get(getCtx(), I_UY_StoreLOSale.Table_Name)
			.getPO(getUY_StoreLOSale_ID(), get_TrxName());	}

	/** Set UY_StoreLOSale.
		@param UY_StoreLOSale_ID UY_StoreLOSale	  */
	public void setUY_StoreLOSale_ID (int UY_StoreLOSale_ID)
	{
		if (UY_StoreLOSale_ID < 1) 
			set_Value (COLUMNNAME_UY_StoreLOSale_ID, null);
		else 
			set_Value (COLUMNNAME_UY_StoreLOSale_ID, Integer.valueOf(UY_StoreLOSale_ID));
	}

	/** Get UY_StoreLOSale.
		@return UY_StoreLOSale	  */
	public int getUY_StoreLOSale_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_StoreLOSale_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}