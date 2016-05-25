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

/** Generated Model for UY_PayOrder
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_PayOrder extends PO implements I_UY_PayOrder, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20160405L;

    /** Standard Constructor */
    public X_UY_PayOrder (Properties ctx, int UY_PayOrder_ID, String trxName)
    {
      super (ctx, UY_PayOrder_ID, trxName);
      /** if (UY_PayOrder_ID == 0)
        {
			setC_BPartner_ID (0);
			setC_DocType_ID (0);
			setDateTrx (new Timestamp( System.currentTimeMillis() ));
			setDueDate (new Timestamp( System.currentTimeMillis() ));
			setPayAmt (Env.ZERO);
			setProcessed (false);
			setSubtotal (Env.ZERO);
			setUY_PayOrder_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_PayOrder (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_PayOrder[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set AlaOrden.
		@param AlaOrden AlaOrden	  */
	public void setAlaOrden (boolean AlaOrden)
	{
		set_Value (COLUMNNAME_AlaOrden, Boolean.valueOf(AlaOrden));
	}

	/** Get AlaOrden.
		@return AlaOrden	  */
	public boolean isAlaOrden () 
	{
		Object oo = get_Value(COLUMNNAME_AlaOrden);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set AmtResguardo.
		@param AmtResguardo AmtResguardo	  */
	public void setAmtResguardo (BigDecimal AmtResguardo)
	{
		set_ValueNoCheck (COLUMNNAME_AmtResguardo, AmtResguardo);
	}

	/** Get AmtResguardo.
		@return AmtResguardo	  */
	public BigDecimal getAmtResguardo () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtResguardo);
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
			set_ValueNoCheck (COLUMNNAME_C_BankAccount_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BankAccount_ID, Integer.valueOf(C_BankAccount_ID));
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

	public org.compiere.model.I_C_Payment getC_Payment() throws RuntimeException
    {
		return (org.compiere.model.I_C_Payment)MTable.get(getCtx(), org.compiere.model.I_C_Payment.Table_Name)
			.getPO(getC_Payment_ID(), get_TrxName());	}

	/** Set Payment.
		@param C_Payment_ID 
		Payment identifier
	  */
	public void setC_Payment_ID (int C_Payment_ID)
	{
		if (C_Payment_ID < 1) 
			set_Value (COLUMNNAME_C_Payment_ID, null);
		else 
			set_Value (COLUMNNAME_C_Payment_ID, Integer.valueOf(C_Payment_ID));
	}

	/** Get Payment.
		@return Payment identifier
	  */
	public int getC_Payment_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Payment_ID);
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

	/** Set Discount %.
		@param Discount 
		Discount in percent
	  */
	public void setDiscount (BigDecimal Discount)
	{
		set_Value (COLUMNNAME_Discount, Discount);
	}

	/** Get Discount %.
		@return Discount in percent
	  */
	public BigDecimal getDiscount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Discount);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Discount 2 %.
		@param Discount2 
		Discount in percent
	  */
	public void setDiscount2 (BigDecimal Discount2)
	{
		set_Value (COLUMNNAME_Discount2, Discount2);
	}

	/** Get Discount 2 %.
		@return Discount in percent
	  */
	public BigDecimal getDiscount2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Discount2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Discount 3 %.
		@param Discount3 
		Discount in percent
	  */
	public void setDiscount3 (BigDecimal Discount3)
	{
		set_Value (COLUMNNAME_Discount3, Discount3);
	}

	/** Get Discount 3 %.
		@return Discount in percent
	  */
	public BigDecimal getDiscount3 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Discount3);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Discount4.
		@param Discount4 Discount4	  */
	public void setDiscount4 (BigDecimal Discount4)
	{
		set_Value (COLUMNNAME_Discount4, Discount4);
	}

	/** Get Discount4.
		@return Discount4	  */
	public BigDecimal getDiscount4 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Discount4);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Discount Amount.
		@param DiscountAmt 
		Calculated amount of discount
	  */
	public void setDiscountAmt (BigDecimal DiscountAmt)
	{
		set_Value (COLUMNNAME_DiscountAmt, DiscountAmt);
	}

	/** Get Discount Amount.
		@return Calculated amount of discount
	  */
	public BigDecimal getDiscountAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DiscountAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** DocAction AD_Reference_ID=135 */
	public static final int DOCACTION_AD_Reference_ID=135;
	/** Complete = CO */
	public static final String DOCACTION_Complete = "CO";
	/** Approve = AP */
	public static final String DOCACTION_Approve = "AP";
	/** Reject = RJ */
	public static final String DOCACTION_Reject = "RJ";
	/** Post = PO */
	public static final String DOCACTION_Post = "PO";
	/** Void = VO */
	public static final String DOCACTION_Void = "VO";
	/** Close = CL */
	public static final String DOCACTION_Close = "CL";
	/** Reverse - Correct = RC */
	public static final String DOCACTION_Reverse_Correct = "RC";
	/** Reverse - Accrual = RA */
	public static final String DOCACTION_Reverse_Accrual = "RA";
	/** Invalidate = IN */
	public static final String DOCACTION_Invalidate = "IN";
	/** Re-activate = RE */
	public static final String DOCACTION_Re_Activate = "RE";
	/** <None> = -- */
	public static final String DOCACTION_None = "--";
	/** Prepare = PR */
	public static final String DOCACTION_Prepare = "PR";
	/** Unlock = XL */
	public static final String DOCACTION_Unlock = "XL";
	/** Wait Complete = WC */
	public static final String DOCACTION_WaitComplete = "WC";
	/** Request = RQ */
	public static final String DOCACTION_Request = "RQ";
	/** Asign = AS */
	public static final String DOCACTION_Asign = "AS";
	/** Pick = PK */
	public static final String DOCACTION_Pick = "PK";
	/** Recive = RV */
	public static final String DOCACTION_Recive = "RV";
	/** Apply = AY */
	public static final String DOCACTION_Apply = "AY";
	/** Set Document Action.
		@param DocAction 
		The targeted status of the document
	  */
	public void setDocAction (String DocAction)
	{

		set_Value (COLUMNNAME_DocAction, DocAction);
	}

	/** Get Document Action.
		@return The targeted status of the document
	  */
	public String getDocAction () 
	{
		return (String)get_Value(COLUMNNAME_DocAction);
	}

	/** DocStatus AD_Reference_ID=131 */
	public static final int DOCSTATUS_AD_Reference_ID=131;
	/** Drafted = DR */
	public static final String DOCSTATUS_Drafted = "DR";
	/** Completed = CO */
	public static final String DOCSTATUS_Completed = "CO";
	/** Approved = AP */
	public static final String DOCSTATUS_Approved = "AP";
	/** Not Approved = NA */
	public static final String DOCSTATUS_NotApproved = "NA";
	/** Voided = VO */
	public static final String DOCSTATUS_Voided = "VO";
	/** Invalid = IN */
	public static final String DOCSTATUS_Invalid = "IN";
	/** Reversed = RE */
	public static final String DOCSTATUS_Reversed = "RE";
	/** Closed = CL */
	public static final String DOCSTATUS_Closed = "CL";
	/** Unknown = ?? */
	public static final String DOCSTATUS_Unknown = "??";
	/** In Progress = IP */
	public static final String DOCSTATUS_InProgress = "IP";
	/** Waiting Payment = WP */
	public static final String DOCSTATUS_WaitingPayment = "WP";
	/** Waiting Confirmation = WC */
	public static final String DOCSTATUS_WaitingConfirmation = "WC";
	/** Asigned = AS */
	public static final String DOCSTATUS_Asigned = "AS";
	/** Requested = RQ */
	public static final String DOCSTATUS_Requested = "RQ";
	/** Recived = RV */
	public static final String DOCSTATUS_Recived = "RV";
	/** Picking = PK */
	public static final String DOCSTATUS_Picking = "PK";
	/** Applied = AY */
	public static final String DOCSTATUS_Applied = "AY";
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
		set_ValueNoCheck (COLUMNNAME_DueDate, DueDate);
	}

	/** Get Due Date.
		@return Date when the payment is due
	  */
	public Timestamp getDueDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DueDate);
	}

	/** Set Payment amount.
		@param PayAmt 
		Amount being paid
	  */
	public void setPayAmt (BigDecimal PayAmt)
	{
		set_Value (COLUMNNAME_PayAmt, PayAmt);
	}

	/** Get Payment amount.
		@return Amount being paid
	  */
	public BigDecimal getPayAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PayAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set Process Now.
		@param Processing Process Now	  */
	public void setProcessing (boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Boolean.valueOf(Processing));
	}

	/** Get Process Now.
		@return Process Now	  */
	public boolean isProcessing () 
	{
		Object oo = get_Value(COLUMNNAME_Processing);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Subtotal.
		@param Subtotal Subtotal	  */
	public void setSubtotal (BigDecimal Subtotal)
	{
		set_Value (COLUMNNAME_Subtotal, Subtotal);
	}

	/** Get Subtotal.
		@return Subtotal	  */
	public BigDecimal getSubtotal () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Subtotal);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set TieneResguardo.
		@param TieneResguardo TieneResguardo	  */
	public void setTieneResguardo (boolean TieneResguardo)
	{
		set_ValueNoCheck (COLUMNNAME_TieneResguardo, Boolean.valueOf(TieneResguardo));
	}

	/** Get TieneResguardo.
		@return TieneResguardo	  */
	public boolean isTieneResguardo () 
	{
		Object oo = get_Value(COLUMNNAME_TieneResguardo);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set TotalDiscounts.
		@param TotalDiscounts TotalDiscounts	  */
	public void setTotalDiscounts (BigDecimal TotalDiscounts)
	{
		set_Value (COLUMNNAME_TotalDiscounts, TotalDiscounts);
	}

	/** Get TotalDiscounts.
		@return TotalDiscounts	  */
	public BigDecimal getTotalDiscounts () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalDiscounts);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public I_UY_PayEmit getUY_PayEmit() throws RuntimeException
    {
		return (I_UY_PayEmit)MTable.get(getCtx(), I_UY_PayEmit.Table_Name)
			.getPO(getUY_PayEmit_ID(), get_TrxName());	}

	/** Set UY_PayEmit.
		@param UY_PayEmit_ID UY_PayEmit	  */
	public void setUY_PayEmit_ID (int UY_PayEmit_ID)
	{
		if (UY_PayEmit_ID < 1) 
			set_Value (COLUMNNAME_UY_PayEmit_ID, null);
		else 
			set_Value (COLUMNNAME_UY_PayEmit_ID, Integer.valueOf(UY_PayEmit_ID));
	}

	/** Get UY_PayEmit.
		@return UY_PayEmit	  */
	public int getUY_PayEmit_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_PayEmit_ID);
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

	public I_UY_PayOrderGen getUY_PayOrderGen() throws RuntimeException
    {
		return (I_UY_PayOrderGen)MTable.get(getCtx(), I_UY_PayOrderGen.Table_Name)
			.getPO(getUY_PayOrderGen_ID(), get_TrxName());	}

	/** Set UY_PayOrderGen.
		@param UY_PayOrderGen_ID UY_PayOrderGen	  */
	public void setUY_PayOrderGen_ID (int UY_PayOrderGen_ID)
	{
		if (UY_PayOrderGen_ID < 1) 
			set_Value (COLUMNNAME_UY_PayOrderGen_ID, null);
		else 
			set_Value (COLUMNNAME_UY_PayOrderGen_ID, Integer.valueOf(UY_PayOrderGen_ID));
	}

	/** Get UY_PayOrderGen.
		@return UY_PayOrderGen	  */
	public int getUY_PayOrderGen_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_PayOrderGen_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_PayOrder.
		@param UY_PayOrder_ID UY_PayOrder	  */
	public void setUY_PayOrder_ID (int UY_PayOrder_ID)
	{
		if (UY_PayOrder_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_PayOrder_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_PayOrder_ID, Integer.valueOf(UY_PayOrder_ID));
	}

	/** Get UY_PayOrder.
		@return UY_PayOrder	  */
	public int getUY_PayOrder_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_PayOrder_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_Resguardo getUY_Resguardo() throws RuntimeException
    {
		return (I_UY_Resguardo)MTable.get(getCtx(), I_UY_Resguardo.Table_Name)
			.getPO(getUY_Resguardo_ID(), get_TrxName());	}

	/** Set UY_Resguardo.
		@param UY_Resguardo_ID UY_Resguardo	  */
	public void setUY_Resguardo_ID (int UY_Resguardo_ID)
	{
		if (UY_Resguardo_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_Resguardo_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_Resguardo_ID, Integer.valueOf(UY_Resguardo_ID));
	}

	/** Get UY_Resguardo.
		@return UY_Resguardo	  */
	public int getUY_Resguardo_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_Resguardo_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}