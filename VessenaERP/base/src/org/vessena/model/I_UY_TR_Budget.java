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

/** Generated Interface for UY_TR_Budget
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_TR_Budget 
{

    /** TableName=UY_TR_Budget */
    public static final String Table_Name = "UY_TR_Budget";

    /** AD_Table_ID=1000811 */
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

    /** Column name C_BPartner_ID_From */
    public static final String COLUMNNAME_C_BPartner_ID_From = "C_BPartner_ID_From";

	/** Set C_BPartner_ID_From	  */
	public void setC_BPartner_ID_From (int C_BPartner_ID_From);

	/** Get C_BPartner_ID_From	  */
	public int getC_BPartner_ID_From();

    /** Column name C_BPartner_ID_To */
    public static final String COLUMNNAME_C_BPartner_ID_To = "C_BPartner_ID_To";

	/** Set C_BPartner_ID_To	  */
	public void setC_BPartner_ID_To (int C_BPartner_ID_To);

	/** Get C_BPartner_ID_To	  */
	public int getC_BPartner_ID_To();

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

    /** Column name CityType */
    public static final String COLUMNNAME_CityType = "CityType";

	/** Set CityType	  */
	public void setCityType (String CityType);

	/** Get CityType	  */
	public String getCityType();

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

    /** Column name ExecuteAction */
    public static final String COLUMNNAME_ExecuteAction = "ExecuteAction";

	/** Set ExecuteAction	  */
	public void setExecuteAction (String ExecuteAction);

	/** Get ExecuteAction	  */
	public String getExecuteAction();

    /** Column name ExecuteAction2 */
    public static final String COLUMNNAME_ExecuteAction2 = "ExecuteAction2";

	/** Set ExecuteAction2	  */
	public void setExecuteAction2 (String ExecuteAction2);

	/** Get ExecuteAction2	  */
	public String getExecuteAction2();

    /** Column name Expense */
    public static final String COLUMNNAME_Expense = "Expense";

	/** Set Expense	  */
	public void setExpense (String Expense);

	/** Get Expense	  */
	public String getExpense();

    /** Column name IncotermType */
    public static final String COLUMNNAME_IncotermType = "IncotermType";

	/** Set IncotermType	  */
	public void setIncotermType (String IncotermType);

	/** Get IncotermType	  */
	public String getIncotermType();

    /** Column name InTransit */
    public static final String COLUMNNAME_InTransit = "InTransit";

	/** Set InTransit	  */
	public void setInTransit (boolean InTransit);

	/** Get InTransit	  */
	public boolean isInTransit();

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

    /** Column name Note */
    public static final String COLUMNNAME_Note = "Note";

	/** Set Note.
	  * Optional additional user defined information
	  */
	public void setNote (String Note);

	/** Get Note.
	  * Optional additional user defined information
	  */
	public String getNote();

    /** Column name observaciones */
    public static final String COLUMNNAME_observaciones = "observaciones";

	/** Set observaciones	  */
	public void setobservaciones (String observaciones);

	/** Get observaciones	  */
	public String getobservaciones();

    /** Column name PartnerType */
    public static final String COLUMNNAME_PartnerType = "PartnerType";

	/** Set PartnerType	  */
	public void setPartnerType (String PartnerType);

	/** Get PartnerType	  */
	public String getPartnerType();

    /** Column name PaymentTermNote */
    public static final String COLUMNNAME_PaymentTermNote = "PaymentTermNote";

	/** Set Payment Term Note.
	  * Note of a Payment Term
	  */
	public void setPaymentTermNote (String PaymentTermNote);

	/** Get Payment Term Note.
	  * Note of a Payment Term
	  */
	public String getPaymentTermNote();

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

    /** Column name UserID */
    public static final String COLUMNNAME_UserID = "UserID";

	/** Set User ID.
	  * User ID or account number
	  */
	public void setUserID (int UserID);

	/** Get User ID.
	  * User ID or account number
	  */
	public int getUserID();

    /** Column name UY_Ciudad_ID */
    public static final String COLUMNNAME_UY_Ciudad_ID = "UY_Ciudad_ID";

	/** Set UY_Ciudad	  */
	public void setUY_Ciudad_ID (int UY_Ciudad_ID);

	/** Get UY_Ciudad	  */
	public int getUY_Ciudad_ID();

	public I_UY_Ciudad getUY_Ciudad() throws RuntimeException;

    /** Column name UY_TR_Border_ID */
    public static final String COLUMNNAME_UY_TR_Border_ID = "UY_TR_Border_ID";

	/** Set UY_TR_Border	  */
	public void setUY_TR_Border_ID (int UY_TR_Border_ID);

	/** Get UY_TR_Border	  */
	public int getUY_TR_Border_ID();

	public I_UY_TR_Border getUY_TR_Border() throws RuntimeException;

    /** Column name UY_TR_Border_ID_1 */
    public static final String COLUMNNAME_UY_TR_Border_ID_1 = "UY_TR_Border_ID_1";

	/** Set UY_TR_Border_ID_1.
	  * UY_TR_Border_ID_1
	  */
	public void setUY_TR_Border_ID_1 (int UY_TR_Border_ID_1);

	/** Get UY_TR_Border_ID_1.
	  * UY_TR_Border_ID_1
	  */
	public int getUY_TR_Border_ID_1();

	public I_UY_TR_Border getUY_TR_Border_I() throws RuntimeException;

    /** Column name UY_TR_Budget_ID */
    public static final String COLUMNNAME_UY_TR_Budget_ID = "UY_TR_Budget_ID";

	/** Set UY_TR_Budget	  */
	public void setUY_TR_Budget_ID (int UY_TR_Budget_ID);

	/** Get UY_TR_Budget	  */
	public int getUY_TR_Budget_ID();

    /** Column name UY_TR_BudgetReason_ID */
    public static final String COLUMNNAME_UY_TR_BudgetReason_ID = "UY_TR_BudgetReason_ID";

	/** Set Budget Reason	  */
	public void setUY_TR_BudgetReason_ID (int UY_TR_BudgetReason_ID);

	/** Get Budget Reason	  */
	public int getUY_TR_BudgetReason_ID();

	public I_UY_TR_BudgetReason getUY_TR_BudgetReason() throws RuntimeException;

    /** Column name UY_TR_Way_ID */
    public static final String COLUMNNAME_UY_TR_Way_ID = "UY_TR_Way_ID";

	/** Set UY_TR_Way	  */
	public void setUY_TR_Way_ID (int UY_TR_Way_ID);

	/** Get UY_TR_Way	  */
	public int getUY_TR_Way_ID();

	public I_UY_TR_Way getUY_TR_Way() throws RuntimeException;

    /** Column name ValoremPercentage */
    public static final String COLUMNNAME_ValoremPercentage = "ValoremPercentage";

	/** Set ValoremPercentage.
	  * ValoremPercentage
	  */
	public void setValoremPercentage (BigDecimal ValoremPercentage);

	/** Get ValoremPercentage.
	  * ValoremPercentage
	  */
	public BigDecimal getValoremPercentage();
}
