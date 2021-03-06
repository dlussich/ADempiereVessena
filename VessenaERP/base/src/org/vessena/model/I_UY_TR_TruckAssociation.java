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

/** Generated Interface for UY_TR_TruckAssociation
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_TR_TruckAssociation 
{

    /** TableName=UY_TR_TruckAssociation */
    public static final String Table_Name = "UY_TR_TruckAssociation";

    /** AD_Table_ID=1000790 */
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

    /** Column name QtyKm */
    public static final String COLUMNNAME_QtyKm = "QtyKm";

	/** Set QtyKm.
	  * QtyKm
	  */
	public void setQtyKm (int QtyKm);

	/** Get QtyKm.
	  * QtyKm
	  */
	public int getQtyKm();

    /** Column name TruckAssociationType */
    public static final String COLUMNNAME_TruckAssociationType = "TruckAssociationType";

	/** Set TruckAssociationType.
	  * TruckAssociationType
	  */
	public void setTruckAssociationType (String TruckAssociationType);

	/** Get TruckAssociationType.
	  * TruckAssociationType
	  */
	public String getTruckAssociationType();

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

    /** Column name UY_TR_Truck_ID */
    public static final String COLUMNNAME_UY_TR_Truck_ID = "UY_TR_Truck_ID";

	/** Set UY_TR_Truck	  */
	public void setUY_TR_Truck_ID (int UY_TR_Truck_ID);

	/** Get UY_TR_Truck	  */
	public int getUY_TR_Truck_ID();

	public I_UY_TR_Truck getUY_TR_Truck() throws RuntimeException;

    /** Column name UY_TR_Truck_ID_New */
    public static final String COLUMNNAME_UY_TR_Truck_ID_New = "UY_TR_Truck_ID_New";

	/** Set UY_TR_Truck_ID_New	  */
	public void setUY_TR_Truck_ID_New (int UY_TR_Truck_ID_New);

	/** Get UY_TR_Truck_ID_New	  */
	public int getUY_TR_Truck_ID_New();

    /** Column name UY_TR_Truck_ID_Old */
    public static final String COLUMNNAME_UY_TR_Truck_ID_Old = "UY_TR_Truck_ID_Old";

	/** Set UY_TR_Truck_ID_Old	  */
	public void setUY_TR_Truck_ID_Old (int UY_TR_Truck_ID_Old);

	/** Get UY_TR_Truck_ID_Old	  */
	public int getUY_TR_Truck_ID_Old();

    /** Column name UY_TR_TruckAssociation_ID */
    public static final String COLUMNNAME_UY_TR_TruckAssociation_ID = "UY_TR_TruckAssociation_ID";

	/** Set UY_TR_TruckAssociation	  */
	public void setUY_TR_TruckAssociation_ID (int UY_TR_TruckAssociation_ID);

	/** Get UY_TR_TruckAssociation	  */
	public int getUY_TR_TruckAssociation_ID();

    /** Column name UY_TR_TruckType_ID */
    public static final String COLUMNNAME_UY_TR_TruckType_ID = "UY_TR_TruckType_ID";

	/** Set UY_TR_TruckType	  */
	public void setUY_TR_TruckType_ID (int UY_TR_TruckType_ID);

	/** Get UY_TR_TruckType	  */
	public int getUY_TR_TruckType_ID();

	public I_UY_TR_TruckType getUY_TR_TruckType() throws RuntimeException;

    /** Column name UY_TR_TruckType_ID_2 */
    public static final String COLUMNNAME_UY_TR_TruckType_ID_2 = "UY_TR_TruckType_ID_2";

	/** Set UY_TR_TruckType_ID_2	  */
	public void setUY_TR_TruckType_ID_2 (int UY_TR_TruckType_ID_2);

	/** Get UY_TR_TruckType_ID_2	  */
	public int getUY_TR_TruckType_ID_2();

    /** Column name UY_TR_TruckType_ID_3 */
    public static final String COLUMNNAME_UY_TR_TruckType_ID_3 = "UY_TR_TruckType_ID_3";

	/** Set UY_TR_TruckType_ID_3	  */
	public void setUY_TR_TruckType_ID_3 (int UY_TR_TruckType_ID_3);

	/** Get UY_TR_TruckType_ID_3	  */
	public int getUY_TR_TruckType_ID_3();
}
