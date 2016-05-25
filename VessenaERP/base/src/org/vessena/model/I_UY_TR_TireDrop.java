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

/** Generated Interface for UY_TR_TireDrop
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_TR_TireDrop 
{

    /** TableName=UY_TR_TireDrop */
    public static final String Table_Name = "UY_TR_TireDrop";

    /** AD_Table_ID=1000784 */
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

    /** Column name Motivo */
    public static final String COLUMNNAME_Motivo = "Motivo";

	/** Set Motivo.
	  * Motivo
	  */
	public void setMotivo (String Motivo);

	/** Get Motivo.
	  * Motivo
	  */
	public String getMotivo();

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

    /** Column name observaciones */
    public static final String COLUMNNAME_observaciones = "observaciones";

	/** Set observaciones	  */
	public void setobservaciones (String observaciones);

	/** Get observaciones	  */
	public String getobservaciones();

    /** Column name oldstatus */
    public static final String COLUMNNAME_oldstatus = "oldstatus";

	/** Set oldstatus	  */
	public void setoldstatus (String oldstatus);

	/** Get oldstatus	  */
	public String getoldstatus();

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

    /** Column name QtyKm5 */
    public static final String COLUMNNAME_QtyKm5 = "QtyKm5";

	/** Set QtyKm5.
	  * QtyKm5
	  */
	public void setQtyKm5 (int QtyKm5);

	/** Get QtyKm5.
	  * QtyKm5
	  */
	public int getQtyKm5();

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

    /** Column name UY_TR_TireDrop_ID */
    public static final String COLUMNNAME_UY_TR_TireDrop_ID = "UY_TR_TireDrop_ID";

	/** Set UY_TR_TireDrop	  */
	public void setUY_TR_TireDrop_ID (int UY_TR_TireDrop_ID);

	/** Get UY_TR_TireDrop	  */
	public int getUY_TR_TireDrop_ID();

    /** Column name UY_TR_Tire_ID */
    public static final String COLUMNNAME_UY_TR_Tire_ID = "UY_TR_Tire_ID";

	/** Set UY_TR_Tire	  */
	public void setUY_TR_Tire_ID (int UY_TR_Tire_ID);

	/** Get UY_TR_Tire	  */
	public int getUY_TR_Tire_ID();

	public I_UY_TR_Tire getUY_TR_Tire() throws RuntimeException;

    /** Column name UY_TR_TireMark_ID */
    public static final String COLUMNNAME_UY_TR_TireMark_ID = "UY_TR_TireMark_ID";

	/** Set UY_TR_TireMark	  */
	public void setUY_TR_TireMark_ID (int UY_TR_TireMark_ID);

	/** Get UY_TR_TireMark	  */
	public int getUY_TR_TireMark_ID();

	public I_UY_TR_TireMark getUY_TR_TireMark() throws RuntimeException;

    /** Column name UY_TR_TireMeasure_ID */
    public static final String COLUMNNAME_UY_TR_TireMeasure_ID = "UY_TR_TireMeasure_ID";

	/** Set UY_TR_TireMeasure	  */
	public void setUY_TR_TireMeasure_ID (int UY_TR_TireMeasure_ID);

	/** Get UY_TR_TireMeasure	  */
	public int getUY_TR_TireMeasure_ID();

	public I_UY_TR_TireMeasure getUY_TR_TireMeasure() throws RuntimeException;
}
