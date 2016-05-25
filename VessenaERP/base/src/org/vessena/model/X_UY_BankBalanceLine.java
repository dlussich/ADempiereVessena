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

/** Generated Model for UY_BankBalanceLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_BankBalanceLine extends PO implements I_UY_BankBalanceLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140926L;

    /** Standard Constructor */
    public X_UY_BankBalanceLine (Properties ctx, int UY_BankBalanceLine_ID, String trxName)
    {
      super (ctx, UY_BankBalanceLine_ID, trxName);
      /** if (UY_BankBalanceLine_ID == 0)
        {
			setAmtSourceCr (Env.ZERO);
			setAmtSourceDr (Env.ZERO);
			setC_BankAccount_ID (0);
			setUY_BankBalance_ID (0);
			setUY_BankBalanceLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_BankBalanceLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_BankBalanceLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Source Credit.
		@param AmtSourceCr 
		Source Credit Amount
	  */
	public void setAmtSourceCr (BigDecimal AmtSourceCr)
	{
		set_Value (COLUMNNAME_AmtSourceCr, AmtSourceCr);
	}

	/** Get Source Credit.
		@return Source Credit Amount
	  */
	public BigDecimal getAmtSourceCr () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtSourceCr);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Source Debit.
		@param AmtSourceDr 
		Source Debit Amount
	  */
	public void setAmtSourceDr (BigDecimal AmtSourceDr)
	{
		set_Value (COLUMNNAME_AmtSourceDr, AmtSourceDr);
	}

	/** Get Source Debit.
		@return Source Debit Amount
	  */
	public BigDecimal getAmtSourceDr () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtSourceDr);
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

	public I_UY_BankBalance getUY_BankBalance() throws RuntimeException
    {
		return (I_UY_BankBalance)MTable.get(getCtx(), I_UY_BankBalance.Table_Name)
			.getPO(getUY_BankBalance_ID(), get_TrxName());	}

	/** Set UY_BankBalance.
		@param UY_BankBalance_ID UY_BankBalance	  */
	public void setUY_BankBalance_ID (int UY_BankBalance_ID)
	{
		if (UY_BankBalance_ID < 1) 
			set_Value (COLUMNNAME_UY_BankBalance_ID, null);
		else 
			set_Value (COLUMNNAME_UY_BankBalance_ID, Integer.valueOf(UY_BankBalance_ID));
	}

	/** Get UY_BankBalance.
		@return UY_BankBalance	  */
	public int getUY_BankBalance_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_BankBalance_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_BankBalanceLine.
		@param UY_BankBalanceLine_ID UY_BankBalanceLine	  */
	public void setUY_BankBalanceLine_ID (int UY_BankBalanceLine_ID)
	{
		if (UY_BankBalanceLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_BankBalanceLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_BankBalanceLine_ID, Integer.valueOf(UY_BankBalanceLine_ID));
	}

	/** Get UY_BankBalanceLine.
		@return UY_BankBalanceLine	  */
	public int getUY_BankBalanceLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_BankBalanceLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}