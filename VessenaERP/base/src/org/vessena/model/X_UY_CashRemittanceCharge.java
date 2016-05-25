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

/** Generated Model for UY_CashRemittanceCharge
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_CashRemittanceCharge extends PO implements I_UY_CashRemittanceCharge, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20160115L;

    /** Standard Constructor */
    public X_UY_CashRemittanceCharge (Properties ctx, int UY_CashRemittanceCharge_ID, String trxName)
    {
      super (ctx, UY_CashRemittanceCharge_ID, trxName);
      /** if (UY_CashRemittanceCharge_ID == 0)
        {
			setAmount (Env.ZERO);
			setC_Currency_ID (0);
			setUY_CashRemittanceCharge_ID (0);
			setUY_CashRemittance_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_CashRemittanceCharge (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_CashRemittanceCharge[")
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
	public void setAmount2 (int Amount2)
	{
		set_Value (COLUMNNAME_Amount2, Integer.valueOf(Amount2));
	}

	/** Get Amount2.
		@return Amount2	  */
	public int getAmount2 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Amount2);
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

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_Name)
			.getPO(getC_BPartner_ID(), get_TrxName());	}

	/** Set Business Partner .
		@param C_BPartner_ID 
		Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Business Partner .
		@return Identifies a Business Partner
	  */
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
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

	/** Set Due Date.
		@param DueDate 
		Date when the payment is due
	  */
	public void setDueDate (Timestamp DueDate)
	{
		set_Value (COLUMNNAME_DueDate, DueDate);
	}

	/** Get Due Date.
		@return Date when the payment is due
	  */
	public Timestamp getDueDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DueDate);
	}

	/** Set UY_CashRemittanceCharge.
		@param UY_CashRemittanceCharge_ID UY_CashRemittanceCharge	  */
	public void setUY_CashRemittanceCharge_ID (int UY_CashRemittanceCharge_ID)
	{
		if (UY_CashRemittanceCharge_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_CashRemittanceCharge_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_CashRemittanceCharge_ID, Integer.valueOf(UY_CashRemittanceCharge_ID));
	}

	/** Get UY_CashRemittanceCharge.
		@return UY_CashRemittanceCharge	  */
	public int getUY_CashRemittanceCharge_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_CashRemittanceCharge_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_CashRemittance getUY_CashRemittance() throws RuntimeException
    {
		return (I_UY_CashRemittance)MTable.get(getCtx(), I_UY_CashRemittance.Table_Name)
			.getPO(getUY_CashRemittance_ID(), get_TrxName());	}

	/** Set UY_CashRemittance.
		@param UY_CashRemittance_ID UY_CashRemittance	  */
	public void setUY_CashRemittance_ID (int UY_CashRemittance_ID)
	{
		if (UY_CashRemittance_ID < 1) 
			set_Value (COLUMNNAME_UY_CashRemittance_ID, null);
		else 
			set_Value (COLUMNNAME_UY_CashRemittance_ID, Integer.valueOf(UY_CashRemittance_ID));
	}

	/** Get UY_CashRemittance.
		@return UY_CashRemittance	  */
	public int getUY_CashRemittance_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_CashRemittance_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_MediosPago getUY_MediosPago() throws RuntimeException
    {
		return (I_UY_MediosPago)MTable.get(getCtx(), I_UY_MediosPago.Table_Name)
			.getPO(getUY_MediosPago_ID(), get_TrxName());	}

	/** Set Medios de Pago.
		@param UY_MediosPago_ID Medios de Pago	  */
	public void setUY_MediosPago_ID (int UY_MediosPago_ID)
	{
		if (UY_MediosPago_ID < 1) 
			set_Value (COLUMNNAME_UY_MediosPago_ID, null);
		else 
			set_Value (COLUMNNAME_UY_MediosPago_ID, Integer.valueOf(UY_MediosPago_ID));
	}

	/** Get Medios de Pago.
		@return Medios de Pago	  */
	public int getUY_MediosPago_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_MediosPago_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}