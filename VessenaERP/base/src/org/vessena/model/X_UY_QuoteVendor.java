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

/** Generated Model for UY_QuoteVendor
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC - $Id$ */
public class X_UY_QuoteVendor extends PO implements I_UY_QuoteVendor, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140216L;

    /** Standard Constructor */
    public X_UY_QuoteVendor (Properties ctx, int UY_QuoteVendor_ID, String trxName)
    {
      super (ctx, UY_QuoteVendor_ID, trxName);
      /** if (UY_QuoteVendor_ID == 0)
        {
			setC_BPartner_ID (0);
			setC_Currency_ID (0);
// 142
			setDateTrx (new Timestamp( System.currentTimeMillis() ));
			setDocAction (null);
// RQ
			setDocStatus (null);
// DR
			setDocumentNo (null);
			setEffectiveDate (new Timestamp( System.currentTimeMillis() ));
			setpaymentruletype (null);
// CR
			setProcessed (false);
// N
			setUY_QuoteVendor_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UY_QuoteVendor (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UY_QuoteVendor[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_AD_User getAD_User() throws RuntimeException
    {
		return (org.compiere.model.I_AD_User)MTable.get(getCtx(), org.compiere.model.I_AD_User.Table_Name)
			.getPO(getAD_User_ID(), get_TrxName());	}

	/** Set User/Contact.
		@param AD_User_ID 
		User within the system - Internal or Business Partner Contact
	  */
	public void setAD_User_ID (int AD_User_ID)
	{
		if (AD_User_ID < 1) 
			set_Value (COLUMNNAME_AD_User_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_ID, Integer.valueOf(AD_User_ID));
	}

	/** Get User/Contact.
		@return User within the system - Internal or Business Partner Contact
	  */
	public int getAD_User_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set ApprovalDate.
		@param ApprovalDate ApprovalDate	  */
	public void setApprovalDate (Timestamp ApprovalDate)
	{
		set_ValueNoCheck (COLUMNNAME_ApprovalDate, ApprovalDate);
	}

	/** Get ApprovalDate.
		@return ApprovalDate	  */
	public Timestamp getApprovalDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_ApprovalDate);
	}

	/** Set ApprovalDescription.
		@param ApprovalDescription ApprovalDescription	  */
	public void setApprovalDescription (String ApprovalDescription)
	{
		set_Value (COLUMNNAME_ApprovalDescription, ApprovalDescription);
	}

	/** Get ApprovalDescription.
		@return ApprovalDescription	  */
	public String getApprovalDescription () 
	{
		return (String)get_Value(COLUMNNAME_ApprovalDescription);
	}

	/** ApprovalStatus AD_Reference_ID=1000231 */
	public static final int APPROVALSTATUS_AD_Reference_ID=1000231;
	/** Pendiente Aprobacion Nivel 1 = PENDING1 */
	public static final String APPROVALSTATUS_PendienteAprobacionNivel1 = "PENDING1";
	/** Pendiente Aprobacion Nivel 2 = PENDING2 */
	public static final String APPROVALSTATUS_PendienteAprobacionNivel2 = "PENDING2";
	/** Aprobado Nivel 1 = APPROVED1 */
	public static final String APPROVALSTATUS_AprobadoNivel1 = "APPROVED1";
	/** Aprobado Nivel 2 = APPROVED2 */
	public static final String APPROVALSTATUS_AprobadoNivel2 = "APPROVED2";
	/** Rechazado Nivel 1 = REJECT1 */
	public static final String APPROVALSTATUS_RechazadoNivel1 = "REJECT1";
	/** Rechazado Nivel 2 = REJECT2 */
	public static final String APPROVALSTATUS_RechazadoNivel2 = "REJECT2";
	/** Aprobado por Compras = APPROVEDPO */
	public static final String APPROVALSTATUS_AprobadoPorCompras = "APPROVEDPO";
	/** Pendiente Aprobacion Compras = PENDINGPO */
	public static final String APPROVALSTATUS_PendienteAprobacionCompras = "PENDINGPO";
	/** Rechazado por Compras = REJECTPO */
	public static final String APPROVALSTATUS_RechazadoPorCompras = "REJECTPO";
	/** Nueva = NUEVA */
	public static final String APPROVALSTATUS_Nueva = "NUEVA";
	/** Set ApprovalStatus.
		@param ApprovalStatus ApprovalStatus	  */
	public void setApprovalStatus (String ApprovalStatus)
	{

		set_ValueNoCheck (COLUMNNAME_ApprovalStatus, ApprovalStatus);
	}

	/** Get ApprovalStatus.
		@return ApprovalStatus	  */
	public String getApprovalStatus () 
	{
		return (String)get_Value(COLUMNNAME_ApprovalStatus);
	}

	/** Set ApprovalUser_ID.
		@param ApprovalUser_ID ApprovalUser_ID	  */
	public void setApprovalUser_ID (int ApprovalUser_ID)
	{
		if (ApprovalUser_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ApprovalUser_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ApprovalUser_ID, Integer.valueOf(ApprovalUser_ID));
	}

	/** Get ApprovalUser_ID.
		@return ApprovalUser_ID	  */
	public int getApprovalUser_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ApprovalUser_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** ApprovedType AD_Reference_ID=1000354 */
	public static final int APPROVEDTYPE_AD_Reference_ID=1000354;
	/** AUTORIZADO = AUTORIZADO */
	public static final String APPROVEDTYPE_AUTORIZADO = "AUTORIZADO";
	/** RECHAZADO = RECHAZADO */
	public static final String APPROVEDTYPE_RECHAZADO = "RECHAZADO";
	/** Set ApprovedType.
		@param ApprovedType ApprovedType	  */
	public void setApprovedType (String ApprovedType)
	{

		set_Value (COLUMNNAME_ApprovedType, ApprovedType);
	}

	/** Get ApprovedType.
		@return ApprovedType	  */
	public String getApprovedType () 
	{
		return (String)get_Value(COLUMNNAME_ApprovedType);
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
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
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
			set_ValueNoCheck (COLUMNNAME_C_Currency_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
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

	public org.compiere.model.I_C_PaymentTerm getC_PaymentTerm() throws RuntimeException
    {
		return (org.compiere.model.I_C_PaymentTerm)MTable.get(getCtx(), org.compiere.model.I_C_PaymentTerm.Table_Name)
			.getPO(getC_PaymentTerm_ID(), get_TrxName());	}

	/** Set Payment Term.
		@param C_PaymentTerm_ID 
		The terms of Payment (timing, discount)
	  */
	public void setC_PaymentTerm_ID (int C_PaymentTerm_ID)
	{
		if (C_PaymentTerm_ID < 1) 
			set_Value (COLUMNNAME_C_PaymentTerm_ID, null);
		else 
			set_Value (COLUMNNAME_C_PaymentTerm_ID, Integer.valueOf(C_PaymentTerm_ID));
	}

	/** Get Payment Term.
		@return The terms of Payment (timing, discount)
	  */
	public int getC_PaymentTerm_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_PaymentTerm_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Date Required.
		@param DateRequired 
		Date when required
	  */
	public void setDateRequired (Timestamp DateRequired)
	{
		set_Value (COLUMNNAME_DateRequired, DateRequired);
	}

	/** Get Date Required.
		@return Date when required
	  */
	public Timestamp getDateRequired () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateRequired);
	}

	/** Set Response Date.
		@param DateResponse 
		Date of the Response
	  */
	public void setDateResponse (Timestamp DateResponse)
	{
		set_ValueNoCheck (COLUMNNAME_DateResponse, DateResponse);
	}

	/** Get Response Date.
		@return Date of the Response
	  */
	public Timestamp getDateResponse () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateResponse);
	}

	/** Set Transaction Date.
		@param DateTrx 
		Transaction Date
	  */
	public void setDateTrx (Timestamp DateTrx)
	{
		set_ValueNoCheck (COLUMNNAME_DateTrx, DateTrx);
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
		set_ValueNoCheck (COLUMNNAME_DocumentNo, DocumentNo);
	}

	/** Get Document No.
		@return Document sequence number of the document
	  */
	public String getDocumentNo () 
	{
		return (String)get_Value(COLUMNNAME_DocumentNo);
	}

	/** Set EffectiveDate.
		@param EffectiveDate EffectiveDate	  */
	public void setEffectiveDate (Timestamp EffectiveDate)
	{
		set_ValueNoCheck (COLUMNNAME_EffectiveDate, EffectiveDate);
	}

	/** Get EffectiveDate.
		@return EffectiveDate	  */
	public Timestamp getEffectiveDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_EffectiveDate);
	}

	/** Set Approved.
		@param IsApproved 
		Indicates if this document requires approval
	  */
	public void setIsApproved (boolean IsApproved)
	{
		set_Value (COLUMNNAME_IsApproved, Boolean.valueOf(IsApproved));
	}

	/** Get Approved.
		@return Indicates if this document requires approval
	  */
	public boolean isApproved () 
	{
		Object oo = get_Value(COLUMNNAME_IsApproved);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsApproved1.
		@param IsApproved1 IsApproved1	  */
	public void setIsApproved1 (boolean IsApproved1)
	{
		set_Value (COLUMNNAME_IsApproved1, Boolean.valueOf(IsApproved1));
	}

	/** Get IsApproved1.
		@return IsApproved1	  */
	public boolean isApproved1 () 
	{
		Object oo = get_Value(COLUMNNAME_IsApproved1);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set NeedApprove1.
		@param NeedApprove1 NeedApprove1	  */
	public void setNeedApprove1 (boolean NeedApprove1)
	{
		set_Value (COLUMNNAME_NeedApprove1, Boolean.valueOf(NeedApprove1));
	}

	/** Get NeedApprove1.
		@return NeedApprove1	  */
	public boolean isNeedApprove1 () 
	{
		Object oo = get_Value(COLUMNNAME_NeedApprove1);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** paymentruletype AD_Reference_ID=1000194 */
	public static final int PAYMENTRULETYPE_AD_Reference_ID=1000194;
	/** Contado = CO */
	public static final String PAYMENTRULETYPE_Contado = "CO";
	/** Credito = CR */
	public static final String PAYMENTRULETYPE_Credito = "CR";
	/** Set paymentruletype.
		@param paymentruletype paymentruletype	  */
	public void setpaymentruletype (String paymentruletype)
	{

		set_Value (COLUMNNAME_paymentruletype, paymentruletype);
	}

	/** Get paymentruletype.
		@return paymentruletype	  */
	public String getpaymentruletype () 
	{
		return (String)get_Value(COLUMNNAME_paymentruletype);
	}

	/** Set PO_User_ID.
		@param PO_User_ID PO_User_ID	  */
	public void setPO_User_ID (int PO_User_ID)
	{
		if (PO_User_ID < 1) 
			set_Value (COLUMNNAME_PO_User_ID, null);
		else 
			set_Value (COLUMNNAME_PO_User_ID, Integer.valueOf(PO_User_ID));
	}

	/** Get PO_User_ID.
		@return PO_User_ID	  */
	public int getPO_User_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PO_User_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Total Amount.
		@param TotalAmt 
		Total Amount
	  */
	public void setTotalAmt (BigDecimal TotalAmt)
	{
		set_ValueNoCheck (COLUMNNAME_TotalAmt, TotalAmt);
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

	public I_UY_POPolicy getUY_POPolicy() throws RuntimeException
    {
		return (I_UY_POPolicy)MTable.get(getCtx(), I_UY_POPolicy.Table_Name)
			.getPO(getUY_POPolicy_ID(), get_TrxName());	}

	/** Set UY_POPolicy.
		@param UY_POPolicy_ID UY_POPolicy	  */
	public void setUY_POPolicy_ID (int UY_POPolicy_ID)
	{
		if (UY_POPolicy_ID < 1) 
			set_Value (COLUMNNAME_UY_POPolicy_ID, null);
		else 
			set_Value (COLUMNNAME_UY_POPolicy_ID, Integer.valueOf(UY_POPolicy_ID));
	}

	/** Get UY_POPolicy.
		@return UY_POPolicy	  */
	public int getUY_POPolicy_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_POPolicy_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UY_QuoteVendor.
		@param UY_QuoteVendor_ID UY_QuoteVendor	  */
	public void setUY_QuoteVendor_ID (int UY_QuoteVendor_ID)
	{
		if (UY_QuoteVendor_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_QuoteVendor_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_QuoteVendor_ID, Integer.valueOf(UY_QuoteVendor_ID));
	}

	/** Get UY_QuoteVendor.
		@return UY_QuoteVendor	  */
	public int getUY_QuoteVendor_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_QuoteVendor_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_RFQ_MacroReq getUY_RFQ_MacroReq() throws RuntimeException
    {
		return (I_UY_RFQ_MacroReq)MTable.get(getCtx(), I_UY_RFQ_MacroReq.Table_Name)
			.getPO(getUY_RFQ_MacroReq_ID(), get_TrxName());	}

	/** Set UY_RFQ_MacroReq.
		@param UY_RFQ_MacroReq_ID UY_RFQ_MacroReq	  */
	public void setUY_RFQ_MacroReq_ID (int UY_RFQ_MacroReq_ID)
	{
		if (UY_RFQ_MacroReq_ID < 1) 
			set_Value (COLUMNNAME_UY_RFQ_MacroReq_ID, null);
		else 
			set_Value (COLUMNNAME_UY_RFQ_MacroReq_ID, Integer.valueOf(UY_RFQ_MacroReq_ID));
	}

	/** Get UY_RFQ_MacroReq.
		@return UY_RFQ_MacroReq	  */
	public int getUY_RFQ_MacroReq_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_RFQ_MacroReq_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_RFQ_MacroReqLine getUY_RFQ_MacroReqLine() throws RuntimeException
    {
		return (I_UY_RFQ_MacroReqLine)MTable.get(getCtx(), I_UY_RFQ_MacroReqLine.Table_Name)
			.getPO(getUY_RFQ_MacroReqLine_ID(), get_TrxName());	}

	/** Set UY_RFQ_MacroReqLine.
		@param UY_RFQ_MacroReqLine_ID UY_RFQ_MacroReqLine	  */
	public void setUY_RFQ_MacroReqLine_ID (int UY_RFQ_MacroReqLine_ID)
	{
		if (UY_RFQ_MacroReqLine_ID < 1) 
			set_Value (COLUMNNAME_UY_RFQ_MacroReqLine_ID, null);
		else 
			set_Value (COLUMNNAME_UY_RFQ_MacroReqLine_ID, Integer.valueOf(UY_RFQ_MacroReqLine_ID));
	}

	/** Get UY_RFQ_MacroReqLine.
		@return UY_RFQ_MacroReqLine	  */
	public int getUY_RFQ_MacroReqLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_RFQ_MacroReqLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UY_RFQ_Requisition getUY_RFQ_Requisition() throws RuntimeException
    {
		return (I_UY_RFQ_Requisition)MTable.get(getCtx(), I_UY_RFQ_Requisition.Table_Name)
			.getPO(getUY_RFQ_Requisition_ID(), get_TrxName());	}

	/** Set UY_RFQ_Requisition.
		@param UY_RFQ_Requisition_ID UY_RFQ_Requisition	  */
	public void setUY_RFQ_Requisition_ID (int UY_RFQ_Requisition_ID)
	{
		if (UY_RFQ_Requisition_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UY_RFQ_Requisition_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UY_RFQ_Requisition_ID, Integer.valueOf(UY_RFQ_Requisition_ID));
	}

	/** Get UY_RFQ_Requisition.
		@return UY_RFQ_Requisition	  */
	public int getUY_RFQ_Requisition_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UY_RFQ_Requisition_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set VendorDocumentNo.
		@param VendorDocumentNo VendorDocumentNo	  */
	public void setVendorDocumentNo (String VendorDocumentNo)
	{
		set_Value (COLUMNNAME_VendorDocumentNo, VendorDocumentNo);
	}

	/** Get VendorDocumentNo.
		@return VendorDocumentNo	  */
	public String getVendorDocumentNo () 
	{
		return (String)get_Value(COLUMNNAME_VendorDocumentNo);
	}
}