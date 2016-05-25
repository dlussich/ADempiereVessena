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

import org.compiere.model.I_C_DocType;
import org.compiere.model.I_M_Shipper;
import org.compiere.model.MTable;
import org.compiere.util.KeyNamePair;

/** Generated Interface for UY_CierreTransporteHdr
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_UY_CierreTransporteHdr 
{

    /** TableName=UY_CierreTransporteHdr */
    public static final String Table_Name = "UY_CierreTransporteHdr";

    /** AD_Table_ID=1000132 */
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

	public I_C_DocType getC_DocType() throws RuntimeException;

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

    /** Column name EndDate */
    public static final String COLUMNNAME_EndDate = "EndDate";

	/** Set End Date.
	  * Last effective date (inclusive)
	  */
	public void setEndDate (Timestamp EndDate);

	/** Get End Date.
	  * Last effective date (inclusive)
	  */
	public Timestamp getEndDate();

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

    /** Column name M_Shipper_ID */
    public static final String COLUMNNAME_M_Shipper_ID = "M_Shipper_ID";

	/** Set Shipper.
	  * Method or manner of product delivery
	  */
	public void setM_Shipper_ID (int M_Shipper_ID);

	/** Get Shipper.
	  * Method or manner of product delivery
	  */
	public int getM_Shipper_ID();

	public I_M_Shipper getM_Shipper() throws RuntimeException;

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

    /** Column name StartDate */
    public static final String COLUMNNAME_StartDate = "StartDate";

	/** Set Start Date.
	  * First effective day (inclusive)
	  */
	public void setStartDate (Timestamp StartDate);

	/** Get Start Date.
	  * First effective day (inclusive)
	  */
	public Timestamp getStartDate();

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

    /** Column name UY_AsignaTransporteHdr_ID */
    public static final String COLUMNNAME_UY_AsignaTransporteHdr_ID = "UY_AsignaTransporteHdr_ID";

	/** Set UY_AsignaTransporteHdr_ID	  */
	public void setUY_AsignaTransporteHdr_ID (int UY_AsignaTransporteHdr_ID);

	/** Get UY_AsignaTransporteHdr_ID	  */
	public int getUY_AsignaTransporteHdr_ID();

	public I_UY_AsignaTransporteHdr getUY_AsignaTransporteHdr() throws RuntimeException;

    /** Column name uy_bultosapagar */
    public static final String COLUMNNAME_uy_bultosapagar = "uy_bultosapagar";

	/** Set uy_bultosapagar	  */
	public void setuy_bultosapagar (int uy_bultosapagar);

	/** Get uy_bultosapagar	  */
	public int getuy_bultosapagar();

    /** Column name UY_CierreTransporteHdr_ID */
    public static final String COLUMNNAME_UY_CierreTransporteHdr_ID = "UY_CierreTransporteHdr_ID";

	/** Set UY_CierreTransporteHdr	  */
	public void setUY_CierreTransporteHdr_ID (int UY_CierreTransporteHdr_ID);

	/** Get UY_CierreTransporteHdr	  */
	public int getUY_CierreTransporteHdr_ID();

    /** Column name UY_Drivers_ID */
    public static final String COLUMNNAME_UY_Drivers_ID = "UY_Drivers_ID";

	/** Set UY_Drivers	  */
	public void setUY_Drivers_ID (int UY_Drivers_ID);

	/** Get UY_Drivers	  */
	public int getUY_Drivers_ID();

	// public I_UY_Drivers getUY_Drivers() throws RuntimeException;

    /** Column name uy_fechavalores */
    public static final String COLUMNNAME_uy_fechavalores = "uy_fechavalores";

	/** Set uy_fechavalores	  */
	public void setuy_fechavalores (Timestamp uy_fechavalores);

	/** Get uy_fechavalores	  */
	public Timestamp getuy_fechavalores();

    /** Column name uy_km_fin */
    public static final String COLUMNNAME_uy_km_fin = "uy_km_fin";

	/** Set uy_km_fin	  */
	public void setuy_km_fin (int uy_km_fin);

	/** Get uy_km_fin	  */
	public int getuy_km_fin();

    /** Column name uy_km_inicio */
    public static final String COLUMNNAME_uy_km_inicio = "uy_km_inicio";

	/** Set uy_km_inicio	  */
	public void setuy_km_inicio (int uy_km_inicio);

	/** Get uy_km_inicio	  */
	public int getuy_km_inicio();

    /** Column name UY_Vincular_Cobranza */
    public static final String COLUMNNAME_UY_Vincular_Cobranza = "UY_Vincular_Cobranza";

	/** Set UY_Vincular_Cobranza	  */
	public void setUY_Vincular_Cobranza (String UY_Vincular_Cobranza);

	/** Get UY_Vincular_Cobranza	  */
	public String getUY_Vincular_Cobranza();

    /** Column name UY_Vincular_DevDirectas */
    public static final String COLUMNNAME_UY_Vincular_DevDirectas = "UY_Vincular_DevDirectas";

	/** Set UY_Vincular_DevDirectas	  */
	public void setUY_Vincular_DevDirectas (String UY_Vincular_DevDirectas);

	/** Get UY_Vincular_DevDirectas	  */
	public String getUY_Vincular_DevDirectas();
}
