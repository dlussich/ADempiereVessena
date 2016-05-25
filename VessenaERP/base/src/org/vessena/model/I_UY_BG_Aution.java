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

/** Generated Interface for UY_BG_Aution
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_BG_Aution 
{

    /** TableName=UY_BG_Aution */
    public static final String Table_Name = "UY_BG_Aution";

    /** AD_Table_ID=1000955 */
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

    /** Column name Amt */
    public static final String COLUMNNAME_Amt = "Amt";

	/** Set Amount.
	  * Amount
	  */
	public void setAmt (BigDecimal Amt);

	/** Get Amount.
	  * Amount
	  */
	public BigDecimal getAmt();

    /** Column name amt2 */
    public static final String COLUMNNAME_amt2 = "amt2";

	/** Set amt2	  */
	public void setamt2 (BigDecimal amt2);

	/** Get amt2	  */
	public BigDecimal getamt2();

    /** Column name amt3 */
    public static final String COLUMNNAME_amt3 = "amt3";

	/** Set amt3	  */
	public void setamt3 (BigDecimal amt3);

	/** Get amt3	  */
	public BigDecimal getamt3();

    /** Column name AmtRetention */
    public static final String COLUMNNAME_AmtRetention = "AmtRetention";

	/** Set AmtRetention	  */
	public void setAmtRetention (BigDecimal AmtRetention);

	/** Get AmtRetention	  */
	public BigDecimal getAmtRetention();

    /** Column name AmtRetention2 */
    public static final String COLUMNNAME_AmtRetention2 = "AmtRetention2";

	/** Set AmtRetention2	  */
	public void setAmtRetention2 (BigDecimal AmtRetention2);

	/** Get AmtRetention2	  */
	public BigDecimal getAmtRetention2();

    /** Column name ApprovalStatus */
    public static final String COLUMNNAME_ApprovalStatus = "ApprovalStatus";

	/** Set ApprovalStatus	  */
	public void setApprovalStatus (String ApprovalStatus);

	/** Get ApprovalStatus	  */
	public String getApprovalStatus();

    /** Column name ApprovalText */
    public static final String COLUMNNAME_ApprovalText = "ApprovalText";

	/** Set ApprovalText	  */
	public void setApprovalText (String ApprovalText);

	/** Get ApprovalText	  */
	public String getApprovalText();

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

    /** Column name Code */
    public static final String COLUMNNAME_Code = "Code";

	/** Set Validation code.
	  * Validation Code
	  */
	public void setCode (String Code);

	/** Get Validation code.
	  * Validation Code
	  */
	public String getCode();

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

    /** Column name C_UOM_ID */
    public static final String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/** Set UOM.
	  * Unit of Measure
	  */
	public void setC_UOM_ID (int C_UOM_ID);

	/** Get UOM.
	  * Unit of Measure
	  */
	public int getC_UOM_ID();

	public org.compiere.model.I_C_UOM getC_UOM() throws RuntimeException;

    /** Column name DateApproved */
    public static final String COLUMNNAME_DateApproved = "DateApproved";

	/** Set DateApproved	  */
	public void setDateApproved (Timestamp DateApproved);

	/** Get DateApproved	  */
	public Timestamp getDateApproved();

    /** Column name DateRequested */
    public static final String COLUMNNAME_DateRequested = "DateRequested";

	/** Set DateRequested	  */
	public void setDateRequested (Timestamp DateRequested);

	/** Get DateRequested	  */
	public Timestamp getDateRequested();

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

    /** Column name HaveAttach1 */
    public static final String COLUMNNAME_HaveAttach1 = "HaveAttach1";

	/** Set HaveAttach1	  */
	public void setHaveAttach1 (boolean HaveAttach1);

	/** Get HaveAttach1	  */
	public boolean isHaveAttach1();

    /** Column name HaveAttach2 */
    public static final String COLUMNNAME_HaveAttach2 = "HaveAttach2";

	/** Set HaveAttach2	  */
	public void setHaveAttach2 (boolean HaveAttach2);

	/** Get HaveAttach2	  */
	public boolean isHaveAttach2();

    /** Column name HaveAttach3 */
    public static final String COLUMNNAME_HaveAttach3 = "HaveAttach3";

	/** Set HaveAttach3	  */
	public void setHaveAttach3 (boolean HaveAttach3);

	/** Get HaveAttach3	  */
	public boolean isHaveAttach3();

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

    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/** Set Product.
	  * Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID);

	/** Get Product.
	  * Product, Service, Item
	  */
	public int getM_Product_ID();

	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException;

    /** Column name Price */
    public static final String COLUMNNAME_Price = "Price";

	/** Set Price.
	  * Price
	  */
	public void setPrice (BigDecimal Price);

	/** Get Price.
	  * Price
	  */
	public BigDecimal getPrice();

    /** Column name PriceEntered */
    public static final String COLUMNNAME_PriceEntered = "PriceEntered";

	/** Set Price.
	  * Price Entered - the price based on the selected/base UoM
	  */
	public void setPriceEntered (BigDecimal PriceEntered);

	/** Get Price.
	  * Price Entered - the price based on the selected/base UoM
	  */
	public BigDecimal getPriceEntered();

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

    /** Column name Qty */
    public static final String COLUMNNAME_Qty = "Qty";

	/** Set Quantity.
	  * Quantity
	  */
	public void setQty (BigDecimal Qty);

	/** Get Quantity.
	  * Quantity
	  */
	public BigDecimal getQty();

    /** Column name RequestedUser_ID */
    public static final String COLUMNNAME_RequestedUser_ID = "RequestedUser_ID";

	/** Set RequestedUser_ID	  */
	public void setRequestedUser_ID (int RequestedUser_ID);

	/** Get RequestedUser_ID	  */
	public int getRequestedUser_ID();

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

    /** Column name UY_BG_AutionBid_ID */
    public static final String COLUMNNAME_UY_BG_AutionBid_ID = "UY_BG_AutionBid_ID";

	/** Set UY_BG_AutionBid	  */
	public void setUY_BG_AutionBid_ID (int UY_BG_AutionBid_ID);

	/** Get UY_BG_AutionBid	  */
	public int getUY_BG_AutionBid_ID();

	public I_UY_BG_AutionBid getUY_BG_AutionBid() throws RuntimeException;

    /** Column name UY_BG_Aution_ID */
    public static final String COLUMNNAME_UY_BG_Aution_ID = "UY_BG_Aution_ID";

	/** Set UY_BG_Aution	  */
	public void setUY_BG_Aution_ID (int UY_BG_Aution_ID);

	/** Get UY_BG_Aution	  */
	public int getUY_BG_Aution_ID();

    /** Column name UY_BG_AutionReq_ID */
    public static final String COLUMNNAME_UY_BG_AutionReq_ID = "UY_BG_AutionReq_ID";

	/** Set UY_BG_AutionReq	  */
	public void setUY_BG_AutionReq_ID (int UY_BG_AutionReq_ID);

	/** Get UY_BG_AutionReq	  */
	public int getUY_BG_AutionReq_ID();

	public I_UY_BG_AutionReq getUY_BG_AutionReq() throws RuntimeException;
}
