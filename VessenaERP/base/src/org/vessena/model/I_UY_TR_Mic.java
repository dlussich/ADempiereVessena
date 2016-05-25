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

/** Generated Interface for UY_TR_Mic
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_TR_Mic 
{

    /** TableName=UY_TR_Mic */
    public static final String Table_Name = "UY_TR_Mic";

    /** AD_Table_ID=1000777 */
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

    /** Column name AduanaDestino */
    public static final String COLUMNNAME_AduanaDestino = "AduanaDestino";

	/** Set AduanaDestino	  */
	public void setAduanaDestino (String AduanaDestino);

	/** Get AduanaDestino	  */
	public String getAduanaDestino();

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

    /** Column name C_Country_ID */
    public static final String COLUMNNAME_C_Country_ID = "C_Country_ID";

	/** Set Country.
	  * Country 
	  */
	public void setC_Country_ID (int C_Country_ID);

	/** Get Country.
	  * Country 
	  */
	public int getC_Country_ID();

	public org.compiere.model.I_C_Country getC_Country() throws RuntimeException;

    /** Column name C_Country_ID_1 */
    public static final String COLUMNNAME_C_Country_ID_1 = "C_Country_ID_1";

	/** Set C_Country_ID_1	  */
	public void setC_Country_ID_1 (int C_Country_ID_1);

	/** Get C_Country_ID_1	  */
	public int getC_Country_ID_1();

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

    /** Column name Consignatario */
    public static final String COLUMNNAME_Consignatario = "Consignatario";

	/** Set Consignatario	  */
	public void setConsignatario (String Consignatario);

	/** Get Consignatario	  */
	public String getConsignatario();

    /** Column name ContadorPais */
    public static final String COLUMNNAME_ContadorPais = "ContadorPais";

	/** Set ContadorPais	  */
	public void setContadorPais (int ContadorPais);

	/** Get ContadorPais	  */
	public int getContadorPais();

    /** Column name Copies */
    public static final String COLUMNNAME_Copies = "Copies";

	/** Set Copies	  */
	public void setCopies (BigDecimal Copies);

	/** Get Copies	  */
	public BigDecimal getCopies();

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

    /** Column name CrtImgNum1 */
    public static final String COLUMNNAME_CrtImgNum1 = "CrtImgNum1";

	/** Set CrtImgNum1	  */
	public void setCrtImgNum1 (String CrtImgNum1);

	/** Get CrtImgNum1	  */
	public String getCrtImgNum1();

    /** Column name CrtImgStatus1 */
    public static final String COLUMNNAME_CrtImgStatus1 = "CrtImgStatus1";

	/** Set CrtImgStatus1	  */
	public void setCrtImgStatus1 (String CrtImgStatus1);

	/** Get CrtImgStatus1	  */
	public String getCrtImgStatus1();

    /** Column name CrtLineStatus1 */
    public static final String COLUMNNAME_CrtLineStatus1 = "CrtLineStatus1";

	/** Set CrtLineStatus1	  */
	public void setCrtLineStatus1 (String CrtLineStatus1);

	/** Get CrtLineStatus1	  */
	public String getCrtLineStatus1();

    /** Column name CrtStatus1 */
    public static final String COLUMNNAME_CrtStatus1 = "CrtStatus1";

	/** Set CrtStatus1	  */
	public void setCrtStatus1 (String CrtStatus1);

	/** Get CrtStatus1	  */
	public String getCrtStatus1();

    /** Column name DateAction */
    public static final String COLUMNNAME_DateAction = "DateAction";

	/** Set DateAction	  */
	public void setDateAction (Timestamp DateAction);

	/** Get DateAction	  */
	public Timestamp getDateAction();

    /** Column name DateFinish */
    public static final String COLUMNNAME_DateFinish = "DateFinish";

	/** Set Finish Date.
	  * Finish or (planned) completion date
	  */
	public void setDateFinish (Timestamp DateFinish);

	/** Get Finish Date.
	  * Finish or (planned) completion date
	  */
	public Timestamp getDateFinish();

    /** Column name DateLast */
    public static final String COLUMNNAME_DateLast = "DateLast";

	/** Set DateLast	  */
	public void setDateLast (Timestamp DateLast);

	/** Get DateLast	  */
	public Timestamp getDateLast();

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

    /** Column name DeliveryRule */
    public static final String COLUMNNAME_DeliveryRule = "DeliveryRule";

	/** Set Delivery Rule.
	  * Defines the timing of Delivery
	  */
	public void setDeliveryRule (String DeliveryRule);

	/** Get Delivery Rule.
	  * Defines the timing of Delivery
	  */
	public String getDeliveryRule();

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

    /** Column name Destinatario */
    public static final String COLUMNNAME_Destinatario = "Destinatario";

	/** Set Destinatario	  */
	public void setDestinatario (String Destinatario);

	/** Get Destinatario	  */
	public String getDestinatario();

    /** Column name Diference */
    public static final String COLUMNNAME_Diference = "Diference";

	/** Set Diference	  */
	public void setDiference (int Diference);

	/** Get Diference	  */
	public int getDiference();

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

    /** Column name DUNS */
    public static final String COLUMNNAME_DUNS = "DUNS";

	/** Set D-U-N-S.
	  * Dun & Bradstreet Number
	  */
	public void setDUNS (String DUNS);

	/** Get D-U-N-S.
	  * Dun & Bradstreet Number
	  */
	public String getDUNS();

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

    /** Column name ExecuteAction4 */
    public static final String COLUMNNAME_ExecuteAction4 = "ExecuteAction4";

	/** Set ExecuteAction4	  */
	public void setExecuteAction4 (String ExecuteAction4);

	/** Get ExecuteAction4	  */
	public String getExecuteAction4();

    /** Column name Importe */
    public static final String COLUMNNAME_Importe = "Importe";

	/** Set Importe	  */
	public void setImporte (BigDecimal Importe);

	/** Get Importe	  */
	public BigDecimal getImporte();

    /** Column name InProcess */
    public static final String COLUMNNAME_InProcess = "InProcess";

	/** Set InProcess	  */
	public void setInProcess (boolean InProcess);

	/** Get InProcess	  */
	public boolean isInProcess();

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

    /** Column name IsDrop */
    public static final String COLUMNNAME_IsDrop = "IsDrop";

	/** Set IsDrop	  */
	public void setIsDrop (boolean IsDrop);

	/** Get IsDrop	  */
	public boolean isDrop();

    /** Column name IsLastre */
    public static final String COLUMNNAME_IsLastre = "IsLastre";

	/** Set IsLastre	  */
	public void setIsLastre (boolean IsLastre);

	/** Get IsLastre	  */
	public boolean isLastre();

    /** Column name IsManual */
    public static final String COLUMNNAME_IsManual = "IsManual";

	/** Set Manual.
	  * This is a manual process
	  */
	public void setIsManual (boolean IsManual);

	/** Get Manual.
	  * This is a manual process
	  */
	public boolean isManual();

    /** Column name IsRemolque */
    public static final String COLUMNNAME_IsRemolque = "IsRemolque";

	/** Set IsRemolque	  */
	public void setIsRemolque (boolean IsRemolque);

	/** Get IsRemolque	  */
	public boolean isRemolque();

    /** Column name IsRemolque2 */
    public static final String COLUMNNAME_IsRemolque2 = "IsRemolque2";

	/** Set IsRemolque2	  */
	public void setIsRemolque2 (boolean IsRemolque2);

	/** Get IsRemolque2	  */
	public boolean isRemolque2();

    /** Column name IsSemiRemolque */
    public static final String COLUMNNAME_IsSemiRemolque = "IsSemiRemolque";

	/** Set IsSemiRemolque	  */
	public void setIsSemiRemolque (boolean IsSemiRemolque);

	/** Get IsSemiRemolque	  */
	public boolean isSemiRemolque();

    /** Column name IsSemiRemolque2 */
    public static final String COLUMNNAME_IsSemiRemolque2 = "IsSemiRemolque2";

	/** Set IsSemiRemolque2	  */
	public void setIsSemiRemolque2 (boolean IsSemiRemolque2);

	/** Get IsSemiRemolque2	  */
	public boolean isSemiRemolque2();

    /** Column name IsValid */
    public static final String COLUMNNAME_IsValid = "IsValid";

	/** Set Valid.
	  * Element is valid
	  */
	public void setIsValid (boolean IsValid);

	/** Get Valid.
	  * Element is valid
	  */
	public boolean isValid();

    /** Column name LocationComment */
    public static final String COLUMNNAME_LocationComment = "LocationComment";

	/** Set Location comment.
	  * Additional comments or remarks concerning the location
	  */
	public void setLocationComment (String LocationComment);

	/** Get Location comment.
	  * Additional comments or remarks concerning the location
	  */
	public String getLocationComment();

    /** Column name LocatorValue */
    public static final String COLUMNNAME_LocatorValue = "LocatorValue";

	/** Set Locator Key.
	  * Key of the Warehouse Locator
	  */
	public void setLocatorValue (String LocatorValue);

	/** Get Locator Key.
	  * Key of the Warehouse Locator
	  */
	public String getLocatorValue();

    /** Column name MicStatus */
    public static final String COLUMNNAME_MicStatus = "MicStatus";

	/** Set MicStatus	  */
	public void setMicStatus (String MicStatus);

	/** Get MicStatus	  */
	public String getMicStatus();

    /** Column name Mobile */
    public static final String COLUMNNAME_Mobile = "Mobile";

	/** Set Mobile	  */
	public void setMobile (String Mobile);

	/** Get Mobile	  */
	public String getMobile();

    /** Column name NroDNA */
    public static final String COLUMNNAME_NroDNA = "NroDNA";

	/** Set NroDNA	  */
	public void setNroDNA (String NroDNA);

	/** Get NroDNA	  */
	public String getNroDNA();

    /** Column name NroMic */
    public static final String COLUMNNAME_NroMic = "NroMic";

	/** Set NroMic	  */
	public void setNroMic (String NroMic);

	/** Get NroMic	  */
	public String getNroMic();

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

    /** Column name OriginalTruck */
    public static final String COLUMNNAME_OriginalTruck = "OriginalTruck";

	/** Set OriginalTruck	  */
	public void setOriginalTruck (String OriginalTruck);

	/** Get OriginalTruck	  */
	public String getOriginalTruck();

    /** Column name Permiso */
    public static final String COLUMNNAME_Permiso = "Permiso";

	/** Set Permiso	  */
	public void setPermiso (String Permiso);

	/** Get Permiso	  */
	public String getPermiso();

    /** Column name pesoBruto */
    public static final String COLUMNNAME_pesoBruto = "pesoBruto";

	/** Set pesoBruto	  */
	public void setpesoBruto (BigDecimal pesoBruto);

	/** Get pesoBruto	  */
	public BigDecimal getpesoBruto();

    /** Column name pesoNeto */
    public static final String COLUMNNAME_pesoNeto = "pesoNeto";

	/** Set pesoNeto	  */
	public void setpesoNeto (BigDecimal pesoNeto);

	/** Get pesoNeto	  */
	public BigDecimal getpesoNeto();

    /** Column name Porteador */
    public static final String COLUMNNAME_Porteador = "Porteador";

	/** Set Porteador	  */
	public void setPorteador (String Porteador);

	/** Get Porteador	  */
	public String getPorteador();

    /** Column name precinto */
    public static final String COLUMNNAME_precinto = "precinto";

	/** Set precinto	  */
	public void setprecinto (String precinto);

	/** Get precinto	  */
	public String getprecinto();

    /** Column name Precinto2 */
    public static final String COLUMNNAME_Precinto2 = "Precinto2";

	/** Set Precinto2	  */
	public void setPrecinto2 (String Precinto2);

	/** Get Precinto2	  */
	public String getPrecinto2();

    /** Column name PrintDoc */
    public static final String COLUMNNAME_PrintDoc = "PrintDoc";

	/** Set PrintDoc	  */
	public void setPrintDoc (String PrintDoc);

	/** Get PrintDoc	  */
	public String getPrintDoc();

    /** Column name PrintFormatType */
    public static final String COLUMNNAME_PrintFormatType = "PrintFormatType";

	/** Set Format Type.
	  * Print Format Type
	  */
	public void setPrintFormatType (String PrintFormatType);

	/** Get Format Type.
	  * Print Format Type
	  */
	public String getPrintFormatType();

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

    /** Column name QtyPackage */
    public static final String COLUMNNAME_QtyPackage = "QtyPackage";

	/** Set QtyPackage	  */
	public void setQtyPackage (BigDecimal QtyPackage);

	/** Get QtyPackage	  */
	public BigDecimal getQtyPackage();

    /** Column name Recinto */
    public static final String COLUMNNAME_Recinto = "Recinto";

	/** Set Recinto	  */
	public void setRecinto (String Recinto);

	/** Get Recinto	  */
	public String getRecinto();

    /** Column name Remitente */
    public static final String COLUMNNAME_Remitente = "Remitente";

	/** Set Remitente	  */
	public void setRemitente (String Remitente);

	/** Get Remitente	  */
	public String getRemitente();

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

	public I_UY_TR_Truck getRemolque() throws RuntimeException;

    /** Column name ResponseText */
    public static final String COLUMNNAME_ResponseText = "ResponseText";

	/** Set Response Text.
	  * Request Response Text
	  */
	public void setResponseText (String ResponseText);

	/** Get Response Text.
	  * Request Response Text
	  */
	public String getResponseText();

    /** Column name Result */
    public static final String COLUMNNAME_Result = "Result";

	/** Set Result.
	  * Result of the action taken
	  */
	public void setResult (String Result);

	/** Get Result.
	  * Result of the action taken
	  */
	public String getResult();

    /** Column name Rut1 */
    public static final String COLUMNNAME_Rut1 = "Rut1";

	/** Set Rut1	  */
	public void setRut1 (String Rut1);

	/** Get Rut1	  */
	public String getRut1();

    /** Column name Rut2 */
    public static final String COLUMNNAME_Rut2 = "Rut2";

	/** Set Rut2	  */
	public void setRut2 (String Rut2);

	/** Get Rut2	  */
	public String getRut2();

    /** Column name SecNoCrt */
    public static final String COLUMNNAME_SecNoCrt = "SecNoCrt";

	/** Set SecNoCrt	  */
	public void setSecNoCrt (String SecNoCrt);

	/** Get SecNoCrt	  */
	public String getSecNoCrt();

    /** Column name SecNoImg1 */
    public static final String COLUMNNAME_SecNoImg1 = "SecNoImg1";

	/** Set SecNoImg1	  */
	public void setSecNoImg1 (String SecNoImg1);

	/** Get SecNoImg1	  */
	public String getSecNoImg1();

    /** Column name SecNoLine1 */
    public static final String COLUMNNAME_SecNoLine1 = "SecNoLine1";

	/** Set SecNoLine1	  */
	public void setSecNoLine1 (String SecNoLine1);

	/** Get SecNoLine1	  */
	public String getSecNoLine1();

    /** Column name Seguro */
    public static final String COLUMNNAME_Seguro = "Seguro";

	/** Set Seguro	  */
	public void setSeguro (BigDecimal Seguro);

	/** Get Seguro	  */
	public BigDecimal getSeguro();

    /** Column name sheet */
    public static final String COLUMNNAME_sheet = "sheet";

	/** Set sheet	  */
	public void setsheet (int sheet);

	/** Get sheet	  */
	public int getsheet();

    /** Column name SubstituteTruck */
    public static final String COLUMNNAME_SubstituteTruck = "SubstituteTruck";

	/** Set SubstituteTruck	  */
	public void setSubstituteTruck (String SubstituteTruck);

	/** Get SubstituteTruck	  */
	public String getSubstituteTruck();

    /** Column name Tractor_ID */
    public static final String COLUMNNAME_Tractor_ID = "Tractor_ID";

	/** Set Tractor_ID	  */
	public void setTractor_ID (int Tractor_ID);

	/** Get Tractor_ID	  */
	public int getTractor_ID();

	public I_UY_TR_Truck getTractor() throws RuntimeException;

    /** Column name TransactionCode */
    public static final String COLUMNNAME_TransactionCode = "TransactionCode";

	/** Set Transaction Code.
	  * The transaction code represents the search definition
	  */
	public void setTransactionCode (String TransactionCode);

	/** Get Transaction Code.
	  * The transaction code represents the search definition
	  */
	public String getTransactionCode();

    /** Column name TruckData */
    public static final String COLUMNNAME_TruckData = "TruckData";

	/** Set TruckData	  */
	public void setTruckData (String TruckData);

	/** Get TruckData	  */
	public String getTruckData();

    /** Column name Type */
    public static final String COLUMNNAME_Type = "Type";

	/** Set Type.
	  * Type of Validation (SQL, Java Script, Java Language)
	  */
	public void setType (String Type);

	/** Get Type.
	  * Type of Validation (SQL, Java Script, Java Language)
	  */
	public String getType();

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

    /** Column name UY_Ciudad_ID_4 */
    public static final String COLUMNNAME_UY_Ciudad_ID_4 = "UY_Ciudad_ID_4";

	/** Set UY_Ciudad_ID_4	  */
	public void setUY_Ciudad_ID_4 (int UY_Ciudad_ID_4);

	/** Get UY_Ciudad_ID_4	  */
	public int getUY_Ciudad_ID_4();

    /** Column name UY_Ciudad_ID_5 */
    public static final String COLUMNNAME_UY_Ciudad_ID_5 = "UY_Ciudad_ID_5";

	/** Set UY_Ciudad_ID_5	  */
	public void setUY_Ciudad_ID_5 (int UY_Ciudad_ID_5);

	/** Get UY_Ciudad_ID_5	  */
	public int getUY_Ciudad_ID_5();

    /** Column name UY_Departamentos_ID */
    public static final String COLUMNNAME_UY_Departamentos_ID = "UY_Departamentos_ID";

	/** Set Departamentos o regiones por Pais	  */
	public void setUY_Departamentos_ID (int UY_Departamentos_ID);

	/** Get Departamentos o regiones por Pais	  */
	public int getUY_Departamentos_ID();

	public I_UY_Departamentos getUY_Departamentos() throws RuntimeException;

    /** Column name UY_Departamentos_ID_1 */
    public static final String COLUMNNAME_UY_Departamentos_ID_1 = "UY_Departamentos_ID_1";

	/** Set UY_Departamentos_ID_1	  */
	public void setUY_Departamentos_ID_1 (int UY_Departamentos_ID_1);

	/** Get UY_Departamentos_ID_1	  */
	public int getUY_Departamentos_ID_1();

    /** Column name UY_TR_Aduana_ID */
    public static final String COLUMNNAME_UY_TR_Aduana_ID = "UY_TR_Aduana_ID";

	/** Set UY_TR_Aduana	  */
	public void setUY_TR_Aduana_ID (int UY_TR_Aduana_ID);

	/** Get UY_TR_Aduana	  */
	public int getUY_TR_Aduana_ID();

	public I_UY_TR_Aduana getUY_TR_Aduana() throws RuntimeException;

    /** Column name UY_TR_Aduana_ID_1 */
    public static final String COLUMNNAME_UY_TR_Aduana_ID_1 = "UY_TR_Aduana_ID_1";

	/** Set UY_TR_Aduana_ID_1	  */
	public void setUY_TR_Aduana_ID_1 (int UY_TR_Aduana_ID_1);

	/** Get UY_TR_Aduana_ID_1	  */
	public int getUY_TR_Aduana_ID_1();

    /** Column name UY_TR_Aduana_ID_2 */
    public static final String COLUMNNAME_UY_TR_Aduana_ID_2 = "UY_TR_Aduana_ID_2";

	/** Set UY_TR_Aduana_ID_2	  */
	public void setUY_TR_Aduana_ID_2 (int UY_TR_Aduana_ID_2);

	/** Get UY_TR_Aduana_ID_2	  */
	public int getUY_TR_Aduana_ID_2();

    /** Column name UY_TR_Aduana_ID_3 */
    public static final String COLUMNNAME_UY_TR_Aduana_ID_3 = "UY_TR_Aduana_ID_3";

	/** Set UY_TR_Aduana_ID_3	  */
	public void setUY_TR_Aduana_ID_3 (int UY_TR_Aduana_ID_3);

	/** Get UY_TR_Aduana_ID_3	  */
	public int getUY_TR_Aduana_ID_3();

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

    /** Column name UY_TR_Border_ID_2 */
    public static final String COLUMNNAME_UY_TR_Border_ID_2 = "UY_TR_Border_ID_2";

	/** Set UY_TR_Border_ID_2	  */
	public void setUY_TR_Border_ID_2 (int UY_TR_Border_ID_2);

	/** Get UY_TR_Border_ID_2	  */
	public int getUY_TR_Border_ID_2();

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

    /** Column name UY_TR_Mark_ID */
    public static final String COLUMNNAME_UY_TR_Mark_ID = "UY_TR_Mark_ID";

	/** Set UY_TR_Mark	  */
	public void setUY_TR_Mark_ID (int UY_TR_Mark_ID);

	/** Get UY_TR_Mark	  */
	public int getUY_TR_Mark_ID();

	public I_UY_TR_Mark getUY_TR_Mark() throws RuntimeException;

    /** Column name UY_TR_Mark_ID_1 */
    public static final String COLUMNNAME_UY_TR_Mark_ID_1 = "UY_TR_Mark_ID_1";

	/** Set UY_TR_Mark_ID_1	  */
	public void setUY_TR_Mark_ID_1 (int UY_TR_Mark_ID_1);

	/** Get UY_TR_Mark_ID_1	  */
	public int getUY_TR_Mark_ID_1();

    /** Column name UY_TR_Mic_ID */
    public static final String COLUMNNAME_UY_TR_Mic_ID = "UY_TR_Mic_ID";

	/** Set UY_TR_Mic	  */
	public void setUY_TR_Mic_ID (int UY_TR_Mic_ID);

	/** Get UY_TR_Mic	  */
	public int getUY_TR_Mic_ID();

    /** Column name UY_TR_OperativeSite_ID */
    public static final String COLUMNNAME_UY_TR_OperativeSite_ID = "UY_TR_OperativeSite_ID";

	/** Set UY_TR_OperativeSite	  */
	public void setUY_TR_OperativeSite_ID (int UY_TR_OperativeSite_ID);

	/** Get UY_TR_OperativeSite	  */
	public int getUY_TR_OperativeSite_ID();

	public I_UY_TR_OperativeSite getUY_TR_OperativeSite() throws RuntimeException;

    /** Column name UY_TR_OperativeSite_ID_1 */
    public static final String COLUMNNAME_UY_TR_OperativeSite_ID_1 = "UY_TR_OperativeSite_ID_1";

	/** Set UY_TR_OperativeSite_ID_1	  */
	public void setUY_TR_OperativeSite_ID_1 (int UY_TR_OperativeSite_ID_1);

	/** Get UY_TR_OperativeSite_ID_1	  */
	public int getUY_TR_OperativeSite_ID_1();

    /** Column name UY_TR_PackageType_ID */
    public static final String COLUMNNAME_UY_TR_PackageType_ID = "UY_TR_PackageType_ID";

	/** Set UY_TR_PackageType	  */
	public void setUY_TR_PackageType_ID (int UY_TR_PackageType_ID);

	/** Get UY_TR_PackageType	  */
	public int getUY_TR_PackageType_ID();

	public I_UY_TR_PackageType getUY_TR_PackageType() throws RuntimeException;

    /** Column name UY_TR_TransOrder_ID */
    public static final String COLUMNNAME_UY_TR_TransOrder_ID = "UY_TR_TransOrder_ID";

	/** Set UY_TR_TransOrder	  */
	public void setUY_TR_TransOrder_ID (int UY_TR_TransOrder_ID);

	/** Get UY_TR_TransOrder	  */
	public int getUY_TR_TransOrder_ID();

	public I_UY_TR_TransOrder getUY_TR_TransOrder() throws RuntimeException;

    /** Column name UY_TR_TransOrder_ID_1 */
    public static final String COLUMNNAME_UY_TR_TransOrder_ID_1 = "UY_TR_TransOrder_ID_1";

	/** Set UY_TR_TransOrder_ID_1	  */
	public void setUY_TR_TransOrder_ID_1 (int UY_TR_TransOrder_ID_1);

	/** Get UY_TR_TransOrder_ID_1	  */
	public int getUY_TR_TransOrder_ID_1();

	public I_UY_TR_TransOrder getUY_TR_TransOrder_I() throws RuntimeException;

    /** Column name UY_TR_Truck_ID */
    public static final String COLUMNNAME_UY_TR_Truck_ID = "UY_TR_Truck_ID";

	/** Set UY_TR_Truck	  */
	public void setUY_TR_Truck_ID (int UY_TR_Truck_ID);

	/** Get UY_TR_Truck	  */
	public int getUY_TR_Truck_ID();

	public I_UY_TR_Truck getUY_TR_Truck() throws RuntimeException;

    /** Column name UY_TR_Truck_ID_1 */
    public static final String COLUMNNAME_UY_TR_Truck_ID_1 = "UY_TR_Truck_ID_1";

	/** Set UY_TR_Truck_ID_1	  */
	public void setUY_TR_Truck_ID_1 (int UY_TR_Truck_ID_1);

	/** Get UY_TR_Truck_ID_1	  */
	public int getUY_TR_Truck_ID_1();

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

    /** Column name YearFrom */
    public static final String COLUMNNAME_YearFrom = "YearFrom";

	/** Set YearFrom	  */
	public void setYearFrom (String YearFrom);

	/** Get YearFrom	  */
	public String getYearFrom();

    /** Column name YearTo */
    public static final String COLUMNNAME_YearTo = "YearTo";

	/** Set YearTo	  */
	public void setYearTo (String YearTo);

	/** Get YearTo	  */
	public String getYearTo();
}
