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
import org.openup.model.I_UY_POPolicyCategory;
import org.openup.model.I_UY_POPolicyRange;
import org.openup.model.I_UY_POSection;

/** Generated Interface for M_Requisition
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_M_Requisition 
{

    /** TableName=M_Requisition */
    public static final String Table_Name = "M_Requisition";

    /** AD_Table_ID=702 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 1 - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(1);

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

    /** Column name ApprovalSeqNo */
    public static final String COLUMNNAME_ApprovalSeqNo = "ApprovalSeqNo";

	/** Set ApprovalSeqNo	  */
	public void setApprovalSeqNo (int ApprovalSeqNo);

	/** Get ApprovalSeqNo	  */
	public int getApprovalSeqNo();

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

    /** Column name CategoryUser_ID */
    public static final String COLUMNNAME_CategoryUser_ID = "CategoryUser_ID";

	/** Set CategoryUser_ID	  */
	public void setCategoryUser_ID (int CategoryUser_ID);

	/** Get CategoryUser_ID	  */
	public int getCategoryUser_ID();

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

    /** Column name DateDoc */
    public static final String COLUMNNAME_DateDoc = "DateDoc";

	/** Set Document Date.
	  * Date of the Document
	  */
	public void setDateDoc (Timestamp DateDoc);

	/** Get Document Date.
	  * Date of the Document
	  */
	public Timestamp getDateDoc();

    /** Column name DateRequested */
    public static final String COLUMNNAME_DateRequested = "DateRequested";

	/** Set DateRequested	  */
	public void setDateRequested (Timestamp DateRequested);

	/** Get DateRequested	  */
	public Timestamp getDateRequested();

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

    /** Column name Help */
    public static final String COLUMNNAME_Help = "Help";

	/** Set Comment/Help.
	  * Comment or Hint
	  */
	public void setHelp (String Help);

	/** Get Comment/Help.
	  * Comment or Hint
	  */
	public String getHelp();

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

    /** Column name IsApproved2 */
    public static final String COLUMNNAME_IsApproved2 = "IsApproved2";

	/** Set IsApproved2	  */
	public void setIsApproved2 (boolean IsApproved2);

	/** Get IsApproved2	  */
	public boolean isApproved2();

    /** Column name IsApprovedCategory */
    public static final String COLUMNNAME_IsApprovedCategory = "IsApprovedCategory";

	/** Set IsApprovedCategory	  */
	public void setIsApprovedCategory (boolean IsApprovedCategory);

	/** Get IsApprovedCategory	  */
	public boolean isApprovedCategory();

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

    /** Column name M_Requisition_ID */
    public static final String COLUMNNAME_M_Requisition_ID = "M_Requisition_ID";

	/** Set Requisition.
	  * Material Requisition
	  */
	public void setM_Requisition_ID (int M_Requisition_ID);

	/** Get Requisition.
	  * Material Requisition
	  */
	public int getM_Requisition_ID();

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

    /** Column name NeedApprove1 */
    public static final String COLUMNNAME_NeedApprove1 = "NeedApprove1";

	/** Set NeedApprove1	  */
	public void setNeedApprove1 (boolean NeedApprove1);

	/** Get NeedApprove1	  */
	public boolean isNeedApprove1();

    /** Column name NeedApprove2 */
    public static final String COLUMNNAME_NeedApprove2 = "NeedApprove2";

	/** Set NeedApprove2	  */
	public void setNeedApprove2 (boolean NeedApprove2);

	/** Get NeedApprove2	  */
	public boolean isNeedApprove2();

    /** Column name nivel */
    public static final String COLUMNNAME_nivel = "nivel";

	/** Set nivel	  */
	public void setnivel (int nivel);

	/** Get nivel	  */
	public int getnivel();

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

    /** Column name POUser_ID */
    public static final String COLUMNNAME_POUser_ID = "POUser_ID";

	/** Set POUser_ID	  */
	public void setPOUser_ID (int POUser_ID);

	/** Get POUser_ID	  */
	public int getPOUser_ID();

    /** Column name PriorityRule */
    public static final String COLUMNNAME_PriorityRule = "PriorityRule";

	/** Set Priority.
	  * Priority of a document
	  */
	public void setPriorityRule (String PriorityRule);

	/** Get Priority.
	  * Priority of a document
	  */
	public String getPriorityRule();

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

    /** Column name UY_POPolicyCategory_ID */
    public static final String COLUMNNAME_UY_POPolicyCategory_ID = "UY_POPolicyCategory_ID";

	/** Set UY_POPolicyCategory	  */
	public void setUY_POPolicyCategory_ID (int UY_POPolicyCategory_ID);

	/** Get UY_POPolicyCategory	  */
	public int getUY_POPolicyCategory_ID();

	public I_UY_POPolicyCategory getUY_POPolicyCategory() throws RuntimeException;

    /** Column name UY_POPolicyRange_ID */
    public static final String COLUMNNAME_UY_POPolicyRange_ID = "UY_POPolicyRange_ID";

	/** Set UY_POPolicyRange	  */
	public void setUY_POPolicyRange_ID (int UY_POPolicyRange_ID);

	/** Get UY_POPolicyRange	  */
	public int getUY_POPolicyRange_ID();

	public I_UY_POPolicyRange getUY_POPolicyRange() throws RuntimeException;

    /** Column name UY_POSection_ID */
    public static final String COLUMNNAME_UY_POSection_ID = "UY_POSection_ID";

	/** Set UY_POSection	  */
	public void setUY_POSection_ID (int UY_POSection_ID);

	/** Get UY_POSection	  */
	public int getUY_POSection_ID();

	public I_UY_POSection getUY_POSection() throws RuntimeException;
}
