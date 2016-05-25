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

/** Generated Interface for UY_TR_Remito
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_TR_Remito 
{

    /** TableName=UY_TR_Remito */
    public static final String Table_Name = "UY_TR_Remito";

    /** AD_Table_ID=1000789 */
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

    /** Column name bultos */
    public static final String COLUMNNAME_bultos = "bultos";

	/** Set bultos	  */
	public void setbultos (BigDecimal bultos);

	/** Get bultos	  */
	public BigDecimal getbultos();

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

    /** Column name C_BPartner_Location_ID_1 */
    public static final String COLUMNNAME_C_BPartner_Location_ID_1 = "C_BPartner_Location_ID_1";

	/** Set C_BPartner_Location_ID_1	  */
	public void setC_BPartner_Location_ID_1 (int C_BPartner_Location_ID_1);

	/** Get C_BPartner_Location_ID_1	  */
	public int getC_BPartner_Location_ID_1();

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

    /** Column name Destino */
    public static final String COLUMNNAME_Destino = "Destino";

	/** Set Destino	  */
	public void setDestino (String Destino);

	/** Get Destino	  */
	public String getDestino();

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

    /** Column name ExecuteAction */
    public static final String COLUMNNAME_ExecuteAction = "ExecuteAction";

	/** Set ExecuteAction	  */
	public void setExecuteAction (String ExecuteAction);

	/** Get ExecuteAction	  */
	public String getExecuteAction();

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

    /** Column name Origen */
    public static final String COLUMNNAME_Origen = "Origen";

	/** Set Origen	  */
	public void setOrigen (String Origen);

	/** Get Origen	  */
	public String getOrigen();

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

    /** Column name UY_TR_Crt_ID */
    public static final String COLUMNNAME_UY_TR_Crt_ID = "UY_TR_Crt_ID";

	/** Set UY_TR_Crt	  */
	public void setUY_TR_Crt_ID (int UY_TR_Crt_ID);

	/** Get UY_TR_Crt	  */
	public int getUY_TR_Crt_ID();

	public I_UY_TR_Crt getUY_TR_Crt() throws RuntimeException;

    /** Column name UY_TR_Driver_ID */
    public static final String COLUMNNAME_UY_TR_Driver_ID = "UY_TR_Driver_ID";

	/** Set UY_TR_Driver	  */
	public void setUY_TR_Driver_ID (int UY_TR_Driver_ID);

	/** Get UY_TR_Driver	  */
	public int getUY_TR_Driver_ID();

	public I_UY_TR_Driver getUY_TR_Driver() throws RuntimeException;

    /** Column name UY_TR_Remito_ID */
    public static final String COLUMNNAME_UY_TR_Remito_ID = "UY_TR_Remito_ID";

	/** Set UY_TR_Remito	  */
	public void setUY_TR_Remito_ID (int UY_TR_Remito_ID);

	/** Get UY_TR_Remito	  */
	public int getUY_TR_Remito_ID();

    /** Column name UY_TR_TransOrder_ID */
    public static final String COLUMNNAME_UY_TR_TransOrder_ID = "UY_TR_TransOrder_ID";

	/** Set UY_TR_TransOrder	  */
	public void setUY_TR_TransOrder_ID (int UY_TR_TransOrder_ID);

	/** Get UY_TR_TransOrder	  */
	public int getUY_TR_TransOrder_ID();

	public I_UY_TR_TransOrder getUY_TR_TransOrder() throws RuntimeException;

    /** Column name UY_TR_TransOrderLine_ID */
    public static final String COLUMNNAME_UY_TR_TransOrderLine_ID = "UY_TR_TransOrderLine_ID";

	/** Set UY_TR_TransOrderLine	  */
	public void setUY_TR_TransOrderLine_ID (int UY_TR_TransOrderLine_ID);

	/** Get UY_TR_TransOrderLine	  */
	public int getUY_TR_TransOrderLine_ID();

	public I_UY_TR_TransOrderLine getUY_TR_TransOrderLine() throws RuntimeException;

    /** Column name UY_TR_Trip_ID */
    public static final String COLUMNNAME_UY_TR_Trip_ID = "UY_TR_Trip_ID";

	/** Set UY_TR_Trip	  */
	public void setUY_TR_Trip_ID (int UY_TR_Trip_ID);

	/** Get UY_TR_Trip	  */
	public int getUY_TR_Trip_ID();

	public I_UY_TR_Trip getUY_TR_Trip() throws RuntimeException;

    /** Column name UY_TR_Truck_ID */
    public static final String COLUMNNAME_UY_TR_Truck_ID = "UY_TR_Truck_ID";

	/** Set UY_TR_Truck	  */
	public void setUY_TR_Truck_ID (int UY_TR_Truck_ID);

	/** Get UY_TR_Truck	  */
	public int getUY_TR_Truck_ID();

	public I_UY_TR_Truck getUY_TR_Truck() throws RuntimeException;

    /** Column name UY_TR_Truck_ID_Aux */
    public static final String COLUMNNAME_UY_TR_Truck_ID_Aux = "UY_TR_Truck_ID_Aux";

	/** Set UY_TR_Truck_ID_Aux	  */
	public void setUY_TR_Truck_ID_Aux (int UY_TR_Truck_ID_Aux);

	/** Get UY_TR_Truck_ID_Aux	  */
	public int getUY_TR_Truck_ID_Aux();

    /** Column name Weight */
    public static final String COLUMNNAME_Weight = "Weight";

	/** Set Weight.
	  * Weight of a product
	  */
	public void setWeight (BigDecimal Weight);

	/** Get Weight.
	  * Weight of a product
	  */
	public BigDecimal getWeight();

    /** Column name Weight2 */
    public static final String COLUMNNAME_Weight2 = "Weight2";

	/** Set Weight2	  */
	public void setWeight2 (BigDecimal Weight2);

	/** Get Weight2	  */
	public BigDecimal getWeight2();
}
