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

/** Generated Model for UY_CashCountPayRule
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_CashCountPayRule extends PO implements I_UY_CashCountPayRule, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150908L;

    /** Standard Constructor */
    public X_UY_CashCountPayRule (Properties ctx, int UY_CashCountPayRule_ID, String trxName)
    {
      super (ctx, UY_CashCountPayRule_ID, trxName);
      /** if (UY_CashCountPayRule_ID == 0)
        {
			setC_Currency_ID (0);
			setUY_CashCount_ID (0);
			setUY_CashCountPayRule_ID (0);
			setUY_PaymentRule_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_CashCountPayRule (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_CashCountPayRule[")
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

	/** Set Amount2.
		@param Amount2 Amount2	  */
	public void setAmount2 (BigDecimal Amount2)
	{
		set_Value (COLUMNNAME_Amount2, Amount2);
	}

	/** Get Amount2.
		@return Amount2	  */
	public BigDecimal getAmount2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Amount2);
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

	public I_UY_CashCount getUY_CashCount() throws RuntimeException
    {
		return (I_UY_CashCount)MTable.get(getCtx(), I_UY_CashCount.Table_Name)
			.getPO(getUY_CashCount_ID(), get_TrxName());	}

	/** Set UY_CashCount.
		@param UY_CashCount_ID UY_CashCount	  */
	public void setUY_CashCount_ID (int UY_CashCount_ID)
	{
		if (UY_CashCount_ID < 1) 
			set_Value (COLUMNNAME_UY_CashCount_ID, null);
		else 
			set_Value (COLUMNNAME_UY_CashCount_ID, Integer.valueOf(UY_CashCount_ID));
	}

	/** Get UY_CashCount.
		@return UY_CashCount	  */
	public int getUY_CashCount_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_CashCount_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_CashCountPayRule.
		@param UY_CashCountPayRule_ID UY_CashCountPayRule	  */
	public void setUY_CashCountPayRule_ID (int UY_CashCountPayRule_ID)
	{
		if (UY_CashCountPayRule_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_CashCountPayRule_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_CashCountPayRule_ID, Integer.valueOf(UY_CashCountPayRule_ID));
	}

	/** Get UY_CashCountPayRule.
		@return UY_CashCountPayRule	  */
	public int getUY_CashCountPayRule_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_CashCountPayRule_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_PaymentRule getUY_PaymentRule() throws RuntimeException
    {
		return (I_UY_PaymentRule)MTable.get(getCtx(), I_UY_PaymentRule.Table_Name)
			.getPO(getUY_PaymentRule_ID(), get_TrxName());	}

	/** Set UY_PaymentRule.
		@param UY_PaymentRule_ID UY_PaymentRule	  */
	public void setUY_PaymentRule_ID (int UY_PaymentRule_ID)
	{
		if (UY_PaymentRule_ID < 1) 
			set_Value (COLUMNNAME_UY_PaymentRule_ID, null);
		else 
			set_Value (COLUMNNAME_UY_PaymentRule_ID, Integer.valueOf(UY_PaymentRule_ID));
	}

	/** Get UY_PaymentRule.
		@return UY_PaymentRule	  */
	public int getUY_PaymentRule_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_PaymentRule_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}