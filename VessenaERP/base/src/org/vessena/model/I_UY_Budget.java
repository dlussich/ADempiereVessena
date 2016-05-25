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

/** Generated Interface for UY_Budget
 *  @author Adempiere (generated) 
 *  @version Release 3.7.0LTS
 */
public interface I_UY_Budget 
{

    /** TableName=UY_Budget */
    public static final String Table_Name = "UY_Budget";

    /** AD_Table_ID=1000319 */
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

    /** Column name CopyFrom */
    public static final String COLUMNNAME_CopyFrom = "CopyFrom";

	/** Set Copy From.
	  * Copy From Record
	  */
	public void setCopyFrom (boolean CopyFrom);

	/** Get Copy From.
	  * Copy From Record
	  */
	public boolean isCopyFrom();

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

    /** Column name DateApproved */
    public static final String COLUMNNAME_DateApproved = "DateApproved";

	/** Set DateApproved	  */
	public void setDateApproved (Timestamp DateApproved);

	/** Get DateApproved	  */
	public Timestamp getDateApproved();

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

    /** Column name descripcion */
    public static final String COLUMNNAME_descripcion = "descripcion";

	/** Set descripcion	  */
	public void setdescripcion (String descripcion);

	/** Get descripcion	  */
	public String getdescripcion();

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

    /** Column name foot_text */
    public static final String COLUMNNAME_foot_text = "foot_text";

	/** Set foot_text	  */
	public void setfoot_text (String foot_text);

	/** Get foot_text	  */
	public String getfoot_text();

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

    /** Column name header_text */
    public static final String COLUMNNAME_header_text = "header_text";

	/** Set header_text	  */
	public void setheader_text (String header_text);

	/** Get header_text	  */
	public String getheader_text();

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

    /** Column name IsDescription */
    public static final String COLUMNNAME_IsDescription = "IsDescription";

	/** Set Description Only.
	  * if true, the line is just description and no transaction
	  */
	public void setIsDescription (boolean IsDescription);

	/** Get Description Only.
	  * if true, the line is just description and no transaction
	  */
	public boolean isDescription();

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

    /** Column name PaymentRule */
    public static final String COLUMNNAME_PaymentRule = "PaymentRule";

	/** Set Payment Rule.
	  * How you pay the invoice
	  */
	public void setPaymentRule (boolean PaymentRule);

	/** Get Payment Rule.
	  * How you pay the invoice
	  */
	public boolean isPaymentRule();

    /** Column name Pic1_ID */
    public static final String COLUMNNAME_Pic1_ID = "Pic1_ID";

	/** Set Pic1_ID	  */
	public void setPic1_ID (int Pic1_ID);

	/** Get Pic1_ID	  */
	public int getPic1_ID();

    /** Column name Pic2_ID */
    public static final String COLUMNNAME_Pic2_ID = "Pic2_ID";

	/** Set Pic2_ID	  */
	public void setPic2_ID (int Pic2_ID);

	/** Get Pic2_ID	  */
	public int getPic2_ID();

    /** Column name Pic3_ID */
    public static final String COLUMNNAME_Pic3_ID = "Pic3_ID";

	/** Set Pic3_ID	  */
	public void setPic3_ID (int Pic3_ID);

	/** Get Pic3_ID	  */
	public int getPic3_ID();

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

    /** Column name PriorityRule */
    public static final String COLUMNNAME_PriorityRule = "PriorityRule";

	/** Set Priority.
	  * Priority of a document
	  */
	public void setPriorityRule (boolean PriorityRule);

	/** Get Priority.
	  * Priority of a document
	  */
	public boolean isPriorityRule();

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
	public void setSendEMail (String SendEMail);

	/** Get Send EMail.
	  * Enable sending Document EMail
	  */
	public String getSendEMail();

    /** Column name Sent */
    public static final String COLUMNNAME_Sent = "Sent";

	/** Set Sent	  */
	public void setSent (boolean Sent);

	/** Get Sent	  */
	public boolean isSent();

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

    /** Column name UY_Atencion */
    public static final String COLUMNNAME_UY_Atencion = "UY_Atencion";

	/** Set UY_Atencion.
	  * UY_Atencion
	  */
	public void setUY_Atencion (String UY_Atencion);

	/** Get UY_Atencion.
	  * UY_Atencion
	  */
	public String getUY_Atencion();

    /** Column name UY_Budget_ID */
    public static final String COLUMNNAME_UY_Budget_ID = "UY_Budget_ID";

	/** Set UY_Budget	  */
	public void setUY_Budget_ID (int UY_Budget_ID);

	/** Get UY_Budget	  */
	public int getUY_Budget_ID();

    /** Column name UY_BudgetCloned_ID */
    public static final String COLUMNNAME_UY_BudgetCloned_ID = "UY_BudgetCloned_ID";

	/** Set UY_BudgetCloned_ID	  */
	public void setUY_BudgetCloned_ID (int UY_BudgetCloned_ID);

	/** Get UY_BudgetCloned_ID	  */
	public int getUY_BudgetCloned_ID();

	public I_UY_Budget getUY_BudgetCloned() throws RuntimeException;

    /** Column name UY_CloneBudget */
    public static final String COLUMNNAME_UY_CloneBudget = "UY_CloneBudget";

	/** Set UY_CloneBudget	  */
	public void setUY_CloneBudget (String UY_CloneBudget);

	/** Get UY_CloneBudget	  */
	public String getUY_CloneBudget();

    /** Column name UY_GenerateOrder */
    public static final String COLUMNNAME_UY_GenerateOrder = "UY_GenerateOrder";

	/** Set UY_GenerateOrder	  */
	public void setUY_GenerateOrder (String UY_GenerateOrder);

	/** Get UY_GenerateOrder	  */
	public String getUY_GenerateOrder();

    /** Column name UY_ManufOrder_ID */
    public static final String COLUMNNAME_UY_ManufOrder_ID = "UY_ManufOrder_ID";

	/** Set UY_ManufOrder	  */
	public void setUY_ManufOrder_ID (int UY_ManufOrder_ID);

	/** Get UY_ManufOrder	  */
	public int getUY_ManufOrder_ID();

	public I_UY_ManufOrder getUY_ManufOrder() throws RuntimeException;

    /** Column name UY_PrintBudget */
    public static final String COLUMNNAME_UY_PrintBudget = "UY_PrintBudget";

	/** Set UY_PrintBudget	  */
	public void setUY_PrintBudget (String UY_PrintBudget);

	/** Get UY_PrintBudget	  */
	public String getUY_PrintBudget();

    /** Column name UY_PrintBudget2 */
    public static final String COLUMNNAME_UY_PrintBudget2 = "UY_PrintBudget2";

	/** Set UY_PrintBudget2	  */
	public void setUY_PrintBudget2 (String UY_PrintBudget2);

	/** Get UY_PrintBudget2	  */
	public String getUY_PrintBudget2();

    /** Column name WithOptions */
    public static final String COLUMNNAME_WithOptions = "WithOptions";

	/** Set WithOptions	  */
	public void setWithOptions (boolean WithOptions);

	/** Get WithOptions	  */
	public boolean isWithOptions();

    /** Column name WorkName */
    public static final String COLUMNNAME_WorkName = "WorkName";

	/** Set WorkName	  */
	public void setWorkName (String WorkName);

	/** Get WorkName	  */
	public String getWorkName();
}
