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

/** Generated Interface for UY_TR_Trip
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_TR_Trip 
{

    /** TableName=UY_TR_Trip */
    public static final String Table_Name = "UY_TR_Trip";

    /** AD_Table_ID=1000753 */
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

    /** Column name Amount */
    public static final String COLUMNNAME_Amount = "Amount";

	/** Set Amount.
	  * Amount in a defined currency
	  */
	public void setAmount (BigDecimal Amount);

	/** Get Amount.
	  * Amount in a defined currency
	  */
	public BigDecimal getAmount();

    /** Column name C_BPartner_ID_Aux */
    public static final String COLUMNNAME_C_BPartner_ID_Aux = "C_BPartner_ID_Aux";

	/** Set C_BPartner_ID_Aux	  */
	public void setC_BPartner_ID_Aux (int C_BPartner_ID_Aux);

	/** Get C_BPartner_ID_Aux	  */
	public int getC_BPartner_ID_Aux();

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

    /** Column name C_BPartner_Location_ID_2 */
    public static final String COLUMNNAME_C_BPartner_Location_ID_2 = "C_BPartner_Location_ID_2";

	/** Set C_BPartner_Location_ID_2	  */
	public void setC_BPartner_Location_ID_2 (int C_BPartner_Location_ID_2);

	/** Get C_BPartner_Location_ID_2	  */
	public int getC_BPartner_Location_ID_2();

    /** Column name C_BPartner_Location_ID_3 */
    public static final String COLUMNNAME_C_BPartner_Location_ID_3 = "C_BPartner_Location_ID_3";

	/** Set C_BPartner_Location_ID_3	  */
	public void setC_BPartner_Location_ID_3 (int C_BPartner_Location_ID_3);

	/** Get C_BPartner_Location_ID_3	  */
	public int getC_BPartner_Location_ID_3();

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

    /** Column name C_Invoice_ID */
    public static final String COLUMNNAME_C_Invoice_ID = "C_Invoice_ID";

	/** Set Invoice.
	  * Invoice Identifier
	  */
	public void setC_Invoice_ID (int C_Invoice_ID);

	/** Get Invoice.
	  * Invoice Identifier
	  */
	public int getC_Invoice_ID();

	public org.compiere.model.I_C_Invoice getC_Invoice() throws RuntimeException;

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

    /** Column name DateDUA */
    public static final String COLUMNNAME_DateDUA = "DateDUA";

	/** Set DateDUA	  */
	public void setDateDUA (Timestamp DateDUA);

	/** Get DateDUA	  */
	public Timestamp getDateDUA();

    /** Column name DatePromised */
    public static final String COLUMNNAME_DatePromised = "DatePromised";

	/** Set Date Promised.
	  * Date Order was promised
	  */
	public void setDatePromised (Timestamp DatePromised);

	/** Get Date Promised.
	  * Date Order was promised
	  */
	public Timestamp getDatePromised();

    /** Column name DateShipment */
    public static final String COLUMNNAME_DateShipment = "DateShipment";

	/** Set DateShipment	  */
	public void setDateShipment (Timestamp DateShipment);

	/** Get DateShipment	  */
	public Timestamp getDateShipment();

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

    /** Column name DateUnload */
    public static final String COLUMNNAME_DateUnload = "DateUnload";

	/** Set DateUnload	  */
	public void setDateUnload (Timestamp DateUnload);

	/** Get DateUnload	  */
	public Timestamp getDateUnload();

    /** Column name DeclarationType */
    public static final String COLUMNNAME_DeclarationType = "DeclarationType";

	/** Set DeclarationType	  */
	public void setDeclarationType (String DeclarationType);

	/** Get DeclarationType	  */
	public String getDeclarationType();

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

    /** Column name Despachante */
    public static final String COLUMNNAME_Despachante = "Despachante";

	/** Set Despachante	  */
	public void setDespachante (String Despachante);

	/** Get Despachante	  */
	public String getDespachante();

    /** Column name Despachante2 */
    public static final String COLUMNNAME_Despachante2 = "Despachante2";

	/** Set Despachante2	  */
	public void setDespachante2 (String Despachante2);

	/** Get Despachante2	  */
	public String getDespachante2();

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

    /** Column name DuaNo */
    public static final String COLUMNNAME_DuaNo = "DuaNo";

	/** Set DuaNo	  */
	public void setDuaNo (String DuaNo);

	/** Get DuaNo	  */
	public String getDuaNo();

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

    /** Column name ExecuteAction3 */
    public static final String COLUMNNAME_ExecuteAction3 = "ExecuteAction3";

	/** Set ExecuteAction3	  */
	public void setExecuteAction3 (String ExecuteAction3);

	/** Get ExecuteAction3	  */
	public String getExecuteAction3();

    /** Column name Frame1 */
    public static final String COLUMNNAME_Frame1 = "Frame1";

	/** Set Frame1	  */
	public void setFrame1 (boolean Frame1);

	/** Get Frame1	  */
	public boolean isFrame1();

    /** Column name Frame2 */
    public static final String COLUMNNAME_Frame2 = "Frame2";

	/** Set Frame2	  */
	public void setFrame2 (boolean Frame2);

	/** Get Frame2	  */
	public boolean isFrame2();

    /** Column name Frame3 */
    public static final String COLUMNNAME_Frame3 = "Frame3";

	/** Set Frame3	  */
	public void setFrame3 (boolean Frame3);

	/** Get Frame3	  */
	public boolean isFrame3();

    /** Column name Frame4 */
    public static final String COLUMNNAME_Frame4 = "Frame4";

	/** Set Frame4	  */
	public void setFrame4 (boolean Frame4);

	/** Get Frame4	  */
	public boolean isFrame4();

    /** Column name Frame5 */
    public static final String COLUMNNAME_Frame5 = "Frame5";

	/** Set Frame5	  */
	public void setFrame5 (boolean Frame5);

	/** Get Frame5	  */
	public boolean isFrame5();

    /** Column name Frame6 */
    public static final String COLUMNNAME_Frame6 = "Frame6";

	/** Set Frame6	  */
	public void setFrame6 (boolean Frame6);

	/** Get Frame6	  */
	public boolean isFrame6();

    /** Column name Identificator */
    public static final String COLUMNNAME_Identificator = "Identificator";

	/** Set Identificator.
	  * Identificator
	  */
	public void setIdentificator (String Identificator);

	/** Get Identificator.
	  * Identificator
	  */
	public String getIdentificator();

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

    /** Column name IsConsolidated */
    public static final String COLUMNNAME_IsConsolidated = "IsConsolidated";

	/** Set IsConsolidated	  */
	public void setIsConsolidated (boolean IsConsolidated);

	/** Get IsConsolidated	  */
	public boolean isConsolidated();

    /** Column name IsDangerous */
    public static final String COLUMNNAME_IsDangerous = "IsDangerous";

	/** Set IsDangerous	  */
	public void setIsDangerous (boolean IsDangerous);

	/** Get IsDangerous	  */
	public boolean isDangerous();

    /** Column name IsLoad */
    public static final String COLUMNNAME_IsLoad = "IsLoad";

	/** Set IsLoad	  */
	public void setIsLoad (boolean IsLoad);

	/** Get IsLoad	  */
	public boolean isLoad();

    /** Column name IsRepresentation */
    public static final String COLUMNNAME_IsRepresentation = "IsRepresentation";

	/** Set IsRepresentation	  */
	public void setIsRepresentation (boolean IsRepresentation);

	/** Get IsRepresentation	  */
	public boolean isRepresentation();

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

    /** Column name Numero */
    public static final String COLUMNNAME_Numero = "Numero";

	/** Set Numero	  */
	public void setNumero (String Numero);

	/** Get Numero	  */
	public String getNumero();

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

    /** Column name Observaciones3 */
    public static final String COLUMNNAME_Observaciones3 = "Observaciones3";

	/** Set Observaciones3	  */
	public void setObservaciones3 (String Observaciones3);

	/** Get Observaciones3	  */
	public String getObservaciones3();

    /** Column name Observaciones4 */
    public static final String COLUMNNAME_Observaciones4 = "Observaciones4";

	/** Set Observaciones4	  */
	public void setObservaciones4 (String Observaciones4);

	/** Get Observaciones4	  */
	public String getObservaciones4();

    /** Column name OnuNO */
    public static final String COLUMNNAME_OnuNO = "OnuNO";

	/** Set OnuNO	  */
	public void setOnuNO (String OnuNO);

	/** Get OnuNO	  */
	public String getOnuNO();

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

    /** Column name ProdSource */
    public static final String COLUMNNAME_ProdSource = "ProdSource";

	/** Set ProdSource	  */
	public void setProdSource (String ProdSource);

	/** Get ProdSource	  */
	public String getProdSource();

    /** Column name ProductAmt */
    public static final String COLUMNNAME_ProductAmt = "ProductAmt";

	/** Set ProductAmt	  */
	public void setProductAmt (BigDecimal ProductAmt);

	/** Get ProductAmt	  */
	public BigDecimal getProductAmt();

    /** Column name ProductDescription */
    public static final String COLUMNNAME_ProductDescription = "ProductDescription";

	/** Set Product Description.
	  * Product Description
	  */
	public void setProductDescription (String ProductDescription);

	/** Get Product Description.
	  * Product Description
	  */
	public String getProductDescription();

    /** Column name ProductType */
    public static final String COLUMNNAME_ProductType = "ProductType";

	/** Set Product Type.
	  * Type of product
	  */
	public void setProductType (String ProductType);

	/** Get Product Type.
	  * Type of product
	  */
	public String getProductType();

    /** Column name QtyPackage */
    public static final String COLUMNNAME_QtyPackage = "QtyPackage";

	/** Set QtyPackage	  */
	public void setQtyPackage (BigDecimal QtyPackage);

	/** Get QtyPackage	  */
	public BigDecimal getQtyPackage();

    /** Column name ReceiptMode */
    public static final String COLUMNNAME_ReceiptMode = "ReceiptMode";

	/** Set ReceiptMode	  */
	public void setReceiptMode (String ReceiptMode);

	/** Get ReceiptMode	  */
	public String getReceiptMode();

    /** Column name ReferenceNo */
    public static final String COLUMNNAME_ReferenceNo = "ReferenceNo";

	/** Set Reference No.
	  * Your customer or vendor number at the Business Partner's site
	  */
	public void setReferenceNo (String ReferenceNo);

	/** Get Reference No.
	  * Your customer or vendor number at the Business Partner's site
	  */
	public String getReferenceNo();

    /** Column name Representado_ID */
    public static final String COLUMNNAME_Representado_ID = "Representado_ID";

	/** Set Representado_ID	  */
	public void setRepresentado_ID (int Representado_ID);

	/** Get Representado_ID	  */
	public int getRepresentado_ID();

    /** Column name Representante */
    public static final String COLUMNNAME_Representante = "Representante";

	/** Set Representante	  */
	public void setRepresentante (String Representante);

	/** Get Representante	  */
	public String getRepresentante();

    /** Column name Satisfied */
    public static final String COLUMNNAME_Satisfied = "Satisfied";

	/** Set Satisfied	  */
	public void setSatisfied (boolean Satisfied);

	/** Get Satisfied	  */
	public boolean isSatisfied();

    /** Column name TransitType */
    public static final String COLUMNNAME_TransitType = "TransitType";

	/** Set TransitType	  */
	public void setTransitType (String TransitType);

	/** Get TransitType	  */
	public String getTransitType();

    /** Column name TripType */
    public static final String COLUMNNAME_TripType = "TripType";

	/** Set TripType	  */
	public void setTripType (String TripType);

	/** Get TripType	  */
	public String getTripType();

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

	public I_UY_TR_Budget getUY_TR_Budget() throws RuntimeException;

    /** Column name UY_TR_BudgetLine_ID */
    public static final String COLUMNNAME_UY_TR_BudgetLine_ID = "UY_TR_BudgetLine_ID";

	/** Set UY_TR_BudgetLine	  */
	public void setUY_TR_BudgetLine_ID (int UY_TR_BudgetLine_ID);

	/** Get UY_TR_BudgetLine	  */
	public int getUY_TR_BudgetLine_ID();

    /** Column name UY_TR_Despachante_ID */
    public static final String COLUMNNAME_UY_TR_Despachante_ID = "UY_TR_Despachante_ID";

	/** Set UY_TR_Despachante	  */
	public void setUY_TR_Despachante_ID (int UY_TR_Despachante_ID);

	/** Get UY_TR_Despachante	  */
	public int getUY_TR_Despachante_ID();

	public I_UY_TR_Despachante getUY_TR_Despachante() throws RuntimeException;

    /** Column name UY_TR_Despachante_ID_1 */
    public static final String COLUMNNAME_UY_TR_Despachante_ID_1 = "UY_TR_Despachante_ID_1";

	/** Set UY_TR_Despachante_ID_1	  */
	public void setUY_TR_Despachante_ID_1 (int UY_TR_Despachante_ID_1);

	/** Get UY_TR_Despachante_ID_1	  */
	public int getUY_TR_Despachante_ID_1();

	public I_UY_TR_Despachante getUY_TR_Despachante_I() throws RuntimeException;

    /** Column name UY_TR_Despachante_ID_2 */
    public static final String COLUMNNAME_UY_TR_Despachante_ID_2 = "UY_TR_Despachante_ID_2";

	/** Set UY_TR_Despachante_ID_2	  */
	public void setUY_TR_Despachante_ID_2 (int UY_TR_Despachante_ID_2);

	/** Get UY_TR_Despachante_ID_2	  */
	public int getUY_TR_Despachante_ID_2();

    /** Column name UY_TR_Despachante_ID_3 */
    public static final String COLUMNNAME_UY_TR_Despachante_ID_3 = "UY_TR_Despachante_ID_3";

	/** Set UY_TR_Despachante_ID_3	  */
	public void setUY_TR_Despachante_ID_3 (int UY_TR_Despachante_ID_3);

	/** Get UY_TR_Despachante_ID_3	  */
	public int getUY_TR_Despachante_ID_3();

    /** Column name UY_TR_PackageType_ID */
    public static final String COLUMNNAME_UY_TR_PackageType_ID = "UY_TR_PackageType_ID";

	/** Set UY_TR_PackageType	  */
	public void setUY_TR_PackageType_ID (int UY_TR_PackageType_ID);

	/** Get UY_TR_PackageType	  */
	public int getUY_TR_PackageType_ID();

	public I_UY_TR_PackageType getUY_TR_PackageType() throws RuntimeException;

    /** Column name UY_TR_Representante_ID */
    public static final String COLUMNNAME_UY_TR_Representante_ID = "UY_TR_Representante_ID";

	/** Set UY_TR_Representante	  */
	public void setUY_TR_Representante_ID (int UY_TR_Representante_ID);

	/** Get UY_TR_Representante	  */
	public int getUY_TR_Representante_ID();

    /** Column name UY_TR_Representante_ID_1 */
    public static final String COLUMNNAME_UY_TR_Representante_ID_1 = "UY_TR_Representante_ID_1";

	/** Set UY_TR_Representante_ID_1	  */
	public void setUY_TR_Representante_ID_1 (int UY_TR_Representante_ID_1);

	/** Get UY_TR_Representante_ID_1	  */
	public int getUY_TR_Representante_ID_1();

    /** Column name UY_TR_Trip_ID */
    public static final String COLUMNNAME_UY_TR_Trip_ID = "UY_TR_Trip_ID";

	/** Set UY_TR_Trip	  */
	public void setUY_TR_Trip_ID (int UY_TR_Trip_ID);

	/** Get UY_TR_Trip	  */
	public int getUY_TR_Trip_ID();

    /** Column name UY_TR_Way_ID */
    public static final String COLUMNNAME_UY_TR_Way_ID = "UY_TR_Way_ID";

	/** Set UY_TR_Way	  */
	public void setUY_TR_Way_ID (int UY_TR_Way_ID);

	/** Get UY_TR_Way	  */
	public int getUY_TR_Way_ID();

	public I_UY_TR_Way getUY_TR_Way() throws RuntimeException;

    /** Column name Volume */
    public static final String COLUMNNAME_Volume = "Volume";

	/** Set Volume.
	  * Volume of a product
	  */
	public void setVolume (BigDecimal Volume);

	/** Get Volume.
	  * Volume of a product
	  */
	public BigDecimal getVolume();

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
