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

/** Generated Interface for UY_MediosPago
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_MediosPago 
{

    /** TableName=UY_MediosPago */
    public static final String Table_Name = "UY_MediosPago";

    /** AD_Table_ID=1000027 */
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

    /** Column name A_Name */
    public static final String COLUMNNAME_A_Name = "A_Name";

	/** Set Account Name.
	  * Name on Credit Card or Account holder
	  */
	public void setA_Name (String A_Name);

	/** Get Account Name.
	  * Name on Credit Card or Account holder
	  */
	public String getA_Name();

    /** Column name C_BankAccount_ID */
    public static final String COLUMNNAME_C_BankAccount_ID = "C_BankAccount_ID";

	/** Set Bank Account.
	  * Account at the Bank
	  */
	public void setC_BankAccount_ID (int C_BankAccount_ID);

	/** Get Bank Account.
	  * Account at the Bank
	  */
	public int getC_BankAccount_ID();

	public org.compiere.model.I_C_BankAccount getC_BankAccount() throws RuntimeException;

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

    /** Column name CheckLetter */
    public static final String COLUMNNAME_CheckLetter = "CheckLetter";

	/** Set CheckLetter	  */
	public void setCheckLetter (String CheckLetter);

	/** Get CheckLetter	  */
	public String getCheckLetter();

    /** Column name CheckLetter2 */
    public static final String COLUMNNAME_CheckLetter2 = "CheckLetter2";

	/** Set CheckLetter2	  */
	public void setCheckLetter2 (String CheckLetter2);

	/** Get CheckLetter2	  */
	public String getCheckLetter2();

    /** Column name CheckNo */
    public static final String COLUMNNAME_CheckNo = "CheckNo";

	/** Set Check No.
	  * Check Number
	  */
	public void setCheckNo (String CheckNo);

	/** Get Check No.
	  * Check Number
	  */
	public String getCheckNo();

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

    /** Column name Custodio */
    public static final String COLUMNNAME_Custodio = "Custodio";

	/** Set Custodio	  */
	public void setCustodio (String Custodio);

	/** Get Custodio	  */
	public String getCustodio();

    /** Column name Date1 */
    public static final String COLUMNNAME_Date1 = "Date1";

	/** Set Date.
	  * Fecha Original del Documento 
	  */
	public void setDate1 (Timestamp Date1);

	/** Get Date.
	  * Fecha Original del Documento 
	  */
	public Timestamp getDate1();

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

    /** Column name DateFrom */
    public static final String COLUMNNAME_DateFrom = "DateFrom";

	/** Set Date From.
	  * Starting date for a range
	  */
	public void setDateFrom (Timestamp DateFrom);

	/** Get Date From.
	  * Starting date for a range
	  */
	public Timestamp getDateFrom();

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

    /** Column name DivideRate */
    public static final String COLUMNNAME_DivideRate = "DivideRate";

	/** Set Divide Rate.
	  * To convert Source number to Target number, the Source is divided
	  */
	public void setDivideRate (BigDecimal DivideRate);

	/** Get Divide Rate.
	  * To convert Source number to Target number, the Source is divided
	  */
	public BigDecimal getDivideRate();

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

    /** Column name DueDate */
    public static final String COLUMNNAME_DueDate = "DueDate";

	/** Set Due Date.
	  * Date when the payment is due
	  */
	public void setDueDate (Timestamp DueDate);

	/** Get Due Date.
	  * Date when the payment is due
	  */
	public Timestamp getDueDate();

    /** Column name estado */
    public static final String COLUMNNAME_estado = "estado";

	/** Set estado	  */
	public void setestado (String estado);

	/** Get estado	  */
	public String getestado();

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

    /** Column name IsInitialLoad */
    public static final String COLUMNNAME_IsInitialLoad = "IsInitialLoad";

	/** Set IsInitialLoad	  */
	public void setIsInitialLoad (boolean IsInitialLoad);

	/** Get IsInitialLoad	  */
	public boolean isInitialLoad();

    /** Column name IsPrinted */
    public static final String COLUMNNAME_IsPrinted = "IsPrinted";

	/** Set Printed.
	  * Indicates if this document / line is printed
	  */
	public void setIsPrinted (boolean IsPrinted);

	/** Get Printed.
	  * Indicates if this document / line is printed
	  */
	public boolean isPrinted();

    /** Column name isrechazado */
    public static final String COLUMNNAME_isrechazado = "isrechazado";

	/** Set isrechazado	  */
	public void setisrechazado (boolean isrechazado);

	/** Get isrechazado	  */
	public boolean isrechazado();

    /** Column name IsSOTrx */
    public static final String COLUMNNAME_IsSOTrx = "IsSOTrx";

	/** Set Sales Transaction.
	  * This is a Sales Transaction
	  */
	public void setIsSOTrx (boolean IsSOTrx);

	/** Get Sales Transaction.
	  * This is a Sales Transaction
	  */
	public boolean isSOTrx();

    /** Column name Micr */
    public static final String COLUMNNAME_Micr = "Micr";

	/** Set Micr.
	  * Combination of routing no, account and check no
	  */
	public void setMicr (String Micr);

	/** Get Micr.
	  * Combination of routing no, account and check no
	  */
	public String getMicr();

    /** Column name nobanco */
    public static final String COLUMNNAME_nobanco = "nobanco";

	/** Set nobanco	  */
	public void setnobanco (String nobanco);

	/** Get nobanco	  */
	public String getnobanco();

    /** Column name oldstatus */
    public static final String COLUMNNAME_oldstatus = "oldstatus";

	/** Set oldstatus	  */
	public void setoldstatus (String oldstatus);

	/** Get oldstatus	  */
	public String getoldstatus();

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

    /** Column name Posted */
    public static final String COLUMNNAME_Posted = "Posted";

	/** Set Posted.
	  * Posting status
	  */
	public void setPosted (boolean Posted);

	/** Get Posted.
	  * Posting status
	  */
	public boolean isPosted();

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

    /** Column name RoutingNo */
    public static final String COLUMNNAME_RoutingNo = "RoutingNo";

	/** Set Routing No.
	  * Bank Routing Number
	  */
	public void setRoutingNo (String RoutingNo);

	/** Get Routing No.
	  * Bank Routing Number
	  */
	public String getRoutingNo();

    /** Column name serie */
    public static final String COLUMNNAME_serie = "serie";

	/** Set serie	  */
	public void setserie (String serie);

	/** Get serie	  */
	public String getserie();

    /** Column name TenderType */
    public static final String COLUMNNAME_TenderType = "TenderType";

	/** Set Tender type.
	  * Method of Payment
	  */
	public void setTenderType (String TenderType);

	/** Get Tender type.
	  * Method of Payment
	  */
	public String getTenderType();

    /** Column name tipomp */
    public static final String COLUMNNAME_tipomp = "tipomp";

	/** Set tipomp	  */
	public void settipomp (String tipomp);

	/** Get tipomp	  */
	public String gettipomp();

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

    /** Column name UY_CheckReam_ID */
    public static final String COLUMNNAME_UY_CheckReam_ID = "UY_CheckReam_ID";

	/** Set UY_CheckReam	  */
	public void setUY_CheckReam_ID (int UY_CheckReam_ID);

	/** Get UY_CheckReam	  */
	public int getUY_CheckReam_ID();

	public I_UY_CheckReam getUY_CheckReam() throws RuntimeException;

    /** Column name UY_CheckReamLine_ID */
    public static final String COLUMNNAME_UY_CheckReamLine_ID = "UY_CheckReamLine_ID";

	/** Set UY_CheckReamLine	  */
	public void setUY_CheckReamLine_ID (int UY_CheckReamLine_ID);

	/** Get UY_CheckReamLine	  */
	public int getUY_CheckReamLine_ID();

	public I_UY_CheckReamLine getUY_CheckReamLine() throws RuntimeException;

    /** Column name UY_DevolucionChq_ID */
    public static final String COLUMNNAME_UY_DevolucionChq_ID = "UY_DevolucionChq_ID";

	/** Set UY_DevolucionChq	  */
	public void setUY_DevolucionChq_ID (int UY_DevolucionChq_ID);

	/** Get UY_DevolucionChq	  */
	public int getUY_DevolucionChq_ID();

    /** Column name UY_InvoiceCashPayment_ID */
    public static final String COLUMNNAME_UY_InvoiceCashPayment_ID = "UY_InvoiceCashPayment_ID";

	/** Set UY_InvoiceCashPayment	  */
	public void setUY_InvoiceCashPayment_ID (int UY_InvoiceCashPayment_ID);

	/** Get UY_InvoiceCashPayment	  */
	public int getUY_InvoiceCashPayment_ID();

	public I_UY_InvoiceCashPayment getUY_InvoiceCashPayment() throws RuntimeException;

    /** Column name uy_isreemplazo */
    public static final String COLUMNNAME_uy_isreemplazo = "uy_isreemplazo";

	/** Set uy_isreemplazo	  */
	public void setuy_isreemplazo (boolean uy_isreemplazo);

	/** Get uy_isreemplazo	  */
	public boolean isuy_isreemplazo();

    /** Column name UY_LinePayment_ID */
    public static final String COLUMNNAME_UY_LinePayment_ID = "UY_LinePayment_ID";

	/** Set UY_LinePayment	  */
	public void setUY_LinePayment_ID (int UY_LinePayment_ID);

	/** Get UY_LinePayment	  */
	public int getUY_LinePayment_ID();

	public I_UY_LinePayment getUY_LinePayment() throws RuntimeException;

    /** Column name UY_MediosPago_ID */
    public static final String COLUMNNAME_UY_MediosPago_ID = "UY_MediosPago_ID";

	/** Set Medios de Pago	  */
	public void setUY_MediosPago_ID (int UY_MediosPago_ID);

	/** Get Medios de Pago	  */
	public int getUY_MediosPago_ID();

    /** Column name UY_MovBancariosHdr_ID */
    public static final String COLUMNNAME_UY_MovBancariosHdr_ID = "UY_MovBancariosHdr_ID";

	/** Set UY_MovBancariosHdr_ID	  */
	public void setUY_MovBancariosHdr_ID (int UY_MovBancariosHdr_ID);

	/** Get UY_MovBancariosHdr_ID	  */
	public int getUY_MovBancariosHdr_ID();

	public I_UY_MovBancariosHdr getUY_MovBancariosHdr() throws RuntimeException;

    /** Column name UY_MovBancariosLine_ID */
    public static final String COLUMNNAME_UY_MovBancariosLine_ID = "UY_MovBancariosLine_ID";

	/** Set UY_MovBancariosLine_ID	  */
	public void setUY_MovBancariosLine_ID (int UY_MovBancariosLine_ID);

	/** Get UY_MovBancariosLine_ID	  */
	public int getUY_MovBancariosLine_ID();

	public I_UY_MovBancariosLine getUY_MovBancariosLine() throws RuntimeException;

    /** Column name UY_PayEmit_ID */
    public static final String COLUMNNAME_UY_PayEmit_ID = "UY_PayEmit_ID";

	/** Set UY_PayEmit	  */
	public void setUY_PayEmit_ID (int UY_PayEmit_ID);

	/** Get UY_PayEmit	  */
	public int getUY_PayEmit_ID();

	public I_UY_PayEmit getUY_PayEmit() throws RuntimeException;

    /** Column name UY_PaymentRule_ID */
    public static final String COLUMNNAME_UY_PaymentRule_ID = "UY_PaymentRule_ID";

	/** Set UY_PaymentRule	  */
	public void setUY_PaymentRule_ID (int UY_PaymentRule_ID);

	/** Get UY_PaymentRule	  */
	public int getUY_PaymentRule_ID();

	public I_UY_PaymentRule getUY_PaymentRule() throws RuntimeException;

    /** Column name UY_PayOrder_ID */
    public static final String COLUMNNAME_UY_PayOrder_ID = "UY_PayOrder_ID";

	/** Set UY_PayOrder	  */
	public void setUY_PayOrder_ID (int UY_PayOrder_ID);

	/** Get UY_PayOrder	  */
	public int getUY_PayOrder_ID();

	public I_UY_PayOrder getUY_PayOrder() throws RuntimeException;

    /** Column name UY_Reemplaza_ID */
    public static final String COLUMNNAME_UY_Reemplaza_ID = "UY_Reemplaza_ID";

	/** Set UY_Reemplaza_ID	  */
	public void setUY_Reemplaza_ID (int UY_Reemplaza_ID);

	/** Get UY_Reemplaza_ID	  */
	public int getUY_Reemplaza_ID();
}
