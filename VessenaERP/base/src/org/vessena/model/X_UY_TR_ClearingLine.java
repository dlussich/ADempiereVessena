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

/** Generated Model for UY_TR_ClearingLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_TR_ClearingLine extends PO implements I_UY_TR_ClearingLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150611L;

    /** Standard Constructor */
    public X_UY_TR_ClearingLine (Properties ctx, int UY_TR_ClearingLine_ID, String trxName)
    {
      super (ctx, UY_TR_ClearingLine_ID, trxName);
      /** if (UY_TR_ClearingLine_ID == 0)
        {
			setAmount (Env.ZERO);
// 0
			setDifferenceAmt (Env.ZERO);
			setExpenseAmt (Env.ZERO);
			setSaldoAnterior (Env.ZERO);
			setUY_TR_Clearing_ID (0);
			setUY_TR_ClearingLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_TR_ClearingLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_TR_ClearingLine[")
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

	/** Set Amount3.
		@param Amount3 Amount3	  */
	public void setAmount3 (BigDecimal Amount3)
	{
		set_Value (COLUMNNAME_Amount3, Amount3);
	}

	/** Get Amount3.
		@return Amount3	  */
	public BigDecimal getAmount3 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Amount3);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public org.compiere.model.I_C_BankAccount getC_BankAccount() throws RuntimeException
    {
		return (org.compiere.model.I_C_BankAccount)MTable.get(getCtx(), org.compiere.model.I_C_BankAccount.Table_Name)
			.getPO(getC_BankAccount_ID(), get_TrxName());	}

	/** Set Bank Account.
		@param C_BankAccount_ID 
		Account at the Bank
	  */
	public void setC_BankAccount_ID (int C_BankAccount_ID)
	{
		if (C_BankAccount_ID < 1) 
			set_Value (COLUMNNAME_C_BankAccount_ID, null);
		else 
			set_Value (COLUMNNAME_C_BankAccount_ID, Integer.valueOf(C_BankAccount_ID));
	}

	/** Get Bank Account.
		@return Account at the Bank
	  */
	public int getC_BankAccount_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BankAccount_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Rate.
		@param CurrencyRate 
		Currency Conversion Rate
	  */
	public void setCurrencyRate (BigDecimal CurrencyRate)
	{
		set_Value (COLUMNNAME_CurrencyRate, CurrencyRate);
	}

	/** Get Rate.
		@return Currency Conversion Rate
	  */
	public BigDecimal getCurrencyRate () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CurrencyRate);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Difference.
		@param DifferenceAmt 
		Difference Amount
	  */
	public void setDifferenceAmt (BigDecimal DifferenceAmt)
	{
		set_Value (COLUMNNAME_DifferenceAmt, DifferenceAmt);
	}

	/** Get Difference.
		@return Difference Amount
	  */
	public BigDecimal getDifferenceAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DifferenceAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Expense Amount.
		@param ExpenseAmt 
		Amount for this expense
	  */
	public void setExpenseAmt (BigDecimal ExpenseAmt)
	{
		set_Value (COLUMNNAME_ExpenseAmt, ExpenseAmt);
	}

	/** Get Expense Amount.
		@return Amount for this expense
	  */
	public BigDecimal getExpenseAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ExpenseAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set montopesos.
		@param montopesos montopesos	  */
	public void setmontopesos (BigDecimal montopesos)
	{
		set_Value (COLUMNNAME_montopesos, montopesos);
	}

	/** Get montopesos.
		@return montopesos	  */
	public BigDecimal getmontopesos () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_montopesos);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set SaldoAnterior.
		@param SaldoAnterior 
		SaldoAnterior
	  */
	public void setSaldoAnterior (BigDecimal SaldoAnterior)
	{
		set_Value (COLUMNNAME_SaldoAnterior, SaldoAnterior);
	}

	/** Get SaldoAnterior.
		@return SaldoAnterior
	  */
	public BigDecimal getSaldoAnterior () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_SaldoAnterior);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set SaldoFinal.
		@param SaldoFinal SaldoFinal	  */
	public void setSaldoFinal (BigDecimal SaldoFinal)
	{
		set_Value (COLUMNNAME_SaldoFinal, SaldoFinal);
	}

	/** Get SaldoFinal.
		@return SaldoFinal	  */
	public BigDecimal getSaldoFinal () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_SaldoFinal);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public I_UY_TR_Clearing getUY_TR_Clearing() throws RuntimeException
    {
		return (I_UY_TR_Clearing)MTable.get(getCtx(), I_UY_TR_Clearing.Table_Name)
			.getPO(getUY_TR_Clearing_ID(), get_TrxName());	}

	/** Set UY_TR_Clearing.
		@param UY_TR_Clearing_ID UY_TR_Clearing	  */
	public void setUY_TR_Clearing_ID (int UY_TR_Clearing_ID)
	{
		if (UY_TR_Clearing_ID < 1) 
			set_Value (COLUMNNAME_UY_TR_Clearing_ID, null);
		else 
			set_Value (COLUMNNAME_UY_TR_Clearing_ID, Integer.valueOf(UY_TR_Clearing_ID));
	}

	/** Get UY_TR_Clearing.
		@return UY_TR_Clearing	  */
	public int getUY_TR_Clearing_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_Clearing_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_TR_ClearingLine.
		@param UY_TR_ClearingLine_ID UY_TR_ClearingLine	  */
	public void setUY_TR_ClearingLine_ID (int UY_TR_ClearingLine_ID)
	{
		if (UY_TR_ClearingLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_TR_ClearingLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_TR_ClearingLine_ID, Integer.valueOf(UY_TR_ClearingLine_ID));
	}

	/** Get UY_TR_ClearingLine.
		@return UY_TR_ClearingLine	  */
	public int getUY_TR_ClearingLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_TR_ClearingLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}