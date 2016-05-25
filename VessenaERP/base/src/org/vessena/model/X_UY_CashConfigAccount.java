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

/** Generated Model for UY_CashConfigAccount
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_CashConfigAccount extends PO implements I_UY_CashConfigAccount, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150826L;

    /** Standard Constructor */
    public X_UY_CashConfigAccount (Properties ctx, int UY_CashConfigAccount_ID, String trxName)
    {
      super (ctx, UY_CashConfigAccount_ID, trxName);
      /** if (UY_CashConfigAccount_ID == 0)
        {
			setC_BankAccount_ID (0);
			setUY_CashConfigAccount_ID (0);
			setUY_CashConfig_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_CashConfigAccount (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_CashConfigAccount[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	/** Set UY_CashConfigAccount.
		@param UY_CashConfigAccount_ID UY_CashConfigAccount	  */
	public void setUY_CashConfigAccount_ID (int UY_CashConfigAccount_ID)
	{
		if (UY_CashConfigAccount_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_CashConfigAccount_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_CashConfigAccount_ID, Integer.valueOf(UY_CashConfigAccount_ID));
	}

	/** Get UY_CashConfigAccount.
		@return UY_CashConfigAccount	  */
	public int getUY_CashConfigAccount_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_CashConfigAccount_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_CashConfig getUY_CashConfig() throws RuntimeException
    {
		return (I_UY_CashConfig)MTable.get(getCtx(), I_UY_CashConfig.Table_Name)
			.getPO(getUY_CashConfig_ID(), get_TrxName());	}

	/** Set UY_CashConfig.
		@param UY_CashConfig_ID UY_CashConfig	  */
	public void setUY_CashConfig_ID (int UY_CashConfig_ID)
	{
		if (UY_CashConfig_ID < 1) 
			set_Value (COLUMNNAME_UY_CashConfig_ID, null);
		else 
			set_Value (COLUMNNAME_UY_CashConfig_ID, Integer.valueOf(UY_CashConfig_ID));
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