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

/** Generated Model for UY_ConciliaSystem
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS - $Id$ */
public class X_UY_ConciliaSystem extends PO implements I_UY_ConciliaSystem, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20131104L;

    /** Standard Constructor */
    public X_UY_ConciliaSystem (Properties ctx, int UY_ConciliaSystem_ID, String trxName)
    {
      super (ctx, UY_ConciliaSystem_ID, trxName);
      /** if (UY_ConciliaSystem_ID == 0)
        {
			setC_Bank_ID (0);
			setC_BankAccount_ID (0);
			setC_Currency_ID (0);
			setDateTrx (new Timestamp( System.currentTimeMillis() ));
			setDocumentNo (null);
			setIsError (false);
// N
			setIsSameGrid (false);
			setUY_ConciliaSystem_ID (0);
			setUY_Conciliation_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_ConciliaSystem (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_ConciliaSystem[")
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

	/** Set Accounting Fact.
		@param Fact_Acct_ID Accounting Fact	  */
	public void setFact_Acct_ID (int Fact_Acct_ID)
	{
		if (Fact_Acct_ID < 1) 
			set_Value (COLUMNNAME_Fact_Acct_ID, null);
		else 
			set_Value (COLUMNNAME_Fact_Acct_ID, Integer.valueOf(Fact_Acct_ID));
	}

	/** Get Accounting Fact.
		@return Accounting Fact	  */
	public int getFact_Acct_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Fact_Acct_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Record ID.
		@param Record_ID 
		Direct internal record ID
	  */
	public void setRecord_ID (int Record_ID)
	{
		if (Record_ID < 0) 
			set_Value (COLUMNNAME_Record_ID, null);
		else 
			set_Value (COLUMNNAME_Record_ID, Integer.valueOf(Record_ID));
	}

	/** Get Record ID.
		@return Direct internal record ID
	  */
	public int getRecord_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Record_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set UY_ConciliaSystem.
		@param UY_ConciliaSystem_ID UY_ConciliaSystem	  */
	public void setUY_ConciliaSystem_ID (int UY_ConciliaSystem_ID)
	{
		if (UY_ConciliaSystem_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_ConciliaSystem_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_ConciliaSystem_ID, Integer.valueOf(UY_ConciliaSystem_ID));
	}

	/** Get UY_ConciliaSystem.
		@return UY_ConciliaSystem	  */
	public int getUY_ConciliaSystem_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_ConciliaSystem_ID);
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
}