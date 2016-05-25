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

/** Generated Model for UY_CashConfig
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_CashConfig extends PO implements I_UY_CashConfig, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20160219L;

    /** Standard Constructor */
    public X_UY_CashConfig (Properties ctx, int UY_CashConfig_ID, String trxName)
    {
      super (ctx, UY_CashConfig_ID, trxName);
      /** if (UY_CashConfig_ID == 0)
        {
			setCantBilletes (0);
// 0
			setCantTickets (0);
// 0
			setC_BankAccount_ID (0);
			setC_BankAccount_ID_1 (0);
			setUY_CashConfig_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_CashConfig (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_CashConfig[")
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

	/** Set CantBilletes.
		@param CantBilletes CantBilletes	  */
	public void setCantBilletes (int CantBilletes)
	{
		set_Value (COLUMNNAME_CantBilletes, Integer.valueOf(CantBilletes));
	}

	/** Get CantBilletes.
		@return CantBilletes	  */
	public int getCantBilletes () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CantBilletes);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set CantTickets.
		@param CantTickets CantTickets	  */
	public void setCantTickets (int CantTickets)
	{
		set_Value (COLUMNNAME_CantTickets, Integer.valueOf(CantTickets));
	}

	/** Get CantTickets.
		@return CantTickets	  */
	public int getCantTickets () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CantTickets);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getCashCountCashier_A() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getCashCountCashier_Acct(), get_TrxName());	}

	/** Set CashCountCashier_Acct.
		@param CashCountCashier_Acct CashCountCashier_Acct	  */
	public void setCashCountCashier_Acct (int CashCountCashier_Acct)
	{
		set_Value (COLUMNNAME_CashCountCashier_Acct, Integer.valueOf(CashCountCashier_Acct));
	}

	/** Get CashCountCashier_Acct.
		@return CashCountCashier_Acct	  */
	public int getCashCountCashier_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CashCountCashier_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getCashCountCredit_A() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getCashCountCredit_Acct(), get_TrxName());	}

	/** Set CashCountCredit_Acct.
		@param CashCountCredit_Acct CashCountCredit_Acct	  */
	public void setCashCountCredit_Acct (int CashCountCredit_Acct)
	{
		set_Value (COLUMNNAME_CashCountCredit_Acct, Integer.valueOf(CashCountCredit_Acct));
	}

	/** Get CashCountCredit_Acct.
		@return CashCountCredit_Acct	  */
	public int getCashCountCredit_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CashCountCredit_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getCashCountCredit_Acc() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getCashCountCredit_Acct_1(), get_TrxName());	}

	/** Set CashCountCredit_Acct_1.
		@param CashCountCredit_Acct_1 CashCountCredit_Acct_1	  */
	public void setCashCountCredit_Acct_1 (int CashCountCredit_Acct_1)
	{
		set_Value (COLUMNNAME_CashCountCredit_Acct_1, Integer.valueOf(CashCountCredit_Acct_1));
	}

	/** Get CashCountCredit_Acct_1.
		@return CashCountCredit_Acct_1	  */
	public int getCashCountCredit_Acct_1 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CashCountCredit_Acct_1);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getCashCountDiff_A() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getCashCountDiff_Acct(), get_TrxName());	}

	/** Set CashCountDiff_Acct.
		@param CashCountDiff_Acct CashCountDiff_Acct	  */
	public void setCashCountDiff_Acct (int CashCountDiff_Acct)
	{
		set_Value (COLUMNNAME_CashCountDiff_Acct, Integer.valueOf(CashCountDiff_Acct));
	}

	/** Get CashCountDiff_Acct.
		@return CashCountDiff_Acct	  */
	public int getCashCountDiff_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CashCountDiff_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getCashCountDiff_Acc() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getCashCountDiff_Acct_1(), get_TrxName());	}

	/** Set CashCountDiff_Acct_1.
		@param CashCountDiff_Acct_1 CashCountDiff_Acct_1	  */
	public void setCashCountDiff_Acct_1 (int CashCountDiff_Acct_1)
	{
		set_Value (COLUMNNAME_CashCountDiff_Acct_1, Integer.valueOf(CashCountDiff_Acct_1));
	}

	/** Get CashCountDiff_Acct_1.
		@return CashCountDiff_Acct_1	  */
	public int getCashCountDiff_Acct_1 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CashCountDiff_Acct_1);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getCashCountEnvase_A() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getCashCountEnvase_Acct(), get_TrxName());	}

	/** Set CashCountEnvase_Acct.
		@param CashCountEnvase_Acct CashCountEnvase_Acct	  */
	public void setCashCountEnvase_Acct (int CashCountEnvase_Acct)
	{
		set_Value (COLUMNNAME_CashCountEnvase_Acct, Integer.valueOf(CashCountEnvase_Acct));
	}

	/** Get CashCountEnvase_Acct.
		@return CashCountEnvase_Acct	  */
	public int getCashCountEnvase_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CashCountEnvase_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getCashCountService_A() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getCashCountService_Acct(), get_TrxName());	}

	/** Set CashCountService_Acct.
		@param CashCountService_Acct CashCountService_Acct	  */
	public void setCashCountService_Acct (int CashCountService_Acct)
	{
		set_Value (COLUMNNAME_CashCountService_Acct, Integer.valueOf(CashCountService_Acct));
	}

	/** Get CashCountService_Acct.
		@return CashCountService_Acct	  */
	public int getCashCountService_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CashCountService_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set C_BankAccount_ID_1.
		@param C_BankAccount_ID_1 C_BankAccount_ID_1	  */
	public void setC_BankAccount_ID_1 (int C_BankAccount_ID_1)
	{
		set_Value (COLUMNNAME_C_BankAccount_ID_1, Integer.valueOf(C_BankAccount_ID_1));
	}

	/** Get C_BankAccount_ID_1.
		@return C_BankAccount_ID_1	  */
	public int getC_BankAccount_ID_1 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BankAccount_ID_1);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set C_BankAccount_ID_2.
		@param C_BankAccount_ID_2 C_BankAccount_ID_2	  */
	public void setC_BankAccount_ID_2 (int C_BankAccount_ID_2)
	{
		set_Value (COLUMNNAME_C_BankAccount_ID_2, Integer.valueOf(C_BankAccount_ID_2));
	}

	/** Get C_BankAccount_ID_2.
		@return C_BankAccount_ID_2	  */
	public int getC_BankAccount_ID_2 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BankAccount_ID_2);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set C_BankAccount_ID_3.
		@param C_BankAccount_ID_3 C_BankAccount_ID_3	  */
	public void setC_BankAccount_ID_3 (int C_BankAccount_ID_3)
	{
		set_Value (COLUMNNAME_C_BankAccount_ID_3, Integer.valueOf(C_BankAccount_ID_3));
	}

	/** Get C_BankAccount_ID_3.
		@return C_BankAccount_ID_3	  */
	public int getC_BankAccount_ID_3 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BankAccount_ID_3);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set C_BankAccount_ID_4.
		@param C_BankAccount_ID_4 C_BankAccount_ID_4	  */
	public void setC_BankAccount_ID_4 (int C_BankAccount_ID_4)
	{
		set_Value (COLUMNNAME_C_BankAccount_ID_4, Integer.valueOf(C_BankAccount_ID_4));
	}

	/** Get C_BankAccount_ID_4.
		@return C_BankAccount_ID_4	  */
	public int getC_BankAccount_ID_4 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BankAccount_ID_4);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Tolerance.
		@param Tolerance Tolerance	  */
	public void setTolerance (BigDecimal Tolerance)
	{
		set_Value (COLUMNNAME_Tolerance, Tolerance);
	}

	/** Get Tolerance.
		@return Tolerance	  */
	public BigDecimal getTolerance () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Tolerance);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set ToleranceAmount.
		@param ToleranceAmount ToleranceAmount	  */
	public void setToleranceAmount (BigDecimal ToleranceAmount)
	{
		set_Value (COLUMNNAME_ToleranceAmount, ToleranceAmount);
	}

	/** Get ToleranceAmount.
		@return ToleranceAmount	  */
	public BigDecimal getToleranceAmount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ToleranceAmount);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set UY_CashConfig.
		@param UY_CashConfig_ID UY_CashConfig	  */
	public void setUY_CashConfig_ID (int UY_CashConfig_ID)
	{
		if (UY_CashConfig_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_CashConfig_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_CashConfig_ID, Integer.valueOf(UY_CashConfig_ID));
	}

	/** Get UY_CashConfig.
		@return UY_CashConfig	  */
	public int getUY_CashConfig_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_CashConfig_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}