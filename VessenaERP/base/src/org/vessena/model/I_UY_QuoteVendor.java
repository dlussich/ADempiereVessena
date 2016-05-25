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
package org.openup.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.model.*;
import org.compiere.util.KeyNamePair;

/** Generated Interface for UY_QuoteVendor
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_QuoteVendor 
{

    /** TableName=UY_QuoteVendor */
    public static final String Table_Name = "UY_QuoteVendor";

    /** AD_Table_ID=1000378 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Client.
	  * Client/Tenant for this installation.
	  */
	public int getAD_Client_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Organization.
	  * Organizational entity within client
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Organization.
	  * Organizational entity within client
	  */
	public int getAD_Org_ID();

    /** Column name AD_User_ID */
    public static final String COLUMNNAME_AD_User_ID = "AD_User_ID";

	/** Set User/Contact.
	  * User within the system - Internal or Business Partner Contact
	  */
	public void setAD_User_ID (int AD_User_ID);

	/** Get User/Contact.
	  * User within the system - Internal or Business Partner Contact
	  */
	public int getAD_User_ID();

	public org.compiere.model.I_AD_User getAD_User() throws RuntimeException;

    /** Column name ApprovalDate */
    public static final String COLUMNNAME_ApprovalDate = "ApprovalDate";

	/** Set ApprovalDate	  */
	public void setApprovalDate (Timestamp ApprovalDate);

	/** Get ApprovalDate	  */
	public Timestamp getApprovalDate();

    /** Column name ApprovalDescription */
    public static final String COLUMNNAME_ApprovalDescription = "ApprovalDescription";

	/** Set ApprovalDescription	  */
	public void setApprovalDescription (String ApprovalDescription);

	/** Get ApprovalDescription	  */
	public String getApprovalDescription();

    /** Column name ApprovalStatus */
    public static final String COLUMNNAME_ApprovalStatus = "ApprovalStatus";

	/** Set ApprovalStatus	  */
	public void setApprovalStatus (String ApprovalStatus);

	/** Get ApprovalStatus	  */
	public String getApprovalStatus();

    /** Column name ApprovalUser_ID */
    public static final String COLUMNNAME_ApprovalUser_ID = "ApprovalUser_ID";

	/** Set ApprovalUser_ID	  */
	public void setApprovalUser_ID (int ApprovalUser_ID);

	/** Get ApprovalUser_ID	  */
	public int getApprovalUser_ID();

    /** Column name ApprovedType */
    public static final String COLUMNNAME_ApprovedType = "ApprovedType";

	/** Set ApprovedType	  */
	public void setApprovedType (String ApprovedType);

	/** Get ApprovedType	  */
	public String getApprovedType();

    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/** Set Business Partner .
	  * Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/** Get Business Partner .
	  * Identifies a Business Partner
	  */
	public int getC_BPartner_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException;

    /** Column name C_Currency_ID */
    public static final String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/** Set Currency.
	  * The Currency for this record
	  */
	public void setC_Currency_ID (int C_Currency_ID);

	/** Get Currency.
	  * The Currency for this record
	  */
	public int getC_Currency_ID();

	public org.compiere.model.I_C_Currency getC_Currency() throws RuntimeException;

    /** Column name C_DocType_ID */
    public static final String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

	/** Set Document Type.
	  * Document type or rules
	  */
	public void setC_DocType_ID (int C_DocType_ID);

	/** Get Document Type.
	  * Document type or rules
	  */
	public int getC_DocType_ID();

	public org.compiere.model.I_C_DocType getC_DocType() throws RuntimeException;

    /** Column name C_PaymentTerm_ID */
    public static final String COLUMNNAME_C_PaymentTerm_ID = "C_PaymentTerm_ID";

	/** Set Payment Term.
	  * The terms of Payment (timing, discount)
	  */
	public void setC_PaymentTerm_ID (int C_PaymentTerm_ID);

	/** Get Payment Term.
	  * The terms of Payment (timing, discount)
	  */
	public int getC_PaymentTerm_ID();

	public org.compiere.model.I_C_PaymentTerm getC_PaymentTerm() throws RuntimeException;

    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Created.
	  * Date this record was created
	  */
	public Timestamp getCreated();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Get Created By.
	  * User who created this records
	  */
	public int getCreatedBy();

    /** Column name DateRequired */
    public static final String COLUMNNAME_DateRequired = "DateRequired";

	/** Set Date Required.
	  * Date when required
	  */
	public void setDateRequired (Timestamp DateRequired);

	/** Get Date Required.
	  * Date when required
	  */
	public Timestamp getDateRequired();

    /** Column name DateResponse */
    public static final String COLUMNNAME_DateResponse = "DateResponse";

	/** Set Response Date.
	  * Date of the Response
	  */
	public void setDateResponse (Timestamp DateResponse);

	/** Get Response Date.
	  * Date of the Response
	  */
	public Timestamp getDateResponse();

    /** Column name DateTrx */
    public static final String COLUMNNAME_DateTrx = "DateTrx";

	/** Set Transaction Date.
	  * Transaction Date
	  */
	public void setDateTrx (Timestamp DateTrx);

	/** Get Transaction Date.
	  * Transaction Date
	  */
	public Timestamp getDateTrx();

    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/** Set Description.
	  * Optional short description of the record
	  */
	public void setDescription (String Description);

	/** Get Description.
	  * Optional short description of the record
	  */
	public String getDescription();

    /** Column name DocAction */
    public static final String COLUMNNAME_DocAction = "DocAction";

	/** Set Document Action.
	  * The targeted status of the document
	  */
	public void setDocAction (String DocAction);

	/** Get Document Action.
	  * The targeted status of the document
	  */
	public String getDocAction();

    /** Column name DocStatus */
    public static final String COLUMNNAME_DocStatus = "DocStatus";

	/** Set Document Status.
	  * The current status of the document
	  */
	public void setDocStatus (String DocStatus);

	/** Get Document Status.
	  * The current status of the document
	  */
	public String getDocStatus();

    /** Column name DocumentNo */
    public static final String COLUMNNAME_DocumentNo = "DocumentNo";

	/** Set Document No.
	  * Document sequence number of the document
	  */
	public void setDocumentNo (String DocumentNo);

	/** Get Document No.
	  * Document sequence number of the document
	  */
	public String getDocumentNo();

    /** Column name EffectiveDate */
    public static final String COLUMNNAME_EffectiveDate = "EffectiveDate";

	/** Set EffectiveDate	  */
	public void setEffectiveDate (Timestamp EffectiveDate);

	/** Get EffectiveDate	  */
	public Timestamp getEffectiveDate();

    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/** Set Active.
	  * The record is active in the system
	  */
	public void setIsActive (boolean IsActive);

	/** Get Active.
	  * The record is active in the system
	  */
	public boolean isActive();

    /** Column name IsApproved */
    public static final String COLUMNNAME_IsApproved = "IsApproved";

	/** Set Approved.
	  * Indicates if this document requires approval
	  */
	public void setIsApproved (boolean IsApproved);

	/** Get Approved.
	  * Indicates if this document requires approval
	  */
	public boolean isApproved();

    /** Column name IsApproved1 */
    public static final String COLUMNNAME_IsApproved1 = "IsApproved1";

	/** Set IsApproved1	  */
	public void setIsApproved1 (boolean IsApproved1);

	/** Get IsApproved1	  */
	public boolean isApproved1();

    /** Column name NeedApprove1 */
    public static final String COLUMNNAME_NeedApprove1 = "NeedApprove1";

	/** Set NeedApprove1	  */
	public void setNeedApprove1 (boolean NeedApprove1);

	/** Get NeedApprove1	  */
	public boolean isNeedApprove1();

    /** Column name paymentruletype */
    public static final String COLUMNNAME_paymentruletype = "paymentruletype";

	/** Set paymentruletype	  */
	public void setpaymentruletype (String paymentruletype);

	/** Get paymentruletype	  */
	public String getpaymentruletype();

    /** Column name PO_User_ID */
    public static final String COLUMNNAME_PO_User_ID = "PO_User_ID";

	/** Set PO_User_ID	  */
	public void setPO_User_ID (int PO_User_ID);

	/** Get PO_User_ID	  */
	public int getPO_User_ID();

    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/** Set Processed.
	  * The document has been processed
	  */
	public void setProcessed (boolean Processed);

	/** Get Processed.
	  * The document has been processed
	  */
	public boolean isProcessed();

    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/** Set Process Now	  */
	public void setProcessing (boolean Processing);

	/** Get Process Now	  */
	public boolean isProcessing();

    /** Column name TotalAmt */
    public static final String COLUMNNAME_TotalAmt = "TotalAmt";

	/** Set Total Amount.
	  * Total Amount
	  */
	public void setTotalAmt (BigDecimal TotalAmt);

	/** Get Total Amount.
	  * Total Amount
	  */
	public BigDecimal getTotalAmt();

    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Updated.
	  * Date this record was updated
	  */
	public Timestamp getUpdated();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get Updated By.
	  * User who updated this records
	  */
	public int getUpdatedBy();

    /** Column name UY_POPolicy_ID */
    public static final String COLUMNNAME_UY_POPolicy_ID = "UY_POPolicy_ID";

	/** Set UY_POPolicy	  */
	public void setUY_POPolicy_ID (int UY_POPolicy_ID);

	/** Get UY_POPolicy	  */
	public int getUY_POPolicy_ID();

	public I_UY_POPolicy getUY_POPolicy() throws RuntimeException;

    /** Column name UY_QuoteVendor_ID */
    public static final String COLUMNNAME_UY_QuoteVendor_ID = "UY_QuoteVendor_ID";

	/** Set UY_QuoteVendor	  */
	public void setUY_QuoteVendor_ID (int UY_QuoteVendor_ID);

	/** Get UY_QuoteVendor	  */
	public int getUY_QuoteVendor_ID();

    /** Column name UY_RFQ_MacroReq_ID */
    public static final String COLUMNNAME_UY_RFQ_MacroReq_ID = "UY_RFQ_MacroReq_ID";

	/** Set UY_RFQ_MacroReq	  */
	public void setUY_RFQ_MacroReq_ID (int UY_RFQ_MacroReq_ID);

	/** Get UY_RFQ_MacroReq	  */
	public int getUY_RFQ_MacroReq_ID();

	public I_UY_RFQ_MacroReq getUY_RFQ_MacroReq() throws RuntimeException;

    /** Column name UY_RFQ_MacroReqLine_ID */
    public static final String COLUMNNAME_UY_RFQ_MacroReqLine_ID = "UY_RFQ_MacroReqLine_ID";

	/** Set UY_RFQ_MacroReqLine	  */
	public void setUY_RFQ_MacroReqLine_ID (int UY_RFQ_MacroReqLine_ID);

	/** Get UY_RFQ_MacroReqLine	  */
	public int getUY_RFQ_MacroReqLine_ID();

	public I_UY_RFQ_MacroReqLine getUY_RFQ_MacroReqLine() throws RuntimeException;

    /** Column name UY_RFQ_Requisition_ID */
    public static final String COLUMNNAME_UY_RFQ_Requisition_ID = "UY_RFQ_Requisition_ID";

	/** Set UY_RFQ_Requisition	  */
	public void setUY_RFQ_Requisition_ID (int UY_RFQ_Requisition_ID);

	/** Get UY_RFQ_Requisition	  */
	public int getUY_RFQ_Requisition_ID();

	public I_UY_RFQ_Requisition getUY_RFQ_Requisition() throws RuntimeException;

    /** Column name VendorDocumentNo */
    public static final String COLUMNNAME_VendorDocumentNo = "VendorDocumentNo";

	/** Set VendorDocumentNo	  */
	public void setVendorDocumentNo (String VendorDocumentNo);

	/** Get VendorDocumentNo	  */
	public String getVendorDocumentNo();
}
