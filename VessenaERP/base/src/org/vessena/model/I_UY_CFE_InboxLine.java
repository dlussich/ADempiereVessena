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

/** Generated Interface for UY_CFE_InboxLine
 *  @author Adempiere (generated) 
 *  @version Release 3.7.1RC
 */
public interface I_UY_CFE_InboxLine 
{

    /** TableName=UY_CFE_InboxLine */
    public static final String Table_Name = "UY_CFE_InboxLine";

    /** AD_Table_ID=1010778 */
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

    /** Column name Address1 */
    public static final String COLUMNNAME_Address1 = "Address1";

	/** Set Address 1.
	  * Address line 1 for this location
	  */
	public void setAddress1 (String Address1);

	/** Get Address 1.
	  * Address line 1 for this location
	  */
	public String getAddress1();

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

    /** Column name CfeType */
    public static final String COLUMNNAME_CfeType = "CfeType";

	/** Set CfeType	  */
	public void setCfeType (String CfeType);

	/** Get CfeType	  */
	public String getCfeType();

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

    /** Column name C_PaymentTerm_ID */
    public static final String COLUMNNAME_C_PaymentTerm_ID = "C_PaymentTerm_ID";

	/** Set Payment Term.
	  * The terms of Payment (timing, discount)
	  */
	public void setC_PaymentTerm_ID (int C_PaymentTerm_ID);

	/** Get Payment Term.
	  * The terms of Payment (timing, discount)
	  */
	public int getC_PaymentTerm_ID();

	public org.compiere.model.I_C_PaymentTerm getC_PaymentTerm() throws RuntimeException;

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

    /** Column name DateCFE */
    public static final String COLUMNNAME_DateCFE = "DateCFE";

	/** Set DateCFE	  */
	public void setDateCFE (Timestamp DateCFE);

	/** Get DateCFE	  */
	public Timestamp getDateCFE();

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

    /** Column name DGIStatus */
    public static final String COLUMNNAME_DGIStatus = "DGIStatus";

	/** Set DGIStatus	  */
	public void setDGIStatus (String DGIStatus);

	/** Get DGIStatus	  */
	public String getDGIStatus();

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

    /** Column name ExecuteAction */
    public static final String COLUMNNAME_ExecuteAction = "ExecuteAction";

	/** Set ExecuteAction	  */
	public void setExecuteAction (String ExecuteAction);

	/** Get ExecuteAction	  */
	public String getExecuteAction();

    /** Column name GrandTotal */
    public static final String COLUMNNAME_GrandTotal = "GrandTotal";

	/** Set Grand Total.
	  * Total amount of document
	  */
	public void setGrandTotal (BigDecimal GrandTotal);

	/** Get Grand Total.
	  * Total amount of document
	  */
	public BigDecimal getGrandTotal();

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

    /** Column name IsSelected */
    public static final String COLUMNNAME_IsSelected = "IsSelected";

	/** Set Selected	  */
	public void setIsSelected (boolean IsSelected);

	/** Get Selected	  */
	public boolean isSelected();

    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/** Set Name.
	  * Alphanumeric identifier of the entity
	  */
	public void setName (String Name);

	/** Get Name.
	  * Alphanumeric identifier of the entity
	  */
	public String getName();

    /** Column name NroCAE */
    public static final String COLUMNNAME_NroCAE = "NroCAE";

	/** Set NroCAE	  */
	public void setNroCAE (String NroCAE);

	/** Get NroCAE	  */
	public String getNroCAE();

    /** Column name NroCFE */
    public static final String COLUMNNAME_NroCFE = "NroCFE";

	/** Set NroCFE	  */
	public void setNroCFE (String NroCFE);

	/** Get NroCFE	  */
	public String getNroCFE();

    /** Column name nrodoc */
    public static final String COLUMNNAME_nrodoc = "nrodoc";

	/** Set nrodoc	  */
	public void setnrodoc (String nrodoc);

	/** Get nrodoc	  */
	public String getnrodoc();

    /** Column name observaciones */
    public static final String COLUMNNAME_observaciones = "observaciones";

	/** Set observaciones	  */
	public void setobservaciones (String observaciones);

	/** Get observaciones	  */
	public String getobservaciones();

    /** Column name Postal */
    public static final String COLUMNNAME_Postal = "Postal";

	/** Set ZIP.
	  * Postal code
	  */
	public void setPostal (String Postal);

	/** Get ZIP.
	  * Postal code
	  */
	public String getPostal();

    /** Column name PrintDoc */
    public static final String COLUMNNAME_PrintDoc = "PrintDoc";

	/** Set PrintDoc	  */
	public void setPrintDoc (boolean PrintDoc);

	/** Get PrintDoc	  */
	public boolean isPrintDoc();

    /** Column name serie */
    public static final String COLUMNNAME_serie = "serie";

	/** Set serie	  */
	public void setserie (String serie);

	/** Get serie	  */
	public String getserie();

    /** Column name SourceType */
    public static final String COLUMNNAME_SourceType = "SourceType";

	/** Set SourceType	  */
	public void setSourceType (String SourceType);

	/** Get SourceType	  */
	public String getSourceType();

    /** Column name TotalLines */
    public static final String COLUMNNAME_TotalLines = "TotalLines";

	/** Set Total Lines.
	  * Total of all document lines
	  */
	public void setTotalLines (BigDecimal TotalLines);

	/** Get Total Lines.
	  * Total of all document lines
	  */
	public BigDecimal getTotalLines();

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

    /** Column name UY_CFE_Inbox_ID */
    public static final String COLUMNNAME_UY_CFE_Inbox_ID = "UY_CFE_Inbox_ID";

	/** Set UY_CFE_Inbox	  */
	public void setUY_CFE_Inbox_ID (int UY_CFE_Inbox_ID);

	/** Get UY_CFE_Inbox	  */
	public int getUY_CFE_Inbox_ID();

	public I_UY_CFE_Inbox getUY_CFE_Inbox() throws RuntimeException;

    /** Column name UY_CFE_InboxLine_ID */
    public static final String COLUMNNAME_UY_CFE_InboxLine_ID = "UY_CFE_InboxLine_ID";

	/** Set UY_CFE_InboxLine	  */
	public void setUY_CFE_InboxLine_ID (int UY_CFE_InboxLine_ID);

	/** Get UY_CFE_InboxLine	  */
	public int getUY_CFE_InboxLine_ID();

    /** Column name UY_CFE_InboxLoad_ID */
    public static final String COLUMNNAME_UY_CFE_InboxLoad_ID = "UY_CFE_InboxLoad_ID";

	/** Set UY_CFE_InboxLoad	  */
	public void setUY_CFE_InboxLoad_ID (int UY_CFE_InboxLoad_ID);

	/** Get UY_CFE_InboxLoad	  */
	public int getUY_CFE_InboxLoad_ID();

	public I_UY_CFE_InboxLoad getUY_CFE_InboxLoad() throws RuntimeException;

    /** Column name UY_Departamentos_ID */
    public static final String COLUMNNAME_UY_Departamentos_ID = "UY_Departamentos_ID";

	/** Set Departamentos o regiones por Pais	  */
	public void setUY_Departamentos_ID (int UY_Departamentos_ID);

	/** Get Departamentos o regiones por Pais	  */
	public int getUY_Departamentos_ID();

	public I_UY_Departamentos getUY_Departamentos() throws RuntimeException;

    /** Column name UY_Localidades_ID */
    public static final String COLUMNNAME_UY_Localidades_ID = "UY_Localidades_ID";

	/** Set Localidades por Departamentos	  */
	public void setUY_Localidades_ID (int UY_Localidades_ID);

	/** Get Localidades por Departamentos	  */
	public int getUY_Localidades_ID();

	public I_UY_Localidades getUY_Localidades() throws RuntimeException;
}
