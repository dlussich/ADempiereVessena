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

/** Generated Interface for UY_MovBancariosHdr
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_UY_MovBancariosHdr 
{

    /** TableName=UY_MovBancariosHdr */
    public static final String Table_Name = "UY_MovBancariosHdr";

    /** AD_Table_ID=1000040 */
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

	public I_C_BankAccount getC_BankAccount() throws RuntimeException;

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

	public I_C_BPartner getC_BPartner() throws RuntimeException;

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

	public I_C_Currency getC_Currency() throws RuntimeException;

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

    /** Column name CreateFrom */
    public static final String COLUMNNAME_CreateFrom = "CreateFrom";

	/** Set Create lines from.
	  * Process which will generate a new document lines based on an existing document
	  */
	public void setCreateFrom (String CreateFrom);

	/** Get Create lines from.
	  * Process which will generate a new document lines based on an existing document
	  */
	public String getCreateFrom();

    /** Column name Date1 */
    public static final String COLUMNNAME_Date1 = "Date1";

	/** Set Date.
	  * Date when business is not conducted
	  */
	public void setDate1 (Timestamp Date1);

	/** Get Date.
	  * Date when business is not conducted
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

    /** Column name isamortizable */
    public static final String COLUMNNAME_isamortizable = "isamortizable";

	/** Set isamortizable	  */
	public void setisamortizable (boolean isamortizable);

	/** Get isamortizable	  */
	public boolean isamortizable();

    /** Column name IsGanado */
    public static final String COLUMNNAME_IsGanado = "IsGanado";

	/** Set IsGanado	  */
	public void setIsGanado (boolean IsGanado);

	/** Get IsGanado	  */
	public boolean isGanado();

    /** Column name IsInitialLoad */
    public static final String COLUMNNAME_IsInitialLoad = "IsInitialLoad";

	/** Set IsInitialLoad	  */
	public void setIsInitialLoad (boolean IsInitialLoad);

	/** Get IsInitialLoad	  */
	public boolean isInitialLoad();

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

    /** Column name MultiplyRate */
    public static final String COLUMNNAME_MultiplyRate = "MultiplyRate";

	/** Set Multiply Rate.
	  * Rate to multiple the source by to calculate the target.
	  */
	public void setMultiplyRate (BigDecimal MultiplyRate);

	/** Get Multiply Rate.
	  * Rate to multiple the source by to calculate the target.
	  */
	public BigDecimal getMultiplyRate();

    /** Column name oldstatus */
    public static final String COLUMNNAME_oldstatus = "oldstatus";

	/** Set oldstatus	  */
	public void setoldstatus (String oldstatus);

	/** Get oldstatus	  */
	public String getoldstatus();

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

    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/** Set Process Now	  */
	public void setProcessing (boolean Processing);

	/** Get Process Now	  */
	public boolean isProcessing();

    /** Column name totcobrado */
    public static final String COLUMNNAME_totcobrado = "totcobrado";

	/** Set totcobrado	  */
	public void settotcobrado (BigDecimal totcobrado);

	/** Get totcobrado	  */
	public BigDecimal gettotcobrado();

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

    /** Column name UY_C_BankAccount_From_ID */
    public static final String COLUMNNAME_UY_C_BankAccount_From_ID = "UY_C_BankAccount_From_ID";

	/** Set UY_C_BankAccount_From_ID	  */
	public void setUY_C_BankAccount_From_ID (int UY_C_BankAccount_From_ID);

	/** Get UY_C_BankAccount_From_ID	  */
	public int getUY_C_BankAccount_From_ID();

    /** Column name UY_C_Currency_From_ID */
    public static final String COLUMNNAME_UY_C_Currency_From_ID = "UY_C_Currency_From_ID";

	/** Set UY_C_Currency_From_ID	  */
	public void setUY_C_Currency_From_ID (int UY_C_Currency_From_ID);

	/** Get UY_C_Currency_From_ID	  */
	public int getUY_C_Currency_From_ID();

	public I_C_Currency getUY_C_Currency_From() throws RuntimeException;

    /** Column name uy_cuotas */
    public static final String COLUMNNAME_uy_cuotas = "uy_cuotas";

	/** Set uy_cuotas	  */
	public void setuy_cuotas (int uy_cuotas);

	/** Get uy_cuotas	  */
	public int getuy_cuotas();

    /** Column name uy_fechafirma */
    public static final String COLUMNNAME_uy_fechafirma = "uy_fechafirma";

	/** Set uy_fechafirma	  */
	public void setuy_fechafirma (Timestamp uy_fechafirma);

	/** Get uy_fechafirma	  */
	public Timestamp getuy_fechafirma();

    /** Column name uy_intereses */
    public static final String COLUMNNAME_uy_intereses = "uy_intereses";

	/** Set uy_intereses	  */
	public void setuy_intereses (BigDecimal uy_intereses);

	/** Get uy_intereses	  */
	public BigDecimal getuy_intereses();

    /** Column name UY_MediosPago_ID */
    public static final String COLUMNNAME_UY_MediosPago_ID = "UY_MediosPago_ID";

	/** Set Medios de Pago	  */
	public void setUY_MediosPago_ID (int UY_MediosPago_ID);

	/** Get Medios de Pago	  */
	public int getUY_MediosPago_ID();

	public I_UY_MediosPago getUY_MediosPago() throws RuntimeException;

    /** Column name UY_MovBancariosHdr_ID */
    public static final String COLUMNNAME_UY_MovBancariosHdr_ID = "UY_MovBancariosHdr_ID";

	/** Set UY_MovBancariosHdr	  */
	public void setUY_MovBancariosHdr_ID (int UY_MovBancariosHdr_ID);

	/** Get UY_MovBancariosHdr	  */
	public int getUY_MovBancariosHdr_ID();

    /** Column name UY_NumVale */
    public static final String COLUMNNAME_UY_NumVale = "UY_NumVale";

	/** Set Numero de Vale.
	  * Numero de Vale
	  */
	public void setUY_NumVale (int UY_NumVale);

	/** Get Numero de Vale.
	  * Numero de Vale
	  */
	public int getUY_NumVale();

	public I_UY_MovBancariosHdr getUY_NumV() throws RuntimeException;

    /** Column name UY_SubTotal */
    public static final String COLUMNNAME_UY_SubTotal = "UY_SubTotal";

	/** Set UY_SubTotal	  */
	public void setUY_SubTotal (BigDecimal UY_SubTotal);

	/** Get UY_SubTotal	  */
	public BigDecimal getUY_SubTotal();

    /** Column name uy_tasa */
    public static final String COLUMNNAME_uy_tasa = "uy_tasa";

	/** Set uy_tasa	  */
	public void setuy_tasa (int uy_tasa);

	/** Get uy_tasa	  */
	public int getuy_tasa();

    /** Column name uy_total_manual */
    public static final String COLUMNNAME_uy_total_manual = "uy_total_manual";

	/** Set uy_total_manual	  */
	public void setuy_total_manual (BigDecimal uy_total_manual);

	/** Get uy_total_manual	  */
	public BigDecimal getuy_total_manual();

    /** Column name uy_totalme */
    public static final String COLUMNNAME_uy_totalme = "uy_totalme";

	/** Set uy_totalme	  */
	public void setuy_totalme (BigDecimal uy_totalme);

	/** Get uy_totalme	  */
	public BigDecimal getuy_totalme();

    /** Column name uy_totalmn */
    public static final String COLUMNNAME_uy_totalmn = "uy_totalmn";

	/** Set uy_totalmn	  */
	public void setuy_totalmn (BigDecimal uy_totalmn);

	/** Get uy_totalmn	  */
	public BigDecimal getuy_totalmn();
}
