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

/** Generated Interface for UY_TT_Contract
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_TT_Contract 
{

    /** TableName=UY_TT_Contract */
    public static final String Table_Name = "UY_TT_Contract";

    /** AD_Table_ID=1010779 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name AccountNo */
    public static final String COLUMNNAME_AccountNo = "AccountNo";

	/** Set Account No.
	  * Account Number
	  */
	public void setAccountNo (String AccountNo);

	/** Get Account No.
	  * Account Number
	  */
	public String getAccountNo();

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

    /** Column name IsDelivered */
    public static final String COLUMNNAME_IsDelivered = "IsDelivered";

	/** Set Delivered	  */
	public void setIsDelivered (boolean IsDelivered);

	/** Get Delivered	  */
	public boolean isDelivered();

    /** Column name IsDelivered1 */
    public static final String COLUMNNAME_IsDelivered1 = "IsDelivered1";

	/** Set IsDelivered1	  */
	public void setIsDelivered1 (boolean IsDelivered1);

	/** Get IsDelivered1	  */
	public boolean isDelivered1();

    /** Column name IsDelivered2 */
    public static final String COLUMNNAME_IsDelivered2 = "IsDelivered2";

	/** Set IsDelivered2	  */
	public void setIsDelivered2 (boolean IsDelivered2);

	/** Get IsDelivered2	  */
	public boolean isDelivered2();

    /** Column name IsDelivered3 */
    public static final String COLUMNNAME_IsDelivered3 = "IsDelivered3";

	/** Set IsDelivered3	  */
	public void setIsDelivered3 (boolean IsDelivered3);

	/** Get IsDelivered3	  */
	public boolean isDelivered3();

    /** Column name IsDelivered4 */
    public static final String COLUMNNAME_IsDelivered4 = "IsDelivered4";

	/** Set IsDelivered4	  */
	public void setIsDelivered4 (boolean IsDelivered4);

	/** Get IsDelivered4	  */
	public boolean isDelivered4();

    /** Column name IsDelivered5 */
    public static final String COLUMNNAME_IsDelivered5 = "IsDelivered5";

	/** Set IsDelivered5	  */
	public void setIsDelivered5 (boolean IsDelivered5);

	/** Get IsDelivered5	  */
	public boolean isDelivered5();

    /** Column name LocatorValue */
    public static final String COLUMNNAME_LocatorValue = "LocatorValue";

	/** Set Locator Key.
	  * Key of the Warehouse Locator
	  */
	public void setLocatorValue (int LocatorValue);

	/** Get Locator Key.
	  * Key of the Warehouse Locator
	  */
	public int getLocatorValue();

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

    /** Column name PrintDoc1 */
    public static final String COLUMNNAME_PrintDoc1 = "PrintDoc1";

	/** Set PrintDoc1	  */
	public void setPrintDoc1 (boolean PrintDoc1);

	/** Get PrintDoc1	  */
	public boolean isPrintDoc1();

    /** Column name PrintDoc2 */
    public static final String COLUMNNAME_PrintDoc2 = "PrintDoc2";

	/** Set PrintDoc2.
	  * PrintDoc2
	  */
	public void setPrintDoc2 (boolean PrintDoc2);

	/** Get PrintDoc2.
	  * PrintDoc2
	  */
	public boolean isPrintDoc2();

    /** Column name PrintDoc3 */
    public static final String COLUMNNAME_PrintDoc3 = "PrintDoc3";

	/** Set PrintDoc3.
	  * PrintDoc3
	  */
	public void setPrintDoc3 (boolean PrintDoc3);

	/** Get PrintDoc3.
	  * PrintDoc3
	  */
	public boolean isPrintDoc3();

    /** Column name PrintDoc4 */
    public static final String COLUMNNAME_PrintDoc4 = "PrintDoc4";

	/** Set PrintDoc4.
	  * PrintDoc4
	  */
	public void setPrintDoc4 (boolean PrintDoc4);

	/** Get PrintDoc4.
	  * PrintDoc4
	  */
	public boolean isPrintDoc4();

    /** Column name PrintDoc5 */
    public static final String COLUMNNAME_PrintDoc5 = "PrintDoc5";

	/** Set PrintDoc5	  */
	public void setPrintDoc5 (boolean PrintDoc5);

	/** Get PrintDoc5	  */
	public boolean isPrintDoc5();

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

    /** Column name ScanText */
    public static final String COLUMNNAME_ScanText = "ScanText";

	/** Set ScanText	  */
	public void setScanText (String ScanText);

	/** Get ScanText	  */
	public String getScanText();

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

    /** Column name UY_DeliveryPoint_ID */
    public static final String COLUMNNAME_UY_DeliveryPoint_ID = "UY_DeliveryPoint_ID";

	/** Set UY_DeliveryPoint	  */
	public void setUY_DeliveryPoint_ID (int UY_DeliveryPoint_ID);

	/** Get UY_DeliveryPoint	  */
	public int getUY_DeliveryPoint_ID();

	public I_UY_DeliveryPoint getUY_DeliveryPoint() throws RuntimeException;

    /** Column name UY_R_Reclamo_ID */
    public static final String COLUMNNAME_UY_R_Reclamo_ID = "UY_R_Reclamo_ID";

	/** Set UY_R_Reclamo	  */
	public void setUY_R_Reclamo_ID (int UY_R_Reclamo_ID);

	/** Get UY_R_Reclamo	  */
	public int getUY_R_Reclamo_ID();

	public I_UY_R_Reclamo getUY_R_Reclamo() throws RuntimeException;

    /** Column name UY_R_Reclamo_ID_2 */
    public static final String COLUMNNAME_UY_R_Reclamo_ID_2 = "UY_R_Reclamo_ID_2";

	/** Set UY_R_Reclamo_ID_2	  */
	public void setUY_R_Reclamo_ID_2 (int UY_R_Reclamo_ID_2);

	/** Get UY_R_Reclamo_ID_2	  */
	public int getUY_R_Reclamo_ID_2();

    /** Column name UY_TT_Box_ID */
    public static final String COLUMNNAME_UY_TT_Box_ID = "UY_TT_Box_ID";

	/** Set UY_TT_Box	  */
	public void setUY_TT_Box_ID (int UY_TT_Box_ID);

	/** Get UY_TT_Box	  */
	public int getUY_TT_Box_ID();

	public I_UY_TT_Box getUY_TT_Box() throws RuntimeException;

    /** Column name UY_TT_Contract_ID */
    public static final String COLUMNNAME_UY_TT_Contract_ID = "UY_TT_Contract_ID";

	/** Set UY_TT_Contract	  */
	public void setUY_TT_Contract_ID (int UY_TT_Contract_ID);

	/** Get UY_TT_Contract	  */
	public int getUY_TT_Contract_ID();
}
