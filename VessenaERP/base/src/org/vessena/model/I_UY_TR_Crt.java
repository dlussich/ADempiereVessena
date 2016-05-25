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

/** Generated Interface for UY_TR_Crt
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_TR_Crt 
{

    /** TableName=UY_TR_Crt */
    public static final String Table_Name = "UY_TR_Crt";

    /** AD_Table_ID=1000772 */
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

    /** Column name amt1 */
    public static final String COLUMNNAME_amt1 = "amt1";

	/** Set amt1	  */
	public void setamt1 (BigDecimal amt1);

	/** Get amt1	  */
	public BigDecimal getamt1();

    /** Column name amt10 */
    public static final String COLUMNNAME_amt10 = "amt10";

	/** Set amt10	  */
	public void setamt10 (BigDecimal amt10);

	/** Get amt10	  */
	public BigDecimal getamt10();

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

    /** Column name amt4 */
    public static final String COLUMNNAME_amt4 = "amt4";

	/** Set amt4	  */
	public void setamt4 (BigDecimal amt4);

	/** Get amt4	  */
	public BigDecimal getamt4();

    /** Column name amt5 */
    public static final String COLUMNNAME_amt5 = "amt5";

	/** Set amt5	  */
	public void setamt5 (BigDecimal amt5);

	/** Get amt5	  */
	public BigDecimal getamt5();

    /** Column name amt6 */
    public static final String COLUMNNAME_amt6 = "amt6";

	/** Set amt6	  */
	public void setamt6 (BigDecimal amt6);

	/** Get amt6	  */
	public BigDecimal getamt6();

    /** Column name amt7 */
    public static final String COLUMNNAME_amt7 = "amt7";

	/** Set amt7	  */
	public void setamt7 (BigDecimal amt7);

	/** Get amt7	  */
	public BigDecimal getamt7();

    /** Column name amt8 */
    public static final String COLUMNNAME_amt8 = "amt8";

	/** Set amt8	  */
	public void setamt8 (BigDecimal amt8);

	/** Get amt8	  */
	public BigDecimal getamt8();

    /** Column name amt9 */
    public static final String COLUMNNAME_amt9 = "amt9";

	/** Set amt9	  */
	public void setamt9 (BigDecimal amt9);

	/** Get amt9	  */
	public BigDecimal getamt9();

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

    /** Column name C_Currency2_ID */
    public static final String COLUMNNAME_C_Currency2_ID = "C_Currency2_ID";

	/** Set C_Currency2_ID	  */
	public void setC_Currency2_ID (int C_Currency2_ID);

	/** Get C_Currency2_ID	  */
	public int getC_Currency2_ID();

	public org.compiere.model.I_C_Currency getC_Currency2() throws RuntimeException;

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

    /** Column name C_Location_ID */
    public static final String COLUMNNAME_C_Location_ID = "C_Location_ID";

	/** Set Address.
	  * Location or Address
	  */
	public void setC_Location_ID (int C_Location_ID);

	/** Get Address.
	  * Location or Address
	  */
	public int getC_Location_ID();

	public org.compiere.model.I_C_Location getC_Location() throws RuntimeException;

    /** Column name codigo */
    public static final String COLUMNNAME_codigo = "codigo";

	/** Set codigo	  */
	public void setcodigo (String codigo);

	/** Get codigo	  */
	public String getcodigo();

    /** Column name Consignatario */
    public static final String COLUMNNAME_Consignatario = "Consignatario";

	/** Set Consignatario	  */
	public void setConsignatario (String Consignatario);

	/** Get Consignatario	  */
	public String getConsignatario();

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

    /** Column name Destinatario2 */
    public static final String COLUMNNAME_Destinatario2 = "Destinatario2";

	/** Set Destinatario2	  */
	public void setDestinatario2 (String Destinatario2);

	/** Get Destinatario2	  */
	public String getDestinatario2();

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

    /** Column name FleteLiteral */
    public static final String COLUMNNAME_FleteLiteral = "FleteLiteral";

	/** Set FleteLiteral	  */
	public void setFleteLiteral (String FleteLiteral);

	/** Get FleteLiteral	  */
	public String getFleteLiteral();

    /** Column name Importe */
    public static final String COLUMNNAME_Importe = "Importe";

	/** Set Importe	  */
	public void setImporte (String Importe);

	/** Get Importe	  */
	public String getImporte();

    /** Column name Info */
    public static final String COLUMNNAME_Info = "Info";

	/** Set Info.
	  * Information
	  */
	public void setInfo (String Info);

	/** Get Info.
	  * Information
	  */
	public String getInfo();

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

    /** Column name IsOldSequence */
    public static final String COLUMNNAME_IsOldSequence = "IsOldSequence";

	/** Set IsOldSequence	  */
	public void setIsOldSequence (boolean IsOldSequence);

	/** Get IsOldSequence	  */
	public boolean isOldSequence();

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

    /** Column name LiteralNumber */
    public static final String COLUMNNAME_LiteralNumber = "LiteralNumber";

	/** Set LiteralNumber	  */
	public void setLiteralNumber (String LiteralNumber);

	/** Get LiteralNumber	  */
	public String getLiteralNumber();

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

    /** Column name MicDnaNo */
    public static final String COLUMNNAME_MicDnaNo = "MicDnaNo";

	/** Set MicDnaNo	  */
	public void setMicDnaNo (String MicDnaNo);

	/** Get MicDnaNo	  */
	public String getMicDnaNo();

    /** Column name MicNo */
    public static final String COLUMNNAME_MicNo = "MicNo";

	/** Set MicNo	  */
	public void setMicNo (String MicNo);

	/** Get MicNo	  */
	public String getMicNo();

    /** Column name Notificar */
    public static final String COLUMNNAME_Notificar = "Notificar";

	/** Set Notificar	  */
	public void setNotificar (String Notificar);

	/** Get Notificar	  */
	public String getNotificar();

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

    /** Column name otros */
    public static final String COLUMNNAME_otros = "otros";

	/** Set otros	  */
	public void setotros (String otros);

	/** Get otros	  */
	public String getotros();

    /** Column name otros2 */
    public static final String COLUMNNAME_otros2 = "otros2";

	/** Set otros2	  */
	public void setotros2 (String otros2);

	/** Get otros2	  */
	public String getotros2();

    /** Column name otros3 */
    public static final String COLUMNNAME_otros3 = "otros3";

	/** Set otros3	  */
	public void setotros3 (String otros3);

	/** Get otros3	  */
	public String getotros3();

    /** Column name Porteador */
    public static final String COLUMNNAME_Porteador = "Porteador";

	/** Set Porteador	  */
	public void setPorteador (String Porteador);

	/** Get Porteador	  */
	public String getPorteador();

    /** Column name Porteador2 */
    public static final String COLUMNNAME_Porteador2 = "Porteador2";

	/** Set Porteador2	  */
	public void setPorteador2 (String Porteador2);

	/** Get Porteador2	  */
	public String getPorteador2();

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

    /** Column name ProductAmt */
    public static final String COLUMNNAME_ProductAmt = "ProductAmt";

	/** Set ProductAmt	  */
	public void setProductAmt (String ProductAmt);

	/** Get ProductAmt	  */
	public String getProductAmt();

    /** Column name QtyPackage */
    public static final String COLUMNNAME_QtyPackage = "QtyPackage";

	/** Set QtyPackage	  */
	public void setQtyPackage (BigDecimal QtyPackage);

	/** Get QtyPackage	  */
	public BigDecimal getQtyPackage();

    /** Column name Reembolso */
    public static final String COLUMNNAME_Reembolso = "Reembolso";

	/** Set Reembolso	  */
	public void setReembolso (String Reembolso);

	/** Get Reembolso	  */
	public String getReembolso();

    /** Column name Remitente */
    public static final String COLUMNNAME_Remitente = "Remitente";

	/** Set Remitente	  */
	public void setRemitente (String Remitente);

	/** Get Remitente	  */
	public String getRemitente();

    /** Column name Remitente2 */
    public static final String COLUMNNAME_Remitente2 = "Remitente2";

	/** Set Remitente2	  */
	public void setRemitente2 (String Remitente2);

	/** Get Remitente2	  */
	public String getRemitente2();

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

    /** Column name transporte */
    public static final String COLUMNNAME_transporte = "transporte";

	/** Set transporte	  */
	public void settransporte (String transporte);

	/** Get transporte	  */
	public String gettransporte();

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

    /** Column name UY_TR_Trip_ID */
    public static final String COLUMNNAME_UY_TR_Trip_ID = "UY_TR_Trip_ID";

	/** Set UY_TR_Trip	  */
	public void setUY_TR_Trip_ID (int UY_TR_Trip_ID);

	/** Get UY_TR_Trip	  */
	public int getUY_TR_Trip_ID();

	public I_UY_TR_Trip getUY_TR_Trip() throws RuntimeException;

    /** Column name ValorFleteExt */
    public static final String COLUMNNAME_ValorFleteExt = "ValorFleteExt";

	/** Set ValorFleteExt	  */
	public void setValorFleteExt (String ValorFleteExt);

	/** Get ValorFleteExt	  */
	public String getValorFleteExt();

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
