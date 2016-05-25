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

/** Generated Interface for UY_UserReq
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_UserReq 
{

    /** TableName=UY_UserReq */
    public static final String Table_Name = "UY_UserReq";

    /** AD_Table_ID=1000888 */
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

    /** Column name Address1 */
    public static final String COLUMNNAME_Address1 = "Address1";

	/** Set Address 1.
	  * Address line 1 for this location
	  */
	public void setAddress1 (String Address1);

	/** Get Address 1.
	  * Address line 1 for this location
	  */
	public String getAddress1();

    /** Column name Address2 */
    public static final String COLUMNNAME_Address2 = "Address2";

	/** Set Address 2.
	  * Address line 2 for this location
	  */
	public void setAddress2 (String Address2);

	/** Get Address 2.
	  * Address line 2 for this location
	  */
	public String getAddress2();

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

    /** Column name Aproved_ebagsa */
    public static final String COLUMNNAME_Aproved_ebagsa = "Aproved_ebagsa";

	/** Set Aproved_ebagsa.
	  * Aproved_ebagsa
	  */
	public void setAproved_ebagsa (boolean Aproved_ebagsa);

	/** Get Aproved_ebagsa.
	  * Aproved_ebagsa
	  */
	public boolean isAproved_ebagsa();

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

    /** Column name C_City_ID */
    public static final String COLUMNNAME_C_City_ID = "C_City_ID";

	/** Set City.
	  * City
	  */
	public void setC_City_ID (int C_City_ID);

	/** Get City.
	  * City
	  */
	public int getC_City_ID();

	public org.compiere.model.I_C_City getC_City() throws RuntimeException;

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

    /** Column name Cedula */
    public static final String COLUMNNAME_Cedula = "Cedula";

	/** Set Cedula	  */
	public void setCedula (String Cedula);

	/** Get Cedula	  */
	public String getCedula();

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

    /** Column name C_Region_ID */
    public static final String COLUMNNAME_C_Region_ID = "C_Region_ID";

	/** Set Region.
	  * Identifies a geographical Region
	  */
	public void setC_Region_ID (int C_Region_ID);

	/** Get Region.
	  * Identifies a geographical Region
	  */
	public int getC_Region_ID();

	public org.compiere.model.I_C_Region getC_Region() throws RuntimeException;

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

    /** Column name EMail */
    public static final String COLUMNNAME_EMail = "EMail";

	/** Set EMail Address.
	  * Electronic Mail Address
	  */
	public void setEMail (String EMail);

	/** Get EMail Address.
	  * Electronic Mail Address
	  */
	public String getEMail();

    /** Column name Email2 */
    public static final String COLUMNNAME_Email2 = "Email2";

	/** Set Email2	  */
	public void setEmail2 (String Email2);

	/** Get Email2	  */
	public String getEmail2();

    /** Column name FirstName */
    public static final String COLUMNNAME_FirstName = "FirstName";

	/** Set FirstName	  */
	public void setFirstName (String FirstName);

	/** Get FirstName	  */
	public String getFirstName();

    /** Column name FirstSurname */
    public static final String COLUMNNAME_FirstSurname = "FirstSurname";

	/** Set FirstSurname	  */
	public void setFirstSurname (String FirstSurname);

	/** Get FirstSurname	  */
	public String getFirstSurname();

    /** Column name GrantType */
    public static final String COLUMNNAME_GrantType = "GrantType";

	/** Set GrantType	  */
	public void setGrantType (String GrantType);

	/** Get GrantType	  */
	public String getGrantType();

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

    /** Column name HaveAttach4 */
    public static final String COLUMNNAME_HaveAttach4 = "HaveAttach4";

	/** Set HaveAttach4	  */
	public void setHaveAttach4 (boolean HaveAttach4);

	/** Get HaveAttach4	  */
	public boolean isHaveAttach4();

    /** Column name HaveAttach5 */
    public static final String COLUMNNAME_HaveAttach5 = "HaveAttach5";

	/** Set HaveAttach5	  */
	public void setHaveAttach5 (boolean HaveAttach5);

	/** Get HaveAttach5	  */
	public boolean isHaveAttach5();

    /** Column name HaveAttach6 */
    public static final String COLUMNNAME_HaveAttach6 = "HaveAttach6";

	/** Set HaveAttach6	  */
	public void setHaveAttach6 (boolean HaveAttach6);

	/** Get HaveAttach6	  */
	public boolean isHaveAttach6();

    /** Column name HaveAttach7 */
    public static final String COLUMNNAME_HaveAttach7 = "HaveAttach7";

	/** Set HaveAttach7	  */
	public void setHaveAttach7 (boolean HaveAttach7);

	/** Get HaveAttach7	  */
	public boolean isHaveAttach7();

    /** Column name HaveAttach8 */
    public static final String COLUMNNAME_HaveAttach8 = "HaveAttach8";

	/** Set HaveAttach8	  */
	public void setHaveAttach8 (boolean HaveAttach8);

	/** Get HaveAttach8	  */
	public boolean isHaveAttach8();

    /** Column name HaveAttach9 */
    public static final String COLUMNNAME_HaveAttach9 = "HaveAttach9";

	/** Set HaveAttach9	  */
	public void setHaveAttach9 (boolean HaveAttach9);

	/** Get HaveAttach9	  */
	public boolean isHaveAttach9();

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

    /** Column name IsSelected */
    public static final String COLUMNNAME_IsSelected = "IsSelected";

	/** Set Selected	  */
	public void setIsSelected (boolean IsSelected);

	/** Get Selected	  */
	public boolean isSelected();

    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/** Set Name.
	  * Alphanumeric identifier of the entity
	  */
	public void setName (String Name);

	/** Get Name.
	  * Alphanumeric identifier of the entity
	  */
	public String getName();

    /** Column name observaciones */
    public static final String COLUMNNAME_observaciones = "observaciones";

	/** Set observaciones	  */
	public void setobservaciones (String observaciones);

	/** Get observaciones	  */
	public String getobservaciones();

    /** Column name Observaciones2 */
    public static final String COLUMNNAME_Observaciones2 = "Observaciones2";

	/** Set Observaciones2	  */
	public void setObservaciones2 (String Observaciones2);

	/** Get Observaciones2	  */
	public String getObservaciones2();

    /** Column name PersonGrantType */
    public static final String COLUMNNAME_PersonGrantType = "PersonGrantType";

	/** Set PersonGrantType	  */
	public void setPersonGrantType (String PersonGrantType);

	/** Get PersonGrantType	  */
	public String getPersonGrantType();

    /** Column name Phone */
    public static final String COLUMNNAME_Phone = "Phone";

	/** Set Phone.
	  * Identifies a telephone number
	  */
	public void setPhone (String Phone);

	/** Get Phone.
	  * Identifies a telephone number
	  */
	public String getPhone();

    /** Column name Phone_2 */
    public static final String COLUMNNAME_Phone_2 = "Phone_2";

	/** Set Phone_2	  */
	public void setPhone_2 (String Phone_2);

	/** Get Phone_2	  */
	public String getPhone_2();

    /** Column name Phone_Ident */
    public static final String COLUMNNAME_Phone_Ident = "Phone_Ident";

	/** Set Phone_Ident	  */
	public void setPhone_Ident (boolean Phone_Ident);

	/** Get Phone_Ident	  */
	public boolean isPhone_Ident();

    /** Column name Phone_Ident_2 */
    public static final String COLUMNNAME_Phone_Ident_2 = "Phone_Ident_2";

	/** Set Phone_Ident_2	  */
	public void setPhone_Ident_2 (boolean Phone_Ident_2);

	/** Get Phone_Ident_2	  */
	public boolean isPhone_Ident_2();

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

    /** Column name RequestedUser_ID */
    public static final String COLUMNNAME_RequestedUser_ID = "RequestedUser_ID";

	/** Set RequestedUser_ID	  */
	public void setRequestedUser_ID (int RequestedUser_ID);

	/** Get RequestedUser_ID	  */
	public int getRequestedUser_ID();

    /** Column name RUC */
    public static final String COLUMNNAME_RUC = "RUC";

	/** Set RUC	  */
	public void setRUC (String RUC);

	/** Get RUC	  */
	public String getRUC();

    /** Column name SecondName */
    public static final String COLUMNNAME_SecondName = "SecondName";

	/** Set SecondName	  */
	public void setSecondName (String SecondName);

	/** Get SecondName	  */
	public String getSecondName();

    /** Column name SecondSurname */
    public static final String COLUMNNAME_SecondSurname = "SecondSurname";

	/** Set SecondSurname	  */
	public void setSecondSurname (String SecondSurname);

	/** Get SecondSurname	  */
	public String getSecondSurname();

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

    /** Column name UY_BG_Bursa_ID */
    public static final String COLUMNNAME_UY_BG_Bursa_ID = "UY_BG_Bursa_ID";

	/** Set UY_BG_Bursa_ID	  */
	public void setUY_BG_Bursa_ID (int UY_BG_Bursa_ID);

	/** Get UY_BG_Bursa_ID	  */
	public int getUY_BG_Bursa_ID();

	public I_UY_BG_Bursa getUY_BG_Bursa() throws RuntimeException;

    /** Column name UY_BG_Customer_ID */
    public static final String COLUMNNAME_UY_BG_Customer_ID = "UY_BG_Customer_ID";

	/** Set UY_BG_Customer	  */
	public void setUY_BG_Customer_ID (int UY_BG_Customer_ID);

	/** Get UY_BG_Customer	  */
	public int getUY_BG_Customer_ID();

	public I_UY_BG_Customer getUY_BG_Customer() throws RuntimeException;

    /** Column name UY_BG_UserActivity_ID */
    public static final String COLUMNNAME_UY_BG_UserActivity_ID = "UY_BG_UserActivity_ID";

	/** Set UY_BG_UserActivity	  */
	public void setUY_BG_UserActivity_ID (int UY_BG_UserActivity_ID);

	/** Get UY_BG_UserActivity	  */
	public int getUY_BG_UserActivity_ID();

	public I_UY_BG_UserActivity getUY_BG_UserActivity() throws RuntimeException;

    /** Column name UY_UserReq_ID */
    public static final String COLUMNNAME_UY_UserReq_ID = "UY_UserReq_ID";

	/** Set UY_UserReq	  */
	public void setUY_UserReq_ID (int UY_UserReq_ID);

	/** Get UY_UserReq	  */
	public int getUY_UserReq_ID();
}
