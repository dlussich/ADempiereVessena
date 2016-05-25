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
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.util.KeyNamePair;
import org.openup.model.I_UY_AsignaTransporteHdr;
import org.openup.model.I_UY_Budget;
import org.openup.model.I_UY_Departamentos;
import org.openup.model.I_UY_FF_Branch;
import org.openup.model.I_UY_FF_CashOut;
import org.openup.model.I_UY_ManufOrder;
import org.openup.model.I_UY_PaymentRule;
import org.openup.model.I_UY_Resguardo;

/** Generated Interface for C_Invoice
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS
 */
public interface I_C_Invoice 
{

    /** TableName=C_Invoice */
    public static final String Table_Name = "C_Invoice";

    /** AD_Table_ID=318 */
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

    /** Column name AD_OrgTrx_ID */
    public static final String COLUMNNAME_AD_OrgTrx_ID = "AD_OrgTrx_ID";

	/** Set Trx Organization.
	  * Performing or initiating organization
	  */
	public void setAD_OrgTrx_ID (int AD_OrgTrx_ID);

	/** Get Trx Organization.
	  * Performing or initiating organization
	  */
	public int getAD_OrgTrx_ID();

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

    /** Column name AmtOriginal */
    public static final String COLUMNNAME_AmtOriginal = "AmtOriginal";

	/** Set AmtOriginal	  */
	public void setAmtOriginal (BigDecimal AmtOriginal);

	/** Get AmtOriginal	  */
	public BigDecimal getAmtOriginal();

    /** Column name AmtRetention */
    public static final String COLUMNNAME_AmtRetention = "AmtRetention";

	/** Set AmtRetention	  */
	public void setAmtRetention (BigDecimal AmtRetention);

	/** Get AmtRetention	  */
	public BigDecimal getAmtRetention();

    /** Column name C_Activity_ID */
    public static final String COLUMNNAME_C_Activity_ID = "C_Activity_ID";

	/** Set Activity.
	  * Business Activity
	  */
	public void setC_Activity_ID (int C_Activity_ID);

	/** Get Activity.
	  * Business Activity
	  */
	public int getC_Activity_ID();

	public org.compiere.model.I_C_Activity getC_Activity() throws RuntimeException;

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

    /** Column name C_BPartner_ID_Aux */
    public static final String COLUMNNAME_C_BPartner_ID_Aux = "C_BPartner_ID_Aux";

	/** Set C_BPartner_ID_Aux	  */
	public void setC_BPartner_ID_Aux (int C_BPartner_ID_Aux);

	/** Get C_BPartner_ID_Aux	  */
	public int getC_BPartner_ID_Aux();

    /** Column name C_BPartner_Location_ID */
    public static final String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

	/** Set Partner Location.
	  * Identifies the (ship to) address for this Business Partner
	  */
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/** Get Partner Location.
	  * Identifies the (ship to) address for this Business Partner
	  */
	public int getC_BPartner_Location_ID();

	public org.compiere.model.I_C_BPartner_Location getC_BPartner_Location() throws RuntimeException;

    /** Column name C_Campaign_ID */
    public static final String COLUMNNAME_C_Campaign_ID = "C_Campaign_ID";

	/** Set Campaign.
	  * Marketing Campaign
	  */
	public void setC_Campaign_ID (int C_Campaign_ID);

	/** Get Campaign.
	  * Marketing Campaign
	  */
	public int getC_Campaign_ID();

	public org.compiere.model.I_C_Campaign getC_Campaign() throws RuntimeException;

    /** Column name C_CashBook_ID */
    public static final String COLUMNNAME_C_CashBook_ID = "C_CashBook_ID";

	/** Set Cash Book.
	  * Cash Book for recording petty cash transactions
	  */
	public void setC_CashBook_ID (int C_CashBook_ID);

	/** Get Cash Book.
	  * Cash Book for recording petty cash transactions
	  */
	public int getC_CashBook_ID();

	public org.compiere.model.I_C_CashBook getC_CashBook() throws RuntimeException;

    /** Column name C_CashLine_ID */
    public static final String COLUMNNAME_C_CashLine_ID = "C_CashLine_ID";

	/** Set Cash Journal Line.
	  * Cash Journal Line
	  */
	public void setC_CashLine_ID (int C_CashLine_ID);

	/** Get Cash Journal Line.
	  * Cash Journal Line
	  */
	public int getC_CashLine_ID();

	public org.compiere.model.I_C_CashLine getC_CashLine() throws RuntimeException;

    /** Column name C_Charge_ID */
    public static final String COLUMNNAME_C_Charge_ID = "C_Charge_ID";

	/** Set Charge.
	  * Additional document charges
	  */
	public void setC_Charge_ID (int C_Charge_ID);

	/** Get Charge.
	  * Additional document charges
	  */
	public int getC_Charge_ID();

	public org.compiere.model.I_C_Charge getC_Charge() throws RuntimeException;

    /** Column name C_ConversionType_ID */
    public static final String COLUMNNAME_C_ConversionType_ID = "C_ConversionType_ID";

	/** Set Currency Type.
	  * Currency Conversion Rate Type
	  */
	public void setC_ConversionType_ID (int C_ConversionType_ID);

	/** Get Currency Type.
	  * Currency Conversion Rate Type
	  */
	public int getC_ConversionType_ID();

	public org.compiere.model.I_C_ConversionType getC_ConversionType() throws RuntimeException;

    /** Column name C_Country_ID */
    public static final String COLUMNNAME_C_Country_ID = "C_Country_ID";

	/** Set Country.
	  * Country 
	  */
	public void setC_Country_ID (int C_Country_ID);

	/** Get Country.
	  * Country 
	  */
	public int getC_Country_ID();

	public org.compiere.model.I_C_Country getC_Country() throws RuntimeException;

    /** Column name C_Country_ID_1 */
    public static final String COLUMNNAME_C_Country_ID_1 = "C_Country_ID_1";

	/** Set C_Country_ID_1	  */
	public void setC_Country_ID_1 (int C_Country_ID_1);

	/** Get C_Country_ID_1	  */
	public int getC_Country_ID_1();

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

    /** Column name C_Customer_ID */
    public static final String COLUMNNAME_C_Customer_ID = "C_Customer_ID";

	/** Set C_Customer_ID	  */
	public void setC_Customer_ID (int C_Customer_ID);

	/** Get C_Customer_ID	  */
	public int getC_Customer_ID();

	public org.compiere.model.I_C_BPartner getC_Customer() throws RuntimeException;

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

    /** Column name C_DocTypeTarget_ID */
    public static final String COLUMNNAME_C_DocTypeTarget_ID = "C_DocTypeTarget_ID";

	/** Set Target Document Type.
	  * Target document type for conversing documents
	  */
	public void setC_DocTypeTarget_ID (int C_DocTypeTarget_ID);

	/** Get Target Document Type.
	  * Target document type for conversing documents
	  */
	public int getC_DocTypeTarget_ID();

	public org.compiere.model.I_C_DocType getC_DocTypeTarget() throws RuntimeException;

    /** Column name C_DunningLevel_ID */
    public static final String COLUMNNAME_C_DunningLevel_ID = "C_DunningLevel_ID";

	/** Set Dunning Level	  */
	public void setC_DunningLevel_ID (int C_DunningLevel_ID);

	/** Get Dunning Level	  */
	public int getC_DunningLevel_ID();

	public org.compiere.model.I_C_DunningLevel getC_DunningLevel() throws RuntimeException;

    /** Column name ChargeAmt */
    public static final String COLUMNNAME_ChargeAmt = "ChargeAmt";

	/** Set Charge amount.
	  * Charge Amount
	  */
	public void setChargeAmt (BigDecimal ChargeAmt);

	/** Get Charge amount.
	  * Charge Amount
	  */
	public BigDecimal getChargeAmt();

    /** Column name C_Invoice_ID */
    public static final String COLUMNNAME_C_Invoice_ID = "C_Invoice_ID";

	/** Set Invoice.
	  * Invoice Identifier
	  */
	public void setC_Invoice_ID (int C_Invoice_ID);

	/** Get Invoice.
	  * Invoice Identifier
	  */
	public int getC_Invoice_ID();

    /** Column name ContribuyenteFinal */
    public static final String COLUMNNAME_ContribuyenteFinal = "ContribuyenteFinal";

	/** Set ContribuyenteFinal	  */
	public void setContribuyenteFinal (boolean ContribuyenteFinal);

	/** Get ContribuyenteFinal	  */
	public boolean isContribuyenteFinal();

    /** Column name CopyFrom */
    public static final String COLUMNNAME_CopyFrom = "CopyFrom";

	/** Set Copy From.
	  * Copy From Record
	  */
	public void setCopyFrom (String CopyFrom);

	/** Get Copy From.
	  * Copy From Record
	  */
	public String getCopyFrom();

    /** Column name C_Order_ID */
    public static final String COLUMNNAME_C_Order_ID = "C_Order_ID";

	/** Set Order.
	  * Order
	  */
	public void setC_Order_ID (int C_Order_ID);

	/** Get Order.
	  * Order
	  */
	public int getC_Order_ID();

	public org.compiere.model.I_C_Order getC_Order() throws RuntimeException;

    /** Column name C_Payment_ID */
    public static final String COLUMNNAME_C_Payment_ID = "C_Payment_ID";

	/** Set Payment.
	  * Payment identifier
	  */
	public void setC_Payment_ID (int C_Payment_ID);

	/** Get Payment.
	  * Payment identifier
	  */
	public int getC_Payment_ID();

	public org.compiere.model.I_C_Payment getC_Payment() throws RuntimeException;

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

    /** Column name C_Period_ID_From */
    public static final String COLUMNNAME_C_Period_ID_From = "C_Period_ID_From";

	/** Set C_Period_ID_From	  */
	public void setC_Period_ID_From (int C_Period_ID_From);

	/** Get C_Period_ID_From	  */
	public int getC_Period_ID_From();

    /** Column name C_Period_ID_To */
    public static final String COLUMNNAME_C_Period_ID_To = "C_Period_ID_To";

	/** Set C_Period_ID_To	  */
	public void setC_Period_ID_To (int C_Period_ID_To);

	/** Get C_Period_ID_To	  */
	public int getC_Period_ID_To();

    /** Column name C_Project_ID */
    public static final String COLUMNNAME_C_Project_ID = "C_Project_ID";

	/** Set Project.
	  * Financial Project
	  */
	public void setC_Project_ID (int C_Project_ID);

	/** Get Project.
	  * Financial Project
	  */
	public int getC_Project_ID();

	public org.compiere.model.I_C_Project getC_Project() throws RuntimeException;

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

    /** Column name CreateFrom */
    public static final String COLUMNNAME_CreateFrom = "CreateFrom";

	/** Set Create lines from.
	  * Process which will generate a new document lines based on an existing document
	  */
	public void setCreateFrom (String CreateFrom);

	/** Get Create lines from.
	  * Process which will generate a new document lines based on an existing document
	  */
	public String getCreateFrom();

    /** Column name CreateFrom2 */
    public static final String COLUMNNAME_CreateFrom2 = "CreateFrom2";

	/** Set CreateFrom2	  */
	public void setCreateFrom2 (String CreateFrom2);

	/** Get CreateFrom2	  */
	public String getCreateFrom2();

    /** Column name CurrencyRate */
    public static final String COLUMNNAME_CurrencyRate = "CurrencyRate";

	/** Set Rate.
	  * Currency Conversion Rate
	  */
	public void setCurrencyRate (BigDecimal CurrencyRate);

	/** Get Rate.
	  * Currency Conversion Rate
	  */
	public BigDecimal getCurrencyRate();

    /** Column name Custodio */
    public static final String COLUMNNAME_Custodio = "Custodio";

	/** Set Custodio	  */
	public void setCustodio (String Custodio);

	/** Get Custodio	  */
	public String getCustodio();

    /** Column name DateAcct */
    public static final String COLUMNNAME_DateAcct = "DateAcct";

	/** Set Account Date.
	  * Accounting Date
	  */
	public void setDateAcct (Timestamp DateAcct);

	/** Get Account Date.
	  * Accounting Date
	  */
	public Timestamp getDateAcct();

    /** Column name DateInvoiced */
    public static final String COLUMNNAME_DateInvoiced = "DateInvoiced";

	/** Set Date Invoiced.
	  * Date printed on Invoice
	  */
	public void setDateInvoiced (Timestamp DateInvoiced);

	/** Get Date Invoiced.
	  * Date printed on Invoice
	  */
	public Timestamp getDateInvoiced();

    /** Column name DateOrdered */
    public static final String COLUMNNAME_DateOrdered = "DateOrdered";

	/** Set Date Ordered.
	  * Date of Order
	  */
	public void setDateOrdered (Timestamp DateOrdered);

	/** Get Date Ordered.
	  * Date of Order
	  */
	public Timestamp getDateOrdered();

    /** Column name DatePrinted */
    public static final String COLUMNNAME_DatePrinted = "DatePrinted";

	/** Set Date printed.
	  * Date the document was printed.
	  */
	public void setDatePrinted (Timestamp DatePrinted);

	/** Get Date printed.
	  * Date the document was printed.
	  */
	public Timestamp getDatePrinted();

    /** Column name datetimeinvoiced */
    public static final String COLUMNNAME_datetimeinvoiced = "datetimeinvoiced";

	/** Set datetimeinvoiced	  */
	public void setdatetimeinvoiced (Timestamp datetimeinvoiced);

	/** Get datetimeinvoiced	  */
	public Timestamp getdatetimeinvoiced();

    /** Column name DateVendor */
    public static final String COLUMNNAME_DateVendor = "DateVendor";

	/** Set DateVendor	  */
	public void setDateVendor (Timestamp DateVendor);

	/** Get DateVendor	  */
	public Timestamp getDateVendor();

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

    /** Column name Discount */
    public static final String COLUMNNAME_Discount = "Discount";

	/** Set Discount %.
	  * Discount in percent
	  */
	public void setDiscount (BigDecimal Discount);

	/** Get Discount %.
	  * Discount in percent
	  */
	public BigDecimal getDiscount();

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

    /** Column name DocStatusReason */
    public static final String COLUMNNAME_DocStatusReason = "DocStatusReason";

	/** Set DocStatusReason.
	  * DocStatusReason
	  */
	public void setDocStatusReason (String DocStatusReason);

	/** Get DocStatusReason.
	  * DocStatusReason
	  */
	public String getDocStatusReason();

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

    /** Column name DocumentNoAux */
    public static final String COLUMNNAME_DocumentNoAux = "DocumentNoAux";

	/** Set DocumentNoAux.
	  * DocumentNoAux
	  */
	public void setDocumentNoAux (String DocumentNoAux);

	/** Get DocumentNoAux.
	  * DocumentNoAux
	  */
	public String getDocumentNoAux();

    /** Column name DueDate */
    public static final String COLUMNNAME_DueDate = "DueDate";

	/** Set Due Date.
	  * Date when the payment is due
	  */
	public void setDueDate (Timestamp DueDate);

	/** Get Due Date.
	  * Date when the payment is due
	  */
	public Timestamp getDueDate();

    /** Column name DunningGrace */
    public static final String COLUMNNAME_DunningGrace = "DunningGrace";

	/** Set Dunning Grace Date	  */
	public void setDunningGrace (Timestamp DunningGrace);

	/** Get Dunning Grace Date	  */
	public Timestamp getDunningGrace();

    /** Column name embalaje */
    public static final String COLUMNNAME_embalaje = "embalaje";

	/** Set embalaje	  */
	public void setembalaje (String embalaje);

	/** Get embalaje	  */
	public String getembalaje();

    /** Column name GenerateInvoice */
    public static final String COLUMNNAME_GenerateInvoice = "GenerateInvoice";

	/** Set GenerateInvoice	  */
	public void setGenerateInvoice (String GenerateInvoice);

	/** Get GenerateInvoice	  */
	public String getGenerateInvoice();

    /** Column name GenerateTo */
    public static final String COLUMNNAME_GenerateTo = "GenerateTo";

	/** Set Generate To.
	  * Generate To
	  */
	public void setGenerateTo (String GenerateTo);

	/** Get Generate To.
	  * Generate To
	  */
	public String getGenerateTo();

    /** Column name GrandTotal */
    public static final String COLUMNNAME_GrandTotal = "GrandTotal";

	/** Set Grand Total.
	  * Total amount of document
	  */
	public void setGrandTotal (BigDecimal GrandTotal);

	/** Get Grand Total.
	  * Total amount of document
	  */
	public BigDecimal getGrandTotal();

    /** Column name incoterms */
    public static final String COLUMNNAME_incoterms = "incoterms";

	/** Set incoterms	  */
	public void setincoterms (String incoterms);

	/** Get incoterms	  */
	public String getincoterms();

    /** Column name InvoiceCollectionType */
    public static final String COLUMNNAME_InvoiceCollectionType = "InvoiceCollectionType";

	/** Set Collection Status.
	  * Invoice Collection Status
	  */
	public void setInvoiceCollectionType (String InvoiceCollectionType);

	/** Get Collection Status.
	  * Invoice Collection Status
	  */
	public String getInvoiceCollectionType();

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

    /** Column name IsDevengable */
    public static final String COLUMNNAME_IsDevengable = "IsDevengable";

	/** Set IsDevengable	  */
	public void setIsDevengable (boolean IsDevengable);

	/** Get IsDevengable	  */
	public boolean isDevengable();

    /** Column name IsDiscountPrinted */
    public static final String COLUMNNAME_IsDiscountPrinted = "IsDiscountPrinted";

	/** Set Discount Printed.
	  * Print Discount on Invoice and Order
	  */
	public void setIsDiscountPrinted (boolean IsDiscountPrinted);

	/** Get Discount Printed.
	  * Print Discount on Invoice and Order
	  */
	public boolean isDiscountPrinted();

    /** Column name IsInDispute */
    public static final String COLUMNNAME_IsInDispute = "IsInDispute";

	/** Set In Dispute.
	  * Document is in dispute
	  */
	public void setIsInDispute (boolean IsInDispute);

	/** Get In Dispute.
	  * Document is in dispute
	  */
	public boolean isInDispute();

    /** Column name IsPaid */
    public static final String COLUMNNAME_IsPaid = "IsPaid";

	/** Set Paid.
	  * The document is paid
	  */
	public void setIsPaid (boolean IsPaid);

	/** Get Paid.
	  * The document is paid
	  */
	public boolean isPaid();

    /** Column name IsPayScheduleValid */
    public static final String COLUMNNAME_IsPayScheduleValid = "IsPayScheduleValid";

	/** Set Pay Schedule valid.
	  * Is the Payment Schedule is valid
	  */
	public void setIsPayScheduleValid (boolean IsPayScheduleValid);

	/** Get Pay Schedule valid.
	  * Is the Payment Schedule is valid
	  */
	public boolean isPayScheduleValid();

    /** Column name IsPrinted */
    public static final String COLUMNNAME_IsPrinted = "IsPrinted";

	/** Set Printed.
	  * Indicates if this document / line is printed
	  */
	public void setIsPrinted (boolean IsPrinted);

	/** Get Printed.
	  * Indicates if this document / line is printed
	  */
	public boolean isPrinted();

    /** Column name IsSelfService */
    public static final String COLUMNNAME_IsSelfService = "IsSelfService";

	/** Set Self-Service.
	  * This is a Self-Service entry or this entry can be changed via Self-Service
	  */
	public void setIsSelfService (boolean IsSelfService);

	/** Get Self-Service.
	  * This is a Self-Service entry or this entry can be changed via Self-Service
	  */
	public boolean isSelfService();

    /** Column name IsSOTrx */
    public static final String COLUMNNAME_IsSOTrx = "IsSOTrx";

	/** Set Sales Transaction.
	  * This is a Sales Transaction
	  */
	public void setIsSOTrx (boolean IsSOTrx);

	/** Get Sales Transaction.
	  * This is a Sales Transaction
	  */
	public boolean isSOTrx();

    /** Column name IsTaxIncluded */
    public static final String COLUMNNAME_IsTaxIncluded = "IsTaxIncluded";

	/** Set Price includes Tax.
	  * Tax is included in the price 
	  */
	public void setIsTaxIncluded (boolean IsTaxIncluded);

	/** Get Price includes Tax.
	  * Tax is included in the price 
	  */
	public boolean isTaxIncluded();

    /** Column name IsTransferred */
    public static final String COLUMNNAME_IsTransferred = "IsTransferred";

	/** Set Transferred.
	  * Transferred to General Ledger (i.e. accounted)
	  */
	public void setIsTransferred (boolean IsTransferred);

	/** Get Transferred.
	  * Transferred to General Ledger (i.e. accounted)
	  */
	public boolean isTransferred();

    /** Column name LiteralNumber */
    public static final String COLUMNNAME_LiteralNumber = "LiteralNumber";

	/** Set LiteralNumber	  */
	public void setLiteralNumber (String LiteralNumber);

	/** Get LiteralNumber	  */
	public String getLiteralNumber();

    /** Column name LiteralNumber2 */
    public static final String COLUMNNAME_LiteralNumber2 = "LiteralNumber2";

	/** Set LiteralNumber2	  */
	public void setLiteralNumber2 (String LiteralNumber2);

	/** Get LiteralNumber2	  */
	public String getLiteralNumber2();

    /** Column name marcas */
    public static final String COLUMNNAME_marcas = "marcas";

	/** Set marcas	  */
	public void setmarcas (String marcas);

	/** Get marcas	  */
	public String getmarcas();

    /** Column name M_InOut_ID */
    public static final String COLUMNNAME_M_InOut_ID = "M_InOut_ID";

	/** Set Shipment/Receipt.
	  * Material Shipment Document
	  */
	public void setM_InOut_ID (int M_InOut_ID);

	/** Get Shipment/Receipt.
	  * Material Shipment Document
	  */
	public int getM_InOut_ID();

	public org.compiere.model.I_M_InOut getM_InOut() throws RuntimeException;

    /** Column name M_PriceList_ID */
    public static final String COLUMNNAME_M_PriceList_ID = "M_PriceList_ID";

	/** Set Price List.
	  * Unique identifier of a Price List
	  */
	public void setM_PriceList_ID (int M_PriceList_ID);

	/** Get Price List.
	  * Unique identifier of a Price List
	  */
	public int getM_PriceList_ID();

	public org.compiere.model.I_M_PriceList getM_PriceList() throws RuntimeException;

    /** Column name M_RMA_ID */
    public static final String COLUMNNAME_M_RMA_ID = "M_RMA_ID";

	/** Set RMA.
	  * Return Material Authorization
	  */
	public void setM_RMA_ID (int M_RMA_ID);

	/** Get RMA.
	  * Return Material Authorization
	  */
	public int getM_RMA_ID();

	public org.compiere.model.I_M_RMA getM_RMA() throws RuntimeException;

    /** Column name M_Warehouse_ID */
    public static final String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

	/** Set Warehouse.
	  * Storage Warehouse and Service Point
	  */
	public void setM_Warehouse_ID (int M_Warehouse_ID);

	/** Get Warehouse.
	  * Storage Warehouse and Service Point
	  */
	public int getM_Warehouse_ID();

	public org.compiere.model.I_M_Warehouse getM_Warehouse() throws RuntimeException;

    /** Column name numeracion */
    public static final String COLUMNNAME_numeracion = "numeracion";

	/** Set numeracion	  */
	public void setnumeracion (String numeracion);

	/** Get numeracion	  */
	public String getnumeracion();

    /** Column name observaciones */
    public static final String COLUMNNAME_observaciones = "observaciones";

	/** Set observaciones	  */
	public void setobservaciones (String observaciones);

	/** Get observaciones	  */
	public String getobservaciones();

    /** Column name PaymentRule */
    public static final String COLUMNNAME_PaymentRule = "PaymentRule";

	/** Set Payment Rule.
	  * How you pay the invoice
	  */
	public void setPaymentRule (String PaymentRule);

	/** Get Payment Rule.
	  * How you pay the invoice
	  */
	public String getPaymentRule();

    /** Column name paymentruletype */
    public static final String COLUMNNAME_paymentruletype = "paymentruletype";

	/** Set paymentruletype	  */
	public void setpaymentruletype (String paymentruletype);

	/** Get paymentruletype	  */
	public String getpaymentruletype();

    /** Column name pesoBruto */
    public static final String COLUMNNAME_pesoBruto = "pesoBruto";

	/** Set pesoBruto	  */
	public void setpesoBruto (String pesoBruto);

	/** Get pesoBruto	  */
	public String getpesoBruto();

    /** Column name pesoNeto */
    public static final String COLUMNNAME_pesoNeto = "pesoNeto";

	/** Set pesoNeto	  */
	public void setpesoNeto (String pesoNeto);

	/** Get pesoNeto	  */
	public String getpesoNeto();

    /** Column name POReference */
    public static final String COLUMNNAME_POReference = "POReference";

	/** Set Order Reference.
	  * Transaction Reference Number (Sales Order, Purchase Order) of your Business Partner
	  */
	public void setPOReference (String POReference);

	/** Get Order Reference.
	  * Transaction Reference Number (Sales Order, Purchase Order) of your Business Partner
	  */
	public String getPOReference();

    /** Column name Posted */
    public static final String COLUMNNAME_Posted = "Posted";

	/** Set Posted.
	  * Posting status
	  */
	public void setPosted (boolean Posted);

	/** Get Posted.
	  * Posting status
	  */
	public boolean isPosted();

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

    /** Column name ProcessedOn */
    public static final String COLUMNNAME_ProcessedOn = "ProcessedOn";

	/** Set Processed On.
	  * The date+time (expressed in decimal format) when the document has been processed
	  */
	public void setProcessedOn (BigDecimal ProcessedOn);

	/** Get Processed On.
	  * The date+time (expressed in decimal format) when the document has been processed
	  */
	public BigDecimal getProcessedOn();

    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/** Set Process Now	  */
	public void setProcessing (boolean Processing);

	/** Get Process Now	  */
	public boolean isProcessing();

    /** Column name proforminvoice_text */
    public static final String COLUMNNAME_proforminvoice_text = "proforminvoice_text";

	/** Set proforminvoice_text	  */
	public void setproforminvoice_text (String proforminvoice_text);

	/** Get proforminvoice_text	  */
	public String getproforminvoice_text();

    /** Column name QtyQuote */
    public static final String COLUMNNAME_QtyQuote = "QtyQuote";

	/** Set QtyQuote	  */
	public void setQtyQuote (int QtyQuote);

	/** Get QtyQuote	  */
	public int getQtyQuote();

    /** Column name Ref_Invoice_ID */
    public static final String COLUMNNAME_Ref_Invoice_ID = "Ref_Invoice_ID";

	/** Set Referenced Invoice	  */
	public void setRef_Invoice_ID (int Ref_Invoice_ID);

	/** Get Referenced Invoice	  */
	public int getRef_Invoice_ID();

    /** Column name Reversal_ID */
    public static final String COLUMNNAME_Reversal_ID = "Reversal_ID";

	/** Set Reversal ID.
	  * ID of document reversal
	  */
	public void setReversal_ID (int Reversal_ID);

	/** Get Reversal ID.
	  * ID of document reversal
	  */
	public int getReversal_ID();

	public org.compiere.model.I_C_Invoice getReversal() throws RuntimeException;

    /** Column name SalesRep_ID */
    public static final String COLUMNNAME_SalesRep_ID = "SalesRep_ID";

	/** Set Sales Representative.
	  * Sales Representative or Company Agent
	  */
	public void setSalesRep_ID (int SalesRep_ID);

	/** Get Sales Representative.
	  * Sales Representative or Company Agent
	  */
	public int getSalesRep_ID();

	public org.compiere.model.I_AD_User getSalesRep() throws RuntimeException;

    /** Column name SendEMail */
    public static final String COLUMNNAME_SendEMail = "SendEMail";

	/** Set Send EMail.
	  * Enable sending Document EMail
	  */
	public void setSendEMail (boolean SendEMail);

	/** Get Send EMail.
	  * Enable sending Document EMail
	  */
	public boolean isSendEMail();

    /** Column name serie */
    public static final String COLUMNNAME_serie = "serie";

	/** Set serie	  */
	public void setserie (String serie);

	/** Get serie	  */
	public String getserie();

    /** Column name TotalLines */
    public static final String COLUMNNAME_TotalLines = "TotalLines";

	/** Set Total Lines.
	  * Total of all document lines
	  */
	public void setTotalLines (BigDecimal TotalLines);

	/** Get Total Lines.
	  * Total of all document lines
	  */
	public BigDecimal getTotalLines();

    /** Column name transporte */
    public static final String COLUMNNAME_transporte = "transporte";

	/** Set transporte	  */
	public void settransporte (String transporte);

	/** Get transporte	  */
	public String gettransporte();

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

    /** Column name User1_ID */
    public static final String COLUMNNAME_User1_ID = "User1_ID";

	/** Set User List 1.
	  * User defined list element #1
	  */
	public void setUser1_ID (int User1_ID);

	/** Get User List 1.
	  * User defined list element #1
	  */
	public int getUser1_ID();

	public org.compiere.model.I_C_ElementValue getUser1() throws RuntimeException;

    /** Column name User2_ID */
    public static final String COLUMNNAME_User2_ID = "User2_ID";

	/** Set User List 2.
	  * User defined list element #2
	  */
	public void setUser2_ID (int User2_ID);

	/** Get User List 2.
	  * User defined list element #2
	  */
	public int getUser2_ID();

	public org.compiere.model.I_C_ElementValue getUser2() throws RuntimeException;

    /** Column name UY_AsignaTransHdrACTUAL_ID */
    public static final String COLUMNNAME_UY_AsignaTransHdrACTUAL_ID = "UY_AsignaTransHdrACTUAL_ID";

	/** Set UY_AsignaTransHdrACTUAL_ID	  */
	public void setUY_AsignaTransHdrACTUAL_ID (int UY_AsignaTransHdrACTUAL_ID);

	/** Get UY_AsignaTransHdrACTUAL_ID	  */
	public int getUY_AsignaTransHdrACTUAL_ID();

	public I_UY_AsignaTransporteHdr getUY_AsignaTransHdrACTUAL() throws RuntimeException;

    /** Column name UY_AsignaTransporteHdr_ID */
    public static final String COLUMNNAME_UY_AsignaTransporteHdr_ID = "UY_AsignaTransporteHdr_ID";

	/** Set UY_AsignaTransporteHdr_ID	  */
	public void setUY_AsignaTransporteHdr_ID (int UY_AsignaTransporteHdr_ID);

	/** Get UY_AsignaTransporteHdr_ID	  */
	public int getUY_AsignaTransporteHdr_ID();

	public I_UY_AsignaTransporteHdr getUY_AsignaTransporteHdr() throws RuntimeException;

    /** Column name UY_Budget_ID */
    public static final String COLUMNNAME_UY_Budget_ID = "UY_Budget_ID";

	/** Set UY_Budget	  */
	public void setUY_Budget_ID (int UY_Budget_ID);

	/** Get UY_Budget	  */
	public int getUY_Budget_ID();

	public I_UY_Budget getUY_Budget() throws RuntimeException;

    /** Column name uy_cantbultos */
    public static final String COLUMNNAME_uy_cantbultos = "uy_cantbultos";

	/** Set uy_cantbultos	  */
	public void setuy_cantbultos (BigDecimal uy_cantbultos);

	/** Get uy_cantbultos	  */
	public BigDecimal getuy_cantbultos();

    /** Column name uy_cantbultos_manual */
    public static final String COLUMNNAME_uy_cantbultos_manual = "uy_cantbultos_manual";

	/** Set uy_cantbultos_manual	  */
	public void setuy_cantbultos_manual (BigDecimal uy_cantbultos_manual);

	/** Get uy_cantbultos_manual	  */
	public BigDecimal getuy_cantbultos_manual();

    /** Column name UY_CloneInvoice */
    public static final String COLUMNNAME_UY_CloneInvoice = "UY_CloneInvoice";

	/** Set UY_CloneInvoice	  */
	public void setUY_CloneInvoice (String UY_CloneInvoice);

	/** Get UY_CloneInvoice	  */
	public String getUY_CloneInvoice();

    /** Column name UY_Departamentos_ID */
    public static final String COLUMNNAME_UY_Departamentos_ID = "UY_Departamentos_ID";

	/** Set Departamentos o regiones por Pais	  */
	public void setUY_Departamentos_ID (int UY_Departamentos_ID);

	/** Get Departamentos o regiones por Pais	  */
	public int getUY_Departamentos_ID();

	public I_UY_Departamentos getUY_Departamentos() throws RuntimeException;

    /** Column name UY_Departamentos_ID_1 */
    public static final String COLUMNNAME_UY_Departamentos_ID_1 = "UY_Departamentos_ID_1";

	/** Set UY_Departamentos_ID_1	  */
	public void setUY_Departamentos_ID_1 (int UY_Departamentos_ID_1);

	/** Get UY_Departamentos_ID_1	  */
	public int getUY_Departamentos_ID_1();

    /** Column name UY_FF_Branch_ID */
    public static final String COLUMNNAME_UY_FF_Branch_ID = "UY_FF_Branch_ID";

	/** Set UY_FF_Branch	  */
	public void setUY_FF_Branch_ID (int UY_FF_Branch_ID);

	/** Get UY_FF_Branch	  */
	public int getUY_FF_Branch_ID();

	public I_UY_FF_Branch getUY_FF_Branch() throws RuntimeException;

    /** Column name UY_FF_CashOut_ID */
    public static final String COLUMNNAME_UY_FF_CashOut_ID = "UY_FF_CashOut_ID";

	/** Set UY_FF_CashOut	  */
	public void setUY_FF_CashOut_ID (int UY_FF_CashOut_ID);

	/** Get UY_FF_CashOut	  */
	public int getUY_FF_CashOut_ID();

	public I_UY_FF_CashOut getUY_FF_CashOut() throws RuntimeException;

    /** Column name UY_Invoice */
    public static final String COLUMNNAME_UY_Invoice = "UY_Invoice";

	/** Set UY_Invoice	  */
	public void setUY_Invoice (String UY_Invoice);

	/** Get UY_Invoice	  */
	public String getUY_Invoice();

    /** Column name UY_Invoice_ID */
    public static final String COLUMNNAME_UY_Invoice_ID = "UY_Invoice_ID";

	/** Set UY_Invoice_ID	  */
	public void setUY_Invoice_ID (int UY_Invoice_ID);

	/** Get UY_Invoice_ID	  */
	public int getUY_Invoice_ID();

    /** Column name UY_ManufOrder_ID */
    public static final String COLUMNNAME_UY_ManufOrder_ID = "UY_ManufOrder_ID";

	/** Set UY_ManufOrder	  */
	public void setUY_ManufOrder_ID (int UY_ManufOrder_ID);

	/** Get UY_ManufOrder	  */
	public int getUY_ManufOrder_ID();

	public I_UY_ManufOrder getUY_ManufOrder() throws RuntimeException;

    /** Column name UY_PaymentRule_ID */
    public static final String COLUMNNAME_UY_PaymentRule_ID = "UY_PaymentRule_ID";

	/** Set UY_PaymentRule	  */
	public void setUY_PaymentRule_ID (int UY_PaymentRule_ID);

	/** Get UY_PaymentRule	  */
	public int getUY_PaymentRule_ID();

	public I_UY_PaymentRule getUY_PaymentRule() throws RuntimeException;

    /** Column name UY_ProcesoFactmasHdr_ID */
    public static final String COLUMNNAME_UY_ProcesoFactmasHdr_ID = "UY_ProcesoFactmasHdr_ID";

	/** Set UY_ProcesoFactmasHdr	  */
	public void setUY_ProcesoFactmasHdr_ID (BigDecimal UY_ProcesoFactmasHdr_ID);

	/** Get UY_ProcesoFactmasHdr	  */
	public BigDecimal getUY_ProcesoFactmasHdr_ID();

    /** Column name UY_Remito_ID */
    public static final String COLUMNNAME_UY_Remito_ID = "UY_Remito_ID";

	/** Set UY_Remito_ID	  */
	public void setUY_Remito_ID (BigDecimal UY_Remito_ID);

	/** Get UY_Remito_ID	  */
	public BigDecimal getUY_Remito_ID();

    /** Column name UY_ReservaPedidoHdr_ID */
    public static final String COLUMNNAME_UY_ReservaPedidoHdr_ID = "UY_ReservaPedidoHdr_ID";

	/** Set UY_ReservaPedidoHdr	  */
	public void setUY_ReservaPedidoHdr_ID (BigDecimal UY_ReservaPedidoHdr_ID);

	/** Get UY_ReservaPedidoHdr	  */
	public BigDecimal getUY_ReservaPedidoHdr_ID();

    /** Column name UY_Resguardo_ID */
    public static final String COLUMNNAME_UY_Resguardo_ID = "UY_Resguardo_ID";

	/** Set UY_Resguardo	  */
	public void setUY_Resguardo_ID (int UY_Resguardo_ID);

	/** Get UY_Resguardo	  */
	public int getUY_Resguardo_ID();

	public I_UY_Resguardo getUY_Resguardo() throws RuntimeException;

    /** Column name uy_tipogeneracion */
    public static final String COLUMNNAME_uy_tipogeneracion = "uy_tipogeneracion";

	/** Set uy_tipogeneracion	  */
	public void setuy_tipogeneracion (String uy_tipogeneracion);

	/** Get uy_tipogeneracion	  */
	public String getuy_tipogeneracion();

    /** Column name Volume */
    public static final String COLUMNNAME_Volume = "Volume";

	/** Set Volume.
	  * Volume of a product
	  */
	public void setVolume (String Volume);

	/** Get Volume.
	  * Volume of a product
	  */
	public String getVolume();

	
}
