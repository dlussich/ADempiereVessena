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

/** Generated Model for UY_ConciliaBank
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS - $Id$ */
public class X_UY_ConciliaBank extends PO implements I_UY_ConciliaBank, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20131104L;

    /** Standard Constructor */
    public X_UY_ConciliaBank (Properties ctx, int UY_ConciliaBank_ID, String trxName)
    {
      super (ctx, UY_ConciliaBank_ID, trxName);
      /** if (UY_ConciliaBank_ID == 0)
        {
			setC_Bank_ID (0);
			setC_BankAccount_ID (0);
			setC_Currency_ID (0);
			setDateTrx (new Timestamp( System.currentTimeMillis() ));
			setDescription (null);
			setDocumentNo (null);
			setIsError (false);
// N
			setIsSameGrid (false);
			setUY_BankExtract_ID (0);
			setUY_ConciliaBank_ID (0);
			setUY_Conciliation_ID (0);
			setUY_LoadExtract_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_ConciliaBank (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_ConciliaBank[")
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

	public org.compiere.model.I_C_Bank getC_Bank() throws RuntimeException
    {
		return (org.compiere.model.I_C_Bank)MTable.get(getCtx(), org.compiere.model.I_C_Bank.Table_Name)
			.getPO(getC_Bank_ID(), get_TrxName());	}

	/** Set Bank.
		@param C_Bank_ID 
		Bank
	  */
	public void setC_Bank_ID (int C_Bank_ID)
	{
		if (C_Bank_ID < 1) 
			set_Value (COLUMNNAME_C_Bank_ID, null);
		else 
			set_Value (COLUMNNAME_C_Bank_ID, Integer.valueOf(C_Bank_ID));
	}

	/** Get Bank.
		@return Bank
	  */
	public int getC_Bank_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Bank_ID);
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

	public org.compiere.model.I_C_DocType getC_DocType() throws RuntimeException
    {
		return (org.compiere.model.I_C_DocType)MTable.get(getCtx(), org.compiere.model.I_C_DocType.Table_Name)
			.getPO(getC_DocType_ID(), get_TrxName());	}

	/** Set Document Type.
		@param C_DocType_ID 
		Document type or rules
	  */
	public void setC_DocType_ID (int C_DocType_ID)
	{
		if (C_DocType_ID < 0) 
			set_Value (COLUMNNAME_C_DocType_ID, null);
		else 
			set_Value (COLUMNNAME_C_DocType_ID, Integer.valueOf(C_DocType_ID));
	}

	/** Get Document Type.
		@return Document type or rules
	  */
	public int getC_DocType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DocType_ID);
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

	/** Set Valuation Date.
		@param DateValue 
		Date of valuation
	  */
	public void setDateValue (Timestamp DateValue)
	{
		set_Value (COLUMNNAME_DateValue, DateValue);
	}

	/** Get Valuation Date.
		@return Date of valuation
	  */
	public Timestamp getDateValue () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateValue);
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

	/** Set Document No.
		@param DocumentNo 
		Document sequence number of the document
	  */
	public void setDocumentNo (String DocumentNo)
	{
		set_Value (COLUMNNAME_DocumentNo, DocumentNo);
	}

	/** Get Document No.
		@return Document sequence number of the document
	  */
	public String getDocumentNo () 
	{
		return (String)get_Value(COLUMNNAME_DocumentNo);
	}

	/** Set Error.
		@param IsError 
		An Error occurred in the execution
	  */
	public void setIsError (boolean IsError)
	{
		set_Value (COLUMNNAME_IsError, Boolean.valueOf(IsError));
	}

	/** Get Error.
		@return An Error occurred in the execution
	  */
	public boolean isError () 
	{
		Object oo = get_Value(COLUMNNAME_IsError);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Manual.
		@param IsManual 
		This is a manual process
	  */
	public void setIsManual (boolean IsManual)
	{
		set_Value (COLUMNNAME_IsManual, Boolean.valueOf(IsManual));
	}

	/** Get Manual.
		@return This is a manual process
	  */
	public boolean isManual () 
	{
		Object oo = get_Value(COLUMNNAME_IsManual);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsSameGrid.
		@param IsSameGrid IsSameGrid	  */
	public void setIsSameGrid (boolean IsSameGrid)
	{
		set_Value (COLUMNNAME_IsSameGrid, Boolean.valueOf(IsSameGrid));
	}

	/** Get IsSameGrid.
		@return IsSameGrid	  */
	public boolean isSameGrid () 
	{
		Object oo = get_Value(COLUMNNAME_IsSameGrid);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set sucursal.
		@param sucursal sucursal	  */
	public void setsucursal (String sucursal)
	{
		set_Value (COLUMNNAME_sucursal, sucursal);
	}

	/** Get sucursal.
		@return sucursal	  */
	public String getsucursal () 
	{
		return (String)get_Value(COLUMNNAME_sucursal);
	}

	/** Set Total Amount.
		@param TotalAmt 
		Total Amount
	  */
	public void setTotalAmt (BigDecimal TotalAmt)
	{
		set_Value (COLUMNNAME_TotalAmt, TotalAmt);
	}

	/** Get Total Amount.
		@return Total Amount
	  */
	public BigDecimal getTotalAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public I_UY_BankExtract getUY_BankExtract() throws RuntimeException
    {
		return (I_UY_BankExtract)MTable.get(getCtx(), I_UY_BankExtract.Table_Name)
			.getPO(getUY_BankExtract_ID(), get_TrxName());	}

	/** Set UY_BankExtract.
		@param UY_BankExtract_ID UY_BankExtract	  */
	public void setUY_BankExtract_ID (int UY_BankExtract_ID)
	{
		if (UY_BankExtract_ID < 1) 
			set_Value (COLUMNNAME_UY_BankExtract_ID, null);
		else 
			set_Value (COLUMNNAME_UY_BankExtract_ID, Integer.valueOf(UY_BankExtract_ID));
	}

	/** Get UY_BankExtract.
		@return UY_BankExtract	  */
	public int getUY_BankExtract_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_BankExtract_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_ConciliaBank.
		@param UY_ConciliaBank_ID UY_ConciliaBank	  */
	public void setUY_ConciliaBank_ID (int UY_ConciliaBank_ID)
	{
		if (UY_ConciliaBank_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_ConciliaBank_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_ConciliaBank_ID, Integer.valueOf(UY_ConciliaBank_ID));
	}

	/** Get UY_ConciliaBank.
		@return UY_ConciliaBank	  */
	public int getUY_ConciliaBank_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_ConciliaBank_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_Conciliated getUY_Conciliated() throws RuntimeException
    {
		return (I_UY_Conciliated)MTable.get(getCtx(), I_UY_Conciliated.Table_Name)
			.getPO(getUY_Conciliated_ID(), get_TrxName());	}

	/** Set UY_Conciliated.
		@param UY_Conciliated_ID UY_Conciliated	  */
	public void setUY_Conciliated_ID (int UY_Conciliated_ID)
	{
		if (UY_Conciliated_ID < 1) 
			set_Value (COLUMNNAME_UY_Conciliated_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Conciliated_ID, Integer.valueOf(UY_Conciliated_ID));
	}

	/** Get UY_Conciliated.
		@return UY_Conciliated	  */
	public int getUY_Conciliated_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Conciliated_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_Conciliation getUY_Conciliation() throws RuntimeException
    {
		return (I_UY_Conciliation)MTable.get(getCtx(), I_UY_Conciliation.Table_Name)
			.getPO(getUY_Conciliation_ID(), get_TrxName());	}

	/** Set UY_Conciliation.
		@param UY_Conciliation_ID UY_Conciliation	  */
	public void setUY_Conciliation_ID (int UY_Conciliation_ID)
	{
		if (UY_Conciliation_ID < 1) 
			set_Value (COLUMNNAME_UY_Conciliation_ID, null);
		else 
			set_Value (COLUMNNAME_UY_Conciliation_ID, Integer.valueOf(UY_Conciliation_ID));
	}

	/** Get UY_Conciliation.
		@return UY_Conciliation	  */
	public int getUY_Conciliation_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Conciliation_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_LoadExtract getUY_LoadExtract() throws RuntimeException
    {
		return (I_UY_LoadExtract)MTable.get(getCtx(), I_UY_LoadExtract.Table_Name)
			.getPO(getUY_LoadExtract_ID(), get_TrxName());	}

	/** Set UY_LoadExtract.
		@param UY_LoadExtract_ID UY_LoadExtract	  */
	public void setUY_LoadExtract_ID (int UY_LoadExtract_ID)
	{
		if (UY_LoadExtract_ID < 1) 
			set_Value (COLUMNNAME_UY_LoadExtract_ID, null);
		else 
			set_Value (COLUMNNAME_UY_LoadExtract_ID, Integer.valueOf(UY_LoadExtract_ID));
	}

	/** Get UY_LoadExtract.
		@return UY_LoadExtract	  */
	public int getUY_LoadExtract_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_LoadExtract_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}