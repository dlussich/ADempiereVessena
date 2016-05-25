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

/** Generated Interface for UY_TR_LoadMonitor
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_TR_LoadMonitor 
{

    /** TableName=UY_TR_LoadMonitor */
    public static final String Table_Name = "UY_TR_LoadMonitor";

    /** AD_Table_ID=1000834 */
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

    /** Column name C_Currency_ID_2 */
    public static final String COLUMNNAME_C_Currency_ID_2 = "C_Currency_ID_2";

	/** Set C_Currency_ID_2	  */
	public void setC_Currency_ID_2 (int C_Currency_ID_2);

	/** Get C_Currency_ID_2	  */
	public int getC_Currency_ID_2();

    /** Column name C_Currency_ID_3 */
    public static final String COLUMNNAME_C_Currency_ID_3 = "C_Currency_ID_3";

	/** Set C_Currency_ID_3	  */
	public void setC_Currency_ID_3 (int C_Currency_ID_3);

	/** Get C_Currency_ID_3	  */
	public int getC_Currency_ID_3();

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

    /** Column name ChangeDriver */
    public static final String COLUMNNAME_ChangeDriver = "ChangeDriver";

	/** Set ChangeDriver	  */
	public void setChangeDriver (boolean ChangeDriver);

	/** Get ChangeDriver	  */
	public boolean isChangeDriver();

    /** Column name ChangeTruck */
    public static final String COLUMNNAME_ChangeTruck = "ChangeTruck";

	/** Set ChangeTruck	  */
	public void setChangeTruck (boolean ChangeTruck);

	/** Get ChangeTruck	  */
	public boolean isChangeTruck();

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

    /** Column name HandleCRT */
    public static final String COLUMNNAME_HandleCRT = "HandleCRT";

	/** Set HandleCRT	  */
	public void setHandleCRT (boolean HandleCRT);

	/** Get HandleCRT	  */
	public boolean isHandleCRT();

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

    /** Column name IsAssociated */
    public static final String COLUMNNAME_IsAssociated = "IsAssociated";

	/** Set IsAssociated.
	  * IsAssociated
	  */
	public void setIsAssociated (boolean IsAssociated);

	/** Get IsAssociated.
	  * IsAssociated
	  */
	public boolean isAssociated();

    /** Column name IsAssociated2 */
    public static final String COLUMNNAME_IsAssociated2 = "IsAssociated2";

	/** Set IsAssociated2	  */
	public void setIsAssociated2 (boolean IsAssociated2);

	/** Get IsAssociated2	  */
	public boolean isAssociated2();

    /** Column name IsBeforeLoad */
    public static final String COLUMNNAME_IsBeforeLoad = "IsBeforeLoad";

	/** Set IsBeforeLoad	  */
	public void setIsBeforeLoad (boolean IsBeforeLoad);

	/** Get IsBeforeLoad	  */
	public boolean isBeforeLoad();

    /** Column name IsConfirmation */
    public static final String COLUMNNAME_IsConfirmation = "IsConfirmation";

	/** Set IsConfirmation	  */
	public void setIsConfirmation (boolean IsConfirmation);

	/** Get IsConfirmation	  */
	public boolean isConfirmation();

    /** Column name IsDelivered */
    public static final String COLUMNNAME_IsDelivered = "IsDelivered";

	/** Set Delivered	  */
	public void setIsDelivered (boolean IsDelivered);

	/** Get Delivered	  */
	public boolean isDelivered();

    /** Column name IsLastre */
    public static final String COLUMNNAME_IsLastre = "IsLastre";

	/** Set IsLastre	  */
	public void setIsLastre (boolean IsLastre);

	/** Get IsLastre	  */
	public boolean isLastre();

    /** Column name IsOwn */
    public static final String COLUMNNAME_IsOwn = "IsOwn";

	/** Set IsOwn	  */
	public void setIsOwn (boolean IsOwn);

	/** Get IsOwn	  */
	public boolean isOwn();

    /** Column name IsOwn2 */
    public static final String COLUMNNAME_IsOwn2 = "IsOwn2";

	/** Set IsOwn2	  */
	public void setIsOwn2 (boolean IsOwn2);

	/** Get IsOwn2	  */
	public boolean isOwn2();

    /** Column name IsPlanification */
    public static final String COLUMNNAME_IsPlanification = "IsPlanification";

	/** Set IsPlanification	  */
	public void setIsPlanification (boolean IsPlanification);

	/** Get IsPlanification	  */
	public boolean isPlanification();

    /** Column name IsTrasbordo */
    public static final String COLUMNNAME_IsTrasbordo = "IsTrasbordo";

	/** Set IsTrasbordo	  */
	public void setIsTrasbordo (boolean IsTrasbordo);

	/** Get IsTrasbordo	  */
	public boolean isTrasbordo();

    /** Column name IsWarehouseRequired */
    public static final String COLUMNNAME_IsWarehouseRequired = "IsWarehouseRequired";

	/** Set IsWarehouseRequired.
	  * IsWarehouseRequired
	  */
	public void setIsWarehouseRequired (boolean IsWarehouseRequired);

	/** Get IsWarehouseRequired.
	  * IsWarehouseRequired
	  */
	public boolean isWarehouseRequired();

    /** Column name LoadProduct */
    public static final String COLUMNNAME_LoadProduct = "LoadProduct";

	/** Set LoadProduct	  */
	public void setLoadProduct (boolean LoadProduct);

	/** Get LoadProduct	  */
	public boolean isLoadProduct();

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

    /** Column name OnMove */
    public static final String COLUMNNAME_OnMove = "OnMove";

	/** Set OnMove.
	  * OnMove
	  */
	public void setOnMove (boolean OnMove);

	/** Get OnMove.
	  * OnMove
	  */
	public boolean isOnMove();

    /** Column name PayAmt */
    public static final String COLUMNNAME_PayAmt = "PayAmt";

	/** Set Payment amount.
	  * Amount being paid
	  */
	public void setPayAmt (BigDecimal PayAmt);

	/** Get Payment amount.
	  * Amount being paid
	  */
	public BigDecimal getPayAmt();

    /** Column name PayAmt2 */
    public static final String COLUMNNAME_PayAmt2 = "PayAmt2";

	/** Set PayAmt2	  */
	public void setPayAmt2 (BigDecimal PayAmt2);

	/** Get PayAmt2	  */
	public BigDecimal getPayAmt2();

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

    /** Column name Remolque_ID */
    public static final String COLUMNNAME_Remolque_ID = "Remolque_ID";

	/** Set Remolque_ID.
	  * Remolque_ID
	  */
	public void setRemolque_ID (int Remolque_ID);

	/** Get Remolque_ID.
	  * Remolque_ID
	  */
	public int getRemolque_ID();

    /** Column name Remolque_ID_2 */
    public static final String COLUMNNAME_Remolque_ID_2 = "Remolque_ID_2";

	/** Set Remolque_ID_2	  */
	public void setRemolque_ID_2 (int Remolque_ID_2);

	/** Get Remolque_ID_2	  */
	public int getRemolque_ID_2();

    /** Column name TotalAmt */
    public static final String COLUMNNAME_TotalAmt = "TotalAmt";

	/** Set Total Amount.
	  * Total Amount
	  */
	public void setTotalAmt (BigDecimal TotalAmt);

	/** Get Total Amount.
	  * Total Amount
	  */
	public BigDecimal getTotalAmt();

    /** Column name TotalWeight */
    public static final String COLUMNNAME_TotalWeight = "TotalWeight";

	/** Set TotalWeight	  */
	public void setTotalWeight (BigDecimal TotalWeight);

	/** Get TotalWeight	  */
	public BigDecimal getTotalWeight();

    /** Column name TotalWeight2 */
    public static final String COLUMNNAME_TotalWeight2 = "TotalWeight2";

	/** Set TotalWeight2	  */
	public void setTotalWeight2 (BigDecimal TotalWeight2);

	/** Get TotalWeight2	  */
	public BigDecimal getTotalWeight2();

    /** Column name Tractor_ID */
    public static final String COLUMNNAME_Tractor_ID = "Tractor_ID";

	/** Set Tractor_ID	  */
	public void setTractor_ID (int Tractor_ID);

	/** Get Tractor_ID	  */
	public int getTractor_ID();

    /** Column name Tractor_ID_2 */
    public static final String COLUMNNAME_Tractor_ID_2 = "Tractor_ID_2";

	/** Set Tractor_ID_2	  */
	public void setTractor_ID_2 (int Tractor_ID_2);

	/** Get Tractor_ID_2	  */
	public int getTractor_ID_2();

    /** Column name TypeLoad */
    public static final String COLUMNNAME_TypeLoad = "TypeLoad";

	/** Set TypeLoad	  */
	public void setTypeLoad (String TypeLoad);

	/** Get TypeLoad	  */
	public String getTypeLoad();

    /** Column name UnloadProduct */
    public static final String COLUMNNAME_UnloadProduct = "UnloadProduct";

	/** Set UnloadProduct.
	  * UnloadProduct
	  */
	public void setUnloadProduct (boolean UnloadProduct);

	/** Get UnloadProduct.
	  * UnloadProduct
	  */
	public boolean isUnloadProduct();

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

    /** Column name UY_Ciudad_ID */
    public static final String COLUMNNAME_UY_Ciudad_ID = "UY_Ciudad_ID";

	/** Set UY_Ciudad	  */
	public void setUY_Ciudad_ID (int UY_Ciudad_ID);

	/** Get UY_Ciudad	  */
	public int getUY_Ciudad_ID();

	public I_UY_Ciudad getUY_Ciudad() throws RuntimeException;

    /** Column name UY_Ciudad_ID_1 */
    public static final String COLUMNNAME_UY_Ciudad_ID_1 = "UY_Ciudad_ID_1";

	/** Set UY_Ciudad_ID_1	  */
	public void setUY_Ciudad_ID_1 (int UY_Ciudad_ID_1);

	/** Get UY_Ciudad_ID_1	  */
	public int getUY_Ciudad_ID_1();

    /** Column name UY_Ciudad_ID_2 */
    public static final String COLUMNNAME_UY_Ciudad_ID_2 = "UY_Ciudad_ID_2";

	/** Set UY_Ciudad_ID_2	  */
	public void setUY_Ciudad_ID_2 (int UY_Ciudad_ID_2);

	/** Get UY_Ciudad_ID_2	  */
	public int getUY_Ciudad_ID_2();

    /** Column name UY_Ciudad_ID_3 */
    public static final String COLUMNNAME_UY_Ciudad_ID_3 = "UY_Ciudad_ID_3";

	/** Set UY_Ciudad_ID_3	  */
	public void setUY_Ciudad_ID_3 (int UY_Ciudad_ID_3);

	/** Get UY_Ciudad_ID_3	  */
	public int getUY_Ciudad_ID_3();

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

    /** Column name UY_TR_Driver_ID_2 */
    public static final String COLUMNNAME_UY_TR_Driver_ID_2 = "UY_TR_Driver_ID_2";

	/** Set UY_TR_Driver_ID_2	  */
	public void setUY_TR_Driver_ID_2 (int UY_TR_Driver_ID_2);

	/** Get UY_TR_Driver_ID_2	  */
	public int getUY_TR_Driver_ID_2();

    /** Column name UY_TR_LoadMonitor_ID */
    public static final String COLUMNNAME_UY_TR_LoadMonitor_ID = "UY_TR_LoadMonitor_ID";

	/** Set UY_TR_LoadMonitor	  */
	public void setUY_TR_LoadMonitor_ID (int UY_TR_LoadMonitor_ID);

	/** Get UY_TR_LoadMonitor	  */
	public int getUY_TR_LoadMonitor_ID();

    /** Column name UY_TR_LoadStatus_ID */
    public static final String COLUMNNAME_UY_TR_LoadStatus_ID = "UY_TR_LoadStatus_ID";

	/** Set UY_TR_LoadStatus	  */
	public void setUY_TR_LoadStatus_ID (int UY_TR_LoadStatus_ID);

	/** Get UY_TR_LoadStatus	  */
	public int getUY_TR_LoadStatus_ID();

	public I_UY_TR_LoadStatus getUY_TR_LoadStatus() throws RuntimeException;

    /** Column name UY_TR_TransOrder_ID */
    public static final String COLUMNNAME_UY_TR_TransOrder_ID = "UY_TR_TransOrder_ID";

	/** Set UY_TR_TransOrder	  */
	public void setUY_TR_TransOrder_ID (int UY_TR_TransOrder_ID);

	/** Get UY_TR_TransOrder	  */
	public int getUY_TR_TransOrder_ID();

	public I_UY_TR_TransOrder getUY_TR_TransOrder() throws RuntimeException;

    /** Column name UY_TR_TransOrder_ID_2 */
    public static final String COLUMNNAME_UY_TR_TransOrder_ID_2 = "UY_TR_TransOrder_ID_2";

	/** Set UY_TR_TransOrder_ID_2	  */
	public void setUY_TR_TransOrder_ID_2 (int UY_TR_TransOrder_ID_2);

	/** Get UY_TR_TransOrder_ID_2	  */
	public int getUY_TR_TransOrder_ID_2();

    /** Column name UY_TR_Truck_ID */
    public static final String COLUMNNAME_UY_TR_Truck_ID = "UY_TR_Truck_ID";

	/** Set UY_TR_Truck	  */
	public void setUY_TR_Truck_ID (int UY_TR_Truck_ID);

	/** Get UY_TR_Truck	  */
	public int getUY_TR_Truck_ID();

	public I_UY_TR_Truck getUY_TR_Truck() throws RuntimeException;

    /** Column name UY_TR_Truck_ID_2 */
    public static final String COLUMNNAME_UY_TR_Truck_ID_2 = "UY_TR_Truck_ID_2";

	/** Set UY_TR_Truck_ID_2	  */
	public void setUY_TR_Truck_ID_2 (int UY_TR_Truck_ID_2);

	/** Get UY_TR_Truck_ID_2	  */
	public int getUY_TR_Truck_ID_2();

    /** Column name UY_TR_Truck_ID_3 */
    public static final String COLUMNNAME_UY_TR_Truck_ID_3 = "UY_TR_Truck_ID_3";

	/** Set UY_TR_Truck_ID_3	  */
	public void setUY_TR_Truck_ID_3 (int UY_TR_Truck_ID_3);

	/** Get UY_TR_Truck_ID_3	  */
	public int getUY_TR_Truck_ID_3();

    /** Column name UY_TR_Truck_ID_4 */
    public static final String COLUMNNAME_UY_TR_Truck_ID_4 = "UY_TR_Truck_ID_4";

	/** Set UY_TR_Truck_ID_4	  */
	public void setUY_TR_Truck_ID_4 (int UY_TR_Truck_ID_4);

	/** Get UY_TR_Truck_ID_4	  */
	public int getUY_TR_Truck_ID_4();

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

    /** Column name UY_TR_TruckType_ID_4 */
    public static final String COLUMNNAME_UY_TR_TruckType_ID_4 = "UY_TR_TruckType_ID_4";

	/** Set UY_TR_TruckType_ID_4	  */
	public void setUY_TR_TruckType_ID_4 (int UY_TR_TruckType_ID_4);

	/** Get UY_TR_TruckType_ID_4	  */
	public int getUY_TR_TruckType_ID_4();
}
