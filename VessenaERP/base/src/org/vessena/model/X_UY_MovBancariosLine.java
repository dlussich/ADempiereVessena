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

/** Generated Model for UY_MovBancariosLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_MovBancariosLine extends PO implements I_UY_MovBancariosLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20121018L;

    /** Standard Constructor */
    public X_UY_MovBancariosLine (Properties ctx, int UY_MovBancariosLine_ID, String trxName)
    {
      super (ctx, UY_MovBancariosLine_ID, trxName);
      /** if (UY_MovBancariosLine_ID == 0)
        {
			setC_Currency_ID (0);
			setDateAcct (new Timestamp( System.currentTimeMillis() ));
			setDateTrx (new Timestamp( System.currentTimeMillis() ));
			setDocumentNo (null);
			setIsManual (false);
			setIsReversal (false);
			setProcessed (false);
			setuy_capitalamt (Env.ZERO);
			setuy_interesesamt (Env.ZERO);
			setUY_MovBancariosHdr_ID (0);
			setUY_MovBancariosLine_ID (0);
			setuy_totalamt (Env.ZERO);
        } */
    }

    /** Load Constructor */
    public X_UY_MovBancariosLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_MovBancariosLine[")
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

	public org.compiere.model.I_C_Charge getC_Charge() throws RuntimeException
    {
		return (org.compiere.model.I_C_Charge)MTable.get(getCtx(), org.compiere.model.I_C_Charge.Table_Name)
			.getPO(getC_Charge_ID(), get_TrxName());	}

	/** Set Charge.
		@param C_Charge_ID 
		Additional document charges
	  */
	public void setC_Charge_ID (int C_Charge_ID)
	{
		if (C_Charge_ID < 1) 
			set_Value (COLUMNNAME_C_Charge_ID, null);
		else 
			set_Value (COLUMNNAME_C_Charge_ID, Integer.valueOf(C_Charge_ID));
	}

	/** Get Charge.
		@return Additional document charges
	  */
	public int getC_Charge_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Charge_ID);
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

	/** Set Check No.
		@param CheckNo 
		Check Number
	  */
	public void setCheckNo (String CheckNo)
	{
		set_Value (COLUMNNAME_CheckNo, CheckNo);
	}

	/** Get Check No.
		@return Check Number
	  */
	public String getCheckNo () 
	{
		return (String)get_Value(COLUMNNAME_CheckNo);
	}

	/** Set Account Date.
		@param DateAcct 
		Accounting Date
	  */
	public void setDateAcct (Timestamp DateAcct)
	{
		set_Value (COLUMNNAME_DateAcct, DateAcct);
	}

	/** Get Account Date.
		@return Accounting Date
	  */
	public Timestamp getDateAcct () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateAcct);
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

	/** Set Document Status.
		@param DocStatus 
		The current status of the document
	  */
	public void setDocStatus (String DocStatus)
	{
		set_Value (COLUMNNAME_DocStatus, DocStatus);
	}

	/** Get Document Status.
		@return The current status of the document
	  */
	public String getDocStatus () 
	{
		return (String)get_Value(COLUMNNAME_DocStatus);
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

	/** Set estado.
		@param estado estado	  */
	public void setestado (String estado)
	{
		set_Value (COLUMNNAME_estado, estado);
	}

	/** Get estado.
		@return estado	  */
	public String getestado () 
	{
		return (String)get_Value(COLUMNNAME_estado);
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

	/** Set Reversal.
		@param IsReversal 
		This is a reversing transaction
	  */
	public void setIsReversal (boolean IsReversal)
	{
		set_Value (COLUMNNAME_IsReversal, Boolean.valueOf(IsReversal));
	}

	/** Get Reversal.
		@return This is a reversing transaction
	  */
	public boolean isReversal () 
	{
		Object oo = get_Value(COLUMNNAME_IsReversal);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Processed.
		@param Processed 
		The document has been processed
	  */
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Processed.
		@return The document has been processed
	  */
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** TenderType AD_Reference_ID=214 */
	public static final int TENDERTYPE_AD_Reference_ID=214;
	/** Credit Card = C */
	public static final String TENDERTYPE_CreditCard = "C";
	/** Check = K */
	public static final String TENDERTYPE_Check = "K";
	/** Direct Deposit = A */
	public static final String TENDERTYPE_DirectDeposit = "A";
	/** Direct Debit = D */
	public static final String TENDERTYPE_DirectDebit = "D";
	/** Account = T */
	public static final String TENDERTYPE_Account = "T";
	/** Cash = X */
	public static final String TENDERTYPE_Cash = "X";
	/** Retencion OTT = O */
	public static final String TENDERTYPE_RetencionOTT = "O";
	/** Canje = J */
	public static final String TENDERTYPE_Canje = "J";
	/** Conforme = F */
	public static final String TENDERTYPE_Conforme = "F";
	/** Tickets Alim. = L */
	public static final String TENDERTYPE_TicketsAlim = "L";
	/** Fideicomiso = I */
	public static final String TENDERTYPE_Fideicomiso = "I";
	/** Set Tender type.
		@param TenderType 
		Method of Payment
	  */
	public void setTenderType (String TenderType)
	{

		set_Value (COLUMNNAME_TenderType, TenderType);
	}

	/** Get Tender type.
		@return Method of Payment
	  */
	public String getTenderType () 
	{
		return (String)get_Value(COLUMNNAME_TenderType);
	}

	/** Set uy_capitalamt.
		@param uy_capitalamt uy_capitalamt	  */
	public void setuy_capitalamt (BigDecimal uy_capitalamt)
	{
		set_Value (COLUMNNAME_uy_capitalamt, uy_capitalamt);
	}

	/** Get uy_capitalamt.
		@return uy_capitalamt	  */
	public BigDecimal getuy_capitalamt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_uy_capitalamt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set uy_interesesamt.
		@param uy_interesesamt uy_interesesamt	  */
	public void setuy_interesesamt (BigDecimal uy_interesesamt)
	{
		set_Value (COLUMNNAME_uy_interesesamt, uy_interesesamt);
	}

	/** Get uy_interesesamt.
		@return uy_interesesamt	  */
	public BigDecimal getuy_interesesamt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_uy_interesesamt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	public I_UY_MovBancariosHdr getUY_MovBancariosHdr() throws RuntimeException
    {
		return (I_UY_MovBancariosHdr)MTable.get(getCtx(), I_UY_MovBancariosHdr.Table_Name)
			.getPO(getUY_MovBancariosHdr_ID(), get_TrxName());	}

	/** Set UY_MovBancariosHdr_ID.
		@param UY_MovBancariosHdr_ID UY_MovBancariosHdr_ID	  */
	public void setUY_MovBancariosHdr_ID (int UY_MovBancariosHdr_ID)
	{
		if (UY_MovBancariosHdr_ID < 1) 
			set_Value (COLUMNNAME_UY_MovBancariosHdr_ID, null);
		else 
			set_Value (COLUMNNAME_UY_MovBancariosHdr_ID, Integer.valueOf(UY_MovBancariosHdr_ID));
	}

	/** Get UY_MovBancariosHdr_ID.
		@return UY_MovBancariosHdr_ID	  */
	public int getUY_MovBancariosHdr_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_MovBancariosHdr_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_MovBancariosLine_ID.
		@param UY_MovBancariosLine_ID UY_MovBancariosLine_ID	  */
	public void setUY_MovBancariosLine_ID (int UY_MovBancariosLine_ID)
	{
		if (UY_MovBancariosLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_MovBancariosLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_MovBancariosLine_ID, Integer.valueOf(UY_MovBancariosLine_ID));
	}

	/** Get UY_MovBancariosLine_ID.
		@return UY_MovBancariosLine_ID	  */
	public int getUY_MovBancariosLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_MovBancariosLine_ID);
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

	/** Set uy_totalamt.
		@param uy_totalamt uy_totalamt	  */
	public void setuy_totalamt (BigDecimal uy_totalamt)
	{
		set_Value (COLUMNNAME_uy_totalamt, uy_totalamt);
	}

	/** Get uy_totalamt.
		@return uy_totalamt	  */
	public BigDecimal getuy_totalamt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_uy_totalamt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}